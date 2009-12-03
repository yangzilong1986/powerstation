#include <stdio.h>
#include <cics_eci.h>
#include <unistd.h>
#include <time.h>
#include <string.h>

#include "cicsclisync.h"

JNIEXPORT jint JNICALL Java_com_hisun_eci_CicsCliSyncJni_clientSyncOutJni
  (JNIEnv * env, jobject obj, jstring systemName, jstring uesrName, jstring passwrod,jstring programId,jint timeOut, jbyteArray sendData, jbyteArray recvBuffer)
{
	CICS_EciSystem_t servers[20];             /*Servers from CICS_EciListSystems */
	cics_ushort_t    serverCount    = 20;     /*Length counter for above list    */
	cics_char_t      progid[8 + 1] ;          /*Program to call                  */
	ECI_PARMS        EciParms;                /*The ECI Parameter block          */
	char             CommArea[ECI_SYNC_SIZE+1]; /*Commarea for use in ECI calls    */
	char             tempstr[255];            /*Used to validate users input     */
	char             ssccode[6+1];
	int              datalen;
	int rc;
	char *p;
	s_SOCK_COMM *outputdata;
	char						strLen[9+1];
	
	int iRet = 0;
	
	/* get args */
	const char * psysNm = (*env)->GetStringUTFChars(env,systemName,0);
	const char * puserNm = (*env)->GetStringUTFChars(env,uesrName,0);
	const char * ppwd = (*env)->GetStringUTFChars(env,passwrod,0);
	const char * pprogId = (*env)->GetStringUTFChars(env,programId,0);
	const jbyte* psendData = (const jbyte*)(*env)->GetByteArrayElements(env,sendData, JNI_FALSE);
	const jbyte* precvBuffer = (const jbyte*)(*env)->GetByteArrayElements(env,recvBuffer, JNI_FALSE);
	
	int iSendLen = (*env)->GetArrayLength(env,sendData);
	int iSize = (*env)->GetArrayLength(env,recvBuffer);
    
  trc("get args, systemName[%s],userName[%s],passwd[%s],progId[%s],sendLen[%d],timeOut[%d]\n",psysNm,puserNm,ppwd,pprogId,iSendLen,timeOut);
  /* Set up the ECI Parameter block */
	memset(progid,'\0',sizeof(progid));
	memset(CommArea, '\0', ECI_SYNC_SIZE);
	memset(&EciParms, 0, sizeof(ECI_PARMS));
	memset(tempstr,'\0',sizeof(tempstr));
	memset(ssccode,'\0',sizeof(ssccode));
	memset(strLen,'\0',sizeof(strLen));
	
	outputdata = (s_SOCK_COMM *)CommArea;
	p = outputdata->buffer;
	memcpy(p,psendData,iSendLen);
	sprintf( outputdata->slen, "%08ld", iSendLen );

	/*memcpy(CommArea, psendData,iSendLen);*/
	if (pprogId == NULL ||  strlen(pprogId) <= 0)
	{
		trc("program id invaild, [%s]\n",pprogId);
		iRet = -1;
		goto end;
	}
	
	strcpy(progid,pprogId);

	EciParms.eci_version= ECI_VERSION_1A;
	EciParms.eci_call_type                         = ECI_SYNC;
	memcpy(&EciParms.eci_program_name, progid,       8);
	memcpy(&EciParms.eci_userid,       puserNm,   8);
	memcpy(&EciParms.eci_password,     ppwd,   8);
	memcpy(&EciParms.eci_system_name,  psysNm, 8);
	EciParms.eci_commarea                          = CommArea;
	EciParms.eci_commarea_length                   = ECI_SYNC_SIZE;
	EciParms.eci_extend_mode                       = ECI_NO_EXTEND;
	EciParms.eci_luw_token                         = ECI_LUW_NEW;
	EciParms.eci_timeout                           = timeOut;
	
	trc("Ready send msg, SendLen[%s],SendData[%s],ProgId[%s]\n",outputdata->slen,outputdata->buffer,progid);
	/* Call the chosen server with the given parameters */
	rc = CICS_ExternalCall (&EciParms);
	
	if (rc == ECI_NO_ERROR) 
	{
			p=outputdata->buffer;
			memcpy(strLen,outputdata->slen,9);
			datalen = atoi(strLen);
			trc("Return: retcod[%.4s],slen[%.9s]\n",outputdata->retcod,outputdata->slen);
			memcpy((void *)precvBuffer,p,datalen);
			trc("ProgramID[%s],receive Data Success: [%d][%s]\n", progid, datalen, precvBuffer);
	
			/*printf("programID[%s],receive Data Success:[%s]\n", progid,p);*/
			/*strcpy(ssccode, NET_OK);*/
			
	    /*memcpy(precvBuffer,CommArea,strlen(CommArea));*/

			iRet = datalen;
	} 
	else
	{
		/*strcpy(ssccode,NET_ERROR_RP);*/
		switch (rc) 
		{
			case ECI_ERR_INVALID_DATA_LENGTH: p = "ECI_ERR_INVALID_DATA_LENGTH";break;
			case ECI_ERR_INVALID_EXTEND_MODE: p = "ECI_ERR_INVALID_EXTEND_MODE";break;
			case ECI_ERR_NO_CICS:             p = "ECI_ERR_NO_CICS            ";break;
			case ECI_ERR_CICS_DIED:           p = "ECI_ERR_CICS_DIED          ";break;
			case ECI_ERR_REQUEST_TIMEOUT:     p = "ECI_ERR_REQUEST_TIMEOUT    ";break;
			case ECI_ERR_RESPONSE_TIMEOUT:    p = "ECI_ERR_RESPONSE_TIMEOUT   ";break;
			case ECI_ERR_TRANSACTION_ABEND:   p = "ECI_ERR_TRANSACTION_ABEND  ";break;
			case ECI_ERR_LUW_TOKEN:           p = "ECI_ERR_EXEC_LUW_TOKEN     ";break;
			case ECI_ERR_SYSTEM_ERROR:        p = "ECI_ERR_SYSTEM_ERROR       ";break;
			case ECI_ERR_NULL_WIN_HANDLE:     p = "ECI_ERR_NULL_WIN_HANDLE    ";break;
			case ECI_ERR_NULL_MESSAGE_ID:     p = "ECI_ERR_NULL_MESSAGE_ID    ";break;
			case ECI_ERR_THREAD_CREATE_ERROR: p = "ECI_ERR_THREAD_CREATE_ERROR";break;
			case ECI_ERR_INVALID_CALL_TYPE:   p = "ECI_ERR_INVALID_CALL_TYPE  ";break;
			case ECI_ERR_ALREADY_ACTIVE:      p = "ECI_ERR_ALREADY_ACTIVE     ";break;
			case ECI_ERR_RESOURCE_SHORTAGE:   p = "ECI_ERR_RESOURCE_SHORTAGE  ";break;
			case ECI_ERR_NO_SESSIONS:         p = "ECI_ERR_NO_SESSIONS        ";break;
			case ECI_ERR_NULL_SEM_HANDLE:     p = "ECI_ERR_NULL_SEM_HANDLE    ";break;
			case ECI_ERR_INVALID_DATA_AREA:   p = "ECI_ERR_INVALID_DATA_AREA  ";break;
			case ECI_ERR_INVALID_VERSION:     p = "ECI_ERR_INVALID_VERSION    ";break;
			case ECI_ERR_UNKNOWN_SERVER:      p = "ECI_ERR_UNKNOWN_SERVER     ";break;
			case ECI_ERR_CALL_FROM_CALLBACK:  p = "ECI_ERR_CALL_FROM_CALLBACK ";break;
			case ECI_ERR_MORE_SYSTEMS:        p = "ECI_ERR_MORE_SYSTEMS       ";break;
			case ECI_ERR_NO_SYSTEMS:          p = "ECI_ERR_NO_SYSTEMS         ";break;
			case ECI_ERR_SECURITY_ERROR:      p = "ECI_ERR_SECURITY_ERROR     ";break;
			case ECI_ERR_MAX_SYSTEMS:         p = "ECI_ERR_MAX_SYSTEMS        ";break;
			case ECI_ERR_MAX_SESSIONS:        p = "ECI_ERR_MAX_SESSIONS       ";break;
			case ECI_ERR_ROLLEDBACK:          p = "ECI_ERR_ROLLEDBACK         ";break;
			default:
				p = "!!!Unknown!!!";
				trc("Unknown Return Code : %d\n", rc);
				break;
    	}
		datalen=0;
		memcpy((void*)precvBuffer, p, strlen(p));
		trc("Failure: [%s]\n",p);
		iRet = -2;
	}
	
	
end:
	/* release args */
	(*env)->ReleaseStringUTFChars(env,systemName,(const char*)psysNm);
	(*env)->ReleaseStringUTFChars(env,uesrName,(const char*)puserNm);
	(*env)->ReleaseStringUTFChars(env,passwrod,(const char*)ppwd);
	(*env)->ReleaseStringUTFChars(env,programId,(const char*)pprogId);
	(*env)->ReleaseByteArrayElements(env,sendData, (signed char *)psendData,0);
	(*env)->ReleaseByteArrayElements(env,recvBuffer, (signed char *)precvBuffer,0);

	return iRet;
}

