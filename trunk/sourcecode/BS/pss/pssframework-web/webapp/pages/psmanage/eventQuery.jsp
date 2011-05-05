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
<div id="tbl-container" style="height: expression(((document.documentElement.clientHeight ||document.body.clientHeight)-29));">
<form:form action="${ctx}/psmanage/psmon/eventQuery" modelAttribute="psMonitorQuery">
  <input type="hidden" id="psId" name="psId" value="${psMonitorQuery.psId}" />
  <input type="hidden" id="sdate" name="sdate" value="${psMonitorQuery.sdate}" />
  <input type="hidden" id="edate" name="edate" value="${psMonitorQuery.edate}" />
  <table width="100%" border="0" cellspacing="0" class="gridBody" id="object_table">
    <thead class="tableHeader">
      <tr>
        <th>序号</th>
        <th sortColumn="trigTime">跳闸时间</th>
        <th sortColumn="eventName">动作类型</th>
        <th sortColumn="phase">故障相位</th>
        <th sortColumn="closed">开关状态</th>
        <th sortColumn="locked">是否闭锁</th>
        <th sortColumn="currentValue">动作值</th>
      </tr>
    </thead>
    <tbody class="tableBody" id="dataBody">
      <c:forEach items="${page.result}" var="item" varStatus="status">
        <tr class="${status.count % 2 == 0 ? 'odd' : 'even'}">
          <td>${page.thisPageFirstElementNumber + status.index}</td>
          <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.trigTime}"></fmt:formatDate> &nbsp;</td>
          <td>${item.eventName}&nbsp;</td>
          <td>${item.phase}&nbsp;</td>
          <td>${item.closed}&nbsp;</td>
          <td>${item.locked}&nbsp;</td>
          <td>${item.currentValue}&nbsp;</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</form:form>
</div>
<div><simpletable:pageToolbar page="${page}"></simpletable:pageToolbar></div>
</body>
<script type="text/javascript">
$(document).ready(function() {
    // 分页需要依赖的初始化动作
    window.simpleTable = new SimpleTable('psMonitorQuery','${page.thisPageNumber}','${page.pageSize}','${pageRequest.sortColumns}');
});
</script>
</html>