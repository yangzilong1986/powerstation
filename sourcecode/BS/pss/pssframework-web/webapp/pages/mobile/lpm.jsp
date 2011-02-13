<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>低压漏保及配变管理系统</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/mobile.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    $("#btnParameterSetting").click( function() {
        parameterSetting();
    });
    
    $("#btnTripping").click( function() {
        tripping();
    });
    
    readB66F();
    readC04F();
});

function parameterSetting() {
    //alert("parameterSetting");
    top.location = "<pss:path type="webapp"/>" + "/mobile/lps?psId=${param.psId}&random=" + Math.random();
}

function tripping() {
    //alert("tripping");
    top.location = "<pss:path type="webapp"/>" + "/mobile/lpt?psId=${param.psId}&random=" + Math.random();
}


function readB66F() {
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
    sb_dto.append('"baudrate":"' + $("#baudrate").val() + '"').append(',');
    sb_dto.append('"stopbit":"' + $("#stopbit").val() + '"').append(',');
    sb_dto.append('"checkbit":"' + $("#checkbit").val() + '"').append(',');
    sb_dto.append('"odd_even_bit":"' + $("#odd_even_bit").val() + '"').append(',');
    sb_dto.append('"databit":"' + $("#databit").val() + '"');
    sb_dto.append('}').append(',');
    sb_dto.append('"waitforPacket":"' + $("#waitforPacket").val() + '"').append(',');
    sb_dto.append('"waitforByte":"' + $("#waitforByte").val() + '"').append(',');
    sb_dto.append('"commandItems":').append('[').append('{');
    sb_dto.append('"identifier":').append('"8000B66F"');
    sb_dto.append('}').append(']');
    sb_dto.append('}]');
    sb_dto.append('}');
    var url = '<pss:path type="webapp"/>/psmanage/psmon/down.json';
    var params = {
            "dto": sb_dto.toString(),
            "mtoType": $("#protocolNo").val(),
            "fetchCount": 3,
            "random": Math.random()
    };
    $.ajax({
        type: 'POST',
        url: url,
        data: jQuery.param(params),
        dataType: 'json',
        success: function(data) {
            //alert(data.collectId);
            //alert(data.fetchCount);
            setTimeout("fetchResultReadB66F(" + data.collectId + ", " + data.fetchCount + ")", 7000);
        },
        error: function(XmlHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
            //setTimeout("readB66F()", 3000);
        }
    });
}

function fetchResultReadB66F(collectId, fetchCount) {
    //alert(collectId + "," + fetchCount);
    //alert($("#opcilist").val());
    var url = '<pss:path type="webapp"/>/psmanage/psmon/up.json';
    var params = {
            "collectId": collectId,
            "type": "ReadB66F"
    };
    $.ajax({
        type: 'POST',
        url: url,
        data: jQuery.param(params),
        dataType: 'json',
        success: function(data) {
            var b = showResultReadB66F(data.resultMap);
            if(!b && fetchCount > 0) {
                setTimeout("fetchResultReadB66F(" + collectId + ", " + (fetchCount - 1) + ")", 7000);
            }
        },
        error: function() {
            alert(errorThrown);
            //setTimeout("readB66F()", 5000);
        }
    });
}

function showResultReadB66F(resultMap) {
    var logicalAddr = $("#logicalAddr").val();
    var meterAddr = $("#meterAddr").val();
    var result = resultMap[logicalAddr + '#' + fillTopsMeterAddr(meterAddr) + "#" + "8000B66F"];
    //alert(result);
    if(typeof result != "undefined") {
        for(var i = 0; i < $("input[ci='8000B66F']").length; i++) {
            $($("input[ci='8000B66F']")[i]).val(result[$($("input[ci='8000B66F']")[i]).attr("di")]);
        }
        return true;
    }
    else {
        return false;
    }
}

function readC04F() {
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
    sb_dto.append('"baudrate":"' + $("#baudrate").val() + '"').append(',');
    sb_dto.append('"stopbit":"' + $("#stopbit").val() + '"').append(',');
    sb_dto.append('"checkbit":"' + $("#checkbit").val() + '"').append(',');
    sb_dto.append('"odd_even_bit":"' + $("#odd_even_bit").val() + '"').append(',');
    sb_dto.append('"databit":"' + $("#databit").val() + '"');
    sb_dto.append('}').append(',');
    sb_dto.append('"waitforPacket":"' + $("#waitforPacket").val() + '"').append(',');
    sb_dto.append('"waitforByte":"' + $("#waitforByte").val() + '"').append(',');
    sb_dto.append('"commandItems":').append('[').append('{');
    sb_dto.append('"identifier":').append('"8000C04F"');
    sb_dto.append('}').append(']');
    sb_dto.append('}]');
    sb_dto.append('}');
    var url = '<pss:path type="webapp"/>/psmanage/psmon/down.json';
    var params = {
            "dto": sb_dto.toString(),
            "mtoType": $("#protocolNo").val(),
            "fetchCount": 3,
            "random": Math.random()
    };
    $.ajax({
        type: 'POST',
        url: url,
        data: jQuery.param(params),
        dataType: 'json',
        success: function(data) {
            //alert(data.collectId);
            //alert(data.fetchCount);
            setTimeout("fetchResultReadC04F(" + data.collectId + ", " + data.fetchCount + ")", 7000);
        },
        error: function(XmlHttpRequest, textStatus, errorThrown) {
            alert(errorThrown);
            //setTimeout("readC04F()", 3000);
        }
    });
}

