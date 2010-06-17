<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
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

</script>
</head>
<body>
<div class="electric_lcon" id="electric_Con">
<ul class="default" id="electric_Con_1" style="padding: 5px;">
  <div class="tab"><span>漏电保护器</span></div>
  <div class="da_mid"
    style="display: block; overflow-y: auto; overflow-x: auto; width: expression((         document.documentElement.clientWidth ||         document.body.clientWidth) -10 ); height: expression(((         document.documentElement.clientHeight ||         document.body.clientHeight) -35 ) );">
  <div><form:form action="/archive/psinfo" modelAttribute="psinfo">
    <input type="hidden" id="_type" name="_type" value="${_type}" />
     <input type="hidden" id="tgId" name="tgId" value="${tgId}" />
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
      <tr height="40">
        <td width="20%" align="right" class="green"><font color="red">* </font>资产编号：</td>
        <td width="30%"><form:input path="assetNo" cssClass="required input2" /></td>
        <td width="20%" align="right" class="green">集中器地址：</td>
        <td width="30%"><form:select path="terminalInfo" items="${termList}" id="termId" itemLabel="logicalAddr"
          itemValue="termId" cssStyle="width:155px;" /></td>
      </tr>
      <tr height="40">
        <td align="right" class="green">通信方式：</td>
        <td><form:select path="commModeGm" items="${commModeList}" id="commModeGm" itemLabel="name"
          itemValue="code" cssStyle="width:155px;" /></td>
        <td align="right" class="green">漏保型号：</td>
        <td><form:select path="modelCode" items="${psModelList}" id="modelCode" itemLabel="name" itemValue="code"
          cssStyle="width:155px;" /></td>
      </tr>
      <tr height="40">
        <td align="right" class="green">波特率：</td>
        <td><form:select path="btl" items="${btlList}" id="btl" itemLabel="name" itemValue="code"
          cssStyle="width:155px;" /></td>
        <td align="right" class="green">额定电流：</td>
        <td><form:select path="ratedEc" items="${ratedEcList}" id="ratedEc" itemLabel="name" itemValue="code"
          cssStyle="width:155px;" /></td>
      </tr>
      <tr height="40">
        <td align="right" class="green">剩余电流档位：</td>
        <td><form:select path="remcGear" items="${remcGearList}" id="remcGear" itemLabel="name" itemValue="code"
          cssStyle="width:155px;" /></td>
        <td align="right" class="green">剩余电流档位当前值：</td>
        <td><form:select path="remcGearValue" items="${remcGearValueList}" id="remcGearValue" itemLabel="name"
          itemValue="code" cssStyle="width:155px;" /></td>
      </tr>
      <tr height="40">
        <td align="right" class="green">漏电分断延迟档位：</td>
        <td><form:select path="offDelayGear" items="${offDelayGearList}" id="offDelayGear" itemLabel="name"
          itemValue="code" cssStyle="width:155px;" /></td>
        <td align="right" class="green">漏电分断延迟时间：</td>
        <td><form:select path="offDelayValue" items="${offDelayValueList}" id="offDelayValue" itemLabel="name"
          itemValue="code" cssStyle="width:155px;" /></td>
      </tr>
      <tr height="40">
        <td align="right" class="green">功能设置字：</td>
        <td><form:input path="functionCode" cssClass="input2" /></td>
        <td align="right" class="green">漏保类型：</td>
        <td><form:select path="psType" items="${psTypeList}" id="psType" itemLabel="name" itemValue="code"
          cssStyle="width:155px;" /></td>
      </tr>
      <tr>
        <td align="right" class="green">漏保地址：</td>
        <td colspan="3"><form:input path="psAddr" size="98" cssClass="input2"/></td>
      </tr>
    </table>
  </form:form></div>
  <div><input type="button" id="save" value="保 存" /></div>
  </div>
</ul>
</div>
</body>
<script>
val =  new Validation(document.forms[0],{onSubmit:true,onFormValidate : function(result,form) {
 return result;
}}
);

$(function(){
jQuery("#save").click(function(){
  alert(123)
    if(val.validate()){
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
    })
      
})



getData= function(type){
var data;
if(type == "add"){
  data = jQuery("form[id=psinfo]").not("#psId").serialize(); 
}else {
  data = jQuery("form[id=psinfo]").serialize(); 
}
return data;
}

addpsinfo = function(){
  var tgFromData = getData('add');
  var url="${ctx}/archive/psinfo.json";
  if(confirm("确定要保存该漏电保护器?")){
    jQuery.ajax({
         url: url,
         data:tgFromData,
         dataType:'json',
         type:'POST',
         cache: false,
         success: function(json){
           var msg = json['msg'];
           var isSucc = json['isSucc'];
           jQuery("#psId").val(json['psId']);
         },error:function(e){
             alert("error");
             alert(e.message);
         }
       });
  }
}

updatepsinfo = function(){
  var tgFromData = getData("update");
    var url="${ctx}/archive/psinfo/"+jQuery("#psId").val()+'.json?_method=put';
    if(confirm("确定要更新该漏电保护器?")){
      jQuery.ajax({
           url: url,
           data:tgFromData,
           dataType:'json',
           type:'post',
           cache: false,
           success: function(json){
             var msg=json['msg'];
             var isSucc = json['isSucc'];
     
              alert(msg);
           },error:function(e){
             alert("error")
               alert(e.getMessage());
           }
         });
    }
}

</script>
</html>