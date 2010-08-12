/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2010-7-29 20:55:32                           */
/*==============================================================*/


alter table R_REALTIME_TASK_RECV
   drop constraint FK_R_REALTI_R_TASK_RE_R_REALTI;

alter table R_TASK_TACTIC
   drop constraint FK_R_TASK_T_R_TASK_TA_R_TASK;

drop table R_COMMANDITEM cascade constraints;

drop table R_DATAITEM cascade constraints;

drop table R_REALTIME_TASK cascade constraints;

drop table R_REALTIME_TASK_RECV cascade constraints;

drop table R_TASK cascade constraints;

drop table R_TASK_DATAITEM cascade constraints;

drop table R_TASK_TACTIC cascade constraints;

drop table R_TERM_PARA cascade constraints;

drop table R_TERM_PARA_TP cascade constraints;

drop table R_TERM_PARA_TP_LIST cascade constraints;

drop table R_TERM_TASK cascade constraints;

drop sequence SEQ_REALTIME_TASK;

drop sequence SEQ_TASK;

create sequence SEQ_REALTIME_TASK
increment by 1
start with 1
 maxvalue 9999999999
 minvalue 1
 cache 20;

create sequence SEQ_TASK
increment by 1
start with 1
 maxvalue 999999999999
 minvalue 1
 cache 20;

/*==============================================================*/
/* Table: R_COMMANDITEM                                         */
/*==============================================================*/
create table R_COMMANDITEM  (
   PROTOCOL_NO          VARCHAR2(5)                     not null,
   COMMANDITEM_CODE     VARCHAR2(20)                    not null,
   COMMANDITEM_NAME     VARCHAR2(255),
   COMMANDITEM_DESC     VARCHAR2(2000),
   GP_CHAR              VARCHAR2(5),
   AUX_INFO             VARCHAR2(20),
   WRITE_FLAG           CHAR(1),
   READ_FLAG            CHAR(1),
   OBJECT_FLAG_PG       CHAR(1),
   OBJECT_FLAG_TG       CHAR(1),
   OBJECT_FLAG_CUST     CHAR(1),
   OBJECT_FLAG_SUBS     CHAR(1),
   constraint PK_R_COMMANDITEM primary key (PROTOCOL_NO, COMMANDITEM_CODE)
)
tablespace TABS_ARCHIVE;

comment on table R_COMMANDITEM is
'�Բ�ͬ��Լ������ͳһ�������';

comment on column R_COMMANDITEM.PROTOCOL_NO is
'������PROTOCOL_TERM';

comment on column R_COMMANDITEM.GP_CHAR is
'������GP_CHAR';

comment on column R_COMMANDITEM.WRITE_FLAG is
'1 �C ��д 0 �C ����д';

comment on column R_COMMANDITEM.READ_FLAG is
'1 �C �ɶ� 0 �C ���ɶ�';

comment on column R_COMMANDITEM.OBJECT_FLAG_PG is
'1 - ֧�� 0 - ��֧��';

comment on column R_COMMANDITEM.OBJECT_FLAG_TG is
'1 - ֧�� 0 - ��֧��';

comment on column R_COMMANDITEM.OBJECT_FLAG_CUST is
'1 - ֧�� 0 - ��֧��';

comment on column R_COMMANDITEM.OBJECT_FLAG_SUBS is
'1 - ֧�� 0 - ��֧��';

/*==============================================================*/
/* Table: R_DATAITEM                                            */
/*==============================================================*/
create table R_DATAITEM  (
   PROTOCOL_NO          VARCHAR2(5)                     not null,
   COMMANDITEM_CODE     VARCHAR2(20)                    not null,
   DATAITEM_CODE        VARCHAR2(20)                    not null,
   DATAITEM_NAME        VARCHAR2(255),
   DATAITEM_DESC        VARCHAR2(2000),
   DATAITEM_UNIT        VARCHAR2(50),
   DATAITEM_FORMAT      VARCHAR2(255),
   DATAITEM_VALUE_TYPE  VARCHAR2(5),
   DATAITEM_VALUE_CODE  VARCHAR2(20),
   constraint PK_R_DATAITEM primary key (PROTOCOL_NO, COMMANDITEM_CODE, DATAITEM_CODE)
)
tablespace TABS_GATHER;

