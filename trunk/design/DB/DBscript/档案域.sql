/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2010-7-24 20:46:02                           */
/*==============================================================*/


alter table C_GP
   drop constraint FK_C_GP_C_GP_CTER_C_TERMIN;

alter table C_GP
   drop constraint FK_C_GP_C_MP_C_GP_C_MP;

alter table C_MP
   drop constraint FK_C_MP_C_MP_C_CO_C_CONS;

alter table C_MP
   drop constraint FK_C_MP_C_MP_C_ME_C_METER;

alter table C_MP_USE
   drop constraint FK_C_MP_USE_C_MP_C_MP_C_MP;

alter table C_PS
   drop constraint FK_C_PS_C_GP_C_PS_C_GP;

alter table C_PS
   drop constraint FK_C_PS_C_TERMINA_C_TERMIN;

alter table C_TERM_OBJ_RELA
   drop constraint FK_C_TERM_C_TERM_OBJ_RELA;

drop table C_CONS cascade constraints;

drop table C_GP cascade constraints;

drop table C_METER cascade constraints;

drop table C_MP cascade constraints;

drop table C_MP_USE cascade constraints;

drop table C_PS cascade constraints;

drop table C_TERMINAL cascade constraints;

drop table C_TERM_OBJ_RELA cascade constraints;

drop sequence SEQ_C_CONS;

drop sequence SEQ_C_GP;

drop sequence SEQ_C_METER;

drop sequence SEQ_C_MP;

drop sequence SEQ_C_PS;

drop sequence SEQ_C_TERMINAL;

drop sequence SEQ_C_TERM_OBJ_RELA;

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

create sequence SEQ_C_TERM_OBJ_RELA
increment by 1
start with 1
 maxvalue 999999999
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
'1) �����빩����ҵ�������õ��ϵ����֯����˳�Ϊ�õ�ͻ�������û�����ͬ�õ��ַ��Ϊ��ͬ�û���
ͬһ�ͻ����ڵ�ַ�Ķ���ܵ�㣬������Ϊ����û���Ҳ������Ϊһ���û���
�õ�ͻ������õ��ַ���õ���𣬹����ѹ���������ʣ���ͬ�������õ����ԡ�
2)����ͨ����װ���ݼ�����õ�鵵��ҵ����ʵ��ת�������¼��
3)��ʵ����Ҫ�ɲ�ѯ�ͻ��õ������Ϣ��ҵ��ʹ�á�';

comment on column C_CONS.CONS_ID is
'��ʵ���¼��Ψһ��ʶ����������Ϊ��ˮ��';

comment on column C_CONS.CONS_NO is
'�õ�ͻ����ⲿ��ʶ
���ù��ҵ�����˾Ӫ����������༯:5110.1  �õ�ͻ���Ź���';

comment on column C_CONS.CONS_NAME is
'�û������ƣ�һ����ڿͻ�ʵ���еĿͻ����ƣ���Ҳ��������һЩ����Ȼ����Ϣ���� XXX�����ǣ�������ͨ���û�����ֱ��ʶ��';

comment on column C_CONS.CONS_SORT_CODE is
'�û�һ�ֳ��õķ��෽ʽ�������û��Ĺ���
01 ��ѹ��02 ��ѹ�Ǿ���03 ��ѹ����';

comment on column C_CONS.ELEC_ADDR is
'�õ�ͻ����õ��ַ';

comment on column C_CONS.TRADE_CODE is
'�õ�ͻ�����ҵ�������
���ù���GB/T 4754-2002';

comment on column C_CONS.ELEC_TYPE_CODE is
'�õ�ͻ����õ�������
���ù��ҵ�����˾Ӫ����������༯:5110.4 �õ����
��ҵ�õ磬��С���ʣ����������õ磬ũҵ�����õ磬ƶ����ũҵ�Ź��õ�
';

comment on column C_CONS.CONTRACT_CAP is
'��ͬԼ���ı��û�������';

comment on column C_CONS.RUN_CAP is
'�õ�ͻ�����ʹ�õĺ�ͬ����������ͣ�ͻ�������ͣ�ڼ��������������ں�ͬ������ȥ����ͣ������';

comment on column C_CONS.SHIFT_NO is
'�õ�ͻ���������η���
���ù��ҵ�����˾Ӫ����������༯:5110.6�õ�ͻ�������δ���
���࣬���࣬���࣬��������
';

