// 旁路管理
$(document).ready(function() {
	$.handelCombox('orgTar','orgNo');
	//$.json2html("dtext","../../jsp/runmanage/bypass_manage_history_iframe.xml","");
	$("#query").click(function(){
		$.json2html("dtext","../../jsp/runmanage/bypass_manage_history_iframe.xml","");
	});
});

function popMana(numb){	
	if(numb == 1 ||numb == 2){
		top.showDialogBox("旁路管理", "../../jsp/runmanage/pop/bypass_manage.html?type="+numb, 800, 900);
	}
    return false;
}