<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>



<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title><bean:message bundle="gather" key="gather.task.title.dataItem"/></title>
    <script type="text/javascript" src="<peis:contextPath/>/js/task.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
    <link href="<peis:contextPath/>/css/window.css" rel="stylesheet"type="text/css">
    <script type="text/javascript">
    var choosedItems = "";
    choosedItems = '<bean:write name="choosed" scope="request"/>';

    // 数据项设定
    function setDataItem() {
        var dataitem_codes = "";
        var dataitem_names = "";
        var items = document.getElementsByName("choose");
        var tableObj = document.getElementById("dataItems");
        var dataItemTable = top.getMainFrameObj().taskoperate.document.all.dataItem;
        while (dataItemTable.rows.length > 1) {
            dataItemTable.deleteRow(1);
        }
        // 叠加选择的数据项信息
        index = 1;
        for (i = 0; i < items.length; i++) {
            if (items[i].checked) {
                // 将选择的数据项设置到前台显示
                var tr = dataItemTable.insertRow();
                $(tr).click(function(){top.getMainFrameObj().taskoperate.selectSingleRow(this);});
                $(tr).mouseover(function(){this.style.cursor = "pointer";});
                tr.insertCell().innerHTML = index++;
                tr.insertCell().innerHTML = tableObj.rows[i + 1].cells[1].innerHTML;
                tr.insertCell().innerHTML = tableObj.rows[i + 1].cells[2].innerHTML;
            }
        }
        top.getMainFrameObj().taskoperate.setDataItemCode();
        parent.GB_hide();
    }
    </script>
  </head>
  
  <body onload="dataItemInit()">
    <div id="body">
      <div class="tab"><em><bean:message bundle="gather" key="gather.task.title.dataItem"/></em></div>
      <div id="main" style="width: expression(((document.documentElement.clientWidth || document.body.clientWidth) - 18));">
        <div id="tool">
          <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
              <td width="70" class="label"><bean:message bundle="gather" key="gather.task.lable.protocol_type"/>：</td>
              <td width="150" class="dom">
                <peis:selectlist name="protocol_type" sql="GATHER0002"/>
              </td> 
              <td width="70" class="label"><bean:message bundle="gather" key="gather.task.lable.datatype"/>：</td>
              <td width="120" class="dom">
                <logic:present name="dataTypeSql">
                  <%  String dataTypeSql = (String)request.getAttribute("dataTypeSql");
                      dataTypeSql = (dataTypeSql == null ? "AL_AUTORM_0006" : dataTypeSql);
                  %>
                  <peis:selectlist name="dataType" sql="<%=dataTypeSql%>"/>
                </logic:present> 
                <logic:notPresent name="dataTypeSql">
                  <peis:selectlist name="dataType" sql="AL_AUTORM_0006"/>
                </logic:notPresent>
              </td>
              <td colspan="4"></td>
              <td class="functionLabe2" width="150" align="right">
                <input type="button" class="input1" onclick="setDataItem()" value="<bean:message bundle="gather" key="gather.task.button.submit"/>"/>
              </td>
            </tr>
          </table>
        </div>
        <div class="content">
          <div id="cont_1">
            <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 152));">
              <table id="dataItems" border="0" cellpadding="0" cellspacing="0" width="100%">
                <thead>
                  <tr>
                    <th width="5%"><bean:message bundle="gather" key="gather.task.table.th.rowno"/></th>
                    <th width="20%"><bean:message bundle="gather" key="gather.task.table.th.dataitemcode"/></th>
                    <th width="70%"><bean:message bundle="gather" key="gather.task.table.th.dataitemname"/></th>
                    <th width="5%"><input type="checkbox" name="chooseAllItem" onclick="chooseAll()"/></th>
                  </tr>
                </thead>
                <tbody align="center">
                  <logic:present name="allDataItem">
                    <logic:iterate id="datainfo" name="allDataItem">
                      <tr>
                        <td><bean:write name="datainfo" property="rowNo"/></td>
                        <td><bean:write name="datainfo" property="col1"/></td>
                        <td><bean:write name="datainfo" property="col2"/></td>
                        <td><input type="checkbox" name="choose"/></td>
                      </tr>
                    </logic:iterate>
                  </logic:present>
                </tbody>
              </table>
            </div>
            <div class="nullContainer">
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
