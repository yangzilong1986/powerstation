<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>采集器信息</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.dataForAjax.js"></script>
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';
var termId=$.url.param("termId");
var gpId=$.url.param("gpId");
//初始化加载
$(function(){
  $("input[name='termId']").val(termId);
  $("input[name='gpId']").val(gpId);
})
//保存采集器信息
function save(){

    var url=contextPath+"/archive/gmAction.do?action=addGm2ByLowCustNew";
=======
    var url=contextPath+"/archive/gmInfoAction.do?action=saveOrUpdateGm&r=" + parseInt(Math.random() * 1000);
>>>>>>> .r1071
      if(GMCheck()){
        $("#save").attr("disabled",true);
        var data =getFormData("form");
        if(data){
            jQuery.ajax({
                type:'post',
                url:url,
                data:data,
                dataType:'json',
                success:function(json){
                    var termId=json['termId'];
                    var msg=json['msg'];
                    $("#save").attr("disabled",false);
                    if(msg=="1"){
                       alert("保存成功");
                       parent.GB_hide();
                       parent.contentArea.location.href=contextPath+"/archive/gmListQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0023&termId="+termId+"&pageRows=20";
                    }else if(msg=="2"){
                       alert("资产编号已经存在");
                    }else{
                       alert("保存失败");
                    }
                },
                error :function(XMLHttpRequest,textStatus,errorThrown){
                   $("#save").attr("disabled",false);
                   alert(textStatus);
                   alert(errorThrown);
                }
            });
        }
      }
}
</script>
</head>
<body style="overflow: hidden;">
<div id="main" style="height: 100%;">
  <div class="tab"><em>采集器信息</em></div>
  <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 80));">
    <div class="main">
     <div id="form">
     <input type="hidden" name="termId" value="${object_gm.termId}"/>
     <input type="hidden" name="gmId" value="${object_gm.gmId}"/>
     <table border="0" cellpadding="0" cellspacing="0" align="center">
        <tr>
          <td width="13%" class="label"><font color="red">* </font>资产编号：</td>
          <td width="20%" class="dom"><input type="text" id="assetNo" name="assetNo" value="${object_gm.assetNo}"/></td>
          <td width="13%" class="label"><font color="red">* </font>采集器地址：</td>
          <td width="20%" class="dom"><input type="text" id="gmAddr" name="gmAddr" value="${object_gm.gmAddr}"/></td>
          <td width="13%" class="label">通讯方式：</td>
          <td width="21%" class="dom">
            <peis:selectlist name="commMode" sql="SL_ARCHIVE_0010"/>
            <script type="text/javascript">
               $("#commMode").val(${object_gm.commMode});
            </script>
          </td>
        </tr>
        <tr>
          <td class="label">抄表周期：</td>
          <td class="dom"><input type="text" name="rmCycle" id="rmCycle" style="width: 125px;" value="${object_gm.rmCycle}"/> 分</td>
          <td class="label">型　号：</td>
          <td class="dom">
            <peis:selectlist name="modelCode" sql="SL_ARCHIVE_0088"/>
            <script type="text/javascript">
               $("#modelCode").val(${object_gm.modelCode});
            </script>
          </td>
          <td class="label">厂　家：</td>
          <td class="dom">
           <peis:selectlist name="madeFac" sql="SL_ARCHIVE_0008"/>
           <script type="text/javascript">
               $("#madeFac").val('${object_gm.madeFac}');
            </script>
          </td>
        </tr>
        <tr>
          <td class="label">安装日期：</td>
          <td class="dom_date">
           <input type="text" id="installDate" name="installDate" value="<fmt:formatDate value="${object_gm.installDate}" type="date"/>" onfocus="peisDatePicker()" readonly="readonly" />
          </td>
          <td class="label">状　态：</td>
          <td class="dom">
            <peis:selectlist name="gmStatus" sql="SL_ARCHIVE_0009"/>
            <script type="text/javascript">
               $("#gmStatus").val(${object_gm.gmStatus});
            </script>
          </td>
        </tr>
       <tr>
          <td class="label">安装单位：</td>
          <td class="dom" colspan="5">
            <input type="text" name="constrGang" style="width:612px;" value="${object_gm.constrGang}"/>
          </td>
        </tr>
        <tr>
          <td class="label">备　注：</td>
          <td class="dom" colspan="5">
            <textarea class="input_textarea3" name="remark" style="width:612px;height:180px">${object_gm.remark}</textarea>
          </td>
        </tr>
     </table>
     </div>
    </div>
  </div>
  <div class="guidePanel">
    <input class="input1" type="button" id="save" value="保 存" onclick="save();" />
  </div>
</div>
</body>
</html>