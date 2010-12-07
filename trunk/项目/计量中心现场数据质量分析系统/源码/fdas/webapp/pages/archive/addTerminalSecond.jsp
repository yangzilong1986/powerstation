<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../common/loading.jsp"%>

<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>专变用户档案新增第二步</title>
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/loading.css" />
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/ext-all.css" />
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/xtheme-gray.css" />
  <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.dataForAjax.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/loading.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/ext/ext-base.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/ext/ext-all.js"></script>
  <script type="text/javascript">
var contextPath = '<peis:contextPath/>';
show_loading();
$(document).ready(function(){
  $('#ACShow').hide(); //默认不显示交采信息
  $('#totalGPShow').hide(); //默认不显示总加组信息
  inputAndSelectInit();
  //加载用户id
  loadCustId();
  if($("input[name='termId']").val()==''){
    loadParamTblTbody();
  }
  selectInit();
   //是否交采事件
   $('#isac').click(function(){
        if($('#isac').attr("checked")==true){
            $('#ACShow').show();
            $("#agRelaTd").append("<span class='ac'>交采</span>");
        }else{
            $('#ACShow').hide();
            $(".ac").remove();
        }
    })
   //是否建总加组
   $('#isTotalGP').click(function(){
        if($('#isTotalGP').attr("checked")==true){
            $('#totalGPShow').show();
        }else{
            $('#totalGPShow').hide();
        }
    })
    //是否多用户
    $('#isMoreCust').click(function(){
        if($('#isMoreCust').attr("checked")==true){
            $('#custTr').show();
        }else{
            $('#custTr').hide();
        }
    })
    //用户select列表点击事件
    $('#custId').change(function(){
          //加载交采用户
          loadAcCust($(this).val());
          //加载交采用户下变压器
          loadTran($(this).val());
    })
    //交采初始化
     if($('#isac').attr("checked")==true){
         $('#ACShow').show();
         $("#agRelaTd").append("<span class='ac'>交采</span>");
     }else{
         $('#ACShow').hide();
         $(".ac").remove();
     }
     //建总加组初始化
    if($('#isTotalGP').attr("checked")==true){
          $('#totalGPShow').show();
      }else{
          $('#totalGPShow').hide();
      }
    //多用户总加组
     if($('#isMoreCust').attr("checked")==true){
         $('#custTr').show();
     }else{
         $('#custTr').hide();
     }
     $("#protocolNo").change(loadParamTblTbody);
     remove_loading();
});

