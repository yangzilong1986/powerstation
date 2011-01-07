<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>低压漏保及配变管理系统</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $("#btnTripping").click(function() {
            remoteTriping();
        });

        $("#btnClosing").click(function() {
            remoteSwitching();
        });

        $("#btnTesting").click(function() {
            remoteTesting();
        });
    });

    var bResultRemote = false;
    function disableRemoteOperation() {
        bResultRemote = false;
        $("#btnTripping").attr("disabled", true);
        $("#btnClosing").attr("disabled", true);
        $("#btnTesting").attr("disabled", true);
    }

    function enableRemoteOperation() {
        $("#btnTripping").attr("disabled", false);
        $("#btnClosing").attr("disabled", false);
        $("#btnTesting").attr("disabled", false);
    }

    function initOpResultRemote(msg) {
        //alert(msg);
        $("#resultRemote").html(msg);
    }

    function showResultRemote(resultMap) {
        //alert(resultMap);
        var logicalAddr = $("#logicalAddr").val();
        var meterAddr = $("#meterAddr").val();
        var result = null;
        result = resultMap[logicalAddr + '#' + fillTopsMeterAddr(meterAddr) + "#" + "8000C037"];
        if(typeof result != "undefined") {
            if(result == "1") {
                result = "开关试验跳成功";
            }
            else if(result == "2") {
                result = "开关试验跳失败";
            }
            $("#resultRemote").html(result);
            bResultRemote = true;
            return true;
        }
        else {
            result = resultMap[logicalAddr + '#' + fillTopsMeterAddr(meterAddr) + "#" + "8000C036"];
            if(typeof result != "undefined") {
                if(result == "1") {
                    result = "开关分合闸成功";
                }
                else if(result == "2") {
                    result = "开关分合闸失败";
                }
                $("#resultRemote").html(result);
                bResultRemote = true;
                return true;
            }
            else {
                return false;
            }
        }
    }

    //开关分闸
    function remoteTriping() {
        disableRemoteOperation();
        var sb_dto = new StringBuffer();
        sb_dto.append('{');
        sb_dto.append('"collectObjects_Transmit":').append('[{');
        sb_dto.append('"terminalAddr":"' + $("#logicalAddr").val() + '"').append(',');
        sb_dto.append('"equipProtocol":"' + $("#protocolNo").val() + '"').append(',');
        sb_dto.append('"meterAddr":"' + $("#meterAddr").val() + '"').append(',');
        sb_dto.append('"meterType":"' + $("#meterType").val() + '"').append(',');
        sb_dto.append('"funcode":"4"').append(',');
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
        sb_dto.append('"identifier":').append('"8000C036"').append(',');
        sb_dto.append('"datacellParam":').append('{');
        sb_dto.append('"8000C03601": "50"');
        sb_dto.append('}');
        sb_dto.append('}').append(']');
        sb_dto.append('}]');
        sb_dto.append('}');
        initOpResultRemote('正在开关分闸...');
        var url = '<pss:path type="webapp"/>/psmanage/psmon/down.json';
        var params = {
            "dto" : sb_dto.toString(),
            "mtoType" : $("#protocolNo").val()
        };
        $.ajax({
            type : 'POST',
            url : url,
            data : jQuery.param(params),
            dataType : 'json',
            success : function(data) {
                //alert(data.collectId);
                //alert(data.fetchCount);
                setTimeout("fetchRemoteTripingResult(" + data.collectId + ", " + data.fetchCount + ")", 3000);
            },
            error : function(XmlHttpRequest, textStatus, errorThrown) {
                initOpResultRemote('下发开关分闸命令失败...');
                enableRemoteOperation();
            }
        });
    }

    function fetchRemoteTripingResult(collectId, fetchCount) {
        var url = '<pss:path type="webapp"/>/psmanage/psmon/up.json';
        var params = {
            "collectId" : collectId,
            "type" : "RemoteTriping"
        };
        $.ajax({
            type : 'POST',
            url : url,
            data : jQuery.param(params),
            dataType : 'json',
            success : function(data) {
                var b = showResultRemote(data.resultMap);
                if(!b && fetchCount > 0) {
                    setTimeout("fetchRemoteTripingResult(" + collectId + ", " + (fetchCount - 1) + ")", 3000);
                }
                else if(b) {
                    enableRemoteOperation();
                }
                else {
                    initOpResultRemote('下发开关分闸命令超时');
                    enableRemoteOperation();
                }
            },
            error : function() {
            }
        });
    }

    //开关合闸
    function remoteSwitching() {
        disableRemoteOperation();
        var sb_dto = new StringBuffer();
        sb_dto.append('{');
        sb_dto.append('"collectObjects_Transmit":').append('[{');
        sb_dto.append('"terminalAddr":"' + $("#logicalAddr").val() + '"').append(',');
        sb_dto.append('"equipProtocol":"' + $("#protocolNo").val() + '"').append(',');
        sb_dto.append('"meterAddr":"' + $("#meterAddr").val() + '"').append(',');
        sb_dto.append('"meterType":"' + $("#meterType").val() + '"').append(',');
        sb_dto.append('"funcode":"4"').append(',');
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
        sb_dto.append('"identifier":').append('"8000C036"').append(',');
        sb_dto.append('"datacellParam":').append('{');
        sb_dto.append('"8000C03601": "5F"');
        sb_dto.append('}');
        sb_dto.append('}').append(']');
        sb_dto.append('}]');
        sb_dto.append('}');
        initOpResultRemote('正在开关合闸...');
        var url = '<pss:path type="webapp"/>/psmanage/psmon/down.json';
        var params = {
            "dto" : sb_dto.toString(),
            "mtoType" : $("#protocolNo").val()
        };
        $.ajax({
            type : 'POST',
            url : url,
            data : jQuery.param(params),
            dataType : 'json',
            success : function(data) {
                //alert(data.collectId);
                //alert(data.fetchCount);
                setTimeout("fetchRemoteSwitchingResult(" + data.collectId + ", " + data.fetchCount + ")", 3000);
            },
            error : function(XmlHttpRequest, textStatus, errorThrown) {
                initOpResultRemote('下发开关合闸命令失败...');
                enableRemoteOperation();
            }
        });
    }

    function fetchRemoteSwitchingResult(collectId, fetchCount) {
        var url = '<pss:path type="webapp"/>/psmanage/psmon/up.json';
        var params = {
            "collectId" : collectId,
            "type" : "RemoteSwitching"
        };
        $.ajax({
            type : 'POST',
            url : url,
            data : jQuery.param(params),
            dataType : 'json',
            success : function(data) {
                var b = showResultRemote(data.resultMap);
                if(!b && fetchCount > 0) {
                    setTimeout("fetchRemoteSwitchingResult(" + collectId + ", " + (fetchCount - 1) + ")", 3000);
                }
                else if(b) {
                    enableRemoteOperation();
                }
                else {
                    initOpResultRemote('下发开关合闸命令超时');
                    enableRemoteOperation();
                }
            },
            error : function() {
            }
        });
    }

    // 开关试跳
    function remoteTesting() {
        disableRemoteOperation();
        var sb_dto = new StringBuffer();
        sb_dto.append('{');
        sb_dto.append('"collectObjects_Transmit":').append('[{');
        sb_dto.append('"terminalAddr":"' + $("#logicalAddr").val() + '"').append(',');
        sb_dto.append('"equipProtocol":"' + $("#protocolNo").val() + '"').append(',');
        sb_dto.append('"meterAddr":"' + $("#meterAddr").val() + '"').append(',');
        sb_dto.append('"meterType":"' + $("#meterType").val() + '"').append(',');
        sb_dto.append('"funcode":"4"').append(',');
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
        initOpResultRemote('正在试验跳...');
        var url = '<pss:path type="webapp"/>/psmanage/psmon/down.json';
        var params = {
            "dto" : sb_dto.toString(),
            "mtoType" : $("#protocolNo").val()
        };
        $.ajax({
            type : 'POST',
            url : url,
            data : jQuery.param(params),
            dataType : 'json',
            success : function(data) {
                //alert(data.collectId);
                //alert(data.fetchCount);
                setTimeout("fetchRemoteTestingResult(" + data.collectId + ", " + data.fetchCount + ")", 3000);
            },
            error : function(XmlHttpRequest, textStatus, errorThrown) {
                initOpResultRemote('下发试验跳命令失败...');
                enableRemoteOperation();
            }
        });
    }

    function fetchRemoteTestingResult(collectId, fetchCount) {
        var url = '<pss:path type="webapp"/>/psmanage/psmon/up.json';
        var params = {
            "collectId" : collectId,
            "type" : "RemoteTest"
        };
        $.ajax({
            type : 'POST',
            url : url,
            data : jQuery.param(params),
            dataType : 'json',
            success : function(data) {
                var b = showResultRemote(data.resultMap);
                if(!b && fetchCount > 0) {
                    setTimeout("fetchRemoteTestingResult(" + collectId + ", " + (fetchCount - 1) + ")", 3000);
                }
                else if(b) {
                    enableRemoteOperation();
                }
                else {
                    initOpResultRemote('下发试验跳命令超时');
                    enableRemoteOperation();
                }
            },
            error : function() {
            }
        });
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
<div align="right"><a href="<pss:path type="webapp"/>/mobile/"> 退 出 </a></div>
<div style="height: 50px;">
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
<div style="height: 60px; text-align: center; vertical-align: bottom;">
  <input id="btnTripping" type="button" value="远程分闸" style="font-size: 24px; width: 120px; height: 40px; vertical-align: middle;" />
</div>
<div style="height: 60px; text-align: center; vertical-align: bottom;">
  <input id="btnClosing" type="button" value="远程合闸" style="font-size: 24px; width: 120px; height: 40px; vertical-align: middle;" />
</div>
<div style="height: 60px; text-align: center; vertical-align: bottom;">
  <input id="btnTesting" type="button" value="远程试跳" style="font-size: 24px; width: 120px; height: 40px; vertical-align: middle;" />
</div>
<div id="resultRemote" style="height: 50px;"></div>
</body>
</html>