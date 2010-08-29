<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
 <head>
  <title><bean:message bundle="archive" key="archive.new.meter" /></title>
  <meta http-equiv="Content-Language" content="zh-cn">
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
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
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.dataForAjax.js"></script>
  <script type="text/javascript">
  var contextPath = '<peis:contextPath/>';
  var tgId=$.url.param("tgId");
  var orgNo=$.url.param("orgNo");
  var flag=0;
  $(document).ready(function(){
    selectInit();
    protocolIsac();
    jQuery("#c_button").click(function(){
      submitByAjax();
    });
  $("#deviceLable").hide();
  $("#deviceDom").hide();
  if($("input[name='termId']").val()==''){
    loadParamTblTbody();
  }
  //交采事件注册
  $('#isac').click(function(){
        if($('#isac').attr("checked")==true){
            $('#ACShow').show();
        }else{
            $('#ACShow').hide();
        }
    })
    if($('#isac').attr("checked")==true){
            $('#ACShow').show();
        }else{
            $('#ACShow').hide();
        }
     showDevice();
     $("#protocolNo").change(loadParamTblTbody);
  });
   //显示上级设备
  function showDevice(){
    if($("#commPattern").val()==1){
       $("#deviceLable").hide();
       $("#deviceDom").hide();
    }
    if($("#commPattern").val()==2){
       $("#deviceLable").show();
       $("#deviceDom").show();
       $("#deviceLable").html("上级设备：");
    }
  }
   //接入终端
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

//保存集中器信息，并跳转到采集器页面






  function saveAndShowCollect(){
     ///if(flag==1){
     //   submitAndForwardByAjax();
     //}else{
     //  alert("请先保存集中器信息");
     //}
     var termId=$("input[name='termId']").val();
     if(termId==null || termId==''){
       alert("请先保存集中器信息");
     }else{
       submitAndForwardByAjax();
     }
  }

