/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2010-6-3 21:31:48                            */
/*==============================================================*/


drop table A_CODE cascade constraints;

drop table A_DB_VERSION cascade constraints;

drop sequence SEQ_A_CODE;

create sequence SEQ_A_CODE
increment by 1
start with 1
 maxvalue 9999999;

/*==============================================================*/
/* Table: A_CODE                                                */
/*==============================================================*/
create table A_CODE  (
   CODE_ID              NUMBER                          not null,
   CODE_CATE            VARCHAR2(20),
   CODE                 VARCHAR2(10),
   NAME                 VARCHAR2(255),
   REMARK               VARCHAR2(255),
   CODE_TYPE            VARCHAR2(5),
   VALUE                VARCHAR2(255),
   constraint PK_A_CODE primary key (CODE_ID)
);

comment on column A_CODE.CODE_TYPE is
'0 – 系统编码
1 – 用户编码
';

/*==============================================================*/
/* Table: A_DB_VERSION                                          */
/*==============================================================*/
create table A_DB_VERSION  (
   OBJ_NAME             VARCHAR2(20)                    not null,
   OBJ_TYPE             Acquisition
                        VARCHAR2(5),
   MOD_TIME             date,
   MOD_MAN              VARCHAR2(20),
   constraint PK_A_DB_VERSION primary key (OBJ_NAME)
)
tablespace TABS_ARCHIVE;

comment on table A_DB_VERSION is
'管理数据对象版本';

comment on column A_DB_VERSION.OBJ_TYPE is
'数据对象类型1：表； 2：序列';

