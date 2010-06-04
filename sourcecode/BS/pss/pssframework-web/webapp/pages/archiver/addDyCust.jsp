
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
<%@page contentType="text/html; charset=UTF-8"%> 
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<peis:contextPath />/css/window.css" rel="stylesheet" type="text/css">
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/object/object_biguserinfomation.js">
</script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common-ajax.js"></script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title><bean:message bundle="archive" key="archive.new.DyCust"/></title>
</head>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";
</script>
<body bgcolor="#f7f7ff"  style="overflow-x:hidden;overflow-y:hidden;">
<ul class="e_title">
    <li class="e_titleon"><a href="#"><bean:message bundle="archive" key="archive.tg.cust"/></a>
  </ul>
  <div class="user_add_table">
  <html:form  action="do/archive/addArchiveInTg" method="post" target="resultPage">
  <input type="hidden" name="action" value="addDyCust">
  <table width="96%" border="0" align="center"  cellpadding="0" cellspacing="0" >
    <tr>
      <td id="msg" colspan="6"></td>
    </tr>
    <tr>
      <td align="right" width="15%"><bean:message bundle="archive" key="archive.custNo"/>：</td>
      <td width="35%"><input type="text" name="custNo" class="user_add_input"></td>
      <td align="right" width="15%"><bean:message bundle="archive" key="archive.custName"/>：</td>
      <td width="35%"><input type="text" name="custName" class="user_add_input"></td>
    </tr>
    <tr>
      <td align="right" width="15%"><bean:message bundle="archive" key="archive.org"/>：</td>
      <td width="35%"><peis:selectlist name="orgNo" sql="LOG0002"  extendProperty=" class='user_add_input'"/></td>
      <td align="right"><bean:message bundle="archive" key="archive.cust.status"/>：</td>
      <td><peis:selectlist name="custStatus" sql="COMMON0004" extendProperty=" class='user_add_input'"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message bundle="archive" key="archive.cust.status"/>：</td>
      <td><peis:selectlist name="tradeSort" sql="COMMON0002" extendProperty=" class='user_add_input'"/></td>
      <td align="right"><bean:message bundle="archive" key="archive.voltGrade"/>：</td>
      <td><peis:selectlist name="voltGrade" sql="COMMON0007"  extendProperty=" class='user_add_input'"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message bundle="archive" key="archive.contact"/>：</td>
      <td><input type="text" name="contact" class="user_add_input"></td>
      <td align="right"><bean:message bundle="archive" key="archive.phonet"/>：</td>
      <td><input type="text" name="phone" class="user_add_input"></td>
    </tr>
    <tr>
      <td align="right"><bean:message bundle="archive" key="archive.rmDay"/>：</td>
      <td><input type="text" name="rmDay" class="user_add_input"></td>
      <td align="right"><bean:message bundle="archive" key="archive.rmSectNo"/>：</td>
      <td><input type="text" name="rmSectNo" class="user_add_input"></td>
    </tr>
    <tr>
      <td align="right"><bean:message bundle="archive" key="archive.rmSn"/>：</td>
      <td><peis:selectlist name="rmSn" sql="COMMON0004" extendProperty=" class='user_add_input'"/></td>
      <td align="right"></td>
      <td></td>
    </tr>
    <tr>
      <td align="right"><bean:message bundle="archive" key="archive.addr"/>：</td>
      <td colspan="3"><input type="text" name="custAddr"  class="user_add_input2"></td>
    </tr>
    <tr> 
      <td align="right"><bean:message bundle="archive" key="archive.remark"/>：</td>
      <td colspan="3"><textarea name="remark" class="input_textarea2"></textarea></td>
    </tr>  
    <tr>
   	<td align="right" colspan="6">
   		<input class="input_save"  type="submit" name="button1" value="<bean:message bundle='archive' key='archive.label.save'/>"/>
   		<input class="input_cancel" type="button" value="<bean:message bundle='archive' key='archive.label.cacel'/>" onclick="javascript:window.close();">
   		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   	</td>
   </tr>
  </table>
</html:form>
</div>
<iframe name="resultPage" frameborder=0 width=100% height=100%></iframe>
</body>
</html>
