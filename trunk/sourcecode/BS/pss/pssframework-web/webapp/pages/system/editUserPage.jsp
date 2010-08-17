<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="system.user.edit.title" /></title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
</head>
<body>
<form:form action="/system/user" method="post" modelAttribute="user">
<input type="hidden" id="_type" name="_type" value="${_type}">
  <div id="divUser" style="position: absolute; top: 0px; left: 0px; width: 100%; height: 100%;">
  <div id="bg">
  <ul id=datamenu_Option class="cb font1">
    <li class="curr"><a href="javascript:showUsernfo();" onfocus="blur()"><spring:message
      code="system.user.czyxx" /></a></li>
    <li><a href="javascript:showGw();" onfocus="blur()"><spring:message code="system.user.gwjs" /></a></li>
    <!-- <li><a href="javascript:showFw();" onfocus="blur()"><spring:message code="system.user.ywfw" /></a></li>
		<li><a href="javascript:showQx();" onfocus="blur()"><spring:message code="system.user.czqx" /></a></li> -->
  </ul>
  </div>
  <table width="99%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="15%" height="30" align="right"><font color="#ff0000">*</font><spring:message code="system.user.zh" />：</td>
      <td width="30%" align="left"><form:input path="staffNo" cssStyle="width:150" cssClass="required input2 min-length-5"/></td>
      <td width="15%" height="30" align="right"><font color="#ff0000">*</font><spring:message code="system.user.mc" />：</td>
      <td width="30%" align="left"><form:input path="name" cssStyle="width:150" cssClass="required input2 min-length-5"/></td>
    </tr>
    <tr>
      <td align="right"><font color="#ff0000">*</font><spring:message code="system.user.mm" />：</td>
      <td align="left"><form:password path="passwd" id="passwd"  showPassword="true" cssStyle="width:150" cssClass="required input2" /></td>
      <td align="right"><font color="#ff0000">*</font><spring:message code="system.user.qrmm" />：</td>
      <td align="left"><input type="password" name="passwd_rep" id="passwd_rep" value="${user.passwd}" class="required input2"
        style="width: 150"></td>
    </tr>
    <tr>
      <td align="right"><spring:message code="system.user.ssdw" />：</td>
      <td align="left"><form:select path="orgInfo.orgId" items="${orgInfo}" disabled="${disabled}" id="orgId"
        itemLabel="orgName" itemValue="orgId" cssStyle="width:150px;" /></td>
      <td align="right"><spring:message code="system.user.dh" />：</td>
      <td align="left"><form:input path="mobile" cssStyle="width:150" /></td>
    </tr>
    <tr>
      <td align="right"><spring:message code="system.user.zt" />：</td>
      <td align="left"><form:select path="enable" items="${userStat}" disabled="${disabled}" id="enable"
        itemLabel="name" itemValue="code" cssStyle="width:150px;" /></td>
    </tr>
  </table>
  <table align="center">
    <tr>
      <td width="100%" height="30" align="center"><input type="button" class="btnbg4" name="queding2"
        value='<spring:message code="system.button.qd" />' /> &nbsp; <input type="button" class="btnbg4" name="cancel2"
        value='<spring:message code="system.button.qx" />' onclick="closeWin()" /></td>
    </tr>
  </table>
  </div>
  <div id="divFw" style="display: none; position: absolute; top: 0px; left: 0px; width: 100%; height: 100%;">
  <div id="bg">
  <ul id=datamenu_Option class="cb font1">
    <li><a href="javascript:showUsernfo();" onfocus="blur()"><spring:message code="system.user.czyxx" /></a></li>
    <li class="curr"><a href="javascript:showFw();" onfocus="blur()"><spring:message code="system.user.ywfw" /></a></li>
    <li><a href="javascript:showGw();" onfocus="blur()"><spring:message code="system.user.gwjs" /></a></li>
    <li><a href="javascript:showQx();" onfocus="blur()"><spring:message code="system.user.czqx" /></a></li>
  </ul>
  </div>
  <div class="da_con" align="center">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <thead>
      <tr>
        <th width="20%"><spring:message code="system.user.xh" /></th>
        <th width="40%"><spring:message code="system.user.dxmc" /></th>
        <th width="20%"><spring:message code="system.user.sq" /><input type="checkbox" name="select1"
          onclick="selectAllBox(this,'sRole1')" /></th>
        <th width="20%"><spring:message code="system.user.ksq" /><input type="checkbox" name="select2"
          onclick="selectAllBox(this,'rRole1')" /></th>
      </tr>
    </thead>
    <tbody>
      <tr align="center" class="trmainstyle">
        <td height="20"></td>
        <td height="20"></td>
        <td></td>
      </tr>
    </tbody>
  </table>
  </div>
  <table align="center">
    <tr>
      <td width="100%" height="30" align="center"><input type="button" class="btnbg4" name="queding2"
        value='<spring:message code="system.button.qd" />' /> &nbsp; <input type="button" class="btnbg4" name="cancel2"
        value='<spring:message code="system.button.qx" />' onclick="closeWin()" /></td>
    </tr>
  </table>
  </div>
  <div id="divGw"
    style="display: none; position: absolute; top: 0px; left: 0px; width: 100%; height: 100%; OVERFLOW-Y: AUTO;">
  <div id="bg">
  <ul id=datamenu_Option class="cb font1">
    <li><a href="javascript:showUsernfo();" onfocus="blur()"><spring:message code="system.user.czyxx" /></a></li>
    <li class="curr"><a href="javascript:showGw();" onfocus="blur()"><spring:message code="system.user.gwjs" /></a></li>
    <!--  <li><a href="javascript:showFw();" onfocus="blur()"><spring:message code="system.user.ywfw" /></a></li>
		<li><a href="javascript:showQx();" onfocus="blur()"><spring:message code="system.user.czqx" /></a></li>-->
  </ul>
  </div>
  <div class="da_con" align="center">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <thead>
      <tr>
        <th>角色名称</th>
        <th>角色说明</th>
        <th><input type="checkbox" name="select1" onclick="setAllCheckboxState('roleIds',this.checked)" /></th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${roleInfos}" var="item" varStatus="status">
        <tr height="20" <c:if test="${status.count%2==0}">bgcolor="#f3f3f3"</c:if>>
          <td>${item.roleName}</td>
          <td>${item.roleRemark}</td>
          <td><form:checkbox path="roleIds" value="${item.roleId}" /></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  </div>
  <table align="center">
    <tr>
      <td width="100%" height="30" align="center"><input type="button" class="btnbg4" name="queding2"
        value='<spring:message code="system.button.qd" />' /> &nbsp; <input type="button" class="btnbg4" name="cancel2"
        value='<spring:message code="system.button.qx" />' onclick="closeWin()" /></td>
    </tr>
  </table>
  </div>
  <div id="divQx"
    style="display: none; position: absolute; top: 0px; left: 0px; width: 100%; height: 100%; OVERFLOW-Y: AUTO;">
  <div id="bg">
  <ul id=datamenu_Option class="cb font1">
    <li><a href="javascript:showUsernfo();" onfocus="blur()"><spring:message code="system.user.czyxx" /></a></li>
    <li><a href="javascript:showFw();" onfocus="blur()"><spring:message code="system.user.ywfw" /></a></li>
    <li><a href="javascript:showGw();" onfocus="blur()"><spring:message code="system.user.gwjs" /></a></li>
    <li class="curr"><a href="javascript:showQx();" onfocus="blur()"><spring:message code="system.user.czqx" /></a></li>
  </ul>
  </div>
  <div class="tableContainer" style="width: 99%;">
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <thead>
      <tr class="trheadStyle">
        <th width="10%"><spring:message code="system.user.xh" /></th>
        <th width="20%"><spring:message code="system.user.dxmc" /></th>
        <th width="10%"><spring:message code="system.user.sq" /><input type="checkbox" name="select1"
          onclick="selectAllBox(this,'sRole3')" /></th>
        <th width="10%"><spring:message code="system.user.ksq" /><input type="checkbox" name="select2"
          onclick="selectAllBox(this,'rRole3')" /></th>
      </tr>
    </thead>
    <tbody>
      <tr align="center" class="trmainstyle">
        <td height="20"></td>
        <td height="20"></td>
      </tr>
    </tbody>
  </table>
  </div>
  <table align="center">
    <tr>
      <td width="100%" height="30" align="center"><input type="button" class="btnbg4" name="queding2"
        value='<spring:message code="system.button.qd" />'"/> &nbsp; <input type="button" class="btnbg4" name="cancel2"
        value='<spring:message code="system.button.qx" />'" onclick="closeWin()" /></td>
    </tr>
  </table>
  </div>
