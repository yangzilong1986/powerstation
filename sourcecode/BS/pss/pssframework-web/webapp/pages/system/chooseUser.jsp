<<<<<<< .mine
<%@ page contentType="text/html; charset=utf-8" %>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>


<html>
<head>
<title>对象选择</title>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/object.js"></script>

<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
var chooseInfo = '请选择要添加的对象！';

function doClickAll() {
    var flag = document.getElementById("chooseAll").checked;
    var items = document.getElementsByName("ItemID");
    for(i=0; i < items.length; i++) {
        items[i].checked = flag;
    }
}
function addUser() {
    var userlist = "";
    var items = document.getElementsByName("ItemID");
    for(i=0; i <items.length; i++) {
        if(items[i].checked) {
            userlist += "," + items[i].value;
        }
    }
    if(userlist == "") {
        alert(chooseInfo);
    }
    else {
        document.getElementsByName("userList")[0].value = userlist;
        var s_id = document.getElementsByName("s_id")[0].value;
        document.forms[1].action = contextPath + "/system/groupAction.do?action=addUser&s_id=" + s_id;
        document.forms[1].submit();
    }
}
$(function(){
  //  var height = (document.documentElement.clientHeight||document.body.clientHeight) - 172;
  //  $(".tableContainer").height(height).css("overflow-y", "scroll");
});
</script>
</head>
<body >
<div id="main" align="center" style="width:expression(((document.documentElement.clientWidth||document.body.clientWidth) - 2));">
  <html:form action="/system/chooseUserAction" method="post">
    <html:hidden property="action" value="getQuery"/>
    <html:hidden property="pageType" value="page"/>
    <peis:text name="pageRows" type="hidden"/>
    <html:hidden property="sqlcode"/>
    <html:hidden property="s_id"/>
   <div id="tool">
    <div class="opbutton2">
            <html:submit styleClass="input1"><bean:message bundle="control" key="control.button.cx"/></html:submit>
            <input type="button" class="input1" value="<bean:message bundle='control' key='control.button.submit'/>" onclick="addUser()"/>
            <input type="button" class="input1" value="<bean:message bundle='control' key='control.button.gb'/>" onclick="parent.GB_hide();"/>
          </div>
    	 <table width="100%"  border="0" cellpadding="0" cellspacing="1">
            <tr height="30">
              <td width="70" class="label"><bean:message bundle="control" key="control.lable.gddw"/>：</td>
              <td width="120" class="dom">
                <peis:selectlist name="org_no" sql="COMMON0029" extendProperty="class='user_add_input'"/>
              </td>
              <td width="70" class="label"><bean:message bundle="control" key="control.lable.ljdz"/>：</td>
              <td width="120" class="dom">
                <html:text property="logic_addr" styleClass="user_add_input"/>
              </td>
              <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0108">
                <td width="70" class="label"><bean:message bundle="system" key="group.zt"/>：</td>
                <td width="120" class="dom">
                  <peis:selectlist name="cur_status" sql="COMMON0036" extendProperty="class='user_add_input'"/>
                </td>
                <td colspan="3"></td>
              </logic:equal>
              <logic:notEqual name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0108">
                <td colspan="5"></td>
              </logic:notEqual>
            </tr>
            <tr height="30">
              <td width="70" class="label"><bean:message bundle="control" key="control.lable.hh"/>：</td>
              <td width="120" class="dom">
                <html:text property="object_no" styleClass="user_add_input"/>
              </td>
              <td width="70" class="label"><bean:message bundle="control" key="control.lable.hm"/>：</td>
              <td width="120" class="dom">
                <html:text property="object_name" styleClass="user_add_input"/>
              </td>
              <td colspan="5"></td>
            </tr>
          </table>
  </div>
  <div class="content">
    <div id="cont_1">
      <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 125));">
        <table id="object_table" width="100%"  border="0" cellpadding="0" cellspacing="0">
                <thead>
                  <tr class="trheadstyle">
                    <th><input type="checkbox" id="chooseAll" onclick="doClickAll()" checked/></th>
                    <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0107">
                      <th><bean:message bundle="system" key="group.xh"/></th>
                      <th><bean:message bundle="system" key="group.dwmc"/></th>
                      <th><bean:message bundle="system" key="group.sfdxl"/></th>
                      <th><bean:message bundle="system" key="group.hm"/></th>
                      <th><bean:message bundle="system" key="group.hh"/></th>
                      <th><bean:message bundle="system" key="group.sshy"/></th>
                      <th><bean:message bundle="system" key="group.yxrl"/></th>
                      <th><bean:message bundle="system" key="group.dydj"/></th>
                      <th><bean:message bundle="system" key="group.ydlb"/></th>
                    </logic:equal>
                    <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0122">
                      <th><bean:message bundle="system" key="group.xh"/></th>
                      <th><bean:message bundle="system" key="group.dwmc"/></th>
                      <th><bean:message bundle="system" key="group.tqmc"/></th>
                      <th><bean:message bundle="system" key="group.tqbh"/></th>
                      <th><bean:message bundle="system" key="group.fz"/></th>
                      <th><bean:message bundle="system" key="group.dydj"/></th>
                    </logic:equal>
                    <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0123">
                      <th><bean:message bundle="system" key="group.xh"/></th>
                      <th><bean:message bundle="system" key="group.dwmc"/></th>
                      <th><bean:message bundle="system" key="group.hm"/></th>
                      <th><bean:message bundle="system" key="group.hh"/></th>
                      <th><bean:message bundle="system" key="group.tqmc"/></th>
                      <th><bean:message bundle="system" key="group.sfzdh"/></th>
                      <th><bean:message bundle="system" key="group.ydlb"/></th>
                    </logic:equal>
                    <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0108">
                      <th><bean:message bundle="system" key="group.xh"/></th>
                      <th><bean:message bundle="system" key="group.dwmc"/></th>
                      <th><bean:message bundle="system" key="group.zcbh"/></th>
                      <th><bean:message bundle="system" key="group.ljdz"/></th>
                      <th><bean:message bundle="system" key="group.gy"/></th>
                      <th><bean:message bundle="system" key="group.zdlx"/></th>
                      <th><bean:message bundle="system" key="group.ssdxbh"/></th>
                      <th><bean:message bundle="system" key="group.ssdxmc"/></th>
                      <th><bean:message bundle="system" key="group.zdzt"/></th>
                    </logic:equal>
                  </tr>
                </thead>
                <tbody align="center">
                  <logic:present name="PG_QUERY_RESULT">
                    <logic:iterate id="datainfo" name="PG_QUERY_RESULT">
                      <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0107">
                        <tr class="trmainstyle">
                          <td><input type="checkbox" name="ItemID" value="${datainfo.col9}@1" checked/></td>
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
                      <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0122">
                        <tr style="cursor:pointer;">
                          <td><input type="checkbox" name="ItemID" value="${datainfo.col6}@2" checked/></td>
                          <td><bean:write name="datainfo" property="rowNo"/></td>
                          <td><bean:write name="datainfo" property="col1"/></td>
                          <td><bean:write name="datainfo" property="col2"/></td>
                          <td><bean:write name="datainfo" property="col3"/></td>
                          <td><bean:write name="datainfo" property="col4"/></td>
                          <td><bean:write name="datainfo" property="col5"/></td>
                        </tr>
                      </logic:equal>
                      <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0123">
                        <tr style="cursor:pointer;">
                          <td><input type="checkbox" name="ItemID" value="${datainfo.col1}@3" checked/></td>
                          <td><bean:write name="datainfo" property="rowNo"/></td>
                          <td><bean:write name="datainfo" property="col5"/></td>
                          <td><bean:write name="datainfo" property="col3"/></td>
                          <td><bean:write name="datainfo" property="col2"/></td>
                          <td><bean:write name="datainfo" property="col7"/></td>
                          <td><bean:write name="datainfo" property="col8"/></td>
                          <td><bean:write name="datainfo" property="col9"/></td>
                        </tr>
                      </logic:equal>
                      <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0108">
                        <tr style="cursor:pointer;">
                          <td><input type="checkbox" name="ItemID" value="${datainfo.col8}@5" checked/></td>
                          <td><bean:write name="datainfo" property="rowNo"/></td>
                          <td><bean:write name="datainfo" property="col1"/></td>
                          <td><bean:write name="datainfo" property="col2"/></td>
                          <td><bean:write name="datainfo" property="col3"/></td>
                          <td><bean:write name="datainfo" property="col4"/></td>
                          <td><bean:write name="datainfo" property="col5"/></td>
                          <td><bean:write name="datainfo" property="col6"/></td>
                          <td><bean:write name="datainfo" property="col7"/></td>
                          <td><bean:write name="datainfo" property="col10"/></td>
                        </tr>
                      </logic:equal>
                    </logic:iterate>
                  </logic:present>
                </tbody>
              </table>
      </div>
     	 <div class="pageContainer" >
              <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0107">
                <peis:pagination sql="AL_SYSTEM_0107" queryActionPath="/system/chooseUserAction" allowRowsChange="true"/>
              </logic:equal>
              <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0122">
                <peis:pagination sql="AL_SYSTEM_0122" queryActionPath="/system/chooseUserAction" allowRowsChange="true"/>
              </logic:equal>
              <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0123">
                <peis:pagination sql="AL_SYSTEM_0123" queryActionPath="/system/chooseUserAction" allowRowsChange="true"/>
              </logic:equal>
              <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0108">
                <peis:pagination sql="AL_SYSTEM_0108" queryActionPath="/system/chooseUserAction" allowRowsChange="true"/>
              </logic:equal>
            </div>
    </div>
  </div>
  </html:form>
