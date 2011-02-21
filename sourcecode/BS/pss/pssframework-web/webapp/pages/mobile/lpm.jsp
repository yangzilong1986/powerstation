<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:if test="${param.read != 'true'}">
<meta http-equiv="refresh" content="2; url=${ctx}/mobile/lpm/rlpd?psId=${param.psId}&read=true" />
</c:if>
<title>低压漏保及配变管理系统</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/mobile.css" />
</head>
<body>
<div>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="50%" height="20" align="left">漏保名称：${psInfo.psName}</td>
      <td width="50%" align="right"><a href="<pss:path type="webapp"/>/mobile/"> 退 出 </a></td>
    </tr>
  </table>
</div>
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
<div style="height: 40px; text-align: center; vertical-align: bottom;">
  <a href="${ctx}/mobile/lps?psId=${param.psId}" style="border: 1px #AED5D4 solid; font-size: 14px; padding: 5px; text-decoration: none;">漏保参数设置</a>
</div>
<div style="height: 40px; text-align: center; vertical-align: bottom;">
  <a href="${ctx}/mobile/lpt?psId=${param.psId}" style="border: 1px #AED5D4 solid; font-size: 14px; padding: 5px; text-decoration: none;">远 程 分 合 闸</a>
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
      <td width="52%" align="left"><input ci="8000B66F" di="B611" type="text" value="${result.voltA}" style="width: 95px; height: 20px; text-align: right;" /> <span class="red"><strong>V</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">B相电压：</td>
      <td align="left"><input ci="8000B66F" di="B612" type="text" value="${result.voltB}" style="width: 95px; height: 20px; text-align: right;" /> <span class="red"><strong>V</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">C相电压：</td>
      <td align="left"><input ci="8000B66F" di="B613" type="text" value="${result.voltC}" style="width: 95px; height: 20px; text-align: right;" /> <span class="red"><strong>V</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">A相电流：</td>
      <td align="left"><input ci="8000B66F" di="B621" type="text" value="${result.ecurA}" style="width: 95px; height: 20px; text-align: right;" /> <span class="red"><strong>A</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">B相电流：</td>
      <td align="left"><input ci="8000B66F" di="B622" type="text" value="${result.ecurB}" style="width: 95px; height: 20px; text-align: right;" /> <span class="red"><strong>A</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">C相电流：</td>
      <td align="left"><input ci="8000B66F" di="B623" type="text" value="${result.ecurC}" style="width: 95px; height: 20px; text-align: right;" /> <span class="red"><strong>A</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">剩余电流：</td>
      <td align="left"><input ci="8000B66F" di="B660" type="text" value="${result.ecurR}" style="width: 95px; height: 20px; text-align: right;" /> <span class="red"><strong>mA</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">剩余电流动作值：</td>
      <td align="left"><input ci="8000C04F" di="8000C04F07" type="text" value="${result.erWorkingValue}" style="width: 95px; height: 20px; text-align: right;" /> <span class="red"><strong>mA</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">分断时间：</td>
      <td align="left"><input ci="8000C04F" di="8000C04F09" type="text" value="${result.timeBlock}" style="width: 95px; height: 20px; text-align: right;" /> <span class="red"><strong>ms</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">额定负载电流：</td>
      <td align="left"><input ci="8000C04F" di="8000C04F05" type="text" value="${result.ecurRating}" style="width: 95px; height: 20px; text-align: right;" /> <span class="red"><strong>A</strong></span></td>
    </tr>
    <tr>
      <td height="25" align="right">设备状态：</td>
      <td align="left">
        <textarea ci="8000C04F" di="8000C04F0X" style="width: 97px; height: 80px; overflow: auto;">${result.estatus}</textarea>
      </td>
    </tr>
  </table>
</div>
<div style="height: 10px;"></div>
</body>
</html>