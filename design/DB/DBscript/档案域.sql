/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2010-6-3 21:30:45                            */
/*==============================================================*/


alter table C_GP
   drop constraint FK_C_GP_C_GP_CTER_C_TERMIN;

alter table C_GP
   drop constraint FK_C_GP_C_MP_C_GP_C_MP;

alter table C_METER_MP_RELA
   drop constraint FK_C_METER__C_METER_C_C_METER;

alter table C_METER_MP_RELA
   drop constraint FK_C_METER__C_METER_M_C_MP;

alter table C_MP
   drop constraint FK_C_MP_C_MP_C_CO_C_CONS;

alter table C_MP_USE
   drop constraint FK_C_MP_USE_C_MP_C_MP_C_MP;

alter table C_PS
   drop constraint FK_C_PS_C_TERMINA_C_TERMIN;

drop table C_CONS cascade constraints;

drop table C_GP cascade constraints;

drop table C_METER cascade constraints;

drop table C_METER_MP_RELA cascade constraints;

drop table C_MP cascade constraints;

drop table C_MP_USE cascade constraints;

drop table C_PS cascade constraints;

drop table C_TERMINAL cascade constraints;

drop sequence SEQ_C_CONS;

drop sequence SEQ_C_GP;

drop sequence SEQ_C_METER;

drop sequence SEQ_C_MP;

drop sequence SEQ_C_PS;

drop sequence SEQ_C_TERMINAL;

drop sequence SEQ_MP_RELA;

create sequence SEQ_C_CONS
increment by 1
start with 1
 maxvalue 99999999
 minvalue 1
 cache 20;

create sequence SEQ_C_GP
increment by 1
start with 1
 maxvalue 99999999
 minvalue 1
 cache 20;

create sequence SEQ_C_METER
increment by 1
start with 1
 maxvalue 99999999
 minvalue 1
 cache 20;

create sequence SEQ_C_MP
increment by 1
start with 1
 maxvalue 99999999
 minvalue 1
 cache 20;

create sequence SEQ_C_PS
increment by 1
start with 1
 maxvalue 99999999
 minvalue 1
 cache 20;

create sequence SEQ_C_TERMINAL
increment by 1
start with 1
 maxvalue 99999999
 minvalue 1
 cache 20;

create sequence SEQ_MP_RELA
increment by 1
start with 1
 maxvalue 99999999
 minvalue 1
 cache 20;

/*==============================================================*/
/* Table: C_CONS                                                */
/*==============================================================*/
create table C_CONS  (
   CONS_ID              NUMBER(16)                      not null,
   CONS_NO              VARCHAR2(16)                    not null,
   CONS_NAME            VARCHAR2(256)                   not null,
   CONS_SORT_CODE       VARCHAR2(8),
   ELEC_ADDR            VARCHAR2(256)                   not null,
   TRADE_CODE           VARCHAR2(8),
   ELEC_TYPE_CODE       VARCHAR2(8),
   CONTRACT_CAP         NUMBER(16,6),
   RUN_CAP              NUMBER(16,6),
   SHIFT_NO             VARCHAR2(8),
   LODE_ATTR_CODE       VARCHAR2(8),
   VOLT_CODE            VARCHAR2(8),
   HEC_INDUSTRY_CODE    VARCHAR2(8),
   HOLIDAY              VARCHAR2(32),
   BUILD_DATE           DATE,
   PS_DATE              DATE,
   CANCEL_DATE          DATE,
   DUE_DATE             DATE,
   STATUS_CODE          VARCHAR2(8),
   ORG_ID               NUMBER,
   RRIO_CODE            VARCHAR2(8),
   CHK_CYCLE            NUMBER(5),
   POWEROFF_CODE        VARCHAR2(8),
   MR_SECT_NO           VARCHAR2(16)                    not null,
   LASTTIME_STAMP       DATE                           default SYSDATE,
   PINYIN_CODE          VARCHAR2(16),
   constraint PK_C_CONS primary key (CONS_ID)
)
tablespace TABS_ARCHIVE;

