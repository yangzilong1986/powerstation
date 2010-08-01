<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="system.role.manage.title"/></title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<script type="text/javascript">
var contextPath = "${ctx}";
function fresh() {
    var roleType = roleList.roleForm.roleType.value;
    roleList.document.forms[0].submit();
    //roleList.location.href=contextPath + "/system/role?action=getQuery&sqlcode=ROLE0001&roleType="+roleType+"&random=" + Math.random();; 
    document.frames["roleDetail"].location.href = contextPath + '/system/role/detail?roleType=1&random=' + Math.random();
}
</script>
</head>
<body>
<div id="main2" style="height: 100%;">
  <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
    <tr>
      <td width="390" valign="top" class="pad10" style="border-right:1px solid #5d90d7;">
        <iframe width="100%" height="100%" frameborder="0" id="roleList" name="roleList" src="${ctx}/system/role/query?roleType=1" ></iframe>
      </td>
      <td valign="top" class="pad5">
        <iframe width="100%" height="100%" frameborder="0" id="roleDetail" name="roleDetail" src="${ctx}/system/role/detail?roleType=1" ></iframe>
      </td>
    </tr>
  </table>
</div>
<iframe id="hideframe" name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"> </iframe>
</body>
</html>
