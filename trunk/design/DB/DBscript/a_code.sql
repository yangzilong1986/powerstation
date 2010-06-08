prompt PL/SQL Developer import file
prompt Created on 2010��6��8�� by Administrator
set feedback off
set define off
prompt Disabling triggers for A_CODE...
alter table A_CODE disable all triggers;
prompt Deleting A_CODE...
delete from A_CODE;
commit;
prompt Loading A_CODE...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (489, 'GP_TYPE', '1', 'ר���û�', '����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (490, 'GP_TYPE', '2', '���/̨��', '����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (491, 'GP_TYPE', '3', '��ѹ�û�', '����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (492, 'GP_TYPE', '4', '���վ', '����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (820, 'GROSS_SN', '1', '1', '�ܼ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (821, 'GROSS_SN', '2', '2', '�ܼ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (822, 'GROSS_SN', '3', '3', '�ܼ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (823, 'GROSS_SN', '4', '4', '�ܼ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (811, 'GROUP_PROPERTY', '0', '˽��', 'Ⱥ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (812, 'GROUP_PROPERTY', '1', '����', 'Ⱥ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (240, 'GROUP_TYPE', '1', 'ר��Ⱥ��', 'Ⱥ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (241, 'GROUP_TYPE', '2', '���Ⱥ��', 'Ⱥ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (242, 'GROUP_TYPE', '3', '��ѹ�û�Ⱥ��', 'Ⱥ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (243, 'GROUP_TYPE', '4', '�ն�Ⱥ��', 'Ⱥ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1176, 'GROUP_TYPE', '5', '�����õ�Ⱥ��', 'Ⱥ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (311, 'HM_STATIS_SQC', '1', 'A��', 'г������ ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (312, 'HM_STATIS_SQC', '2', 'B��', 'г������ ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (313, 'HM_STATIS_SQC', '3', 'C��', 'г������ ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (309, 'HM_STATIS_TYPE', '1', '��ѹг��', 'г������ ͳ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (310, 'HM_STATIS_TYPE', '2', '����г��', 'г������ ͳ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (564, 'H_POWER_TYPE', '1', '�ش���ܺ���', '���ܺ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (565, 'H_POWER_TYPE', '2', '����ܺ���', '���ܺ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (566, 'H_POWER_TYPE', '3', '��ͨ���ܺ���', '���ܺ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (790, 'INSTAL_CHAR', '1', 'ר���û�', '��װ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (791, 'INSTAL_CHAR', '2', '���ñ�', '��װ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (792, 'INSTAL_CHAR', '3', '���۹ؿ�', '��װ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (793, 'INSTAL_CHAR', '4', '�糧�����ؿ�', '��װ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (794, 'INSTAL_CHAR', '5', '�����ؿ�', '��װ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (265, 'IO_TYPE', '1', '����', '�����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (266, 'IO_TYPE', '2', '���', '�����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1082, 'IS_MAS', '1', '����', '������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1083, 'IS_MAS', '2', '����', '������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (253, 'IS_MUL_LINE', '1', '����Դ', '�����Դ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (254, 'IS_MUL_LINE', '2', '���Դ', '�����Դ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (338, 'IS_SPECIAL', '0', '��ר���û�', '�û�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (337, 'IS_SPECIAL', '1', 'ר���û�', '�û�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (384, 'KPI_TYPE', '01', '���ɹ����ն˸�����', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (385, 'KPI_TYPE', '02', '���������ն˸�����', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (386, 'KPI_TYPE', '03', '��վ�ն˸�����', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (387, 'KPI_TYPE', '04', '��ѹ�����û�����������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (388, 'KPI_TYPE', '05', '��վ���ܼ����㸲����', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (389, 'KPI_TYPE', '06', '��ѹ���񼯳�����ز���ʽռ��', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (390, 'KPI_TYPE', '07', '�ܼ���ͻ����ɱ���', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (391, 'KPI_TYPE', '08', '�ܼ�ش�ͻ����ɱ���', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (392, 'KPI_TYPE', '09', '�ܼ�⹫�䣨��䣩���ɱ���', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (393, 'KPI_TYPE', '10', '���ɹ����ն�������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (394, 'KPI_TYPE', '11', '������ն�������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (395, 'KPI_TYPE', '12', '��վ�������ɼ��ն�������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (396, 'KPI_TYPE', '13', '������������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (397, 'KPI_TYPE', '14', '���ɹ����ն�ƽ��������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (398, 'KPI_TYPE', '15', '������ն�ƽ��������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (399, 'KPI_TYPE', '16', '��վ�������ɼ��ն�ƽ��������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (400, 'KPI_TYPE', '17', '������ƽ��������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (401, 'KPI_TYPE', '18', '���ɹ����ն˲ɼ�������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (402, 'KPI_TYPE', '19', '���������ն˲ɼ�������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (403, 'KPI_TYPE', '20', '��վ�������ɼ��ն˲ɼ�������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (404, 'KPI_TYPE', '21', '�������ɼ�������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (405, 'KPI_TYPE', '22', '���ɹ����ն˲ɼ���ȷ��', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (406, 'KPI_TYPE', '23', '���������ն˲ɼ���ȷ��', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (407, 'KPI_TYPE', '24', '��վ�������ɼ��ն˲ɼ���ȷ��', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (408, 'KPI_TYPE', '25', '�������ɼ���ȷ��', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (409, 'KPI_TYPE', '26', '���ɹ����ն����������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (410, 'KPI_TYPE', '27', '���������ն����������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (411, 'KPI_TYPE', '28', '��վ�������ɼ��ն����������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (412, 'KPI_TYPE', '29', '���������������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (413, 'KPI_TYPE', '30', '���ɹ����ն˿�����ȷ������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (414, 'KPI_TYPE', '31', '������Զ�̿�����ȷ��', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (415, 'KPI_TYPE', '32', '���ɹ����ն˱���׼ȷ��', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (416, 'KPI_TYPE', '33', '���������ն˱���׼ȷ��', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (417, 'KPI_TYPE', '34', '��վ�������ɼ��ն˱���׼ȷ��', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (418, 'KPI_TYPE', '35', '����������׼ȷ��', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (419, 'KPI_TYPE', '36', 'ʵ�ֽ��������ֳ��ն���', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (420, 'KPI_TYPE', '37', '��վͨ��ͨ�������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (421, 'KPI_TYPE', '38', '��������(�ص㻧)���ն��᳭���ɹ���(�ز�)', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (422, 'KPI_TYPE', '39', '��������(�ص㻧)���ն��᳭���ɹ���(485)', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (423, 'KPI_TYPE', '40', '���¶��᳭���ɹ���(�ز�)', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (424, 'KPI_TYPE', '41', '���¶��᳭���ɹ���(485)', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (425, 'KPI_TYPE', '42', '����(�ص㻧)���ն����������������(�ز�)', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (426, 'KPI_TYPE', '43', '����(�ص㻧)���ն����������������(485)', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (427, 'KPI_TYPE', '44', '�¶����������������(�ز�)', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (428, 'KPI_TYPE', '45', '�¶����������������(485)', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (429, 'KPI_TYPE', '46', '������һ�γ����ɹ���(�ز�)', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (430, 'KPI_TYPE', '47', '������һ�γ����ɹ���(485)', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (431, 'KPI_TYPE', '48', '������Уʱ�ɹ���', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (432, 'KPI_TYPE', '49', '̨�����ܱ�㲥Уʱ�ɹ���', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (433, 'KPI_TYPE', '50', '���ɹ����ն�ϵͳ���ݲɼ���ʱ', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (434, 'KPI_TYPE', '51', '���������ն�ϵͳ���ݲɼ���ʱ', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (435, 'KPI_TYPE', '52', '��վ�������ɼ��ն�ϵͳ���ݲɼ���ʱ', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (436, 'KPI_TYPE', '53', '������ϵͳ���ݲɼ���ʱ', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (437, 'KPI_TYPE', '54', '���ɹ����ն���վϵͳ������ʱ', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (438, 'KPI_TYPE', '55', '���������ն���վϵͳ������ʱ', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (439, 'KPI_TYPE', '56', '��վ�������ɼ��ն���վϵͳ������ʱ', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (440, 'KPI_TYPE', '57', '��������վϵͳ������ʱ', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (441, 'KPI_TYPE', '58', 'ϵͳ��Ӧʱ��', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (442, 'KPI_TYPE', '59', '��վ�����ϵͳ��ƽ����������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (443, 'KPI_TYPE', '60', '�ձ����ºϸ���', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (444, 'KPI_TYPE', '61', '������CPU������', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (445, 'KPI_TYPE', '62', '���������縺����', 'ϵͳָ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (835, 'LAT_MODE', '11', 'ͨѶ��ʽ', 'γ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (836, 'LAT_MODE', '12', '����', 'γ������', '0', null);
commit;
prompt 100 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (837, 'LAT_MODE', '13', '�ն��ͺ�', 'γ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (838, 'LAT_MODE', '14', '�ն�����', 'γ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (839, 'LAT_MODE', '21', '��λ�����ţ�', 'γ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (840, 'LAT_MODE', '22', '��ҵ', 'γ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (841, 'LAT_MODE', '23', '��·', 'γ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (842, 'LAT_MODE', '24', '��ѹ�ȼ�', 'γ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (843, 'LAT_MODE', '25', '�����ȼ�', 'γ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (107, 'LAT_MODE', '26', '�õ����', 'γ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (557, 'LAT_MODE', '31', '��������', 'γ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (558, 'LAT_MODE', '41', '���վ', 'γ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (328, 'LAT_MODE', '42', '̨��', 'γ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (114, 'LAT_MODE_ELECMON', '21', '���絥λ', '��ѹ�ϸ���ά�ȷ�ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (116, 'LAT_MODE_ELECMON', '23', '��·', '��ѹ�ϸ���ά�ȷ�ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (115, 'LAT_MODE_ELECMON', '28', '���վ', '��ѹ�ϸ���ά�ȷ�ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (493, 'LINE_STATUS', '01', 'Ͷ��', '��·״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (494, 'LINE_STATUS', '02', '��Ͷ��', '��·״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (495, 'LINE_STATUS', '03', '����', '��·״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (496, 'LINE_STATUS', '04', '����', '��·״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (158, 'LINE_TYPE', '1', '����', '��·���', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (159, 'LINE_TYPE', '2', '������', '��·���', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (514, 'LOAD_CHAR', '1', '1��', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (515, 'LOAD_CHAR', '2', '2��', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (516, 'LOAD_CHAR', '3', '3��', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (232, 'LOG_TYPE', '1', '������־', '��־����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (193, 'LOG_TYPE', '10', '��׼����', '��־����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (378, 'LOG_TYPE', '11', '�����¼ɾ��', '��־����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (233, 'LOG_TYPE', '2', '�������������־', '��־����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (234, 'LOG_TYPE', '3', 'ԭʼ�õ������޸�', '��־����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (235, 'LOG_TYPE', '4', 'Ȩ�ޱ����־', '��־����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (236, 'LOG_TYPE', '5', 'ϵͳ����', '��־����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (237, 'LOG_TYPE', '6', '�ɼ�����', '��־����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (238, 'LOG_TYPE', '7', '�쳣�����޸�', '��־����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (239, 'LOG_TYPE', '8', '��ֵ������־', '��־����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (42, 'LOG_TYPE', '9', 'Ԥ�������', '��־����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (108, 'MADE_FAC', '1', '��������', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (129, 'MADE_FAC', '10', '����', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (130, 'MADE_FAC', '11', '��½', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (131, 'MADE_FAC', '12', '������', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (135, 'MADE_FAC', '13', '��˹��', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (136, 'MADE_FAC', '14', '����', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (137, 'MADE_FAC', '15', '����', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (138, 'MADE_FAC', '16', '�������ϵ����ӿƼ���չ���޹�˾', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (139, 'MADE_FAC', '17', '��ʤ', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (140, 'MADE_FAC', '18', '����', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (141, 'MADE_FAC', '19', '������', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (120, 'MADE_FAC', '2', '������Ѷ', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (165, 'MADE_FAC', '20', '���', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (166, 'MADE_FAC', '21', '�㽭˳��', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (179, 'MADE_FAC', '22', '׿ά', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (180, 'MADE_FAC', '23', 'ABB', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (181, 'MADE_FAC', '24', '����', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (182, 'MADE_FAC', '25', '����', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (183, 'MADE_FAC', '26', '��Ƽ�', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (184, 'MADE_FAC', '27', 'Эͬ', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (185, 'MADE_FAC', '28', '����', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (223, 'MADE_FAC', '29', '�ʽ�', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (121, 'MADE_FAC', '3', '��ɽ����', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (224, 'MADE_FAC', '30', 'ɽ������', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (225, 'MADE_FAC', '31', '���ݽݱ�', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (122, 'MADE_FAC', '4', '�����Ϸ��������ſƼ���չ���޹�˾', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (123, 'MADE_FAC', '5', '���ݻ���', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (124, 'MADE_FAC', '6', '�Ƶ�', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (126, 'MADE_FAC', '7', '������', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (127, 'MADE_FAC', '8', '����', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (128, 'MADE_FAC', '9', '���ݰٸ�����', '���쳧��', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (546, 'MEAS_MODE', '1', '�߹��߼�', '������ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (547, 'MEAS_MODE', '2', '�߹��ͼ�', '������ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (548, 'MEAS_MODE', '3', '�͹��ͼ�', '������ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (815, 'METER_DIGITS', '4', '4', '��תλ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (816, 'METER_DIGITS', '5', '5', '��תλ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (817, 'METER_DIGITS', '6', '6', '��תλ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (818, 'METER_DIGITS', '7', '7', '��תλ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (28, 'METER_STATUS', '1', '����', '���״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (29, 'METER_STATUS', '2', 'ͣ��', '���״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (30, 'METER_STATUS', '3', '���', '���״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (881, 'METER_TYPE', '1', 'Զ����', '������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (882, 'METER_TYPE', '2', 'Ԥ���ѱ�', '������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (883, 'METER_TYPE', '3', '����', '������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (884, 'METER_TYPE', '4', '�๦�ܵ��ӱ�', '������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (885, 'METER_TYPE', '5', '������ӱ�', '������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (886, 'METER_TYPE', '6', '��е��', '������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (887, 'METER_TYPE', '7', '���׶๦�ܱ�', '������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (500, 'ML_STATUS', '01', 'Ͷ��', 'ĸ��״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (501, 'ML_STATUS', '02', '��Ͷ��', 'ĸ��״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (502, 'ML_STATUS', '03', '����', 'ĸ��״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (497, 'ML_TYPE', '01', '��ĸ', 'ĸ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (498, 'ML_TYPE', '02', '��ĸ', 'ĸ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (499, 'ML_TYPE', '03', '��ĸ', 'ĸ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (267, 'MODEL_CODE', '1', 'DD862', '����ͺ�', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (268, 'MODEL_CODE', '2', 'DT862', '����ͺ�', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (269, 'MODEL_CODE', '3', 'DTSD855', '����ͺ�', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (270, 'MODEL_CODE', '4', 'DS862', '����ͺ�', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (271, 'MODEL_CODE', '5', 'DDSF855', '����ͺ�', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (272, 'MODEL_CODE', '6', 'DDSY855', '����ͺ�', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (273, 'MODEL_CODE', '7', 'DDS855', '����ͺ�', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (256, 'MODEL_TYPE', '1', '̨��ģ��', 'ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (257, 'MODEL_TYPE', '10', '����ģ��', 'ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (258, 'MODEL_TYPE', '2', '��������ģ��', 'ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (259, 'MODEL_TYPE', '3', 'ȫ��ģ��', 'ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (260, 'MODEL_TYPE', '5', '��ѹ��·ģ��', 'ģ������', '0', null);
commit;
prompt 200 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (261, 'MODEL_TYPE', '6', 'ĸ��ģ��', 'ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (262, 'MODEL_TYPE', '7', '��ѹ��ģ��', 'ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (263, 'MODEL_TYPE', '8', 'վ��ģ��', 'ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (264, 'MODEL_TYPE', '9', '��ѹģ��', 'ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (103, 'MONITORING_TYPE', '1', 'A��', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (104, 'MONITORING_TYPE', '2', 'B��', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (105, 'MONITORING_TYPE', '3', 'C��', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (106, 'MONITORING_TYPE', '4', 'D��', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (44, 'MONITOR_TYPE', '1', 'A��', '��������6~10KVĸ�ߵ�ѹ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (45, 'MONITOR_TYPE', '2', 'B��', '��������110KV��35KVר�߹���Ŀͻ�110KV��35KVר�߹���Ŀͻ�110KV��35KVר�߹���Ŀͻ�110KV,35KVר�߹���ͻ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (46, 'MONITOR_TYPE', '3', 'C��', '��������35KV��ר�߹����û���10KV�����û�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (47, 'MONITOR_TYPE', '4', 'D��', '��������380/220V��ѹ������û�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (69, 'MONTH_LIST', '1', '01', '����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (60, 'MONTH_LIST', '10', '10', '����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (59, 'MONTH_LIST', '11', '11', '����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (58, 'MONTH_LIST', '12', '12', '����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (68, 'MONTH_LIST', '2', '02', '����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (67, 'MONTH_LIST', '3', '03', '����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (66, 'MONTH_LIST', '4', '04', '����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (65, 'MONTH_LIST', '5', '05', '����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (64, 'MONTH_LIST', '6', '06', '����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (63, 'MONTH_LIST', '7', '07', '����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (62, 'MONTH_LIST', '8', '08', '����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (61, 'MONTH_LIST', '9', '09', '����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (330, 'MP_ADDR', '1', '�����ѹ��', 'װ��λ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (331, 'MP_ADDR', '2', '������ѹ��', 'װ��λ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (332, 'MP_ADDR', '3', '�����ѹ��', 'װ��λ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (333, 'MP_ADDR', '4', '��·', 'װ��λ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (334, 'MP_ADDR', '5', 'վ�ñ�', 'װ��λ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (156, 'MP_DRCT', '1', '����', 'װ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (157, 'MP_DRCT', '2', '����', 'װ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (446, 'MP_USEAGE', '01', '����ר��', '�����;', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (447, 'MP_USEAGE', '02', 'ר��ר��', '�����;', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (448, 'MP_USEAGE', '03', '��ѹ�û�', '�����;', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (449, 'MP_USEAGE', '04', '̨������', '�����;', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (450, 'MP_USEAGE', '05', '���俼��', '�����;', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (451, 'MP_USEAGE', '06', '��·����', '�����;', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (452, 'MP_USEAGE', '07', 'վ�õ翼��', '�����;', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (453, 'MP_USEAGE', '08', '���������ؿ�', '�����;', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (454, 'MP_USEAGE', '09', '���۹���ؿ�', '�����;', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (455, 'MP_USEAGE', '10', '���й���ؿ�', '�����;', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (456, 'MP_USEAGE', '11', 'ʡ������ؿ�', '�����;', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (457, 'MP_USEAGE', '12', '���以���ؿڱ�', '�����;', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (458, 'MP_USEAGE', '13', '��������', '�����;', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (459, 'MP_USEAGE', '14', '��·����', '�����;', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (876, 'MP_USEAGE', '15', '�ӵر�', '�����;', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (875, 'MP_USEAGE', '16', '�翹��', '�����;', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (354, 'OBJECT_TYPE', '1', 'ר���û�', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (363, 'OBJECT_TYPE', '10', '����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (364, 'OBJECT_TYPE', '11', 'ĸ��', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (365, 'OBJECT_TYPE', '12', '��ѹ��', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (366, 'OBJECT_TYPE', '13', '����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (367, 'OBJECT_TYPE', '14', '����Ա', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (368, 'OBJECT_TYPE', '15', '��ɫ', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (369, 'OBJECT_TYPE', '16', '����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (370, 'OBJECT_TYPE', '17', '�˵�', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (371, 'OBJECT_TYPE', '18', 'Ⱥ��', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (372, 'OBJECT_TYPE', '19', '��ʩ', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (355, 'OBJECT_TYPE', '2', '���', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (373, 'OBJECT_TYPE', '20', '����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (374, 'OBJECT_TYPE', '21', '��׼����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (375, 'OBJECT_TYPE', '22', 'ϵͳ����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (376, 'OBJECT_TYPE', '23', '���', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (377, 'OBJECT_TYPE', '24', '��ѹ�ȼ�', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (356, 'OBJECT_TYPE', '3', '��ѹ�û�', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (357, 'OBJECT_TYPE', '4', '���վ', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (358, 'OBJECT_TYPE', '5', '�ն�', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (359, 'OBJECT_TYPE', '6', '�ɼ���', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (360, 'OBJECT_TYPE', '7', '������', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (361, 'OBJECT_TYPE', '8', '����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (362, 'OBJECT_TYPE', '9', '��·', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (160, 'ORDER_TYPE', '1', '�豸����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (161, 'ORDER_TYPE', '2', '�쳣��Ϣ', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (202, 'ORG_TYPE', '01', '������˾', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (203, 'ORG_TYPE', '02', 'ʡ��˾', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (204, 'ORG_TYPE', '03', '���й�˾', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (205, 'ORG_TYPE', '04', '���ع�˾', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (206, 'ORG_TYPE', '05', '�ֹ�˾', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (207, 'ORG_TYPE', '06', '������', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (246, 'PAGE_CNT', '10', '10', 'ÿҳ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (251, 'PAGE_CNT', '100', '100', 'ÿҳ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (247, 'PAGE_CNT', '20', '20', 'ÿҳ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (248, 'PAGE_CNT', '30', '30', 'ÿҳ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (249, 'PAGE_CNT', '40', '40', 'ÿҳ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (250, 'PAGE_CNT', '50', '50', 'ÿҳ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (252, 'PAGE_CNT', '500', '500', 'ÿҳ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (194, 'PARA_CATE', '1', '�ն˲���', '�������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (195, 'PARA_CATE', '2', '���������', '�������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (228, 'PARA_TYPE', '1', 'ͨ�Ų���', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (244, 'PARA_TYPE', '10', '�������', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (787, 'PARA_TYPE', '11', '��������', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (824, 'PARA_TYPE', '12', '�ܼ�������', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (70, 'PARA_TYPE', '14', '�������', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (475, 'PARA_TYPE', '15', '�õ����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (476, 'PARA_TYPE', '16', '���ò���', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (477, 'PARA_TYPE', '17', '��������', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (382, 'PARA_TYPE', '18', '�޹���������', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (335, 'PARA_TYPE', '19', '�������', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (229, 'PARA_TYPE', '2', '�������', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (230, 'PARA_TYPE', '3', '�¼�����', '��������', '0', null);
commit;
prompt 300 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (231, 'PARA_TYPE', '4', '��ֵ����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (819, 'PARA_TYPE', '8', '��������', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (570, 'PARA_TYPE', '9', '�ն�����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (81, 'PLA_CHAR', '1', '��糧', '�糧����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (82, 'PLA_CHAR', '2', '�˵�վ', '��վ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (83, 'PLA_CHAR', '4', 'ˮ��վ', '��վ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (84, 'PLA_CHAR', '5', '��糡', '��վ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (142, 'PLA_CHAR', '6', 'Сˮ��վ', '��վ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (783, 'PLUSE_CONSTANT', '0', '�����й���', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (784, 'PLUSE_CONSTANT', '1', '�����޹���', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (785, 'PLUSE_CONSTANT', '2', '�����й���', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (786, 'PLUSE_CONSTANT', '3', '�����޹���', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (379, 'POWER_FACTOR', '0-100', '0.8', '����>0,<=100', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (380, 'POWER_FACTOR', '100-160', '0.85', '����>100,<=160', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (381, 'POWER_FACTOR', '160', '0.9', '����>160', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (478, 'PR', '1', '�ַ�', '��Ȩ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (479, 'PR', '2', '�û�', '��Ȩ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1180, 'PRE_CHARGE_MODE', '0', '�翨', '��ֵ��ʽ', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1181, 'PRE_CHARGE_MODE', '1', 'Զ��', '��ֵ��ʽ', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1177, 'PRE_CHARGE_MODE', '2', 'Token', '��ֵ��ʽ', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1182, 'PRE_CHARGE_TYPE', '0', '���', '��ֵ����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1183, 'PRE_CHARGE_TYPE', '1', '����', '��ֵ����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1178, 'PRE_CRED_NAME', '0', '���֤', '֤������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1179, 'PRE_CRED_NAME', '1', '����֤', '֤������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1193, 'PRE_CUST_STATUS', '0', '������', '�û�״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1194, 'PRE_CUST_STATUS', '1', '����', '�û�״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1195, 'PRE_CUST_STATUS', '2', '����', '�û�״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1196, 'PRE_CUST_STATUS', '3', '��ͣ', '�û�״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1197, 'PRE_CUST_STATUS', '4', '����', '�û�״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1198, 'PRE_CUST_STATUS', '5', '������', '�û�״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1199, 'PRE_CUST_STATUS', '6', '����', '�û�״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1207, 'PRE_EQ', '1', '100', '�������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1208, 'PRE_EQ', '2', '200', '�������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1209, 'PRE_EQ', '3', '500', '�������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1210, 'PRE_EQ', '4', '1000', '�������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1211, 'PRE_EQ', '5', '2000', '�������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1212, 'PRE_EQ', '6', '200001', '�������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1191, 'PRE_FIXED_METHOD', '0', '��������ȡ', '�̶�������ȡ��ʽ', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1192, 'PRE_FIXED_METHOD', '1', '��������', '�̶�������ȡ��ʽ', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1200, 'PRE_METER_STATUS', '0', '���', '���״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1201, 'PRE_METER_STATUS', '1', '����', '���״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1202, 'PRE_METER_STATUS', '2', '��ͣ', '���״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1203, 'PRE_METER_STATUS', '3', '����', '���״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1213, 'PRE_MONEY', '1', '100', '������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1214, 'PRE_MONEY', '2', '200', '������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1215, 'PRE_MONEY', '3', '600', '������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1216, 'PRE_MONEY', '4', '1000', '������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1217, 'PRE_MONEY', '5', '2000', '������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1218, 'PRE_MONEY', '6', '20000', '������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1204, 'PRE_PAY_ENTITY', '10', '�ֽ�', '֧��ʵ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1205, 'PRE_PAY_ENTITY', '20', '�ֻ��˺�', '֧��ʵ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1206, 'PRE_PAY_ENTITY', '30', '�����˺�', '֧��ʵ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1184, 'PRE_PRICE_TYPE', '0', '������', '�������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1185, 'PRE_PRICE_TYPE', '1', '�����', '�������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1186, 'PRE_STEP_TYPE', '0', 'ȫ����', '���ݵ������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1187, 'PRE_STEP_TYPE', '1', '����1', '���ݵ������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1188, 'PRE_STEP_TYPE', '2', '����2', '���ݵ������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1189, 'PRE_STEP_TYPE', '3', '����3', '���ݵ������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1190, 'PRE_STEP_TYPE', '4', '����4', '���ݵ������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (37, 'PRI_MASTER', '4', '4', '���ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (38, 'PRI_MASTER', '5', '5', '���ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (39, 'PRI_MASTER', '6', '6', '���ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (40, 'PRI_MASTER', '7', '7', '���ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (41, 'PRI_MASTER', '8', '8', '���ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (274, 'PROTOCOL_METER', '10', 'ȫ��DLT645��Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (275, 'PROTOCOL_METER', '11', '�°�645��Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (276, 'PROTOCOL_METER', '20', '�㽭����ԼA', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (277, 'PROTOCOL_METER', '21', '�㽭����ԼB', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (278, 'PROTOCOL_METER', '31', '��ʤ��Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (279, 'PROTOCOL_METER', '32', '�����Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (280, 'PROTOCOL_METER', '33', '�������Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (281, 'PROTOCOL_METER', '34', '��¡��Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (282, 'PROTOCOL_METER', '35', '�����Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (283, 'PROTOCOL_METER', '36', '������D���Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (284, 'PROTOCOL_METER', '37', '��̹�Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (285, 'PROTOCOL_METER', '38', '��½��Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (286, 'PROTOCOL_METER', '39', '���ǹ�Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (287, 'PROTOCOL_METER', '40', '��������Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (288, 'PROTOCOL_METER', '41', 'ABB�Ħ����Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (289, 'PROTOCOL_METER', '42', 'ABBԲ���Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (290, 'PROTOCOL_METER', '43', '������Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (291, 'PROTOCOL_METER', '44', '����MK3���Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (292, 'PROTOCOL_METER', '45', '������Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (293, 'PROTOCOL_METER', '46', '������B���Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (294, 'PROTOCOL_METER', '47', '�����Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (295, 'PROTOCOL_METER', '48', '�������ӹ�Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (296, 'PROTOCOL_METER', '49', '"��÷����Լ"', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (297, 'PROTOCOL_METER', '50', '��˹����Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (298, 'PROTOCOL_METER', '51', '��ʤ����', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (299, 'PROTOCOL_METER', '55', '����˹�أ�Elster����Լ', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (300, 'PROTOCOL_METER', '60', '"����֧��·���װ�ù�Լ"', '����Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (314, 'PROTOCOL_TERM', '100', '698��Լ', '�ն˹�Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (336, 'PROTOCOL_TERM', '101', '�Ĵ�˫����Լ', '�ն˹�Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (339, 'PROTOCOL_TERM', '102', '�Ĵ�üɽ��Լ', '�ն˹�Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (340, 'PROTOCOL_TERM', '106', '������Լ', '�ն˹�Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (341, 'PROTOCOL_TERM', '120', '�㽭��Լ', '�ն˹�Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (342, 'PROTOCOL_TERM', '121', '�㽭������Լ', '�ն˹�Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (343, 'PROTOCOL_TERM', '122', '�㶫���û���Լ', '�ն˹�Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (344, 'PROTOCOL_TERM', '123', '�㶫���û���Լ���ڶ��棩', '�ն˹�Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (345, 'PROTOCOL_TERM', '124', '�㶫����Լ', '�ն˹�Լ', '0', null);
commit;
prompt 400 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (688, 'PROTOCOL_TERM', '125', '��������Լ', '�ն˹�Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (689, 'PROTOCOL_TERM', '126', '�㶫������Լ', '�ն˹�Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (690, 'PROTOCOL_TERM', '127', '�������û���Լ', '�ն˹�Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (691, 'PROTOCOL_TERM', '129', '�㽭�Զ����Լ', '�ն˹�Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (692, 'PROTOCOL_TERM', '146', '�㶫���վ��Լ', '�ն˹�Լ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (584, 'PT_RATIO', '1', '220/220', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (587, 'PT_RATIO', '10', '50/5', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (594, 'PT_RATIO', '100', '10000/100', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (595, 'PT_RATIO', '101', '10000��3/100/��3', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (596, 'PT_RATIO', '105', '10500/100', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (588, 'PT_RATIO', '11', '1100/100', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (597, 'PT_RATIO', '110', '11kV/100V', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (602, 'PT_RATIO', '1100', '110000/100', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (603, 'PT_RATIO', '1101', '110000��3/100/��3', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (585, 'PT_RATIO', '2', '10/5', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (589, 'PT_RATIO', '20', '100/5', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (598, 'PT_RATIO', '200', '1000/5', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (604, 'PT_RATIO', '2200', '220000/100', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (605, 'PT_RATIO', '2201', '220000��3/100/��3', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (590, 'PT_RATIO', '25', '10000/400', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (586, 'PT_RATIO', '3', '380/100', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (599, 'PT_RATIO', '350', '35000/100', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (591, 'PT_RATIO', '40', '200/5', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (606, 'PT_RATIO', '5000', '500000/100', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (592, 'PT_RATIO', '60', '6kV/100V', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (600, 'PT_RATIO', '600', '60kV/100V', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (593, 'PT_RATIO', '63', '6300/100', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (601, 'PT_RATIO', '660', '66kV/100V', 'PT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (186, 'PW_DATA_TYPE', '10', '�й�����', '��������-��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (187, 'PW_DATA_TYPE', '20', '�޹�����', '��������-��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (551, 'P_VALUE', '1', '����', '����ֵ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (552, 'P_VALUE', '2', '�ܾ�', '����ֵ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (559, 'QLF_MARK', '-1', '����', '�ϸ��ʶ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (829, 'QLF_MARK', '0', '���ϸ�', '�ϸ��ʶ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (828, 'QLF_MARK', '1', '�ϸ�', '�ϸ��ʶ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (607, 'RATED_EC', '10', '3A/6A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (620, 'RATED_EC', '100', '30A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1062, 'RATED_EC', '1000', '5(40)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (621, 'RATED_EC', '101', '5(6)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1063, 'RATED_EC', '1010', '10(60)', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1064, 'RATED_EC', '1020', '2.5(15)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1065, 'RATED_EC', '1030', '0.3(1.2)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (622, 'RATED_EC', '110', '15A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (623, 'RATED_EC', '111', '1A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (624, 'RATED_EC', '120', '25A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (625, 'RATED_EC', '121', '0.3A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1101, 'RATED_EC', '123', '123', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (626, 'RATED_EC', '130', '����', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (627, 'RATED_EC', '140', '5(40)', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (628, 'RATED_EC', '150', '2.5(20)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (629, 'RATED_EC', '160', '3(5)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (630, 'RATED_EC', '170', '3��5��100)', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (631, 'RATED_EC', '180', '10(80)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (632, 'RATED_EC', '190', '100.0A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (608, 'RATED_EC', '20', '5A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (633, 'RATED_EC', '200', '100A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (634, 'RATED_EC', '210', '1500A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (635, 'RATED_EC', '220', '150A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (636, 'RATED_EC', '230', '2.5(2.5)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (637, 'RATED_EC', '240', '2.5(5)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (638, 'RATED_EC', '250', '200A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (639, 'RATED_EC', '260', '250A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (640, 'RATED_EC', '270', '3(20)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (641, 'RATED_EC', '280', '30(120)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (642, 'RATED_EC', '290', '300A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (609, 'RATED_EC', '30', '10A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (643, 'RATED_EC', '300', '40/80A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (644, 'RATED_EC', '310', '400A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (645, 'RATED_EC', '320', '5(10)', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (646, 'RATED_EC', '330', '5(15)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (647, 'RATED_EC', '340', '5(5)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (648, 'RATED_EC', '350', '500A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (649, 'RATED_EC', '360', '50A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (650, 'RATED_EC', '370', '600A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (651, 'RATED_EC', '380', '800A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (652, 'RATED_EC', '390', '380/20V', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (610, 'RATED_EC', '40', '1.5(6)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (653, 'RATED_EC', '400', '380/22kV', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (654, 'RATED_EC', '410', '1.5/615A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (655, 'RATED_EC', '420', '1.5/6A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (656, 'RATED_EC', '430', '10/20A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (657, 'RATED_EC', '440', '10/400A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (658, 'RATED_EC', '450', '10/40A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (659, 'RATED_EC', '460', '10/600A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (660, 'RATED_EC', '470', '10/60A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (661, 'RATED_EC', '480', '10/80 8A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (662, 'RATED_EC', '490', '10/80A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (611, 'RATED_EC', '50', '5(10)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (663, 'RATED_EC', '500', '100/5A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (664, 'RATED_EC', '510', '1200A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (665, 'RATED_EC', '520', '15/60A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (666, 'RATED_EC', '530', '150/5A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (667, 'RATED_EC', '540', '16/60A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (668, 'RATED_EC', '550', '2./10A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (669, 'RATED_EC', '560', '2.0/10A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (670, 'RATED_EC', '570', '2.05/10A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (671, 'RATED_EC', '580', '2.0A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (672, 'RATED_EC', '590', '2.5./10A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (325, 'ADD_COULO_TYPE', '-1', '����', '����׷������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (326, 'ADD_COULO_TYPE', '1', '��׷��', '����׷������', '1', null);
commit;
prompt 500 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (327, 'ADD_COULO_TYPE', '2', '��׷��', '����׷������', '2', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (317, 'ALARM_GRADE', 'A', '��', 'Ԥ���ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (318, 'ALARM_GRADE', 'B', '��', 'Ԥ���ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (319, 'ALARM_GRADE', 'C', '��', 'Ԥ���ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (320, 'ALARM_GRADE', 'D', '����', 'Ԥ���ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (321, 'ALARM_GRADE', 'E', '����', 'Ԥ���ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (322, 'ALARM_GRADE', 'F', '����', 'Ԥ���ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (323, 'ALARM_GRADE', 'G', '����', 'Ԥ���ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (324, 'ALARM_GRADE', 'H', '����', 'Ԥ���ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (808, 'AN_CYCLE', '1', 'Сʱ', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (809, 'AN_CYCLE', '2', '��', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (810, 'AN_CYCLE', '3', '��', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (806, 'AN_TACTICS', '1', '�Զ�����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (807, 'AN_TACTICS', '2', '�ֶ�����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (580, 'AN_TACTICS', '3', '�Զ��ֶ���֧��', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (143, 'AREA', '1', '����', '����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (144, 'AREA', '2', 'ũ��', '����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (563, 'BS_TYPE', '1', '��ͣ', '��ͣ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (562, 'BS_TYPE', '2', '��ͣ�ָ�', '��ͣ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (561, 'BS_TYPE', '3', '����', '��ͣ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (560, 'BS_TYPE', '4', '���ݻָ�', '��ͣ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1093, 'BTL', '0', '300', '������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1094, 'BTL', '1', '600', '������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1095, 'BTL', '2', '1200', '������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1096, 'BTL', '3', '2400', '������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1097, 'BTL', '4', '4800', '������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1098, 'BTL', '5', '7200', '������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1099, 'BTL', '6', '9600', '������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1100, 'BTL', '7', '19200', '������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (346, 'BYPASS_DIRECT', '1', '����', '��·����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (347, 'BYPASS_DIRECT', '2', '����', '��·����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (85, 'CAN_BREAK', '1', '����բ', '��բ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (86, 'CAN_BREAK', '2', '������բ', '��բ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (540, 'CHANNEL_TYPE', '1', 'TCP', 'ͨ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (541, 'CHANNEL_TYPE', '2', 'UDP', 'ͨ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (534, 'COMM_MODE', '1', 'GPRS', 'ͨѶ��ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (535, 'COMM_MODE', '2', 'CDMA', 'ͨѶ��ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (536, 'COMM_MODE', '3', '230M', 'ͨѶ��ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (537, 'COMM_MODE', '4', '232����', 'ͨѶ��ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (538, 'COMM_MODE', '5', '��������', 'ͨѶ��ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (539, 'COMM_MODE', '6', '����', 'ͨѶ��ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (480, 'COMM_MODE', '7', '����ר��', 'ͨѶ��ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (79, 'COMM_MODE_GM', '1', '485', '�Ա�ͨѶ��ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (74, 'COMM_MODE_GM', '2', '�ز�', '�Ա�ͨѶ��ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (75, 'COMM_MODE_GM', '3', 'С����', '�Ա�ͨѶ��ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (76, 'COMM_MODE_GM', '4', 'ZIGBEE', '�Ա�ͨѶ��ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (699, 'CT_RATIO', '1', '5/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (708, 'CT_RATIO', '10', '50/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (733, 'CT_RATIO', '100', '500/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (766, 'CT_RATIO', '1000', '5000/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (767, 'CT_RATIO', '1001', '1000/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (734, 'CT_RATIO', '101', '50/100', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (735, 'CT_RATIO', '102', '100/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1073, 'CT_RATIO', '10500', '52500/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (709, 'CT_RATIO', '11', '55/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (768, 'CT_RATIO', '1100', '110000/100', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (736, 'CT_RATIO', '111', '3/10', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (710, 'CT_RATIO', '12', '60/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (737, 'CT_RATIO', '120', '600/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (769, 'CT_RATIO', '1201', '1200/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (738, 'CT_RATIO', '121', '120/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (739, 'CT_RATIO', '125', '625/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (711, 'CT_RATIO', '13', '60/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (712, 'CT_RATIO', '14', '65/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (740, 'CT_RATIO', '140', '700/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (713, 'CT_RATIO', '15', '75/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (741, 'CT_RATIO', '150', '750/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (770, 'CT_RATIO', '1500', '7500/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (771, 'CT_RATIO', '1501', '1500/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (742, 'CT_RATIO', '151', '150/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (714, 'CT_RATIO', '16', '80/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (743, 'CT_RATIO', '160', '800/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (772, 'CT_RATIO', '1601', '1600/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (773, 'CT_RATIO', '1602', '1600/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (744, 'CT_RATIO', '170', '850/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (715, 'CT_RATIO', '18', '90/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (745, 'CT_RATIO', '180', '900/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (700, 'CT_RATIO', '2', '10/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (716, 'CT_RATIO', '20', '100/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (746, 'CT_RATIO', '200', '1000/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (774, 'CT_RATIO', '2000', '10000/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1074, 'CT_RATIO', '20000', '100000/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (775, 'CT_RATIO', '2001', '2000/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (747, 'CT_RATIO', '201', '200/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (748, 'CT_RATIO', '210', '1050/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1075, 'CT_RATIO', '22000', '220000/10', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (717, 'CT_RATIO', '23', '115/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (718, 'CT_RATIO', '24', '120/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (749, 'CT_RATIO', '240', '1200/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (776, 'CT_RATIO', '2401', '2400/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (719, 'CT_RATIO', '25', '125/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (750, 'CT_RATIO', '250', '1250/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (777, 'CT_RATIO', '2500', '2500/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (701, 'CT_RATIO', '3', '15/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (720, 'CT_RATIO', '30', '150/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (751, 'CT_RATIO', '300', '1500/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (778, 'CT_RATIO', '3000', '15000/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1076, 'CT_RATIO', '30000', '150000/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (779, 'CT_RATIO', '3001', '3000/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (752, 'CT_RATIO', '301', '300/1', 'CT���', '1', null);
commit;
prompt 600 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (753, 'CT_RATIO', '320', '1600/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (754, 'CT_RATIO', '350', '35000/100', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (780, 'CT_RATIO', '3500', '3500/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1077, 'CT_RATIO', '35000', '165000/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (781, 'CT_RATIO', '3501', '17500/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (755, 'CT_RATIO', '360', '1800/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (702, 'CT_RATIO', '4', '20/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (721, 'CT_RATIO', '40', '200/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (756, 'CT_RATIO', '400', '2000/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (782, 'CT_RATIO', '4000', '20000/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1078, 'CT_RATIO', '40000', '200000/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1066, 'CT_RATIO', '4001', '4000/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (757, 'CT_RATIO', '401', '400/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (722, 'CT_RATIO', '41', '40/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (723, 'CT_RATIO', '45', '225/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (758, 'CT_RATIO', '480', '2400/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (703, 'CT_RATIO', '5', '25/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (724, 'CT_RATIO', '50', '250/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (759, 'CT_RATIO', '500', '2500/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1067, 'CT_RATIO', '5000', '5000/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1068, 'CT_RATIO', '5001', '25000/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (760, 'CT_RATIO', '501', '500/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (725, 'CT_RATIO', '51', '50/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1069, 'CT_RATIO', '5250', '26250/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (704, 'CT_RATIO', '6', '30/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (726, 'CT_RATIO', '60', '300/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (761, 'CT_RATIO', '600', '3000/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1070, 'CT_RATIO', '6000', '6000/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1071, 'CT_RATIO', '6001', '30000/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (762, 'CT_RATIO', '601', '600/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (727, 'CT_RATIO', '61', '6/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (728, 'CT_RATIO', '62', '60/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1079, 'CT_RATIO', '66000', '330000/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (705, 'CT_RATIO', '7', '35/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (763, 'CT_RATIO', '700', '3500/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (729, 'CT_RATIO', '72', '360/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (706, 'CT_RATIO', '8', '40/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (730, 'CT_RATIO', '80', '400/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (764, 'CT_RATIO', '800', '4000/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1072, 'CT_RATIO', '8000', '40000/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (765, 'CT_RATIO', '801', '800/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (731, 'CT_RATIO', '81', '8/1', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (707, 'CT_RATIO', '9', '11/3', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (732, 'CT_RATIO', '90', '450/5', 'CT���', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (520, 'CUR_STATUS', '1', '����', '��ǰ״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (521, 'CUR_STATUS', '2', '����', '��ǰ״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (522, 'CUR_STATUS', '6', '����', '��ǰ״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (523, 'CUR_STATUS', '7', 'ͣ��', '��ǰ״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (844, 'CUST_CURVE_DATA', '10', '�����й�', '�Զ��屨��--����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (845, 'CUST_CURVE_DATA', '11', '�����й�', '�Զ��屨��--����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (846, 'CUST_CURVE_DATA', '12', '�����޹�', '�Զ��屨��--����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (847, 'CUST_CURVE_DATA', '13', '�����޹�', '�Զ��屨��--����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (848, 'CUST_DATA_DENS', '10', 'Сʱ����', '�Զ��屨��-�����ܶ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (849, 'CUST_DATA_DENS', '11', '������', '�Զ��屨��-�����ܶ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (850, 'CUST_DATA_DENS', '12', '������', '�Զ��屨��-�����ܶ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (43, 'CUST_DATA_DENS', '15', '����������', '�Զ��屨��-�����ܶ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (851, 'CUST_DATA_TYPE', '10', '����', '�Զ��屨��--����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (852, 'CUST_DATA_TYPE', '11', '����', '�Զ��屨��--����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (853, 'CUST_DATA_TYPE', '12', '����', '�Զ��屨��--����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (854, 'CUST_PHASE_TYPE', '10', 'A��', '�Զ��屨��--����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (855, 'CUST_PHASE_TYPE', '11', 'B��', '�Զ��屨��--����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (856, 'CUST_PHASE_TYPE', '12', 'C��', '�Զ��屨��--����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1084, 'CUST_STATUS', '1', '����', '�û�״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1085, 'CUST_STATUS', '2', '�����', '�û�״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1086, 'CUST_STATUS', '3', '������', '�û�״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1087, 'CUST_STATUS', '4', '��ͣ', '�û�״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (517, 'CUST_TYPE', '1', '��ѹ', '�û�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (518, 'CUST_TYPE', '2', '��ѹ�Ǿ���', '�û�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (519, 'CUST_TYPE', '3', '��ѹ����', '�û�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1088, 'DATAGROUP_RM', '10000', '��������', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1089, 'DATAGROUP_RM', '10001', '��ǰ����', '��ǰ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1090, 'DATAGROUP_RM', '10002', 'Сʱ��������', 'Сʱ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1091, 'DATAGROUP_RM', '10003', '�ն�������', '�ն�������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1092, 'DATAGROUP_RM', '10004', '�¶�������', '�¶�������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (208, 'DATAGROUP_TASK_100', '100000', '��ǰ����', '698��Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (209, 'DATAGROUP_TASK_100', '100010', 'Сʱ��������', '698��Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (210, 'DATAGROUP_TASK_100', '100100', '�ն�������', '698��Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (211, 'DATAGROUP_TASK_100', '100110', '�����ն�������', '698��Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (212, 'DATAGROUP_TASK_100', '100200', '�¶�������', '698��Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (213, 'DATAGROUP_TASK_100', '100300', '��������', '698��Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (462, 'DATAGROUP_TASK_101', '101000', '��ǰ����', '�Ĵ�˫����Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (463, 'DATAGROUP_TASK_101', '101010', 'Сʱ��������', '�Ĵ�˫����Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (464, 'DATAGROUP_TASK_101', '101100', '�ն�������', '�Ĵ�˫����Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (465, 'DATAGROUP_TASK_101', '101110', '�����ն�������', '�Ĵ�˫����Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (466, 'DATAGROUP_TASK_101', '101200', '�¶�������', '�Ĵ�˫����Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (467, 'DATAGROUP_TASK_101', '101300', '��������', '�Ĵ�˫����Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (693, 'DATAGROUP_TASK_102', '102000', '��ǰ����', '�Ĵ�üɽ��Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (694, 'DATAGROUP_TASK_102', '102010', 'Сʱ��������', '�Ĵ�üɽ��Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (695, 'DATAGROUP_TASK_102', '102100', '�ն�������', '�Ĵ�üɽ��Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (696, 'DATAGROUP_TASK_102', '102110', '�����ն�������', '�Ĵ�üɽ��Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (697, 'DATAGROUP_TASK_102', '102200', '�¶�������', '�Ĵ�üɽ��Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (698, 'DATAGROUP_TASK_102', '102300', '��������', '�Ĵ�üɽ��Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (214, 'DATAGROUP_TASK_106', '106000', '��ǰ����', '������Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (215, 'DATAGROUP_TASK_106', '106010', 'Сʱ��������', '������Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (216, 'DATAGROUP_TASK_106', '106100', '�ն�������', '������Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (217, 'DATAGROUP_TASK_106', '106110', '�����ն�������', '������Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (218, 'DATAGROUP_TASK_106', '106200', '�¶�������', '������Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (219, 'DATAGROUP_TASK_106', '106300', '��������', '������Լ��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (220, 'DATAGROUP_TASK_120', '12010', '��ͨ����', '�㽭��Լ-����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (221, 'DATAGROUP_TASK_120', '12020', '�м�����', '�㽭��Լ-����', '0', null);
commit;
prompt 700 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (222, 'DATAGROUP_TASK_120', '12030', '�쳣����', '�㽭��Լ-����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (109, 'DATAGROUP_TASK_121', '12110', '��ͨ����', '�㽭������Լ-����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (110, 'DATAGROUP_TASK_121', '12120', '�м�����', '�㽭������Լ-����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (111, 'DATAGROUP_TASK_121', '12130', '�쳣����', '�㽭������Լ-����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (112, 'DATAGROUP_TASK_122', '12210', '��ͨ����', '�㶫���û���Լ-����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (113, 'DATAGROUP_TASK_122', '12220', '�м�����', '�㶫���û���Լ-����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (125, 'DATAGROUP_TASK_122', '12230', '�쳣����', '�㶫���û���Լ-����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (468, 'DATAGROUP_TASK_123', '12310', '��ͨ����', '�㶫���û���Լ���ڶ��棩-����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (469, 'DATAGROUP_TASK_124', '12410', '��ͨ����', '�㶫����Լ-����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1080, 'DATAGROUP_TASK_125', '12510', '��ͨ����', '��������Լ-����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (470, 'DATAGROUP_TASK_126', '12610', '��ͨ����', '�㶫������Լ-����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1081, 'DATAGROUP_TASK_127', '12710', '��ͨ����', '�������û���Լ-����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (132, 'DATAGROUP_TASK_129', '12910', '��ͨ����', '�㽭�Զ����Լ-����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (133, 'DATAGROUP_TASK_129', '12920', '�м�����', '�㽭�Զ����Լ-����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (134, 'DATAGROUP_TASK_129', '12930', '�쳣����', '�㽭�Զ����Լ-����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (471, 'DATAGROUP_TASK_146', '14610', '��ͨ����', '�㶫���վ��Լ-����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (117, 'DATE_TYPE', '1', '��', 'ͳ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (118, 'DATE_TYPE', '2', '��', 'ͳ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (119, 'DATE_TYPE', '3', '��', 'ͳ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (13, 'DC_CHK_TYPE', '01', '���볬��', '���ݼ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (14, 'DC_CHK_TYPE', '02', '����Ƿ�', '���ݼ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (15, 'DC_CHK_TYPE', '03', '���뵹��', '���ݼ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (48, 'DC_DATA_DENS', '10', '�ն�������', '���ݼ��-�����ܶ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (49, 'DC_DATA_DENS', '20', '�¶�������', '���ݼ��-�����ܶ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (50, 'DC_DATA_TYPE', '10', '�����й�', '���ݼ��-��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (51, 'DC_DATA_TYPE', '20', '�����й�', '���ݼ��-��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (52, 'DC_DATA_TYPE', '30', '�����޹�', '���ݼ��-��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (53, 'DC_DATA_TYPE', '40', '�����޹�', '���ݼ��-��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (8, 'DEAL_STATUS', '0', 'δ����', '��������״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (9, 'DEAL_STATUS', '1', '�ѵǼ�', '��������״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (10, 'DEAL_STATUS', '2', '���ɹ�', '��������״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (11, 'DEAL_STATUS', '3', '�Ѵ���', '��������״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (12, 'DEAL_STATUS', '4', '�ѹ鵵', '��������״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (91, 'DEV_TYPE', '1', 'I��', 'װ�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (92, 'DEV_TYPE', '2', 'II��', 'װ�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (93, 'DEV_TYPE', '3', 'III��', 'װ�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (94, 'DEV_TYPE', '4', 'IV��', 'װ�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (95, 'DEV_TYPE', '5', 'V��', 'װ�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (16, 'DI_DATA_DENS', '10', '�ն�������', '����¼��-�����ܶ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (18, 'DI_DATA_DENS', '11', '����������', '����¼��-�����ܶ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (17, 'DI_DATA_DENS', '20', '�¶�������', '����¼��-�����ܶ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (571, 'DQ_DATA_DENS', '10', '�ն�������', '��������-�����ܶ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (572, 'DQ_DATA_DENS', '11', '����������', '��������-�����ܶ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (573, 'DQ_DATA_DENS', '20', '�¶�������', '��������-�����ܶ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (574, 'DQ_DATA_DENS', '30', '��������', '��������-�����ܶ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (487, 'DQ_DATA_DENS', '40', 'ȫ������', '��������-�����ܶ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (488, 'DQ_DATA_DENS', '50', '��������', '��������-�����ܶ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (575, 'DQ_DATA_TYPE', '10', '��������', '��������-��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (576, 'DQ_DATA_TYPE', '20', '��������', '��������-��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (834, 'ELEC_APP_TYPE', '100', '��ҵ', '�õ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (857, 'ELEC_APP_TYPE', '101', '"��С����"', '�õ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (859, 'ELEC_APP_TYPE', '200', '"���������õ�"', '�õ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (858, 'ELEC_APP_TYPE', '300', '"ũҵ�����õ�"', '�õ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (860, 'ELEC_APP_TYPE', '301', '"ƶ����ũҵ�Ź��õ�"', '�õ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (861, 'ELEC_APP_TYPE', '400', '"һ�㹤��ҵ"', '�õ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (862, 'ELEC_APP_TYPE', '500', '"����"', '�õ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (863, 'ELEC_APP_TYPE', '900', '����', '�õ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (188, 'EQ_DATA_TYPE', '10', '�����й���', '��������-��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (189, 'EQ_DATA_TYPE', '11', '�����й���', '��������-��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (190, 'EQ_DATA_TYPE', '12', '�����й���', '��������-��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (191, 'EQ_DATA_TYPE', '13', '�����й�ƽ', '��������-��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (192, 'EQ_DATA_TYPE', '14', '�����й���', '��������-��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (71, 'EQ_LEVEL', '1', '0��100kWh', '�õ�ˮƽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (72, 'EQ_LEVEL', '2', '100��200kWh', '�õ�ˮƽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (73, 'EQ_LEVEL', '3', '300kWh����', '�õ�ˮƽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (460, 'EQ_TYPE', '1', '����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (461, 'EQ_TYPE', '2', '����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (801, 'EX_ATTR', '0', '�ն�', '�쳣����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (795, 'EX_ATTR', '1', '485���', '�쳣����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (802, 'EX_ATTR', '2', '����', '�쳣����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (803, 'EX_ATTR', '3', '�ܼ���', '�쳣����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (804, 'EX_ATTR', '4', 'ģ����', '�쳣����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (805, 'EX_ATTR', '5', '���û�', '�쳣����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (796, 'EX_CATE', '10', '�豸�쳣', '�쳣���', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (797, 'EX_CATE', '20', '�õ��쳣', '�쳣���', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (35, 'EX_CHAR', '0', '����', '�쳣����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (36, 'EX_CHAR', '1', '�ָ�', '�쳣����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1219, 'EX_DEAL_STATUS', '10', 'δ���', '��������״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1220, 'EX_DEAL_STATUS', '20', 'δ�ɹ�', '��������״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1221, 'EX_DEAL_STATUS', '30', '���ɹ�', '��������״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1222, 'EX_DEAL_STATUS', '40', '�ɹ�����', '��������״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1223, 'EX_DEAL_STATUS', '50', '������', '��������״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1224, 'EX_DEAL_STATUS', '60', '�ȴ�ȷ��', '��������״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1225, 'EX_DEAL_STATUS', '70', '������', '��������״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1226, 'EX_DEAL_STATUS', '80', '�ѹ鵵', '��������״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (798, 'EX_LARGE', '10', '�õ��쳣', '�쳣����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (799, 'EX_LARGE', '20', '����쳣', '�쳣����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (800, 'EX_LARGE', '30', '�ն��쳣', '�쳣����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (472, 'EX_LEVEL', '1', '����', '�¼�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (473, 'EX_LEVEL', '2', 'һ��', '�¼�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (474, 'EX_LEVEL', '3', '��Ҫ', '�¼�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (581, 'EX_TYPE', '1', '����װ�ü��', '�쳣����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (582, 'EX_TYPE', '2', '���������Լ��', '�쳣����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (583, 'EX_TYPE', '3', '���ݺ����Լ��', '�쳣����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (245, 'EX_TYPE', '4', '�õ���', '�쳣����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (255, 'EX_TYPE', '5', '�������õ���', '�쳣����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (874, 'FAULT_TYPE', '1', '�ն�(������)����վ��ͨѶ', '�豸��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1, 'FAULT_TYPE', '2', '�ն�(������)�����ͨѶ', '�豸��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (2, 'FAULT_TYPE', '3', 'ʱ�Ӵ���', '�豸��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (3, 'FAULT_TYPE', '4', '������', '�豸��������', '0', null);
commit;
prompt 800 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (4, 'FAULT_TYPE', '5', '�ն��¼�����', '�豸��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (5, 'FAULT_TYPE', '6', '�ն˿��ع���', '�豸��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (6, 'FAULT_TYPE', '7', '�ն���ͨѶδ����', '�豸��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (7, 'FAULT_TYPE', '8', '��ͨѶ����������', '�豸��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (87, 'FR_PHASE', '0', '��ȷ��', '������λ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (88, 'FR_PHASE', '1', 'A��', '������λ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (89, 'FR_PHASE', '2', 'B��', '������λ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (90, 'FR_PHASE', '3', 'C��', '������λ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (31, 'GM_STATUS', '1', '����', '�ɼ���״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (32, 'GM_STATUS', '2', '����', '�ɼ���״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (33, 'GM_STATUS', '6', '����', '�ɼ���״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (34, 'GM_STATUS', '7', 'ͣ��', '�ɼ���״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (542, 'GP_CHAR', '1', '485', '����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (543, 'GP_CHAR', '2', '����', '����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (544, 'GP_CHAR', '3', '�ܼ���', '����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (545, 'GP_CHAR', '4', 'ģ����', '����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (196, 'GP_CHAR', '5', '�����', '����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (197, 'GP_CHAR', '6', '�ն���Ϣ��', '����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (198, 'GP_CHAR', '7', '�����ִ�', '����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (199, 'GP_CHAR', '8', 'ֱ��ģ�������', '����������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (549, 'GP_STATUS', '0', '��Ч', '������״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (550, 'GP_STATUS', '1', '��Ч', '������״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (896, 'WORK_GROUP', '80288', '�࿲��Ӫ���ؿڱ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (897, 'WORK_GROUP', '803', 'ϼɽ��Ӫ��', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (898, 'WORK_GROUP', '80301', 'ϼɽ��Ӫ��һ��', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (899, 'WORK_GROUP', '80302', 'ϼɽ��Ӫ������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (900, 'WORK_GROUP', '80360', 'ϼɽװ���һ��', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (901, 'WORK_GROUP', '80388', 'ϼɽ��Ӫ���ؿڱ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (902, 'WORK_GROUP', '804', 'տ���г���', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (903, 'WORK_GROUP', '805', '�ͻ���������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (904, 'WORK_GROUP', '810', 'տ���ֱ���', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (905, 'WORK_GROUP', '811', 'տ��ũ�繫˾', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (906, 'WORK_GROUP', '81101', '�ϵ�������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (907, 'WORK_GROUP', '81102', '��ͷ������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (908, 'WORK_GROUP', '81103', '��ͷ������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (909, 'WORK_GROUP', '81104', '�ٶɹ�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (910, 'WORK_GROUP', '81105', 'Ǭ��������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (911, 'WORK_GROUP', '81106', '����������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (912, 'WORK_GROUP', '81107', 'ũ���', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (913, 'WORK_GROUP', '81108', '���¹�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (914, 'WORK_GROUP', '81109', '���⹩����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (915, 'WORK_GROUP', '81110', '�񰲹�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (916, 'WORK_GROUP', '81111', '̫ƽ������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (917, 'WORK_GROUP', '81112', '��ɽ������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (918, 'WORK_GROUP', '81113', '���޹�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (919, 'WORK_GROUP', '81114', '���򹩵���', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (920, 'WORK_GROUP', '81115', '־��������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (921, 'WORK_GROUP', '81188', 'տ��ũ�繫˾�ؿڱ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (922, 'WORK_GROUP', '812', '��ͷ��', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (923, 'WORK_GROUP', '81201', '��ͷװ����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (924, 'WORK_GROUP', '81288', '��ͷ�ֹؿڱ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (925, 'WORK_GROUP', '813', '������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (926, 'WORK_GROUP', '81301', '����װ����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (927, 'WORK_GROUP', '81388', '�����ֹؿڱ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (928, 'WORK_GROUP', '814', '���¾�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (929, 'WORK_GROUP', '81401', '����װ����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (930, 'WORK_GROUP', '81488', '���¾ֹؿڱ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (931, 'WORK_GROUP', '823', '��Ϫ�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (932, 'WORK_GROUP', '82301', '��Ϫ�г���', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (933, 'WORK_GROUP', '82310', '��Ϫ�г����ͻ�����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (934, 'WORK_GROUP', '82320', '��Ϫ�г�����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (935, 'WORK_GROUP', '82321', '��Ϫ���Ӫҵ��(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (936, 'WORK_GROUP', '82330', '��Ϫũ�繫˾(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (937, 'WORK_GROUP', '82331', '���ǹ�����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (938, 'WORK_GROUP', '82332', '����������(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (939, 'WORK_GROUP', '82333', '���Թ��������ɣ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (940, 'WORK_GROUP', '82334', '��̶���������ɣ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (941, 'WORK_GROUP', '82335', '���¹�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (942, 'WORK_GROUP', '82336', '���๩�������ɣ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (943, 'WORK_GROUP', '82337', '��̶������(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (944, 'WORK_GROUP', '82338', '���ڹ��������ɣ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (945, 'WORK_GROUP', '82339', '���¹�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (946, 'WORK_GROUP', '82340', '��¼������(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (947, 'WORK_GROUP', '82341', '��ͷ������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (948, 'WORK_GROUP', '82342', '���񹩵���', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (949, 'WORK_GROUP', '82343', '���¹�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (950, 'WORK_GROUP', '82344', '��̹�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (951, 'WORK_GROUP', '82345', 'ɳ�Ź�����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (952, 'WORK_GROUP', '82346', '���Ź�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (953, 'WORK_GROUP', '82347', '�뱱������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (954, 'WORK_GROUP', '82348', '���鹩����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (955, 'WORK_GROUP', '82349', '����������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (956, 'WORK_GROUP', '82350', '��ͤӪҵ��(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (957, 'WORK_GROUP', '82351', '���Թ�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (958, 'WORK_GROUP', '82352', '���๩����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (959, 'WORK_GROUP', '82353', '���ڹ�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (960, 'WORK_GROUP', '82354', '��̶������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (961, 'WORK_GROUP', '82355', '��ǹ�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (962, 'WORK_GROUP', '82360', '��Ϫũ��ؿ���', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (963, 'WORK_GROUP', '82366', '��Ϫ��Ӫ�ؿ���', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (964, 'WORK_GROUP', '82377', '��Ϫ��Ӫ��ֱ��', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (965, 'WORK_GROUP', '825', '���Ź����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (966, 'WORK_GROUP', '82510', '�������Ӫҵ��(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (967, 'WORK_GROUP', '82511', '������(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (968, 'WORK_GROUP', '82512', '�Ǳ���', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (969, 'WORK_GROUP', '82513', '������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (970, 'WORK_GROUP', '82520', '����ũ�繫˾(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (971, 'WORK_GROUP', '82521', '������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (972, 'WORK_GROUP', '82522', '�Ͱ���', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (973, 'WORK_GROUP', '82523', '���������ɣ�', 'ά������', '1', null);
commit;
prompt 900 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (974, 'WORK_GROUP', '82524', '������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (975, 'WORK_GROUP', '82525', '�����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (976, 'WORK_GROUP', '82526', '��β��', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (977, 'WORK_GROUP', '82527', '������(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (978, 'WORK_GROUP', '82528', '�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (979, 'WORK_GROUP', '82529', '������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (980, 'WORK_GROUP', '82530', 'ǰɽ��', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (981, 'WORK_GROUP', '82531', '������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (982, 'WORK_GROUP', '82532', '������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (983, 'WORK_GROUP', '82533', '������(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (984, 'WORK_GROUP', '82534', '������(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (985, 'WORK_GROUP', '82535', '��ɽ��', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (986, 'WORK_GROUP', '82536', '������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (987, 'WORK_GROUP', '82537', '������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (988, 'WORK_GROUP', '82540', '���ż�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (989, 'WORK_GROUP', '82550', '���Ų���', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (990, 'WORK_GROUP', '82560', '�������Ʋ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (991, 'WORK_GROUP', '82570', '�����г���', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (992, 'WORK_GROUP', '850', 'տ������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (993, 'WORK_GROUP', '851', '��Ϫ����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (994, 'WORK_GROUP', '860', 'տ���ּ�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (995, 'WORK_GROUP', '86011', '������ϼɽ��(����)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (996, 'WORK_GROUP', '86012', '������ϼɽ��(����)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (997, 'WORK_GROUP', '86021', '�������࿲��(����)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (998, 'WORK_GROUP', '86022', '�������࿲��(����)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (999, 'WORK_GROUP', '86032', '������ũ��(����)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1000, 'WORK_GROUP', '86033', '������ũ��(����)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1001, 'WORK_GROUP', '86044', '����������������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1002, 'WORK_GROUP', '86060', '������������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1003, 'WORK_GROUP', '86070', '���������°�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1004, 'WORK_GROUP', '86077', '��������׼��', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1005, 'WORK_GROUP', '86080', '��������ͷ��', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1006, 'WORK_GROUP', '86088', '�������Զ�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1007, 'WORK_GROUP', '861', '��Ϫ������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1008, 'WORK_GROUP', '870', 'տ��������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1009, 'WORK_GROUP', '871', '��Ϫ���Ʋ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1010, 'WORK_GROUP', '877', 'տ����Ϣ��', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1011, 'WORK_GROUP', '881', '���������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1012, 'WORK_GROUP', '88101', '������Ӫ����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1013, 'WORK_GROUP', '88102', '�Ǳ���Ӫ����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1014, 'WORK_GROUP', '88103', 'ʯ�ǹ�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1015, 'WORK_GROUP', '88104', '���̹��������ɣ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1016, 'WORK_GROUP', '88105', '��ɽ������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1017, 'WORK_GROUP', '88106', '��ˮ������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1018, 'WORK_GROUP', '88107', '��弹�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1019, 'WORK_GROUP', '88108', '���񹩵���', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1020, 'WORK_GROUP', '88109', '����������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1021, 'WORK_GROUP', '88110', '��ƽ������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1022, 'WORK_GROUP', '88111', '���幩����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1023, 'WORK_GROUP', '88112', 'ʯ�빩����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1024, 'WORK_GROUP', '88113', '�����������ɣ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1025, 'WORK_GROUP', '88114', 'Ӫ�й�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1026, 'WORK_GROUP', '88115', '��ɽ������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1027, 'WORK_GROUP', '88116', '�������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1028, 'WORK_GROUP', '88117', 'ʯ��������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1029, 'WORK_GROUP', '88118', '�ӵ̹�����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1030, 'WORK_GROUP', '88119', '���Ź�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1031, 'WORK_GROUP', '88120', '���幩����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1032, 'WORK_GROUP', '88121', '�Ӵ�������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1033, 'WORK_GROUP', '88122', 'ƽ̹������(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1034, 'WORK_GROUP', '88123', '�»�������(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1035, 'WORK_GROUP', '88124', 'ʯ�Ǳ��վ', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1036, 'WORK_GROUP', '88125', '��������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1037, 'WORK_GROUP', '88126', '���̹�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1038, 'WORK_GROUP', '88127', 'ʯ�빩����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1039, 'WORK_GROUP', '88150', '�����г���', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1040, 'WORK_GROUP', '88151', '������Ӫ����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1041, 'WORK_GROUP', '88160', '��������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1042, 'WORK_GROUP', '88170', '����������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1043, 'WORK_GROUP', '88180', '�������Ʋ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1044, 'WORK_GROUP', '88188', '�����ؿڱ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1045, 'WORK_GROUP', '88198', '���������->����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1046, 'WORK_GROUP', '88199', '����(����)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1047, 'WORK_GROUP', '882', '���ݹ����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1048, 'WORK_GROUP', '88201', '�г����ͻ�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1102, 'WORK_GROUP', '88202', '������Ӫ����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1103, 'WORK_GROUP', '88203', '������Ӫ����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1104, 'WORK_GROUP', '88204', '���ǹ�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1105, 'WORK_GROUP', '88205', '��ɳ������(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1106, 'WORK_GROUP', '88206', '����������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1107, 'WORK_GROUP', '88207', '��·������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1108, 'WORK_GROUP', '88208', '��ҹ�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1109, 'WORK_GROUP', '88209', '�Ƽҹ�����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1110, 'WORK_GROUP', '88210', '�ͼҹ�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1111, 'WORK_GROUP', '88211', '��ˮ������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1112, 'WORK_GROUP', '88212', '���˹�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1113, 'WORK_GROUP', '88213', '���񹩵���', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1114, 'WORK_GROUP', '88214', '���Ź�����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1115, 'WORK_GROUP', '88215', '���͹�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1116, 'WORK_GROUP', '88216', '����������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1117, 'WORK_GROUP', '88217', 'Ӣ��������(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1118, 'WORK_GROUP', '88218', '���繩����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1119, 'WORK_GROUP', '88219', '�׸߹�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1120, 'WORK_GROUP', '88220', '���﹩����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1121, 'WORK_GROUP', '88221', '��ʯ������(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1122, 'WORK_GROUP', '88222', '���¹�����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1123, 'WORK_GROUP', '88223', '������ũ����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1124, 'WORK_GROUP', '88224', '��湩����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1125, 'WORK_GROUP', '88225', '�Ҹ�ũ����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1126, 'WORK_GROUP', '88226', '���ǹ�����(��)', 'ά������', '1', null);
commit;
prompt 1000 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1127, 'WORK_GROUP', '88227', '�ջ�ũ����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1128, 'WORK_GROUP', '88228', '����ũ����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1129, 'WORK_GROUP', '88229', '�����γ���(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1130, 'WORK_GROUP', '88231', '��ɳ������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1131, 'WORK_GROUP', '88232', '�Ƽҹ�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1132, 'WORK_GROUP', '88233', '���Ź�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1133, 'WORK_GROUP', '88234', '��ʯ������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1134, 'WORK_GROUP', '88235', 'Ӣ��������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1135, 'WORK_GROUP', '88236', '���繩����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1136, 'WORK_GROUP', '88250', '�����г���', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1137, 'WORK_GROUP', '88260', '���ݼ�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1138, 'WORK_GROUP', '88270', '���ݲ���', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1139, 'WORK_GROUP', '88280', '�������Ʋ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1140, 'WORK_GROUP', '88288', '���ݹؿڱ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1141, 'WORK_GROUP', '88298', '���ݹ����->����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1142, 'WORK_GROUP', '88299', '����(����)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1143, 'WORK_GROUP', '883', '�⴨�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1144, 'WORK_GROUP', '88301', 'ǳˮ������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1145, 'WORK_GROUP', '88302', '��᪹�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1146, 'WORK_GROUP', '88303', '��ʯ������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1147, 'WORK_GROUP', '88304', '����۹�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1148, 'WORK_GROUP', '88305', '���͹�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1149, 'WORK_GROUP', '88306', '��ɽ��������(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1150, 'WORK_GROUP', '88307', '��β������(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1151, 'WORK_GROUP', '88308', '����������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1152, 'WORK_GROUP', '88309', '���Ĺ�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1153, 'WORK_GROUP', '88310', '���̹�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1154, 'WORK_GROUP', '88311', '���¹��������ɣ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1155, 'WORK_GROUP', '88312', '��ɽ������(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1156, 'WORK_GROUP', '88313', '��׺���������ɣ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1157, 'WORK_GROUP', '88314', '���Ź�����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1158, 'WORK_GROUP', '88316', 'Ӫҵһ��', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1159, 'WORK_GROUP', '88317', '÷¼�ڶ�������(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1160, 'WORK_GROUP', '88318', '���̹�����(��)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1161, 'WORK_GROUP', '88319', '����ά��1��', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1162, 'WORK_GROUP', '88320', '����ά��2��', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1163, 'WORK_GROUP', '88321', '����ά��3��', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1164, 'WORK_GROUP', '88340', '�⴨�г���', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1165, 'WORK_GROUP', '88341', 'Ӫҵ����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1166, 'WORK_GROUP', '88342', 'Ӫҵ����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1167, 'WORK_GROUP', '88343', '��׺������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1168, 'WORK_GROUP', '88344', '���¹�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1169, 'WORK_GROUP', '88350', '�⴨����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1170, 'WORK_GROUP', '88360', '�⴨������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1171, 'WORK_GROUP', '88370', '�⴨���Ʋ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1172, 'WORK_GROUP', '88388', '�⴨�ؿڱ�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1173, 'WORK_GROUP', '88398', '�⴨�����->����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1174, 'WORK_GROUP', '88399', '�⴨(����)', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1175, 'WORK_GROUP', '899', 'տ��������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (613, 'RATED_EC', '60', '1(10)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (673, 'RATED_EC', '600', '2.5//10A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (612, 'RATED_EC', '61', '2A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (674, 'RATED_EC', '610', '2.5/10.A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (675, 'RATED_EC', '620', '2.5/10A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (676, 'RATED_EC', '630', '2.5/10\A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (677, 'RATED_EC', '640', '2.5/114A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (678, 'RATED_EC', '650', '2.5/5A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (679, 'RATED_EC', '660', '2.5A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (680, 'RATED_EC', '670', '20/40A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (681, 'RATED_EC', '680', '20/80A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (682, 'RATED_EC', '690', '200/5A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (615, 'RATED_EC', '70', '1(2)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (683, 'RATED_EC', '700', '220A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (614, 'RATED_EC', '71', '2.5A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (684, 'RATED_EC', '710', '24/80A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (685, 'RATED_EC', '720', '250/5A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (686, 'RATED_EC', '730', '3/60A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (687, 'RATED_EC', '740', '3/6A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (167, 'RATED_EC', '750', '30/100A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (168, 'RATED_EC', '760', '30/120A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (169, 'RATED_EC', '770', '30/60A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (170, 'RATED_EC', '780', '300/220A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (171, 'RATED_EC', '790', '300/5A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (617, 'RATED_EC', '80', '5(20)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (172, 'RATED_EC', '800', '380/220A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (616, 'RATED_EC', '81', '1.5A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (173, 'RATED_EC', '810', '4/20A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (174, 'RATED_EC', '820', '40/5A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (175, 'RATED_EC', '830', '400/5A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (176, 'RATED_EC', '840', '5-20A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (177, 'RATED_EC', '850', '5/10A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (178, 'RATED_EC', '860', '5/20  7A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1049, 'RATED_EC', '870', '5/20A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1050, 'RATED_EC', '880', '5/22A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1051, 'RATED_EC', '890', '5/300A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (619, 'RATED_EC', '90', '10(40)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1052, 'RATED_EC', '900', '5/30A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (618, 'RATED_EC', '91', '3A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1053, 'RATED_EC', '910', '5/40A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1054, 'RATED_EC', '920', '5/6A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1055, 'RATED_EC', '930', '50/5A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1056, 'RATED_EC', '940', '500/5A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1057, 'RATED_EC', '950', '520A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1058, 'RATED_EC', '960', '75/5A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1059, 'RATED_EC', '970', '750/5A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1060, 'RATED_EC', '980', '80A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (1061, 'RATED_EC', '990', '1(1)A', '�����', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (481, 'RATED_VOLT', '1', '220V', '���ѹ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (482, 'RATED_VOLT', '2', '3?��380V', '���ѹ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (483, 'RATED_VOLT', '3', '3?��220/380V', '���ѹ', '0', null);
commit;
prompt 1100 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (484, 'RATED_VOLT', '4', '3?��100V', '���ѹ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (485, 'RATED_VOLT', '5', '3?��57.7/100V', '���ѹ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (486, 'RATED_VOLT', '6', '57.7', '���ѹ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (147, 'RELAY_TYPE', '0', '���м�', '�м�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (148, 'RELAY_TYPE', '1', 'һ���м�', '�м�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (149, 'RELAY_TYPE', '2', '�����м�', '�м�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (150, 'RELAY_TYPE', '3', '�����м�', '�м�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (151, 'RELAY_TYPE', '4', '�ļ��м�', '�м�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (152, 'RELAY_TYPE', '9', '�Զ��м�', '�м�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (19, 'RLDJ', '1', '<50', '�����ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (20, 'RLDJ', '2', '>=50,<100', '�����ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (21, 'RLDJ', '3', '>=100,<315', '�����ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (22, 'RLDJ', '4', '>=315,<500', '�����ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (23, 'RLDJ', '5', '>=500,800', '�����ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (24, 'RLDJ', '6', '>=800', '�����ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (25, 'ROLE_TYPE', '1', '��λ��ɫ', '��ɫ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (26, 'ROLE_TYPE', '2', '����Ȩ��', '��ɫ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (27, 'ROLE_TYPE', '3', 'ϵͳ����', '��ɫ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (315, 'RUN_STATUS', '1', '����', '����״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (316, 'RUN_STATUS', '2', '������', '����״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (329, 'RUN_STATUS', '3', 'ͣ��', '����״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (383, 'SAR', '1', 'տ��', '������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (153, 'SCH_TYPE', '1', '���', '�޵緽������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (154, 'SCH_TYPE', '2', '�ܷ�', '�޵緽������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (155, 'SCH_TYPE', '3', '�����޵�', '�޵緽������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (510, 'SHIFT_NO', '1', '����', '�������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (511, 'SHIFT_NO', '2', '2��', '�������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (512, 'SHIFT_NO', '3', '3��', '�������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (513, 'SHIFT_NO', '4', '��������', '�������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (162, 'SL_MONITORING_0002', '1', '��λ', 'ͳ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (163, 'SL_MONITORING_0002', '2', '���վ', 'ͳ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (164, 'SL_MONITORING_0002', '3', '��·', 'ͳ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (348, 'SS_TYPE', '1', 'ȫ���ܹ�����', '���۵�������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (349, 'SS_TYPE', '2', 'ȫ����������', '���۵�������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (350, 'SS_TYPE', '3', 'ȫ����������', '���۵�������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (351, 'SS_TYPE', '4', 'ȫ�����۵���', '���۵�������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (352, 'SS_TYPE', '5', '�����ܹ�����', '���۵�������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (353, 'SS_TYPE', '6', 'ר���û����۵���', '���۵�������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (96, 'STATUS', '1', '����', '����״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (97, 'STATUS', '2', '������', '����״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (98, 'STATUS', '3', '������', '����״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (99, 'STATUS', '4', '�������', '����״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (100, 'STATUS', '5', '�ѹ鵵', '����״̬', null, null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (503, 'SUBS_STATUS', '1', '����', '���վ״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (504, 'SUBS_STATUS', '2', 'ͣ��', '���վ״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (505, 'SUBS_STATUS', '3', '���', '���վ״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (77, 'SUBS_TYPE', '1', '���վ', '��վ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (78, 'SUBS_TYPE', '2', '�糧', '��վ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (80, 'SUBS_TYPE', '3', '����վ', '��վ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (226, 'SW_ATTR', '1', '����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (227, 'SW_ATTR', '2', '����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (814, 'SW_STATUS', '0', '�Ͽ�', '����״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (813, 'SW_STATUS', '1', '�պ�', '����״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (825, 'SYS_LOG_TYPE', '1', '�洢����ִ�д���', 'ϵͳ��־����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (826, 'SYS_LOG_TYPE', '2', 'JOB����', 'ϵͳ��־����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (827, 'SYS_LOG_TYPE', '3', '��ռ�ʹ�����', 'ϵͳ��־����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (830, 'SYS_OBJECT', '1', 'ר���û�', 'ϵͳ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (831, 'SYS_OBJECT', '2', '���', 'ϵͳ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (832, 'SYS_OBJECT', '3', '��ѹ�û�', 'ϵͳ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (833, 'SYS_OBJECT', '4', '���վ', 'ϵͳ����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (888, 'SYS_SYMBOL', '-1', 'ȫ��', 'ȫ��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (877, 'SYS_SYMBOL', '1', '>', '���ں�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (878, 'SYS_SYMBOL', '10', '<=', 'С�ڵ���', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (879, 'SYS_SYMBOL', '20', '>=', '���ڵ���', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (880, 'SYS_SYMBOL', '3', '<', 'С�ں�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (54, 'SYS_TIME', '1', '��', '��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (55, 'SYS_TIME', '2', '��', '��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (56, 'SYS_TIME', '3', '��', '��', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (57, 'SYS_TIME', '4', '����', '����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (579, 'S_TYPE', '1', '230M�û�����Ⱥ', '�������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (578, 'S_TYPE', '2', '�ܷ巽��', '�������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (577, 'S_TYPE', '3', '��巽��', '�������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (200, 'TASK_TYPE', '1', '�Զ�����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (201, 'TASK_TYPE', '2', '��վ����', '��������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (101, 'TERM_MODEL', '1', 'BF001', '�ն��ͺ�', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (102, 'TERM_MODEL', '2', 'BF002', '�ն��ͺ�', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (866, 'TERM_TP_TYPE', '1', '����ר���ն�', '����ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (864, 'TERM_TP_TYPE', '10', '�������վ�ն�', '����ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (865, 'TERM_TP_TYPE', '11', '���߱��վ�ն�', '����ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (506, 'TERM_TP_TYPE', '2', '��������ն�', '����ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (507, 'TERM_TP_TYPE', '3', '����������', '����ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (508, 'TERM_TP_TYPE', '4', '����Զ����', '����ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (509, 'TERM_TP_TYPE', '5', '230Mר���ն�', '����ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (553, 'TERM_TP_TYPE', '6', '����ר���ն�', '����ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (554, 'TERM_TP_TYPE', '7', '��������ն�', '����ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (555, 'TERM_TP_TYPE', '8', '���߼�����', '����ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (556, 'TERM_TP_TYPE', '9', '����Զ����', '����ģ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (301, 'TERM_TYPE', '01', '���ɿ����ն�', '�ն�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (302, 'TERM_TYPE', '02', '���ɼ���ն�', '�ն�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (303, 'TERM_TYPE', '03', '��ͨ����ն�', '�ն�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (304, 'TERM_TYPE', '04', '������ն�', '�ն�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (305, 'TERM_TYPE', '05', '��ѹ������', '�ն�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (306, 'TERM_TYPE', '06', '��ѹ�ɼ��ն�', '�ն�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (307, 'TERM_TYPE', '07', '�ɼ�ģ���', '�ն�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (308, 'TERM_TYPE', '08', '�ؿڵ������ն�', '�ն�����', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (569, 'TG_STATUS', '1', '����', '̨��״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (568, 'TG_STATUS', '2', 'ͣ��', '̨��״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (567, 'TG_STATUS', '3', '���', '̨��״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (145, 'TRAN_CODE', '1', 'S11-M.RD��', '��ѹ���ͺ�', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (146, 'TRAN_CODE', '2', 'S10-M.RD��', '��ѹ���ͺ�', '1', null);
commit;
prompt 1200 records committed...
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (871, 'TRAN_STATUS', '1', '����', '��ѹ��״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (872, 'TRAN_STATUS', '2', 'ͣ��', '��ѹ��״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (873, 'TRAN_STATUS', '3', '���', '��ѹ��״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (867, 'TRAN_TYPE', '1', '����', '��ѹ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (868, 'TRAN_TYPE', '2', 'վ�ñ�', '��ѹ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (869, 'TRAN_TYPE', '3', '����', '��ѹ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (870, 'TRAN_TYPE', '4', 'ר��', '��ѹ������', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (789, 'USER_STATUS', '0', 'ͣ��', '�û�״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (788, 'USER_STATUS', '1', '����', '�û�״̬', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (524, 'VOLT_GRADE', '1', '110KV', '��ѹ�ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (525, 'VOLT_GRADE', '2', '220KV', '��ѹ�ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (526, 'VOLT_GRADE', '3', '500KV', '��ѹ�ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (527, 'VOLT_GRADE', '4', '10KV', '��ѹ�ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (528, 'VOLT_GRADE', '5', '35KV', '��ѹ�ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (529, 'VOLT_GRADE', '6', '380V', '��ѹ�ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (530, 'VOLT_GRADE', '7', '220V', '��ѹ�ȼ�', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (531, 'WIRING_MODE', '1', '����', '���߷�ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (532, 'WIRING_MODE', '3', '��������', '���߷�ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (533, 'WIRING_MODE', '4', '��������', '���߷�ʽ', '0', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (889, 'WORK_GROUP', '800', 'տ�������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (890, 'WORK_GROUP', '80022', 'տ���г�����', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (891, 'WORK_GROUP', '80099', 'տ���г����󻧰�', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (892, 'WORK_GROUP', '802', '�࿲��Ӫ��', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (893, 'WORK_GROUP', '80201', '�࿲��Ӫ��һ��', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (894, 'WORK_GROUP', '80202', '�࿲��Ӫ������', 'ά������', '1', null);
insert into A_CODE (CODE_ID, CODE_CATE, CODE, NAME, REMARK, CODE_TYPE, VALUE)
values (895, 'WORK_GROUP', '80280', '�࿲װ���һ��', 'ά������', '1', null);
commit;
prompt 1226 records loaded
prompt Enabling triggers for A_CODE...
alter table A_CODE enable all triggers;
set feedback on
set define on
prompt Done.
