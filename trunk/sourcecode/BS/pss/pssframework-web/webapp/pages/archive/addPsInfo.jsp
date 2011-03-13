<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>漏保信息</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<script type="text/javascript">
var contextPath = '${ctx}';


//初始化接脉冲
function initIsPulse(){
   var gpSn2=$('#gpSn2').val();
   if(gpSn2!=''){
     $('#pulseShow').show();
     $('#isPulse').attr("checked","true");
   }else{
     $('#pulseShow').hide();
   }
}
//接入终端
function addTerminal(){
   var params = {
                 "sqlCode":"AL_ARCHIVE_0040",
                 "pageRows":20,
                 "logicalAddr":""
               };
   var url=contextPath+"/archive/termListQueryInBigCust.do?action=normalMode&"+ jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
   showDialogBox("终端定位",url, 495, 800);
}

//连接远传表
function meterState(){
   var meterState=$("#meterType").val();
   var mpId = $("input[name='mpId']").val();
   if(mpId == ''){ //新增电表
       if(meterState=="1"){
	      var url="${ctx}/jsp/archive/remoteMeter.jsp";
	      window.location.href=url;
	   }
   }
}


function lpad (object,lenth) {
    var value = object.value;
    var len = lenth-value.length;
    var s = new Array(len);
    for(var i = 0; i< len;i++){
      s[i] = '0';
    }
    object.value = s.join('')+value;
}

function lpadString (str,lenth) {
    var value = str;
    var len = lenth-value.length;
    var s = new Array(len);
    for(var i = 0; i< len;i++){
      s[i] = '0';
    }
    str = s.join('')+value;
    return str;
}



