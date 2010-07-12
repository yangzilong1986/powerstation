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
});
</script>
</head>
<body style="overflow-x: hidden; overflow-y: auto;">
<div>
  <div id="psInfo" style="margin: 3px; background-color: #dff0f1;">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="18%" height="30" align="right">资产编号：</td>
        <td width="15%" align="left">CS0001</td>
        <td width="18%" height="30" align="right">漏保型号：</td>
        <td width="15%" align="left">QLL1-Z(250)</td>
        <td width="18%" height="30" align="right">漏保地址：</td>
        <td width="16%" align="left">000000000001</td>
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
            <td></td>
          </tr>
          <tr>
            <td></td>
          </tr>
          <tr>
            <td></td>
          </tr>
        </table>
      </div>
    </div>
    <div style="width: 30%; height: 150; float: left;">
      <div style="width: 100%; height: 72;">
        <div id="rtVoltage" style="width: 100%; height: 100%; margin: 3px; background-color: #dff0f1;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td></td>
          </tr>
          <tr>
            <td></td>
          </tr>
          <tr>
            <td></td>
          </tr>
        </table>
        </div>
      </div>
      <div style="width: 100%; height: 72;">
        <div id="rtEc" style="width: 100%; height: 100%; margin: 3px; background-color: #dff0f1;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td></td>
          </tr>
          <tr>
            <td></td>
          </tr>
          <tr>
            <td></td>
          </tr>
        </table>
        </div>
      </div>
    </div>
    <div style="width: 40%; height: 150; float: left;">
      <div id="rtPsParam" style="width: 100%; height: 100%; margin: 3px; background-color: #dff0f1;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td></td>
          </tr>
          <tr>
            <td></td>
          </tr>
          <tr>
            <td></td>
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
          <div id="setupTime" style="width: 100%; height: 50; margin: 3px; background-color: #dff0f1;">
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td></td>
              </tr>
              <tr>
                <td></td>
              </tr>
              <tr>
                <td></td>
              </tr>
            </table>
          </div>
        </div>
      </ul>
      <ul class=disNone id=datamenu_Con_1>
        <div class="content">
          <div id="cont_1">
            <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-287));">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <thead>
                  <tr>
                    <th width="5%" class="bg01">序号</th>
                    <th width="30%" class="bg01">跳闸时间</th>
                    <th width="20%" class="bg01">跳闸类型</th>
                    <th width="15%" class="bg01">故障相位</th>
                    <th width="30%" class="bg01">跳闸电参数</th>
                  </tr>
                </thead>
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