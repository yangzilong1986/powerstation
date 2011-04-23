<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:if test="${goRlpd != 'true'}">
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
<div style="height: 50px; text-align: center; vertical-align: bottom;">
  <a href="${ctx}/mobile/lpt/rb?psId=${param.psId}" style="background-color: #E3EEFB; border: 1px #AED5D4 solid; font-size: 14px; font-weight: bold; padding: 5px 13px; text-decoration: none;">远 程 分 闸</a>
</div>
<div style="height: 50px; text-align: center; vertical-align: bottom;">
  <a href="${ctx}/mobile/lpt/rs?psId=${param.psId}" style="background-color: #E3EEFB; border: 1px #AED5D4 solid; font-size: 14px; font-weight: bold; padding: 5px 13px; text-decoration: none;">远 程 合 闸</a>
</div>
<div style="height: 50px; text-align: center; vertical-align: bottom;">
  <a href="${ctx}/mobile/lpt/rt?psId=${param.psId}" style="background-color: #E3EEFB; border: 1px #AED5D4 solid; font-size: 14px; font-weight: bold; padding: 5px; text-decoration: none;">漏电动作试跳</a>
</div>
<div style="height: 50px; text-align: center; vertical-align: bottom;">
  <a href="${ctx}/mobile/lpm/rlpd?psId=${param.psId}&read=true" style="background-color: #E3EEFB; border: 1px #AED5D4 solid; font-size: 14px; font-weight: bold; padding: 5px; text-decoration: none;">读取开关状态</a>
</div>
<div id="resultRemote" style="height: 30px;">${resultMsg}</div>
</body>
</html>