
<%@include file="../../commons/taglibs.jsp"%>



<%@page contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<peis:contextPath/>/css/window.css" rel="stylesheet" type="text/css">

<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<html>
<head>
<title><bean:message bundle="archive" key="archive.new.tginfo"/></title>
</head>
<script type="text/javascript" language="javascript">

function save() {
	if(""==document.getElementsByName("tgNo")[0].value) {
		alert("<bean:message bundle="archive" key="archive.tg.tgnoisnonull"/>");
		return false;
	}
	if(""==document.getElementsByName("tgName")[0].value) {
		alert("<bean:message bundle="archive" key="archive.tg.tgnameisnonull"/>");
		return false;
	}
	if(""==document.getElementsByName("voltGrade")[0].value) {
		alert("<bean:message bundle="archive" key="archive.tg.voltisnonull"/>");
		return false;
	}
	document.getElementsByName("entry")[0].disabled = true;
	document.forms[0].submit();
}




</script>
<body >
<ul class="e_title">
    <li class="e_titleon"><a href="#"><bean:message bundle="archive" key="archive.tg.info"/></a>
  </ul>
  <div class="user_add_table">
  <html:form action="do/archive/editTgInfo" onsubmit="return validateTgInfoForm(this)">
  <table width="96%" border="0" align="center"  cellpadding="0" cellspacing="0" >
    <tr>
     <font color="red"> <html:errors bundle="archive" property="errormessage"/></font>
    </tr>
    <tr>
      <input type="hidden" name="action" value="addTgInfo"/>
      <td align="right" width="15%"><font color="red">*</font><bean:message bundle="archive" key="archive.tg.no"/>：</td>
      <td width="25%"><peis:text type="text" name="tgNo" extendProperty="class='user_add_input'"/></td>
      <td align="right" width="15%"><font color="red">*</font><bean:message bundle="archive" key="archive.tg.name"/>：</td>
      <td width="25%"><peis:text type="text" name="tgName" extendProperty="class='user_add_input'"/></td>
    </tr>
    <tr>
      <td align="right" width="15%"><bean:message bundle="archive" key="archive.org"/>：</td>
      <td width="35%"><peis:selectlist name="orgNo" sql="ORG0002" extendProperty="class='user_add_input'"/></td>
      <td align="right" width="15%"><bean:message bundle="archive" key="archive.tg.status"/>：</td>
      <td width="35%"><peis:selectlist name="tgStatus" sql="TG00A" extendProperty="class='user_add_input'"/></td>
    </tr>
    <tr>
      <td align="right" width="15%"><bean:message bundle="archive" key="archive.tg.capa"/>：</td>
      <td width="35%"><peis:text type="text" name="tgCapa" extendProperty="class='user_add_input'"/>KVA</td>
      <td align="right" width="15%"><bean:message bundle="archive" key="archive.tg.tgThreshold"/>：</td>
      <td width="35%"><peis:text type="text" name="tgThreshold" extendProperty="class='user_add_input'"/></td>
    </tr>
    <tr>
      <td align="right" width="15%"><font color="red">*</font><bean:message bundle="archive" key="archive.voltGrade"/>：</td>
      <td width="35%"><peis:selectlist name="voltGrade" sql="COMMON0007" extendProperty="class='user_add_input'"/></td>
      <td align="right" width="15%"></td>
      <td width="35%"></td>
    </tr>
    <tr> 
      <td align="right"><bean:message bundle="archive" key="archive.installAddr"/>：</td>
      <td colspan="3"><peis:text type="text" name="installAddr" extendProperty="class='user_add_input2'"/></td>
    </tr>
    <tr> 
      <td align="right"><bean:message bundle="archive" key="archive.remark"/>：</td>
      <td colspan="3"><textarea name="remark" class="input_textarea2"></textarea></td>
    </tr>
    
     <tr>
   	<td align="right" colspan="6">
   		<input class="input_save"  type="submit" name="entry" value="<bean:message key='global.save'/>" />
   		<input class="input_cancel" type="button" value="<bean:message  key='global.cancel'/>" onclick="javascript:window.close();">
   		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   	</td>
   </tr>
  </table>
  </html:form>
  <html:javascript formName="tgInfoForm" />
</div>
</body>
</html>
