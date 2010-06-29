<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<%@ page import="peis.common.FusionChartsCreator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title><bean:message bundle="autorm" key="autorm.gatherQuality.Title"/></title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/layout.css"/>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/modules.css"/>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common-ajax.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/FusionCharts/FusionCharts.js"></script>
<script type="text/javascript" language="javascript">

function changeDate(type){
	var td = document.getElementById("tjrq");
	if(type==1){
		td.innerHTML='<input type="text" name="tjsj" onkeypress="if((event.keyCode <48 || event.keyCode > 57) && event.keyCode != 45) event.returnValue = false;" value="'+yesterday+'"/><a onclick="event.cancelBubble=true;" href="javascript:showCalendar(\'dimg1\',false,\'tjsj\',null)"><img name="dimg1" src="<peis:contextPath/>/images/common/calendar.gif" width="34" height="21" align="middle" border="0" alt=""/></a>';
	}
	if(type==2){
		td.innerHTML='<input type="text" name="tjsj" onkeypress="if((event.keyCode <48 || event.keyCode > 57) && event.keyCode != 45) event.returnValue = false;" value="'+year+'" size="6"/>'+'年'+'<input type="text" name="tjsj1" onkeypress="if((event.keyCode <48 || event.keyCode > 57) && event.keyCode != 45) event.returnValue = false;" value="'+month+'" size="2"/>'+'月';
	}
	if(type==3){
		td.innerHTML='<input type="text" name="tjsj" onkeypress="if((event.keyCode <48 || event.keyCode > 57) && event.keyCode != 45) event.returnValue = false;" value="'+yesterday+'"/><a onclick="event.cancelBubble=true;" href="javascript:showCalendar(\'dimg1\',false,\'tjsj\',null)"><img name="dimg1" src="<peis:contextPath/>/images/common/calendar.gif" width="34" height="21" align="middle" border="0" alt=""/></a>'+
		'~~'+'<input type="text" name="tjsj1" onkeypress="if((event.keyCode <48 || event.keyCode > 57) && event.keyCode != 45) event.returnValue = false;" value="'+yesterday+'"/><a onclick="event.cancelBubble=true;" href="javascript:showCalendar(\'dimg1\',false,\'tjsj1\',null)"><img name="dimg1" src="<peis:contextPath/>/images/common/calendar.gif" width="34" height="21" align="middle" border="0" alt=""/></a>';
	}
}
</script>
</head>
<body>
 <div id="main">	
    <html:form action="/gatherSuccessRates" method="post">
    <div>
    <input type="hidden" name="mothed" value="detail"/>
    <html:hidden property="sqlcode"/>	
	 <div id="tool">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">	
		<tr>
				<td width="7%"><bean:message bundle="autorm" key="autorm.gatherQuality.title.gddw"/>:</td>
				<td width="12%"><peis:selectlist name="org_no" sql="COMMON0029" extendProperty="class='mainSelect'"/></td>
				<td width="7%"><bean:message bundle="autorm" key="autorm.gatherQuality.sjmd"/>:</td>
				<td width="12%"><peis:selectlist name="sjmd" sql="SJMD0001" extendProperty="class='mainSelect'"/></td>
				
			</tr>
			<tr>
				<td width="7%"><bean:message bundle="autorm" key="autorm.gatherQuality.sjlx"/>:</td>
				<td width="12%"><peis:selectlist name="sjlx" sql="SJLX0001" extendProperty="class='mainSelect'"/></td>
				<td width="7%"><bean:message bundle="autorm" key="autorm.gatherQuality.sblx"/>:</td>
				<td width="12%"><html:select property="sblx">
					<html:option value="1"><bean:message bundle="autorm" key="autorm.gatherQuality.title.zd"/></html:option>
					<html:option value="2"><bean:message bundle="autorm" key="autorm.gatherQuality.title.jzq"/></html:option>
					<html:option value="3"><bean:message bundle="autorm" key="autorm.gatherQuality.title.mkb"/></html:option>
				</html:select></td>
				<td width="7%"><bean:message bundle="autorm" key="autorm.gatherQuality.txfs"/>:</td>
				<td width="15%"><html:select property="txfs">
					<html:option value=""><bean:message bundle="autorm" key="autorm.gatherQuality.title.all"/></html:option>
					<html:option value="1"><bean:message bundle="autorm" key="autorm.gatherQuality.title.gprs"/></html:option>
					<html:option value="2"><bean:message bundle="autorm" key="autorm.gatherQuality.title.cdma"/></html:option>
					<html:option value="3"><bean:message bundle="autorm" key="autorm.gatherQuality.title.230m"/></html:option>
					<html:option value="4"><bean:message bundle="autorm" key="autorm.gatherQuality.title.yxwl"/></html:option>
				</html:select></td>
		</tr>
		<tr>
				<td><bean:message bundle="autorm" key="autorm.gatherQuality.ckfs"/>:</td>
				<td><html:select property="ckfs" onchange="changeDate(this.options[this.options.selectedIndex].value)">
					<html:option value="1"><bean:message bundle="autorm" key="autorm.gatherQuality.title.arck"/></html:option>
					<html:option value="2"><bean:message bundle="autorm" key="autorm.gatherQuality.title.ayck"/></html:option>
					<html:option value="3"><bean:message bundle="autorm" key="autorm.gatherQuality.title.asjdck"/></html:option>
				</html:select></td>
				<td><bean:message bundle="autorm" key="autorm.gatherQuality.tjsj"/>:</td>
				<td id="tjrq" colspan="5"><bean:write name ="htmlrq" scope="request" filter="false"/>
				</td>	
				</tr>
			</table>
			<div class="t_botton">	
			 <div class="t_right">				
           		<input type="submit" class="bottonStyle_01" value="<bean:message bundle="autorm" key="autorm.button.query"/>"/> 
           		<!-- <input type="button" class="bottonStyle_01" value='<bean:message bundle="autorm" key="autorm.button.ToExcel"/>'/> -->
			</div>
			 <div class="clear"></div>
		</div>	
	</div>
	</div>
	</html:form>
	 <div class="content">
	 	<div id="cont_1">
     <%	
	//String a="<chart caption='抄表成功率' shownames='1' showvalues='0' decimals='0'><categories><category label='广东省电力公司'/> </categories><dataset seriesName='终端成功率' color='AFD8F8' showValues='0'><set value='97'/> </dataset><dataset seriesName='集中器成功率' color='F6BD0F' showValues='0'><set value='0'/> </dataset><dataset seriesName='模块表成功率' color='8BBA00' showValues='0'><set value='0'/> </dataset></chart>";
	String chartCode= FusionChartsCreator.createChart(request.getContextPath()+"/FusionCharts/Column3D.swf", "",(String)request.getAttribute("xml"), "myFirst", 500, 300, false, false);
	%>
		<table>
			<tr>
				<td width="100%" align="center">
					<%=chartCode%>
				</td>
			</tr>
		</table>
		<div class="tableContainer">
		<table align="center" border="0" cellpadding="0" cellspacing="1" width="100%">
		 <logic:equal name="gatherSuccessRatesForm" property="sqlcode" value="CBCGL0002">
		   <thead><tr>
		    <th colspan="2">&nbsp;</th> 
		    <th colspan="2">应抄电表</th> 
		    <th colspan="2">实抄电表</th> 
		    <th >&nbsp;</th> 
		  </tr> 
		  <tr>
            <th>序号</th>          
            <th>设备厂家</th>
            <th>总电表数</th>
            <th>抄表次数</th>           
            <th>成功电表数</th>     
            <th>平均成功次数</th>        
            <th>采集成功率</th>               
        </tr></thead>
        <logic:present name="PG_QUERY_RESULT">
        <logic:iterate id="datainfo" name="PG_QUERY_RESULT" indexId="number"> 
          <tr>
           <td> <bean:write name="datainfo" property="rowNo"/></td>
          <td> <bean:write name="datainfo" property="col1"/></td>
          <td> <bean:write name="datainfo" property="col3"/></td>
          <td> <bean:write name="datainfo" property="col4"/></td>
          <td> <bean:write name="datainfo" property="col5"/></td>
          <td><bean:write name="datainfo" property="col6"/></td>
          <td> <bean:write name="datainfo" property="col7"/></td>
          </tr>
        </logic:iterate>
        </logic:present>
        </logic:equal>  
        <logic:equal name="gatherSuccessRatesForm" property="sqlcode" value="CBWZL0002">
           <thead><tr>
            <th>序号</th>          
            <th>设备厂家</th>
            <th>总采集数据项</th>
            <th>成功采集数据项</th>                 
            <th>采集成功率</th>               
        </tr></thead> 
         <logic:present name="PG_QUERY_RESULT">
        <logic:iterate id="datainfo" name="PG_QUERY_RESULT" indexId="number"> 
          <tr>
           <td> <bean:write name="datainfo" property="rowNo"/></td>
          <td> <bean:write name="datainfo" property="col1"/></td>
          <td> <bean:write name="datainfo" property="col5"/></td>
          <td> <bean:write name="datainfo" property="col3"/></td>
          <td> <bean:write name="datainfo" property="col7"/></td>
          </tr>
        </logic:iterate>
        </logic:present>
        </logic:equal>
	
       </table>
       </div>
       </div>
       </div>
       </div>

</body>

</html>
