<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<title>功率曲线</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
<form:form action="${ctx}/statistics/powercruv" modelAttribute="statisticsQuery">
<form:hidden path="tgId"/>
<form:hidden path="orgId"/>
<form:hidden path="ddate"/>
<div id="tbl-container" style="height: expression(((document.documentElement.clientHeight ||document.body.clientHeight)-31));">
<table width="100%" border="0" cellspacing="0" class="gridBody" id="object_table">
  <thead class="tableHeader">
    <tr>
      <th>序号</th>
      <th sortColumn="assetNo">资产编号</th>
      <th sortColumn="dataTime">数据时间</th>
      <th sortColumn="actPower">有功功率</th>
      <th sortColumn="reactPower">无功功率</th>
      <th sortColumn="actPowerA">A相有功功率</th>
      <th sortColumn="actPowerB">B相有功功率</th>
      <th sortColumn="actPowerC">C相有功功率</th>
      <th sortColumn="reactPowerA">A相无功功率</th>
      <th sortColumn="reactPowerB">B相无功功率</th>
      <th sortColumn="reactPowerC">C相无功功率</th>
    </tr>
  </thead>
  <tbody class="tableBody" id="dataBody">
    <c:forEach items="${page.result}" var="item" varStatus="status">
      <tr class="${status.count % 2 == 0 ? 'odd' : 'even'}">
        <td height="20">${page.thisPageFirstElementNumber + status.index}</td>
        <td><c:out value='${item.assetNo}' />&nbsp;</td>
        <td><fmt:formatDate pattern ="yyyy-MM-dd HH:mm:ss" value="${item.dataTime}"></fmt:formatDate> &nbsp;</td>
        <td><c:out value='${item.actPower}' />&nbsp;</td>
        <td><c:out value='${item.reactPower}' />&nbsp;</td>
        <td><c:out value='${item.actPowerA}' />&nbsp;</td>
        <td><c:out value='${item.actPowerB}' />&nbsp;</td>
        <td><c:out value='${item.actPowerC}' />&nbsp;</td>
        <td><c:out value='${item.reactPowerA}' />&nbsp;</td>
        <td><c:out value='${item.reactPowerA}' />&nbsp;</td>
        <td><c:out value='${item.reactPowerC}' />&nbsp;</td>
      </tr>
    </c:forEach>
  </tbody>
</table>
 </div>
</form:form>

<div style="height: 30px; background: #DBEAEB; vertical-align: middle; text-align: right; border-left: 1px #85C0B4 solid; border-right: 1px #85C0B4 solid; border-bottom: 1px #85C0B4 solid;"><simpletable:pageToolbar page="${page}"></simpletable:pageToolbar></div>
</body>
<script type="text/javascript">
    $(document).ready(function() {
      // 分页需要依赖的初始化动作
      window.simpleTable = new SimpleTable('statisticsQuery','${page.thisPageNumber}','${page.pageSize}','${pageRequest.sortColumns}');
    });
  </script>
</html>