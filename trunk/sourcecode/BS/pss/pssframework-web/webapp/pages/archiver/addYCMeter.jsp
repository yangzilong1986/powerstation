

<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
<%@page contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<peis:contextPath />/css/window.css" rel="stylesheet" type="text/css">

<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<html>
<head>
<title><bean:message bundle="archive" key="archive.new.meter"/></title>
</head>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";


function typeChange() {
    var object_id = "<bean:write name='object_id'/>";
    var object_type = "<bean:write name='object_type'/>";
	var typeValue = document.getElementsByName("meterType")[0].value;
	var str_url = contextPath + "/do/archive/gotoAddArchive.do";
	if(object_type=='2'){
		str_url += "?action=gotoAddMeterInTg&tgId="+object_id;
	}
	else if(object_type=='1'){
		str_url += "?action=gotoAddMeter&custId="+object_id;
	}
	    document.body.innerHTML='正在切换页面……';
	    window.location.href = str_url;
		window.resizeTo(500,500);
		window.moveTo((screen.availWidth-500)/2,(screen.availHeight-450)/2)
//	window.close();
}

function Check(){
	if(""==document.getElementsByName("mpName")[0].value) {
		alert("计量点名称不能为空");
		return false;
	}  
	if(""==document.getElementsByName("assetNo")[0].value) {
		alert("资产编号不能为空");
		return false;
	}
	if(""==document.getElementsByName("logicalAddr")[0].value) {
		alert("终端逻辑地址不能为空");
		return false;
	}
	document.getElementsByName("entry")[0].disabled = true;
}

function initButton(){
	 //var termObj = document.all.termId;
	 //  if(termObj.options.length == 0 || termObj.value == '0')
     //  {
     //     document.getElementById("entry").disabled = true;
     //  }
}




