<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>台区档案录入</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<style type="text/css">
a {
    text-decoration: underline;
    color: #16528B;
}

a:active {
    text-decoration: underline;
    color: #FF0000;
}

a:hover {
    text-decoration: underline;
    color: #16528B;
}
</style>

<script type="text/javascript">
	//弹出式窗口
	var opwindow = null; //记录打开浏览窗口的对象
	function windowPopup(url, wd, ht) {
		if (opwindow != null) {
			opwindow.close();
		}
		opwindow = open(
				url,
				'',
				'height='
						+ ht
						+ ',width='
						+ wd
						+ ',top='
						+ (screen.availHeight - ht)
						/ 2
						+ ', left='
						+ (screen.availWidth - wd)
						/ 2
						+ ', toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');
	}

    

	var contextPath = '${ctx}';
</script>
</head>
<body>
<div class="electric_lcon" id="electric_Con" style="margin: 5px;">
<ul class=default id=electric_Con_1>
  <div class="tab"><span>台区信息</span></div>
  <div class="da_mid"
    style="display: block; overflow-y: auto; overflow-x: auto; width: expression((         document.documentElement.clientWidth ||         document.body.clientWidth) -10 ); height: expression(((                         document.documentElement.clientHeight ||                         document.body.clientHeight) -35 ) );">
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
        <td width="20%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:input path="tgNo" id="tgNo" cssClass="required input2" maxlength="16" disabled="true"
            cssStyle="width:145px;" />
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:input path="tgNo" id="tgNo" cssClass="required input2" maxlength="16" cssStyle="width:145px;" />
        </security:authorize></td>
        <td width="10%" align="right" class="green">台区名称：</td>
        <td width="20%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:input path="tgName" id="tgName" cssClass="required input2" cssStyle="width:145px;" disabled="true" />
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:input path="tgName" id="tgName" cssClass="required input2" cssStyle="width:145px;" />
        </security:authorize></td>
        <td width="10%" align="right" class="green">管理单位：</td>
        <td width="25%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:select path="orgInfo.orgId" items="${orglist}" id="orgId" itemLabel="orgName" disabled="true"
            itemValue="orgId" cssStyle="width:150px;" />
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:select path="orgInfo.orgId" items="${orglist}" id="orgId" itemLabel="orgName" itemValue="orgId"
            cssStyle="width:150px;" />
        </security:authorize></td>
      </tr>
      <tr height="30">
        <td width="15%" align="right" class="green">容 量：</td>
        <td width="20%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:input path="tgCap" id="tgCap" cssClass="validate-number input2" cssStyle="width:125px;" disabled="true" />
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:input path="tgCap" id="tgCap" cssClass="validate-number input2" cssStyle="width:125px;" />
        </security:authorize> kVA</td>
        <td width="10%" align="right" class="green">运行状态：</td>
        <td width="20%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:select path="runStatusCode" id="runStatusCode" itemLabel="name" itemValue="code" items="${statuslist}"
            cssStyle="width:145px;" disabled="true" />
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:select path="runStatusCode" id="runStatusCode" itemLabel="name" itemValue="code" items="${statuslist}"
            cssStyle="width:145px;" />
        </security:authorize></td>
        <td width="15%" align="right" class="green">地 址：</td>
        <td width="25%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:input path="instAddr" id="instAddr" cssStyle="width:150px;" disabled="true" />
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2,ROLE_AUTHORITY_1">
          <form:input path="instAddr" id="instAddr" cssStyle="width:150px;" />
        </security:authorize></td>
      </tr>
      <tr>
        <td width="100%" colspan="6" align="right"><security:authorize
          ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <input id="save" name="save" type="button" class="btnbg4" value="保存">
        </security:authorize></td>
      </tr>
    </table>
  </form:form></div>
  <div class="tr mgt10"><!-- <a onclick=""><img src='<pss:path type="bgcolor"/>/img/img2_bt.gif' width="15" height="15" /></a> --></div>
  <div class="mgt10">
  <div class="mgt10 da_top"><span>变压器信息</span> <security:authorize ifAnyGranted="ROLE_AUTHORITY_3">
    <h1><a onclick="openTransformer('${tginfo.tgId}')"><img src='<pss:path type="bgcolor"/>/img/bt_add.gif'
      width="16" height="16" style="cursor: pointer;" /></a></h1>
  </security:authorize></div>
  <div class="da_con">
  <table border="0" cellpadding="0" cellspacing="0" width="100%" id="tranInfo">
    <thead>
      <tr>
        <th width="14%">变压器名称</th>
        <th width="14%">容量(kVA)</th>
        <th width="14%">变压器型号</th>
        <th width="14%">额定高压</th>
        <th width="14%">额定低压</th>
        <th width="14%">安装地址</th>
        <th>操作</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${tginfo.tranInfos}" var="tran" varStatus="status">
        <tr id="tran_${tran.equipId}" <c:if test="${status.count%2==0}">bgcolor="#f3f3f3"</c:if>>
          <td>&nbsp;${tran.tranName}</td>
          <td>&nbsp;${tran.plateCap}</td>
          <td>&nbsp;<pss:code code="${tran.modelNo}" codeCate="<%=SystemConst.CODE_TRAN_CODE%>" /></td>
          <td>&nbsp;<pss:code code="${tran.rvHv}" codeCate="<%=SystemConst.CODE_VOLT_GRADE%>" /></td>
          <td>&nbsp;<pss:code code="${tran.rvLv}" codeCate="<%=SystemConst.CODE_VOLT_GRADE%>" /></td>
          <td>&nbsp;${tran.instAddr}</td>
          <td><security:authorize ifAnyGranted="ROLE_AUTHORITY_1">
            <a onclick="deleteTranInfo('${tran.equipId}')">删除</a>
          </security:authorize>&nbsp;<security:authorize ifAnyGranted="ROLE_AUTHORITY_2">/&nbsp;
          <a onclick="updateTranInfo('${tran.equipId}')">修改</a>
          </security:authorize> &nbsp;<security:authorize ifAnyGranted="ROLE_AUTHORITY_4">/&nbsp;<a
              onclick="showTranInfo('${tran.equipId}')">查看</a>
          </security:authorize></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  </div>
  <!-- 集中器信息 -->
  <div class="mgt10 da_top"><span>集中器信息</span> <security:authorize ifAnyGranted="ROLE_AUTHORITY_3">
    <h1><a onclick="openTerm('${tginfo.tgId}')"><img src='<pss:path type="bgcolor"/>/img/bt_add.gif' width="16"
      height="16" style="cursor: pointer;" /></a></h1>
  </security:authorize></div>
  <div class="da_con">
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th width="14%">资产编号</th>
        <th width="14%">逻辑地址</th>
        <th width="14%">终端类型</th>
        <th width="14%">通讯类型</th>
        <th width="14%">相线</th>
        <th width="14%">运行状态</th>
        <th>操作</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${termlist}" var="term" varStatus="status">
        <tr id="term_${term.termId}" <c:if test="${status.count%2==0}">bgcolor="#f3f3f3"</c:if>>
          <td>&nbsp;${term.assetNo}</td>
          <td>&nbsp;${term.logicalAddr}</td>
          <td>&nbsp;<pss:code code="${term.termType}" codeCate="<%=SystemConst.CODE_TERM_TYPE%>" /></td>
          <td>&nbsp;<pss:code code="${term.commMode}" codeCate="<%=SystemConst.CODE_COMM_MODE%>" /></td>
          <td>&nbsp;<pss:code code="${term.wiringMode}" codeCate="<%=SystemConst.CODE_WIRING_MODE %>" /></td>
          <td>&nbsp;<pss:code code="${term.runStatus}" codeCate="<%=SystemConst.CODE_RUN_STATUS %>" /></td>
          <td><security:authorize ifAnyGranted="ROLE_AUTHORITY_1">
            <a onclick="deleteTermInfo('${term.termId}')">删除</a>
          </security:authorize>&nbsp;<security:authorize ifAnyGranted="ROLE_AUTHORITY_2">/&nbsp;<a
              onclick="updateTermInfo('${term.termId}')">修改</a>
          </security:authorize> &nbsp;<security:authorize ifAnyGranted="ROLE_AUTHORITY_4">/&nbsp;<a
              onclick="showTermInfo('${term.termId}')">查看</a>
          </security:authorize></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  </div>
  
  <div class="mgt10 da_top"><span>台区考核表信息</span> <security:authorize
    ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_10">
    <h1><a onclick="openMeterInfo('${tginfo.tgId}')"><img src='<pss:path type="bgcolor"/>/img/bt_add.gif'
      width="16" height="16" /></a></h1>
  </security:authorize></div>
  <div class="da_con">
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th width="14%">计量点名称</th>
        <th width="14%">表地址</th>
        <th width="14%">测量点序号</th>
        <th width="14%">采集终端</th>
        <th width="14%">运行状态</th>
        <th width="14%">总倍率</th>
        <th>操作</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${tginfo.mpInfos}" var="mpInfo" varStatus="status">
        <tr id="mp_${mpInfo.mpId}" <c:if test="${status.count%2==0}">bgcolor="#f3f3f3"</c:if>>
          <td>&nbsp;${mpInfo.mpName}</td>
          <td>&nbsp;${mpInfo.gpInfos[0].gpAddr}</td>
          <td>&nbsp;${mpInfo.gpInfos[0].gpSn}</td>
          <td>&nbsp;${mpInfo.gpInfos[0].terminalInfo.logicalAddr}</td>
          <td>&nbsp;<pss:code code="${mpInfo.statusCode}" codeCate="<%=SystemConst.CODE_METER_STATUS %>" /></td>
          <td>&nbsp;${mpInfo.meterInfo.tFactor}</td>
          <td><security:authorize ifAnyGranted="ROLE_AUTHORITY_1">
            <a onclick="deleteMpInfo('${mpInfo.mpId}')">删除</a>
          </security:authorize>&nbsp;<security:authorize ifAnyGranted="ROLE_AUTHORITY_2,ROLE_AUTHORITY_10">/&nbsp;<a
              onclick="updateMpInfo('${mpInfo.mpId}')">修改</a>
          </security:authorize> &nbsp;<security:authorize ifAnyGranted="ROLE_AUTHORITY_4">/&nbsp;<a
              onclick="showMpInfo('${mpInfo.mpId}')">查看</a>
          </security:authorize></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  </div>
  <div class="mgt10 da_top"><span>保护开关信息</span> <security:authorize
    ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_12">
    <h1><a onclick="openPsInfo('${tginfo.tgId}')"><img src='<pss:path type="bgcolor"/>/img/bt_add.gif'
      width="16" height="16" /></a></h1>
  </security:authorize></div>
  <div class="da_con">
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th width="14%">资产编号</th>
        <th width="14%">漏保地址</th>
        <th width="14%">测量点序号</th>
        <th width="14%">漏保型号</th>
        <th width="14%">额定电流</th>
        <th width="14%">计划试跳</th>
        <th>操作</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${pslist}" var="ps" varStatus="status">
        <tr id="ps_${ps.psId}" <c:if test="${status.count%2==0}">bgcolor="#f3f3f3"</c:if>>
          <td>&nbsp;${ps.assetNo}</td>
          <td>&nbsp;${ps.gpInfo.gpAddr}</td>
          <td>&nbsp;${ps.gpInfo.gpSn}</td>
          <td>&nbsp;<pss:code code="${ps.modelCode}" codeCate="<%=SystemConst.CODE_PS_MODEL%>" /></td>
          <td>&nbsp;<pss:code code="${ps.ratedEc}" codeCate="<%=SystemConst.CODE_RATED_EC%>" /></td>
          <td><c:choose>
            <c:when test="${ps.autoTest==1}">
          ${ps.testDay}日${ps.testTime}时
          </c:when>
            <c:otherwise>
                          无
          </c:otherwise>
          </c:choose></td>
          <td><security:authorize ifAnyGranted="ROLE_AUTHORITY_1,ROLE_AUTHORITY_12">
            <a onclick="deletePsInfo('${ps.psId}')">删除</a>
          </security:authorize>&nbsp;<security:authorize ifAnyGranted="ROLE_AUTHORITY_2,ROLE_AUTHORITY_11,ROLE_AUTHORITY_12">/&nbsp;<a
              onclick="updatePsInfo('${ps.psId}')">修改</a>
          </security:authorize> &nbsp;<security:authorize ifAnyGranted="ROLE_AUTHORITY_4">/&nbsp;<a
              onclick="showPsInfo('${ps.psId}')">查看</a>
          </security:authorize></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  </div>
    <!-- 操作员信息 -->
  <div class="mgt10 da_top"><span>操作员信息</span> <security:authorize ifAnyGranted="ROLE_AUTHORITY_3">
    <h1><a onclick="openUser('${tginfo.tgId}')"><img src='<pss:path type="bgcolor"/>/img/bt_add.gif' width="16"
      height="16" style="cursor: pointer;" /></a></h1>
  </security:authorize></div>
  <div class="da_con">
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <thead>
      <tr>
        <th width="30%">姓名</th>
        <th width="30%">电话号码</th>
        <th>操作</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach items="${tginfo.userInfoList}" var="user" varStatus="status">
        <tr id="user_${user.empNo}" <c:if test="${status.count%2==0}">bgcolor="#f3f3f3"</c:if>>
          <td>&nbsp;${user.name}</td>
          <td>&nbsp;${user.mobile}</td>
          <td><security:authorize ifAnyGranted="ROLE_AUTHORITY_1">
            <a onclick="deleteUserInfo('${user.empNo}')">删除</a>
          </security:authorize> &nbsp;</td>
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
	  });

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
    });
	    
});


