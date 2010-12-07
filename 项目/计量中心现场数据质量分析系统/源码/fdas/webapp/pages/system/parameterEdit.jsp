<%@ page contentType="text/html; charset=utf-8" %>




<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=gb2312">
<title><bean:message bundle="system" key="parameter.edit.title"/></title>
<link href="<peis:contextPath/>/css/window.css" rel="stylesheet" type="text/css">
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";
</script>
</head>

<body>
<html:form action="/parameterAddAction" onsubmit="return validateParameterForm(this)"  method="post" target="hideframe">
<input type="hidden" name="action" value="update"/>
  <div id="body">
    <div class="tab">
      <ul>
        <li id="tab_1" class="tab_on">
          <a href="#" onClick="return false;" onFocus="blur()">参数编辑</a>
        </li>
        <li class="clear"></li>
      </ul>
    </div>
    <div id="main"  style="width:expression(((document.documentElement.clientWidth||document.body.clientWidth) - 18));">
      <div id="tool">
        <table width="98%" border="0" cellpadding="0" cellspacing="0" align="center" class="e_detail_t">
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
                   <logic:equal name="isEdit" scope="request" value="1">
                    <html:submit styleClass="input1"><bean:message bundle="system" key="button.qd"/></html:submit>
            		<input type="button" name="cancel2" value='<bean:message bundle="system" key="button.qx"/>' class="input1" onclick="parent.GB_hide()"/>
                    </logic:equal>
                    <logic:equal name="isEdit" scope="request" value="0">
                    	<input type="button" name="cancel2" value='<bean:message bundle="system" key="button.qx"/>' class="input1" onclick="parent.GB_hide()"/>
                    </logic:equal>
                  </td>
                </tr>
              </table>
            </td>
            <td class="contentRight">　</td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</html:form>
 <html:javascript formName="parameterForm" />
<iframe name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"> </iframe>
</body>

</html>
