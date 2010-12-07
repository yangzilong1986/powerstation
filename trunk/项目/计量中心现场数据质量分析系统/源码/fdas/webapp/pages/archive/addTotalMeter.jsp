<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>总表信息</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<script type="text/javascript">
//初始化加载

//所属终端
function addTerminal(){
   var tgId=$("input[name='tgId']").val();
   var sqlCode="";
   if(tgFlag==1){
     sqlCode="AL_ARCHIVE_0050";//配变台区页面
   }else{
     sqlCode="AL_ARCHIVE_0022";//低压集中器页面
   }
   var params = {
                 "sqlCode":sqlCode,
                 "pageRows":20,
                 "objectId":tgId,
                 "logicalAddr":""
               };
   var url=contextPath+"/archive/termListQuery.do?action=normalMode&"+ jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
   showDialogBox("终端定位",url, 495, 800);
}
//保存电表信息
function save(){
    var url=contextPath+"/archive/meterAction.do?action=updateMeter";
      if(totalMeterCheck()){
        $("#save").attr("disabled",true);
        var data = jQuery("#main").dataForAjax({
            });
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
                       parent.GB_hide();
                       if(tgFlag=="1"){
                         //top.getMainFrameObj().location.href=contextPath+"/archive/addTgAction.do?action=showDeviceInfoByTgID";
                         //动态加载台区关联设备信息
						top.getMainFrameObj().loadTgRelevevance();
                       }else{
                      	 //top.getMainFrameObj().location.href=contextPath+"/archive/addLowCustAction.do?action=showDeviceInfoByTgID";
                      	 top.getMainFrameObj().loadTgRelevevance();
                       }
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
//select框初始化
function selectInit(){
    $("#ctRatio").val(${object_meter.ctRatio});
    $("#ptRatio").val(${object_meter.ptRatio});
    $("#protocolNo").val(${object_gp.protocolNo});
    $("#termId").val('${object_gp.termId}');
    $("#tranId").val('${object_gp.tranId}');
    $("#madeFactory").val('${object_meter.madeFactory}');
    $("#btl").val(${object_meter.btl});
    $("#meterType").val(${object_meter.meterType});
    $("#ratedVolt").val(${object_meter.ratedVolt});
    $("#ratedEc").val(${object_meter.ratedEc});
    $("#wiringMode").val(${object_meter.wiringMode});
    //$("#lineId").val(${object_meter.lineId});
    lineIdComboBox.setValue('${object_meter.lineId}');
    $("#modelCode").val(${object_meter.modelCode});
    $("#measMode").val(${object_meter.measMode});
    
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

//连接远传表
function meterState(){
   var meterState=$("#meterType").val();
   var mpId = $("input[name='mpId']").val();
   if(mpId == ''){ //新增电表
       if(meterState=="1"){
	      var url="<peis:contextPath/>/jsp/archive/remoteMeterTg.jsp?tgFlag="+tgFlag;
	      window.location.href=url;
	   }
   }
}
</script>
</head>
<body>
<form:form action="/archive/meterinfo" modelAttribute="meterinfo">
  <div style="height: 100%;">
  <div>
  <div id="485Show">
  <div class="data3"><span>重要信息</span></div>
  <div class="data3_con">
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
      <td width="13%" class="green"><font color="red">* </font>序 号：</td>
      <td width="20%"><form:input path="meterinfo.meterMpRelaInfo.mpInfo.gpInfo.gpSn"/></td>
      <td width="13%" class="green"><font color="red">* </font>计量点名称：</td>
      <td width="20%"><form:input path="meterinfo.meterMpRelaInfo.mpInfo.mpName"/></td>
      <td width="13%" class="green"><font color="red">* </font>计量点编号：</td>
      <td width="20%"><form:input path="meterinfo.meterMpRelaInfo.mpInfo.mpNo"/></td>
    </tr>
    <tr>
      <td class="green">CT变比：</td>
      <td><form:select path="meterinfo.meterMpRelaInfo.mpInfo.gpInfo.ctTimes" items="${ctList}" id="ctTimes" itemLabel="name" itemValue="code"
          cssStyle="width:155px;"/></td>
      <td class="green">PT变比：</td>
      <td><form:select path="meterinfo.meterMpRelaInfo.mpInfo.gpInfo.ptTimes" items="${ptList}" id="ptTimes" itemLabel="name" itemValue="code"
          cssStyle="width:155px;"/></td>
      <td class="green"><font color="red">* </font>表 地 址：</td>
      <td><form:input path="meterinfo.meterMpRelaInfo.mpInfo.gpInfo.gpAddr"/></td>
    </tr>
    <tr>
      <td class="green">表 规 约：</td>
      <td><form:select path="commNo" /></td>
      <td class="green">所属终端：</td>
      <td><form:select path="meterinfo.meterMpRelaInfo.mpInfo.gpInfo.termId" items="${termList}" id="termId" itemLabel="logicalAddr"
          itemValue="termId" cssStyle="width:155px;" /> <input type="button" value="..." onclick="addTerminal();"
        style="width: 20px" /></td>
    </tr>
  </table>
  </div>
  </div>
  <div id="pulseShow">
  <div class="data3"><span>一般信息</span></div>
  <div class="data3_con">
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
      <td width="13%" class="green">安装位置：</td>
      <td width="20%"><form:input path="meterinfo.meterMpRelaInfo.mpInfo.mpAddr"></form:input></td>
      <td width="13%" class="green">出厂编号：</td>
      <td width="20%"><input type="text" name="leaveFacNo" id="leaveFacNo" value="${object_meter.leaveFacNo}" /></td>
      <td width="13%" class="green">波 特 率：</td>
      <td width="20%"><form:select path="baudrate" items="${btlList}" id="btl" itemLabel="name" itemValue="code"
          cssStyle="width:155px;" /></td>
    </tr>
    
    <tr>
      <td class="green">接线方式：</td>
      <td><form:select path="meterinfo.meterMpRelaInfo.mpInfo.wiringMode" items="${wiringModeList}" id="wiringMode" itemLabel="name" itemValue="code"
          cssStyle="width:155px;" /></td>
      <td class="green">供电线路：</td>
      <td><form:select path="lineId" /></td>
      <td class="green">表计型号：</td>
      <td><form:select path="modelCode" /></td>
    </tr>
    <tr>
      <td class="green">端 口 号：</td>
      <td><input type="text" id="port" name="port" value="${object_gp.port}" /></td>
      <td class="green">位 数：</td>
      <td><input type="text" id="meterDigits" name="meterDigits" value="${object_meter.meterDigits}" /></td>
    </tr>
    <tr>
      <td class="green">计量方式：</td>
      <td><form:select path="measMode" /></td>
      <td class="green">安装日期：</td>
      <td class="dom_date"><form:select path="offDelayValue" items="${offDelayValueList}" id="offDelayValue"
        itemLabel="name" itemValue="code" cssStyle="width:155px;" /></td>
      <td width="34%" colspan="2" align="center"><input type="checkbox" id="sucratCptId" name="sucratCptId"
        value="${object_gp.sucratCptId!=" " ? "1" : "0"}" ${object_gp.sucratCptId==1?"checked" : ""}>功率累计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      </td>
    </tr>
  </table>
  </div>
  </div>
  </div>
  <div class="guidePanel"><input class="input1" type="button" id="save" value="保 存" onclick="save();" /></div>
  </div>
</form:form>
</body>
</html>