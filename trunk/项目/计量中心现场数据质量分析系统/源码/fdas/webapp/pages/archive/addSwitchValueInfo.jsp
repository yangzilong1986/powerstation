<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>开关量信息</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<script type="text/javascript">
var contextPath = '${ctx}';

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
  <div class="tab"><span>开关量信息</span></div>
  <div class="da_mid"
    style="display: block; overflow-y: auto; overflow-x: auto; width: expression((     document.documentElement.clientWidth ||       document.body.clientWidth) -10 ); height: expression(((            document.documentElement.clientHeight ||         document.body.clientHeight) -35 ) );">
  <div><form:form action="/archive/switchvalueinfo" modelAttribute="switchvalueinfo">
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
    <input type="hidden" name="tgId" value="${tgId}">
    <input type="hidden" name="termIdOld" value="${switchvalueinfo.terminalInfo.termId}">
    <input type="hidden" name="switchNoOld" value="${switchvalueinfo.switchNo}">
    <div id="main">
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
      <tr height="30px">
        <td width="20%" align="right" class="green"><font color="red">* </font>开关量编号：</td>
        <td width="30%"><security:authorize
          ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:input path="switchNo" cssClass="required input2 validate-number" disabled="${disabled}" />
        </security:authorize><security:authorize
          ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:input path="switchNo"
            cssClass="required input2 validate-number validate-ajax-${ctx}/archive/switchvalueinfo/checkSwtichNo.json"
            disabled="${disabled}" />
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
            cssClass="validate-ajax-${ctx}/archive/switchvalueinfo/checkSwitchNo.json" />
        </security:authorize></td>
      </tr>
      <tr height="30px">
        <td align="right" class="green">开关量名称：</td>
        <td><security:authorize
          ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:input path="switchValueName" maxlength="20" cssClass="required input2" disabled="${disabled}" />
        </security:authorize><security:authorize
          ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
          <form:input path="switchValueName" disabled="${disabled}" />
        </security:authorize></td>
        <td align="right" class="green">开关量属性：</td>
        <td><security:authorize
          ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
                <form:select path="switchType" items="${switchTypeList}" itemLabel="name" itemValue="code"
            cssStyle="width:155px;" disabled="${disabled}"  />
        </security:authorize><security:authorize
          ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
         <form:select path="switchType" items="${switchTypeList}" itemLabel="name" itemValue="code"
            cssStyle="width:155px;" disabled="${disabled}"  />
        </security:authorize></td>
      </tr>
    </table>
    </div>
  </form:form></div>
  <div style="text-align: center"><br></br>
  <security:authorize
    ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">
    <c:if test="${_type!='show'}">
      <input type="button" id="save" value="保 存" class="btnbg4" />
    </c:if>
  </security:authorize>&nbsp;<input type="button" id="close" value="关闭" class="btnbg4" onclick="closeWin()" /></div>
  </div>
</ul>
</div>
</body>
<script>
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


