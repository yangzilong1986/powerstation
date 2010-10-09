<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<title>表码曲线</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript" src="${ctx}/scripts/FusionChartsJSClass/FusionCharts.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
<div class="graphContainer" style="width: 100%; height:expression(((document.documentElement.clientHeight||document.body.clientHeight)));">
  <c:out value='${chart}' escapeXml="false" />
</div>
</body>
</html>