<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>台区档案录入</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<script type="text/javascript">
//弹出式窗口
var opwindow = null;     //记录打开浏览窗口的对象
function windowPopup(url, wd, ht) {
    /*if(opwindow != null) {
        opwindow.close();
    }*/
    opwindow = open(url,'','height='+ht+',width='+wd+',top='+(screen.availHeight-ht)/2+', left='+(screen.availWidth-wd)/2+', toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');
}

var contextPath  = '${ctx}';

</script>
</head>
<body>
<div class="electric_lcon" id="electric_Con" style="margin: 5px;">
<ul class=default id=electric_Con_1>
  <div class="tab"><span>台区信息</span></div>
  <div class="da_mid"
    style="display: block; overflow-y: auto; overflow-x: auto; width: expression((   document.documentElement.clientWidth ||               document.body.clientWidth) -10 ); height: expression(((               document.documentElement.clientHeight ||               document.body.clientHeight) -35 ) );">
  <div><form:form action="/archive/tginfo" modelAttribute="tginfo">
    <table width="95%" border="0" cellspacing="0" cellpadding="0">
      <tr height="30">
        <input type="hidden" id="_type" name="_type" value="${_type}"></input>
        <c:choose>
          <c:when test="${_type=='edit' || _type=='new'}">
            <c:set var="disabled" value="false"></c:set>
          </c:when>
          <c:otherwise>
            <c:set var="disabled" value="true"></c:set>
          </c:otherwise>
        </c:choose>
        <td width="15%" align="right" class="green"><font color="red"><form:hidden path="tgId" />* </font>台区编号：</td>
        <td width="20%"><form:input path="tgNo" id="tgNo" cssClass="required input2" maxlength="16"
          disabled="${disabled}" cssStyle="width:145px;" /></td>
        <td width="10%" align="right" class="green">台区名称：</td>
        <td width="20%"><form:input path="tgName" id="tgName" cssClass="required input2" cssStyle="width:145px;"
          disabled="${disabled}" /></td>
        <td width="10%" align="right" class="green">管理单位：</td>
        <td width="25%"><form:select path="orgInfo.orgId" items="${orglist}" disabled="${disabled}" id="orgId"
          itemLabel="orgName" itemValue="orgId" cssStyle="width:150px;" /></td>
      </tr>
      <tr height="30">
        <td width="15%" align="right" class="green">容 量：</td>
        <td width="20%"><form:input path="tgCap" id="tgCap" cssClass="validate-number input2"
          cssStyle="width:125px;" disabled="${disabled}" /> kVA</td>
        <td width="10%" align="right" class="green">运行状态：</td>
        <td width="20%"><form:select path="runStatusCode" id="runStatusCode" itemLabel="name" itemValue="code"
          items="${statuslist}" cssStyle="width:145px;" disabled="${disabled}" /></td>
        <td width="15%" align="right" class="green">地 址：</td>
        <td width="25%"><form:input path="instAddr" id="instAddr" cssStyle="width:150px;" disabled="${disabled}" /></td>
      </tr>
      <tr>
        <td width="100%" colspan="6" align="right"><input id="save" name="save" type="button" class="btnbg4"
          value="保存" <c:if test="${disabled == 'true'}">disabled</c:if>></td>
      </tr>
    </table>
  </form:form></div>
  <div class="tr mgt10"><!-- <a onclick=""><img src='<pss:path type="bgcolor"/>/img/img2_bt.gif' width="15" height="15" /></a> --></div>
  <div class="mgt10">
  <div class="mgt10 da_top"><span>变压器信息</span>
  <h1><a onclick="openTransformer('${tginfo.tgId}')"><img src='<pss:path type="bgcolor"/>/img/bt_add.gif'
    width="16" height="16" style="cursor: pointer;" /></a></h1>
  </div>
  <div class="da_con">
  <table border="0" cellpadding="0" cellspacing="0" width="100%" id="tranInfo">
    <thead>
      <tr>
        <th>名称</th>
        <th>容量(kVA)</th>
        <th>型号</th>
        <th>安装地址</th>
        <th>操作</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${tginfo.tranInfos}" var="tran" varStatus="status">
        <tr id="tran_${tran.equipId}" <c:if test="${status.count%2==0}">bgcolor="#f3f3f3"</c:if>>
          <td>&nbsp;${tran.tranName}</td>
          <td>&nbsp;${tran.plateCap}</td>
          <td>&nbsp;<pss:code code="${tran.modelNo}" codeCate="TRAN_CODE" /></td>
          <td>&nbsp;${tran.instAddr}</td>
          <td><a onclick="deleteTranInfo('${tran.equipId}')">删除</a>&nbsp;/&nbsp;<a
            onclick="updateTranInfo('${tran.equipId}')">修改</a></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  </div>
  <div class="mgt10 da_top"><span>台区考核表信息</span>
  <h1><a onclick="openMeterInfo('${tginfo.tgId}')"><img src='<pss:path type="bgcolor"/>/img/bt_add.gif'
    width="16" height="16" /></a></h1>
  </div>
  <div class="da_con">
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th>计量点名称</th>
        <th>表地址</th>
        <th>采集终端</th>
        <th>运行状态</th>
        <th>操作</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${tginfo.mpInfos}" var="mpInfo" varStatus="status">
        <tr id="mp_${mpInfo.mpId}" <c:if test="${status.count%2==0}">bgcolor="#f3f3f3"</c:if>>
          <td>&nbsp;${mpInfo.mpName}</td>
          <td>&nbsp;${mpInfo.gpInfos[0].gpAddr}</td>
          <td>&nbsp;${mpInfo.gpInfos[0].terminalInfo.logicalAddr}</td>
          <td>&nbsp;<pss:code code="${mpInfo.statusCode}" codeCate="<%=SystemConst.CODE_METER_STATUS %>" /></td>
          <td><a onclick="deleteMpInfo('${mpInfo.mpId}')">删除</a>&nbsp;/&nbsp;<a
            onclick="updateMpInfo('${mpInfo.mpId}')">修改</a></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  </div>
  <div class="mgt10 da_top"><span>保护开关信息</span>
  <h1><a onclick="openPsInfo('${tginfo.tgId}')"><img src='<pss:path type="bgcolor"/>/img/bt_add.gif' width="16"
    height="16" /></a></h1>
  </div>
  <div class="da_con">
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th>资产编号</th>
        <th>漏保地址</th>
        <th>计划试跳</th>
        <th>操作</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${pslist}" var="ps" varStatus="status">
        <tr id="ps_${ps.psId}" <c:if test="${status.count%2==0}">bgcolor="#f3f3f3"</c:if>>
          <td>&nbsp;${ps.assetNo}</td>
          <td>&nbsp;${ps.gpInfo.gpAddr}</td>
          <td><c:choose>
            <c:when test="${ps.autoTest==1}">
          ${ps.testDay}日${ps.testTime}时
          </c:when>
            <c:otherwise>
                          无
          </c:otherwise>
          </c:choose></td>
          <td><a onclick="deletePsInfo('${ps.psId}')">删除</a>&nbsp;/&nbsp;<a onclick="updatePsInfo('${ps.psId}')">修改</a></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  </div>
  <!-- 集中器信息 -->
  <div class="mgt10 da_top"><span>集中器信息</span>
  <h1><a onclick="openTerm('${tginfo.tgId}')"><img src='<pss:path type="bgcolor"/>/img/bt_add.gif' width="16"
    height="16" style="cursor: pointer;" /></a></h1>
  </div>
  <div class="da_con">
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th>资产号</th>
        <th>逻辑地址</th>
        <th>终端类型</th>
        <th>相线</th>
        <th>运行状态</th>
        <th>操作</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${termlist}" var="term" varStatus="status">
        <tr id="term_${term.termId}" <c:if test="${status.count%2==0}">bgcolor="#f3f3f3"</c:if>>
          <td>&nbsp;${term.assetNo}</td>
          <td>&nbsp;${term.logicalAddr}</td>
          <td>&nbsp;<pss:code code="${term.termType}" codeCate="<%=SystemConst.CODE_TERM_TYPE %>" /></td>
          <td>&nbsp;<pss:code code="${term.wiringMode}" codeCate="<%=SystemConst.CODE_WIRING_MODE %>" /></td>
          <td>&nbsp;<pss:code code="${term.runStatus}" codeCate="<%=SystemConst.CODE_RUN_STATUS %>" /></td>
          <td><a onclick="deleteTermInfo('${term.termId}')">删除</a>&nbsp;/&nbsp;<a
            onclick="updateTermInfo('${term.termId}')">修改</a></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  </div>
  <!-- 操作员信息 -->
  <div class="mgt10 da_top"><span>操作员信息</span>
  <h1><a onclick="openUser('${tginfo.tgId}')"><img src='<pss:path type="bgcolor"/>/img/bt_add.gif' width="16"
    height="16" style="cursor: pointer;" /></a></h1>
  </div>
  <div class="da_con">
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th>姓名</th>
        <th>电话号码</th>
        <th>操作</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${tginfo.userInfoList}" var="user" varStatus="status">
        <tr id="user_${user.empNo}" <c:if test="${status.count%2==0}">bgcolor="#f3f3f3"</c:if>>
          <td>&nbsp;${user.name}</td>
          <td>&nbsp;${user.mobile}</td>
          <td><a onclick="deleteUserInfo('${user.empNo}')">删除</a></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  </div>
  </div>
  </div>
