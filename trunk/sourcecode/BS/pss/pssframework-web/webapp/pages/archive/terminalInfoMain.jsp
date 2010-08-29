<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>专变用户档案(终端)</title>
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
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
  <script type="text/javascript">
  var contextPath = '<peis:contextPath/>';
  
$(document).ready(function(){
  $('#ACShow').hide(); //默认不显示交采信息

  $('#totalGPShow').hide(); //默认不显示总加组信息

  if($("input[name='termId']").val()==''){
    loadParamTblTbody();
  }
  selectInit();
  $('#isac').click(function(){
        if($('#isac').attr("checked")==true){
            $('#ACShow').show();
            $("#agRelaTd").append("<span class='ac'>交采</span>");
        }else{
            $('#ACShow').hide();
            $(".ac").remove();
        }
    })
  $('#isTotalGP').click(function(){
        if($('#isTotalGP').attr("checked")==true){
            $('#totalGPShow').show();
        }else{
            $('#totalGPShow').hide();
        }
    })
     if($('#isac').attr("checked")==true){
         $('#ACShow').show();
         $("#agRelaTd").append("<span class='ac'>交采</span>");
     }else{
         $('#ACShow').hide();
         $(".ac").remove();
     }
    if($('#isTotalGP').attr("checked")==true){
          $('#totalGPShow').show();
      }else{
          $('#totalGPShow').hide();
      }
    $('#meterTable input[type=checkbox]').click(function(){
        var mpId=$(this).val();
        if($(this).attr("checked")==true){
           var url=contextPath+"/archive/terminalAction2.do?action=getGpSnByMpId&mpId="+mpId;
           $.ajax({ //加载总加组中测量点序号

             url: url,
             cache: false,
             success: function(html){
               $("#agRelaTd").append(html);
             }
           });
        }else{
           var mpCheckBoxId="."+mpId;
           $(mpCheckBoxId).remove();
        }
    });
    $("#protocolNo").change(loadParamTblTbody);
});

//总加组下测量点序号验证(保证每个测量点序号都不相同，包括交采)
function gpSnCheck(sn){
   var gpSnTotal=$(sn).val();
   var gpSn=$('#gpSn').val(); //交采测量点

   var checkedSn=$("input[name='gpSnTotal'][checked=true]").val();
   if($(sn).attr("checked")==true){
      if(gpSnTotal==gpSn){
         alert("不允许勾选与交采相同的测量点序号");
         $(sn).attr("checked",false);
      }
      if($("input[name='gpSnTotal'][checked=true]").length>1){
         if(gpSnTotal==checkedSn){
           alert("不允许勾选与相同的测量点序号");
           $(sn).attr("checked",false);
         }
      }
   }
}

//保存
function save(){
    formSubmit();
}
 //终端参数加载
  function loadParamTblTbody() {
    var protocolNo = $("#protocolNo").val();
    var commMode = $("#commMode").val();
    var termType = $("#termType").val();
    var params = {"action": "loadParamTblTbodyByAjax",
                  "protocolNo": protocolNo,
                  "commMode": commMode,
                  "termType": termType};
    var url = contextPath + "/archive/terminalAction1.do";
    $.ajax({
        type: "POST",
        url: url,
        cache: false,
        data: params,
        success: function(data) {
            $("#paramTblTbody").html(data);
        }
    });
}
//提交表单
function formSubmit(){
  var url=contextPath+"/archive/terminalAction2.do?action=saveOrUpdateTermInTg";
      if(terminalCheck()){
       $("#save").attr("disabled",true);
        var data = jQuery("#form").dataForAjax({
            });
        if(data){
            jQuery.ajax({
                type:'post',
                url:url,
                data:data,
                dataType:'json',
                success:function(json){
                    var msg=json['msg'];
                    var termId=json['termId'];
                    $("#save").attr("disabled",false);
                    if(msg=="1"){
                       alert("保存成功");
                       $("input[name='termId']").val(termId);
                       //top.getMainFrameObj().location.href=contextPath+"/archive/addBigCustAction.do?action=showDeviceInfoByCustID";
                       //top.getMainFrameObj().loadCustRelevevance();
                       top.getMainFrameObj().treeframe.location.reload();
                    }else if(msg=="2"){
                       alert("该资产编号已经存在");
                    }else if(msg=="3"){
                       alert("该逻辑地址已经存在");
                    }else if(msg=="5"){
                       alert("电表中存在的测量点序号相同,或者电表中测量点序号与交采相同,请重新勾选");
                    }
                    else{
                       alert("保存失败");
                    }
                }
            });
        }
      }
}
 //删除终端
   function deleteTerm(termId){  
     var url=contextPath+"/archive/terminalAction1.do?action=deleteTerminalInBigCust&termId="+termId;
     if(confirm("确定要删除该终端?")){
          $.ajax({
            url: url,
            dataType:'json',
            cache: false,
            success: function(json){
              var msg=json['msg'];
               if(msg=="1"){
                alert("删除成功");
                //window.location.href=contextPath+"/archive/addBigCustAction.do?action=showDeviceInfoByCustID";
                top.getMainFrameObj().location.reload();
                }else if(msg=="3"){
                   alert("该终端下存在电表不允许删除");
                }
                else{
                   alert("删除失败");
                }
            }
          });
     }
   }
