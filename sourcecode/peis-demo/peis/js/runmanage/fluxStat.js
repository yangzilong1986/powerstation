//
$(document).ready(function() {
	//部门。
	$.handelCombox('orgTar','orgNo');
	// 图形的生成。
	//_buildPic();
	// radio增加click事件。
	$("input[type='radio'][name='checkType']").click(function() {
		var checkedValue = $("input[type='radio'][name='checkType'][checked]").attr("value");
		if(checkedValue == 1){
			$("#factry").hide();
			$("#factryN").hide();
			$("#call").show();
			$("#callN").show();
		}else if(checkedValue == 2){
			$("#call").hide();
			$("#callN").hide();
			$("#factry").show();
			$("#factryN").show();
		}
	});
	// 查询
	$("#query").click(function(){
		//重新加载图形。
		_buildPic();
        var checkedValue = $("input[type='radio'][name='checkType'][checked]").attr("value");
		if(checkedValue == 1){
			$("#name").text("通讯方式");
		}else if(checkedValue == 2){
			$("#name").text("终端厂家");
		}
        // json 增加数据区。
    	$.json2html("dtext","../../jsp/runmanage/flux_stat_iframe.xml","");
	});
	// json 增加数据区。
	//$.json2html("dtext","../../jsp/runmanage/flux_stat_iframe.xml","");	
	// 图形中可以点击的css为小手。
	//$("tr[color='blue']").css
});

//展开事件。
function _myChange(numb){
	var checkedValue = $("input[type='radio'][name='checkType'][checked]").attr("value");
	var no = 1 ;
	if(checkedValue == 1){
		no = 1 ;
	}else{
		no += 4 ; 
	}
	$("#dtext_table_tr_"+(numb + no)).toggle('slow');
	$("#dtext_table_tr_"+(numb + no +1)).toggle('slow');
	$("#dtext_table_tr_"+(numb + no +2)).toggle('slow');
	$("#dtext_table_tr_"+(numb + no +3)).toggle('slow');
	// change img
	var flip = 0;
	if($("#img"+numb).attr("src").indexOf('minus1')!=-1){
		$("#img"+numb).attr("src","../../img/tree2/plus1.gif");
	}else{
		$("#img"+numb).attr("src","../../img/tree2/minus1.gif");
	}
}

//图形的生成。
function _buildPic(){
	var chart_obj = new FusionCharts("../../FusionCharts/Column3D.swf", "chart_id", "800", "275", "0", "0");
    chart_obj.setDataURL("flux_data.xml");
    chart_obj.render("graphBlock");
}


// 弹出终端页面
function showDiv(){
	top.showDialogBox("终端数", "../../jsp/runmanage/pop/flux_div.html", 800, 900);
    return false;
}

// 弹出页面终端厂家
function showDivFactory(){
	top.showDialogBox("通讯流量_厂家", "../../jsp/runmanage/pop/flux_div_factory.html", 800, 900);
    return false;
}

// 弹出页面通讯方式
function showCall(){
	top.showDialogBox("通讯流量_通讯方式", "../../jsp/runmanage/pop/flux_call_type.html", 800, 900);
    return false;
}
