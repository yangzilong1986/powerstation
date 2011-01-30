<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>低压漏保及配变管理系统</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/mobile.css" />
</head>
<body>
<div align="right"><a href="<pss:path type="webapp"/>/mobile/"> 退 出 </a></div>
<div>漏&nbsp; 保：</div>
<div>&nbsp;&nbsp;&nbsp; 
  <c:forEach var="item" items="${pslist}">
    <a href="<pss:path type='webapp'/>/mobile/lpm?psId=${item.psId}"><c:out value="${item.psName}" /></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
  </c:forEach>
</div>
<div>台&nbsp; 区：</div>
<div>&nbsp;&nbsp;&nbsp; 
  <c:forEach var="item" items="${tglist}">
    <a href="<pss:path type='webapp'/>/mobile/clp?orgId=${param.orgId}&tgId=${item.tgId}"><c:out value="${item.tgName}" /></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
  </c:forEach>
</div>
<div>单&nbsp; 位：</div>
<div>&nbsp;&nbsp;&nbsp; 
  <c:forEach var="item" items="${orglist}">
    <a href="<pss:path type='webapp'/>/mobile/clp?orgId=${item.orgId}"><c:out value="${item.orgName}" /></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
  </c:forEach>
</div>
</body>
</html>