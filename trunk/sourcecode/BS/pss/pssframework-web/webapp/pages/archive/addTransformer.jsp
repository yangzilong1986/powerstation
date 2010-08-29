<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>变压器</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
</head>
<body>
<div class="electric_lcon" id=electric_Con>
<ul class="default" id="electric_Con_1" style="padding: 5px;">
  <div class="tab"><span>变压器信息</span></div>
  <div class="da_mid"
    style="display: block; overflow-y: auto; overflow-x: auto; width: expression((         document.documentElement.clientWidth ||                     document.body.clientWidth) -10 ); height: expression(((                     document.documentElement.clientHeight ||                     document.body.clientHeight) -35 ) );">
  <form:form action="/archive/tranInfo" modelAttribute="tranInfo">
    <input type="hidden" id="<%=SystemConst.CONTROLLER_METHOD_TYPE%>" name="<%=SystemConst.CONTROLLER_METHOD_TYPE%>"
      value="${_type}" />
    <form:hidden path="tgInfo.tgId" />
    <form:hidden path="orgInfo.orgId" />
    <table border="0" cellpadding="0" cellspacing="0" align="center">
      <tr height="40">
        <td width="15%" align="right" class="green"><font color="red">* </font>变压器名称：</td>
        <td width="15%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:input path="tranName" cssClass="required input2" maxlength="16" disabled="true" />
        </security:authorize> <security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:input path="tranName" cssClass="required input2" maxlength="16" />
        </security:authorize></td>
        <td width="15%" align="right" class="green">变压器型号：</td>
        <td width="15%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:select path="modelNo" id="modelNo" itemLabel="name" itemValue="code" onchange="" items="${typelist}"
            cssStyle="width:150px" disabled="true" />
        </security:authorize> <security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:select path="modelNo" id="modelNo" itemLabel="name" itemValue="code" onchange="" items="${typelist}"
            cssStyle="width:150px" />
        </security:authorize></td>
      </tr>
      <tr height="40">
        <td width="13%" align="right" class="green">容 量：</td>
        <td width="20%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:input path="plateCap" cssClass="input2" cssStyle="width:130px" disabled="true" />
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:input path="plateCap" cssClass="input2" cssStyle="width:130px" />
        </security:authorize> kVA</td>
        <td align="right" class="green">运行状态：</td>
        <td><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:select path="runStatusCode" id="runStatusCode" itemLabel="name" itemValue="code" onchange=""
            items="${statuslist}" cssStyle="width:150px" disabled="true" />
        </security:authorize> <security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:select path="runStatusCode" id="runStatusCode" itemLabel="name" itemValue="code" onchange=""
            items="${statuslist}" cssStyle="width:150px" />
        </security:authorize></td>
      </tr>
      <tr height="40">
        <td width="15%" align="right" class="green">安装日期：</td>
        <td width="15%"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:input path="instDate" cssClass="input_time" disabled="true"
            onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" cssStyle="height:23px;width:150px;"></form:input>
        </security:authorize> <security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:input path="instDate" cssClass="input_time" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"
            readonly="readonly" cssStyle="height:23px;width:150px;"></form:input>
        </security:authorize></td>
        <td align="right" class="green">安装地址：</td>
        <td colspan="3"><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:input path="instAddr" cssClass="input2" cssStyle="width:390px" disabled="true"></form:input>
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:input path="instAddr" cssClass="input2" cssStyle="width:390px"></form:input>
        </security:authorize></td>
      </tr>
      <tr height="40">
        <td align="right" class="green">额定电压_高压：</td>
        <td><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:select path="rvHv" id="rvHv" itemLabel="name" itemValue="code" onchange="" items="${voltlist}"
            disabled="true" cssStyle="width:150px" />
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:select path="rvHv" id="rvHv" itemLabel="name" itemValue="code" onchange="" items="${voltlist}"
            cssStyle="width:150px" />
        </security:authorize></td>
        <td align="right" class="green">额定电压_中压：</td>
        <td><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:select path="rvMv" id="rvMv" itemLabel="name" itemValue="code" onchange="" items="${voltlist}"
            disabled="true" cssStyle="width:150px" />
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:select path="rvMv" id="rvMv" itemLabel="name" itemValue="code" onchange="" items="${voltlist}"
            cssStyle="width:150px" />
        </security:authorize></td>
        <td align="right" class="green">额定电压_低压：</td>
        <td><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:select path="rvLv" id="rvLv" itemLabel="name" itemValue="code" onchange="" items="${voltlist}"
            disabled="true" cssStyle="width:150px" />
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:select path="rvLv" id="rvLv" itemLabel="name" itemValue="code" onchange="" items="${voltlist}"
            cssStyle="width:150px" />
        </security:authorize></td>
      </tr>
      <tr height="40">
        <td align="right" class="green">额定电流_高压：</td>
        <td><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:select path="rcHv" id="rcHv" itemLabel="name" itemValue="code" onchange="" items="${ratedlist}"
            disabled="true" cssStyle="width:150px" />
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:select path="rcHv" id="rcHv" itemLabel="name" itemValue="code" onchange="" items="${ratedlist}"
            cssStyle="width:150px" />
        </security:authorize></td>
        <td align="right" class="green">额定电流_中压：</td>
        <td><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:select path="rcMv" id="rcMv" itemLabel="name" itemValue="code" onchange="" items="${ratedlist}"
            disabled="true" cssStyle="width:150px" />
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:select path="rcMv" id="rcMv" itemLabel="name" itemValue="code" onchange="" items="${ratedlist}"
            cssStyle="width:150px" />
        </security:authorize></td>
        <td align="right" class="green">额定电流_低压：</td>
        <td><security:authorize ifNotGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:select path="rcLv" id="rcLv" itemLabel="name" itemValue="code" onchange="" items="${ratedlist}"
            disabled="true" cssStyle="width:150px" />
        </security:authorize><security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
          <form:select path="rcLv" id="rcLv" itemLabel="name" itemValue="code" onchange="" items="${ratedlist}"
            cssStyle="width:150px" />
        </security:authorize></td>
      </tr>
    </table>
  </form:form>
  <div style="text-align: center"><br />
  <security:authorize ifAnyGranted="ROLE_AUTHORITY_3,ROLE_AUTHORITY_2">
    <input type="button" id="save" value="保 存" class="btnbg4" />
  </security:authorize>&nbsp;<input type="button" id="close" value="关闭" class="btnbg4" onclick="closeWin()" /></div>
  </div>
