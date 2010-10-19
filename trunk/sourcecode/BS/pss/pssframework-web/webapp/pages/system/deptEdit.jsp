<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>部门档案</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
</head>
<body>
<div class="electric_lcon" id=electric_Con>
<ul class="default" id="electric_Con_1" style="padding: 5px;">
  <div class="tab"><span>部门信息</span></div>
  <div class="da_mid"
    style="display: block; overflow-y: auto; overflow-x: auto; width: expression((   document.documentElement.clientWidth ||             document.body.clientWidth) -10 ); height: expression(((             document.documentElement.clientHeight ||             document.body.clientHeight) -35 ) );">
  <form:form action="/system/orginfo" modelAttribute="orginfo">
    <input type="hidden" name="<%=SystemConst.CONTROLLER_METHOD_TYPE%>" id="<%=SystemConst.CONTROLLER_METHOD_TYPE%>"
      value="${_type}">
    <c:choose>
      <c:when test="${_type=='edit' || _type=='new'}">
        <c:set var="disabled" value="false"></c:set>
      </c:when>
      <c:otherwise>
        <c:set var="disabled" value="true"></c:set>
      </c:otherwise>
    </c:choose>
    <table width="95%" border="0" cellspacing="0" cellpadding="0">
      <form:hidden path="orgId" />
      <tr height="30">
        <td width="15%" align="right" class="green"><spring:message code="system.dept.dwbh" />（<font
          color="#ff0000">*</font>）： <input type="hidden" name="oldOrgNo" value='${orginfo.orgNo}' /></td>
        <td width="20%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:input path="orgNo" cssClass="required input2" maxlength="20" cssStyle="width:155px;" disabled="true" />
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:input path="orgNo" cssClass="required input2 validate-ajax-${ctx}/system/orginfo/checkOrgNo.json" maxlength="20" cssStyle="width:155px;" readonly="true"/>
        </security:authorize></td>
        <td width="10%" align="right" class="green"><spring:message code="system.dept.dwmc" />（<font
          color="#ff0000">*</font>）：</td>
        <td width="25%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:input path="orgName" disabled="true" />
        </security:authorize> <security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:input path="orgName" cssClass="required" />
        </security:authorize></td>
        <td width="10%" align="right" class="green"><spring:message code="system.dept.dwlx" />：</td>
        <td width="20%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:select path="orgType" itemLabel="name" itemValue="code" onchange="" items="${orgtype}"
            cssStyle="width:155px;" disabled="true" />
        </security:authorize> <security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:select path="orgType" itemLabel="name" itemValue="code" onchange="" items="${orgtype}"
            cssStyle="width:155px;"></form:select>
        </security:authorize></td>
      </tr>
      <tr height="30">
      <form:hidden path="parentOrgInfo.orgId"/>
        <td align="right" class="green"><spring:message code="system.dept.sjdwmc" />：</td>
        <td>${orginfo.parentOrgInfo.orgName}</td>
        <td align="right" class="green"><spring:message code="system.dept.sjdwlx" />：</td>
        <td><pss:code code="${orginfo.parentOrgInfo.orgType}" codeCate="<%=SystemConst.CODE_ORG_TYPE%>" /></td>
        <td align="right" class="green"><spring:message code="system.dept.pxh" />（<font color="#ff0000">*</font>）：</td>
        <td><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:input path="sortNo" disabled="true" />
        </security:authorize> <security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:input path="sortNo"
            cssClass="required validate-number validate-ajax-${ctx}/system/orginfo/checkSortNo.json" />
        </security:authorize></td>
      </tr>
      <tr height="30">
        <td width="100%" colspan="6" align="right"><security:authorize
          ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <input id="save" name="save" type="button" class="btnbg4" value="保存">
        </security:authorize></td>
      </tr>
    </table>
  </form:form>
</ul>
</div>
</body>
<script>
val =  new Validation(document.forms[0],{immediate:true,onSubmit:true,onFormValidate : function(result,form) {
 return result;
}}
);
$(function(){
  $("#save").click(function(){
	  var ret = val.result();
	  if(ret==""){
		  val.validate();
	  }
		 if(ret==true){
          $(this).attr("disabled","disabled");
          if($("#orgId").val()){
            updateorginfo();
          }else {
            addorginfo();
          }
          
          $(this).attr("disabled","");
      }else{
        $(this).attr("disabled","");
      }
      });
        
  });



  getData= function(type){
  var data;
    data = $("form[id=orginfo]").serialize(); 
  return data;
  };

  addorginfo = function(){
    var psFormData = getData('add');
    var url="${ctx}/system/orginfo.json";
    if(confirm("确定要保存该部门?")){
      $.ajax({
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
                 $("#orgId").val(json['orgId']);
                 parent.parent.tabscontainerleft.tree.location.href = "${ctx}/tree";
             }
           },error:function(e){
           }
         });
    }
  };

  updateorginfo = function(){
    var psFormData = getData("update");
    var orgId =$("#orgId").val();
      var url="${ctx}/system/orginfo/"+orgId+".json?_method=put";
      if(confirm("确定要更新该部门?")){
        $.ajax({
             url: url,
             data:psFormData,
             dataType:'json',
             type:'post',
             cache: false,
             success: function(json){
           var msg = json["<%=SystemConst.CONTROLLER_AJAX_MESSAGE%>"];
           var isSucc = json["<%=SystemConst.CONTROLLER_AJAX_IS_SUCC%>"];
							alert(msg);
							if (isSucc) {
								parent.parent.tabscontainerleft.tree.location.href = "${ctx}/tree";
							}
						},
						error : function(e) {
							alert("error")
							alert(e.getMessage());
						}
					});
		}
	}

	function closeWin() {
		window.close();
	}
</script>
</html>
