<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>台区监测</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/effects.js"></script>
<script type="text/javascript">
var setupFlag = false;
var monObject = 0;  // 监测对象默认为总表

function mySwitchTab(prefix, order, cnts) {
    if(cntMonitor0 > 0) {
        alert("正在监测总表...");
        return false;
    }
    if(cntMonitor1 > 1) {
        alert("正在监测漏保开关...");
        return false;
    }
    if(cntMonitor2 > 1) {
        alert("正在监测油温...");
        return false;
    }
    if(cntMonitor3 > 1) {
        alert("正在监测开关量...");
        return false;
    }
    SwitchTab(prefix, order, cnts);
    monObject = order;
    initMonitorPage();
    return true;
}

function initMonitorPage() {
    var url = "<pss:path type="webapp"/>/tgmanage/tgmon";
    if(monObject == 0) {      //总表监测
        if($("#totalMeter").val() == "" || $("#totalMeter").val() == null || $("#totalMeter").val() == "null") {
            alert("该台区下没有总表档案");
            return;
        }
        var params = $("#totalMeter").val().split("#");
        url += "/tm?gpId=" + params[0] + "&logicalAddr=" + params[1] + "&protocolNo=" + params[2] + "&gpSn=" + params[3];
        $("#mframe0").attr("src", url);
    }
    else if(monObject == 1) { //漏保开关监测
        if($("#ps").val() == "" || $("#ps").val() == null || $("#ps").val() == "null") {
            alert("该台区下没有漏保开关档案");
            return;
        }
        var params = $("#ps").val().split("#");
        url += "/ps?psId=" + params[0] + "&logicalAddr=" + params[1] + "&protocolNo=" + params[2] + "&meterAddr=" + params[3] + "&meterType=" + params[4] + "&port=" + params[5] + "&psModel=" + params[6];
        $("#mframe1").attr("src", url);
    }
    else if(monObject == 2) { //油温监测
        if($("#analog").val() == "" || $("#analog").val() == null || $("#analog").val() == "null") {
            alert("该台区下没有直流模拟量档案");
            return;
        }
        var params = $("#analog").val().split("#");
        url += "/aq?gpId=" + params[0] + "&logicalAddr=" + params[1] + "&protocolNo=" + params[2] + "&gpSn=" + params[3];
        $("#mframe2").attr("src", url);
    }
    else if(monObject == 3) { //开关量监测
        alert("该功能尚未实现!");
        if($("#term").val() == "" || $("#term").val() == null || $("#term").val() == "null") {
            //alert("该台区下没有集中器档案");
            return;
        }
        url += "/sw/" + $("#term").val();
        //$("#mframe3").attr("src", url);
        return;
    }
}

$(document).ready(function() {
    initMonitorPage();

    $("#startMonitoringBtn0").attr("disabled", false);
    $("#endMonitoringBtn0").attr("disabled", true);

    $("#startMonitoringBtn1").attr("disabled", false);
    $("#endMonitoringBtn1").attr("disabled", true);

    $("#startMonitoringBtn2").attr("disabled", false);
    $("#endMonitoringBtn2").attr("disabled", true);

    $("#startMonitoringBtn3").attr("disabled", false);
    $("#endMonitoringBtn3").attr("disabled", true);
});