/******************************************************************
    Function      : trc()
    Description   : 日志
    Input         : 参数一： 格式
    Return        : 0，成功；-1，失败
******************************************************************/
void trc(char* Afmt, ...)
{
	va_list Fap;
	char    Ffilename[256];
	FILE *  Ffp;
	int     Ffd;
	char	Fdatetime[32];
	char  Fday[4];

	memset(Fdatetime, 0x00, sizeof(Fdatetime));
	memset(Fday, 0x00, sizeof(Fday));
	getdatetime(Fdatetime);
	memcpy(Fday,Fdatetime+6,2);

	snprintf(Ffilename, sizeof(Ffilename), "%s/trc/%s/cicsclisync.trc", getenv("HOME"),Fday);
	if ((Ffp = fopen(Ffilename, "a+")) == NULL)
		return;
	Ffd = fileno(Ffp);
	lockf(Ffd, F_LOCK, 0l);
	va_start(Fap, Afmt);
	fprintf(Ffp, "[%4.4s-%2.2s-%2.2s %2.2s:%2.2s:%2.2s]", Fdatetime, Fdatetime+4, Fdatetime+6, 
		Fdatetime+8, Fdatetime+10, Fdatetime+12);
	if (vfprintf(Ffp, Afmt, Fap) < 0)
	{
		lockf(Ffd, F_ULOCK, 0l);
		fclose(Ffp);
		return;
	}
	va_end(Fap);
	lockf(Ffd, F_ULOCK, 0l);
	fclose(Ffp); 

	return;
}

/******************************************************************
    Function      : getdatetime()
    Description   : 获得当前时间，从年到秒
    Input         : 参数一： 日期时间字符串用于输出
    Return        : 0，成功；-1，失败
******************************************************************/
char *getdatetime( char *datetime)
{
    time_t  t;
    struct  tm  *p;

    time( &t );
    p = localtime( &t );

    if (p->tm_year >= 70)
        p->tm_year = p->tm_year + 1900;
    else if (p->tm_year < 38)
        p->tm_year = p->tm_year + 2000;

    sprintf(datetime, "%04d%02d%02d%02d%02d%02d",
                    p->tm_year, p->tm_mon+1,
                    p->tm_mday, p->tm_hour, p->tm_min, p->tm_sec );

    return( datetime );
}
