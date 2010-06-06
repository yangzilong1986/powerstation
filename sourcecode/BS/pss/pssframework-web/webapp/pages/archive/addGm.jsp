

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
<title><bean:message bundle="archive" key="archive.add.gm"/></title>
</head>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";

function Check() {
	if(""==document.getElementsByName("assetNo")[0].value){
		alert("<bean:message bundle='archive' key='archive.gm.assetNoIsNull'/>");
		return false;
	}
	document.getElementsByName("Update")[0].disabled = true;
}

function initButton(){
	 var termObj = document.all.termId;
	   if(termObj.options.length == 0 || termObj.value == '0')
       {
          document.getElementById("Update").disabled = true;
       }
}





</script>
<body bgcolor="#f7f7ff" style="overflow-x:hidden;overflow-y:hidden;" onload="initButton();">
<ul class="e_title">
    <li class="e_titleon"><a href="#"><bean:message bundle="archive" key="archive.gm.info"/></a>
  </ul>
  <div class="user_add_table">
<html:form  action="/do/archive/gmOption" method="post" target="resultPage" onsubmit="return Check();">
	<input type="hidden" name="action" value="addGm">
	<table width="96%"  border=0 cellpadding=0 cellspacing=1 align="center">
		
		<tr>
		  <td align="right" width="20%"><bean:message bundle="archive" key="archive.gp.LOGICAL_ADDR"/></td>
		  <td width="30%"><peis:selectlist name="termId" sql="TERMINTG0001" extendProperty="class='user_add_input'"/></td>
		  <td align="right" width="20%"><bean:message bundle="archive" key="archive.gm.assetNo"/></td>
		  <td width="30%"><peis:text type="text" name="assetNo" extendProperty="class='user_add_input'"/></td>
		</tr>
		<tr>
		  <td align="right" width="20%"><bean:message bundle="archive" key="archive.gm.addr"/></td>
		  <td width="30%"><peis:text type="text" name="gmAddr" extendProperty="class='user_add_input'"/></td>
		  <td align="right" width="20%"><bean:message bundle="archive" key="archive.MODEL_CODE"/></td>
		  <td width="30%"><peis:selectlist name="gmModel" sql="gmMode" extendProperty="class='user_add_input'"/></td>
		</tr>
		<tr>
		  <td align="right" width="20%"><bean:message bundle="archive" key="archive.COMM_MODE"/></td>
		  <td width="30%"><peis:selectlist name="commMode" sql="COMMON0014" extendProperty="class='user_add_input'"/></td>
		  <td align="right" width="20%"><bean:message bundle="archive" key="archive.terminal.MADE_FAC"/></td>
		  <td width="30%"><peis:selectlist name="madeFac" sql="COMMON0015" extendProperty="class='user_add_input'"/></td>
		</tr>
		<tr>
		  <td align="right" width="20%"><bean:message bundle="archive" key="archive.reCycle"/></td>
		  <td width="30%">
		  <input type="text" name="rmCycle" class='user_add_input' onkeyup="this.value=this.value.replace(/\D/gi,'')"/>
		  <!-- 
		  <peis:text type="text" name="rmCycle" extendProperty="class='user_add_input' onkeyup='this.value=this.value.replace(/\D/gi,\'\')'"/>
		  -->
		  </td>
		  <td align="right" width="20%"><bean:message bundle="archive" key="archive.PR"/></td>
		  <td width="30%"><peis:selectlist name="pr" sql="COMMON0016" extendProperty="class='user_add_input'"/></td>
		</tr>
		<tr>
		  <td align="right" width="20%"><bean:message bundle="archive" key="archive.org"/></td>
		  <td width="30%"><peis:selectlist name="orgNo" sql="ORG0002" extendProperty="class='user_add_input'"/></td>
		  <td align="right" width="20%"><bean:message bundle="archive" key="archive.status"/></td>
		  <td width="30%"><peis:selectlist name="gmStatus" sql="COMMON0018" extendProperty="class='user_add_input'"/></td>
		</tr>
		<tr>
		  <td align="right" width="20%"><bean:message bundle="archive" key="archive.remark"/></td>
		  <td width="30%"><peis:text type="textarea" name="remark" extendProperty="class='user_add_input'"/></td>
		</tr>
		<tr>
   		  <td align="right" colspan="6">
   			<input type="submit" name="Update" value="<bean:message  key='global.save'/>" class="input_ok">
   				&nbsp;&nbsp;
   			<input type="button" class="input_ok" value="<bean:message key='global.cancel'/>" onclick="javascript:parent.window.close();">
   		   		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   		  </td>
   		</tr>
	</table>
		  
	</html:form>
	<iframe name="resultPage" frameborder=0 width=0 height=0></iframe>
 </div>    
</body>
</html>
