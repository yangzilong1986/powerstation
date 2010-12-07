<% /** 数据检查的frames页面 */ %>
<%@ page language="java" pageEncoding="UTF-8" %>

 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据检查</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css"/>
<script type="text/javascript">

//任务配置中改变任务类型
function changeType(type) {
    var dataModify = document.getElementById("dataModify");
    var master = document.getElementById("master");
    var mainObj = document.getElementById("taskSetMain");
    if(type == "dataModify") {
    	master.className = 'tab_off';
    	dataModify.className = 'tab_on';
        mainObj.src = "<peis:contextPath/>/do/autorm/DataCheckQuery.do?action=query&page=1&pageSize=20";
    }
    else {
    	dataModify.className = 'tab_off';
    	master.className = 'tab_on';
        mainObj.src = "<peis:contextPath/>/do/autorm/DataCheckModQuery.do?action=query&page=1&pageSize=20";
    }
}
</script>
</head>
<body>
  <div id="body">
    <div class="tab2">
      <ul>
      	<!-- 数据修正链接  -->
        <li id="dataModify" class="tab_on"><a href="javascript:changeType('dataModify');" onfocus="blur()">数据修正</a></li>
        <li id="master" class="tab_off"><a href="javascript:changeType('master');" onfocus="blur()">修正查询</a></li>
        <li class="clear"></li>
      </ul>
    </div>
    <div id="main" style="height: expression(((document.documentElement.clientHeight||document.body.clientHeight) - 44));"> 
      <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
        <tr>
          <td>
          	<iframe id="taskSetMain" src="<peis:contextPath/>/do/autorm/DataCheckQuery.do?action=query&page=1&pageSize=20" width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
          </td>
        </tr>
      </table>
    </div>
  </div>
</body>
</html>
