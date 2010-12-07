<%/** 单用户查询-没有查询到数据的报错页面    */%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>

<%@ page import="peis.common.CommDataDisposal" %>
<script>
	var msg = '<%=request.getAttribute("errorMessage")%>';
    alert(msg);
    window.history.go(-1);
</script>