comment on table C_CONS is
'1) 依法与供电企业建立供用电关系的组织或个人称为用电客户，简称用户，不同用电地址视为不同用户。
同一客户相邻地址的多个受电点，可以立为多个用户，也可以立为一个用户。
用电客户包含用电地址，用电类别，供电电压，负荷性质，合同容量等用电属性。
2)可以通过新装增容及变更用电归档等业务，由实体转入产生记录。
3)该实体主要由查询客户用电基本信息等业务使用。';

comment on column C_CONS.CONS_ID is
'本实体记录的唯一标识，产生规则为流水号';

comment on column C_CONS.CONS_NO is
'用电客户的外部标识
引用国家电网公司营销管理代码类集:5110.1  用电客户编号规则';

comment on column C_CONS.CONS_NAME is
'用户的名称，一般等于客户实体中的客户名称，但也允许附加上一些非自然的信息。如 XXX（东城），便于通过用户名称直接识别。';

comment on column C_CONS.CONS_SORT_CODE is
'用户一种常用的分类方式，方便用户的管理
01 高压，02 低压非居民，03 低压居民';

comment on column C_CONS.ELEC_ADDR is
'用电客户的用电地址';

comment on column C_CONS.TRADE_CODE is
'用电客户的行业分类代码
引用国标GB/T 4754-2002';

comment on column C_CONS.ELEC_TYPE_CODE is
'用电客户的用电类别分类
引用国家电网公司营销管理代码类集:5110.4 用电类别
大工业用电，中小化肥，居民生活用电，农业生产用电，贫困县农业排灌用电
';

comment on column C_CONS.CONTRACT_CAP is
'合同约定的本用户的容量';

comment on column C_CONS.RUN_CAP is
'用电客户正在使用的合同容量，如暂停客户，在暂停期间其运行容量等于合同容量减去已暂停的容量';

comment on column C_CONS.SHIFT_NO is
'用电客户的生产班次分类
引用国家电网公司营销管理代码类集:5110.6用电客户生产班次代码
单班，二班，三班，连续生产
';

comment on column C_CONS.LODE_ATTR_CODE is
'负荷的重要程度分类
引用国家电网公司营销管理代码类集:5110.44负荷类别分类与代码
一类，二类，三类
';

comment on column C_CONS.VOLT_CODE is
'用电客户的供电电压等级代码，多路电源时取电压等级最高的供电电压等级代码
引用《国家电网公司信息分类与代码体系－综合代码类集－电压等级代码表》';

comment on column C_CONS.HEC_INDUSTRY_CODE is
'依据国家最新的高耗能行业划分';

comment on column C_CONS.HOLIDAY is
'周休日通过数字连续表示周休哪几天，类似于飞机航班日期表示，如1.2.3,表示星期一星期二和星期三休息。
';

comment on column C_CONS.BUILD_DATE is
'电子用户档案的首次建立日期';

comment on column C_CONS.PS_DATE is
'用户的首次送电日期';

comment on column C_CONS.CANCEL_DATE is
'销户业务信息归档的日期';

comment on column C_CONS.DUE_DATE is
'临时用电客户约定的用电到期日期';

comment on column C_CONS.STATUS_CODE is
'用电客户的状态说明，说明客户是否处于业扩变更中或已销户
引用国家电网公司营销管理代码类集:5110.9 客户状态标志代码
正常用电客户，当前新装客户，当前变更客户，已销户客户
';

comment on column C_CONS.ORG_ID is
'供电单位编码，一般是指的用户的直接供电管理单位，也可以是大客户管理中心等由于管理原因产生的客户管理单位';

comment on column C_CONS.RRIO_CODE is
'客户重要性等级：特级、一级、二级';

