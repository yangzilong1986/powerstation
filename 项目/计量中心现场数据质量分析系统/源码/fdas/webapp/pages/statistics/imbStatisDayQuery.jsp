<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<title>不平衡数据</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
<div class="tableContainer" style="width: 100%; height: expression(((document.documentElement.clientHeight ||document.body.clientHeight)-29));">
<form:form action="${ctx}/statistics/imbStatisDay" modelAttribute="statisticsQuery">
<form:hidden path="tgId"/>
<form:hidden path="orgId"/>
<form:hidden path="ddate"/>
<table width="1200" border="0" cellspacing="0" class="gridBody" id="object_table">
  <thead class="tableHeader">
    <tr>
      <th>序号</th>
      <th sortColumn="assetNo">资产编号</th>
      <th sortColumn="dataTime">数据时间</th>
      <th sortColumn="ecurOverImbalTime">电流不平衡度越限日累计时间</th>
      <th sortColumn="voltOverImbalTime">电压不平衡度越限日累计时间 </th>
      <th sortColumn="ecurImbalMax">电流不平衡最大值</th>
      <th sortColumn="ecurImbalMaxTime">电流不平衡最大值发生时间</th>
      <th sortColumn="voltImbalMax">电压不平衡最大值</th>
      <th sortColumn="voltImbalMaxTime">电压不平衡最大值发生时间</th>
    </tr>
  </thead>
  <tbody class="tableBody" id="dataBody">
    <c:forEach items="${page.result}" var="item" varStatus="status">
      <tr class="${status.count % 2 == 0 ? 'odd' : 'even'}">
        <td>${page.thisPageFirstElementNumber + status.index}</td>
        <td><c:out value='${item.assetNo}' />&nbsp;</td>
        <td><fmt:formatDate pattern ="yyyy-MM-dd HH:mm:ss" value="${item.dataTime}"></fmt:formatDate> &nbsp;</td>
        <td><c:out value='${item.ecurOverImbalTime}' />&nbsp;</td>
        <td><c:out value='${item.voltOverImbalTime}' />&nbsp;</td>
        <td><c:out value='${item.ecurImbalMax}' />&nbsp;</td>
        <td><c:out value='${item.ecurImbalMaxTime}' />&nbsp;</td>
        <td><c:out value='${item.voltImbalMax}' />&nbsp;</td>
        <td><c:out value='${item.voltImbalMaxTime}' />&nbsp;</td>
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