//AJAX提交表单
   function submitByAjax(){
      var url=contextPath+"/archive/terminalAction1.do?action=addTermInfoSecond";
      if(modelTermCheck()){
        $("#c_button").attr("disabled",true);
        var data = getFormData("#form");
        if(data){
            jQuery.ajax({
                type:'post',
                url:url,
                data:data,
                dataType:'json',
                success:function(json){
                    var msg=json['msg'];
                    var termId=json['termId'];
                    var objRelId=json['objRelId'];
                    $("#c_button").attr("disabled",false);
                    if(msg=="1"){
                       alert("保存成功");
                       $("input[name='termId']").val(termId);
                       $("input[name='objRelId']").val(objRelId);
                       //flag=1;
                       if(confirm("是否新增采集器")==true){
                         submitAndForwardByAjax();
                       }else{
                         //url=contextPath+"/archive/terminalAction1.do?action=clearTermSession";
                         //top.getMainFrameObj().location.href=url;
                         top.getMainFrameObj().loadTgRelevevance();
                         parent.parent.GB_hide();
                       }
                    }else if(msg=="2"){
                       alert("该资产编号已经存在");
                    }else if(msg=="3"){
                       alert("该逻辑地址已经存在");
                    }else{
                       alert("保存失败");
                    }
                }
            });
        }
      }
   }
    //AJAX提交表单，并跳转到采集器
   function submitAndForwardByAjax(){
     var url=contextPath+"/archive/terminalAction1.do?action=addTermInfoSecond";
        if(modelTermCheck()){
        var data = getFormData("#form");
        if(data){
            jQuery.ajax({
                type:'post',
                url:url,
                data:data,
                dataType:'json',
                success:function(json){
                    var termId=json['termId'];
                    var msg=json['msg'];
                    var tgId=$("input[name='tgId']").val();
                      if(msg=="1"){
                        window.location.href=contextPath+"/archive/gmListQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0023&termId="+termId+"&pageRows=20";
                      }else if(msg=="2"){
                         alert("该资产编号已经存在");
                      }else if(msg=="3"){
                         alert("该逻辑地址已经存在");
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
       $("#curStatus").val(${object_term.curStatus});
       $("#commMode").val(${object_term.commMode});
       $("#wiringMode").val(${object_term.wiringMode});
       $("#madeFac").val('${object_term.madeFac}');
       $("#termType").val('${object_term.termType}');
       $("#tranId").val('${object_gp.tranId}');
       $("#pr").val(${object_term.pr});
       $("#modelCode").val(${object_term.modelCode});
       $("#lineId").val(${object_gp.lineId});
       //lineIdComboBox.setValue('${object_gp.lineId}');
       $("#commPattern").val(${object_term.commPattern});
       $("#termIdCasc").val(${object_termCascade.termIdCasc});
       $("#ctRatio").val(${object_ct});
       $("#ptRatio").val(${object_pt});
       $("#machNo").val('${object_term.machNo}');
       $("#channelType").val('${object_term.channelType}');
       $("#protocolNo").val('${object_term.protocolNo}');
   }
   //如果是广东集抄规约，是否接交采功能屏蔽






   function protocolIsac(){
      var protocolNo=$('#protocolNo').val();
      if(protocolNo=="126"){ //广东集抄规约
         $('#isac').attr("disabled","true");
      }
   }
</script>
 </head>
 <body>
  <div class="tab_con" style="height: expression(((         document.documentElement.clientHeight ||         document.body.clientHeight) -   85 ) );">
   <div id="form">
    <input type="hidden" name="action" value="addTerm" />
    <input type="hidden" name="tgId" value="${tgId}" />
    <input type="hidden" name="orgNo" value="${orgNo}" />
    <input type="hidden" name="termId" value="${object_term.termId}" />
    <div class="main">
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
       <td class="label">
        当前状态：
       </td>
       <td class="dom">
        <peis:selectlist name="curStatus" sql="SL_ARCHIVE_0012" />
       </td>
       <td class="label">
        设备规约：






       </td>
       <td class="dom">
        <peis:selectlist name="protocolNo" sql="SL_ARCHIVE_0072" />
       </td>
       <td class="label">
        通讯方式：






       </td>
       <td class="dom">
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
       <td class="dom">
        <peis:selectlist name="termType" sql="SL_ARCHIVE_0014" />
       </td>
      </tr>
      <tr>
     <td class="label">
        机器编号：






       </td>
       <td class="dom">
        <peis:selectlist name="machNo" sql="SL_ARCHIVE_0063" extendProperty="disabled='disabled'" />
       </td>
       <td class="label">
        前置机通道：






       </td>
       <td class="dom">
        <input type="text" name="fepCnl" id="fepCnl" value="${object_term.fepCnl}" disabled="disabled"/>
       </td> 
        <td class="label">
        通道类型：






       </td>
       <td class="dom">
        <peis:selectlist name="channelType" sql="SL_ARCHIVE_0064" />
       </td>
      </tr>
      <tr>
       <td class="label">
        产 权：
       </td>
       <td class="dom">
        <peis:selectlist name="pr" sql="SL_ARCHIVE_0016" />
       </td>
       <td class="label">
        安装日期：






       </td>
       <td class="dom_date">
        <input type="text" id="installDate" name="installDate" value="<fmt:formatDate value="${object_term.installDate}" type="date"/>" onfocus="peisDatePicker()" readonly="readonly" />
       </td>
       <td class="label">
        入库日期：






       </td>
       <td class="dom_date">
        <input type="text" name="leaveFacDate" id="leaveFacDate" value="<fmt:formatDate value="${object_term.leaveFacDate}" type="date"/>" onfocus="peisDatePicker()" readonly="readonly" />
       </td>
      </tr>
      <tr>
       <td class="label">
        安装单位：






       </td>
       <td class="dom">
        <input type="text" id="constrGang" name="constrGang" value="${object_term.constrGang}" />
       </td>
       <td class="label">
        终端型号：

<<<<<<< .mine





=======
      </td>
       <td class="dom">
        <peis:selectlist name="modelCode" sql="SL_ARCHIVE_0017" />
>>>>>>> .r1071
       </td>
       <td  class="label">SIM卡号：</td>
       <td  class="dom">
            <input type="text" id="simNo" name="simNo" value="${object_term.simNo}" />
       </td>
      </tr>
      <tr>
       <td colspan="2" align="center">
        <input type="checkbox" name="isac" id="isac" value="${object_term.isac!=" " ? "1" : "0"}" ${object_term.isac==1?"checked" : ""}>
        接交采






       </td>
       <td class="label">
        物理地址：

<<<<<<< .mine

       </td>
       <td class="dom">
        <peis:selectlist name="modelCode" sql="SL_ARCHIVE_0017" />
=======
>>>>>>> .r1071
       </td>
       <td colspan="3" class="dom">
        <input type="text" id="physicsAddr" name="physicsAddr" style="width: 390px" value="${object_term.physicsAddr}" />
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
         <input type="text" id="gpSn" name="gpSn" value="${object_gp.gpSn}" />
        </td>
        <td width="10%" class="label">
         供电线路：






        </td>
        <td width="15%" class="dom">
         <peis:selectlist name="lineId" sql="SL_ARCHIVE_0018"/>
        </td>
        <td width="44%" colspan="2" align="center">
         <input type="checkbox" id="sucratCptId" name="sucratCptId" value="${object_gp.sucratCptId!=" " ? "1" : "0"}" ${object_gp.sucratCptId==1?"checked" : ""}>
         功率累计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <input type="checkbox" id="computeFlag" name="computeFlag" value="${object_gp.computeFlag!=" " ? "1" : "0"}" ${object_gp.computeFlag==1 ? "checked" : ""}/>
         电量累计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 变压器：
         <peis:selectlist name="tranId" sql="SL_ARCHIVE_0021" />
        </td>
       </tr>
       <tr>
        <td class="label">
         CT变比：






        </td>
        <td class="dom">
         <peis:selectlist name="ctRatio" sql="SL_ARCHIVE_0019" />
        </td>
        <td class="label">
         PT变比：






        </td>
        <td class="dom">
         <peis:selectlist name="ptRatio" sql="SL_ARCHIVE_0020" />
        </td>
        <td class="label">
         端口号：
        </td>
        <td class="dom">
         <input type="text" id="port" name="port" value="${object_gp.port}" />
        </td>
       </tr>
      </table>
     </div>
    </div>
    <div class="data3">
     <span>级联信息</span>
    </div>
    <div class="data3_con">
     <table border="0" cellpadding="0" cellspacing="0" width="100%">
      <tr>
       <td width="20%" class="label">
        通信模式：






       </td>
       <td width="30%" class="dom">
        <select name="commPattern" id="commPattern" onChange="showDevice();">
         <option value="1">
          主






         </option>
         <option value="2">
          从






         </option>
        </select>
       </td>
       <td width="20%" class="label" id="deviceLable">
        下级设备：






       </td>
       <td width="30%" class="dom" id="deviceDom">
        <logic:equal value="" name="termId">
         <peis:selectlist name="termIdCasc" sql="SL_ARCHIVE_0049" />
        </logic:equal>
        <logic:notEqual value="" name="termId">
         <peis:selectlist name="termIdCasc" sql="SL_ARCHIVE_0056" />
        </logic:notEqual>
        <input type="hidden" name="termIdCascTemp" id="termIdCascTemp" value="${object_termCascade.termIdCasc}" />
       </td>
      </tr>
     </table>
    </div>
    <!--
    <div id="assInfoShow">
     <div class="data3">
      <span>关联信息</span>
     </div>
     <div class="data3_con">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
       <tr>
        <td align="right" class="lable">
         电表：






        </td>
        <logic:present name="object_meter">
         <logic:iterate id="meter" name="object_meter">
          <td>
           <input type="checkbox" <logic:equal value="${object_term.logicalAddr}" name="meter" property="col4">checked</logic:equal> id="mpIdList" name="mpIdList" value="<bean:write name="meter" property="col8"/>" />
           <bean:write name="meter" property="col2" />
           /
           <bean:write name="meter" property="col1" />
          </td>
         </logic:iterate>
        </logic:present>
        <td></td>
       </tr>
      </table>
     </div>
    </div> -->
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
   </div>
   <div class="guidePanel">
    <input type="button" id="c_button" value="保 存" class="input1" />
   </div>
  </div>
 </body>
</html>
