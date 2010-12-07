<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>专变用户档案(开关)</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/greybox.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.dataForAjax.js"></script>
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';

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
//保存
function save(){
     var url=contextPath+"/archive/switchAction.do?action=saveOrUpdateSwitch";
      if(switchCheck()){
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
                       parent.GB_hide();
                       //top.getMainFrameObj().location.href=contextPath+"/archive/addBigCustAction.do?action=showDeviceInfoByCustID";
                      top.getMainFrameObj().loadCustRelevevance();
                    }else if(msg=="2"){
                       alert("同一客户下开关序号唯一");
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
  <div class="tab"><em>开关信息</em></div>
  <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 80));">
    <div class="main">
      <div id="form">
      <input type="hidden" name="termSwId" value="${object_term_switch.termSwId}"/>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
       <tr>
        <td width="15%" class="label">开关序号：</td>
        <td width="25%" class="dom">
         <select name="swSn" id="swSn">
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
          <option value="6">6</option>
          <option value="7">7</option>
          <option value="8">8</option>
         </select>
         <script type="text/javascript">
           $("#swSn").val(${object_term_switch.swSn});
         </script>
        </td>
        <td colspan="2"></td>
       </tr>
       <tr>
        <td width="15%" class="label">采集终端：</td>
        <td width="25%" class="dom">
          <peis:selectlist name="termId" sql="SL_ARCHIVE_0048"/>
          <script type="text/javascript">
             $("#termId").val('${object_term_switch.termId}');
          </script>
         <input type="button" id="addTerminal" value="..." onclick="addTerminal();" style="width: 22px; cursor: pointer;"/>
        </td>
        <td width="15%" class="label">开关名称：</td>
        <td width="25%" class="dom">
         <input name="swName" id="swName" type="text" value="${object_term_switch.swName}"/>
        </td>
       </tr>
       <tr>
        <td class="label">开关轮次：</td>
        <td class="dom">
         <select name="swTurn" id="swTurn">
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
          <option value="6">6</option>
          <option value="7">7</option>
          <option value="8">8</option>
         </select>
          <script type="text/javascript">
             $("#swTurn").val(${object_term_switch.swTurn});
          </script>
        </td>
        <td class="label">开关容量：</td>
        <td class="dom">
         <input type="text" name="capacity" style="width: 115px;" value="<fmt:formatNumber value="${object_term_switch.capacity}" pattern="#0.0000#"  minFractionDigits="0"/>"/> kVA
        </td>
       </tr>
       <tr>
        <td class="label">开关属性：</td>
        <td class="dom">
          <peis:selectlist name="swAttr" sql="SL_ARCHIVE_0052"/>
          <script type="text/javascript">
             $("#swAttr").val(${object_term_switch.swAttr});
          </script>
        </td>
        <td class="label">开关状态：</td>
        <td class="dom">
          <peis:selectlist name="swStatus" sql="SL_ARCHIVE_0053"/>
          <script type="text/javascript">
             $("#swStatus").val(${object_term_switch.swStatus});
          </script>
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