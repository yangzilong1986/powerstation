
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
<%@page contentType="text/html; charset=UTF-8"%> 
<link href="<peis:contextPath />/css/window.css" rel="stylesheet" type="text/css">
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/object/object_biguserinfomation.js">
</script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common-ajax.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/dwr/engine.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/dwr/util.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/dwr/interface/Terminal.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/dwr/interface/cust.js"></script>
<html>
<head>
<title><bean:message bundle="archive" key="archive.edit.DyCust"/></title>
</head>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";





function jc(){
	if(document.getElementById("checkbox1").checked){
		document.getElementById("tabel3").style.display = "";
	}
	else
	{
		document.getElementById("tabel3").style.display = "none";
	}
}

function viewGm() {
	var termId = document.getElementsByName("termId")[0].value;
	Terminal.getGmMapByTermId(termId,function(data){
			DWRUtil.removeAllOptions("gmId");
			DWRUtil.addOptions("gmId",data);
		
	})
}

function update(){
	if(""==document.getElementsByName("custNo")[0].value) {
		alert("<bean:message bundle="archive" key="archive.alert.cust.custNo"/>");
		return false;
	}
	if(""==document.getElementsByName("assetNo")[0].value) {
		alert("<bean:message bundle="archive" key="archive.meter.assetnononull"/>");
		return false;
	}
	var integerPatrn = /^[1-9]\d*$/;
	var rmday = document.getElementsByName("rmDay")[0].value;
		if(rmday != "" && !integerPatrn.exec(rmday)){
	    alert("<bean:message bundle='archive' key='archive.rmDay'/>"+"<bean:message bundle='archive' key='archive.isNotNumber'/>");
		return false;
	}
	document.getElementsByName("Update")[0].disabled = true;
	document.forms[0].submit();
}



</script>