comment on column C_CONS.LODE_ATTR_CODE is
'���ɵ���Ҫ�̶ȷ���
���ù��ҵ�����˾Ӫ����������༯:5110.44���������������
һ�࣬���࣬����
';

comment on column C_CONS.VOLT_CODE is
'�õ�ͻ��Ĺ����ѹ�ȼ����룬��·��Դʱȡ��ѹ�ȼ���ߵĹ����ѹ�ȼ�����
���á����ҵ�����˾��Ϣ�����������ϵ���ۺϴ����༯����ѹ�ȼ������';

comment on column C_CONS.HEC_INDUSTRY_CODE is
'���ݹ������µĸߺ�����ҵ����';

comment on column C_CONS.HOLIDAY is
'������ͨ������������ʾ�����ļ��죬�����ڷɻ��������ڱ�ʾ����1.2.3,��ʾ����һ���ڶ�����������Ϣ��
';

comment on column C_CONS.BUILD_DATE is
'�����û��������״ν�������';

comment on column C_CONS.PS_DATE is
'�û����״��͵�����';

comment on column C_CONS.CANCEL_DATE is
'����ҵ����Ϣ�鵵������';

comment on column C_CONS.DUE_DATE is
'��ʱ�õ�ͻ�Լ�����õ絽������';

comment on column C_CONS.STATUS_CODE is
'�õ�ͻ���״̬˵����˵���ͻ��Ƿ���ҵ������л�������
���ù��ҵ�����˾Ӫ����������༯:5110.9 �ͻ�״̬��־����
�����õ�ͻ�����ǰ��װ�ͻ�����ǰ����ͻ����������ͻ�
';

comment on column C_CONS.ORG_ID is
'���絥λ���룬һ����ָ���û���ֱ�ӹ������λ��Ҳ�����Ǵ�ͻ��������ĵ����ڹ���ԭ������Ŀͻ�����λ';

comment on column C_CONS.RRIO_CODE is
'�ͻ���Ҫ�Եȼ����ؼ���һ��������';

comment on column C_CONS.CHK_CYCLE is
'�������(��λ����)�����ڴ�ſͻ����������Ϣ���������ڼ��ƻ��ƶ�ʱ����ȡ������';

comment on column C_CONS.POWEROFF_CODE is
'ͣ���־��01 ��ͣ��  02 δͣ�磬��ӳ�ͻ���ǰ�Ƿ���ͣ��״̬';

comment on column C_CONS.MR_SECT_NO is
'����α�ʶ,���ڱ�ʾ�õ�ͻ������ĳ����';

comment on column C_CONS.LASTTIME_STAMP is
'����ṹ�޸�ʱ���';

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
'�ɼ�ϵͳ�����ڵĲ��������';

comment on column C_GP.GP_CHAR is
'������GP_CHAR';

comment on column C_GP.GP_TYPE is
'������GP_TYPE';

comment on column C_GP.GP_STATUS is
'������GP_STATUS��1 - ��Ч 0 - ��Ч';

comment on column C_GP.GP_ADDR is
'ͨѶ��ַ';

comment on column C_GP.PROTOCOL_NO is
'����Լ�ţ�������PROTOCOL_METER';

comment on column C_GP.COMPUTE_FLAG is
'���������ʶ 1 - ������� 0 - ���������';

comment on column C_GP.SUCRAT_CPT_ID is
'1 - ������� 0 - ���������';

comment on column C_GP.LASTTIME_STAMP is
'����ṹ�޸�ʱ���';

/*==============================================================*/
/* Table: C_METER                                               */
/*==============================================================*/
create table C_METER  (
   METER_ID             NUMBER(16)                      not null,
   ASSET_NO             VARCHAR2(32),
   INST_LOC             VARCHAR2(256),
   INST_DATE            DATE,
   T_FACTOR             NUMBER(10,2),
   REF_METER_FLAG       VARCHAR2(8),
   REF_METER_ID         NUMBER(16),
   MODULE_NO            VARCHAR2(32),
   ORG_ID               NUMBER,
   COMM_NO              VARCHAR2(8),
   BAUDRATE             VARCHAR2(16),
   COMM_MODE            VARCHAR2(8)
)
tablespace TABS_ARCHIVE;

