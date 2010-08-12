/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2010-7-28 18:10:54                           */
/*==============================================================*/


drop table D_EC_CURV_C cascade constraints;

drop table D_EC_CURV_H cascade constraints;

drop table D_EI_CURV_C cascade constraints;

drop table D_EI_CURV_H cascade constraints;

drop table D_EI_FREEZE_DAY cascade constraints;

drop table D_EI_FREEZE_DAY_H cascade constraints;

drop table D_POWER_CRUV_C cascade constraints;

drop table D_POWER_CRUV_H cascade constraints;

/*==============================================================*/
/* Table: D_EC_CURV_C                                           */
/*==============================================================*/
create table D_EC_CURV_C  (
   GP_ID                NUMBER                          not null,
   ASSET_NO             VARCHAR2(20),
   ORG_NO               VARCHAR2(20),
   DDATE                VARCHAR2(8)                     not null,
   DATA_TIME            DATE                            not null,
   ACCEPT_TIME          DATE                            not null,
   CT_TIMES             NUMBER,
   PT_TIMES             NUMBER,
   ECUR_A               NUMBER,
   ECUR_B               NUMBER,
   ECUR_C               NUMBER,
   ECUR_L               NUMBER,
   ECUR_S               NUMBER,
   VOLT_B               NUMBER,
   VOLT_A               NUMBER,
   VOLT_C               NUMBER,
   DATA_FLAG            VARCHAR2(5),
   DATA_SOURCE          VARCHAR2(5),
   constraint PK_D_EC_CURV_C primary key (GP_ID, DATA_TIME)
)
tablespace TABS_DATA;

comment on table D_EC_CURV_C is
'���ݲ������ɲɼ�ƽֱ̨��д�������ݽ�����ҵ��ƽ̨����ʹ�ü�ת����ʷ��
';

comment on column D_EC_CURV_C.ASSET_NO is
'����ʲ����';

comment on column D_EC_CURV_C.DDATE is
'YYYYMMDD��NOT NULL';

comment on column D_EC_CURV_C.DATA_FLAG is
'1 - ԭʼ 2 - ����';

comment on column D_EC_CURV_C.DATA_SOURCE is
'1 - 485 2 - ����
3 - ���� 4 - ϵͳ��ȫ
';

/*==============================================================*/
/* Table: D_EC_CURV_H                                           */
/*==============================================================*/
create table D_EC_CURV_H  (
   GP_ID                NUMBER                          not null,
   ASSET_NO             VARCHAR2(20),
   ORG_NO               VARCHAR2(20),
   DDATE                VARCHAR2(8)                     not null,
   DATA_TIME            DATE                            not null,
   ACCEPT_TIME          DATE                            not null,
   CT_TIMES             NUMBER,
   PT_TIMES             NUMBER,
   ECUR_A               NUMBER,
   ECUR_B               NUMBER,
   ECUR_C               NUMBER,
   ECUR_L               NUMBER,
   ECUR_S               NUMBER,
   VOLT_B               NUMBER,
   VOLT_A               NUMBER,
   VOLT_C               NUMBER,
   DATA_FLAG            VARCHAR2(5),
   DATA_SOURCE          VARCHAR2(5),
   constraint PK_D_EC_CURV_H primary key (GP_ID, DATA_TIME)
)
tablespace TABS_DATA;

comment on table D_EC_CURV_H is
'���ݲ������ɲɼ�ƽֱ̨��д�������ݽ�����ҵ��ƽ̨����ʹ�ü�ת����ʷ��
';

comment on column D_EC_CURV_H.ASSET_NO is
'����ʲ����';

comment on column D_EC_CURV_H.DDATE is
'YYYYMMDD��NOT NULL';

comment on column D_EC_CURV_H.DATA_FLAG is
'1 - ԭʼ 2 - ����';

comment on column D_EC_CURV_H.DATA_SOURCE is
'1 - 485 2 - ����
3 - ���� 4 - ϵͳ��ȫ
';

