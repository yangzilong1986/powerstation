<%@ page language="java" pageEncoding="UTF-8"%>

<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>


<html>
  <head>   
    <title><bean:message bundle="system" key="parameter.edit.title"/></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css"/>
	 <script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>  
    <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
	<script type="text/javascript" language="javascript">
		var contextPath = "<peis:contextPath/>";		
		function detail(PName, orgNo) {
			var str_url=contextPath+'/parameterAction.do?action=beforeEdit&PName='+PName+'&edit=1&orgNo='+orgNo+'&random='+ Math.random();
    	    top.showDialogBox("配置修改",str_url, 300, 460);   	
		}
		function view(PName, orgNo) {
			var str_url=contextPath+'/parameterAction.do?action=detail&PName='+PName+'&edit=0&orgNo='+orgNo+'&random='+ Math.random();
    	    top.showDialogBox("配置修改",str_url, 450, 250);	
		}
		
		function fresh(){
			parent.content.location.href=contextPath+'/parameterAction.do?action=getQuery&sqlcode=PARAMETER0001&random='+ Math.random();
		}

	
 	</script>
  </head>
  
  <body>
  	<div id="body">
  	 <div class="tab">
      <ul>
        <li id="tab_1" class="tab_on"><a href="javascript:void(0);" onfocus="blur()"><bean:message bundle="system" key="parameter.manage.title"/></a></li>
        <li class="clear"></li>
      </ul>
    </div>
     <div id="main" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 44));">
     <div class="content">
        <div id="cont_1">
 	 <div id="tableContainer" class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 56));">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <thead><tr>
		
		    <th><bean:message bundle="system" key="parameter.xh"/></th> 
		    <th><bean:message bundle="system" key="parameter.csmc"/></th>
		 	<th><bean:message bundle="system" key="parameter.dwmc"/></th>
		 	<th><bean:message bundle="system" key="parameter.csz"/></th>
		 	<th><bean:message bundle="system" key="parameter.bz"/></th>
		 	<th></th>
		  </tr> </thead>
    <logic:present name="PG_QUERY_RESULT">
        <logic:iterate id="datainfo" name="PG_QUERY_RESULT" indexId="number"> 
          <tr align="center" class="trmainstyle">
          <td> <bean:write name="datainfo" property="rowNo"/></td>
          <td> <bean:write name="datainfo" property="col1"/></td>
          <td> <bean:write name="datainfo" property="col2"/></td>
          <td> <bean:write name="datainfo" property="col3"/></td>
          <td> <bean:write name="datainfo" property="col4"/></td>         
          <td> 
          <logic:equal name="isEdit" value="1" scope="request"> 
          <input type="button" name="edit" class="input1" value='<bean:message bundle="system" key="parameter.button.xg" />' onclick="detail('<bean:write name="datainfo" property="col1"/>','<bean:write name="datainfo" property="col5"/>')"/>         
          </logic:equal>
          
          </td>
          </tr>
        </logic:iterate>
        </logic:present>
        </table>
        </div>
        <div class="nullContainer"></div>
    </div>
    </div>
    </div>
    </div>
  </body>
</html>
