<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>


<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=gb2312">
<title>ResultFresh</title>
<link href="<peis:contextPath/>/style/style.css" rel="stylesheet" type="text/css">
</head>

<body>
<logic:notEmpty name="DELETE_GROUP" scope="request">
<script type="text/javascript">
alert(1);
var contextPath = "<peis:contextPath/>";
var iResult = parseInt('<peis:param type="PAGE" paramName="iResult"/>');
var sResult = '<peis:param type="PAGE" paramName="sResult"/>';
if(iResult >= 0) {
    alert(sResult);
    top.getMainFrameObj().location.href = contextPath + '/jsp/system/manageGroup.jsp?random=' + Math.random();
}
else {
    alert(sResult);
}
</script>
</logic:notEmpty>
<logic:notEmpty name="ADD_NOTICE" scope="request">
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
var iResult = parseInt('<peis:param type="PAGE" paramName="iResult"/>');
var sResult = '<peis:param type="PAGE" paramName="sResult"/>';
if(iResult >= 0) {
    alert(sResult);
    //top.getMainFrameObj().workpage.location.href = contextPath+'/noticeAction.do?action=getQuery&sqlcode=NOTICE0001&random='+ Math.random();
          top.getMainFrameObj().workpage.location.href = contextPath+'/noticeAction.do?action=init&random='+ Math.random();   
   
}
else {
    alert(sResult);
}
</script>
</logic:notEmpty>
<logic:notEmpty name="DELETE_NOTICE" scope="request">
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
var iResult = parseInt('<peis:param type="PAGE" paramName="iResult"/>');
var sResult = '<peis:param type="PAGE" paramName="sResult"/>';
var loadType = '<peis:param type="PAGE" paramName="loadType"/>';
if(iResult >= 0) {
    alert(sResult);
    if(loadType != "50") {
        top.getMainFrameObj().workpage.location.href = contextPath+'/noticeAction.do?action=init&random='+ Math.random();   
    }
    else {
        top.getMainFrameObj().workpage.location.href = contextPath+'/noticeAction.do?action=getQuery&sqlcode=NOTICE0001&random='+ Math.random();
    }
}
else {
    alert(sResult);
}
</script>
</logic:notEmpty>
<logic:notEmpty name="DELETE_USER" scope="request">
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
var iResult = parseInt('<peis:param type="PAGE" paramName="iResult"/>');
var sResult = '<peis:param type="PAGE" paramName="sResult"/>';
if(iResult >= 0) {
    alert(sResult);
    top.getMainFrameObj().subContent.location.href = contextPath + '/jsp/system/userManagerFrame.jsp?random=' + Math.random();
}
else {
    alert(sResult);
}
</script>
</logic:notEmpty>
<logic:notEmpty name="CHANGE_PASSWORD" scope="request">
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
var iResult = parseInt('<peis:param type="PAGE" paramName="iResult"/>');
var sResult = '<peis:param type="PAGE" paramName="sResult"/>';
if(iResult >= 0) {
    alert(sResult);
    top.getMainFrameObj().subContent.location.href = contextPath + '/jsp/system/passwordManage.jsp?random=' + Math.random();
}
else {
    alert(sResult);
}
</script>
</logic:notEmpty>
<logic:notEmpty name="DELETE_ROLE" scope="request">
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
var iResult = parseInt('<peis:param type="PAGE" paramName="iResult"/>');
var sResult = '<peis:param type="PAGE" paramName="sResult"/>';
if(iResult >= 0) {
    alert(sResult);
    top.getMainFrameObj().subContent.location.href = contextPath + '/jsp/system/roleManagerFrame.jsp?random=' + Math.random();
}
else {
    alert(sResult);
}
</script>
</logic:notEmpty>
<logic:notEmpty name="DELETE_CODE" scope="request">
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
var iResult = parseInt('<peis:param type="PAGE" paramName="iResult"/>');
var sResult = '<peis:param type="PAGE" paramName="sResult"/>';
if(iResult >= 0) {
    alert(sResult);
    top.getMainFrameObj().codeList.location.href = contextPath + '/system/codeAction.do?action=getQuery&codeType=1&random=' + Math.random();
}
else {
    alert(sResult);
}
</script>
</logic:notEmpty>
</body>

</html>
