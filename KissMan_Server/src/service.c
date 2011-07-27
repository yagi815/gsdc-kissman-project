/*
 * service.c
 *
 *  Created on: Jul 18, 2011
 *      Author: rsyoung
 */

#include "service.h"


const char sam_cmd[][CMD_LINE_LEN] = {
		"dummy command", /* never called */
		"/bin/ls /home/rsyoung/wallpaper/",
		"/bin/ls /home/rsyoung/wallpaper/",
		"/bin/ls /home/rsyoung/wallpaper/",
		"/bin/ls /home/rsyoung/wallpaper/",
		"/bin/cat /proc/cpuinfo",
		"/bin/ls /etc/",
};


/*
const char condor_cmd[][CMD_LINE_LEN] = {
		"dummy command",
		"condor_status",
		"condor_q",
};


const char opennebula_cmd[][CMD_LINE_LEN] = {
		"dummy command",
		"onevm list",
};
*/


int get_svc_cmdline(const struct service_struct svc_struct, char* cmdline);

/**
 * execute a service based on server number and service type. It first gets
 * a command for service, create a pipe for the command, redirect the output
 * of execution to sending file descriptor fd.
 *
 * @param fd file descriptor to send data
 * @param svc_struct service struct which cotains server number and service type
 *
 * @return 1 if successfully execute a service; otherwise -1
 */
int execute_service(const int fd, const struct service_struct svc_struct)
{
	char cmdline[CMD_LINE_LEN];
	bzero(cmdline, CMD_LINE_LEN);

	FILE *fp;
	char buffer[BUF_SIZE];
	int nbytes = -1;

	/* get service command */
	if (get_svc_cmdline(svc_struct, cmdline) < 0)
		return -1;

	fp = popen(cmdline, "r");
	if (fp == NULL) {
		printf("[Error] : failure to run command at %d, %s\n", __LINE__, __FILE__);
		return -1;
	}

	bzero(buffer, BUF_SIZE);

	while (fgets(buffer, BUF_SIZE, fp) != NULL) {
		nbytes = send(fd, buffer, BUF_SIZE, 0);

		// printf("nbytes[%d]: strlen[%d]: %s", nbytes, (int)strlen(buffer), buffer);
		printf("%s", buffer);

		if (nbytes < 0)
			printf("[Error] : bytes are less than 0 at %d, %s\n", __LINE__, __FILE__);
		bzero(buffer, BUF_SIZE);
	}
	pclose(fp);

	return 1;
}



int execute_service_2(const int fd, const struct service_struct svc_struct)
{
	char cmdline[CMD_LINE_LEN];
	bzero(cmdline, CMD_LINE_LEN);

	FILE *fp;
	char buffer[BUF_SIZE];
	int nbytes = -1;

	/* get service command */
	if (get_svc_cmdline(svc_struct, cmdline) < 0)
		return -1;

	fp = popen(cmdline, "r");
	if (fp == NULL) {
		printf("[Error] : failure to run command at %d, %s\n", __LINE__, __FILE__);
		return -1;
	}

	bzero(buffer, BUF_SIZE);

    FILE *fpout;
    size_t count = 0;

    fpout = fopen("output.txt", "w");
    if(fpout == NULL) {
        perror("failed to open sample.txt");
        return -1;
    }

	while (fgets(buffer, BUF_SIZE, fp) != NULL) {
		//nbytes = send(fd, buffer, BUF_SIZE, 0);

		// printf("nbytes[%d]: strlen[%d]: %s", nbytes, (int)strlen(buffer), buffer);
		printf("%s", buffer);
	    count += fwrite(buffer, 1, strlen(buffer), fpout);
		//if (nbytes < 0)
		//	printf("[Error] : bytes are less than 0 at %d, %s\n", __LINE__, __FILE__);
		bzero(buffer, BUF_SIZE);
	}
	pclose(fp);
	fclose(fpout);

    fpout = fopen("output.txt", "r");
    if(fpout == NULL) {
        perror("failed to open sample.txt");
        return -1;
    }


	bzero(buffer, BUF_SIZE);
	while (fgets(buffer, BUF_SIZE, fpout) != NULL) {
		nbytes = send(fd, buffer, BUF_SIZE, 0);

		// printf("nbytes[%d]: strlen[%d]: %s", nbytes, (int)strlen(buffer), buffer);
		printf("%s", buffer);
		if (nbytes < 0)
			printf("[Error] : bytes are less than 0 at %d, %s\n", __LINE__, __FILE__);
		bzero(buffer, BUF_SIZE);
	}

	fclose(fpout);


	return 1;
}

/**
 * get a command line for a server number and service type
 *
 * @param svc_struct service struct holding server number and service type
 * @param cmdline command line to be returned
 *
 * @return 1 if successfully get service command; otherwise -1
 */
int get_svc_cmdline(const struct service_struct svc_struct, char* cmdline)
{
	const unsigned int svr_num = svc_struct.server_num;
	const unsigned int svc_type = svc_struct.service_type;

	switch (svr_num) {
	case SVR_SAM_STATION:
		if (strcpy(cmdline, sam_cmd[svc_type]) != NULL)
			return 1;

	case SVR_CONDOR_BATCH_SYSTEM:
	case SVR_ONE_CLOUD_SYSTEM:
		if (strcpy(cmdline, svc_struct.cmdline) != NULL)
			return 1;

	default:
		break;
	}

	printf("[Error] : error while copying service command at %d, %s\n",
			__LINE__, __FILE__);

	return -1;

}
