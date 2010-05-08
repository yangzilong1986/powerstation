/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2010-5-8 1:08:43                             */
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


drop trigger "tib_r_realtime_task"
/

drop table R_COMMANDITEM cascade constraints
/

drop table R_REALTIME_TASK cascade constraints
/

drop sequence SEQ_R_TASK
/

create sequence SEQ_R_TASK
increment by 1
start with 1
 maxvalue 9999999999
 minvalue 1
 cache 20
/

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
/

comment on table R_COMMANDITEM is
'对不同规约命令项统一编码管理'
/

comment on column R_COMMANDITEM.PROTOCOL_NO is
'见编码PROTOCOL_TERM'
/

comment on column R_COMMANDITEM.GP_CHAR is
'见编码GP_CHAR'
/

comment on column R_COMMANDITEM.WRITE_FLAG is
'1 – 可写 0 – 不可写'
/

comment on column R_COMMANDITEM.READ_FLAG is
'1 – 可读 0 – 不可读'
/

comment on column R_COMMANDITEM.OBJECT_FLAG_PG is
'1 - 支持 0 - 不支持'
/

comment on column R_COMMANDITEM.OBJECT_FLAG_TG is
'1 - 支持 0 - 不支持'
/

comment on column R_COMMANDITEM.OBJECT_FLAG_CUST is
'1 - 支持 0 - 不支持'
/

comment on column R_COMMANDITEM.OBJECT_FLAG_SUBS is
'1 - 支持 0 - 不支持'
/

/*==============================================================*/
/* Table: R_REALTIME_TASK                                       */
/*==============================================================*/
create table R_REALTIME_TASK  (
   TASK_ID              NUMBER                          not null,
   SEQUENCE_CODE        NUMBER                          not null,
   SEND_MSG             VARCHAR2(512),
   RECV_MSG             VARCHAR2(512),
   POST_TIME            DATE                           default SYSDATE,
   TASK_STATUS          VARCHAR2(5)                    default '0',
   constraint PK_R_REALTIME_TASK primary key (TASK_ID)
)
tablespace TABS_INTERACTION
/

comment on column R_REALTIME_TASK.TASK_STATUS is
'任务状态：0：未处理；1：处理中；2：执行成功；3：执行失败'
/


create trigger "tib_r_realtime_task" before insert
on R_REALTIME_TASK for each row
declare
    integrity_error  exception;
    errno            integer;
    errmsg           char(200);
    dummy            integer;
    found            boolean;

begin
    --  Column "TASK_ID" uses sequence SEQ_R_TASK
    select SEQ_R_TASK.NEXTVAL INTO :new.TASK_ID from dual;

--  Errors handling
exception
    when integrity_error then
       raise_application_error(errno, errmsg);
end;
/

