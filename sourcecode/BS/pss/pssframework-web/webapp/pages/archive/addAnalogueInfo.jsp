<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>模拟量信息</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<script type="text/javascript">


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
</script>
</head>
<body>
<form:form action="/archive/analogueinfo" modelAttribute="analogueinfo">
  <input type="hidden" name="<%=SystemConst.CONTROLLER_METHOD_TYPE%>" id="<%=SystemConst.CONTROLLER_METHOD_TYPE%>"
    value="${_type}"></input>
  <c:choose>
    <c:when test="${_type=='edit' || _type=='new'}">
      <c:set var="disabled" value="false"></c:set>
    </c:when>
    <c:otherwise>
      <c:set var="disabled" value="true"></c:set>
    </c:otherwise>
  </c:choose>
  <input type="hidden" name="gpInfo.objectId" id="gpInfo.objectId" value="${analogueinfo.gpInfo.objectId}">
  <input type="hidden" name="gpInfo.gpType" id="gpInfo.gpType" value="2">
  <input type="hidden" name="gpInfo.gpChar" id="gpInfo.gpChar" value="8">
  <div class="electric_lcon" id="electric_Con" style="">
  <ul class=default id=electric_Con_1>
    <div class="tab"><span>模拟量信息</span></div>
    <div class="da_mid"
      style="display: block; overflow-y: auto; overflow-x: auto; width: expression((document.documentElement.clientWidth ||document.body.clientWidth) -10 ); height: expression(((document.documentElement.clientHeight ||document.body.clientHeight) -35 ) );">
    <div class="tab"><span>重要信息</span><input type="hidden" id="channelType" name="channelType" value="1" /> <input type="hidden"
      id="pwAlgorith" name="pwAlgorith" value="0" /> <input type="hidden" id="pwContent" name="pwContent" value="8888" />
    <input type="hidden" id="mpExpressMode" name="mpExpressMode" value="3" /></div>
    <div id="485Show" class="da_mid"
      style="display: block; overflow-y: auto; overflow-x: auto; height: expression((( document.documentElement.clientHeight | document.body.clientHeight) - 700 ) );">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
      <tr height="30px">
        <td  width="13%" class="green" align="right"><font color="red">* </font>端 口 号：</td>
        <td width="13%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_10">
          <form:select path="gpInfo.port" disabled="${disabled}" id="port" cssClass="required validate-number"></form:select>
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_10">
          <form:select path="gpInfo.port" disabled="${disabled}" id="port" items="${ports}" itemLabel="key" itemValue="value" cssClass="required validate-number int-range-1-8 validate-ajax-${ctx}/archive/analogueinfo/checkPort.json"></form:select>
        </security:authorize></td>
        <td width="13%" class="green" align="right"><font color="red">* </font>模拟量名称：</td>
        <td width="20%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_10">
          <form:input path="analogueName" cssClass="required" disabled="${disabled}" />
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_10">
          <form:input path="analogueName" cssClass="required" disabled="${disabled}" />
        </security:authorize></td>
        
         <td width="13%" class="green" align="right">所属终端：</td>
        <td width="20%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_10">
          <form:select path="gpInfo.terminalInfo.termId" items="${termList}" id="termId" itemLabel="logicalAddr"
            itemValue="termId" cssStyle="width:155px;" disabled="${disabled}" onchange="checkGpSn()"/>
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_10">
          <form:select path="gpInfo.terminalInfo.termId" items="${termList}" id="termId" itemLabel="logicalAddr"
            itemValue="termId" cssStyle="width:155px;" disabled="${disabled}" cssClass="validate-ajax-${ctx}/archive/analogueinfo/checkGpSn.json validate-ajax-${ctx}/archive/analogueinfo/checkGpAddr.json"/>
        </security:authorize></td>
        
      </tr>
       <tr height="30px">
          <td  class="green" align="right">量程起始值：</td>
        <td ><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_10">
          <form:input path="rangeBegin" disabled="${disabled}" id="port" cssClass="required validate-number"></form:input>
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_10">
          <form:input path="rangeBegin" disabled="${disabled}" id="port" cssClass="required validate-number"></form:input>
        </security:authorize></td>
        
         <td  class="green" align="right">量程结束值：</td>
        <td ><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_10">
          <form:input path="rangeEnd" disabled="${disabled}" id="port" cssClass="required validate-number"></form:input>
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_10">
          <form:input path="rangeEnd" disabled="${disabled}" id="port" cssClass="required validate-number"></form:input>
        </security:authorize></td>
 
       </tr>
       
              <tr height="30px">
          <td  class="green" align="right">模拟量上限：</td>
        <td ><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_10">
          <form:input path="maxValue" disabled="${disabled}" id="port" cssClass="required validate-number"></form:input>
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_10">
          <form:input path="maxValue" disabled="${disabled}" id="port" cssClass="required validate-number"></form:input>
        </security:authorize></td>
        
         <td  class="green" align="right">模拟量下限：</td>
        <td ><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_10">
          <form:input path="minValue" disabled="${disabled}" id="port" cssClass="required validate-number"></form:input>
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_10">
          <form:input path="minValue" disabled="${disabled}" id="port" cssClass="required validate-number"></form:input>
        </security:authorize></td>
 
       </tr>
      
    </table>
    </div>

    <div style="text-align: center" id="msg"></div>
    <div style="text-align: center"><br />
    <security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_10">
      <c:if test="${_type!='show'}">
      <!--  <input type="button" id="f10" value="参数下发" class="btnbg4" disabled="disabled"/>-->
        <input type="button" id="save" value="保 存" class="btnbg4" />
      </c:if>
    </security:authorize>&nbsp;<input type="button" id="close" value="关闭" class="btnbg4" onclick="closeWin()" /></div>
    </div>
  </ul>
  </div>
