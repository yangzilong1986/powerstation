<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>userDetail</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
var errorUser = '<bean:message bundle="system" key="user.errorUser"/>';
var errorDelUser = '<bean:message bundle="system" key="user.errorDelUser"/>';
var confirmDel = '<bean:message bundle="system" key="user.confirmDel"/>';

//新增操作员
function newUser() {
    var str_url = contextPath + "/system/userAction.do?action=beforeAdd&random=" + Math.random();
    //windowPopup(str_url, 650, 450);
    top.showDialogBox("新增操作员", str_url, 495, 800);
}

//编辑操作员
function editUser() {
    var userId = parent.userList.sSelectedUserID;
    if(userId == "") {
        alert(errorUser);
        return;
    }
    var str_url = contextPath + "/system/userAction.do?action=beforeEdit&userId=" + userId + "&random=" + Math.random();
    //windowPopup(str_url, 650, 450);
    top.showDialogBox("编辑操作员", str_url, 495, 800);
}

//查看操作员
function viewUser() {
    var userId = parent.userList.sSelectedUserID;
    if(userId == "") {
        alert(errorUser);
        return;
    }
    var str_url = contextPath + "/system/userAction.do?action=beforeEdit&userId=" + userId + "&isEdit=false&random=" + Math.random();
    //windowPopup(str_url, 650, 450);
    top.showDialogBox("查看操作员", str_url, 495, 800);
}

//删除账号
function deleteUser() {
    var userId = parent.userList.sSelectedUserID;
    if(userId == "") {
        alert(errorDelUser);
        return;
    }
    if(confirm(confirmDel)) {
        parent.hideframe.location.href = contextPath + "/system/userAction.do?action=delete&userId=" + userId + "&random=" + Math.random();
    }
}
function fresh(userId) {
    parent.userList.document.forms[0].submit();
    parent.document.frames["userList"].location.href = contextPath + '/system/userAction.do?action=getQuery&id='
            + userId + '&random=' + Math.random();
    parent.document.frames["userManager"].location.href = contextPath
            + '/system/userAction.do?action=detail&userId=' + userId + '&random=' + Math.random();
}
</script>
</head>
<body>
<div>
  <logic:equal name="isEdit" value="1" scope="request">
    <div class="pad5">
      <input type="button" id="new" class="input1" value='<bean:message bundle="system" key="button.xz"/>' onclick="newUser()" />
      <input type="button" id="edit" class="input1" value='<bean:message bundle="system" key="button.bj"/>' onclick="editUser()" />
      <input type="button" id="delete" class="input1" value='<bean:message bundle="system" key="button.sc"/>' onclick="deleteUser()" />
    </div>
  </logic:equal>
  <div class="tab"><em><bean:message bundle="system" key="user.czyxx" /></em></div>
  <div class="tab_con">
    <div class="main">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="15%" class="label"><bean:message bundle="system" key="user.zh" />：</td>
          <td width="25%" class="dom"><bean:write name="userForm" property="userNo" /></td>
          <td width="15%" class="label"><bean:message bundle="system" key="user.mc" />：</td>
          <td width="25%" class="dom"><bean:write name="userForm" property="username" /></td>
        </tr>
        <tr>
          <td width="15%" class="label"><bean:message bundle="system" key="user.ssdw" />：</td>
          <td width="25%" class="dom"><bean:write name="userForm" property="orgName" /></td>
          <td width="15%" class="label"><bean:message bundle="system" key="user.dh" />：</td>
          <td width="25%" class="dom"><bean:write name="userForm" property="phone" /></td>
        </tr>
        <tr>
          <td width="15%" class="label"><bean:message bundle="system" key="user.zt" />：</td>
          <td width="25%" class="dom"><bean:write name="userForm" property="status" /></td>
          <td width="15%" class="label"></td>
          <td width="25%" class="dom"></td>
        </tr>
      </table>
    </div>
    <div class="data1"><span><bean:message bundle="system" key="user.czyqx" /></span></div>
    <div class="data1_con">
      <div class="main" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-204));">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td align="left"><bean:message bundle="system" key="user.ywfw" />：</td>
          </tr>
          <tr>
            <td style="padding-left: 10px;"><bean:write name="fw" scope="request" /></td>
          </tr>
          <tr>
            <td align="left"><bean:message bundle="system" key="user.gwjs" />：</td>
          </tr>
          <tr>
            <td style="padding-left: 10px;"><bean:write name="gw" scope="request" /></td>
          </tr>
          <tr>
            <td align="left"><bean:message bundle="system" key="user.czqx" />：</td>
          </tr>
          <tr>
            <td style="padding-left: 10px;"><bean:write name="qx" scope="request" /></td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</div>
</body>
</html>
