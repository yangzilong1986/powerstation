/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2010-5-3 16:15:54                            */
/*==============================================================*/


drop table R_REALTIME_TASK cascade constraints;

/*==============================================================*/
/* Table: R_REALTIME_TASK                                       */
/*==============================================================*/
create table R_REALTIME_TASK  (
   TASK_ID              NUMBER                          not null,
   SEQUENCE_CODE        NUMBER                          not null,
   MSN                  VARCHAR2(512),
   POST_TIME            DATE                           default SYSDATE,
   TASK_STATUS          VARCHAR2(5)                    default '0',
   constraint PK_R_REALTIME_TASK primary key (TASK_ID)
)
tablespace TABS_INTERACTION;

comment on column R_REALTIME_TASK.TASK_STATUS is
'����״̬��0��δ����1�������У�2��ִ�гɹ���3��ִ��ʧ��';

