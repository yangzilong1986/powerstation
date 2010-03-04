// 安装覆盖率。
$(document).ready(function() {
	// 供电单位。
		$.handelCombox('orgTar', 'orgNo');
		//_buildPic("set_cover_percent_a_data.xml");
		// 加载数据区
		//$.json2html("dtext","../../jsp/runmanage/set_cover_percent_iframe_a.xml","");
		// 查询按钮事件。
		$("#query").click(function() {
			var type = $("input[type='radio'][checked]").val();
			if (type == 4) {
				_titleName("电压等级", "变电站", "终端");
				$.json2html("dtext","../../jsp/runmanage/set_cover_percent_iframe_b.xml","");
				_buildPic("set_cover_percent_b_data.xml");
			} else {
				_titleName(type == 3 ? "供电单位(台区)" : "供电单位(容量)", "户",
						type == 3 ? "集中器" : "终端");
				$.json2html("dtext","../../jsp/runmanage/set_cover_percent_iframe_a.xml","");
				_buildPic("set_cover_percent_a_data.xml");
			}
			// 隐藏一列。
			/*if ($("input[type='checkbox']").attr("checked")
					&& (type == 1 || type == 2)) {
				$("#col_00").show();
			} else {
				$("#col_00").hide();
			}*/			
		});
		// 类型radio改变事件。
		$("input[type='radio']").click(function() {
			var type = $("input[type='radio'][checked]").val();
			if (type == 3) {
				$("#corporation").show();
				$("#orgTar").show();
				//$("#capacitGrade").hide();
			} else if (type == 4) {
				//$("#capacitGrade").hide();
				$("#corporation").hide();
				$("#orgTar").hide();
			} else {
				//$("#capacitGrade").show();
				$("#corporation").show();
				$("#orgTar").show();
			}
		});
	});

// 图形部分。
function _buildPic(xml) {
	var chart_obj = new FusionCharts("../../FusionCharts/Column3D.swf",
			"chart_id", "800", "275", "0", "0");
	chart_obj.setDataURL(xml);
	chart_obj.render("graphBlock");
}
// 标题头控制。
function _titleName(name, usedSetNumb, havSetNumb) {
	$("#name").text(name);
	$("#usedSetNumb1").text(usedSetNumb);
	$("#havSetNumb1").text(havSetNumb);
	$("#usedSetNumb2").text(usedSetNumb);
	$("#havSetNumb1").text(havSetNumb);
}

// 下级张开。
function _myChange(numb) {
	for (a = 1; a <= 7; a++) {
		$("#dtext_table_tr_" + (numb + a)).toggle('slow');
	}
	if ($("#img" + numb).attr("src").indexOf('minus1') != -1) {
		$("#img" + numb).attr("src", "../../img/tree2/plus1.gif");
	} else {
		$("#img" + numb).attr("src", "../../img/tree2/minus1.gif");
	}
}