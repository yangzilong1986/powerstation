<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@	taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";
var result = "<bean:write name='result' scope='request'/>";
    if(result=='3'){
		alert("总加组中应用了该采集点");
	}
	if(result=='1'){
		alert("更新电表成功");
		parent.window.opener.location.href = parent.window.opener.location.href;
		parent.window.close();
	}
	else{
		alert("更新电表失败");
		parent.document.getElementsByName("Update")[0].disabled = false;
	}

</script>