</ul>
</div>
</body>
<script type="text/javascript">
    val = new Validation(document.forms[0], {onSubmit:true,onFormValidate : function(result, form) {
        return result;
    }}
            );


    //保存变压器信息
    var tgFlag = null;



    getData = function(type) {
        var data;
        if (type == "add") {
            data = $("form[id=tranInfo]").serialize();
        } else {
            data = $("form[id=tranInfo]").serialize();
        }
        return data;
    }


    addTranInfo = function() {
        var fromData = getData('add');
        var url = "${ctx}/archive/tranInfo.json";
        if (confirm("确定要保存该变压器?")) {
            $.ajax({
                url: url,
                data:fromData,
                dataType:'json',
                type:'POST',
                cache: false,
                success: function(json) {
            	 var msg = json['<%=SystemConst.CONTROLLER_AJAX_MESSAGE%>'];
                 var isSucc = json['<%=SystemConst.CONTROLLER_AJAX_IS_SUCC%>'];
                    alert(msg);
                    if(isSucc){
                    opener.location.href = "${ctx}/archive/tginfo/${tranInfo.tgInfo.tgId}/edit";
                    closeWin();
                    }
                },error:function(e) {
                   
                    alert(e.message);
                }
            });
        }
    };

    $(function() {
        $("#save").click(function() {
            if (val.validate()) {
                $(this).attr("disabled", "disabled");
                if($("#_type").val()=="edit"){
                	updateTranInfo();
                }else if($("#_type").val()=="new"){
                	addTranInfo();
                }
                
                $(this).attr("disabled", "");
            } else {
                $(this).attr("disabled", "");
            }
        });
    });

    function   closeWin(){ 
       window.close(); 
      } ;

    updateTranInfo = function (){
    	var tgFromData = getData("update");
    	  var url="${ctx}/archive/tranInfo/${tranInfo.equipId}.json?_method=put";
    	  if(confirm("确定要更新该变压器?")){
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
    	            if(isSucc){
                        opener.location.href = "${ctx}/archive/tginfo/${tranInfo.tgInfo.tgId}/edit";
                        closeWin();
                        }
    	         },error:function(e){
                      alert("error")
    	             alert(e.getMessage());
    	         }
    	       });
    	  }
    }
</script>
</html>