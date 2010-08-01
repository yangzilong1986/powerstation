<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title><bean:message bundle="system" key="user.manage.title" /></title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
function fresh() {
    userList.document.forms[0].submit();
    document.frames["userManager"].location.href = contextPath + '/system/userAction.do?action=detail&random=' + Math.random();
}
</script>
</head>
<body>
<div id="main2" style="height: 100%;">
  <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
    <tr>
      <td width="390" valign="top" class="pad10" style="border-right:1px solid #5d90d7;">
        <iframe width="100%" height="100%" frameborder="0" id="userList" name="userList" src="<peis:contextPath/>/system/userAction.do?action=getQuery"></iframe>
      </td>
      <td valign="top" class="pad5">
        <iframe width="100%" height="100%" frameborder="0" name="userManager" id="userManager" src="<peis:contextPath/>/system/userAction.do?action=detail"></iframe>
      </td>
    </tr>
  </table>
</div>
<iframe id="hideframe" name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"> </iframe>
</body>
</html>
