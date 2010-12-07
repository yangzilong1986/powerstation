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
<div class="tableContainer" style="height: expression(((document.documentElement.clientHeight ||document.body.clientHeight)-29));">
<form:form action="${ctx}/statistics/psEventQuery/event" modelAttribute="statisticsQuery">
  <form:hidden path="tgId" />
  <form:hidden path="orgId" />
  <form:hidden path="sdate" />
  <form:hidden path="edate" />
  <table width="100%" border="0" cellspacing="0" class="gridBody" id="object_table">
    <thead class="tableHeader">
      <tr>
        <th>序号</th>
        <th sortColumn="tgName">台区</th>
        <th sortColumn="assetNo">资产编号</th>
        <th sortColumn="trigTime">跳闸时间</th>
        <th sortColumn="eventName">动作类型</th>
        <th sortColumn="phase">故障相位</th>
        <th sortColumn="closed">开关状态</th>
        <th sortColumn="locked">是否锁死</th>
        <th sortColumn="currentValue">动作值</th>
      </tr>
    </thead>
    <tbody class="tableBody" id="dataBody">
      <c:forEach items="${page.result}" var="item" varStatus="status">
        <tr class="${status.count % 2 == 0 ? 'odd' : 'even'}">
          <td>${page.thisPageFirstElementNumber + status.index}</td>
          <td><c:out value='${item.tgName}' />&nbsp;</td>
          <td><c:out value='${item.assetNo}' />&nbsp;</td>
          <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.trigTime}"></fmt:formatDate> &nbsp;</td>
          <td><c:if test="${item.closed eq 1}">合闸成功</c:if><c:if test="${item.closed eq 0}"><c:out value='${item.eventName}' /></c:if>&nbsp;</td>
          <td><c:if test="${item.closed eq 0}"><c:out value='${item.phase}' /></c:if>&nbsp;</td>
          <td><c:if test="${item.closed eq 1}">合闸</c:if><c:if test="${item.closed eq 0}">分闸</c:if>&nbsp;</td>
          <td><c:if test="${item.closed eq 0}"><c:if test="${item.locked eq 1}">锁死</c:if><c:if test="${item.locked eq 0}">未锁死</c:if></c:if>&nbsp;</td>
          <td><c:if test="${item.closed eq 0}"><c:out value='${item.currentValue}' /></c:if>&nbsp;</td>
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
  window.simpleTable = new SimpleTable('statisticsQuery','${page.thisPageNumber}','${page.pageSize}','${pageRequest.sortColumns}');
});
</script>
</html>