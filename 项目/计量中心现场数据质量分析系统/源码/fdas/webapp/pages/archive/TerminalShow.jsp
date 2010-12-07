<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>




<html>
<head>
<title><bean:message bundle="archive" key="archive.terminal.info"/></title>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<peis:contextPath />/css/window.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/switchOp.js"></script>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
var isac = '<bean:write name="term" property="isac"/>';
function init(isac) {
	if(isac == "1") {
		document.getElementsByName("isac")[0].checked = "true";
	}
	checkIsac();
	var isSw = '<bean:write name ="isSwitch"/>';
	if("1" == isSw) {
	    document.getElementsByName("isSwitch")[0].checked = "true";
	    displaySwitch();
	}
}

function displaySwitch() {
	if(document.getElementsByName("isSwitch")[0].checked) {
		document.getElementById("switchTab").style.display = '';
		document.getElementById("switchHead").style.display = '';
	}
	else{
	    if(window.confirm("此操作提交后将会删除该终端下所有的开关信息，是否继续？")){
	       document.getElementById("switchTab").style.display = 'none';
		   document.getElementById("switchHead").style.display = 'none';
	    }else
	    {
	      document.getElementsByName("isSwitch")[0].checked = true;
	    }
	}
}

function checkIsac() {
    if(document.getElementsByName("isac")[0].checked) {
        document.getElementById("isacId").style.display = '';
    }
    else{
        document.getElementById("isacId").style.display = 'none';
    }
}

function editCheck() {
//	var itemLen = document.getElementsByName("dataItem").length;
//	var Item = "";
//	var Value = "";
//	var Code = "";
//	for(var i=0;i<itemLen;i++) {
//		Item += "," + document.getElementsByName("dataItem")[i].value;
//		Value += "," + document.getElementsByName("paraValue")[i].value;
//		Code += "," + document.getElementsByName("itemCode")[i].value;
//	}	
//	if(Item.indexOf(",") != -1) {
//		Item = Item.substring(1);
//		document.getElementsByName("Item")[0].value = Item;
//	}
//	if(Value.indexOf(",") != -1) {
//		Value = Value.substring(1);
//		document.getElementsByName("Value")[0].value = Value;
//	}
//	if(Code.indexOf(",") != -1) {
//		Code = Code.substring(1);
//		document.getElementsByName("Code")[0].value = Code;
//	}
  var logicalAddr = document.getElementsByName("logicalAddr")[0].value;
   
	if(""==document.getElementsByName("assetNo")[0].value){
		alert("<bean:message bundle="archive" key="archive.tg.assetnoisnonull"/>");
		document.getElementsByName("entry")[0].disabled = false;
		return false;
	}
	if(""==logicalAddr){
		alert("<bean:message bundle="archive" key="archive.tg.logicaladdrisnonull"/>");
	    document.getElementsByName("entry")[0].disabled = false;
		return false;
	}
    if(logicalAddr.length<8){
        alert("<bean:message bundle="archive" key="archive.term.logicalLength"/>");
        return false;
    }
	if(document.getElementsByName("isSwitch")[0].checked)
	{
	   if(! swValidator())
	   {
	      return false;
	   }
	}
	document.getElementsByName("editBt")[0].disabled = true;
	
}
</script>
</head>
<body bgcolor="#f7f7ff" style="overflow-x:hidden;" onload="init('<bean:write name ="term" property="isac"/>');checkIsac();">
<ul class="e_title">
  <li class="e_titleon"><a href="#"><bean:message bundle="archive" key="archive.terminal.info"/></a>
</ul>
<html:form styleId="terminalForm" action="/archive/terminalAction" method="POST" target="editTerminalPage" onsubmit="return editCheck()">
<input type="hidden" name="action" value="update"/>
<peis:text type="hidden" name="termId"/>
<peis:text type="hidden" name="objectId"/>
<peis:text type="hidden" name="objectType"/>
<peis:text type="hidden" name="orgNo"/>
<peis:text type="hidden" name="runStatus"/>
<peis:text type="hidden" name="simNo"/>
<peis:text type="hidden" name="batchId"/>
<input type="hidden" name="leaveFacDate" value="<bean:write name='term' format='yyyy-MM-dd' property='installDate'/>"/>
<peis:text type="hidden" name="channelType"/>
<input type="hidden" name="className" value="ATerminal">
<input type="hidden" name="logicalAddr1" id="logicalAddr1" value="<bean:write name='logicalAddr'/>"/>
<table border="0" cellpadding="0" cellspacing="0"  class="function_title">
  <tr>
    <td class="functionLabel" width="100"><bean:message bundle="archive" key="archive.terminal.info"/></td>
  </tr>
