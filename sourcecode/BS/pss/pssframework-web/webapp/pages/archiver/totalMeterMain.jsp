<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>配变用户档案(电表)</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/greybox.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/xtheme-gray.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/ext/ext-all.js"></script>
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
$(document).ready(function(){
  selectInit();
});
//所属终端
function addTerminal(){
   var tgId=$("input[name='tgId']").val();
   var params = {
                 "sqlCode":"AL_ARCHIVE_0021",
                 "pageRows":20,
                 "objectId":tgId
               };
   var url=contextPath+"/archive/termListQuery.do?action=normalMode&"+ jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
   top.showDialogBox("终端定位",url, 495, 800);
}
//保存电表信息
function save(){
   var mpId=$("input[name='mpId']").val();
   var url=contextPath+"/archive/meterAction.do?action=updateMeter&mpId="+mpId;
      if(totalMeterCheck()){
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
                       var tgId=$("input[name='tgId']").val();
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
//删除总表
   function delMeter(mpId){
     var url=contextPath+"/archive/meterAction.do?action=deleteTotalMeter&mpId="+mpId;
     if(confirm("确定要删除该总表?")){
           $("#delete").attr("disabled",true);
              $.ajax({
            url: url,
            dataType:'json',
            cache: false,
            success: function(json){
             $("#delete").attr("disabled",false);
              var msg=json['msg'];
               if(msg=="1"){
                alert("删除成功");
                top.getMainFrameObj().location.reload();
                }
                else{
                   alert("删除失败");
                }
            }
       });
     }
   }
//连接远传表
function meterState(){
   var meterState=$("#meterType").val();
   var mpId = $("input[name='mpId']").val();
   if(mpId == ''){ //新增电表
       if(meterState=="1"){
	      var url="<peis:contextPath/>/jsp/archive/remoteMeterLowCustTgMain.jsp";
	      window.location.href=url;
	   }
   }
}
//select框初始化
function selectInit(){
  var mpId = $("input[name='mpId']").val();
  var meterType = $("select[name='meterType']").val();
  if(mpId!=''){ //编辑电表的时候
     if(meterType == '1'){ //远传表
       $("#meterType > option[value!='1']").remove();
     }else{
       $("#meterType > option[value='1']").remove();
     }
  }
}
</script>
</head>
<body>
<div id="form">
<input type="hidden" name="orgNo" value="${orgNo}" />
<input type="hidden" name="tgId" value="${tgId}"/>
<input type="hidden" name="mpId" value="${mpId}"/>
<input type="hidden" name="gpId" value="${gpId}"/>
<div class="tab"><em>电表信息</em></div>
  <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 25));">
  <div class="main">
      <div id="485Show">
      <div class="data3"><span>重要信息</span></div>
      <div class="data3_con">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td width="13%" class="label"><font color="red">* </font>序　号：</td>
            <td width="20%" class="dom"><input type="text" name="gpSn" id="gpSn" value="${object_gp.gpSn}"/></td>
            <td width="13%" class="label"><font color="red">* </font>计量点名称：</td>
            <td width="20%" class="dom"><input type="text" name="mpName" id="mpName" value="${object_meter.mpName}"/></td>
            <td width="13%" class="label"><font color="red">* </font>资产编号：</td>
            <td width="20%" class="dom"><input type="text" name="assetNo" id="assetNo" value="${object_meter.assetNo}"/></td>
           </tr>
          <tr>
            <td  class="label">CT变比：</td>
            <td  class="dom">
              <peis:selectlist name="ctRatio" sql="SL_ARCHIVE_0019"/>
              <script type="text/javascript">
               $("#ctRatio").val(${object_meter.ctRatio});
              </script>
            </td>
            <td  class="label">PT变比：</td>
            <td  class="dom">
              <peis:selectlist name="ptRatio" sql="SL_ARCHIVE_0020"/>
              <script type="text/javascript">
               $("#ptRatio").val(${object_meter.ptRatio});
              </script>
            </td>
            <td  class="label"><font color="red">* </font>表 地 址：</td>
            <td  class="dom"><input type="text" id="gpAddr" name="gpAddr" value="${object_gp.gpAddr}"/></td>
           </tr>
           <tr>
            <td  class="label">表 规 约：</td>
            <td  class="dom">
              <peis:selectlist name="protocolNo" sql="SL_ARCHIVE_0022"/>
              <script type="text/javascript">
               $("#protocolNo").val(${object_gp.protocolNo});
              </script>
            </td>
            <td  class="label">所属终端：</td>
            <td  class="dom">
             <peis:selectlist name="termId" sql="SL_ARCHIVE_0031"/>
              <script type="text/javascript">
               $("#termId").val('${object_gp.termId}');
              </script>
              <input type="button" value="..." onclick="addTerminal();" style="width:20px"/>
            </td>
            <td  class="label">变压器：</td>
            <td  class="dom">
               <peis:selectlist name="tranId" sql="SL_ARCHIVE_0021"/>
               <script type="text/javascript">
               $("#tranId").val('${object_gp.tranId}');
               </script>
            </td>
           </tr>
          </table>
      </div>
    </div>
    <div id="pulseShow">
      <div class="data3"><span>一般信息</span></div>
      <div class="data3_con">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
           <tr>
            <td width="13%" class="label">厂　家：</td>
            <td width="20%" class="dom">
              <peis:selectlist name="madeFactory" sql="SL_ARCHIVE_0008"/>
              <script type="text/javascript">
               $("#madeFactory").val('${object_meter.madeFactory}');
               </script>
            </td>
            <td width="13%" class="label">出厂编号：</td>
            <td width="20%" class="dom">
              <input type="text" name="leaveFacNo" id="leaveFacNo" value="${object_meter.leaveFacNo}"/>
            </td>
            <td width="13%" class="label">波 特 率：</td>
            <td width="20%" class="dom">
              <peis:selectlist name="btl" sql="SL_ARCHIVE_0023"/>
              <script type="text/javascript">
               $("#btl").val(${object_meter.btl});
               </script>
            </td>
           </tr>
           <tr>
            <td  class="label">表 类 型：</td>
            <td  class="dom">
             <peis:selectlist name="meterType" sql="SL_ARCHIVE_0024" onChange="meterState();"/>
             <script type="text/javascript">
               $("#meterType").val(${object_meter.meterType});
               </script>
            </td>
            <td  class="label">额定电压：</td>
            <td  class="dom">
              <peis:selectlist name="ratedVolt" sql="SL_ARCHIVE_0026"/>
              <script type="text/javascript">
               $("#ratedVolt").val(${object_meter.ratedVolt});
               </script>
            </td>
            <td  class="label">额定电流：</td>
            <td  class="dom">
              <peis:selectlist name="ratedEc" sql="SL_ARCHIVE_0025"/>
              <script type="text/javascript">
               $("#ratedEc").val(${object_meter.ratedEc});
               </script>
            </td>
           </tr>
           <tr>
            <td  class="label">相　线：</td>
            <td  class="dom">
              <peis:selectlist name="wiringMode" sql="SL_ARCHIVE_0026"/>
              <script type="text/javascript">
               $("#wiringMode").val(${object_meter.wiringMode});
               </script>
            </td>
            <td  class="label">供电线路：</td>
            <td  class="dom">
              <peis:selectlist name="lineId" sql="SL_ARCHIVE_0018" associate="true"/>
              <script type="text/javascript">
                 //$("#lineId").val(${object_meter.lineId});
                 lineIdComboBox.setValue('${object_meter.lineId}');
               </script>
            </td>
            <td  class="label">表计型号：</td>
            <td  class="dom">
              <peis:selectlist name="modelCode" sql="SL_ARCHIVE_0029"/>
              <script type="text/javascript">
               $("#modelCode").val(${object_meter.modelCode});
               </script>
            </td>
           </tr>
             <tr>
             <td  class="label">端 口 号：</td>
             <td  class="dom">
              <input type="text" id="port" name="port" value="${object_gp.port}"/>
             </td>
             <td  class="label">位　数：</td>
             <td  class="dom">
                <input type="text" id="meterDigits" name="meterDigits" value="${object_meter.meterDigits}"/>
             </td>
             <td  class="label">通信密码：</td>
             <td  class="dom">
              <input type="password" id="commPwd" name="commPwd" value="${object_meter.commPwd}"/>
             </td>
           </tr>
           <tr>
             <td  class="label">计量方式：</td>
             <td  class="dom">
               <peis:selectlist name="measMode" sql="SL_ARCHIVE_0030"/>
               <script type="text/javascript">
               $("#measMode").val(${object_meter.measMode});
               </script>
             </td>
             <td  class="label">安装日期：</td>
             <td  class="dom_date">
              <input type="text" name="mpDate" id="mpDate" value="${object_meter.mpDate}" onfocus="peisDatePicker()" readonly="readonly" />
             </td>
             <td width="34%"  colspan="2" align="center">
              <input type="checkbox" id="sucratCptId" name="sucratCptId" value="${object_gp.sucratCptId!="" ? "1" : "0"}" ${object_gp.sucratCptId==1 ? "checked" : ""}>功率累计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <input type="checkbox" id="computeFlag" name="computeFlag" value="${object_gp.computeFlag!="" ? "1" : "0"}" ${object_gp.computeFlag==1 ? "checked" : ""}/>电量累计
            </td>
           </tr>
          <tr>
           <td colspan="6" align="right">
              <input type="button" class="input3"  id="save" value="保存总表" onClick="save();"/>
              <input type="button" class="input3" id="delete" value="删除总表" onClick="delMeter(${mpId})"/>
           </td>
          </tr>
          </table>
         </div>
      </div>
  </div>
</div>
</div>
</body>
</html>