comment on table C_METER is
'1)���ڼ�¼�������°�װ�ĵ��ܱ������豸��Ϣ�������˵��ܱ���������ԣ���ʵ����Ҫ�������ܱ��ʲ���š��ۺϱ��ʡ���װ���ڡ���װλ�á��Ƿ�ο����ο����ʲ���ŵ����ԡ�
2)ͨ����װ�����ݼ�����õ�鵵���ؿڼ�������װ������鵵��ҵ����ʵ��ת�������¼��
3)��ʵ����Ҫ�ɲ�ѯ�����������Ϣ��ҵ��ʹ�á�
';

comment on column C_METER.METER_ID is
'��ʵ���¼��Ψһ��ʶ�ţ�ȡ��Ӫ���豸��ĵ��ܱ���Ϣʵ�塣';

comment on column C_METER.ASSET_NO is
'�ʲ����';

comment on column C_METER.INST_LOC is
'���ܱ�װ������λ��';

comment on column C_METER.INST_DATE is
'��װ����';

comment on column C_METER.T_FACTOR is
'���ܱ��ۺϱ���=���ܱ�������*ͬһ�������µ����������ı���*ͬһ�������µ�ѹ�������ı���';

comment on column C_METER.REF_METER_FLAG is
'�������ܱ��Ƿ��ǲο�������01�ǡ�02��';

comment on column C_METER.REF_METER_ID is
'������ǰ���ܱ����ڽ�����;ʱ���ο��ǿ���ܱ�������㡣';

comment on column C_METER.MODULE_NO is
'������ܱ��Ǽ���ʱ����Ӧ��ģ����';

comment on column C_METER.ORG_ID is
'�������λ�Ĵ���';

comment on column C_METER.COMM_NO is
'ͨѶ��Լ��645����';

comment on column C_METER.BAUDRATE is
'���ܱ�Ĳ�����';

comment on column C_METER.COMM_MODE is
'ͨѶ��ʽ��485��gprs�����⡭��';

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
   METER_ID             NUMBER(16)                      not null,
   constraint PK_C_MP primary key (MP_ID)
)
tablespace TABS_ARCHIVE;

comment on table C_MP is
'1)���ڼ�¼��Ҫ��װ����װ�õ�λ�õ����Ϣ�����Խ��һ����������������ֱ�ʹ�ã���ʱ�����㶨������������Խ��������������һ�������Ĺ��ܣ���ʱ�����㶨���һ�������Խ�����������⣬��ʱ��������Զ����һ����
�����˼��������Ȼ���ԣ���ʵ����Ҫ�����������š����������ơ��������ַ����������ࡢ���������ʵ����ԡ�
2)ͨ����װ�����ݼ�����õ�鵵���ؿڼ�������װ������鵵��ҵ����ʵ��ת�������¼��
3)��ʵ����Ҫ�ɲ�ѯ�����������Ϣ��ҵ��ʹ�á�';

comment on column C_MP.MP_ID is
'���������ļ�����Ψһ��ʶ��';

comment on column C_MP.MP_NO is
'�û��Լ���д�ı�ţ�Ĭ�ϸ���ʶһ��';

comment on column C_MP.MP_NAME is
'����������';

comment on column C_MP.MP_ADDR is
'�������ַ';

comment on column C_MP.TYPE_CODE is
'������������Ҫ���࣬������01�õ�ͻ���02�ؿڵ�';

comment on column C_MP.MP_ATTR_CODE is
'������������Ҫ���ʣ�������01���㡢02���˵�';

comment on column C_MP.USAGE_TYPE_CODE is
'������������Ҫ��;�����ù��ҵ�����˾Ӫ����������༯:5110.19���ܼ��������ͷ�������루01�۵����㡢02̨�����翼�ˡ�03��·���翼�ˡ�04ָ�������05���۹���ؿڡ�06���й���ؿڡ�07ʡ������ؿڡ�08��ʡ���ؿڡ�09�������ؿڡ�10������ؿڡ�11���������ؿ�......��';

comment on column C_MP.SIDE_CODE is
'���������������ľ���λ�ã�������01���վ�ڡ�02���վ�⡢03��ѹ�ࡢ04��ѹ���';

comment on column C_MP.VOLT_CODE is
'����������ĵ�ѹ�ȼ������ù��ҵ�����˾��Ϣ�����������ϵ���ۺϴ����༯����ѹ�ȼ������������01 10KV��02 110KV��03 220KV��04 35KV��05 220V��06 6KV��07 380V��08 500KV��';

