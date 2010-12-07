<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><spring:message code="system.permission.manage.title" /></title>
<title>userList</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript">
var contextPath = "${ctx}";
var sSelectedUserID = "";
function selectRow(sUserID, oRow) {
    sSelectedUserID = sUserID;

    if(!sSelectedUserID || "" == sSelectedUserID){
    	return;
    }
    var url = contextPath + '/system/user/' + sSelectedUserID + '?random=' + Math.random();
    parent.document.frames["userManager"].location.href = url;
    selectSingleRow(oRow);
}

//列表变更
function changeList() {
    $("#orgId").attr("disabled", false);
    document.forms[0].submit();
}

function changeOrg() {
    $("#orgId").attr("disabled", false);
    document.forms[0].submit();
}


$(function(){
	 var firstId = $("#dataBody>tr:first").attr("id");
	 if(firstId != 'undefined'){
	   selectRow(firstId,firstId);
	 }
})


var contextPath = "${ctx}";
var errorUser = '<spring:message code="system.user.errorUser" />';
var errorDelUser = '<spring:message code="system.user.errorDelUser" />';
var confirmDel = '<spring:message code="system.user.confirmDel" />';

//新增操作员
function newUser() {
    var str_url = contextPath + "/system/user/new";
    windowPopup(str_url, 800, 495);
}

//编辑操作员
function editUser() {
    var userId = sSelectedUserID;
    if(userId == "") {
        alert(errorUser);
        return;
    }
    var str_url = contextPath + "/system/user/" + userId + "/edit";
    windowPopup(str_url, 800, 495);
}

//查看操作员
function viewUser() {
    var userId = sSelectedUserID;
    if(userId == "") {
        alert(errorUser);
        return;
    }
    var str_url = contextPath + "/system/user/" + userId ;
    windowPopup(str_url, 800, 495);
}

//删除账号
deleteUser = function(){
	     if(sSelectedUserID==null || sSelectedUserID ==""){
            alert("请选择要删除的账号")
		   return;
		 } 

		 var url = "${ctx}/system/user/"+sSelectedUserID+".json?_method=delete";
		 if (confirm("确定要删除该账号?")) {
		     $.ajax({
		         url: url,
		         dataType:'json',
		         type:'POST',
		         cache: false,
		         success: function(json) {
		    	   var msg = json['<%=SystemConst.CONTROLLER_AJAX_MESSAGE%>'];
		           var isSucc = json['<%=SystemConst.CONTROLLER_AJAX_IS_SUCC%>'];
		             if(isSucc){
		              $("#"+sSelectedUserID).remove();
		             }else{
		             }

		             alert(msg);
		         },error:function(e) {
		             alert("delete error");
		             alert(e.message);
		         }
		     });
		 }
}
</script>
</head>
<body>
<form:form name="system/user/list" modelAttribute="userInfo" method="get">
	<div id="tool">
	<table border="0" cellpadding="0" cellspacing="0">
		<tr height="25">
			<td><spring:message code="system.user.ssdw" />：</td>
			<td>
        <form:select path="orgInfo.orgId" items="${orglist}" id="orgId" itemLabel="orgName" itemValue="orgId"
          onchange="changeOrg()"  cssStyle="width:150px;" />
         
         <script type="text/javascript">
         document.getElementById('orgId').value='${userInfo.orgInfo.orgId}';
         </script>
			<td colspan="2">
      
      <security:authentication property="principal" var="loginUser"/>
      <c:set value="${loginUser.roleInfoList}" var="roles"></c:set>
       <c:forEach items="${roles}" var = "role">
       <c:if test="${role.roleId==0}">
      <form:checkbox path="showAllAccount" onclick="changeList()"/>显示全部</c:if>
      </c:forEach>
			</td>
		</tr>
		<tr height="25">
			<td colspan="4">
       <security:authorize ifAnyGranted="ROLE_AUTHORITY_1">
      <input type="button" id="new" class="btnbg4" value=<spring:message code="system.button.xz" />
				onclick="newUser()" /></security:authorize> <security:authorize ifAnyGranted="ROLE_AUTHORITY_2"> <input type="button" id="edit" class="btnbg4"
				value=<spring:message code="system.button.bj" /> onclick="editUser()" /></security:authorize> <security:authorize ifAnyGranted="ROLE_AUTHORITY_3"> <input type="button" id="delete"
				class="btnbg4" value=<spring:message code="system.button.sc" /> onclick="deleteUser()" /></security:authorize></td>
		</tr>
	</table>
	</div>
	<div class="content">
	<div id="cont_1">
	<div class="tableContainer"
		style="height: expression((( document.documentElement.clientHeight ||document.body.clientHeight) -50 ) )">
	<table width="100%" border="0" cellspacing="0" class="gridBody" id="object_table">
		<thead class="tableHeader">
			<tr style="height: 20px;">
				<th ><spring:message code="system.user.xh" /></th>
				<th ><spring:message code="system.user.zh" /></th>
				<th ><spring:message code="system.user.mc" /></th>
			</tr>
		</thead>
		<tbody id="dataBody">
			<c:forEach items="${page.result}" var="item" varStatus="status">
				<tr style="height: 20px;" id="${item.empNo}" class="${status.count % 2 == 0 ? 'odd' : 'even'}" onclick="selectRow('${item.empNo}', this)"
					style="cursor: pointer;">
					<td>${page.thisPageFirstElementNumber + status.index}</td>
					<td>${item.staffNo}&nbsp;</td>
					<td>${item.name}&nbsp;</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<simpletable:pageToolbarNoRight page="${page}"></simpletable:pageToolbarNoRight></div>
	</div>
	</div>
</form:form>
</body>
<script type="text/javascript">
		$(document).ready(function() {
			// 分页需要依赖的初始化动作
			window.simpleTable = new SimpleTable('userInfo','${page.thisPageNumber}','${page.pageSize}','${pageRequest.sortColumns}');
		});
	</script>
</html>
