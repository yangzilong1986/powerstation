
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
    <title>添加低压用户返回页面</title>
  </head>
<script type="text/javascript" language="javascript">
function init(result){
	if(result=="1"){
		alert("添加开关成功");
		parent.window.opener.location.href = parent.window.opener.location.href;
		parent.window.close();
		return false;
	}
	if(result=="2"){
		alert("更新开关成功");
		parent.window.opener.location.href = parent.window.opener.location.href;
		parent.window.close();
		return false;
	}
    if(result=="-1"){
        alert("同一终端下的开关序号不能重复！");
        parent.document.getElementsByName("Update")[0].disabled = false;
    }
    if(result=="-2"){
        alert("同一终端下的开关轮次不能重复！");
        parent.document.getElementsByName("Update")[0].disabled = false;
    }
	else{
		alert("添加开关失败");
		parent.document.getElementsByName("Update")[0].disabled = false;
	}
}
</script>
  
  <body onload="init('<bean:write name="result"/>');">
    <input type="hidden" name="result" value="<bean:write name='result'/>">
  </body>
</html:html>
