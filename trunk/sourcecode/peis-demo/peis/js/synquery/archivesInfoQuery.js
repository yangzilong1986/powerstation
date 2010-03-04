/*
 *档案资料查询 private js
 */
var _val = 1 ;
var _ty = 1;
$(document).ready(function() {
	chageTabHead();
	chageTabName();
	_ty = $.url.param("type");
	$("input[type='radio'][name='type']").click(function() {
		var check = $("input[type='radio'][name='type'][checked]").val();
		_tab_tool(check);
		_json2html();
		if(_ty ==1){
			_val = check==1?1:check==2?5:check==3?9:11;
		}else if(_ty == 2){}else if(_ty == 3){}else if(_ty == 4){}
		
		
	});
	// 三个tab公用一个页面时使用。
		$("li[class^='target_o']").click(function() {
			$("li[class^='target_o']").attr("class", "target_off");
			$(this).attr("class", "target_on");
			_json2html();
		});
		_json2html();
		// 新增报表按钮。
		$("#addquery").click(
				function() {
					top.showDialogBox("新增报表",
							"../../jsp/synquery/pop/add_info_query.html", 575,
							900);
				});
		// 修改报表按钮。
		$("#chanquery").click(
				function() {
					top.showDialogBox("修改报表",
							"../../jsp/synquery/pop/add_info_query.html", 575,
							900);
				});
	});
// 加载数据区。 注：这里提为方法是因为要多数据的模拟时要增加。
function _json2html() {
	$.json2html("dtext", "../../jsp/synquery/archives_info_query_iframe.xml",
			"");
}
// tool div tab
function _tab_tool(check) {
	if (check == 2) {
		$("#zb").hide();
		$("#pb").show();
		$("#pb_1").attr("class", "target_on");
		$("#dy").hide();
		$("#bdz").hide();
	} else if (check == 3) {
		$("#zb").hide();
		$("#pb").hide();
		$("#dy").show();
		$("#dy_1").attr("class", "target_on");
		$("#bdz").hide();
	} else if (check == 4) {
		$("#zb").hide();
		$("#pb").hide();
		$("#dy").hide();
		$("#bdz").show();
		$("#bdz_1").attr("class", "target_on");
	} else {
		$("#zb").show();
		$("#zb_1").attr("class", "target_on");
		$("#pb").hide();
		$("#dy").hide();
		$("#bdz").hide();
	}
}

//
function tabChange() {
	var type = $.url.param("type");
}

function chageTabName() {
	var type = $.url.param("type");
	$("li[class^='tab_']").attr("class", "tab_off");
	$("#tab_" + type).attr("class", "tab_on");
}

function chageTabHead() {
	var type = $.url.param("type");
	if (type == 1) {
		$("#zlcx").show();
		$("#bmcx").hide();
		$("#fhcx").hide();
		$("#dlcx").hide();
	} else if (type == 2) {
		$("#zlcx").hide();
		$("#bmcx").show();
		$("#fhcx").hide();
		$("#dlcx").hide();
	} else if (type == 3) {
		$("#zlcx").hide();
		$("#bmcx").hide();
		$("#fhcx").show();
		$("#dlcx").hide();
	} else if (type == 4) {
		$("#zlcx").hide();
		$("#bmcx").hide();
		$("#fhcx").hide();
		$("#dlcx").show();
	}
}

// 弹出页面。
function showText() {
	var sel = $("input[type='radio'][checked]").val();
	top.showDialogBox("自定义报表", "../../jsp/synquery/pop/info_query.html?type="
			+ _ty+"&select="+sel+"&values="+_val, 575, 900);
}

function setVal(val){
	_val = val ;
}
