/*
 * sam_mon.c
 *
 *  Created on: Mar 16, 2011
 *      Author: rsyoung
 */



#include <./include/kissman_svr.h>

const char sam_cmd[][CMD_LINE_LEN] = {
		"/bin/ls /home/rsyoung/wallpaper/",
		"/bin/ls /home/rsyoung/wallpaper/",
		"/bin/ls /home/rsyoung/wallpaper/",
		"/bin/ls /home/rsyoung/wallpaper/",
		"/bin/ls /home/rsyoung/wallpaper/",
		"/bin/cat /proc/cpuinfo",
		"/bin/ls /etc/",
};


int sam_service(const int fd, const unsigned int svc_type)
{

	FILE *fp;
	char buffer[BUF_SIZE];
	int nbytes = -1;
	char cmdline[CMD_LINE_LEN];

	bzero(cmdline, CMD_LINE_LEN);

	strcpy(cmdline, sam_cmd[svc_type]);

	printf("fd: [%d], cmdline[%d]: %s\n", fd, svc_type-1, sam_cmd[svc_type-1]);
	printf("fd: [%d], cmdline[%d]: %s\n", fd, svc_type, cmdline);
	printf("fd: [%d], cmdline[%d]: %s\n", fd, svc_type+1, sam_cmd[svc_type+1]);

	fp = popen(cmdline, "r");
	if (fp == NULL) {
		printf("Failed to run command\n" );
		return -1;
	}

	bzero(buffer, BUF_SIZE);

	while (fgets(buffer, BUF_SIZE-1, fp) != NULL) {
		nbytes = send(fd, buffer, BUF_SIZE-1, 0);
		printf("nbytes[%d]: strlen[%d]: %s", nbytes, (int)strlen(buffer), buffer);
		if (nbytes < 0)
			printf("---------- Error -----------\n");
	}
	pclose(fp);


	return 0;
}


