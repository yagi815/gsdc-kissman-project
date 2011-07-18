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

static int is_new_connection(const int fd, const int server_sockfd, fd_set *readfds);
static int get_service_code(const int fd, fd_set* readfds, unsigned int* service_code);

static int  identify_service(const unsigned int svr_code,
							 struct service_struct* svc_struct);

static int get_valid_fd(const fd_set fds);
extern int execute_service(const int fd, const struct service_struct svc_struct);

static int server_num;


int main(int argc, char* argv[])
{

	int server_sockfd;
	socklen_t server_len;;

	struct sockaddr_in server_address;

	int ret;
	fd_set testfds, readfds;

	/* arguments should be 2 for correct execution */
	if ( argc != 2 )
    {
        /* ./kissman_svr <server number>, eg. ./kissman_svr 1 */
        printf( "usage: %s <server number>\n", argv[0] );
        exit(-1);
    }

	/* convert a given argument to server number */
	server_num = atoi(argv[1]);

	/* create a socket */
	server_sockfd = socket(AF_INET, SOCK_STREAM, 0);
	printf("[MSG] : server_sockfd = %d\n", server_sockfd);

	server_address.sin_family = AF_INET;
	server_address.sin_addr.s_addr = htonl(INADDR_ANY);
	server_address.sin_port = htons(PORT_NUM);
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
	FD_ZERO(&testfds);
	FD_SET(server_sockfd, &readfds);

	while(1)
	{
		int fd = -1;

		testfds = readfds;

		/* checks to see if any sockets are ready for reading */
		ret = select(FD_SETSIZE, &testfds, (fd_set *)0,
										(fd_set *)0, (struct timeval *)0);
		if(ret < 1) {
			printf("[Error] : failure to select fd at %d, %s\n", __LINE__, __FILE__);
			exit(-1);
		}

		/* get a file descriptor to read */
		if ((fd = get_valid_fd(testfds)) == -1)
			continue;

		/**
		 * check if it is a new connection. If so, it create a client socket
		 * fd, otherwise, it has to receive data from the fd
		 */
		if (1 == is_new_connection(fd, server_sockfd, &readfds))
			continue;


		unsigned int service_code;
		struct service_struct svc_struct;

		/* try to get service code. If fail, then continue */
		if (-1 == get_service_code(fd, &readfds, &service_code))
			continue;

		/**
		 * if there is a message from a client, then
		 * identify service.
		 */
		if (1 == identify_service(service_code, &svc_struct))
		{
			/* execute service */
			execute_service(fd, svc_struct);
		}
		else
		{
			printf("[Error] : cannot identify service at %d, %s\n", __LINE__, __FILE__);
		}

		close(fd);
		FD_CLR(fd, &readfds);

	}
	return 0;
}


/**
 * find a valid file descriptor in file descriptor set (fds)
 *
 * @param fds file descriptor sets
 * @return a valid file descriptor if there is; otherwise -1
 */
static int get_valid_fd(const fd_set fds)
{
	int fd;

	/**
	 * check if fd is a member of fds.
	 * If so, returns fd, otherwise -1
	 */
	for (fd = 0; fd < FD_SETSIZE; fd++)
		if(FD_ISSET(fd, &fds)) break;

	if (fd > FD_SETSIZE) return -1;

	printf("[MSG] : fd = %d\n", fd);
	return fd;
}


/**
 * evaluate if the connection is new. If fd == server_sockfd, need to create
 * a new connection; otherwise there is data to be received.
 *
 * @param fd file descriptor which is ready to receive data
 * @param server_sockfd server socket file descriptor
 * @param readfds read file descriptor set
 *
 * @return 1 if fd == server_sockfd; otherwise -1
 */
static int is_new_connection(const int fd, const int server_sockfd, fd_set *readfds)
{
	int client_sockfd;
	socklen_t client_len;
	struct sockaddr_in client_address;

	/* it is a new connection */
	if (fd == server_sockfd) {
		client_len = sizeof(client_address);
		client_sockfd = accept(server_sockfd,
				(struct sockaddr *)&client_address, &client_len);
		FD_SET(client_sockfd, readfds);
		printf("[MSG] : adding client on fd %d\n", client_sockfd);
		return 1;
	}

	return -1;
}


/**
 * receive a service code from fd.
 *
 * @param fd file descriptor to receive service code
 * @param readfds file descriptor set for reading
 * @param service_code a variable to contain service code
 *
 * @return 1 if successfully received; otherwise -1
 */
static int get_service_code(const int fd, fd_set* readfds, unsigned int* service_code)
{

	int ret;

	if ((ret = recv(fd, service_code, sizeof(unsigned int), 0)) <= 0) {
		if(ret == 0)
			/* connection closed */
			printf("[MSG] : socket %d hung up\n", fd);
		else
			printf("[Error] : recv() error lol at %d, %s.\n", __LINE__, __FILE__);

		FD_CLR(fd, readfds);
		close(fd);

		return -1;
	}

	return 1;
}

/**
 * identify a service type from a given code which combined server number and
 * a service type in a single unsigned integer.
 *
 * @param svr_code an unsigned integer which contains server number and
 * a service type in a single value
 *
 * @param svc_struct a structure to be returned, containing resolved server
 * number and service type
 *
 * @return 1 if success; otherwise -1
 */
static int identify_service(const unsigned int svr_code,
							 struct service_struct* svc_struct)
{
	unsigned int svr_num, svr_type;

	printf("service code = %d\n", svr_code);
	svr_num = get_server_num(svr_code);

	if (svr_num != server_num) {
		printf("Server number is not matched: server[%u] != [%u]\n",
													server_num, svr_num);
		return -1;
	}

	svr_type = get_service_type(svr_code);

	switch (server_num) {

	case SVR_SAM_STATION:
		if (svr_type == 0 || svr_type >= SVC_SAM_MAX) {
			printf("[Error] : service number is wrong: server[%u], service [%u]"
					" at %d, %s\n", svr_num, svr_type, __LINE__, __FILE__);
			return -1;
		}
		break;

	case SVR_CONDOR_BATCH_SYSTEM:
		if (svr_type == 0 || svr_type >= SVC_CONDOR_MAX) {
			printf("[Error] : service number is wrong: server[%u], service [%u]"
					" at %d, %s\n", svr_num, svr_type, __LINE__, __FILE__);
			return -1;
		}
		break;

	default:
		break;
	}

	svc_struct->server_num = svr_num;
	svc_struct->service_type = svr_type;


	return 1;
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