</script>
</head>
<body>
<div class="electric_lcon" id="electric_Con">
<ul class="default" id="electric_Con_1" style="padding: 5px;">
  <div class="tab"><span>漏电保护器</span></div>
  <div class="da_mid"
    style="display: block; overflow-y: auto; overflow-x: auto; width: expression((     document.documentElement.clientWidth ||   document.body.clientWidth) -10 ); height: expression(((        document.documentElement.clientHeight ||     document.body.clientHeight) -35 ) );">
  <div><form:form action="/archive/psinfo" modelAttribute="psinfo">
    <input type="hidden" id="<%=SystemConst.CONTROLLER_METHOD_TYPE%>" name="<%=SystemConst.CONTROLLER_METHOD_TYPE%>"
      value="${_type}" />
    <c:choose>
      <c:when test="${_type=='edit' || _type=='new'}">
        <c:set var="disabled" value="false"></c:set>
      </c:when>
      <c:otherwise>
        <c:set var="disabled" value="true"></c:set>
      </c:otherwise>
    </c:choose>
    <form:hidden path="gpInfo.objectId" />
      <input type="hidden" name="gpInfo.gpType" id="gpInfo.gpType" value="2">
 	  <input type="hidden" name="gpInfo.gpChar" id="gpInfo.gpChar" value="1">
    <div id="main"><input type="hidden" id="channelType" name="channelType" value="1" /> <input type="hidden"
      id="pwAlgorith" name="pwAlgorith" value="0" /> <input type="hidden" id="pwContent" name="pwContent" value="8888" />
    <input type="hidden" id="mpExpressMode" name="mpExpressMode" value="3" />
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
      <tr height="30px">
        <td width="20%" align="right" class="green"><font color="red">* </font>资产编号：</td>
        <td width="30%"><security:authorize
          ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:input path="assetNo" cssClass="required input2" disabled="${disabled}" />
        </security:authorize><security:authorize
          ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:input path="assetNo" cssClass="required input2" disabled="${disabled}" />
        </security:authorize></td>
        <td width="20%" align="right" class="green">集中器地址：</td>
        <td width="30%"><security:authorize
          ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:select path="terminalInfo.termId" items="${termList}" id="termAddr" itemLabel="logicalAddr"
            itemValue="termId" cssStyle="width:155px;" disabled="${disabled}" />
        </security:authorize><security:authorize
          ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:select path="terminalInfo.termId" items="${termList}" id="termAddr" itemLabel="logicalAddr"
            itemValue="termId" cssStyle="width:155px;" disabled="${disabled}"
            cssClass="validate-ajax-${ctx}/archive/psinfo/checkGpSn.json validate-ajax-${ctx}/archive/psinfo/checkGpAddr.json" />
        </security:authorize></td>
      </tr>
      <tr height="30px">
        <td align="right" class="green"><font color="red">* </font>漏保地址：</td>
        <td><!-- gpAddr --> <input type="hidden" name="gpInfo.gpAddrOld" value="${psinfo.gpInfo.gpAddr}" /> <security:authorize
          ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:input path="gpInfo.gpAddr" id="gpAddr" maxlength="20" cssClass="required input2" disabled="${disabled}" />
        </security:authorize><security:authorize
          ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:input path="gpInfo.gpAddr" id="gpAddr" maxlength="12" onchange="lpad(this,12)" disabled="${disabled}"
            cssClass="required validate-number validate-ajax-${ctx}/archive/psinfo/checkGpAddr.json" />
        </security:authorize></td>
        <td align="right" class="green"><font color="red">* </font>测量点序号：</td>
        <td><!-- gpsn --> <input type="hidden" name="gpInfo.gpSnOld" value="${psinfo.gpInfo.gpSn}" /><security:authorize
          ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:input path="gpInfo.gpSn"  id="gpSn" cssClass="required input2 validate-number" disabled="${disabled}" />
        </security:authorize><security:authorize
          ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:input path="gpInfo.gpSn" id="gpSn"
            cssClass="required validate-number validate-ajax-${ctx}/archive/psinfo/checkGpSn.json"
            disabled="${disabled}" />
        </security:authorize></td>
      </tr>
      <tr height="30px">
        <td align="right" class="green"><font color="red">* </font>漏保名称：</td>
        <td><security:authorize
          ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:input path="psName" cssClass="required input2" disabled="${disabled}" />
        </security:authorize><security:authorize
          ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:input path="psName" cssClass="required input2" disabled="${disabled}" />
        </security:authorize></td>
        <td align="right" class="green">安装地址：</td>
        <td>${istAddr}</td>
      </tr>
      <tr height="30px">
        <td align="right" class="green">试跳时间：</td>
        <td><security:authorize
          ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <span><form:checkbox path="autoTest" id="autoTest" title="自动试跳" value="1" disabled="${disabled}" />自动试跳&nbsp;</span>
          <span id="time"><form:select path="testDay" items="${dayList}" id="testTime" itemLabel="name"
            itemValue="code" disabled="${disabled}" />日&nbsp;<form:select path="testTime" items="${clockList}"
            id="testTime" itemLabel="name" itemValue="code" disabled="${disabled}" />点</span>
        </security:authorize><security:authorize
          ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:checkbox path="autoTest" id="autoTest" title="自动试跳" value="1" disabled="${disabled}" />自动试跳&nbsp;<span
            id="time"><form:select path="testDay" items="${dayList}" id="testTime" itemLabel="name"
            itemValue="code" disabled="${disabled}" />日&nbsp;<form:select path="testTime" items="${clockList}"
            id="testTime" itemLabel="name" itemValue="code" disabled="${disabled}" />点</span>
        </security:authorize></td>
      </tr>
    </table>
    </div>
    <div class="tab" id="detail_tile"><span>当前状态</span></div>
    <div class="da_mid" style="width: expression(document.body.clientWidth-32)">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
      <tr height="30px">
        <td align="right" class="green">通信方式：</td>
        <td><security:authorize
          ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:select path="commModeGm" items="${commModeList}" id="commModeGm" itemLabel="name" itemValue="code"
            cssStyle="width:155px;" disabled="${disabled}" />
        </security:authorize><security:authorize
          ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:select path="commModeGm" items="${commModeList}" id="commModeGm" itemLabel="name" itemValue="code"
            cssStyle="width:155px;" disabled="${disabled}" />
        </security:authorize></td>
        <td align="right" class="green">漏保型号：</td>
        <td><security:authorize
          ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:select path="modelCode" items="${psModelList}" id="modelCode" itemLabel="name" itemValue="code"
            cssStyle="width:155px;" disabled="${disabled}" />
        </security:authorize><security:authorize
          ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:select path="modelCode" items="${psModelList}" id="modelCode" itemLabel="name" itemValue="code"
            cssStyle="width:155px;" disabled="${disabled}" />
        </security:authorize></td>
      </tr>
      <tr height="30px">
        <td align="right" class="green">波特率：</td>
        <td><security:authorize
          ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:select path="btl" items="${btlList}" itemLabel="name" itemValue="code" cssStyle="width:155px;"
            disabled="${disabled}" />
        </security:authorize><security:authorize
          ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:select path="btl" items="${btlList}" itemLabel="name" itemValue="code" cssStyle="width:155px;"
            disabled="${disabled}" />
        </security:authorize></td>
        <td align="right" class="green">额定电流：</td>
        <td><security:authorize
          ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:select path="ratedEc" items="${ratedEcList}" id="ratedEc" itemLabel="name" itemValue="code"
            cssStyle="width:155px;" disabled="${disabled}" />
        </security:authorize><security:authorize
          ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:select path="ratedEc" items="${ratedEcList}" id="ratedEc" itemLabel="name" itemValue="code"
            cssStyle="width:155px;" disabled="${disabled}" />
        </security:authorize></td>
      </tr>
      <tr height="30px">
        <td align="right" class="green">规约：</td>
        <td><security:authorize
          ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:select path="gpInfo.protocolNo" items="${protocolList}" id="protocolNo" itemLabel="name" itemValue="code"
            cssStyle="width:155px;" disabled="${disabled}" />
        </security:authorize><security:authorize
          ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:select path="gpInfo.protocolNo" items="${protocolList}" id="protocolNo" itemLabel="name" itemValue="code"
            cssStyle="width:155px;" disabled="${disabled}" />
        </security:authorize></td>
        <td align="right" class="green">漏保类型：</td>
        <td><security:authorize
          ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:select path="psType" items="${psTypeList}" id="psType" itemLabel="name" itemValue="code"
            cssStyle="width:155px;" disabled="${disabled}" />
        </security:authorize><security:authorize
          ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:select path="psType" items="${psTypeList}" id="psType" itemLabel="name" itemValue="code"
            cssStyle="width:155px;" disabled="${disabled}" />
        </security:authorize></td>
      </tr>
    </table>
    <table id="detail" border="0" cellpadding="0" cellspacing="0" width="100%">
      <tr height="30px">
        <td align="right" class="green">漏电分断延迟档位：</td>
        <td><form:select path="offDelayGear" items="${offDelayGearList}" id="offDelayGear" itemLabel="name"
          itemValue="code" cssStyle="width:145px;" disabled="true" /></td>
        <td align="right" class="green">剩余电流档位：</td>
        <td><form:select path="remcGear" 
        items="${remcGearList}" id="remcGear" itemLabel="name" itemValue="code"
          cssStyle="width:165px;" disabled="true" /></td>
      </tr>
      <!--  
			<tr height="30px">
				<td align="right" class="green">CT：</td>
				<td><form:select path="gpInfo.ctTimes" items="${ctList}" id="ctTimes" itemLabel="name" itemValue="code"
					cssStyle="width:155px;"  /></td>
				<td align="right" class="green">PT：</td>
				<td><form:select path="gpInfo.ptTimes" items="${ptList}" id="ptTimes" itemLabel="name" itemValue="code"
					cssStyle="width:155px;"  /></td>
			</tr>-->
      <tr height="30px">
        <td align="right" class="green">功能状态设置：</td>
        <td colspan="3"><c:forEach items="${psinfo.functionMap}" var="function">
          <form:checkbox path="functionsChecked" value="${function.value}" disabled="true" />${function.key}
 </c:forEach></td>
      </tr>
    </table>
    </div>
  </form:form></div>
  <div style="text-align: center" id="msg"></div>
  <div style="text-align: center" ><br></br>
  <security:authorize
    ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
    <c:if test="${_type!='show'}">
      <input type="button" id="f10" value="参数下发" class="btnbg4" disabled="disabled"/>
      <input type="button" id="save" value="保 存" class="btnbg4" />
    </c:if>
  </security:authorize>&nbsp;<input type="button" id="close" value="关闭" class="btnbg4" onclick="closeWin()" /></div>
  </div>
