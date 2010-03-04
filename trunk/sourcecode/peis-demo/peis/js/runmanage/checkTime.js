//校时成功率。
$(document).ready(function() {
	$.handelCombox('orgTar','orgNo');
	//$.json2html("dtext","../../jsp/runmanage/check_time_iframe.xml","");	
	// 查询按钮
	$("#query").click(function(){
		$.json2html("dtext","../../jsp/runmanage/check_time_iframe.xml","");
	});
});