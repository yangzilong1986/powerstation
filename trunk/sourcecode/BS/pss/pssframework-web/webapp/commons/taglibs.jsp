<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="/WEB-INF/tld/extremecomponents.tld" prefix="ec" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<link type='text/css' rel='stylesheet' href='${ctx}/e3/commons/ext/resources/css/ext-all.css' />

<script type="text/javascript">
<!--
if ( typeof(Ext) == "undefined" || typeof(Ext.DomHelper) == "undefined" ){
  document.write('<script src="${ctx}/e3/commons/ext/adapter/ext/ext-base.js"></script>');
  document.write('<script src="${ctx}/e3/commons/ext/ext-all.js"></script>');
  }
//-->
</script>
