
<!--
  本页的作用是显示大客户信息的内容
  内容包括：大用户信息，终端，电表，模块表，采集点，总加组信息,开关


  并在此提供新增、编辑与删除的入口


-->
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
<%@page contentType="text/html; charset=UTF-8"%>
<link href="<peis:contextPath />/css/window.css" rel="stylesheet" type="text/css">
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/object/object_biguserinfomation.js">
</script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/ajax.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<html>
<head>
<title><bean:message bundle="archive" key="archive.tg.jcDevice"/></title>
</head>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";


function detailCust(custId,tgId){
	var winFeatures = "dialogWidth:850px;dialogHeight:350px;";
	var str_url = contextPath + "/showArchive.do";
	str_url += "?action=showCustInTg&custId="+custId+"&tgId="+tgId;
	str_url += "&rand="+Math.random();
	windowPopup1(str_url,850,500);
	
}

function addCustInTg() {
	var tgId = document.getElementsByName("tgId")[0].value;
	var str_url = contextPath + "/do/archive/gotoAddArchive.do";
	str_url += "?action=gotoAddCustInTg&tgId="+tgId;
	windowPopup1(str_url,850,500);
}

function deleteDyCust(custId) {
	//alert(custId);
	IdArray=new Array();
	IdArray[0]=custId;
	
	if(confirm("<bean:message bundle='archive' key='archive.del.tips'/>")){
		var url = contextPath + "/do/archive/deleteArchive.do?action=deleteDyCust&custId="+custId;
		//alert(url);
		ajax_send(url);
	}
}

  




</script>
<body bgcolor="#f7f7ff">
<table width=96% border=0 cellpadding=0 cellspacing=0 align=center>
    <tr>
      <td width="100%">
      <!--终端区域-->
      <table border="0" cellpadding="0" cellspacing="0"  class="function_title">
		 <tr>
		    <td class="functionLabel" width=100><bean:message bundle="archive" key="archive.custInTg"/></td>
		    <td class="functionLabe2" width="690" height="24" align="right">
		        <input type="button" name="button" value="<bean:message bundle='archive' key='archive.new.dyyh'/>" onclick="addCustInTg();" class="input4">

		   </td>
		 </tr>
      </table>
      <div>
      <div id="tableContainer" class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 44));">
      <table width="100%" class="tablestyle" border=0 cellpadding=0 cellspacing=0 align="center">
		 <tr>
		      <th><bean:message bundle="archive" key="archive.meter.sn"/></th>
		      <th><bean:message bundle="archive" key="archive.custNo"/></th>
		      <th><bean:message bundle="archive" key="archive.custName"/></th>
		      <th><bean:message bundle="archive" key="archive.gp.ASSET_NO"/></th>
		      <th><bean:message bundle="archive" key="archive.cust.status"/></th>
		      <th><bean:message  key="global.operation"/></th>
		 </tr>
		 <logic:present name="PG_QUERY_RESULT">
		    <logic:iterate id="cust" name="PG_QUERY_RESULT">
		      <tr style="cursor:hand;" id="termRow<bean:write name="cust" property="col1"/>" onmouseover="if('<bean:write name="cust" property="col1"/>' != '') this.style.cursor='hand';">
		        <td>
		          <bean:write name="cust" property="rowNo"/>
		        </td>
		        <td>
		          <bean:write name="cust" property="col2"/>
		        </td>
		        <td>
		          <bean:write name="cust" property="col3"/>
		        </td>
		        <td>
		          <bean:write name="cust" property="col4"/>
		        </td>
		        <td>
		          <bean:write name="cust" property="col5"/>
		        </td>
		        <td>
		          <a href="javascript:detailCust('<bean:write name="cust" property="col1"/>','<bean:write name="cust" property="col6"/>');"><bean:message key="global.edit"/></a>|<a href="javascript:deleteDyCust('<bean:write name="cust" property="col1"/>');"><bean:message key="global.delete"/></a>
		        </td>
		        
		      </tr>
		    </logic:iterate>
		    </logic:present>
	   </table>
	   </div>
	    <div class="pageContainer">
	    	<input type="hidden" name="action" value="getQuery"/>
	    	<peis:text type="hidden" name="pageType"/>
	    	<peis:text type="hidden" name="pageRows"/>
	    	<peis:text type="hidden" name="sqlcode"/>
	    	<peis:text type="hidden" name="tgId"/>
      		<peis:pagination queryActionPath="do/archive/custInTg" allowRowsChange="true"/>
    	</div>
	   </div>
	   </td>
	</tr>
</table>
</body>
</html>
