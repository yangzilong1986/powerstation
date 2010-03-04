// 换表、换CPT
$(document).ready(function() {
	$.handelCombox('mdivFactory','divFactory');
	
	if($.url.param("change") ==1||$.url.param("change") ==2){
		$("#new").hide();
		$("#ammeNumb").val(20938456);
		$("#ct").val("500/1");
		$("#pt").val("200/1");
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
