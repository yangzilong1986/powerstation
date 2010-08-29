<%/** 电量单用户查询    */%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>

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
	<%
		List dataList = (List)request.getAttribute("DATA_LIST");
	%>
	<script>
		var contextPath = '<peis:contextPath/>';
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
			if($('tjsj').value=="")
				$('tjsj').setValue(dDateStr);

			var custNo = _mainControllerWindow.getCurrCustNo();
			if(!custNo.blank() && custNo != 'null' ){
				$('object_no').setValue(custNo);
				$('dxjh').setValue(custNo);
			}
			else
				alert('用户Id不存在,请先查询基本信息！！！！！');
		});

		/** 表单提交验证 */
		function checkForm(){
			return true;
		}

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
    <form name="form1" action="<peis:contextPath/>/autorm/eventQuery4single.do" method="get" target="iframe1" onsubmit="return checkForm();">
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
          		<html:hidden property="pageType" value="page"/>
          		<input type="hidden" name="showType" value="1"/>
           		<input type="hidden" name="action" value="getQuery" />
	          	<input type="hidden" name="sqlcode" value="EXCEPTION0019">
	          	<input type="hidden" name="dxjh" value="<%=(request.getParameter("object_no")==null)?"":request.getParameter("object_no")%>">
	          	<input name="pageRows" id="pageRows"  type=hidden value="" >
             <bean:message bundle="autorm" key="autorm.dataquery.lable.dxbh"/>：
             <input id="object_no" class="user_add_input" type="text" name="object_no" readonly="readonly" style="width:120px;" value="<%=(request.getParameter("object_no")==null)?"":request.getParameter("object_no")%>"/>
             <bean:message bundle="autorm" key="autorm.dataquery.lable.dxmc"/>：
            
             <input class="user_add_input" type="text" name="object_name" readonly="readonly" style="width:120px;" value="<%=(request.getAttribute("object_name")==null)?"":request.getAttribute("object_name")%>"/>
             <bean:message bundle="autorm" key="autorm.dataquery.lable.jld"/>：
              <select name="gpId">
             	 <logic:present name="SELECT_GP">
	             <logic:iterate id="opt" name="SELECT_GP">
             		 <option value="<bean:write name="opt" property="key" />">
                           <bean:write name="opt" property="value" />
                       </option>
	             </logic:iterate>
	             </logic:present>
             </select>
             <bean:message bundle="autorm" key="autorm.dataquery.lable.sjsj"/>：<input class="user_add_input" type="text" name="tjsj" value="<%=(request.getParameter("dataTime")==null)?"":request.getParameter("dataTime")%>"/>
             <a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'tjsj',null)">
               <img name="dimg1" src="<peis:contextPath/>/images/common/calendar.gif" width="34" height="21" align="middle" border="0" alt="">
             </a>
             <input type="submit" class="input_ok" value="<bean:message bundle='autorm' key='autorm.dataquery.lable.query'/>"/>
          </td>
		</tr>
      </table>
      <br/>
      <div id="tableContainer" class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 100));">
      <iframe name="iframe1" src="<peis:contextPath/>/jsp/autorm/eventList4singleQuery.jsp" width="100%"  height="100%" frameborder="0" scrolling="no"></iframe>
		</div>      
      </td>
    </tr>
    </table>
     </form>
  </body>
</html>