</script>
<body bgcolor="#f7f7ff"  style="overflow-x:hidden;overflow-y:hidden;" onload="initButton();">

  <div class="user_add_table">
  <html:form  action="/archive/meterMpAction" method="post" target="hideFrame" onsubmit="return Check();">
  <input type="hidden" name="object_id" value="<bean:write name='object_id'/>">
  <input type="hidden" name="object_type" value="<bean:write name='object_type'/>">
  <input type="hidden" name="termType" value="7"/>
  <input type="hidden" name="action" value="addYCMeterMp"/>
  <table border="0" cellpadding="0" cellspacing="0"  class="function_title">
		 <tr>
		    <td class="functionLabel" width=100><bean:message bundle="archive" key="archive.meter.info"/></td>
		 </tr>
  </table>
  <table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0" class="table1">
    <tr>
      <td id="msg" colspan="6"></td>
    </tr>
    <tr>
     <td width="12%" align="right"><font color="red">* </font><bean:message bundle="archive" key="archive.terminal.assetNo"/>：</td>
    <td width="20%"><input type="text" name="assetNo" class="user_add_input"></td>
      <td align="right" width="12%"><bean:message bundle="archive" key="archive.meter.type"/>：</td>
      <td width="20%"><select name="meterType"  class="user_add_input"/>
      <option value="1" ><bean:message bundle="archive" key="archive.meter.mode" />
      </option>
      </select></td>
    <!--   <peis:selectlist  name="meterType" sql="COMMON0021" onChange="typeChange();" extendProperty="class='user_add_input'"/></td>--> 
      <td align="right" width="12%"><font color="red">* </font><bean:message bundle="archive" key="archive.meter.MP_NAME"/>：</td>
      <td width="20%"><input type="text" name="mpName" class="user_add_input"/></td>
    </tr>
    <tr>
      <td align="right" width="12%"><bean:message bundle="archive" key="archive.meter.ct"/>：</td>
      <td width="20%"><peis:selectlist name="ctRatio" sql="COMMON0022" extendProperty="class='user_add_input'"/> </td>
      <td align="right" width="12%"><bean:message bundle="archive" key="archive.meter.pt"/>：</td>
      <td width="20%"><peis:selectlist name="ptRatio" sql="COMMON0023" extendProperty="class='user_add_input'"/></td>
      <td align="right" width="12%"><bean:message bundle="archive" key="archive.meterProNo"/>：</td>
      <td width="20%"><peis:selectlist name="proNo" sql="METERPN0001" extendProperty="class='user_add_input'"/> </td>
    </tr>
    <tr>
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.PR"/>：</td>
    <td width="20%"><peis:selectlist name="pr" sql="COMMON0016" extendProperty="class='user_add_input'"/></td>
      <td align="right" width="12%"><bean:message bundle="archive" key="archive.terminal.MADE_FAC"/>：</td>
      <td width="20%"><peis:selectlist name="madeFactory" sql="COMMON0015" extendProperty="class='user_add_input'"/></td>
       <td width="12%"  align="right"><bean:message bundle="archive" key="archive.INSTALL_DATE"/>：</td>
    <td width="20%">
       <input type="text" name="installDate"  class="user_add_input">
	   <a onclick="event.cancelBubble=true;" href="javascript:showCalendar('addTerm',false,'installDate',null)">
         <img name="addTerm" src="<peis:contextPath/>/images/common/calendar.gif" width="34" height="21" align="middle" border="0" alt="">
       </a>
    </td>
    </tr>
    <tr>
      <td align="right" width="12%"><bean:message bundle="archive" key="archive.btl"/>：</td>
      <td width="20%"><peis:selectlist name="btl" sql="COMMON0024" extendProperty="class='user_add_input'"/></td>
      <td align="right" width="12%"><bean:message bundle="archive" key="archive.meter.meterDigits"/>：</td>
      <td width="20%">
         <select name="meterDigits" class="user_add_input">
		     	<option value="4">4</option>
		     	<option value="5">5</option>
		     	<option value="6">6</option>
		     	<option value="7">7</option>
		     </select>
	  </td>
	  <td align="right" width="12%"><bean:message bundle="archive" key="archive.terminal.WIRING_MODE"/>：</td>
      <td width="20%"><peis:selectlist name="wiringMode" sql="COMMON0012" extendProperty="class='user_add_input'"/></td>
    </tr>
    <tr>
      <td align="right" width="12%"><bean:message bundle="archive" key="archive.meter.ratedVolt"/>：</td>
      <td width="20%"><peis:selectlist name="ratedVolt" sql="COMMON0025" extendProperty="class='user_add_input'"/></td>
      <td align="right" width="12%"><bean:message bundle="archive" key="archive.meter.ratedEc"/>：</td>
      <td width="20%"><peis:selectlist name="ratedEc" sql="COMMON0026" extendProperty="class='user_add_input'"/></td>
      <td align="right" width="12%"><bean:message bundle="archive" key="archive.LEAVT_FAC_NO"/>：</td>
      <td width="20%"><input type="text" name="leaveFacNo" class="user_add_input"/></td>
    </tr>
    <tr>
      <td align="right" width="12%"><bean:message bundle="archive" key="archive.meter.line"/>：</td>
      <td width="20%"><peis:selectlist name="lineId" sql="COMMON0214" extendProperty="class='user_add_input'"/>
      <!-- <input type="text" name="lineId" class="user_add_input"/>-->
      </td>
      <td align="right" width="12%"><bean:message bundle="archive" key="archive.gp.status"/>：</td>
      <td width="20%"><peis:selectlist name="status" sql="COMMON0018" extendProperty="class='user_add_input'"/></td>
      <td align="right" width="12%">电量计算标识：</td>
      <td width="20%"><input type="checkbox" name="computeFlag" value="1" checked>功率计算标识：<input type="checkbox" name="sucratcptId" value="1" checked></td>
    </tr>
  </table>
  <br/>
  <table border="0" cellpadding="0" cellspacing="0"  class="function_title">
		 <tr>
		    <td class="functionLabel" width=100>通讯信息</td>
		 </tr>
  </table>
  <table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0" class="table1">
  <tr>
    <td  class="functionLabe2" colspan="6">&nbsp;</td>
  </tr>
  <tr>
    <td width="12%" align="right"><font color="red">* </font><bean:message bundle="archive" key="archive.terminal.logicaladdr"/>：</td>
    <td width="20%"><input type="text" name="logicalAddr"  class="user_add_input"></td> 
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.physicsAddr"/>：</td>
    <td width="20%"><input type="text" name="physicsAddr"  class="user_add_input"></td>
  </tr>
  <tr>
  
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.PROTOCOL_NO"/>：</td>
    <td width="20%"><peis:selectlist name="protocolNo" sql="COMMON0013" extendProperty="class='user_add_input'"/></td>
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.COMM_MODE"/>：</td>
    <td width="20%"><peis:selectlist name="commMode" sql="COMMON0014" extendProperty="class='user_add_input'"/></td>
  </tr>
  
  <tr>
  
   
   
  </tr>
  </table>
  <br/>
  <table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0">
    <tr>
   	<td align="right" colspan="6">
   		<input class="input_ok" type="submit" name="entry"  value="<bean:message  key='global.save'/>">
   		<input class="input_ok" type="button" value="<bean:message key='global.cancel'/>" onclick="javascript:parent.window.close();">
   		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   	</td>
   </tr>
  </table>
</html:form>
</div>
<iframe name="hideFrame" src="" frameborder=0 width=0 height=0></iframe>
</body>
</html>
