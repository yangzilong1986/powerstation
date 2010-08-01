<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>远程试验跳</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript">
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

function remoteTest() {
    disableOperation();
    var sb_dto = new StringBuffer();
    sb_dto.append('{');
    sb_dto.append('"collectObjects_Transmit":').append('[{');
    sb_dto.append('"terminalAddr":"' + $("#logicalAddr").val() + '"').append(',');
    sb_dto.append('"equipProtocol":"' + $("#protocolNo").val() + '"').append(',');
    sb_dto.append('"meterAddr":"' + $("#meterAddr").val() + '"').append(',');
    sb_dto.append('"meterType":"' + $("#meterType").val() + '"').append(',');
    sb_dto.append('"funcode":"' + $("#funcode").val() + '"').append(',');
    sb_dto.append('"port":"' + $("#port").val() + '"').append(',');
    sb_dto.append('"serialPortPara":').append('{');
    sb_dto.append('"baudrate":"' + $("#baudrate").val() + '"').append(',');
    sb_dto.append('"stopbit":"' + $("#stopbit").val() + '"').append(',');
    sb_dto.append('"checkbit":"' + $("#checkbit").val() + '"').append(',');
    sb_dto.append('"odd_even_bit":"' + $("#odd_even_bit").val() + '"').append(',');
    sb_dto.append('"databit":"' + $("#databit").val() + '"');
    sb_dto.append('}').append(',');
    sb_dto.append('"waitforPacket":"' + $("#waitforPacket").val() + '"').append(',');
    sb_dto.append('"waitforByte":"' + $("#waitforByte").val() + '"').append(',');
    sb_dto.append('"commandItems":').append('[').append('{');
    sb_dto.append('"identifier":').append('"8000C037"');
    sb_dto.append('}').append(']');
    sb_dto.append('}]');
    sb_dto.append('}');
    initOpResult('正在试验跳...');
    var url = '<pss:path type="webapp"/>/psmanage/rmttest/down.json';
    var params = {
            "dto": sb_dto.toString(),
            "mtoType": $("#protocolNo").val()
    };
    $.ajax({
        type: 'POST',
        url: url,
        data: jQuery.param(params),
        dataType: 'json',
        success: function(data) {
            //alert(data.collectId);
            //alert(data.fetchCount);
            setTimeout("fetchSetupResult(" + data.collectId + ", " + data.fetchCount + ")", 3000);
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
            initOpResult('下发试验跳命令失败...');
            enableOperation();
        }
    });
}

function fetchSetupResult(collectId, fetchCount) {
    //alert(collectId + "," + fetchCount);
    //alert($("#opcilist").val());
    var url = '<pss:path type="webapp"/>/psmanage/rmttest/up.json';
    var params = {
            "collectId": collectId
    };
    $.ajax({
        type: 'POST',
        url: url,
        data: jQuery.param(params),
        dataType: 'json',
        success: function(data) {
            var b = showSetupResult(data.resultMap);
            if(!b && fetchCount > 0) {
                setTimeout("fetchSetupResult(" + collectId + ", " + (fetchCount - 1) + ")", 3000);
            }
            else if(b) {
                enableOperation();
            }
            else {
                initOpResult('试验跳超时');
                enableOperation();
            }
        },
        error: function() {
        }
    });
}

function showSetupResult(resultMap) {
    var logicalAddr = $("#logicalAddr").val();
    var meterAddr = $("#meterAddr").val();
    var result = resultMap[logicalAddr + '#' + meterAddr + "#" + "8000C037"];
    if(typeof result != "undefined") {
        $("#resultMsg").html(result);
        return true;
    }
    else {
        return false;
    }
}

function disableOperation() {
    $("#rmtTestBtn").attr("disabled", true);
}

function enableOperation() {
    $("#rmtTestBtn").attr("disabled", false);
}

function initOpResult(msg) {
    $("#resultMsg").html(msg);
}
</script>
</head>
<body>
<div style="background: #dbeaeb; border: 1px #add0d5 solid; height:expression((document.documentElement.clientHeight||document.body.clientHeight));">
  <div style="display: none;">
    <input type="hidden" id="protocolNo" name="protocolNo" value="100" />
    <input type="hidden" id="logicalAddr" name="logicalAddr" value="96123456" />
    <input type="hidden" id="meterAddr" name="meterAddr" value="1" />
    <input type="hidden" id="meterType" name="meterType" value="100" />
    <input type="hidden" id="funcode" name="funcode" value="4" />
    <input type="hidden" id="port" name="port" value="1" />
    <input type="hidden" id="baudrate" name="baudrate" value="110" />
    <input type="hidden" id="stopbit" name="stopbit" value="1" />
    <input type="hidden" id="checkbit" name="checkbit" value="0" />
    <input type="hidden" id="odd_even_bit" name="odd_even_bit" value="1" />
    <input type="hidden" id="databit" name="databit" value="8" />
    <input type="hidden" id="waitforPacket" name="waitforPacket" value="10" />
    <input type="hidden" id="waitforByte" name="waitforByte" value="5" />
  </div>
  <div style="text-align: center; padding-top: expression((parentNode.clientHeight/2)-120)">
    <input type="button" id="rmtTestBtn" value="远程试验跳" style="width: 300px; height: 100px; cursor: pointer; font-size: 18px; font-weight: bolder;" onclick="remoteTest()" />
  </div>
  <div id="resultMsg" style="text-align: center; padding: 20px; font-family: serif; font-size: 16px;"></div>
</div>
</body>
</html>