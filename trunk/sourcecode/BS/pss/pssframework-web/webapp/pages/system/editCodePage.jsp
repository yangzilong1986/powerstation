<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=gb2312">
<title><bean:message bundle="system" key="code.edit.title"/></title>
<link href="<peis:contextPath/>/css/window.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";
</script>
</head>

<body>
<html:form action="/system/codeAddAction" onsubmit="return validateCodeForm(this)"  method="post" target="hideframe">
<html:hidden property="action"/>
<div id="body">
    <div class="tab">
      <ul>
        <li id="tab_1" class="tab_on">
          <a href="#" onClick="return false;" onFocus="blur()">编码编辑</a>
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
              <html:hidden property="codeType"/>
              <html:hidden property="remark"/>
                <tr align="center">
                  <td width="25%"><bean:message bundle="system" key="code.bmlb"/>:</td>
                 <td><bean:write name="codeForm" property="codeCate"/></td>
                 <html:hidden property="codeCate"/>
                </tr>
                <tr align="center">
                  <td><bean:message bundle="system" key="code.bmz"/>:<font color="#ff0000">*</font></td>
                 <td ><html:text property="code"/></td>
                 <input type="hidden" name="code1" value="<bean:write name="codeForm" property="code"/>" />
                </tr>
                 <tr align="center">
                  <td><bean:message bundle="system" key="code.bmsm"/>:<font color="#ff0000">*</font></td>
                 <td ><html:text property="name"/></td>
                </tr>
                <tr>
                  <td width="100%" height="30" colspan="2" align="center">
                  <logic:notEqual name="isEdit" scope="request" value="false">
                    <html:submit styleClass="input1"><bean:message bundle="system" key="button.qd"/></html:submit>
                  </logic:notEqual>
            		<input type="button" name="cancel2" value='<bean:message bundle="system" key="button.qx"/>' class="input1" onclick="parent.GB_hide()"/>
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
<html:javascript formName="codeForm" />
</body>
</html>
