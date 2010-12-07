<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>


<html>
<head>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css"/>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';
var confirmDel = '<bean:message bundle="system" key="group.confirmDel"/>';
var errorUser = '<bean:message bundle="system" key="group.errorUser"/>';
function rowNum() {
    var tabObj = document.getElementById("userList");
    var num = tabObj.rows.length;
    return num;
}

function doClickAll() {
    var flag = document.getElementById("chooseAll").checked;
    var items = document.getElementsByName("ItemID");
    for(i=0; i < items.length; i++) {
        items[i].checked = flag;
    }
}

function delUser(s_id) {
    var userlist = "";
    var items = document.getElementsByName("ItemID");
    var groupType = parent.document.getElementsByName("groupType")[0].value;
    for(i=0; i < items.length; i++) {
        if(items[i].checked) {
            userlist += ","+items[i].value;
        }
    }
    if(userlist == "") {
        alert(errorUser);
    }
    else {
        if(window.confirm(confirmDel)) {
            window.location = contextPath + "/system/groupAction.do?action=delUser&s_id=" + s_id + "&userList=" + userlist + "&groupType=" + groupType;
        }
    }
} 
</script>
</head>
<body>
<html:form action="/system/schListAction">
  <html:hidden property="action" value="getQuery"/>
  <html:hidden property="pageType" value="page"/>
  <html:hidden property="sqlcode"/>
  <html:hidden property="s_id"/>
  <div id="tableContainer" class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-27)); margin-top: 0px;margin-left: 0px;">
    <table id="userList" border="0" cellpadding="0" cellspacing="0" width="100%">
      <thead><tr>
        <logic:equal name="schemeListForm" property="sqlcode" value="AL_SYSTEM_0103">
          <th width="6%"><input type="checkbox" id="chooseAll" onclick="doClickAll()"/></th>
          <th width="6%"><bean:message bundle="system" key="group.xh"/></th>
          <th width="10%"><bean:message bundle="system" key="group.dwmc"/></th>
          <th width="10%"><bean:message bundle="system" key="group.sfdxl"/></th>
          <th width="20%"><bean:message bundle="system" key="group.hm"/></th>
          <th width="12%"><bean:message bundle="system" key="group.hh"/></th>
          <th width="10%"><bean:message bundle="system" key="group.sshy"/></th>
          <th width="8%"><bean:message bundle="system" key="group.yxrl"/></th>
          <th width="8%"><bean:message bundle="system" key="group.dydj"/></th>
          <th width="10%"><bean:message bundle="system" key="group.ydlb"/></th>
        </logic:equal>
        <logic:equal name="schemeListForm" property="sqlcode" value="AL_SYSTEM_0120">
          <th width="8%"><input type="checkbox" id="chooseAll" onclick="doClickAll()"/></th>
          <th width="8%"><bean:message bundle="system" key="group.xh"/></th>
          <th width="20%"><bean:message bundle="system" key="group.dwmc"/></th>
          <th width="22%"><bean:message bundle="system" key="group.tqmc"/></th>
          <th width="20%"><bean:message bundle="system" key="group.tqbh"/></th>
          <th width="10%"><bean:message bundle="system" key="group.fz"/></th>
          <th width="12%"><bean:message bundle="system" key="group.dydj"/></th>
        </logic:equal>
        <logic:equal name="schemeListForm" property="sqlcode" value="AL_SYSTEM_0121">
          <th width="6%"><input type="checkbox" id="chooseAll" onclick="doClickAll()"/></th>
          <th width="6%"><bean:message bundle="system" key="group.xh"/></th>
          <th width="10%"><bean:message bundle="system" key="group.dwmc"/></th>
          <th width="20%"><bean:message bundle="system" key="group.hm"/></th>
          <th width="12%"><bean:message bundle="system" key="group.hh"/></th>
          <th width="10%"><bean:message bundle="system" key="group.tqmc"/></th>
          <th width="8%"><bean:message bundle="system" key="group.sfzdh"/></th>
          <th width="10%"><bean:message bundle="system" key="group.ydlb"/></th>
        </logic:equal>
        <logic:equal name="schemeListForm" property="sqlcode" value="AL_SYSTEM_0104">
          <th width="6%"><input type="checkbox" id="chooseAll" onclick="doClickAll()"/></th>
          <th width="6%"><bean:message bundle="system" key="group.xh"/></th>
          <th width="12%"><bean:message bundle="system" key="group.dwmc"/></th>
          <th width="10%"><bean:message bundle="system" key="group.zcbh"/></th>
          <th width="10%"><bean:message bundle="system" key="group.ljdz"/></th>
          <th width="14%"><bean:message bundle="system" key="group.gy"/></th>
          <th width="12%"><bean:message bundle="system" key="group.zdlx"/></th>
          <th width="10%"><bean:message bundle="system" key="group.ssdxbh"/></th>
          <th width="10%"><bean:message bundle="system" key="group.ssdxmc"/></th>
        </logic:equal>
      </tr></thead>
      <tbody align="center">
        <logic:present name="PG_QUERY_RESULT">
          <logic:iterate id="datainfo" name="PG_QUERY_RESULT">
            <logic:equal name="schemeListForm" property="sqlcode" value="AL_SYSTEM_0103">
              <tr style="cursor:pointer;">
                <td><input type="checkbox" name="ItemID" value="<bean:write name='datainfo' property='col9'/>@1"/></td>
                <td><bean:write name="datainfo" property="rowNo"/></td>
                <td><bean:write name="datainfo" property="col1"/></td>
                <td><bean:write name="datainfo" property="col2"/></td>
                <td><bean:write name="datainfo" property="col3"/></td>
                <td><bean:write name="datainfo" property="col4"/></td>
                <td><bean:write name="datainfo" property="col5"/></td>
                <td><bean:write name="datainfo" property="col6"/></td>
                <td><bean:write name="datainfo" property="col7"/></td>
                <td><bean:write name="datainfo" property="col8"/></td>
              </tr>
            </logic:equal>
            <logic:equal name="schemeListForm" property="sqlcode" value="AL_SYSTEM_0120">
              <tr style="cursor:pointer;">
                <td><input type="checkbox" name="ItemID" value="<bean:write name='datainfo' property='col6'/>@2"/></td>
                <td><bean:write name="datainfo" property="rowNo"/></td>
                <td><bean:write name="datainfo" property="col1"/></td>
                <td><bean:write name="datainfo" property="col2"/></td>
                <td><bean:write name="datainfo" property="col3"/></td>
                <td><bean:write name="datainfo" property="col4"/></td>
                <td><bean:write name="datainfo" property="col5"/></td>
              </tr>
            </logic:equal>
            <logic:equal name="schemeListForm" property="sqlcode" value="AL_SYSTEM_0121">
              <tr style="cursor:pointer;">
                <td><input type="checkbox" name="ItemID" value="<bean:write name='datainfo' property='col9'/>@3"/></td>
                <td><bean:write name="datainfo" property="rowNo"/></td>
                <td><bean:write name="datainfo" property="col5"/></td>
                <td><bean:write name="datainfo" property="col3"/></td>
                <td><bean:write name="datainfo" property="col2"/></td>
                <td><bean:write name="datainfo" property="col7"/></td>
                <td><bean:write name="datainfo" property="col8"/></td>
                <td><bean:write name="datainfo" property="col9"/></td>
              </tr>
            </logic:equal>
            <logic:equal name="schemeListForm" property="sqlcode" value="AL_SYSTEM_0104">
              <tr style="cursor:pointer;">
                <td><input type="checkbox" name="ItemID" value="<bean:write name='datainfo' property='col8'/>@5"/></td>
                <td><bean:write name="datainfo" property="rowNo"/></td>
                <td><bean:write name="datainfo" property="col1"/></td>
                <td><bean:write name="datainfo" property="col2"/></td>
                <td><bean:write name="datainfo" property="col3"/></td>
                <td><bean:write name="datainfo" property="col4"/></td>
                <td><bean:write name="datainfo" property="col5"/></td>
                <td><bean:write name="datainfo" property="col6"/></td>
                <td><bean:write name="datainfo" property="col7"/></td>
              </tr>
            </logic:equal>
          </logic:iterate>
        </logic:present>
      </tbody>  
    </table>
  </div>
  <div class="pageContainer">
    <logic:equal name="schemeListForm" property="sqlcode" value="AL_SYSTEM_0103">
      <peis:pagination sql="AL_SYSTEM_0103" queryActionPath="/system/schListAction" allowRowsChange="true"/>
    </logic:equal>
    <logic:equal name="schemeListForm" property="sqlcode" value="AL_SYSTEM_0120">
      <peis:pagination sql="AL_SYSTEM_0120" queryActionPath="/system/schListAction" allowRowsChange="true"/>
    </logic:equal>
    <logic:equal name="schemeListForm" property="sqlcode" value="AL_SYSTEM_0121">
      <peis:pagination sql="AL_SYSTEM_0121" queryActionPath="/system/schListAction" allowRowsChange="true"/>
    </logic:equal>
    <logic:equal name="schemeListForm" property="sqlcode" value="AL_SYSTEM_0104">
      <peis:pagination sql="AL_SYSTEM_0104" queryActionPath="/system/schListAction" allowRowsChange="true"/>
    </logic:equal>
  </div>
</html:form>
</body>
</html>
