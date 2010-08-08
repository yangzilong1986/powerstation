<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="system.permission.manage.title" /></title>
<title>userDetail</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript">

function fresh(userId) {
    parent.userList.document.forms[0].submit();
    parent.document.frames["userList"].location.href = contextPath + '/system/user?action=getQuery&id='
            + userId + '&random=' + Math.random();
    parent.document.frames["userManager"].location.href = contextPath
            + '/system/user/' + userId + '&random=' + Math.random();
}
</script>
</head>
<body>
<div class="electric_lcon" id="electric_Con">
<ul class=default id=electric_Con_1>
	<div class="mgt10 da_top"><span><spring:message code="system.user.czyxx" /></span></span></div>
	<div>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr height="25px;">
			<td width="15%" align="right" class="green"><spring:message code="system.user.zh" />：</td>
			<td width="25%">${user.staffNo}</td>
			<td width="15%" align="right" class="green"><spring:message code="system.user.mc" />：</td>
			<td width="25%">${user.name}</td>
		</tr>
		<tr height="25px;">
			<td width="15%" align="right" class="green"><spring:message code="system.user.ssdw" />：</td>
			<td width="25%">${user.orgInfo.orgName}</td>
			<td width="15%" align="right" class="green"><spring:message code="system.user.dh" />：</td>
			<td width="25%">${user.mobile}</td>
		</tr>
		<tr height="25px;">
			<td width="15%" align="right" class="green"><spring:message code="system.user.zt" />：</td>
			<td width="25%"><pss:code code="${user.enable}" codeCate="<%=SystemConst.CODE_USER_STATUS %>" /></td>
			<td width="15%" align="right" class="green"></td>
			<td width="25%"></td>
		</tr>
	</table>
	</div>
	<div class="mgt10 da_top"><span><spring:message code="system.user.czyjs" /></span></div>
	<div class="content">
	<div id="cont_1">
	<div class="tableContainer"
		style="height: expression(((document.documentElement.clientHeight ||document.body.clientHeight) -141));">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr height="25px;">
			<td align="left"><spring:message code="system.user.gwjs" />：</td>
		</tr>
		<tr height="25px;">
			<td style="padding-left: 10px;">${roleNames}</td>
		</tr>
	</table>
	</div>
	</div>
	</div>
</ul>
</div>
</body>
</html>
