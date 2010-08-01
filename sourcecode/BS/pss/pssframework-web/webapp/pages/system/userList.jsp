<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="system.permission.manage.title" /></title>
<title>userList</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript">
var contextPath = "${ctx}";
var sSelectedUserID = "";
function selectRow(sUserID, oRow) {
    sSelectedUserID = sUserID;
    var url = contextPath + '/system/user/' + sSelectedUserID + '?random=' + Math.random();
    parent.document.frames["userManager"].location.href = url;
   // selectSingleRow(oRow);
}

//列表变更
function changeList() {
    $("#orgNo").attr("disabled", false);
    document.forms[0].submit();
}

function changeOrg() {
    $("#orgNo").attr("disabled", false);
    document.forms[0].submit();
}


$(function(){
	 var firstId = $("#dataBody>tr:first").attr("id");
	 selectRow(firstId);
	 
})
</script>
</head>
<body >
<div id="body"><form:form action="/system/user" modelAttribute="user">
	<div id="tool">
	<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><spring:message code="system.user.ssdw" />：</td>
			<td></td>
			<td colspan="2"><input type="checkbox" name="isAll" value="1" onclick="changeList()" />显示全部</td>
		</tr>
	</table>
	</div>
	<div >
	<div id="cont_1">
	<div id="tableContainer"
		style="height: expression(((     document.documentElement.clientHeight ||     document.body.clientHeight) -64 ) );">
	<table width="100%" border="0" cellspacing="0" class="gridBody" id="object_table">
		<thead class="tableHeader">
			<tr>
				<th><spring:message code="system.user.xh" /></th>
				<th><spring:message code="system.user.zh" /></th>
				<th><spring:message code="system.user.mc" /></th>
			</tr>
		</thead>
		<tbody id="dataBody">
			<c:forEach items="${page.result}" var="item" varStatus="status">
				<tr id="${item.empNo}" class="${status.count % 2 == 0 ? 'odd' : 'even'}" onclick="selectRow('${item.empNo}', this)"
					style="cursor: pointer;">
					<td>${page.thisPageFirstElementNumber + status.index}</td>
					<td>${item.staffNo}&nbsp;</td>
					<td>${item.name}&nbsp;</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<simpletable:pageToolbar page="${page}"></simpletable:pageToolbar></div>
	</div>
	</div>
</form:form></div>
</body>
</html>
