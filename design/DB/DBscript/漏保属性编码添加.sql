
/*漏保型号*/
insert into a_code values(SEQ_A_CODE.NEXTVAL,'PS_MODEL',1,'QLL1-Z(250)','','0','');
insert into a_code values(SEQ_A_CODE.NEXTVAL,'PS_MODEL',2,'QLL1-Z(100)','','0','');
insert into a_code values(SEQ_A_CODE.NEXTVAL,'PS_MODEL',3,'QLL1-Z(400)','','0','');
insert into a_code values(SEQ_A_CODE.NEXTVAL,'PS_MODEL',4,'QLL1-Z(600)','','0','');

/*剩余电流档位*/
insert into a_code values(SEQ_A_CODE.NEXTVAL,'REM_EC',1,'100mA','','0','1');
insert into a_code values(SEQ_A_CODE.NEXTVAL,'REM_EC',2,'300mA','','0','2');
insert into a_code values(SEQ_A_CODE.NEXTVAL,'REM_EC',3,'400mA','','0','3');
insert into a_code values(SEQ_A_CODE.NEXTVAL,'REM_EC',4,'800mA','','0','4');
insert into a_code values(SEQ_A_CODE.NEXTVAL,'REM_EC',5,'自动','','0','5');
/*-漏电分断延迟档位*/
insert into a_code values(SEQ_A_CODE.NEXTVAL,'OFF_DELAY_VALUE',1,'300mS','','0','1');
insert into a_code values(SEQ_A_CODE.NEXTVAL,'OFF_DELAY_VALUE',2,'500mS','','0','2');

/*-漏电类别*/
insert into a_code values(SEQ_A_CODE.NEXTVAL,'PS_TYPE',1,'总保','','0','1');
insert into a_code values(SEQ_A_CODE.NEXTVAL,'PS_TYPE',2,'二级保','','0','2');
commit;