</table>
<table class="table1" width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
  <tr>
    <td class="functionLabe2" colspan="6">&nbsp;</td>
  </tr>
  <tr>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.terminal.assetNo"/>：</td>
    <td width="22%"><input name="assetNo" value="<bean:write name='term' property='assetNo'/>" readonly="readonly" class="user_add_input"></td>
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.terminal.logicaladdr"/>：</td>
    <td width="22%"><peis:text name="logicalAddr" styleClass="user_add_input" /></td>
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.terminal.TERM_TYPE"/>：</td>
    <td width="22%"><peis:selectlist name="termType" sql="COMMON0011" onChange="loadParamTblTbody()" extendProperty="class='user_add_input'"/></td>
  </tr>
  <tr>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.terminal.WIRING_MODE"/>：</td>
    <td width="22%"><peis:selectlist name="wiringMode" sql="COMMON0012" extendProperty="class='user_add_input'"/></td>
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.PROTOCOL_NO"/>：</td>
    <td width="22%"><peis:selectlist name="protocolNo" sql="COMMON0013" onChange="loadParamTblTbody()" extendProperty="class='user_add_input'"/></td>
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.COMM_MODE"/>：</td>
    <td width="22%"><peis:selectlist name="commMode" sql="COMMON0014" onChange="loadParamTblTbody()" extendProperty="class='user_add_input'"/></td>
  </tr>
  <tr>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.terminal.MADE_FAC"/>：</td>
    <td width="22%"><peis:selectlist name="madeFac" sql="COMMON0015" extendProperty="class='user_add_input'"/></td>
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.PR"/>：</td>
    <td width="22%"><peis:selectlist name="pr" sql="COMMON0016" extendProperty="class='user_add_input'"/></td>
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.MODEL_CODE"/>：</td>
    <td width="22%"><peis:selectlist name="modelCode" sql="COMMON0017" extendProperty="class='user_add_input'"/></td>
  </tr>
  <tr>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.CUR_STATUS"/>：</td>
    <td width="22%"><peis:selectlist name="curStatus" sql="COMMON0018" extendProperty="class='user_add_input'"/></td>
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.LEAVT_FAC_NO"/>：</td>
    <td width="22%"><peis:text type="text" name="leaveFacNo"  styleClass="user_add_input"/></td>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.INSTALL_DATE"/>：</td>
    <td width="22%">
       <input type="text" name="installDate" value="<bean:write name='term' format='yyyy-MM-dd' property='installDate'/>"  class="user_add_input"/>
	   <a onclick="event.cancelBubble=true;" href="javascript:showCalendar('addTerm',false,'installDate',null)">
         <img name="addTerm" src="<peis:contextPath/>/images/common/calendar.gif" width="34" height="21" align="middle" border="0" alt="">
       </a>
    </td>
  </tr>
  <tr>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.physicsAddr"/>：</td>
    <td width="22%"><peis:text name="physicsAddr" styleClass="user_add_input" /></td>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.machNo"/>：</td>
    <td width="22%"><peis:selectlist name="machNo" sql="SL_ARCHIVE_0001" extendProperty="class='user_add_input'"/></td>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.fepCnl"/>：</td>
    <td width="22%"><peis:text name="fepCnl" styleClass="user_add_input" /></td>
  </tr>
  <tr>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.isac"/>：</td>
    <td width="22%"><input type="checkbox" name="isac" value="1" onclick="checkIsac();"></td>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.isTermSwitch"/>：</td>
    <td width="22%"><input type="checkbox" name="isSwitch" value="1" onclick="displaySwitch();"></td>
    <td width="12%" align="right"></td>
    <td width="22%"></td>
  </tr>
  <tr id="isacId" style="display:none">
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.ac.ct"/>：</td>
    <td width="22%"><peis:selectlist name="ctRatio" sql="COMMON0022" extendProperty="class='user_add_input'"/></td>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.ac.pt"/>：</td>
    <td width="22%"><peis:selectlist name="ptRatio" sql="COMMON0023" extendProperty="class='user_add_input'"/></td>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.ac.port"/>：</td>
    <td width="22%"><peis:text type="text" styleId="port" name="port" styleClass="user_add_input" extendProperty="class='user_add_input'"/></td>
  </tr>
</table>

<br/>
<table style="display:none" id="switchHead" border="0" cellpadding="0" cellspacing="0"  class="function_title">
  <tr>
    <td class="functionLabel" width="100"><bean:message bundle="archive" key="archive.isTermSw.info"/></td>
    <td class="functionLabe2" width="690" height="24" align="right">
      <input type="button" name="btNew" value="<bean:message key="global.add"/>" class="input" onclick="addSW();">
    </td>
  </tr>
