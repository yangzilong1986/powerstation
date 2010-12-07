<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>配变用户档案(电表)</title>
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
<script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.dataForAjax.js"></script>
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';
var orgNo=$.url.param("orgNo");

//初始化加载
$(function(){
   $("input[name='orgNo']").val(orgNo);
})
//所属终端
function addTerminal(){
   var tgId=$("input[name='tgId']").val();
   var params = {
                 "sqlCode":"AL_ARCHIVE_0021",
                 "pageRows":20,
                 "objectId":tgId
               };
   var url=contextPath+"/archive/termListQuery.do?action=normalMode&"+ jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
   showDialogBox("终端定位",url, 495, 800);
}
//保存电表信息
function save(){
   var url=contextPath+"/archive/meterAction.do?action=updateMeter";
      if(totalMeterCheck()){
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
                       //top.getMainFrameObj().location.href=contextPath+"/archive/addLowCustAction.do?action=showDeviceInfoByTgID";
                       top.getMainFrameObj().loadTgRelevevance();
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
    <div id="form">
    <input type="hidden" name="orgNo" value="${orgNo}" />
    <input type="hidden" name="tgId" value="${tgId}"/>
    <input type="hidden" name="mpId" value="${mpId}"/>
    <input type="hidden" name="gpId" value="${gpId}"/>
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
             <peis:selectlist name="meterType" sql="SL_ARCHIVE_0024"/>
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
              <peis:selectlist name="wiringMode" sql="SL_ARCHIVE_0027"/>
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
              <input type="text" name="mpDate" id="mpDate" value="<fmt:formatDate value="${object_meter.mpDate}" type="date"/>" onfocus="peisDatePicker()" readonly="readonly" />
             </td>
             <td width="34%"  colspan="2" align="center">
              <input type="checkbox" id="sucratCptId" name="sucratCptId" value="${object_gp.sucratCptId!="" ? "1" : "0"}" ${object_gp.sucratCptId==1 ? "checked" : ""}>功率累计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <input type="checkbox" id="computeFlag" name="computeFlag" value="${object_gp.computeFlag!="" ? "1" : "0"}" ${object_gp.computeFlag==1 ? "checked" : ""}/>电量累计
            </td>
           </tr>
          </table>
      </div>
    </div>
    <div class="guidePanel">
      <input class="input1" type="button" value="保 存" onclick="save();" />
    </div>
    </div>
  </div>
</body>
</html>