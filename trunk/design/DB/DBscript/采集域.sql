/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2010-6-9 21:13:42                            */
/*==============================================================*/


alter table R_REALTIME_TASK_RECV
   drop constraint FK_R_REALTI_R_TASK_RE_R_REALTI;

drop table R_COMMANDITEM cascade constraints;

drop table R_REALTIME_TASK cascade constraints;

drop table R_REALTIME_TASK_RECV cascade constraints;

drop table R_TERM_PARA cascade constraints;

drop table R_TERM_PARA_TP cascade constraints;

drop table R_TERM_PARA_TP_LIST cascade constraints;

drop sequence SEQ_R_TASK;

drop sequence SEQ_TASK_SEQUNCE;

create sequence SEQ_R_TASK
increment by 1
start with 1
 maxvalue 9999999999
 minvalue 1
 cache 20;

create sequence SEQ_TASK_SEQUNCE
increment by 1
start with 1
 maxvalue 9999999999
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
'对不同规约命令项统一编码管理';

comment on column R_COMMANDITEM.PROTOCOL_NO is
'见编码PROTOCOL_TERM';

comment on column R_COMMANDITEM.GP_CHAR is
'见编码GP_CHAR';

comment on column R_COMMANDITEM.WRITE_FLAG is
'1 – 可写 0 – 不可写';

comment on column R_COMMANDITEM.READ_FLAG is
'1 – 可读 0 – 不可读';

comment on column R_COMMANDITEM.OBJECT_FLAG_PG is
'1 - 支持 0 - 不支持';

comment on column R_COMMANDITEM.OBJECT_FLAG_TG is
'1 - 支持 0 - 不支持';

comment on column R_COMMANDITEM.OBJECT_FLAG_CUST is
'1 - 支持 0 - 不支持';

comment on column R_COMMANDITEM.OBJECT_FLAG_SUBS is
'1 - 支持 0 - 不支持';

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
   constraint PK_R_REALTIME_TASK primary key (TASK_ID)
)
tablespace TABS_INTERACTION;

comment on column R_REALTIME_TASK.TASK_STATUS is
'任务状态：0：未处理；1：处理中；2：执行成功；3：执行失败';

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
'见编码GP_CHAR';

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
'见编码PROTOCOL_TERM';

comment on column R_TERM_PARA_TP.TERM_TP_TYPE is
'见编码TERM_TP_TYPE';

comment on column R_TERM_PARA_TP.PARA_CATE is
'见编码PARA_CATE';

comment on column R_TERM_PARA_TP.USE_MARK is
'1 - 选用 0 - 不选';

comment on column R_TERM_PARA_TP.ISPUBLIC is
'1 - 公有 0 - 私有';

comment on column R_TERM_PARA_TP.WR_FLAG is
'11 - 读写 10 - 只读 01 - 只写';

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
'终端规约号，见编码PROTOCOL_TERM';

comment on column R_TERM_PARA_TP_LIST.TERM_TP_TYPE is
'见编码TERM_TP_TYPE';

comment on column R_TERM_PARA_TP_LIST.V_FLAG is
'0 – 取模板值 1 – 取终端参数表值';

alter table R_REALTIME_TASK_RECV
   add constraint FK_R_REALTI_R_TASK_RE_R_REALTI foreign key (TASK_ID)
      references R_REALTIME_TASK (TASK_ID);

