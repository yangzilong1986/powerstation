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
<div class="tableContainer" style="width: 100%; height: expression(((document.documentElement.clientHeight ||document.body.clientHeight)-29));">
<form:form action="${ctx}/psmanage/psmon/ecCurvQuery" modelAttribute="psMonitorQuery">
  <input type="hidden" id="psId" name="psId" value="${psMonitorQuery.psId}" />
  <input type="hidden" id="sdate" name="sdate" value="${psMonitorQuery.sdate}" />
  <input type="hidden" id="edate" name="edate" value="${psMonitorQuery.edate}" />
  <table style="width: expression((document.documentElement.clientWidth||document.body.clientWidth)<1000?'1000px':((document.documentElement.clientWidth||document.body.clientWidth)-2));" border="0" cellspacing="0" class="gridBody" id="object_table">
    <thead class="tableHeader">
      <tr>
        <th>序号</th>
        <th sortColumn="dataTime">数据时间</th>
        <th sortColumn="closed">开关状态</th>
        <th sortColumn="locked">是否闭锁</th>
        <th sortColumn="phase">故障相位</th>
        <th sortColumn="actionType">动作类型</th>
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
          <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.dataTime}"></fmt:formatDate>&nbsp;</td>
          <td><c:if test="${item.opened eq 0}">合闸</c:if><c:if test="${item.opened eq 1}">分闸</c:if>&nbsp;</td>
          <td><c:if test="${item.opened eq 1}"><c:if test="${item.locked eq 1}">闭锁</c:if><c:if test="${item.locked eq 0}">未闭锁</c:if></c:if>&nbsp;</td>
          <td><c:if test="${item.opened eq 1}"><c:if test="${item.phase eq '00'}">无效</c:if><c:if test="${item.phase eq '01'}">A相</c:if><c:if test="${item.phase eq '10'}">B相</c:if><c:if test="${item.phase eq '11'}">C相</c:if></c:if>&nbsp;</td>
          <td><c:if test="${item.opened eq 0}">合闸</c:if><c:if test="${item.opened eq 1}"><c:set var="key" value="${item.actionType}"></c:set><c:out value="${actionTypeMap[key]}" /></c:if>&nbsp;</td>
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
<div><simpletable:pageToolbar page="${page}"></simpletable:pageToolbar></div>
</body>
<script type="text/javascript">
$(document).ready(function() {
  // 分页需要依赖的初始化动作
  window.simpleTable = new SimpleTable('psMonitorQuery','${page.thisPageNumber}','${page.pageSize}','${pageRequest.sortColumns}');
});
</script>
</html>