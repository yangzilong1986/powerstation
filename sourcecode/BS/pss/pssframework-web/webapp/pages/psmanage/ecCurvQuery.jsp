<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<title>漏保电压电流曲线</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
<div class="tableContainer" style="height: expression(((document.documentElement.clientHeight ||document.body.clientHeight)-31));">
<form:form action="${ctx}/psmanage/psmon/eventQuery" modelAttribute="psMonitorQuery">
  <input type="hidden" id="psId" name="psId" value="${psMonitorQuery.psId}" />
  <input type="hidden" id="ddate" name="ddate" value="${psMonitorQuery.ddate}" />
  <table width="100%" border="0" cellspacing="0" class="gridBody" id="object_table">
    <thead class="tableHeader">
      <tr>
        <th>序号</th>
        <th sortColumn="dataTime">数据时间</th>
        <th sortColumn="voltA">A相电压</th>
        <th sortColumn="voltB">B相电压</th>
        <th sortColumn="voltC">C相电压</th>
        <th sortColumn="ecurA">A相电流</th>
        <th sortColumn="ecurB">B相电流</th>
        <th sortColumn="ecurC">C相电流</th>
        <th sortColumn="ecurS">剩余电流</th>
      </tr>
    </thead>
    <tbody class="tableBody" id="dataBody">
      <c:forEach items="${page.result}" var="item" varStatus="status">
        <tr class="${status.count % 2 == 0 ? 'odd' : 'even'}">
          <td>${page.thisPageFirstElementNumber + status.index}</td>
          <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.dataTime}"></fmt:formatDate> &nbsp;</td>
          <td><c:out value='${item.voltA}' />&nbsp;</td>
          <td><c:out value='${item.voltB}' />&nbsp;</td>
          <td><c:out value='${item.voltC}' />&nbsp;</td>
          <td><c:out value='${item.ecurA}' />&nbsp;</td>
          <td><c:out value='${item.ecurB}' />&nbsp;</td>
          <td><c:out value='${item.ecurC}' />&nbsp;</td>
          <td><c:out value='${item.ecurS}' />&nbsp;</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</form:form>
</div>
<div style="height: 30px; background: #DBEAEB; vertical-align: middle; text-align: right; border-left: 1px #85C0B4 solid; border-right: 1px #85C0B4 solid; border-bottom: 1px #85C0B4 solid;"><simpletable:pageToolbar page="${page}"></simpletable:pageToolbar></div>
</body>
<script type="text/javascript">
$(document).ready(function() {
  // 分页需要依赖的初始化动作
  window.simpleTable = new SimpleTable('psMonitorQuery','${page.thisPageNumber}','${page.pageSize}','${pageRequest.sortColumns}');
});
</script>
</html>