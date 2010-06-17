/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2010-6-17 21:30:20                           */
/*==============================================================*/


alter table G_LINE_RELA
   drop constraint FK_G_LINE_R_G_LINE_LI_G_LINE;

alter table G_LINE_TG_RELA
   drop constraint FK_G_LINE_T_G_LINE_TG_G_LINE;

alter table G_LINE_TG_RELA
   drop constraint FK_G_LINE_T_G_TG_LINE_G_TG;

alter table G_SUBS_LINE_RELA
   drop constraint FK_G_SUBS_L_G_LINE_SU_G_LINE;

alter table G_SUBS_LINE_RELA
   drop constraint FK_G_SUBS_L_G_SUBS_LI_G_TRAN;

alter table G_TRAN
   drop constraint FK_G_TRAN_G_TG_G_TR_G_TG;

drop table G_LINE cascade constraints;

drop table G_LINE_RELA cascade constraints;

drop table G_LINE_TG_RELA cascade constraints;

drop index G_LINE_SUBS_LINE_RELA_FK;

drop index G_SUBS_LINE_SUB_FK;

drop table G_SUBS_LINE_RELA cascade constraints;

drop table G_TG cascade constraints;

drop table G_TRAN cascade constraints;

drop sequence SEQ_G_LINE;

drop sequence SEQ_G_TG;

drop sequence SEQ_G_TRAN;

drop sequence SEQ_LINE_RELA;

create sequence SEQ_G_LINE
increment by 1
start with 1
 maxvalue 99999999
 minvalue 1
 cache 20;

create sequence SEQ_G_TG
increment by 1
start with 1
 maxvalue 99999999
 minvalue 1
 cache 20;

create sequence SEQ_G_TRAN
increment by 1
start with 1
 maxvalue 99999999
 minvalue 1
 cache 20;

create sequence SEQ_LINE_RELA
increment by 1
start with 1
 maxvalue 99999999
 minvalue 1
 cache 20;

/*==============================================================*/
/* Table: G_LINE                                                */
/*==============================================================*/
create table G_LINE  (
   LINE_ID              NUMBER(16)                      not null,
   LINE_NO              VARCHAR2(16),
   LINE_NAME            VARCHAR2(256)                   not null,
   ORG_ID               NUMBER                          not null,
   VOLT_CODE            VARCHAR2(8),
   SUBLINE_FLAG         VARCHAR2(8),
   RUN_STATUS_CODE      VARCHAR2(8),
   CONS_ID              NUMBER(16),
   LASTTIME_STAMP       DATE                           default SYSDATE,
   constraint PK_G_LINE primary key (LINE_ID)
)
tablespace TABS_ARCHIVE;

comment on table G_LINE is
'1) ������·������Ϣ����Ҫ���԰�����·���롢��·���ơ�������㷽ʽ����λ������·���衢��λ������·�翹
2) ���������Ϣ����ҵ����¼���������ͨ��������ϵͳ�ӿڹ��̲�����
3) ��ʵ����Ҫ�����������Ϣ����ҵ�񡢿��˵�Ԫ����ҵ��ʹ�ã��ڵ�Ѽ����û�ר����·���Ҳ��Ҫʹ�á�';

comment on column G_LINE.LINE_ID is
'��·��ϵͳ�ڲ�Ψһ��ʶ';

comment on column G_LINE.LINE_NO is
'��·�ı���';

comment on column G_LINE.ORG_ID is
'��·����λ';

comment on column G_LINE.SUBLINE_FLAG is
'��ʶ�Ƿ�֧�ߣ����ǡ� �� ';

comment on column G_LINE.RUN_STATUS_CODE is
'��ʶ��·��ǰ״̬�����С�ͣ�á� ���';

comment on column G_LINE.CONS_ID is
'�õ�ͻ����ڲ�Ψһ��ʶ';

comment on column G_LINE.LASTTIME_STAMP is
'����ṹ�޸�ʱ���';

/*==============================================================*/
/* Table: G_LINE_RELA                                           */
/*==============================================================*/
create table G_LINE_RELA  (
   LINE_RELA_ID         NUMBER(16)                      not null,
   LINE_ID              NUMBER(16),
   LINK_LINE_ID         NUMBER(16),
   CASCADE_FLAG         VARCHAR2(8),
   LASTTIME_STAMP       DATE                           default SYSDATE,
   constraint PK_G_LINE_RELA primary key (LINE_RELA_ID)
)
tablespace TABS_ARCHIVE;

