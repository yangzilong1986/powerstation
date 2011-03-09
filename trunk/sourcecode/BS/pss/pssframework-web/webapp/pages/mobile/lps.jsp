<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:if test="${param.read != 'true'}">
<meta http-equiv="refresh" content="2; url=${ctx}/mobile/lps/r?psId=${param.psId}&read=true" />
</c:if>
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
<form id="lpsForm" name="lpsForm" action="${ctx}/mobile/lps/s?psId=${param.psId}&modelId=${result.lpModelId}" method="post">
<div align="center">
  <table width="90%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="48%" height="25" align="right">额定过载保护电流值：</td>
      <td width="52%" align="left">
        <input id="S_8001C04F03" name="S_8001C04F03" type="text" value="${result.rlcGearValue}" style="width: 105px; height: 20px;" /> <span class="red"><strong>A</strong></span>
      </td>
    </tr>
    <tr>
      <td height="25" align="right"></td>
      <td align="left">${hintRLCGearValue}</td>
    </tr>
    <tr>
      <td height="25" align="right">剩余电流档位：</td>
      <td align="left">
        <select id="S_8001C04F04" name="S_8001C04F04" style="width: 120px; height: 22px;">
          <c:if test="${result.rcGear == '1'}"><option value="1" selected="selected">1</option></c:if>
          <c:if test="${result.rcGear != '1'}"><option value="1">1</option></c:if>
          <c:if test="${result.rcGear == '2'}"><option value="2" selected="selected">2</option></c:if>
          <c:if test="${result.rcGear != '2'}"><option value="2">2</option></c:if>
          <c:if test="${result.rcGear == '3'}"><option value="3" selected="selected">3</option></c:if>
          <c:if test="${result.rcGear != '3'}"><option value="3">3</option></c:if>
          <c:if test="${result.rcGear == '4'}"><option value="4" selected="selected">4</option></c:if>
          <c:if test="${result.rcGear != '4'}"><option value="4">4</option></c:if>
          <c:if test="${result.rcGear == '5'}"><option value="5" selected="selected">自动挡</option></c:if>
          <c:if test="${result.rcGear != '5'}"><option value="5">自动挡</option></c:if>
        </select>
      </td>
    </tr>
    <tr>
      <td height="25" align="right">剩余电流当前档位值：</td>
      <td align="left">
        <select style="width: 120px; height: 22px;" disabled="disabled">
          <c:if test="${result.rcGear == '1'}"><option value="1" selected="selected">100</option></c:if>
          <c:if test="${result.rcGear != '1'}"><option value="1">1</option></c:if>
          <c:if test="${result.rcGear == '2'}"><option value="2" selected="selected">300</option></c:if>
          <c:if test="${result.rcGear != '2'}"><option value="2">2</option></c:if>
          <c:if test="${result.rcGear == '3'}"><option value="3" selected="selected">500</option></c:if>
          <c:if test="${result.rcGear != '3'}"><option value="3">3</option></c:if>
          <c:if test="${result.rcGear == '4'}"><option value="4" selected="selected">800</option></c:if>
          <c:if test="${result.rcGear != '4'}"><option value="4">4</option></c:if>
          <c:if test="${result.rcGear == '5'}"><option value="5" selected="selected">自动档位值</option></c:if>
          <c:if test="${result.rcGear != '5'}"><option value="5">自动档位值</option></c:if>
        </select>
      </td>
    </tr>
    <tr>
      <td height="25" align="right">漏电分断延迟档位：</td>
      <td align="left">
        <select id="S_8001C04F05" name="S_8001C04F05" style="width: 120px; height: 22px;">
          <c:if test="${result.cbdGear == '1'}"><option value="1" selected="selected">1</option></c:if>
          <c:if test="${result.cbdGear != '1'}"><option value="1">1</option></c:if>
          <c:if test="${result.cbdGear == '2'}"><option value="2" selected="selected">2</option></c:if>
          <c:if test="${result.cbdGear != '2'}"><option value="2">2</option></c:if>
        </select>
      </td>
    </tr>
    <tr>
      <td height="25" align="right">漏电分断延迟时间值：</td>
      <td align="left">
        <input type="text" value="${result.cbdGearValue}" disabled="disabled" style="width: 95px; height: 20px;" /> <span class="red"><strong>mS</strong></span>
      </td>
    </tr>
    <tr>
      <td height="25" align="right">告警功能启用状态：</td>
      <td align="left">
        <select id="S_8000C04F10_07" name="S_8000C04F10_07" style="width: 120px; height: 22px;">
          <c:if test="${result.funcSetupByte7 == '1'}"><option value="1" selected="selected">启用</option></c:if>
          <c:if test="${result.funcSetupByte7 != '1'}"><option value="1">启用</option></c:if>
          <c:if test="${result.funcSetupByte7 == '0'}"><option value="0" selected="selected">停用</option></c:if>
          <c:if test="${result.funcSetupByte7 != '0'}"><option value="0">停用</option></c:if>
        </select>
      </td>
    </tr>
    <tr>
      <td height="25" align="right">特波启用状态：</td>
      <td align="left">
        <select id="S_8000C04F10_08" name="S_8000C04F10_08" style="width: 120px; height: 22px;">
          <c:if test="${result.funcSetupByte8 == '1'}"><option value="1" selected="selected">启用</option></c:if>
          <c:if test="${result.funcSetupByte8 != '1'}"><option value="1">启用</option></c:if>
          <c:if test="${result.funcSetupByte8 == '0'}"><option value="0" selected="selected">停用</option></c:if>
          <c:if test="${result.funcSetupByte8 != '0'}"><option value="0">停用</option></c:if>
        </select>
      </td>
    </tr>
  </table>
</div>
<div style="height: 20px;">&nbsp;</div>
<div style="height: 50px; text-align: center; vertical-align: bottom;">
  <input id="btnSetting" type="submit" value=" 设 置 " style="font-size: 16px;" />
</div>
</form>
<div id="result" style="height: 30px;">${resultMsg}</div>
</body>
</html>