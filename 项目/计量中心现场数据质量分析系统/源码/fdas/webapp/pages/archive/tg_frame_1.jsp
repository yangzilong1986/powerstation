<%@ page language="java" pageEncoding="UTF-8"%>

<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>




<html:html lang="true">
  <head>
    <html:base />
    
    <title><bean:message bundle="archive" key="archive.tg.info"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<peis:contextPath/>/css/window.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
	<script type="text/javascript" language="javascript">
	var contextPath='<peis:contextPath/>';
	var lastPageNo="1";
	function changeParamPage(pageNo){
		var tgId = document.all.tgId.value;
		eval("document.all.g"+lastPageNo+".className='e_titleoff'");
		lastPageNo=pageNo;
		eval("document.all.g"+pageNo+".className='e_titleon'");
		if(pageNo=="1"){
			
			fcssz.document.location=contextPath+"/showTg.do?tgId="+tgId+"&action=showTg&rand="+Math.round();
		}
		else if(pageNo=="2"){
			
			fcssz.document.location=contextPath+"/showTgDevice.do?tgId="+tgId+"&action=showTgDevice&rand="+Math.round();
		}
		else if(pageNo=="3"){
			
			fcssz.document.location=contextPath+"/do/archive/custInTg.do?tgId="+tgId+"&action=getQuery&pageRows=20&pageType=page&sqlcode=TG0005";
		}
		
	}
	
	
		
	</script>

  </head>
  <body style="overflow-x:hidden;overflow-y:hidden;">
  <div class="equip_title" >
        <ul class="e_title">
          <li id="g1" class="e_titleon"><a href="javascript:changeParamPage(1);" onfocus="blur()"><bean:message bundle="archive" key="archive.tg.info"/></a></li>
          <li id="g2" class="e_titleoff"><a href="javascript:changeParamPage(2);" onfocus="blur()"><bean:message bundle="archive" key="archive.deviceInTg"/></a></li>
          <li id="g3" class="e_titleoff"><a href="javascript:changeParamPage(3);" onfocus="blur()"><bean:message bundle="archive" key="archive.custInTg"/></a></li>
        </ul>
  </div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" align=center>
  <input type="hidden" id="tgId" value="<bean:write name="TG_ID"/>">
   <tr height="484">
     <td width="100%" scope="10">
        <iframe name="fcssz" src="" frameborder=0 width=100% height=100%></iframe>
     </td>
   </tr>
  </table>
  <script language=javascript>
  changeParamPage(1);
  
</script>
</body>
</html:html>