<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<title>电压统计数据</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
<div class="tableContainer" style="width: 100%; height: expression(((document.documentElement.clientHeight ||document.body.clientHeight)-31));">
<form:form action="${ctx}/statistics/voltStatisDay" modelAttribute="statisticsQuery">
<form:hidden path="tgId"/>
<form:hidden path="orgId"/>
<form:hidden path="ddate"/>
<table width="5000" border="0" cellspacing="0" class="gridBody" id="object_table">
  <thead class="tableHeader">
    <tr>
      <th>序号</th>
      <th sortColumn="assetNo">资产编号</th>
      <th sortColumn="dataTime">数据时间</th>
      <th sortColumn="voltOverUpuplimitTimeA">A相电压越上上限日累计时间</th>
      <th sortColumn="voltOverDowndownlimitTimeA">A相电压越下下限日累计时间</th>
      <th sortColumn="voltOverUplimitTimeA">A相电压越上限日累计时间</th>
      <th sortColumn="voltOverDownlimitTimeA">A相电压越下限日累计时间</th>
      <th sortColumn="voltQualifyTimeA">A相电压合格日累计时间</th>
      <th sortColumn="voltOverUpuplimitTimeB">B相电压越上上限日累计时间</th>
      <th sortColumn="voltOverDowndownlimitTimeB">B相电压越下下限日累计时间</th>
      <th sortColumn="voltOverUplimitTimeB">B相电压越上限日累计时间</th>
      <th sortColumn="voltOverDownlimitTimeB">B相电压越下限日累计时间</th>
      <th sortColumn="voltQualifyTimeB">B相电压合格日累计时间</th>
      <th sortColumn="voltOverUpuplimitTimeC">C相电压越上上限日累计时间</th>
      <th sortColumn="voltOverDowndownlimitTimeC">C相电压越下下限日累计时间</th>
      <th sortColumn="voltOverUplimitTimeC">C相电压越上限日累计时间</th>
      <th sortColumn="voltOverDownlimitTimeC">C相电压越下限日累计时间</th>
      <th sortColumn="voltQualifyTimeC">C相电压合格日累计时间</th>
      <th sortColumn="voltPeakA">A相电压最大值</th>
      <th sortColumn="voltPeakATime">A相电压最大值发生时间</th>
      <th sortColumn="voltValleyA">A相电压最小值</th>
      <th sortColumn="voltValleyATime">A相电压最小值发生时间</th>
      <th sortColumn="voltPeakB">B相电压最大值</th>
      <th sortColumn="voltPeakBTime">B相电压最大值发生时间</th>
      <th sortColumn="voltValleyB">B相电压最小值</th>
      <th sortColumn="voltValleyBTime">B相电压最小值发生时间</th>
      <th sortColumn="voltPeakC">C相电压最大值</th>
      <th sortColumn="voltPeakCTime">C相电压最大值发生时间</th>
      <th sortColumn="voltValleyC">C相电压最小值</th>
      <th sortColumn="voltValleyCTime">C相电压最小值发生时间</th>
      <th sortColumn="averageVoltA">A相平均电压</th>
      <th sortColumn="averageVoltB">B相平均电压</th>
      <th sortColumn="averageVoltC">C相平均电压</th>
    </tr>
  </thead>
  <tbody class="tableBody" id="dataBody">
    <c:forEach items="${page.result}" var="item" varStatus="status">
      <tr class="${status.count % 2 == 0 ? 'odd' : 'even'}">
        <td height="20">${page.thisPageFirstElementNumber + status.index}</td>
        <td><c:out value='${item.assetNo}' />&nbsp;</td>
        <td><fmt:formatDate pattern ="yyyy-MM-dd HH:mm:ss" value="${item.dataTime}"></fmt:formatDate> &nbsp;</td>
        <td><c:out value='${item.voltOverUpuplimitTimeA}' />&nbsp;</td>
        <td><c:out value='${item.voltOverDowndownlimitTimeA}' />&nbsp;</td>
        <td><c:out value='${item.voltOverUplimitTimeA}' />&nbsp;</td>
        <td><c:out value='${item.voltOverDownlimitTimeA}' />&nbsp;</td>
        <td><c:out value='${item.voltQualifyTimeA}' />&nbsp;</td>
        <td><c:out value='${item.voltOverUpuplimitTimeB}' />&nbsp;</td>
        <td><c:out value='${item.voltOverDowndownlimitTimeB}' />&nbsp;</td>
        <td><c:out value='${item.voltOverUplimitTimeB}' />&nbsp;</td>
        <td><c:out value='${item.voltOverDownlimitTimeB}' />&nbsp;</td>
        <td><c:out value='${item.voltQualifyTimeB}' />&nbsp;</td>
        <td><c:out value='${item.voltOverUpuplimitTimeC}' />&nbsp;</td>
        <td><c:out value='${item.voltOverDowndownlimitTimeC}' />&nbsp;</td>
        <td><c:out value='${item.voltOverUplimitTimeC}' />&nbsp;</td>
        <td><c:out value='${item.voltOverDownlimitTimeC}' />&nbsp;</td>
        <td><c:out value='${item.voltQualifyTimeC}' />&nbsp;</td>
        <td><c:out value='${item.voltPeakA}' />&nbsp;</td>
        <td><c:out value='${item.voltPeakATime}' />&nbsp;</td>
        <td><c:out value='${item.voltValleyA}' />&nbsp;</td>
        <td><c:out value='${item.voltValleyATime}' />&nbsp;</td>
        <td><c:out value='${item.voltPeakB}' />&nbsp;</td>
        <td><c:out value='${item.voltPeakBTime}' />&nbsp;</td>
        <td><c:out value='${item.voltValleyB}' />&nbsp;</td>
        <td><c:out value='${item.voltValleyBTime}' />&nbsp;</td>
        <td><c:out value='${item.voltPeakC}' />&nbsp;</td>
        <td><c:out value='${item.voltPeakCTime}' />&nbsp;</td>
        <td><c:out value='${item.voltValleyC}' />&nbsp;</td>
        <td><c:out value='${item.voltValleyCTime}' />&nbsp;</td>
        <td><c:out value='${item.averageVoltA}' />&nbsp;</td>
        <td><c:out value='${item.averageVoltB}' />&nbsp;</td>
        <td><c:out value='${item.averageVoltC}' />&nbsp;</td>
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
      window.simpleTable = new SimpleTable('statisticsQuery','${page.thisPageNumber}','${page.pageSize}','${pageRequest.sortColumns}');
    });
  </script>
</html>