comment on table G_LINE_RELA is
'1) ��������·����·�Ķ�Զ��ϵ������ǰ��Ч��ֻ��һ������Ҫ���ԣ���ǰ��·��ϵ��־
2) ͨ�����������Ϣ����ҵ����¼�������¼����ͨ��������ϵͳ�ӿڹ��̲�����¼��
3) ��ʵ����Ҫ�����������Ϣ����ҵ�񡢿��˵�Ԫ����ҵ��ʹ�á�';

comment on column G_LINE_RELA.LINE_RELA_ID is
'ϵͳ�ڲ���ʶ';

comment on column G_LINE_RELA.LINE_ID is
'��·��ϵͳ�ڲ�Ψһ��ʶ';

comment on column G_LINE_RELA.LINK_LINE_ID is
'�����·�ı�ʶ';

comment on column G_LINE_RELA.CASCADE_FLAG is
'������ǰ��Ч����·������ϵ ,0. �ǣ� 1. ��';

comment on column G_LINE_RELA.LASTTIME_STAMP is
'����ṹ�޸�ʱ���';

/*==============================================================*/
/* Table: G_LINE_TG_RELA                                        */
/*==============================================================*/
create table G_LINE_TG_RELA  (
   LINE_TQ_ID           NUMBER(16)                      not null,
   TG_ID                NUMBER(16),
   LINE_ID              NUMBER(16),
   RELA_FLAG            VARCHAR2(8),
   LASTTIME_STAMP       DATE                           default SYSDATE,
   constraint PK_G_LINE_TG_RELA primary key (LINE_TQ_ID)
)
tablespace TABS_ARCHIVE;

comment on table G_LINE_TG_RELA is
'1) ��ǰ��·��̨�����Ƕ�Զ��ϵ����ʵ���Ƕ�Զ��ϵ��ת��������ǰ��Ч��ֻ��һ������Ҫ���ԣ���ǰ��·̨����ϵ��־
2) ͨ�����������Ϣ����ҵ����¼�������¼����ͨ��������ϵͳ�ӿڹ��̲�����¼��
3) ��ʵ����Ҫ�����������Ϣ����ҵ�񡢿��˵�Ԫ����ҵ��ʹ�á�';

comment on column G_LINE_TG_RELA.LINE_TQ_ID is
'��·̨����ϵ�ڲ���ʶ';

comment on column G_LINE_TG_RELA.TG_ID is
'̨����ʶ';

comment on column G_LINE_TG_RELA.LINE_ID is
'��·��ϵͳ�ڲ�Ψһ��ʶ';

comment on column G_LINE_TG_RELA.RELA_FLAG is
'��ǰ��·��̨�����Ƕ�Զ��ϵ�� ����ǰ��Ч��ֻ��һ�� ,0. �� 1. �� ';

comment on column G_LINE_TG_RELA.LASTTIME_STAMP is
'����ṹ�޸�ʱ���';

/*==============================================================*/
/* Table: G_SUBS_LINE_RELA                                      */
/*==============================================================*/
create table G_SUBS_LINE_RELA  (
   RELA_ID              NUMBER(16)                      not null,
   EQUIP_ID             NUMBER(16),
   LINE_ID              NUMBER(16),
   SUBS_ID              NUMBER(16),
   RELA_FLAG            VARCHAR2(8),
   LASTTIME_STAMP       DATE                           default SYSDATE,
   constraint PK_G_SUBS_LINE_RELA primary key (RELA_ID)
)
tablespace TABS_ARCHIVE;

comment on table G_SUBS_LINE_RELA is
'1) �����˱��վ����·�Ķ�Զ��ϵ,�Ǳ��վ����·��Զ��ϵת����ʵ�壬����ǰ��Ч��ֻ��һ��
2) ���������Ϣ����ҵ����¼���������ͨ��������ϵͳ�ӿڹ��̲�����
3) ��ʵ����Ҫ�����������Ϣ����ҵ�񡢿��˵�Ԫ����ҵ��ʹ�á�';

comment on column G_SUBS_LINE_RELA.RELA_ID is
'���վ��·��ϵ��ʶ';

comment on column G_SUBS_LINE_RELA.EQUIP_ID is
'�豸��Ψһ��ʶ�� �����ʱ�����ڶ�Ӧ����ģ���еı�ѹ��Ψһ��ʶ';

