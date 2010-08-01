<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title><bean:message bundle="system" key="role.edit.title"/></title>
<link href="<peis:contextPath/>/css/window.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
function submitDisposal(form) {
    var wmf = window.frames.menuFunctionFrame;
    var sMenuFunctionID = "";
    if(wmf.bTreeviewLoaded) {
        sMenuFunctionID = wmf.getSelectedString1();
        //alert(sMenuFunctionID);
        if(sMenuFunctionID == "") {
            alert("请选择功能项");
            return false;
        }
        document.all.functionID.value = sMenuFunctionID;
    }
    return validateRoleForm(form);
}
</script>
</head>

<body>
<html:form action="/system/roleAddAction" onsubmit="return submitDisposal(this);" method="post" target="hideframe">
<input type="hidden" name="action" value="saveOrUpdate"/>
<html:hidden property="roleId"/>
<html:hidden property="roleType"/>
<div id="body">
  <div class="tab">
    <ul>
      <li id="tab_1" class="tab_on">
        <a href="#" onClick="return false;" onFocus="blur()"><bean:message bundle="system" key="role.edit.title"/></a>
      </li>
      <li class="clear"></li>
    </ul>
  </div>
  <div id="main"  style="width:expression(((document.documentElement.clientWidth||document.body.clientWidth)-18));">
    <div id="tool">
      <table width="100%" border=0 cellpadding=0 cellspacing=0 align=center class="e_detail_t">
        <tr align="center">
          <td class="contentLeft">　</td>
          <td>
            <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0" >
              <tr>
                <td width="15%"><bean:message bundle="system" key="role.jsmc"/><font color="#ff0000">*</font>：</td>
                <td><html:text property="roleName"/></td>
              </tr>
              <tr>
                <td width="15%"><bean:message bundle="system" key="role.jssm"/><font color="#ff0000">*</font>：</td>
                <td><html:textarea property="roleRemark" cols="49" rows="5"></html:textarea></td>
              </tr>
              <tr>
                <td colspan="2" style="height: 10px;"></td>
              </tr>
              <tr>
                <td width="100%" style="height: 244px; margin-top: 10px; margin-bottom: 10px;" colspan="2" align="center">
                  <input type="hidden" name="functionID"/>
                  <iframe style="border: 1px solid #5d90d7;" name="menuFunctionFrame" src="<peis:contextPath/>/system/buildFunctionAction.do?action=getRole&roleID=<bean:write name="roleId" scope="request"/>" scrolling="auto" width="98%" height="100%" frameborder="1"> </iframe>
                </td>
              </tr>
              <tr>
                <td colspan="2" style="height: 10px;"></td>
              </tr>
              <tr>
                <td width="100%" height="30" colspan="2" align="center">
                  <logic:notEqual name="isEdit" scope="request" value="1">
                    <html:submit styleClass="input1"><bean:message bundle="system" key="button.qd"/></html:submit>
                  </logic:notEqual>
                  <input type="button" name="cancel2" value='<bean:message bundle="system" key="button.qx"/>' class="input1" onclick="top.GB_hide()"/>
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
<html:javascript formName="roleForm" />
<iframe name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"> </iframe>
</body>

</html>
