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
<div>
<div class="pad5"><input type="button" id="new"  value='<spring:message code="system.button.xz"/>'
	onclick="newRole()" /> <input type="button" id="edit"  value='<spring:message code="system.button.bj"/>'
	onclick="editRole()" /> <input type="button" id="delete" 
	value='<spring:message code="system.button.sc"/>' onclick="deleteRole()" /></div>
<div class="tab"><em><spring:message code="system.role.jsxxxx"></spring:message></em></div>
<div class="tab_con">
<div class="main">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td width="30%" class="dom"><spring:message code="system.role.jssm" /> ：</td>
		<td width="70%" class="dom"></td>
	</tr>
	<tr>
		<td width="100%" class="dom" colspan="2">${remark}</td>
	</tr>
	<tr>
		<td width="30%" class="dom"><spring:message code="system.role.jssyydcy" />：</td>
		<td width="70%" class="dom"></td>
	</tr>
	<tr>
		<td width="100%" class="dom" colspan="2">${user}</td>
	</tr>
</table>
</div>
<div class="data1"><span>角色所拥有的功能</span></div>
<div class="data1_con">
<div class="main"
	style="height: expression(((   document.documentElement.clientHeight ||     document.body.clientHeight) -     234 ) );">
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
		<c:forEach items="${roleInfo.resourceInfoList}" var="item" varStatus="status">
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
</div>
</body>
<script type="text/javascript">
var contextPath = "${ctx}";
var errorRole = '<spring:message code="system.role.errorRole" />';
var errorDelRole = '<spring:message code="system.role.errorDelRole"/>';
var confirmDel = '<spring:message code="system.role.confirmDel"/>';
//新增角色
function newRole() {
    var roleType = parent.roleList.roleForm.roleType.value;
    var str_url = contextPath + "/system/role?action=beforeEdit&roleType=" + roleType + "&random=" + Math.random();
    //windowPopup(str_url, 650, 450);
    top.showDialogBox("新增角色", str_url, 495, 800);
}
//编辑角色
function editRole() {
    var roleId = parent.roleList.sSelectedRoleID;
    if(roleId == "") {
        alert(errorRole);
        return;
    }
    var roleType = parent.roleList.roleForm.roleType.value;
    var str_url = contextPath + "/system/role?action=beforeEdit&roleId=" + roleId + "&roleType=" + roleType + "&random=" + Math.random();
    //windowPopup(str_url, 650, 450);
    top.showDialogBox("编辑角色", str_url, 495, 800);
}

function viewRole() {
    var roleId = parent.roleList.sSelectedRoleID;
    if(roleId == "") {
        alert(errorRole);
        return;
    }
    var roleType = parent.roleList.roleForm.roleType.value;
    var str_url = contextPath + "/system/role?action=beforeEdit&roleId=" + roleId + "&roleType=" + roleType + "&isEdit=false&random=" + Math.random();
    //windowPopup(str_url, 650, 450);
    top.showDialogBox("查看角色", str_url, 495, 800);
}
//删除角色
function deleteRole() {
    var roleId = parent.roleList.sSelectedRoleID;
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
</html>