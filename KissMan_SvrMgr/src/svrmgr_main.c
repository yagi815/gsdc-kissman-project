/*
 * server_test.c
 *
 *  Created on: Mar 17, 2011
 *      Author: rsyoung
 */


#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <stdlib.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <sys/ioctl.h>
#include <string.h>


#define OFFSET_SERVER_TYPE		8
#define SVR_SAM_STATION			0x1
#define SVR_BATCH_SYSTEM		0x2

#define SVC_SAM_LOGFILE			0x1
#define SVC_SAM_CPUINFO			0x2
#define SVC_SAM_MEMINFO			0x3
#define SVC_SAM_PROCESSINFO		0x4
#define SVC_SAM_CACHE_STATUS	0x5
#define SVC_SAM_ERROR			0x7


unsigned int server_num = 1;
unsigned int service_num = 1;

int main()
{
	int sockfd;
	int len;
	struct sockaddr_in address;
	int result;
	unsigned int server_num = 1;

	sockfd = socket(AF_INET, SOCK_STREAM, 0);
	address.sin_family = AF_INET;
	address.sin_addr.s_addr = inet_addr("127.0.0.1");
	address.sin_port = htons(9734);
	len = sizeof(address);


	result = connect(sockfd, (struct sockaddr *)&address, len);

	if (result == -1) {
		perror("oops: client");
		exit(1);
	}

	server_num <<= OFFSET_SERVER_TYPE;
	server_num |= SVC_SAM_CACHE_STATUS;

	printf("server_num: = %x\n", server_num);

	write(sockfd, &server_num, sizeof(unsigned int));

	char buffer[1024];


	int nbytes =  -1;
	printf("sockfd == %d\n", sockfd);

	while (1) {

		//MSG_WAITALL
		//nbytes = read(sockfd, buffer, sizeof(buffer)-1);
		nbytes = recv(sockfd, buffer, sizeof(buffer)-1, 0);

		if (nbytes == 0)
			break;
		if (nbytes < 0) {
			printf("Error -------\n");
			exit(1);
		}

		printf("nbytes[%d]: strlen[%d]: %s", nbytes, (int)strlen(buffer), buffer);
		bzero(buffer,1024);
	}

	printf("sockfd == %d\n", sockfd);

	close(sockfd);
	return 0;
}