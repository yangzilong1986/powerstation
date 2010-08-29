<%@ page language="java" pageEncoding="UTF-8"%>

<%@include file="../../commons/taglibs.jsp"%>


<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title><bean:message bundle="system" key="dept.edit.title" /></title>
<link href="<peis:contextPath/>/css/window.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript">
function checkForm(obj) {
    var patrn = /^[0-9]*$/;
    if(!patrn.test($("#orgNo").val())) {
        alert("单位编号必须输入数字");
        $("#orgNo").focus();
        return false;
    }
    else {
        return validateOrgForm(obj);
    }
}
</script>
</head>
<body>
<html:form action="/system/orgAddAction" onsubmit="return checkForm(this)" target="hideframe">
  <html:hidden property="action" />
  <div id="body">
    <div class="tab">
      <ul>
        <li id="tab_1" class="tab_on">
          <a href="#" onClick="return false;" onFocus="blur()"><bean:message bundle="system" key="dept.edit.title" /></a>
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
              <table width="100%" align="center" border="0" cellpadding="0"cellspacing="1">
                <tr>
                  <td colspan="4" style="height: 20px;"></td>
                </tr>
                <tr>
                  <td width="15%">
                    <bean:message bundle="system" key="dept.dwbh" />（<font color="#ff0000">*</font>）：
                    <html:hidden property="id" />
                    <input type="hidden" name="oldOrgNo" value='<bean:write name="orgForm" property="orgNo"/>' />
                  </td>
                  <td width="35%">
                    <html:text styleId="orgNo" property="orgNo" disabled="disabled" />
                    <logic:equal name="orgForm" property="action" value="save"></logic:equal>
                  </td>
                  <td width="15%"><bean:message bundle="system" key="dept.dwmc" />（<font color="#ff0000">*</font>）：</td>
                  <td width="35%"><html:text property="orgName" /></td>
                </tr>
                <tr>
                  <td><bean:message bundle="system" key="dept.dwlx" />：</td>
                  <td>
                    <peis:selectlist name="orgType" sql="COMMON0032" extendProperty="class='mainSelect'" value="" />
                  </td>
                  <td><bean:message bundle="system" key="dept.sjdwmc" />：</td>
                  <td>
                    <bean:write name="orgForm" property="upOrgName" />
                    <html:hidden property="orgUpper" />
                  </td>
                </tr>
                <tr>
                  <td><bean:message bundle="system" key="dept.sjdwlx" />：</td>
                  <td><bean:write name="orgForm" property="upOrgTypeName" /></td>
                  <td><bean:message bundle="system" key="dept.pxh" />（<font color="#ff0000">*</font>）：</td>
                  <td><html:text property="orgSort" /></td>
                </tr>
                <tr>
                  <td colspan="4" style="height: 20px;"></td>
                </tr>
                <tr>
                  <td  align="center" colspan="4">
                    <html:submit styleClass="input1">
                      <bean:message bundle="system" key="button.qd" />
                    </html:submit>
                    &nbsp;&nbsp;
                    <input type="button" name="cancel2" value='<bean:message bundle="system" key="button.qx"/>' class="input1" onclick="top.GB_hide()" />
                  </td>
                </tr>
                <tr>
                  <td colspan="4" style="height: 20px;"></td>
                </tr>
              </table>
            </td>
            <td class="contentRight">　</td>
          </tr>
        </table>
      </div>
    </div>
  </div>
  <iframe name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"> </iframe>
</html:form>
<html:javascript formName="orgForm" />
</body>
</html>
