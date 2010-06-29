<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ page import="peis.common.CommDataDisposal" %>

<html>
  <head>
  <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <meta http-equiv="expires" content="0"/>
    <title><bean:message bundle="autorm" key="autorm.dataquery.lable.ckqx"/></title>
    <link href="<peis:contextPath/>/css/window.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
    <script type="text/javascript"  src="<peis:contextPath/>/FusionCharts/FusionCharts.js"></script>
	<script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/prototype/prototype.js"></script>
	<script type="text/javascript" src="<peis:contextPath/>/js/common/multiframe.js"></script>
	<script>
		var dDateStr = '<%=CommDataDisposal.getYestDate()%>';
		/* 总控窗体 */
		var _mainControllerWindow;
		_mainControllerWindow = getControllerWindow(window);
		document.observe('dom:loaded', function() {
			_mainControllerWindow = getControllerWindow(window);
			if (!_mainControllerWindow) {
				throw new Error('总控窗体未能找到！');
			}
			/** 如果时间为空，填充时间 */
			if($('dataTime').value=="")
				$('dataTime').setValue(dDateStr);

			var custNo = _mainControllerWindow.getCurrCustNo();
			if(!custNo.blank() && custNo != 'null' ){
				$('object_no').setValue(custNo);
				/** 第一次访问提交表单 */
				var h = window.location.href;
				if(h.indexOf('.jsp')!=-1){
					if(checkForm())//手动
						$("form1").submit();
				}
			}
			else
				alert('用户Id不存在,请先查询基本信息！！！！！');
		});

		/** 表单提交验证 */
		function checkForm(){
			return true;
		}
	</script>
  </head>
  <body>
    <form name="form1" action="<peis:contextPath/>/singleUserQuery.do?action=pfDayData" method="post" onsubmit="return checkForm();">
    <table width="98%" border=0 cellpadding=0 cellspacing=0 align=center class="e_detail_t">
    <tr>
    <td width=10><img src="<peis:contextPath/>/img/e_detail_tleft.gif" width=10 height=28></td>
    <td width="100%" background="<peis:contextPath/>/img/e_detail_tmain.gif" class="functionTitle"><bean:message bundle="autorm" key="autorm.dataquery.lable.ckqx"/></td>  
    <td width=10><img src="<peis:contextPath/>/img/e_detail_tright.gif" width=10 height=28></td>
    </tr>
    <tr>
      <td class="contentLeft"></td>
      <td>
      <table border="0" cellpadding="0" cellspacing="0" class="function_title">
		<tr>
		  <td>
          <input type="hidden" name="sys_object" value="1"/>
          <input type="hidden" name="showType" value="1"/>
             <bean:message bundle="autorm" key="autorm.dataquery.lable.dxbh"/>：
             <input id="object_no" class="user_add_input" type="text" name="object_no" readonly="readonly" style="width:120px;" value="<%=(request.getParameter("object_no")==null)?"":request.getParameter("object_no")%>"/>
             <bean:message bundle="autorm" key="autorm.dataquery.lable.dxmc"/>：
             <input class="user_add_input" type="text" name="object_name" readonly="readonly" style="width:120px;" value="<%=(request.getAttribute("object_name")==null)?"":request.getAttribute("object_name")%>"/>
             <bean:message bundle="autorm" key="autorm.dataquery.lable.jld"/>：<peis:selectlist sql="QUERY0007" name="gpId" extendProperty=""/>
             <bean:message bundle="autorm" key="autorm.dataquery.lable.sjsj"/>：<input class="user_add_input" type="text" name="dataTime" value="<%=(request.getParameter("dataTime")==null)?"":request.getParameter("dataTime")%>"/>
             <a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'dataTime',null)">
               <img name="dimg1" src="<peis:contextPath/>/images/common/calendar.gif" width="34" height="21" align="middle" border="0" alt="">
             </a>
             <input type="submit" class="input_ok" value="<bean:message bundle='autorm' key='autorm.dataquery.lable.query'/>"/>
          </td>
		</tr>
      </table>
      <br/>
      <table width="100%" border=0 cellpadding=0 cellspacing=0 align="center">
            <tr>
               <td height="335" width="60%"><%=request.getAttribute("CHART_CODE") %></td>
               <td height="335" width="40%">
                  <div id="tableContainer" class="tableContainer" style="width:350px;height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 120));">
                     <table align="center" width="440" border="0" cellpadding="0"cellspacing="0">
                    <thead><tr>
                       <th width="40"><bean:message bundle="autorm" key="autorm.dataquery.lable.no"/></th>
                       <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.date"/></th>
                       <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.zglys"/></th>
                       <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.axglys"/></th>
                       <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.bxglys"/></th>
                       <th width="80"><bean:message bundle="autorm" key="autorm.dataquery.lable.cxglys"/></th>
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
