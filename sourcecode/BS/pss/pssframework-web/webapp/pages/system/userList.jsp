<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>userList</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEX.js"></script>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
var sSelectedUserID = "";
function selectRow(sUserID, oRow) {
    sSelectedUserID = sUserID;
    parent.document.frames["userManager"].location.href = contextPath + '/system/userAction.do?action=detail&userId=' + sSelectedUserID + '&random=' + Math.random();
    selectSingleRow(oRow);
}

//列表变更
function changeList() {
    $("#orgNo").attr("disabled", false);
    document.forms[0].submit();
}

function changeOrg() {
    $("#orgNo").attr("disabled", false);
    document.forms[0].submit();
}

function init() {
    if('<peis:param type="PAGE" paramName="isAll"/>' == 'true') {
        $("#orgNo").attr("disabled", true);
    }
    else {
        $("#orgNo").attr("disabled", false);
    }
    var tem = document.getElementById("tem");
    if(tem.value != "null" && tem.value != "") {
        selectSingleRow(document.getElementById(tem.value));
    }
    else {
        var len = $(".tableContainer table>tbody>tr");
        if(len.length > 1) {
            var obj = len[0];
            obj.onclick();
        }
    }
}
</script>
</head>
<body onload="init()">
<div id="body">
  <html:form action="/system/userAction">
  <input type="hidden" name="action" value="getQuery" />
  <input type="hidden" id="tem" name="tem" value="<%=request.getParameter("id")%>" />
  <div id="tool">
    <table border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td><bean:message bundle="system" key="user.ssdw" />：</td>
        <td>
          <input type="hidden" name="action" value="getQuery" />
          <html:hidden property="sqlcode" />
          <peis:selectlist styleId="orgNo" name="orgNo" sql="COMMON0029" extendProperty="class='mainSelect'" onChange="changeOrg()" />
        </td>
        <td colspan="2"><html:checkbox name="userForm" property="isAll" value="1" onclick="changeList()" />显示全部</td>
      </tr>
    </table>
    <div class="clear"></div>
  </div>
  </html:form>
  <div class="content">
    <div id="cont_1">
      <div id="tableContainer" class="tableContainer" style="height: expression(((document.documentElement.clientHeight||document.body.clientHeight)-64));">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <thead>
            <tr>
              <th><bean:message bundle="system" key="user.xh" /></th>
              <th><bean:message bundle="system" key="user.zh" /></th>
              <th><bean:message bundle="system" key="user.mc" /></th>
            </tr>
          </thead>
          <logic:present name="PG_QUERY_RESULT">
            <logic:iterate id="datainfo" name="PG_QUERY_RESULT" indexId="number">
              <tr id="<bean:write name="datainfo" property="col1"/>" onclick="selectRow('<bean:write name="datainfo" property="col1"/>', this)" style="cursor: pointer;">
                <td><bean:write name="datainfo" property="rowNo" /></td>
                <td><bean:write name="datainfo" property="col2" /></td>
                <td><bean:write name="datainfo" property="col3" /></td>
              </tr>
            </logic:iterate>
          </logic:present>
        </table>
      </div>
      <div class="nullContainer"></div>
    </div>
  </div>
</div>
</body>
</html>
