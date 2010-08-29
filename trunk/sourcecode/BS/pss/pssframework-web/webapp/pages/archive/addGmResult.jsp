<%@include file="../../commons/taglibs.jsp"%>



<%@page contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<peis:contextPath />/css/style.css" rel="stylesheet" type="text/css">

<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<html:html lang="true">
  <head>
    <html:base />
    
    <title>addGmResult.jsp</title>

  </head>
<script type="text/javascript" language="javascript">

function init(result){
	if(result=='1'){
		alert("添加采集器成功");
		parent.window.opener.location.href = parent.window.opener.location.href;
		parent.window.close();
		return false;
	}
	else if(result=="2"){
		alert("该资产编号已经存在");
		parent.document.getElementsByName("Update")[0].disabled = false;
		return false;
	}
	else{
		alert("添加采集器失败");
		parent.document.getElementsByName("Update")[0].disabled = false;
	}
}

</script>
  
  <body onload="init('<bean:write name="result"/>')">
<%--    <input type="hidden" name="result" value="<bean:write name='result'/>">--%>
  </body>
</html:html>
