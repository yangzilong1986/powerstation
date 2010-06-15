<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>漏保信息</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<script type="text/javascript">
var contextPath = '<pss:contextPath/>';


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
	      var url="<pss:contextPath/>/jsp/archive/remoteMeter.jsp";
	      window.location.href=url;
	   }
   }
}

</script>
</head>
<body style="overflow: hidden;">
<div id="form">
<div id="main" style="height: 100%;">
<div class="tab"><em>漏点保护器</em></div>
<div class="tab_con"
  style="height: expression(((                 document.documentElement.clientHeight ||                 document.body.clientHeight) -                   80 ) );">
<div class="main"> <form:form
  action="/archive/psinfo" modelAttribute="psinfo">
  <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
      <td width="15%" class="label">资产编号：</td>
      <td width="25%" class="dom"></td>
      <td width="15%" class="label">漏保地址：</td>
      <td width="25%" class="dom"></td>
    </tr>
    <tr>
      <td class="label"><font color="red">* </font>通信方式：</td>
      <td class="dom"></td>
      <td class="label">型号：</td>
      <td class="dom"></td>
    </tr>
    <tr>
      <td class="label">波特率：</td>
      <td class="dom"><input type="text" id="port" name="port" value="${object_gp1.port}" /></td>
      <td class="label">额定电流：</td>
      <td class="dom"></td>
    </tr>
    <tr>
      <td class="label">剩余电流档位：</td>
      <td class="dom"></td>
      <td class="label">剩余电流档位当前值：</td>
      <td class="dom"></td>
    </tr>
    <tr>
      <td class="label">漏电分断延迟档位：</td>
      <td class="dom"></td>
      <td class="label">漏电分断延迟时间：</td>
      <td class="dom"></td>
    </tr>
    <tr>
      <td class="label">功能设置字：</td>
      <td class="dom"></td>
      <td class="label">漏保类型：</td>
      <td class="dom"></td>
    </tr>
  </table>
</form:form></div>
<div class="guidePanel"><input class="input1" type="button" id="save" value="保 存" onclick="save();" /></div>
</div>
</div>
</div>
</body>
</html>