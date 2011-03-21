/*
 * main.c
 *
 *  Created on: Mar 16, 2011
 *      Author: rsyoung
 */

#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <unistd.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/time.h>
#include <sys/ioctl.h>
#include <string.h>
#include <stdlib.h>

#define MAGIC_CODE_END			0xff

#define OFFSET_SERVER_TYPE		8
#define SVR_SAM_STATION			0x1
#define SVR_BATCH_SYSTEM		0x2

#define BUF_SIZE				1024
#define CMD_LINE_LEN			1024

struct service_struct {
	unsigned int server_num;
	unsigned int service_type;
};

enum svc_sam {
	SVC_SAM_LOGFILE = 1,
	SVC_SAM__LOGFILE,
	SVC_SAM_CPUINFO,
	SVC_SAM_MEMINFO,
	SVC_SAM_PROCESSINFO,
	SVC_SAM_CACHE_STATUS,
	SVC_SAM_MAX
};

const char sam_cmd[][CMD_LINE_LEN] = {
		"/bin/ls /etc/",
		"/bin/ls /etc/",
		"/bin/ls /etc/",
		"/bin/ls /etc/",
		"/bin/ls /etc/",
};

static unsigned int get_server_num(const unsigned int svr_code);
static unsigned int get_service_type(const unsigned int svr_code);


static int  identify_service(const unsigned int svr_code,
							 struct service_struct* svc_struct);

static int execute_service(const int fd, const struct service_struct svc_struct);
static int sam_service(const int fd, const unsigned int svc_type);

static int server_num = 1;

int main(int argc, char* argv[])
{
	int server_sockfd, client_sockfd;
	socklen_t server_len, client_len;

	struct sockaddr_in server_address;
	struct sockaddr_in client_address;

	int ret;
	fd_set readfds, testfds;

	server_sockfd = socket(AF_INET, SOCK_STREAM, 0);

	server_address.sin_family = AF_INET;
	server_address.sin_addr.s_addr = htonl(INADDR_ANY);
	server_address.sin_port = htons(9734);
	server_len = sizeof(server_address);

	ret = bind(server_sockfd, (struct sockaddr *)&server_address, server_len);
	if (ret != 0) {
		perror("Error: fail to bind\n");
		exit(1);
	}

	ret = listen(server_sockfd, 5);
	if (ret != 0) {
		perror("Error: fail to listen\n");
		exit(1);
	}


	FD_ZERO(&readfds);
	FD_SET(server_sockfd, &readfds);

	while(1)
	{
		int fd;
		int nread;

		testfds = readfds;

		printf("server waiting\n");

		client_len = sizeof(client_address);
		ret = select(FD_SETSIZE, &testfds, (fd_set *)0,
										(fd_set *)0, (struct timeval *)0);
		if(ret < 1) {
			perror("Error: KissMan Server\n");
			exit(1);
		}

		for (fd = 0; fd < FD_SETSIZE; fd++) {
			if(!FD_ISSET(fd, &testfds))
				continue;

			if (fd == server_sockfd) {
				client_len = sizeof(client_address);
				client_sockfd = accept(server_sockfd,
						(struct sockaddr *)&client_address, &client_len);
				FD_SET(client_sockfd, &readfds);
				printf("adding client on fd %d\n", client_sockfd);
				continue;
			}


			ioctl(fd, FIONREAD, &nread);

			if(nread == 0) {
				close(fd);
				FD_CLR(fd, &readfds);
				printf("ioctl: removing client on fd %d\n", fd);
				continue;
			}

			unsigned int service_code;
			struct service_struct svc_struct;

			ret = read(fd, &service_code, sizeof(unsigned int));

			if (!identify_service(service_code, &svc_struct))
			{
				if (!execute_service(fd, svc_struct))
				{
					printf("execute: removing client on fd %d\n", fd);
				}
				close(fd);
				FD_CLR(fd, &readfds);

			}
			else
			{
				printf("Error on fd %d\n", fd);
				close(fd);
				FD_CLR(fd, &readfds);
			}
		}
	}
	return 0;
}

static int identify_service(const unsigned int svr_code,
							 struct service_struct* svc_struct)
{
	unsigned int svr_num, svr_type;

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
			printf("Service number is wrong: server[%u], service [%u]\n",
														svr_num, svr_type);
			return -1;
		}
		break;

	case SVR_BATCH_SYSTEM:
		break;

	default:
		break;
	}

	svc_struct->server_num = svr_num;
	svc_struct->service_type = svr_type;

	//printf("Server [%u], Service [%u], max[%d]\n", svr_num, svr_type, SVC_SAM_MAX);

	return 0;
}



static unsigned int get_server_num(const unsigned int svr_code)
{
	return svr_code >> OFFSET_SERVER_TYPE;
}

static unsigned int get_service_type(const unsigned int svr_code)
{
	return svr_code & ((1 << OFFSET_SERVER_TYPE)-1);
}


static int execute_service(const int fd, const struct service_struct svc_struct)
{
	const unsigned int svr_num = svc_struct.server_num;
	const unsigned int svc_type = svc_struct.server_num;


	switch (svr_num) {

	case SVR_SAM_STATION:
		return sam_service(fd, svc_type);

	case SVR_BATCH_SYSTEM:
		break;

	default:
		break;
	}

	return 0;

}


static int sam_service(const int fd, const unsigned int svc_type)
{

	FILE *fp;
	char buffer[BUF_SIZE];
	int nbytes = -1;
	char cmdline[CMD_LINE_LEN];

	bzero(cmdline, CMD_LINE_LEN);

	strcpy(cmdline, sam_cmd[svc_type]);

	// printf("cmdline: %s\n", cmdline);
	fp = popen(cmdline, "r");
	if (fp == NULL) {
		printf("Failed to run command\n" );
		return -1;
	}

	bzero(buffer, BUF_SIZE);

	while (fgets(buffer, BUF_SIZE-1, fp) != NULL) {
		nbytes = write(fd, &buffer, BUF_SIZE-1);
		//printf("nbytes[%d]: strlen[%d]: %s\n", nbytes, (int)strlen(buffer), buffer);
	}
	pclose(fp);

	return 0;
}

