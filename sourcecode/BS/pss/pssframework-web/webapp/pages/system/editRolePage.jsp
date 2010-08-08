<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="system.role.edit.title" /></title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript">
var contextPath = "${ctx}";
function submitDisposal(form) {
    var wmf = window.frames.menuFunctionFrame;
    var sMenuFunctionID = "";
    if(wmf.bTreeviewLoaded) {
        sMenuFunctionID = wmf.getSelectedString1();
        //alert(sMenuFunctionID);
        if(sMenuFunctionID == "") {
            alert("请选择功能项");
            return false;
        }
        document.all.functionID.value = sMenuFunctionID;
    }
    return validateRoleForm(form);
}

saveRole  = function(){
 document.getElementById("resourceIds").value = menuFunctionFrame.getCheckValues();
 alert(document.getElementById("resourceIds").value);
 updateRoleInfo();
}

updateRoleInfo = function(){
   var FormData = getData("update");
      var url="${ctx}/system/role/${role.roleId}.json?_method=put";
      if(confirm("确定要更新该角色?")){
        jQuery.ajax({
             url: url,
             data:FormData,
             dataType:'json',
             type:'post',
             cache: false,
             success: function(json){
              var msg = json['<%=SystemConst.CONTROLLER_AJAX_MESSAGE%>'];
              var isSucc = json['<%=SystemConst.CONTROLLER_AJAX_IS_SUCC%>'];
               alert(msg);
               if(isSucc){
                 opener.parent.userManager.location.href ="${ctx}/system/role/${role.roleId}";
                 closeWin();
               }
             },
             error:function(XmlHttpRequest)
             {
             alert("更新失败;"+XmlHttpRequest.responseText +  XmlHttpRequest.statusText);
             }
           });
      }

}

getData= function(type){
  var data;
    data = jQuery("form[id=role]").serialize(); 
  return data;
}

function   closeWin() 
{ 
       window.close(); 
} 



</script>
</head>
<body>
<form:form action="/system/role" method="post" modelAttribute="role">
  <form:hidden path="roleId"/>
  <form:hidden path="roleType"/>
  <div class="tab">
  <ul>
    <li id="tab_1" class="tab_on"><a href="#" onClick="return false;" onFocus="blur()"><spring:message code="system.role.edit.title" /></a></li>
  </ul>
  </div>
  <div style="width: expression(((document.documentElement.clientWidth ||   document.body.clientWidth) -18 ) );">
  <div id="tool">
  <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="15%"><spring:message code="system.role.jsmc" /><font color="#ff0000">*</font>：</td>
      <td><form:input path="roleName" /></td>
    </tr>
    <tr>
      <td width="15%"><spring:message code="system.role.jssm" /><font color="#ff0000">*</font>：</td>
      <td><form:textarea path="roleRemark" cols="49" rows="5" /></td>
    </tr>
    <tr>
      <td colspan="2" style="height: 10px;"></td>
    </tr>
    <tr>
      <td width="100%" style="height: 280px; margin-top: 10px; margin-bottom: 10px;" colspan="2" align="center">
      <form:hidden path="resourceIds" id="resourceIds"/>
      <iframe style="border: opx solid #5d90d7;" name="menuFunctionFrame" src="${ctx}/tree/fun?checked=${role.resourceIds}"
      
        scrolling="auto" width="98%" height="100%" frameborder="1" align="middle"> </iframe></td>
    </tr>
    <tr>
      <td colspan="2" style="height: 10px;"></td>
    </tr>
    <tr>
      <td width="100%" height="30" colspan="2" align="center">
      
      <input
        type="button" name="cancel2" value='<spring:message code="system.button.qd" />' 
        onclick="saveRole()" />
        
       <input
        type="button" name="cancel2" value='<spring:message code="system.button.qx"/>' 
        onclick="top.GB_hide()" /></td>
    </tr>
  </table>
  </div>
  </div>
</form:form>
</body>
</html>
