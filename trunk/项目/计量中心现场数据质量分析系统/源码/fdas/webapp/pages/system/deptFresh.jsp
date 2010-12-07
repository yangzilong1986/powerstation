<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>


<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>ResultFresh</title>
</head>

<body>
<script type="text/javascript">
var iResult = parseInt('<peis:param type="PAGE" paramName="iResult"/>');
var sResult = '<peis:param type="PAGE" paramName="sResult"/>';
var orgNo = parent.document.getElementsByName("orgNo")[0].value;

if(iResult >= 0) {
    alert(sResult);
    //top.opener.fresh(orgNo);
    //top.window.close();
    top.getMainFrameObj().document.frames["dept"].fresh(orgNo);
    top.GB_hide();
}
else {
    alert(sResult);
}
</script>
</body>

</html>
