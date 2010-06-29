<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<%@ page import="peis.common.FusionChartsCreator"%>
<html>
<head>

<title><bean:message bundle="autorm" key="autorm.realTimeReading.title"/></title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/dataframe.css"/>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/component.js"></script>
 <script type="text/javascript" language="javascript" src="<peis:contextPath/>/FusionCharts/FusionCharts.js"></script>   
<script type="text/javascript" language="javascript">

var contextPath = "<peis:contextPath/>";

function getdetail(sblx,txfs,org_no){
	var form=document.forms[0];
	form.org_no.value=org_no;
	form.sblx.value=sblx;
	form.txfs.value=txfs;
	form.action="<peis:contextPath/>"+"/gatherSuccessRates.do?mothed=detail";
	form.target="popupWin";
	var wd=1000;
	var ht=600;
	var popupWin = window.open("", 'popupWin', 'width='+wd+',height='+ht+',top='+(screen.availHeight-ht)/2+', left='+(screen.availWidth-wd)/2+',scrollbars=yes');	
	form.submit();
}


	$(document).ready(function(){
		//img的绑定事件

		$('img').click(function(){
			if($(this).attr("src").indexOf("plus1.gif")!=-1){
				$(this).attr("src", contextPath + "/images/common/jsFlexGrid/minus1.gif");
				//alert("显示")
				var trs = $(this).parent().parent().nextAll().each(function(i){
					if($(this).attr("lastflag")){
						$(this).attr("class", "styleShow");
					}
					else return false; //跳出循环
				});
			}
			else{
				$(this).attr("src", contextPath + "/images/common/jsFlexGrid/plus1.gif");
				//alert("隐藏");
				var trs = $(this).parent().parent().nextAll().each(function(){
					if($(this).attr("lastflag")){
						$(this).attr("class", "styleHidden");
					}
					else return false; //跳出循环
				});
			}
		});
	});
	
</script>
</head>

<body>
<div id="cont_1">
<%	
	//String a="<chart caption='抄表成功率' shownames='1' showvalues='0' decimals='0'><categories><category label='广东省电力公司'/> </categories><dataset seriesName='终端成功率' color='AFD8F8' showValues='0'><set value='97'/> </dataset><dataset seriesName='集中器成功率' color='F6BD0F' showValues='0'><set value='0'/> </dataset><dataset seriesName='模块表成功率' color='8BBA00' showValues='0'><set value='0'/> </dataset></chart>";
	String chartCode= FusionChartsCreator.createChart(request.getContextPath()+"/FusionCharts/MSColumn3D.swf", "",(String)request.getAttribute("xml"), "chart", 800, 275, false, false);
%>
			<%=chartCode%>

<div id="tableContainer" class="tableContainer">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
		  <thead><tr>
		  	<logic:equal name="gatherSuccessRatesForm" property="sqlcode" value="CBCGL0002">
			    <th>&nbsp;</th>
			    <th>&nbsp;</th>
			    <th colspan="5">应抄电表数</th> 
			    <th colspan="5">实抄电表数</th> 
			    <th colspan="5">抄表失败电表数</th>
			    <th colspan="5">抄表成功率</th>
		    </logic:equal>
		    <logic:equal name="gatherSuccessRatesForm" property="sqlcode" value="CBWZL0002">
		    	<th>&nbsp;</th>
		    	<th>&nbsp;</th>
			    <th colspan="5">应抄数据项数</th> 
			    <th colspan="5">实抄数据项数</th> 
			    <th colspan="5">缺项数</th>
			    <th colspan="5">完整率</th>
		    </logic:equal>
		  </tr>
		  <tr>
		  	<th>&nbsp;</th>
		  	<th><bean:message bundle="autorm" key="autorm.gatherQuality.title.gddw"/>(终端厂家)</th>
		  
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.totle"/></th>
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.gprs"/></th>
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.cdma"/></th>
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.yxwl"/></th>
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.gsm"/></th>
            
            
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.totle"/></th>
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.gprs"/></th>
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.cdma"/></th>
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.yxwl"/></th>
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.gsm"/></th>
            
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.totle"/></th>
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.gprs"/></th>
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.cdma"/></th>
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.yxwl"/></th>
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.gsm"/></th>
            
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.totle"/></th>
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.gprs"/></th>
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.cdma"/></th>
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.yxwl"/></th>
            <th><bean:message bundle="autorm" key="autorm.gatherQuality.title.gsm"/></th>
        </tr>
        </thead>
        <html:form action="/gatherSuccessRates" method="post">
        <html:hidden property="mothed" value="detail"/>
        <html:hidden property="sjmd"/>
        <html:hidden property="sjlx"/>
        <html:hidden property="ckfs"/>
        <html:hidden property="tjsj"/>
        <html:hidden property="sqlcode"/>
        <html:hidden property="org_no"/>       
        <html:hidden property="year"/>
        <html:hidden property="month"/>
        <html:hidden property="sblx"/>
        <html:hidden property="txfs"/>
		<tbody>
        	<logic:present name="PG_QUERY_RESULT">
             <logic:iterate id="datalist" name="PG_QUERY_RESULT" indexId="numberfirst">
             	<logic:iterate id="second" name="datalist" offset="1" length="1">
           			<bean:define id="orgName" name="second"/>
           		</logic:iterate>
             	<logic:iterate id="second" name="datalist" offset="2" length="1">
             		<logic:empty name="second">
             			<tr align="left">
                         <td>
			              <img src="<peis:contextPath/>/images/common/jsFlexGrid/plus1.gif" style="cursor:pointer;"/>
                         </td>
			             <td style="text-align: left;">
			              <bean:write name="orgName"/>
                         </td>
                        </tr>
             		</logic:empty>
             		<logic:notEmpty name="second">
             			<tr class="styleHidden" lastflag="00">
		         		 	<td></td>
                            <td style="text-align: left;" nowrap="nowrap">
		         		 	&nbsp;&nbsp;&nbsp;&nbsp;<bean:write name="second"/>
                            </td>
                         </tr>
             		</logic:notEmpty>
             	</logic:iterate>
           		<logic:iterate id="second" name="datalist" offset="3">
		         <td style="text-align: left;">
	         		<bean:write name="second"/>
		         </td>
			    </logic:iterate>
             </logic:iterate>
             </logic:present>
        </tbody>
        </html:form>
       </table>
       </div>
       <div class="nullContainer"></div>
       </div>
</body>
</html>