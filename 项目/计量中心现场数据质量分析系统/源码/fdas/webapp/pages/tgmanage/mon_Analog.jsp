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
var cntMonitor = 0;
$(document).ready(function() {
    
});

function startMonitoring() {
    cntMonitor = 10;
    monitor();
}

function endMonitoring() {
    cntMonitor = 0;
}

function StringBuffer() {
    this.data = [];
}

StringBuffer.prototype.append = function() {
    this.data.push(arguments[0]);
    return this;
}

StringBuffer.prototype.toString = function() {
    return this.data.join("");
}

function monitor() {
    var sb_dto = new StringBuffer();
    sb_dto.append('{');
    sb_dto.append('"collectObjects":').append('[');
    sb_dto.append('{');
    sb_dto.append('"logicalAddr":"' + $("#logicalAddr").val() + '"').append(',');
    sb_dto.append('"equipProtocol":"' + $("#protocolNo").val() + '"').append(',');
    sb_dto.append('"channelType":"1"').append(',');
    sb_dto.append('"pwAlgorith":"0"').append(',');
    sb_dto.append('"pwContent":"8888"').append(',');
    sb_dto.append('"mpExpressMode":"3"').append(',');
    sb_dto.append('"mpSn":["' + $("#gpSn").val() + '"]').append(',');
    sb_dto.append('"commandItems":').append('[').append('{');
    sb_dto.append('"identifier":"100C0073"');
    sb_dto.append('}').append(']');
    sb_dto.append('}');
    sb_dto.append(']}');
    
    var params = {
            "dto": sb_dto.toString(),
            "mtoType": $("#protocolNo").val(),
            "monType": "Analog",
            "random": Math.random()
    };
    
    var url = '<pss:path type="webapp"/>/tgmanage/tgmon/down.json';
    $.ajax({
        type: 'POST',
        url: url,
        data: jQuery.param(params),
        dataType: 'json',
        success: function(data) {
            //alert(data.collectId);
            //alert(data.fetchCount);
            setTimeout("fetchReturnResult(" + data.collectId + ", " + data.fetchCount + ")", 3000);
        },
        error: function() {
        }
    });
}

function fetchReturnResult(collectId, fetchCount) {
    var params = {
            "collectId": collectId,
            "monType": "Analog",
            "logicalAddr": $("#logicalAddr").val(),
            "gpSn": $("#gpSn").val(),
            "commandItem": "100C0073",
            "chartHistoryValues01": $("#chartValues01").val(),
            "random": Math.random()
    };
    
    var url = '<pss:path type="webapp"/>/tgmanage/tgmon/up.json';
    $.ajax({
        type: "POST",
        url: url,
        data: jQuery.param(params),
        dataType: 'json',
        success: function(data) {
            if(data.returnFlag) {
                $("#chartValues01").val(data.chartValues01);
                refreshChart("chart01", data.chartXML01);
                if(cntMonitor > 0) {
                    cntMonitor--;
                    setTimeout("monitor()", 5000);
                }
                else {
                    parent.endMonitoring2();
                }
            }
            else if(!data.returnFlag && fetchCount > 0) {
                setTimeout("fetchReturnResult(" + collectId + ", " + (fetchCount - 1) + ")", 3000);
            }
            else {
                if(cntMonitor > 0) {
                    cntMonitor--;
                    setTimeout("monitor()", 5000);
                }
                else {
                    parent.endMonitoring2();
                }
            }
        },
        error: function(){
            //alert("error")
        }
    });
}

function refreshChart(chartId, chartXML) {
    var chartObj = getChartFromId(chartId);
    chartObj.setDataXML(chartXML);
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
    <input type="hidden" id="chartValues01" name="chartValues01" value="" />
  </div>
  <div style="float: left; width: 100%; height: 100%">
    <c:out value='${initChart01}' escapeXml="false" />
  </div>
</div>
</body>
</html>