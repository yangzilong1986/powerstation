<%@page contentType="text/html; charset=UTF-8"%>
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
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>

<html:html lang="true">
  <head> 
    <html:base />
    <title></title>
  </head>
<script type="text/javascript" language="javascript">
var contextPath = '<peis:contextPath/>';
function init(forwardUrl,msg){
   alert(msg);
   parent.GB_hide();
   top.getMainFrameObj().location.href=contextPath+forwardUrl;
   return false;
}
</script>
  <body onload="init('${forwardUrl}','${msg}');">
  </body>
</html:html>
