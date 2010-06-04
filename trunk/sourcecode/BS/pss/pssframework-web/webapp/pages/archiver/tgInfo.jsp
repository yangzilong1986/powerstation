
<!--
  本页的作用是显示大客户信息的内容
  内容包括：大用户信息，终端，电表，模块表，采集点，总加组信息,开关

  并在此提供新增、编辑与删除的入口

-->
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
<%@page contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<peis:contextPath />/css/window.css" rel="stylesheet" type="text/css">

<%--<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>--%>
<%--<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>--%>
<html>
<head>
<title><bean:message bundle="archive" key="archive.tg.title"/></title>
</head>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";
function update() {
var tgcapa = document.getElementsByName("tgCapa")[0].value;
var tgname = document.getElementsByName("tgName")[0].value;
var partern= /^[0-9]\d*$/;
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
	
	if(tgcapa!=""&&!partern.exec(tgcapa)){
	    alert("<bean:message bundle="archive" key="archive.capa.isnotnumber"/>");
		return false;
	}
	if(tgname.length>60){
	    alert("<bean:message bundle="archive" key="archive.tgname.length"/>");
		return false;
	}
	document.getElementsByName("Update")[0].disabled = true;
	document.forms[0].submit();
}

</script>
<body bgcolor="#f7f7ff" >
<html:form id="tgForm" action="do/archive/editTgInfo" method="post" target="hideFrame">
<input type="hidden" name="action" value="editTgInfo">
<input type="hidden" name="tgId" value="<bean:write name="tg" property="tgId"/>">
<table width=80%" border="0" align="center"  cellpadding="0" cellspacing="0" >
    <tr>
      <tr>
		 <td colspan="4">&nbsp;</td>
      </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.tg.no"/>：</td>
      <td width="30%"><input type="hidden" name="tgNo" value="<bean:write name="tg" property="tgNo"/>" class="user_add_input" style="width: 140;"><bean:write name="tg" property="tgNo"/></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.tg.name"/>：</td>
      <td width="30%"><input type="text" name="tgName" value="<bean:write name="tg" property="tgName"/>"  class="user_add_input" style="width: 140;"></td>
    </tr> 
    <tr>
		 <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.org"/>：</td>
      <td width="30%"><peis:selectlist name="orgNo" sql="ORG0002" extendProperty="class='user_add_select' style='width: 140;'"/></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.tg.status"/>：</td>
      <td width="30%"><peis:selectlist name="tgStatus" sql="TG00A"  extendProperty="class='user_add_select' style='width: 140;'"/></td>
    </tr>
    <tr>
		 <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.tg.capa"/>：</td>
      <td width="30%"><input type="text" name="tgCapa" value="<bean:write name="tg" property="tgCapa"/>"  class="user_add_input" style="width: 140;"></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.tg.tgThreshold"/>：</td>
      <td width="30%"><input type="text" name="tgThreshold" value="<bean:write name="tg" property="tgThreshold"/>" class="user_add_input" style="width: 140;"></td>
    </tr>
     <tr>
		 <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td align="right" width="15%"><font color="red">*</font><bean:message bundle="archive" key="archive.voltGrade"/>：</td>
      <td width="35%"><peis:selectlist name="voltGrade" sql="COMMON0007" extendProperty="class='user_add_select' style='width: 140;'" /></td>
      <td align="right" width="15%"></td>
      <td width="35%"></td>
    </tr>
    <tr>
		 <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.installAddr"/>：</td>
      <td colspan="3"><input type="text" name="installAddr" value="<bean:write name="tg" property="installAddr"/>"  class="user_add_input" style="width: 399;"></td>
    </tr>
    <tr>
		 <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.remark"/>：</td>
      <td colspan="3"><textarea name="remark" class="input_textarea2" style="width: 399;"><bean:write name="tg" property="remark"/></textarea></td>
    </tr>
    <tr>
		 <td colspan="4">&nbsp;</td>
    </tr>
    <tr>
   	<td align="right" colspan="4">
   	    <input class="input_ok" type="button" name="Update" value="<bean:message key='global.save'/>" onclick="update();">
   		&nbsp;&nbsp;
   	</td>
   </tr>  
</table>
</html:form>
<iframe name="hideFrame" src="" frameborder=0 width=0 height=0></iframe>
</body>
</html>
