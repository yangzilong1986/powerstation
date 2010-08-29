<%@include file="../../commons/taglibs.jsp"%>



<%@page contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<peis:contextPath />/css/window.css" rel="stylesheet" type="text/css">

<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<html>
<head>
<title><bean:message bundle="archive" key="archive.new.term"/></title>
</head>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";
var isac = '<bean:write name="term" property="isac"/>';
function init(isac){
	//if(isac=="1"){
	//	document.getElementsByName("isac")[0].checked = "true";
	//}
	if(isac=="1"){
		document.getElementsByName("isac")[0].checked = "true";
	}
	checkIsac();
	
	var isCascade = '<bean:write name="isCascade"/>';
	if("1" == isCascade)
	{
	   document.getElementById("checkbox1").checked = true;
	}
	CheckCascade();
	
	var jlfs = '<bean:write name="jlfs"/>'; 
	if("1" == jlfs)
	{
	   document.getElementsByName("jlfs")[0].checked = true;
	}else if("2" == jlfs)
	{
	   document.getElementsByName("jlfs")[1].checked = true;
	}
	 alertJlfs();
	var port = '<bean:write name="port"/>';
	document.getElementsByName("port")[0].value = port;
	var xczq = '<bean:write name="xczq"/>';
	document.getElementsByName("xczq")[0].value = xczq;
	var sbcfcs = '<bean:write name="sbcfcs"/>';
	document.getElementsByName("sbcfcs")[0].value = sbcfcs;
}

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

function alertJlfs()
{
   var jlfsValue = 0;
   if(document.getElementsByName("jlfs")[0].checked){
      //jlfsValue = document.getElementsByName("jlfs")[0].value;
      document.getElementById("zdjlfs").style.display = "";
      document.getElementById("bdjlfs").style.display = "none";
   }else if (document.getElementsByName("jlfs")[1].checked){
      //jlfsValue = document.getElementsByName("jlfs")[1].value;
      document.getElementById("zdjlfs").style.display = "none";
      document.getElementById("bdjlfs").style.display = "";
   }
}

function CheckCascade(){
	if(document.getElementById("checkbox1").checked){
		
		document.getElementById("table2").style.display = "";
		document.getElementById("table3").style.display = "";
		
	}
	else
	{
		document.getElementById("table2").style.display = "none";
		document.getElementById("table3").style.display = "none";
	}
}

function checkIsac() {
	if(document.getElementsByName("isac")[0].checked) {
		document.getElementById("ctId").style.display = '';
		document.getElementById("ptId").style.display = '';
	}
	else{
		document.getElementById("ctId").style.display = 'none';
		document.getElementById("ptId").style.display = 'none';
	}
}

function editCheck() {
	var itemLen = document.getElementsByName("dataItem").length;
	var Item = "";
	var Value = "";
	var Code = "";
	for(var i=0;i<itemLen;i++) {
		Item += "," + document.getElementsByName("dataItem")[i].value;
		Value += "," + document.getElementsByName("paraValue")[i].value;
		Code += "," + document.getElementsByName("itemCode")[i].value;
	}	
	if(Item.indexOf(",") != -1) {
		Item = Item.substring(1);
		document.getElementsByName("Item")[0].value = Item;
	}
	if(Value.indexOf(",") != -1) {
		Value = Value.substring(1);
		document.getElementsByName("Value")[0].value = Value;
	}
	if(Code.indexOf(",") != -1) {
		Code = Code.substring(1);
		document.getElementsByName("Code")[0].value = Code;
	}
	
}
</script>
<body bgcolor="#f7f7ff" style="overflow-x:hidden;" onload="init('<bean:write name ="term" property="isac"/>');checkIsac();">
<ul class="e_title">
    <li class="e_titleon"><a href="#"><bean:message bundle="archive" key="archive.terminal.info"/></a>
