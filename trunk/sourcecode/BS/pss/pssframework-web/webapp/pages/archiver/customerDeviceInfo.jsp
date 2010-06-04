<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>

<html>
<head>
<title><bean:message bundle="archive" key="archive.device.info"/></title>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<peis:contextPath />/css/window.css" rel="stylesheet" type="text/css">
</head>

<body style="overflow-x: auto; overflow-y: auto;">
<table width=98% border=0 cellpadding=0 cellspacing=0 align=center class="e_detail_t">
  <tr>
    <td width=10><img src="<peis:contextPath />/img/e_detail_tleft.gif" width=10 height=28></td>
    <td width="100%" background="<peis:contextPath />/img/e_detail_tmain.gif" class="functionTitle"><bean:message bundle="archive" key="archive.device.info"/></td>  
    <td width=10><img src="<peis:contextPath />/img/e_detail_tright.gif" width=10 height=28></td>
  </tr>
  <tr>
    <td class="contentLeft">　</td>
    <td>
		
		  <!--终端区域-->
		  <table border="0" cellpadding="0" cellspacing="0" class="function_title">
		        <tr>
		        <td class="functionLabel" width=102 align="left"><bean:message bundle="archive" key="archive.term.list"/></td>
		        <td class="functionLabe2" width="690" height="24" align="right">
		        <bean:message bundle="archive" key="archive.new.device"/>		        
		        <select name="ArchiveId" id="selectId">
		        	 <option value="1"><bean:message bundle="archive" key="archive.terminal"/></option>
		         	 <option value="2"><bean:message bundle="archive" key="archive.gp"/></option> 
		        	 <option value="3"><bean:message bundle="archive" key="archive.meter"/></option>
		        	 <!-- <option value="4"><bean:message bundle="archive" key="archive.sw"/></option> -->
		        	 <option value="5"><bean:message bundle="archive" key="archive.ag"/></option>
		        </select>
		        	<input type="button" class="input_ok" name="add" value="<bean:message  key='global.submit'/>" onclick="addArchive('<bean:write name="custId"/>');">
		        </td>
		      </tr>
      </table>
		  <table width="100%" class="tablestyle" border=0 cellpadding=0 cellspacing=0 align="center">
		    <tr>
		      <!--标题头-->
		      <th><bean:message bundle="archive" key="archive.termNo"/></th>
		      <th><bean:message bundle="archive" key="archive.terminal.logicaladdr"/></th>
		      <th><bean:message bundle="archive" key="archive.terminal.TERM_TYPE"/></th>
		      <th><bean:message bundle="archive" key="archive.terminal.WIRING_MODE"/></th>
		      <th><bean:message bundle="archive" key="archive.terminal.MADE_FAC"/></th>
		      <th><bean:message bundle="archive" key="archive.terminal.RUN_STATUS"/></th>
		      <th><bean:message key="global.operation"/></th>
		    </tr>
		    <logic:present name="object_terminal">
		    <logic:iterate id="terminal" name="object_terminal">
		      <tr style="cursor:pointer;" id="termRow<bean:write name="terminal" property="col1"/>">
		        <td>
		          <bean:write name="terminal" property="col7"/>
		        </td>
		        <td>
		          <bean:write name="terminal" property="col2"/>
		        </td>
		        <td>
		          <bean:write name="terminal" property="col3"/>
		        </td>
		        <td>
		          <bean:write name="terminal" property="col4"/>
		        </td>
		        <td>
		          <bean:write name="terminal" property="col5"/>
		        </td>
		        <td>
		          <bean:write name="terminal" property="col6"/>
		        </td>
		        <td>
		          <a href="javascript:showTerminal('<bean:write name="terminal" property="col1"/>');"><bean:message  key="global.edit"/></a>|
		          <a href="javascript:delteTerminal('<bean:write name="terminal" property="col1"/>')"><bean:message  key="global.delete"/></a>
		        </td>
		        
		      </tr>
		    </logic:iterate>
		    </logic:present>
		  </table>
		  <!--采集点区域-->
		  <table border="0" cellpadding="0" cellspacing="0"  class="function_title">
		        <tr>
		        <td class="functionLabel" width=100><bean:message bundle="archive" key="archive.gp.list"/></td>
		      </tr>
      </table>
		  <table width="100%" class="tablestyle" border=0 cellpadding=0 cellspacing=0 align="center">
		    <tr>
		      <th><bean:message bundle="archive" key="archive.gp.gpSn"/></th>
		      <th><bean:message bundle="archive" key="archive.gp.logicaladdr"/></th>
		      <th><bean:message bundle="archive" key="archive.gp.GP_CHAR"/></th>
		      <th><bean:message bundle="archive" key="archive.gp.CT_TIMES"/></th>
		      <th><bean:message bundle="archive" key="archive.gp.PT_TIMES"/></th>
			  <th><bean:message bundle="archive" key="archive.terminal.RUN_STATUS"/></th>
			  <th><bean:message bundle="archive" key="archive.gp.ASSET_NO"/></th>
			  <th><bean:message  key="global.operation"/></th>
		    </tr>
		    <logic:present name="object_gp">
		    <logic:iterate id="gpself" name="object_gp">
		    <tr style="cursor:hand;" id="gpRow<bean:write name="gpself" property="col1"/>">
		      <td><bean:write name="gpself" property="col8"/></td>
		      <td><bean:write name="gpself" property="col2"/></td>
		      <td><bean:write name="gpself" property="col3"/></td>
		      <td><bean:write name="gpself" property="col4"/></td>
		      <td><bean:write name="gpself" property="col5"/></td>
		      <td><bean:write name="gpself" property="col6"/></td>
		      <td><bean:write name="gpself" property="col7"/></td>
		      <td><a href="javascript:showGP('<bean:write name="gpself" property="col1"/>','<bean:write name="gpself" property="col9"/>','<bean:write name="custId"/>');"><bean:message key="global.edit"/></a>|
		      	  <a href="javascript:deleteGp('<bean:write name="gpself" property="col1"/>')"><bean:message  key="global.delete"/></a>
		      </td>
		     </tr>
		    </logic:iterate>
		    </logic:present>
		  </table>
		    <!--电表区域-->
		    <table border="0" cellpadding="0" cellspacing="0"  class="function_title">
		        <tr>
		        <td class="functionLabel" width=100><bean:message bundle="archive" key="archive.meter.list"/> </td>
		      </tr>
		    </table>
		    
		    <table width="100%" class="tablestyle" border=0 cellpadding=0 cellspacing=0 align="center">
		      <tr>
		        <!--标题头-->
		        <th ><bean:message bundle="archive" key="archive.meter.MP_NAME"/></th>
		        <th><bean:message bundle="archive" key="archive.gp.ASSET_NO"/></th>
		        <th><bean:message bundle="archive" key="archive.gp.GP_ADDR"/></th>
		        <th><bean:message bundle="archive" key="archive.gp.logicaladdr"/></th>
		        <th><bean:message bundle="archive" key="archive.meter.type"/></th>
		        <th><bean:message bundle="archive" key="archive.terminal.MADE_FAC"/></th>
		        <th><bean:message bundle="archive" key="archive.terminal.RUN_STATUS"/></th>
				<th><bean:message key="global.operation"/></th>
		      </tr>
		      <logic:present name="object_meter">
		      <logic:iterate id="meter" name="object_meter">
		        <tr style="cursor:hand;" id="meterRow<bean:write name="meter" property="col1"/>">
		          <td>
		            <bean:write name="meter" property="col2"/>
		          </td>
		          <td>
		            <bean:write name="meter" property="col3"/>
		          </td>
		          <td>
		            <bean:write name="meter" property="col4"/>
		          </td>
		          <td>
		            <bean:write name="meter" property="col5"/>
		          </td>
		          <td>
		            <bean:write name="meter" property="col6"/>
		          </td>
		          <td>
		            <bean:write name="meter" property="col7"/>
		          </td>
		          <td>
		            <bean:write name="meter" property="col8"/>
		          </td>
		          <td>
		          	<a href="javascript:showMeter('<bean:write name="meter" property="col1"/>','<bean:write name="meter" property="col9"/>','<bean:write name="custId"/>');"><bean:message key="global.edit"/></a>|
		          	<a href="javascript:deleteMeter('<bean:write name="meter" property="col1"/>')"><bean:message key="global.delete"/></a>
		          </td>
		        </tr>
		      </logic:iterate>
		      </logic:present>
		    </table>
		    
		    <!--开关区域-->
		    <table border="0" cellpadding="0" cellspacing="0"  class="function_title">
		        <tr>
		        <td class="functionLabel" width=100><bean:message bundle="archive" key="archive.sw.list"/></td>
		      </tr>
		    </table>
		    <table width="100%" class="tablestyle" border=0 cellpadding=0 cellspacing=0 align="center">
		      <tr class="trheadstyle">
		        <!--标题头-->
		        <th ><bean:message bundle="archive" key="archive.sw.no"/></th>
		        <th><bean:message bundle="archive" key="archive.sw.name"/></th>
		        <th><bean:message bundle="archive" key="archive.belongTerm"/></th>
		        <th><bean:message bundle="archive" key="archive.sw.turn"/></th>
		        <th><bean:message bundle="archive" key="archive.sw.capa"/></th>
		        <th><bean:message bundle="archive" key="archive.sw.attr"/></th>
		        <th><bean:message bundle="archive" key="archive.sw.status"/></th>
		        <th><bean:message key="global.operation"/></th>
		      </tr>
		      <logic:present name="object_tSw">
		      <logic:iterate id="tsw" name="object_tSw">
		        <tr style="cursor:hand;" id="termRow<bean:write name="tsw" property="col1"/>">
		          <td>
		            <bean:write name="tsw" property="col2"/>
		          </td>
		          <td>
		            <bean:write name="tsw" property="col3"/>
		          </td>
		          <td>
		            <bean:write name="tsw" property="col4"/>
		          </td>
		          <td>
		            <bean:write name="tsw" property="col5"/>
		          </td>
		          <td>
		            <bean:write name="tsw" property="col6"/>
		          </td>
		          <td>
		            <bean:write name="tsw" property="col7"/>
		          </td>
		          <td>
		            <bean:write name="tsw" property="col8"/>
		          </td>
		          <td>
		          	<a href="javascript:showTSw('<bean:write name="tsw" property="col1"/>');"><bean:message key="global.edit"/></a>|
		          	<a href="javascript:deleteSw('<bean:write name="tsw" property="col1"/>');"><bean:message key="global.delete"/></a>
		          </td>
		        </tr>
		      </logic:iterate>
		      </logic:present>
		    </table>	
		    <!--总加组区域-->
		    <table border="0" cellpadding="0" cellspacing="0"  class="function_title">
		        <tr>
		        <td class="functionLabel" width=100>
		            <bean:message bundle="archive" key="archive.ag.info"/>
		        </td>
		        
		      </tr>
		    </table>
		    
		    <table width="100%" class="tablestyle" border=0 cellpadding=0 cellspacing=0 align="center">
		      <tr>
		        <!--标题头-->
		        <th ><bean:message bundle="archive" key="archive.termNo"/></th>
		        <th><bean:message bundle="archive" key="archive.terminal.logicaladdr"/></th>
		        <th><bean:message bundle="archive" key="archive.ag.sn"/></th>
		        <th><bean:message key="global.operation"/></th>
		      </tr>
		       <logic:present name="object_rela">
		      <logic:iterate id="rela" name="object_rela">
		        <tr style="cursor:hand;" id="termRow<bean:write name="rela" property="col3"/>">
		          <td>
		            <bean:write name="rela" property="col1"/>
		          </td>
		          <td>
		            <bean:write name="rela" property="col2"/>
		          </td>
		          <td>
		            <bean:write name="rela" property="col3"/>
		          </td>
		          <td>
		          	<a href="javascript:showRela('<bean:write name="rela" property="col3"/>','<bean:write name="rela" property="col4"/>');"><bean:message key="global.edit"/></a>|
		          	<a href="javascript:deleteRela('<bean:write name="rela" property="col3"/>','<bean:write name="rela" property="col4"/>','<bean:write name="rela" property="col5"/>');"><bean:message key="global.delete"/></a>
		          </td>
		        </tr>
		      </logic:iterate>
		      </logic:present>
		    </table>
        </td>
    <td class="contentRight">　</td>
  </tr>
   <tr align="center" bgcolor="#FFFFFF">
    <td ><img src="<peis:contextPath />/img/e_detail_bleft.gif" width=10 height=27></td>
    <td background="<peis:contextPath />/img/e_detail_bmain.gif">&nbsp;</td>
    <td ><img src="<peis:contextPath />/img/e_detail_bright.gif" width=10 height=27></td>
  </tr>
