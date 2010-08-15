<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript">


var contextPath = "${ctx}";
var sSelectedUserID = "";


function changeOrg() {
    $("#orgId").attr("disabled", false);
    document.forms[0].submit();
}
//列表变更
function changeList() {
    $("#orgId").attr("disabled", false);
    document.forms[0].submit();
}

getData= function(){
  var data;

    data = $("form[0]").serialize(); 
  
  return data;
  }

//添加账号
save = function(){
       if(!hasOneChecked('checkedUser')){
            alert("请选择要添加的用户")
       return;
     } 

     var url = "${ctx}/archive/tgopinfo/${tgUserQuery.tgId}.json?_method=put";
     var data1 = getData()
     if (confirm("确定要添加所选择的用户?")) {
         $.ajax({
             url: url,
             dataType:'json',
             type:'POST',
             cache: false,
             data:data1,
             success: function(json) {
               var msg = json['<%=SystemConst.CONTROLLER_AJAX_MESSAGE%>'];
               var isSucc = json['<%=SystemConst.CONTROLLER_AJAX_IS_SUCC%>'];
                 if(isSucc){
                  opener.location.href ="${ctx}/archive/tginfo/${tgUserQuery.tgId}/edit";
                  closeWin();
                 }
             },error:function(e) {
                 alert("delete error");
             }
         });
     }
}

function   closeWin() 
{ 
        window.close(); 
} 
</script>
</head>
<body>
<form:form name="/archive/tgopinfo" modelAttribute="tgUserQuery" method="get">
<div class="electric_lcon" id="electric_Con">
<ul class=default id=electric_Con_1>
  <div>
  <form:hidden path="tgId"/>
    <table border="0" cellpadding="0" cellspacing="0">
      <tr height="30px">
        <td width="100" class="green" align="right">单位：</td>
        <td width="120"><select name="orgId" id="orgId" style="width: 150px;" onchange="changeOrg()">
          <c:forEach var="item" items="${orgInfo}">
            <option <c:if test="${item.orgId eq pageRequest.orgId}">selected</c:if>
              value="<c:out value="${item.orgId}"/>"><c:out value="${item.orgName}" /></option>
          </c:forEach>
        </select></td>
        <td colspan="2"><form:checkbox path="showAllAccount" onclick="changeList()" />显示全部
        <td><input class="btnbg4" type="button" name="ok" id="ok" value="确定" onclick="save()" /></td>
      </tr>
    </table>
  </div>
  <div id="bg" style="height: 30px; text-align: center;">
  <ul id=”datamenu_Option“ class="cb font1">
    <li class="curr" id=datamenu_Option_0 style="cursor: pointer;">客户一览</li>
  </ul>
  </div>
  <div style="height: expression(((   document.documentElement.clientHeight ||   document.body.clientHeight) -105 ) );">
  <table width="100%" border="0" cellspacing="0" class="gridBody" id="object_table">
    <thead class="tableHeader">
      <tr>
        <th style="width: 5px;"></th>
        <th style="width: 5px;" align="center"><input type="checkbox"
          onclick="setAllCheckboxState('checkedUser',this.checked)"></th>
        <th sortColumn="name">姓名</th>
        <th sortColumn="mobile">联系号码</th>
      </tr>
    </thead>
    <tbody class="tableBody" id="dataBody">
      <c:forEach items="${page.result}" var="item" varStatus="status">
        <tr class="${status.count % 2 == 0 ? 'odd' : 'even'}">
          <td>${page.thisPageFirstElementNumber + status.index}</td>
          <td><input type="checkbox" name="checkedUser" value="<c:out value="${item.empNo}"/>"></td>
          <td><c:out value='${item.name}' />&nbsp;</td>
          <td><c:out value='${item.mobile}' />&nbsp;</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  <simpletable:pageToolbar page="${page}"></simpletable:pageToolbar></div>
</ul>
</div>
</form:form>
</body>
<script type="text/javascript">
</script>
<script type="text/javascript">
		$(document).ready(function() {
			// 分页需要依赖的初始化动作
			window.simpleTable = new SimpleTable('pageRequest','${page.thisPageNumber}','${page.pageSize}','${pageRequest.sortColumns}');
		});
	</script>
</html>
