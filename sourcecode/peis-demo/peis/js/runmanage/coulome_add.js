// JavaScript Document
$(document).ready(function(){
	// 新增按钮。
	$("#new").click(function(){
		top.showDialogBox("追加电量", "../../jsp/runmanage/pop/add_coulome.html?type=2", 600, 900);	 
	});	
	// 查询按钮。
	$("#query").click(function(){
		$.json2html("dtext","../../jsp/runmanage/coulome_add_iframe.xml","");													 
	})
});
// 查看和修改追加电量事件。
function _go2coulomeAdd(numb){
  if(numb == 1 || numb == 2){
		top.showDialogBox("追加电量", "../../jsp/runmanage/pop/add_coulome.html?type="+numb,600, 900);	 
	}else{
		//$("this").remove();
	}
}