/*==============================================================*/
/* Table: D_EI_CURV_C                                           */
/*==============================================================*/
create table D_EI_CURV_C  (
   GP_ID                NUMBER                          not null,
   ASSET_NO             VARCHAR2(20),
   ORG_NO               VARCHAR2(20),
   DDATE                VARCHAR2(8)                     not null,
   DATA_TIME            DATE                            not null,
   ACCEPT_TIME          DATE                            not null,
   TOTAL_TIMES          NUMBER,
   P_ACT_TOTAL          NUMBER,
   P_ACT_SHARP          NUMBER,
   P_ACT_PEAK           NUMBER,
   P_ACT_LEVEL          NUMBER,
   P_ACT_VALLEY         NUMBER,
   I_ACT_TOTAL          NUMBER,
   I_ACT_SHARP          NUMBER,
   I_ACT_PEAK           NUMBER,
   I_ACT_LEVEL          NUMBER,
   I_ACT_VALLEY         NUMBER,
   REACT_Q1             NUMBER,
   REACT_Q2             NUMBER,
   REACT_Q3             NUMBER,
   REACT_Q4             NUMBER,
   P_REACT_TOTAL        NUMBER,
   P_REACT_SHARP        NUMBER,
   P_REACT_PEAK         NUMBER,
   P_REACT_LEVEL        NUMBER,
   P_REACT_VALLEY       NUMBER,
   I_REACT_TOTAL        NUMBER,
   I_REACT_SHARP        NUMBER,
   DATA_FLAG            VARCHAR2(5),
   DATA_SOURCE          VARCHAR2(5),
   I_REACT_VALLEY       NUMBER,
   I_REACT_LEVEL        NUMBER,
   I_REACT_PEAK         NUMBER,
   constraint PK_D_EI_CURV_C primary key (GP_ID, DATA_TIME)
)
tablespace TABS_DATA;

comment on table D_EI_CURV_C is
'���ڷţ�15�����ӣ������ڣ����ݣ���ǰ�յ�/����ǰ7�գ����䣩
���ݲ������ɲɼ�ƽֱ̨��д�������ݽ�����ҵ��ƽ̨����ʹ�ü�ת����ʷ��
';

comment on column D_EI_CURV_C.ASSET_NO is
'����ʲ����';

comment on column D_EI_CURV_C.DDATE is
'YYYYMMDD��NOT NULL';

comment on column D_EI_CURV_C.DATA_FLAG is
'1 - ԭʼ 2 - ����';

comment on column D_EI_CURV_C.DATA_SOURCE is
'1 - 485 2 - ����
3 - ���� 4 - ϵͳ��ȫ
';

/*==============================================================*/
/* Table: D_EI_CURV_H                                           */
/*==============================================================*/
create table D_EI_CURV_H  (
   GP_ID                NUMBER                          not null,
   ASSET_NO             VARCHAR2(20),
   ORG_NO               VARCHAR2(20),
   DDATE                VARCHAR2(8)                     not null,
   DATA_TIME            DATE                            not null,
   ACCEPT_TIME          DATE                            not null,
   TOTAL_TIMES          NUMBER,
   P_ACT_TOTAL          NUMBER,
   P_ACT_SHARP          NUMBER,
   P_ACT_PEAK           NUMBER,
   P_ACT_LEVEL          NUMBER,
   P_ACT_VALLEY         NUMBER,
   I_ACT_TOTAL          NUMBER,
   I_ACT_SHARP          NUMBER,
   I_ACT_PEAK           NUMBER,
   I_ACT_LEVEL          NUMBER,
   I_ACT_VALLEY         NUMBER,
   REACT_Q1             NUMBER,
   REACT_Q2             NUMBER,
   REACT_Q3             NUMBER,
   REACT_Q4             NUMBER,
   P_REACT_TOTAL        NUMBER,
   P_REACT_SHARP        NUMBER,
   P_REACT_PEAK         NUMBER,
   P_REACT_LEVEL        NUMBER,
   P_REACT_VALLEY       NUMBER,
   I_REACT_TOTAL        NUMBER,
   I_REACT_SHARP        NUMBER,
   DATA_FLAG            VARCHAR2(5),
   DATA_SOURCE          VARCHAR2(5),
   I_REACT_VALLEY       NUMBER,
   I_REACT_LEVEL        NUMBER,
   I_REACT_PEAK         NUMBER,
   constraint PK_D_EI_CURV_H primary key (GP_ID, DATA_TIME)
)
tablespace TABS_DATA;

comment on table D_EI_CURV_H is
'
';

comment on column D_EI_CURV_H.ASSET_NO is
'����ʲ����';

comment on column D_EI_CURV_H.DDATE is
'YYYYMMDD��NOT NULL';

comment on column D_EI_CURV_H.DATA_FLAG is
'1 - ԭʼ 2 - ����';

comment on column D_EI_CURV_H.DATA_SOURCE is
'1 - 485 2 - ����
3 - ���� 4 - ϵͳ��ȫ
';

