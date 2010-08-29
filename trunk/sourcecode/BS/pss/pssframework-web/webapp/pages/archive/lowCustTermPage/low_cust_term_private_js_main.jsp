<!-- 集中器维护私有JS -->
<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglibs.jsp"%>
<script type="text/javascript">
  var contextPath = '<peis:contextPath/>';
  var tgId=$.url.param("tgId");
  var orgNo=$.url.param("orgNo");
  var flag=0;
/**************************************************************************************************************
页面初次加载
***************************************************************************************************************/
  show_loading();
  $(document).ready(function(){
    jQuery("#c_button").click(function(){
      submitByAjax();
    });
  $("#deviceLable").hide();
  $("#deviceDom").hide();
  if($("input[name='termId']").val()==''){
    loadParamTblTbody();
  }
  //加载台区关联信息
  loadTgAssociatedInfo();
  selectInit();
  //termCascInit();
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
  
   //接入终端
  function addTerminal(){
   var tgId=$("input[name='tgId']").val();
   var params = {
                 "sqlCode":"AL_ARCHIVE_0021",
                 "pageRows":20,
                 "objectId":tgId
               };
   var url=contextPath+"/archive/termListQuery.do?action=normalMode&"+ jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
   top.showDialogBox("终端定位",url, 495, 800);
  }


/**************************************************************************************************************
业务逻辑操作部分
***************************************************************************************************************/
//保存集中器信息，并跳转到采集器页面
  function saveAndShowCollect(){
    var termId=$("input[name='termId']").val();
     if(termId==null || termId==''){
       alert("请先保存集中器信息");
     }else{
       submitAndForwardByAjax();
     }
  }

//AJAX提交表单
   function submitByAjax(){
      var url=contextPath+"/archive/lowCustTermAndTgTermAction.do?action=saveOrUpdateLowTermOrTgTerm";
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
                    var msg=json['msg'];
                    var termId=json['termId'];
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
                       if(confirm("是否继续维护集中器")==true){
                           var url1=contextPath+"/archive/commAction.do?action=clearSessionByTerm";
                           $.ajax({
                             url: url1,
                             cache: false,
                             success: function(html){
                                 if(parent.tgTermCollectFlag=="1"){
                                    //top.getMainFrameObj().location.href=contextPath + "/archive/tgTermQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0049&pageRows=20";
                                    top.getMainFrameObj().location.href=contextPath + "/jsp/archive/selectTgTerm.jsp";
                                 }else{
                                    top.getMainFrameObj().location.href=contextPath +"/jsp/archive/selectLowCustTerm.jsp";
                                    //top.getMainFrameObj().location.href=contextPath + "/archive/lowCustTermQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0100&pageRows=20";
                                 }
                             }
                           });
                       }else{
                           if(parent.tgTermCollectFlag=="1"){
                              //top.getMainFrameObj().location.href=contextPath+"/jsp/archive/tgTypeUpdate.jsp";
                              top.getMainFrameObj().location.href=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=tgTypeUpdate&selectType=0"; 
                           }else{
                              //top.getMainFrameObj().location.href=contextPath+"/jsp/archive/lowVoltCustTypeUpdate.jsp"; 
                              top.getMainFrameObj().location.href=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=lowVoltCustTypeUpdate&selectType=0";
                           }
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
      var url=contextPath+"/archive/lowCustTermAndTgTermAction.do?action=saveOrUpdateLowTermOrTgTerm";
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
                    if(msg=="1"){
                       //更新父页面的orgNo
                       parent.orgNo = $("input[name='orgNo']").val();
                       //切换父页面的TAB
                       $('#te',parent.document).attr("class","target_off");
                       $('#gm',parent.document).attr("class","target_on");
                       //跳转到采集器列表页面
                       window.location.href = contextPath + "/archive/gmInfoListQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0023&termId="+termId+"&pageRows=20";
                    }else if(msg=="2"){
                       alert("该用户已经存在");
                    }else if(msg=="3"){
                       alert("该电表已经存在");
                    }else{
                       alert("保存失败");
                    }
                }
            });
        }
      }
   }
    //删除集中器
   function deleteTerminal(termId){
     var termId=$("input[name='termId']").val();
     var url=contextPath+"/archive/terminalAction1.do?action=deleteTerminal&termId="+termId;
     if(confirm("确定要删除该集中器?")){
           $.ajax({
            url: url,
            dataType:'json',
            cache: false,
            success: function(json){
                 var msg=json['msg'];
	             if(msg=="1"){
	                alert("删除成功");
	                top.getMainFrameObj().location.reload();
	             }else if(msg=="3"){
	                alert("该集中器下存在电表、采集器及低压用户，或者级联其他集中器，不允许删除");
	             }
	             else{
	                alert("删除失败");
	             }
	            }
            });
     }
   }
