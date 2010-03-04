$(document).ready(function() {
    $("#query").click(function(){
      $.json2html("report_list_rec","data/report_list_data.xml","");
    });
    
    $('#fileBtn').change(function(){
    	alert($('#fileBtn').attr("value")+" 上传成功!");	
    });
});

/**
 *  报表生成参数：变电站名称、年份
 */
function addByTransYear(){
	top.showDialogBox("报表生成","../../jsp/report/addByTransYear.html", 450, 800);
}

/**
*  报表生成参数：变电站名称、月份
*/
function addByTransMonth(){
	top.showDialogBox("报表生成","../../jsp/report/addByTransMonth.html", 450, 800);
}

/**
 * 报表生成参数：单位名称、月份
 */
function addByUnitMonth(){
	top.showDialogBox("报表生成","../../jsp/report/addByUnitMonth.html", 450, 800);
}

/**
*  报表生成参数：单位名称、变电站名称、日期
*/
function addByUnitTransDt(){
	top.showDialogBox("报表生成","../../jsp/report/addByUnitTransDt.html", 450, 800);
}

/**
*  报表生成参数：月份
*/
function addByMonth(){
	top.showDialogBox("报表生成","../../jsp/report/addByMonth.html", 450, 800);
}

/**
*  报表生成参数：月份
*/
function addReport(){
	top.showDialogBox("报表生成","../../jsp/report/addReport.html", 450, 800);
}

/**
*  报表上传
*/
function reportUpload(){
}

/**
*  报表浏览
*/
function reportBrowse(){
	top.showDialogBox("报表浏览","../../jsp/report/reportBrowse.html", 450, 800);
}
