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

 var type ='${_type}';
 if(type=='update' || type=='edit'){
	 updateRoleInfo();
 }else if(type=='new'){
	  newRoleInfo();
 }
 
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
            	 opener.parent.parent.changeType(2)
                 //opener.parent.roleDetail.location.href ="${ctx}/system/role/${role.roleId}";
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


newRoleInfo = function(){
     var FormData = getData("new");
        var url="${ctx}/system/role.json";
        if(confirm("确定要新建角色?")){
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
                	 opener.parent.parent.changeType(2)
                   //opener.parent.roleDetail.location.href ="${ctx}/system/role/"+json['roleId'];
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



function showRole() {
    document.all.roleInfo.style.display = "";
    document.all.resources.style.display = "none";
    document.all.authority.style.display = "none";
}
function showResources() {
    document.all.roleInfo.style.display = "none";
    document.all.resources.style.display = "";
    document.all.authority.style.display = "none";
}
function showAuthority() {
    document.all.roleInfo.style.display = "none";
    document.all.resources.style.display = "none";
    document.all.authority.style.display = "";
}

var lastQueryType = 2;

function changeType(obj) {
    var lastTd = document.getElementById("query" + lastQueryType);
    var nowTd = document.getElementById("query" + obj);
    lastTd.className = 'none';
    nowTd.className = 'curr';
    lastQueryType = obj;
    var url = contextPath;
    
    if(lastQueryType == 2) {
    	showRole()
    }
    if(lastQueryType == 3) {
    	showResources()
    }
    if(lastQueryType == 4) {
    	showAuthority()
    }
}

</script>
</head>
<body>
<form:form action="/system/role" method="post" modelAttribute="role">
  <div style="padding: 2 2 2 2">
  <div id="bg">
  <ul id=datamenu_Option class="cb font1">
    <li class="curr" id="query2"><a href="javascript:changeType(2);" onfocus="blur()">角色信息</a></li>
    <li id="query3"><a href="javascript:changeType(3);" onfocus="blur()">功能信息</a></li>
    <li id="query4"><a href="javascript:changeType(4);" onfocus="blur()">权限列表</a></li>
    <!-- <li><a href="javascript:showQx();" onfocus="blur()"><spring:message code="system.user.czqx" /></a></li> -->
  </ul>
  </div>
  <div class="content">
  <div id="cont_1">
  <div class="tableContainer"
    style="height: expression(((   document.documentElement.clientHeight || document.body.clientHeight) -70 ) )">
  <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0" id="roleInfo">
    <tr style="height: 20px;">
      <td width="15%" align="right" class="green"><spring:message code="system.role.jsmc" /><font color="#ff0000">*</font>：</td>
      <td><form:input path="roleName" /></td>
    </tr>
    <tr style="height: 20px;">
      <td width="15%" align="right" class="green"><spring:message code="system.role.jssm" /><font color="#ff0000">*</font>：</td>
      <td><form:textarea path="roleRemark" cols="49" rows="5" /></td>
    </tr>
  </table>
  <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0" id="resources" style="display: none">
    <tr>
      <td width="100%" style="height: 280px; margin-top: 10px;" colspan="2" align="center"><form:hidden
        path="resourceIds" id="resourceIds" /> <iframe style="border: opx solid #5d90d7;" name="menuFunctionFrame"
        src="${ctx}/tree/fun/checktree?checked=${role.resourceIds}" scrolling="auto" width="98%" height="100%"
        frameborder="0" align="middle"> </iframe></td>
    </tr>
  </table>
  <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0" id="authority" style="display: none">
    <thead>
      <tr>
        <th>权限名称</th>
        <th>权限说明</th>
        <th><input type="checkbox" name="select1" onclick="setAllCheckboxState('authorityIds',this.checked)" /></th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${authorityInfoList}" var="item" varStatus="status">
        <tr height="20" <c:if test="${status.count%2==0}">bgcolor="#f3f3f3"</c:if>>
          <td>${item.authorityName}</td>
          <td>${item.authorityRemark}</td>
          <td><form:checkbox path="authorityIds" value="${item.authorityId}" /></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  </div>
  </div>
  <div style="height: 2px;"></div>
  <div align="center"><input type="button" name="cancel2" class="btnbg4"
    value='<spring:message code="system.button.qd" />' onclick="saveRole()" /> <input type="button" name="cancel2"
    value='<spring:message code="system.button.qx"/>' onclick="closeWin()" class="btnbg4" /></div>
  </div>
  </div>
</form:form>
</body>
</html>