</table>

<iframe name="hideFrame" src="" frameborder=0 width=0 height=0></iframe>
</body>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/object/object_biguserinfomation.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/ajax.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";

//编辑终端信息
function showTerminal(terminalId) {
	var str_url = contextPath + "/archive/terminalAction.do";
	str_url += "?action=detail&terminalId=" + terminalId;
	str_url += "&rand=" + Math.random();
	windowPopup1(str_url, 850, 600);
}

//编辑采集点信息
function showGP(gpId, mpId, custId) {
	var str_url = contextPath + "/archive/gpAction.do";
	str_url += "?action=detail&gpId=" + gpId + "&mpId=" + mpId + "&custId=" + custId;
	str_url += "&rand=" + Math.random();
	windowPopup1(str_url, 500, 350);
}

//编辑电表信息
function showMeter(mpId, meterType, custId) {
	var str_url = contextPath + "/archive/meterMpAction.do";
	str_url += "?action=detail&mpId=" + mpId + "&meterType=" + meterType + "&custId=" + custId;
	str_url += "&rand=" + Math.random();
	windowPopup1(str_url, 500, 450);
}

//编辑终端开关
function showTSw(tSwId) {
    var custId = '<bean:write name="custId"/>';
    var str_url = contextPath + "/archive/termSwitchAction.do";
    str_url += "?action=detail&tSwId=" + tSwId +" &custId="+  custId;
    str_url += "&rand=" + Math.random();
    windowPopup1(str_url, 500, 250);
}

