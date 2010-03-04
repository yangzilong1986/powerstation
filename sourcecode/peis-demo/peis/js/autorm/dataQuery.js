/**
 * 改变查询方式 obj 查询方式��ʽ
 */
function changeQueryType(obj) {
	var lastTd = document.getElementById("query" + lastQueryType);
	var nowTd = document.getElementById("query" + obj);
	lastTd.className = 'tab_off';
	nowTd.className = 'tab_on';

	lastQueryType = obj;
	var url = contextPath + "/jsp/autorm/";
	if(lastQueryType == 1) {              //表码数据
		url += "meterQuery.html";
	}
	else if(lastQueryType == 2) {         //功率数据
		url += "powerQuery.html";
	}
	else if(lastQueryType == 3) {         //电压数据
		url += "voltQuery.html";
	}
	else if(lastQueryType == 4) {         //电流数据
		url += "ecurQuery.html";
	}
	else if(lastQueryType == 5) {         //功率因数
		url += "pfQuery.html";
	}
	else if(lastQueryType == 6) {         //谐波数据
		url += "";
	}
	else if(lastQueryType == 7) {         //事件数据
		url += "";
	}
    else if(lastQueryType == 8) {         //电量数据
        url += "elecQuery.html";
    }
	document.getElementById("dataQuery").src = url;
}

// 打开查看窗口
function openNewWindow(url, width, ht) {
	if (opwindow != null) {
		opwindow.close();
	}
	feature = 'width=' + width + ',height=' + ht
			+ ',menubar=no,toolbar=no,location=no,';
	feature += 'top=' + (screen.availHeight - ht) / 2 + ', left='
			+ (screen.availWidth - width) / 2
			+ 'scrollbars=auto,status=no,modal=yes';
	opwindow = window.open(url, '', feature);
}

// 电量数据查询页面初始化
function elecInit() {
    date = document.getElementsByName("date")[0].value;
    year = document.getElementsByName("year")[0].value;
    month = document.getElementsByName("month")[0].value;
    meterDTChange();
    var dateType = document.getElementsByName("dateType")[0];
    var showId = document.getElementsByName("showType")[0].value;
    lastShowType = showId;
    var showTD = document.getElementById("show" + lastShowType);
    showTD.className = 'target_on';
    var showTR = document.getElementById("QUERY_TR_" + lastShowType);
    showTR.style.display = "";
    if (dateType.value == '1') {
        for (i = 1; i <= 6; i++) {
            var tableObj = document.getElementById("data_table_" + i);
            for (j = 0; j < tableObj.rows.length; j++) {
                var td = tableObj.rows[j].cells[4];
                td.style.display = "";
            }
        }
//      document.getElementById("PAGE_DAY").style.display = "none";
//      document.getElementById("PAGE_MONTH").style.display = "none";
//      document.getElementById("PAGE_GATHER").style.display = "";
    }
    if (dateType.value == '2') {
        for (i = 1; i <= 5; i++) {
            var tableObj = document.getElementById("data_table_" + i);
            var cellIndex = tableObj.rows[0].cells.length - 2;
            for (j = 0; j < tableObj.rows.length; j++) {
                var td = tableObj.rows[j].cells[cellIndex];
                td.style.display = "";
            }
        }
//      document.getElementById("PAGE_DAY").style.display = "";
//      document.getElementById("PAGE_MONTH").style.display = "none";
//      document.getElementById("PAGE_GATHER").style.display = "none";
    }
    if (dateType.value == '3') {
        for (i = 1; i <= 6; i++) {
            var tableObj = document.getElementById("data_table_" + i);
            var cellIndex = tableObj.rows[0].cells.length - 1;
            for (j = 0; j < tableObj.rows.length; j++) {
                var td = tableObj.rows[j].cells[cellIndex];
                td.style.display = "";
            }
        }
//      document.getElementById("PAGE_DAY").style.display = "none";
//      document.getElementById("PAGE_MONTH").style.display = "";
//      document.getElementById("PAGE_GATHER").style.display = "none";
    }
}

