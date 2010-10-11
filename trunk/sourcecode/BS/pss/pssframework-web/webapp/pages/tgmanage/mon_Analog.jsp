<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>油温监测</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/FusionChartsJSClass/FusionCharts.js"></script>
<script type="text/javascript">
var bMonitor = false;
$(document).ready(function() {
    
});

function startMonitoring() {
    bMonitor = true;
}

function endMonitoring() {
    bMonitor = false;
}
</script>
</head>
<body>
<div class="graphContainer" style="width: 100%; border: 0; height:expression(((document.documentElement.clientHeight||document.body.clientHeight)));">
  <div>
    <input type="hidden" id="gpId" name="gpId" value="${param.gpId}" />
    <input type="hidden" id="logicalAddr" name="logicalAddr" value="${param.logicalAddr}" />
    <input type="hidden" id="protocolNo" name="protocolNo" value="${param.protocolNo}" />
    <input type="hidden" id="gpSn" name="gpSn" value="${param.gpSn}" />
  </div>
  <div style="float: left; width: 100%; height: 100%">
    <c:out value='${initChart01}' escapeXml="false" />
  </div>
</div>
</body>
</html>