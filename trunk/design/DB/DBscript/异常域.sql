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
'©�����¼�����';

comment on column "E_Loubao_ECode"."EventName" is
'�¼�����';

comment on column "E_Loubao_ECode"."ValueTitle" is
'������Ϣ����';

comment on column "E_Loubao_ECode"."ValueUnit" is
'������Ϣ������λ';

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
'©���¼���¼����������ϢԶ�̲���״̬�Ĺ���';

comment on column "E_Loubao_event".LBSJ_ID is
'©���¼���ţ�ͬʱ��������Ϣ�ָ�����ʱ���õĻ�ִ��';

comment on column "E_Loubao_event".GP_SN is
'���������';

comment on column "E_Loubao_event"."TrigTime" is
'�¼�����ʱ��';

comment on column "E_Loubao_event"."ReceiveTime" is
'����ʱ��';

comment on column "E_Loubao_event"."Closed" is
'��բ״̬��0����բ��1����բ';

comment on column "E_Loubao_event"."Locked" is
'����״̬��1��������0��δ����';

comment on column "E_Loubao_event"."Phase" is
'��λ';

comment on column "E_Loubao_event"."CurrentValue" is
'�¼�����ʱ�İ�����Ϣ��ֵ';

comment on column "E_Loubao_event"."ResetSent" is
'�ѷ��ͺ�բ���0��δ���� 1������';