</form:form>
</body>
<script type="text/javascript">
var contextPath = "${ctx}";
//
function showUsernfo() {
    document.all.divUser.style.display = "";
    document.all.divFw.style.display = "none";
    document.all.divGw.style.display = "none";
    document.all.divQx.style.display = "none";
}
function showQx() {
    document.all.divUser.style.display = "none";
    document.all.divFw.style.display = "none";
    document.all.divGw.style.display = "none";
    document.all.divQx.style.display = "";
}
function showFw() {
    document.all.divUser.style.display = "none";
    document.all.divFw.style.display = "";
    document.all.divGw.style.display = "none";
    document.all.divQx.style.display = "none";
}
function showGw() {
    document.all.divUser.style.display = "none";
    document.all.divFw.style.display = "none";
    document.all.divGw.style.display = "";
    document.all.divQx.style.display = "none";
}

// 判断岗位是否选择上
function gwIsChecked() {
    var th = document.forms[0];
    for(var i=0;i<th.elements.length;i++) {
       var o=th.elements[i];
       if(o.name=="sRole2" && o.checked)
        {
           return true;
        }
    }
    return false;
} 

// 判断业务范围是否选择上

function fwIsChecked() {
    var th=document.forms[0];
    for(var i=0;i<th.elements.length;i++) {
        var o=th.elements[i];
        if(o.name=="sRole1" && o.checked) {
            return true;
        }
    }
    return false;
}

