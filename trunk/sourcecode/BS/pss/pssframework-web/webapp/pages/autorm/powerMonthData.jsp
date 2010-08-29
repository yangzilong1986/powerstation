<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>


<html>
  <head>
    <title><bean:message bundle="autorm" key="autorm.dataquery.lable.ckrsj"/></title>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <link href="<peis:contextPath/>/css/window.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
    <SCRIPT LANGUAGE="Javascript" SRC="<peis:contextPath/>/FusionCharts/FusionCharts.js"></SCRIPT>
    <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
  </head>
  <body>
    <form action="<peis:contextPath/>/do/autorm/dataQuery.do?action=powerMonthData" method="post">
    <table width="98%" border=0 cellpadding=0 cellspacing=0 align=center class="e_detail_t">
    <tr>
    <td width=10><img src="<peis:contextPath/>/img/e_detail_tleft.gif" width=10 height=28></td>
    <td width="100%" background="<peis:contextPath/>/img/e_detail_tmain.gif" class="functionTitle"><bean:message bundle="autorm" key="autorm.dataquery.lable.ckrsj"/></td>  
    <td width=10><img src="<peis:contextPath/>/img/e_detail_tright.gif" width=10 height=28></td>
    </tr>
    <tr valign=center >
      <td class="contentLeft">　</td>
      <td>
       <table border="0" cellpadding="0" cellspacing="0" class="function_title">
		<tr>
		  <td>
		  <input type="hidden" name="sys_object" value="<bean:write name='sys_object' scope='request'/>"/>
          <input type="hidden" name="showType" value="<bean:write name='showType' scope='request'/>"/>
          <input type="hidden" name="seq" value="<bean:write name='seq' scope='request'/>"/>
             <bean:message bundle="autorm" key="autorm.dataquery.lable.dxbh"/>：<input class="user_add_input" type="text" name="object_no" readonly="readonly" style="width:120px;" value="<bean:write name='object_no' scope='request'/>"/>
             <bean:message bundle="autorm" key="autorm.dataquery.lable.dxmc"/>：<input class="user_add_input" type="text" name="object_name" readonly="readonly" style="width:120px;" value="<bean:write name='object_name' scope='request'/>"/>
             <bean:message bundle="autorm" key="autorm.dataquery.lable.jld"/>：<peis:selectlist sql="QUERY0007" name="gp_id" extendProperty="class='user_add_input'"/>
             <bean:message bundle="autorm" key="autorm.dataquery.lable.sjsj"/>：
             <input class="user_add_input" type="text" name="year" style="width: 80px" value="<bean:write name='year' scope='request'/>"><bean:message bundle="autorm" key="autorm.dataquery.lable.year"/>
             <input class="user_add_input" type="text" name="month" style="width: 30px" value="<bean:write name='month' scope='request'/>"><bean:message bundle="autorm" key="autorm.dataquery.lable.month"/>
             <input type="submit" class="input_ok" value="<bean:message bundle='autorm' key='autorm.dataquery.lable.query'/>"/>
          </td>
		</tr>
      </table>
      <br/>
      <table width="100%" border=0 cellpadding=0 cellspacing=0 align="center">
         <tr>
         <td width="100%" valign="top">
             <%=request.getAttribute("CHART_CODE") %>
         </td>
         </tr>
         <tr>
         <td width="100%" valign="top">
             <div id="tableContainer" class="tableContainer" style="height:130px;">
                  <table align="center" width="100%" border="0" cellpadding="0"cellspacing="0">
                    <thead>
                    <tr>
                       <th><bean:message bundle="autorm" key="autorm.dataquery.lable.no"/></th>
                       <th><bean:message bundle="autorm" key="autorm.dataquery.lable.date"/></th>
                       <logic:equal value="1" name="showType">
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.zdyggl"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.zdygglfssj"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.ygglw0sj"/></th>
                       </logic:equal>
                       <logic:equal value="2" name="showType">
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.axzdyggl"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.axzdygglfssj"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.axygglw0sj"/></th>
                       </logic:equal>
                       <logic:equal value="3" name="showType">
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.bxzdyggl"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.bxzdygglfssj"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.bxygglw0sj"/></th>
                       </logic:equal>
                       <logic:equal value="4" name="showType">
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.cxzdyggl"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.cxzdygglfssj"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.cxygglw0sj"/></th>
                       </logic:equal>
                    </tr>
                    </thead>
                    <tbody>
                    <logic:present name="DATA_LIST">
		            <logic:iterate id="datainfo" name="DATA_LIST">
		            <tr>
		                 <td><bean:write name="datainfo" property="rowNo"/></td>
				         <td><bean:write name="datainfo" property="col1"/></td>
				         <td><bean:write name="datainfo" property="col2"/></td>
				         <td><bean:write name="datainfo" property="col3"/></td>
                         <td><bean:write name="datainfo" property="col4"/></td>
                         <td><bean:write name="datainfo" property="col5"/></td>
				    </tr>
				    </logic:iterate>
				    </logic:present>
				    </tbody>
                 </table>
             </div>
         </td>
         </tr>
      </table>
      </td>
      <td class="contentRight">　</td>
    </tr>
    <tr valign=center bgcolor="#FFFFFF">
    <td ><img src="<peis:contextPath/>/img/e_detail_bleft.gif" width=10 height=27></td>
    <td align="right" background="<peis:contextPath/>/img/e_detail_bmain.gif"> </td>
    <td ><img src="<peis:contextPath/>/img/e_detail_bright.gif" width=10 height=27></td>
    </tr>
    </table> 
     </form>
  </body>
</html>
