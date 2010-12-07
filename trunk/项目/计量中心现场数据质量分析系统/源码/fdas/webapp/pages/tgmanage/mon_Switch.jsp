<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>开关量监测</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/FusionChartsJSClass/FusionCharts.js"></script>
<script type="text/javascript">
var cntMonitor = 0;
$(document).ready(function() {
    
});

function startMonitoring() {
    cntMonitor = 10;
}

function endMonitoring() {
    cntMonitor = 0;
}
</script>
</head>
<body>
<div class="graphContainer" style="width: 100%; border: 0; height:expression(((document.documentElement.clientHeight||document.body.clientHeight)));">

</div>
</body>
</html>