<%@ page language="java" pageEncoding="UTF-8"%>

<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <title><bean:message bundle="archive" key="archive.bitcust.info"/></title>
	<meta http-equiv="Content-Language" content="zh-cn">
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<link href="<peis:contextPath/>/css/window.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" language="javascript">
		var contextPath = "<peis:contextPath/>";
		
		//更新检查是否填写
		function update() {
			if(document.getElementsByName("custName")[0].value==""){
				alert("<bean:message bundle='archive' key='archive.alert.cust.custName'/>");
				return false;
			}
			var doublePatrn=/^([1]|[0]|([0]\.\d*))$/;
			var integerPatrn = /^[1-9]\d*$/;
			var number=/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;

			
			var strContractCapa = document.getElementsByName("contractCapa")[0].value;
			if(strContractCapa != "" && !number.exec(strContractCapa)){
				alert("<bean:message bundle='archive' key='archive.contractCapa'/>"+"<bean:message bundle='archive' key='archive.isNotNumber'/>");
				return false;
			}
			
			var strRunCapa = document.getElementsByName("runCapa")[0].value;
			if(strRunCapa != "" && !number.exec(strRunCapa)){
				alert("<bean:message bundle='archive' key='archive.runCapa'/>"+"<bean:message bundle='archive' key='archive.isNotNumber'/>");
				return false;
			}
			
			var strSecuCapa = document.getElementsByName("secuCapa")[0].value;
			if(strSecuCapa != "" && !number.exec(strSecuCapa)){
				alert("<bean:message bundle='archive' key='archive.secuCapa'/>"+"<bean:message bundle='archive' key='archive.isNotNumber'/>");
				return false;
			}
			
			var strPowerFactor = document.getElementsByName("powerFactor")[0].value;
			if(strPowerFactor != "" && !number.exec(strPowerFactor)){
				alert("<bean:message bundle='archive' key='archive.powerFactor'/>"+"<bean:message bundle='archive' key='archive.isNotNumber'/>");
				return false;
			}
			
			var strRmDay = document.getElementsByName("rmDay")[0].value;
			if(strRmDay != ""){
			    var intRmDay = parseInt(strRmDay);
			    if(!intRmDay || intRmDay < 0 || intRmDay > 28)
			    {
			       alert("<bean:message bundle='archive' key='archive.validator.rmday'/>");
				   return false;
			    }
			}
			document.getElementsByName("Update")[0].disabled = true;
			document.forms[0].submit();
		}
	</script>
  </head>
  <body>
   <div class="user_add_table">
    <html:form action="/archive/customerAction" method="post" target="hideFrame" >
     <input type="hidden" name="action" value="saveOrUpdate"/>
     <table width="96%" border="0" align="center"  cellpadding="0" cellspacing="0" >
    <tr>
      <!-- <td id="msg" colspan="6"></td> -->
      <peis:text type="hidden" name="custType"/>
    </tr>
    <tr>
      <td align="right" width="13%">
      <input type="hidden" type="text" name="custId" value="<bean:write name='cust' property='custId'/>">
      <font color="red">* </font><bean:message bundle="archive" key="archive.custNo"/>：</td>
      <td width="20%">
	      <input class="user_add_input" type="text" readonly="readonly" type="text" name="custNo" value="<bean:write name='cust' property='custNo'/>">
      </td> 
      
      <td align="right" width="13%"><font color="red">* </font>
      <bean:message bundle="archive" key="archive.custName"/>：</td>
      
      <td colspan="3" width="54%">
      <input class="user_add_input3" type="text" name="custName" value="<bean:write name='cust' property='custName'/>" >
      </td>
    </tr>
    <tr>
     <td align="right" width="13%"><bean:message bundle="archive" key="archive.org"/>：</td>
      <td width="20%"><peis:selectlist name="orgNo" sql="ORG0002" extendProperty="class='user_add_select'" /></td>
      <td align="right" width="13%"><bean:message bundle="archive" key="archive.addr"/>：</td>
      <td colspan="3" width="54%"><input class="user_add_input3" type="text" name="custAddr" value="<bean:write name='cust' property='custAddr'/>"></td>
    </tr>
    <tr>
      <td width="13%" align="right"><bean:message bundle="archive" key="archive.contractCapa"/>：</td>
      <td width="20%"><input class="user_add_input" type="text" name="contractCapa" value="<bean:write name='cust' property='contractCapa' format='#.####'/>" >
        KVA</td>
      <td width="13%" align="right"><bean:message bundle="archive" key="archive.runCapa"/>：</td>
      <td width="20%"><input class="user_add_input" type="text" name="runCapa" value="<bean:write name='cust' property='runCapa' format='#.####'/>">
        KVA</td>
      <td width="13%" align="right"><bean:message bundle="archive" key="archive.secuCapa"/>：</td>
      <td width="21%"><input class="user_add_input" type="text" name="secuCapa" value=<bean:write name='cust' property='secuCapa' format='#.####'/> >
        KVA</td>
    </tr>
    <tr>
      <td align="right"><bean:message bundle="archive" key="archive.isMulLine"/>：</td>
      <td><peis:selectlist name="isMulLine" sql="COMMON0005" extendProperty="class='user_add_select'" /></td>
      <td align="right"><bean:message bundle="archive" key="archive.trade"/>：</td>
      <td><peis:selectlist name="tradeSort" sql="COMMON0002" extendProperty="class='user_add_select'" /></td>
      <td align="right"><bean:message bundle="archive" key="archive.elecAppType"/>：</td>
      <td><peis:selectlist name="elecAppType" sql="COMMON0003" extendProperty="class='user_add_select'" /></td>
    </tr>
    <tr>
      <td align="right"><bean:message bundle="archive" key="archive.hPowerType"/>：</td>
      <td><peis:selectlist name="highPowerType" sql="COMMON0010" extendProperty="class='user_add_select'"  /></td>
      <td align="right"><bean:message bundle="archive" key="archive.loadChar"/>：</td>
      <td><peis:selectlist name="loadChar" sql="COMMON0006" extendProperty="class='user_add_select'"   /></td>
      <td align="right"><bean:message bundle="archive" key="archive.voltGrade"/>：</td>
      <td><peis:selectlist name="voltGrade" sql="COMMON0007" extendProperty="class='user_add_select'" /></td>
    </tr>
    <tr>
      <td align="right"><bean:message bundle="archive" key="archive.powerFactor"/>：</td>
      <td><input class="user_add_input" type="text" name="powerFactor"  value="<bean:write name='cust' property='powerFactor'/>"></td>
      <td align="right"><bean:message bundle="archive" key="archive.shiftNo"/>：</td>
      <td><peis:selectlist name="shiftNo" sql="COMMON0008" extendProperty="class='user_add_select'" /></td>
      <td align="right"><bean:message bundle="archive" key="archive.holiday"/>：</td>
      <td><input class="user_add_input" type="text" name="holiday" value="<bean:write name='cust' property='holiday'/>"></td>
    </tr>
    <tr>
      <td align="right"><bean:message bundle="archive" key="archive.status"/>：</td>
	  <td><peis:selectlist name="custStatus" sql="COMMON0004" extendProperty="class='user_add_select'" /></td>
      <td align="right"><bean:message bundle="archive" key="archive.contact"/>：</td>
      <td><input class="user_add_input" type="text" name="contact" value="<bean:write name='cust' property='contact'/>"></td>
      <td align="right"><bean:message bundle="archive" key="archive.phone"/>：</td>
      <td><input class="user_add_input" type="text" name="phone" value="<bean:write name='cust' property='phone'/>"></td>
    </tr>
    <tr>
	  <td align="right"><bean:message bundle="archive" key="archive.rmDay"/>：</td>
	  <td><input class="user_add_input" type="text" name="rmDay" value="<bean:write name='cust' property='rmDay'/>"></td>
      <td align="right"><bean:message bundle="archive" key="archive.rmSectNo"/>：</td>
	  <td><input class="user_add_input" type="text" name="rmSectNo" value="<bean:write name='cust' property='rmSectNo'/>"></td>
	  <td align="right"><bean:message bundle="archive" key="archive.rmSn"/>：</td>
	  <td><input class="user_add_input" type="text" name="rmSn" value="<bean:write name='cust' property='rmSn'/>"></td>
	
	</tr>
	<tr>
	  <td align="right" rowspan="2"><bean:message bundle="archive" key="archive.remark"/>：</td>
      <td colspan="3" rowspan="2"><textarea class="input_textarea3" name="remark" ><bean:write name='cust' property='remark'/></textarea></td>
    </tr>
    <tr>
    </tr>
     <tr>
   	<td align="right" colspan="6">
   		<input class="input_save"  type="button" name="Update"  value="<bean:message key='global.save'/>" onclick="update();">
   		<input class="input_cancel" type="button" value="<bean:message key='global.cancel'/>" onclick="javascript:parent.window.close();">
   		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   	</td>
   </tr>
  </table>
   </html:form>
   </div>
   <iframe name="hideFrame" src="" frameborder=0 width=0 height=0></iframe>
  </body>
</html:html>
