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

/* current server which kissman_svr is running on */
//static current_server = -1;


#define OFFSET_SERVER_TYPE		8
#define SVR_SAM_STATION			0x1
#define SVR_BATCH_SYSTEM		0x2

/*
#define SVC_SAM_LOGFILE			0x1
#define SVC_SAM_CPUINFO			0x2
#define SVC_SAM_MEMINFO			0x3
#define SVC_SAM_PROCESSINFO		0x4
#define SVC_SAM_CACHE_STATUS	0x5
*/


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


static unsigned int get_server_num(const unsigned int svr_code);
static unsigned int get_service_type(const unsigned int svr_code);
static int server_check(const unsigned int svr_code);


static int  identify_service(const unsigned int svr_code,
							 struct service_struct* svc_struct);


static int server_num = 1;
static int service_type = -1;

int main(int argc, char* argv[])
{
	int server_sockfd, client_sockfd;
	int server_len, client_len;

	struct sockaddr_in server_address;
	struct sockaddr_in client_address;

	int result;
	fd_set readfds, testfds;

	server_sockfd = socket(AF_INET, SOCK_STREAM, 0);

	server_address.sin_family = AF_INET;
	server_address.sin_addr.s_addr = htonl(INADDR_ANY);
	server_address.sin_port = htons(9734);
	server_len = sizeof(server_address);

	bind(server_sockfd, (struct sockaddr *)&server_address, server_len);

	listen(server_sockfd, 5);

	FD_ZERO(&readfds);
	FD_SET(server_sockfd, &readfds);

	while(1)
	{
		char ch;
		int fd;
		int nread;

		testfds = readfds;

		printf("server waiting\n");

		client_len = sizeof(client_address);
		result = select(FD_SETSIZE, &testfds, (fd_set *)0,
										(fd_set *)0, (struct timeval *)0);
		if(result < 1) {
			perror("Error KissMan Server\n");
			exit(1);
		}

		for (fd = 0; fd < FD_SETSIZE; fd++) {
			if(FD_ISSET(fd, &testfds)) {
				if (fd == server_sockfd) {
					client_len = sizeof(client_address);
					client_sockfd = accept(server_sockfd,
							(struct sockadd *)&client_address, &client_len);
					FD_SET(client_sockfd, &readfds);
					printf("adding client on fd %d\n", client_sockfd);
				}
				else {

					ioctl(fd, FIONREAD, &nread);

					if(nread == 0) {
						close(fd);
						FD_CLR(fd, &readfds);
						printf("removing client on fd %d\n", fd);
					}
					else {
						unsigned int service_code;
						struct service_struct svc_struct;

						read(fd, &service_code, sizeof(unsigned int));

						if (identify_service(service_code, &svc_struct))
						{
							sleep(3);
							write(fd, &(svc_struct.server_num), sizeof(unsigned int));
						}
						else
						{
							printf("Error on fd %d\n", fd);

						}
					}
				}
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
		return 0;
	}

	svr_type = get_service_type(svr_code);

	switch (server_num) {

	case SVR_SAM_STATION:
		if (svr_type == 0 || svr_type >= SVC_SAM_MAX) {
			printf("Service number is wrong: server[%u], service [%u]\n",
														svr_num, svr_type);
			return 0;
		}
		break;

	case SVR_BATCH_SYSTEM:
		break;

	default:
		break;
	}

	svc_struct->server_num = svr_num;
	svc_struct->service_type = svr_type;

	printf("Server [%u], Service [%u], max[%d]\n", svr_num, svr_type, SVC_SAM_MAX);

	return 1;
}



static unsigned int get_server_num(const unsigned int svr_code)
{
	return svr_code >> OFFSET_SERVER_TYPE;
}

static unsigned int get_service_type(const unsigned int svr_code)
{
	return svr_code & ((1 << OFFSET_SERVER_TYPE)-1);
}
