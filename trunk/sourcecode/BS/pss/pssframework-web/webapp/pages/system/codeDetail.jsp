<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编码管理</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
<script type="text/javascript">
    var contextPath = "<peis:contextPath/>";    
    var errorType = '<bean:message bundle="system" key="code.errorType"/>'; 
    var errorCode = '<bean:message bundle="system" key="code.errorCode"/>';
    var errorDelCode = '<bean:message bundle="system" key="code.errorDelCode"/>';
    var confirmDel = '<bean:message bundle="system" key="code.confirmDel"/>';
    var sSelectedCodeId = "";   
    function selectRow(sCodeId, oRow) {
        sSelectedCodeId= sCodeId;
        selectSingleRow(oRow);
    }   
        
    //新增编码
    function newCode() {
        var codeCate = parent.codeList.sSelectedCodeCate;
        var codeType = parent.codeList.document.forms[0].codeType.value;
        if(codeCate == "") {
            alert(errorType);
            return;
        }
        var str_url = contextPath + "/system/codeAction.do?action=beforeEdit&codeCate=" + codeCate + "&codeType=" + codeType +"&random=" + Math.random();
        top.showDialogBox("新增编码", str_url, 215, 400);
        
    }
    
    //编辑编码
    function editCode() {
        var codeCate = parent.codeList.sSelectedCodeCate;
        if(codeCate == "") {
            alert(errorType);
            return;
        }
        if(sSelectedCodeId == "") {
            alert(errorCode);
            return;
        }
        var str_url = contextPath + "/system/codeAction.do?action=beforeEdit&code=" + sSelectedCodeId + "&codeCate=" + codeCate + "&random=" + Math.random();
        top.showDialogBox("编辑编码", str_url, 215, 400);
    }
    
    //查看编码
    function viewCode() {
        var codeCate = parent.codeList.sSelectedCodeCate;
        if(codeCate == "") {
            alert(errorType);
            return;
        }
        if(sSelectedCodeId == "") {
            alert(errorCode);
            return;
        }
        var str_url = contextPath + "/system/codeAction.do?action=beforeEdit&code=" + sSelectedCodeId + "&codeCate=" + codeCate + "&isEdit=false&random=" + Math.random();
        windowPopup(str_url, 450, 250);
    }
  
    //删除编码
    function deleteCode() {
        var codeCate = parent.codeList.sSelectedCodeCate;
        if(codeCate == "") {
            alert(errorType);
            return;
        }
        if(sSelectedCodeId == "") {
            alert(errorDelCode);
            return;
        }
        if(confirm(confirmDel)) {
            hideframe.location.href = contextPath + "/system/codeAction.do?action=delete&code=" + sSelectedCodeId + "&codeCate=" + codeCate + "&random=" + Math.random();
        }
    }
    
    function fresh(){
        parent.fresh();
    }
    </script>
</head>
<body> 
<div id="tool">
  <div class="opbutton">
    <logic:equal name="codeForm" property="codeType" value="1">
      <logic:equal name="isEdit" value="1" scope="request">
         <input type="button" id="new" class="input1" value='<bean:message bundle="system" key="button.xz"/>' onclick="newCode()" />
        <input type="button" id="edit" class="input1" value='<bean:message bundle="system" key="button.bj"/>' onclick="editCode()" />
        <input type="button" id="delete" class="input1" value='<bean:message bundle="system" key="button.sc"/>' onclick="deleteCode()" />
      </logic:equal>
    <logic:equal name="isEdit" value="1" scope="request">
      </logic:equal>
    </logic:equal>
    <logic:notEqual name="codeForm" property="codeType" value="1">
      <input type="button" id="new" class="input1" value='<bean:message bundle="system" key="button.xz"/>' onclick="newCode()" disabled />
      <input type="button" id="edit" class="input1" value='<bean:message bundle="system" key="button.bj"/>' onclick="editCode()" disabled />
      <input type="button" id="delete" class="input1" value='<bean:message bundle="system" key="button.sc"/>' onclick="deleteCode()" disabled />
    </logic:notEqual>
  </div>  
  <div class="clear"></div>
</div>
<div class="content" style="">
  <div id="cont_1">
    <div class="target3">
      <ul>
        <li class="target_on">
          <a href="#" onClick="return false;">编码值列表</a>
        </li>
        <li class="clear"></li>
      </ul>
    </div>
    <div class="graphContainer" style="height: expression((( document.documentElement.clientHeight || document.body.clientHeight)-62));">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <thead>
          <tr>
            <th>
              <bean:message bundle="system" key="code.xh" />
            </th>
            <th>
              <bean:message bundle="system" key="code.bmlb" />
            </th>
            <th>
              <bean:message bundle="system" key="code.bmz" />
            </th>
            <th>
              <bean:message bundle="system" key="code.bmsm" />
            </th>
            <th>
              <bean:message bundle="system" key="code.bz" />
            </th>
          </tr>
        </thead>       
        <tbody>
         <logic:present name="PG_QUERY_RESULT">
            <logic:iterate id="datainfo" name="PG_QUERY_RESULT" indexId="number">
              <tr align="center" class="trmainstyle" onclick="selectRow('<bean:write name="datainfo" property="col2"/>', this)" style="cursor: pointer;">
                <td>
                  <bean:write name="datainfo" property="rowNo" />
                </td>
                <td>
                  <bean:write name="datainfo" property="col1" />
                </td>
                <td>
                  <bean:write name="datainfo" property="col2" />
                </td>
                <td>
                  <bean:write name="datainfo" property="col3" />
                </td>
                <td style="width=30%">
                  <bean:write name="datainfo" property="col4" />
                </td>
              </tr>
            </logic:iterate>
          </logic:present>  
        </tbody>
      </table>
    </div>
    <div class="pageContainer" id="pageContainer">
    </div>
  </div>
</div>
<iframe name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"> </iframe>
</body>
</html>