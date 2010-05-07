/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2010-5-7 15:57:44                            */
/*==============================================================*/


drop table A_DB_VERSION cascade constraints;

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

