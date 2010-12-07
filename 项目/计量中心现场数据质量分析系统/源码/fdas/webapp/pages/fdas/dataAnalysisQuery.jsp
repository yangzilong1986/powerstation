<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<title>数据分析</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
<div class="tableContainer" style="height: expression(((document.documentElement.clientHeight ||document.body.clientHeight)-29));">
<form:form action="${ctx}/fdas/dataAnalysisQuery/query" modelAttribute="statisticsQuery">
  <form:hidden path="sdate" />
  <form:hidden path="edate" />
  <table width="100%" border="0" cellspacing="0" class="gridBody" id="object_table">
    <thead class="tableHeader">
      <tr>
        <th>序号</th>
        <th sortColumn="dbjh">电表局号</th>
        <th sortColumn="xiangxian">相线</th>
        <th sortColumn="jyrq">外校检验日期</th>
        <th sortColumn="jysbbm">外校设备编码</th>
        <th sortColumn="axdl">A相电流</th>
        <th sortColumn="bxdl">B相电流</th>
        <th sortColumn="cxdl">C相电流</th>
        <th sortColumn="glys">功率因数</th>
        <th sortColumn="pjwc">平均误差</th>
        <th sortColumn="njwc">内校误差</th>
      </tr>
    </thead>
    <tbody class="tableBody" id="dataBody">
      <c:forEach items="${page.result}" var="item" varStatus="status">
        <tr class="${status.count % 2 == 0 ? 'odd' : 'even'}">
          <td>${page.thisPageFirstElementNumber + status.index}</td>
          <td><c:out value='${item.dbjh}' />&nbsp;</td>
          <td><c:if test="${item.xiangxian eq 2}">单相</c:if><c:if test="${item.xiangxian eq 3}">三相三线</c:if><c:if test="${item.xiangxian eq 4}">三相四线</c:if>&nbsp;</td>
          <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.jyrq}"></fmt:formatDate> &nbsp;</td>
          <td><c:out value='${item.jysbbm}' />&nbsp;</td>
          <td><c:out value='${item.axdl}' />&nbsp;</td>
          <td><c:out value='${item.bxdl}' />&nbsp;</td>
          <td><c:out value='${item.cxdl}' />&nbsp;</td>
          <td><c:out value='${item.glys}' />&nbsp;</td>
          <td><c:out value='${item.pjwc}' />&nbsp;</td>
          <td><c:out value='${item.njwc}' />&nbsp;</td>
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