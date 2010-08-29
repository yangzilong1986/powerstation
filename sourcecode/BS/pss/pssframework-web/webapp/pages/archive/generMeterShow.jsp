
<%@include file="../../commons/taglibs.jsp"%>



<%@page contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<peis:contextPath />/css/window.css" rel="stylesheet" type="text/css">

<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<html>
<head>
<title><bean:message bundle="archive" key="archive.edit.meter"/></title>
</head>
<script type="text/javascript" language="javascript">
function isModeMeter() {
	if(""==document.getElementsByName("assetNo")[0].value) {
		alert("<bean:message bundle="archive" key="archive.meter.assetnononull"/>");
		return false;
	}
	if(""==document.getElementsByName("termId")[0].value) {
		alert("<bean:message bundle="archive" key="archive.meter.termnonull"/>");
		return false;
	}
    if(""==document.getElementsByName("mpName")[0].value) {
		alert("计量点名称不能为空！");
		return false;
	}
   if(""==document.getElementsByName("gpAddr")[0].value) {
		alert("表地址不能为空！");
		return false;
	}
   if(""==document.getElementsByName("port")[0].value) {
		alert("通讯端口不能为空!");
		return false;
	}
	
   return true;
}
var contextPath = "<peis:contextPath/>";
function update() {
	if(isModeMeter()){
	document.getElementsByName("Update")[0].disabled = true;
	document.forms[0].submit();
	}
}

</script>
<body>
<ul class="e_title">
    <li class="e_titleon"><a href="#"><bean:message bundle="archive" key="archive.meter.info"/></a>
  </ul>
  <div class="user_add_table">
  <html:form id="meterForm" action="/archive/meterMpAction" method="post" target="resultPage" >
    	<input type="hidden" name="className" value="AMeterMp">
    	<input type="hidden" name="action" value="updateMeterMp">
    	<input type="hidden" name="termId_old" value="<bean:write name="termId" />">
        <peis:text type="hidden" name="mpId"/>
  
  <table width="96%" border="0" align="center"  cellpadding="0" cellspacing="0" >
  
     <tr>
     <td align="right" width="20%"><bean:message bundle="archive" key="archive.gp.ASSET_NO"/>：</td>
      <td width="30%">
      <input type="hidden" readonly="readonly"name="assetNo" value="<bean:write name='meter' property='assetNo'/>" /><bean:write name='meter' property='assetNo'/>
      </td>
    </tr>
    <tr>
       <td align="right" width="20%"><bean:message bundle="archive" key="archive.meter.type"/>：</td>
      <td width="30%"><peis:selectlist name="meterType" sql="COMMON0021" value="<bean:write name='meter' property='meterType'/>"  extendProperty="class='user_add_input'"/></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meter.MP_NAME"/>：</td>
      <td width="30%"><peis:text type="text" name="mpName" styleClass="user_add_input"/></td>
    </tr>
   
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meter.ct"/>：</td>
      <td width="30%"><peis:selectlist name="ctRatio" sql="COMMON0022"  value="<bean:write name='meter' property='ctRatio'/>" extendProperty="class='user_add_input'"/> </td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meter.pt"/>：</td>
      <td width="30%"><peis:selectlist name="ptRatio" sql="COMMON0023" value="<bean:write name='meter' property='ptRatio'/>" extendProperty="class='user_add_input'"/></td>
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meterProNo"/>：</td>
      <td width="30%"><peis:selectlist name="protocolNo" sql="METERPN0001" extendProperty="class='user_add_input'"/> </td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.gp.GP_ADDR"/>：</td>
      <td width="30%"><input type="text" name="gpAddr" value="<bean:write name='gp' property='gpAddr'/>" class="user_add_input"/></td>
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meter.port"/>：</td>
      <td width="30%"><input type="text" name="port" value="<bean:write name='gp' property='port'/>" class="user_add_input"/></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.terminal.MADE_FAC"/>：</td>
      <td width="30%"><peis:selectlist name="madeFactory" sql="COMMON0015" extendProperty="class='user_add_input'"/></td>
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.btl"/>：</td>
      <td width="30%"><peis:selectlist name="btl" sql="COMMON0024" extendProperty="class='user_add_input'"/></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meter.meterDigits"/>：</td>
      <td width="30%">
         <peis:selectlist name="meterDigits" sql="COMMON0033" extendProperty="class='user_add_input'"/>
	  </td>
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.terminal.WIRING_MODE"/>：</td>
      <td width="30%"><peis:selectlist name="wiringMode" sql="COMMON0012" extendProperty="class='user_add_input'"/></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meter.ratedVolt"/>：</td>
      <td width="30%"><peis:selectlist name="ratedVolt" sql="COMMON0025" extendProperty="class='user_add_input'"/></td>
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meter.ratedEc"/>：</td>
      <td width="30%"><peis:selectlist name="ratedEc" sql="COMMON0026" extendProperty="class='user_add_input'"/></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.LEAVT_FAC_NO"/>：</td>
      <td width="30%"><input type="text" name="leaveFacNo" value="<bean:write name="meter" property="leaveFacNo"/>" class="user_add_input"/></td>
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.terminal.logicaladdr"/>：</td>
      <td width="30%">
             <logic:present name="custId">
		     	<peis:selectlist name="termId" sql="TERMINGP0001" extendProperty="class='user_add_input'"/>
		     </logic:present>
		     <logic:present name="tgId">
		     	<peis:selectlist name="termId" sql="TERMINGP0003" extendProperty="class='user_add_input'"/>
		     </logic:present>
      </td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meter.line"/>：</td>
      <td width="30%"><input type="text" name="lineId" value="<bean:write name="meter" property="lineId"/>" class="user_add_input"/></td>
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.gp.status"/>：</td>
      <td width="30%"><peis:selectlist name="status" sql="COMMON0038" extendProperty="class='user_add_input'"/></td>
      <td align="right" width="20%">电量计算标识：</td>
      <td width="30%">
      <logic:equal name="computeFlag"  value="1">
      <input type="checkbox" name="computeFlag" value="1" checked>
     </logic:equal>
     <logic:notEqual name="computeFlag"   value="1">
      <input type="checkbox" name="computeFlag" value="1" >
    </logic:notEqual>
      
      功率计算标识： 
      <logic:equal name="sucratCptId"   value="1">
      <input type="checkbox" name="sucratCptId" value="1" checked>
      </logic:equal>
      <logic:notEqual name="sucratCptId"  value="1">
       <input type="checkbox" name="sucratCptId" value="1" >
      </logic:notEqual></td>
    </tr>
    <tr>
   	<td align="right" colspan="6">
   	    <input class="input_ok" type="button" name="Update" onclick="update();" value="<bean:message key='global.save'/>" >
   		<input class="input_ok" type="button"  value="<bean:message  key='global.cancel'/>" onclick="javascript:parent.window.close();">
   		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   	</td>
   </tr>
  </table>
</html:form>
        <script type="text/javascript" language="javascript">
			if("<bean:write name='gp' property='computeFlag'/>"=="1"){
				document.getElementsByName("computeFlag")[0].checked = true;
			}
		</script>
 </div> 
<iframe name="resultPage" frameborder=0 width=100% height=100%></iframe>
</body>
</html>
