
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
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
    <title></title>
  </head>
<script type="text/javascript" language="javascript">
function init(result){
	if(result == "1"){
		alert("<bean:message bundle="archive" key="archive.add.success"/>");
   	  	top.window.opener.document.all.objectId.value = "<bean:write name='custId'/>";
        top.window.opener.document.all.objectType.value = "1";
   	    top.window.opener.document.archiveQueryForm.submit();
		parent.window.close();
		return false;
	}
	else if(result == "2"){
		alert("该户号已经存在");
		parent.document.getElementsByName("Update")[0].disabled = false;
		return false;
	}
	else{
		alert("<bean:message bundle="archive" key="archive.add.failure"/>");
		parent.document.getElementsByName("Update")[0].disabled = false;
		
	}
}
</script>
  
  <body onload="init('<bean:write name="result"/>');">
    <input type="hidden" name="result" value="<bean:write name='result'/>">
  </body>
</html:html>