comment on column R_DATAITEM.PROTOCOL_NO is
'PK��������PROTOCOL_TERM';

/*==============================================================*/
/* Table: R_REALTIME_TASK                                       */
/*==============================================================*/
create table R_REALTIME_TASK  (
   TASK_ID              NUMBER                          not null,
   SEQUENCE_CODE        NUMBER                          not null,
   LOGICAL_ADDR         VARCHAR2(20),
   SEND_MSG             VARCHAR2(512),
   POST_TIME            DATE                           default SYSDATE,
   TASK_STATUS          VARCHAR2(5)                    default '0',
   GP_MARK              VARCHAR2(50),
   COMMAND_MARK         VARCHAR2(50),
   constraint PK_R_REALTIME_TASK primary key (TASK_ID)
)
tablespace TABS_INTERACTION;

comment on column R_REALTIME_TASK.TASK_STATUS is
'����״̬��0��δ����1�������У�2��ִ�гɹ���3��ִ��ʧ��';

/*==============================================================*/
/* Table: R_REALTIME_TASK_RECV                                  */
/*==============================================================*/
create table R_REALTIME_TASK_RECV  (
   TASK_ID              NUMBER,
   SEQUENCE_CODE        NUMBER                          not null,
   LOGICAL_ADDR         VARCHAR2(20),
   RECV_MSG             VARCHAR2(512),
   RECV_TIME            DATE                           default SYSDATE
)
tablespace TABS_INTERACTION;

/*==============================================================*/
/* Table: R_TASK                                                */
/*==============================================================*/
create table R_TASK  (
   TASK_ID              NUMBER                          not null,
   PROTOCOL_NO          VARCHAR2(5)                     not null,
   SYS_OBJECT           VARCHAR2(5)                     not null,
   TASK_NAME            VARCHAR2(255),
   TASK_TYPE            VARCHAR2(5),
   STARTUP_FLAG         CHAR(1),
   GP_CHAR              VARCHAR2(5),
   GP_SN                NUMBER,
   TIME_INTERVAL        NUMBER,
   DATA_TYPE            VARCHAR2(10),
   BASE_TIME_GW         DATE,
   SENDUP_CYCLE_GW      NUMBER,
   SENDUP_UNIT_GW       NUMBER,
   EXT_CNT_GW           NUMBER,
   START_TIME_MASTER    DATE,
   END_TIME_MASTER      DATE,
   EXEC_CYCLE_MASTER    NUMBER,
   EXEC_UNIT_MASTER     NUMBER,
   EXECONCE_TIMES       NUMBER,
   PRI_MASTER           NUMBER,
   AFN                  NUMBER,
   constraint PK_R_TASK primary key (TASK_ID, PROTOCOL_NO, SYS_OBJECT)
)
tablespace TABS_GATHER;

comment on column R_TASK.PROTOCOL_NO is
'PK��������PROTOCOL_TERM';

comment on column R_TASK.SYS_OBJECT is
'PK��������SYS_OBJECT';

comment on column R_TASK.TASK_TYPE is
'������TASK_TYPE';

comment on column R_TASK.STARTUP_FLAG is
'����(1)
ֹͣ(0)
';

comment on column R_TASK.GP_CHAR is
'������GP_CHAR';

comment on column R_TASK.TIME_INTERVAL is
'���ݵ�ʱ��������λΪ����';

comment on column R_TASK.SENDUP_UNIT_GW is
'��(0)��ʱ(1)����(2)����(3)';

comment on column R_TASK.EXEC_CYCLE_MASTER is
'��(0)��ʱ(1)����(2)����(3)';

comment on column R_TASK.PRI_MASTER is
'4��5��6��7��8';

/*==============================================================*/
/* Table: R_TASK_DATAITEM                                       */
/*==============================================================*/
create table R_TASK_DATAITEM  (
   TASK_ID              NUMBER                          not null,
   PROTOCOL_NO          VARCHAR2(5)                     not null,
   SYS_OBJECT           VARCHAR2(5)                     not null,
   COMMANDITEM_CODE     VARCHAR2(20)                    not null,
   SEQUENCE             NUMBER,
   constraint PK_R_TASK_DATAITEM primary key (TASK_ID, PROTOCOL_NO, SYS_OBJECT, COMMANDITEM_CODE)
)
tablespace TABS_GATHER;

