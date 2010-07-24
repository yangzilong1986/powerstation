<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>总表信息</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<script type="text/javascript">

//所属终端
function addTerminal(){
   var tgId=$("input[name='tgId']").val();
   var sqlCode="";
   if(tgFlag==1){
     sqlCode="AL_ARCHIVE_0050";//配变台区页面
   }else{
     sqlCode="AL_ARCHIVE_0022";//低压集中器页面
   }
   var params = {
                 "sqlCode":sqlCode,
                 "pageRows":20,
                 "objectId":tgId,
                 "logicalAddr":""
               };
   var url=contextPath+"/archive/termListQuery.do?action=normalMode&"+ jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
   showDialogBox("终端定位",url, 495, 800);
}
//保存电表信息
function save(){
    var url=contextPath+"/archive/meterAction.do?action=updateMeter";
      if(totalMeterCheck()){
        $("#save").attr("disabled",true);
        var data = jQuery("#main").dataForAjax({
            });
        if(data){
            jQuery.ajax({
                type:'post',
                url:url,
                data:data,
                dataType:'json',
                success:function(json){
                    var msg=json['msg'];
                    $("#save").attr("disabled",false);
                    if(msg=="1"){
                       alert("保存成功");
                       parent.GB_hide();
                       if(tgFlag=="1"){
                         //top.getMainFrameObj().location.href=contextPath+"/archive/addTgAction.do?action=showDeviceInfoByTgID";
                         //动态加载台区关联设备信息
						top.getMainFrameObj().loadTgRelevevance();
                       }else{
                      	 //top.getMainFrameObj().location.href=contextPath+"/archive/addLowCustAction.do?action=showDeviceInfoByTgID";
                      	 top.getMainFrameObj().loadTgRelevevance();
                       }
                    }else if(msg=="2"){
                       alert("该资产编号已经存在");
                    }else{
                       alert("保存失败");
                    }
                }
            });
        }
      }
}
//select框初始化
function selectInit(){
    $("#ctRatio").val(${object_meter.ctRatio});
    $("#ptRatio").val(${object_meter.ptRatio});
    $("#protocolNo").val(${object_gp.protocolNo});
    $("#termId").val('${object_gp.termId}');
    $("#tranId").val('${object_gp.tranId}');
    $("#madeFactory").val('${object_meter.madeFactory}');
    $("#btl").val(${object_meter.btl});
    $("#meterType").val(${object_meter.meterType});
    $("#ratedVolt").val(${object_meter.ratedVolt});
    $("#ratedEc").val(${object_meter.ratedEc});
    $("#wiringMode").val(${object_meter.wiringMode});
    //$("#lineId").val(${object_meter.lineId});
    lineIdComboBox.setValue('${object_meter.lineId}');
    $("#modelCode").val(${object_meter.modelCode});
    $("#measMode").val(${object_meter.measMode});
    
  var mpId = $("input[name='mpId']").val();
  var meterType = $("select[name='meterType']").val();
  if(mpId!=''){ //编辑电表的时候
     if(meterType == '1'){ //远传表
       $("#meterType > option[value!='1']").remove();
     }else{
       $("#meterType > option[value='1']").remove();
     }
  }
}