// 表码查询页面初始化���ʼ��
function meterInit() {
	date = document.getElementsByName("date")[0].value;
	year = document.getElementsByName("year")[0].value;
	month = document.getElementsByName("month")[0].value;
	meterDTChange();
	var dateType = document.getElementsByName("dateType")[0];
	var showId = document.getElementsByName("showType")[0].value;
	lastShowType = showId;
	var showTD = document.getElementById("show" + lastShowType);
	showTD.className = 'target_on';
	var showTR = document.getElementById("QUERY_TR_" + lastShowType);
	showTR.style.display = "";
	if (dateType.value == '1') {
		for (i = 1; i <= 6; i++) {
			var tableObj = document.getElementById("data_table_" + i);
			for (j = 0; j < tableObj.rows.length; j++) {
				var td = tableObj.rows[j].cells[4];
				td.style.display = "";
			}
		}
//		document.getElementById("PAGE_DAY").style.display = "none";
//		document.getElementById("PAGE_MONTH").style.display = "none";
//		document.getElementById("PAGE_GATHER").style.display = "";
	}
	if (dateType.value == '2') {
		for (i = 1; i <= 5; i++) {
			var tableObj = document.getElementById("data_table_" + i);
			var cellIndex = tableObj.rows[0].cells.length - 2;
			for (j = 0; j < tableObj.rows.length; j++) {
				var td = tableObj.rows[j].cells[cellIndex];
				td.style.display = "";
			}
		}
//		document.getElementById("PAGE_DAY").style.display = "";
//		document.getElementById("PAGE_MONTH").style.display = "none";
//		document.getElementById("PAGE_GATHER").style.display = "none";
	}
	if (dateType.value == '3') {
		for (i = 1; i <= 6; i++) {
			var tableObj = document.getElementById("data_table_" + i);
			var cellIndex = tableObj.rows[0].cells.length - 1;
			for (j = 0; j < tableObj.rows.length; j++) {
				var td = tableObj.rows[j].cells[cellIndex];
				td.style.display = "";
			}
		}
//		document.getElementById("PAGE_DAY").style.display = "none";
//		document.getElementById("PAGE_MONTH").style.display = "";
//		document.getElementById("PAGE_GATHER").style.display = "none";
	}
}
// 改变表码查询的数据类型���������
function meterDTChange() {
	var dateType = document.getElementsByName("dateType")[0];
	if (dateType.value == "1") {
		document.getElementById("DATA_DAY").style.display = "none";
		document.getElementById("DATA_MONTH").style.display = "";
		document.getElementsByName("sqlcode")[0].value = "QUERY0004";
	} else if (dateType.value == "2") {
		document.getElementById("DATA_DAY").style.display = "";
		document.getElementById("DATA_MONTH").style.display = "none";
		document.getElementsByName("sqlcode")[0].value = "QUERY0002";
	} else if (dateType.value == "3") {
		document.getElementById("DATA_DAY").style.display = "none";
		document.getElementById("DATA_MONTH").style.display = "";
		document.getElementsByName("sqlcode")[0].value = "QUERY0003";
	}
}
// 改变表码查询显示内容��ʾ����
function changeShowType(obj) {
	var lastTd = document.getElementById("show" + lastShowType);
	var nowTd = document.getElementById("show" + obj);
	var lastTR = document.getElementById("QUERY_TR_" + lastShowType);
	var nowTR = document.getElementById("QUERY_TR_" + obj);
	lastTd.className = 'target_off';
	nowTd.className = 'target_on';
	lastTR.style.display = "none";
	nowTR.style.display = "";
	lastShowType = obj;
	document.getElementsByName("showType")[0].value = lastShowType;
}

