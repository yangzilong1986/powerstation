<%@ page language="java" pageEncoding="UTF-8"%>

<%@include file="../../commons/taglibs.jsp"%>


<html>
<head>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/control.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
var confirmDel = '<bean:message bundle="system" key="code.confirmDel"/>';
var errorQzmc = '<bean:message bundle="system" key="group.errorQzmc"/>';
function initEdit(){
    var groupId = document.getElementsByName("groupId")[0].value;
    var groupType = document.getElementsByName("groupType")[0].value;
    var url = contextPath + "/system/groupAction.do?action=getListByID&groupId=" + groupId + "&groupType=" + groupType;
    document.getElementsByName("SCHEME_LIST")[0].src = url;
    var mothed = '<bean:write name="mothed" scope="request"/>';
    var groupName = document.getElementsByName("groupName")[0];
    var shareFlag = document.getElementsByName("shareFlag")[0];
    var groupType = document.getElementsByName("groupType")[0];
    var remark = document.getElementsByName("remark")[0];
    var groupAddress =document.getElementsByName("groupAddress")[0];
    if(mothed == "add") {
        document.getElementById("save").style.display = "";
        groupName.disabled = false;       
        shareFlag.disabled = false;
        groupType.disabled = false;
        remark.disabled = false;
        groupAddress.disabled = false;
    }
}

//群组类型变化
function changeGroupType(){
    var groupId = document.getElementsByName("groupId")[0].value;
    var groupType = document.getElementsByName("groupType")[0].value;
    var url = contextPath + "/system/groupAction.do?action=getListByID&groupId=" + groupId + "&groupType=" + groupType;
    document.getElementsByName("SCHEME_LIST")[0].src = url;
}
  
//编辑群组
function editGroup() {
    document.getElementsByName("groupName")[0].disabled = false;
    document.getElementsByName("shareFlag")[0].disabled = false;
    document.getElementsByName("remark")[0].disabled = false;
    document.getElementsByName("groupAddress")[0].disabled = false;
    //document.getElementsByName("groupType")[0].disabled = false;
    document.getElementById("groupTypeId").disabled = false;
    document.getElementById("save").style.display = "";
    document.getElementById("addUser").style.display = "";
    document.getElementById("delUser").style.display = "";
}
      
//删除群组
function deleteGroup() {
    var groupId=document.getElementById("groupId").value;
    if(confirm(confirmDel)){
        hideframe.location.href = contextPath + "/system/groupAction.do?action=delGroup&groupId=" + groupId + "&random=" + Math.random();
    }
}

function submitDisposal() {
    var groupName = trim(document.all.groupName.value);
    if(groupName == ""){
        alert(errorQzmc);
        return false;
    }
    var groupAddress = document.getElementsByName("groupAddress")[0].value;
	var patrn = /^[0-9A-F]*$/;
	if((groupAddress !=''||groupAddress!=null)&&!patrn.test(groupAddress)) {
		alert("群组地址格式必须为16进制,而且必须是大写");
		return false;
	}
    
    return true;
}

function authorizeGroup() {
    var groupId = document.getElementById("groupId").value;
    var str_url = contextPath + "/system/groupAction.do?action=authorize&groupId=" + groupId + "&random=" + Math.random();
    top.showDialogBox("群组授权", str_url, 575, 900);
}

function chooseUser() {
    var s_id = document.getElementsByName("groupId")[0].value;
    var groupType = document.getElementsByName("groupType")[0].value;
    var url = contextPath + "/system/groupAction.do?action=chooseUserInit&s_id=" + s_id + "&groupType=" + groupType;
    top.showDialogBox("对象选择", url, 575, 900);
}

function flusUserList() {
    var groupId = document.getElementsByName("groupId")[0].value;
    var groupType = document.getElementsByName("groupType")[0].value;
    var url = contextPath + "/system/groupAction.do?action=getListByID&groupId=" + groupId + "&groupType=" + groupType;
    document.getElementsByName("SCHEME_LIST")[0].src = url;
}

