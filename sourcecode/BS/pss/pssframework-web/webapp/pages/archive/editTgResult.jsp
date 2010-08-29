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
    
    <title></title>

  </head>
<script type="text/javascript" language="javascript">

function init(result){
	if(result=='1'){
		alert("<bean:message bundle="archive" key="archive.update.success"/>");
		parent.window.location.href = parent.window.location.href;
	}
	if(result=='2'){
	    alert("<bean:message bundle="archive" key="archive.tg.tgNoexist"/>");
		return false;
	}
	if(result=='0'){
		alert("<bean:message bundle="archive" key="archive.update.failure"/>");
		return false;
	}
}

</script>
  
  <body onload="init('<bean:write name="result"/>')">
    
  </body>
</html:html>
