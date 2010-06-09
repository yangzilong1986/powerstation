<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>变压器</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
</head>
<body>
<div class="electric_lcon" id=electric_Con>
<ul class="default" id="electric_Con_1" style="padding: 5px;">
  <div class="tab"><span>变压器信息</span></div>
  <div class="da_con"><form:form action="/archive/traninfo" modelAttribute="traninfo">
    <form:hidden path="equipId" />
    <form:hidden path="tgId" />
    <table border="0" cellpadding="0" cellspacing="0" align="center">
      <tr>
        <td width="13%" class="green"><font color="red">* </font>变压器名称：</td>
        <td width="20%" class="input2"><form:input path="tranName" cssClass="required" maxlength="16" /></td>
        <td width="13%" class="green">变压器型号：</td>
        <td width="21%" class="input2"><form:select path="typeCode" id="typeCode" itemLabel="name" itemValue="code"
          onchange="" items="${typelist}" /></td>
        <td colspan="2"></td>
      </tr>
      <tr>
        <td width="13%" class="green">容 量：</td>
        <td width="20%" class="input2"><form:input path="plateCap" /> kVA</td>
        <td class="green">运行状态：</td>
        <td class="input2"><form:select path="runStatusCode" id="runStatusCode" itemLabel="name" itemValue="code"
          onchange="" items="${statuslist}" /></td>
      </tr>
      <tr>
        <td class="green">额定电压 _ 高压:</td>
        <td class="input2"><form:select path="rvHv" id="rvHv" itemLabel="name" itemValue="code" onchange=""
          items="${voltlist}" /></td>
        <td class="green">额定电压 _ 中压：</td>
        <td class="input2"><form:select path="rvMv" id="rvMv" itemLabel="name" itemValue="code" onchange=""
          items="${voltlist}" /></td>
        <td class="green">额定电压 _ 低压：</td>
        <td class="input2"><form:select path="rvLv" id="rvLv" itemLabel="name" itemValue="code" onchange=""
          items="${voltlist}" /></td>
      </tr>
      <tr>
        <td class="green">额定电流 _ 高压：</td>
        <td class="input2"><form:select path="rcHv" id="rcHv" itemLabel="name" itemValue="code" onchange=""
          items="${ratedlist}" /></td>
        <td class="green">额定电流 _ 中压：</td>
        <td class="input2"><form:select path="rcMv" id="rcMv" itemLabel="name" itemValue="code" onchange=""
          items="${ratedlist}" /></td>
        <td class="green">额定电流 _ 低压：</td>
        <td class="input2"><form:select path="rcLv" id="rcLv" itemLabel="name" itemValue="code" onchange=""
          items="${ratedlist}" /></td>
      </tr>
      <tr>
        <td class="green">安装日期：</td>
        <td class="input_time"><form:input  path="instDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"></form:input></td>
        <td class="green">安装地址：</td>
        <td colspan="5" class="input2"><form:input path="instAddr"></form:input></td>
      </tr>
    </table>
  </form:form>
  <div class="guidePanel"><input type="button" id="save" value="保 存" onClick="save();" /></div>
  </div>
</ul>
</div>
</body>
<script type="text/javascript">
val =  new Validation(document.forms[0],{onSubmit:true,onFormValidate : function(result,form) {
 return result;
}}
);


var contextPath = '<peis:contextPath/>';

//保存变压器信息
var tgFlag=null;


//select框初始化
function selectInit(){
  $("#modelCode").val(${object_tran.modelCode});
  $("#tranStatus").val(${object_tran.tranStatus});
  $("#firstVlot").val(${object_tran.firstVlot});
  $("#secondVlot").val(${object_tran.secondVlot});
  $("#thirdVlot").val(${object_tran.thirdVlot});
}


getData= function(type){
  var data;
  if(type == "add"){
    data = jQuery("form[id=traninfo]").serialize(); 
  }else {
    data = jQuery("form[id=traninfo]").serialize(); 
  }
  return data;
  }


addTran = function(){
    var fromData = getData('add');
    var url="${ctx}/archive/traninfo.json";
    if(confirm("确定要保存该变压器?")){
      jQuery.ajax({
           url: url,
           data:fromData,
           dataType:'json',
           type:'POST',
           cache: false,
           success: function(json){
             var msg = json['msg'];
             var isSucc = json['isSucc'];
             //jQuery("#tranId").val(json['tranId']);
             //alert(json['tranId'])
             parent.location.href="${ctx}/arvhive/tginfo/"+${"#tgId"}.val();
             window.close();
           },error:function(e){
               alert("error");
               alert(e.message);
           }
         });
    }
  }

jQuery(function(){
  jQuery("#save").click(function(){
  if(val.validate()){
      jQuery(this).attr("disabled","disabled");
      addTran();
      jQuery(this).attr("disabled","");
  }else{
    jQuery(this).attr("disabled","");
  }
  });
})
  
</script>
</html>