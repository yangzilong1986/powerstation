

<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
<%@page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title><bean:message bundle="archive" key="archive.new.meter"/></title>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<peis:contextPath />/css/window.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";


function typeChange() {
	var typeValue = document.getElementsByName("meterType")[0].value;
	var custId = "<bean:write name='custId'/>";
	if(typeValue=='1'){
		document.getElementsByName("assetNo")[0].value = '';
		document.getElementsByName("assetNo")[0].disabled = true;
		var str_url = contextPath + "/archive/meterMpAction.do";
		str_url += "?action=gotoAddYCMeter&object_id="+custId+"&object_type=1";
		document.body.innerHTML='……';
		window.location.href = str_url;
		window.resizeTo(800,600);
		window.moveTo((screen.availWidth-800)/2,(screen.availHeight-600)/2)
		//windowPopup1(str_url,800,550);
		//window.close();
	}
	else{
		document.getElementsByName("assetNo")[0].disabled = false;
	}
}

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
		alert("通讯端口不能为空！");
		return false;
	}
	
	var custId = '<bean:write name="custId"/>'
	if(document.getElementsByName("meterType")[0].value=="1"){
		if(confirm("<bean:message bundle="archive" key="archive.meter.modelmeter"/>")) {
			var str_url = contextPath + "/archive/terminalAction.do";
			str_url += "?action=beforeAdd&custId="+custId;
			windowPopup1(str_url,850,650);
		}
	}
	document.getElementsByName("entry")[0].disabled = true;

}

function initButton(){
	 var termObj = document.all.termId;
	   if(termObj.options.length == 0 || termObj.value == '0')
       {
          document.getElementById("entry").disabled = true;
       }
}
</script>
</head>
<body bgcolor="#f7f7ff"  style="overflow-x:hidden;overflow-y:hidden;" onload="initButton();">
<ul class="e_title">
    <li class="e_titleon"><a href="#"><bean:message bundle="archive" key="archive.meter.info"/></a>
  </ul>
  <div class="user_add_table">  
  <html:form  action="/archive/meterMpAction" method="post" target="hideFrame" onsubmit="return isModeMeter();">
  <input type="hidden" name="custId" value="<bean:write name='custId'/>">
  <input type="hidden" name="action" value="saveOrUpdate"/>
  <table width="96%" border="0" align="center"  cellpadding="0" cellspacing="0" >
   
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meter.type"/>：</td>
      <td width="30%"><peis:selectlist name="meterType" sql="COMMON0021" onChange="typeChange();" extendProperty="class='user_add_input'"/></td>
      <td align="left" colspan="2"></td>
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.gp.ASSET_NO"/>：</td>
      <td width="30%"><input type="text" name="assetNo" class="user_add_input"/></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meter.MP_NAME"/>：</td>
      <td width="30%"><input type="text" name="mpName" class="user_add_input"/></td>
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meter.ct"/>：</td>
      <td width="30%"><peis:selectlist name="ctRatio" sql="COMMON0022" extendProperty="class='user_add_input'"/> </td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meter.pt"/>：</td>
      <td width="30%"><peis:selectlist name="ptRatio" sql="COMMON0023" extendProperty="class='user_add_input'"/></td>
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meterProNo"/>：</td>
      <td width="30%"><peis:selectlist name="proNo" sql="METERPN0001" extendProperty="class='user_add_input'"/> </td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.gp.GP_ADDR"/>：</td>
      <td width="30%"><input type="text" name="gpAddr" class="user_add_input"/></td>
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meter.port"/>：</td>
      <td width="30%"><input type="text" name="port" class="user_add_input"/></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.terminal.MADE_FAC"/>：</td>
      <td width="30%"><peis:selectlist name="madeFactory" sql="COMMON0015" extendProperty="class='user_add_input'"/></td>
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.btl"/>：</td>
      <td width="30%"><peis:selectlist name="btl" sql="COMMON0024" extendProperty="class='user_add_input'"/></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meter.meterDigits"/>：</td>
      <td width="30%">
         <select name="meterDigits" class="user_add_input">
		     	<option value="4">4</option>
		     	<option value="5">5</option>
		     	<option value="6">6</option>
		     	<option value="7">7</option>
		     </select>
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
      <td width="30%"><input type="text" name="leaveFacNo" class="user_add_input"/></td>
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.terminal.LOGICAL_ADDR"/>：</td>
      <td width="30%"><peis:selectlist name="termId" sql="TERMINGP0001" extendProperty="class='user_add_input'"/></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meter.line"/>：</td>
      <td width="30%"><peis:selectlist name="lineId" sql="COMMON0214" extendProperty="class='user_add_input'"/>
      <!-- <input type="text" name="lineId" class="user_add_input"/>--></td>
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.gp.status"/>：</td>
      <td width="30%"><peis:selectlist name="status" sql="COMMON0038" extendProperty="class='user_add_input'"/></td>
      <td align="right" width="20%">电量计算标识：</td>
      <td width="30%"><input type="checkbox" name="computeFlag" value="1" checked>功率计算标识：<input type="checkbox" name="sucratCptId" value="1" checked></td>
    </tr>
    
    <tr>
   	<td align="right" colspan="6">
   		<input class="input_ok" type="submit" name="entry"  value="<bean:message  key='global.save'/>">
   		<input class="input_ok" type="button" value="<bean:message  key='global.cancel'/>" onclick="javascript:parent.window.close();">
   		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   	</td>
   </tr>
  </table>
</html:form>
 </div> 
<iframe name="hideFrame" src="" frameborder=0 width=0 height=0></iframe>
</body>
</html>