comment on column R_TASK_DATAITEM.PROTOCOL_NO is
'PK��������PROTOCOL_TERM';

comment on column R_TASK_DATAITEM.SYS_OBJECT is
'PK��������SYS_OBJECT';

/*==============================================================*/
/* Table: R_TASK_TACTIC                                         */
/*==============================================================*/
create table R_TASK_TACTIC  (
   TACTIC_ID            NUMBER                          not null,
   TASK_TYPE            VARCHAR2(5)                     not null,
   PROTOCOL_NO          VARCHAR2(5)                     not null,
   SYS_OBJECT           VARCHAR2(5)                     not null,
   GP_CHAR              VARCHAR2(5)                     not null,
   GP_SN                NUMBER                          not null,
   TASK_ID              NUMBER                          not null,
   constraint PK_R_TASK_TACTIC primary key (TACTIC_ID)
)
tablespace TABS_GATHER;

comment on column R_TASK_TACTIC.TASK_TYPE is
'������TASK_TYPE��NOT NULL';

comment on column R_TASK_TACTIC.PROTOCOL_NO is
'������PROTOCOL_TERM��NOT NULL';

comment on column R_TASK_TACTIC.SYS_OBJECT is
'������SYS_OBJECT��NOT NULL';

comment on column R_TASK_TACTIC.GP_CHAR is
'������GP_CHAR��NOT NULL';

comment on column R_TASK_TACTIC.GP_SN is
'NOT NULL';

comment on column R_TASK_TACTIC.TASK_ID is
'NOT NULL';

/*==============================================================*/
/* Table: R_TERM_PARA                                           */
/*==============================================================*/
create table R_TERM_PARA  (
   TERM_ID              NUMBER                          not null,
   GP_CHAR              VARCHAR2(5)                     not null,
   GP_SN                NUMBER                          not null,
   PARA_CODE            VARCHAR2(50)                    not null,
   PARA_VALUE           VARCHAR2(500),
   COMMANDITEM_CODE     VARCHAR2(20),
   DATAITEM_CODE        VARCHAR2(50),
   constraint PK_R_TERM_PARA primary key (TERM_ID, GP_CHAR, GP_SN, PARA_CODE)
)
tablespace TABS_ARCHIVE;

comment on column R_TERM_PARA.GP_CHAR is
'������GP_CHAR';

/*==============================================================*/
/* Table: R_TERM_PARA_TP                                        */
/*==============================================================*/
create table R_TERM_PARA_TP  (
   PROTOCOL_NO          VARCHAR2(5)                     not null,
   TERM_TP_TYPE         VARCHAR2(5)                     not null,
   COMMANDITEM_CODE     VARCHAR2(20)                    not null,
   COMMANDITEM_NAME     VARCHAR2(255),
   PARA_CATE            VARCHAR2(5),
   PARA_TYPE            VARCHAR2(5),
   USE_MARK             VARCHAR2(5),
   ISPUBLIC             VARCHAR2(5),
   WR_FLAG              VARCHAR2(5),
   constraint PK_R_TERM_PARA_TP primary key (PROTOCOL_NO, TERM_TP_TYPE, COMMANDITEM_CODE)
)
tablespace TABS_ARCHIVE;

comment on column R_TERM_PARA_TP.PROTOCOL_NO is
'������PROTOCOL_TERM';

comment on column R_TERM_PARA_TP.TERM_TP_TYPE is
'������TERM_TP_TYPE';

comment on column R_TERM_PARA_TP.PARA_CATE is
'������PARA_CATE';

comment on column R_TERM_PARA_TP.USE_MARK is
'1 - ѡ�� 0 - ��ѡ';

comment on column R_TERM_PARA_TP.ISPUBLIC is
'1 - ���� 0 - ˽��';

comment on column R_TERM_PARA_TP.WR_FLAG is
'11 - ��д 10 - ֻ�� 01 - ֻд';

