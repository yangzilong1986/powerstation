
<!--
  本页的作用是显示大客户信息的内容
  内容包括：大用户信息，终端，电表，模块表，采集点，总加组信息,开关

  并在此提供新增、编辑与删除的入口

-->
<%@include file="../../commons/taglibs.jsp"%>



<%@page contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<peis:contextPath />/css/style.css" rel="stylesheet" type="text/css">

<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/dwr/engine.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/dwr/util.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/dwr/interface/Terminal.js"></script>
<html>
<head>
<title></title>
</head>
<script type="text/javascript" language="javascript">

function init(result,termId) {
	if(result=="1"){
		alert("<bean:message bundle="archive" key="archive.add.success"/>");
		parent.window.opener.location.href = parent.window.opener.location.href;
		parent.window.close();
		return false;
	}
	if(result=="2")
	{
		alert("<bean:message bundle="archive" key="archive.gp.gpSnexists"/>");
		//parent.window.opener.location.href = parent.window.opener.location.href;
		parent.document.getElementsByName("entry")[0].disabled=false;
		//parent.window.close();
		return false;
	}
	if(result=="0")
	{
		alert("<bean:message bundle="archive" key="archive.add.failure"/>");
		parent.document.getElementsByName("entry")[0].disabled=false;
	}
		if(result=="3")
	{
		alert("计量点编号不能为空，请先添加电表！");
		parent.document.getElementsByName("entry")[0].disabled=false;
		return false;
	}
}



</script>
<body bgcolor="#f7f7ff" onload="init('<bean:write name="result"/>');">
 <input type="hidden" name="result" value="<bean:write name='result'/>">

</body>
</html>
