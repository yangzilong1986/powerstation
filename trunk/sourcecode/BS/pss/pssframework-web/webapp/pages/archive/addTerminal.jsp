<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>终端</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
function displaySwitch() {
	if(document.getElementsByName("isSwitch")[0].checked) {
		document.getElementById("switchTab").style.display = '';
		document.getElementById("switchHead").style.display = '';
	}
	else{
		document.getElementById("switchTab").style.display = 'none';
		document.getElementById("switchHead").style.display = 'none';
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

function check() {
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
	document.getElementsByName("entry")[0].disabled = true;
}
</script>
</head>
<body bgcolor="#f7f7ff" style="overflow-x:hidden;">
<ul class="e_title">
  <li class="e_titleon"><a href="#"><bean:message bundle="archive" key="archive.terminal.info"/></a>
</ul>
<html:form styleId="terminalForm" action="/archive/terminalAction" method="post" target="newTerminalPage" onsubmit="return check();">
<input type="hidden" name="action" value="saveOrUpdate"/>
<input type="hidden" name="termId">
<input type="hidden" name="objectId" value="<bean:write name='custId'/>">
<input type="hidden" name="objectType" value="1">  <!-- 对象类型为专变用户（1） -->
<input type="hidden" name="orgNo">
<input type="hidden" name="runStatus" value="2">   <!-- 新增终端时默认运行状态为不在线（2） -->
<input type="hidden" name="simNo">
<input type="hidden" name="batchId">
<input type="hidden" name="leaveFacDate">
<input type="hidden" name="channelType">
<input type="hidden" name="className" value="ATerminal">
<table border="0" cellpadding="0" cellspacing="0" class="function_title">
  <tr>
    <td class="functionLabel" width=100><bean:message bundle="archive" key="archive.terminal.info"/></td>
  </tr>
</table>
<table class="table1" width="100%" border=0 cellpadding=0 cellspacing=0 align="center">
  <tr>
    <td class="functionLabe2" colspan="6">&nbsp;</td>
  </tr>
   <tr> 
	  <td colspan="6">
	    <font color="red"><html:errors bundle="archive" property="errs"/></font>
	  </td>  
  </tr>
  <tr>
    <td width="12%" align="right"><font color="red">* </font><bean:message bundle="archive" key="archive.terminal.assetNo"/>：</td>
    <td width="22%"><input type="text" name="assetNo" class="user_add_input"></td>
    <td width="12%" align="right"><font color="red">* </font><bean:message bundle="archive" key="archive.terminal.LOGICAL_ADDR"/>：</td>
    <td width="22%"><input type="text" name="logicalAddr"  class="user_add_input"></td>
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
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.LEAVT_FAC_NO"/>：</td>
    <td width="22%"><input type="text" name="leaveFacNo"  class="user_add_input"></td>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.INSTALL_DATE"/>：</td>
    <td width="22%">
       <input type="text" name="installDate" class="user_add_input">
	   <a onclick="event.cancelBubble=true;" href="javascript:showCalendar('addTerm',false,'installDate',null)">
         <img name="addTerm" src="<peis:contextPath/>/images/common/calendar.gif" width="34" height="21" align="middle" border="0" alt="">
       </a>
    </td>
  </tr>
  <tr>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.physicsAddr"/>：</td>
    <td width="22%"><input type="text" name="physicsAddr"  class="user_add_input"></td>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.machNo"/>：</td>
    <td width="22%"><peis:selectlist name="machNo" sql="SL_ARCHIVE_0001" extendProperty="class='user_add_input'"/></td>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.fepCnl"/>：</td>
    <td width="22%"><input type="text" name="fepCnl"  class="user_add_input"></td>
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
    <td width="22%"><input type="text" name="port" class="user_add_input"/></td>
  </tr>
</table>
<br/>
<table style="display:none" id="switchHead" border="0" cellpadding="0" cellspacing="0" class="function_title">
  <tr>
    <td class="functionLabel" width=100><bean:message bundle="archive" key="archive.isTermSw.info"/></td>
    <td class="functionLabe2" width="690" height="24" align="right">
      <input type="button" name="btNew" value="<bean:message bundle="archive" key="archive.new.sw"/>" class="input" onclick="addSW();">
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
    <th width="6%"><bean:message key="global.edit"/></th>
  </tr>
</table>
<br/> 
<table id="paramHead" border="0" cellpadding="0" cellspacing="0" class="function_title">
  <tr>
    <td class="functionLabel" width="100"><bean:message bundle="archive" key="archive.sw.param"/></td>
  </tr>
</table> 
<table id="paramTbl" class="tablestyle" width="100%" border=0 cellpadding=0 cellspacing=0 align="center">
  <thead>
    <tr>
      <th><bean:message bundle="archive" key="archive.sn"/></th>
  	  <th><bean:message bundle="archive" key="archive.para.name"/></th>
  	  <th><bean:message bundle="archive" key="archive.para.value"/></th>
    </tr>
  </thead>
  <tbody id="paramTblTbody">
    
  </tbody>
</table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td class="functionLabe2" width="100%" align="right">
       <input class="input_ok" type="submit" name="entry" value="<bean:message key='global.save'/>" >
       <input class="input_ok" type="button" name="button2" value="<bean:message key='global.cancel'/>" onclick="javascript:parent.window.close();">
    </td>
  </tr>
</table>
</html:form>
<iframe name="newTerminalPage" frameborder="0" width="0" height="0"></iframe>
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

$(document).ready( function() {
    loadParamTblTbody();
});
</script>
</body>
</html>
