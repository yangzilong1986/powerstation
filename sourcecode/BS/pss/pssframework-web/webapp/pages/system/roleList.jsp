<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>roleList</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript">
var contextPath = "${ctx}";
var sSelectedRoleID = "";
function selectRow(sRoleID, oRow) {
    sSelectedRoleID = sRoleID;
    parent.document.frames["roleDetail"].location.href = contextPath
            + '/system/role/'+ sSelectedRoleID 
            + '?random=' + Math.random();
    //selectSingleRow(oRow);
}

//列表变更
function changeList(type) {
    if(type != 1) {
        parent.document.getElementById("new").disabled = true;
        parent.document.getElementById("edit").disabled = true;
        parent.document.getElementById("delete").disabled = true;
    }
    else {
        parent.document.getElementById("new").disabled = false;
        parent.document.getElementById("edit").disabled = false;
        parent.document.getElementById("delete").disabled = false;
    }
    document.forms[0].submit();
}

function init() {
  
}

$(function(){
	 var firstId = $("#dataBody>tr:first").attr("id");
	 selectRow(firstId);
})
</script>
</head>
<body>
<div id="body"><form:form action="/system/role/list" modelAttribute="roleInfo">
	<div class="content">
	<div id="cont_1">
	<div
		style="height: expression(((     document.documentElement.clientHeight ||       document.body.clientHeight) -34 ) );">
	<table width="100%" border="0" cellspacing="0" class="gridBody" id="object_table">
		<thead class="tableHeader">
			<tr>
				<th><spring:message code="system.role.xh" /></th>
				<th align="center"><input type="checkbox" onclick="setAllCheckboxState('ItemID',this.checked)"></th>
				<!-- 排序时为th增加sortColumn即可,new SimpleTable('sortColumns')会为tableHeader自动增加排序功能; -->
				<th sortColumn="roleName"><spring:message code="system.role.jsmc" /></th>
			</tr>
		</thead>
		<tbody id="dataBody">
			<c:forEach items="${page.result}" var="item" varStatus="status">
				<tr id="${item.roleId}" class="${status.count % 2 == 0 ? 'odd' : 'even'}"
					onclick="selectRow('${item.roleId}', this)" style="cursor: pointer;">
					<td>${page.thisPageFirstElementNumber + status.index}</td>
					<td><input type="checkbox" name="ItemID" value=""></td>
					<td>${item.roleName}&nbsp;</td>
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