//编辑总加组信息
function showRela(relaId,termId) {
	var custId = '<bean:write name="custId"/>';
	var str_url = contextPath + "/archive/aAgRelaAction.do";
	str_url += "?action=detail&relaId=" + relaId + "&termId=" + termId + "&custId=" + custId;
    str_url += "&rand=" + Math.random();
	windowPopup1(str_url, 750, 450);
}

function addTerm(custId) {
    var str_url = contextPath + "/do/archive/gotoAddArchive.do";
    str_url += "?action=gotoAddTerminal&custId=" + custId;
    str_url += "&rand=" + Math.random();
    windowPopup1(str_url, 850, 600);
}

function addArchive(custId) {
	var dx = document.getElementsByName("ArchiveId")[0].value;
	if(dx == "1") {        //添加终端
		var str_url = contextPath + "/archive/terminalAction.do";
		str_url += "?action=beforeAdd&custId=" + custId + "&random=" + Math.random();
		windowPopup1(str_url, 850, 600);
	}
	else if(dx == "2") {   //添加测量点
		var str_url = contextPath + "/archive/gpAction.do";
		str_url += "?action=beforAddGp&custId=" + custId + "&random=" + Math.random();
		windowPopup1(str_url, 500, 350);
	}
	else if(dx == "3") {   //添加电表
		var str_url = contextPath + "/archive/meterMpAction.do";
		str_url += "?action=beforAddMeterMp&custId=" + custId + "&random=" + Math.random();
		windowPopup1(str_url, 500, 450);
	}
	else if(dx == "4"){    //添加开关
		var str_url = contextPath + "/archive/termSwitchAction.do";
		str_url += "?action=beforAddMeterMp&custId=" + custId + "&random=" + Math.random();
		windowPopup1(str_url, 500, 250);
	}
	else if(dx == "5") {   //添加总加组
		var str_url = contextPath + "/archive/aAgRelaAction.do";
		str_url += "?action=beforAddAAGRela&custId=" + custId + "&random=" + Math.random();
		windowPopup1(str_url, 750, 450);
	}
}

