<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title><bean:message bundle="system" key="dept.manage.title"/></title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css"/>
	<script type="text/javascript" language="javascript">
		var contextPath = "<peis:contextPath/>";
		function nodeEvent(objectType, objectID, objectName, orgType){
	    //alert("单位编号"+objectID);
		    document.frames["line"].location.href=contextPath+'/archive/lineAction.do?action=getQuery&orgNo=' + objectID + '&random='+ Math.random();
		}		
 	</script>
  </head>
  
  <body>
  	<div id="body">
  	  <div class="tab">
        <ul>
          <li class="tab_on">线路管理</li>
          <li class="clear"></li>
        </ul>
      </div>
       <div id="main" style="height: expression(((document.documentElement.clientHeight||document.body.clientHeight) - 48));"> 
  <table border="0" cellpadding="0" cellspacing="0" style="width:100%;height:100%;">
     <tr>
      <td id="tree" width="200" height="100%" style="border-right: 1px solid #085952;">
  		 <iframe width="100%" height="100%" frameborder="0" id="DTree" name="DTree" src="<peis:contextPath/>/windowTreeAction.do?action=getTree&WINDOW=TREE&SMXBH=99991001&objectType=10&queryFunction=query()&typicalType=1&limited=false&limitedtype=#null" ></iframe>
      </td>
      <td height="100%">
   		 <iframe  width="100%" height="100%" frameborder="0" id="line" src="<peis:contextPath/>/archive/lineAction.do?action=getQuery"></iframe>
      </td>
    </tr>
    </table>
    </div>
    </div>
  </body>
</html:html>