comment on column C_MP.APP_DATE is
'������������������������';

comment on column C_MP.RUN_DATE is
'Ͷ������';

comment on column C_MP.WIRING_MODE is
'���ù��ҵ�����˾Ӫ����������༯:5110.84���ܱ���߷�ʽ��������루1���ࡢ2�������ߡ�3�������ߣ�';

comment on column C_MP.MEAS_MODE is
'���ù��ҵ�����˾Ӫ����������༯:5110.33���ܼ�����ʽ���루1�߹��߼ơ�2�߹��ͼơ�3�͹��ͼƣ�';

comment on column C_MP.ORG_ID is
'���絥λ���룬һ����ָ���û���ֱ�ӹ������λ��Ҳ�����Ǵ�ͻ��������ĵ����ڹ���ԭ������Ŀͻ�����λ';

comment on column C_MP.SWITCH_NO is
'���������������Ŀ��ر��';

comment on column C_MP.MR_SECT_NO is
'���������������ĳ���α��';

comment on column C_MP.TG_ID is
'̨����Ψһ��ʶ';

comment on column C_MP.EXCHG_TYPE_CODE is
'����������ĵ����������󣬰�����01������ҵ��02���������03ʡ����ҵ��04������ҵ��05���۵�λ��';

comment on column C_MP.MD_TYPE_CODE is
'���ù��ҵ�����˾Ӫ����������༯:5110.32���ܼ���װ�÷�������루1�������װ�á�2�������װ�á�3�������װ�á�4�������װ�á�5�������װ��......��';

comment on column C_MP.MR_SN is
'���������ĳ���˳��';

comment on column C_MP.METER_FLAG is
'�����������Ƿ�װ����װ�ã�������01�ǡ�02��';

comment on column C_MP.STATUS_CODE is
'����������ĵ�ǰ״̬��������01������02���á�03ͣ�á�04������';

comment on column C_MP.LC_FLAG is
'�Ƿ�װ�����豸��01�ǡ�02��';

comment on column C_MP.CONS_ID is
'�û���ʶ';

comment on column C_MP.LASTTIME_STAMP is
'����ṹ�޸�ʱ���';

comment on column C_MP.METER_ID is
'��ʵ���¼��Ψһ��ʶ�ţ�ȡ��Ӫ���豸��ĵ��ܱ���Ϣʵ�塣';

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
'1)���ڼ�¼��������;����Ϣ������������ж�����;�������۵����㡢̨�����翼�ˡ����������ؿڡ�������ؿڵȡ������˼���������;��Ķ�Ӧ��ϵ���ԣ���ʵ����Ҫ������;ID���������š���;��������ԡ�
2)ͨ����װ�����ݼ�����õ�鵵���ؿڼ�������װ������鵵��ҵ����ʵ��ת�������¼��
3)��ʵ����Ҫ�ɲ�ѯ�����������Ϣ��ҵ��ʹ�á�';

comment on column C_MP_USE.USAGE_ID is
'��ʵ���¼��Ψһ��ʶ��';

comment on column C_MP_USE.MP_ID is
'���������ļ�����Ψһ��ʶ��';

comment on column C_MP_USE.USAGE_CODE is
'���ù��ҵ�����˾Ӫ����������༯:5110.19���ܼ��������ͷ�������루01�۵����㡢02̨�����翼�ˡ�03��·���翼�ˡ�04ָ�������05���۹���ؿڡ�06���й���ؿڡ�07ʡ������ؿڡ�08��ʡ���ؿڡ�09�������ؿڡ�10������ؿڡ�11���������ؿ�......��';

/*==============================================================*/
/* Table: C_PS                                                  */
/*==============================================================*/
create table C_PS  (
   PS_ID                NUMBER                          not null,
   TERM_ID              NUMBER,
   GP_ID                NUMBER,
   ASSET_NO             VARCHAR2(20),
   MODEL_CODE           VARCHAR2(5),
   COMM_MODE_GM         VARCHAR2(5),
   BTL                  VARCHAR2(5),
   RATED_EC             VARCHAR2(5),
   REMC_GEAR            VARCHAR2(5),
   REMC_GEAR_VALUE      VARCHAR2(5),
   OFF_DELAY_GEAR       VARCHAR2(5),
   OFF_DELAY_VALUE      VARCHAR2(5),
   FUNCTION_CODE        VARCHAR2(8),
   PS_TYPE              VARCHAR2(5),
   LASTTIME_STAMP       DATE                           default SYSDATE,
   PINYIN_CODE          VARCHAR2(16),
   constraint PK_C_PS primary key (PS_ID)
)
tablespace TABS_ARCHIVE;