</ul>
</div>
</body>
<script>

var checkVal =  new Validation(document.forms[0],{immediate:true,onSubmit:true,onFormValidate : function(result,form) {return result;}});
	  
function StringBuffer() {
    this.data = [];
}

StringBuffer.prototype.append = function() {
    this.data.push(arguments[0]);
    return this;
};

StringBuffer.prototype.toString = function() {
    return this.data.join("");
};


checkBox  = function(){
   if($("#autoTest").attr("checked")){
     $("#time").show();
   }else{
	 $("#time").hide();
   }
};

function getOptionText(id){
	if(!$("#"+id)){
		alert("没有指定的dom");
		return '';
	}
	
	return $("#"+id+" option[selected]").text();
	
}

function akeySetupTermParamF10(){
	   var sb_dto = new StringBuffer();
	   
	    sb_dto.append('{');
	    sb_dto.append('"collectObjects":').append('[');
	    for(var loop = 0; loop < 1; loop++) {
	        if(loop != 0) {
	            sb_dto.append(',');
	        }
	        var logicalAddr;
	        var gpsn;
	        var equipProtocol;
	       	var channelType;
	        var baudrate;
	        var gpAddr;
	        
	        logicalAddr = getOptionText("termAddr");
	        gpsn = $("#gpSn").val();
	        equipProtocol = $("#protocolNo").val();
	        baudrate = $("#btl").val();
	        gpAddr = $("#gpAddr").val();
	        
	        sb_dto.append('{');
	        sb_dto.append('"logicalAddr":"' + logicalAddr + '"').append(',');
	        sb_dto.append('"equipProtocol":"' + equipProtocol + '"').append(',');
	        sb_dto.append('"channelType":"' + $("#channelType").val() + '"').append(',');
	        sb_dto.append('"pwAlgorith":"' + $("#pwAlgorith").val() + '"').append(',');
	        sb_dto.append('"pwContent":"' + $("#pwContent").val() + '"').append(',');
	        sb_dto.append('"mpExpressMode":"' + $("#mpExpressMode").val() + '"').append(',');
	        sb_dto.append('"mpSn":["' + 0 + '"]').append(',');
	        sb_dto.append('"commandItems":').append('[').append('{');
	        var ciarray = ['10040010'];
	       
	        for(var i = 0; i < ciarray.length; i++) {
	            if(i > 0) {
	                sb_dto.append('{');
	            }
	            sb_dto.append('"identifier":"' + ciarray[i] + '"').append(',');
	            sb_dto.append('"circleLevel":"' + 1 + '"').append(',');
	            
	            sb_dto.append('"datacellParam":').append('{');
	            
	            
	          /*  
	               *<"1004001001", String>      : 本次电能表/交流采样装置配置数量
	       	 *     *<"10040010020001", String>  : 本次配置第0001块电能表/交流采样装置序号 【默认与所属测量点号相同】
	       	 *     *<"10040010030001", String>  : 本次配置第0001块电能表/交流采样装置所属测量点号
	       	 *     *<"10040010040001", String>  : 本次配置第0001块电能表/交流采样装置通信波特率 C_METER.BAUDRATE
	       	 *     *<"10040010050001", String>  : 本次配置第0001块电能表/交流采样装置通信端口号 C_GP.PORT
	       	 *     *<"10040010060001", String>  : 本次配置第0001块电能表/交流采样装置通信协议类型 C_GP.PROTOCOL_NO
	       	 *     *<"10040010070001", String>  : 本次配置第0001块电能表/交流采样装置通信地址  C_GP.GP_ADDR
	       	 
	    	 *      <"10040010080001", String>  : 本次配置第0001块电能表/交流采样装置通信密码 【默认为000000000000】
	    	 *      <"10040010100001", String>  : 本次配置第0001块电能表/交流采样装置电能费率个数 【默认为000100(4个费率)】
	    	 *      <"10040010120001", String>  : 本次配置第0001块电能表/交流采样装置有功电能示值的整数位个数 【默认为10(6位)】
	    	 *      <"10040010130001", String>  : 本次配置第0001块电能表/交流采样装置有功电能示值的小数位个数 【默认为11(4位)】
	    	 *      <"10040010140001", String>  : 本次配置第0001块电能表/交流采样装置所属采集器通信地址 【默认为000000000000】
	    	 *      <"10040010150001", String>  : 本次配置第0001块电能表/交流采样装置所属的用户大类号 【默认为0000】
	    	 *      <"10040010160001", String>  : 本次配置第0001块电能表/交流采样装置所属的用户小类号 【默认为0000】
	       	 */
				
	       	 	var gpFully = "";
	          
	       	 	gpFully = lpadString(gpsn, 4);
	       	 	gpFully = '0001';
	       	
	       	    sb_dto.append('"1004001001": "1"');
	       	    sb_dto.append("}").append(',');
	       	    
	       	 	sb_dto.append('"circleDataItems":').append('{');
	       	 	
	       	 	sb_dto.append('"dataItemGroups":').append('[{');
	       	 	
	       	 	sb_dto.append('"dataItemList":').append('[');
	       	 	
	       	 	
	       	 	sb_dto.append('{');
	       	 	sb_dto.append('"dataItemCode":').append('"1004001002'+gpFully).append('",');
	       	 	sb_dto.append('"dataItemValue":').append("\""+gpsn+"\"");
	       	    sb_dto.append("}");
	       	 	sb_dto.append(',');
	       	    
	       	 	sb_dto.append('{');
	       	 	sb_dto.append('"dataItemCode":').append('"1004001003'+gpFully).append('",');
	       	 	sb_dto.append('"dataItemValue":').append("\""+gpsn+"\"");
	       	 	sb_dto.append("}");
	       	 	sb_dto.append(',');
	       	 	
	       	 	
	       	 	sb_dto.append('{');
	       	 	sb_dto.append('"dataItemCode":').append('"1004001004'+gpFully).append('",');
	       	 	sb_dto.append('"dataItemValue":').append("\""+baudrate+"\"");
	       	 	sb_dto.append("}");
	       	 	sb_dto.append(',');
	       	 	
	       	 	sb_dto.append('{');
	       	 	sb_dto.append('"dataItemCode":').append('"1004001005'+gpFully).append('",');
	       	 	sb_dto.append('"dataItemValue":').append("\"1\"");
	       	 	sb_dto.append("}");
	       	 	sb_dto.append(',');
	       	 	
	       	 	
	       	 	sb_dto.append('{');
	       	 	sb_dto.append('"dataItemCode":').append('"1004001006'+gpFully).append('",');
	       	 	sb_dto.append('"dataItemValue":').append("\""+equipProtocol+"\"");
	       	 	sb_dto.append("}");
	       	 	sb_dto.append(',');
	     	   
	       	 	sb_dto.append('{');
	       	 	sb_dto.append('"dataItemCode":').append('"1004001007'+gpFully).append('",');
	       	 	sb_dto.append('"dataItemValue":').append("\""+gpAddr+"\"");
	       	 	sb_dto.append("}");
	       	 	sb_dto.append(',');
	     	    
	     	    
	       	    /****************************************************************/
           	 	sb_dto.append('{');
	       	 	sb_dto.append('"dataItemCode":').append('"1004001008'+gpFully).append('",');
	       	 	sb_dto.append('"dataItemValue":').append("\"000000000000\"");
	       	 	sb_dto.append("}");
	       	 	sb_dto.append(',');
	     	    
           	 	sb_dto.append('{');
	       	 	sb_dto.append('"dataItemCode":').append('"1004001010'+gpFully).append('",');
	       	 	sb_dto.append('"dataItemValue":').append("\"000100\"");
	       	 	sb_dto.append("}");
	       	 	sb_dto.append(',');
	       	 	
           	 	sb_dto.append('{');
	       	 	sb_dto.append('"dataItemCode":').append('"1004001012'+gpFully).append('",');
	       	 	sb_dto.append('"dataItemValue":').append("\"10\"");
	       	 	sb_dto.append("}");
	       	 	sb_dto.append(',');
	       	 	
           	 	sb_dto.append('{');
	       	 	sb_dto.append('"dataItemCode":').append('"1004001013'+gpFully).append('",');
	       	 	sb_dto.append('"dataItemValue":').append("\"11\"");
	       	 	sb_dto.append("}");
	       	 	sb_dto.append(',');
	       	 	
           	 	sb_dto.append('{');
	       	 	sb_dto.append('"dataItemCode":').append('"1004001014'+gpFully).append('",');
	       	 	sb_dto.append('"dataItemValue":').append("\"000000000000\"");
	       	 	sb_dto.append("}");
	       	 	sb_dto.append(',');
	       	 	
           	 	sb_dto.append('{');
	       	 	sb_dto.append('"dataItemCode":').append('"1004001015'+gpFully).append('",');
	       	 	sb_dto.append('"dataItemValue":').append("\"0000\"");
	       	 	sb_dto.append("}");
	       	 	sb_dto.append(',');
	     	    
           	 	sb_dto.append('{');
	       	 	sb_dto.append('"dataItemCode":').append('"1004001016'+gpFully).append('",');
	       	 	sb_dto.append('"dataItemValue":').append("\"0000\"");
	       	 	sb_dto.append("}");
	     	   /****************************************************************/
	     	    
	     	    sb_dto.append("]}");
	     	   	sb_dto.append("]}");
	            
	            if(i < ciarray.length - 1) {
	                sb_dto.append('}').append(',');
	            }
	        }
	        
	        sb_dto.append('}').append(']');
	        sb_dto.append('}');
	    }
	    
	    sb_dto.append(']}');
	    
	    
	    var url = '<pss:path type="webapp"/>/archive/psinfo/akeySetupTermParamF10.json';
	    $.ajax({
	        type: 'POST',
	        url: url,
	        data: "dto=" + sb_dto.toString(),
	        dataType: 'json',
	        beforeSend:function(){
	        	$("#msg").html("下发中……");
	        	toggleButton();
	        },
	        success: function(data) {
	        	if("-1" !=data){
	        		fetchReturnResult(data, 10, 100);
	        	}else{
	        		$("#msg").html("调用出错");
	            	toggleButton(true);
	        	}
	        },
	        
	        error: function() {
	        	$("#msg").html("下发失败");
	        	alert('error');
	        }
	    });

	   
}

