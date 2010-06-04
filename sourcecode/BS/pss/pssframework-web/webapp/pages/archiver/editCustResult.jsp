<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
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
        top.opener.document.forms[0].submit();
		alert("<bean:message bundle='archive' key='archive.update.success'/>");
		parent.document.getElementsByName("Update")[0].disabled = false;
	}
	else{
		alert("<bean:message bundle='archive' key='archive.update.failure'/>");
		parent.document.getElementsByName("Update")[0].disabled = false;
	}
}

</script>
  
  <body onload="init('<bean:write name="result"/>')">
    
  </body>
</html:html>
