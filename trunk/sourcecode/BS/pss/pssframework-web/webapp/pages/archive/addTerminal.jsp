<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>终端</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
</head>
<body>
<div class="electric_lcon" id=electric_Con>
<ul class="default" id="electric_Con_1" style="padding: 5px;">
  <div class="tab"><span>终端信息</span></div>
  <div class="da_mid"
    style="display: block; overflow-y: auto; overflow-x: auto; width: expression((             document.documentElement.clientWidth ||             document.body.clientWidth) -10 ); height: expression(((             document.documentElement.clientHeight ||             document.body.clientHeight) -35 ) );">
  <form:form action="/archive/terminalinfo" modelAttribute="terminalinfo">
    <input type="hidden" name="<%=SystemConst.CONTROLLER_METHOD_TYPE%>" id="<%=SystemConst.CONTROLLER_METHOD_TYPE%>"
      value="${_type}">
    <spring:bind path="termObjRelas">
      <input type="hidden" name="termObjRelas[0].objId" id="termObjRelas[0].objId" value="${tgId}">
      <input type="hidden" name="termObjRelas[0].objType" id="termObjRelas[0].objType" value="2">
    </spring:bind>
    <table border="0" cellpadding="0" cellspacing="0" align="center">
      <tr height="30">
        <td width="15%" align="right" class="green"><font color="red">* </font>资产编号：</td>
        <td width="20%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:input path="assetNo" cssClass="required input2" maxlength="20" cssStyle="width:155px;" disabled="true"/>
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1"><form:input path="assetNo" cssClass="required input2" maxlength="20" cssStyle="width:155px;" /></security:authorize></td>
        <td width="10%" align="right" class="green"><font color="red">* </font>逻辑地址：</td>
        <td width="20%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:input path="logicalAddr" cssClass="required input2" maxlength="20" cssStyle="width:155px;" disabled="true"/>
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1"><form:input path="logicalAddr" cssClass="required input2" maxlength="20" cssStyle="width:155px;"/></security:authorize></td>
        <td width="10%" align="right" class="green">SIM：</td>
        <td width="25%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:input path="simNo" cssClass="required input2" maxlength="20" disabled="true"/>
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1"><form:input path="simNo" cssClass="required input2" maxlength="20" /></security:authorize></td>
      </tr>
      <tr height="30">
        <td align="right" class="green">设备规约：</td>
        <td><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:select path="protocolNo" id="protocolNo" itemLabel="name" itemValue="code" onchange=""
            items="${protocollist}" cssStyle="width:155px;" disabled="true"/>
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1"><form:select path="protocolNo" id="protocolNo" itemLabel="name" itemValue="code" onchange=""
            items="${protocollist}" cssStyle="width:155px;" /></security:authorize></td>
        <td align="right" class="green">通讯方式：</td>
        <td><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:select path="commMode" id="commMode" itemLabel="name" itemValue="code" onchange="" items="${commlist}"
            cssStyle="width:155px;" disabled="true"/>
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1"><form:select path="commMode" id="commMode" itemLabel="name" itemValue="code" onchange="" items="${commlist}"
            cssStyle="width:155px;" /></security:authorize></td>
        <td align="right" class="green">相 线：</td>
        <td><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:select path="wiringMode" id="wiringMode" itemLabel="name" itemValue="code" onchange=""
            items="${wiringlist}" cssStyle="width:155px;" disabled="true"/>
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1"><form:select path="wiringMode" id="wiringMode" itemLabel="name" itemValue="code" onchange=""
            items="${wiringlist}" cssStyle="width:155px;" /></security:authorize></td>
      </tr>
      <tr height="30">
        <td align="right" class="green">设备厂家：</td>
        <td><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:select path="madeFac" id="madeFac" itemLabel="name" itemValue="code" onchange="" items="${faclist}"
            cssStyle="width:155px;" disabled="true" />
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1"><form:select path="madeFac" id="madeFac" itemLabel="name" itemValue="code" onchange="" items="${faclist}"
            cssStyle="width:155px;" /></security:authorize></td>
        <td align="right" class="green">终端类型：</td>
        <td><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:select path="termType" id="termType" itemLabel="name" itemValue="code" onchange="" items="${typelist}"
            cssStyle="width:155px;" disabled="true"/>
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1"><form:select path="termType" id="termType" itemLabel="name" itemValue="code" onchange="" items="${typelist}"
            cssStyle="width:155px;" /></security:authorize></td>
        <td align="right" class="green">产 权：</td>
        <td><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:select path="pr" id="pr" itemLabel="name" itemValue="code" onchange="" items="${prlist}"
            cssStyle="width:155px;" disabled="true"/>
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1"><form:select path="pr" id="pr" itemLabel="name" itemValue="code" onchange="" items="${prlist}"
            cssStyle="width:155px;" /></security:authorize></td>
      </tr>
      <tr height="30">
        <td align="right" class="green">安装日期：</td>
        <td><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:input path="installDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" cssClass="input_time"
            readonly="readonly" cssStyle="width:155px;height:25px;" disabled="true"></form:input>
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1"> <form:input path="installDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" cssClass="input_time"
            readonly="readonly" cssStyle="width:155px;height:25px;"></form:input></security:authorize></td>
        <td align="right" class="green">当前状态：</td>
        <td><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:select path="curStatus" id="curStatus" itemLabel="name" itemValue="code" onchange=""
            items="${statuslist}" cssStyle="width:155px;" disabled="true"/>
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1"><form:select path="curStatus" id="curStatus" itemLabel="name" itemValue="code" onchange=""
            items="${statuslist}" cssStyle="width:155px;" /></security:authorize></td>
        <td align="right" class="green">运行状态：</td>
        <td><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:select path="runStatus" id="runStatus" itemLabel="name" itemValue="code" onchange=""
            items="${runStatuslist}" cssStyle="width:155px;" disabled="true"/>
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1"><form:select path="runStatus" id="runStatus" itemLabel="name" itemValue="code" onchange=""
            items="${runStatuslist}" cssStyle="width:155px;" /></security:authorize></td>
      </tr>
    </table>
  </form:form>
  <div style="text-align: center"><br></br>
  <security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
    <input type="button" id="save" value="保 存" class="btnbg4" />
  </security:authorize>&nbsp;<input type="button" id="close" value="关闭" class="btnbg4" onclick="closeWin()" /></div>
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
      if(val.validate()){
          jQuery(this).attr("disabled","disabled");
          if($("#_type").val()=="edit"){
            updateterminalinfo();
          }else if($("#_type").val()=="new"){
            addterminalinfo();
          }
          
          jQuery(this).attr("disabled","");
      }else{
        jQuery(this).attr("disabled","");
      }
      })
        
  })



  getData= function(type){
  var data;
    data = jQuery("form[id=terminalinfo]").serialize(); 
  return data;
  }

  addterminalinfo = function(){
    var psFormData = getData('add');
    var url="${ctx}/archive/terminalinfo.json";
    if(confirm("确定要保存该终端?")){
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
               closeWin()
             }
           },error:function(e){
           }
         });
    }
  }

  updateterminalinfo = function(){
    var psFormData = getData("update");
      var url="${ctx}/archive/terminalinfo/${terminalinfo.termId}.json?_method=put";
      if(confirm("确定要更新该终端?")){
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
                 opener.location.href ="${ctx}/archive/tginfo/${tgId}/edit";
            	   closeWin();
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
