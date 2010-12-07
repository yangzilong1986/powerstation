<%@include file="../../commons/taglibs.jsp"%>



<%@page contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<peis:contextPath />/css/window.css" rel="stylesheet" type="text/css">

<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<html>
<head>
<title><bean:message bundle="archive" key="archive.new.gp"/></title>
</head>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";
function changeChar(type){
	if("2"==type) {
		document.getElementById("isPluse").style.display='';
	}
	else{
		document.getElementById("isPluse").style.display='none';
	}
}

function save(){
	if(""==document.getElementsByName("termId")[0].value) {
		alert("<bean:message bundle="archive" key="archive.gp.termnonull"/>");
		return false;
	}
	var integerPatrn = /^[1-9]\d*$/;
	var strCt = document.getElementsByName("ctTimes")[0].value;
			if(strCt != "" && !integerPatrn.exec(strCt)){
				alert("<bean:message bundle='archive' key='archive.gp.CT_TIMES'/>"+"<bean:message bundle='archive' key='archive.isNotNumber'/>");
				return false;
			}
    var strPt = document.getElementsByName("ptTimes")[0].value;
			if(strPt != "" && !integerPatrn.exec(strPt)){
				alert("<bean:message bundle='archive' key='archive.gp.PT_TIMES'/>"+"<bean:message bundle='archive' key='archive.isNotNumber'/>");
				return false;
			}
	document.getElementsByName("entry")[0].disabled = true;
	document.forms[0].submit();
}

function initButton(){
	 var termObj = document.all.termId;
	   if(termObj.options.length == 0 || termObj.value == '0')
       {
          document.getElementById("entry").disabled = true;
       }
}

</script>
<body bgcolor="#f7f7ff"  style="overflow-x:hidden;overflow-y:hidden;" onload="initButton();">
<ul class="e_title">
    <li class="e_titleon"><a href="#"><bean:message bundle="archive" key="archive.gp.info"/></a>
  </ul>
  <div class="user_add_table">
  <html:form  action="do/archive/addArchiveInTg" method="post" target="newGpPage">
          <input type="hidden" name="className" value="AGp">
		  <input type="hidden" name="objectId" value="<bean:write name='tgId'/>">
		  <input type="hidden" name="gpType" value="2">
		  <input type="hidden" name="action" value="addGpInTg">
  <table width="96%" border="0" align="center"  cellpadding="0" cellspacing="0" >
   
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.gp.LOGICAL_ADDR"/>：</td>
      <td width="30%">
                <logic:notEmpty name="custId">
		     	<peis:selectlist name="termId" sql="TERMINGP0001" extendProperty="class='user_add_input'"/>
		     	</logic:notEmpty>
		     	<logic:notEmpty name="tgId">
		     	<peis:selectlist name="termId" sql="TERMINTG0001" extendProperty="class='user_add_input'"/>
		     	</logic:notEmpty>
      </td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.gpChar"/>：</td>
      <td width="30%">
      	<peis:selectlist name="gpChar" sql="COMMON0020" extendProperty="class='user_add_input'" onChange="changeChar(this.value);"/>
      </td>
    </tr> 
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.gp.CT_TIMES"/>：</td>
      <td width="30%"><input type="text" name="ctTimes" class="user_add_input"/></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.gp.PT_TIMES"/>：</td>
      <td width="30%"><input type="text" name="ptTimes" class="user_add_input"></td>
    </tr>
    <tr>
      <td align="right" width="20%"><font color="red">* </font><bean:message bundle="archive" key="archive.sn"/>：</td>
      <td width="30%"><input type="text" name="gpSn" class="user_add_input" onkeyup="this.value=this.value.replace(/\D/gi,'')"></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.gp.gpAddr"/>：</td>
      <td width="30%"><input type="text" name="gpAddr" class="user_add_input"></td>
    </tr>  
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.PROTOCOL_NO"/>：</td>
      <td width="30%"><peis:selectlist name="protocolNo" sql="METERPN0001" extendProperty="class='user_add_input'"/></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.mpId"/>：</td>
      <td width="30%"><peis:selectlist name="mpId" sql="MPINTG0001" extendProperty="class='user_add_input'"/></td>
    </tr>   
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.gp.status"/>：</td>
      <td width="30%"><select name="gpStatus" class="user_add_input">
		      	<option value="1"><bean:message bundle="archive" key="archive.gp.status.valid"/></option>
		      	<option value="0"><bean:message bundle="archive" key="archive.gp.status.invalid"/></option>
		      </select></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.compute_flag"/>：</td>
      <td width="30%"><input type="checkbox" name="computeFlag" value="1"></td>
    </tr>
    <tr id="isPluse" style="display:none">
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meterConstant"/>：</td>
      <td width="30%"><peis:text type="text" name="meterConstant"/></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.pluseConstant"/>：</td>
      <td width="30%"><peis:selectlist name="pluseConstant" sql="METER0006"/></td>
    </tr>  
    <tr>
   	<td align="right" colspan="6">
   		<input type="button" name="entry" onclick="save();"  value="<bean:message  key='global.save'/>" class="input_ok"/>
   		<input type="button"  class="input_ok" value="<bean:message  key='global.cancel'/>" onclick="javascript:parent.window.close();">
   		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   	</td>
   </tr>  
  </table>
  </html:form>
  <iframe name="newGpPage" frameborder=0 width=0 height=0></iframe>
  </div>
</body>
</html>
