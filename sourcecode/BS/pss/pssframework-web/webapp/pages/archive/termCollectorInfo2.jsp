<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../common/loading.jsp"%>
<html>
 <head>
  <title><bean:message bundle="archive" key="archive.new.meter" />
  </title>
  <meta http-equiv="Content-Language" content="zh-cn">
  <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/ext-all.css" />
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/xtheme-gray.css" />
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/loading.css" />
  <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.dataForAjax.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/loading.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/ext/ext-base.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/ext/ext-all.js"></script>
  <script type="text/javascript">
  var contextPath = '<peis:contextPath/>';
  var tgId=$.url.param("tgId");
  var orgNo=$.url.param("orgNo");
  var flag=0;
  show_loading();
  $(document).ready(function(){
  //$('#tgId').change(loadGetSelect);
  protocolIsac();
  selectInit();
  $("#deviceLable").hide();
  $("#deviceDom").hide();
  if($("input[name='termId']").val()==''){
    loadParamTblTbody();
  }
  getOrgNo();
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
     remove_loading();
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
   var params = {
                 "sqlCode":"AL_ARCHIVE_0021",
                 "pageRows":20
               };
   var url=contextPath+"/archive/termListQuery.do?action=normalMode&"+ jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
   top.showDialogBox("终端定位",url, 495, 800);
  }
 
  //保存集中器信息，并跳转到采集器页面






  function saveAndShowCollect(){
     var termId=$("input[name='termId']").val();
     if(termId==null || termId==''){
       alert("请先保存集中器信息");
     }else{
       submitAndForwardByAjax();
     }
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
   //AJAX提交表单
   function submitByAjax(){
      var url=contextPath+"/archive/terminalAction1.do?action=addTermInfoSecond";
      if(modelTermCheck()){
        $("#save").attr("disabled",true);
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
                    $("#save").attr("disabled",false);
                    if(msg=="1"){
                       alert("保存成功");
                       $("input[name='termId']").val(termId);
                       if(confirm("是否新增采集器")==true){
                         submitAndForwardByAjax();
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
   //完成
   function finish(){
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
                    var msg=json['msg'];
                    if(msg=="1"){
                       alert("保存成功");
<<<<<<< .mine
                       if(confirm("是否继续新增集中器")==true){
                           var url1=contextPath+"/archive/commAction.do?action=clearSessionByTerm";
                           $.ajax({
                             url: url1,
                             cache: false,
                             success: function(html){
                                top.getMainFrameObj().location.href=contextPath+"/jsp/archive/addLowCustTermFrame.jsp";
                             }
                           });
                       }else{
                           //top.getMainFrameObj().location.href=contextPath+"/jsp/archive/lowVoltCustType.jsp"; 
=======
                       var tgTermCollectFlag = parent.tgTermCollectFlag;
                       if(tgTermCollectFlag == "1"){ //新增集中器页面

                         if(confirm("是否继续新增集中器")==true){
                           top.getMainFrameObj().location.href=contextPath+"/jsp/archive/addLowCustTermFrame.jsp";
                         }else{
>>>>>>> .r837
                           top.getMainFrameObj().location.href=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=lowVoltCustType&selectType=1";
<<<<<<< .mine
=======
                         }
                       }else if(tgTermCollectFlag =="0"){ //维护集中器页面

                         if(confirm("是否继续维护集中器")==true){
                           top.getMainFrameObj().location.href=contextPath +"/jsp/archive/selectLowCustTerm.jsp";
                         }else{
                           top.getMainFrameObj().location.href=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=lowVoltCustTypeUpdate&selectType=0";
                         }
                       }else{ //从配变终端跳转过来

                         if(confirm("是否继续维护集中器")==true){
                           top.getMainFrameObj().location.href=contextPath + "/jsp/archive/selectTgTerm.jsp";
                         }else{
                           top.getMainFrameObj().location.href=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=tgTypeUpdate&selectType=0"; 
                         }
>>>>>>> .r837
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
                    var tgId=$("select[name='tgId']").val();
                    var termId1=$("input[name='termId']").val();
                    if(termId1=="null" || termId1=="" || termId1==null){
                      alert("请先保存集中器信息");
                    }else{
                       if(msg=="1"){
                        window.location.href=contextPath+"/archive/gmAction.do?action=getGmList&termId="+termId+"&tgId="+tgId+"";
                      }else if(msg=="2"){
                         alert("该资产编号已经存在");
                      }else if(msg=="3"){
                         alert("该逻辑地址已经存在");
                      }else{
                         alert("保存失败");
                      }
                    }
                }
            });
        }
      }
   }
 //动态生成select控件
 function loadGetSelect(){
    var tgId=$('#tgId').val();
    var termId=$("input[name='termId']").val();
    var tgTermFlag=$("input[name='tgTermFlag']").val();
    var url=contextPath+"/archive/addLowCustInfoAction.do?action=getSelect&tgId="+tgId+"&termId="+termId;
    $.ajax({
      type: "POST",
      url: url,
      cache: false,
      dateType:'script',
      success: function(data) {
           if(tgId==""){//如果没有所属台区

<<<<<<< .mine





              //$("#tranId").html("<option value=''>未接变压器</option>");//清空变压器=======
              //$("#tranId").html("<option value=''>未接变压器</option>");//清空变压器
>>>>>>> .r837






              $("#termIdCasc").html("<option value=''></option>");//清空级联终端
           }
          eval(data); //加载变压器,级联终端下拉框






          $("#tranId").val('${object_gp.tranId}'); //加载变压器select框之后，选中该终端所接变压器
          $("#termIdCasc").val('${object_termCascade.termIdCasc}');//加载级联终端select框之后，选中该终端的级联终端
      }
   });
   loadMeterCheckBox();
 }
//动态生成电表






 function loadMeterCheckBox(){
    var tgId=$('#tgId').val();
    var termId=$("input[name='termId']").val();
    var url=contextPath+"/archive/addLowCustInfoAction.do?action=getCheckBox&tgId="+tgId+"&termId="+termId;
    $.ajax({
     url: url,
     cache: false,
     success: function(html){
       $("#results").html(html);
      }
    });
}
   //动态获取台区下orgNo
   function getOrgNo(){
     var tgId=$('#tgId').val();
     var url=contextPath+"/archive/addLowCustInfoAction.do?action=getOrgNo&tgId="+tgId;
     $.ajax({
       url: url,
       cache: false,
       success: function(data){
          $("input[name='orgNo']").val(data);
        }
      });
      loadGetSelect();
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
       $("#lineId").val('${object_gp.lineId}');
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
//高级查询
function comfirmAfterQuery(data){
  var objectId=""+data.OBJECT_ID;
  $("#tgId").val(objectId);
  getOrgNo(); //加载该台区相关设备及信息
}
</script>
 </head>
 <body>
  <div id="form">
   <div class="tab_con" style="height: expression((( document.documentElement.clientHeight || document.body.clientHeight) -   0 ) );">
    <input type="hidden" name="orgNo" value="${object_term.orgNo}" />
    <input type="hidden" name="termId" value="${termId}" />
    <input type="hidden" name="objRelId" value="${object_termObj.objRelId}" />
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
       <td width="13%" class="label">
        所属台区：
       </td>
       <td width="20%" class="dom">
        <peis:selectlist name="tgId" sql="SL_ARCHIVE_0037" onChange="getOrgNo();" />
        <script type="text/javascript">
               $("#tgId").val(${tgId});
               </script>
        <input type="button" value="..." onclick="qry_adv_click({OBJECT_TYPE : '2',ARCHIVE_TYPE : '1',QUERY_TYPE : '1',IFRAME_NAME:'contentArea',SINGLE_RESULT : '1'})" style="width: 22px; cursor: pointer;" />
       </td>
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






       </td>
       <td class="dom">
        <peis:selectlist name="modelCode" sql="SL_ARCHIVE_0017" />
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
        <input type="checkbox" name="isac" id="isac" value="${object_term.isac!=" " ? "1" : "0"}" ${object_term.isac==1? "checked" : ""}>
        接交采






       </td>
       <td class="label">
        物理地址：






       </td>
       <td colspan="3" class="dom">
        <input type="text" id="physicsAddr" name="physicsAddr" style="width: 390px" value="${object_term.physicsAddr}" />
       </td>
<<<<<<< .mine
<<<<<<< .mine
       <td  class="label">SIM卡号：</td>
       <td  class="dom">
            <input type="text" id="simNo" name="simNo" value="${object_term.simNo}" />
=======
       <td class="label">
        SIM卡号：

>>>>>>> .r837
       </td>
=======
>>>>>>> .r1071
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
<<<<<<< .mine
         <peis:selectlist name="lineId" sql="SL_ARCHIVE_0018" associate="true"/>
=======
         <peis:selectlist name="lineId" sql="SL_ARCHIVE_0018" />
>>>>>>> .r837
        </td>
        <td width="44%" colspan="2" align="center">
         <input type="checkbox" id="sucratCptId" name="sucratCptId" value="${object_gp.sucratCptId!=" " ? "1" : "0"}" ${object_gp.sucratCptId==1? "checked" : ""}>
         功率累计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <input type="checkbox" id="computeFlag" name="computeFlag" value="${object_gp.computeFlag!=" " ? "1" : "0"}" ${object_gp.computeFlag==1 ? "checked" : ""}/>
         电量累计 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         变压器：
         <select name="tranId" id="tranId">
<<<<<<< .mine
           <option value="">未接变压器</option>
          </select>
=======
          <option value="">
           未接变压器

          </option>
         </select>
>>>>>>> .r837
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
        <select name="termIdCasc" id="termIdCasc">
         <option value=""></option>
        </select>
       </td>
      </tr>
     </table>
    </div>
    <!--
      <div id="assInfoShow">
        <div class="data3"><span>关联信息</span></div>
        <div class="data3_con">
          <table border="0" cellpadding="0" cellspacing="0" width="100%">
           <tr id="results">
            
           </tr>
          </table>
        </div>
      </div>-->
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
    <div class="guidePanel">
     <input type="button" value="保 存" id="save" class="input1" onClick="submitByAjax();" />
    </div>
   </div>
  </div>
 </body>
</html>
