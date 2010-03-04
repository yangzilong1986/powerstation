// 换表、换CPT
$(document).ready(function() {
	$.handelCombox('mdivFactory','divFactory');
	
	if($.url.param("change") ==1){
		$("#new").hide();
	}
	
	/*
	 * 三个tab公用一个页面时使用。
	 * $("li[id ^= 'tab_']").click(function (){
		$("li[id ^= 'tab_']").removeClass();
		$("li[id ^= 'tab_']").addClass("tab_off");
		$(this).attr("class","tab_on");
		
	});*/
	$("#save").click(function(){
		top.GB_hide();	
	});
});
