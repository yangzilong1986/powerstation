<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>codeList.jsp</title>
    <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
    <script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
    <script type="text/javascript">
	var contextPath = "<peis:contextPath/>";		
	var sSelectedCodeCate = "";   
	function selectRow(sCodeCate, oRow) {
  		sSelectedCodeCate = sCodeCate;
  		var codeType=document.forms[0].codeType.value;
  		parent.document.frames["codeManager"].location.href = contextPath + '/system/codeAction.do?action=detail&codeCate=' + sSelectedCodeCate + '&codeType=' + codeType + '&random=' + Math.random();
        selectSingleRow(oRow);
	}

    //列表变更
	function changeList(type) {
        if(parent.document.frames["codeManager"].document.getElementById("new") != null) {
            if(type != 1) {
                parent.document.frames["codeManager"].document.getElementById("new").disabled = true;
                parent.document.frames["codeManager"].document.getElementById("edit").disabled = true;
                parent.document.frames["codeManager"].document.getElementById("delete").disabled = true;
            }
            else {
                parent.document.frames["codeManager"].document.getElementById("new").disabled = false;
                parent.document.frames["codeManager"].document.getElementById("edit").disabled = false;
                parent.document.frames["codeManager"].document.getElementById("delete").disabled = false;
            }
        }
        document.forms[0].submit();
	}
  
    function init(){
        var len = document.getElementsByTagName("tr");
        var obj = len[2];
        obj.onclick();
    }
 	</script>
  </head>

  <body onload="init();">
    <div id="body">
      <div id="tool">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="66" class="label">
              <bean:message bundle="system" key="code.bmlb" />：
            </td>
            <td width="120" class="dom">
              <html:form action="/system/codeAction">
                <input type="hidden" name="action" value="getQuery" />
                <html:select property="codeType" onchange="changeList(this.options[this.options.selectedIndex].value)">
                  <html:option value="0">
                    <bean:message bundle="system" key="code.xtbm" />
                  </html:option>
                  <html:option value="1">
                    <bean:message bundle="system" key="code.yhbm" />
                  </html:option>
                </html:select>
              </html:form>
            </td>
          </tr>
        </table>
        <div class="clear"></div>
      </div>
      <div class="content">
        <div id="cont_1">
          <div id="tableContainer" class="tableContainer"
            style="height: expression(((document.documentElement.clientHeight || document.body.clientHeight) - 38));">
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
                    <bean:message bundle="system" key="code.bmlbsm" />
                  </th>
                </tr>
              </thead>
              <logic:present name="PG_QUERY_RESULT">
                <logic:iterate id="datainfo" name="PG_QUERY_RESULT" indexId="number">
                  <tr align="center" class="trmainstyle" onclick="selectRow('<bean:write name="datainfo" property="col1"/>', this)" style="cursor: pointer;">
                    <td>
                      <bean:write name="datainfo" property="rowNo" />
                    </td>
                    <td>
                      <bean:write name="datainfo" property="col1" />
                    </td>
                    <td style="width=50%">
                      <bean:write name="datainfo" property="col2" />
                    </td>
                  </tr>
                </logic:iterate>
              </logic:present>
            </table>
          </div>
          <div class="pageContainer" id="pageContainer">
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
