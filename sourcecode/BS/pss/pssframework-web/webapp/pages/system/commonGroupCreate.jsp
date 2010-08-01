<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/control.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
var objectIdList = "<bean:write name='objectIdList' scope='request'/>";

$(document).ready(function(){
    document.getElementsByName("groupType")[0].disabled = true;
});

//创建群组
function create(){
    if(objectIdList == ""){
        return;
    }
    var groupName = document.getElementsByName("groupName")[0].value;
    var groupType = document.getElementsByName("groupType")[0].value;
    if(groupName == null || groupName == ""){
        alert("请填写群组名称!");
        return;
    }
    
    var params = {"objectIdList": objectIdList,
                  "groupType": groupType
                 };
    var strUrl = contextPath + "/system/groupAction.do?action=createGroupAndAddUser&" + jQuery.param(params) + "&" + $("#form1").serialize() + "&r=" + parseInt(Math.random() * 1000);
    $.get(strUrl, function(result){
        if("nameExist" == result){
            alert("群组名称存在!");
        }
        else if("success" == result){
            alert("创建群组成功!");
            parent.GB_hide();
        }
        else {
            alert("创建群组失败!");
        }
    });
}
</script>
</head>

<body>
<html:form action="/system/commonGroupAction" styleId="form1">
<html:hidden property="objectIdList"/>
<div class="pad10">
  <div class="tab"><em><bean:message bundle="system" key="group.qzxxxx" /></em></div>
  <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-166));">
    <div class="main">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="15%" class="label"><bean:message bundle="system" key="group.qzmc" />：</td>
          <td width="25%" class="dom"><html:text property="groupName"/></td>
          <td width="15%">
            <html:checkbox property="shareFlag" value="1" />
            <bean:message bundle="system" key="group.sfgk" />
          </td>
          <td width="15%" class="label"><bean:message bundle="system" key="group.qzlb" />：</td>
          <td width="25%">
            <peis:selectlist name="groupType" sql="AL_SYSTEM_0102" extendProperty="class='mainSelect'" />
          </td>
        </tr>
        <tr>
          <td width="15%" class="label"><bean:message bundle="system" key="group.bz" />：</td>
          <td colspan="4"><html:textarea property="remark" rows="8" cols="60"></html:textarea></td>
        </tr>
        <tr align="center">
          <td colspan="5" style="height: 80px;">
            <input type="button" name="qdBt" class="input1"
                value='<bean:message bundle="system" key="button.qd"/>'
                onclick="create();" />&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" name="cancel" class="input1"
                value='<bean:message bundle="system" key="button.qx"/>'
                onclick="parent.GB_hide();" />
          </td>
        </tr>
      </table>
    </div>
  </div>
</div>
</html:form>
</body>
</html>