/**************************************************************************************************************
加载终端参数
***************************************************************************************************************/
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
/**************************************************************************************************************
页面控件初始化
***************************************************************************************************************/
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
//级联信息主,从模式初始化
  function termCascInit(){
    var termIdCasc=$("select[name='termIdCasc']").val();
    if(termIdCasc==-1){
      $('#commPattern').val(1);
    }else{
      $('#commPattern').val(2);
      $("#deviceLable").show();
      $("#deviceDom").show();
    }
  }
  //select框初始化
  function selectInit(){
      $("#curStatus").val(${object_term.curStatus});
      $("#commMode").val(${object_term.commMode});
      $("#wiringMode").val(${object_term.wiringMode});
      $("#madeFac").val('${object_term.madeFac}');
      $("#termType").val('${object_term.termType}');
      //$("#tranId").val('${object_gp.tranId}');
      $("#pr").val(${object_term.pr});
      $("#modelCode").val(${object_term.modelCode});
      //$("#lineId").val(${object_gp.lineId});
      lineIdComboBox.setValue('${object_gp.lineId}');
      $("#commPattern").val(${object_term.commPattern});
      $("#ctRatio").val(${object_ct});
      $("#ptRatio").val(${object_pt});
      $("#machNo").val('${object_term.machNo}');
      $("#channelType").val('${object_term.channelType}');
      $("#protocolNo").val('${object_term.protocolNo}');
      protocolIsac();
  }
  //如果是广东集抄规约，是否接交采功能屏蔽
  function protocolIsac(){
     var protocolNo=$('#protocolNo').val();
     if(protocolNo=="126"){ //广东集抄规约
        $('#isac').attr("disabled","true");
     }
  }
/**************************************************************************************************************
加载台区关联信息
***************************************************************************************************************/
//加载台区关联信息(部门号，变压器，终端，电表)
  function loadTgAssociatedInfo(){
      var tgId=$('#tgId').val();
      getOrgNo(tgId);
      loadTranSelect(tgId);
      loadTermSelect(tgId);
      loadMeterSelect(tgId);
  }
  //动态获取台区下orgNo
  function getOrgNo(tgId){
     var url=contextPath+"/archive/commAction.do?action=getJsonByObjectData";
     if(tgId!=""){//如果有所属台区查询部门号
         var params={
            "objectId":tgId,
            "methodsName":"load",
            "className":"TgService"
         }
       //请求
       $.ajax({
           type:'post',
           url:url,
           data:params,
           dataType:'json',
           success:function(json){
           $("input[name='orgNo']").val(json[0].orgNo);
           }
       });
     }else{//如果没有则设置成空
        $("input[name='orgNo']").val("");
     }
  }
  //动态加载变压器select框
  function loadTranSelect(tgId){
     var url=contextPath+"/archive/commAction.do?action=getSelect";
     if(tgId!=""){
        var params={
             "sqlCode":"SL_ARCHIVE_0091",
             "selectId":"tranId",
             "valueIndex":"0",
             "nameIndex":"1",
             "tgId":tgId
        }
        //请求
        $.ajax({
          type: "POST",
          url: url,
          data:params,
          dateType:'script',
          success: function(data){
             eval(data);
             $("#tranId").val('${object_gp.tranId}'); //加载变压器select框之后，选中该终端所接变压器
          }
        });  
     }else{//如果没有所属台区
        $("#tranId > option").remove();
     }
  }
  //动态加载台区所属终端
  function loadTermSelect(tgId){
    var termId=$("input[name='termId']").val();
    var url=contextPath+"/archive/commAction.do?action=getSelect";
    if(tgId!=""){
        var params={}
        if(termId!=null){ //如果是维护页面
          params={
             "sqlCode":"SL_ARCHIVE_0090",
             "selectId":"termIdCasc",
             "valueIndex":"0",
             "nameIndex":"1",
             "tgId":tgId,
             "termId":termId
          }
        }else{//如果是新增页面
          params={
             "sqlCode":"SL_ARCHIVE_0089",
             "selectId":"termIdCasc",
             "valueIndex":"0",
             "nameIndex":"1",
             "tgId":tgId,
             "termId":termId
          }
        }
        //请求
        $.ajax({
          type: "POST",
          url: url,
          data:params,
          dateType:'script',
          success: function(data){
             eval(data);
          }
        });  
    }else{
       $("#termIdCasc > option").remove();
       $("#termIdCasc").val('${object_termCascade.termIdCasc}');//加载级联终端select框之后，选中该终端的级联终端
    }
  }
  //动态加载台区下电表
  function loadMeterSelect(tgId){
     var url=contextPath+"/archive/addLowCustInfoAction.do?action=getCheckBox&tgId="+tgId;
     $.ajax({
      url: url,
      cache: false,
      success: function(html){
        $("#results").html(html);
       }
     });
  }
/**************************************************************************************************************
高级查询
***************************************************************************************************************/
//高级查询
function comfirmAfterQuery(data){
  var objectId=""+data.OBJECT_ID;
  $("#tgId").val(objectId);
  loadTgAssociatedInfo(); //加载该台区相关设备及信息
}
//高级查询
function qry_adv_click(qry_param_obj){
   parent.parent.showDialogBox("高级查询","/peis/jsp/queryadv/qry_adv_main.jsp?"+jQuery.param(qry_param_obj),458,775);
}
</script>