</ul>
<html:form id="terminalForm" action="do/archive/editTermInTg" method="post" target="newTerminalPage" onsubmit="return editCheck();">
<peis:text type="hidden" name="termId"/>
<input type="hidden" name="action" value="updateTermInTg"/>
<input type="hidden" name="tgId" value="<bean:write name='tgId'/>">
<input type="hidden" name="logicaladdr_old" value="<bean:write name="term" property="logicalAddr"/>">
<table border="0" cellpadding="0" cellspacing="0"  class="function_title">
  <tr>
    <td class="functionLabel" width=100><bean:message bundle="archive" key="archive.terminal.info"/></td>
  </tr>
</table>
<table class="table1" width="100%" border=0 cellpadding=0 cellspacing=0 align="center">
  <tr>
    <td  class="functionLabe2" colspan="6">&nbsp;</td>
  </tr>
  <tr>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.terminal.assetNo"/>：</td>
    <td width="22%"><input type="hidden" name="assetNo" value="<bean:write name='term' property='assetNo'/>" readonly="readonly" class="user_add_input"><bean:write name="term" property="assetNo"/></td>
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.terminal.LOGICAL_ADDR"/>：</td>
    <td width="22%"><peis:text type="text" name="logicalAddr" styleClass="user_add_input"/></td>
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.terminal.TERM_TYPE"/>：</td>
    <td width="22%"><peis:selectlist name="termType" sql="COMMON0011_TG" extendProperty="class='user_add_input'"/></td>
  </tr>
  <tr>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.terminal.WIRING_MODE"/>：</td>
    <td width="22%"><peis:selectlist name="wiringMode" sql="COMMON0012" extendProperty="class='user_add_input'"/></td>
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.PROTOCOL_NO"/>：</td>
    <td width="22%"><peis:selectlist name="protocolNo" sql="COMMON0013" extendProperty="class='user_add_input'"/></td>
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.COMM_MODE"/>：</td>
    <td width="22%"><peis:selectlist name="commMode" sql="COMMON0014" extendProperty="class='user_add_input'"/></td>
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
    <td width="22%"><peis:selectlist name="curStatus" sql="COMMON0018" extendProperty="class='user_add_input'" /></td>
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.LEAVT_FAC_NO"/>：</td>
    <td width="22%"><peis:text type="text" name="leavtFacNo"  styleClass="user_add_input"/></td>
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.INSTALL_DATE"/>：</td>
    <td width="22%">
       <input type="text" name="installDate"  value="<bean:write name='term' format='yyyy-mm-dd' property='installDate'/>"  class="user_add_input">
	   <a onclick="event.cancelBubble=true;" href="javascript:showCalendar('addTerm',false,'installDate',null)">
         <img name="addTerm" src="<peis:contextPath/>/images/common/calendar.gif" width="34" height="21" align="middle" border="0" alt="">
       </a>
    </td>
  </tr>
  <tr>
  <!-- 
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.org"/>：</td>
    <td width="22%"><peis:selectlist name="orgNo" sql="LOG0002" extendProperty="class='user_add_input'"/></td>
     -->
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.physicsAddr"/>：</td>
    <td width="22%"><peis:text type="text" name="physicsAddr" styleClass="user_add_input"/></td>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.isCacs"/>：</td>
	<td><input type="checkbox" id="checkbox1" name="cascade" value="1" onclick="CheckCascade();"></td>
	<td width="12%" align="right"><bean:message bundle="archive" key="archive.isac"/>：</td>
	<td><input type="checkbox" name="isac" value="1" onclick="checkIsac();"></td>
  </tr>
  <!-- 
  <tr>
    <td width="12%"  align="right"><bean:message bundle="archive" key="archive.isac"/>：</td>
    <td width="22%"><input type="checkbox" name="isac" value="1" onclick="checkIsac();"></td>
    <td width="12%" align="right"><bean:message bundle="archive" key="archive.isTermSwitch"/>：</td>
    <td width="22%"><input type="checkbox" name="isSwitch" value="1" onclick="displaySwitch();"></td>
    <td width="12%"  align="right"></td>
    <td width="22%"></td>
  </tr>
   -->
</table>

