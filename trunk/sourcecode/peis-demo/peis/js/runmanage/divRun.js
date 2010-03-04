//
$(document).ready(function(){
	//部门。
	$.handelCombox('orgTar','orgNo');
	$.handelCombox('myCommMode','commMode');
	$.handelCombox('myDivFactory','divFactory');
		    	
    //查询按钮方法。
    $("#query").click(function (){
    	//重新加载图形。
    	_buildPic();
        // 加载数据区
        var checkedValue = $("input[type='radio'][name='queryType'][checked]").attr("value");
        //图形部分的下拉框的替换变换。
        if(checkedValue == 2){
        	$("#myCommMode").hide();
        	$("#myDivFactory").show();
        }else{
        	$("#myDivFactory").hide();
        	$("#myCommMode").show();
        }
        // 条件。
        if(checkedValue == 3){
        	$("#groupByComOrFac").hide();
        	$("#comAndFactory").show();
        	$.json2html("caftext","../../jsp/runmanage/div_run_stat_iframe_b.xml","");	
        }else{
        	$("#calName").text(checkedValue==1?"通讯方式":"终端厂家");
        	$("#groupByComOrFac").show();
        	$("#comAndFactory").hide();
			if($("input[type='radio'][name='objectType'][checked]").attr("value") == 5){
				if(checkedValue == 1){
					$.json2html("coftext","../../jsp/runmanage/div_run_stat_iframe_d.xml","");
				}else{
					$.json2html("coftext","../../jsp/runmanage/div_run_stat_iframe_c.xml","");
				}						
			}else{
        		$.json2html("coftext","../../jsp/runmanage/div_run_stat_iframe_a.xml","");	
			}
        }
    });
    
    //图形改变
    //$("input[type='radio'][name='type']").click(_buildPic);
    
    //图形改变
    $("#commMode").change(_buildPic);
});

//展开事件。
function _myChange(numb){
	var checkedValue = $("input[type='radio'][name='queryType'][checked]").attr("value");
	var no = 1 ;
	if(checkedValue == 1){
		no = 1 ;
	}else{
		no += 2 ; 
	}
	$("#coftext_table_tr_"+(numb + no)).toggle('slow');
	$("#coftext_table_tr_"+(numb + no +1)).toggle('slow');
	// change img
	//var flip = 0;
	if($("#img"+numb).attr("src").indexOf('minus1')!=-1){
		$("#img"+numb).attr("src","../../img/tree2/plus1.gif");
	}else{
		$("#img"+numb).attr("src","../../img/tree2/minus1.gif");
	}
}
// 通讯和厂家
function __myChange(numb){	
	$("#caftext_table_tr_"+(numb + 1)).toggle('slow');
	$("#caftext_table_tr_"+(numb + 2)).toggle('slow');
	// change img
	//var flip = 0;
	if($("#img"+numb).attr("src").indexOf('minus1')!=-1){
		$("#img"+numb).attr("src","../../img/tree2/plus1.gif");
	}else{
		$("#img"+numb).attr("src","../../img/tree2/minus1.gif");
	}
}

// 通讯和厂家
function __myChangec(numb){	
	$("#coftext_table_tr_"+(numb + 1)).toggle('slow');
	$("#coftext_table_tr_"+(numb + 2)).toggle('slow');
	// change img
	//var flip = 0;
	if($("#img"+numb).attr("src").indexOf('minus1')!=-1){
		$("#img"+numb).attr("src","../../img/tree2/plus1.gif");
	}else{
		$("#img"+numb).attr("src","../../img/tree2/minus1.gif");
	}
}

// 全部时候的。

//图形的生成。
function _buildPic(){
	var chart_obj = new FusionCharts("../../FusionCharts/MSColumn3D.swf", "chart_id", "800", "275", "0", "0");
    chart_obj.setDataURL("div_run_state_data.xml");
    chart_obj.render("graphBlock");
}

//弹出页面终端厂家
function showDivFactory(){
	top.showDialogBox("通讯流量_厂家", "../../jsp/runmanage/pop/div_run_factory.html", 575, 900);
    return false;
}

// 弹出页面通讯方式
function showCall(){
	top.showDialogBox("通讯流量_通讯方式", "../../jsp/runmanage/pop/div_run_call.html", 575, 900);
    return false;
}
