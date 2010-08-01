<%@page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=gb2312">
<title>ResultFresh</title>
<link href="<peis:contextPath/>/style/style.css" rel="stylesheet" type="text/css">
</head>

<body>
<logic:notEmpty name="GroupNameExist">
<script type="text/javascript" language="javascript">
var errorQzmc = '<bean:message bundle="system" key="group.error.groupnameexist"/>';
alert(errorQzmc);
</script>
</logic:notEmpty>

<logic:empty name="GroupNameExist">
<script type="text/javascript" language="javascript">
var iResult = parseInt("<peis:param type="PAGE" paramName="iResult"/>");
var sResult = "<peis:param type="PAGE" paramName="sResult"/>";
var Id="<peis:param type="PAGE" paramName="Id"/>";

if(iResult >= 0) {
    alert(sResult);
    parent.fresh(Id);
}
else {
    alert(sResult);
}
</script>
</logic:empty>
</body>

</html>