<br/>
<table id="table2" style="display:none" class="function_title">
		  	<tr>
		  	 <td class="functionLabel" width=100>
		       <bean:message bundle="archive" key="archive.term.case"/>
		     </td>
		  	</tr>
		  </table>
<table id="table3" style="display:none" width="100%"  border=0 cellpadding=0 cellspacing=0 align="center" class="table1">
		    <tr id="zdjlf1">
		     <td width="25%">
		     <input type="radio" name="jlfs" id="checkbox2" value="1" checked="checked" onclick="alertJlfs()"><bean:message bundle="archive" key="archive.active.case"/>
		     </td>
		      <td width="25%">
		     <input type="radio" name="jlfs" id="checkbox3" value="2" onclick="alertJlfs()"><bean:message bundle="archive" key="archive.passive.case"/>
		     </td>
		     <td colspan="2">&nbsp;</td>
		    </tr>
		    <tr>
		      <td colspan="4">&nbsp;</td>
		    </tr>
		    <tr id="zdjlfs" style="display: ">
		       <td align="right"><bean:message bundle="archive" key="archive.case.port"/>：
		        <select name="port" class="user_add_select">
		          <option value="0">0</option>
		          <option value="1">1</option>
		          <option value="2">2</option>
		          <option value="3">3</option>
		          <option value="4">4</option>
		          <option value="5">5</option>
		          <option value="6">6</option>
		          <option value="7">7</option>
		          <option value="8">8</option>
		          <option value="9">9</option>
		          <option value="10">10</option>
		          <option value="11">11</option>
		          <option value="12">12</option>
		          <option value="13">13</option>
		          <option value="14">14</option>
		          <option value="15">15</option>
		          <option value="16">16</option>
		          <option value="17">17</option>
		          <option value="18">18</option>
		          <option value="19">19</option>
		          <option value="20">20</option>
		          <option value="21">21</option>
		          <option value="22">22</option>
		          <option value="23">23</option>
		          <option value="24">24</option>
		          <option value="25">25</option>
		          <option value="26">26</option>
		          <option value="27">27</option>
		          <option value="28">28</option>
		          <option value="29">29</option>
		          <option value="30">30</option>
		          <option value="31">31</option>
		        </select>
		       </td>
		       <td align="right"><bean:message bundle="archive" key="archive.sur.cycle"/>：
		       	<select name="xczq" class="user_add_select"> 
		       		<option value="1">1</option>
		       		<option value="2">2</option>
		       		<option value="3">3</option>
		       		<option value="4">4</option>
		       		<option value="5">5</option>
		       		<option value="6">6</option>
		       		<option value="7">7</option>
		       		<option value="8">8</option>
		       		<option value="9">9</option>
		       		<option value="10">10</option>
		       		<option value="11">11</option>
		       		<option value="12">12</option>
		       		<option value="13">13</option>
		       		<option value="14">14</option>
		       		<option value="15">15</option>
		       		<option value="16">16</option>
		       		<option value="17">17</option>
		       		<option value="18">18</option>
		       		<option value="19">19</option>
		       		<option value="20">20</option>
		       		<option value="21">21</option>
		       		<option value="22">22</option>
		       		<option value="23">23</option>
		       		<option value="24">24</option>
		       		<option value="25">25</option>
		       		<option value="26">26</option>
		       		<option value="27">27</option>
		       		<option value="28">28</option>
		       		<option value="29">29</option>
		       		<option value="30">30</option>
		       		<option value="31">31</option>
		       		<option value="32">32</option>
		       		<option value="33">33</option>
		       		<option value="34">34</option>
		       		<option value="35">35</option>
		       		<option value="36">36</option>
		       		<option value="37">37</option>
		       		<option value="38">38</option>
		       		<option value="39">39</option>
		       		<option value="40">40</option>
		       		<option value="41">41</option>
		       		<option value="42">42</option>
		       		<option value="43">43</option>
		       		<option value="44">44</option>
		       		<option value="45">45</option>
		       		<option value="46">46</option>
		       		<option value="47">47</option>
		       		<option value="48">48</option>
		       		<option value="49">49</option>
		       		<option value="50">50</option>
		       		<option value="51">51</option>
		       		<option value="52">52</option>
		       		<option value="53">53</option>
		       		<option value="54">54</option>
		       		<option value="55">55</option>
		       		<option value="56">56</option>
		       		<option value="57">57</option>
		       		<option value="58">58</option>
		       		<option value="59">59</option>
		       		<option value="60">60</option>
		       	</select>
		       </td>
		    
		       <td align="right"><bean:message bundle="archive" key="archive.fail.resend"/>：
		        <select name="sbcfcs" class="user_add_select"> 
		        	<option value="0">0</option>
		        	<option value="1">1</option>
		        	<option value="2">2</option>
		        	<option value="3">3</option>
		        </select>
		       </td>
		       <td align="right"><bean:message bundle="archive" key="archive.term.num"/>： <input type="text" name="zdgs"  class="user_add_input" value="<bean:write name="zdgs"/>"></td>
		    </tr>
		    <tr id="bdjlfs" style="display: none">
		        <td align="right"><bean:message bundle="archive" key="archive.active.termforCase"/>：
		        <peis:selectlist name="termIdCacs" sql="TG0012" extendProperty="class='user_add_select'"/></td>
		        <td colspan="3">&nbsp;</td>
		     </tr>
		    <tr><td colspan="4">&nbsp;</td></tr>
		    
