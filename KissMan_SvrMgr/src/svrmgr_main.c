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




#define OFFSET_SERVER_TYPE				8
#define SVR_SAM_STATION					0x1
#define SVR_PBS_BATCH_SYSTEM			0x2

/* for cloudgate test purpose */
#define SVR_CONDOR_BATCH_SYSTEM			(0x10)



#define SVC_SAM_LOGFILE					0x1
#define SVC_SAM_CPUINFO					0x2
#define SVC_SAM_MEMINFO					0x3
#define SVC_SAM_PROCESSINFO				0x4
#define SVC_SAM_CACHE_STATUS			0x5
#define SVC_SAM_ERROR					0x7


#define SVC_CONDOR_CONDOR_STATUS		0x1
#define SVC_CONDOR_CONDOR_Q				0x2

#define BUF_LENGTH						1024

unsigned int server_num = SVR_CONDOR_BATCH_SYSTEM;
unsigned int service_num = 4;
void itoa(int n, char s[]);
void reverse(char s[]);

int main()
{
	int sockfd;
	int len;
	struct sockaddr_in address;
	int result;
	//unsigned int server_num = 1;

	sockfd = socket(AF_INET, SOCK_STREAM, 0);
	address.sin_family = AF_INET;
	address.sin_addr.s_addr = inet_addr("127.0.0.1");
//	address.sin_addr.s_addr = inet_addr("131.225.154.205");
	address.sin_port = htons(9734);
	len = sizeof(address);


	result = connect(sockfd, (struct sockaddr *)&address, len);

	if (result == -1) {
		perror("oops: client");
		exit(1);
	}

	server_num <<= OFFSET_SERVER_TYPE;
	server_num |= service_num;

	printf("server_num: = %x, %d, %d\n", server_num, server_num, SVR_CONDOR_BATCH_SYSTEM);

	//server_num = htonl(server_num);
	//write(sockfd, &server_num, sizeof(unsigned int));

	char buf[4];
	itoa(server_num, buf);
	//server_num = itoa(server_num);
	write(sockfd, buf, sizeof(buf));


	char buffer[1024];


	int nbytes =  -1;
	printf("sockfd == %d\n", sockfd);

	do {

		bzero(buffer,1024);

		//nbytes = read(sockfd, buffer, sizeof(buffer)-1);
		nbytes = recv(sockfd, buffer, BUF_LENGTH, 0);

		if ( nbytes > 0 ) {
			// printf("nbytes[%d]: strlen[%d]: %s", nbytes, (int)strlen(buffer), buffer);
			printf("%s", buffer);
		}
        else if ( nbytes == 0 )
            printf("Connection closed\n");
        else
            printf("recv failed: \n");

		//printf("%s", buffer);
	} while (nbytes > 0);

	printf("sockfd == %d\n", sockfd);

	close(sockfd);
	return 0;
}


/* itoa:  convert n to characters in s */
void itoa(int n, char s[])
{
    int i, sign;

    if ((sign = n) < 0)  /* record sign */
        n = -n;          /* make n positive */
    i = 0;
    do {       /* generate digits in reverse order */
        s[i++] = n % 10 + '0';   /* get next digit */
    } while ((n /= 10) > 0);     /* delete it */
    if (sign < 0)
        s[i++] = '-';
    s[i] = '\0';
    reverse(s);
}


/* reverse:  reverse string s in place */
void reverse(char s[])
{
    int i, j;
    char c;

    for (i = 0, j = strlen(s)-1; i<j; i++, j--) {
        c = s[i];
        s[i] = s[j];
        s[j] = c;
    }
}

