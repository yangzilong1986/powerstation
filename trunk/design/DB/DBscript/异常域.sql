/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2010-8-1 18:01:10                            */
/*==============================================================*/


drop table E_EVENT_DATA cascade constraints;

drop table "E_Loubao_ECode" cascade constraints;

drop table "E_Loubao_event" cascade constraints;

drop sequence SEQ_E_EVENT;

create sequence SEQ_E_EVENT
increment by 1
start with 1
maxvalue 999999999999;

/*==============================================================*/
/* Table: E_EVENT_DATA                                          */
/*==============================================================*/
create table E_EVENT_DATA  (
   ED_ID                NUMBER                          not null,
   LOGICAL_ADDR         VARCHAR(20),
   GP_SN                NUMBER,
   GP_CHAR              VARCHAR2(5),
   EX_CODE_FEP          VARCHAR2(20),
   EX_TIME              DATE,
   ACCEPT_TIME          DATE,
   constraint PK_E_EVENT_DATA primary key (ED_ID)
);

/*==============================================================*/
/* Table: "E_Loubao_ECode"                                      */
/*==============================================================*/
create table "E_Loubao_ECode"  (
   "EventCode"          NUMBER(2)                       not null,
   "EventName"          VARCHAR2(30)                    not null,
   "ValueTitle"         VARCHAR2(40),
   "ValueUnit"          VARCHAR2(10),
   constraint PK_E_LOUBAO_ECODE primary key ("EventCode")
);

comment on table "E_Loubao_ECode" is
'漏保的事件代码';

comment on column "E_Loubao_ECode"."EventName" is
'事件名称';

comment on column "E_Loubao_ECode"."ValueTitle" is
'伴随信息名称';

comment on column "E_Loubao_ECode"."ValueUnit" is
'伴随信息计量单位';

/*==============================================================*/
/* Table: "E_Loubao_event"                                      */
/*==============================================================*/
create table "E_Loubao_event"  (
   LBSJ_ID              NUMBER                          not null,
   GP_SN                NUMBER,
   "EventCode"          NUMBER(2)                       not null,
   "TrigTime"           DATE,
   "ReceiveTime"        DATE,
   "Closed"             NUMBER(1)                       not null,
   "Locked"             NUMBER(1)                       not null,
   "Phase"              VARCHAR2(10)                    not null,
   "CurrentValue"       NUMBER(10),
   "ResetSent"          NUMBER(1)                      default 0 not null,
   constraint PK_E_LOUBAO_EVENT primary key (LBSJ_ID)
);

comment on table "E_Loubao_event" is
'漏保事件记录，包括短消息远程操作状态的管理';

comment on column "E_Loubao_event".LBSJ_ID is
'漏保事件编号，同时用作短消息恢复操作时采用的回执码';

comment on column "E_Loubao_event".GP_SN is
'测量点序号';

comment on column "E_Loubao_event"."TrigTime" is
'事件发生时间';

comment on column "E_Loubao_event"."ReceiveTime" is
'接收时间';

comment on column "E_Loubao_event"."Closed" is
'合闸状态，0：分闸，1：合闸';

comment on column "E_Loubao_event"."Locked" is
'锁死状态：1：锁死，0：未锁死';

comment on column "E_Loubao_event"."Phase" is
'相位';

comment on column "E_Loubao_event"."CurrentValue" is
'事件发生时的伴随信息的值';

comment on column "E_Loubao_event"."ResetSent" is
'已发送合闸命令，0：未发送 1：发送';