//电表checkbox单击,在总加组中加载该电表对应的测量点序号
function meterCheckBoxClick(mc){
   var mpId=$(mc).val();
   if($(mc).attr("checked")==true){
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
}

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
//加载custId
function loadCustId(){
  var termId=$("input[name='termId']").val();
  var url=contextPath+"/archive/terminalAction2.do?action=getCustIdList&termId="+termId;
    $.ajax({
        type: "POST",
        url: url,
        cache: false,
        dateType:'script',
        success: function(data) {
             eval(data);//加载custId，select控件
             $("#custId option").each(function(){//遍历select控件，加载每个用户对应的电表，开关,变压器
                         　　　　var id=$(this).val();
                 loadMeterAndSwitch(id);
                 //loadTran(id);
                         　  })
        }
     });
     //加载交采用户
     loadAcCust(${object_gp.objectId});
     //加载交采用户下变压器
     loadTran(${object_gp.objectId});
}
//根据custId加载电表，开关
function loadMeterAndSwitch(custId){
   var termId=$("input[name='termId']").val();
   var url=contextPath+"/archive/terminalAction2.do?action=getMeterAndSwitch&custId="+custId+"&termId="+termId;
   $.ajax({
     url: url,
     cache: false,
     success: function(html){
       var meterAndSwitch= html.split("|");
       $("#meterTd").append(meterAndSwitch[0]);
       $("#switchTd").append(meterAndSwitch[1])
     }
   });
}
//加载变压器
function loadTran(custId){
  //var custId=$('#objectId').val();
  var url=contextPath+"/archive/addBigCustAction.do?action=getSelect&custId="+custId;
   $.ajax({
        type: "POST",
        url: url,
        cache: false,
        dateType:'script',
        success: function(data) {
            eval(data);
            $("#tranId").val('${object_gp.tranId}');
        }
   });
}
 //控件样式初始化
function inputAndSelectInit(){
var bigCustTermFlag=$("input[name='bigCustTermFlag']").val();
 if(bigCustTermFlag=="1"){//专变用户编辑
  // $('#tab_1 > a').html("档案维护第三步");
 }else{
  // $('#tab_1 > a').html("档案录入第二步");
 }
}
//添加所属用户
//function addCust(){
   //var params = {
     //            "sqlCode":"AL_ARCHIVE_0041",
       //          "pageRows":20,
         //        "custNo":""
           //    };
   //var url=contextPath+"/archive/localBigCustListQuery.do?action=normalMode&"+ jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
   //top.showDialogBox("用户定位",url, 495, 800);
   
//}

//删除所属用户
function delCust(){
   var count=$("#custId option").length;
   $("#custId option:selected").each(function(){
        var custIdsText="."+$(this).val();
        $(custIdsText).remove(); //删除该用户关联的电表，开关信息
        //删除用户关联变压器(暂时认定select框内排在第一个的用户对应变压器)
        if($("#custId option:eq(0)").val()==$(this).val()){ //如果当前要删除的用户刚好是第一个用户，将该用户对应的变压器删除
           $("#tranId").val('');//清空变压器
           //当第一个用户删除之前，加载第二个用户对应的变压器
           loadTran($("#custId option:eq(1)").val());
           loadAcCust($("#custId option:eq(1)").val());
        }
        $(this).remove();//删除用户名
        if(count==1){//如果把用户全部删除
          //交采用户，变压器都删除
          $("#tranId option").remove();
          $("#objectId option").remove();
        }else{
          //删除用户之后，默认记载第一个用户作为交采，和变压器
          loadTran($("#custId option:eq(0)").val());
          loadAcCust($("#custId option:eq(0)").val());
        }
        count--;
　  })
}
//上一步
function lastStep(){
  var bigCustTermFlag=$("input[name='bigCustTermFlag']").val();
  if(bigCustTermFlag=="1"){
   //window.location.href= contextPath + "/archive/bigCustTermListQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0043&pageRows=20";
   //window.location.href= contextPath + "/jsp/archive/selectTerminal.jsp";
   history.back();
  }else{
    //window.location.href=contextPath+"/jsp/archive/archiveType.jsp";
    var url=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=archiveType&selectType=1";
    window.location.href=url; 
  }
}
//完成
function finish(){
    formSubmit();
}
//提交form
function formSubmit(){
   var url=contextPath+"/archive/terminalAction2.do?action=saveOrUpdateTerminal";
      if(terminalCheck()){
        $("#finish").attr("disabled",true);
        var custIds=$("#custId option").val();
        if(custIds!='undefined' && custIds!=''){//如果所属用户不为空
           $("#custId option").each(function(){//将custId下所有option设置为selected
 　　　　             $(this).attr("selected","true");
　                        })
        }
        var data = getFormData("form");
        if(data){
            jQuery.ajax({
                type:'post',
                url:url,
                data:data,
                dataType:'json',
                success:function(json){
                    var msg=json['msg'];
                    $("#finish").attr("disabled",false);
                    if(msg=="1"){
                       alert("保存成功");
                       var bigCustTermFlag=$("input[name='bigCustTermFlag']").val();
                       if(bigCustTermFlag=="1"){
                         if(confirm("是否继续维护终端")==true){
                           window.location.href= contextPath + "/jsp/archive/selectTerminal.jsp";
                         }else{
                            //window.location.href=contextPath+"/jsp/archive/archiveTypeUpdate.jsp"; 
                            window.location.href=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=archiveTypeUpdate&selectType=0";
                         }
                       }else{
                         if(confirm("是否继续新增终端")==true){
                            window.location.href= contextPath + "/jsp/archive/addTerminalSecond.jsp";
                         }else{
                            //window.location.href=contextPath+"/jsp/archive/archiveType.jsp"; 
                            window.location.href=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=archiveType&selectType=1";
                         }
                       }
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
  $("#lineId").val('${object_gp.lineId}');
  //lineIdComboBox.setValue('${object_gp.lineId}');
  $("#ctRatio").val(${object_ct});
  $("#ptRatio").val(${object_pt});
  $("#machNo").val('${object_term.machNo}');
  $("#channelType").val('${object_term.channelType}');
}
//所属用户下拉框初始化
function useSelectInit(custId,custName){
  $("#custId").append("<option value='"+custId+"'>"+custName+"</option>");
}
//高级查询
function comfirmAfterQuery(data){
    var objectId=""+data.OBJECT_ID;
    objectId=filterNoRepeatStr(objectId);
    show_loading();
    if(checkCustIdUnq(objectId)){
      //根据custId加载用户名
      var url=contextPath+"/archive/terminalAction2.do?action=getCustNameByCustId&custId="+objectId;
      $.ajax({
        url: url,
        cache: false,
        success: function(data){
           $("#custId option").each(function(){//将之前select框中为空的用户移除
               if($(this).val()==''){
                  $(this).remove();
               }
        　                 })
           //$("#custId").append(html);
           eval(data);
           //默认选中第一个用户
           $("#custId option:eq(0)").attr("selected","true");
           //默认加载排在第一个用户的变压器
           loadTran($("#custId option:eq(0)").val());
           //默认加载排在第一个用户做交采用户
           loadAcCust($("#custId option:eq(0)").val());
        }
      });
      //加载开关
      var objectIdArray=objectId.split(",");
      for(var i=0;i<objectIdArray.length;i++){
          loadMeterAndSwitch(objectIdArray[i]); //加载电表开关
      }
    }
    remove_loading();
}
//验证custId是否重复
function checkCustIdUnq(custIds){
  var custIdArray=custIds.split(",");
  var count=true;
  $("#custId option").each(function(){//遍历select控件
      for(var i=0;i<custIdArray.length;i++){
         if($(this).val()==custIdArray[i]){
           alert("不能选重复用户!");
           count=false;
         }
      } 
　  });
   return count;
}
//加载交采用户
function loadAcCust(objectId){
   //根据custId加载用户名
    var url=contextPath+"/archive/terminalAction2.do?action=getCustNameByObjectId&custId="+objectId;
    $.ajax({
      url: url,
      cache: false,
      success: function(data){
        eval(data);
        //$("#objectId").html(html);
      }
    });
}
</script>
 </head>
 <body>
  <div id="body">
   <div id="form">
    <jsp:include page="archiveTabs.jsp" flush="true">
      <jsp:param value="1" name="pageType"/>
    </jsp:include>
    <div id="main">
     <div class="tab">
      <em>终端信息</em>
     </div>
     <div class="tab_con" style="height: expression(((   document.documentElement.clientHeight ||   document.body.clientHeight) -     123 ) );">
      <div class="main">
       <input type="hidden" name="termId" value="${object_term.termId}" />
       <!-- //标记位，用来区分专变用户录入还是编辑 -->
       <input type="hidden" name="bigCustTermFlag" value="${bigCustTermFlag}" />
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
         <td class="label">
          所属用户：
         </td>
         <td rowspan="3" class="dom">
          <select multiple="multiple" name="custId" id="custId" style="height: 80px">
          </select>
          <input type="button" value="+" onclick="qry_adv_click({OBJECT_TYPE : '1',ARCHIVE_TYPE : '0',QUERY_TYPE : '0'})" style="width: 20px; cursor: pointer;" />
          <input type="button" value="-" onClick="delCust();" style="width: 20px; cursor: pointer;" />
         </td>
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
          <input type="text" name="fepCnl" id="fepCnl" value="${object_term.fepCnl}" disabled="disabled"/>
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
         <td class="dom">
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
            SIM卡号：
         </td>
         <td class="dom">
            <input type="text" id="simNo" name="simNo" value="${object_term.simNo}" />
         </td>
         <td class="label">
          通讯方式：
         </td>
         <td width="21%" class="dom">
          <peis:selectlist name="commMode" sql="SL_ARCHIVE_0011" />
         </td>
        </tr>
        <tr>
         <td colspan="2" align="center">
          <input type="checkbox" name="isac" id="isac" value="${object_term.isac!=" " ? "1" : "0"}" ${object_term.isac==1?"checked" : ""}>
          接交采
          <input type="checkbox" name="isTotalGP" id="isTotalGP" value="1" ${object_gp_total.gpSn!=null?"checked" : ""}>
          建总加组
         </td>
         <td class="label">
          安装单位：
         </td>
         <td class="dom">
          <input type="text" id="constrGang" name="constrGang" value="${object_term.constrGang}" />
         </td>
         <td class="label">
          终端类型：
         </td>
         <td class="dom">
          <peis:selectlist name="termType" sql="SL_ARCHIVE_0054" />
         </td>
        </tr>
        <tr>
          <td colspan="2"></td>
          <td class="label">
                                物理地址：
          </td>
          <td colspan="2" class="dom2">
             <input type="text" id="physicsAddr" style="width:300px" name="physicsAddr" value="${object_term.physicsAddr}" />
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
          <td class="label">
           计量点序号：
          </td>
          <td class="dom">
           <input type="text" id="gpSn" name="gpSn" value="0" style="width: 60px" readonly/>
          </td>
          <td class="label">
           供电线路：
          </td>
          <td class="dom">
           <peis:selectlist name="lineId" sql="SL_ARCHIVE_0018" />
          </td>
          <td colspan="2">
           <input type="checkbox" id="sucratCptId" name="sucratCptId" value="${object_gp.sucratCptId!=" " ? "1" : "0"}" ${object_gp.sucratCptId==1? "checked" : ""}/>
           功率累计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           <input type="checkbox" id="computeFlag" name="computeFlag" value="${object_gp.computeFlag!=" " ? "1" : "0"}" ${object_gp.computeFlag==1 ? "checked" : ""}/>
           电量累计 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <td class="label">
           变压器：
          </td>
          <td class="dom">
           <select id="tranId" name="tranId"></select>
          </td>
         </tr>
         <tr>
          <td class="label">
           端 口 号：
          </td>
          <td class="dom">
           <input type="text" id="port" name="port" value="${object_gp.port}" style="width: 60px" />
          </td>
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
           交采用户：
          </td>
          <td class="dom">
           <select id="objectId" name="objectId"></select>
          </td>
         </tr>
        </table>
       </div>
      </div>
      <div id="assInfoShow">
       <div class="data3">
        <span>关联信息</span>
       </div>
       <div class="data3_con">
        <table border="0" cellpadding="0" cellspacing="0" width="100%" id="meterTable">
         <tr>
          <td align="left">
           电表:
          </td>
          <td id="meterTd"></td>
         </tr>
        </table>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
         <tr>
          <td align="left">
           开关:
          </td>
          <td id="switchTd"></td>
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
             <bean:write name="object_agRela" ignore="true" filter="false" />
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
     </div>
     <div class="guidePanel">
      <input type="button" id="last" value="上一步" class="input1" onclick="lastStep();" />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="finish" value="完 成" class="input1" onclick="finish();" />
     </div>
    </div>
   </div>
  </div>
 </body>
</html>
