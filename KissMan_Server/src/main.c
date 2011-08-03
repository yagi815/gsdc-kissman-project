/*
 * main.c
 *
 *  Created on: Mar 16, 2011
 *      Author: rsyoung
 */

#include <sys/types.h>
#include <unistd.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/time.h>
#include <sys/ioctl.h>

#include "service.h"


static unsigned int get_server_num(const unsigned int svr_code);
static unsigned int get_service_type(const unsigned int svr_code);
static void make_new_connection(const int server_sockfd, fd_set* masterfds, int* fdmax);
static void *get_in_addr(struct sockaddr *sa);
static void set_server_addr(struct sockaddr_in* svr_addr);
static int get_request(const int fd, struct  service_struct* svc_struct);
static void process_request(const int fd);
static int get_service_code(const int fd, unsigned int* svc_code);
static int get_command_line(const int fd, struct service_struct* svc_struct);

static int check_server_num(int svr_num);
static int check_service_type(struct service_struct *svc_struct);

static void dump_svc(struct service_struct* svc_struct);

extern int execute_service(const int fd, const struct service_struct svc_struct);

static int server_num;

int main(int argc, char* argv[])
{

	int server_sockfd;
	socklen_t server_len;;

	struct sockaddr_in server_address;

	int ret;
	fd_set masterfds, readfds;

	/* arguments should be 2 for correct execution */
	/*
	 * temporally blocked
	if ( argc != 2 ) {
        printf( "usage: %s <server number>\n", argv[0] );
        exit(-1);
    }
	server_num = atoi(argv[1]);
	*/



	/* create a socket */
	server_sockfd = socket(AF_INET, SOCK_STREAM, 0);
	printf("[MSG] : server_sockfd = %d\n", server_sockfd);


	/* setsockopt: Handy debugging trick that lets
	* us rerun the server immediately after we kill it;
	* otherwise we have to wait about 20 secs.
	* Eliminates "ERROR on binding: Address already in use" error.
	*/
	int optval = 1;
	setsockopt(server_sockfd, SOL_SOCKET, SO_REUSEADDR,
		 (const void *)&optval , sizeof(int));

	set_server_addr(&server_address);
	server_len = sizeof(server_address);



	/* bind the socket to an address */
	ret = bind(server_sockfd, (struct sockaddr *)&server_address, server_len);
	if (ret != 0) {
		printf("[Error] : failure to bind at %d, %s\n", __LINE__, __FILE__);
		exit(1);
	}

	/* listen from connection */
	ret = listen(server_sockfd, MAX_QUEUE_LEN);

	if (ret != 0) {
		perror("Error: fail to listen\n");
		exit(1);
	}

	FD_ZERO(&readfds);
	FD_ZERO(&masterfds);
	FD_SET(server_sockfd, &masterfds);

	int fdmax = server_sockfd;

	while(1)
	{
		int fd = -1;

		readfds = masterfds;

		/* checks to see if any sockets are ready for reading */
		ret = select(fdmax+1, &readfds, (fd_set *)0,
										(fd_set *)0, (struct timeval *)0);
		if(ret < 1) {
			printf("[Error] : failure to select fd at %d, %s\n", __LINE__, __FILE__);
			exit(-1);
		}

		/* check if there is a fd to read */

		for(fd = 0; fd <= fdmax; fd++) {

			/* if fd is not set in readfds, then skip the rest code */
			if(!FD_ISSET(fd, &readfds)) continue;

			// printf("fd = %d\n", fd);

			if (fd == server_sockfd) {
				make_new_connection(server_sockfd, &masterfds, &fdmax);
			} else {
				process_request(fd);

				FD_CLR(fd, &masterfds);
				close(fd);
				printf("[MSG] : fd = %d closed\n", fd);
			}
		}
	}

	close(server_sockfd);

	return 0;
}

/**
 * set server address
 *
 * @param svr_addr sockaddr_in struct to be filled with values
 */
static void set_server_addr(struct sockaddr_in* svr_addr)
{
	svr_addr->sin_family = AF_INET;
	svr_addr->sin_addr.s_addr = htonl(INADDR_ANY);
	svr_addr->sin_port = htons(PORT_NUM);
}

/**
 * get sockaddr for IPv4 or IPv6
 *
 * @param sa socket address
 *  */
static void *get_in_addr(struct sockaddr *sa)
{
	if (sa->sa_family == AF_INET) {
		return &(((struct sockaddr_in*)sa)->sin_addr);
	}

	return &(((struct sockaddr_in6*)sa)->sin6_addr);
}

/**
 * make a new connection from a client
 *
 * @param server_sockfd server socket fd
 * @param masterfds master file descriptor set
 * @param fdmax max file descriptor to return
 */
static void make_new_connection(const int server_sockfd, fd_set* masterfds, int* fdmax)
{

	int client_sockfd;
	socklen_t client_len;
	struct sockaddr_storage client_address;

	client_len = sizeof(client_address);
	client_sockfd = accept(server_sockfd,
			(struct sockaddr *)&client_address, &client_len);

	if(client_sockfd < 0) {
		printf("[Error] : cannot accept a new connection at %d, %s\n", __LINE__, __FILE__);
		return;
	}

	FD_SET(client_sockfd, masterfds);
	char clientIP[INET6_ADDRSTRLEN];

	printf("[MSG] : new connection from %s on socket %d\n",
					inet_ntop(client_address.ss_family,
							  get_in_addr((struct sockaddr*)&client_address),
							  clientIP, INET6_ADDRSTRLEN),
							  client_sockfd);

	if(client_sockfd > *fdmax) *fdmax = client_sockfd;

}

/**
 * process a request from a client. It gets a service code, identifies a service
 * and execute service according to the service code.
 *
 * @param fd file descriptor to communicate.
 */
