<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<title>电流越限数据</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
<div class="tableContainer" style="width: 100%; height: expression(((document.documentElement.clientHeight ||document.body.clientHeight)-29));">
<form:form action="${ctx}/statistics/ecurStatisDay" modelAttribute="statisticsQuery">
<form:hidden path="tgId"/>
<form:hidden path="orgId"/>
<form:hidden path="ddate"/>
<table width="3000" border="0" cellspacing="0" class="gridBody" id="object_table">
  <thead class="tableHeader">
    <tr>
      <th>序号</th>
      <th sortColumn="assetNo">资产编号</th>
      <th sortColumn="dataTime">数据时间</th>
      <th sortColumn="ecurOverUpuplimitTimeA">A相电流越上上限累计时间</th>
      <th sortColumn="ecurOverUplimitTimeA">A相电流越上限累计时间</th>
      <th sortColumn="ecurOverUpuplimitTimeB">B相电流越上上限累计时间</th>
      <th sortColumn="ecurOverUplimitTimeB">B相电流越上限累计时间</th>
      <th sortColumn="ecurOverUpuplimitTimeC">C相电流越上上限累计时间</th>
      <th sortColumn="ecurOverUplimitTimeC">C相电流越上限累计时间</th>
      <th sortColumn="ecurOverUplimitTimeO">零序电流越上限累计时间</th>
      <th sortColumn="ecurPeakA">A相电流最大值</th>
      <th sortColumn="ecurPeakATime">A相电流最大值发生时间</th>
      <th sortColumn="ecurPeakB">B相电流最大值</th>
      <th sortColumn="ecurPeakBTime">B相电流最大值发生时间</th>
      <th sortColumn="ecurPeakC">C相电流最大值</th>
      <th sortColumn="ecurPeakCTime">C相电流最大值发生时间</th>
      <th sortColumn="ecurPeakO">零序电流最大值</th>
      <th sortColumn="ecurPeakOTime">零序电流最大值发生时间</th>
    </tr>
  </thead>
  <tbody class="tableBody" id="dataBody">
    <c:forEach items="${page.result}" var="item" varStatus="status">
      <tr class="${status.count % 2 == 0 ? 'odd' : 'even'}">
        <td>${page.thisPageFirstElementNumber + status.index}</td>
        <td><c:out value='${item.assetNo}' />&nbsp;</td>
        <td><fmt:formatDate pattern ="yyyy-MM-dd HH:mm:ss" value="${item.dataTime}"></fmt:formatDate> &nbsp;</td>
        <td><c:out value='${item.ecurOverUpuplimitTimeA}' />&nbsp;</td>
        <td><c:out value='${item.ecurOverUplimitTimeA}' />&nbsp;</td>
        <td><c:out value='${item.ecurOverUpuplimitTimeB}' />&nbsp;</td>
        <td><c:out value='${item.ecurOverUplimitTimeB}' />&nbsp;</td>
        <td><c:out value='${item.ecurOverUpuplimitTimeC}' />&nbsp;</td>
        <td><c:out value='${item.ecurOverUplimitTimeC}' />&nbsp;</td>
        <td><c:out value='${item.ecurOverUplimitTimeO}' />&nbsp;</td>
        <td><c:out value='${item.ecurPeakA}' />&nbsp;</td>
        <td><c:out value='${item.ecurPeakATime}' />&nbsp;</td>
        <td><c:out value='${item.ecurPeakB}' />&nbsp;</td>
        <td><c:out value='${item.ecurPeakBTime}' />&nbsp;</td>
        <td><c:out value='${item.ecurPeakC}' />&nbsp;</td>
        <td><c:out value='${item.ecurPeakCTime}' />&nbsp;</td>
        <td><c:out value='${item.ecurPeakO}' />&nbsp;</td>
        <td><c:out value='${item.ecurPeakOTime}' />&nbsp;</td>
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