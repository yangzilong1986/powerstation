/*
 *档案资料查询 private js
 */
$(document).ready(function (){		
		$("input[type='radio'][name='type']").click(function(){
			var check = $("input[type='radio'][name='type'][checked]").val();
			_tab_tool(check);	
			_json2html();
		});	
	  //三个tab公用一个页面时使用。
	  $("li[id!='tab_1']").click(function (){
			$("li[id!='tab_1']").removeClass();
			$("li").addClass("target_off");
			$(this).attr("class","target_on");	
			_json2html();
		});		
		_json2html();
		//新增报表按钮。
		$("#addquery").click(function(){
			top.showDialogBox("新增报表", "../../jsp/synquery/pop/add_info_query.html", 550, 1000);													
		});
		//修改报表按钮。
		$("#chanquery").click(function(){
			top.showDialogBox("修改报表", "../../jsp/synquery/pop/add_info_query.html",550, 1000);													
		});
});
//加载数据区。 注：这里提为方法是因为要多数据的模拟时要增加。
function _json2html(){
	$.json2html("dtext","../../jsp/synquery/archives_info_query_iframe.xml","");
}
// tool div tab
function _tab_tool(check){
	if(check == 2){
		$("#zb").hide();
		$("#pb").show();
		$("#pb_1").attr("class","target_on");	
		$("#dy").hide();
		$("#bdz").hide();
	}else if(check == 3){
		$("#zb").hide();
		$("#pb").hide();
		$("#dy").show();
		$("#dy_1").attr("class","target_on");	
		$("#bdz").hide();
	}else if(check == 4){
		$("#zb").hide();
		$("#pb").hide();
		$("#dy").hide();
		$("#bdz").show();
		$("#bdz_1").attr("class","target_on");	
	}else{
		$("#zb").show();
		$("#zb_1").attr("class","target_on");	
		$("#pb").hide();
		$("#dy").hide();
		$("#bdz").hide();
	}
}

// 弹出页面。
function showText(){
	top.showDialogBox("自定义报表", "../../jsp/synquery/pop/info_query.html", 550, 900);
}