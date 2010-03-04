$(document).ready(function (){	
	$.handelCombox('orgTar','orgNo');
	// query
	$("#query").click(function(){
		$.json2html("tdate", "../../jsp/synquery/unit_query_iframe.xml","");										 
	});
	
	var chart_obj = new FusionCharts("../../FusionCharts/MSColumn3D.swf", "chart_id", "750", "150", "0", "0");
  chart_obj.setDataURL("data.xml");
  chart_obj.render("graphBlock1");
	var chart_obj1 = new FusionCharts("../../FusionCharts/MSColumn3D.swf", "chart_id2", "750", "150", "0", "0");
  chart_obj1.setDataURL("data.xml");
  chart_obj1.render("graphBlock2");
});