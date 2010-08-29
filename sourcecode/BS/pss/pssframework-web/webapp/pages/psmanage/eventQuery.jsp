<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<title>跳闸信息</title>
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
        <th sortColumn="trigTime">跳闸时间</th>
        <th sortColumn="eventName">跳闸类型</th>
        <th sortColumn="phase">故障相位</th>
        <th sortColumn="closed">合闸状态</th>
        <th sortColumn="locked">锁死状态</th>
        <th sortColumn="currentValue">跳闸电参数值</th>
      </tr>
    </thead>
    <tbody class="tableBody" id="dataBody">
      <c:forEach items="${page.result}" var="item" varStatus="status">
        <tr class="${status.count % 2 == 0 ? 'odd' : 'even'}">
          <td height="20">${page.thisPageFirstElementNumber + status.index}</td>
          <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.trigTime}"></fmt:formatDate> &nbsp;</td>
          <td><c:out value='${item.eventName}' />&nbsp;</td>
          <td><c:out value='${item.phase}' />&nbsp;</td>
          <td><c:if test="${item.closed eq 1}">合闸</c:if><c:if test="${item.closed eq 0}">分闸</c:if>&nbsp;</td>
          <td><c:if test="${item.locked eq 1}">锁死</c:if><c:if test="${item.locked eq 0}">未锁死</c:if>&nbsp;</td>
          <td><c:out value='${item.currentValue}' />&nbsp;</td>
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