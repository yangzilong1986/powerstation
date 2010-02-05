CICS	= /usr/lpp/cics
CICSPAR	= -edlC
CICSLIB	= -bI:$(CICS)/lib/cicsprC.exp -e main -L$(CICS)/lib -L/usr/lib/dce -L/usr/lib/threads -ldcelibc_r -ldcepthreads -lpthreads_compat -lc_r -lc -lcicsecico
CICSINCL	= -I$(CICS)/include -I/usr/include/dce

libcicsclisync.so:cicsclisync.o
	cc -qmkshrobj -o libcicsclisync.so cicsclisync.o ${CICSINCL} ${CICSLIB}
	rm -rf cicsclisync.o
cicsclisync.o:cicsclisync.c
	cc -c cicsclisync.c -I./ -I${JAVA_HOME}/include ${CICSINCL} ${CICSLIB}