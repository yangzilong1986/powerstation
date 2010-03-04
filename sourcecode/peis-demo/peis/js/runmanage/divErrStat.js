// JavaScript Document
$(document).ready(function(){
	// 
	$.handelCombox('orgTar','orgNo');	
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
		// pic
		var chart_obj = new FusionCharts("../../FusionCharts/Column3D.swf", "chart_id", "800", "275", "0", "0");
    chart_obj.setDataURL("div_err_stat_data.xml");
    chart_obj.render("graphBlock");
		// data
		setTitle($("input[name='queryType'][checked]").val());
		$.json2html("tdate","../../jsp/runmanage/div_err_stat_iframe.xml","");
	});
});

// 时间框的格式。
function changeTime(maxlength,size,vals){
	$("#statTime").val(vals);
	$("#statTime").attr("maxlength",maxlength);
	$("#statTime").attr("size",size);
}

// 
function setTitle(numb){
	if(numb == 3){
		$("#untiType").text("故障类型")	;
	}else{
		$("#untiType").text(numb == 1?"通讯方式":"终端厂家")	;
	}
}

// 
function _myChange(numb){
	var type = $("input[name='queryType'][checked]").val();
	var no = 0;
	no = type==1?0:type==2?2:4 ;
	//alert(no);
	$("#tdate_table_tr_"+(numb + no+1)).toggle('slow');
	$("#tdate_table_tr_"+(numb + no+2)).toggle('slow');
	if($("#img"+numb).attr("src").indexOf('minus1')!=-1){
		$("#img"+numb).attr("src","../../img/tree2/plus1.gif");
	}else{
		$("#img"+numb).attr("src","../../img/tree2/minus1.gif");
	}
}

//
function showDiv(num){	
  var DwName = tdate.rows(num).cells(1).innerHTML;
	//var t = (num == 1||num ==7||num==15 )?"全部":"";
	//DwName += ":"
	//alert(DwName);
	top.showDialogBox("故障终端数", "../../jsp/runmanage/pop/div_err_div.html?title="+DwName,600, 900);	 
}

// 
function showDivFactory(num){
	var DwName = tdate.rows(num).cells(1).innerHTML;
	top.showDialogBox("终端厂家", "../../jsp/runmanage/pop/div_err_call.html?type="+2+"&amp;title="+DwName,600, 900);	 
}

//
function showCall(num){
	var DwName = tdate.rows(num).cells(1).innerHTML;
	top.showDialogBox("终端厂家", "../../jsp/runmanage/pop/div_err_call.html?type="+1+"&amp;title="+DwName,600, 900);	 
}