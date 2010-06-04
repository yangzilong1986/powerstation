<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<script type="text/javascript" language="javascript">

    var result = '<bean:write name="result"/>';
	if(result=="1"){
		alert("<bean:message bundle="archive" key="archive.update.success"/>");
		parent.window.opener.location.href = parent.window.opener.location.href;
		parent.window.close();
	}
	if(result=="3"){
		alert("<bean:message bundle="archive" key="archive.tg.logicaladdrexist"/>");
	}
    if(result=="0"){
		alert("<bean:message bundle="archive" key="archive.update.failure"/>");
	}
</script>