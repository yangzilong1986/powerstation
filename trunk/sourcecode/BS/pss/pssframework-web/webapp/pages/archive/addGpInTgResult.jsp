
<%@include file="../../commons/taglibs.jsp"%>



<%@page contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<peis:contextPath />/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/object/object_biguserinfomation.js">
</script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common-ajax.js"></script>
<html:html lang="true">
  <head> 
    <html:base />
    <title>添加低压台区下终端结果返回页面</title>
  </head>
<script type="text/javascript" language="javascript">
function init(result){
	if(result=="1"){
		alert("添加测量点成功");
		parent.window.opener.location.href = parent.window.opener.location.href;
		parent.window.close();
	}
	else{
		alert("添加终端（集中器）失败");
	}
}
</script>
  
  <body onload="init('<bean:write name="result"/>');">
    <input type="text" name="result" value="<bean:write name='result'/>">
  </body>
</html:html>