function fetchResultReadC04F(collectId, fetchCount) {
    //alert(collectId + "," + fetchCount);
    //alert($("#opcilist").val());
    var url = '<pss:path type="webapp"/>/psmanage/psmon/up.json';
    var params = {
            "collectId": collectId,
            "type": "ReadC04F"
    };
    $.ajax({
        type: 'POST',
        url: url,
        data: jQuery.param(params),
        dataType: 'json',
        success: function(data) {
            var b = showResultReadC04F(data.resultMap);
            if(!b && fetchCount > 0) {
                setTimeout("fetchResultReadC04F(" + collectId + ", " + (fetchCount - 1) + ")", 7000);
            }
        },
        error: function() {
            alert(errorThrown);
            //setTimeout("readC04F()", 5000);
        }
    });
}

function showResultReadC04F(resultMap) {
    var logicalAddr = $("#logicalAddr").val();
    var meterAddr = $("#meterAddr").val();
    var result = resultMap[logicalAddr + '#' + fillTopsMeterAddr(meterAddr) + "#" + "8000C04F"];
    if(typeof result != "undefined") {
        for(var i = 0; i < $("input[ci='8000C04F']").length; i++) {
            $($("input[ci='8000C04F']")[i]).val(result[$($("input[ci='8000C04F']")[i]).attr("di")]);
        }
        
        var msgStatus = "";
        if(result['8000C04F01'] == '0') {
            $("#rmtTripImg").attr('src', '<pss:path type="bgcolor"/>/img/pss-on.png');
            msgStatus += "合闸；\r";
        }
        else if(result['8000C04F01'] == '1') {
            $("#rmtTripImg").attr('src', '<pss:path type="bgcolor"/>/img/pss-off.png');
            msgStatus += "分闸；\r";
            if(result['8000C04F02'] == '0') {
                msgStatus += "未闭锁；\r";
            }
            else if(result['8000C04F02'] == '1') {
                msgStatus += "闭锁；\r";
            }

            if(result['8000C04F03'] == '00') {
                msgStatus += "相位：无效；\r";
            }
            else if(result['8000C04F03'] == '01') {
                msgStatus += "相位：A相；\r";
            }
            else if(result['8000C04F03'] == '10') {
                msgStatus += "相位：B相；\r";
            }
            else if(result['8000C04F03'] == '11') {
                msgStatus += "相位：C相；\r";
            }

            if(result['8000C04F04'] == '0000') {
                msgStatus += "漏电跳闸";
            }
            else if(result['8000C04F04'] == '0001') {
                msgStatus += "突变跳闸";
            }
            else if(result['8000C04F04'] == '0010') {
                msgStatus += "特波跳闸";
            }
            else if(result['8000C04F04'] == '0011') {
                msgStatus += "过载跳闸";
            }
            else if(result['8000C04F04'] == '0100') {
                msgStatus += "过压跳闸";
            }
            else if(result['8000C04F04'] == '0101') {
                msgStatus += "欠压跳闸";
            }
            else if(result['8000C04F04'] == '0110') {
                msgStatus += "短路跳闸";
            }
            else if(result['8000C04F04'] == '0111') {
                msgStatus += "手动跳闸";
            }
            else if(result['8000C04F04'] == '1000') {
                msgStatus += "停电跳闸";
            }
            else if(result['8000C04F04'] == '1001') {
                msgStatus += "互感器故障跳闸";
            }
            else if(result['8000C04F04'] == '1010') {
                msgStatus += "远程跳闸";
            }
            else if(result['8000C04F04'] == '1011') {
                msgStatus += "其它原因跳闸";
            }
            else if(result['8000C04F04'] == '1100') {
                msgStatus += "合闸过程中";
            }
            else if(result['8000C04F04'] == '1101') {
                msgStatus += "合闸失败";
            }
        }
        $("textarea[ci='8000C04F'][di='8000C04F0X']").val(msgStatus);
        
        return true;
    }
    else {
        return false;
    }
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

function fillTopsMeterAddr(meterAddr) {
    var result = $.trim(meterAddr);
    var lens = result.length;
    if(lens < 12) {
        for( var i = 0; i < (12 - lens); i++) {
            result = '0' + result;
        }
    }
    else {
        result = result.substring(0, 12);
    }

    return result;
}
</script>
</head>
<body>
<noscript>
  <div><input type="button" id="ns" name="ns" value=" 召 测 " /></div>
</noscript>
<div align="left" style="float: left">漏保名称：${psInfo.psName}</div>
<div align="right" style="float: right;"><a href="<pss:path type="webapp"/>/mobile/"> 退 出 </a></div>
<div style="height: 10px;">
  <input type="hidden" id="psId" name="psId" value="${param.psId}" />
  <input type="hidden" id="protocolNo" name="protocolNo" value="${psInfo.terminalInfo.protocolNo}" />
  <input type="hidden" id="logicalAddr" name="logicalAddr" value="${psInfo.terminalInfo.logicalAddr}" />
  <input type="hidden" id="meterAddr" name="meterAddr" value="${psInfo.gpInfo.gpAddr}" />
  <input type="hidden" id="meterType" name="meterType" value="100" />
  <input type="hidden" id="port" name="port" value="1" />
  <input type="hidden" id="baudrate" name="baudrate" value="110" />
  <input type="hidden" id="stopbit" name="stopbit" value="1" />
  <input type="hidden" id="checkbit" name="checkbit" value="0" />
  <input type="hidden" id="odd_even_bit" name="odd_even_bit" value="1" />
  <input type="hidden" id="databit" name="databit" value="8" />
  <input type="hidden" id="waitforPacket" name="waitforPacket" value="10" />
  <input type="hidden" id="waitforByte" name="waitforByte" value="5" />
  <input type="hidden" id="psModel" name="psModel" value="${psModel.code}" />
</div>
<div style="height: 50px; text-align: center; vertical-align: bottom;">
  <input id="btnParameterSetting" type="button" value="漏保参数设置" style="font-size: 14px; width: 100px; height: 30px; vertical-align: middle;" />
  <input id="btnTripping" type="button" value="远程分合闸" style="font-size: 14px; width: 100px; height: 30px; vertical-align: middle;" />
</div>
<div align="center">
  <table width="90%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="50%" height="25" align="left">资产编号：<strong>${psInfo.assetNo}</strong></td>
      <td width="50%" align="left">漏保名称：<strong>${psInfo.psName}</strong></td>
    </tr>
    <tr>
      <td height="25" align="left">漏保型号：<strong>${psModel.name}</strong></td>
      <td align="left">漏保类型：<strong>${psType.name}</strong></td>
    </tr>
  </table>
</div>
<div align="center">
  <table width="90%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="48%" height="25" align="right">A相电压：</td>
      <td width="52%" align="left"><input ci="8000B66F" di="B611" type="text" value="" style="width: 95px; height: 20px; text-align: right;" /> <span class="red"><strong>V</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">B相电压：</td>
      <td align="left"><input ci="8000B66F" di="B612" type="text" value="" style="width: 95px; height: 20px; text-align: right;" /> <span class="red"><strong>V</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">C相电压：</td>
      <td align="left"><input ci="8000B66F" di="B613" type="text" value="" style="width: 95px; height: 20px; text-align: right;" /> <span class="red"><strong>V</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">A相电流：</td>
      <td align="left"><input ci="8000B66F" di="B621" type="text" value="" style="width: 95px; height: 20px; text-align: right;" /> <span class="red"><strong>A</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">B相电流：</td>
      <td align="left"><input ci="8000B66F" di="B622" type="text" value="" style="width: 95px; height: 20px; text-align: right;" /> <span class="red"><strong>A</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">C相电流：</td>
      <td align="left"><input ci="8000B66F" di="B623" type="text" value="" style="width: 95px; height: 20px; text-align: right;" /> <span class="red"><strong>A</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">剩余电流：</td>
      <td align="left"><input ci="8000B66F" di="B660" type="text" value="" style="width: 75px; height: 20px; text-align: right;" /> <span class="red"><strong>mA</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">剩余电流动作值：</td>
      <td align="left"><input ci="8000C04F" di="8000C04F07" type="text" value="" style="width: 75px; height: 20px; text-align: right;" /> <span class="red"><strong>mA</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">分断时间：</td>
      <td align="left"><input ci="8000C04F" di="8000C04F09" type="text" value="" style="width: 75px; height: 20px; text-align: right;" /> <span class="red"><strong>ms</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">额定负载电流：</td>
      <td align="left"><input ci="8000C04F" di="8000C04F05" type="text" value="" style="width: 75px; height: 20px; text-align: right;" /> <span class="red"><strong>A</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">设备状态：</td>
      <td align="left">
        <textarea ci="8000C04F" di="8000C04F0X" style="width: 100px; height: 80px; overflow: auto;"></textarea>
      </td>
    </tr>
  </table>
</div>
<div style="height: 10px;"></div>
</body>
</html>