function deleteUser() {
    var groupId = document.getElementsByName("groupId")[0].value;
    window.SCHEME_LIST.delUser(groupId);
}

function fresh(groupId){
    if(typeof groupId == "undefined") {
        groupId = "";
    }
    parent.groupList.location.href = contextPath + '/system/groupAction.do?action=getGroupList&sqlcode=AL_SYSTEM_0100&random=' + Math.random();
    parent.groupContent.location.href = contextPath + "/system/groupAction.do?action=showGroup&mothed=edit&groupId=" + groupId + "&random=" + Math.random();
}
</script>
</head>

<body onload="initEdit()">
<html:form action="/system/groupAction" onsubmit="return submitDisposal();" target="hideframe">
  <html:hidden property="groupId" />
  <input type="hidden" name="action" value="saveOrUpdate" />
  <div class="pad5" style="height: 35px;">
    <logic:equal value="yes" name="isCanW" scope="request">
      <input type="button" name="edit" class="input2" value='<bean:message bundle="system" key="group.button.bjqz"/>' onclick="editGroup()" />
      <input type="button" name="delete" class="input2" value='<bean:message bundle="system" key="group.button.scqz"/>' onclick="deleteGroup()" />
    </logic:equal>
    <logic:equal value="yes" name="hasAuthorize" scope="request">
      <input type="button" name="authorize" class="input2" value='<bean:message bundle="system" key="group.button.sq"/>' onclick="authorizeGroup()" />
    </logic:equal>
  </div>
  <div class="tab"><em><bean:message bundle="system" key="group.qzxxxx" /></em></div>
  <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-66));">
    <div class="main">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="15%" class="label"><bean:message bundle="system" key="group.qzmc" />：</td>
          <td width="25%" class="dom"><html:text property="groupName" disabled="true" /></td>
          <td width="15%">
            <html:checkbox property="shareFlag" disabled="true" value="1" />
            <bean:message bundle="system" key="group.sfgk" />
          </td>
          <td width="15%" class="label"><bean:message bundle="system" key="group.qzlb" />：</td>
          <td width="25%">
            <peis:selectlist name="groupType" sql="AL_SYSTEM_0102" onChange="changeGroupType();" extendProperty="class='mainSelect'" />
            <html:hidden styleId="groupTypeId" property="groupType" disabled="true" />
          </td>
        </tr>
        <tr>
          <td width="15%" class="label" >群组地址：</td>
          <td width="25%" class="dom" colspan="2"><html:text  maxlength="4" property="groupAddress" disabled="true" /><font size="1">&nbsp;&nbsp;(4位16进制)</font></td>
        </tr>
        <tr>
          <td width="15%" class="label"><bean:message bundle="system" key="group.bz" />：</td>
          <td colspan="4"><html:textarea property="remark" rows="5" cols="45" disabled="true"></html:textarea></td>
        </tr>
      </table>
    </div>
    <div class="data1">
      <span><bean:message bundle="system" key="group.yhlb"/></span>
      <h1>
        <input type="button" id="addUser" style="display:none" value='<bean:message bundle="system" key="group.button.tjyh"/>' class="input2" onclick="chooseUser()" />
        <input type="button" id="delUser" style="display:none" value='<bean:message bundle="system" key="group.button.scyh"/>' class="input2" onclick="deleteUser()" />
        <html:submit styleId="save" style="display:none" styleClass="input2"><bean:message bundle="system" key="group.button.bcqz" /></html:submit>
      </h1>
    </div>
    <div class="data1_con">
      <div style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 232));">
        <iframe name="SCHEME_LIST" id="SCHEME_LIST" src="" width="100%" height="100%" frameborder="0"></iframe>
      </div>
    </div>
  </div>
  <script type="text/javascript">
  document.getElementsByName("groupType")[0].disabled = true;
  </script>
</html:form>
<iframe name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"></iframe>
</body>
</html>