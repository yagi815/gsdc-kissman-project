/*
 * service.h
 *
 *  Created on: Jul 18, 2011
 *      Author: rsyoung
 */

#ifndef SERVICE_H_
#define SERVICE_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>

#include <./include/kissman_svr.h>


#define MAX_QUEUE_LEN			10
#define PORT_NUM				9734


enum svc_sam {
	SVC_SAM_LOGFILE = 1,
	SVC_SAM__LOGFILE,
	SVC_SAM_CPUINFO,
	SVC_SAM_MEMINFO,
	SVC_SAM_PROCESSINFO,
	SVC_SAM_CACHE_STATUS,
	SVC_SAM_MAX
};



enum svc_condor {
	SVC_CONFOR_CONDOR_STATUS = 1,
	SVC_CONFOR_CONDOR_Q,
	SVC_CONDOR_PROCESSINFO,
	SVC_CONDOR_LS,
	SVC_CONDOR_MAX
};




struct service_struct {
	unsigned int server_num;
	unsigned int service_type;
};




#endif /* SERVICE_H_ */