function fetchReturnResult(appIds, sFetchCount, commanditems) {
    var iFetchCount = 0;
    try {
        iFetchCount = parseInt(sFetchCount);
    }
    catch(e) {
        iFetchCount = 0;
    }
    var params = {
            "collectId": appIds,
            "fetchCount": iFetchCount,
            "random":Math.random()
    };
    
    var url = '<pss:path type="webapp"/>/archive/psinfo/backAkeySetupTermParamF10.json';

    $.ajax({
        type: "POST",
        url: url,
        data: params,
        beforeSend:function(){
        	$("#msg").html("获取中……");
        	toggleButton();
        },
        success: function(data) {
        	if(""==data){
        		  if(iFetchCount > 0) {
                      setTimeout("fetchReturnResult('" + appIds + "', " + (iFetchCount - 1) +", '"+ commanditems+ "')", 3000);
                  }else{
                	  $("#msg").html("设置超时");
                  	  toggleButton(true);
                  }
        	}
            else if("1"==data) {
            	$("#msg").html("设置成功");
            	toggleButton(true);
            }else{
            	$("#msg").html("设置失败");
            	toggleButton(true);
            }
        },
        error:function(){
              alert("error");
        	toggleButton(true);
          }
    });

    if(iFetchCount == 0) {
    	 $("#msg").html("设置超时");
     	  toggleButton(true);
        //alert("操作结束");
    }
}