</table>
<br/>
<table border="0" cellpadding="0" cellspacing="0"  class="function_title">
  <tr>
    <td class="functionLabel" width=100><bean:message bundle="archive" key="archive.sw.param"/></td>
  </tr>
</table> 
<table class="tablestyle" width="100%" border=0 cellpadding=0 cellspacing=0 align="center">
  <tr>
    <th><bean:message bundle="archive" key="archive.sn"/></th>
	<th><bean:message bundle="archive" key="archive.para.name"/></th>
	<th><bean:message bundle="archive" key="archive.para.value"/></th>
  </tr>
  <logic:present name="termPara">
     <logic:iterate id="para" name="termPara" >
		     	<tr>
		     	  <input type="hidden" name="dataItem" value="<bean:write name='para' property='col1'/>"/>
		     	  <input type="hidden" name="domainCode" value="<bean:write name='para' property='col3'/>"/>
		     	  <input type="hidden" name="itemCode" value="<bean:write name='para' property='col4'/>"/>
		     	  <input type="hidden" name="paraType" value="<bean:write name='para' property='col6'/>"/>
		     	  <input type="hidden" name="paraCate" value="<bean:write name='para' property='col5'/>"/>
		     	  <td><bean:write name="para" property="rowNo"/></td>
		     	  <td><bean:write name="para" property="col1"/></td>
		     	  <td><input type="text" class="user_add_input" name="paraValue" value="<bean:write name='para' property='col2'/>"></td>
		     	</tr>
     </logic:iterate>
   </logic:present>
		     	<tr id="ctId" style="display:none">
		     	  <td>3</td>
		     	  <td><bean:message bundle="archive" key="archive.ac.ct"/></td>
		     	  <td><peis:selectlist name="ctRatio" sql="COMMON0022" extendProperty="class='user_add_input'"/></td>
		     	</tr>
		     	<tr id="ptId" style="display:none">
		     	  <td>4</td>
		     	  <td><bean:message bundle="archive" key="archive.ac.pt"/></td>
		     	  <td><peis:selectlist name="ptRatio" sql="COMMON0023" extendProperty="class='user_add_input'"/></td>
		     	</tr>
		    </table>
		    <input type="hidden" name="Item">
		    <input type="hidden" name="Value">
		    <input type="hidden" name="Code">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td class="functionLabe2" width="100%" align="right">
       <input type="submit" name="button1" value="<bean:message  key='global.save'/>" " class="input_ok">
       <input type="button" name="button2" value="<bean:message  key='global.cancel'/>" onclick="javascript:parent.window.close();"  class="input_ok">
    </td>
  </tr>
</table>
</html:form>
<iframe name="newTerminalPage" frameborder=0 width=0 height=0></iframe>
</body>
</html>