comment on table C_PS is
'©�㱣������(leakage protection switch)';

comment on column C_PS.ASSET_NO is
'NOT NULL';

comment on column C_PS.MODEL_CODE is
'©���ͺ� : ������PS_MODEL';

comment on column C_PS.COMM_MODE_GM is
'ͨѶ��ʽ : ������COMM_MODE_GM';

comment on column C_PS.BTL is
'������BTL';

comment on column C_PS.RATED_EC is
'������RATED_EC';

comment on column C_PS.REMC_GEAR is
'������REM_EC';

comment on column C_PS.OFF_DELAY_GEAR is
'������OFF_DELAY_VALUE';

comment on column C_PS.PS_TYPE is
'��code PS_TYPE  1���ܱ���2��������';

comment on column C_PS.LASTTIME_STAMP is
'����ṹ�޸�ʱ���';

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
'������RUN_STATUS';

comment on column C_TERMINAL.CUR_STATUS is
'������CUR_STATUS';

comment on column C_TERMINAL.TERM_TYPE is
'������TERM_TYPE';

comment on column C_TERMINAL.WIRING_MODE is
'������WIRING_MODE';

comment on column C_TERMINAL.MODEL_CODE is
'�ն��ͺ� : ������TERM_MODEL';

comment on column C_TERMINAL.MADE_FAC is
'������MADE_FAC';

comment on column C_TERMINAL.COMM_MODE is
'������COMM_MODE';

comment on column C_TERMINAL.CHANNEL_TYPE is
'������CHANNEL_TYPE';

comment on column C_TERMINAL.PROTOCOL_NO is
'�ն˹�Լ�ţ�������PROTOCOL_TERM';

comment on column C_TERMINAL.PR is
'������PR';

comment on column C_TERMINAL.ISAC is
'1 - �ӽ��� 0 - ���ӽ���';

comment on column C_TERMINAL.MACH_NO is
'���豸������Ϣ';

comment on column C_TERMINAL.COMM_PATTERN is
'1������2����';

comment on column C_TERMINAL.LASTTIME_STAMP is
'����ṹ�޸�ʱ���';

/*==============================================================*/
/* Table: C_TERM_OBJ_RELA                                       */
/*==============================================================*/
create table C_TERM_OBJ_RELA  (
   TERM_OBJ_ID          NUMBER                          not null,
   TERM_ID              NUMBER,
   OBJ_TYPE             VARCHAR(8),
   OBJ_ID               NUMBER,
   constraint PK_C_TERM_OBJ_RELA primary key (TERM_OBJ_ID)
)
tablespace TABS_ARCHIVE;

alter table C_GP
   add constraint FK_C_GP_C_GP_CTER_C_TERMIN foreign key (TERM_ID)
      references C_TERMINAL (TERM_ID);

alter table C_GP
   add constraint FK_C_GP_C_MP_C_GP_C_MP foreign key (MP_ID)
      references C_MP (MP_ID);

alter table C_MP
   add constraint FK_C_MP_C_MP_C_CO_C_CONS foreign key (CONS_ID)
      references C_CONS (CONS_ID);

alter table C_MP
   add constraint FK_C_MP_C_MP_C_ME_C_METER foreign key (METER_ID)
      references C_METER (METER_ID);

alter table C_MP_USE
   add constraint FK_C_MP_USE_C_MP_C_MP_C_MP foreign key (MP_ID)
      references C_MP (MP_ID);

alter table C_PS
   add constraint FK_C_PS_C_GP_C_PS_C_GP foreign key (GP_ID)
      references C_GP (GP_ID);

alter table C_PS
   add constraint FK_C_PS_C_TERMINA_C_TERMIN foreign key (TERM_ID)
      references C_TERMINAL (TERM_ID);

alter table C_TERM_OBJ_RELA
   add constraint FK_C_TERM_C_TERM_OBJ_RELA foreign key (TERM_ID)
      references C_TERMINAL (TERM_ID);