val =  new Validation(document.forms[0],{immediate:true,onSubmit:true,onFormValidate : function(result,form) {
	   return result;
	  }}
	  );


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
	        gpsn = $("#gpInfo.gpSn").val();
	        equipProtocol = $("#gpInfo.protocolNo").val();
	        baudrate = getOptionText("btl");
	        gpAddr = $("#gpInfo.gpAddr").val();
	        
	        sb_dto.append('{');
	        sb_dto.append('"logicalAddr":"' + logicalAddr + '"').append(',');
	        sb_dto.append('"equipProtocol":"' + equipProtocol + '"').append(',');
	        sb_dto.append('"channelType":"' + $("#channelType").val() + '"').append(',');
	        sb_dto.append('"pwAlgorith":"' + $("#pwAlgorith").val() + '"').append(',');
	        sb_dto.append('"pwContent":"' + $("#pwContent").val() + '"').append(',');
	        sb_dto.append('"mpExpressMode":"' + $("#mpExpressMode").val() + '"').append(',');
	        sb_dto.append('"mpSn":["' + gpsn + '"]').append(',');
	        sb_dto.append('"commandItems":').append('[').append('{');
	        var ciarray = ['1004001'];
	        for(var i = 0; i < ciarray.length; i++) {
	            if(i > 0) {
	                sb_dto.append('{');
	            }
	            sb_dto.append('"identifier":"' + ciarray[i] + '"').append(',');
	            sb_dto.append('"circleLevel":"' + 1 + '"').append(',');
	            
	            sb_dto.append('"datacellParam":').append('[');
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
	       	 	
	       	 	sb_dto.append('{');
	       	    sb_dto.append('"1004001001": "1"');
	       	    sb_dto.append("}").append(',');
	            
	       	 	sb_dto.append('{');
	       	    sb_dto.append('"1004001002'+gpFully+'": "'+gpsn+'"');
	       	    sb_dto.append("}").append(',');
	       	 	
	       	    sb_dto.append('{');
	     	    sb_dto.append('"1004001003'+gpFully+'": "'+gpsn+'"');
	       	    sb_dto.append("}").append(',');
	       	    
	       	 	sb_dto.append('{');
	     	    sb_dto.append('"1004001004'+gpFully+'": "'+baudrate+'"');
	       	    sb_dto.append("}").append(',');
	       	    
	       	 	sb_dto.append('{');
	     	    sb_dto.append('"1004001005'+gpFully+'": "1"');
	       	    sb_dto.append("}").append(',');
	       	    
	       	 	sb_dto.append('{');
	     	    sb_dto.append('"1004001006'+gpFully+'": "'+equipProtocol+'"');
	       	    sb_dto.append("}").append(',');
	       	    
	       	 	sb_dto.append('{');
	     	    sb_dto.append('"1004001007'+gpFully+'": "'+gpAddr+'"');
	     	   	sb_dto.append("}");
	     	  	sb_dto.append(']');
	            
	            if(i < ciarray.length - 1) {
	                sb_dto.append('}').append(',');
	            }
	        }
	        
	        sb_dto.append('}').append(']');
	        sb_dto.append('}');
	    }
	    
	    sb_dto.append(']}');
	    
	    var url = '<pss:path type="webapp"/>/archive/switchvalueinfo/akeySetupTermParamF10.json';
	    $.ajax({
	        type: 'POST',
	        url: url,
	        data: "dto=" + sb_dto.toString(),
	        dataType: 'json',
	        success: function(data) {
	            fetchReturnResult(data, 10, 100);
	        },
	        error: function() {
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
    
    var url = '<pss:path type="webapp"/>/archive/switchvalueinfo/backAkeySetupTermParamF10.json';

    $.ajax({
        type: "POST",
        url: url,
        data: params,
        success: function(data) {
            
            if(iFetchCount > 0 && !data) {
                setTimeout("fetchReturnResult('" + appIds + "', " + (iFetchCount - 1) +", '"+ commanditems+ "')", 3000);
            }
            else {
                enableButton();
            }
        },
        error:function(){
               //alert("error")
          }
    });

    if(iFetchCount == 0) {
        enableButton();
        //alert("操作结束");
    }
}


$(function(){

  $("#save").click(function(){
	  
      if(val.result()){
          jQuery(this).attr("disabled","disabled");
          if($("#_type").val()=="edit"){
            updateswitchvalueinfo();
          }else if($("#_type").val()=="new"){
            addswitchvalueinfo();
          }
          
          jQuery(this).attr("disabled","");
      }else{
        jQuery(this).attr("disabled","");
      }
      });
      
});



getData= function(type){
	var data;
  	data = jQuery("form[id=switchvalueinfo]").serialize(); 
	return data;
};



addswitchvalueinfo = function(){
  var psFormData = getData('add');
  var url="${ctx}/archive/switchvalueinfo.json";
  if(confirm("确定要保存该开关量?")){
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
        	   opener.location.href ="${ctx}/archive/tginfo/${tgId}/edit";
        	   closeWin();
           }
         },error:function(XmlHttpRequest,textStatus, errorThrown){
        	 alert("新建失败;"+XmlHttpRequest.responseText);
         }
       });
  }
};

updateswitchvalueinfo = function(){
  var psFormData = getData("update");
    var url="${ctx}/archive/switchvalueinfo/${switchvalueinfo.switchId}.json?_method=put";
    if(confirm("确定要更新该开关量?")){
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
								opener.location.href = "${ctx}/archive/tginfo/${tgId}/edit";
								closeWin();
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