function toggleButton(data){
	if(data){
		    $("#f10").attr("disabled","");
	}else{
		 $("#f10").attr("disabled","true");
	}
};
	

function initOpResult(msg) {
    var cilist = $("#opcilist").val();
    var ciarray = cilist.split(',');
    for(var i = 0; i < ciarray.length; i++) {
        $("#ciop" + ciarray[i]).html(msg);
    }
}

$(function(){
	/*
	$("#msg").beforeSend(function(e,xhr,o) { 
	    $(this).html("正在请求"+o.url); 
	}).ajaxSuccess(function(e,xhr,o) { 
	    $(this).html(o.url+"请求成功"); 
	}).ajaxError(function(e,xhr,o) { 
	    $(this).html(o.url+"请求失败"); 
	}); */
	
  checkBox();
  
 $("#f10").click(function(){
	 akeySetupTermParamF10();
 });

  $("#autoTest").click(function(){
      if($(this).attr("checked")){
    	  $("#time").show();
      }else
      {
    	  $("#time").hide();
      }
    });
  
if('${_type}' == "new"){
	//$("#detail_tile").hide();
	$("#detail").hide();
}
	
$("#save").click(function(){
	var ret = checkVal.validate();
    if(ret){
        jQuery(this).attr("disabled","disabled");
        if($("#_type").val()=="edit"){
          updatepsinfo();
        }else if($("#_type").val()=="new"){
          addpsinfo();
        }
        
        jQuery(this).attr("disabled","");
    }else{
      jQuery(this).attr("disabled","");
    }
    });
      
});



