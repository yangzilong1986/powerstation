// 
$(document).ready(function() {
	$.handelCombox('orgTar','orgNo');
	$.handelCombox('mCommMode','commMode');
	$.handelCombox('mdivFactory','divFactory');
	
	// 图形的生成。
	//_buildPic();
	// radio增加click事件。
	$("input[type='radio'][name='queryType']").click(function() {
		var checkedValue = $("input[type='radio'][name='queryType'][checked]").attr("value");
		if(checkedValue == 1){
			$("#dc").hide();
			$("#mdivFactory").hide();
			$("#ct").show();
			$("#mCommMode").show();
		}else{
			$("#ct").hide();
			$("#mCommMode").hide();
			$("#dc").show();
			$("#mdivFactory").show();
		}		
	});
	// 查询按钮。
	$("#query").click(function(){
		hideRow();
		var checkedValue = $("input[type='radio'][name='queryType'][checked]").attr("value");		
		var objectType = $("input[type='radio'][name='objectType'][checked]").attr("value");
		if(checkedValue == 1){
			$("#calName").text("通讯方式");
		}else{
			$("#calName").text("终端厂家");
		}				
		// json 增加数据区。
		if(objectType == 4){
			$("#tit").text("电压等级");
			$.json2html("dtext","../../jsp/runmanage/div_online_stat_2_iframe.xml","");
		}else{
			$("#tit").text("供电单位");
			$.json2html("dtext","../../jsp/runmanage/div_online_stat_iframe.xml","");
		}
		// 图形的生成。
		_buildPic();
	});
	// 默认条件查询。
	//$.json2html("dtext","../../jsp/runmanage/div_online_stat_iframe.xml","");
});
//隐藏列。
function hideRow(){
	var type = $("#dataType").val();
	$("#zd").show();
	$("#rpj").show();
	//alert(type);
	if(type ==2){
		$("#col_09").hide();
		$("#col_08").show();		
	}else if(type ==3){
		$("#col_08").hide();
		$("#col_09").show();		
	}else {
		$("#col_08").hide();
		$("#col_09").hide();
	}
}

// 图形的生成。
function _buildPic(){
	var chart_obj = new FusionCharts("../../FusionCharts/Column3D.swf", "chart_id", "800", "275", "0", "0");
    chart_obj.setDataURL("div_online_stat_data.xml");
    chart_obj.render("graphBlock");
}

//展开事件。
function _myChange(numb){
	var checkedValue = $("input[type='radio'][name='queryType'][checked]").attr("value");
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
	//var flip = 0;
	if($("#img"+numb).attr("src").indexOf('minus1')!=-1){
		$("#img"+numb).attr("src","../../img/tree2/plus1.gif");
	}else{
		$("#img"+numb).attr("src","../../img/tree2/minus1.gif");
	}
}
// 明细。
function showInfo(numb){
	top.showDialogBox("终端故障明细", "../../jsp/runmanage/pop/div_err_info.html?type="+numb, 600, 900);
}
