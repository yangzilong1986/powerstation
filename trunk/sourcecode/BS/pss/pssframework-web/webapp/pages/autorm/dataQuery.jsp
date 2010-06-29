<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<html>
  <head>
  <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <title><bean:message bundle="autorm" key="autorm.dataquery.lable.dataquery"/></title>
    <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css"/>
    <script type="text/javascript" src="<peis:contextPath/>/js/dataQuery.js"></script>
    <script type="text/javascript">
    var lastQueryType = 1;
    var contextPath = "<peis:contextPath/>";
    
    function nodeEvent(objectType, objectID, objectName, orgType) {
        if("13" == objectType || "1" == objectType || "2" == objectType)
        {
           dataQuery.doSubmit(objectID,objectType);
        }
	    //var queryForm = document.getElementById("dataQuery");
	    //alert(queryForm);
	    //dataQuery.doSubmit();
	}
	
   // $(document).ready( function () {
   //     
   // });
   
   function changeTreeMode(modeId)
	   {
	       document.getElementsByName("DTree")[0].src = contextPath + "/windowTreeAction.do?action=getTree&WINDOW=TREE&SMXBH="+modeId+"&objectType=10&queryFunction=query()&typicalType=1&limited=false&limitedtype=#null";
	   }
    </script>
  </head>
  
  <body>
    <div id="body">
      <div class="tab">
        <ul>
          <li id="query1" class="tab_on"><a href="javascript:changeQueryType(1);" onfocus="blur()"><bean:message bundle="autorm" key="autorm.dataquery.lable.meter"/></a></li>
          <li id="query2" class="tab_off"><a href="javascript:changeQueryType(2);" onfocus="blur()"><bean:message bundle="autorm" key="autorm.dataquery.lable.power"/></a></li>
          <li id="query3" class="tab_off"><a href="javascript:changeQueryType(3);" onfocus="blur()"><bean:message bundle="autorm" key="autorm.dataquery.lable.volt"/></a></li>
          <li id="query4" class="tab_off"><a href="javascript:changeQueryType(4);" onfocus="blur()"><bean:message bundle="autorm" key="autorm.dataquery.lable.ecur"/></a></li>
          <li id="query5" class="tab_off"><a href="javascript:changeQueryType(5);" onfocus="blur()"><bean:message bundle="autorm" key="autorm.dataquery.lable.pf"/></a></li>
          <!-- <li id="query6" class="tab_off"><a href="javascript:changeQueryType(6);" onfocus="blur()"><bean:message bundle="autorm" key="autorm.dataquery.lable.xbsj"/></a></li>
          <li id="query7" class="tab_off"><a href="javascript:changeQueryType(7);" onfocus="blur()"><bean:message bundle="autorm" key="autorm.dataquery.lable.action"/></a></li> -->
          <li id="query8" class="tab_off"><a href="javascript:changeQueryType(8);" onfocus="blur()"><bean:message bundle="autorm" key="autorm.dataquery.lable.electric"/></a></li>
          <li class="clear"></li>
        </ul>
      </div>
      <div id="main" style="height: expression(((document.documentElement.clientHeight||document.body.clientHeight) - 48));"> 
        <table border="0" cellpadding="0" cellspacing="0" style="width:100%;height:100%;">
          <tr>
            <td id="tree" width="200" height="100%" style="display: block; border-right: 1px solid #085952;">
              <iframe width="100%" height="100%" frameborder="0" name="DTree" src="<peis:contextPath/>/windowTreeAction.do?action=getTree&WINDOW=TREE&SMXBH=99991002&objectType=10&queryFunction=query()&typicalType=1&limited=false&limitedtype=#null" ></iframe>
            </td>
            <td height="100%">
              <iframe width="100%" height="100%" frameborder="0" id="dataQuery" src="<peis:contextPath/>/do/autorm/dataQuery.do?action=meterQuery"></iframe>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </body>
</html>
