<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<html>
<head>

</head>

<body>
<html:form action="/autorm/fastReadingAction" styleId="fastReadingActionForm" method="post">
    <input type="hidden" name="action"/>
    <input type="hidden" name="sys_object"/>
    <input type="hidden" name="r_selectlist"/>
    <input type="hidden" name="r_commanditems"/>
    <input type="hidden" name="points"/>
    <html:hidden property="r_appids" styleId="r_appids"/>
    <html:hidden property="r_resend" styleId="r_resend"/>
    <html:hidden property="isAll" styleId="isAll"/>
</html:form>

<logic:notEmpty name="fetchCount" scope="request">
<script type="text/javascript">
parent.fetchReturnResult($("#r_appids").val(), '<bean:write name="fetchCount" scope="request"/>', "<bean:write name='commanditems' scope='request'/>");
</script>
</logic:notEmpty>
</body>

</html>