//select框初始化
function selectInit(){
  $("#curStatus").val(${object_term.curStatus});
  $("#protocolNo").val(${object_term.protocolNo});
  $("#commMode").val(${object_term.commMode});
  $("#wiringMode").val(${object_term.wiringMode});
  $("#madeFac").val('${object_term.madeFac}');
  $("#termType").val('${object_term.termType}');
  $("#modelCode").val(${object_term.modelCode});
  $("#pr").val(${object_term.pr});
  $("#lineId").val(${object_gp.lineId});
  //lineIdComboBox.setValue('${object_gp.lineId}');
  $("#tranId").val('${object_gp.tranId}');
  $("#ctRatio").val(${object_ct});
  $("#ptRatio").val(${object_pt});
  $("#machNo").val('${object_term.machNo}');
  $("#channelType").val('${object_term.channelType}');
}
</script>
 </head>
 <body style="overflow: hidden;">
  <div id="form">
   <div id="main" style="height: 100%;">
    <div class="tab">
     <em>终端信息</em>
    </div>
    <div class="tab_con" style="height: expression(((           document.documentElement.clientHeight ||           document.body.clientHeight) -             25 ) );">
     <div class="main">
      <!-- 该终端所属的用户列表 -->
      <input type="hidden" name="termObjectIds" value="${object_term_objectIds}" />
      <!-- 该终端ID -->
      <input type="hidden" name="termId" value="${object_term.termId}" />
      <table border="0" cellpadding="0" cellspacing="0" width="900" align="center">
       <tr>
        <td width="13%" class="label">
         <font color="red">* </font>资产编号：

        </td>
        <td width="20%" class="dom">
         <input type="text" name="assetNo" id="assetNo" value="${object_term.assetNo}" />
        </td>
        <td width="13%" class="label">
         <font color="red">* </font>逻辑地址：

        </td>
        <td width="20%" class="dom">
         <input type="text" name="logicalAddr" id="logicalAddr" value="${object_term.logicalAddr}" />
        </td>
        <td colspan="2"></td>
       </tr>
       <tr>
        <td width="13%" class="label">
         当前状态：
        </td>
        <td width="20%" class="dom">
         <peis:selectlist name="curStatus" sql="SL_ARCHIVE_0012" />
        </td>
        <td width="13%" class="label">
         设备规约：

        </td>
        <td width="20%" class="dom">
         <peis:selectlist name="protocolNo" sql="SL_ARCHIVE_0055" />
        </td>
        <td width="13%" class="label">
         通讯方式：

        </td>
        <td width="21%" class="dom">
         <peis:selectlist name="commMode" sql="SL_ARCHIVE_0011" />
        </td>
       </tr>
       <tr>
        <td class="label">
         相 线：
        </td>
        <td class="dom">
         <peis:selectlist name="wiringMode" sql="SL_ARCHIVE_0013" />
        </td>
        <td class="label">
         设备厂家：

        </td>
        <td class="dom">
         <peis:selectlist name="madeFac" sql="SL_ARCHIVE_0008" />
        </td>
        <td class="label">
         终端类型：

        </td>
        <td width="24%" class="dom">
         <peis:selectlist name="termType" sql="SL_ARCHIVE_0054" />
        </td>
       </tr>
       <tr>
       <td class="label">
        机器编号：

        </td>
        <td class="dom">
         <peis:selectlist name="machNo" sql="SL_ARCHIVE_0063" extendProperty="disabled='disabled'"/>
        </td>
        <td class="label">
         前置机通道：

        </td>
        <td class="dom">
         <input type="text" name="fepCnl" id="fepCnl"  value="${object_term.fepCnl}" disabled="disabled"/>
        </td>
        <td class="label">
         通道类型：

        </td>
        <td width="24%" class="dom">
         <peis:selectlist name="channelType" sql="SL_ARCHIVE_0064" />
        </td>
       </tr>
       <tr>
        <td class="label">
         终端型号：

        </td>
        <td class="dom">
         <peis:selectlist name="modelCode" sql="SL_ARCHIVE_0017" />
        </td>
        <td class="label">
         入库日期：

        </td>
        <td class="dom_date">
         <input type="text" name="leaveFacDate" id="leaveFacDate" value="<fmt:formatDate value="${object_term.leaveFacDate}" type="date"/>" onfocus="peisDatePicker()" readonly="readonly" />
        </td>
        <td class="label">
         产 权：
        </td>
        <td width="24%" class="dom">
         <peis:selectlist name="pr" sql="SL_ARCHIVE_0016" />
        </td>
       </tr>
       <tr>
        <td class="label">
         安装日期：

        </td>
        <td class="dom_date">
         <input type="text" id="installDate" name="installDate" value="<fmt:formatDate value="${object_term.installDate}" type="date"/>" onfocus="peisDatePicker()" readonly="readonly" />
        </td>
        <td class="label">
                             物理地址：

        </td>
        <td class="dom">
         <input type="text" id="physicsAddr" name="physicsAddr" value="${object_term.physicsAddr}" />
        </td>
        <td class="label">
           SIM卡号：

        </td>
        <td class="dom">
          <input type="text" id="simNo" name="simNo" value="${object_term.simNo}" />
        </td>
       </tr>
       <tr>
        <td colspan="2" align="center">
         <input type="checkbox" name="isac" id="isac" value="${object_term.isac!=" " ? "1" : "0"}" ${object_term.isac==1?"checked" : ""}>
         接交采

        </td>
         <td class="label">
         安装单位：

        </td>
        <td colspan="3" class="dom2">
         <input type="text" id="constrGang" name="constrGang" value="${object_term.constrGang}" />
        </td>
       </tr>
      </table>
     </div>
     <div id="ACShow">
      <div class="data3">
       <span>交采信息</span>
      </div>
      <div class="data3_con">
       <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
         <td width="13%" class="label">
          计量点序号：
         </td>
         <td width="10%" class="dom">
          <input type="text" id="gpSn" name="gpSn" value="0" disabled="disabled"/>
         </td>
         <td width="10%" class="label">
          供电线路：

         </td>
         <td width="15%" class="dom">
          <peis:selectlist name="lineId" sql="SL_ARCHIVE_0018"/>
         </td>
         <td width="44%" colspan="2" align="center">
          <input type="checkbox" id="sucratCptId" name="sucratCptId" value="${object_gp.sucratCptId!=" " ? "1" : "0"}" ${object_gp.sucratCptId==1? "checked" : ""}/>
          功率累计&nbsp;&nbsp;&nbsp;
          <input type="checkbox" id="computeFlag" name="computeFlag" value="${object_gp.computeFlag!=" " ? "1" : "0"}" ${object_gp.computeFlag==1 ? "checked" : ""}/>
          电量累计
          变压器：
          <peis:selectlist name="tranId" sql="SL_ARCHIVE_0021" />
         </td>
        </tr>
        <tr>
         <td class="label">
          端 口 号：
         </td>
         <td class="dom">
          <input type="text" id="port" name="port" value="${object_gp.port}" />
         </td>
         <td class="label">
          CT变比：

         </td>
         <td  class="dom">
          <peis:selectlist name="ctRatio" sql="SL_ARCHIVE_0019" />
         </td>
         <td  class="label">
          PT变比：

         </td>
         <td class="dom">
          <peis:selectlist name="ptRatio" sql="SL_ARCHIVE_0020" />
         </td>
        </tr>
       </table>
      </div>
     </div>
     <div id="assInfoShow" style="display: none;">
      <div class="data3">
       <span>关联信息</span>
      </div>
      <div class="data3_con">
       <table border="0" cellpadding="0" cellspacing="0" width="100%" id="meterTable">
        <tr>
         <td align="right" class="lable">
          电表：

         </td>
         <logic:present name="object_meter">
          <logic:iterate id="meter" name="object_meter">
           <td>
            <input type="checkbox"  <c:if test="${object_term.logicalAddr==meter.col4 &&  meter.col4!=''}">checked</c:if>  id="mpIdList" name="mpIdList" value="<bean:write name="meter" property="col8"/>" />
            <bean:write name="meter" property="col2" />
            /
            <bean:write name="meter" property="col1" />
           </td>
          </logic:iterate>
         </logic:present>
        </tr>
       </table>
       <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td align="right" class="lable">
                      开关：
          </td>
          <logic:present name="object_term_switch">
           <logic:iterate id="term_switch" name="object_term_switch">
            <td>
             <input type="checkbox" <c:if test="${object_term.logicalAddr==term_switch.col3 &&  term_switch.col3!=''}">checked</c:if>  id="termSwIdList" name="termSwIdList" value="<bean:write name="term_switch" property="col8"/>" />
             开关名称：<bean:write name="term_switch" property="col2" />
             /
             开关序号：<bean:write name="term_switch" property="col1" />
            </td>
           </logic:iterate>
          </logic:present>
         </tr>
       </table>
      </div>
     </div>
     <div id="totalGPShow">
      <div class="data2">
       <span>总加组信息</span>
      </div>
      <div class="data2_con">
       <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <thead>
         <tr>
          <th>
           总加组序号

          </th>
          <th>
           测量点名称

          </th>
         </tr>
        </thead>
        <tbody id="agRelaTbody">
         <tr>
          <td align="center">
           1
          </td>
          <td id="agRelaTd">
            <logic:present name="object_agRela">
              <bean:write name="object_agRela" ignore="true" filter="false"/>
            </logic:present>
          </td>
         </tr>
        </tbody>
       </table>
      </div>
     </div>
     <div id="paramShow">
      <div class="data2">
       <span>终端参数</span>
      </div>
      <div class="data2_con">
       <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <thead>
         <tr>
          <th>
           序号
          </th>
          <th>
           终端参数
          </th>
          <th>
           参数值

          </th>
         </tr>
        </thead>
        <tbody id="paramTblTbody">
         <logic:present name="termParas">
         <logic:iterate id="para" name="termParas">
          <tr>
           <td>
            <bean:write name="para" property="rowNo" />
           </td>
           <td>
            <bean:write name="para" property="col1" />
           </td>
           <td>
            <input type="text" id="paraValue" name="paraValue" value="<bean:write name='para' property='col3'/>" />
            <input type="hidden" id="paraCode" name="paraCode" value="<bean:write name='para' property='col2'/>" />
            <input type="hidden" id="commanditemCode" name="commanditemCode" value="<bean:write name='para' property='col4'/>" />
            <input type="hidden" id="dataitemCode" name="dataitemCode" value="<bean:write name='para' property='col5'/>" />
           </td>
          </tr>
         </logic:iterate>
        </logic:present>
        </tbody>
       </table>
      </div>
     </div>
     <table border="0" cellpadding="0" cellspacing="0" width="100%">
    <tr>
     <td align="right">
      <input class="input2" type="button" id="c_button" value="保存终端" onClick="save();"/>
      <input class="input2" type="button" id="delete" value="删除终端" onClick="deleteTerm('${object_term.termId}');" />
     </td>
    </tr>
    </table>
    </div>
   </div>
  </div>
 </body>
</html>