comment on column G_SUBS_LINE_RELA.LINE_ID is
'��·��ϵͳ�ڲ�Ψһ��ʶ';

comment on column G_SUBS_LINE_RELA.SUBS_ID is
'���վϵͳ�ڲ���ʶ';

comment on column G_SUBS_LINE_RELA.RELA_FLAG is
'��־��ǰ��Ч�ı��վ��·��ϵ';

comment on column G_SUBS_LINE_RELA.LASTTIME_STAMP is
'����ṹ�޸�ʱ���';

/*==============================================================*/
/* Index: G_SUBS_LINE_SUB_FK                                    */
/*==============================================================*/
create index G_SUBS_LINE_SUB_FK on G_SUBS_LINE_RELA (
   SUBS_ID ASC
);

/*==============================================================*/
/* Index: G_LINE_SUBS_LINE_RELA_FK                              */
/*==============================================================*/
create index G_LINE_SUBS_LINE_RELA_FK on G_SUBS_LINE_RELA (
   LINE_ID ASC
);

/*==============================================================*/
/* Table: G_TG                                                  */
/*==============================================================*/
create table G_TG  (
   TG_ID                NUMBER(16)                      not null,
   ORG_ID               NUMBER                          not null,
   TG_NO                VARCHAR2(16)                    not null,
   TG_NAME              VARCHAR2(256)                   not null,
   TG_CAP               NUMBER(16,6),
   INST_ADDR            VARCHAR2(256),
   CHG_DATE             DATE,
   PUB_PRIV_FLAG        VARCHAR2(8),
   RUN_STATUS_CODE      VARCHAR2(8),
   LASTTIME_STAMP       DATE                           default SYSDATE,
   constraint PK_G_TG primary key (TG_ID)
)
tablespace TABS_ARCHIVE;

comment on table G_TG is
'1) ����̨��������Ϣ,ר��Ҳ��Ϊ̨����������̨�����롢̨�����ơ�̨����ַ����ר���־����Ϣ
2) ͨ�����������Ϣ����ҵ����¼���������ͨ����װ���������õ�鵵���̲�������ͨ��������ϵͳ�ӿڹ��̲�����
3) ��ʵ����Ҫ�����������Ϣ����ҵ�񡢿��˵�Ԫ����ҵ��ʹ�ã��ڵ�Ѽ����û�ר����·���Ҳ��Ҫʹ�á�';

comment on column G_TG.TG_ID is
'̨����ʶ';

comment on column G_TG.ORG_ID is
'����λ���';

comment on column G_TG.TG_NO is
'̨������';

comment on column G_TG.TG_NAME is
'̨������';

comment on column G_TG.TG_CAP is
'̨������ , Ϊ�ɲ������еı�ѹ������֮�� ';

comment on column G_TG.CHG_DATE is
'̨����װ�� ����� �����ʱ��';

comment on column G_TG.PUB_PRIV_FLAG is
'̨���� 0. ������� 1. ר�� ';

comment on column G_TG.RUN_STATUS_CODE is
'̨������״̬';

comment on column G_TG.LASTTIME_STAMP is
'����ṹ�޸�ʱ���';

/*==============================================================*/
/* Table: G_TRAN                                                */
/*==============================================================*/
create table G_TRAN  (
   EQUIP_ID             NUMBER(16)                      not null,
   TG_ID                NUMBER(16),
   ORG_ID               NUMBER,
   CONS_ID              NUMBER(16),
   TYPE_CODE            VARCHAR2(8),
   TRAN_NAME            VARCHAR2(256),
   INST_ADDR            VARCHAR2(256),
   INST_DATE            DATE,
   PLATE_CAP            NUMBER(16,6),
   MS_FLAG              VARCHAR2(8),
   RUN_STATUS_CODE      VARCHAR2(8),
   PUB_PRIV_FLAG        VARCHAR2(8),
   PROTECT_MODE         VARCHAR2(8),
   FRSTSIDE_VOLT_CODE   VARCHAR2(8),
   SNDSIDE_VOLT_CODE    VARCHAR2(8),
   MODEL_NO             VARCHAR2(8),
   RV_HV                VARCHAR2(8),
   RC_HV                VARCHAR2(8),
   RV_MV                VARCHAR2(8),
   RC_MV                VARCHAR2(8),
   RV_LV                VARCHAR2(8),
   RC_LV                VARCHAR2(8),
   PR_CODE              VARCHAR2(8),
   LASTTIME_STAMP       DATE                           default SYSDATE,
   constraint PK_G_TRAN primary key (EQUIP_ID)
)
tablespace TABS_ARCHIVE;

