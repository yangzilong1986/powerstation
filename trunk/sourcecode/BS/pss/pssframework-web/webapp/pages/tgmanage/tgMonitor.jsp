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

function mySwitchTab(prefix, order, cnts) {
    SwitchTab(prefix, order, cnts);
    return true;
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