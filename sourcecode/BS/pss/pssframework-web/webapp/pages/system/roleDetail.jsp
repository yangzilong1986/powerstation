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
<div class="pad5"><input type="button" id="new" class="input1"
	value='<bean:message bundle="system" key="button.xz"/>' onclick="newRole()" /> <input type="button" id="edit"
	class="input1" value='<bean:message bundle="system" key="button.bj"/>' onclick="editRole()" /> <input type="button"
	id="delete" class="input1" value='<bean:message bundle="system" key="button.sc"/>' onclick="deleteRole()" /></div>
<div class="tab"><em><spring:message code="system.role.jsxxxx"></spring:message></em></div>
<div class="tab_con">
<div class="main">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tr>
		<td width="30%" class="dom"><bean:message bundle="system" key="role.jssm" />：</td>
		<td width="70%" class="dom"></td>
	</tr>
	<tr>
		<td width="100%" class="dom" colspan="2"><bean:write name="remark" scope="request" /></td>
	</tr>
	<tr>
		<td width="30%" class="dom"><bean:message bundle="system" key="role.jssyydcy" />：</td>
		<td width="70%" class="dom"></td>
	</tr>
	<tr>
		<td width="100%" class="dom" colspan="2"><bean:write name="users" scope="request" /></td>
	</tr>
</table>
</div>
<div class="data1"><span>角色所拥有的功能</span></div>
<div class="data1_con">
<div class="main"
	style="height: expression((( document.documentElement.clientHeight || document.body.clientHeight) -   234 ) );">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<thead>
		<tr>
			<th><bean:message bundle="system" key="role.xh" /></th>
			<th><bean:message bundle="system" key="role.gnbh" /></th>
			<th><bean:message bundle="system" key="role.gnmc" /></th>
			<th><bean:message bundle="system" key="role.gnsm" /></th>
		</tr>
	</thead>
	<tbody>
		<logic:present name="PG_QUERY_RESULT">
			<logic:iterate id="datainfo" name="PG_QUERY_RESULT" indexId="number">
				<tr align="center" onclick="selectSingleRow(this);" style="cursor: pointer;">
					<td><bean:write name="datainfo" property="rowNo" /></td>
					<td><bean:write name="datainfo" property="col1" /></td>
					<td align="left" style="padding-left: 5px;"><bean:write name="datainfo" property="col2" /></td>
					<td align="left" style="padding-left: 5px;"><bean:write name="datainfo" property="col3" /></td>
				</tr>
			</logic:iterate>
		</logic:present>
	</tbody>
</table>
</div>
</div>
</div>
</div>
</body>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
var errorRole = '<bean:message bundle="system" key="role.errorRole"/>';
var errorDelRole = '<bean:message bundle="system" key="role.errorDelRole"/>';
var confirmDel = '<bean:message bundle="system" key="role.confirmDel"/>';
//新增角色
function newRole() {
    var roleType = parent.roleList.roleForm.roleType.value;
    var str_url = contextPath + "/system/roleAction.do?action=beforeEdit&roleType=" + roleType + "&random=" + Math.random();
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
    var str_url = contextPath + "/system/roleAction.do?action=beforeEdit&roleId=" + roleId + "&roleType=" + roleType + "&random=" + Math.random();
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
    var str_url = contextPath + "/system/roleAction.do?action=beforeEdit&roleId=" + roleId + "&roleType=" + roleType + "&isEdit=false&random=" + Math.random();
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
        parent.hideframe.location.href = contextPath + "/system/roleAction.do?action=delete&roleId=" + roleId + "&roleType=" + roleType + "&random=" + Math.random();
    }
}
function fresh(Id) {
    var roleType = parent.roleList.roleForm.roleType.value;
    parent.roleList.roleForm.tem.value = Id;
    parent.roleList.document.forms[0].submit();
    parent.document.frames["roleList"].location.href = contextPath
            + '/system/roleAction.do?action=getQuery&roleType=1&id=' + Id + '&random=' + Math.random();
    parent.document.frames["roleDetail"].location.href = contextPath
            + '/system/roleAction.do?action=detail&roleId=' + Id + '&roleType=' + roleType + '&random='
            + Math.random();
}
</script>
</html>