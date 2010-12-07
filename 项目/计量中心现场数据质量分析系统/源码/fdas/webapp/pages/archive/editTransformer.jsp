<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>变压器信息</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
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
        var data = getFormData("form");
        if(data){
            jQuery.ajax({
                type:'post',
                url:url,
                data:data,
                dataType:'json',
                success:function(json){
                    var msg=json['msg'];
                    if(msg=="1"){
                       alert("保存成功");
                       parent.GB_hide();
                       top.getMainFrameObj().location.href=contextPath+"/archive/addLowCustAction.do?action=showDeviceInfoByTgID";
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
</script>
</head>
<body style="overflow: hidden;">
<div id="main" style="height: 100%;">
  <div class="tab"><em>变压器信息</em></div>
  <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 80));">
    <div class="main">
      <div id="form">
       <input type="hidden" name="objectId" value="${tgId}"/>
       <input type="hidden" name="tranId" value="${tranId}"/>
      <table border="0" cellpadding="0" cellspacing="0" width="900" align="center">
        <tr>
          <td width="13%" class="label"><font color="red">* </font>资产编号：</td>
          <td width="20%" class="dom"><input type="text" id="assetNo" name="assetNo" value="${object_tran.assetNo}"/></td>
          <td width="13%" class="label"><font color="red">* </font>变压器名称：</td>
          <td width="20%" class="dom"><input type="text" id="tranName" name="tranName" value="${object_tran.tranName}"/></td>
           <td width="13%" class="label">变压器型号：</td>
          <td width="21%" class="dom">
             <peis:selectlist name="modelCode" sql="SL_ARCHIVE_0005"/>
             <script type="text/javascript">
               $("#modelCode").val(${object_tran.modelCode});
             </script>
          </td>
        </tr>
        <tr>
          <td width="13%" class="label">容　量：</td>
          <td width="20%" class="dom"><input type="text" id="tgCapa" name="tranCapa" value="<fmt:formatNumber value="${object_tran.tranCapa}" pattern="#0.0000#"  minFractionDigits="0"/>"  style="width: 115px;"/> kVA</td>                
          <td width="13%" class="label">变损阈值：</td>
          <td width="20%" class="dom">
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
          <td colspan="5" class="dom2">
            <input type="text" id="installAddr" name="installAddr" value="${object_tran.installAddr}"/>
          </td>
        </tr>
        <tr>
          <td rowspan="3" class="label">备　注：</td>
          <td class="dom" colspan="5">
            <textarea name="remark" style="width: 734px; height: 120px;">${object_tran.remark}</textarea>
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