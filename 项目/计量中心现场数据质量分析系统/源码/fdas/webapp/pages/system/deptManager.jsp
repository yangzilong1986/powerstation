<%@ page language="java" pageEncoding="UTF-8"%>

<%@include file="../../commons/taglibs.jsp"%>


<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title><bean:message bundle="system" key="dept.manage.title" /></title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
var errorDept = '<bean:message bundle="system" key="dept.errorDept"/>';
var sSelectedOrgID = "";
function selectRow(sOrgID, oRow) {
    sSelectedOrgID = sOrgID;
    selectSingleRow(oRow);
}

//新增供电单位
function newOrg() {
    var selectId = parent.document.frames("DTree").selectId;
    var str_url = contextPath + "/system/orgAction.do?action=beforeEdit&orgNo=" + selectId + "&random=" + Math.random();
    top.showDialogBox("新增供电单位", str_url, 495, 800);
}

function fresh(orgNo) {
    parent.document.frames["dept"].location.href = contextPath + '/system/orgAction.do?action=getQuery&orgNo=' + orgNo + '&sqlcode=COMMON0030&random=' + Math.random();
    parent.document.frames("DTree").location = contextPath + '/windowTreeAction.do?action=getTree&WINDOW=TREE&SMXBH=99991001&objectType=10&queryFunction=query()&typicalType=1&limited=false&limitedtype=null';
}

//编辑供电单位
function editOrg() {
    if(sSelectedOrgID == "") {
        sSelectedOrgID = parent.document.frames("DTree").selectId;
    }
    if(sSelectedOrgID == "" || sSelectedOrgID == null) {
        alert(errorDept);
        return;
    }
    var str_url = contextPath + "/system/orgAction.do?action=beforeEdit&deptID=" + sSelectedOrgID + "&random=" + Math.random();
    top.showDialogBox("编辑供电单位", str_url, 495, 800);
}
</script>
</head>
<body>
<div id="main">
  <div class="content">
    <div id="cont_3">
      <div class="target3">
        <ul>
          <li class="target_on">&nbsp;<bean:message bundle="system" key="dept.dwxxxx" />&nbsp;</li>
          <li class="clear"></li>
        </ul>
      </div>
      <div id="tool">
        <table align="center">
          <tr>
            <td width="13%"><bean:message bundle="system" key="dept.dwbh" />：</td>
            <td width="38%"><bean:write name="orgForm" property="orgNo" /></td>
            <td width="13%"><bean:message bundle="system" key="dept.dwmc" />：</td>
            <td width="38%"><bean:write name="orgForm" property="orgName" /></td>
          </tr>
          <tr>
            <td><bean:message bundle="system" key="dept.dwlx" />：</td>
            <td><bean:write name="orgForm" property="orgTypeName" /></td>
            <td><bean:message bundle="system" key="dept.sjdwmc" />：</td>
            <td><bean:write name="orgForm" property="upOrgName" /></td>
          </tr>
          <tr>
            <td><bean:message bundle="system" key="dept.sjdwlx" />：</td>
            <td><bean:write name="orgForm" property="upOrgTypeName" /></td>
            <td><bean:message bundle="system" key="dept.pxh" />：</td>
            <td><bean:write name="orgForm" property="orgSort" /></td>
          </tr>
        </table>
      </div>
    </div>
    <div id="cont_3">
      <div class="target5">
        <ul>
          <li class="target_on">&nbsp;<bean:message bundle="system" key="dept.bdwjqxjdw" />&nbsp;</li>
          <li class="clear"></li>
        </ul>
      </div>
      <div id="tool">
        <logic:equal name="isEdit" value="1" scope="request">
          <div class="t_botton">
            <div class="t_left">
              <input type="button" class="input1" name="add" value='<bean:message bundle="system" key="button.xz"/>' onclick="newOrg()" />
              <input type="button" class="input1" name="edit" value='<bean:message bundle="system" key="button.bj"/>' onclick="editOrg()" />
            </div>
            <div class="clear"></div>
          </div>
        </logic:equal>
      </div>
      <div id="tableContainer" class="tableContainer" style="height: expression((( document.documentElement.clientHeight||document.body.clientHeight)-213));">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <thead>
            <tr>
              <th><bean:message bundle="system" key="dept.xh" /></th>
              <th><bean:message bundle="system" key="dept.dwbh" /></th>
              <th><bean:message bundle="system" key="dept.dwmc" /></th>
              <th><bean:message bundle="system" key="dept.dwlx" /></th>
              <th><bean:message bundle="system" key="dept.sjdw" /></th>
            </tr>
          </thead>
          <logic:present name="PG_QUERY_RESULT">
            <logic:iterate id="datainfo" name="PG_QUERY_RESULT" indexId="number">
              <tr align="center" onclick="selectRow('<bean:write name="datainfo" property="col1"/>', this)" style="cursor: pointer;">
                <td><bean:write name="datainfo" property="rowNo" /></td>
                <td><bean:write name="datainfo" property="col1" /></td>
                <td><bean:write name="datainfo" property="col2" /></td>
                <td><bean:write name="datainfo" property="col4" /></td>
                <td><bean:write name="datainfo" property="col6" /></td>
              </tr>
            </logic:iterate>
          </logic:present>
        </table>
      </div>
    </div>
  </div>
</div>
</body>
</html>
