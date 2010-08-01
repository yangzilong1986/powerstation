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
var contextPath = "${ctx}";
var errorUser = '<spring:message code="system.user.errorUser" />';
var errorDelUser = '<spring:message code="system.user.errorDelUser" />';
var confirmDel = '<spring:message code="system.user.confirmDel" />';

//新增操作员
function newUser() {
    var str_url = contextPath + "/system/user/new";
    windowPopup(str_url, 800, 495);
}

//编辑操作员
function editUser() {
    var userId = '${user.empNo}';
    if(userId == "") {
        alert(errorUser);
        return;
    }
    var str_url = contextPath + "/system/user/" + userId + "/edit";
    windowPopup(str_url, 800, 495);
}

//查看操作员
function viewUser() {
    var userId = parent.userList.sSelectedUserID;
    if(userId == "") {
        alert(errorUser);
        return;
    }
    var str_url = contextPath + "/system/user/" + userId ;
    windowPopup(str_url, 800, 495);
    //top.showDialogBox("查看操作员", str_url, 495, 800);
}

//删除账号
function deleteUser() {
    var userId = parent.userList.sSelectedUserID;
    if(userId == "") {
        alert(errorDelUser);
        return;
    }
    if(confirm(confirmDel)) {
        parent.hideframe.location.href = contextPath + "/system/user/" + userId + "?_method=delete&random=" + Math.random();
    }
}
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
	<ul class="default" id="electric_Con_1">
		<div><input type="button" id="new" class="btnbg4" value=<spring:message code="system.button.xz" />
			onclick="newUser()" /> <input type="button" id="edit" class="btnbg4"
			value=<spring:message code="system.button.bj" /> onclick="editUser()" /> <input type="button" id="delete"
			class="btnbg4" value=<spring:message code="system.button.sc" /> onclick="deleteUser()" /></div>
		<br />
		<div class="tab" id="detail_tile"><span><spring:message code="system.user.czyxx" /></span></div>
		<div class="tab_con">
		<div class="main">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr height ="25px;">
				<td width="15%" align="right" class="green"><spring:message code="system.user.zh" />：</td>
				<td width="25%">${user.staffNo}</td>
				<td width="15%" align="right" class="green"><spring:message code="system.user.mc" />：</td>
				<td width="25%">${user.name}</td>
			</tr>
			<tr height ="25px;">
				<td width="15%" align="right" class="green"><spring:message code="system.user.ssdw" />：</td>
				<td width="25%">${user.orgInfo.orgName}</td>
				<td width="15%" align="right" class="green"><spring:message code="system.user.dh" />：</td>
				<td width="25%">${user.mobile}</td>
			</tr>
			<tr height ="25px;">
				<td width="15%" align="right" class="green"><spring:message code="system.user.zt" />：</td>
				<td width="25%">
				<pss:code code="${user.enable}" codeCate="<%=SystemConst.CODE_USER_STATUS %>" />
				</td>
				<td width="15%" align="right" class="green"></td>
				<td width="25%"></td>
			</tr>
		</table>
		</div>
		<div class="tab" id="detail_tile"><span><spring:message code="system.user.czyqx" /></span></div>
		<div class="data1_con">
		<div class="main"
			style="height: expression((( document.documentElement.clientHeight ||document.body.clientHeight) -204 ) );">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr height ="25px;">
				<td align="left"><spring:message code="system.user.ywfw" />：</td>
			</tr>
			<tr height ="25px;">
				<td style="padding-left: 10px;"></td>
			</tr>
			<tr height ="25px;">
				<td align="left"><spring:message code="system.user.gwjs" />：</td>
			</tr>
			<tr height ="25px;">
				<td style="padding-left: 10px;"></td>
			</tr>
			<tr height ="25px;">
				<td align="left"><spring:message code="system.user.czqx" />：</td>
			</tr>
			<tr height ="25px;">
				<td style="padding-left: 10px;"></td>
			</tr>
		</table>
		</div>
		</div>
		</div>
	</ul>
	</div>
</body>
</html>
