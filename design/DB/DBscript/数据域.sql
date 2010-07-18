/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2010-7-18 14:29:21                           */
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
);

comment on table D_EC_CURV_C is
'数据产生：由采集平台直接写上送数据进来；业务平台负责使用及转到历史表；
';

comment on column D_EC_CURV_C.ASSET_NO is
'电表资产编号';

comment on column D_EC_CURV_C.DDATE is
'YYYYMMDD，NOT NULL';

comment on column D_EC_CURV_C.DATA_FLAG is
'1 - 原始 2 - 修正';

comment on column D_EC_CURV_C.DATA_SOURCE is
'1 - 485 2 - 脉冲
3 - 交采 4 - 系统补全
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
);

comment on table D_EC_CURV_H is
'数据产生：由采集平台直接写上送数据进来；业务平台负责使用及转到历史表；
';

comment on column D_EC_CURV_H.ASSET_NO is
'电表资产编号';

comment on column D_EC_CURV_H.DDATE is
'YYYYMMDD，NOT NULL';

comment on column D_EC_CURV_H.DATA_FLAG is
'1 - 原始 2 - 修正';

comment on column D_EC_CURV_H.DATA_SOURCE is
'1 - 485 2 - 脉冲
3 - 交采 4 - 系统补全
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
);

comment on table D_EI_CURV_C is
'用于放（15）分钟（日以内）数据（当前日的/或者前7日，可配）
数据产生：由采集平台直接写上送数据进来；业务平台负责使用及转到历史表；
';

comment on column D_EI_CURV_C.ASSET_NO is
'电表资产编号';

comment on column D_EI_CURV_C.DDATE is
'YYYYMMDD，NOT NULL';

comment on column D_EI_CURV_C.DATA_FLAG is
'1 - 原始 2 - 修正';

comment on column D_EI_CURV_C.DATA_SOURCE is
'1 - 485 2 - 脉冲
3 - 交采 4 - 系统补全
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
);

comment on table D_EI_CURV_H is
'
';

comment on column D_EI_CURV_H.ASSET_NO is
'电表资产编号';

comment on column D_EI_CURV_H.DDATE is
'YYYYMMDD，NOT NULL';

comment on column D_EI_CURV_H.DATA_FLAG is
'1 - 原始 2 - 修正';

comment on column D_EI_CURV_H.DATA_SOURCE is
'1 - 485 2 - 脉冲
3 - 交采 4 - 系统补全
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
);

comment on table D_EI_FREEZE_DAY is
'用于放冻结数据（日冻结，保留7天或者1月，可配），电量数据按年自动归档 。
数据产生：由采集平台直接写上送数据进来；业务平台负责使用及转到历史表；
';

comment on column D_EI_FREEZE_DAY.ASSET_NO is
'电表资产编号';

comment on column D_EI_FREEZE_DAY.DDATE is
'YYYYMMDD，NOT NULL';

comment on column D_EI_FREEZE_DAY.DATA_FLAG is
'1 - 原始 2 - 修正';

comment on column D_EI_FREEZE_DAY.DATA_SOURCE is
'1 - 485 2 - 脉冲
3 - 交采 4 - 系统补全
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
);

comment on table D_EI_FREEZE_DAY_H is
'
';

comment on column D_EI_FREEZE_DAY_H.ASSET_NO is
'电表资产编号';

comment on column D_EI_FREEZE_DAY_H.DDATE is
'YYYYMMDD，NOT NULL';

comment on column D_EI_FREEZE_DAY_H.DATA_FLAG is
'1 - 原始 2 - 修正';

comment on column D_EI_FREEZE_DAY_H.DATA_SOURCE is
'1 - 485 2 - 脉冲
3 - 交采 4 - 系统补全
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
);

comment on table D_POWER_CRUV_C is
'系统当前功率数据（当前日/或者前7日以前的），历史自动归档为历史数据。
数据产生：由采集平台直接写上送数据进来；业务平台负责使用及转到历史表；
';

comment on column D_POWER_CRUV_C.ASSET_NO is
'电表资产编号';

comment on column D_POWER_CRUV_C.DDATE is
'YYYYMMDD，NOT NULL';

comment on column D_POWER_CRUV_C.DATA_FLAG is
'1 - 原始 2 - 修正';

comment on column D_POWER_CRUV_C.DATA_SOURCE is
'1 - 485 2 - 脉冲
3 - 交采 4 - 系统补全
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
);

comment on column D_POWER_CRUV_H.ASSET_NO is
'电表资产编号';

comment on column D_POWER_CRUV_H.DDATE is
'YYYYMMDD，NOT NULL';

comment on column D_POWER_CRUV_H.DATA_FLAG is
'1 - 原始 2 - 修正';

comment on column D_POWER_CRUV_H.DATA_SOURCE is
'1 - 485 2 - 脉冲
3 - 交采 4 - 系统补全
';