</form:form>
</body>
<script>

val =  new Validation(document.forms[0],{immediate:true,onSubmit:true,onFormValidate : function(result,form) {
   return result;
  }}
  );
  
  
$(function(){
$("#save").click(function(){
	var ret = val.result();
	 if(ret==true || ""==ret){
        jQuery(this).attr("disabled","disabled");
        if($("#_type").val()=="edit"){
          updateanalogueinfo();
        }else if($("#_type").val()=="new"){
          addanalogueinfo();
        }
        
        jQuery(this).attr("disabled","");
    }else{
      jQuery(this).attr("disabled","");
    }
    });
    
    /*
$("#f10").click(function(){
	 akeySetupTermParamF10();
});*/
      
});



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
	        var port;
	        
	        logicalAddr = getOptionText("termId");
	        gpsn = $("#gpSn").val();
	        equipProtocol = $("#protocolNo").val();
	        baudrate = getOptionText("btl");
	        gpAddr = $("#gpAddr").val();
	        port = $("#prot").val();
	       
	        
	        sb_dto.append('{');
	        sb_dto.append('"logicalAddr":"' + logicalAddr + '"').append(',');
	        sb_dto.append('"equipProtocol":"' + equipProtocol + '"').append(',');
	        sb_dto.append('"channelType":"' + $("#channelType").val() + '"').append(',');
	        sb_dto.append('"pwAlgorith":"' + $("#pwAlgorith").val() + '"').append(',');
	        sb_dto.append('"pwContent":"' + $("#pwContent").val() + '"').append(',');
	        sb_dto.append('"mpExpressMode":"' + $("#mpExpressMode").val() + '"').append(',');
	        sb_dto.append('"mpSn":["' + gpsn + '"]').append(',');
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
	       	 */
				
	       	 	var gpFully = "";
	          
	       	 	gpFully = lpadString(gpsn, 4);
	       	 	
	       	
	       	    sb_dto.append('"1004001001": "1"').append(',');
	            
	       	    sb_dto.append('"1004001002'+gpFully+'": "'+gpsn+'"').append(',');
	       	 	
	     	    sb_dto.append('"1004001003'+gpFully+'": "'+gpsn+'"').append(',');
	       	    
	     	    sb_dto.append('"1004001004'+gpFully+'": "'+baudrate+'"').append(',');
	       	    
	     	    sb_dto.append('"1004001005'+gpFully+'": "'+port+'"').append(',');
	       	    
	     	    sb_dto.append('"1004001006'+gpFully+'": "'+equipProtocol+'"').append(',');
	       	    
	     	    sb_dto.append('"1004001007'+gpFully+'": "'+gpAddr+'"').append(',');
	     	    
	     	    
	     	   /****************************************************************/
	     	    sb_dto.append('"1004001008'+gpFully+'": "000000000000"').append(',');
	     	    
	     	    
	     	    sb_dto.append('"1004001010'+gpFully+'": "000100"').append(',');
	     	    
	     	    sb_dto.append('"1004001012'+gpFully+'": "10"').append(',');
	     	    
	     	    sb_dto.append('"1004001013'+gpFully+'": "11"').append(',');
	     	    
	     	    sb_dto.append('"1004001014'+gpFully+'": "000000000000"').append(',');
	     	    
	     	    sb_dto.append('"1004001015'+gpFully+'": "0000"').append(',');
	     	    
	     	    sb_dto.append('"1004001016'+gpFully+'": "0000"');
	     	    
	     	   /****************************************************************/
	     	   
	     	   sb_dto.append("}");
	            
	            if(i < ciarray.length - 1) {
	                sb_dto.append('}').append(',');
	            }
	        }
	        
	        sb_dto.append('}').append(']');
	        sb_dto.append('}');
	    }
	    
	    sb_dto.append(']}');
	    
	    var url = '<pss:path type="webapp"/>/archive/analogueinfo/akeySetupTermParamF10.json';
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
    
    var url = '<pss:path type="webapp"/>/archive/analogueinfo/backAkeySetupTermParamF10.json';

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
	


getData= function(type){
var data;
  data = jQuery("form[id=analogueinfo]").serialize(); 
return data;
};

addanalogueinfo = function(){
  var psFormData = getData('add');
  var url="${ctx}/archive/analogueinfo.json";
  if(confirm("确定要保存该模拟量?")){
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
             opener.location.href ="${ctx}/archive/tginfo/${analogueinfo.gpInfo.objectId}/edit";
             closeWin();
            /* toggleButton(true);
      	   if(confirm("是否需要一键下发参数?")){
					 akeySetupTermParamF10();
				 }else{
					 closeWin();
				 }*/
           }
         },error:function(e){
             alert(e.message);
         }
       });
  }
};

updateanalogueinfo = function(){
    var psFormData = getData("update");
    var url="${ctx}/archive/analogueinfo/${analogueinfo.gpInfo.gpId}.json?_method=put";
    if(confirm("确定要更新该模拟量?")){
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
             if(isSucc){
               opener.location.href ="${ctx}/archive/tginfo/${analogueinfo.gpInfo.objectId}/edit";
               closeWin();
               /*toggleButton(true);
        	   if(confirm("是否需要一键下发参数?")){
					 akeySetupTermParamF10();
				 }else{
					 closeWin();
				 }*/
             }
           },error:function(e){
             	alert("error");
               alert(e.getMessage());
           }
         });
    }
};

function closeWin() 
{ 
    window.close(); 
} 
</script>
</html>