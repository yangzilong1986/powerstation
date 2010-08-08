<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>roleDetail</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
</head>
<body>
<div class="electric_lcon" id="electric_Con">
<form:form action="/system/role" modelAttribute="role">
<ul class=default id=electric_Con_1>
	<div class="mgt10 da_top"><span><spring:message code="system.role.jsxxxx"></spring:message></span></div>
	<div>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td width="30%" class="dom"><spring:message code="system.role.jssm" /> ：</td>
			<td width="70%" class="dom"></td>
		</tr>
		<tr>
			<td width="100%" class="dom" colspan="2">${role.roleRemark}</td>
		</tr>
	</table>
	</div>
	<div class="mgt10 da_top"><span>角色所拥有的功能</span></div>
	<div class="content">
	<div id="cont_1">
	<div class="tableContainer"
		style="height: expression(((   document.documentElement.clientHeight ||   document.body.clientHeight) -   94 ) );">
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<thead>
			<tr>
				<th><spring:message code="system.role.xh" /></th>
				<th><spring:message code="system.role.gnbh" /></th>
				<th><spring:message code="system.role.gnmc" /></th>
				<th><spring:message code="system.role.gnsm" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${role.resourceInfoList}" var="item" varStatus="status">
				<tr align="center" onclick="selectSingleRow(this);" style="cursor: pointer;">
					<td>${status.index+1}</td>
					<td>${item.resourceId}</td>
					<td align="left" style="padding-left: 5px;">${item.resourceName}</td>
					<td align="left" style="padding-left: 5px;">${item.resourceComment}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	</div>
	</div>
</ul>
</form:form>
</div>
</body>
<script type="text/javascript">
</script>
</html>