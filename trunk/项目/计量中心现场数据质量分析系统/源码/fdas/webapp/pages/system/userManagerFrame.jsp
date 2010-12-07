<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="system.user.manage.title" /></title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript">
var contextPath = "${ctx}";
function fresh() {
    userList.document.forms[0].submit();
   // document.frames["userManager"].location.href = contextPath + '/system/userAction.do?action=detail&random=' + Math.random();
}
</script>
</head>
<body>
<div id="main2" style="height: 100%;">
  <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
    <tr>
    <td width="1px;"></td>
      <td width="390" valign="top" >
        <iframe width="100%" height="100%" frameborder="0" id="userList" name="userList" src="${ctx}/system/user/list"></iframe>
      </td>
       <td width="5" valign="top" >
        
      </td>
      <td valign="top" >
        <iframe width="100%" height="100%" frameborder="0" name="userManager" id="userManager" src=""></iframe>
      </td>
      <td width="1px;"></td>
    </tr>
  </table>
</div>
<iframe id="hideframe" name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"> </iframe>
</body>
</html>
