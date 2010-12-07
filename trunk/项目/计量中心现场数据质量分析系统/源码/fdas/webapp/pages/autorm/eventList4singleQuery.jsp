<%/** 电量单用户查询    */%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="peis.common.CommDataDisposal" %>
<html>
  <head>
  <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <title>电量单用户查询</title>
    <link href="<peis:contextPath/>/css/window.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
    <script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
    <script type="text/javascript"  src="<peis:contextPath/>/FusionCharts/FusionCharts.js"></script>
	<script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/prototype/prototype.js"></script>
	<script type="text/javascript" src="<peis:contextPath/>/js/common/multiframe.js"></script>
	<%!
	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd 24hh:mm:ss");
	%>
	<%
		
		List dataList = (List)request.getAttribute("DATA_LIST");
	%>
	<script>
	var contextPath = '<peis:contextPath/>';
		function viewExplain(edId){
	    	var str_url = contextPath +"/eExDataAction.do?action=getExplain&edId="+edId+"&random=" + Math.random();
	    	windowPopup(str_url, 450, 350);
	    }
	    
	    function viewDetail(edId){
	    	var str_url = contextPath +"/eExDataAction.do?action=getDetail&edId="+edId+"&random=" + Math.random();
	    	windowPopup(str_url, 450, 350);
	    }

		
	</script>
  </head>
  <body>
    <table width="98%" border=0 cellpadding=0 cellspacing=0 align=center class="e_detail_t">
    <tr>
      <td class="contentLeft"></td>
      <td>
      <br/>
      <div id="tableContainer" class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 120));">
      <%=request.getAttribute("CHART_CODE")!=null?request.getAttribute("CHART_CODE"):""%>
      <table width="100%" border=0 cellpadding=0 cellspacing=0 align="center">
                    <thead>
	                    <tr>
	                    	<th>序号</th>
	                    	<th>异常发生时间</th>
	                    	<th>事件内容</th>
	                    </tr>
                    </thead>
                 <tbody id="DATA_TBODY">
                 	<logic:present name="PG_QUERY_RESULT">
        				<logic:iterate id="datainfo" name="PG_QUERY_RESULT" indexId="number">
        					<tr>
        					  <td><a href="javascript:void(0);" onclick='javascript:viewDetail(<bean:write name="datainfo" property="col3"/>)'><bean:write name="datainfo" property="rowNo"/></a></td>
        					  <td><bean:write name="datainfo" property="col1"/></td>
					          <td><a href="javascript:void(0);" onclick='javascript:viewExplain(<bean:write name="datainfo" property="col3"/>)'><bean:write name="datainfo" property="col2"/></a></td>
					        </tr>
        				</logic:iterate>
        			</logic:present> 
		         </tbody>
      </table></div>
       	<div class="pageContainer">
       	<logic:present name="PG_QUERY_RESULT">
	  		<peis:pagination sql="EXCEPTION0019" queryActionPath="/autorm/eventQuery4single" allowRowsChange="true"/>
	  	</logic:present> 
	 	</div>
      </td>
    </tr>
    </table>
  </body>
</html>