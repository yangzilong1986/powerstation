<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
<html>
  <head>
    <html:base />
    <title><bean:message bundle="archive" key="archive.info" /></title>
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

//显示编辑页面前的详细信息
function showDetail(id, type) {
    if(type == "DY") {
        var winFeatures = "dialogWidth:900px;dialogHeight:600px;";
        var str_url = contextPath + "/archive/customerAction.do";
        str_url += "?action=detail&custId=" + id;
        str_url += "&rand=" + Math.random();
        windowPopup1(str_url, 900, 600);
    }
    else if(type == "TG") {
        var str_url = contextPath + "/jsp/archive/tg_frame.jsp?TG_ID=" + id;
        windowPopup1(str_url, 900, 600);
    }
    else {
        var str_url = contextPath + "/archive/customerAction.do";
        str_url += "?action=archiveFrame";
        str_url += "&id=" + id + "&type=" + type;
        str_url += "&rand=" +Math.random();
        windowPopup1(str_url, 900, 600);
    }
}

//删除对象
function delObject(id, type) {
    if(type == "CUST") {
    	/*var idArray = new Array();
    	idArray[0] = id;
    	cust.queryDeviceByBigCust(id, function(data){
    		if(data.length!=0){
    			if(confirm("<bean:message bundle="archive" key="archive.confirm_delete"/>")){
    			parent.archivepage.location.href = contextPath + "/archive/customerAction.do?action=delete&custId="+Id;
    			}
    		}
    		else{
    			if(confirm("<bean:message bundle="archive" key="archive.confirm_delete"/>")){
    				var url = contextPath + "/archive/customerAction.do?action=delete&custId="+Id;
    				parent.archivepage.location.href = contextPath + "/archive/customerAction.do?action=delete&custId="+Id;
    			
    			}
    		}
    	})*/
        var params = {"action": "deleteByAjax",
                      "custId": id};
        var url = contextPath + "/archive/customerAction.do";
        $.ajax({
            type: "POST",
            url: url,
            cache: false,
            data: params,
            success: function(data) {
                var result = data.split("#")[0];
                var message = data.split("#")[1];
                alert(message);
                if(result == "1") {     // 删除成功
                    $("#archiveQueryForm").submit();
                }
            }
        });
    }
}

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
  <body onload="init();">
    <html:form styleId="archiveQueryForm" action="/archive/archiveQuery" method="post">
      <input type="hidden" name="action" value="normalMode">
      <html:hidden property="sortCol" />
      <html:hidden property="sortMode" />
      <html:hidden property="objectId" />
      <html:hidden property="objectType" />
      <div>
        <div id="tableContainer" class="tableContainer" style="height: expression(((document.documentElement.clientHeight || document.body.clientHeight) - 36));">
          <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <thead>
              <tr>
                <!-- <th width="2%"><input type="checkbox" name="selectAll" onclick="selectAllCB()" /></th> -->
                <th width="4%"><bean:message key="global.sn" /></th>
                <th id="objectNo" onclick="sort('objectNo');" width="16%" onmouseover="this.style.cursor='pointer';">
                  户号
                </th>
                <th id="objectName" onclick="sort('objectName');" width="20%" onmouseover="this.style.cursor='pointer';">
                  户名 
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
                    <td>
                      <a href="javascript:showDetail('<bean:write name="datainfo" property="col6"/>','<bean:write name="datainfo" property="col7"/>');"><bean:message key="global.edit" /></a>
                      &nbsp;|&nbsp;
                      <a href="javascript:delObject('<bean:write name="datainfo" property="col6"/>','<bean:write name="datainfo" property="col7"/>')"><bean:message key="global.delete" /></a>
                    </td>
                  </tr>
                </logic:iterate>
              </logic:present>
            </tbody>
          </table>
        </div>
        <div class="pageContainer">
          <peis:paging actionForm="archiveQueryForm" rowsChange="true" />
        </div>
      </div>
      <html:hidden styleId="sqlCode" property="sqlCode" />
    </html:form>
  </body>
</html>
