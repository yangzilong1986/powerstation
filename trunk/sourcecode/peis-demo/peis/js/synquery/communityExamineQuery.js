var _type = 1;
$(document).ready(function() { 													 
	_type = $.url.param("type");
	$("#tab_"+_type).attr("class","tab_on");	
	// 新增报表按钮。
	$("#addquery").click(function() {
			top.showDialogBox("新增报表",
					"../../jsp/synquery/pop/add_info_query.html", 550,
					1000);
		});
	// 修改报表按钮。
	$("#chanquery").click(function() {
			top.showDialogBox("修改报表",
					"../../jsp/synquery/pop/add_info_query.html", 550,
					1000);
		});
});


// 
function showText(){
		//alert(_type);
		var _title = _type==1?"档案资料":_type==2?"日表码":_type==3?"月表码":_type==4?"日电量":_type==5?"月电量":"负荷数据";
		top.showDialogBox(_title,
					"../../jsp/synquery/pop/commmunity_examine_query.html?type="+_type, 550,
					1000);
}