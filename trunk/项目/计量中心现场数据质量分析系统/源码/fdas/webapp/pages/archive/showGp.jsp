
<!--
  本页的作用是显示大客户信息的内容
  内容包括：大用户信息，终端，电表，模块表，采集点，总加组信息,开关

  并在此提供新增、编辑与删除的入口

-->
<%@include file="../../commons/taglibs.jsp"%>



<%@page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title><bean:message bundle="archive" key="archive.edit.gp"/></title>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<peis:contextPath />/css/window.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript">

function init(gpChar,protocolNo,gpStatus,computeFlag,termId,mpId){
	document.all.gpChar.value = gpChar;
	document.all.protocolNo.value = protocolNo;
	document.all.gpStatus.value = gpStatus;
	if(computeFlag=="1"){
		document.all.computeFlag.checked = 'true';
	}
	document.all.termId.value = termId;
	document.all.mpId.value = mpId;
	
	
}  

function changeChar(type){
    
	if("2"==type) {
		document.getElementById("isPluse").style.display='';
	}
	else{
		document.getElementById("isPluse").style.display='none';
	}
}


</script>
</head>
<body style="overflow-x:hidden;overflow-y:hidden;" bgcolor="#f7f7ff" onload="init('<bean:write name="gp" property="gpChar"/>','<bean:write name="gp" property="protocolNo"/>',
								'<bean:write name="gp" property="gpStatus"/>','<bean:write name="gp" property="computeFlag"/>',
								'<bean:write name="gp" property="termId"/>','<bean:write name="gp" property="mpId"/>');changeChar('<bean:write name="gp" property="gpChar"/>')">
<ul class="e_title">
    <li class="e_titleon"><a href="#"><bean:message bundle="archive" key="archive.gp.info"/></a>
  </ul>
   <div class="user_add_table">
  <html:form  action="/archive/gpAction" method="post" target="resultPage">
		  <input type="hidden" name="className" value="AGp">
		  <input type="hidden" name="gpSn_old" value="<bean:write name="gp" property="gpSn"/>">
		  <input type="hidden" name="gpAddr_old" value="<bean:write name="gp" property="gpAddr"/>">
		  <input type="hidden" name="action" value="saveOrUpdate">
		  <logic:present name="custId">
		  <input type="hidden" name="objectId" value="<bean:write name='custId'/>">
		  <input type="hidden" name="gpType" value="1">
		  </logic:present>
		  <logic:present name="tgId">
		  <input type="hidden" name="objectId" value="<bean:write name='tgId'/>">
		  <input type="hidden" name="gpType" value="2">
		  </logic:present>
  <table width="96%" border="0" align="center"  cellpadding="0" cellspacing="0" >
    <tr>
      <tr>
		 <td align="right" width="20%"><bean:message bundle="archive" key="archive.gp.no"/>：</td>
		 <td width="30%">
		 <input type="hidden" readonly="readonly" name="gpId" value="<bean:write name="gp" property="gpId"/>"><bean:write name="gp" property="gpId"/></td>
      
    </tr>
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.gp.LOGICAL_ADDR"/>：</td>
      <td width="30%">
                <logic:notEmpty name="custId">
		     	<peis:selectlist name="termId" sql="TERMINGP0001" extendProperty="class='user_add_input'"/>
		     	</logic:notEmpty>
		     	<logic:notEmpty name="tgId">
		     	<peis:selectlist name="termId" sql="TERMINTG0001"/>
		     	</logic:notEmpty>
      </td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.gpChar"/>：</td>
      <td width="30%">
         <select name="gpChar"  onchange="selectedIndex=0" class="user_add_input">
            <option  value="1"><bean:message bundle="archive" key="archive.gp.char.1"/>
            <option  value="2"><bean:message bundle="archive" key="archive.gp.char.2"/>

            <option  value="4"><bean:message bundle="archive" key="archive.gp.char.4"/>
            </option>
         </select>
         
      </td>
    </tr> 
    <!-- 
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.gp.CT_TIMES"/>：</td>
      <td width="30%"><input type="text" name="ctTimes" class="user_add_input" value="<bean:write name="gp" property="ctTimes"/>"/></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.gp.PT_TIMES"/>：</td>
      <td width="30%"><input type="text" name="ptTimes" class="user_add_input" value="<bean:write name="gp" property="ptTimes"/>"></td>
    </tr>
     --> 
    <tr>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.sn"/>：</td>
      <td width="30%"><input type="text" name="gpSn" class="user_add_input" value="<bean:write name="gp" property="gpSn"/>"></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.gp.gpAddr"/>：</td>
      <td width="30%"><input type="text" name="gpAddr" value="<bean:write name="gp" property="gpAddr"/>" class="user_add_input"></td>
    </tr>  
    <tr>
      <td align="right" width="20%">端口号：</td>
      <td width="30%"><input type="text" name="port" class="user_add_input" value="<bean:write name="gp" property="port"/>" onkeyup="this.value=this.value.replace(/\D/gi,'')"></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.PROTOCOL_NO"/>：</td>
      <td width="30%"><peis:selectlist name="protocolNo" sql="METERPN0001" extendProperty="class='user_add_input'"/></td>
    </tr> 
    <tr>
      <td align="right" width="20%">电表资产编号：</td>
      <td width="30%">
              <logic:notEmpty name="custId">
		     	<peis:selectlist name="mpId" sql="METERMPINGP0001" extendProperty="class='user_add_input'"/>
		      </logic:notEmpty>
		      <logic:notEmpty name="tgId">
		      	<peis:selectlist name="mpId" sql="MPINTG0001" extendProperty="class='user_add_input'"/>
		      </logic:notEmpty>
      </td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.gp.status"/>：</td>
      <td width="30%"><select name="gpStatus" class="user_add_input">
            <option value="1"><bean:message bundle="archive" key="archive.gp.status.valid"/></option>
            <option value="0"><bean:message bundle="archive" key="archive.gp.status.invalid"/></option>
          </select></td>
    </tr>   
    <tr>
      <td align="right" width="20%">电量计算标识：</td>
      <td width="30%">
      <logic:equal name="computeFlag"  value="1">
      <input type="checkbox" name="computeFlag" value="1" checked>
     </logic:equal>
     <logic:notEqual name="computeFlag"   value="1">
      <input type="checkbox" name="computeFlag" value="1" >
    </logic:notEqual>
      </td>
      <td align="right" width="20%">功率计算标识：</td>
      <td width="30%">
      <logic:equal name="sucratCptId"   value="1">
      <input type="checkbox" name="sucratCptId" value="1" checked>
      </logic:equal>
      <logic:notEqual name="sucratCptId"  value="1">
       <input type="checkbox" name="sucratCptId" value="1" >
      </logic:notEqual>
      </td>
    </tr>
     <tr id="isPluse">
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.meterConstant"/>：</td>
      <td width="30%"><input type="text" name="meterConstant" class="user_add_input" value="<bean:write name="gp" property="meterConstant"/>"/></td>
      <td align="right" width="20%"><bean:message bundle="archive" key="archive.pluseConstant"/>：</td>
      <td width="30%"><peis:selectlist name="pluseConstant" sql="METER0006"/></td>
    </tr>    
    <tr>
   	<td align="right" colspan="6">
   	    <input type="submit" name="Update" value="<bean:message key='global.save'/>" class="input_ok">
   		<input type="button" value="<bean:message key='global.cancel'/>" onclick="javascript:parent.window.close();" class="input_ok">
   		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
   	</td>
   </tr>  
  </table>
  </html:form>
  <iframe name="resultPage" frameborder=0 width=0 height=0></iframe>
  </div>
</body>
</html>
