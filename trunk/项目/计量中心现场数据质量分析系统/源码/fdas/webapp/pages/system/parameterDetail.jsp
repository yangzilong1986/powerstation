<%@ page contentType="text/html; charset=utf-8" %>




<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=gb2312">
<title>参数查看</title>
<link href="<peis:contextPath/>/css/window.css" rel="stylesheet" type="text/css">
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";
</script>
</head>

<body>
<html:form action="/parameterAddAction" onsubmit="return validateParameterForm(this)"  method="post" target="hideframe">
<input type="hidden" name="action" value="detail"/>
<table width="98%" border=0 cellpadding=0 cellspacing=0 align=center class="e_detail_t">
    <tr>
    <td width=10><img src="<peis:contextPath/>/img/e_detail_tleft.gif" width=10 height=28></td>
    <td width="100%" background="<peis:contextPath/>/img/e_detail_tmain.gif" class="functionTitle">参数查看</td>  
    <td width=10><img src="<peis:contextPath/>/img/e_detail_tright.gif" width=10 height=28></td>
    </tr>
   <tr align="center">
    <td class="contentLeft">　</td>
    <td>
    <table width="98%" align="center" border="0" cellpadding="0"cellspacing="1" >
      <html:hidden property="orgNo"/>
       <html:hidden property="status"/>
       <html:hidden property="PName"/>
        <tr align="center">
          <td width="25%"><bean:message bundle="system" key="parameter.csmc"/>:</td>
         <td align="left"><bean:write name="parameterForm" property="PName"/></td>
        </tr>
        <tr align="center">
          <td><bean:message bundle="system" key="parameter.dwmc"/>:</td>
         <td align="left"><bean:write name="parameterForm" property="orgName"/></td>
        </tr>
        <logic:equal name="isEdit" scope="request" value="1">
         <tr align="center">
          <td><bean:message bundle="system" key="parameter.csz"/>:<font color="#ff0000">*</font></td>        
         <td align="left">       
         <html:text property="PValue"/>
         </td>
        </tr>
        <tr align="center">
          <td><bean:message bundle="system" key="parameter.bz"/>:<font color="#ff0000">*</font></td>
         <td align="left"><html:textarea property="remark" rows="5" cols="35"></html:textarea></td>
        </tr>
        </logic:equal>
         <logic:equal name="isEdit" scope="request" value="0">
         <tr align="center">
          <td><bean:message bundle="system" key="parameter.csz"/>:</td>        
         <td align="left">       
         <bean:write name="parameterForm" property="PValue" />
         </td>
        </tr>
        <tr align="center">
          <td><bean:message bundle="system" key="parameter.bz"/>:</td>
         <bean:write name="parameterForm" property="remark"/>
        </tr>
        </logic:equal>
         <tr>
          <td width="100%" height="30" colspan="2" align="center">
            	<input type="button" name="cancel2" value='<bean:message bundle="system" key="button.qx"/>' class="input_ok" onclick="window.close()"/>
        
          </td>
        </tr>
      </table>
    </td>
    <td class="contentRight">　</td>
  </tr>
  <tr valign="middle" bgcolor="#FFFFFF">
    <td ><img src="<peis:contextPath/>/img/e_detail_bleft.gif" width=10 height=27></td>
    <td align="right" background="<peis:contextPath/>/img/e_detail_bmain.gif">&nbsp;</td>
    <td ><img src="<peis:contextPath/>/img/e_detail_bright.gif" width=10 height=27></td>
    </tr>
</table>
</html:form>
 <html:javascript formName="parameterForm" />
<iframe name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"> </iframe>
</body>

</html>
