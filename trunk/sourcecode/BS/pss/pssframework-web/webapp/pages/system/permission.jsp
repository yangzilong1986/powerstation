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
    lastTd.className = 'none';
    nowTd.className = 'curr';
    lastQueryType = obj;
    var url = contextPath;
    if(lastQueryType == 1) {
        url += "/system/dept/manager";
    }
    if(lastQueryType == 2) {
        url += "/system/role/manager";
    }
    if(lastQueryType == 3) {
        url += "/system/user/manager";
    }
    if(lastQueryType == 4) {
        url += "/system/password/manager";
    }
    document.getElementById("subContent").src = url;
}
</script>
</head>
<body>
<div id="body">

	<div id="bg">
            <ul id=datamenu_Option class="cb font1">
                <li id="query2" class="curr" > <a href="javascript:changeType(2);" onfocus="blur()"><spring:message code="system.permission.jsgl"></spring:message></a></li>
                <li id="query3" ><a href="javascript:changeType(3);" onfocus="blur()"><spring:message code="system.permission.zhgl"></spring:message></a></li>
                <li id="query4" ><a href="javascript:changeType(4);" onfocus="blur()"><spring:message code="system.permission.mmgl"></spring:message></a></li>
          	</ul>
        </div>
 
 
 
    <div style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 42));">
    <iframe id="subContent" name="subContent" width="100%" height="100%" frameborder="0" scrolling="yes" src="${ctx}/system/role/manager"></iframe>
  </div>
</div>
</body>
</html>
