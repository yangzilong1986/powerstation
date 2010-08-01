<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<html>
  <head>
    <title><bean:message bundle="system" key="notice.manage.title" /></title>
    <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css"/>
    <script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/dataQuery.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.xml2json.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/FusionCharts/FusionCharts.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/frame/json2htmlex.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script> 
    <script type="text/javascript">
   		var lastQueryType = 1;
        var contextPath = "<peis:contextPath/>";
        
       function changeType(obj) {
		var lastTd = document.getElementById("query" + lastQueryType);
		var nowTd = document.getElementById("query" + obj);
		lastTd.className = 'tab_off';
		nowTd.className = 'tab_on';

		lastQueryType = obj;
		var url = contextPath;
		if (lastQueryType == 1) {
			url += "/noticeAction.do?action=getQuery&sqlcode=NOTICE0001";
		}
		if (lastQueryType == 2) {
			url += "/noticeAction.do?action=init";
		}
		document.getElementById("workpage").src = url;
	}
	
    </script>
  </head>
  
  <body>
  <div id="body">
  <div class="tab">
        <ul>
		  <li id="query1" class="tab_on"><a href="javascript:changeType(1);" onfocus="blur()"><bean:message bundle="system" key="notice.ggll" /></a></li>
          <li id="query2" class="tab_off"><a href="javascript:changeType(2);" onfocus="blur()"><bean:message bundle="system" key="notice.ggfb" /></a></li>  
          <li class="clear"></li>
        </ul>
  </div>
   <div id="main" style="height: expression(((document.documentElement.clientHeight||document.body.clientHeight) - 44));"> 
     <table border="0" cellpadding="0" cellspacing="0" style="width:100%;height:100%;">
     	<tr>
             <td width="100%">
                <iframe id="workpage" name="workpage" src="<peis:contextPath/>/noticeAction.do?action=getQuery&sqlcode=NOTICE0001" width="100%" height="100%" frameborder="0"></iframe>
             </td>
          </tr>
      </table>
  </div>
  </div>
  </body>
</html>