<body bgcolor="#f7f7ff" style="overflow-x:hidden;overflow-y:hidden;" >
<ul class="e_title">
    <li class="e_titleon"><a href="#"><bean:message bundle="archive" key="archive.custInTg"/></a>
  </ul>
  <!--   <div class="user_add_table">  -->
  <html:form  action="do/archive/editDyCust" method="post" target="resultPage">
          <input type="hidden" name="action" value="editDyCust">
          <logic:present name="tgId">
		  <peis:text type="hidden" name="tgId"/>
		  </logic:present>
		  <table border="0" cellpadding="0" cellspacing="0"  class="function_title">
		        <tr>
		        <td class="functionLabel" width=100><bean:message bundle="archive" key="archive.cust.info"/></td>
		      </tr>
      </table>
  <table width="98%" border="0" align="center"  cellpadding="0" cellspacing="0" class="table1">

    <tr>
		     <td align="right" width="12%"><bean:message bundle="archive" key="archive.custNo"/>：</td>
		     	<peis:text type="hidden" name="custId" styleClass="user_add_input"/>
		     <td width="20%"><peis:text type="text" name="custNo"  styleClass="user_add_input"/></td>
		     <td align="right" width="12%"><bean:message bundle="archive" key="archive.custName"/>：</td>
		     <td width="20%"><peis:text type="text" name="custName"  styleClass="user_add_input"/></td>
		     <td align="right" width="12%"><bean:message bundle="archive" key="archive.trade"/>：</td>
		     <td width="20%"><peis:selectlist name="tradeSort" sql="COMMON0002" extendProperty="class='user_add_select'"/></td>
		    </tr>
		    <tr>
		     <td align="right"><bean:message bundle="archive" key="archive.org"/>：</td>
		     <td><peis:selectlist name="orgNo" sql="ORG0002" extendProperty="class='user_add_select'"/></td>
		     <td align="right"><bean:message bundle="archive" key="archive.contact"/>：</td>
		     <td><peis:text type="text" name="contact" styleClass="user_add_input"/></td>
		     <td align="right"><bean:message bundle="archive" key="archive.phone"/>：</td>
		     <td><peis:text type="text" name="phone" styleClass="user_add_input"/></td>
		    </tr>
		    <tr>
		    <td align="right"><bean:message bundle="archive" key="archive.cust.status"/>：</td>
		     <td><peis:selectlist name="custStatus" sql="COMMON0004" extendProperty="class='user_add_select'"/></td>
		     <td align="right"><bean:message bundle="archive" key="archive.addr"/>：</td>
		     <td colspan="3"><peis:text type="text" name="custAddr" styleClass="user_add_input" extendProperty="style='width:370;'"/></td>
		    </tr>
		    <tr>
		    <td align="right"><bean:message bundle="archive" key="archive.rmDay"/>：</td>
		     <td><peis:text type="text" name="rmDay" styleClass="user_add_input"/></td>
		     <td align="right"  rowspan="3"><bean:message bundle="archive" key="archive.remark"/>：</td>
		     <td colspan="3" rowspan="3"><TEXTAREA name="remark" class="input_textarea" style="width:370;"><bean:write name="cust" property="remark"/></TEXTAREA></td>
		    </tr>
		    <tr>
		     <td align="right"><bean:message bundle="archive" key="archive.rmSectNo"/>：</td>
		     <td><peis:text type="text" name="rmSectNo" styleClass="user_add_input"/></td>  
		    </tr>
		    <tr>
		    <td align="right"><bean:message bundle="archive" key="archive.rmSn"/>：</td>
		     <td><peis:text type="text" name="rmSn" styleClass="user_add_input"/></td> 
		    </tr>
  </table>
  <br/>
  <logic:present name="meter">
  <table border="0" cellpadding="0" cellspacing="0"  class="function_title">
		        <tr>
		        <td class="functionLabel" width=100><bean:message bundle="archive" key="archive.meter.info"/></td>
		      </tr>
      </table>
      <table width="98%" border="0" align="center"  cellpadding="0" cellspacing="0" class="table1">
    <tr>
      <td id="msg" colspan="6"></td>
    </tr>
    <tr>
		       <td align="right" width="12%"><bean:message bundle="archive" key="archive.gp.ASSET_NO"/>：</td>
		       	<peis:text type="hidden" name="mpId"/>
		       	<peis:text type="hidden" name="gpId"/>
		       <td width="20%"><peis:text type="text" name="assetNo" styleClass="user_add_input"/></td>
		       <td align="right" width="12%"><bean:message bundle="archive" key="archive.meter.type"/>：</td>
		       <td width="20%"><peis:selectlist name="meterType" sql="COMMON0021" extendProperty="class='user_add_select'"/></td>
		       <td align="right" width="12%"><bean:message bundle="archive" key="archive.gp.GP_ADDR"/>：</td>
		       <td width="20%"><peis:text type="text" name="gpAddr" styleClass="user_add_input"/></td>
		      </tr>
		      <tr>
		       <td align="right"><bean:message bundle="archive" key="archive.btl"/>：</td>
		       <td><peis:selectlist name="btl" sql="COMMON0024" extendProperty="class='user_add_select'"/></td>
		       <td align="right"><bean:message bundle="archive" key="archive.meterProNo"/>：</td>
		       <td><peis:selectlist name="proNo" sql="METERPN0001" extendProperty="class='user_add_select'"/></td>
		       <td align="right"><bean:message bundle="archive" key="archive.meterFac"/>：</td>
		       <td><peis:selectlist name="madeFac" sql="COMMON0015" extendProperty="class='user_add_select'"/></td>
		      </tr>
		      <tr>
		       <td align="right"><bean:message bundle="archive" key="archive.isControl"/>：</td>
		       <td>
		       	<select name="sfdk" class='user_add_select'>
		       		<option value="1"><bean:message bundle="archive" key="archive.meter.isControl"/></option>
		       		<option value="0"><bean:message bundle="archive" key="archive.meter.isNoControl"/></option>
		       	</select>
		       </td>
		       <td align="right"><bean:message bundle="archive" key="archive.meter.ct"/>：</td>
		       <td><peis:selectlist name="ctRatio" sql="COMMON0022" extendProperty="class='user_add_select'"/></td>
		       <td align="right"><bean:message bundle="archive" key="archive.meter.pt"/>：</td>
		       <td><peis:selectlist name="ptRatio" sql="COMMON0023" extendProperty="class='user_add_select'"/></td>
		      </tr>
		      <tr>
		       <td align="right"><bean:message bundle="archive" key="archive.isCopy"/>：</td>
		       <td><input type="checkbox" name="sfjc" id="checkbox1" value="1" onclick="jc();" checked="checked"></td>
		      </tr>  
		      <tr>
		        <td colspan="6">
		        <table width="98%" id="tabel3"  border=0 cellpadding=0 cellspacing=1 align="center">
				    <tr>
				     <td align="right" width="12%"><bean:message bundle="archive" key="archive.gp.LOGICAL_ADDR"/>：</td>
				     <td align="left" width="20%">
				     <peis:selectlist name="termId" sql="JZQINPB0001" onChange="viewGm();" extendProperty="class='user_add_select'"/>
				     </td>
				     <td align="right" width="12%"><bean:message bundle="archive" key="archive.gm"/>：</td>
				     <td ><peis:selectlist name="gmId" sql="TG0011" extendProperty="class='user_add_select'"/></td>
				     <td align="left"></td>
				     <td ></td>
				    </tr>
				    <tr>
				     <td align="right" width="12%"><bean:message bundle="archive" key="archive.tg.boxSn"/>：</td>
				     <td align="left" width="20%">
				     <select  name="gmId" class="user_add_select"></select>
				     </td>
				     <td align="right" width="12%"><bean:message bundle="archive" key="archive.tg.lineSn"/>：</td>
				     <td ><select  name="gmId" class="user_add_select"></select></td>
				      <td align="right" width="12%"><bean:message bundle="archive" key="archive.tg.relay2"/>：</td>
				      <td width="20%"> <select  name="gmId" class="user_add_select"></select></td>
				     <td align="right"></td>
				     <td ></td>
		    </tr>
		        </table>
		        </td>
		      </tr>  
		    </table>
		    <br>
		  </logic:present>
<br/>
<table width="100%">
<tr>
<td align="right" colspan="6" width="100%">
   		<input type="button" name="Update" onclick="update();" value="<bean:message  key='global.save'/>" class="input_ok">
		<input type="button" name="button2" value="<bean:message  key='global.cancel'/>" onclick="javascript:window.close();"  class="input_ok">
   		&nbsp;&nbsp;&nbsp;&nbsp;
   	</td>
   	</tr>
</table>
  </html:form>
 <!--    </div>-->
  <iframe name="resultPage" frameborder=0 width=0 height=0></iframe>
 
</body>
</html>