</ul>
</div>
</body>
<script>
val =  new Validation(document.forms[0],{onSubmit:true,onFormValidate : function(result,form) {
 return result;
}}
);


jQuery(function(){
	jQuery("#addTg").click(function(){
	if(val.validate()){
      jQuery(this).attr("disabled","disabled");
      addtginfo();
      jQuery(this).attr("disabled","");
	}else{
		jQuery(this).attr("disabled","");
	}
  });


	jQuery("#updateTg").click(function(){
		if(val.validate()){
	      jQuery(this).attr("disabled","disabled");
	      updatetginfo();
	      jQuery(this).attr("disabled","");
		}else{
			jQuery(this).attr("disabled","");
		}
	  })

  jQuery("#save").click(function(){
    if(val.validate()){
        jQuery(this).attr("disabled","disabled");

        if($("#_type").val()=="edit"){
        	updatetginfo();
        }else if($("#_type").val()=="new"){
        	addtginfo();
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
  data = jQuery("form[id=tginfo]").not("#tgId").serialize(); 
}else {
	data = jQuery("form[id=tginfo]").serialize(); 
}
return data;
}
/*******************************************************************/
addtginfo = function(){
  var tgFromData = getData('add');
  var url="${ctx}/archive/tginfo.json";
  if(confirm("确定要保存该台区?")){
    $.ajax({
         url: url,
         data:tgFromData,
         dataType:'json',
         type:'POST',
         cache: false,
         success: function(json){
    	var msg = json['<%=SystemConst.CONTROLLER_AJAX_MESSAGE%>'];
        var isSucc = json['<%=SystemConst.CONTROLLER_AJAX_IS_SUCC%>'];
        alert(msg);
           jQuery("#tgId").val(json['tgId']);
           parent.parent.tabscontainerleft.tree.location.href = "${ctx}/tree";
         },error:function(e){
             alert("error");
             alert(e.message);
         }
       });
  }
}

updatetginfo = function(){
	var tgFromData = getData("update");
	  var url="${ctx}/archive/tginfo/"+jQuery("#tgId").val()+'.json?_method=put';
	  if(confirm("确定要更新该台区?")){
	    $.ajax({
	         url: url,
	         data:tgFromData,
	         dataType:'json',
	         type:'post',
	         cache: false,
	         success: function(json){
	    	 var msg = json['<%=SystemConst.CONTROLLER_AJAX_MESSAGE%>'];
	          var isSucc = json['<%=SystemConst.CONTROLLER_AJAX_IS_SUCC%>'];
	            alert(msg);
	            parent.parent.tabscontainerleft.tree.location.href = "${ctx}/tree";
	         },error:function(e){
                  alert("error")
	             alert(e.getMessage());
	         }
	       });
	  }
}
/*******************************************************************/

//打开新增变压器页面
function openTransformer(){
  
  if(!$("#tgId").val()){
    alert("请先建台区");return;
  }
   var url = "${ctx}/archive/tranInfo/new?tgInfo.tgId="+$("#tgId").val()+"&orgInfo.orgId="+$("#orgId").val();
   windowPopup(url, 960, 575);
}

deleteTranInfo=function(tranId){
 if(tranId==null || tranId ==""){
   return;
 } 

 var url = "${ctx}/archive/tranInfo/"+tranId+".json?_method=delete";
 if (confirm("确定要删除该变压器?")) {
     $.ajax({
         url: url,
         dataType:'json',
         type:'POST',
         cache: false,
         success: function(json) {
    	   var msg = json['<%=SystemConst.CONTROLLER_AJAX_MESSAGE%>'];
           var isSucc = json['<%=SystemConst.CONTROLLER_AJAX_IS_SUCC%>'];
             if(isSucc){
              alert(msg);
              $("#tran_"+tranId).remove();
             }
         },error:function(e) {
             alert("delete error");
             alert(e.message);
         }
     });
 }
}

updateTranInfo=function(tranId){
	   var url = "${ctx}/archive/tranInfo/"+tranId+"/edit";
	   windowPopup(url, 960, 575);
}

/*******************************************************************/
  //打开漏保
 function openPsInfo(tgId){
	 
	  if(!$("#tgId").val()){
	    alert("请先建台区");return;
	  }
     var url = contextPath + "/archive/psinfo/new?gpInfo.objectId="+ $("#tgId").val();
     windowPopup(url, 960, 575);
  } 
 
deletePsInfo=function(psId){
   if(psId==null || psId ==""){
     return;
   } 

   var url = "${ctx}/archive/psinfo/"+psId+".json?_method=delete";
   if (confirm("确定要删除该保护器?")) {
       $.ajax({
           url: url,
           dataType:'json',
           type:'POST',
           cache: false,
           success: function(json) {
    	   var msg = json['<%=SystemConst.CONTROLLER_AJAX_MESSAGE%>'];
           var isSucc = json['<%=SystemConst.CONTROLLER_AJAX_IS_SUCC%>'];
               alert(msg);
               if(isSucc){
                $("#ps_"+psId).remove();
               }
           },error:function(e) {
               alert("delete error");
               alert(e.message);
           }
       });
   }
  }

updatePsInfo=function(psId){
     var url = "${ctx}/archive/psinfo/"+psId+"/edit?gpInfo.objectId="+$("#tgId").val();
     windowPopup(url, 960, 575);
}

/*******************************************************************/
 
function openMeterInfo(tgId){

	  
	  if(!$("#tgId").val()){
	    alert("请先建台区");return;
	  }
	   var url = "${ctx}/archive/mpinfo/new?tgInfo.tgId="+$("#tgId").val();
	   windowPopup(url, 960, 575);
     
}

deleteMpInfo=function(mpId){
     if(mpId==null || mpId ==""){
       return;
     } 

     var url = "${ctx}/archive/mpinfo/"+mpId+".json?_method=delete";
     if (confirm("确定要删除该电表?")) {
         $.ajax({
             url: url,
             dataType:'json',
             type:'POST',
             cache: false,
             success: function(json) {
        	   var msg = json['<%=SystemConst.CONTROLLER_AJAX_MESSAGE%>'];
               var isSucc = json['<%=SystemConst.CONTROLLER_AJAX_IS_SUCC%>'];
                 alert(msg);
                 if(isSucc){
                  $("#mp_"+mpId).remove();
                 }
             },error:function(e) {
                 alert("delete error");
                 alert(e.message);
             }
         });
     }
    }

  updateMpInfo=function(mpId){
       var url = "${ctx}/archive/mpinfo/"+mpId+"/edit?tgInfo.tgId="+$("#tgId").val();
       windowPopup(url, 960, 575);
  }
/*******************************************************************/
  //打开新增终端页面
 function openTerm(tgId){
    
    if(!$("#tgId").val()){
      alert("请先建台区");return;
    }
  var url = contextPath + "/archive/terminalinfo/new?tgId=" +$("#tgId").val();
  windowPopup(url, 960, 575);
} 
 
deleteTermInfo=function(termId){
   if(termId==null || termId ==""){
     return;
   } 

   var url = "${ctx}/archive/terminalinfo/"+termId+".json?_method=delete";
   if (confirm("确定要删除该集中器?")) {
       $.ajax({
           url: url,
           dataType:'json',
           type:'POST',
           cache: false,
           success: function(json) {
               var msg = json['<%=SystemConst.CONTROLLER_AJAX_MESSAGE%>'];
               var isSucc = json['<%=SystemConst.CONTROLLER_AJAX_IS_SUCC%>'];
               alert(msg);
               if(isSucc){
                $("#term_"+termId).remove();
               }
           },error:function(e) {
               alert("delete error");
               alert(e.message);
           }
       });
   }
  }

updateTermInfo=function(termId){
     var url = "${ctx}/archive/terminalinfo/"+termId+"/edit?tgId="+$("#tgId").val();
     windowPopup(url, 960, 575);
}
/*******************************************************************/
  //打开新增台区操作员页面
 function openUser(tgId){
    
    if(!$("#tgId").val()){
      alert("请先建台区");return;
    }
  var url = contextPath + "/archive/tgopinfo?tgId="+$("#tgId").val()
  windowPopup(url, 960, 575);
} 
 
 deleteUserInfo=function(userId){
   if(userId==null || userId ==""){
     return;
   } 
   var url = "${ctx}/archive/tgopinfo/"+userId+".json?_method=delete";
   if (confirm("确定要删除该操作员?")) {
       $.ajax({
           url: url,
           dataType:'json',
           type:'POST',
           cache: false,
           data:"tgId="+$("#tgId").val(),
           success: function(json) {
               var msg = json['<%=SystemConst.CONTROLLER_AJAX_MESSAGE%>'];
               var isSucc = json['<%=SystemConst.CONTROLLER_AJAX_IS_SUCC%>'];
               alert(msg);
               if(isSucc){
                $("#user_"+userId).remove();
               }
           },error:function(e) {
               alert("delete error");
           }
       });
   }
  }

updateTgOpInfo=function(userId){
     var url = "${ctx}/archive/tgopinfo/"+userId+"/edit?tgId="+$("#tgId").val();
     windowPopup(url, 960, 575);
}
/*******************************************************************/

</script>
</html>