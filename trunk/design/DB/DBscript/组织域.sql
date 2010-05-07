/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2010-5-7 15:55:42                            */
/*==============================================================*/



-- Type package declaration
create or replace package PDTypes  
as
    TYPE ref_cursor IS REF CURSOR;
end;

-- Integrity package declaration
create or replace package IntegrityPackage AS
 procedure InitNestLevel;
 function GetNestLevel return number;
 procedure NextNestLevel;
 procedure PreviousNestLevel;
 end IntegrityPackage;
/

-- Integrity package definition
create or replace package body IntegrityPackage AS
 NestLevel number;

-- Procedure to initialize the trigger nest level
 procedure InitNestLevel is
 begin
 NestLevel := 0;
 end;


-- Function to return the trigger nest level
 function GetNestLevel return number is
 begin
 if NestLevel is null then
     NestLevel := 0;
 end if;
 return(NestLevel);
 end;

-- Procedure to increase the trigger nest level
 procedure NextNestLevel is
 begin
 if NestLevel is null then
     NestLevel := 0;
 end if;
 NestLevel := NestLevel + 1;
 end;

-- Procedure to decrease the trigger nest level
 procedure PreviousNestLevel is
 begin
 NestLevel := NestLevel - 1;
 end;

 end IntegrityPackage;
/


drop trigger "tib_o_role"
/

drop trigger "tib_o_staff"
/

drop trigger "tib_o_user_role"
/

alter table O_DEPT
   drop constraint FK_O_DEPT_OG_COMPAN_O_ORG
/

alter table O_STAFF
   drop constraint FK_O_STAFF_OG_DEPT_P_O_DEPT
/

alter table O_USER_ROLE
   drop constraint FK_O_USER_R_OG_ROLE_E_O_ROLE
/

alter table O_USER_ROLE
   drop constraint FK_O_USER_R_OR_EMP_RO_O_STAFF
/

drop table O_DEPT cascade constraints
/

drop table O_ORG cascade constraints
/

drop table O_ROLE cascade constraints
/

drop table O_STAFF cascade constraints
/

drop table O_USER_ROLE cascade constraints
/

drop sequence SEQ_O_ROLE
/

drop sequence SEQ_O_STAFF
/

drop sequence SEQ_O_USER_ROLE
/

create sequence SEQ_O_ROLE
increment by 1
start with 1
 maxvalue 99999999
 minvalue 1
 cache 20
/

create sequence SEQ_O_STAFF
increment by 1
start with 1
 minvalue 1
cache 20
nocache
 maxvalue 99999999
/

create sequence SEQ_O_USER_ROLE
increment by 1
start with 1
 minvalue 1
nocycle
 cache 20
 maxvalue 99999999
/

/*==============================================================*/
/* Table: O_DEPT                                                */
/*==============================================================*/
create table O_DEPT  (
   DEPT_NO              VARCHAR2(16)                    not null,
   ORG_NO               VARCHAR2(16)                    not null,
   ABBR                 VARCHAR2(256),
   NAME                 VARCHAR2(256),
   TYPE_CODE            VARCHAR2(8),
   P_DEPT_NO            VARCHAR2(16),
   DISP_SN              NUMBER(5),
   LASTTIME_STAMP       DATE                           default SYSDATE,
   constraint PK_O_DEPT primary key (DEPT_NO)
)
tablespace TABS_ARCHIVE
/

comment on table O_DEPT is
'1) ����Ϊ���絥λ�еľ��岿�ţ����ҡ�����ȡ��磺����ࡢӪҵ����������õ������������ʵ�����ת��Ϊ��ͼ��������������ʵ�����á���ʵ����Ҫ�������ű�ʶ���������ơ��������͡��ϼ����ű�ʶ�����ԡ�
2) ͨ�����Ź�����¼�������¼��
'
/

comment on column O_DEPT.DEPT_NO is
'��ʵ���¼��Ψһ��ʶ���������ŵ�Ψһ���롣'
/

comment on column O_DEPT.ORG_NO is
'��ʵ���¼��Ψһ��ʶ���������絥λ��Ψһ���롣'
/

