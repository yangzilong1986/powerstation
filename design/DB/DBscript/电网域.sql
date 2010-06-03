/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2010-6-3 21:29:08                            */
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
'1) 描述线路基本信息，主要属性包括线路编码、线路名称、线损计算方式、单位长度线路电阻、单位长度线路电抗
2) 线损基础信息管理业务中录入产生，或通过与生产系统接口过程产生。
3) 该实体主要由线损基础信息管理业务、考核单元管理业务使用，在电费计算用户专线线路损耗也需要使用。';

comment on column G_LINE.LINE_ID is
'线路的系统内部唯一标识';

comment on column G_LINE.LINE_NO is
'线路的编码';

comment on column G_LINE.ORG_ID is
'线路管理单位';

comment on column G_LINE.SUBLINE_FLAG is
'标识是否支线：：是、 否 ';

comment on column G_LINE.RUN_STATUS_CODE is
'标识线路当前状态：运行、停用、 拆除';

comment on column G_LINE.CONS_ID is
'用电客户的内部唯一标识';

comment on column G_LINE.LASTTIME_STAMP is
'最后表结构修改时间戳';

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
'1) 描述了线路与线路的多对多关系，但当前生效的只有一个，主要属性：当前线路关系标志
2) 通过线损基础信息管理业务中录入产生记录，或通过与生产系统接口过程产生记录。
3) 该实体主要由线损基础信息管理业务、考核单元管理业务使用。';

comment on column G_LINE_RELA.LINE_RELA_ID is
'系统内部标识';

comment on column G_LINE_RELA.LINE_ID is
'线路的系统内部唯一标识';

comment on column G_LINE_RELA.LINK_LINE_ID is
'相关线路的标识';

comment on column G_LINE_RELA.CASCADE_FLAG is
'标明当前生效的线路级联关系 ,0. 是， 1. 否';

comment on column G_LINE_RELA.LASTTIME_STAMP is
'最后表结构修改时间戳';

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
'1) 当前线路与台区间是多对多关系，此实体是多对多关系的转化，但当前生效的只有一个，主要属性：当前线路台区关系标志
2) 通过线损基础信息管理业务中录入产生记录，或通过与生产系统接口过程产生记录。
3) 该实体主要由线损基础信息管理业务、考核单元管理业务使用。';

comment on column G_LINE_TG_RELA.LINE_TQ_ID is
'线路台区关系内部标识';

comment on column G_LINE_TG_RELA.TG_ID is
'台区标识';

comment on column G_LINE_TG_RELA.LINE_ID is
'线路的系统内部唯一标识';

comment on column G_LINE_TG_RELA.RELA_FLAG is
'当前线路与台区间是多对多关系， 但当前生效的只有一个 ,0. 否， 1. 是 ';

comment on column G_LINE_TG_RELA.LASTTIME_STAMP is
'最后表结构修改时间戳';

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
'1) 描述了变电站与线路的多对多关系,是变电站与线路多对多关系转化的实体，但当前生效的只有一个
2) 线损基础信息管理业务中录入产生，或通过与生产系统接口过程产生。
3) 该实体主要由线损基础信息管理业务、考核单元管理业务使用。';

comment on column G_SUBS_LINE_RELA.RELA_ID is
'变电站线路关系标识';

comment on column G_SUBS_LINE_RELA.EQUIP_ID is
'设备的唯一标识， 变更的时候用于对应线损模型中的变压器唯一标识';

comment on column G_SUBS_LINE_RELA.LINE_ID is
'线路的系统内部唯一标识';

comment on column G_SUBS_LINE_RELA.SUBS_ID is
'变电站系统内部标识';

comment on column G_SUBS_LINE_RELA.RELA_FLAG is
'标志当前生效的变电站线路关系';

comment on column G_SUBS_LINE_RELA.LASTTIME_STAMP is
'最后表结构修改时间戳';

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
'1) 描述台区基本信息,专变也做为台区管理，包括台区编码、台区名称、台区地址、公专变标志等信息
2) 通过线损基础信息管理业务中录入产生，或通过新装增容与变更用电归档过程产生；或通过与生产系统接口过程产生。
3) 该实体主要由线损基础信息管理业务、考核单元管理业务使用，在电费计算用户专线线路损耗也需要使用。';

comment on column G_TG.TG_ID is
'台区标识';

comment on column G_TG.ORG_ID is
'管理单位编号';

comment on column G_TG.TG_NO is
'台区编码';

comment on column G_TG.TG_NAME is
'台区名称';

comment on column G_TG.TG_CAP is
'台区容量 , 为可并列运行的变压器容量之和 ';

comment on column G_TG.CHG_DATE is
'台区新装、 拆除、 变更的时间';

comment on column G_TG.PUB_PRIV_FLAG is
'台区是 0. 公变或者 1. 专变 ';

comment on column G_TG.RUN_STATUS_CODE is
'台区运行状态';

comment on column G_TG.LASTTIME_STAMP is
'最后表结构修改时间戳';

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
'1) 描述变压器的运行信息及铭牌参数，包括变压器编码、变压器型号、变压器铭牌容量、当前状态等信息
2) 通过线损基础信息管理业务中录入产生，或新装增容与变更用电归档过程产生；或通过与生产系统接口过程产生。
3) 该实体主要由线损基础信息管理业务、考核单元管理业务使用，在电费计算用户专线线路损耗也需要使用。';

comment on column G_TRAN.EQUIP_ID is
'设备的唯一标识， 变更的时候用于对应线损模型中的变压器唯一标识';

comment on column G_TRAN.TG_ID is
'台区标识 ';

comment on column G_TRAN.CONS_ID is
'用电客户的内部唯一标识';

comment on column G_TRAN.TYPE_CODE is
'区分是变压器还是高压电动';

comment on column G_TRAN.TRAN_NAME is
'设备的名称';

comment on column G_TRAN.INST_DATE is
'设备的安装日期';

comment on column G_TRAN.PLATE_CAP is
'设备铭牌上登记的容量';

comment on column G_TRAN.MS_FLAG is
'引用国家电网公司营销管理代码类集 :5110.17 电源用途分类与代码';

comment on column G_TRAN.RUN_STATUS_CODE is
'本次变更前的运行状态 01 运行、 02 停用、 03 拆除 ';

comment on column G_TRAN.PROTECT_MODE is
'受电设备的保护方式， 引用代码“变压器保护方式分类”';

comment on column G_TRAN.FRSTSIDE_VOLT_CODE is
'设备的一侧电压 ';

comment on column G_TRAN.SNDSIDE_VOLT_CODE is
'设备的二侧电压';

comment on column G_TRAN.MODEL_NO is
'设备的型号';

comment on column G_TRAN.RV_HV is
'额定电压 _ 高压';

comment on column G_TRAN.RC_HV is
'额定电流 _ 高压';

comment on column G_TRAN.RV_MV is
'额定电压 _ 中压';

comment on column G_TRAN.RC_MV is
'额定电流 _ 中压';

comment on column G_TRAN.RV_LV is
'额定电压 _ 低压';

comment on column G_TRAN.RC_LV is
'额定电流 _ 低压';

comment on column G_TRAN.PR_CODE is
'设备的产权说明 01 局属、 02 用户';

comment on column G_TRAN.LASTTIME_STAMP is
'最后表结构修改时间戳';

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

