<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<html>
  <head>
  <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <title><bean:message bundle="autorm" key="autorm.dataquery.lable.ckrsj"/></title>
    <link href="<peis:contextPath/>/css/window.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
    <SCRIPT LANGUAGE="Javascript" SRC="<peis:contextPath/>/FusionCharts/FusionCharts.js"></SCRIPT>
    <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
  </head>
  <body>
    <form action="<peis:contextPath/>/do/autorm/dataQuery.do?action=ecurMonthData" method="post">
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
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.axdlfz"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.axdlfzfssj"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.axdlysxsj"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.axdlyssxsj"/></th>
                       </logic:equal>
                       <logic:equal value="2" name="showType">
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.bxdlfz"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.bxdlfzfssj"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.bxdlysxsj"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.bxdlyssxsj"/></th>
                       </logic:equal>
                       <logic:equal value="3" name="showType">
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.cxdlfz"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.cxdlfzfssj"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.cxdlysxsj"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.cxdlyssxsj"/></th>
                       </logic:equal>
                       <logic:equal value="4" name="showType">
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.dlfz0x"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.dlfzfssj0x"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.dlysxsj0x"/></th>
                          <th><bean:message bundle="autorm" key="autorm.dataquery.lable.dlyssxsj0x"/></th>
                       </logic:equal>
                    </tr>
                    </thead>
                    <tbody>
                    <logic:present name="DATA_LIST">
		            <logic:iterate id="datainfo" name="DATA_LIST">
		            <tr class="trmainstyle">
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