// 表码数据查看详细
// showType: 1: 正向有功; 2: 反向有功; 3: 正向无功;
// 4: 反向无功; 5: 无功; 6: 需量;
// dataType： 2: 日数据曲线; 3: 月数据�����
function meterDetail(width, ht, gp_id, sys_object, object_no, dataType) {
	var url;
	var action;
	var showType = document.getElementsByName("showType")[0].value;
	url = contextPath + "/do/autorm/dataQuery.do"
	if (dataType == 2) {
		url = url + "?action=meterDayData";
		url = url + "&dataTime=" + date;
	}
	if (dataType == 3) {
		url = url + "?action=meterMonthData";
		url = url + "&year=" + year;
		url = url + "&month=" + month;
	}
	url = url + "&sys_object=" + sys_object;
	url = url + "&object_no=" + object_no;
	url = url + "&gp_id=" + gp_id;
	url = url + "&showType=" + showType;

	openNewWindow(url, width, ht);
}
//功率查询初始化��ʼ��
function powerInit() {
	date = document.getElementsByName("date")[0].value;
	year = document.getElementsByName("year")[0].value;
	month = document.getElementsByName("month")[0].value;
	powerDTChange();
	var dateType = document.getElementsByName("dateType")[0];
	var showId = document.getElementsByName("showType")[0].value;
	lastShowType = showId;
	var showTD = document.getElementById("show" + lastShowType);
	showTD.className = 'target_on';
	var showTR = document.getElementById("QUERY_TR_" + lastShowType);
	showTR.style.display = "";
	if (dateType.value == '2') {
		var tableObj = document.getElementById("data_table");
		var cellIndex = tableObj.rows[0].cells.length - 1;
		for (j = 4; j < tableObj.rows.length; j++) {
			var td = tableObj.rows[j].cells[cellIndex];
			td.style.display = "";
		}
//		document.getElementById("PAGE_DAY").style.display = "";
//		document.getElementById("PAGE_MONTH").style.display = "none";
	}
	if (dateType.value == '3') {
		var tableObj = document.getElementById("data_table");
		var cellIndex = tableObj.rows[0].cells.length;
		for (j = 4; j < tableObj.rows.length; j++) {
			var td = tableObj.rows[j].cells[cellIndex];
			td.style.display = "";
		}
//		document.getElementById("PAGE_DAY").style.display = "none";
//		document.getElementById("PAGE_MONTH").style.display = "";
	}
}
//�ı改变功率查询数据类型�������
function powerDTChange() {
	var dateType = document.getElementsByName("dateType")[0];
	if (dateType.value == "2") {
		document.getElementById("DATA_DAY").style.display = "";
		document.getElementById("DATA_MONTH").style.display = "none";
		document.getElementsByName("sqlcode")[0].value = "QUERY0010";
	} else if (dateType.value == "3") {
		document.getElementById("DATA_DAY").style.display = "none";
		document.getElementById("DATA_MONTH").style.display = "";
		document.getElementsByName("sqlcode")[0].value = "QUERY0012";
	}
}
//�ı改变功率查询显示内容��ʾ����
function powerShowType(obj) {
	var lastTd = document.getElementById("show" + lastShowType);
	var nowTd = document.getElementById("show" + obj);
	var lastTR = document.getElementById("QUERY_TR_" + lastShowType);
	var nowTR = document.getElementById("QUERY_TR_" + obj);
	var seq;
	lastTd.className = 'target_off';
	nowTd.className = 'target_on';
	lastTR.style.display = "none";
	nowTR.style.display = "";
	lastShowType = obj;
	document.getElementsByName("showType")[0].value = lastShowType;
	if (obj == 1)
		seq = "Z";
	if (obj == 2)
		seq = "A";
	if (obj == 3)
		seq = "B";
	if (obj == 4)
		seq = "C";
	document.getElementsByName("seq")[0].value = seq;
	//document.forms[0].submit();
}

