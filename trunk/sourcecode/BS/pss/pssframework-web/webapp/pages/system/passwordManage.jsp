<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title><bean:message bundle="system" key="passwd.edit.title" /></title>
<link href="<peis:contextPath/>/css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
var errorNoPasswd='<bean:message bundle="system" key="passwd.errorNoPasswd"/>';
var errorNoNewPasswd='<bean:message bundle="system" key="passwd.errorNoNewPasswd"/>';
var errorNewPasswdLength='<bean:message bundle="system" key="passwd.errorNewPasswdLength"/>';
var errorNoConPasswd='<bean:message bundle="system" key="passwd.errorNoConPasswd"/>';
var errorPasswdDiff='<bean:message bundle="system" key="passwd.errorPasswdDiff"/>';
//提交处理
function submitDisposal() {
    if(document.all.oldPassword.value == "") {
        alert(errorNoPasswd);
        return false;
    }
    if(document.all.newPassword.value == "") {
        alert(errorNoNewPasswd);
        return false;
    }
    if(document.all.newPassword.value.length<5) {
        alert(errorNewPasswdLength);
        return false;
    }
    if(document.all.affirmPassword.value == "") {
        alert(errorNoConPasswd);
        return false;
    }

    if(document.all.newPassword.value != document.all.affirmPassword.value) {
        alert(errorPasswdDiff);
        return false;
    }

    return true;
}
function fresh(){
	parent.changeType(4);
}
</script>
</head>
<body>
<html:form action="/system/userAction.do" onsubmit="return submitDisposal();" target="hideframe" method="post">
  <input type="hidden" name="action" value="changePassword" />
  <div id="main" style="height: 100%;">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="100%" height="30" colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td width="46%" height="40" align="right"><bean:message bundle="system" key="passwd.srymm" />：<font color="#ff0000">*</font>　</td>
        <td width="54%" align="left">
          <input type="password" name="oldPassword" value="" class="mainInput" />
        </td>
      </tr>
      <tr>
        <td height="40" align="right"><bean:message bundle="system" key="passwd.srxmm" />：<font color="#ff0000">*</font>　</td>
        <td align="left">
          <input type="password" name="newPassword" value="" class="mainInput" />
        </td>
      </tr>
      <tr>
        <td height="40" align="right"><bean:message bundle="system" key="passwd.qrxmm" />：<font color="#ff0000">*</font>　</td>
        <td align="left">
          <input type="password" name="affirmPassword" value="" class="mainInput" />
        </td>
      </tr>
      <tr>
        <td width="100%" height="30" colspan="2">&nbsp;</td>
      </tr>
      <tr>
        <td width="100%" height="50" colspan="2" align="center">
          <html:submit styleClass="input3">
            <bean:message bundle="system" key="button.qd" />
          </html:submit>
          &nbsp;
          <html:reset styleClass="input3">
            <bean:message bundle="system" key="button.qx" />
          </html:reset>
        </td>
      </tr>
    </table>
  </div>
</html:form>
<iframe name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"> </iframe>
</body>
</html>
