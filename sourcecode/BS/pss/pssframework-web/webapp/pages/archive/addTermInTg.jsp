<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><bean:message bundle="archive" key="archive.new.term"/></title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/object/object_biguserinfomation.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/common-ajax.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/dwr/engine.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/dwr/util.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/dwr/interface/Terminal.js"></script>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";

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
  
function Check() {
    if(""==document.getElementsByName("assetNo")[0].value) {
        alert('<bean:message bundle="archive" key="archive.tg.assetnoisnonull"/>');
        return false;
    }
    if(""==document.getElementsByName("logicalAddr")[0].value) {
        alert('<bean:message bundle="archive" key="archive.tg.logicaladdrisnonull"/>');
        return false;
    }
//  if(""==document.getElementsByName("physicsAddr")[0].value) {
//      alert("物理地址不能为空");
//      return false;
//  }
    if(document.getElementById("checkbox1").checked){
       if(document.getElementsByName("jlfs")[0].checked){
          var zdgsStr = document.getElementsByName("zdgs")[0].value;
          if("" == zdgsStr)
          {
             alert('<bean:message bundle="archive" key="archive.tg.termcntisnonull"/>');
             return false;
          }
       }else if (document.getElementsByName("jlfs")[1].checked){
          var termIdCacs = document.getElementsByName("termIdCacs")[0].value;
          if("" == termIdCacs)
          {
             alert('<bean:message bundle="archive" key="archive.tg.shouldcascadeterm"/>');
             return false;
          }
       }
    }
    document.getElementsByName("entry")[0].disabled = true;
}
</script>
</head>
<body bgcolor="#f7f7ff" style="overflow-x:hidden;">
<ul class="e_title">
  <li class="e_titleon"><a href="#"><bean:message bundle="archive" key="archive.tg.termInfo"/></a></li>