//提交处理
checkPassword=function(){
	var password = $("#passwd").val();
	var password_rep = $("#passwd_rep").val();

    if(password!=password_rep) {
        alert('<spring:message code="system.errors.passwd.different"/>');
        return false;
    }

    if(!hasOneChecked('roleIds')){
    	return confirm('<spring:message code="system.user.message.gw.null" />');
    }

    return true
}

$(function(){
    var type ='${_type}';
    
	$("[name=queding2][type=button]").click(function(){


		
		if(val.validate()){
		   
		      $(this).attr("disabled","disabled");
		      
	          if( checkPassword()){
                  if(type=='update' || type=='edit'){
                	  updateUserInfo();
                  }else if(type=='new'){
                	  newUserInfo();
                  }
	          }else{
             
	          }
		      
		      jQuery(this).attr("disabled","");
			}else{
				jQuery(this).attr("disabled","");
			}
	});
})
updateUserInfo = function(){
	 var formData = getData("update");
	    var url="${ctx}/system/user/${user.empNo}.json?_method=put";
	    if(confirm("确定要更新该账号?")){
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
	            	 opener.parent.parent.changeType(3)
	          	   //opener.parent.userManager.location.href ="${ctx}/system/user/${user.empNo}";
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

newUserInfo = function(){

	 var formData = getData("add");
	    var url="${ctx}/system/user.json";
	    if(confirm("确定要添加该账号?")){
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
                 opener.parent.parent.changeType(3)
		          	   //opener.parent.userManager.location.href ="${ctx}/system/user/"+json['userId'];
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
	  data = jQuery("form[id=user]").serialize(); 
	return data;
}

function   closeWin() 
{ 
        window.close(); 
} 

function event(obj,no) {
	var name=obj.name;
	var rname = "r"+name.substring(1);
	var box=document.getElementsByName(rname)[no-1];
	if(obj.checked){
		box.disabled=false;
	}else{
		if(box.checked){
			box.click()
		}
		box.disabled=true;
	}
}


val =  new Validation(document.forms[0],{onSubmit:true,onFormValidate : function(result,form) {
   return result;
  }}
  );
</script>
</html>