comment on column C_CONS.CHK_CYCLE is
'检查周期(单位：月)：用于存放客户检查周期信息，便于周期检查计划制定时，获取参数。';

comment on column C_CONS.POWEROFF_CODE is
'停电标志：01 已停电  02 未停电，反映客户当前是否处于停电状态';

comment on column C_CONS.MR_SECT_NO is
'抄表段标识,用于表示用电客户所属的抄表段';

comment on column C_CONS.LASTTIME_STAMP is
'最后表结构修改时间戳';

/*==============================================================*/
/* Table: C_GP                                                  */
/*==============================================================*/
create table C_GP  (
   GP_ID                NUMBER                          not null,
   OBJECT_ID            NUMBER                          not null,
   TERM_ID              NUMBER,
   MP_ID                NUMBER,
   GM_ID                NUMBER,
   GP_SN                NUMBER,
   GP_CHAR              VARCHAR2(5),
   GP_TYPE              VARCHAR2(5),
   GP_STATUS            VARCHAR2(5),
   GP_ADDR              VARCHAR2(20),
   CT_TIMES             NUMBER,
   PT_TIMES             NUMBER,
   PORT                 VARCHAR2(20),
   PROTOCOL_NO          VARCHAR2(5),
   COMPUTE_FLAG         VARCHAR2(5),
   PLUSE_CONSTANT       NUMBER,
   METER_CONSTANT       NUMBER,
   SUCRAT_CPT_ID        VARCHAR2(5),
   LINE_ID              NUMBER,
   MAS_METER_SN         NUMBER,
   TRAN_ID              NUMBER,
   LASTTIME_STAMP       DATE                           default SYSDATE,
   constraint PK_C_GP primary key (GP_ID)
)
tablespace TABS_ARCHIVE;

comment on table C_GP is
'采集系统范畴内的测量点概念';

comment on column C_GP.GP_CHAR is
'见编码GP_CHAR';

comment on column C_GP.GP_TYPE is
'见编码GP_TYPE';

comment on column C_GP.GP_STATUS is
'见编码GP_STATUS，1 - 有效 0 - 无效';

comment on column C_GP.GP_ADDR is
'通讯地址';

comment on column C_GP.PROTOCOL_NO is
'电表规约号，见编码PROTOCOL_METER';

comment on column C_GP.COMPUTE_FLAG is
'电量计算标识 1 - 参与计算 0 - 不参与计算';

comment on column C_GP.SUCRAT_CPT_ID is
'1 - 参与计算 0 - 不参与计算';

comment on column C_GP.LASTTIME_STAMP is
'最后表结构修改时间戳';

/*==============================================================*/
/* Table: C_METER                                               */
/*==============================================================*/
create table C_METER  (
   METER_ID             NUMBER(16)                      not null,
   INST_LOC             VARCHAR2(256),
   INST_DATE            DATE,
   T_FACTOR             NUMBER(10,2),
   REF_METER_FLAG       VARCHAR2(8),
   REF_METER_ID         NUMBER(16),
   MODULE_NO            VARCHAR2(32),
   ORG_ID               NUMBER,
   COMM_ADDR1           VARCHAR2(16),
   COMM_ADDR2           VARCHAR2(16),
   COMM_NO              VARCHAR2(8),
   BAUDRATE             VARCHAR2(16),
   通讯方式                 VARCHAR2(8)
)
tablespace TABS_ARCHIVE;

comment on table C_METER is
'1)用于记录计量点下安装的电能表运行设备信息，定义了电能表的运行属性，本实体主要包括电能表资产编号、综合倍率、安装日期、安装位置、是否参考表、参考表资产编号等属性。
2)通过新装、增容及变更用电归档、关口计量点新装及变更归档等业务，由实体转入产生记录。
3)该实体主要由查询计量点相关信息等业务使用。
';

comment on column C_METER.METER_ID is
'本实体记录的唯一标识号，取自营销设备域的电能表信息实体。';

