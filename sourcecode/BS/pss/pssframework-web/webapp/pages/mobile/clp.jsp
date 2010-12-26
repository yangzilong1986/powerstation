<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>低压漏保及配变管理系统</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript">
function selectOrg(orgId) {
    //alert(orgId);
    top.location = "<pss:path type="webapp"/>" + "/mobile/clp?orgId=" + orgId + "&tgid=-1&random=" + Math.random();
}

function selectTg(tgId) {
    //alert(tgId);
    if('${param.orgId}' == '' || '${param.orgId}' == 'null') {
        top.location = "<pss:path type="webapp"/>" + "/mobile/clp?orgId=0&tgid=" + tgId + "&random=" + Math.random();
    }
    else {
        top.location = "<pss:path type="webapp"/>" + "/mobile/clp?orgId=${param.orgId}&tgid=" + tgId + "&random=" + Math.random();
    }
}

function selectPs(psId) {
    //alert(psId);
    top.location = "<pss:path type="webapp"/>" + "/mobile/lpm?psId=" + psId + "&random=" + Math.random();
}
</script>
</head>
<body>
<div align="right"><a href="<pss:path type="webapp"/>/mobile/"> 退 出 </a></div>
<!-- <div><input type="text"" id="s" name="s" value="" /> <input type="button" id="btnSearch" value="搜索" /></div> -->
<div>漏&nbsp; 保：</div>
<div>
  <c:forEach var="item" items="${pslist}">
    <a href="javascript: selectPs('<c:out value="${item.psId}"/>');"><c:out value="${item.psName}" /></a>&nbsp;&nbsp;&nbsp; 
  </c:forEach>
</div>
<div>台&nbsp; 区：</div>
<div>
  <c:forEach var="item" items="${tglist}">
    <a href="javascript: selectTg('<c:out value="${item.tgId}"/>');"><c:out value="${item.tgName}" /></a>&nbsp;&nbsp;&nbsp; 
  </c:forEach>
</div>
<div>单&nbsp; 位：</div>
<div>
  <c:forEach var="item" items="${orglist}">
    <a href="javascript: selectOrg('<c:out value="${item.orgId}"/>');"><c:out value="${item.orgName}" /></a>&nbsp;&nbsp;&nbsp; 
  </c:forEach>
</div>
</body>
</html>