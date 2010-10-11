<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>漏保开关监测</title>
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
    <input type="hidden" id="psId" name="psId" value="${param.psId}" />
    <input type="hidden" id="logicalAddr" name="logicalAddr" value="${param.logicalAddr}" />
    <input type="hidden" id="protocolNo" name="protocolNo" value="${param.protocolNo}" />
    <input type="hidden" id="meterAddr" name="meterAddr" value="${param.meterAddr}" />
    <input type="hidden" id="meterType" name="meterType" value="${param.meterType}" />
    <input type="hidden" id="port" name="port" value="${param.port}" />
    <input type="hidden" id="psModel" name="psModel" value="${param.psModel}" />
  </div>
  <div style="float: left; margin: 2px; width: 49%; height: 300px;">
    <c:out value='${initChart02}' escapeXml="false" />
  </div>
  <div style="float: left; margin: 2px; width: 49%; height: 300px;">
    <c:out value='${initChart03}' escapeXml="false" />
  </div>
</div>
</body>
</html>