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
  is '���Ͷ���Ϣ����';
-- Add comments to the columns 
comment on column A_SMS_Send.ID
  is 'seq_sms_send.nextval';
comment on column A_SMS_Send.telecode
  is '���͵��绰����';  
comment on column A_SMS_Send.codec
  is '����Ϣ����:0:����  4:Bin; 8:GB����';
comment on column A_SMS_Send.msg
  is '����Ӣ�����Ϊ160�ֽڣ�����bin�Ϊ140�ֽڣ������������Ϊ70�������ַ�';
comment on column A_SMS_Send.status
  is '״̬�� 0���� 1������ 2�ɹ����� 3ʧ��';
comment on column A_SMS_Send.insertTime
  is '��������ʱ��';
comment on column A_SMS_Send.tag
  is '��ע�ֶΣ������������ڱ��������Ϣ';
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
  is '���յ��Ķ���Ϣ';
-- Add comments to the columns 
comment on column A_SMS_Receive.ID
  is 'seq_sms_receive.nextval';
comment on column A_SMS_Receive.Telecode
  is '���ͷ��ĵ绰����';
comment on column A_SMS_Receive.codec
  is '����Ϣ����:0:����  4:Bin; 8:GB����';
comment on column A_SMS_Receive.msg
  is '��Ϣ����';
comment on column A_SMS_Receive.ReceiveTime
  is '����ʱ��';

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
