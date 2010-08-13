<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="system.passwd.edit.title" /></title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript">
var errorNoPasswd='<spring:message code="system.passwd.errorNoPasswd"/>';
var errorNoNewPasswd='<spring:message code="system.passwd.errorNoNewPasswd"/>';
var errorNewPasswdLength='<spring:message code="system.passwd.errorNewPasswdLength"/>';
var errorNoConPasswd='<spring:message code="system.passwd.errorNoConPasswd"/>';
var errorPasswdDiff='<spring:message code="system.passwd.errorPasswdDiff"/>';
//提交处理
function checkPassword() {
    if(document.all.oldPasswd.value == "") {
        alert(errorNoPasswd);
        return false;
    }
    if(document.all.newPasswd.value == "") {
        alert(errorNoNewPasswd);
        return false;
    }
    if(document.all.newPasswd.value.length<5) {
        alert(errorNewPasswdLength);
        return false;
    }
    if(document.all.cofirmPasswd.value == "") {
        alert(errorNoConPasswd);
        return false;
    }

    if(document.all.newPasswd.value != document.all.cofirmPasswd.value) {
        alert(errorPasswdDiff);
        return false;
    }

    return true;
}
function fresh(){
	parent.changeType(4);
}


updatePassWord = function(){

      if(checkPassword()){
      	var formData = getData();
          var url="${ctx}/system/password/${user.empNo}.json?_method=put";
          if(confirm("确定要更新该密码?")){
            jQuery.ajax({
                 url: url,
                 data:formData,
                 dataType:'json',
                 type:'post',
                 cache: false,
                 success: function(json){
          	  	var msg = json['<%=SystemConst.CONTROLLER_AJAX_MESSAGE%>'];
                	var isSucc = json['<%=SystemConst.CONTROLLER_AJAX_IS_SUCC%>'];
                   alert(msg);
                   if(isSucc){
                  	 parent.changeType(4)
                	   //opener.parent.userManager.location.href ="${ctx}/system/user/${user.empNo}";
                	  // closeWin();
                   }
                 },
                 error:function(XmlHttpRequest)
                 {
                 alert("更新失败;"+XmlHttpRequest.responseText +  XmlHttpRequest.statusText);
                 }
               });
          }
      }
	  
}


getData= function(type){
  var data;
    data = jQuery("form[id=user]").serialize(); 
  return data;
}

function   closeWin() 
{ 
        window.close(); 
} 

</script>
</head>
<body>
<form:form action="/system/password" modelAttribute="user">
  <div id="main" style="height: 100%;">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="100%" height="30" colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td width="46%" height="40" align="right"><spring:message code="system.passwd.srymm" />：<font
        color="#ff0000">*</font></td>
      <td width="54%" align="left"><form:password path="oldPasswd" cssStyle="width:150" cssClass="required input2" /></td>
    </tr>
    <tr>
      <td height="40" align="right"><spring:message code="system.passwd.srxmm" />：<font color="#ff0000">*</font></td>
      <td align="left"><form:password path="newPasswd" cssStyle="width:150" cssClass="required input2" /></td>
    </tr>
    <tr>
      <td height="40" align="right"><spring:message code="system.passwd.qrxmm" />：<font color="#ff0000">*</font></td>
      <td align="left"><input type="password" name="cofirmPasswd" class="required input2" style="width: 150"></td>
    </tr>
    <tr>
      <td width="100%" height="30" colspan="2">&nbsp;</td>
    </tr>
    <tr>
      <td width="100%" height="50" colspan="2" align="center"><input type="button" class=""
        onclick="updatePassWord()" value="<spring:message code="system.button.qd" />"> &nbsp; <input
        type="reset" class="" value="<spring:message code="system.button.qx" />"></td>
    </tr>
  </table>
  </div>
</form:form>
</body>
<script type="text/javascript">
val =  new Validation(document.forms[0],{onSubmit:true,onFormValidate : function(result,form) {
   return result;
  }}
  );
</script>
</html>
