<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="system.permission.manage.title"/></title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<script type="text/javascript">
var lastQueryType = 2;
var contextPath = "${ctx}";

function changeType(obj) {
    var lastTd = document.getElementById("query" + lastQueryType);
    var nowTd = document.getElementById("query" + obj);
    lastTd.className = 'tab_off';
    nowTd.className = 'tab_on';
    lastQueryType = obj;
    var url = contextPath;
    if(lastQueryType == 1) {
        url += "/system/dept/ManagerFrame";
    }
    if(lastQueryType == 2) {
        url += "/system/role/ManagerFrame";
    }
    if(lastQueryType == 3) {
        url += "/system/user/ManagerFrame";
    }
    if(lastQueryType == 4) {
        url += "/system/password/Manage";
    }
    document.getElementById("subContent").src = url;
}
</script>
</head>
<body>
<div id="body">
  <div class="tab">
    <ul class="default">
      <li id="query2" class="tab_on">
        <a href="javascript:changeType(2);" onfocus="blur()"><spring:message code="system.permission.jsgl"></spring:message></a>
      </li>
      <li id="query3" class="tab_off">
        <a href="javascript:changeType(3);" onfocus="blur()"><spring:message code="system.permission.zhgl"></spring:message></a>
      </li>
      <li id="query4" class="tab_off">
        <a href="javascript:changeType(4);" onfocus="blur()"><spring:message code="system.permission.mmgl"></spring:message></a>
      </li>
      <li class="clear"></li>
    </ul>
    <h1><a href="#"><img src="${ctx}/img/bt_help.gif" width="14" height="15" /></a></h1>
  </div>
  <div style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 42));">
    <iframe id="subContent" name="subContent" width="100%" height="100%" frameborder="0" scrolling="yes" src="${ctx}/system/role/manager"></iframe>
  </div>
</div>
</body>
</html>
