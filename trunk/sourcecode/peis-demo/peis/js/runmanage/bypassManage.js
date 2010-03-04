// 旁路管理
$(document).ready(function() {
	$.handelCombox('orgTar','orgNo');
	if($.url.param("type") ==1){
		$("#save").hide();
	}
});

// 关闭窗口
function close(){
	
}