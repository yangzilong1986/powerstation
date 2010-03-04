// 
$(document).ready(function() {
	// radio增加click事件。
	$("input[type='radio']").click(function() {
		var checkedValue = $("input[type='radio'][checked]").attr("value");
		if(checkedValue == 1){
			changeRadio("户号","户名",false);
		}else if(checkedValue == 2){
			changeRadio("配变名称","配变局号",false);
		}else if(checkedValue == 3){
			changeRadio("户号","户名",false);
		}else if(checkedValue == 4){
			changeRadio("变电站名称","变电站名称",false);
		}
	});
	// 查询按钮。
	$("#query").click(function(){
		var val = $("input[type='radio'][checked]").attr("value");
		if(val == 1){
			colName("户号","户名",false);
		}else if(val == 2){
			colName("配变名称","配变局号",false);
		}else if(val == 3){
			colName("户号","户名",false);
		}else if(val == 4){
			colName("变电站名称","变电站名称",false);
		}
		// json 增加数据区。
		$.json2html("test","../../jsp/runmanage/equipment_exc_stat_iframe.xml","");
	});
	// 默认条件查询。
	//$.json2html("test","../../jsp/runmanage/equipment_exc_stat_iframe.xml","");
});

// 
function changeRadio(_userid,_userName,isShow){
	/*if(isShow){
		$("#orgNo").hide("slow");
	}else{
		$("#orgNo").show("slow");
	}	*/
	$("#userId").text(_userid+"：");
	$("#userName").text(_userName+"：");
}

//
function colName(_userid,_userName,isShow){
	/*if(isShow){
		$("#orgNo").hide("slow");
	}else{
	$("#orgNo").show("slow");
	}	*/	
	$("#userId1").text(_userid);
	$("#userName1").text(_userName);
}

//
function buildList() {
	location = "./equipment_exc_list.html";
}