getData= function(type){
var data;
if(type == "add"){
  data = jQuery("form[id=psinfo]").not("#psId").serialize(); 
}else {
  data = jQuery("form[id=psinfo]").serialize(); 
}
return data;
};



addpsinfo = function(){
  var psFormData = getData('add');
  var url="${ctx}/archive/psinfo.json";
  if(confirm("确定要保存该漏电保护器?")){
    jQuery.ajax({
         url: url,
         data:psFormData,
         dataType:'json',
         type:'POST',
         cache: false,
         success: function(json){
    	 var msg = json['<%=SystemConst.CONTROLLER_AJAX_MESSAGE%>'];
         var isSucc = json['<%=SystemConst.CONTROLLER_AJAX_IS_SUCC%>'];
           alert(msg);
           if(isSucc){
        	   opener.location.href ="${ctx}/archive/tginfo/${psinfo.gpInfo.objectId}/edit";
        	   toggleButton(true);
        	   if(confirm("是否需要一键下发参数?")){
					 akeySetupTermParamF10();
				 }else{
					 closeWin();
				 }
           }
         },error:function(XmlHttpRequest,textStatus, errorThrown){
        	 alert("新建失败;"+XmlHttpRequest.responseText);
         }
       });
  }
};

updatepsinfo = function(){
  var psFormData = getData("update");
    var url="${ctx}/archive/psinfo/${psinfo.psId}.json?_method=put";
    if(confirm("确定要更新该漏电保护器?")){
      jQuery.ajax({
           url: url,
           data:psFormData,
           dataType:'json',
           type:'post',
           cache: false,
           success: function(json){
    	  var msg = json['<%=SystemConst.CONTROLLER_AJAX_MESSAGE%>'];
          var isSucc = json['<%=SystemConst.CONTROLLER_AJAX_IS_SUCC%>'];
							alert(msg);
							if (isSucc) {
								opener.location.href = "${ctx}/archive/tginfo/${psinfo.gpInfo.objectId}/edit";
								toggleButton(true);
								 if(confirm("是否需要一键下发参数?")){
									 akeySetupTermParamF10();
								 }else{
									 closeWin();
								 }
								
							}
						},
						error : function(XmlHttpRequest) {
							alert("更新失败;" + XmlHttpRequest.responseText
									+ XmlHttpRequest.statusText);
						}
					});
		}
	};
	function closeWin() {
		window.close();
	};
</script>
</html>