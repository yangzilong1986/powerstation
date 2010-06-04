<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>变压器信息</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.dataForAjax.js"></script>
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';
//AJAX提交表单
   function save(){
      var url=contextPath+"/archive/tranAction.do?action=updateTran";
      if(tranCheck()){
       $("#save").attr("disabled",true);
        var data = getFormData("form");
        if(data){
            jQuery.ajax({
                type:'post',
                url:url,
                data:data,
                dataType:'json',
                success:function(json){
                    var msg=json['msg'];
                    $("#save").attr("disabled",false);
                    if(msg=="1"){
                       alert("保存成功");
                       var tgId=$("input[name='objectId']").val();
                       top.getMainFrameObj().location.href=contextPath+"/archive/updateLowCustInfoAction.do?action=showTgEdit&tgId="+tgId;
                    }else if(msg=="2"){
                       alert("该资产编号已经存在");
                    }else{
                       alert("保存失败");
                    }
                }
            });
        }
      }
   }
   //删除变压器
   function deleteTran(tranId){
     var url=contextPath+"/archive/tranAction.do?action=deleteTran&tranId="+tranId;
     if(confirm("确定要删除该变压器?")){
          $("#delete").attr("disabled",true);
           $.ajax({
            url: url,
            dataType:'json',
            cache: false,
            success: function(json){
              var msg=json['msg'];
              $("#delete").attr("disabled",false);
               if(msg=="1"){
                alert("删除成功");
                top.getMainFrameObj().location.reload();
                 }else if(msg=="3"){
                   alert("变压器下存在终端（集中器）或电表，不允许删除");
                }
                else{
                   alert("删除失败");
                }
            }
       });
     }
   }
</script>
</head>
<body style="overflow: hidden;">
<div class="tab"><em>变压器信息</em></div>
  <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 25));">
  <div class="main">
  <div id="form">
  <input type="hidden" id="tranId" name="tranId" value="${object_tran.tranId}"/>
  <input type="hidden" name="objectId" value="${tgId}"/>
     <table border="0" cellpadding="0" cellspacing="0" width="100%">
       <tr>
          <td width="20%" class="label"><font color="red">* </font>资产编号：</td>
          <td width="13%" class="dom"><input type="text" id="assetNo" name="assetNo" value="${object_tran.assetNo}"/></td>
          <td width="20%" class="label"><font color="red">* </font>变压器名称：</td>
          <td width="13%" class="dom"><input type="text" id="tranName" name="tranName" value="${object_tran.tranName}"/></td>
          <td width="20%" class="label">变压器型号：</td>
          <td width="13%" class="dom">
             <peis:selectlist name="modelCode" sql="SL_ARCHIVE_0005"/>
             <script type="text/javascript">
               $("#modelCode").val(${object_tran.modelCode});
             </script>
          </td>
        </tr>
       <tr>
          <td class="label">容　量：</td>
          <td class="dom"><input type="text" id="tgCapa" name="tranCapa" value="<fmt:formatNumber value="${object_tran.tranCapa}" pattern="#0.0000#"  minFractionDigits="0"/>"  style="width: 115px;"/> kVA</td>                
          <td class="label">变损阈值：</td>
          <td class="dom">
             <input type="text" id="tgThreshold" name="tranThreshold"  value="<c:if test="${object_tran.tranThreshold!=null}"><fmt:formatNumber value="${object_tran.tranThreshold*100}" pattern="#0.0000#"  minFractionDigits="0"/></c:if>" style="width: 125px;"/> %</td>
          <td class="label">运行状态：</td>
          <td class="dom">
            <peis:selectlist name="tranStatus" sql="SL_ARCHIVE_0006"/>
            <script type="text/javascript">
               $("#tranStatus").val(${object_tran.tranStatus});
             </script>
          </td>
        </tr>
         <tr>
          <td class="label">入口电压等级：</td>
          <td class="dom" >
            <peis:selectlist name="firstVlot" sql="SL_ARCHIVE_0007"/>
            <script type="text/javascript">
               $("#firstVlot").val(${object_tran.firstVlot});
             </script>
          </td>
          <td class="label">出口电压等级1：</td>
          <td class="dom">
            <peis:selectlist name="secondVlot" sql="SL_ARCHIVE_0007"/>
            <script type="text/javascript">
               $("#secondVlot").val(${object_tran.secondVlot});
             </script>
          </td>
          <td class="label">出口电压等级2：</td>
          <td class="dom">
            <peis:selectlist name="thirdVlot" sql="SL_ARCHIVE_0007"/>
            <script type="text/javascript">
               $("#thirdVlot").val(${object_tran.thirdVlot});
             </script>
          </td>
        </tr>
        <tr>
          <td class="label">安装日期：</td>
          <td class="dom_date">
            <input type="text" name="installDate" id="installDate" value="<fmt:formatDate value="${object_tran.installDate}" type="date"/>" onfocus="peisDatePicker()" readonly="readonly" />
          </td>
          <td class="label">安装地址：</td>
          <td colspan="3" class="dom">
            <input type="text" id="installAddr" name="installAddr" style="width:300" value="${object_tran.installAddr}"/>
          </td>
        </tr>
        <tr>
          <td rowspan="3" class="label">备　注：</td>
          <td class="dom" colspan="5">
            <textarea name="remark" style="width:462;height:80">${object_tran.remark}</textarea>
          </td>
        </tr>
        <tr>
          <td colspan="6" align="right">
             <input type="button" class="input3" id="save" value="保存变压器" onClick="save();"/>
             <input type="button" class="input3" id="delete" value="删除变压器" onClick="deleteTran(${object_tran.tranId})"/>
          </td>
        </tr>
     </table>
  </div>
  </div>
</div>
</body>
</html>