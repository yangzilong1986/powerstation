
<%@include file="../../commons/taglibs.jsp"%>



<%@page contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<peis:contextPath />/css/window.css" rel="stylesheet" type="text/css">
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/object/object_biguserinfomation.js">
</script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/ajax.js"></script>
<html>
<head>
<title><bean:message bundle="archive" key="archive.deviceInTg"/></title>
</head>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";
function showTerminal(terminalId,tgId){
	//alert("1");
	var str_url = contextPath + "/showTerminalAction.do";
	str_url += "?action=TerminalInTg&terminalId="+terminalId;
	str_url += "&tg_id="+tgId;
	str_url += "&rand="+Math.random();
	//alert(str_url);
	windowPopup1(str_url,850, 450);
	//window.open(str_url,'',height=450,width=600);
	//parent.winopen(str_url,400,500);
}

function showGP(gpId,mpId,tgId){
	
	var str_url = contextPath + "/showGpAction.do";
	str_url += "?action=showGp&gpId="+gpId+"&mpId="+mpId+"&tgId="+tgId;
	str_url += "&rand="+Math.random();
	windowPopup1(str_url,500, 350);
	
}

function showMeter(mpId,maeterType,tgId){
	var str_url = contextPath + "/showMaeter.do";
	str_url += "?action=showMeter&mpId="+mpId+"&maeterType="+maeterType+"&tgId="+tgId;
	str_url += "&rand="+Math.random();
	windowPopup1(str_url,500, 450);
}

//编辑查看采集器
function showGm(gmId,tgId) {
	var str_url = contextPath + "/showArchive.do";
	str_url += "?action=showGm&gmId="+gmId+"&tgId="+tgId;
	str_url += "&rand="+Math.random();
	//alert(str_url);
	windowPopup1(str_url,500, 350);
}


function deleteTerminal(termId) {
	if(confirm("<bean:message bundle="archive" key="archive.del.term"/>")){
		IdArray=new Array();
		IdArray[0]=termId;
		var url = contextPath + "/do/archive/deleteArchive.do?action=deleteTerminal&termId="+termId;
		ajax_send(url);
	}
}

function deleteGp(gpId) {
	if(confirm("<bean:message bundle="archive" key="archive.del.gp"/>")){
		IdArray=new Array();
		IdArray[0]=gpId;
		var url = contextPath + "/do/archive/deleteArchive.do?action=deleteGp&gpId="+gpId;
		ajax_send1(url);
		}
}

function deleteMeter(mpId) {
	if(confirm("<bean:message bundle="archive" key="archive.del.meter"/>")){
		IdArray=new Array();
		IdArray[0]=mpId;
		var url = contextPath + "/do/archive/deleteArchive.do?action=deleteMeter&mpId="+mpId;
		ajax_send2(url);
	}
}

function delGm(gmId) {
	if(confirm("<bean:message bundle="archive" key="archive.del.gp"/>")){
		IdArray=new Array();
		IdArray[0]=gmId;
		var url = contextPath + "/do/archive/deleteArchive.do?action=deleteGm&gmId="+gmId;
		ajax_send(url);
	}
}

function addArchiveInTg(tgId) {
	var dx = document.getElementsByName("ArchiveId")[0].value;
	if(dx=="1"){
		var str_url = contextPath + "/do/archive/gotoAddArchive.do";
		str_url += "?action=gotoAddTerminalInTg&tgId="+tgId;
		windowPopup1(str_url,850,450);
	}
	else if(dx=="2"){
		var str_url = contextPath + "/do/archive/gotoAddArchive.do";
		str_url += "?action=gotoAddGpInTg&tgId="+tgId;
		windowPopup1(str_url,500,350);
	}
	else if(dx=="3"){
		var str_url = contextPath + "/do/archive/gotoAddArchive.do";
		str_url += "?action=gotoAddMeterInTg&tgId="+tgId;
		windowPopup1(str_url,500,450);
	}
	else{
		var str_url = contextPath + "/do/archive/gotoAddArchive.do";
		str_url += "?action=gotoAddGmInTg&tgId="+tgId;
		windowPopup1(str_url,500,350);
	}
}
  