comment on column C_METER.INST_LOC is
'电能表安装的物理位置';

comment on column C_METER.INST_DATE is
'安装日期';

comment on column C_METER.T_FACTOR is
'电能表综合倍率=电能表自身倍率*同一计量点下电流互感器的倍率*同一计量点下电压互感器的倍率';

comment on column C_METER.REF_METER_FLAG is
'标明电能表是否是参考表，包括01是、02否';

comment on column C_METER.REF_METER_ID is
'标明当前电能表用于结算用途时，参考那块电能表计量结算。';

comment on column C_METER.MODULE_NO is
'定义电能表是集抄时，对应的模块编号';

comment on column C_METER.ORG_ID is
'供电管理单位的代码';

comment on column C_METER.COMM_ADDR1 is
'通讯地址1';

comment on column C_METER.COMM_ADDR2 is
'通讯地址2';

comment on column C_METER.COMM_NO is
'通讯规约：645……';

comment on column C_METER.BAUDRATE is
'电能表的波特率';

comment on column C_METER.通讯方式 is
'通讯方式：485、gprs、红外……';

/*==============================================================*/
/* Table: C_METER_MP_RELA                                       */
/*==============================================================*/
create table C_METER_MP_RELA  (
   METER_MP_ID          NUMBER(16)                      not null,
   METER_ID             NUMBER(16)                      not null,
   MP_ID                NUMBER(16),
   constraint PK_C_METER_MP_RELA primary key (METER_MP_ID)
)
tablespace TABS_ARCHIVE;

comment on table C_METER_MP_RELA is
'1)用于记录电能表和计量点之间关系的信息，定义了电能表与计量点的唯一标识属性，本实体主要包括电能表计量点关系标识、电能表资产编号、计量点编号等属性。
2)通过新装、增容及变更用电归档、关口计量点新装及变更归档等业务，由实体转入产生记录。
3)该实体主要由查询计量点相关信息等业务使用。';

comment on column C_METER_MP_RELA.METER_MP_ID is
'本实体记录的唯一标识号';

comment on column C_METER_MP_RELA.METER_ID is
'本实体记录的唯一标识号，取自营销设备域的电能表信息实体。';

comment on column C_METER_MP_RELA.MP_ID is
'容器所属的计量点唯一标识号';

/*==============================================================*/
/* Table: C_MP                                                  */
/*==============================================================*/
create table C_MP  (
   MP_ID                NUMBER(16)                      not null,
   MP_NO                VARCHAR2(16)                    not null,
   MP_NAME              VARCHAR2(256),
   MP_ADDR              VARCHAR2(256),
   TYPE_CODE            NUMBER(12,4),
   MP_ATTR_CODE         VARCHAR2(8),
   USAGE_TYPE_CODE      VARCHAR2(8),
   SIDE_CODE            VARCHAR2(8),
   VOLT_CODE            VARCHAR2(8),
   APP_DATE             DATE,
   RUN_DATE             DATE,
   WIRING_MODE          VARCHAR2(8),
   MEAS_MODE            VARCHAR2(8),
   ORG_ID               NUMBER,
   SWITCH_NO            VARCHAR2(32),
   MR_SECT_NO           VARCHAR2(16),
   LINE_ID              NUMBER(16),
   TG_ID                NUMBER(16),
   EXCHG_TYPE_CODE      VARCHAR2(8),
   MD_TYPE_CODE         VARCHAR2(8),
   MR_SN                NUMBER(5),
   METER_FLAG           VARCHAR2(8),
   STATUS_CODE          VARCHAR2(8),
   LC_FLAG              VARCHAR2(8),
   CONS_ID              NUMBER(16),
   LASTTIME_STAMP       DATE                           default SYSDATE,
   PINYIN_CODE          VARCHAR2(16),
   constraint PK_C_MP primary key (MP_ID)
)
tablespace TABS_ARCHIVE;

