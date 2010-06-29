<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<%@include file="../common/blockloading.jsp"%>


<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="<peis:contextPath/>/css/mainframe.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">
    function selectRow(taskId) {
        var protocol_type = document.all.protocol_type.value;
        window.parent.showTask(taskId, protocol_type);
    }
    </script>
  </head>
  <body>
    <html:form action="/autorm/taskQueryAction" method="get">
    <html:hidden property="action" value="taskList"/>
	<html:hidden property="pageType" value="page"/>
    <html:hidden property="sqlcode" value="GATHER0001"/>
    <div>
      <div id="tool">
        <table border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="66" class="label"><bean:message bundle="gather" key="gather.task.lable.protocol_type"/>：</td>
            <td width="120" class="dom"><peis:selectlist name="protocol_type" sql="AL_TASKSET_1006"/></td>
            <td width="66" class="label"><bean:message bundle="gather" key="gather.task.lable.task_type"/>：</td>
            <td width="120" class="dom"><peis:selectlist name="task_type" sql="GATHER0003"/></td>
          </tr>
        </table>
        <div class="t_botton">
          <div class="t_left">
          </div>
          <div class="t_right">
            <html:submit property="to_submit" styleClass="input1" ><bean:message bundle="gather" key="gather.task.button.query"/></html:submit>
          </div>
          <div class="clear"></div>
        </div>
      </div>
      <div class="content">
        <div id="cont_1">
          <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 90));">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <thead><tr>
                <th width="10%"><bean:message bundle="gather" key="gather.task.table.th.rowno"/></th>
  				<th width="20%"><peis:pagehead actionForm="taskQueryForm" styleClass="order" function="orderBy" href="" orderBy="TASK_ID" title="任务号"></peis:pagehead></th>
  				<th width="45%"><peis:pagehead actionForm="taskQueryForm" styleClass="order" function="orderBy" href="" orderBy="TASK_NAME" title="任务名称"></peis:pagehead></th>
  				<th width="25%"><peis:pagehead actionForm="taskQueryForm" styleClass="order" function="orderBy" href="" orderBy="NAME" title="数据类型"></peis:pagehead></th>
              </tr></thead>
              <tbody align="center">
              <!-- <logic:present name="PG_QUERY_RESULT">
                <logic:iterate id="datainfo" name="PG_QUERY_RESULT">
                  <tr onclick="selectSingleRow(this);selectRow('<bean:write name="datainfo" property="col1"/>')" onmouseover="if('<bean:write name="datainfo" property="col1"/>' != '') this.style.cursor='pointer';">
                    <td><bean:write name="datainfo" property="rowNo"/></td>
                    <td><bean:write name="datainfo" property="col1"/></td>
                    <td><bean:write name="datainfo" property="col2"/></td>
                    <td><bean:write name="datainfo" property="col3"/></td>
                  </tr>
                </logic:iterate>
                </logic:present>  -->
                <!-- 增加排序功能 -->
                <c:forEach items="${PG_QUERY_RESULT}" var="item" varStatus="status">
                   <tr onclick="selectSingleRow(this);selectRow('${item.TASK_ID}')" onmouseover="if('${item.TASK_ID}' != '') this.style.cursor='pointer';">
                       <td><c:out value="${status.count}"/></td>
                       <td><c:out value="${item.TASK_ID}"/></td>
                       <td><c:out value="${item.TASK_NAME}"/></td>
                       <td><c:out value="${item.NAME}"/></td>
                   </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
          <div class="pageContainer">
           <peis:pagebar actionForm="taskQueryForm" url="/autorm/taskQueryAction.do?action=taskList" rowsChange="true" export="导出excel的名字"/>
          </div>
        </div>
      </div>
    </div>
    </html:form>
  </body>
  <%@include file="../common/blockendloading.jsp"%>
</html>
