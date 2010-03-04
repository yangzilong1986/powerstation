// JavaScript Document
$(document).ready(function (){	
	//$.json2html("dammeter","../../jsp/runmanage/instead_history_1_iframe.xml","");
	//查询按钮。
	$("#query").click(function (){
		var ty = $("#usedType").val();
		var rad = $("input[type='radio'][name='type'][checked]").val();
		if(ty == 1){
			$("#ammeter").show();
			$("#cpt").hide();
			if(rad == 1){
				$("#hamm_1").text("户名");
				$("#hamm_2").text("户号");
				$("#hamm_3").text("测量点序号");
				// 隐藏一列。
				$("#amm_04").show();
			}else if(rad == 2){
				$("#hamm_1").text("变压器局号");
				$("#hamm_2").text("变压器名称");
				$("#hamm_3").text("测量点序号");
				// 隐藏一列。
				$("#amm_04").show();
			}else if(rad == 3){
				$("#hamm_1").text("变电站名称");
				$("#hamm_2").text("开关名称");
				$("#hamm_3").text("测量点序号");
				// 隐藏一列。
				$("#amm_04").show();
			}else if(rad == 4){
				$("#hamm_1").text("户名");
				$("#hamm_2").text("户号");
				$("#hamm_3").text("集中器地址");
				// 隐藏一列。
				$("#amm_04").hide();
			}			
			$.json2html("dammeter","../../jsp/runmanage/instead_history_1_iframe.xml","");
		}else{
			$("#cpt").show();
			$("#ammeter").hide();
			if(rad == 1){
				$("#hcol_1").text("户名");
				$("#hcol_2").text("测量点序号");
				$("#hcol_3").text("终端地址");
				$("#col_02").hide();
			}else if(rad == 2){
				$("#hcol_1").text("变压器局号");
				$("#hcol_2").text("测量点序号");
				$("#hcol_3").text("终端地址");
				$("#col_02").show();
			}else if(rad == 3){
				$("#hcol_1").text("厂站名称");
				$("#hcol_2").text("测量点序号");
				$("#hcol_3").text("终端地址");
				$("#col_02").hide();
			}else if(rad == 4){
				$("#hcol_1").text("户名");
				$("#hcol_2").text("户号");
				$("#hcol_3").text("集中器地址");
				$("#col_02").hide();
			}
			$.json2html("dcpt","../../jsp/runmanage/instead_history_2_iframe.xml","");
		}
		//alert(ty + "||"+ rad);
	});
	
	//新增按钮。
	$("#new").click(showText);
	
});

function showText(){
	var ty = $("#usedType").val();
	if(ty == 1){
		top.showDialogBox("换表登记", "../../jsp/runmanage/pop/instead_ammeter.html?type=1", 575, 1000);
	}else{
		top.showDialogBox("换CT/PT登记", "../../jsp/runmanage/pop/instead_cpt.html?type=1", 575, 1000);
	}
}