static void process_request(const int fd)
{
	unsigned int service_code = 0;
	struct service_struct svc_struct;

	/* try to get service code. If fail, then just return */
	if (-1 == get_request(fd, &svc_struct)) {
		printf("[Error] : failed to get service code = %d\n", service_code);
		return;
	}

	/* execute service */
	execute_service(fd, svc_struct);
}



/**
 * receive a request from a client. The request may consist of two command
 * 1) service code + (-) 2) command.
 *
 * Particularly onevm, condor* releated commands are being sent from a client with
 * service code + its shell command
 *
 * @param fd file descriptor to receive service code and command
 * @param svc_struct contains a server, service type and command
 *
 * @return 1 if successfully received; otherwise -1
 */
static int get_request(const int fd, struct  service_struct* svc_struct)
{

	unsigned int svc_code;

	if (-1 == get_service_code(fd, &svc_code)) return -1;

	/* get server number  and check if it is correct */
	svc_struct->server_num = get_server_num(svc_code);

	/*
	 * temporally blocked
	if (-1 == check_server_num(svc_struct->server_num)) return -1;
	*/


	/* get service type and check if it is correct */
	svc_struct->service_type = get_service_type(svc_code);
	if (-1 == check_service_type(svc_struct)) return -1;

	/* get shell command if there is */
	if (-1 == get_command_line(fd, svc_struct)) return -1;

	dump_svc(svc_struct);

	return 1;
}

/**
 * get service code from fd
 *
 * @param fd file descriptor being used to communicated with a client
 * @param svc_code service code to receive from the client
 * @return 1 if success; otherwise -1
 */
static int get_service_code(const int fd, unsigned int* svc_code)
{
	int ret;
	char buf[4];

	if ((ret = recv(fd, buf, 4, 0)) <= 0) {
		if(ret == 0)
			/* connection closed */
			printf("[MSG] : socket %d hung up\n", fd);
		else
			printf("[Error] : recv() error lol at %d, %s.\n", __LINE__, __FILE__);

		return -1;
	}

	*svc_code = atoi(buf);
	if(*svc_code == 0) return -1;

	return 1;

}


/**
 * check the server number if requested server and current running one are same.
 * It is to prevent from executing commands which are not supported by
 * running server
 *
 * @param svr_num server number which is requested
 * @return 1 if correct; otherwise -1
 */
static int check_server_num(int svr_num)
{
	if (svr_num != server_num) {
		printf("[Error] : Server number is not matched: cur_svr[%u] != req_svr[%u]\n",
													server_num, svr_num);
		return -1;
	}

	return 1;
}

/**
 * receive a shell command line.
 *
 * @param fd file descriptor to receive command line from a client
 * @svc_struct data structure to contain the command line
 * @return 1 if success; otherwise -1
 */
static int get_command_line(const int fd, struct service_struct* svc_struct)
{
	char cmdbuf[CMD_LINE_LEN];
	int ret;
	int svr_num = svc_struct->server_num;

	/* copy command */
	switch (svr_num) {
	case SVR_ONE_CLOUD_SYSTEM:
	case SVR_CONDOR_BATCH_SYSTEM:
		if ((ret = recv(fd, cmdbuf, CMD_LINE_LEN, 0)) <= 0) {
			if(ret == 0)
				/* connection closed */
				printf("[MSG] : socket %d hung up\n", fd);
			else
				printf("[Error] : recv() error lol at %d, %s.\n", __LINE__, __FILE__);

			return -1;
		}

		bzero(svc_struct->cmdline, CMD_LINE_LEN);
		strncpy(svc_struct->cmdline, cmdbuf, CMD_LINE_LEN);
	}
	return 1;
}


/**
 * check service type
 *
 * @param svc_struct service structure containing service type
 * @return 1 if valid; otherwise -1
 */
static int check_service_type(struct service_struct *svc_struct)
{
	int svr_num = svc_struct->server_num;
	int svc_type = svc_struct->service_type;

	switch (svr_num) {
	case SVR_SAM_STATION:
		if (svc_type == 0 || svc_type >= SVC_SAM_MAX) {
			printf("[Error] : service number is wrong: server[%u], service [%u]"
					" at %d, %s\n", svr_num, svc_type, __LINE__, __FILE__);
			return -1;
		}
		break;

	default:
		break;
	}

	return 1;
}


/**
 * dump service structure
 *
 * @param svc_struct service structure to dump
 */
static void dump_svc(struct service_struct* svc_struct)
{
	printf("[MSG] : contents of service_struct\n");
	printf("        server num  : [%d]\n", svc_struct->server_num);
	printf("        service type: [%d]\n", svc_struct->service_type);
	printf("        command     : %s\n", svc_struct->cmdline);
}

/**
 * get a server number from a given code. A server number was 8 bit
 * left shifted. So, in order to get the right server number, it has to be
 * right shifted properly. For example, server number is 1, then it is encoded
 * as 100000000.
 *
 * @param svr_code a code given from a client which contains a service number
 * and service type by bit operation.
 *
 * @return sever number
 */

static unsigned int get_server_num(const unsigned int svr_code)
{
	return svr_code >> OFFSET_SERVER_TYPE;
}


/**
 * get a service type from a given code. A service type is added to a service
 * number which has been 8 bit left shifted. So, in order get to the service
 * type, the code has to be masked with only low 8 bits having "1".
 *
 * @param svr_code a code given from a client which contains a service number
 * and service type by bit operation.
 *
 * @return a service type
 */
static unsigned int get_service_type(const unsigned int svr_code)
{
	return svr_code & ((1 << OFFSET_SERVER_TYPE)-1);
}