comment on table C_MP is
'1)用于记录需要安装计量装置的位置点的信息，可以解决一个正反向表被两个户分别使用，这时计量点定义成两个；可以解决三个单相表代替一个三相表的功能，这时计量点定义成一个；可以解决主副表问题，这时计量点可以定义成一个。
定义了计量点的自然属性，本实体主要包括计量点编号、计量点名称、计量点地址、计量点分类、计量点性质等属性。
2)通过新装、增容及变更用电归档、关口计量点新装及变更归档等业务，由实体转入产生记录。
3)该实体主要由查询计量点相关信息等业务使用。';

comment on column C_MP.MP_ID is
'容器所属的计量点唯一标识号';

comment on column C_MP.MP_NO is
'用户自己编写的编号，默认跟标识一致';

comment on column C_MP.MP_NAME is
'计量点名称';

comment on column C_MP.MP_ADDR is
'计量点地址';

comment on column C_MP.TYPE_CODE is
'定义计量点的主要分类，包括：01用电客户、02关口等';

comment on column C_MP.MP_ATTR_CODE is
'定义计量点的主要性质，包括：01结算、02考核等';

comment on column C_MP.USAGE_TYPE_CODE is
'定义计量点的主要用途，引用国家电网公司营销管理代码类集:5110.19电能计量点类型分类与代码（01售电侧结算、02台区供电考核、03线路供电考核、04指标分析、05趸售供电关口、06地市供电关口、07省级供电关口、08跨省输电关口、09跨区输电关口、10跨国输电关口、11发电上网关口......）';

comment on column C_MP.SIDE_CODE is
'标明计量点所属的具体位置，包括：01变电站内、02变电站外、03高压侧、04低压侧等';

comment on column C_MP.VOLT_CODE is
'标明计量点的电压等级，引用国家电网公司信息分类与代码体系－综合代码类集－电压等级代码表，包括：01 10KV、02 110KV、03 220KV、04 35KV、05 220V、06 6KV、07 380V、08 500KV等';

comment on column C_MP.APP_DATE is
'标明计量点申请设立的日期';

comment on column C_MP.RUN_DATE is
'投运日期';

comment on column C_MP.WIRING_MODE is
'引用国家电网公司营销管理代码类集:5110.84电能表接线方式分类与代码（1单相、2三相三线、3三相四线）';

comment on column C_MP.MEAS_MODE is
'引用国家电网公司营销管理代码类集:5110.33电能计量方式代码（1高供高计、2高供低计、3低供低计）';

comment on column C_MP.ORG_ID is
'供电单位编码，一般是指的用户的直接供电管理单位，也可以是大客户管理中心等由于管理原因产生的客户管理单位';

comment on column C_MP.SWITCH_NO is
'标明计量点所属的开关编号';

comment on column C_MP.MR_SECT_NO is
'标明计量点所属的抄表段编号';

comment on column C_MP.LINE_ID is
'线路的系统内部唯一标识';

comment on column C_MP.TG_ID is
'台区的唯一标识';

comment on column C_MP.EXCHG_TYPE_CODE is
'标明计量点的电量交换对象，包括：01发电企业、02区域电网、03省级企业、04地市企业、05趸售单位等';

comment on column C_MP.MD_TYPE_CODE is
'引用国家电网公司营销管理代码类集:5110.32电能计量装置分类与代码（1Ⅰ类计量装置、2Ⅱ类计量装置、3Ⅲ类计量装置、4Ⅳ类计量装置、5Ⅴ类计量装置......）';

comment on column C_MP.MR_SN is
'定义计量点的抄表顺序';

comment on column C_MP.METER_FLAG is
'标明计量点是否安装计量装置，包括：01是、02否';

comment on column C_MP.STATUS_CODE is
'标明计量点的当前状态，包括：01设立、02在用、03停用、04撤销等';

comment on column C_MP.LC_FLAG is
'是否安装负控设备，01是、02否';

