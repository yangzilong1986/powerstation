// 主站运行管理
$(document).ready(function() {
    $.json2html("server_list","../../jsp/runmanage/sys_real_stat_data.xml","");
});

function detailQuery(){
	window.location.href = "../../jsp/runmanage/server_real_stat.html";
}