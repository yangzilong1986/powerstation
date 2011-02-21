<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>低压漏保及配变管理系统</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/mobile.css" />
</head>
<body>
<div>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="50%" height="20" align="left">漏保名称：${psInfo.psName}</td>
      <td width="50%" align="right">
        <a href="<pss:path type="webapp"/>/mobile/lpm?psId=${param.psId}"> 返 回 </a>
        &nbsp;&nbsp;&nbsp; 
        <a href="<pss:path type="webapp"/>/mobile/"> 退 出 </a>
      </td>
    </tr>
  </table>
</div>
<div style="height: 20px;">
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
<div align="center">
  <table width="90%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="48%" height="25" align="right">剩余电流档位：</td>
      <td width="52%" align="left">
        <select rci="8000C04F" rdi="8000C04F06" sci="8001C04F" sdi="8001C04F04" style="width: 120px; height: 22px;" onchange="chg8000C04F06(this)">
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">自动挡</option>
        </select>
      </td>
    </tr>
    <tr>
      <td height="25" align="right">漏电分断延迟档位：</td>
      <td align="left">
        <select rci="8000C04F" rdi="8000C04F08" sci="8001C04F" sdi="8001C04F05" style="width: 120px; height: 22px;" onchange="chg8000C04F08(this)">
          <option value="1">1</option>
          <option value="2">2</option>
        </select>
      </td>
    </tr>
    <tr>
      <td height="25" align="right">剩余电流当前档位值：</td>
      <td align="left">
        <select rci="8000C04F" rdi="8000C04F07" style="width: 120px; height: 22px;">
          <option value="1">100</option>
          <option value="2">300</option>
          <option value="3">500</option>
          <option value="4">800</option>
          <option value="5">自动档位值</option>
        </select>
      </td>
    </tr>
    <tr>
      <td height="25" align="right">漏电分断延迟时间值：</td>
      <td align="left">
        <input rci="8000C04F" rdi="8000C04F09" type="text" value="${result.rlcGearValue}" style="width: 95px; height: 20px;" /> <span class="red"><strong>mS</strong></span>
      </td>
    </tr>
    <tr>
      <td height="25" align="right">额定负载电流档位值：</td>
      <td align="left">
        <input rci="8000C04F" rdi="8000C04F05" sci="8001C04F" sdi="8001C04F03" type="text" value="${result.rlcGearValue}" style="width: 105px; height: 20px;" /> <span class="red"><strong>A</strong></span>
      </td>
    </tr>
  </table>
</div>
<div style="height: 20px;">&nbsp;</div>
<div style="height: 50px; text-align: center; vertical-align: bottom;">
  <a href="${ctx}/mobile/lps/r?psId=${param.psId}" style="background-color: #E3EEFB; border: 1px #AED5D4 solid; font-size: 14px; font-weight: bold; padding: 5px 13px; text-decoration: none;"> 读 取 </a>
  &nbsp;&nbsp;&nbsp; 
  <a href="${ctx}/mobile/lps/s?psId=${param.psId}" style="background-color: #E3EEFB; border: 1px #AED5D4 solid; font-size: 14px; font-weight: bold; padding: 5px 13px; text-decoration: none;"> 设 置 </a>
</div>
<div id="result" style="height: 30px;">${resultMsg}</div>
</body>
</html>