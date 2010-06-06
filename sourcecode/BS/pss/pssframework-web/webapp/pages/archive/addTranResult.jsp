<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
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
    <title></title>
  </head>
<script type="text/javascript" language="javascript">
var contextPath = '<peis:contextPath/>';

function init(result,tgId){
	if(result == "1"){
		alert("<bean:message bundle="archive" key="archive.add.success"/>");
		parent.GB_hide();
		top.getMainFrameObj().location.href=contextPath+"/archive/addLowCustAction.do?action=addTranForwardSuccess&tgId="+tgId;
        return false;
	}
	else if(result == "2"){
		alert("该资产编号已经存在");
        window.location.href=contextPath+"/jsp/archive/addTransformer.jsp?tgId="+tgId;
		return false;
	}
	else{
		alert("<bean:message bundle="archive" key="archive.add.failure"/>");
		window.location.href=contextPath+"/jsp/archive/addTransformer.jsp?tgId="+tgId;
        return false;
	}
}
</script>
  
  <body onload="init('${result}',${tgId});">
    <input type="hidden" name="result" value="${result}">
  </body>
</html:html>