comment on column O_DEPT.ABBR is
'�������Ƶ���д��'
/

comment on column O_DEPT.NAME is
'���ŵ���ϸ���ơ�'
/

comment on column O_DEPT.TYPE_CODE is
'���ŵ����ͣ������ṩ������ѡ��ʹ�ã����㲿�Ź��ˡ�����ö�������ԣ���Ҫ������01 ����ࡢ02 Ӫҵ����03 ����ࡢ04 ��Ѱࡢ05 �����ࡣ'
/

comment on column O_DEPT.P_DEPT_NO is
'ֱ���ϼ����ŵĲ��ű�š�'
/

comment on column O_DEPT.DISP_SN is
'��������չ��ʱ����ʾ˳��š�'
/

/*==============================================================*/
/* Table: O_ORG                                                 */
/*==============================================================*/
create table O_ORG  (
   ORG_NO               VARCHAR2(16)                    not null,
   ORG_NAME             VARCHAR2(256),
   P_ORG_NO             VARCHAR2(16),
   ORG_TYPE             VARCHAR2(8),
   SORT_NO              NUMBER(5),
   LASTTIME_STAMP       DATE                           default SYSDATE,
   constraint PK_O_ORG primary key (ORG_NO)
)
tablespace TABS_ARCHIVE
/

comment on column O_ORG.ORG_NO is
'��ʵ���¼��Ψһ��ʶ�� �������絥λ��
Ψһ����
'
/

comment on column O_ORG.ORG_NAME is
'���絥λ��ϸ������'
/

comment on column O_ORG.P_ORG_NO is
'ֱ���ϼ����絥λ���'
/

comment on column O_ORG.ORG_TYPE is
'��λ��𣺹�����˾�� ʡ��˾�����й�˾�����ع�˾���ֹ�˾�� �������ȡ�01 ������˾�� 02 ʡ��˾�� 03 ���й�˾ �� 04 ���ع�˾�� 05 �ֹ�˾�� 06 ��������'
/

comment on column O_ORG.SORT_NO is
'��ͬ���е�����˳�����ţ� ����Ȼ����
ʶ���磬 1 �� 2 �� 3 �� 
'
/

comment on column O_ORG.LASTTIME_STAMP is
'����ṹ�޸�ʱ���'
/

/*==============================================================*/
/* Table: O_ROLE                                                */
/*==============================================================*/
create table O_ROLE  (
   ROLE_ID              NUMBER                          not null,
   ROLE_NAME            VARCHAR2(50)                    not null,
   ROLE_REMARK          VARCHAR2(256),
   ROLE_TYPE            VARCHAR2(5),
   CREATOR              VARCHAR2(20),
   CREATTIME            DATE,
   LASTTIME_STAMP       DATE                           default SYSDATE,
   constraint PK_O_ROLE primary key (ROLE_ID)
)
tablespace TABS_ARCHIVE
/

comment on column O_ROLE.ROLE_ID is
'PK��������SEQ_ROLE����'
/

comment on column O_ROLE.ROLE_TYPE is
'������ROLE_TYPE
1 - ��λ��ɫ����Ҫ��ӦӦ�ù��ܣ� 
2 - ����Ȩ�ޣ��絵����ѯ������ά���� 
3 - ϵͳ�������ܲ���ϵͳ����
'
/

comment on column O_ROLE.CREATOR is
'�����ý�ɫ�Ĳ���Ա��ţ�EMP_NO ��'
/

comment on column O_ROLE.LASTTIME_STAMP is
'����ṹ�޸�ʱ���'
/

/*==============================================================*/
/* Table: O_STAFF                                               */
/*==============================================================*/
create table O_STAFF  (
   EMP_NO               NUMBER(16)                      not null,
   DEPT_NO              VARCHAR2(16),
   STAFF_NO             VARCHAR2(16),
   PASSWD               VARCHAR2(50),
   NAME                 VARCHAR2(64),
   GENDER               VARCHAR2(8),
   POS_NAME             VARCHAR2(256),
   POSITION             VARCHAR2(8),
   WORK_TYPE_CODE       VARCHAR2(8),
   MOBILE               VARCHAR2(32),
   REMARK               VARCHAR2(256),
   LASTTIME_STAMP       DATE                           default SYSDATE,
   constraint PK_O_STAFF primary key (EMP_NO)
)
tablespace TABS_ARCHIVE
/