</ul>
<html:form action="do/archive/addArchiveInTg" method="post" target="newTerminalPage" onsubmit="return Check();">
  <input type="hidden" name="action" value="addTerminalInTg"/>
  <input type="hidden" name="tgId" value="<bean:write name='tgId'/>">
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
      <td width="12%" align="right"><font color="red">*</font><bean:message bundle="archive" key="archive.termNo" />：</td>
      <td><input type="text" name="assetNo" class="user_add_input"></td>
      <td width="12%" align="right"><font color="red">*</font><bean:message bundle="archive"
        key="archive.terminal.LOGICAL_ADDR" />：</td>
      <td><input type="text" name="logicalAddr" class="user_add_input"></td>
      <td width="12%" align="right"><bean:message bundle="archive" key="archive.terminal.TERM_TYPE" />：</td>
      <td><peis:selectlist name="termType" sql="COMMON0011_TG" extendProperty="class='user_add_input'" /></td>
    </tr>
    <tr>
      <td width="12%" align="right"><bean:message bundle="archive" key="archive.terminal.WIRING_MODE" />：</td>
      <td><peis:selectlist extendProperty="class='user_add_input'" name="wiringMode" sql="COMMON0012" /></td>
      <td width="12%" align="right"><bean:message bundle="archive" key="archive.PROTOCOL_NO" />：</td>
      <td><peis:selectlist extendProperty="class='user_add_input'" name="protocolNo" sql="COMMON0013" /></td>
      <td width="12%" align="right"><bean:message bundle="archive" key="archive.COMM_MODE" />：</td>
      <td><peis:selectlist extendProperty="class='user_add_input'" name="commMode" sql="COMMON0014" /></td>
    </tr>
    <tr>
      <td width="12%" align="right"><bean:message bundle="archive" key="archive.terminal.MADE_FAC" />：</td>
      <td><peis:selectlist extendProperty="class='user_add_input'" name="madeFac" sql="COMMON0015" /></td>
      <td width="12%" align="right"><bean:message bundle="archive" key="archive.PR" />：</td>
      <td><peis:selectlist extendProperty="class='user_add_input'" name="pr" sql="COMMON0016" /></td>
      <td width="12%" align="right"><bean:message bundle="archive" key="archive.MODEL_CODE" />：</td>
      <td><peis:selectlist extendProperty="class='user_add_input'" name="modelCode" sql="COMMON0017" /></td>
    </tr>
    <tr>
      <td width="12%" align="right"><bean:message bundle="archive" key="archive.CUR_STATUS" />：</td>
      <td><peis:selectlist extendProperty="class='user_add_input'" name="curStatus" sql="COMMON0018" /></td>
      <td width="12%" align="right"><bean:message bundle="archive" key="archive.LEAVT_FAC_NO" />：</td>
      <td><input type="text" name="leavtFacNo" class="user_add_input"></td>
      <td width="12%" align="right"><bean:message bundle="archive" key="archive.INSTALL_DATE" />：</td>
      <td><input type="text" name="installDate" class="user_add_input"> <a
        onclick="event.cancelBubble=true;" href="javascript:showCalendar('termTg',false,'installDate',null)"> <img
        name="termTg" src="<peis:contextPath/>/images/common/calendar.gif" width="34" height="21" align="middle"
        border="0" alt=""> </a></td>
    </tr>
    <tr>
      <td width="12%" align="right"><bean:message bundle="archive" key="archive.physicsAddr" />：</td>
      <td><input type="text" name="physicsAddr" class="user_add_input"></td>
      <td width="12%" align="right"><bean:message bundle="archive" key="archive.isCacs" />：</td>
      <td><input type="checkbox" id="checkbox1" name="cascade" value="1" onclick="CheckCascade();"></td>
      <td width="12%" align="right"><bean:message bundle="archive" key="archive.isac" />：</td>
      <td><input type="checkbox" name="isac" value="1" onclick="checkIsac();"></td>
    </tr>
  </table>
  <br />
  <table id="table2" style="display: none" class="function_title">
    <tr>
      <td class="functionLabel" width="100"><bean:message bundle="archive" key="archive.term.case" /></td>
    </tr>
  </table>
  <table id="table3" style="display: none" width="100%" border="0" cellpadding="0" cellspacing="0" align="center" class="table1">
    <tr id="zdjlf1">
      <td width="25%">
        <input type="radio" name="jlfs" id="checkbox2" value="1" checked="checked" onclick="alertJlfs()"/><bean:message bundle="archive" key="archive.active.case" />
      </td>
      <td width="25%">
        <input type="radio" name="jlfs" id="checkbox3" value="2" onclick="alertJlfs()"/><bean:message bundle="archive" key="archive.passive.case" />
      </td>
      <td colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="4">&nbsp;</td>
    </tr>
    <tr id="zdjlfs" style="display: ">
      <td align="right"><bean:message bundle="archive" key="archive.case.port" />： <select name="port" class="user_add_select">
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
      </select></td>
      <td align="right"><bean:message bundle="archive" key="archive.sur.cycle" />： <select name="xczq" class="user_add_select">
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
      </select></td>
      <td align="right"><bean:message bundle="archive" key="archive.fail.resend" />： <select name="sbcfcs" class="user_add_select">
        <option value="0">0</option>
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
      </select></td>
      <td align="right"><bean:message bundle="archive" key="archive.term.num" />： <input type="text" name="zdgs" class="user_add_input"></td>
    </tr>
    <tr id="bdjlfs" style="display: none">
      <td align="right">
        <bean:message bundle="archive" key="archive.active.termforCase" />： <peis:selectlist name="termIdCacs" sql="TG0012" extendProperty="class='user_add_select'" />
      </td>
      <td colspan="3">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="4">&nbsp;</td>
    </tr>
  </table>
  <!-- 
  <table id="table2" style="display: none" class="function_title">
    <tr>
      <td class="functionLabel" width=100><bean:message bundle="archive" key="archive.term.case" /></td>
    </tr>
  </table>
  <table id="table3" style="display: none" width="100%" border=0 cellpadding=0 cellspacing=0 align="center" class="table1">
    <tr id="zdjlf1">
      <td colspan="4"><input type="radio" name="jlfs" id="checkbox2" value="1" checked="checked"><bean:message bundle="archive" key="archive.active.case" /></td>
    </tr>
    <tr id="zdjlf2">
      <td align="right"><bean:message bundle="archive" key="archive.case.port" />：</td>
      <td><select name="port" class="user_add_select">
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
      </select></td>
      <td align="right"><bean:message bundle="archive" key="archive.sur.cycle" />：</td>
      <td><select name="xczq" class="user_add_select">
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
      </select></td>
    </tr>
    <tr id="zdjlf3">
      <td align="right"><bean:message bundle="archive" key="archive.fail.resend" />：</td>
      <td><select name="sbcfcs" class="user_add_select">
        <option value="0">0</option>
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
      </select></td>
      <td align="right"><bean:message bundle="archive" key="archive.term.num" />：</td>
      <td><input type="text" name="zdgs" class="user_add_input"></td>
    </tr>
    <tr id="bdjlf1">
      <td colspan="4"><input type="radio" name="jlfs" id="checkbox3" value="2"><bean:message
        bundle="archive" key="archive.passive.case" /></td>
    </tr>
    <tr id="bdjlf2">
      <td align="right"><bean:message bundle="archive" key="archive.active.termforCase" />：</td>
      <td colspan="3"><peis:selectlist name="termIdCacs" sql="TG0012" extendProperty="class='user_add_select'" /></td>
    </tr>
  </table>
   -->
  <br />
  <table border="0" cellpadding="0" cellspacing="0" class="function_title">
    <tr>
      <td class="functionLabel" width="100"><bean:message bundle="archive" key="archive.sw.param" /></td>
    </tr>
  </table>
  <table class="tablestyle" width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
    <tr>
      <th><bean:message bundle="archive" key="archive.sn" /></th>
      <th><bean:message bundle="archive" key="archive.para.name" /></th>
      <th><bean:message bundle="archive" key="archive.para.value" /></th>
    </tr>
    <logic:present name="termPara">
      <logic:iterate id="para" name="termPara">
        <tr>
          <td>
            <bean:write name="para" property="rowNo" />
            <input type="hidden" name="dataItem" value="<bean:write name='para' property='col1'/>" />
            <input type="hidden" name="domainCode" value="<bean:write name='para' property='col3'/>" />
            <input type="hidden" name="itemCode" value="<bean:write name='para' property='col4'/>" />
            <input type="hidden" name="paraType" value="<bean:write name='para' property='col6'/>" />
            <input type="hidden" name="paraCate" value="<bean:write name='para' property='col5'/>" />
          </td>
          <td><bean:write name="para" property="col1" /></td>
          <td><input type="text" class="user_add_input" name="paraValue" value="<bean:write name='para' property='col2'/>"></td>
        </tr>
      </logic:iterate>
      <tr id="ctId" style="display: none">
        <td>3</td>
        <td><bean:message bundle="archive" key="archive.ac.ct" /></td>
        <td><peis:selectlist extendProperty="class='user_add_input'" name="ctRatio" sql="COMMON0022" /></td>
      </tr>
      <tr id="ptId" style="display: none">
        <td>4<br>
        <br>
        </td>
        <td><br>
        </td>
        <td>&quot;<peis:selectlist extendProperty="class='user_add_input'" name="ptRatio" sql="COMMON0023" /></td>
      </tr>
    </logic:present>
  </table>
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td class="functionLabe2" width="100%" align="right"><input type="submit" name="entry"
        value="<bean:message  key='global.save'/>" class="input_ok"> <input class="input_ok" type="button"
        value="<bean:message  key='global.cancel'/>" onclick="javascript:parent.window.close();"></td>
    </tr>
  </table>
</html:form>
<iframe name="newTerminalPage" frameborder="0" width="0" height="0"></iframe>
</body>
</html>