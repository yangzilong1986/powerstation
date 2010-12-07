<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ include file="/e3/commons/Common.jsp"%>
<%@ taglib prefix="e3t" uri="/e3/table/E3Table.tld" %>
<%@ page contentType="text/html; charset=utf-8"%>

<HTML>
<HEAD>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
</HEAD>
<BODY> 

<c:url var="uri" value="/servlet/tableServlet?_actionType=showHbnPageTable"/>
<e3t:table id="orgTable"
           items="orgs"
           caption="机构管理"
           uri="${uri}"
          >
    <e3t:column property="id"   title="机构ID" />             
    <e3t:column property="parentId" title="父亲机构ID" />                
    <e3t:column property="name"      title="机构名称" />
    <e3t:column property="viewOrder"   title="显示序号" />
</e3t:table>          

</BODY>
</HTML>