comment on table O_STAFF is
'����Ա'
/

comment on column O_STAFF.EMP_NO is
'��ʵ���¼��Ψһ��ʶ'
/

comment on column O_STAFF.DEPT_NO is
'��ʵ���¼��Ψһ��ʶ�� �������ŵ�Ψһ����'
/

comment on column O_STAFF.STAFF_NO is
'���ţ� Ӫ��ҵ����Ա�ķ��񹤺�'
/

comment on column O_STAFF.NAME is
'ҵ����Ա���� '
/

comment on column O_STAFF.GENDER is
'�Ա� 01 �С� 02 Ů'
/

comment on column O_STAFF.POS_NAME is
'����ְλ����'
/

comment on column O_STAFF.POSITION is
'��Ա���ڸ�λ����'
/

comment on column O_STAFF.WORK_TYPE_CODE is
'�����ֹ����ࣺ 01 �춨��Ա�� 02 ��У��Ա�� 03 װ��ӵ�'
/

comment on column O_STAFF.MOBILE is
'��Ա��ϵ�ֻ�����'
/

comment on column O_STAFF.REMARK is
'��Ա������˵�� '
/

comment on column O_STAFF.LASTTIME_STAMP is
'����ṹ�޸�ʱ���'
/

/*==============================================================*/
/* Table: O_USER_ROLE                                           */
/*==============================================================*/
create table O_USER_ROLE  (
   EMP_ROLE_ID          NUMBER                          not null,
   EMP_NO               VARCHAR2(16),
   ROLE_ID              NUMBER,
   GRANTABLE            NUMBER,
   LASTTIME_STAMP       DATE                           default SYSDATE,
   constraint PK_O_USER_ROLE primary key (EMP_ROLE_ID)
)
tablespace TABS_ARCHIVE
/

comment on table O_USER_ROLE is
'ά������Ա���ɫ�Ķ�Զ�Ĺ�ϵ'
/

comment on column O_USER_ROLE.EMP_ROLE_ID is
'PK����SEQ_ROLE����'
/

comment on column O_USER_ROLE.EMP_NO is
'��ʵ���¼��Ψһ��ʶ'
/

comment on column O_USER_ROLE.GRANTABLE is
'1 - �����������
0 - �������������
Ĭ��1
'
/

comment on column O_USER_ROLE.LASTTIME_STAMP is
'����ṹ�޸�ʱ���'
/

alter table O_DEPT
   add constraint FK_O_DEPT_OG_COMPAN_O_ORG foreign key (ORG_NO)
      references O_ORG (ORG_NO)
/

alter table O_STAFF
   add constraint FK_O_STAFF_OG_DEPT_P_O_DEPT foreign key (DEPT_NO)
      references O_DEPT (DEPT_NO)
/

alter table O_USER_ROLE
   add constraint FK_O_USER_R_OG_ROLE_E_O_ROLE foreign key (ROLE_ID)
      references O_ROLE (ROLE_ID)
/

alter table O_USER_ROLE
   add constraint FK_O_USER_R_OR_EMP_RO_O_STAFF foreign key (EMP_NO)
      references O_STAFF (EMP_NO)
/


create trigger "tib_o_role" before insert
on O_ROLE for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "ROLE_ID" uses sequence SEQ_O_ROLE
    select SEQ_O_ROLE.NEXTVAL INTO :new.ROLE_ID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger "tib_o_staff" before insert
on O_STAFF for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "EMP_NO" uses sequence SEQ_O_STAFF
    select SEQ_O_STAFF.NEXTVAL INTO :new.EMP_NO from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/


create trigger "tib_o_user_role" before insert
on O_USER_ROLE for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "EMP_ROLE_ID" uses sequence SEQ_O_USER_ROLE
    select SEQ_O_USER_ROLE.NEXTVAL INTO :new.EMP_ROLE_ID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/

