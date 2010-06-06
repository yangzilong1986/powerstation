<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  
  <head>
    <title><bean:message bundle="archive" key="archive.new.pg"/></title>
	<meta http-equiv="Content-Language" content="zh-cn">
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<link href="<peis:contextPath/>/css/window.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
	<script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
	<script type="text/javascript" src="<peis:contextPath/>/dwr/engine.js"></script>
	<script type="text/javascript" src="<peis:contextPath/>/dwr/util.js"></script>
	<script type="text/javascript" src="<peis:contextPath/>/dwr/interface/cust.js"></script>
	<script type="text/javascript">
    var contextPath = "<peis:contextPath/>";	
    function validate(){
        if(document.getElementsByName("custNo")[0].value == "") {
            alert("<bean:message bundle='archive' key='archive.alert.cust.custNo'/>");
            return false;
        }
    
        if(document.getElementsByName("custName")[0].value == "") {
            alert("<bean:message bundle='archive' key='archive.alert.cust.custName'/>");
            return false;
        }
    
        var doublePatrn=/^([1]|[0]|([0]\.\d*))$/;
        var integerPatrn = /^[1-9]\d*$/;
    
        var strContractCapa = document.getElementsByName("contractCapa")[0].value;
        if(strContractCapa != "" && !integerPatrn.exec(strContractCapa)){
            alert("<bean:message bundle='archive' key='archive.contractCapa'/>"+"<bean:message bundle='archive' key='archive.isNotNumber'/>");
            return false;
        }
    
        document.getElementsByName("Update")[0].disabled = true;
        return true;
    }
	</script>
  </head>

  <body >
    <ul class="e_title">
    <li class="e_titleon"><a href="#"><bean:message bundle="archive" key="archive.cust.tab_bigcust"/></a>
    </ul>
 <div class="user_add_table">  
  <html:form action="/archive/customerAction" onsubmit="return validate()">
  <input type="hidden" name="action" value="saveOrUpdate"/>
  <table width="96%" border="0" align="center"  cellpadding="0" cellspacing="0" >
    <tr>
     <td colspan="4"><font color="red"> <html:errors bundle="archive" property="errs" /> </font></td>
    </tr>
    <tr>
      <td align="right" width="13%"><font color="red">* </font><bean:message bundle="archive" key="archive.custNo"/>：</td>
      <td width="20%">
	      <input class="user_add_input" type="text" name="custNo"><html:errors bundle="archive" property="custNo" />
      </td> 
      
      <td align="right" width="13%"><font color="red">* </font>
      <bean:message bundle="archive" key="archive.custName"/>：</td>
      
      <td colspan="3" width="54%">
      <input class="user_add_input3" type="text" name="custName" >
      </td>
    </tr>
    <tr>
     <td align="right" width="13%"><bean:message bundle="archive" key="archive.org"/>：</td>
      <td width="20%"><peis:selectlist name="orgNo" sql="ORG0002" extendProperty="class='user_add_select'"/></td>
     
      <td align="right" width="13%"><bean:message bundle="archive" key="archive.addr"/>：</td>
      <td colspan="3" width="54%"><input class="user_add_input3" type="text" name="custAddr" ></td>
    </tr>
    <tr>
      <td width="13%" align="right"><bean:message bundle="archive" key="archive.contractCapa"/>：</td>
      <td width="20%"><input class="user_add_input" type="text" name="contractCapa" >
        KVA</td>
      <td width="13%" align="right"><bean:message bundle="archive" key="archive.runCapa"/>：</td>
      <td width="20%"><input class="user_add_input" type="text" name="runCapa" >
        KVA</td>
      <td width="13%" align="right"><bean:message bundle="archive" key="archive.secuCapa"/>：</td>
      <td width="21%"><input class="user_add_input" type="text" name="secuCapa" >
        KVA</td>
    </tr>
    <tr>
      <td align="right"><bean:message bundle="archive" key="archive.isMulLine"/>：</td>
      <td><peis:selectlist name="isMulLine" sql="COMMON0005" extendProperty="class='user_add_select'"/></td>
      <td align="right"><bean:message bundle="archive" key="archive.trade"/>：</td>
      <td><peis:selectlist name="tradeSort" sql="COMMON0002" extendProperty="class='user_add_select'"/></td>
      <td align="right"><bean:message bundle="archive" key="archive.elecAppType"/>：</td>
      <td><peis:selectlist name="elecAppType" sql="COMMON0003" extendProperty="class='user_add_select'"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message bundle="archive" key="archive.hPowerType"/>：</td>
      <td><peis:selectlist name="hPowerType" sql="COMMON0010" extendProperty="class='user_add_select'"/></td>
      <td align="right"><bean:message bundle="archive" key="archive.loadChar"/>：</td>
      <td><peis:selectlist name="loadChar" sql="COMMON0006" extendProperty="class='user_add_select'"/></td>
      <td align="right"><bean:message bundle="archive" key="archive.voltGrade"/>：</td>
      <td><peis:selectlist name="voltGrade" sql="COMMON0007" extendProperty="class='user_add_select'"/></td>
    </tr>
    <tr>
      <td align="right"><bean:message bundle="archive" key="archive.powerFactor"/>：</td>
      <td><input class="user_add_input" type="text" name="powerFactor" ></td>
      <td align="right"><bean:message bundle="archive" key="archive.shiftNo"/>：</td>
      <td><peis:selectlist name="shiftNo" sql="COMMON0008" extendProperty="class='user_add_select'"/></td>
      <td align="right"><bean:message bundle="archive" key="archive.holiday"/>：</td>
      <td><input class="user_add_input" type="text" name="holiday" >1,3,5</td>
    </tr>
    <tr>
      <td align="right"><bean:message bundle="archive" key="archive.status"/>：</td>
	  <td><peis:selectlist name="custStatus" sql="COMMON0004" extendProperty="class='user_add_select'"/></td>
      <td align="right"><bean:message bundle="archive" key="archive.contact"/>：</td>
      <td><input class="user_add_input" type="text" name="contact" ></td>
      <td align="right"><bean:message bundle="archive" key="archive.phone"/>：</td>
      <td><input class="user_add_input" type="text" name="phone" ></td>
    </tr>
    <tr>
	  <td align="right"><bean:message bundle="archive" key="archive.rmDay"/>：</td>
	  <td><input class="user_add_input" type="text" name="rmDay" value="1"></td>
      <td align="right"><bean:message bundle="archive" key="archive.rmSectNo"/>：</td>
	  <td><input class="user_add_input" type="text" name="rmSectNo" ></td>
	  <td align="right"><bean:message bundle="archive" key="archive.rmSn"/>：</td>
	  <td><input class="user_add_input" type="text" name="rmSn" ></td>
	
	</tr>
	<tr>
	  <td align="right" rowspan="2"><bean:message bundle="archive" key="archive.remark"/>：</td>
      <td colspan="3" rowspan="2"><textarea class="input_textarea3" name="remark"></textarea></td>
    </tr>
    <tr>
    </tr>
     <tr>
   	<td align="right" colspan="6">
   		<input class="input_save"  type="submit" name="Update" value="<bean:message  key='global.save'/>" >
   		<input class="input_cancel" type="button" value="<bean:message key='global.cancel'/>" onclick="javascript:parent.window.close();">
   		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   	</td>
   </tr>
  </table>
   </html:form>
   </div>
  </body>
</html:html>
