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
   // selectSingleRow(oRow);
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
	 if(firstId != undefined){
	 	selectRow(firstId);
	 }
})


var contextPath = "${ctx}";
var errorRole = '<spring:message code="system.role.errorRole" />';
var errorDelRole = '<spring:message code="system.role.errorDelRole"/>';
var confirmDel = '<spring:message code="system.role.confirmDel"/>';
//新增角色
function newRole() {
    var str_url = contextPath + "/system/role/new";
    //windowPopup(str_url, 650, 450);
    windowPopup(str_url, 800, 495);
}
//编辑角色
function editRole() {
    var roleId = sSelectedRoleID;
    if(roleId == "") {
        alert(errorRole);
        return;
    }
    var str_url = contextPath + "/system/role/"+roleId + "/edit?random=" + Math.random();
    windowPopup(str_url, 800, 495);
}

function viewRole() {
	 var roleId = sSelectedRoleID;
	    if(roleId == "") {
	        alert(errorRole);
	        return;
	    }
	    var str_url = contextPath + "/system/role/"+roleId + "?random=" + Math.random();
    windowPopup(str_url, 800, 495);
}
//删除角色
function deleteRole() {
    var roleId = sSelectedRoleID;
    if(roleId == "") {
        alert(errorDelRole);
        return;
    }
    if(confirm(confirmDel)) {
        var roleType = parent.roleList.roleForm.roleType.value;
        parent.hideframe.location.href = contextPath + "/system/role?action=delete&roleId=" + roleId + "&roleType=" + roleType + "&random=" + Math.random();
    }
}
function fresh(Id) {
    var roleType = parent.roleList.roleForm.roleType.value;
    parent.roleList.roleForm.tem.value = Id;
    parent.roleList.document.forms[0].submit();
    parent.document.frames["roleList"].location.href = contextPath
            + '/system/role?action=getQuery&roleType=1&id=' + Id + '&random=' + Math.random();
    parent.document.frames["roleDetail"].location.href = contextPath
            + '/system/role?action=detail&roleId=' + Id + '&roleType=' + roleType + '&random='
            + Math.random();
}

</script>
</head>
<body>
<div><form:form action="/system/role/list" modelAttribute="roleInfo">
	<div id="tool">
	<table>
		<tr>
			<td><input type="button" id="new" class="btnbg4" value='<spring:message code="system.button.xz"/>'
				onclick="newRole()" /> <input type="button" class="btnbg4" id="edit"
				value='<spring:message code="system.button.bj"/>' onclick="editRole()" /> <input type="button" class="btnbg4"
				id="delete" value='<spring:message code="system.button.sc"/>' onclick="deleteRole()" /></td>
		</tr>
	</table>
	</div>
	<br />
	<div class="content">
	<div id="cont_1">
	<div class="tableContainer"
		style="height: expression(((     document.documentElement.clientHeight ||   document.body.clientHeight) -40 ) );">
	<table width="100%" border="0" cellspacing="0" class="gridBody" id="object_table">
		<thead class="tableHeader">
			<tr style="height: 20px;">
				<th><spring:message code="system.role.xh" /></th>
				<!-- 排序时为th增加sortColumn即可,new SimpleTable('sortColumns')会为tableHeader自动增加排序功能; -->
				<th sortColumn="roleName"><spring:message code="system.role.jsmc" /></th>
				<th sortColumn="roleRemark">角色备注</th>
			</tr>
		</thead>
		<tbody id="dataBody">
			<c:forEach items="${page.result}" var="item" varStatus="status">
				<tr style="height: 20px;" id="${item.roleId}" class="${status.count % 2 == 0 ? 'odd' : 'even'}"
					onclick="selectRow('${item.roleId}', this)" style="cursor: pointer;">
					<td>${page.thisPageFirstElementNumber + status.index}</td>
					<td>${item.roleName}&nbsp;</td>
					<td>${item.roleRemark}&nbsp;</td>
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