getData= function(type){
var data;
if(type == "add"){
  data = jQuery("form[id=tginfo]").not("#tgId").serialize(); 
}else {
	data = jQuery("form[id=tginfo]").serialize(); 
}
return data;
};
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
};

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
                  alert("error");
	             alert(e.getMessage());
	         }
	       });
	  }
};
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
};

updateTranInfo=function(tranId){
	   var url = "${ctx}/archive/tranInfo/"+tranId+"/edit";
	   windowPopup(url, 960, 575);
};

showTranInfo=function(tranId){
     var url = "${ctx}/archive/tranInfo/"+tranId;
     windowPopup(url, 960, 575);
};

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
  };

updatePsInfo=function(psId){
     var url = "${ctx}/archive/psinfo/"+psId+"/edit?gpInfo.objectId="+$("#tgId").val();
     windowPopup(url, 960, 575);
};
showPsInfo=function(psId){
    var url = "${ctx}/archive/psinfo/"+psId+"?gpInfo.objectId="+$("#tgId").val();
    windowPopup(url, 960, 575);
};
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
};

updateMpInfo=function(mpId){
     var url = "${ctx}/archive/mpinfo/"+mpId+"/edit?tgInfo.tgId="+$("#tgId").val();
     windowPopup(url, 960, 575);
};

showMpInfo=function(mpId){
    var url = "${ctx}/archive/mpinfo/"+mpId+"?tgInfo.tgId="+$("#tgId").val();
    windowPopup(url, 960, 575);
};
/*******************************************************************/
  //打开新增终端页面
 function openTerm(tgId){
    
    if(!$("#tgId").val()){
      alert("请先建台区");return;
    }
  var url = contextPath + "/archive/terminalinfo/new?tgId=" +$("#tgId").val();
  windowPopup(url, 960, 575);
} ;
 
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
  };

updateTermInfo=function(termId){
     var url = "${ctx}/archive/terminalinfo/"+termId+"/edit?tgId="+$("#tgId").val();
     windowPopup(url, 960, 575);
};

showTermInfo=function(termId){
    var url = "${ctx}/archive/terminalinfo/"+termId+"?tgId="+$("#tgId").val();
    windowPopup(url, 960, 575);
};
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
  };

updateTgOpInfo=function(userId){
     var url = "${ctx}/archive/tgopinfo/"+userId+"/edit?tgId="+$("#tgId").val();
     windowPopup(url, 960, 575);
};
/*******************************************************************/

</script>
</html>