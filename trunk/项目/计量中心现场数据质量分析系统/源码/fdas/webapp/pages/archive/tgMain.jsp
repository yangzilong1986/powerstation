<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>公变主页面</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.dataForAjax.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
<script type="text/javascript">
var contextPath='<peis:contextPath/>';
//保存台区
function saveTg(){
   var url=contextPath+"/archive/updateLowCustInfoAction.do?action=saveOrUpdateTg";
      if(tgCheck()){
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
                       top.getMainFrameObj().location.reload();
                    }else if(msg=="2"){
                       alert("该台区已经存在");
                    }else{
                       alert("保存失败");
                    }
                }
            });
        }
      }
}
//删除台区
function delTg(tgId){
  var url=contextPath+"/archive/updateLowCustInfoAction.do?action=deleteTg&tgId="+tgId;
  if(confirm("确定要删除该台区?")){
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
                top.getMainFrameObj().location.href=contextPath+"/archive/tgListQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0028";
                }else if(msg=="3"){
                    alert("该台区下存在其它设备，不允许删除");
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
<body>
<div class="tab"><em>台区信息</em></div>
<div class="tab_con">
<div id="form">
  <div class="main">
   <input type="hidden" name="tgId" value="${tgId}"/>
   <table border="0" cellpadding="0" cellspacing="0" width="100%">
      <tr>
        <td width="13%" class="label"><font color="red">* </font>台 区 号：</td>
        <td width="20%" class="dom"><input type="text" id="tgNo" name="tgNo" value="${object_tg.tgNo}" /></td>
        <td width="13%" class="label"><font color="red">* </font>台 区 名：</td>
        <td width="20%" class="dom"><input type="text" id="tgName" name="tgName" value="${object_tg.tgName}" /></td>
        <td width="13%" class="label">
           <input type="checkbox" name="isChecked" value="${object_tg.isChecked!="" ? "1" : "0"}" ${object_tg.isChecked==1 ? "checked" : ""}/>
        </td>
        <td width="20%" class="dom">投入考核</td>
      </tr>
      <tr>
        <td width="13%" class="label"><font color="red">* </font>供电单位：</td>
        <td width="20%" class="dom">
           <peis:selectlist name="orgNo" sql="SL_COMMON_0001"/>
           <script type="text/javascript">
            $("#orgNo").val('${object_tg.orgNo}');
           </script>
        </td>
        <td width="13%" class="label">容　量：</td>
        <td width="20%" class="dom"><input type="text" id="tgCapa" name="tgCapa" value="<fmt:formatNumber value="${object_tg.tgCapa}" pattern="#0.0000#"  minFractionDigits="0"/>" style="width:110"/>kVA</td>
        <td width="13%" class="label">线损阈值：</td>
        <td width="20%" class="dom">
           <input type="text" id="tgThreshold" name="tgThreshold"  value="<c:if test="${object_tg.tgThreshold!=null}"><fmt:formatNumber value="${object_tg.tgThreshold*100}" pattern="#0.0000#"  minFractionDigits="0"/></c:if>" style="width: 125px;"/> %</td>
      </tr>
      <tr>
        <td width="13%" class="label">行 政 区：</td>
        <td width="20%" class="dom">
          <peis:selectlist name="sar" sql="SL_ARCHIVE_0002"/>
          <script type="text/javascript">
           $("#sar").val(${object_tg.sar});
          </script>
        </td>
        <td width="13%" class="label">地　域：</td>
        <td width="20%" class="dom">
          <peis:selectlist name="area" sql="SL_ARCHIVE_0003"/>
           <script type="text/javascript">
            $("#area").val(${object_tg.area});
           </script>
        </td>
        <td width="13%" class="label">台区状态：</td>
        <td width="20%" class="dom">
         <peis:selectlist name="tgStatus" sql="SL_ARCHIVE_0004"/>
          <script type="text/javascript">
           $("#tgStatus").val(${object_tg.tgStatus});
          </script>
        </td>
      </tr>
      <tr>
        <td width="13%" class="label">联 系 人：</td>
        <td width="20%" class="dom">
         <input type="text" name="contact" id="contact" value="${object_tg.contact}">
        </td>
        <td width="13%" class="label">联系电话：</td>
        <td width="20%" class="dom">
         <input type="text" name="phone" id="phone" value="${object_tg.phone}">
        </td>
        <td width="13%" class="label">地　址：</td>
        <td width="20%" class="dom">
         <input type="text" name="installAddr" id="installAddr" value="${object_tg.installAddr}">
        </td>
     </tr>
     <tr>
      <td rowspan="3" class="label">备　注：</td>
      <td class="dom" colspan="4">
        <textarea class="input_textarea3" name="remark" style="width:500;height:80" >${object_tg.remark}</textarea>
      </td>
     </tr>
     <tr>
        <td colspan="6" align="right">
          <input type="button" class="input3 mgr10" id="save" value="保存台区" onClick="saveTg()"/>
          <input type="button" class="input3 mgr10" id="delete" value="删除台区" onClick="delTg(${tgId})"/>
        </td>
     </tr>
   </table>
  </div>
</div>
</div>
</body>
</html>