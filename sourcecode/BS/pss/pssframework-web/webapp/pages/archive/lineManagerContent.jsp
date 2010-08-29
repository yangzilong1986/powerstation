<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>



<html>
  <head>
    <html:base />
    <title><bean:message bundle="archive" key="archive.info" />
    </title>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/dataframe.css" />
    <script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
    <script type="text/javascript"><!--
var contextPath = "<peis:contextPath/>";

var arrowUp = document.createElement("SPAN");
arrowUp.innerHTML	= "5";
arrowUp.style.cssText 	= "PADDING-RIGHT: 0px; PADDING-LEFT: 0px; FONT-SIZE: 10px; MARGIN-BOTTOM: 4px; PADDING-BOTTOM: 0px; OVERFLOW: hidden; WIDTH: 10px; COLOR: blue; PADDING-TOP: 0px; FONT-FAMILY: webdings; HEIGHT: 18px";

var arrowDown = document.createElement("SPAN");
arrowDown.innerHTML	= "6";
arrowDown.style.cssText = "PADDING-RIGHT: 0px; PADDING-LEFT: 0px; FONT-SIZE: 10px; MARGIN-BOTTOM: 4px; PADDING-BOTTOM: 0px; OVERFLOW: hidden; WIDTH: 10px; COLOR: blue; PADDING-TOP: 0px; FONT-FAMILY: webdings; HEIGHT: 18px";


//字段排序
function sort(colName) {
    var sortCol = document.getElementsByName("sortCol")[0].value;
    var sortMode = document.getElementsByName("sortMode")[0].value;
    if("" != sortCol && sortMode != "") {
        var tdObj = document.getElementById(sortCol);
        with(tdObj) removeChild(lastChild);
    }
    if(sortCol != colName) {
        sortCol = colName;
        sortMode = null;
    }
    var tdObj = document.getElementById(colName);
    var mode = arrowUp;
    if(null == sortMode || "desc" == sortMode) {
        sortMode = "asc";
        mode = arrowUp;
    }
    else if("asc" == sortMode) {
        sortMode = "desc";
        mode = arrowDown;
    }
    with(tdObj) appendChild(mode);
    document.getElementsByName("sortCol")[0].value = sortCol;
    document.getElementsByName("sortMode")[0].value = sortMode;
    document.forms[0].submit();
}

function init() {
    /*var sortCol = document.getElementsByName("sortCol")[0].value;
    var sortMode = document.getElementsByName("sortMode")[0].value;
    var tdObj = document.getElementById("sortCol");
    var mode = arrowUp;
    if("desc" == sortMode) {
        mode = arrowDown;
    }
    else if("asc" == sortMode) {
        mode = arrowUp;
    }
    with(tdObj) appendChild(mode);*/
}

function getCheckResult() {
    return getSelectedIdString(0);
}
--></script>
  </head>
  <body>
    <html:form styleId="lineQueryForm" action="/archive/lineQuery" method="post">
      <input type="hidden" name="action" value="normalMode">
      <html:hidden property="sortCol" />
      <html:hidden property="sortMode" />
      <html:hidden property="lineNo" />
      <html:hidden property="lineName" />
      <html:hidden property="orgNo" />
      <div>
        <div id="tableContainer" class="tableContainer" style="height: expression(((document.documentElement.clientHeight || document.body.clientHeight) - 36));">
          <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <thead>
              <tr>
                <!-- <th width="2%"><input type="checkbox" name="selectAll" onclick="selectAllCB()" /></th> -->
                <th width="4%"><bean:message key="global.sn" /></th>
                <th id="objectNo" onclick="sort('objectNo');" width="16%" onmouseover="this.style.cursor='pointer';">
                  <bean:message bundle="archive" key="archive.objectNo" />
                </th>
                <th id="objectName" onclick="sort('objectName');" width="20%" onmouseover="this.style.cursor='pointer';">
                  <bean:message bundle="archive" key="archive.objectName" />
                </th>
                <th id="addr" onclick="sort('addr');" width="20%" onmouseover="this.style.cursor='pointer';">
                  <bean:message bundle="archive" key="archive.addr" />
                </th>
                <th id="status" onclick="sort('status');" width="10%" onmouseover="this.style.cursor='pointer';">
                  <bean:message bundle="archive" key="archive.status" />
                </th>
                <th id="orgName" onclick="sort('orgName');" width="15%" onmouseover="this.style.cursor='pointer';">
                  <bean:message bundle="archive" key="archive.org" />
                </th>
                <th width="15%">
                  <bean:message key="global.operation" />
                </th>
              </tr>
            </thead>
            <tbody>
              <logic:present name="PG_QUERY_RESULT">
                <logic:iterate id="datainfo" name="PG_QUERY_RESULT" indexId="num">
                  <tr id="termRow<bean:write name='datainfo' property='col6'/>">
                    <!-- <td>
                      <input type="checkbox" name="ItemID" value="<bean:write name="datainfo" property="col6"/>_<bean:write name="datainfo" property="col7"/>" />
                    </td> -->
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
                    <td>
                      <bean:write name="datainfo" property="col4" />
                    </td>
                    <td>
                      <bean:write name="datainfo" property="col5" />
                    </td>
                  </tr>
                </logic:iterate>
              </logic:present>
            </tbody>
          </table>
        </div>
        <div class="pageContainer">
          <peis:paging actionForm="lineQueryForm" rowsChange="true" />
        </div>
      </div>
      <html:hidden styleId="sqlCode" property="sqlCode" />
    </html:form>
  </body>
</html>