</div>
 <html:form action="/system/groupAction" method="post" target="temp" style="display:none">
    <input type="hidden" name="userList"/>
  </html:form>
  <iframe name="temp" src="<peis:contextPath/>/jsp/system/temp.jsp" style="display: none"></iframe>
</body>
=======
<%@ page contentType="text/html; charset=utf-8" %>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>


<html>
<head>
<title>对象选择</title>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/object.js"></script>

<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
var chooseInfo = '请选择要添加的对象！';

function doClickAll() {
    var flag = document.getElementById("chooseAll").checked;
    var items = document.getElementsByName("ItemID");
    for(i=0; i < items.length; i++) {
        items[i].checked = flag;
    }
}
function addUser() {
    var userlist = "";
    var items = document.getElementsByName("ItemID");
    for(i=0; i <items.length; i++) {
        if(items[i].checked) {
            userlist += "," + items[i].value;
        }
    }
    if(userlist == "") {
        alert(chooseInfo);
    }
    else {
        document.getElementsByName("userList")[0].value = userlist;
        var s_id = document.getElementsByName("s_id")[0].value;
        document.forms[1].action = contextPath + "/system/groupAction.do?action=addUser&s_id=" + s_id;
        document.forms[1].submit();
    }
}
$(function(){
  //  var height = (document.documentElement.clientHeight||document.body.clientHeight) - 172;
  //  $(".tableContainer").height(height).css("overflow-y", "scroll");
});
</script>
</head>
<body >
<div id="main" align="center" style="width:expression(((document.documentElement.clientWidth||document.body.clientWidth) - 2));">
  <html:form action="/system/chooseUserAction" method="post">
    <html:hidden property="action" value="getQuery"/>
    <html:hidden property="pageType" value="page"/>
    <peis:text name="pageRows" type="hidden"/>
    <html:hidden property="sqlcode"/>
    <html:hidden property="s_id"/>
    
   <div id="tool">
    <div class="opbutton2">
            <html:submit styleClass="input1"><bean:message bundle="control" key="control.button.cx"/></html:submit>
            <input type="button" class="input1" value="<bean:message bundle='control' key='control.button.submit'/>" onclick="addUser()"/>
            <input type="button" class="input1" value="<bean:message bundle='control' key='control.button.gb'/>" onclick="parent.GB_hide();"/>
          </div>
    	 <table width="100%"  border="0" cellpadding="0" cellspacing="1">
            <tr height="30">
              <td width="70" class="label"><bean:message bundle="control" key="control.lable.gddw"/>：</td>
              <td width="120" class="dom">
                <peis:selectlist name="org_no" sql="COMMON0029" extendProperty="class='user_add_input'"/>
              </td>
              <td width="70" class="label"><bean:message bundle="control" key="control.lable.ljdz"/>：</td>
              <td width="120" class="dom">
                <html:text property="logic_addr" styleClass="user_add_input"/>
              </td>
              <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0108">
                <td width="70" class="label"><bean:message bundle="system" key="group.zt"/>：</td>
                <td width="120" class="dom">
                  <peis:selectlist name="cur_status" sql="COMMON0036" extendProperty="class='user_add_input'"/>
                </td>
                <td colspan="3"></td>
              </logic:equal>
              <logic:notEqual name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0108">
                <td colspan="5"></td>
              </logic:notEqual>
            </tr>
            <tr height="30">
              <td width="70" class="label"><bean:message bundle="control" key="control.lable.hh"/>：</td>
              <td width="120" class="dom">
                <html:text property="object_no" styleClass="user_add_input"/>
              </td>
              <td width="70" class="label"><bean:message bundle="control" key="control.lable.hm"/>：</td>
              <td width="120" class="dom">
                <html:text property="object_name" styleClass="user_add_input"/>
              </td>
              
              <td width="70" class="label">在线：</td>
              <td width="120" class="dom">
                <peis:selectlist name="run_status" sql="SL_COMMON_0051"/>
              </td>
              <td colspan="5"></td>
            </tr>
          </table>
  </div>
  <div class="content">
    <div id="cont_1">
      <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 125));">
        <table id="object_table" width="100%"  border="0" cellpadding="0" cellspacing="0" >
                <thead>
                  <tr class="trheadstyle">
                    <th width="5%"><input type="checkbox" id="chooseAll" onclick="doClickAll()" checked/></th>
                    <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0107">
                      <th width="5%"><bean:message bundle="system" key="group.xh"/></th>
                      <th width="10%"><bean:message bundle="system" key="group.dwmc"/></th>
                      <th width="7%"><bean:message bundle="system" key="group.ljdz"/></th>
                      <th width="17%"><bean:message bundle="system" key="group.hm"/></th>
                      <th width="10%"><bean:message bundle="system" key="group.hh"/></th>
                      <th width="15%"><bean:message bundle="system" key="group.sshy"/></th>
                      <th width="5%"><bean:message bundle="system" key="group.yxrl"/></th>
                      <th width="5%"><bean:message bundle="system" key="group.dydj"/></th>
                      <th width="5%"><bean:message bundle="system" key="group.ydlb"/></th>
                    </logic:equal>
                    <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0122">
                      <th width="5%"><bean:message bundle="system" key="group.xh"/></th>
                      <th width="10%"><bean:message bundle="system" key="group.dwmc"/></th>
                      <th width="17%"><bean:message bundle="system" key="group.tqmc"/></th>
                      <th width="10%"><bean:message bundle="system" key="group.tqbh"/></th>
                      <th width="5%"><bean:message bundle="system" key="group.ljdz"/></th>
                      <th width="5%"><bean:message bundle="system" key="group.dydj"/></th>
                    </logic:equal>
                    <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0123">
                      <th width="5%"><bean:message bundle="system" key="group.xh"/></th>
                      <th width="10%"><bean:message bundle="system" key="group.dwmc"/></th>
                      <th width="15%"><bean:message bundle="system" key="group.hm"/></th>
                      <th width="10%"><bean:message bundle="system" key="group.hh"/></th>
                      <th width="15%"><bean:message bundle="system" key="group.tqmc"/></th>
                      <th width="10%"><bean:message bundle="system" key="group.sfzdh"/></th>
                      <th width="10%"><bean:message bundle="system" key="group.ydlb"/></th>
                    </logic:equal>
                    <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0108">
                      <th width="5%"><bean:message bundle="system" key="group.xh"/></th>
                      <th width="12%"><bean:message bundle="system" key="group.dwmc"/></th>
                      <th width="7%"><bean:message bundle="system" key="group.zcbh"/></th>
                      <th width="8%"><bean:message bundle="system" key="group.ljdz"/></th>
                      <th width="12%"><bean:message bundle="system" key="group.gy"/></th>
                      <th width="12%"><bean:message bundle="system" key="group.zdlx"/></th>
                      <th width="10%"><bean:message bundle="system" key="group.ssdxbh"/></th>
                      <th width="10%"><bean:message bundle="system" key="group.ssdxmc"/></th>
                      <th width="7%"><bean:message bundle="system" key="group.zdzt"/></th>
                    </logic:equal>
                  </tr>
                </thead>
                <tbody align="center">
                  <logic:present name="PG_QUERY_RESULT">
                    <logic:iterate id="datainfo" name="PG_QUERY_RESULT">
                      <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0107">
                        <tr class="trmainstyle">
                          <td><input type="checkbox" name="ItemID" value="${datainfo.col9}@1" checked/></td>
                          <td><bean:write name="datainfo" property="rowNo"/></td>
                          <td><bean:write name="datainfo" property="col1"/></td>
                          <td><bean:write name="datainfo" property="col10"/></td>
                          <td><bean:write name="datainfo" property="col3"/></td>
                          <td><bean:write name="datainfo" property="col4"/></td>
                          <td><bean:write name="datainfo" property="col5"/></td>
                          <td><bean:write name="datainfo" property="col6"/></td>
                          <td><bean:write name="datainfo" property="col7"/></td>
                          <td><bean:write name="datainfo" property="col8"/></td>
                        </tr>
                      </logic:equal>
                      <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0122">
                        <tr style="cursor:pointer;">
                          <td><input type="checkbox" name="ItemID" value="${datainfo.col6}@2" checked/></td>
                          <td><bean:write name="datainfo" property="rowNo"/></td>
                          <td><bean:write name="datainfo" property="col1"/></td>
                          <td><bean:write name="datainfo" property="col2"/></td>
                          <td><bean:write name="datainfo" property="col3"/></td>
                          <td><bean:write name="datainfo" property="col7"/></td>
                          <td><bean:write name="datainfo" property="col5"/></td>
                        </tr>
                      </logic:equal>
                      <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0123">
                        <tr style="cursor:pointer;">
                          <td><input type="checkbox" name="ItemID" value="${datainfo.col1}@3" checked/></td>
                          <td><bean:write name="datainfo" property="rowNo"/></td>
                          <td><bean:write name="datainfo" property="col5"/></td>
                          <td><bean:write name="datainfo" property="col3"/></td>
                          <td><bean:write name="datainfo" property="col2"/></td>
                          <td><bean:write name="datainfo" property="col7"/></td>
                          <td><bean:write name="datainfo" property="col8"/></td>
                          <td><bean:write name="datainfo" property="col9"/></td>
                        </tr>
                      </logic:equal>
                      <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0108">
                        <tr style="cursor:pointer;">
                          <td><input type="checkbox" name="ItemID" value="${datainfo.col8}@5" checked/></td>
                          <td><bean:write name="datainfo" property="rowNo"/></td>
                          <td><bean:write name="datainfo" property="col1"/></td>
                          <td><bean:write name="datainfo" property="col2"/></td>
                          <td><bean:write name="datainfo" property="col3"/></td>
                          <td><bean:write name="datainfo" property="col4"/></td>
                          <td><bean:write name="datainfo" property="col5"/></td>
                          <td><bean:write name="datainfo" property="col6"/></td>
                          <td><bean:write name="datainfo" property="col7"/></td>
                          <td><bean:write name="datainfo" property="col10"/></td>
                        </tr>
                      </logic:equal>
                    </logic:iterate>
                  </logic:present>
                </tbody>
              </table>
      </div>
     	 <div class="pageContainer" >
              <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0107">
                <peis:pagination sql="AL_SYSTEM_0107" queryActionPath="/system/chooseUserAction" allowRowsChange="true"/>
              </logic:equal>
              <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0122">
                <peis:pagination sql="AL_SYSTEM_0122" queryActionPath="/system/chooseUserAction" allowRowsChange="true"/>
              </logic:equal>
              <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0123">
                <peis:pagination sql="AL_SYSTEM_0123" queryActionPath="/system/chooseUserAction" allowRowsChange="true"/>
              </logic:equal>
              <logic:equal name="chooseUserForm" property="sqlcode" value="AL_SYSTEM_0108">
                <peis:pagination sql="AL_SYSTEM_0108" queryActionPath="/system/chooseUserAction" allowRowsChange="true"/>
              </logic:equal>
            </div>
    </div>
  </div>
  </html:form>
</div>
 <html:form action="/system/groupAction" method="post" target="temp" style="display:none">
    <input type="hidden" name="userList"/>
  </html:form>
  <iframe name="temp" src="<peis:contextPath/>/jsp/system/temp.jsp" style="display: none"></iframe>
</body>
>>>>>>> .r837
</html>