var cntMonitor0 = 0;
var cntMonitor1 = 0;
var cntMonitor2 = 0;
var cntMonitor3 = 0;
// 开始监测 总表
function startMonitoring0() {
    cntMonitor0 = 10;
    $("#startMonitoringBtn0").attr("disabled", true);
    $("#endMonitoringBtn0").attr("disabled", false);
}
// 结束监测 总表
function endMonitoring0() {
    cntMonitor0 = 0;
    $("#startMonitoringBtn0").attr("disabled", false);
    $("#endMonitoringBtn0").attr("disabled", true);
}
// 开始监测 漏保开关
function startMonitoring1() {
    cntMonitor1 = 10;
    $("#startMonitoringBtn1").attr("disabled", true);
    $("#endMonitoringBtn1").attr("disabled", false);
}
// 结束监测 漏保开关
function endMonitoring1() {
    cntMonitor1 = 0;
    $("#startMonitoringBtn1").attr("disabled", false);
    $("#endMonitoringBtn1").attr("disabled", true);
}
// 开始监测 油温监测仪
function startMonitoring2() {
    cntMonitor2 = 10;
    $("#startMonitoringBtn2").attr("disabled", true);
    $("#endMonitoringBtn2").attr("disabled", false);
}
// 结束监测 油温监测仪
function endMonitoring2() {
    cntMonitor2 = 0;
    $("#startMonitoringBtn2").attr("disabled", false);
    $("#endMonitoringBtn2").attr("disabled", true);
}
// 开始监测 开关量
function startMonitoring3() {
    cntMonitor3 = 10;
    $("#startMonitoringBtn3").attr("disabled", true);
    $("#endMonitoringBtn3").attr("disabled", false);
}
// 结束监测 开关量
function endMonitoring3() {
    cntMonitor3 = 0;
    $("#startMonitoringBtn3").attr("disabled", false);
    $("#endMonitoringBtn3").attr("disabled", true);
}
</script>
</head>
<body style="overflow: auto;">
<div>
  <div class="jc_tab">
    <ul id=jc_Option>
      <li class="curr" id=jc_Option_0 style="cursor: pointer;" onclick="return mySwitchTab('jc_',0,4)">总表监测</li>
      <li id=jc_Option_1 style="cursor: pointer;" onclick="return mySwitchTab('jc_',1,4)">漏保开关监测</li>
      <li id=jc_Option_2 style="cursor: pointer;" onclick="return mySwitchTab('jc_',2,4)">油温监测</li>
      <li id=jc_Option_3 style="cursor: pointer;" onclick="return mySwitchTab('jc_',3,4)">开关量监测</li>
    </ul>
  </div>
  <div class="jc_con" id=jc_Con>
    <ul class=default id=jc_Con_0>
      <div style="vertical-align:middle; height: 30px;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="70" height="30" align="right" class="green">总　表：</td>
            <td width="120" align="left">
              <select id="totalMeter" name="totalMeter" onchange="chgTotalMeter()">
                <c:forEach var="item" items="${tmlist}">
                  <option value="<c:out value="${item.VUE}"/>"><c:out value="${item.NME}" /></option>
                </c:forEach>
              </select>
            </td>
            <td align="right">
              <security:authorize ifAnyGranted="ROLE_AUTHORITY_7">
                <input type="button" id="startMonitoringBtn0" value="开始监测" class="jc_sub mgl10" onclick="startMonitoring0()" />
                <input type="button" id="endMonitoringBtn0" value="结束监测" class="jc_sub mgl10" onclick="endMonitoring0()" />
              </security:authorize>
            </td>
          </tr>
        </table>
      </div>
      <div class="content">
        <div id="cont_0" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-75));">
          <iframe id="mframe0" name="mframe0" scrolling="auto" frameborder="0" style="display: block; width: 100%; height: 100%"></iframe>
        </div>
      </div>
    </ul>
    <ul class=disNone id=jc_Con_1>
      <div style="vertical-align:middle; height: 30px;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="70" height="30" align="right" class="green">漏保开关：</td>
            <td width="120" align="left">
              <select id="ps" name="ps" onchange="chgPS()">
                <c:forEach var="item" items="${pslist}">
                  <option value="<c:out value="${item.VUE}"/>"><c:out value="${item.NME}" /></option>
                </c:forEach>
              </select>
            </td>
            <td align="right">
              <security:authorize ifAnyGranted="ROLE_AUTHORITY_7">
                <input type="button" id="startMonitoringBtn1" value="开始监测" class="jc_sub mgl10" onclick="startMonitoring1()" />
                <input type="button" id="endMonitoringBtn1" value="结束监测" class="jc_sub mgl10" onclick="endMonitoring1()" />
              </security:authorize>
            </td>
          </tr>
        </table>
      </div>
      <div class="content">
        <div id="cont_1" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-75));">
          <iframe id="mframe1" name="mframe1" scrolling="auto" frameborder="0" style="display: block; width: 100%; height: 100%"></iframe>
        </div>
      </div>
    </ul>
    <ul class=disNone id=jc_Con_2>
      <div style="vertical-align:middle; height: 30px;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="80" height="30" align="right" class="green">油温监测仪：</td>
            <td width="120" align="left">
              <select id="analog" name="analog" onchange="chgAnalog()">
                <c:forEach var="item" items="${aqlist}">
                  <option value="<c:out value="${item.VUE}"/>"><c:out value="${item.NME}" /></option>
                </c:forEach>
              </select>
            </td>
            <td align="right">
              <security:authorize ifAnyGranted="ROLE_AUTHORITY_7">
                <input type="button" id="startMonitoringBtn2" value="开始监测" class="jc_sub mgl10" onclick="startMonitoring2()" />
                <input type="button" id="endMonitoringBtn2" value="结束监测" class="jc_sub mgl10" onclick="endMonitoring2()" />
              </security:authorize>
            </td>
          </tr>
        </table>
      </div>
      <div class="content">
        <div id="cont_2" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-75));">
          <iframe id="mframe2" name="mframe2" scrolling="auto" frameborder="0" style="display: block; width: 100%; height: 100%"></iframe>
        </div>
      </div>
    </ul>
    <ul class=disNone id=jc_Con_3>
    </ul>
  </div>
</div>
</body>
</html>