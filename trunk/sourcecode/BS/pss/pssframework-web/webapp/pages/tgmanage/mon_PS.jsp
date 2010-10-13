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
    sb_dto.append('"collectObjects_Transmit":').append('[{');
    sb_dto.append('"terminalAddr":"' + $("#logicalAddr").val() + '"').append(',');
    sb_dto.append('"equipProtocol":"' + $("#protocolNo").val() + '"').append(',');
    sb_dto.append('"meterAddr":"' + $("#meterAddr").val() + '"').append(',');
    sb_dto.append('"meterType":"' + $("#meterType").val() + '"').append(',');
    sb_dto.append('"funcode":"1"').append(',');
    sb_dto.append('"port":"' + $("#port").val() + '"').append(',');
    sb_dto.append('"serialPortPara":').append('{');
    sb_dto.append('"baudrate":"110"').append(',');
    sb_dto.append('"stopbit":"1"').append(',');
    sb_dto.append('"checkbit":"0"').append(',');
    sb_dto.append('"odd_even_bit":"1"').append(',');
    sb_dto.append('"databit":"8"');
    sb_dto.append('}').append(',');
    sb_dto.append('"waitforPacket":"10"').append(',');
    sb_dto.append('"waitforByte":"5"').append(',');
    sb_dto.append('"commandItems":').append('[').append('{');
    sb_dto.append('"identifier":').append('"8000B66F"');
    sb_dto.append('}').append(']');
    sb_dto.append('}]');
    sb_dto.append('}');
    
    var params = {
            "dto": sb_dto.toString(),
            "mtoType": $("#protocolNo").val(),
            "monType": "PS",
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
            "monType": "PS",
            "logicalAddr": $("#logicalAddr").val(),
            "gpSn": $("#meterAddr").val(),
            "commandItem": "8000B66F",
            "chartHistoryValues02": $("#chartValues02").val(),
            "chartHistoryValues03": $("#chartValues03").val(),
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
                $("#chartValues02").val(data.chartValues02);
                $("#chartValues03").val(data.chartValues03);
                refreshChart("chart02", data.chartXML02);
                refreshChart("chart03", data.chartXML03);
                if(cntMonitor > 0) {
                    cntMonitor--;
                    setTimeout("monitor()", 5000);
                }
                else {
                    parent.endMonitoring1();
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
                    parent.endMonitoring1();
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
    <input type="hidden" id="psId" name="psId" value="${param.psId}" />
    <input type="hidden" id="logicalAddr" name="logicalAddr" value="${param.logicalAddr}" />
    <input type="hidden" id="protocolNo" name="protocolNo" value="${param.protocolNo}" />
    <input type="hidden" id="meterAddr" name="meterAddr" value="${param.meterAddr}" />
    <input type="hidden" id="meterType" name="meterType" value="${param.meterType}" />
    <input type="hidden" id="port" name="port" value="${param.port}" />
    <input type="hidden" id="psModel" name="psModel" value="${param.psModel}" />
    <input type="hidden" id="chartValues02" name="chartValues02" value="" />
    <input type="hidden" id="chartValues03" name="chartValues03" value="" />
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