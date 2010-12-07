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
    <script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
    <script type="text/javascript"  src="<peis:contextPath/>/FusionCharts/FusionCharts.js"></script>
	<script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/prototype/prototype.js"></script>
	<script type="text/javascript" src="<peis:contextPath/>/js/common/multiframe.js"></script>
	<%
		List dataList = (List)request.getAttribute("DATA_LIST");
	%>
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
			var orgNo =  _mainControllerWindow.getCurrOrgNo();
			if(!custNo.blank() && custNo != 'null' ){
				$('object_no').setValue(custNo);
				$('orgNo').setValue(orgNo);
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
    <form name="form1" action="<peis:contextPath/>/singleUserQuery.do?action=queryElecQuan" method="post" onsubmit="return checkForm();">
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
          <input type="hidden" name="orgNo"/>
             <bean:message bundle="autorm" key="autorm.dataquery.lable.dxbh"/>：
             <input id="object_no" class="user_add_input" type="text" name="object_no" readonly="readonly" style="width:120px;" value="<%=(request.getParameter("object_no")==null)?"":request.getParameter("object_no")%>"/>
             <bean:message bundle="autorm" key="autorm.dataquery.lable.dxmc"/>：
             <input class="user_add_input" type="text" name="object_name" readonly="readonly" style="width:120px;" value="<%=(request.getAttribute("object_name")==null)?"":request.getAttribute("object_name")%>"/>
             <bean:message bundle="autorm" key="autorm.dataquery.lable.sjlx"/>：<peis:selectlist sql="QUERY00010" name="dataType" extendProperty="class='user_add_input'"/>
             <bean:message bundle="autorm" key="autorm.dataquery.lable.sjsj"/>:<input class="user_add_input" type="text" name="dataTime" value="<%=(request.getParameter("dataTime")==null)?"":request.getParameter("dataTime")%>"/>
             <a onclick="event.cancelBubble=true;" href="javascript:showCalendar('dimg1',false,'dataTime',null)">
               <img name="dimg1" src="<peis:contextPath/>/images/common/calendar.gif" width="34" height="21" align="middle" border="0" alt="">
             </a>
             <input type="submit" class="input_ok" value="<bean:message bundle='autorm' key='autorm.dataquery.lable.query'/>"/>
          </td>
		</tr>
      </table>
      <br/>
      <div id="tableContainer" class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 120));">
      <%=request.getAttribute("CHART_CODE")!=null?request.getAttribute("CHART_CODE"):""%>
      <table width="100%" border=0 cellpadding=0 cellspacing=0 align="center">
                    <thead>
	                    <tr>
	                    	<th></th>
	                    	<th>0点</th>
	                    	<th>1点</th>
	                    	<th>2点</th>
	                    	<th>3点</th>
	                    	<th>4点</th>
	                    	<th>5点</th>
	                    	<th>6点</th>
	                    	<th>7点</th>
	                    	<th>8点</th>
	                    	<th>9点</th>
	                    	<th>10点</th>
	                    	<th>11点</th>
	                    	<th>12点</th>
	                    	<th>13点</th>
	                    	<th>14点</th>
	                    	<th>15点</th>
	                    	<th>16点</th>
	                    	<th>17点</th>
	                    	<th>18点</th>
	                    	<th>19点</th>
	                 		<th>20点</th>
	                    	<th>21点</th>
	                    	<th>22点</th>
	                    	<th>23点</th>
	                    </tr>
                    </thead>
                 <tbody id="DATA_TBODY">
			         	<%
			         	String[] anis_y = {"00","15","30","45"}; 
				      	if(dataList!=null && dataList.size()!=0){
				      			Object[] datas = (Object[])dataList.get(0);
				      			int dataCnt = datas[5]==null?0:((Number)datas[5]).intValue();
				      			if(dataCnt==24){
				      				int index_y = 0;
				      				int times = 1; //y轴循环次数
				      				int index = 6;
				      				for(int i_y = 0; i_y < times; i_y++){
					      				out.print("<tr><td>"+anis_y[index_y]+"</td>");
					      				for(int j=0;j<24;j++,index=index+4)
					      					out.print("<td>"+(datas[index]!=null?datas[index]:"0")+"</td>");
					      				out.print("</tr>");
				      				}
				      			}
				      			else
				      				
				      				if(dataCnt==48){
				      				int index_y = 0;
				      				int times = 2; //y轴循环次数
				      				int index = 6;
				      				for(int i_y = 0; i_y < times; i_y++,index_y=index_y+2){
					      				out.print("<tr><td>"+anis_y[index_y]+"</td>");
					      				for(int j=0;j<24;j++,index=index+2)
					      					out.print("<td>"+(datas[index]!=null?datas[index]:"0")+"</td>");
					      				out.print("</tr>");
				      				}}
				      			
				      			else if(dataCnt==96){
				      				int index_y = 0;
				      				int times = 4; //y轴循环次数
				      				int index = 6;
				      				for(int i_y = 0; i_y < times; i_y++,index_y++){
					      				out.print("<tr><td>"+anis_y[index_y]+"</td>");
					      				for(int j=0;j<24;j++,index++)
					      					out.print("<td>"+(datas[index]!=null?datas[index]:"0")+"</td>");
					      				out.print("</tr>");
				      				}
				      			}
				      	}%>
		         </tbody>
      </table></div>
      </td>
    </tr>
    </table>
     </form>
  </body>
</html>