function delteTerminal(termId){//删除终端
	if(confirm("<bean:message bundle='archive' key='archive.del.term'/>")){
		IdArray=new Array();
		IdArray[0]=termId;
		var url = contextPath + "/archive/terminalAction.do?action=delete&termId="+termId;
		ajax_send(url);
	}
}

function deleteGp(gpId) {//删除测量点
    if(confirm("<bean:message bundle='archive' key='archive.del.gp'/>")) {
        IdArray = new Array();
        IdArray[0] = gpId;
        //alert(IdArray[0]);
        var url = contextPath + "/archive/gpAction.do?action=delete&gpId=" + gpId;
        ajax_send1(url);
    }
}

function deleteMeter(mpId) {//删除电表
	if(confirm("<bean:message bundle='archive' key='archive.del.meter'/>")){
		IdArray=new Array();
		IdArray[0]=mpId;
		var url = contextPath + "/archive/meterMpAction.do?action=delete&mpId="+mpId;
		ajax_send2(url);
	}
}

function deleteSw(tSwId){//删除终端开关
	if(confirm("<bean:message bundle='archive' key='archive.del.sw'/>")) {
		IdArray=new Array();
		IdArray[0]=tSwId;
		var url = contextPath + "/archive/termSwitchAction.do?action=delete&tSwId=" + tSwId;
		ajax_send(url);
	}
}

function deleteRela(aGId,termId,gpId) {//删除总加组关系
	if(confirm("<bean:message bundle='archive' key='archive.del.ag'/>")){
		IdArray=new Array();
		IdArray[0]=aGId;
		//alert(aGId)
		var url = contextPath + "/archive/aAgRelaAction.do?action=delete&aGId="+aGId+"&termId="+termId+"&gpId="+gpId;
		ajax_send(url);
	}
}

  
</script>
</html>