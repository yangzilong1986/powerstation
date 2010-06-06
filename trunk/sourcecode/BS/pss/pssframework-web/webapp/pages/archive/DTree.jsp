<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<script type="text/javascript">var projectPath ="<%=request.getContextPath()%>";</script>
	<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/js/ext/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/js/ext/resources/css/tree.css" />
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/ext/ext-all.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/ext/ext-lang-zh_CN.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/tree.js"></script>
	<script type="text/javascript"
		src="<%=request.getContextPath()%>/js/xmlTreeLoad.js"></script>
	<script type="text/javascript">
    var model="ALL2000001";
	var nT=5; 
	var rootname="部门列表";
   </script>

	<title>DTree.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<div id="treeField"></div>
	 <!-- <input type="button" value="改变" onclick='treereload("ALL2000002",2)' /> -->
</body>
</html:html>
