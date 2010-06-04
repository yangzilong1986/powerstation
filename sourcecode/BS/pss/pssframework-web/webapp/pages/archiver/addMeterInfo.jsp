<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>专变用户档案(电表)</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
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
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.dataForAjax.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/jquery.autocomplete.js"></script>

<script type="text/javascript">
var contextPath = '<peis:contextPath/>';

$(document).ready(function(){
  $('#pulseShow').hide(); //默认不显示脉冲信息
  $('#isPulse').click(function(){
        if($('#isPulse').attr("checked")==true){
            $('#pulseShow').show();
        }else{
            $('#pulseShow').hide();
        }
    })
   initIsPulse();
   selectInit();
});
//初始化接脉冲
function initIsPulse(){
   var gpSn2=$('#gpSn2').val();
   if(gpSn2!=''){
     $('#pulseShow').show();
     $('#isPulse').attr("checked","true");
   }else{
     $('#pulseShow').hide();
   }
}
//接入终端
function addTerminal(){
   var params = {
                 "sqlCode":"AL_ARCHIVE_0040",
                 "pageRows":20,
                 "logicalAddr":""
               };
   var url=contextPath+"/archive/termListQueryInBigCust.do?action=normalMode&"+ jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
   showDialogBox("终端定位",url, 495, 800);
}
//保存电表信息
function save(){
    var url=contextPath+"/archive/meterAction.do?action=saveMeter&r=" + parseInt(Math.random() * 1000);
      if(meterCheck()){
        $("#save").attr("disabled",true);
        var data = getFormData("form");
        if(data){
            $.ajax({
                type:'post',
                url:url,
                data:data,
                dataType:'json',
                success:function(json){
                    var msg=json['msg'];
                    $("#save").attr("disabled",false);
                    if(msg=="1"){
                       alert("保存成功");
                       parent.GB_hide();
                       //top.getMainFrameObj().location.href=contextPath+"/archive/addBigCustAction.do?action=showDeviceInfoByCustID";
                       top.getMainFrameObj().loadCustRelevevance();
                    }else if(msg=="2"){
                       alert("该资产编号已经存在");
                    }else if(msg=="5"){
                       alert("该电表的所属终端中存在相同测量点序号，不允许保存");
                    }
                    else{
                       alert("保存失败");
                    }
                },
                error :function(XMLHttpRequest,textStatus,errorThrown){
                   $("#save").attr("disabled",false);
                   alert(textStatus);
                }
            });
        }
      }
}
//连接远传表
function meterState(){
   var meterState=$("#meterType").val();
   var mpId = $("input[name='mpId']").val();
   if(mpId == ''){ //新增电表
       if(meterState=="1"){
	      var url="<peis:contextPath/>/jsp/archive/remoteMeter.jsp";
	      window.location.href=url;
	   }
   }
}
//select框初始化
function selectInit(){
   $("#meterType").val(${object_meter.meterType});
   $("#measMode").val(${object_meter.measMode});
   $("#ctRatio").val(${object_meter.ctRatio});
   $("#ptRatio").val(${object_meter.ptRatio});
   $("#protocolNo").val(${object_gp1.protocolNo});
   $("#madeFactory").val('${object_meter.madeFactory}');
   $("#btl").val(${object_meter.btl});
   $("#meterDigits").val(${object_meter.meterDigits});
   $("#wiringMode").val(${object_meter.wiringMode});
   $("#ratedVolt").val(${object_meter.ratedVolt});
   $("#ratedEc").val(${object_meter.ratedEc});
   $("#termId").val('${object_gp1.termId}');
   //$("#lineId").val(${object_meter.lineId});
   lineIdComboBox.setValue('${object_meter.lineId}');
   $("#status").val(${object_meter.status});
   $("#isMas").val(${object_meter.isMas});
   $("#tranId").val('${object_gp1.tranId}');
   $("#pluseConstant").val(${object_gp2.pluseConstant});
   
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
<div id="main" style="height: 100%;">
  <div class="tab"><em>电表信息</em></div>
  <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 80));">
    <div class="main">
      <input type="hidden" name="mpId" value="${object_meter.mpId}"/>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="15%" class="label">表类型：</td>
          <td width="25%" class="dom">
            <peis:selectlist name="meterType" sql="SL_ARCHIVE_0024" onChange="meterState();"/>
          </td>
          <td width="15%" class="label">计量点名称：</td>
          <td width="25%" class="dom"><input type="text" id="mpName" name="mpName" value="${object_meter.mpName}"/></td>
        </tr>
        <tr>
          <td class="label"><font color="red">* </font>资产编号：</td>
          <td class="dom"><input type="text" id="assetNo" name="assetNo" value="${object_meter.assetNo}"/></td>
          <td class="label">计量方式：</td>
          <td class="dom">
             <peis:selectlist name="measMode" sql="SL_ARCHIVE_0030"/>
          </td>
        </tr>
        <tr>
          <td class="label">CT变比：</td>
          <td class="dom">
            <peis:selectlist name="ctRatio" sql="SL_ARCHIVE_0019"/>
          </td>
          <td class="label">PT变比：</td>
          <td class="dom">
            <peis:selectlist name="ptRatio" sql="SL_ARCHIVE_0020"/>
          </td>
        </tr>
        <tr>
          <td class="label">表规约：</td>
          <td class="dom">
             <peis:selectlist name="protocolNo" sql="SL_ARCHIVE_0022"/>
          </td>
          <td class="label"><font color="red">* </font>表地址：</td>
          <td class="dom"><input type="text" id="gpAddr" name="gpAddr" value="${object_gp1.gpAddr}"/></td>
        </tr>
        <tr>
          <td class="label">通讯端口：</td>
          <td class="dom"><input type="text" id="port" name="port" value="${object_gp1.port}"/></td>
          <td class="label">设备厂家：</td>
          <td class="dom">
           <peis:selectlist name="madeFactory" sql="SL_ARCHIVE_0008"/>
          </td>
        </tr>
        <tr>
          <td class="label">波特率：</td>
          <td class="dom">
           <peis:selectlist name="btl" sql="SL_ARCHIVE_0023"/>
          </td>
          <td class="label">翻转位数：</td>
          <td class="dom">
            <peis:selectlist name="meterDigits" sql="SL_ARCHIVE_0043"/>
          </td>
        </tr>
        <tr>
          <td class="label">相　线：</td>
          <td class="dom">
            <peis:selectlist name="wiringMode" sql="SL_ARCHIVE_0027"/>
          </td>
          <td class="label">出厂编号：</td>
          <td class="dom"><input type="text" id="leaveFacNo" name="leaveFacNo" value="${object_meter.leaveFacNo}"/></td>
        </tr>
        <tr>
          <td class="label">额定电压：</td>
          <td class="dom">
            <peis:selectlist name="ratedVolt" sql="SL_ARCHIVE_0026"/>
          </td>
          <td class="label">额定电流：</td>
          <td class="dom">
           <peis:selectlist name="ratedEc" sql="SL_ARCHIVE_0025"/>
          </td>
        </tr>
        <tr>
          <td class="label">采集终端：</td>
          <td width="20%" class="dom">
            <peis:selectlist name="termId" sql="SL_ARCHIVE_0048"/>
            <input type="button" id="addTerminal" value="..." onclick="addTerminal();" style="width: 22px; cursor: pointer;"/>
          </td>
          <td class="label">供电线路：</td>
          <td class="dom">
            <peis:selectlist name="lineId" sql="SL_ARCHIVE_0018" associate="true"/>
          </td>
        </tr>
        <tr>
          <td class="label">状　态：</td>
          <td class="dom">
            <peis:selectlist name="status" sql="SL_ARCHIVE_0044"/>
          </td>
          <td class="label">主副表：</td>
          <td>
            <peis:selectlist name="isMas" sql="SL_ARCHIVE_0045"/>
            <input type="checkbox" name="isPulse" id="isPulse" value="1"/>接脉冲
          </td>
        </tr>
      </table>
    </div>
    <div id="485Show">
      <div class="data3"><span>485信息</span></div>
      <div class="data3_con">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td align="left" class="label"><font color="red">* </font>计量点序号：</td>
            <td class="dom"><input type="text" id="gpSn1" name="gpSn1" size="8" value="${object_gp1.gpSn}"/></td>
            <td class="label">变压器：</td>
            <td class="dom">
              <peis:selectlist name="tranId" sql="SL_ARCHIVE_0047"/>
            </td>
            <td>
              <input type="checkbox" id="sucratCptId1" name="sucratCptId1" value="${object_gp1.sucratCptId!="" ? "1" : "0"}" ${object_gp1.sucratCptId==1 ? "checked" : ""}/>功率累计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <input type="checkbox" id="computeFlag1" name="computeFlag1" value="${object_gp1.computeFlag!="" ? "1" : "0"}" ${object_gp1.computeFlag==1 ? "checked" : ""}/>电量累计
            </td>
          </tr>
        </table>
      </div>
    </div>
    <div id="pulseShow">
      <div class="data3"><span>脉冲信息</span></div>
      <div class="data3_con">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td align="right"><font color="red">* </font>计量点序号：</td>
            <td align="left"><input type="text" id="gpSn2" name="gpSn2" size="8" value="${object_gp2.gpSn}"/></td>
            <td align="right">脉冲类型：</td>
            <td align="left">
              <peis:selectlist name="pluseConstant" sql="SL_ARCHIVE_0046"/>
            </td>
            <td align="right">脉冲常数：</td>
            <td align="left"><input type="text" id="meterConstant" name="meterConstant" size="8" value="${object_gp2.meterConstant}"/></td>
            <td>
              <input type="checkbox" id="sucratCptId2" name="sucratCptId2" value="${object_gp2.sucratCptId!="" ? "1" : "0"}" ${object_gp2.sucratCptId==1 ? "checked" : ""}/>功率累计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <input type="checkbox" id="computeFlag2" name="computeFlag2" value="${object_gp2.computeFlag!="" ? "1" : "0"}" ${object_gp2.computeFlag==1 ? "checked" : ""}/>电量累计
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