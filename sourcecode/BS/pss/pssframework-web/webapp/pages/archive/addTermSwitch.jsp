

<%@include file="../../commons/taglibs.jsp"%>



<%@page contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<peis:contextPath />/css/window.css" rel="stylesheet" type="text/css">

<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common-ajax.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/dwr/engine.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/dwr/util.js"></script>
<html>
<head>
<title><bean:message bundle="archive" key="archive.new.sw"/></title>
</head>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";


function startOP() {
	if(""==document.getElementsByName("termId")[0].value) {
		alert("所属终端不能为空");
		return false;
	}
	if(""==document.getElementsByName("swName")[0].value){
		alert("开关名称不能为空");
		return false;
	}
	if(""==document.getElementsByName("swSn")[0].value) {
		alert("开关序号不能为空");
		return false;
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
<body bgcolor="#f7f7ff"  style="overflow-x:hidden;overflow-y:hidden;" onload="initButton();">
<ul class="e_title">
    <li class="e_titleon"><a href="#"><bean:message bundle="archive" key="archive.new.sw"/></a>
  </ul>
  <div class="user_add_table">
  <html:form action="/archive/termSwitchAction" method="post" target="newSwPage" onsubmit="return startOP();">
  <input type="hidden" name="action" value="saveOrUpdate">
  <table width="96%" border="0" align="center"  cellpadding="0" cellspacing="0" >
    <tr>
      <td id="msg" colspan="6"></td>
    </tr>
    <tr>
    <td align="right" width="15%"><font color="red">*</font><bean:message bundle="archive" key="archive.sw.name"/>:</td>
    <td width="35%"><peis:text type="text" name="swName" extendProperty="class='user_add_input'"/></td>
	<td align="right" width="15%"><font color="red">*</font><bean:message bundle="archive" key="archive.SW_SN"/>:</td>
	<td width="35%"><peis:text type="text" name="swSn" extendProperty="class='user_add_input'"/></td>
    </tr>
    <tr>
    <td align="right"><bean:message bundle="archive" key="archive.sw.turn"/>:</td>
	<td><peis:text type="text" name="swTurn" extendProperty="class='user_add_input'"/></td>
	<td align="right"><bean:message bundle="archive" key="archive.sw.capa"/>:</td>
	<td><peis:text type="text" name="capacity" extendProperty="class='user_add_input'"/></td>
    </tr>
    <tr>
	<td align="right"><bean:message bundle="archive" key="archive.sw.attr"/>:</td>
	<td><peis:selectlist name="swAttr" sql="COMMON0019" extendProperty="class='user_add_input'"/></td>
	<td align="right"><bean:message bundle="archive" key="archive.belongTerm"/></td>
    <td>
	   <logic:present name="custId">
		 <peis:selectlist name="termId" sql="TERMINGP0001" extendProperty="class='user_add_input'"/>
	   </logic:present>
	</td>
	</tr>
	<tr>
	<td align="right"><bean:message bundle="archive" key="archive.sw.status"/></td>
	<td><peis:selectlist name="swStatus" sql="TERMSW0001" extendProperty="class='user_add_input'"/></td>
	<td align="right"></td>
	<td></td>
	</tr>
	<tr>
	<td align="right" colspan="6">
   		<input class="input_ok" type="submit" name="entry" value="<bean:message  key='global.save'/>" id="bt1">
   		<input class="input_ok" type="button" value="<bean:message  key='global.cancel'/>" onclick="javascript:parent.window.close();" id="bt2">
   		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   	</td>
   	</tr>
  </table>
  </html:form>
   </div>
<iframe name="newSwPage" frameborder=0 width=0 height=0></iframe>
</body>
</html>
