<%@page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=gb2312">
<title>ResultFresh</title>
<link href="<peis:contextPath/>/style/style.css" rel="stylesheet" type="text/css">
</head>

<body>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";    
var iResult = parseInt("<peis:param type="PAGE" paramName="iResult"/>");
var sResult = "<peis:param type="PAGE" paramName="sResult"/>";
var Id="<peis:param type="PAGE" paramName="Id"/>";
var loadType="<peis:param type="PAGE" paramName="loadType"/>";
if(loadType != null && loadType == "10"){
  alert(sResult);
  //top.getMainFrameObj().workpage.noticeForm.submit();  
  top.getMainFrameObj().workpage.location.href = contextPath+'/noticeAction.do?action=getQuery&sqlcode=NOTICE0001&random='+ Math.random();
  top.top.GB_hide();
}else if(loadType != null && loadType == "20"){
  alert(sResult);        
  top.getMainFrameObj().location.href = contextPath+'/parameterAction.do?action=getQuery&sqlcode=PARAMETER0001&random='+ Math.random();
  top.top.GB_hide();
}else{
  if(iResult >= 0) {  
    alert(sResult);
    parent.fresh(Id);
    top.top.GB_hide();
  }else {
    if(isNaN(iResult) == true){  
      alert(sResult);        
      top.getMainFrameObj().location.href = contextPath+'/parameterAction.do?action=getQuery&sqlcode=PARAMETER0001&random='+ Math.random();
      top.top.GB_hide();
    }else{  
      alert(sResult);
    }
  }
}

</script>
</body>

</html>