comment on column C_MP.CONS_ID is
'用户标识';

comment on column C_MP.LASTTIME_STAMP is
'最后表结构修改时间戳';

/*==============================================================*/
/* Table: C_MP_USE                                              */
/*==============================================================*/
create table C_MP_USE  (
   USAGE_ID             NUMBER(16)                      not null,
   MP_ID                NUMBER(16),
   USAGE_CODE           VARCHAR2(8)
)
tablespace TABS_ARCHIVE;

comment on table C_MP_USE is
'1)用于记录计量点用途的信息，计量点可以有多种用途，包括售电侧结算、台区供电考核、发电上网关口、跨国输电关口等。定义了计量点与用途间的对应关系属性，本实体主要包括用途ID、计量点编号、用途代码等属性。
2)通过新装、增容及变更用电归档、关口计量点新装及变更归档等业务，由实体转入产生记录。
3)该实体主要由查询计量点相关信息等业务使用。';

comment on column C_MP_USE.USAGE_ID is
'本实体记录的唯一标识号';

comment on column C_MP_USE.MP_ID is
'容器所属的计量点唯一标识号';

comment on column C_MP_USE.USAGE_CODE is
'引用国家电网公司营销管理代码类集:5110.19电能计量点类型分类与代码（01售电侧结算、02台区供电考核、03线路供电考核、04指标分析、05趸售供电关口、06地市供电关口、07省级供电关口、08跨省输电关口、09跨区输电关口、10跨国输电关口、11发电上网关口......）';

/*==============================================================*/
/* Table: C_PS                                                  */
/*==============================================================*/
create table C_PS  (
   PS_ID                NUMBER                          not null,
   TERM_ID              NUMBER,
   ASSET_NO             VARCHAR2(20),
   PS_ADDR              VARCHAR2(20),
   MODEL_CODE           VARCHAR2(5),
   COMM_MODE_GM         VARCHAR2(5),
   BTL                  VARCHAR2(5),
   RATED_EC             VARCHAR2(5),
   REMC_GEAR            VARCHAR2(5),
   REMC_GEAR_VALUE      VARCHAR2(5),
   OFF_DELAY_GEAR       VARCHAR2(5),
   OFF_DELAY_VALUE      VARCHAR2(5),
   FUNCTION_CODE        VARCHAR2(5),
   PS_TYPE              VARCHAR2(5),
   LASTTIME_STAMP       DATE                           default SYSDATE,
   PINYIN_CODE          VARCHAR2(16),
   constraint PK_C_PS primary key (PS_ID)
)
tablespace TABS_ARCHIVE;

comment on table C_PS is
'漏点保护开关(leakage protection switch)';

comment on column C_PS.ASSET_NO is
'NOT NULL';

comment on column C_PS.MODEL_CODE is
'漏保型号 : 见编码PS_MODEL';

comment on column C_PS.COMM_MODE_GM is
'通讯方式 : 见编码COMM_MODE_GM';

comment on column C_PS.BTL is
'见编码BTL';

comment on column C_PS.RATED_EC is
'见编码RATED_EC';

comment on column C_PS.REMC_GEAR is
'1-5档 （5 为自动档）';

comment on column C_PS.REMC_GEAR_VALUE is
'见编码REM_EC';

comment on column C_PS.OFF_DELAY_GEAR is
'（1—2）';

comment on column C_PS.OFF_DELAY_VALUE is
'见编码OFF_DELAY_VALUE';

comment on column C_PS.PS_TYPE is
'1：总保；2：二级保';

comment on column C_PS.LASTTIME_STAMP is
'最后表结构修改时间戳';

