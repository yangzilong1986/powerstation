<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>   
    <title>noticeList.jsp</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css"/>
	 <script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
	<script type="text/javascript" language="javascript">
		var contextPath = "<peis:contextPath/>";	
		var confirmDel='<bean:message bundle="system" key="notice.confirmDel"/>';	
		function detail(noticeId, publishNo) {
			var str_url=contextPath+'/noticeAction.do?action=beforeEdit&noticeId='+noticeId+'&publishNo='+publishNo+'&random='+ Math.random();
    	    top.showDialogBox("编辑公告", str_url, 550, 850);
		}
		
		function delNotice(noticeId) {
		if(confirm(confirmDel)){
			hideframe.location.href=contextPath+'/noticeAction.do?action=delete&noticeId='+noticeId+'&random='+ Math.random();
			}
		}
		
		function fresh(){
			parent.subContent.location.href=contextPath+'/noticeAction.do?action=getQuery&sqlcode=NOTICE0001&random='+ Math.random();
		}

	
 	</script>
  </head>
  
  <body>
   <div class="content">
        <div id="cont_1">
 	 <div id="tableContainer" class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 13));">
     <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <thead><tr>
		
		    <th><bean:message bundle="system" key="notice.xh"/></th> 
		    <th><bean:message bundle="system" key="notice.ggzt"/></th>
		 	<th><bean:message bundle="system" key="notice.fbsj"/></th>
		 	<th><bean:message bundle="system" key="notice.yxsj"/></th>
		 	<th><bean:message bundle="system" key="notice.fbr"/></th>
		 	<th>公告内容</th>
            <th>公告删除</th>           
		  </tr> </thead>
    <logic:present name="PG_QUERY_RESULT">
        <logic:iterate id="datainfo" name="PG_QUERY_RESULT" indexId="number"> 
          <tr>
          <td> <bean:write name="datainfo" property="rowNo"/></td>
          <td> <bean:write name="datainfo" property="col1"/></td>
          <td> <bean:write name="datainfo" property="col2" format="yyyy-MM-dd"/></td>
          <td> <bean:write name="datainfo" property="col3" format="yyyy-MM-dd"/></td>
          <td> <bean:write name="datainfo" property="col4"/></td>         
          <td align="center"> 
            <input type="button" name="view" class="input2" value='查看' onclick="detail('<bean:write name="datainfo" property="col7"/>','<bean:write name="datainfo" property="col6"/>')"/>         
            <bean:define id="userNo"><bean:write name="SE_SYS_USER_NO" scope="session"/></bean:define>  
          </td>
          <td align="center">
            <logic:equal name='datainfo' property="col6" value="<%=userNo%>">&nbsp;&nbsp;
            <input type="button" name="delete" class="input2" value='删除' onclick="delNotice('<bean:write name="datainfo" property="col7"/>')"/>         
          </logic:equal>
          </td>
          </tr>
        </logic:iterate>
        </logic:present>
        </table>
        </div>
        <div class="nullContainer"></div>
        </div>
    <iframe name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"> </iframe>
  </div>
  </body>
</html>