// 功率数据查看详细
// showType: 1: 三相总; 2: A相; 3: B相; 4: C相;
// dataType： 2: 日数据曲线; 3: 月数据�����
function powerDetail(width, ht, gp_id, sys_object, object_no, dataType) {
	var url;
	var action;
	var showType = document.getElementsByName("showType")[0].value;
	var seq = document.getElementsByName("seq")[0].value;
	url = contextPath + "/do/autorm/dataQuery.do"
	if (dataType == 2) {
		url = url + "?action=powerDayData";
		url = url + "&dataTime=" + date;
	}
	if (dataType == 3) {
		url = url + "?action=powerMonthData";
		url = url + "&year=" + year;
		url = url + "&month=" + month;
	}
	url = url + "&sys_object=" + sys_object;
	url = url + "&object_no=" + object_no;
	url = url + "&gp_id=" + gp_id;
	url = url + "&showType=" + showType;
    url = url + "&seq="+seq;
	openNewWindow(url, width, ht);
}

//��电压查询初始化��ʼ��
function voltInit() {
	date = document.getElementsByName("date")[0].value;
	year = document.getElementsByName("year")[0].value;
	month = document.getElementsByName("month")[0].value;
	voltDTChange();
	var dateType = document.getElementsByName("dateType")[0];
	var showId = document.getElementsByName("showType")[0].value;
	lastShowType = showId;
	var showTD = document.getElementById("show" + lastShowType);
	showTD.className = 'target_on';
	var showTR = document.getElementById("QUERY_TR_" + lastShowType);
	showTR.style.display = "";
	if (dateType.value == '2') {
		var tableObj = document.getElementById("data_table");
		var cellIndex = tableObj.rows[0].cells.length - 1;
		for (j = 3; j < tableObj.rows.length; j++) {
			var td = tableObj.rows[j].cells[cellIndex];
			td.style.display = "";
		}
//		document.getElementById("PAGE_DAY").style.display = "";
//		document.getElementById("PAGE_MONTH").style.display = "none";
	}
	if (dateType.value == '3') {
		var tableObj = document.getElementById("data_table");
		var cellIndex = tableObj.rows[0].cells.length;
		for (j = 3; j < tableObj.rows.length; j++) {
			var td = tableObj.rows[j].cells[cellIndex];
			td.style.display = "";
		}
//		document.getElementById("PAGE_DAY").style.display = "none";
//		document.getElementById("PAGE_MONTH").style.display = "";
	}
}

//改变电压查询数据类型�������
function voltDTChange() {
	var dateType = document.getElementsByName("dateType")[0];
	if (dateType.value == "2") {
		document.getElementById("DATA_DAY").style.display = "";
		document.getElementById("DATA_MONTH").style.display = "none";
		document.getElementsByName("sqlcode")[0].value = "QUERY0014";
	} else if (dateType.value == "3") {
		document.getElementById("DATA_DAY").style.display = "none";
		document.getElementById("DATA_MONTH").style.display = "";
		document.getElementsByName("sqlcode")[0].value = "QUERY0015";
	}
}
//�ı��改变电压查询显示内容��ʾ����
function voltShowType(obj) {
	var lastTd = document.getElementById("show" + lastShowType);
	var nowTd = document.getElementById("show" + obj);
	var lastTR = document.getElementById("QUERY_TR_" + lastShowType);
	var nowTR = document.getElementById("QUERY_TR_" + obj);
	var seq;
	lastTd.className = 'target_off';
	nowTd.className = 'target_on';
	lastTR.style.display = "none";
	nowTR.style.display = "";
	lastShowType = obj;
	document.getElementsByName("showType")[0].value = lastShowType;
	if (obj == 1)
		seq = "A";
	if (obj == 2)
		seq = "B";
	if (obj == 3)
		seq = "C";
	document.getElementsByName("seq")[0].value = seq;
	//document.forms[0].submit();
}

