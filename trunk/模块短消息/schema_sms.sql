-- Create sequence 
create sequence seq_sms_send
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
cycle;

-- Create table
create table A_SMS_Send
(
  ID         number(10),
  telecode   varchar2(20),
  codec      number(1) default 4 not null,
  msg        varchar2(280),
  status     number(1) default 0 not null,
  insertTime date default sysdate not null,
  tag        varchar2(50)
)
;
-- Add comments to the table 
comment on table A_SMS_Send
  is '发送短消息请求';
-- Add comments to the columns 
comment on column A_SMS_Send.ID
  is 'seq_sms_send.nextval';
comment on column A_SMS_Send.telecode
  is '发送到电话号码';  
comment on column A_SMS_Send.codec
  is '短消息编码:0:西文  4:Bin; 8:GB中文';
comment on column A_SMS_Send.msg
  is '对于英文最多为160字节，对于bin最长为140字节，对于中文最多为70个中文字符';
comment on column A_SMS_Send.status
  is '状态： 0待发 1处理中 2成功发送 3失败';
comment on column A_SMS_Send.insertTime
  is '发送请求时间';
comment on column A_SMS_Send.tag
  is '备注字段，可由请求方用于保存相关信息';
-- Create/Recreate indexes 
create index s_sms_send_not_succ on A_SMS_Send (case when status=2 then null else status end);

-- Create sequence 
create sequence seq_sms_receive
minvalue 1
maxvalue 9999999999
start with 1
increment by 1
cycle;

-- Create table
create table A_SMS_Receive
(
  ID          number(10) not null,
  Telecode    varchar2(20) not null,
  codec       number(1) not null,
  msg         varchar2(280),
  ReceiveTime date default sysdate not null
)
;
-- Add comments to the table 
comment on table A_SMS_Receive
  is '接收到的短消息';
-- Add comments to the columns 
comment on column A_SMS_Receive.ID
  is 'seq_sms_receive.nextval';
comment on column A_SMS_Receive.Telecode
  is '发送方的电话号码';
comment on column A_SMS_Receive.codec
  is '短消息编码:0:西文  4:Bin; 8:GB中文';
comment on column A_SMS_Receive.msg
  is '消息内容';
comment on column A_SMS_Receive.ReceiveTime
  is '接收时间';

create or replace trigger BI_A_SMS_Send
  before insert on a_sms_send  
  for each row
declare
  -- local variables here
begin
  if :new.id is null then
    :new.id := seq_sms_send.nextval;
  end if;
end BI_A_SMS_Send;

create or replace trigger BI_A_SMS_Receive
  before insert on a_sms_receive  
  for each row
declare
  -- local variables here
begin
  if :new.id is null then
    :new.id := seq_sms_receive.nextval;
  end if;
end BI_A_SMS_Receive;
