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
<div class="electric_lcon" id="electric_Con"><form:form action="/system/role" modelAttribute="role">
  <ul class=default id=electric_Con_1>
    <div class="mgt10 da_top"><span><spring:message code="system.role.jsxxxx"></spring:message></span></div>
    <div>
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
      <tr style="height: 20px;">
        <td width="30%"><spring:message code="system.role.jssm" /> ：</td>
        <td width="70%"></td>
      </tr>
      <tr style="height: 20px;">
        <td colspan="2" style="padding-left: 10px;">${role.roleRemark}</td>
      </tr>
      <tr style="height: 20px;">
        <td width="30%">角色权限 ：</td>
        <td width="70%"></td>
      </tr>
      <tr style="height: 20px;">
        <td colspan="2" style="padding-left: 10px;">${role.authNames}</td>
      </tr>
    </table>
    </div>
    <div class="mgt10 da_top"><span>角色所拥有的功能</span></div>
    <div class="content">
    <div id="cont_1">
    <div class="tableContainer"
      style="height: expression((( document.documentElement.clientHeight ||   document.body.clientHeight) -   146 ) );">
    <iframe style="border: opx solid;" name="menuFunctionFrame" src="${ctx}/tree/fun/simpleTree/${role.roleId}"
      scrolling="auto" width="100%" height="100%" frameborder="0" align="middle"> </iframe></div>
    </div>
    </div>
  </ul>
</form:form></div>
</body>
<script type="text/javascript">
</script>
</html>