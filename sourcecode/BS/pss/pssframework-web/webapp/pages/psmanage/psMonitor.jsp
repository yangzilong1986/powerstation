<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>漏保监测</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/effects.js"></script>
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.query.js"></script>
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.corner.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    var psId = $.query.get('psId');
    $("#psInfo").corner();
    $("#rmtTrip").corner();
    $("#rtVoltage").corner();
    $("#rtEc").corner();
    $("#rtPsParam").corner();
    $("#timeSetup").corner();
    $("#statusSetup").corner();
    $("#paramsSetup").corner();

    //readB66F();
    //readC04F();
});

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
        for(var i = 0; i < (12 - lens); i++) {
            result = '0' + result;
        }
    }
    else {
        result = result.substring(0, 12);
    }

    return result;
}

function readB66F() {
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
    sb_dto.append('"identifier":').append('"8000B66F"');
    sb_dto.append('}').append(']');
    sb_dto.append('}]');
    sb_dto.append('}');
    var url = '<pss:path type="webapp"/>/psmanage/psmon/down.json';
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
            setTimeout("fetchResultReadB66F(" + data.collectId + ", " + data.fetchCount + ")", 3000);
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
            //alert(errorThrown);
            setTimeout("readB66F()", 3000);
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
                setTimeout("fetchResultReadB66F(" + collectId + ", " + (fetchCount - 1) + ")", 3000);
            }
            else {
                setTimeout("readB66F()", 3000);
            }
        },
        error: function() {
            setTimeout("readB66F()", 3000);
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
    sb_dto.append('"identifier":').append('"8000C04F"');
    sb_dto.append('}').append(']');
    sb_dto.append('}]');
    sb_dto.append('}');
    var url = '<pss:path type="webapp"/>/psmanage/psmon/down.json';
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
            setTimeout("fetchResultReadC04F(" + data.collectId + ", " + data.fetchCount + ")", 3000);
        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
            setTimeout("readC04F()", 3000);
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
                setTimeout("fetchResultReadC04F(" + collectId + ", " + (fetchCount - 1) + ")", 3000);
            }
            else {
                setTimeout("readC04F()", 3000);
            }
        },
        error: function() {
            setTimeout("readC04F()", 3000);
        }
    });
}

function showResultReadC04F(resultMap) {
    var logicalAddr = $("#logicalAddr").val();
    var meterAddr = $("#meterAddr").val();
    var result = resultMap[logicalAddr + '#' + fillTopsMeterAddr(meterAddr) + "#" + "8000C04F"];
    if(typeof result != "undefined") {
        for(var i = 0; i < $("input[ci='8000C04F']").length; i++) {
            if($($("input[ci='8000C04F']")[i]).attr("di") == "8000C04F0X") {
                $($("input[ci='8000C04F']")[i]).val(result['8000C04F01'] + ";" + result['8000C04F02'] + ";" + result['8000C04F03'] + ";" + result['8000C04F04']);
            }
            else {
                $($("input[ci='8000C04F']")[i]).val(result[$($("input[ci='8000C04F']")[i]).attr("di")]);
            }
        }
        return true;
    }
    else {
        return false;
    }
}

function remoteTriping() {
    
}

function remoteSwitching() {
    
}

