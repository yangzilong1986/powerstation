prompt PL/SQL Developer import file
prompt Created on 2010��6��8�� by Administrator
set feedback off
set define off
prompt Disabling triggers for O_ORG...
alter table O_ORG disable all triggers;
prompt Deleting O_ORG...
delete from O_ORG;
commit;
prompt Loading O_ORG...
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (4, '01', 'տ�������', '03', '0', 1, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (5, '0101', '�⴨�����', '04', '01', 5, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (32, '010101', '�⴨�г���', '05', '0101', 50, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (40, '01010101', '��׺������', '06', '010101', 5001, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (109, '01010102', '���¹�����', '06', '010101', 5002, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (110, '01010103', '�⴨�ؿڱ�', '06', '010101', 5003, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (112, '01010104', '��᪹�����', '06', '010101', 5004, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (173, '01010105', 'ǳˮ������', '06', '010101', 5005, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (175, '01010106', '��ʯ������', '06', '010101', 5006, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (176, '01010107', '����۹�����', '06', '010101', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (177, '01010108', '���͹�����', '06', '010101', 5008, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (178, '01010109', '��ɽ��������(��)', '06', '010101', 5009, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (179, '01010110', '��β������(��)', '06', '010101', 5010, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (180, '01010111', '����������', '06', '010101', 5011, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (181, '01010112', '���Ĺ�����', '06', '010101', 5012, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (182, '01010113', '���̹�����', '06', '010101', 5013, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (183, '01010114', '���¹��������ɣ�', '06', '010101', 5014, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (184, '01010115', '��ɽ������(��)', '06', '010101', 5015, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (185, '01010116', '��׺���������ɣ�', '06', '010101', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (186, '01010117', '���Ź�����(��)', '06', '010101', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (187, '01010118', 'Ӫҵһ��', '06', '010101', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (188, '01010119', '÷¼�ڶ�������(��)', '06', '010101', 5019, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (189, '01010120', '���̹�����(��)', '06', '010101', 5020, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (190, '01010121', 'Ӫҵ����', '06', '010101', 5021, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (191, '01010122', 'Ӫҵ����', '06', '010101', 5022, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (6, '0102', 'տ��������', '04', '01', 9, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (15, '010201', '����(����)', '05', '0102', 90, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (172, '01020101', '���ݹ����->����', '06', '010201', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (23, '010202', '����(����)', '05', '0102', 91, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (142, '01020201', '���������->����', '06', '010202', 9101, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (31, '010203', '�⴨(����)', '05', '0102', 92, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (111, '01020301', '�⴨�����->����', '06', '010203', 9201, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (7, '0103', 'տ���г���', '04', '01', 8, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (13, '010301', 'տ���г�����', '05', '0103', 80, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (33, '01030101', 'տ���г����󻧰�', '05', '010301', 8001, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (8, '0104', '���ݹ����', '04', '01', 7, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (17, '010401', '�����г���', '05', '0104', 70, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (143, '01040101', '�õ��ֱ��', '06', '010401', 7001, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (144, '01040102', '������Ӫ����', '06', '010401', 7002, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (145, '01040103', '������Ӫ����', '06', '010401', 7003, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (146, '01040104', '���ǹ�����', '06', '010401', 7004, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (147, '01040105', '��ɳ������', '06', '010401', 7005, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (148, '01040106', '����������', '06', '010401', 7006, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (149, '01040107', '��·������', '06', '010401', 7007, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (150, '01040108', '��ҹ�����', '06', '010401', 7008, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (151, '01040109', '�Ƽҹ�����', '06', '010401', 7009, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (152, '01040110', '�ͼҹ�����', '06', '010401', 7010, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (153, '01040111', '��ˮ������', '06', '010401', 7011, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (154, '01040112', '���񹩵���', '06', '010401', 7012, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (155, '01040113', '���Ź�����', '06', '010401', 7013, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (156, '01040114', '���͹�����', '06', '010401', 7014, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (157, '01040115', '����������', '06', '010401', 7015, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (159, '01040117', '���繩����', '06', '010401', 7016, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (160, '01040118', '�׸߹�����', '06', '010401', 7017, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (161, '01040119', '���﹩����', '06', '010401', 7018, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (162, '01040120', '��ʯ������', '06', '010401', 7019, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (163, '01040121', '���¹�����', '06', '010401', 7020, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (164, '01040122', '������ũ����', '06', '010401', 7021, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (165, '01040123', '��湩����', '06', '010401', 7022, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (166, '01040124', '�Ҹ�ũ����', '06', '010401', 7023, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (167, '01040125', '���ǹ�����', '06', '010401', 7024, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (168, '01040126', '�ջ�ũ����', '06', '010401', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (169, '01040127', '����ũ����', '06', '010401', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (170, '01040128', '�����γ���', '06', '010401', 7027, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (171, '01040129', '���ݹؿڱ�', '06', '010401', 7028, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (174, '01040130', '���˹�����', '06', '010401', 7029, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (377, '01040131', '��ɳ������', '06', '010401', 7030, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (378, '01040132', '���繩����', '06', '010401', 7031, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (379, '01040133', '���Ź�����', '06', '010401', 7032, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (380, '01040134', '�Ƽҹ�����', '06', '010401', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (381, '01040135', '��ʯ������', '06', '010401', 7034, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (382, '01040136', 'Ӣ��������', '06', '010401', 7035, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (9, '0105', 'տ���ֱ���', '04', '01', 2, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (14, '010501', '�࿲��Ӫ��', '05', '0105', 21, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (34, '01050101', '�࿲��Ӫ��һ��', '06', '010501', 2101, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (35, '01050102', '�࿲��Ӫ������', '06', '010501', 2102, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (36, '01050103', '�࿲��Ӫ���ؿڱ�', '06', '010501', 2103, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (16, '010502', '��ͷ��', '05', '0105', 23, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (41, '01050201', '�ϵ�������', '06', '010502', 2301, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (42, '01050202', '��ͷ������', '06', '010502', 2302, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (43, '01050203', '��ͷ������', '06', '010502', 2303, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (44, '01050204', '�ٶɹ�����', '06', '010502', 2304, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (45, '01050205', 'Ǭ��������', '06', '010502', 2305, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (46, '01050206', '����������', '06', '010502', 2306, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (57, '01050207', '��ͷ�ֹؿڱ�', '06', '010502', 2307, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (18, '010503', 'ϼɽ��Ӫ��', '05', '0105', 20, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (37, '01050301', 'ϼɽ��Ӫ��һ��', '06', '010503', 2001, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (38, '01050302', 'ϼɽ��Ӫ������', '06', '010503', 2002, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (39, '01050303', 'ϼɽ��Ӫ���ؿڱ�', '06', '010503', 2003, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (19, '010504', '������', '05', '0105', 24, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (50, '01050401', '�񰲹�����', '06', '010504', 2401, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (51, '01050402', '̫ƽ������', '06', '010504', 2402, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (52, '01050403', '��ɽ������', '06', '010504', 2403, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (53, '01050404', '���޹�����', '06', '010504', 2404, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (54, '01050405', '���򹩵���', '06', '010504', 2405, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (58, '01050406', '�����ֹؿڱ�', '06', '010504', 2406, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (20, '010505', '���¾�', '05', '0105', 22, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (48, '01050501', '���¹�����', '06', '010505', 2201, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (49, '01050502', '���⹩����', '06', '010505', 2202, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (55, '01050503', '־��������', '06', '010505', 2203, to_date('11-11-2001', 'dd-mm-yyyy'));
commit;
prompt 100 records committed...
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (59, '01050504', '���¾ֹؿڱ�', '06', '010505', 2204, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (21, '010506', 'տ��ũ�繫˾', '05', '0105', 25, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (47, '01050601', 'ũ���', '06', '010506', 2501, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (56, '01050602', 'տ��ũ�繫˾�ؿڱ�', '06', '010506', 2502, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (10, '0106', '��Ϫ�����', '04', '01', 3, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (22, '010601', '��Ϫ�г���', '05', '0106', 30, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (60, '01060101', '��Ϫ�г����ͻ�����(��)', '06', '010601', 3001, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (65, '01060102', '���¹�����', '06', '010601', 3002, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (69, '01060103', '���¹�����', '06', '010601', 3003, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (71, '01060104', '��ͷ������', '06', '010601', 3004, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (72, '01060105', '���񹩵���', '06', '010601', 3005, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (73, '01060106', '���¹�����', '06', '010601', 3006, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (74, '01060107', '��̹�����', '06', '010601', 3007, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (76, '01060108', '���Ź�����', '06', '010601', 3008, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (77, '01060109', '�뱱������', '06', '010601', 3009, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (78, '01060110', '���鹩����', '06', '010601', 3010, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (79, '01060111', '����������', '06', '010601', 3011, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (81, '01060112', '���Թ�����', '06', '010601', 3012, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (82, '01060113', '���๩����', '06', '010601', 3013, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (83, '01060114', '���ڹ�����', '06', '010601', 3014, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (84, '01060115', '��̶������', '06', '010601', 3015, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (85, '01060116', '��ǹ�����', '06', '010601', 3016, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (86, '01060117', '��Ϫũ��ؿ���', '06', '010601', 3017, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (87, '01060118', '��Ϫ��Ӫ�ؿ���', '06', '010601', 3018, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (88, '01060119', '��Ϫ��Ӫ��ֱ��', '06', '010601', 3019, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (24, '010602', '��Ϫ�г�����(��)', '05', '0106', 31, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (25, '010603', '��Ϫ���Ӫҵ��(��)', '05', '0106', 32, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (26, '010604', '��Ϫũ�繫˾(��)', '05', '0106', 33, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (61, '01060401', '���ǹ�����(��)', '06', '010604', 3301, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (62, '01060402', '����������(��)', '06', '010604', 3302, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (63, '01060403', '���Թ��������ɣ�', '06', '010604', 3303, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (64, '01060404', '��̶���������ɣ�', '06', '010604', 3304, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (66, '01060405', '���๩�������ɣ�', '06', '010604', 3305, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (67, '01060406', '��̶������(��)', '06', '010604', 3306, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (68, '01060407', '���ڹ��������ɣ�', '06', '010604', 3307, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (70, '01060408', '��¼������(��)', '06', '010604', 3308, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (75, '01060409', 'ɳ�Ź�����(��)', '06', '010604', 3309, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (80, '01060410', '��ͤӪҵ��(��)', '06', '010604', 3310, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (11, '0107', '���Ź����', '04', '01', 4, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (27, '010701', '�������Ӫҵ��(��)', '05', '0107', 40, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (89, '01070101', '������(��)', '06', '010701', 401, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (29, '010702', '�����г���', '05', '0107', 41, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (90, '01070201', '�Ǳ���', '06', '010702', 4101, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (91, '01070202', '������', '06', '010702', 4102, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (92, '01070203', '������', '06', '010702', 4103, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (93, '01070204', '�Ͱ���', '06', '010702', 4104, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (95, '01070205', '������', '06', '010702', 4105, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (97, '01070206', '��β��', '06', '010702', 4106, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (99, '01070207', '�����', '06', '010702', 4107, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (100, '01070208', '������', '06', '010702', 4108, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (101, '01070209', 'ǰɽ��', '06', '010702', 4109, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (102, '01070210', '������', '06', '010702', 4110, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (103, '01070211', '������', '06', '010702', 4111, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (106, '01070212', '��ɽ��', '06', '010702', 4112, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (107, '01070213', '������', '06', '010702', 4113, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (108, '01070214', '������', '06', '010702', 4114, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (30, '010703', '����ũ�繫˾(��)', '05', '0107', 42, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (94, '01070301', '���������ɣ�', '06', '010703', 4201, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (96, '01070302', '�����(��)', '06', '010703', 4202, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (98, '01070303', '������(��)', '06', '010703', 4203, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (104, '01070304', '������(��)', '06', '010703', 4204, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (105, '01070305', '������(��)', '06', '010703', 4205, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (12, '0108', '���������', '04', '01', 6, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (28, '010801', '�����г���', '05', '0108', 60, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (113, '01080101', '���幩����(��)', '06', '010801', 6001, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (114, '01080102', '������Ӫ����', '06', '010801', 6002, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (115, '01080103', '�Ǳ���Ӫ����', '06', '010801', 6003, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (116, '01080104', 'ʯ�ǹ�����', '06', '010801', 6004, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (117, '01080105', '���̹��������ɣ�', '06', '010801', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (118, '01080106', '��ɽ������', '06', '010801', 6006, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (119, '01080107', '��ˮ������', '06', '010801', 6007, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (120, '01080108', '��弹�����', '06', '010801', 6008, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (121, '01080109', '���񹩵���', '06', '010801', 6009, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (122, '01080110', '����������', '06', '010801', 6010, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (123, '01080111', '��ƽ������', '06', '010801', 6011, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (124, '01080112', '���幩����', '06', '010801', 6012, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (125, '01080113', 'ʯ�빩����(��)', '06', '010801', 6013, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (126, '01080114', '�����������ɣ�', '06', '010801', 6014, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (127, '01080115', 'Ӫ�й�����', '06', '010801', 6015, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (128, '01080116', '��ɽ������', '06', '010801', 6016, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (129, '01080117', '�������', '06', '010801', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (130, '01080118', 'ʯ��������', '06', '010801', 6018, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (131, '01080119', '�ӵ̹�����(��)', '06', '010801', 6019, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (132, '01080120', '���Ź�����', '06', '010801', 6020, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (133, '01080121', '�Ӵ�������', '06', '010801', 6021, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (134, '01080122', 'ƽ̹������(��)', '06', '010801', 6022, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (135, '01080123', '�»�������(��)', '06', '010801', 6023, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (136, '01080124', 'ʯ�Ǳ��վ', '06', '010801', 6024, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (137, '01080125', '��������', '06', '010801', 6025, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (138, '01080126', '���̹�����', '06', '010801', 6026, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (139, '01080127', 'ʯ�빩����', '06', '010801', 6027, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (140, '01080128', '������Ӫ����', '06', '010801', 6028, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (141, '01080129', '�����ؿڱ�', '06', '010801', 6029, to_date('11-11-2001', 'dd-mm-yyyy'));
commit;
prompt 193 records loaded
prompt Enabling triggers for O_ORG...
alter table O_ORG enable all triggers;
set feedback on
set define on
prompt Done.