comment on table G_TRAN is
'1) ������ѹ����������Ϣ�����Ʋ�����������ѹ�����롢��ѹ���ͺš���ѹ��������������ǰ״̬����Ϣ
2) ͨ�����������Ϣ����ҵ����¼�����������װ���������õ�鵵���̲�������ͨ��������ϵͳ�ӿڹ��̲�����
3) ��ʵ����Ҫ�����������Ϣ����ҵ�񡢿��˵�Ԫ����ҵ��ʹ�ã��ڵ�Ѽ����û�ר����·���Ҳ��Ҫʹ�á�';

comment on column G_TRAN.EQUIP_ID is
'�豸��Ψһ��ʶ�� �����ʱ�����ڶ�Ӧ����ģ���еı�ѹ��Ψһ��ʶ';

comment on column G_TRAN.TG_ID is
'̨����ʶ ';

comment on column G_TRAN.CONS_ID is
'�õ�ͻ����ڲ�Ψһ��ʶ';

comment on column G_TRAN.TYPE_CODE is
'�����Ǳ�ѹ�����Ǹ�ѹ�綯';

comment on column G_TRAN.TRAN_NAME is
'�豸������';

comment on column G_TRAN.INST_DATE is
'�豸�İ�װ����';

comment on column G_TRAN.PLATE_CAP is
'�豸�����ϵǼǵ�����';

comment on column G_TRAN.MS_FLAG is
'���ù��ҵ�����˾Ӫ����������༯ :5110.17 ��Դ��;���������';

comment on column G_TRAN.RUN_STATUS_CODE is
'���α��ǰ������״̬ 01 ���С� 02 ͣ�á� 03 ��� ';

comment on column G_TRAN.PROTECT_MODE is
'�ܵ��豸�ı�����ʽ�� ���ô��롰��ѹ��������ʽ���ࡱ';

comment on column G_TRAN.FRSTSIDE_VOLT_CODE is
'�豸��һ���ѹ ';

comment on column G_TRAN.SNDSIDE_VOLT_CODE is
'�豸�Ķ����ѹ';

comment on column G_TRAN.MODEL_NO is
'�豸���ͺ�';

comment on column G_TRAN.RV_HV is
'���ѹ _ ��ѹ';

comment on column G_TRAN.RC_HV is
'����� _ ��ѹ';

comment on column G_TRAN.RV_MV is
'���ѹ _ ��ѹ';

comment on column G_TRAN.RC_MV is
'����� _ ��ѹ';

comment on column G_TRAN.RV_LV is
'���ѹ _ ��ѹ';

comment on column G_TRAN.RC_LV is
'����� _ ��ѹ';

comment on column G_TRAN.PR_CODE is
'�豸�Ĳ�Ȩ˵�� 01 ������ 02 �û�';

comment on column G_TRAN.LASTTIME_STAMP is
'����ṹ�޸�ʱ���';

alter table G_LINE_RELA
   add constraint FK_G_LINE_R_G_LINE_LI_G_LINE foreign key (LINE_ID)
      references G_LINE (LINE_ID);

alter table G_LINE_TG_RELA
   add constraint FK_G_LINE_T_G_LINE_TG_G_LINE foreign key (LINE_ID)
      references G_LINE (LINE_ID);

alter table G_LINE_TG_RELA
   add constraint FK_G_LINE_T_G_TG_LINE_G_TG foreign key (TG_ID)
      references G_TG (TG_ID);

alter table G_SUBS_LINE_RELA
   add constraint FK_G_SUBS_L_G_LINE_SU_G_LINE foreign key (LINE_ID)
      references G_LINE (LINE_ID);

alter table G_SUBS_LINE_RELA
   add constraint FK_G_SUBS_L_G_SUBS_LI_G_TRAN foreign key (EQUIP_ID)
      references G_TRAN (EQUIP_ID);

alter table G_TRAN
   add constraint FK_G_TRAN_G_TG_G_TR_G_TG foreign key (TG_ID)
      references G_TG (TG_ID);