function remoteTest() {
    
}
</script>
</head>
<body style="overflow-x: hidden; overflow-y: auto;">
<div>
  <div style="display: none;">
    <input type="hidden" id="protocolNo" name="protocolNo" value="100" />
    <input type="hidden" id="logicalAddr" name="logicalAddr" value="96123456" />
    <input type="hidden" id="meterAddr" name="meterAddr" value="1" />
    <input type="hidden" id="meterType" name="meterType" value="100" />
    <input type="hidden" id="funcode" name="funcode" value="1" />
    <input type="hidden" id="port" name="port" value="1" />
    <input type="hidden" id="baudrate" name="baudrate" value="110" />
    <input type="hidden" id="stopbit" name="stopbit" value="1" />
    <input type="hidden" id="checkbit" name="checkbit" value="0" />
    <input type="hidden" id="odd_even_bit" name="odd_even_bit" value="1" />
    <input type="hidden" id="databit" name="databit" value="8" />
    <input type="hidden" id="waitforPacket" name="waitforPacket" value="10" />
    <input type="hidden" id="waitforByte" name="waitforByte" value="5" />
  </div>
  <div id="psInfo" style="margin: 3px; background-color: #dff0f1;">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="18%" height="30" align="right">资产编号：</td>
        <td width="15%" align="left">资产122</td>
        <td width="18%" height="30" align="right">漏保型号：</td>
        <td width="15%" align="left">QLL1-Z(250)</td>
        <td width="18%" height="30" align="right">漏保地址：</td>
        <td width="16%" align="left">12345678</td>
      </tr>
      <tr>
        <td width="18%" height="30" align="right">漏保类型：</td>
        <td width="15%" align="left">总保</td>
        <td width="18%" height="30" align="right">测量点序号：</td>
        <td width="15%" align="left">2</td>
        <td width="18%" height="30" align="right">通信方式：</td>
        <td width="16%" align="left">485</td>
      </tr>
      <tr>
        <td width="18%" height="30" align="right">剩余电流档位：</td>
        <td width="15%" align="left">自动挡</td>
        <td width="18%" height="30" align="right">额定负载电流档位：</td>
        <td width="15%" align="left">20</td>
        <td width="18%" height="30" align="right">剩余电流当前档位：</td>
        <td width="16%" align="left">200</td>
      </tr>
    </table>
  </div>
  <div style="width: 100%; height: 150;">
    <div style="width: 30%; height: 150; float: left;">
      <div id="rmtTrip" style="width: 100%; height: 100%; margin: 3px; background-color: #dff0f1;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="90" align="center"><img id="rmtTripImg" src="<pss:path type="bgcolor"/>/img/ps-on.png" alt="" style="width: 101px; height: 70px;" /></td>
          </tr>
          <tr>
            <td height="30" align="center">
              <input type="button" id="rmtTestBtn" value=" 跳 闸 " style="width: 60px; height: 25px; cursor: pointer; font-size: 14px; font-weight: normal;" onclick="remoteTriping()" />
              <input type="button" id="rmtTestBtn" value=" 合 闸 " style="width: 60px; height: 25px; cursor: pointer; font-size: 14px; font-weight: normal;" onclick="remoteSwitching()" />
            </td>
          </tr>
          <tr>
            <td height="30" align="center">
              <input type="button" id="rmtTestBtn" value=" 试 跳 " style="width: 122px; height: 25px; cursor: pointer; font-size: 14px; font-weight: normal;" onclick="remoteTest()" />
            </td>
          </tr>
        </table>
      </div>
    </div>
    <div style="width: 30%; height: 150; float: left;">
      <div style="width: 100%; height: 72;">
        <div id="rtVoltage" style="width: 100%; height: 100%; margin: 3px; background-color: #dff0f1;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="22" align="center">A相电压：<input ci="8000B66F" di="B611" type="text" value="" style="width: 95px; height: 20px; text-align: right;" /> V </td>
          </tr>
          <tr>
            <td height="22" align="center">B相电压：<input ci="8000B66F" di="B612" type="text" value="" style="width: 95px; height: 20px; text-align: right;" /> V </td>
          </tr>
          <tr>
            <td height="22" align="center">C相电压：<input ci="8000B66F" di="B613" type="text" value="" style="width: 95px; height: 20px; text-align: right;" /> V </td>
          </tr>
        </table>
        </div>
      </div>
      <div style="width: 100%; height: 72;">
        <div id="rtEc" style="width: 100%; height: 100%; margin: 3px; background-color: #dff0f1;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="22" align="center">A相电流：<input ci="8000B66F" di="B621" type="text" value="" style="width: 95px; height: 20px; text-align: right;" /> A </td>
          </tr>
          <tr>
            <td height="22" align="center">B相电流：<input ci="8000B66F" di="B622" type="text" value="" style="width: 95px; height: 20px; text-align: right;" /> A </td>
          </tr>
          <tr>
            <td height="22" align="center">C相电流：<input ci="8000B66F" di="B623" type="text" value="" style="width: 95px; height: 20px; text-align: right;" /> A </td>
          </tr>
        </table>
        </div>
      </div>
    </div>
    <div style="width: 40%; height: 150; float: left;">
      <div id="rtPsParam" style="width: 100%; height: 100%; margin: 3px; background-color: #dff0f1;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" width="45%" height="29">剩余电流：</td>
            <td align="left" width="55%"><input ci="8000B66F" di="B660" type="text" value="" style="width: 95px; height: 20px; text-align: right;" /> mA </td>
          </tr>
          <tr>
            <td align="right" height="29">剩余电流动作值：</td>
            <td align="left"><input ci="8000C04F" di="8000C04F07" type="text" value="" style="width: 95px; height: 20px; text-align: right;" /> mA </td>
          </tr>
          <tr>
            <td align="right" height="29">分断时间：</td>
            <td align="left"><input ci="8000C04F" di="8000C04F09" type="text" value="" style="width: 95px; height: 20px; text-align: right;" /> ms </td>
          </tr>
          <tr>
            <td align="right" height="29">额定负载电流：</td>
            <td align="left"><input ci="8000C04F" di="8000C04F05" type="text" value="" style="width: 95px; height: 20px; text-align: right;" /> A </td>
          </tr>
          <tr>
            <td align="right" height="29">设备状态：</td>
            <td align="left"><input ci="8000C04F" di="8000C04F0X" type="text" value="" style="width: 115px; height: 20px;" /></td>
          </tr>
        </table>
      </div>
    </div>
  </div>
  <div style="margin: 3px;">
    <div id="bg" style="height: 30px; text-align: center;">
      <ul id=”datamenu_Option“ class="cb font1">
        <li class="curr" id=datamenu_Option_0 style="cursor: pointer;" onmouseover="SwitchTab('datamenu_',0,2)">参数设置</li>
        <li id=datamenu_Option_1 style="cursor: pointer;" onmouseover="SwitchTab('datamenu_',1,2)">跳闸信息查询</li>
      </ul>
    </div>
    <div class="datamenu_lcon" id="datamenu_Con">
      <ul class=default id=datamenu_Con_0>
        <div class="content" style="border: 1px solid #85c0b4;">
          <div style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-296));">
            <div id="timeSetup" style="width: 100%; height: 60; margin: 3px; background-color: #dff0f1;">
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td align="right" width="25%" height="30">当前时钟：</td>
                  <td align="left" width="55%"><input ci="8000C012" di="C012" type="text" value="" style="width: 240px; height: 24px;" /></td>
                  <td align="center" width="20%"><input type="button" id="timeReadBtn" value=" 读 取 " style="width: 70px; height: 25px; cursor: pointer; font-size: 14px; font-weight: normal;" onclick="timeRead()" /></td>
                </tr>
                <tr>
                  <td align="right" height="30">计算机时钟：</td>
                  <td align="left"><input id="computerTime" type="text" value="" style="width: 240px; height: 24px;" /></td>
                  <td align="center"><input type="button" id="timeSetupBtn" value=" 设 置 " style="width: 70px; height: 25px; cursor: pointer; font-size: 14px; font-weight: normal;" onclick="timeSetup()" /></td>
                </tr>
              </table>
            </div>
            <div id="statusSetup" style="width: 100%; height: 60; margin: 3px; background-color: #dff0f1;">
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td align="left" width="20%" height="30" style="padding-left: 20px;"><input type="checkbox" id="funcSetupByte1" name="funcSetupByte" /> 欠压保护功能 </td>
                  <td align="left" width="20%" style="padding-left: 20px;"><input type="checkbox" id="funcSetupByte2" name="funcSetupByte" /> 过压保护功能 </td>
                  <td align="left" width="20%" style="padding-left: 20px;"><input type="checkbox" id="funcSetupByte3" name="funcSetupByte" /> 突变保护功能 </td>
                  <td align="left" width="20%" style="padding-left: 20px;"><input type="checkbox" id="funcSetupByte4" name="funcSetupByte" /> 缓变保护功能 </td>
                  <td align="center" width="20%"><input type="button" id="funcSetupByteReadBtn" value=" 读 取 " style="width: 70px; height: 25px; cursor: pointer; font-size: 14px; font-weight: normal;" onclick="funcSetupByteRead()" /></td>
                </tr>
                <tr>
                  <td align="left" height="30" style="padding-left: 20px;"><input type="checkbox" id="funcSetupByte5" name="funcSetupByte" /> 特波保护功能 </td>
                  <td align="left" style="padding-left: 20px;"><input type="checkbox" id="funcSetupByte6" name="funcSetupByte" /> 自动跟踪功能 </td>
                  <td align="left" style="padding-left: 20px;"><input type="checkbox" id="funcSetupByte7" name="funcSetupByte" /> 告警功能 </td>
                  <td align="left" style="padding-left: 20px;"><input type="checkbox" id="funcSetupByte8" name="funcSetupByte" /> 特波动作值  </td>
                  <td align="center"><input type="button" id="funcSetupByteSetupBtn" value=" 设 置 " style="width: 70px; height: 25px; cursor: pointer; font-size: 14px; font-weight: normal;" onclick="funcSetupByteSetup()" /></td>
                </tr>
              </table>
            </div>
            <div id="paramsSetup" style="width: 100%; height: 90; margin: 3px; background-color: #dff0f1;">
              <table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td align="right" width="22%" height="30">剩余电流档位：</td>
                  <td align="left" width="28%">
                    <select rci="8000C04F" rdi="8000C04F06" sci="8001C04F" sdi="8001C04F04" style="width: 120px; height: 24px;">
                      <option>1</option>
                      <option>2</option>
                      <option>3</option>
                      <option>4</option>
                      <option>自动挡</option>
                    </select>
                  </td>
                  <td align="right" width="22%">漏电分断延迟档位：</td>
                  <td align="left" width="28%">
                    <select rci="8000C04F" rdi="8000C04F08" sci="8001C04F" sdi="8001C04F05" style="width: 120px; height: 24px;">
                      <option>1</option>
                      <option>2</option>
                    </select>
                  </td>
                </tr>
                <tr>
                  <td align="right" height="30">剩余电流当前档位值：</td>
                  <td align="left">
                    <select rci="8000C04F" rdi="8000C04F07" style="width: 120px; height: 24px;">
                      <option>100</option>
                      <option>300</option>
                      <option>500</option>
                      <option>800</option>
                      <option>自动挡</option>
                    </select>
                  </td>
                  <td align="right">漏电分断延迟时间值：</td>
                  <td align="left">
                    <input rci="8000C04F" rdi="8000C04F09" type="text" value="" style="width: 100px; height: 20px;" /> ms
                  </td>
                </tr>
                <tr>
                  <td align="right" height="30">额定负载电流档位值：</td>
                  <td align="left">
                    <input rci="8000C04F" rdi="8000C04F05" sci="8001C04F" sdi="8001C04F03" type="text" value="" style="width: 110px; height: 20px;" /> A
                  </td>
                  <td align="center" colspan="2">
                    <input type="button" id="paramsSetupReadBtn" value=" 读 取 " style="width: 70px; height: 25px; cursor: pointer; font-size: 14px; font-weight: normal;" onclick="paramsSetupRead()" />
                    <input type="button" id="paramsSetupSetupBtn" value=" 设 置 " style="width: 70px; height: 25px; cursor: pointer; font-size: 14px; font-weight: normal;" onclick="paramsSetupSetup()" />
                  </td>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </ul>
      <ul class=disNone id=datamenu_Con_1>
        <div class="content">
          <div id="cont_1">
            <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-294));">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <thead>
                  <tr>
                    <th width="8%" class="bg01">序号</th>
                    <th width="30%" class="bg01">跳闸时间</th>
                    <th width="20%" class="bg01">跳闸类型</th>
                    <th width="15%" class="bg01">故障相位</th>
                    <th width="27%" class="bg01">跳闸电参数</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </ul>
    </div>
  </div>
</div>
</body>
</html>