//连接远传表
function meterState(){
   var meterState=$("#meterType").val();
   var mpId = $("input[name='mpId']").val();
   if(mpId == ''){ //新增电表
       if(meterState=="1"){
	      var url="${ctx}/jsp/archive/remoteMeterTg.jsp?tgFlag="+tgFlag;
	      window.location.href=url;
	   }
   }
}
</script>
</head>
<body>
<form:form action="/archive/mpinfo" modelAttribute="mpinfo">
	<input type="hidden" name="<%=SystemConst.CONTROLLER_METHOD_TYPE%>" id="<%=SystemConst.CONTROLLER_METHOD_TYPE%>"
		value="${_type}"></input>
	<form:hidden path="tgInfo.tgId" />
	<input type="hidden" name="gpInfos[0].objectId" id="gpInfos[0].objectId" value="${mpinfo.tgInfo.tgId}">
	<input type="hidden" name="gpInfos[0].gpType" id="gpInfos[0].gpType" value="2">
	<div class="electric_lcon" id="electric_Con" style="margin: 5px;">
	<ul class=default id=electric_Con_1>
		<div class="tab"><span>总表信息</span></div>
		<div class="da_mid"
			style="display: block; overflow-y: auto; overflow-x: auto; width: expression((   document.documentElement.clientWidth ||   document.body.clientWidth) -10 ); height: expression(((                             document.documentElement.clientHeight ||                             document.body.clientHeight) -35 ) );"">
		<div class="tab"><span>重要信息</span></div>
		<div id="485Show" class="da_mid"
			style="display: block; overflow-y: auto; overflow-x: auto; height: expression(((                 document.documentElement.clientHeight ||                 document.body.clientHeight) -                   700 ) );">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr height="30px">
				<td width="13%" class="green" align="right"><font color="red">* </font>序 号：</td>
				<td width="20%"><form:input path="gpInfos[0].gpSn" cssClass="required validate-number" /></td>
				<td width="13%" class="green" align="right"><font color="red">* </font>计量点名称：</td>
				<td width="20%"><form:input path="mpName" cssClass="required" /></td>
				<td width="13%" class="green" align="right"><font color="red">* </font>计量点编号：</td>
				<td width="20%"><form:input path="mpNo" cssClass="required" /></td>
			</tr>
			<tr height="30px">
				<td class="green" align="right">CT变比：</td>
				<td><form:select path="gpInfos[0].ctTimes" items="${ctList}" id="ctTimes" itemLabel="name" itemValue="code"
					cssStyle="width:155px;" /></td>
				<td class="green" align="right">PT变比：</td>
				<td><form:select path="gpInfos[0].ptTimes" items="${ptList}" id="ptTimes" itemLabel="name" itemValue="code"
					cssStyle="width:155px;" /></td>
				<td class="green" align="right"><font color="red">* </font>表 地 址：</td>
				<td><form:input path="gpInfos[0].gpAddr" cssClass="required" /></td>
			</tr>
			<tr height="30px">
				<td class="green" align="right">表 规 约：</td>
				<td><form:select path="meterInfo.commNo" items="${protocolMeterList}" id="commNo" itemLabel="name"
					itemValue="code" cssStyle="width:155px;" /></td>
				<td class="green" align="right">所属终端：</td>
				<td><form:select path="gpInfos[0].terminalInfo.termId" items="${termList}" id="termId" itemLabel="logicalAddr"
					itemValue="termId" cssStyle="width:155px;" /></td>
				<td class="green" align="right"><font color="red">* </font>资产号：</td>
				<td><form:input path="meterInfo.assertNo" cssClass="required" /></td>
			</tr>
		</table>
		</div>
		<br></br>
		<script>
      function setCheckBox(obj){
          if(obj.type=='checkbox'){
            if(obj.checked==true){
              obj.value = '0';
            }else{
              obj.value = '1';
            }
            return obj
          }
      }
      </script>
		<div class="tab"><span>一般信息</span></div>
		<div id="pulseShow" class="da_mid"
			style="display: block; overflow-y: auto; overflow-x: auto; display: block; overflow-y: auto; overflow-x: auto; height: expression(((                 document.documentElement.clientHeight ||                 document.body.clientHeight) -                   600 ) );">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr height="30px">
				<td width="13%" class="green" align="right">计量方式：</td>
				<td width="20%"><form:select path="measMode" items="${measModeList}" id="measMode" itemLabel="name"
					itemValue="code" cssStyle="width:155px;" /></td>
				<td width="13%" class="green" align="right">端 口 号：</td>
				<td width="20%"><form:input path="gpInfos[0].port"></form:input></td>
				<td width="13%" class="green" align="right">波 特 率：</td>
				<td width="20%"><form:select path="meterInfo.baudrate" items="${btlList}" id="btl" itemLabel="name"
					itemValue="code" cssStyle="width:155px;" /></td>
			</tr>
			<tr height="30px">
				<td class="green" align="right">申请日期：</td>
				<td class="dom_date"><form:input path="appDate" cssClass="input_time"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" cssStyle="height:23px;width:150px;" /></td>
				<td class="green" align="right">投运日期：</td>
				<td class="dom_date"><form:input path="runDate" cssClass="input_time"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" cssStyle="height:23px;width:150px;" /></td>
				<td class="green" align="right">供电线路：</td>
				<td><form:select path="gpInfos[0].lineId" items="${lineList}" id="lineId" itemLabel="name" itemValue="code"
					cssStyle="width:155px;" /></td>
			</tr>
			<tr height="30px">
				<td class="green" align="right">运行状态：</td>
				<td><form:select path="statusCode" items="${runStatusList}" id="statusCode" itemLabel="name" itemValue="code"
					cssStyle="width:155px;" /></td>
				<td class="green" align="right">接线方式：</td>
				<td><form:select path="wiringMode" items="${wiringModeList}" id="wiringMode" itemLabel="name" itemValue="code"
					cssStyle="width:155px;" /></td>
			</tr>
			<tr height="30px">
				<td class="green" align="right">安装位置：</td>
				<td colspan=3><form:input path="mpAddr" size="60"></form:input></td>
				<td colspan="2"><form:checkbox path="gpInfos[0].sucratCptId" value="0" onclick="setCheckBox(this)" /> 功率累计 <form:checkbox
					path="gpInfos[0].computeFlag" value="0" onclick="setCheckBox(this)" /> 电量计算</td>
			</tr>
		</table>
		</div>
		<div style="text-align: center"><br />
		<input type="button" id="save" value="保 存" class="btnbg4" /></div>
		</div>
	</ul>
	</div>
</form:form>
</body>
<script>
val =  new Validation(document.forms[0],{onSubmit:true,onFormValidate : function(result,form) {
 return result;
}}
);

$(function(){
jQuery("#save").click(function(){
    if(val.validate()){
        jQuery(this).attr("disabled","disabled");
        if($("#_type").val()=="edit"){
          updatempinfo();
        }else if($("#_type").val()=="new"){
          addmpinfo();
        }
        
        jQuery(this).attr("disabled","");
    }else{
      jQuery(this).attr("disabled","");
    }
    })
      
})



getData= function(type){
var data;
  data = jQuery("form[id=mpinfo]").serialize(); 
return data;
}

addmpinfo = function(){
  var psFormData = getData('add');
  var url="${ctx}/archive/mpinfo.json";
  if(confirm("确定要保存该电表?")){
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
             opener.location.href ="${ctx}/archive/tginfo/${mpinfo.tgInfo.tgId}/edit";
             closeWin()
           }
         },error:function(e){
             alert(e.message);
         }
       });
  }
}

updatempinfo = function(){
  var psFormData = getData("update");
    var url="${ctx}/archive/mpinfo/${mpinfo.mpId}.json?_method=put";
    if(confirm("确定要更新该电表?")){
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
               opener.location.href ="${ctx}/archive/tginfo/${mpinfo.tgInfo.tgId}/edit";
               closeWin()
             }
           },error:function(e){
             alert("error")
               alert(e.getMessage());
           }
         });
    }
}

function   closeWin() 
{ 
        window.close(); 
} 
</script>
</html>