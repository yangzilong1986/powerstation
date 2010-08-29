<%@ page language="java" pageEncoding="UTF-8"%>

<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>




<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title><bean:message bundle="archive" key="archive.tg.title"/></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<peis:contextPath/>/css/window.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
	<script type="text/javascript" language="javascript">
	  function nodeEvent(objectType, objectID, objectName, orgType)
      {
        if(objectType == "2")
        {
           dataQuery.changeParamPage(1);
        }else if(objectType == "3")
        {
           dataQuery.changeParamPage(3);
        }else
        {
           dataQuery.changeParamPage(2);
        }
      }
	</script>
  </head>
  <body style="overflow-x:auto;overflow-y:hidden;">  
  <table width="98%" border=0 cellpadding=0 cellspacing=0 align=center class="e_detail_t">
    <tr>
    <td width=10><img src="<peis:contextPath/>/img/e_detail_tleft.gif" width=10 height=28></td>
    <td width="100%" background="<peis:contextPath/>/img/e_detail_tmain.gif" class="functionTitle">台区信息</td>  
    <td width=10><img src="<peis:contextPath/>/img/e_detail_tright.gif" width=10 height=28></td>
    </tr>
    <tr valign=center >
      <td class="contentLeft">　</td>
      <td valign="middle">
      <br/>
      <div  style="border:1px #085952 solid;height: expression(((document.documentElement.clientHeight||document.body.clientHeight) - 82));"> 
        <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
		   <td id="tree" width="20%" height="100%" style="display: block; border-right: 1px solid #085952;display: ">
              <iframe width="100%" height="100%" frameborder="0" name="DTree" src="<peis:contextPath/>/windowTreeAction.do?action=getTree&WINDOW=TREE&SMXBH=99991006&objectType=10&queryFunction=query()&tg_id=<%=request.getParameter("TG_ID") %>&typicalType=1&limited=false&limitedtype=#null" ></iframe>
           </td>
           <td width="100%" height="100%">
              <iframe width="100%" height="100%" frameborder="0" id="dataQuery" src="<peis:contextPath/>/do/archive/gotoArchiveFrame.do?id=<%=request.getParameter("TG_ID") %>&type=TG&action=archiveFrame"></iframe>
           </td>
		</tr>
        </table>
        </div>
      </td>
      <td class="contentRight">　</td>
    </tr>
    <tr valign=center bgcolor="#FFFFFF">
    <td ><img src="<peis:contextPath/>/img/e_detail_bleft.gif" width=10 height=27></td>
    <td align="right" background="<peis:contextPath/>/img/e_detail_bmain.gif"> </td>
    <td ><img src="<peis:contextPath/>/img/e_detail_bright.gif" width=10 height=27></td>
    </tr>
    </table> 
  </body>
</html:html>