/*==============================================================*/
/* Table: D_EI_FREEZE_DAY                                       */
/*==============================================================*/
create table D_EI_FREEZE_DAY  (
   GP_ID                NUMBER                          not null,
   ASSET_NO             VARCHAR2(20),
   ORG_NO               VARCHAR2(20),
   DDATE                VARCHAR2(8)                     not null,
   DATA_TIME            DATE                            not null,
   ACCEPT_TIME          DATE                            not null,
   TOTAL_TIMES          NUMBER,
   P_ACT_TOTAL          NUMBER,
   P_ACT_SHARP          NUMBER,
   P_ACT_PEAK           NUMBER,
   P_ACT_LEVEL          NUMBER,
   P_ACT_VALLEY         NUMBER,
   I_ACT_TOTAL          NUMBER,
   I_ACT_SHARP          NUMBER,
   I_ACT_PEAK           NUMBER,
   I_ACT_LEVEL          NUMBER,
   I_ACT_VALLEY         NUMBER,
   REACT_Q1             NUMBER,
   REACT_Q2             NUMBER,
   REACT_Q3             NUMBER,
   REACT_Q4             NUMBER,
   P_REACT_TOTAL        NUMBER,
   P_REACT_SHARP        NUMBER,
   P_REACT_PEAK         NUMBER,
   P_REACT_LEVEL        NUMBER,
   P_REACT_VALLEY       NUMBER,
   I_REACT_TOTAL        NUMBER,
   I_REACT_SHARP        NUMBER,
   I_REACT_VALLEY       NUMBER,
   I_REACT_LEVEL        NUMBER,
   I_REACT_PEAK         NUMBER,
   P_ACT_MAX_DEMAND     NUMBER,
   P_ACT_MAX_DEMAND_TIME DATE,
   I_ACT_MAX_DEMAND     NUMBER,
   I_ACT_MAX_DEMAND_TIME DATE,
   P_REACT_MAX_DEMAND   NUMBER,
   P_REACT_MAX_DEMAND_TIME DATE,
   I_REACT_MAX_DEMAND   NUMBER,
   I_REACT_MAX_DEMAND_TIME DATE,
   DATA_FLAG            VARCHAR2(5),
   DATA_SOURCE          VARCHAR2(5),
   constraint PK_D_EI_FREEZE_DAY primary key (GP_ID, DATA_TIME)
)
tablespace TABS_DATA;

comment on table D_EI_FREEZE_DAY is
'���ڷŶ������ݣ��ն��ᣬ����7�����1�£����䣩���������ݰ����Զ��鵵 ��
���ݲ������ɲɼ�ƽֱ̨��д�������ݽ�����ҵ��ƽ̨����ʹ�ü�ת����ʷ��
';

comment on column D_EI_FREEZE_DAY.ASSET_NO is
'����ʲ����';

comment on column D_EI_FREEZE_DAY.DDATE is
'YYYYMMDD��NOT NULL';

comment on column D_EI_FREEZE_DAY.DATA_FLAG is
'1 - ԭʼ 2 - ����';

comment on column D_EI_FREEZE_DAY.DATA_SOURCE is
'1 - 485 2 - ����
3 - ���� 4 - ϵͳ��ȫ
';

/*==============================================================*/
/* Table: D_EI_FREEZE_DAY_H                                     */
/*==============================================================*/
create table D_EI_FREEZE_DAY_H  (
   GP_ID                NUMBER                          not null,
   ASSET_NO             VARCHAR2(20),
   ORG_NO               VARCHAR2(20),
   DDATE                VARCHAR2(8)                     not null,
   DATA_TIME            DATE                            not null,
   ACCEPT_TIME          DATE                            not null,
   TOTAL_TIMES          NUMBER,
   P_ACT_TOTAL          NUMBER,
   P_ACT_SHARP          NUMBER,
   P_ACT_PEAK           NUMBER,
   P_ACT_LEVEL          NUMBER,
   P_ACT_VALLEY         NUMBER,
   I_ACT_TOTAL          NUMBER,
   I_ACT_SHARP          NUMBER,
   I_ACT_PEAK           NUMBER,
   I_ACT_LEVEL          NUMBER,
   I_ACT_VALLEY         NUMBER,
   REACT_Q1             NUMBER,
   REACT_Q2             NUMBER,
   REACT_Q3             NUMBER,
   REACT_Q4             NUMBER,
   P_REACT_TOTAL        NUMBER,
   P_REACT_SHARP        NUMBER,
   P_REACT_PEAK         NUMBER,
   P_REACT_LEVEL        NUMBER,
   P_REACT_VALLEY       NUMBER,
   I_REACT_TOTAL        NUMBER,
   I_REACT_SHARP        NUMBER,
   I_REACT_VALLEY       NUMBER,
   I_REACT_LEVEL        NUMBER,
   I_REACT_PEAK         NUMBER,
   P_ACT_MAX_DEMAND     NUMBER,
   P_ACT_MAX_DEMAND_TIME DATE,
   I_ACT_MAX_DEMAND     NUMBER,
   I_ACT_MAX_DEMAND_TIME DATE,
   P_REACT_MAX_DEMAND   NUMBER,
   P_REACT_MAX_DEMAND_TIME DATE,
   I_REACT_MAX_DEMAND   NUMBER,
   I_REACT_MAX_DEMAND_TIME DATE,
   DATA_FLAG            VARCHAR2(5),
   DATA_SOURCE          VARCHAR2(5),
   constraint PK_D_EI_FREEZE_DAY_H primary key (GP_ID, DATA_TIME)
)
tablespace TABS_DATA;

