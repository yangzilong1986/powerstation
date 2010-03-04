//系统监控历史查询
$(document).ready(function() {

	$("#query").click(function(){
		//获取select对象 
		var selectedObj = $("#serverRes option:selected"); 
		//获取当前selected的值 
		var serverRes = selectedObj.get(0).value;
		
		if(serverRes == "1")
		    _buildCpuPic();
		else if(serverRes == "2")
			_buildMemPic();
		else if(serverRes == "3")
			_buildDiskPic();
		else if(serverRes == "4")
			_buildDbPic();
	});
});

/**
 * CPU使用率图形的生成。
 */
function _buildCpuPic(){
	var chart_obj = new FusionCharts("../../FusionCharts/RealTimeLine.swf", "cpu", "800", "330", "0", "0");
    chart_obj.setDataURL("cpu_usage_history.xml");
    chart_obj.render("sys_stat");
}

/**
 * 内存使用率图形的生成
 */
function _buildMemPic(){
    var chart_obj = new FusionCharts("../../FusionCharts/MSColumn3D.swf", "memory", "800", "330", "0", "0");
    chart_obj.setDataURL("mem_usage_history.xml");
    chart_obj.render("sys_stat");
}

/**
* 磁盘状态图形的生成
*/
function _buildDiskPic(){
	var chart_obj = new FusionCharts("../../FusionCharts/MSColumn3D.swf", "disk", "800", "330", "0", "0");
    chart_obj.setDataURL("disk_usage_history.xml");
    chart_obj.render("sys_stat");
}

/**
* 数据库状态图形的生成
*/
function _buildDbPic(){
	var chart_obj = new FusionCharts("../../FusionCharts/MSColumn3D.swf", "db", "800", "330", "0", "0");
    chart_obj.setDataURL("db_usage_history.xml");
    chart_obj.render("sys_stat");
}