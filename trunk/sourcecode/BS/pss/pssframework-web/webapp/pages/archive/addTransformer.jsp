<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
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
    style="display: block; overflow-y: auto; overflow-x: auto; width: expression(( document.documentElement.clientWidth ||             document.body.clientWidth) -10 ); height: expression(((             document.documentElement.clientHeight ||             document.body.clientHeight) -35 ) );">
  <form:form action="/archive/tranInfo" modelAttribute="tranInfo">
    <form:hidden path="equipId" />
    <form:hidden path="tgId" />
    <table border="0" cellpadding="0" cellspacing="0" align="center">
      <tr height="40">
        <td width="15%" align="right" class="green"><font color="red">* </font>变压器名称：</td>
        <td width="15%"><form:input path="tranName" cssClass="required input2" maxlength="16" /></td>
        <td width="15%" align="right" class="green">变压器型号：</td>
        <td width="15%"><form:select path="typeCode" id="typeCode" itemLabel="name" itemValue="code" onchange=""
          items="${typelist}" cssStyle="width:150px" /></td>
      </tr>
      <tr height="40">
        <td width="13%" align="right" class="green">容 量：</td>
        <td width="20%"><form:input path="plateCap" cssClass="input2" cssStyle="width:130px" /> kVA</td>
        <td align="right" class="green">运行状态：</td>
        <td><form:select path="runStatusCode" id="runStatusCode" itemLabel="name" itemValue="code" onchange=""
          items="${statuslist}" cssStyle="width:150px" /></td>
      </tr>
      <tr height="40">
        <td width="15%" align="right" class="green">安装日期：</td>
        <td width="15%"><form:input path="instDate" cssClass="input_time"
          onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" cssStyle="height:23px;width:150px;"></form:input></td>
        <td align="right" class="green">安装地址：</td>
        <td colspan="3"><form:input path="instAddr" cssClass="input2" cssStyle="width:390px"></form:input></td>
      </tr>
      <tr height="40">
        <td align="right" class="green">额定电压_高压：</td>
        <td><form:select path="rvHv" id="rvHv" itemLabel="name" itemValue="code" onchange="" items="${voltlist}"
          cssStyle="width:150px" /></td>
        <td align="right" class="green">额定电压_中压：</td>
        <td><form:select path="rvMv" id="rvMv" itemLabel="name" itemValue="code" onchange="" items="${voltlist}"
          cssStyle="width:150px" /></td>
        <td align="right" class="green">额定电压_低压：</td>
        <td><form:select path="rvLv" id="rvLv" itemLabel="name" itemValue="code" onchange="" items="${voltlist}"
          cssStyle="width:150px" /></td>
      </tr>
      <tr height="40">
        <td align="right" class="green">额定电流_高压：</td>
        <td><form:select path="rcHv" id="rcHv" itemLabel="name" itemValue="code" onchange="" items="${ratedlist}"
          cssStyle="width:150px" /></td>
        <td align="right" class="green">额定电流_中压：</td>
        <td><form:select path="rcMv" id="rcMv" itemLabel="name" itemValue="code" onchange="" items="${ratedlist}"
          cssStyle="width:150px" /></td>
        <td align="right" class="green">额定电流_低压：</td>
        <td><form:select path="rcLv" id="rcLv" itemLabel="name" itemValue="code" onchange="" items="${ratedlist}"
          cssStyle="width:150px" /></td>
      </tr>
    </table>
  </form:form>
  <div style="text-align: center"><br />
  <input type="button" id="save" value="保 存" class="btnbg4" /></div>
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


    //select框初始化
    function selectInit() {
        $("#modelCode").val(${object_tran.modelCode});
        $("#tranStatus").val(${object_tran.tranStatus});
        $("#firstVlot").val(${object_tran.firstVlot});
        $("#secondVlot").val(${object_tran.secondVlot});
        $("#thirdVlot").val(${object_tran.thirdVlot});
    }


    getData = function(type) {
        var data;
        if (type == "add") {
            data = jQuery("form[id=tranInfo]").serialize();
        } else {
            data = jQuery("form[id=tranInfo]").serialize();
        }
        return data;
    }


    addTran = function() {
        var fromData = getData('add');
        var url = "${ctx}/archive/tranInfo.json";
        if (confirm("确定要保存该变压器?")) {
            jQuery.ajax({
                url: url,
                data:fromData,
                dataType:'json',
                type:'POST',
                cache: false,
                success: function(json) {
                    var msg = json['msg'];
                    var isSucc = json['isSucc'];
                    alert(msg);
                    if(isSucc){
                    opener.location.href = "${ctx}/archive/tginfo/${tranInfo.tgId}/edit";
                    window.close();
                    }
                },error:function(e) {
                    alert("save error");
                    alert(e.message);
                }
            });
        }
    }

    jQuery(function() {
        jQuery("#save").click(function() {
            if (val.validate()) {
                jQuery(this).attr("disabled", "disabled");
                addTran();
                jQuery(this).attr("disabled", "");
            } else {
                jQuery(this).attr("disabled", "");
            }
        });
    })

</script>
</html>