// 电压数据查看详细
// showType: 1: A相; 2: B相; 3: C相;
// dataType： 2: 日数据曲线; 3: 月数据�����
function voltDetail(width, ht, gp_id, sys_object, object_no, dataType) {
	var url;
	var action;
	var showType = document.getElementsByName("showType")[0].value;
	var seq = document.getElementsByName("seq")[0].value;
	url = contextPath + "/do/autorm/dataQuery.do"
	if (dataType == 2) {
		url = url + "?action=voltDayData";
		url = url + "&dataTime=" + date;
	}
	if (dataType == 3) {
		url = url + "?action=voltMonthData";
		url = url + "&year=" + year;
		url = url + "&month=" + month;
	}
	url = url + "&sys_object=" + sys_object;
	url = url + "&object_no=" + object_no;
	url = url + "&gp_id=" + gp_id;
	url = url + "&showType=" + showType;
    url = url + "&seq="+seq;
	openNewWindow(url, width, ht);
}

//�����电流查询初始化��ʼ��
function ecurInit() {
	date = document.getElementsByName("date")[0].value;
	year = document.getElementsByName("year")[0].value;
	month = document.getElementsByName("month")[0].value;
	ecurDTChange();
	var dateType = document.getElementsByName("dateType")[0];
	var showId = document.getElementsByName("showType")[0].value;
	lastShowType = showId;
	var showTD = document.getElementById("show" + lastShowType);
	showTD.className = 'target_on';
	var showTR = document.getElementById("QUERY_TR_" + lastShowType);
	showTR.style.display = "";
	if (dateType.value == '2') {
		var tableObj = document.getElementById("data_table");
		var cellIndex = tableObj.rows[0].cells.length - 1;
		for (j = 4; j < tableObj.rows.length; j++) {
			var td = tableObj.rows[j].cells[cellIndex];
			td.style.display = "";
		}
//		document.getElementById("PAGE_DAY").style.display = "";
//		document.getElementById("PAGE_MONTH").style.display = "none";
	}
	if (dateType.value == '3') {
		var tableObj = document.getElementById("data_table");
		var cellIndex = tableObj.rows[0].cells.length;
		for (j = 4; j < tableObj.rows.length; j++) {
			var td = tableObj.rows[j].cells[cellIndex];
			td.style.display = "";
		}
//		document.getElementById("PAGE_DAY").style.display = "none";
//		document.getElementById("PAGE_MONTH").style.display = "";
	}
}

//改变电流查询数据类型�������
function ecurDTChange() {
	var dateType = document.getElementsByName("dateType")[0];
	if (dateType.value == "2") {
		document.getElementById("DATA_DAY").style.display = "";
		document.getElementById("DATA_MONTH").style.display = "none";
		document.getElementsByName("sqlcode")[0].value = "QUERY0018";
	} else if (dateType.value == "3") {
		document.getElementById("DATA_DAY").style.display = "none";
		document.getElementById("DATA_MONTH").style.display = "";
		document.getElementsByName("sqlcode")[0].value = "QUERY0019";
	}
}
//改变电流查询显示内容��ʾ����
function ecurShowType(obj) {
	var lastTd = document.getElementById("show" + lastShowType);
	var nowTd = document.getElementById("show" + obj);
	var lastTR = document.getElementById("QUERY_TR_" + lastShowType);
	var nowTR = document.getElementById("QUERY_TR_" + obj);
	var seq;
	lastTd.className = 'target_off';
	nowTd.className = 'target_on';
	lastTR.style.display = "none";
	nowTR.style.display = "";
	lastShowType = obj;
	document.getElementsByName("showType")[0].value = lastShowType;
	if (obj == 1)
		seq = "A";
	if (obj == 2)
		seq = "B";
	if (obj == 3)
		seq = "C";
	if (obj == 4)
		seq = "0";
	document.getElementsByName("seq")[0].value = seq;
	//document.forms[0].submit();
}

