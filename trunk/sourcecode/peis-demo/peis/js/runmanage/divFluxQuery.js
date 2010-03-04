// JavaScript Document
$(document).ready(function(){
	// 
	$.handelCombox('orgTar','orgNo');	
	$.handelCombox('myFactory','divFactory');
	$.handelCombox('myCcommMode','commMode');
	// 
	$("#statType").change(function(){
		var type = $(this).val();
		if(type == 1){
			changeTime("10","6","2008-10-01");
		}else if(type == 2){
			changeTime("7","3","2008-10");
		}else {
			changeTime("4","2","2008");
		}
	});
	// query
	$("#query").click(function(){	
		var ty = $("input[type='radio'][name='type'][checked]").val();
		if(ty == 1||ty==3){
			$("#usrName").text("用户名称");
		}else if(ty == 2){
			$("#usrName").text("台区名称");
		}else{
			$("#usrName").text("变电站名称");
		}
		$.json2html("tdate","../../jsp/runmanage/div_flux_query_iframe.xml","");
	});
});

//时间框的格式。
function changeTime(maxlength,size,vals){
	$("#statTime").val(vals);
	$("#statTime").attr("maxlength",maxlength);
	$("#statTime").attr("size",size);
}
