//
$(document).ready(function(){	

	_buildCpuPic();
	_buildMemPic();
});

/**
 * CPU使用率图形的生成。
 */
function _buildCpuPic(){
	var chart_obj = new FusionCharts("../../FusionCharts/RealTimeLine.swf", "cpu", "600", "300", "0", "0");
    chart_obj.setDataURL("cpu_usage_real.xml");
    chart_obj.render("cpu_usage");
}
/**
* 内存使用率图形的生成。
*/
function _buildMemPic(){
	var chart_obj = new FusionCharts("../../FusionCharts/MSColumn3D.swf", "memory", "600", "300", "0", "0");
   chart_obj.setDataURL("mem_usage_real.xml");
   chart_obj.render("mem_usage");
}