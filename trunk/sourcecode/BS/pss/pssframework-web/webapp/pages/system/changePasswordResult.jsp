<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>changePassword</title>
<link href="<peis:contextPath/>/css/style.css" rel="stylesheet" type="text/css">
</head>

<body>
<script type="text/javascript" language="javascript">
var iResult = parseInt("<peis:param type="PAGE" paramName="iResult"/>");
var sResult = "<peis:param type="PAGE" paramName="sResult"/>";
if(iResult >= 0) {
    alert(sResult);
    parent.parent.GB_hide();
}
else {
    alert(sResult);
}
</script>
</body>

</html>
