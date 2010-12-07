<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
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

function init(result,tgId,termId,orgNo){
	if(result == "1"){//跳转到采集器列表页面
		alert("<bean:message bundle="archive" key="archive.add.success"/>");
         var params = {
                 "tgId":tgId,
                 "orgNo":orgNo,
                 "termId":termId
                };
		window.location.href=contextPath+"/archive/terminalAction1.do?action=showGmlist&"+ jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
        return false;
	}
	else if(result == "2"){
		alert("该资产编号已经存在");
        window.location.href=contextPath+"/archive/terminalAction1.do?action=addTermInit&tgId="+tgId+"&orgNo="+orgNo+"";
		return false;
	}else if(result == "3"){
        alert("该逻辑地址已经存在");
        window.location.href=contextPath+"/archive/terminalAction1.do?action=addTermInit&tgId="+tgId+"&orgNo="+orgNo+"";
        return false;
    }
	else{
		alert("<bean:message bundle="archive" key="archive.add.failure"/>");
		window.location.href=contextPath+"/archive/terminalAction1.do?action=addTermInit&tgId="+tgId+"&orgNo="+orgNo+"";
        return false;
	}
}
</script>
  
  <body onload="init('${result}',${tgId},${termId},${orgNo});">
    <input type="hidden" name="result" value="${result}">
  </body>
</html:html>