// 电流数据查看详细
// showType: 1: A相; 2: B相; 3: C相;
// dataType： 2: 日数据曲线; 3: 月数据�����
function ecurDetail(width, ht, gp_id, sys_object, object_no, dataType) {
	var url;
	var action;
	var showType = document.getElementsByName("showType")[0].value;
	var seq = document.getElementsByName("seq")[0].value;
	url = contextPath + "/do/autorm/dataQuery.do"
	if (dataType == 2) {
		url = url + "?action=ecurDayData";
		url = url + "&dataTime=" + date;
	}
	if (dataType == 3) {
		url = url + "?action=ecurMonthData";
		url = url + "&year=" + year;
		url = url + "&month=" + month;
	}
	url = url + "&sys_object=" + sys_object;
	url = url + "&object_no=" + object_no;
	url = url + "&gp_id=" + gp_id;
	url = url + "&showType=" + showType;
    url = url + "&seq="+seq;
	openNewWindow(url, width, ht);
}

//功率因数查询初始化��ʼ��
function pfInit() {
	date = document.getElementsByName("date")[0].value;
	year = document.getElementsByName("year")[0].value;
	month = document.getElementsByName("month")[0].value;
	pfDTChange();
	var dateType = document.getElementsByName("dateType")[0];
	var showId = document.getElementsByName("showType")[0].value;
	lastShowType = showId;
	var showTD = document.getElementById("show" + lastShowType);
	showTD.className = 'target_on';
	var showTR = document.getElementById("QUERY_TR_" + lastShowType);
	showTR.style.display = "";
	if (dateType.value == '2') {
		var tableObj = document.getElementById("data_table");
		var cellIndex = tableObj.rows[0].cells.length - 1;
		for (j = 1; j < tableObj.rows.length; j++) {
			var td = tableObj.rows[j].cells[cellIndex];
			td.style.display = "";
		}
//		document.getElementById("PAGE_DAY").style.display = "";
//		document.getElementById("PAGE_MONTH").style.display = "none";
	}
	if (dateType.value == '3') {
		var tableObj = document.getElementById("data_table");
		var cellIndex = tableObj.rows[0].cells.length;
		for (j = 1; j < tableObj.rows.length; j++) {
			var td = tableObj.rows[j].cells[cellIndex];
			td.style.display = "";
		}
//		document.getElementById("PAGE_DAY").style.display = "none";
//		document.getElementById("PAGE_MONTH").style.display = "";
	}
}

//改变功率因数查询数据类型�������
function pfDTChange() {
	var dateType = document.getElementsByName("dateType")[0];
	if (dateType.value == "2") {
		document.getElementById("DATA_DAY").style.display = "";
		document.getElementById("DATA_MONTH").style.display = "none";
		document.getElementsByName("sqlcode")[0].value = "QUERY0022";
	} else if (dateType.value == "3") {
		document.getElementById("DATA_DAY").style.display = "none";
		document.getElementById("DATA_MONTH").style.display = "";
		document.getElementsByName("sqlcode")[0].value = "QUERY0023";
	}
}

// 功率因数数据查看详细
// showType: 
// dataType： 2: 日数据曲线; 3: 月数据�����
function pfDetail(width, ht, gp_id, sys_object, object_no, dataType) {
	var url;
	var action;
	var showType = document.getElementsByName("showType")[0].value;
	url = contextPath + "/do/autorm/dataQuery.do"
	if (dataType == 2) {
		url = url + "?action=pfDayData";
		url = url + "&dataTime=" + date;
	}
	if (dataType == 3) {
		url = url + "?action=pfMonthData";
		url = url + "&year=" + year;
		url = url + "&month=" + month;
	}
	url = url + "&sys_object=" + sys_object;
	url = url + "&object_no=" + object_no;
	url = url + "&gp_id=" + gp_id;
	url = url + "&showType=" + showType;
	openNewWindow(url, width, ht);
}