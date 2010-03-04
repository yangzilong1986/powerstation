// 通讯源码查询
$(document).ready(function() {
	$("#query").click(function(){
		$.json2html("recv_text","../../jsp/runmanage/comm_msg_recv_query_iframe.xml","");
		$.json2html("send_text","../../jsp/runmanage/comm_msg_send_query_iframe.xml","");
	});
});