</table>
<table style="display:none" id="switchTab" class="tablestyle" width="100%" border=0 cellpadding=0 cellspacing=0 align="center">
  <tr>
    <th width="27%"><bean:message bundle="archive" key="archive.sw.name"/></th>
    <th width="10%"><bean:message bundle="archive" key="archive.sw.no"/></th>
	<th width="10%"><bean:message bundle="archive" key="archive.sw.turn"/></th>
	<th width="27%"><bean:message bundle="archive" key="archive.sw.capa"/></th>
	<th width="10%"><bean:message bundle="archive" key="archive.sw.attr"/></th>
	<th width="10%"><bean:message bundle="archive" key="archive.sw.status"/></th>
	<th width="6%"><bean:message key="global.operation"/></th>
  </tr>
  <logic:present name="switch">
	<logic:iterate id="swInfo" name="switch" indexId="i">
	   <tr>
	      <td><input type="text" name="swName" value="<bean:write name='swInfo' property='col2'/>"></td>
	      <td>
	         <select name="swSn">
	            <option value="1">1</option>
	            <option value="2">2</option>
	            <option value="3">3</option>
	            <option value="4">4</option>
	            <option value="5">5</option>
	            <option value="6">6</option>
	            <option value="7">7</option>
	            <option value="8">8</option>
	         </select>
	         <script type="text/javascript">
	             var index = parseInt("<bean:write name='swInfo' property='rowNo'/>");
	             document.getElementsByName("swSn")[index-1].value = "<bean:write name='swInfo' property='col1'/>";
	         </script>
	      </td>
	      <td>
	         <select name="swTurn">
	            <option value="1">1</option>
	            <option value="2">2</option>
	            <option value="3">3</option>
	            <option value="4">4</option>
	            <option value="5">5</option>
	            <option value="6">6</option>
	            <option value="7">7</option>
	            <option value="8">8</option>
	         </select>
	         <script type="text/javascript">
	             var index = parseInt("<bean:write name='swInfo' property='rowNo'/>");
	             document.getElementsByName("swTurn")[index-1].value = "<bean:write name='swInfo' property='col3'/>";
	         </script>
	      </td>
	      <td><input type="text" name="capacity" value="<bean:write name='swInfo' property='col6'/>"></td>
	      <td>
	         <select name="swAttr">
	            <option value="1"><bean:message bundle="archive" key="archive.swattr.open"/></option>
	            <option value="2"><bean:message bundle="archive" key="archive.swattr.close"/></option>
	         </select>
	         <script type="text/javascript">
	             var index = parseInt("<bean:write name='swInfo' property='rowNo'/>");
	             document.getElementsByName("swAttr")[index-1].value = "<bean:write name='swInfo' property='col4'/>";
	         </script>
	      </td>
	      <td>
	         <select name="swStatus">
	            <option value="1"><bean:message bundle="archive" key="archive.swstatus.open"/></option>
	            <option value="0"><bean:message bundle="archive" key="archive.swstatus.close"/></option>
	         </select>
	         <script type="text/javascript">
	             var index = parseInt("<bean:write name='swInfo' property='rowNo'/>");
	             document.getElementsByName("swStatus")[index-1].value = "<bean:write name='swInfo' property='col5'/>";
	         </script>
	      </td>
        <td><a href="javascript:void(0)" onclick="del()"><bean:message key="global.delete"/></a></td>
	   </tr>
	</logic:iterate>
  </logic:present>
</table>
<br/>
<table id="paramHead" border="0" cellpadding="0" cellspacing="0" class="function_title">
  <tr>
    <td class="functionLabel" width="100"><bean:message bundle="archive" key="archive.sw.param"/></td>
  </tr>
</table> 
<table id="paramTbl" class="tablestyle" width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
  <thead>
    <tr>
      <th><bean:message bundle="archive" key="archive.sn"/></th>
  	  <th><bean:message bundle="archive" key="archive.para.name"/></th>
  	  <th><bean:message bundle="archive" key="archive.para.value"/></th>
    </tr>
  </thead>
  <tbody id="paramTblTbody">
    <logic:present name="termParas">
    <logic:iterate id="para" name="termParas" >
    <tr>
      <td><bean:write name="para" property="rowNo"/></td>
      <td><bean:write name="para" property="col1"/></td>
      <td>
        <input type="text" id="paraValue" name="paraValue" value="<bean:write name='para' property='col3'/>"/>
        <input type="hidden" id="paraCode" name="paraCode" value="<bean:write name='para' property='col2'/>"/>
        <input type="hidden" id="commanditemCode" name="commanditemCode" value="<bean:write name='para' property='col4'/>"/>
        <input type="hidden" id="dataitemCode" name="dataitemCode" value="<bean:write name='para' property='col5'/>"/>
      </td>
    </tr>
    </logic:iterate>
    </logic:present>
  </tbody>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td class="functionLabe2" width="100%" align="right">
       <input type="submit" name="editBt" value="<bean:message key='global.save'/>" class="input_ok">
       <input type="button" name="button2" value="<bean:message key='global.cancel'/>" onclick="javascript:parent.window.close();"  class="input_ok">
    </td>
  </tr>
</table>
</html:form>
<iframe name="editTerminalPage" frameborder="0" width="0" height="0"></iframe>
<script type="text/javascript">
function loadParamTblTbody() {
    var protocolNo = $("#protocolNo").val();
    var commMode = $("#commMode").val();
    var termType = $("#termType").val();
    var params = {"action": "loadParamTblTbodyByAjax",
                  "protocolNo": protocolNo,
                  "commMode": commMode,
                  "termType": termType};
    var url = contextPath + "/archive/terminalAction.do";
    $.ajax({
        type: "POST",
        url: url,
        cache: false,
        data: params,
        success: function(data) {
            $("#paramTblTbody").html(data);
        }
    });
}
</script>
</body>
</html>
