<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>roleList</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<script type="text/javascript">
var contextPath = "${ctx}";
var sSelectedRoleID = "";
function selectRow(sRoleID, oRow) {
    sSelectedRoleID = sRoleID;
    var roleType = document.forms[0].roleType.value;
    parent.document.frames["roleDetail"].location.href = contextPath
            + '/system/role/detail?roleId=' + sSelectedRoleID + '&roleType=' + roleType
            + '&random=' + Math.random();
    selectSingleRow(oRow);
}

//列表变更
function changeList(type) {
    if(type != 1) {
        parent.document.getElementById("new").disabled = true;
        parent.document.getElementById("edit").disabled = true;
        parent.document.getElementById("delete").disabled = true;
    }
    else {
        parent.document.getElementById("new").disabled = false;
        parent.document.getElementById("edit").disabled = false;
        parent.document.getElementById("delete").disabled = false;
    }
    document.forms[0].submit();
}

function init() {
    var tem = document.getElementById("tem");
    if(tem.value != "null" && tem.value != "") {
        selectSingleRow(document.getElementById(tem.value));
    }
    else {
        var len = document.getElementsByTagName("tr");
        if(len.length > 1) {
            var obj = len[1];
            obj.onclick();
        }
    }
}
</script>
</head>
<body onload="init()">
<div id="body">
  <html:form action="/system/roleAction">
    <input type="hidden" name="action" value="list" />
    <input type="hidden" id="tem" name="tem" value="<%=request.getParameter("id")%>" />
  </html:form>
  <div class="content">
    <div id="cont_1">
      <div id="tableContainer" class="tableContainer" style="height: expression(((document.documentElement.clientHeight||document.body.clientHeight)-34));">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <thead>
            <tr>
              <th><bean:message bundle="system" key="role.xh" /></th>
              <th><bean:message bundle="system" key="role.jsmc" /></th>
            </tr>
          </thead>
          <logic:present name="PG_QUERY_RESULT">
            <logic:iterate id="datainfo" name="PG_QUERY_RESULT" indexId="number">
              <tr id="<bean:write name="datainfo" property="col1"/>" align="center" class="trmainstyle" onclick="selectRow('<bean:write name="datainfo" property="col1"/>', this)" style="cursor: pointer;">
                <td><bean:write name="datainfo" property="rowNo" /></td>
                <td><bean:write name="datainfo" property="col2" /></td>
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
