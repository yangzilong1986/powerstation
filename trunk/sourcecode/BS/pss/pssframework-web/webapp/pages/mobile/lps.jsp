<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:if test="${param.read != 'true' && set != 'true'}">
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
<div style="height: 20px;"></div>
<form id="lpsForm" name="lpsForm" action="${ctx}/mobile/lps/s" method="post">
<input type="hidden" id="psId" name="psId" value="${psInfo.psId}" />
<input type="hidden" id="modelId" name="modelId" value="${result.lpModelId}" />
<input type="hidden" id="S_8000C04F10" name="S_8000C04F10" value="${result.funcSetupBytes}" />
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
        <select style="width: 95px; height: 22px;" disabled="disabled">
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
         <span class="red"><strong>mA</strong></span>
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
        <input id="cbdGearValue" name="cbdGearValue" type="text" value="${result.cbdGearValue}" disabled="disabled" style="width: 95px; height: 20px;" /> <span class="red"><strong>mS</strong></span>
      </td>
    </tr>
    <tr>
      <td height="25" align="right">告警功能启用状态：</td>
      <td align="left">
        <select id="stateAlarm" name="stateAlarm" style="width: 120px; height: 22px;">
          <c:if test="${result.funcSetupByte4 == '0'}"><option value="1" selected="selected">启用</option></c:if>
          <c:if test="${result.funcSetupByte4 != '0'}"><option value="1">启用</option></c:if>
          <c:if test="${result.funcSetupByte4 == '1'}"><option value="0" selected="selected">停用</option></c:if>
          <c:if test="${result.funcSetupByte4 != '1'}"><option value="0">停用</option></c:if>
        </select>
      </td>
    </tr>
    <tr>
      <td height="25" align="right">特波功能启用状态：</td>
      <td align="left">
        <select id="stateElliott" name="stateElliott" style="width: 120px; height: 22px;">
          <c:if test="${result.funcSetupByte5 == '1'}"><option value="1" selected="selected">启用</option></c:if>
          <c:if test="${result.funcSetupByte5 != '1'}"><option value="1">启用</option></c:if>
          <c:if test="${result.funcSetupByte5 == '0'}"><option value="0" selected="selected">停用</option></c:if>
          <c:if test="${result.funcSetupByte5 != '0'}"><option value="0">停用</option></c:if>
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