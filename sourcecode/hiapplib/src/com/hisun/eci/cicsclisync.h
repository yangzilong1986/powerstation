#ifndef _CICSCLISYNC_
#define _CICSCLISYNC_

#include "com_hisun_eci_CicsCliSyncJni.h"

#define ECI_SYNC_SIZE 10240
#define TC_SUCCESS         "0000"  /*³É¹¦*/

void trc(char* Afmt, ...);
char *getdatetime( char *datetime);

typedef struct
{
	char retcod[4];
  char slen[9];
  char buffer[1];
} s_SOCK_COMM;

#endif /* _CICSCLISYNC_ */