// JavaScript Document
$(document).ready(function(){	
	//管理单位
	$.handelCombox('orgTar','orgNo');		
	// 新增加
	$("#new").click(function(){
		top.showDialogBox("主备表替换", "../../jsp/runmanage/pop/replace_watch.html?type=2", 600, 900);	 						 
	});
	// 查询
	$("#query").click(function(){
		$.json2html("dtext","../../jsp/runmanage/replace_watch_iframe.xml","");														 
	});
});

// 查看修改
function _go2coulomeAdd(numb){
  if(numb == 1 || numb == 2){
		top.showDialogBox("主备表替换", "../../jsp/runmanage/pop/replace_watch.html?type="+numb,600, 900);	 
	}else{
		//$("this").remove();
	}
}