/*==============================================================*/
/* Table: R_TERM_PARA_TP_LIST                                   */
/*==============================================================*/
create table R_TERM_PARA_TP_LIST  (
   PROTOCOL_NO          VARCHAR2(5)                     not null,
   TERM_TP_TYPE         VARCHAR2(5)                     not null,
   COMMANDITEM_CODE     VARCHAR2(20)                    not null,
   DATAITEM_CODE        VARCHAR2(50)                    not null,
   DATAITEM_NAME        VARCHAR2(255),
   DATAITEM_DESC        VARCHAR2(500),
   DATAITEM_UNIT        VARCHAR2(50),
   DATAITEM_FORMAT      VARCHAR2(255),
   DATAITEM_VALUE       VARCHAR2(500),
   DATAITEM_DEF_VALUE   VARCHAR2(500),
   SHOW_HTML            LONG,
   V_FLAG               VARCHAR2(5),
   PARA_CODE            VARCHAR2(50)                    not null,
   constraint PK_R_TERM_PARA_TP_LIST primary key (PROTOCOL_NO, TERM_TP_TYPE, COMMANDITEM_CODE, DATAITEM_CODE)
)
tablespace TABS_ARCHIVE;

comment on column R_TERM_PARA_TP_LIST.PROTOCOL_NO is
'�ն˹�Լ�ţ�������PROTOCOL_TERM';

comment on column R_TERM_PARA_TP_LIST.TERM_TP_TYPE is
'������TERM_TP_TYPE';

comment on column R_TERM_PARA_TP_LIST.V_FLAG is
'0 �C ȡģ��ֵ 1 �C ȡ�ն˲�����ֵ';

/*==============================================================*/
/* Table: R_TERM_TASK                                           */
/*==============================================================*/
create table R_TERM_TASK  (
   LOGICAL_ADDR         VARCHAR2(20)                    not null,
   GP_CHAR              VARCHAR2(5)                     not null,
   GP_SN                NUMBER                          not null,
   TASK_ID              NUMBER                          not null,
   PROTOCOL_NO          VARCHAR2(5)                     not null,
   SYS_OBJECT           VARCHAR2(5)                     not null,
   STARTUP_FLAG         CHAR(1),
   TIME_INTERVAL        NUMBER,
   BASE_TIME_GW         DATE,
   SENDUP_CYCLE_GW      NUMBER,
   SENDUP_UNIT_GW       NUMBER,
   EXT_CNT_GW           NUMBER,
   constraint PK_R_TERM_TASK primary key (LOGICAL_ADDR, GP_CHAR, GP_SN, TASK_ID, PROTOCOL_NO, SYS_OBJECT)
)
tablespace TABS_GATHER;

comment on table R_TERM_TASK is
'��¼ÿ���ն��Ѿ����õ�������Ϣ��';

comment on column R_TERM_TASK.LOGICAL_ADDR is
'PK���ն��߼���ַ';

comment on column R_TERM_TASK.GP_CHAR is
'PK��������GP_CHAR';

comment on column R_TERM_TASK.PROTOCOL_NO is
'PK��������PROTOCOL_TERM';

comment on column R_TERM_TASK.SYS_OBJECT is
'PK��������SYS_OBJECT';

comment on column R_TERM_TASK.STARTUP_FLAG is
'1 - ���� 0 - ֹͣ';

comment on column R_TERM_TASK.TIME_INTERVAL is
'���ݵ�ʱ��������λΪ����';

comment on column R_TERM_TASK.BASE_TIME_GW is
'��׼ʱ�� �C ����';

comment on column R_TERM_TASK.SENDUP_CYCLE_GW is
'��������ֵ �C ����';

comment on column R_TERM_TASK.SENDUP_UNIT_GW is
'�������ڵ�λ �C ����';

comment on column R_TERM_TASK.EXT_CNT_GW is
'��ȡ���� �C ����';

alter table R_REALTIME_TASK_RECV
   add constraint FK_R_REALTI_R_TASK_RE_R_REALTI foreign key (TASK_ID)
      references R_REALTIME_TASK (TASK_ID);

alter table R_TASK_TACTIC
   add constraint FK_R_TASK_T_R_TASK_TA_R_TASK foreign key (TASK_ID, PROTOCOL_NO, SYS_OBJECT)
      references R_TASK (TASK_ID, PROTOCOL_NO, SYS_OBJECT);