</script>
<body bgcolor="#f7f7ff">
<table width=96% border=0 cellpadding=0 cellspacing=0 align=center>
    <tr>
      <td width="100%">
      <!--终端区域-->
      <table border="0" cellpadding="0" cellspacing="0"  class="function_title">
		 <tr>
		    <td class="functionLabel" width=100><bean:message bundle="archive" key="archive.term.list"/></td>
		    <td class="functionLabe2" width="690" height="24" align="right">
		        <bean:message bundle="archive" key="archive.new.object"/>
		        	<select name="ArchiveId">
		        	 <option value="1"><bean:message bundle="archive" key="archive.tg.term"/></option>
		        	 <option value="2"><bean:message bundle="archive" key="archive.gp"/></option>
		        	 <option value="3"><bean:message bundle="archive" key="archive.meter"/></option>
		        	 <option value="4"><bean:message bundle="archive" key="archive.tg.gm"/></option>
		        	</select>
		       <input class="input_ok" type="button" name="add" value="<bean:message  key='global.submit'/>" onclick="addArchiveInTg('<bean:write name="tgId"/>');">
		   </td>
		 </tr>
      </table>
      <table width="100%" class="tablestyle" border=0 cellpadding=0 cellspacing=0 align="center">
		 <tr>
		      <th><bean:message bundle="archive" key="archive.terminal.TERM_ID"/></th>
		      <th><bean:message bundle="archive" key="archive.terminal.logicaladdr"/></th>
		      <th><bean:message bundle="archive" key="archive.terminal.TERM_TYPE"/></th>
		      <th><bean:message bundle="archive" key="archive.terminal.WIRING_MODE"/></th>
		      <th><bean:message bundle="archive" key="archive.terminal.MADE_FAC"/></th>
		      <th><bean:message bundle="archive" key="archive.terminal.RUN_STATUS"/></th>
		      <th><bean:message  key="global.operation"/></th>
		 </tr>
		 <logic:present name="object_terminal">
		    <logic:iterate id="terminal" name="object_terminal">
		      <tr  style="cursor:hand;" id="termRow<bean:write name="terminal" property="col1"/>">
		
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
		          <bean:write name="terminal" property="col7"/>
		        </td>
		        <td>
		          <a href="javascript:showTerminal('<bean:write name="terminal" property="col1"/>','<bean:write name="tgId"/>');"><bean:message key="global.edit"/></a>|
		          <a href="javascript:deleteTerminal('<bean:write name="terminal" property="col1"/>')"><bean:message key="global.delete"/></a>
		        </td>
		        
		      </tr>
		    </logic:iterate>
		    </logic:present>
	   </table>
	   <br/>
	   <!--电表区域-->
      <table border="0" cellpadding="0" cellspacing="0"  class="function_title">
		 <tr>
		    <td class="functionLabel" width=100><bean:message bundle="archive" key="archive.meter.list"/></td>
		    <td class="functionLabe2" width="690" height="24" align="right"><td>
		 </tr>
      </table>
      <table width="100%" class="tablestyle" border=0 cellpadding=0 cellspacing=0 align="center">
		 <tr>
		        <th><bean:message bundle="archive" key="archive.meter.MP_NAME"/></th>
		        <th><bean:message bundle="archive" key="archive.meter.assetno"/></th>
		        <th><bean:message bundle="archive" key="archive.gp.GP_ADDR"/></th>
		        <th><bean:message bundle="archive" key="archive.terminal.logicaladdr"/></th>
		 
		        <th><bean:message bundle="archive" key="archive.meter.type"/></th>
		        <th><bean:message bundle="archive" key="archive.meter.factory"/></th>
		        <th><bean:message bundle="archive" key="archive.meter.status"/></th>
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
		          	<a href="javascript:showMeter('<bean:write name="meter" property="col1"/>','<bean:write name="meter" property="col10"/>','<bean:write name="tgId"/>');"><bean:message  key="global.edit"/></a>|
		          	<a href="javascript:deleteMeter('<bean:write name="meter" property="col1"/>')"><bean:message  key="global.delete"/></a>
		          </td>
		        </tr>
		      </logic:iterate>
		      </logic:present>
	   </table>
	   <br/>
	   <!--采集点区域-->
      <table border="0" cellpadding="0" cellspacing="0"  class="function_title">
		 <tr>
		    <td class="functionLabel" width=100><bean:message bundle="archive" key="archive.gp.list"/></td>
		    <td class="functionLabe2" width="690" height="24" align="right"><td>
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
			  <th><bean:message key="global.operation"/></th>
		 </tr>
		 <logic:present name="object_gp">
		    <logic:iterate id="gp" name="object_gp">
		    <tr style="cursor:hand;" id="gpRow<bean:write name="gp" property="col1"/>">
		      <td><bean:write name="gp" property="col2"/></td>
		      <td><bean:write name="gp" property="col3"/></td>
		      <td><bean:write name="gp" property="col4"/></td>
		      <td><bean:write name="gp" property="col5"/></td>
		      <td><bean:write name="gp" property="col6"/></td>
		      <td><bean:write name="gp" property="col7"/></td>
		      <td><bean:write name="gp" property="col8"/></td>
		      <td>
		      	<a href="javascript:showGP('<bean:write name="gp" property="col1"/>','<bean:write name="gp" property="col9"/>','<bean:write name="tgId"/>');"><bean:message  key="global.edit"/></a>|
		      	<a href="javascript:deleteGp('<bean:write name="gp" property="col1"/>')"><bean:message  key="global.delete"/></a>
		      </td>
		     </tr>
		    </logic:iterate>
		    </logic:present>
	   </table>
	   <br/>
	   <!-- 采集器区域 -->
      <table border="0" cellpadding="0" cellspacing="0"  class="function_title">
		 <tr>
		    <td class="functionLabel" width=100><bean:message bundle="archive" key="archive.gm.list"/></td>
		    <td class="functionLabe2" width="690" height="24" align="right"><td>
		 </tr>
      </table>
      <table width="100%" class="tablestyle" border=0 cellpadding=0 cellspacing=0 align="center">
		 <tr>
		      <th><bean:message bundle="archive" key="archive.gp.LOGICAL_ADDR"/></th>
		      <th><bean:message bundle="archive" key="archive.gm.assetNo"/></th>
		      <th><bean:message bundle="archive" key="archive.gm.addr"/></th>
		      <th><bean:message bundle="archive" key="archive.gm.type"/></th>
		      <th><bean:message bundle="archive" key="archive.gm.commmode"/></th>
		      <th><bean:message bundle="archive" key="archive.gm.factory"/></th>
			  <th><bean:message bundle="archive" key="archive.gm.status"/></th>
			  <th><bean:message key="global.operation"/></th>
		 </tr>
		 <logic:present name="object_gm">
		    <logic:iterate id="gm" name="object_gm">
		    <tr style="cursor:hand;" id="termRow<bean:write name="gm" property="col1"/>">
		      <td><bean:write name="gm" property="col2"/></td>
		      <td><bean:write name="gm" property="col3"/></td>
		      <td><bean:write name="gm" property="col4"/></td>
		      <td><bean:write name="gm" property="col5"/></td>
		      <td><bean:write name="gm" property="col7"/></td>
		      <td><bean:write name="gm" property="col8"/></td>
		      <td><bean:write name="gm" property="col9"/></td>
		      <td>
		      	<a href="javascript:showGm('<bean:write name="gm" property="col1"/>','<bean:write name="tgId"/>');"><bean:message  key="global.edit"/></a>|
		      	<a href="javascript:delGm('<bean:write name="gm" property="col1"/>')"><bean:message  key="global.delete"/></a>
		      </td>
		     </tr>
		    </logic:iterate>
		    </logic:present>
	   </table>
      </td>
    </tr>
</table>
<iframe name="hideFrame" src="" frameborder=0 width=0 height=0></iframe>
</body>
</html>
