prompt PL/SQL Developer import file
prompt Created on 2010年6月8日 by Administrator
set feedback off
set define off
prompt Disabling triggers for O_ORG...
alter table O_ORG disable all triggers;
prompt Deleting O_ORG...
delete from O_ORG;
commit;
prompt Loading O_ORG...
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (4, '01', '湛江供电局', '03', '0', 1, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (5, '0101', '吴川供电局', '04', '01', 5, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (32, '010101', '吴川市场部', '05', '0101', 50, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (40, '01010101', '塘缀供电所', '06', '010101', 5001, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (109, '01010102', '黄坡供电所', '06', '010101', 5002, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (110, '01010103', '吴川关口表', '06', '010101', 5003, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (112, '01010104', '长岐供电所', '06', '010101', 5004, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (173, '01010105', '浅水供电所', '06', '010101', 5005, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (175, '01010106', '兰石供电所', '06', '010101', 5006, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (176, '01010107', '王村港供电所', '06', '010101', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (177, '01010108', '覃巴供电所', '06', '010101', 5008, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (178, '01010109', '大山江供电所(旧)', '06', '010101', 5009, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (179, '01010110', '塘尾供电所(旧)', '06', '010101', 5010, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (180, '01010111', '吴阳供电所', '06', '010101', 5011, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (181, '01010112', '振文供电所', '06', '010101', 5012, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (182, '01010113', '樟铺供电所', '06', '010101', 5013, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (183, '01010114', '黄坡供电所（旧）', '06', '010101', 5014, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (184, '01010115', '中山供电所(旧)', '06', '010101', 5015, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (185, '01010116', '塘缀供电所（旧）', '06', '010101', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (186, '01010117', '板桥供电所(旧)', '06', '010101', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (187, '01010118', '营业一班', '06', '010101', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (188, '01010119', '梅录第二供电所(旧)', '06', '010101', 5019, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (189, '01010120', '博铺供电所(旧)', '06', '010101', 5020, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (190, '01010121', '营业二班', '06', '010101', 5021, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (191, '01010122', '营业三班', '06', '010101', 5022, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (6, '0102', '湛江趸售区', '04', '01', 9, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (15, '010201', '雷州(趸售)', '05', '0102', 90, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (172, '01020101', '雷州供电局->趸售', '06', '010201', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (23, '010202', '廉江(趸售)', '05', '0102', 91, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (142, '01020201', '廉江供电局->趸售', '06', '010202', 9101, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (31, '010203', '吴川(趸售)', '05', '0102', 92, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (111, '01020301', '吴川供电局->趸售', '06', '010203', 9201, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (7, '0103', '湛江市场部', '04', '01', 8, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (13, '010301', '湛江市场部大户', '05', '0103', 80, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (33, '01030101', '湛江市场部大户班', '05', '010301', 8001, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (8, '0104', '雷州供电局', '04', '01', 7, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (17, '010401', '雷州市场部', '05', '0104', 70, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (143, '01040101', '用电科直供', '06', '010401', 7001, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (144, '01040102', '城内配营中心', '06', '010401', 7002, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (145, '01040103', '城外配营中心', '06', '010401', 7003, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (146, '01040104', '附城供电所', '06', '010401', 7004, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (147, '01040105', '白沙供电所', '06', '010401', 7005, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (148, '01040106', '沈塘供电所', '06', '010401', 7006, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (149, '01040107', '客路供电所', '06', '010401', 7007, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (150, '01040108', '杨家供电所', '06', '010401', 7008, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (151, '01040109', '唐家供电所', '06', '010401', 7009, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (152, '01040110', '纪家供电所', '06', '010401', 7010, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (153, '01040111', '企水供电所', '06', '010401', 7011, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (154, '01040112', '松竹供电所', '06', '010401', 7012, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (155, '01040113', '龙门供电所', '06', '010401', 7013, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (156, '01040114', '北和供电所', '06', '010401', 7014, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (157, '01040115', '覃斗供电所', '06', '010401', 7015, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (159, '01040117', '调风供电所', '06', '010401', 7016, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (160, '01040118', '雷高供电所', '06', '010401', 7017, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (161, '01040119', '东里供电所', '06', '010401', 7018, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (162, '01040120', '乌石供电所', '06', '010401', 7019, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (163, '01040121', '奋勇供电所', '06', '010401', 7020, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (164, '01040122', '东方红农场所', '06', '010401', 7021, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (165, '01040123', '火炬供电所', '06', '010401', 7022, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (166, '01040124', '幸福农场所', '06', '010401', 7023, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (167, '01040125', '金星供电所', '06', '010401', 7024, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (168, '01040126', '收获农场所', '06', '010401', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (169, '01040127', '马留农场所', '06', '010401', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (170, '01040128', '雷州盐场所', '06', '010401', 7027, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (171, '01040129', '雷州关口表', '06', '010401', 7028, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (174, '01040130', '南兴供电所', '06', '010401', 7029, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (377, '01040131', '白沙供电所', '06', '010401', 7030, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (378, '01040132', '调风供电所', '06', '010401', 7031, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (379, '01040133', '龙门供电所', '06', '010401', 7032, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (380, '01040134', '唐家供电所', '06', '010401', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (381, '01040135', '乌石供电所', '06', '010401', 7034, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (382, '01040136', '英利供电所', '06', '010401', 7035, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (9, '0105', '湛江局本部', '04', '01', 2, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (14, '010501', '赤坎配营部', '05', '0105', 21, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (34, '01050101', '赤坎配营部一班', '06', '010501', 2101, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (35, '01050102', '赤坎配营部二班', '06', '010501', 2102, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (36, '01050103', '赤坎配营部关口表', '06', '010501', 2103, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (16, '010502', '坡头局', '05', '0105', 23, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (41, '01050201', '南调供电所', '06', '010502', 2301, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (42, '01050202', '坡头供电所', '06', '010502', 2302, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (43, '01050203', '龙头供电所', '06', '010502', 2303, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (44, '01050204', '官渡供电所', '06', '010502', 2304, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (45, '01050205', '乾塘供电所', '06', '010502', 2305, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (46, '01050206', '南三供电所', '06', '010502', 2306, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (57, '01050207', '坡头局关口表', '06', '010502', 2307, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (18, '010503', '霞山配营部', '05', '0105', 20, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (37, '01050301', '霞山配营部一班', '06', '010503', 2001, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (38, '01050302', '霞山配营部二班', '06', '010503', 2002, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (39, '01050303', '霞山配营部关口表', '06', '010503', 2003, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (19, '010504', '东海局', '05', '0105', 24, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (50, '01050401', '民安供电所', '06', '010504', 2401, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (51, '01050402', '太平供电所', '06', '010504', 2402, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (52, '01050403', '东山供电所', '06', '010504', 2403, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (53, '01050404', '硇洲供电所', '06', '010504', 2404, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (54, '01050405', '东简供电所', '06', '010504', 2405, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (58, '01050406', '东海局关口表', '06', '010504', 2406, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (20, '010505', '麻章局', '05', '0105', 22, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (48, '01050501', '麻章供电所', '06', '010505', 2201, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (49, '01050502', '湖光供电所', '06', '010505', 2202, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (55, '01050503', '志满供电所', '06', '010505', 2203, to_date('11-11-2001', 'dd-mm-yyyy'));
commit;
prompt 100 records committed...
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (59, '01050504', '麻章局关口表', '06', '010505', 2204, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (21, '010506', '湛江农电公司', '05', '0105', 25, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (47, '01050601', '农电大户', '06', '010506', 2501, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (56, '01050602', '湛江农电公司关口表', '06', '010506', 2502, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (10, '0106', '遂溪供电局', '04', '01', 3, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (22, '010601', '遂溪市场部', '05', '0106', 30, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (60, '01060101', '遂溪市场及客户服务部(大户)', '06', '010601', 3001, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (65, '01060102', '城月供电所', '06', '010601', 3002, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (69, '01060103', '北坡供电所', '06', '010601', 3003, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (71, '01060104', '河头供电所', '06', '010601', 3004, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (72, '01060105', '乐民供电所', '06', '010601', 3005, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (73, '01060106', '建新供电所', '06', '010601', 3006, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (74, '01060107', '杨柑供电所', '06', '010601', 3007, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (76, '01060108', '港门供电所', '06', '010601', 3008, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (77, '01060109', '岭北供电所', '06', '010601', 3009, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (78, '01060110', '江洪供电所', '06', '010601', 3010, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (79, '01060111', '乌塘供电所', '06', '010601', 3011, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (81, '01060112', '黄略供电所', '06', '010601', 3012, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (82, '01060113', '洋青供电所', '06', '010601', 3013, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (83, '01060114', '界炮供电所', '06', '010601', 3014, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (84, '01060115', '草潭供电所', '06', '010601', 3015, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (85, '01060116', '遂城供电所', '06', '010601', 3016, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (86, '01060117', '遂溪农电关口所', '06', '010601', 3017, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (87, '01060118', '遂溪配营关口所', '06', '010601', 3018, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (88, '01060119', '遂溪配营部直管', '06', '010601', 3019, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (24, '010602', '遂溪市场部大户(旧)', '05', '0106', 31, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (25, '010603', '遂溪配电营业部(旧)', '05', '0106', 32, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (26, '010604', '遂溪农电公司(旧)', '05', '0106', 33, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (61, '01060401', '附城供电所(旧)', '06', '010604', 3301, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (62, '01060402', '城西供电所(旧)', '06', '010604', 3302, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (63, '01060403', '黄略供电所（旧）', '06', '010604', 3303, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (64, '01060404', '草潭供电所（旧）', '06', '010604', 3304, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (66, '01060405', '洋青供电所（旧）', '06', '010604', 3305, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (67, '01060406', '北潭供电所(旧)', '06', '010604', 3306, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (68, '01060407', '界炮供电所（旧）', '06', '010604', 3307, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (70, '01060408', '下录供电所(旧)', '06', '010604', 3308, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (75, '01060409', '沙古供电所(旧)', '06', '010604', 3309, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (80, '01060410', '南亭营业厅(旧)', '06', '010604', 3310, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (11, '0107', '徐闻供电局', '04', '01', 4, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (27, '010701', '徐闻配电营业部(旧)', '05', '0107', 40, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (89, '01070101', '城南所(旧)', '06', '010701', 401, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (29, '010702', '徐闻市场部', '05', '0107', 41, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (90, '01070201', '城北所', '06', '010702', 4101, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (91, '01070202', '海安所', '06', '010702', 4102, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (92, '01070203', '下桥所', '06', '010702', 4103, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (93, '01070204', '和安所', '06', '010702', 4104, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (95, '01070205', '西连所', '06', '010702', 4105, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (97, '01070206', '角尾所', '06', '010702', 4106, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (99, '01070207', '新寮所', '06', '010702', 4107, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (100, '01070208', '曲界所', '06', '010702', 4108, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (101, '01070209', '前山所', '06', '010702', 4109, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (102, '01070210', '下洋所', '06', '010702', 4110, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (103, '01070211', '龙塘所', '06', '010702', 4111, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (106, '01070212', '南山所', '06', '010702', 4112, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (107, '01070213', '锦和所', '06', '010702', 4113, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (108, '01070214', '迈陈所', '06', '010702', 4114, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (30, '010703', '徐闻农电公司(旧)', '05', '0107', 42, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (94, '01070301', '迈陈所（旧）', '06', '010703', 4201, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (96, '01070302', '大黄所(旧)', '06', '010703', 4202, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (98, '01070303', '锦和所(旧)', '06', '010703', 4203, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (104, '01070304', '外罗所(旧)', '06', '010703', 4204, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (105, '01070305', '五里所(旧)', '06', '010703', 4205, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (12, '0108', '廉江供电局', '04', '01', 6, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (28, '010801', '廉江市场部', '05', '0108', 60, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (113, '01080101', '龙湾供电所(旧)', '06', '010801', 6001, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (114, '01080102', '廉城配营中心', '06', '010801', 6002, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (115, '01080103', '城北配营中心', '06', '010801', 6003, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (116, '01080104', '石城供电所', '06', '010801', 6004, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (117, '01080105', '安铺供电所（旧）', '06', '010801', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (118, '01080106', '横山供电所', '06', '010801', 6006, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (119, '01080107', '吉水供电所', '06', '010801', 6007, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (120, '01080108', '和寮供电所', '06', '010801', 6008, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (121, '01080109', '新民供电所', '06', '010801', 6009, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (122, '01080110', '雅塘供电所', '06', '010801', 6010, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (123, '01080111', '青平供电所', '06', '010801', 6011, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (124, '01080112', '车板供电所', '06', '010801', 6012, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (125, '01080113', '石岭供电所(旧)', '06', '010801', 6013, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (126, '01080114', '良垌供电所（旧）', '06', '010801', 6014, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (127, '01080115', '营仔供电所', '06', '010801', 6015, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (128, '01080116', '长山供电所', '06', '010801', 6016, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (129, '01080117', '塘蓬供电所', '06', '010801', null, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (130, '01080118', '石颈供电所', '06', '010801', 6018, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (131, '01080119', '河堤供电所(旧)', '06', '010801', 6019, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (132, '01080120', '高桥供电所', '06', '010801', 6020, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (133, '01080121', '河唇供电所', '06', '010801', 6021, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (134, '01080122', '平坦供电所(旧)', '06', '010801', 6022, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (135, '01080123', '新华供电所(旧)', '06', '010801', 6023, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (136, '01080124', '石角变电站', '06', '010801', 6024, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (137, '01080125', '良垌供电所', '06', '010801', 6025, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (138, '01080126', '安铺供电所', '06', '010801', 6026, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (139, '01080127', '石岭供电所', '06', '010801', 6027, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (140, '01080128', '城西配营中心', '06', '010801', 6028, to_date('11-11-2001', 'dd-mm-yyyy'));
insert into O_ORG (ORG_ID, ORG_NO, ORG_NAME, P_ORG_ID, ORG_TYPE, SORT_NO, LASTTIME_STAMP)
values (141, '01080129', '廉江关口表', '06', '010801', 6029, to_date('11-11-2001', 'dd-mm-yyyy'));
commit;
prompt 193 records loaded
prompt Enabling triggers for O_ORG...
alter table O_ORG enable all triggers;
set feedback on
set define on
prompt Done.
