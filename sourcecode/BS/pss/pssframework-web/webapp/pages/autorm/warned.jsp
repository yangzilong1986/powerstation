<%/** 单用户查询-没有查询到数据的报错页面    */%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="peis.common.CommDataDisposal" %>
<script>
	var msg = '<%=request.getAttribute("errorMessage")%>';
    alert(msg);
    window.history.go(-1);
</script>