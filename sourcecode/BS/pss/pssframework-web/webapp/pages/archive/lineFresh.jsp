<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>
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
<script type="text/javascript" language="javascript">
var iResult = parseInt("<peis:param type="PAGE" paramName="iResult"/>");
var sResult = "<peis:param type="PAGE" paramName="sResult"/>";
var orgNo=parent.document.getElementsByName("orgNo")[0].value;

if(iResult >= 0) {
    alert(sResult);
    top.opener.fresh(orgNo);
    top.window.close();
}
else {
    alert(sResult);
}
</script>
</body>

</html>
