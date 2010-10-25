<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<title>试跳记录信息</title>
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
        <th sortColumn="LOGICAL_ADDR">逻辑地址</th>
        <th sortColumn="assetNo">资产编号</th>
        <th sortColumn="DDATE">试跳日期</th>
        <th sortColumn="post_time">下发时间</th>
        <th sortColumn="accept_time">接收时间</th>
        <th sortColumn="TRIP_RESULT">试跳结果</th>
      </tr>
    </thead>
    <tbody class="tableBody" id="dataBody">
      <c:forEach items="${page.result}" var="item" varStatus="status">
        <tr class="${status.count % 2 == 0 ? 'odd' : 'even'}">
          <td>${page.thisPageFirstElementNumber + status.index}</td>
          <td><c:out value='${item.LOGICAL_ADDR}' />&nbsp;</td>
          <td><c:out value='${item.assetNo}' />&nbsp;</td>
          <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.DDATE}"></fmt:formatDate> &nbsp;</td>
          <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.post_time}"></fmt:formatDate> &nbsp;</td>
          <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.accept_time}"></fmt:formatDate> &nbsp;</td>
          <td><c:if test="${item.closed eq 0}"><c:out value='${item.TRIP_RESULT}' /></c:if>&nbsp;</td>
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