/*==============================================================*/
/* Table: C_TERMINAL                                            */
/*==============================================================*/
create table C_TERMINAL  (
   TERM_ID              NUMBER                          not null,
   ORG_ID               NUMBER,
   ASSET_NO             VARCHAR2(20),
   LOGICAL_ADDR         VARCHAR2(20),
   RUN_STATUS           VARCHAR2(5),
   CUR_STATUS           VARCHAR2(5),
   SIM_NO               VARCHAR2(20),
   TERM_TYPE            VARCHAR2(5),
   WIRING_MODE          VARCHAR2(5),
   MODEL_CODE           VARCHAR2(5),
   LEAVE_FAC_NO         VARCHAR2(20),
   BATCH_ID             VARCHAR2(20),
   MADE_FAC             VARCHAR2(5),
   LEAVE_FAC_DATE       DATE,
   INSTALL_DATE         DATE,
   COMM_MODE            VARCHAR2(5),
   CHANNEL_TYPE         VARCHAR2(5),
   PROTOCOL_NO          VARCHAR2(5),
   PR                   VARCHAR2(5),
   ISAC                 VARCHAR2(5),
   PHYSICS_ADDR         VARCHAR2(30),
   MACH_NO              VARCHAR2(10),
   FEP_CNL              VARCHAR2(30),
   CONSTR_GANG          VARCHAR2(50),
   COMM_PATTERN         NUMBER,
   LASTTIME_STAMP       DATE                           default SYSDATE,
   PINYIN_CODE          VARCHAR2(16),
   constraint PK_C_TERMINAL primary key (TERM_ID)
)
tablespace TABS_ARCHIVE;

comment on column C_TERMINAL.RUN_STATUS is
'见编码RUN_STATUS';

comment on column C_TERMINAL.CUR_STATUS is
'见编码CUR_STATUS';

comment on column C_TERMINAL.TERM_TYPE is
'见编码TERM_TYPE';

comment on column C_TERMINAL.WIRING_MODE is
'见编码WIRING_MODE';

comment on column C_TERMINAL.MODEL_CODE is
'终端型号 : 见编码TERM_MODEL';

comment on column C_TERMINAL.MADE_FAC is
'见编码MADE_FAC';

comment on column C_TERMINAL.COMM_MODE is
'见编码COMM_MODE';

comment on column C_TERMINAL.CHANNEL_TYPE is
'见编码CHANNEL_TYPE';

comment on column C_TERMINAL.PROTOCOL_NO is
'终端规约号，见编码PROTOCOL_TERM';

comment on column C_TERMINAL.PR is
'见编码PR';

comment on column C_TERMINAL.ISAC is
'1 - 接交采 0 - 不接交采';

comment on column C_TERMINAL.MACH_NO is
'见设备机器信息';

comment on column C_TERMINAL.COMM_PATTERN is
'1－主，2－从';

comment on column C_TERMINAL.LASTTIME_STAMP is
'最后表结构修改时间戳';

alter table C_GP
   add constraint FK_C_GP_C_GP_CTER_C_TERMIN foreign key (TERM_ID)
      references C_TERMINAL (TERM_ID);

alter table C_GP
   add constraint FK_C_GP_C_MP_C_GP_C_MP foreign key (MP_ID)
      references C_MP (MP_ID);

alter table C_METER_MP_RELA
   add constraint FK_C_METER__C_METER_C_C_METER foreign key (METER_ID)
      references C_METER (METER_ID);

alter table C_METER_MP_RELA
   add constraint FK_C_METER__C_METER_M_C_MP foreign key (MP_ID)
      references C_MP (MP_ID);

alter table C_MP
   add constraint FK_C_MP_C_MP_C_CO_C_CONS foreign key (CONS_ID)
      references C_CONS (CONS_ID);

alter table C_MP_USE
   add constraint FK_C_MP_USE_C_MP_C_MP_C_MP foreign key (MP_ID)
      references C_MP (MP_ID);

alter table C_PS
   add constraint FK_C_PS_C_TERMINA_C_TERMIN foreign key (TERM_ID)
      references C_TERMINAL (TERM_ID);

