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
  <form:hidden path="tgId" />
  <form:hidden path="orgId" />
  <form:hidden path="ddate" />
  <table width="100%" border="0" cellspacing="0" class="gridBody" id="object_table">
    <thead class="tableHeader">
      <tr>
        <th>序号</th>
        <th sortColumn="assetNo">资产编号</th>
        <th sortColumn="dataTime">数据时间</th>
        <th sortColumn="PActTotal">正向有功总</th>
        <th sortColumn="PReactTotal">正向无功总</th>
        <th sortColumn="IActTotal">反向有功总</th>
        <th sortColumn="IReactTotal">反向无功总</th>
      </tr>
    </thead>
    <tbody class="tableBody" id="dataBody">
      <c:forEach items="${page.result}" var="item" varStatus="status">
        <tr class="${status.count % 2 == 0 ? 'odd' : 'even'}">
          <td>${page.thisPageFirstElementNumber + status.index}</td>
          <td><c:out value='${item.assetNo}' />&nbsp;</td>
          <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.dataTime}"></fmt:formatDate> &nbsp;</td>
          <td><c:out value='${item.PActTotal}' />&nbsp;</td>
          <td><c:out value='${item.PReactTotal}' />&nbsp;</td>
          <td><c:out value='${item.IActTotal}' />&nbsp;</td>
          <td><c:out value='${item.IReactTotal}' />&nbsp;</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</form:form>
<simpletable:pageToolbar page="${page}"></simpletable:pageToolbar>
</body>
<script type="text/javascript">
    $(document).ready(function() {
      // 分页需要依赖的初始化动作
      window.simpleTable = new SimpleTable('statisticsQuery','${page.thisPageNumber}','${page.pageSize}','${pageRequest.sortColumns}');
    });
  </script>
</html>