prompt PL/SQL Developer import file
prompt Created on 2010年6月8日 by Administrator
set feedback off
set define off
prompt Disabling triggers for A_CODE...
alter table A_CODE disable all triggers;
prompt Deleting A_CODE...
delete from A_CODE;
commit;
prompt Loading A_CODE...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (489, 'GP_TYPE', '1', '专变用户', '测量点类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (490, 'GP_TYPE', '2', '配变/台区', '测量点类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (491, 'GP_TYPE', '3', '低压用户', '测量点类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (492, 'GP_TYPE', '4', '变电站', '测量点类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (820, 'GROSS_SN', '1', '1', '总加组号', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (821, 'GROSS_SN', '2', '2', '总加组号', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (822, 'GROSS_SN', '3', '3', '总加组号', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (823, 'GROSS_SN', '4', '4', '总加组号', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (811, 'GROUP_PROPERTY', '0', '私有', '群组属性', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (812, 'GROUP_PROPERTY', '1', '公有', '群组属性', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (240, 'GROUP_TYPE', '1', '专变群组', '群组类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (241, 'GROUP_TYPE', '2', '配变群组', '群组类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (242, 'GROUP_TYPE', '3', '低压用户群组', '群组类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (243, 'GROUP_TYPE', '4', '终端群组', '群组类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1176, 'GROUP_TYPE', '5', '有序用电群组', '群组类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (311, 'HM_STATIS_SQC', '1', 'A相', '谐波分析 相序', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (312, 'HM_STATIS_SQC', '2', 'B相', '谐波分析 相序', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (313, 'HM_STATIS_SQC', '3', 'C相', '谐波分析 相序', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (309, 'HM_STATIS_TYPE', '1', '电压谐波', '谐波分析 统计类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (310, 'HM_STATIS_TYPE', '2', '电流谐波', '谐波分析 统计类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (564, 'H_POWER_TYPE', '1', '特大高能耗类', '高能耗类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (565, 'H_POWER_TYPE', '2', '大高能耗类', '高能耗类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (566, 'H_POWER_TYPE', '3', '普通高能耗类', '高能耗类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (790, 'INSTAL_CHAR', '1', '专变用户', '安装点性质', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (791, 'INSTAL_CHAR', '2', '公用变', '安装点性质', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (792, 'INSTAL_CHAR', '3', '趸售关口', '安装点性质', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (793, 'INSTAL_CHAR', '4', '电厂上网关口', '安装点性质', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (794, 'INSTAL_CHAR', '5', '网供关口', '安装点性质', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (265, 'IO_TYPE', '1', '输入', '输入输出方向', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (266, 'IO_TYPE', '2', '输出', '输入输出方向', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1082, 'IS_MAS', '1', '主表', '主副表', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1083, 'IS_MAS', '2', '副表', '主副表', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (253, 'IS_MUL_LINE', '1', '单电源', '供电电源', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (254, 'IS_MUL_LINE', '2', '多电源', '供电电源', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (338, 'IS_SPECIAL', '0', '非专线用户', '用户类型', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (337, 'IS_SPECIAL', '1', '专线用户', '用户类型', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (384, 'KPI_TYPE', '01', '负荷管理终端覆盖率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (385, 'KPI_TYPE', '02', '配变监测计量终端覆盖率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (386, 'KPI_TYPE', '03', '厂站终端覆盖率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (387, 'KPI_TYPE', '04', '低压居民用户集抄覆盖率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (388, 'KPI_TYPE', '05', '厂站电能计量点覆盖率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (389, 'KPI_TYPE', '06', '低压居民集抄混合载波方式占比', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (390, 'KPI_TYPE', '07', '受监测大客户负荷比例', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (391, 'KPI_TYPE', '08', '受监控大客户负荷比例', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (392, 'KPI_TYPE', '09', '受监测公变（配变）负荷比例', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (393, 'KPI_TYPE', '10', '负荷管理终端在线率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (394, 'KPI_TYPE', '11', '配变监测终端在线率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (395, 'KPI_TYPE', '12', '厂站电能量采集终端在线率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (396, 'KPI_TYPE', '13', '集中器在线率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (397, 'KPI_TYPE', '14', '负荷管理终端平均在线率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (398, 'KPI_TYPE', '15', '配变监测终端平均在线率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (399, 'KPI_TYPE', '16', '厂站电能量采集终端平均在线率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (400, 'KPI_TYPE', '17', '集中器平均在线率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (401, 'KPI_TYPE', '18', '负荷管理终端采集完整率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (402, 'KPI_TYPE', '19', '配变监测计量终端采集完整率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (403, 'KPI_TYPE', '20', '厂站电能量采集终端采集完整率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (404, 'KPI_TYPE', '21', '集中器采集完整率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (405, 'KPI_TYPE', '22', '负荷管理终端采集正确率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (406, 'KPI_TYPE', '23', '配变监测计量终端采集正确率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (407, 'KPI_TYPE', '24', '厂站电能量采集终端采集正确率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (408, 'KPI_TYPE', '25', '集中器采集正确率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (409, 'KPI_TYPE', '26', '负荷管理终端运行完好率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (410, 'KPI_TYPE', '27', '配变监测计量终端运行完好率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (411, 'KPI_TYPE', '28', '厂站电能量采集终端运行完好率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (412, 'KPI_TYPE', '29', '集中器运行完好率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (413, 'KPI_TYPE', '30', '负荷管理终端控制正确动作率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (414, 'KPI_TYPE', '31', '集中器远程控制正确率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (415, 'KPI_TYPE', '32', '负荷管理终端报警准确率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (416, 'KPI_TYPE', '33', '配变监测计量终端报警准确率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (417, 'KPI_TYPE', '34', '厂站电能量采集终端报警准确率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (418, 'KPI_TYPE', '35', '集中器报警准确率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (419, 'KPI_TYPE', '36', '实现交流采样现场终端率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (420, 'KPI_TYPE', '37', '厂站通信通道完好率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (421, 'KPI_TYPE', '38', '上日整点(重点户)、日冻结抄读成功率(载波)', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (422, 'KPI_TYPE', '39', '上日整点(重点户)、日冻结抄读成功率(485)', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (423, 'KPI_TYPE', '40', '上月冻结抄读成功率(载波)', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (424, 'KPI_TYPE', '41', '上月冻结抄读成功率(485)', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (425, 'KPI_TYPE', '42', '整点(重点户)、日冻结电量数据完整率(载波)', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (426, 'KPI_TYPE', '43', '整点(重点户)、日冻结电量数据完整率(485)', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (427, 'KPI_TYPE', '44', '月冻结电量数据完整率(载波)', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (428, 'KPI_TYPE', '45', '月冻结电量数据完整率(485)', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (429, 'KPI_TYPE', '46', '集中器一次抄读成功率(载波)', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (430, 'KPI_TYPE', '47', '集中器一次抄读成功率(485)', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (431, 'KPI_TYPE', '48', '集中器校时成功率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (432, 'KPI_TYPE', '49', '台区电能表广播校时成功率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (433, 'KPI_TYPE', '50', '负荷管理终端系统数据采集延时', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (434, 'KPI_TYPE', '51', '配变监测计量终端系统数据采集延时', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (435, 'KPI_TYPE', '52', '厂站电能量采集终端系统数据采集延时', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (436, 'KPI_TYPE', '53', '集中器系统数据采集延时', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (437, 'KPI_TYPE', '54', '负荷管理终端主站系统报警延时', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (438, 'KPI_TYPE', '55', '配变监测计量终端主站系统报警延时', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (439, 'KPI_TYPE', '56', '厂站电能量采集终端主站系统报警延时', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (440, 'KPI_TYPE', '57', '集中器主站系统报警延时', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (441, 'KPI_TYPE', '58', '系统响应时间', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (442, 'KPI_TYPE', '59', '主站计算机系统月平均的运行率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (443, 'KPI_TYPE', '60', '日报表月合格率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (444, 'KPI_TYPE', '61', '服务器CPU负载率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (445, 'KPI_TYPE', '62', '服务器网络负载率', '系统指标', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (835, 'LAT_MODE', '11', '通讯方式', '纬度类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (836, 'LAT_MODE', '12', '厂家', '纬度类型', '0', null);
commit;
prompt 100 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (837, 'LAT_MODE', '13', '终端型号', '纬度类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (838, 'LAT_MODE', '14', '终端类型', '纬度类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (839, 'LAT_MODE', '21', '单位（部门）', '纬度类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (840, 'LAT_MODE', '22', '行业', '纬度类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (841, 'LAT_MODE', '23', '线路', '纬度类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (842, 'LAT_MODE', '24', '电压等级', '纬度类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (843, 'LAT_MODE', '25', '容量等级', '纬度类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (107, 'LAT_MODE', '26', '用电类别', '纬度类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (557, 'LAT_MODE', '31', '故障类型', '纬度类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (558, 'LAT_MODE', '41', '变电站', '纬度类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (328, 'LAT_MODE', '42', '台区', '纬度类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (114, 'LAT_MODE_ELECMON', '21', '供电单位', '电压合格率维度方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (116, 'LAT_MODE_ELECMON', '23', '线路', '电压合格率维度方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (115, 'LAT_MODE_ELECMON', '28', '变电站', '电压合格率维度方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (493, 'LINE_STATUS', '01', '投入', '线路状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (494, 'LINE_STATUS', '02', '不投入', '线路状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (495, 'LINE_STATUS', '03', '故障', '线路状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (496, 'LINE_STATUS', '04', '检修', '线路状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (158, 'LINE_TYPE', '1', '馈线', '线路类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (159, 'LINE_TYPE', '2', '联络线', '线路类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (514, 'LOAD_CHAR', '1', '1类', '负荷性质', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (515, 'LOAD_CHAR', '2', '2类', '负荷性质', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (516, 'LOAD_CHAR', '3', '3类', '负荷性质', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (232, 'LOG_TYPE', '1', '控制日志', '日志类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (193, 'LOG_TYPE', '10', '标准代码', '日志类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (378, 'LOG_TYPE', '11', '变更记录删除', '日志类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (233, 'LOG_TYPE', '2', '档案参数变更日志', '日志类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (234, 'LOG_TYPE', '3', '原始用电数据修改', '日志类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (235, 'LOG_TYPE', '4', '权限变更日志', '日志类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (236, 'LOG_TYPE', '5', '系统参数', '日志类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (237, 'LOG_TYPE', '6', '采集管理', '日志类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (238, 'LOG_TYPE', '7', '异常数据修改', '日志类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (239, 'LOG_TYPE', '8', '增值服务日志', '日志类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (42, 'LOG_TYPE', '9', '预付费相关', '日志类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (108, 'MADE_FAC', '1', '北京振中', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (129, 'MADE_FAC', '10', '科立', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (130, 'MADE_FAC', '11', '科陆', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (131, 'MADE_FAC', '12', '兰吉尔', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (135, 'MADE_FAC', '13', '威斯顿', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (136, 'MADE_FAC', '14', '龙电', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (137, 'MADE_FAC', '15', '三星', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (138, 'MADE_FAC', '16', '深圳市南电量子科技发展有限公司', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (139, 'MADE_FAC', '17', '威胜', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (140, 'MADE_FAC', '18', '林洋', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (141, 'MADE_FAC', '19', '西门子', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (120, 'MADE_FAC', '2', '东方电讯', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (165, 'MADE_FAC', '20', '许继', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (166, 'MADE_FAC', '21', '浙江顺舟', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (179, 'MADE_FAC', '22', '卓维', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (180, 'MADE_FAC', '23', 'ABB', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (181, 'MADE_FAC', '24', '红相', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (182, 'MADE_FAC', '25', '华冠', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (183, 'MADE_FAC', '26', '深科技', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (184, 'MADE_FAC', '27', '协同', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (185, 'MADE_FAC', '28', '新联', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (223, 'MADE_FAC', '29', '朗金', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (121, 'MADE_FAC', '3', '佛山东普', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (224, 'MADE_FAC', '30', '山东积成', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (225, 'MADE_FAC', '31', '广州捷宝', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (122, 'MADE_FAC', '4', '广州南方电力集团科技发展有限公司', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (123, 'MADE_FAC', '5', '杭州华立', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (124, 'MADE_FAC', '6', '浩迪', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (126, 'MADE_FAC', '7', '浩宁达', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (127, 'MADE_FAC', '8', '华立', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (128, 'MADE_FAC', '9', '杭州百富电力', '制造厂家', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (546, 'MEAS_MODE', '1', '高供高计', '计量方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (547, 'MEAS_MODE', '2', '高供低计', '计量方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (548, 'MEAS_MODE', '3', '低供低计', '计量方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (815, 'METER_DIGITS', '4', '4', '翻转位数', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (816, 'METER_DIGITS', '5', '5', '翻转位数', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (817, 'METER_DIGITS', '6', '6', '翻转位数', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (818, 'METER_DIGITS', '7', '7', '翻转位数', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (28, 'METER_STATUS', '1', '运行', '电表状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (29, 'METER_STATUS', '2', '停运', '电表状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (30, 'METER_STATUS', '3', '拆除', '电表状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (881, 'METER_TYPE', '1', '远传表', '表类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (882, 'METER_TYPE', '2', '预付费表', '表类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (883, 'METER_TYPE', '3', '卡表', '表类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (884, 'METER_TYPE', '4', '多功能电子表', '表类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (885, 'METER_TYPE', '5', '单项电子表', '表类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (886, 'METER_TYPE', '6', '机械表', '表类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (887, 'METER_TYPE', '7', '简易多功能表', '表类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (500, 'ML_STATUS', '01', '投入', '母线状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (501, 'ML_STATUS', '02', '不投入', '母线状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (502, 'ML_STATUS', '03', '故障', '母线状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (497, 'ML_TYPE', '01', '主母', '母线类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (498, 'ML_TYPE', '02', '副母', '母线类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (499, 'ML_TYPE', '03', '旁母', '母线类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (267, 'MODEL_CODE', '1', 'DD862', '电表型号', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (268, 'MODEL_CODE', '2', 'DT862', '电表型号', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (269, 'MODEL_CODE', '3', 'DTSD855', '电表型号', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (270, 'MODEL_CODE', '4', 'DS862', '电表型号', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (271, 'MODEL_CODE', '5', 'DDSF855', '电表型号', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (272, 'MODEL_CODE', '6', 'DDSY855', '电表型号', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (273, 'MODEL_CODE', '7', 'DDS855', '电表型号', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (256, 'MODEL_TYPE', '1', '台区模型', '模型类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (257, 'MODEL_TYPE', '10', '主网模型', '模型类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (258, 'MODEL_TYPE', '2', '配网馈线模型', '模型类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (259, 'MODEL_TYPE', '3', '全区模型', '模型类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (260, 'MODEL_TYPE', '5', '分压线路模型', '模型类型', '0', null);
commit;
prompt 200 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (261, 'MODEL_TYPE', '6', '母线模型', '模型类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (262, 'MODEL_TYPE', '7', '变压器模型', '模型类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (263, 'MODEL_TYPE', '8', '站损模型', '模型类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (264, 'MODEL_TYPE', '9', '分压模型', '模型类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (103, 'MONITORING_TYPE', '1', 'A类', '监测点类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (104, 'MONITORING_TYPE', '2', 'B类', '监测点类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (105, 'MONITORING_TYPE', '3', 'C类', '监测点类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (106, 'MONITORING_TYPE', '4', 'D类', '监测点类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (44, 'MONITOR_TYPE', '1', 'A类', '监测点类型6~10KV母线电压', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (45, 'MONITOR_TYPE', '2', 'B类', '监测点类型110KV、35KV专线供电的客户110KV、35KV专线供电的客户110KV、35KV专线供电的客户110KV,35KV专线供电客户', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (46, 'MONITOR_TYPE', '3', 'C类', '监测点类型35KV非专线供电用户和10KV供电用户', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (47, 'MONITOR_TYPE', '4', 'D类', '监测点类型380/220V低压网络和用户', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (69, 'MONTH_LIST', '1', '01', '日期', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (60, 'MONTH_LIST', '10', '10', '日期', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (59, 'MONTH_LIST', '11', '11', '日期', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (58, 'MONTH_LIST', '12', '12', '日期', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (68, 'MONTH_LIST', '2', '02', '日期', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (67, 'MONTH_LIST', '3', '03', '日期', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (66, 'MONTH_LIST', '4', '04', '日期', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (65, 'MONTH_LIST', '5', '05', '日期', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (64, 'MONTH_LIST', '6', '06', '日期', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (63, 'MONTH_LIST', '7', '07', '日期', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (62, 'MONTH_LIST', '8', '08', '日期', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (61, 'MONTH_LIST', '9', '09', '日期', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (330, 'MP_ADDR', '1', '主变高压侧', '装表位置', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (331, 'MP_ADDR', '2', '主变中压侧', '装表位置', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (332, 'MP_ADDR', '3', '主变低压侧', '装表位置', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (333, 'MP_ADDR', '4', '线路', '装表位置', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (334, 'MP_ADDR', '5', '站用变', '装表位置', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (156, 'MP_DRCT', '1', '正向', '装表方向', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (157, 'MP_DRCT', '2', '反向', '装表方向', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (446, 'MP_USEAGE', '01', '公线专变', '电表用途', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (447, 'MP_USEAGE', '02', '专线专变', '电表用途', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (448, 'MP_USEAGE', '03', '低压用户', '电表用途', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (449, 'MP_USEAGE', '04', '台区考核', '电表用途', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (450, 'MP_USEAGE', '05', '主变考核', '电表用途', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (451, 'MP_USEAGE', '06', '线路考核', '电表用途', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (452, 'MP_USEAGE', '07', '站用电考核', '电表用途', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (453, 'MP_USEAGE', '08', '发电上网关口', '电表用途', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (454, 'MP_USEAGE', '09', '趸售供电关口', '电表用途', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (455, 'MP_USEAGE', '10', '地市供电关口', '电表用途', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (456, 'MP_USEAGE', '11', '省级供电关口', '电表用途', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (457, 'MP_USEAGE', '12', '网间互供关口表', '电表用途', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (458, 'MP_USEAGE', '13', '电容器组', '电表用途', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (459, 'MP_USEAGE', '14', '旁路计量', '电表用途', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (876, 'MP_USEAGE', '15', '接地变', '电表用途', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (875, 'MP_USEAGE', '16', '电抗器', '电表用途', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (354, 'OBJECT_TYPE', '1', '专变用户', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (363, 'OBJECT_TYPE', '10', '开关', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (364, 'OBJECT_TYPE', '11', '母线', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (365, 'OBJECT_TYPE', '12', '变压器', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (366, 'OBJECT_TYPE', '13', '部门', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (367, 'OBJECT_TYPE', '14', '操作员', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (368, 'OBJECT_TYPE', '15', '角色', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (369, 'OBJECT_TYPE', '16', '功能', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (370, 'OBJECT_TYPE', '17', '菜单', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (371, 'OBJECT_TYPE', '18', '群组', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (372, 'OBJECT_TYPE', '19', '措施', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (355, 'OBJECT_TYPE', '2', '配变', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (373, 'OBJECT_TYPE', '20', '方案', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (374, 'OBJECT_TYPE', '21', '标准代码', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (375, 'OBJECT_TYPE', '22', '系统参数', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (376, 'OBJECT_TYPE', '23', '电表', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (377, 'OBJECT_TYPE', '24', '电压等级', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (356, 'OBJECT_TYPE', '3', '低压用户', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (357, 'OBJECT_TYPE', '4', '变电站', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (358, 'OBJECT_TYPE', '5', '终端', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (359, 'OBJECT_TYPE', '6', '采集器', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (360, 'OBJECT_TYPE', '7', '测量点', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (361, 'OBJECT_TYPE', '8', '馈线', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (362, 'OBJECT_TYPE', '9', '线路', '对象类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (160, 'ORDER_TYPE', '1', '设备故障', '工单类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (161, 'ORDER_TYPE', '2', '异常信息', '工单类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (202, 'ORG_TYPE', '01', '国网公司', '部门类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (203, 'ORG_TYPE', '02', '省公司', '部门类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (204, 'ORG_TYPE', '03', '地市公司', '部门类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (205, 'ORG_TYPE', '04', '区县公司', '部门类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (206, 'ORG_TYPE', '05', '分公司', '部门类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (207, 'ORG_TYPE', '06', '供电所', '部门类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (246, 'PAGE_CNT', '10', '10', '每页行数', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (251, 'PAGE_CNT', '100', '100', '每页行数', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (247, 'PAGE_CNT', '20', '20', '每页行数', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (248, 'PAGE_CNT', '30', '30', '每页行数', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (249, 'PAGE_CNT', '40', '40', '每页行数', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (250, 'PAGE_CNT', '50', '50', '每页行数', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (252, 'PAGE_CNT', '500', '500', '每页行数', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (194, 'PARA_CATE', '1', '终端参数', '参数类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (195, 'PARA_CATE', '2', '测量点参数', '参数类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (228, 'PARA_TYPE', '1', '通信参数', '参数类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (244, 'PARA_TYPE', '10', '电表配置', '参数类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (787, 'PARA_TYPE', '11', '脉冲配置', '参数类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (824, 'PARA_TYPE', '12', '总加组配置', '参数类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (70, 'PARA_TYPE', '14', '任务参数', '参数类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (475, 'PARA_TYPE', '15', '用电参数', '参数类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (476, 'PARA_TYPE', '16', '配置参数', '参数类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (477, 'PARA_TYPE', '17', '级联参数', '参数类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (382, 'PARA_TYPE', '18', '无功补偿参数', '参数类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (335, 'PARA_TYPE', '19', '冻结参数', '参数类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (229, 'PARA_TYPE', '2', '抄表参数', '参数类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (230, 'PARA_TYPE', '3', '事件参数', '参数类型', '0', null);
commit;
prompt 300 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (231, 'PARA_TYPE', '4', '限值参数', '参数类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (819, 'PARA_TYPE', '8', '其他参数', '参数类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (570, 'PARA_TYPE', '9', '终端属性', '参数类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (81, 'PLA_CHAR', '1', '火电厂', '电厂类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (82, 'PLA_CHAR', '2', '核电站', '厂站类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (83, 'PLA_CHAR', '4', '水电站', '厂站类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (84, 'PLA_CHAR', '5', '风电场', '厂站类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (142, 'PLA_CHAR', '6', '小水电站', '厂站类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (783, 'PLUSE_CONSTANT', '0', '正向有功总', '脉冲属性', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (784, 'PLUSE_CONSTANT', '1', '正向无功总', '脉冲属性', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (785, 'PLUSE_CONSTANT', '2', '反向有功总', '脉冲属性', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (786, 'PLUSE_CONSTANT', '3', '反向无功总', '脉冲属性', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (379, 'POWER_FACTOR', '0-100', '0.8', '容量>0,<=100', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (380, 'POWER_FACTOR', '100-160', '0.85', '容量>100,<=160', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (381, 'POWER_FACTOR', '160', '0.9', '容量>160', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (478, 'PR', '1', '局方', '产权', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (479, 'PR', '2', '用户', '产权', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1180, 'PRE_CHARGE_MODE', '0', '电卡', '充值方式', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1181, 'PRE_CHARGE_MODE', '1', '远程', '充值方式', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1177, 'PRE_CHARGE_MODE', '2', 'Token', '充值方式', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1182, 'PRE_CHARGE_TYPE', '0', '电费', '充值类型', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1183, 'PRE_CHARGE_TYPE', '1', '电量', '充值类型', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1178, 'PRE_CRED_NAME', '0', '身份证', '证件名称', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1179, 'PRE_CRED_NAME', '1', '军官证', '证件名称', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1193, 'PRE_CUST_STATUS', '0', '待开户', '用户状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1194, 'PRE_CUST_STATUS', '1', '开户', '用户状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1195, 'PRE_CUST_STATUS', '2', '正常', '用户状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1196, 'PRE_CUST_STATUS', '3', '报停', '用户状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1197, 'PRE_CUST_STATUS', '4', '换表', '用户状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1198, 'PRE_CUST_STATUS', '5', '待销户', '用户状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1199, 'PRE_CUST_STATUS', '6', '销户', '用户状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1207, 'PRE_EQ', '1', '100', '购电电量', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1208, 'PRE_EQ', '2', '200', '购电电量', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1209, 'PRE_EQ', '3', '500', '购电电量', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1210, 'PRE_EQ', '4', '1000', '购电电量', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1211, 'PRE_EQ', '5', '2000', '购电电量', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1212, 'PRE_EQ', '6', '200001', '购电电量', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1191, 'PRE_FIXED_METHOD', '0', '按次数收取', '固定费用收取方式', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1192, 'PRE_FIXED_METHOD', '1', '按金额比例', '固定费用收取方式', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1200, 'PRE_METER_STATUS', '0', '库存', '电表状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1201, 'PRE_METER_STATUS', '1', '运行', '电表状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1202, 'PRE_METER_STATUS', '2', '报停', '电表状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1203, 'PRE_METER_STATUS', '3', '销户', '电表状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1213, 'PRE_MONEY', '1', '100', '购电金额', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1214, 'PRE_MONEY', '2', '200', '购电金额', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1215, 'PRE_MONEY', '3', '600', '购电金额', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1216, 'PRE_MONEY', '4', '1000', '购电金额', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1217, 'PRE_MONEY', '5', '2000', '购电金额', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1218, 'PRE_MONEY', '6', '20000', '购电金额', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1204, 'PRE_PAY_ENTITY', '10', '现金', '支付实体', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1205, 'PRE_PAY_ENTITY', '20', '手机账号', '支付实体', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1206, 'PRE_PAY_ENTITY', '30', '银行账号', '支付实体', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1184, 'PRE_PRICE_TYPE', '0', '单费率', '电价类型', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1185, 'PRE_PRICE_TYPE', '1', '多费率', '电价类型', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1186, 'PRE_STEP_TYPE', '0', '全费率', '阶梯电价类型', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1187, 'PRE_STEP_TYPE', '1', '费率1', '阶梯电价类型', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1188, 'PRE_STEP_TYPE', '2', '费率2', '阶梯电价类型', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1189, 'PRE_STEP_TYPE', '3', '费率3', '阶梯电价类型', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1190, 'PRE_STEP_TYPE', '4', '费率4', '阶梯电价类型', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (37, 'PRI_MASTER', '4', '4', '优先级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (38, 'PRI_MASTER', '5', '5', '优先级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (39, 'PRI_MASTER', '6', '6', '优先级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (40, 'PRI_MASTER', '7', '7', '优先级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (41, 'PRI_MASTER', '8', '8', '优先级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (274, 'PROTOCOL_METER', '10', '全国DLT645规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (275, 'PROTOCOL_METER', '11', '新版645规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (276, 'PROTOCOL_METER', '20', '浙江电表规约A', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (277, 'PROTOCOL_METER', '21', '浙江电表规约B', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (278, 'PROTOCOL_METER', '31', '威胜规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (279, 'PROTOCOL_METER', '32', '红相规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (280, 'PROTOCOL_METER', '33', '浩宁达规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (281, 'PROTOCOL_METER', '34', '华隆规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (282, 'PROTOCOL_METER', '35', '龙电规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (283, 'PROTOCOL_METER', '36', '兰吉尔D表规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (284, 'PROTOCOL_METER', '37', '许继规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (285, 'PROTOCOL_METER', '38', '科陆规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (286, 'PROTOCOL_METER', '39', '三星规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (287, 'PROTOCOL_METER', '40', '爱拓利规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (288, 'PROTOCOL_METER', '41', 'ABB的α表规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (289, 'PROTOCOL_METER', '42', 'ABB圆表规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (290, 'PROTOCOL_METER', '43', '大崎表规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (291, 'PROTOCOL_METER', '44', '红相MK3表规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (292, 'PROTOCOL_METER', '45', '华立规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (293, 'PROTOCOL_METER', '46', '兰吉尔B表规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (294, 'PROTOCOL_METER', '47', '林洋规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (295, 'PROTOCOL_METER', '48', '东方电子规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (296, 'PROTOCOL_METER', '49', '"伊梅尔规约"', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (297, 'PROTOCOL_METER', '50', '伊斯卡规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (298, 'PROTOCOL_METER', '51', '威胜国标', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (299, 'PROTOCOL_METER', '55', '埃尔斯特（Elster）规约', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (300, 'PROTOCOL_METER', '60', '"配变分支回路监测装置规约"', '电表规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (314, 'PROTOCOL_TERM', '100', '698规约', '终端规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (336, 'PROTOCOL_TERM', '101', '四川双流规约', '终端规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (339, 'PROTOCOL_TERM', '102', '四川眉山规约', '终端规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (340, 'PROTOCOL_TERM', '106', '国网规约', '终端规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (341, 'PROTOCOL_TERM', '120', '浙江规约', '终端规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (342, 'PROTOCOL_TERM', '121', '浙江增补规约', '终端规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (343, 'PROTOCOL_TERM', '122', '广东大用户规约', '终端规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (344, 'PROTOCOL_TERM', '123', '广东大用户规约（第二版）', '终端规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (345, 'PROTOCOL_TERM', '124', '广东配变规约', '终端规约', '0', null);
commit;
prompt 400 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (688, 'PROTOCOL_TERM', '125', '江西配变规约', '终端规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (689, 'PROTOCOL_TERM', '126', '广东集抄规约', '终端规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (690, 'PROTOCOL_TERM', '127', '江西大用户规约', '终端规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (691, 'PROTOCOL_TERM', '129', '浙江自定义规约', '终端规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (692, 'PROTOCOL_TERM', '146', '广东变电站规约', '终端规约', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (584, 'PT_RATIO', '1', '220/220', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (587, 'PT_RATIO', '10', '50/5', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (594, 'PT_RATIO', '100', '10000/100', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (595, 'PT_RATIO', '101', '10000√3/100/√3', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (596, 'PT_RATIO', '105', '10500/100', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (588, 'PT_RATIO', '11', '1100/100', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (597, 'PT_RATIO', '110', '11kV/100V', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (602, 'PT_RATIO', '1100', '110000/100', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (603, 'PT_RATIO', '1101', '110000√3/100/√3', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (585, 'PT_RATIO', '2', '10/5', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (589, 'PT_RATIO', '20', '100/5', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (598, 'PT_RATIO', '200', '1000/5', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (604, 'PT_RATIO', '2200', '220000/100', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (605, 'PT_RATIO', '2201', '220000√3/100/√3', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (590, 'PT_RATIO', '25', '10000/400', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (586, 'PT_RATIO', '3', '380/100', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (599, 'PT_RATIO', '350', '35000/100', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (591, 'PT_RATIO', '40', '200/5', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (606, 'PT_RATIO', '5000', '500000/100', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (592, 'PT_RATIO', '60', '6kV/100V', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (600, 'PT_RATIO', '600', '60kV/100V', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (593, 'PT_RATIO', '63', '6300/100', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (601, 'PT_RATIO', '660', '66kV/100V', 'PT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (186, 'PW_DATA_TYPE', '10', '有功功率', '功率数据-数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (187, 'PW_DATA_TYPE', '20', '无功功率', '功率数据-数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (551, 'P_VALUE', '1', '允许', '参数值', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (552, 'P_VALUE', '2', '拒绝', '参数值', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (559, 'QLF_MARK', '-1', '所有', '合格标识', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (829, 'QLF_MARK', '0', '不合格', '合格标识', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (828, 'QLF_MARK', '1', '合格', '合格标识', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (607, 'RATED_EC', '10', '3A/6A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (620, 'RATED_EC', '100', '30A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1062, 'RATED_EC', '1000', '5(40)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (621, 'RATED_EC', '101', '5(6)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1063, 'RATED_EC', '1010', '10(60)', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1064, 'RATED_EC', '1020', '2.5(15)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1065, 'RATED_EC', '1030', '0.3(1.2)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (622, 'RATED_EC', '110', '15A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (623, 'RATED_EC', '111', '1A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (624, 'RATED_EC', '120', '25A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (625, 'RATED_EC', '121', '0.3A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1101, 'RATED_EC', '123', '123', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (626, 'RATED_EC', '130', '其它', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (627, 'RATED_EC', '140', '5(40)', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (628, 'RATED_EC', '150', '2.5(20)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (629, 'RATED_EC', '160', '3(5)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (630, 'RATED_EC', '170', '3×5（100)', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (631, 'RATED_EC', '180', '10(80)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (632, 'RATED_EC', '190', '100.0A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (608, 'RATED_EC', '20', '5A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (633, 'RATED_EC', '200', '100A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (634, 'RATED_EC', '210', '1500A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (635, 'RATED_EC', '220', '150A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (636, 'RATED_EC', '230', '2.5(2.5)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (637, 'RATED_EC', '240', '2.5(5)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (638, 'RATED_EC', '250', '200A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (639, 'RATED_EC', '260', '250A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (640, 'RATED_EC', '270', '3(20)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (641, 'RATED_EC', '280', '30(120)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (642, 'RATED_EC', '290', '300A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (609, 'RATED_EC', '30', '10A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (643, 'RATED_EC', '300', '40/80A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (644, 'RATED_EC', '310', '400A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (645, 'RATED_EC', '320', '5(10)', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (646, 'RATED_EC', '330', '5(15)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (647, 'RATED_EC', '340', '5(5)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (648, 'RATED_EC', '350', '500A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (649, 'RATED_EC', '360', '50A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (650, 'RATED_EC', '370', '600A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (651, 'RATED_EC', '380', '800A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (652, 'RATED_EC', '390', '380/20V', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (610, 'RATED_EC', '40', '1.5(6)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (653, 'RATED_EC', '400', '380/22kV', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (654, 'RATED_EC', '410', '1.5/615A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (655, 'RATED_EC', '420', '1.5/6A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (656, 'RATED_EC', '430', '10/20A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (657, 'RATED_EC', '440', '10/400A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (658, 'RATED_EC', '450', '10/40A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (659, 'RATED_EC', '460', '10/600A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (660, 'RATED_EC', '470', '10/60A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (661, 'RATED_EC', '480', '10/80 8A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (662, 'RATED_EC', '490', '10/80A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (611, 'RATED_EC', '50', '5(10)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (663, 'RATED_EC', '500', '100/5A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (664, 'RATED_EC', '510', '1200A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (665, 'RATED_EC', '520', '15/60A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (666, 'RATED_EC', '530', '150/5A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (667, 'RATED_EC', '540', '16/60A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (668, 'RATED_EC', '550', '2./10A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (669, 'RATED_EC', '560', '2.0/10A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (670, 'RATED_EC', '570', '2.05/10A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (671, 'RATED_EC', '580', '2.0A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (672, 'RATED_EC', '590', '2.5./10A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (325, 'ADD_COULO_TYPE', '-1', '所有', '电量追加类型', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (326, 'ADD_COULO_TYPE', '1', '日追加', '电量追加类型', '1', null);
commit;
prompt 500 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (327, 'ADD_COULO_TYPE', '2', '月追加', '电量追加类型', '2', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (317, 'ALARM_GRADE', 'A', 'Ⅰ级', '预警等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (318, 'ALARM_GRADE', 'B', 'Ⅱ级', '预警等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (319, 'ALARM_GRADE', 'C', 'Ⅲ级', '预警等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (320, 'ALARM_GRADE', 'D', 'Ⅳ级', '预警等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (321, 'ALARM_GRADE', 'E', 'Ⅴ级', '预警等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (322, 'ALARM_GRADE', 'F', 'Ⅵ级', '预警等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (323, 'ALARM_GRADE', 'G', 'Ⅶ级', '预警等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (324, 'ALARM_GRADE', 'H', 'Ⅷ级', '预警等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (808, 'AN_CYCLE', '1', '小时', '分析周期', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (809, 'AN_CYCLE', '2', '天', '分析周期', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (810, 'AN_CYCLE', '3', '月', '分析周期', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (806, 'AN_TACTICS', '1', '自动分析', '分析策略', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (807, 'AN_TACTICS', '2', '手动分析', '分析策略', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (580, 'AN_TACTICS', '3', '自动手动均支持', '分析策略', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (143, 'AREA', '1', '城市', '区域', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (144, 'AREA', '2', '农村', '区域', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (563, 'BS_TYPE', '1', '暂停', '报停类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (562, 'BS_TYPE', '2', '暂停恢复', '报停类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (561, 'BS_TYPE', '3', '减容', '报停类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (560, 'BS_TYPE', '4', '减容恢复', '报停类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1093, 'BTL', '0', '300', '波特率', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1094, 'BTL', '1', '600', '波特率', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1095, 'BTL', '2', '1200', '波特率', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1096, 'BTL', '3', '2400', '波特率', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1097, 'BTL', '4', '4800', '波特率', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1098, 'BTL', '5', '7200', '波特率', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1099, 'BTL', '6', '9600', '波特率', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1100, 'BTL', '7', '19200', '波特率', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (346, 'BYPASS_DIRECT', '1', '正向', '旁路方向', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (347, 'BYPASS_DIRECT', '2', '反向', '旁路方向', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (85, 'CAN_BREAK', '1', '带拉闸', '拉闸功能', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (86, 'CAN_BREAK', '2', '不带拉闸', '拉闸功能', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (540, 'CHANNEL_TYPE', '1', 'TCP', '通道类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (541, 'CHANNEL_TYPE', '2', 'UDP', '通道类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (534, 'COMM_MODE', '1', 'GPRS', '通讯方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (535, 'COMM_MODE', '2', 'CDMA', '通讯方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (536, 'COMM_MODE', '3', '230M', '通讯方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (537, 'COMM_MODE', '4', '232串口', '通讯方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (538, 'COMM_MODE', '5', '有线网络', '通讯方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (539, 'COMM_MODE', '6', '短信', '通讯方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (480, 'COMM_MODE', '7', '电力专线', '通讯方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (79, 'COMM_MODE_GM', '1', '485', '对表通讯方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (74, 'COMM_MODE_GM', '2', '载波', '对表通讯方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (75, 'COMM_MODE_GM', '3', '小无线', '对表通讯方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (76, 'COMM_MODE_GM', '4', 'ZIGBEE', '对表通讯方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (699, 'CT_RATIO', '1', '5/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (708, 'CT_RATIO', '10', '50/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (733, 'CT_RATIO', '100', '500/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (766, 'CT_RATIO', '1000', '5000/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (767, 'CT_RATIO', '1001', '1000/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (734, 'CT_RATIO', '101', '50/100', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (735, 'CT_RATIO', '102', '100/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1073, 'CT_RATIO', '10500', '52500/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (709, 'CT_RATIO', '11', '55/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (768, 'CT_RATIO', '1100', '110000/100', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (736, 'CT_RATIO', '111', '3/10', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (710, 'CT_RATIO', '12', '60/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (737, 'CT_RATIO', '120', '600/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (769, 'CT_RATIO', '1201', '1200/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (738, 'CT_RATIO', '121', '120/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (739, 'CT_RATIO', '125', '625/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (711, 'CT_RATIO', '13', '60/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (712, 'CT_RATIO', '14', '65/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (740, 'CT_RATIO', '140', '700/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (713, 'CT_RATIO', '15', '75/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (741, 'CT_RATIO', '150', '750/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (770, 'CT_RATIO', '1500', '7500/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (771, 'CT_RATIO', '1501', '1500/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (742, 'CT_RATIO', '151', '150/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (714, 'CT_RATIO', '16', '80/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (743, 'CT_RATIO', '160', '800/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (772, 'CT_RATIO', '1601', '1600/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (773, 'CT_RATIO', '1602', '1600/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (744, 'CT_RATIO', '170', '850/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (715, 'CT_RATIO', '18', '90/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (745, 'CT_RATIO', '180', '900/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (700, 'CT_RATIO', '2', '10/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (716, 'CT_RATIO', '20', '100/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (746, 'CT_RATIO', '200', '1000/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (774, 'CT_RATIO', '2000', '10000/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1074, 'CT_RATIO', '20000', '100000/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (775, 'CT_RATIO', '2001', '2000/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (747, 'CT_RATIO', '201', '200/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (748, 'CT_RATIO', '210', '1050/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1075, 'CT_RATIO', '22000', '220000/10', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (717, 'CT_RATIO', '23', '115/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (718, 'CT_RATIO', '24', '120/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (749, 'CT_RATIO', '240', '1200/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (776, 'CT_RATIO', '2401', '2400/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (719, 'CT_RATIO', '25', '125/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (750, 'CT_RATIO', '250', '1250/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (777, 'CT_RATIO', '2500', '2500/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (701, 'CT_RATIO', '3', '15/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (720, 'CT_RATIO', '30', '150/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (751, 'CT_RATIO', '300', '1500/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (778, 'CT_RATIO', '3000', '15000/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1076, 'CT_RATIO', '30000', '150000/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (779, 'CT_RATIO', '3001', '3000/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (752, 'CT_RATIO', '301', '300/1', 'CT变比', '1', null);
commit;
prompt 600 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (753, 'CT_RATIO', '320', '1600/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (754, 'CT_RATIO', '350', '35000/100', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (780, 'CT_RATIO', '3500', '3500/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1077, 'CT_RATIO', '35000', '165000/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (781, 'CT_RATIO', '3501', '17500/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (755, 'CT_RATIO', '360', '1800/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (702, 'CT_RATIO', '4', '20/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (721, 'CT_RATIO', '40', '200/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (756, 'CT_RATIO', '400', '2000/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (782, 'CT_RATIO', '4000', '20000/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1078, 'CT_RATIO', '40000', '200000/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1066, 'CT_RATIO', '4001', '4000/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (757, 'CT_RATIO', '401', '400/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (722, 'CT_RATIO', '41', '40/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (723, 'CT_RATIO', '45', '225/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (758, 'CT_RATIO', '480', '2400/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (703, 'CT_RATIO', '5', '25/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (724, 'CT_RATIO', '50', '250/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (759, 'CT_RATIO', '500', '2500/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1067, 'CT_RATIO', '5000', '5000/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1068, 'CT_RATIO', '5001', '25000/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (760, 'CT_RATIO', '501', '500/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (725, 'CT_RATIO', '51', '50/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1069, 'CT_RATIO', '5250', '26250/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (704, 'CT_RATIO', '6', '30/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (726, 'CT_RATIO', '60', '300/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (761, 'CT_RATIO', '600', '3000/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1070, 'CT_RATIO', '6000', '6000/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1071, 'CT_RATIO', '6001', '30000/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (762, 'CT_RATIO', '601', '600/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (727, 'CT_RATIO', '61', '6/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (728, 'CT_RATIO', '62', '60/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1079, 'CT_RATIO', '66000', '330000/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (705, 'CT_RATIO', '7', '35/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (763, 'CT_RATIO', '700', '3500/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (729, 'CT_RATIO', '72', '360/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (706, 'CT_RATIO', '8', '40/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (730, 'CT_RATIO', '80', '400/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (764, 'CT_RATIO', '800', '4000/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1072, 'CT_RATIO', '8000', '40000/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (765, 'CT_RATIO', '801', '800/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (731, 'CT_RATIO', '81', '8/1', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (707, 'CT_RATIO', '9', '11/3', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (732, 'CT_RATIO', '90', '450/5', 'CT变比', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (520, 'CUR_STATUS', '1', '运行', '当前状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (521, 'CUR_STATUS', '2', '待调', '当前状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (522, 'CUR_STATUS', '6', '故障', '当前状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (523, 'CUR_STATUS', '7', '停运', '当前状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (844, 'CUST_CURVE_DATA', '10', '正向有功', '自定义报表--曲线数据项', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (845, 'CUST_CURVE_DATA', '11', '反向有功', '自定义报表--曲线数据项', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (846, 'CUST_CURVE_DATA', '12', '正向无功', '自定义报表--曲线数据项', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (847, 'CUST_CURVE_DATA', '13', '反向无功', '自定义报表--曲线数据项', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (848, 'CUST_DATA_DENS', '10', '小时数据', '自定义报表-数据密度', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (849, 'CUST_DATA_DENS', '11', '日数据', '自定义报表-数据密度', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (850, 'CUST_DATA_DENS', '12', '月数据', '自定义报表-数据密度', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (43, 'CUST_DATA_DENS', '15', '抄表日数据', '自定义报表-数据密度', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (851, 'CUST_DATA_TYPE', '10', '电量', '自定义报表--数据项类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (852, 'CUST_DATA_TYPE', '11', '负荷', '自定义报表--数据项类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (853, 'CUST_DATA_TYPE', '12', '表码', '自定义报表--数据项类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (854, 'CUST_PHASE_TYPE', '10', 'A相', '自定义报表--相线', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (855, 'CUST_PHASE_TYPE', '11', 'B相', '自定义报表--相线', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (856, 'CUST_PHASE_TYPE', '12', 'C相', '自定义报表--相线', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1084, 'CUST_STATUS', '1', '正常', '用户状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1085, 'CUST_STATUS', '2', '变更中', '用户状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1086, 'CUST_STATUS', '3', '已销户', '用户状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1087, 'CUST_STATUS', '4', '报停', '用户状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (517, 'CUST_TYPE', '1', '高压', '用户分类', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (518, 'CUST_TYPE', '2', '低压非居民', '用户分类', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (519, 'CUST_TYPE', '3', '低压居民', '用户分类', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1088, 'DATAGROUP_RM', '10000', '曲线数据', '曲线数据', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1089, 'DATAGROUP_RM', '10001', '当前数据', '当前数据', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1090, 'DATAGROUP_RM', '10002', '小时冻结数据', '小时冻结数据', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1091, 'DATAGROUP_RM', '10003', '日冻结数据', '日冻结数据', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1092, 'DATAGROUP_RM', '10004', '月冻结数据', '月冻结数据', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (208, 'DATAGROUP_TASK_100', '100000', '当前数据', '698规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (209, 'DATAGROUP_TASK_100', '100010', '小时冻结数据', '698规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (210, 'DATAGROUP_TASK_100', '100100', '日冻结数据', '698规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (211, 'DATAGROUP_TASK_100', '100110', '抄表日冻结数据', '698规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (212, 'DATAGROUP_TASK_100', '100200', '月冻结数据', '698规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (213, 'DATAGROUP_TASK_100', '100300', '曲线数据', '698规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (462, 'DATAGROUP_TASK_101', '101000', '当前数据', '四川双流规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (463, 'DATAGROUP_TASK_101', '101010', '小时冻结数据', '四川双流规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (464, 'DATAGROUP_TASK_101', '101100', '日冻结数据', '四川双流规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (465, 'DATAGROUP_TASK_101', '101110', '抄表日冻结数据', '四川双流规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (466, 'DATAGROUP_TASK_101', '101200', '月冻结数据', '四川双流规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (467, 'DATAGROUP_TASK_101', '101300', '曲线数据', '四川双流规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (693, 'DATAGROUP_TASK_102', '102000', '当前数据', '四川眉山规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (694, 'DATAGROUP_TASK_102', '102010', '小时冻结数据', '四川眉山规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (695, 'DATAGROUP_TASK_102', '102100', '日冻结数据', '四川眉山规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (696, 'DATAGROUP_TASK_102', '102110', '抄表日冻结数据', '四川眉山规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (697, 'DATAGROUP_TASK_102', '102200', '月冻结数据', '四川眉山规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (698, 'DATAGROUP_TASK_102', '102300', '曲线数据', '四川眉山规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (214, 'DATAGROUP_TASK_106', '106000', '当前数据', '国网规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (215, 'DATAGROUP_TASK_106', '106010', '小时冻结数据', '国网规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (216, 'DATAGROUP_TASK_106', '106100', '日冻结数据', '国网规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (217, 'DATAGROUP_TASK_106', '106110', '抄表日冻结数据', '国网规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (218, 'DATAGROUP_TASK_106', '106200', '月冻结数据', '国网规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (219, 'DATAGROUP_TASK_106', '106300', '曲线数据', '国网规约数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (220, 'DATAGROUP_TASK_120', '12010', '普通任务', '浙江规约-类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (221, 'DATAGROUP_TASK_120', '12020', '中继任务', '浙江规约-类型', '0', null);
commit;
prompt 700 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (222, 'DATAGROUP_TASK_120', '12030', '异常任务', '浙江规约-类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (109, 'DATAGROUP_TASK_121', '12110', '普通任务', '浙江增补规约-类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (110, 'DATAGROUP_TASK_121', '12120', '中继任务', '浙江增补规约-类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (111, 'DATAGROUP_TASK_121', '12130', '异常任务', '浙江增补规约-类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (112, 'DATAGROUP_TASK_122', '12210', '普通任务', '广东大用户规约-类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (113, 'DATAGROUP_TASK_122', '12220', '中继任务', '广东大用户规约-类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (125, 'DATAGROUP_TASK_122', '12230', '异常任务', '广东大用户规约-类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (468, 'DATAGROUP_TASK_123', '12310', '普通任务', '广东大用户规约（第二版）-类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (469, 'DATAGROUP_TASK_124', '12410', '普通任务', '广东配变规约-类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1080, 'DATAGROUP_TASK_125', '12510', '普通任务', '江西配变规约-类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (470, 'DATAGROUP_TASK_126', '12610', '普通任务', '广东集抄规约-类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1081, 'DATAGROUP_TASK_127', '12710', '普通任务', '江西大用户规约-类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (132, 'DATAGROUP_TASK_129', '12910', '普通任务', '浙江自定义规约-类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (133, 'DATAGROUP_TASK_129', '12920', '中继任务', '浙江自定义规约-类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (134, 'DATAGROUP_TASK_129', '12930', '异常任务', '浙江自定义规约-类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (471, 'DATAGROUP_TASK_146', '14610', '普通任务', '广东变电站规约-类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (117, 'DATE_TYPE', '1', '日', '统计类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (118, 'DATE_TYPE', '2', '月', '统计类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (119, 'DATE_TYPE', '3', '年', '统计类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (13, 'DC_CHK_TYPE', '01', '表码超大', '数据检查类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (14, 'DC_CHK_TYPE', '02', '表码非法', '数据检查类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (15, 'DC_CHK_TYPE', '03', '表码倒走', '数据检查类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (48, 'DC_DATA_DENS', '10', '日冻结数据', '数据检查-数据密度', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (49, 'DC_DATA_DENS', '20', '月冻结数据', '数据检查-数据密度', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (50, 'DC_DATA_TYPE', '10', '正向有功', '数据检查-数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (51, 'DC_DATA_TYPE', '20', '反向有功', '数据检查-数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (52, 'DC_DATA_TYPE', '30', '正向无功', '数据检查-数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (53, 'DC_DATA_TYPE', '40', '反向无功', '数据检查-数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (8, 'DEAL_STATUS', '0', '未处理', '工单处理状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (9, 'DEAL_STATUS', '1', '已登记', '工单处理状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (10, 'DEAL_STATUS', '2', '已派工', '工单处理状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (11, 'DEAL_STATUS', '3', '已处理', '工单处理状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (12, 'DEAL_STATUS', '4', '已归档', '工单处理状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (91, 'DEV_TYPE', '1', 'I类', '装置类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (92, 'DEV_TYPE', '2', 'II类', '装置类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (93, 'DEV_TYPE', '3', 'III类', '装置类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (94, 'DEV_TYPE', '4', 'IV类', '装置类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (95, 'DEV_TYPE', '5', 'V类', '装置类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (16, 'DI_DATA_DENS', '10', '日冻结数据', '数据录入-数据密度', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (18, 'DI_DATA_DENS', '11', '抄表日数据', '数据录入-数据密度', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (17, 'DI_DATA_DENS', '20', '月冻结数据', '数据录入-数据密度', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (571, 'DQ_DATA_DENS', '10', '日冻结数据', '数据质量-数据密度', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (572, 'DQ_DATA_DENS', '11', '抄表日数据', '数据质量-数据密度', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (573, 'DQ_DATA_DENS', '20', '月冻结数据', '数据质量-数据密度', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (574, 'DQ_DATA_DENS', '30', '曲线数据', '数据质量-数据密度', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (487, 'DQ_DATA_DENS', '40', '全天数据', '数据质量-数据密度', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (488, 'DQ_DATA_DENS', '50', '周期数据', '数据质量-数据密度', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (575, 'DQ_DATA_TYPE', '10', '电量数据', '数据质量-数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (576, 'DQ_DATA_TYPE', '20', '功率数据', '数据质量-数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (834, 'ELEC_APP_TYPE', '100', '大工业', '用电类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (857, 'ELEC_APP_TYPE', '101', '"中小化肥"', '用电类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (859, 'ELEC_APP_TYPE', '200', '"居民生活用电"', '用电类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (858, 'ELEC_APP_TYPE', '300', '"农业生产用电"', '用电类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (860, 'ELEC_APP_TYPE', '301', '"贫困县农业排灌用电"', '用电类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (861, 'ELEC_APP_TYPE', '400', '"一般工商业"', '用电类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (862, 'ELEC_APP_TYPE', '500', '"趸售"', '用电类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (863, 'ELEC_APP_TYPE', '900', '其他', '用电类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (188, 'EQ_DATA_TYPE', '10', '正向有功总', '电量数据-数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (189, 'EQ_DATA_TYPE', '11', '正向有功尖', '电量数据-数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (190, 'EQ_DATA_TYPE', '12', '正向有功峰', '电量数据-数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (191, 'EQ_DATA_TYPE', '13', '正向有功平', '电量数据-数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (192, 'EQ_DATA_TYPE', '14', '正向有功谷', '电量数据-数据类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (71, 'EQ_LEVEL', '1', '0～100kWh', '用电水平', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (72, 'EQ_LEVEL', '2', '100～200kWh', '用电水平', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (73, 'EQ_LEVEL', '3', '300kWh以上', '用电水平', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (460, 'EQ_TYPE', '1', '正向', '电量类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (461, 'EQ_TYPE', '2', '反向', '电量类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (801, 'EX_ATTR', '0', '终端', '异常属性', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (795, 'EX_ATTR', '1', '485电表', '异常属性', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (802, 'EX_ATTR', '2', '脉冲', '异常属性', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (803, 'EX_ATTR', '3', '总加组', '异常属性', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (804, 'EX_ATTR', '4', '模拟量', '异常属性', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (805, 'EX_ATTR', '5', '大用户', '异常属性', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (796, 'EX_CATE', '10', '设备异常', '异常类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (797, 'EX_CATE', '20', '用电异常', '异常类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (35, 'EX_CHAR', '0', '发生', '异常性质', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (36, 'EX_CHAR', '1', '恢复', '异常性质', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1219, 'EX_DEAL_STATUS', '10', '未审核', '工单处理状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1220, 'EX_DEAL_STATUS', '20', '未派工', '工单处理状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1221, 'EX_DEAL_STATUS', '30', '已派工', '工单处理状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1222, 'EX_DEAL_STATUS', '40', '派工驳回', '工单处理状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1223, 'EX_DEAL_STATUS', '50', '处理中', '工单处理状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1224, 'EX_DEAL_STATUS', '60', '等待确认', '工单处理状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1225, 'EX_DEAL_STATUS', '70', '处理驳回', '工单处理状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1226, 'EX_DEAL_STATUS', '80', '已归档', '工单处理状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (798, 'EX_LARGE', '10', '用电异常', '异常大类', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (799, 'EX_LARGE', '20', '电表异常', '异常大类', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (800, 'EX_LARGE', '30', '终端异常', '异常大类', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (472, 'EX_LEVEL', '1', '严重', '事件级别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (473, 'EX_LEVEL', '2', '一般', '事件级别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (474, 'EX_LEVEL', '3', '次要', '事件级别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (581, 'EX_TYPE', '1', '计量装置检查', '异常类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (582, 'EX_TYPE', '2', '计量合理性检查', '异常类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (583, 'EX_TYPE', '3', '数据合理性检查', '异常类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (245, 'EX_TYPE', '4', '用电检查', '异常类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (255, 'EX_TYPE', '5', '经济性用电检查', '异常类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (874, 'FAULT_TYPE', '1', '终端(集中器)与主站无通讯', '设备故障类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1, 'FAULT_TYPE', '2', '终端(集中器)与表无通讯', '设备故障类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (2, 'FAULT_TYPE', '3', '时钟错误', '设备故障类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (3, 'FAULT_TYPE', '4', '超流量', '设备故障类型', '0', null);
commit;
prompt 800 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (4, 'FAULT_TYPE', '5', '终端事件过多', '设备故障类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (5, 'FAULT_TYPE', '6', '终端开关故障', '设备故障类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (6, 'FAULT_TYPE', '7', '终端有通讯未建档', '设备故障类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (7, 'FAULT_TYPE', '8', '有通讯无任务配置', '设备故障类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (87, 'FR_PHASE', '0', '不确定', '初抄相位', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (88, 'FR_PHASE', '1', 'A相', '初抄相位', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (89, 'FR_PHASE', '2', 'B相', '初抄相位', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (90, 'FR_PHASE', '3', 'C相', '初抄相位', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (31, 'GM_STATUS', '1', '运行', '采集器状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (32, 'GM_STATUS', '2', '待调', '采集器状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (33, 'GM_STATUS', '6', '故障', '采集器状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (34, 'GM_STATUS', '7', '停运', '采集器状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (542, 'GP_CHAR', '1', '485', '测量点性质', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (543, 'GP_CHAR', '2', '脉冲', '测量点性质', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (544, 'GP_CHAR', '3', '总加组', '测量点性质', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (545, 'GP_CHAR', '4', '模拟量', '测量点性质', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (196, 'GP_CHAR', '5', '任务号', '测量点性质', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (197, 'GP_CHAR', '6', '终端信息点', '测量点性质', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (198, 'GP_CHAR', '7', '控制轮次', '测量点性质', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (199, 'GP_CHAR', '8', '直流模拟量点号', '测量点性质', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (549, 'GP_STATUS', '0', '无效', '测量点状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (550, 'GP_STATUS', '1', '有效', '测量点状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (896, 'WORK_GROUP', '80288', '赤坎配营部关口表', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (897, 'WORK_GROUP', '803', '霞山配营部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (898, 'WORK_GROUP', '80301', '霞山配营部一班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (899, 'WORK_GROUP', '80302', '霞山配营部二班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (900, 'WORK_GROUP', '80360', '霞山装拆表一班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (901, 'WORK_GROUP', '80388', '霞山配营部关口表', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (902, 'WORK_GROUP', '804', '湛江市场部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (903, 'WORK_GROUP', '805', '客户服务中心', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (904, 'WORK_GROUP', '810', '湛江局本部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (905, 'WORK_GROUP', '811', '湛江农电公司', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (906, 'WORK_GROUP', '81101', '南调供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (907, 'WORK_GROUP', '81102', '坡头供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (908, 'WORK_GROUP', '81103', '龙头供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (909, 'WORK_GROUP', '81104', '官渡供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (910, 'WORK_GROUP', '81105', '乾塘供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (911, 'WORK_GROUP', '81106', '南三供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (912, 'WORK_GROUP', '81107', '农电大户', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (913, 'WORK_GROUP', '81108', '麻章供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (914, 'WORK_GROUP', '81109', '湖光供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (915, 'WORK_GROUP', '81110', '民安供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (916, 'WORK_GROUP', '81111', '太平供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (917, 'WORK_GROUP', '81112', '东山供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (918, 'WORK_GROUP', '81113', '硇洲供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (919, 'WORK_GROUP', '81114', '东简供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (920, 'WORK_GROUP', '81115', '志满供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (921, 'WORK_GROUP', '81188', '湛江农电公司关口表', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (922, 'WORK_GROUP', '812', '坡头局', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (923, 'WORK_GROUP', '81201', '坡头装拆表班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (924, 'WORK_GROUP', '81288', '坡头局关口表', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (925, 'WORK_GROUP', '813', '东海局', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (926, 'WORK_GROUP', '81301', '东海装拆表班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (927, 'WORK_GROUP', '81388', '东海局关口表', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (928, 'WORK_GROUP', '814', '麻章局', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (929, 'WORK_GROUP', '81401', '麻章装拆表班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (930, 'WORK_GROUP', '81488', '麻章局关口表', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (931, 'WORK_GROUP', '823', '遂溪供电局', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (932, 'WORK_GROUP', '82301', '遂溪市场部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (933, 'WORK_GROUP', '82310', '遂溪市场及客户服务部(大户)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (934, 'WORK_GROUP', '82320', '遂溪市场部大户(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (935, 'WORK_GROUP', '82321', '遂溪配电营业部(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (936, 'WORK_GROUP', '82330', '遂溪农电公司(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (937, 'WORK_GROUP', '82331', '附城供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (938, 'WORK_GROUP', '82332', '城西供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (939, 'WORK_GROUP', '82333', '黄略供电所（旧）', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (940, 'WORK_GROUP', '82334', '草潭供电所（旧）', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (941, 'WORK_GROUP', '82335', '城月供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (942, 'WORK_GROUP', '82336', '洋青供电所（旧）', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (943, 'WORK_GROUP', '82337', '北潭供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (944, 'WORK_GROUP', '82338', '界炮供电所（旧）', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (945, 'WORK_GROUP', '82339', '北坡供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (946, 'WORK_GROUP', '82340', '下录供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (947, 'WORK_GROUP', '82341', '河头供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (948, 'WORK_GROUP', '82342', '乐民供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (949, 'WORK_GROUP', '82343', '建新供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (950, 'WORK_GROUP', '82344', '杨柑供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (951, 'WORK_GROUP', '82345', '沙古供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (952, 'WORK_GROUP', '82346', '港门供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (953, 'WORK_GROUP', '82347', '岭北供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (954, 'WORK_GROUP', '82348', '江洪供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (955, 'WORK_GROUP', '82349', '乌塘供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (956, 'WORK_GROUP', '82350', '南亭营业厅(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (957, 'WORK_GROUP', '82351', '黄略供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (958, 'WORK_GROUP', '82352', '洋青供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (959, 'WORK_GROUP', '82353', '界炮供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (960, 'WORK_GROUP', '82354', '草潭供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (961, 'WORK_GROUP', '82355', '遂城供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (962, 'WORK_GROUP', '82360', '遂溪农电关口所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (963, 'WORK_GROUP', '82366', '遂溪配营关口所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (964, 'WORK_GROUP', '82377', '遂溪配营部直管', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (965, 'WORK_GROUP', '825', '徐闻供电局', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (966, 'WORK_GROUP', '82510', '徐闻配电营业部(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (967, 'WORK_GROUP', '82511', '城南所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (968, 'WORK_GROUP', '82512', '城北所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (969, 'WORK_GROUP', '82513', '海安所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (970, 'WORK_GROUP', '82520', '徐闻农电公司(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (971, 'WORK_GROUP', '82521', '下桥所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (972, 'WORK_GROUP', '82522', '和安所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (973, 'WORK_GROUP', '82523', '迈陈所（旧）', '维护班组', '1', null);
commit;
prompt 900 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (974, 'WORK_GROUP', '82524', '西连所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (975, 'WORK_GROUP', '82525', '大黄所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (976, 'WORK_GROUP', '82526', '角尾所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (977, 'WORK_GROUP', '82527', '锦和所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (978, 'WORK_GROUP', '82528', '新寮所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (979, 'WORK_GROUP', '82529', '曲界所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (980, 'WORK_GROUP', '82530', '前山所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (981, 'WORK_GROUP', '82531', '下洋所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (982, 'WORK_GROUP', '82532', '龙塘所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (983, 'WORK_GROUP', '82533', '外罗所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (984, 'WORK_GROUP', '82534', '五里所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (985, 'WORK_GROUP', '82535', '南山所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (986, 'WORK_GROUP', '82536', '锦和所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (987, 'WORK_GROUP', '82537', '迈陈所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (988, 'WORK_GROUP', '82540', '徐闻计量部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (989, 'WORK_GROUP', '82550', '徐闻财务部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (990, 'WORK_GROUP', '82560', '徐闻生计部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (991, 'WORK_GROUP', '82570', '徐闻市场部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (992, 'WORK_GROUP', '850', '湛江财务部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (993, 'WORK_GROUP', '851', '遂溪财务部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (994, 'WORK_GROUP', '860', '湛江局计量部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (995, 'WORK_GROUP', '86011', '计量部霞山班(外勤)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (996, 'WORK_GROUP', '86012', '计量部霞山班(内勤)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (997, 'WORK_GROUP', '86021', '计量部赤坎班(外勤)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (998, 'WORK_GROUP', '86022', '计量部赤坎班(内勤)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (999, 'WORK_GROUP', '86032', '计量部农电(外勤)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1000, 'WORK_GROUP', '86033', '计量部农电(内勤)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1001, 'WORK_GROUP', '86044', '计量部电网计量班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1002, 'WORK_GROUP', '86060', '计量部东海班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1003, 'WORK_GROUP', '86070', '计量部麻章班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1004, 'WORK_GROUP', '86077', '计量部标准班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1005, 'WORK_GROUP', '86080', '计量部坡头班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1006, 'WORK_GROUP', '86088', '计量部自动化班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1007, 'WORK_GROUP', '861', '遂溪计量部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1008, 'WORK_GROUP', '870', '湛江生技部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1009, 'WORK_GROUP', '871', '遂溪生计部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1010, 'WORK_GROUP', '877', '湛江信息部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1011, 'WORK_GROUP', '881', '廉江供电局', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1012, 'WORK_GROUP', '88101', '廉城配营中心', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1013, 'WORK_GROUP', '88102', '城北配营中心', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1014, 'WORK_GROUP', '88103', '石城供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1015, 'WORK_GROUP', '88104', '安铺供电所（旧）', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1016, 'WORK_GROUP', '88105', '横山供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1017, 'WORK_GROUP', '88106', '吉水供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1018, 'WORK_GROUP', '88107', '和寮供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1019, 'WORK_GROUP', '88108', '新民供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1020, 'WORK_GROUP', '88109', '雅塘供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1021, 'WORK_GROUP', '88110', '青平供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1022, 'WORK_GROUP', '88111', '车板供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1023, 'WORK_GROUP', '88112', '石岭供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1024, 'WORK_GROUP', '88113', '良垌供电所（旧）', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1025, 'WORK_GROUP', '88114', '营仔供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1026, 'WORK_GROUP', '88115', '长山供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1027, 'WORK_GROUP', '88116', '塘蓬供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1028, 'WORK_GROUP', '88117', '石颈供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1029, 'WORK_GROUP', '88118', '河堤供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1030, 'WORK_GROUP', '88119', '高桥供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1031, 'WORK_GROUP', '88120', '龙湾供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1032, 'WORK_GROUP', '88121', '河唇供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1033, 'WORK_GROUP', '88122', '平坦供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1034, 'WORK_GROUP', '88123', '新华供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1035, 'WORK_GROUP', '88124', '石角变电站', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1036, 'WORK_GROUP', '88125', '良垌供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1037, 'WORK_GROUP', '88126', '安铺供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1038, 'WORK_GROUP', '88127', '石岭供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1039, 'WORK_GROUP', '88150', '廉江市场部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1040, 'WORK_GROUP', '88151', '城西配营中心', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1041, 'WORK_GROUP', '88160', '廉江财务部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1042, 'WORK_GROUP', '88170', '廉江计量部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1043, 'WORK_GROUP', '88180', '廉江生计部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1044, 'WORK_GROUP', '88188', '廉江关口表', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1045, 'WORK_GROUP', '88198', '廉江供电局->趸售', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1046, 'WORK_GROUP', '88199', '廉江(趸售)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1047, 'WORK_GROUP', '882', '雷州供电局', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1048, 'WORK_GROUP', '88201', '市场及客户服务部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1102, 'WORK_GROUP', '88202', '城内配营中心', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1103, 'WORK_GROUP', '88203', '城外配营中心', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1104, 'WORK_GROUP', '88204', '附城供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1105, 'WORK_GROUP', '88205', '白沙供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1106, 'WORK_GROUP', '88206', '沈塘供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1107, 'WORK_GROUP', '88207', '客路供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1108, 'WORK_GROUP', '88208', '杨家供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1109, 'WORK_GROUP', '88209', '唐家供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1110, 'WORK_GROUP', '88210', '纪家供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1111, 'WORK_GROUP', '88211', '企水供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1112, 'WORK_GROUP', '88212', '南兴供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1113, 'WORK_GROUP', '88213', '松竹供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1114, 'WORK_GROUP', '88214', '龙门供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1115, 'WORK_GROUP', '88215', '北和供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1116, 'WORK_GROUP', '88216', '覃斗供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1117, 'WORK_GROUP', '88217', '英利供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1118, 'WORK_GROUP', '88218', '调风供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1119, 'WORK_GROUP', '88219', '雷高供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1120, 'WORK_GROUP', '88220', '东里供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1121, 'WORK_GROUP', '88221', '乌石供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1122, 'WORK_GROUP', '88222', '奋勇供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1123, 'WORK_GROUP', '88223', '东方红农场所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1124, 'WORK_GROUP', '88224', '火炬供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1125, 'WORK_GROUP', '88225', '幸福农场所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1126, 'WORK_GROUP', '88226', '金星供电所(旧)', '维护班组', '1', null);
commit;
prompt 1000 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1127, 'WORK_GROUP', '88227', '收获农场所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1128, 'WORK_GROUP', '88228', '马留农场所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1129, 'WORK_GROUP', '88229', '雷州盐场所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1130, 'WORK_GROUP', '88231', '白沙供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1131, 'WORK_GROUP', '88232', '唐家供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1132, 'WORK_GROUP', '88233', '龙门供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1133, 'WORK_GROUP', '88234', '乌石供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1134, 'WORK_GROUP', '88235', '英利供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1135, 'WORK_GROUP', '88236', '调风供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1136, 'WORK_GROUP', '88250', '雷州市场部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1137, 'WORK_GROUP', '88260', '雷州计量部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1138, 'WORK_GROUP', '88270', '雷州财务部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1139, 'WORK_GROUP', '88280', '雷州生计部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1140, 'WORK_GROUP', '88288', '雷州关口表', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1141, 'WORK_GROUP', '88298', '雷州供电局->趸售', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1142, 'WORK_GROUP', '88299', '雷州(趸售)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1143, 'WORK_GROUP', '883', '吴川供电局', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1144, 'WORK_GROUP', '88301', '浅水供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1145, 'WORK_GROUP', '88302', '长岐供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1146, 'WORK_GROUP', '88303', '兰石供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1147, 'WORK_GROUP', '88304', '王村港供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1148, 'WORK_GROUP', '88305', '覃巴供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1149, 'WORK_GROUP', '88306', '大山江供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1150, 'WORK_GROUP', '88307', '塘尾供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1151, 'WORK_GROUP', '88308', '吴阳供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1152, 'WORK_GROUP', '88309', '振文供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1153, 'WORK_GROUP', '88310', '樟铺供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1154, 'WORK_GROUP', '88311', '黄坡供电所（旧）', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1155, 'WORK_GROUP', '88312', '中山供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1156, 'WORK_GROUP', '88313', '塘缀供电所（旧）', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1157, 'WORK_GROUP', '88314', '板桥供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1158, 'WORK_GROUP', '88316', '营业一班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1159, 'WORK_GROUP', '88317', '梅录第二供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1160, 'WORK_GROUP', '88318', '博铺供电所(旧)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1161, 'WORK_GROUP', '88319', '运行维护1班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1162, 'WORK_GROUP', '88320', '运行维护2班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1163, 'WORK_GROUP', '88321', '运行维护3班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1164, 'WORK_GROUP', '88340', '吴川市场部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1165, 'WORK_GROUP', '88341', '营业二班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1166, 'WORK_GROUP', '88342', '营业三班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1167, 'WORK_GROUP', '88343', '塘缀供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1168, 'WORK_GROUP', '88344', '黄坡供电所', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1169, 'WORK_GROUP', '88350', '吴川财务部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1170, 'WORK_GROUP', '88360', '吴川计量部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1171, 'WORK_GROUP', '88370', '吴川生计部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1172, 'WORK_GROUP', '88388', '吴川关口表', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1173, 'WORK_GROUP', '88398', '吴川供电局->趸售', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1174, 'WORK_GROUP', '88399', '吴川(趸售)', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1175, 'WORK_GROUP', '899', '湛江趸售区', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (613, 'RATED_EC', '60', '1(10)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (673, 'RATED_EC', '600', '2.5//10A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (612, 'RATED_EC', '61', '2A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (674, 'RATED_EC', '610', '2.5/10.A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (675, 'RATED_EC', '620', '2.5/10A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (676, 'RATED_EC', '630', '2.5/10\A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (677, 'RATED_EC', '640', '2.5/114A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (678, 'RATED_EC', '650', '2.5/5A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (679, 'RATED_EC', '660', '2.5A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (680, 'RATED_EC', '670', '20/40A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (681, 'RATED_EC', '680', '20/80A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (682, 'RATED_EC', '690', '200/5A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (615, 'RATED_EC', '70', '1(2)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (683, 'RATED_EC', '700', '220A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (614, 'RATED_EC', '71', '2.5A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (684, 'RATED_EC', '710', '24/80A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (685, 'RATED_EC', '720', '250/5A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (686, 'RATED_EC', '730', '3/60A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (687, 'RATED_EC', '740', '3/6A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (167, 'RATED_EC', '750', '30/100A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (168, 'RATED_EC', '760', '30/120A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (169, 'RATED_EC', '770', '30/60A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (170, 'RATED_EC', '780', '300/220A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (171, 'RATED_EC', '790', '300/5A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (617, 'RATED_EC', '80', '5(20)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (172, 'RATED_EC', '800', '380/220A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (616, 'RATED_EC', '81', '1.5A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (173, 'RATED_EC', '810', '4/20A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (174, 'RATED_EC', '820', '40/5A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (175, 'RATED_EC', '830', '400/5A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (176, 'RATED_EC', '840', '5-20A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (177, 'RATED_EC', '850', '5/10A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (178, 'RATED_EC', '860', '5/20  7A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1049, 'RATED_EC', '870', '5/20A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1050, 'RATED_EC', '880', '5/22A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1051, 'RATED_EC', '890', '5/300A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (619, 'RATED_EC', '90', '10(40)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1052, 'RATED_EC', '900', '5/30A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (618, 'RATED_EC', '91', '3A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1053, 'RATED_EC', '910', '5/40A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1054, 'RATED_EC', '920', '5/6A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1055, 'RATED_EC', '930', '50/5A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1056, 'RATED_EC', '940', '500/5A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1057, 'RATED_EC', '950', '520A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1058, 'RATED_EC', '960', '75/5A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1059, 'RATED_EC', '970', '750/5A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1060, 'RATED_EC', '980', '80A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1061, 'RATED_EC', '990', '1(1)A', '额定电流', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (481, 'RATED_VOLT', '1', '220V', '额定电压', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (482, 'RATED_VOLT', '2', '3?á380V', '额定电压', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (483, 'RATED_VOLT', '3', '3?á220/380V', '额定电压', '0', null);
commit;
prompt 1100 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (484, 'RATED_VOLT', '4', '3?á100V', '额定电压', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (485, 'RATED_VOLT', '5', '3?á57.7/100V', '额定电压', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (486, 'RATED_VOLT', '6', '57.7', '额定电压', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (147, 'RELAY_TYPE', '0', '无中继', '中继类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (148, 'RELAY_TYPE', '1', '一级中继', '中继类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (149, 'RELAY_TYPE', '2', '二级中继', '中继类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (150, 'RELAY_TYPE', '3', '三级中继', '中继类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (151, 'RELAY_TYPE', '4', '四级中继', '中继类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (152, 'RELAY_TYPE', '9', '自动中继', '中继类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (19, 'RLDJ', '1', '<50', '容量等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (20, 'RLDJ', '2', '>=50,<100', '容量等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (21, 'RLDJ', '3', '>=100,<315', '容量等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (22, 'RLDJ', '4', '>=315,<500', '容量等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (23, 'RLDJ', '5', '>=500,800', '容量等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (24, 'RLDJ', '6', '>=800', '容量等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (25, 'ROLE_TYPE', '1', '岗位角色', '角色类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (26, 'ROLE_TYPE', '2', '操作权限', '角色类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (27, 'ROLE_TYPE', '3', '系统对象', '角色类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (315, 'RUN_STATUS', '1', '在线', '运行状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (316, 'RUN_STATUS', '2', '不在线', '运行状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (329, 'RUN_STATUS', '3', '停电', '运行状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (383, 'SAR', '1', '湛江', '行政区', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (153, 'SCH_TYPE', '1', '错峰', '限电方案类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (154, 'SCH_TYPE', '2', '避峰', '限电方案类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (155, 'SCH_TYPE', '3', '负控限电', '限电方案类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (510, 'SHIFT_NO', '1', '单班', '生产班次', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (511, 'SHIFT_NO', '2', '2班', '生产班次', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (512, 'SHIFT_NO', '3', '3班', '生产班次', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (513, 'SHIFT_NO', '4', '连续生产', '生产班次', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (162, 'SL_MONITORING_0002', '1', '单位', '统计类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (163, 'SL_MONITORING_0002', '2', '变电站', '统计类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (164, 'SL_MONITORING_0002', '3', '线路', '统计类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (348, 'SS_TYPE', '1', '全局总供电量', '供售电量类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (349, 'SS_TYPE', '2', '全局上网电量', '供售电量类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (350, 'SS_TYPE', '3', '全局网供电量', '供售电量类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (351, 'SS_TYPE', '4', '全局网售电量', '供售电量类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (352, 'SS_TYPE', '5', '区县总供电量', '供售电量类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (353, 'SS_TYPE', '6', '专线用户总售电量', '供售电量类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (96, 'STATUS', '1', '所有', '处理状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (97, 'STATUS', '2', '待处理', '处理状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (98, 'STATUS', '3', '处理中', '处理状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (99, 'STATUS', '4', '处理完成', '处理状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (100, 'STATUS', '5', '已归档', '处理状态', null, null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (503, 'SUBS_STATUS', '1', '运行', '变电站状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (504, 'SUBS_STATUS', '2', '停用', '变电站状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (505, 'SUBS_STATUS', '3', '拆除', '变电站状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (77, 'SUBS_TYPE', '1', '变电站', '厂站类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (78, 'SUBS_TYPE', '2', '电厂', '厂站类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (80, 'SUBS_TYPE', '3', '开关站', '厂站类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (226, 'SW_ATTR', '1', '常开', '开关属性', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (227, 'SW_ATTR', '2', '常闭', '开关属性', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (814, 'SW_STATUS', '0', '断开', '开关状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (813, 'SW_STATUS', '1', '闭合', '开关状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (825, 'SYS_LOG_TYPE', '1', '存储过程执行错误', '系统日志类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (826, 'SYS_LOG_TYPE', '2', 'JOB快照', '系统日志类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (827, 'SYS_LOG_TYPE', '3', '表空间使用情况', '系统日志类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (830, 'SYS_OBJECT', '1', '专变用户', '系统对象', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (831, 'SYS_OBJECT', '2', '配变', '系统对象', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (832, 'SYS_OBJECT', '3', '低压用户', '系统对象', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (833, 'SYS_OBJECT', '4', '变电站', '系统对象', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (888, 'SYS_SYMBOL', '-1', '全部', '全部', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (877, 'SYS_SYMBOL', '1', '>', '大于号', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (878, 'SYS_SYMBOL', '10', '<=', '小于等于', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (879, 'SYS_SYMBOL', '20', '>=', '大于等于', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (880, 'SYS_SYMBOL', '3', '<', '小于号', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (54, 'SYS_TIME', '1', '日', '日', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (55, 'SYS_TIME', '2', '月', '月', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (56, 'SYS_TIME', '3', '年', '年', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (57, 'SYS_TIME', '4', '任意', '任意', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (579, 'S_TYPE', '1', '230M用户控制群', '方案类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (578, 'S_TYPE', '2', '避峰方案', '方案类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (577, 'S_TYPE', '3', '错峰方案', '方案类别', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (200, 'TASK_TYPE', '1', '自动上送', '任务类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (201, 'TASK_TYPE', '2', '主站轮召', '任务类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (101, 'TERM_MODEL', '1', 'BF001', '终端型号', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (102, 'TERM_MODEL', '2', 'BF002', '终端型号', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (866, 'TERM_TP_TYPE', '1', '公网专变终端', '参数模板类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (864, 'TERM_TP_TYPE', '10', '公网变电站终端', '参数模板类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (865, 'TERM_TP_TYPE', '11', '有线变电站终端', '参数模板类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (506, 'TERM_TP_TYPE', '2', '公网配变终端', '参数模板类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (507, 'TERM_TP_TYPE', '3', '公网集中器', '参数模板类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (508, 'TERM_TP_TYPE', '4', '公网远传表', '参数模板类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (509, 'TERM_TP_TYPE', '5', '230M专变终端', '参数模板类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (553, 'TERM_TP_TYPE', '6', '有线专变终端', '参数模板类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (554, 'TERM_TP_TYPE', '7', '有线配变终端', '参数模板类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (555, 'TERM_TP_TYPE', '8', '有线集中器', '参数模板类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (556, 'TERM_TP_TYPE', '9', '有线远传表', '参数模板类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (301, 'TERM_TYPE', '01', '负荷控制终端', '终端类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (302, 'TERM_TYPE', '02', '负荷监测终端', '终端类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (303, 'TERM_TYPE', '03', '普通配变终端', '终端类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (304, 'TERM_TYPE', '04', '配变监控终端', '终端类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (305, 'TERM_TYPE', '05', '低压集中器', '终端类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (306, 'TERM_TYPE', '06', '低压采集终端', '终端类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (307, 'TERM_TYPE', '07', '采集模块表', '终端类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (308, 'TERM_TYPE', '08', '关口电能量终端', '终端类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (569, 'TG_STATUS', '1', '运行', '台区状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (568, 'TG_STATUS', '2', '停运', '台区状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (567, 'TG_STATUS', '3', '拆除', '台区状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (145, 'TRAN_CODE', '1', 'S11-M.RD型', '变压器型号', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (146, 'TRAN_CODE', '2', 'S10-M.RD型', '变压器型号', '1', null);
commit;
prompt 1200 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (871, 'TRAN_STATUS', '1', '运行', '变压器状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (872, 'TRAN_STATUS', '2', '停用', '变压器状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (873, 'TRAN_STATUS', '3', '拆除', '变压器状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (867, 'TRAN_TYPE', '1', '主变', '变压器类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (868, 'TRAN_TYPE', '2', '站用变', '变压器类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (869, 'TRAN_TYPE', '3', '公变', '变压器类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (870, 'TRAN_TYPE', '4', '专变', '变压器类型', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (789, 'USER_STATUS', '0', '停用', '用户状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (788, 'USER_STATUS', '1', '正常', '用户状态', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (524, 'VOLT_GRADE', '1', '110KV', '电压等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (525, 'VOLT_GRADE', '2', '220KV', '电压等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (526, 'VOLT_GRADE', '3', '500KV', '电压等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (527, 'VOLT_GRADE', '4', '10KV', '电压等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (528, 'VOLT_GRADE', '5', '35KV', '电压等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (529, 'VOLT_GRADE', '6', '380V', '电压等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (530, 'VOLT_GRADE', '7', '220V', '电压等级', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (531, 'WIRING_MODE', '1', '单相', '接线方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (532, 'WIRING_MODE', '3', '三相三线', '接线方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (533, 'WIRING_MODE', '4', '三相四线', '接线方式', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (889, 'WORK_GROUP', '800', '湛江供电局', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (890, 'WORK_GROUP', '80022', '湛江市场部大户', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (891, 'WORK_GROUP', '80099', '湛江市场部大户班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (892, 'WORK_GROUP', '802', '赤坎配营部', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (893, 'WORK_GROUP', '80201', '赤坎配营部一班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (894, 'WORK_GROUP', '80202', '赤坎配营部二班', '维护班组', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (895, 'WORK_GROUP', '80280', '赤坎装拆表一班', '维护班组', '1', null);
commit;
prompt 1226 records loaded
prompt Enabling triggers for A_CODE...
alter table A_CODE enable all triggers;
set feedback on
set define on
prompt Done.
