<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>专变用户档案(电表)</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/greybox.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.dataForAjax.js"></script>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/xtheme-gray.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/ext/ext-all.js"></script>
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';

$(document).ready(function(){
  selectInit();
});
//连接远传表

function meterState(){
   var meterState=$("#meterType").val();
   var mpId = $("input[name='mpId']").val();
   if(mpId == ''){ //新增电表
       if(meterState=="1"){
	      var url="<peis:contextPath/>/jsp/archive/remoteMeterLowCustTg.jsp";
	   }else{
	      var url="<peis:contextPath/>/jsp/archive/updateTotalMeter.jsp";
	   } 
	   window.location.href=url;
   }
}
//保存电表信息
function save(){
   var url=contextPath+"/archive/meterAction.do?action=saveOrUpdateRemoteMeterInBigCust";
   if(remoteMeterCheck()){
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
                       parent.GB_hide();
                    }else if(msg=="2"){
                       alert("该资产编号已经存在");
                    }else if(msg=="5"){
                       alert("该终端逻辑地址已经存在");
                    }
                    else{
                       alert("保存失败");
                    }
                }
            });
        }
   }
}
//select框初始化
function selectInit(){
  $("#meterType").val('${object_meter.meterType}');
  $("#measMode").val('${object_meter.measMode}');
  $("#ctRatio").val('${object_meter.ctRatio}');
  $("#ptRatio").val('${object_meter.ptRatio}');
  $("#protocolNo").val('${object_gp.protocolNo}');
  $("#madeFactory").val('${object_meter.madeFactory}');
  $("#btl").val('${object_meter.btl}');
  $("#meterDigits").val('${object_meter.meterDigits}');
  $("#wiringMode").val('${object_meter.wiringMode}');
  $("#ratedVolt").val('${object_meter.ratedVolt}');
  $("#ratedEc").val('${object_meter.ratedEc}');
  lineIdComboBox.setValue('${object_meter.lineId}');
  $("#status").val('${object_meter.status}');
  $("#isMas").val('${object_meter.isMas}');
  $("#termProtocolNo").val('${object_term.protocolNo}');
  $("#commMode").val('${object_term.commMode}');
  $("#curStatus").val('${object_term.curStatus}');
 
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
<body style="overflow: hidden;">
<div id="form">
<input type="hidden" name="mpId" id="mpId" value="${object_meter.mpId}"/>
<input type="hidden" name="objectId" value="${tgId}"/>
<input type="hidden" name="gpType" value="2"/>
<input type="hidden" name="objectType" value="2"/>
<div id="main" style="height: 100%;">
  <div class="tab"><em>电表信息</em></div>
  <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 80));">
    <div class="main">
       <table border="0" cellpadding="0" cellspacing="0" width="100%">
      <tr>
       <td width="15%" class="label">表 类 型：</td>
       <td width="25%" class="dom">
         <peis:selectlist name="meterType" sql="SL_ARCHIVE_0084" onChange="meterState();"/>
       </td>
       <td width="15%" class="label">计量点名称：</td>
       <td width="25%" class="dom"><input type="text" id="mpName" name="mpName" value="${object_meter.mpName}"></td>
      </tr>
      <tr>
       <td width="15%" class="label"><font color="red">* </font>资产编号：</td>
       <td width="25%" class="dom"><input type="text" id="assetNo" name="assetNo" value="${object_meter.assetNo}"/></td>
       <td width="15%" class="label">计量方式：</td>
       <td width="25%" class="dom">
         <peis:selectlist name="measMode" sql="SL_ARCHIVE_0030"/>
       </td>
      </tr>
      <tr>
       <td width="15%" class="label">CT变比：</td>
       <td width="25%" class="dom">
         <peis:selectlist name="ctRatio" sql="SL_ARCHIVE_0019"/>
       </td>
       <td width="15%" class="label">PT变比：</td>
       <td width="25%" class="dom">
          <peis:selectlist name="ptRatio" sql="SL_ARCHIVE_0020"/>
       </td>
      </tr>
      <tr>
       <td width="15%" class="label">表 规 约：</td>
       <td width="25%" class="dom">
          <peis:selectlist name="protocolNo" sql="SL_ARCHIVE_0022"/>
       </td>
       <td width="15%" class="label"><font color="red">* </font>表 地 址：</td>
       <td width="25%" class="dom"><input type="text" id="gpAddr" name="gpAddr" value="${object_gp.gpAddr}"/></td>
      </tr>
      <tr>
       <td width="15%" class="label">通讯端口：</td>
       <td width="25%" class="dom"><input type="text" id="port" name="port" value="${object_gp.port}"/></td>
       <td width="15%" class="label">设备厂家：</td>
       <td width="25%" class="dom">
          <peis:selectlist name="madeFactory" sql="SL_ARCHIVE_0008"/>
       </td>
      </tr>
      <tr>
       <td width="15%" class="label">波 特 率：</td>
       <td width="25%" class="dom">
          <peis:selectlist name="btl" sql="SL_ARCHIVE_0023"/>
       </td>
       <td width="15%" class="label">翻转位数：</td>
       <td width="25%" class="dom">
          <peis:selectlist name="meterDigits" sql="SL_ARCHIVE_0043"/>
       </td>
      </tr>
      <tr>
       <td width="15%" class="label">相　线：</td>
       <td width="25%" class="dom">
         <peis:selectlist name="wiringMode" sql="SL_ARCHIVE_0027"/>
       </td>
       <td width="15%" class="label">出厂编号：</td>
       <td width="25%" class="dom"><input type="text" id="leaveFacNo" name="leaveFacNo" value="${object_meter.leaveFacNo}"/></td>
      </tr>
      <tr>
       <td width="15%" class="label">额定电压：</td>
       <td width="25%" class="dom">
         <peis:selectlist name="ratedVolt" sql="SL_ARCHIVE_0026"/>
       </td>
       <td width="15%" class="label">额定电流：</td>
       <td width="25%" class="dom">
         <peis:selectlist name="ratedEc" sql="SL_ARCHIVE_0025"/>
       </td>
      </tr>
      <tr>
       <td width="15%" class="label">状　态：</td>
       <td width="25%" class="dom">
         <peis:selectlist name="status" sql="SL_ARCHIVE_0044"/>
       </td>
       <td width="15%" class="label">供电线路：</td>
       <td width="25%" class="dom">
          <peis:selectlist name="lineId" sql="SL_ARCHIVE_0018" associate="true"/>
       </td>
      </tr>
      <tr>
      <td width="15%" class="label">主 备 表：</td>
       <td width="25%" class="dom">
         <peis:selectlist name="isMas" sql="SL_ARCHIVE_0045"/>
       </td>
       <td width="15%" class="label">通信密码：</td>
       <td width="25%" class="dom">
          <input type="password" id="commPwd" name="commPwd" value="${object_meter.commPwd}"/>
       </td>
      </tr>
      <tr>
         <td width="34%"  colspan="2" align="center">
            <input type="checkbox" id="sucratCptId" name="sucratCptId" value="${object_gp.sucratCptId!="" ? "1" : "0"}" ${object_gp.sucratCptId==1 ? "checked" : ""}>功率累计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="checkbox" id="computeFlag" name="computeFlag" value="${object_gp.computeFlag!="" ? "1" : "0"}" ${object_gp.computeFlag==1 ? "checked" : ""}/>电量累计
         </td>
      </tr>
     </table>
    </div>
    <div id="485Show">
      <div class="data3"><span>通讯信息</span></div>
      <div class="data3_con">
         <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <tr>
                <td class="label"><font color="red">* </font>终端逻辑地址：</td>
                <td class="dom"><input type="text" id="logicalAddr" name="logicalAddr" value="${object_term.logicalAddr}"/></td>
                <td class="label">物理地址：</td>
                <td class="dom"><input type="text" id="physicsAddr" name="physicsAddr" value="${object_term.physicsAddr}"/></td>
              </tr>
              <tr>
                <td class="label"><font color="red">* </font>设备规约：</td>
                <td class="dom">
                  <peis:selectlist name="termProtocolNo" sql="SL_ARCHIVE_0087" />
                </td>
                <td class="label">通讯方式：</td>
                <td class="dom">
                   <peis:selectlist name="commMode" sql="SL_ARCHIVE_0011" />
                </td>
              </tr>
               <tr>
                <td class="label">当前状态：</td>
                <td class="dom">
                  <peis:selectlist name="curStatus" sql="SL_ARCHIVE_0012" />
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
</div>
</body>
</html>