comment on table D_EI_FREEZE_DAY_H is
'
';

comment on column D_EI_FREEZE_DAY_H.ASSET_NO is
'����ʲ����';

comment on column D_EI_FREEZE_DAY_H.DDATE is
'YYYYMMDD��NOT NULL';

comment on column D_EI_FREEZE_DAY_H.DATA_FLAG is
'1 - ԭʼ 2 - ����';

comment on column D_EI_FREEZE_DAY_H.DATA_SOURCE is
'1 - 485 2 - ����
3 - ���� 4 - ϵͳ��ȫ
';

/*==============================================================*/
/* Table: D_POWER_CRUV_C                                        */
/*==============================================================*/
create table D_POWER_CRUV_C  (
   GP_ID                NUMBER                          not null,
   ASSET_NO             VARCHAR2(20),
   ORG_NO               VARCHAR2(20),
   DDATE                VARCHAR2(8)                     not null,
   DATA_TIME            DATE                            not null,
   ACCEPT_TIME          DATE                            not null,
   TOTAL_TIMES          NUMBER,
   ACT_POWER            NUMBER,
   REACT_POWER          NUMBER,
   ACT_POWER_A          NUMBER,
   ACT_POWER_B          NUMBER,
   ACT_POWER_C          NUMBER,
   REACT_POWER_A        NUMBER,
   REACT_POWER_B        NUMBER,
   REACT_POWER_C        NUMBER,
   DATA_FLAG            VARCHAR2(5),
   DATA_SOURCE          VARCHAR2(5),
   constraint PK_D_POWER_CRUV_C primary key (GP_ID, DATA_TIME)
)
tablespace TABS_DATA;

comment on table D_POWER_CRUV_C is
'ϵͳ��ǰ�������ݣ���ǰ��/����ǰ7����ǰ�ģ�����ʷ�Զ��鵵Ϊ��ʷ���ݡ�
���ݲ������ɲɼ�ƽֱ̨��д�������ݽ�����ҵ��ƽ̨����ʹ�ü�ת����ʷ��
';

comment on column D_POWER_CRUV_C.ASSET_NO is
'����ʲ����';

comment on column D_POWER_CRUV_C.DDATE is
'YYYYMMDD��NOT NULL';

comment on column D_POWER_CRUV_C.DATA_FLAG is
'1 - ԭʼ 2 - ����';

comment on column D_POWER_CRUV_C.DATA_SOURCE is
'1 - 485 2 - ����
3 - ���� 4 - ϵͳ��ȫ
';

/*==============================================================*/
/* Table: D_POWER_CRUV_H                                        */
/*==============================================================*/
create table D_POWER_CRUV_H  (
   GP_ID                NUMBER                          not null,
   ASSET_NO             VARCHAR2(20),
   ORG_NO               VARCHAR2(20),
   DDATE                VARCHAR2(8)                     not null,
   DATA_TIME            DATE                            not null,
   ACCEPT_TIME          DATE                            not null,
   TOTAL_TIMES          NUMBER,
   ACT_POWER            NUMBER,
   REACT_POWER          NUMBER,
   ACT_POWER_A          NUMBER,
   ACT_POWER_B          NUMBER,
   ACT_POWER_C          NUMBER,
   REACT_POWER_A        NUMBER,
   REACT_POWER_B        NUMBER,
   REACT_POWER_C        NUMBER,
   DATA_FLAG            VARCHAR2(5),
   DATA_SOURCE          VARCHAR2(5),
   constraint PK_D_POWER_CRUV_H primary key (GP_ID, DATA_TIME)
)
tablespace TABS_DATA;

comment on column D_POWER_CRUV_H.ASSET_NO is
'����ʲ����';

comment on column D_POWER_CRUV_H.DDATE is
'YYYYMMDD��NOT NULL';

comment on column D_POWER_CRUV_H.DATA_FLAG is
'1 - ԭʼ 2 - ����';

comment on column D_POWER_CRUV_H.DATA_SOURCE is
'1 - 485 2 - ����
3 - ���� 4 - ϵͳ��ȫ
';

