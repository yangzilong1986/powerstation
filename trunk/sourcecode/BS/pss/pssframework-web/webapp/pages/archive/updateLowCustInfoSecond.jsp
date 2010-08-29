<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>低压用户信息</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.dataForAjax.js"></script>
<script type="text/javascript">
var contextPath='<peis:contextPath/>';
var lowCustTermFlag=1;  //标记位,标记位=1是低压用户页面进入，还是电表页面进入

$(function(){
  $('#tgId').change(loadGetSelect);
  
  var url=contextPath+"/archive/addLowCustInfoAction.do?action=addLowCustInfoSecond";
    jQuery("#c_button").click(function(){
      if(lowCustCheck()){
        $("#c_button").attr("disabled",true);
        var data = getFormData("#form");
        if(data){
            jQuery.ajax({
                type:'post',
                url:url,
                data:data,
                dataType:'json',
                success:function(msg){
                    $("#c_button").attr("disabled",false);
                    if(msg=="1"){
                       alert("保存成功");
                       if(confirm("是否继续编辑用户")==true){
                           //window.location.href = contextPath + "/archive/lowCustQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0101&pageRows=20";
                           window.location.href = contextPath + "/jsp/archive/selectLowCust.jsp";
                       }else{
                           //window.location.href=contextPath+"/archive/updateLowCustInfoAction.do?action=finishSecond"; 
                           window.location.href=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=lowVoltCustTypeUpdate&selectType=0";
                       }
                    }else if(msg=="2"){
                       alert("该用户已经存在");
                    }else if(msg=="3"){
                       alert("该表号已经存在");
                    }else if(msg=="5"){
                       alert("同一集中器下的重点表序号不能重复！");
                    }else{
                       alert("保存失败");
                    }
                }
            });
        }
      }
    });
    //中继类型初始化
 $("#repeatTypeLable").hide();
 $("#repeatTypeDom").hide();
 
 //重点表序号初始化
 $("#meterSnLable").hide();
 $("#meterSnDom").hide();
 
 $('#isEph').click(function(){
      if($('#isEph').attr("checked")==true){
          $("#meterSnLable").show();
          $("#meterSnDom").show();
      }else{
          $("#meterSnLable").hide();
          $("#meterSnDom").hide();
      }
  })
 $('#commMode').change(showRepeat);
 
 $('#termId').change(loadGmSelect);
 //loadGmSelect();
 meterSnInit();
 loadGetSelect();
 selectInit();
 showRepeat();
});
//初始化重点表序号
function meterSnInit(){
 if($('#isEph').attr("checked")==true){
      $("#meterSnLable").show();
      $("#meterSnDom").show();
  }else{
      $("#meterSnLable").hide();
      $("#meterSnDom").hide();
  }
}
//当通讯方式为载波信息表，显示中继类型
function showRepeat(){
  if($("#commMode").val()==2){
     $("#repeatTypeLable").show();
     $("#repeatTypeDom").show();
  }else{
     $("#repeatTypeLable").hide();
     $("#repeatTypeDom").hide();
  }
}
//打开中继器页面
function openRepeat(){
   var relayType=$('#relayType').val();
   var url=contextPath+"/jsp/archive/addRepeat.jsp?relayType="+relayType;
   top.showDialogBox("中继器类型维护",url, 495,800);
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

//上一步
function lastStep(){
  //清除session
  //window.location.href=contextPath+"/archive/updateLowCustInfoAction.do?action=last";
  //window.location.href = contextPath + "/jsp/archive/selectLowCust.jsp";
  history.back();
}
//加载采集器select
function loadGmSelect(){
    var termId = $("#termId").val();
    var params = {"action": "getGmId",
                  "termId": termId
                 };
    var url = contextPath + "/archive/addLowCustAction.do";
    $.ajax({
        type: "POST",
        url: url,
        cache: false,
        data: params,
        dateType:'script',
        success: function(data) {
            //$("#gmSelect").html(data);
            eval(data);
            $("#gmId").val('${object_gp.gmId}');
        }
    });
    loadProNo(termId);
}
//动态生成select控件
   function loadGetSelect(){
     var tgId=$('#tgId').val();
     var url=contextPath+"/archive/addLowCustInfoAction.do?action=getTgInfo&tgId="+tgId;
     var url2=contextPath+"/archive/addLowCustInfoAction.do?action=getSelectAddLowCust&tgId="+tgId;
     var url3=contextPath+"/archive/addLowCustInfoAction.do?action=getTranSelect&tgId="+tgId;
     $.getJSON(url, function(json){
        var orgNo=json.orgNo;
        var sar=json.sar;
        $("input[name='orgNo']").val(orgNo);
        $("input[name='sar']").val(sar);
     });
     
     $.ajax({
        type: "POST",
        url: url2,
        cache: false,
        dateType:'script',
        success: function(data) {
            eval(data);
            $("#termId").val('${object_gp.termId}');
            loadGmSelect();
        }
     });
      //动态生成变压器
     $.ajax({
        type: "POST",
        url: url3,
        cache: false,
        dateType:'script',
        success: function(data) {
            eval(data);
            $("#tranId").val('${object_gp.tranId}');
        }
     });
   }
 //select框初始化
 function selectInit(){
     $("#tgId").val(${object_meter.tgId});
     $("#elecAppType").val(${object_customer.elecAppType});
     $("#custStatus").val(${object_customer.custStatus});
     $("#meterType").val(${object_meter.meterType});
     $("#ctRatio").val(${object_meter.ctRatio});
     $("#ptRatio").val(${object_meter.ptRatio});
     $("#btl").val(${object_meter.btl});
     $("#protocolNo").val(${object_gp.protocolNo});
     $("#madeFactory").val('${object_meter.madeFactory}');
     $("#canBreak").val(${object_meter.canBreak});
     $("#frPhase").val(${object_meter.frPhase});
     $("#isPrepay").val(${object_meter.isPrepay});
     $("#relayType").val(${object_meterRelay.relayType});
     $("#commMode").val(${object_meter.commMode});
 }
//高级查询
function comfirmAfterQuery(data){
  var objectId=""+data.OBJECT_ID;
  $("#tgId").val(objectId);
  loadGmSelect();
  meterSnInit();
  loadGetSelect();
}
//根据终端id查询终端规约号
function loadProNo(termId){
  var url=contextPath+"/archive/addLowCustInfoAction.do?action=getTermProNo&termId="+termId;
  $.ajax({
      type: "GET",
	  url: url,
	  cache: false,
	  success: function(proNo){
	     showInputByProNo(proNo);
	     $("#termProNo").val(proNo);
	  }
  });
}
//根据规约号(100,101,102)显示额定电压，额定电流，端口号，通讯密码输入框
function showInputByProNo(proNo){
   if(proNo == "100" || proNo == "101" || proNo == "102"){ //显示额定电压，额定电流，端口号，通讯密码输入框
     if(proNo == "100" || proNo == "101"){
        $("#ratedVoltLabel").show();
        $("#ratedVoltDom").show();
        $("#ratedEcLabel").show();
        $("#ratedEcDom").show();
     }else if (proNo == "102"){
        $("#ratedVoltLabel").hide();
        $("#ratedVoltDom").hide();
        $("#ratedEcLabel").hide();
        $("#ratedEcDom").hide();
     }
       $("#portLabel").show();
       $("#portDom").show();
       $("#commPwdLabel").show();
       $("#commPwdDom").show();
   }else {
     $("#ratedVoltLabel").hide();
     $("#ratedVoltDom").hide();
     $("#ratedEcLabel").hide();
     $("#ratedEcDom").hide();
     $("#portLabel").hide();
     $("#portDom").hide();
     $("#commPwdLabel").hide();
     $("#commPwdDom").hide();
   }
}
</script>
</head>
<body>
<div id="body">
  <jsp:include page="archiveTabs.jsp" flush="true">
      <jsp:param value="3" name="pageType"/>
  </jsp:include>
  <div id="main">
    <div id="form">
    <div class="tab"><em>基本信息</em></div>
    <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 123));">
     <div>
      <div class="data3"><span>基本信息</span></div>
      <div class="data3_con">
        <input type="hidden" name="orgNo" value="${orgNo}"/>
        <input type="hidden" name="sar" value="${sar}"/>
        <input type="hidden" name="custId" value="${object_customer.custId}"/>
        <input type="hidden" name="termProNo" id="termProNo"/>
        <!-- 中继地址 -->
        <input type="hidden" name="relayAddr1" id="relayAddr1" value="${object_meterRelay.relayAddr1}"/>
        <input type="hidden" name="relayAddr2" id="relayAddr2" value="${object_meterRelay.relayAddr2}"/>
        <input type="hidden" name="relayAddr3" id="relayAddr3" value="${object_meterRelay.relayAddr3}"/>
        <input type="hidden" name="relayAddr4" id="relayAddr4" value="${object_meterRelay.relayAddr4}"/>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td width="10%" class="label"><font color="red">* </font>户　号：</td>
            <td width="17%" class="dom"><input type="text" id="custNo" name="custNo" value="${object_customer.custNo}" /></td>
            <td width="10%" class="label"><font color="red">* </font>户　名：</td>
            <td width="17%" class="dom"><input type="text" id="custName" name="custName" value="${object_customer.custName}" /></td>
            <td width="10%" class="label">台区名称：</td>
             <td width="20%" class="dom">
               <peis:selectlist name="tgId" sql="SL_ARCHIVE_0037"/>
               <input type="button" value="..." onclick="qry_adv_click({OBJECT_TYPE : '2',ARCHIVE_TYPE : '1',QUERY_TYPE : '1'})" style="width: 22px; cursor: pointer;"/>
             </td>
             <td colspan="2"></td>
          </tr>
          <tr>
            <td width="10%" class="label">用电类别：</td>
            <td width="17%" class="dom">
              <peis:selectlist name="elecAppType" sql="SL_ARCHIVE_0032"/>
            </td>
            <td width="10%" class="label">用户状态：</td>
            <td width="17%" class="dom">
              <peis:selectlist name="custStatus" sql="SL_ARCHIVE_0033"/>
            </td>
            <td colspan="2" align="center">
              <input type="checkbox" name="isEph" id="isEph" value="${object_customer.isEph!="" ? "1" : "0"}" ${object_customer.isEph==1 ? "checked" : ""}/>重点用户
            </td>
            <td width="10%" class="label" id="meterSnLable">重点表序号：</td>
            <td width="17%" class="dom" id="meterSnDom">
               <select name="masMeterSn" id="masMeterSn">
                  <option value="1201">1201</option>
                  <option value="1202">1202</option>
                  <option value="1203">1203</option>
                  <option value="1204">1204</option>
                  <option value="1205">1205</option>
                  <option value="1206">1206</option>
               </select>
               <script type="text/javascript">
                 $("#masMeterSn").val(${object_gp.masMeterSn});
               </script>
            </td>
          </tr>
          <tr>
            <td width="10%" class="label">抄 表 日：</td>
            <td width="17%" class="dom">
               <input type="text" name="rmDay" id="rmDay" value="${object_customer.rmDay}"/>
            </td>
            <td width="10%" class="label">抄表区号：</td>
            <td width="17%" class="dom">
                <input type="text" name="rmSectNo" id="rmSectNo" value="${object_customer.rmSectNo}">
            </td>
            <td width="10%" class="label">抄表顺序号：</td>
            <td width="17%" class="dom">
                <input type="text" name="rmSn" id="rmSn" value="${object_customer.rmSn}">
            </td>
            <td width="10%" class="label">表 箱 号：</td>
            <td width="17%" class="dom">
                <input type="text" name="meterBox" id="meterBox" value="${object_meter.meterBox}">
            </td>
          </tr>
          <tr>
            <td width="10%" class="label">联 系 人：</td>
            <td width="17%" class="dom">
                <input type="text" name="contact" id="contact" value="${object_customer.contact}">
            </td>
            <td width="10%" class="label">联系电话：</td>
            <td width="17%" class="dom">
                <input type="text" name="phone" id="phone" value="${object_customer.phone}">
            </td>
            <td width="10%" class="label">地　址：</td>
            <td colspan="3" class="dom">
                <input type="text" name="custAddr" id="custAddr" style="width: 373px;" value="${object_customer.custAddr}"/>
            </td>
          </tr>
          <tr>
            <td class="label">备　注：</td>
            <td colspan="5" class="dom">
                <textarea class="input_textarea3" name="remark" style="width: 370px;">${object_customer.remark}</textarea>
            </td>
          </tr>
        </table>
      </div>
    </div>
      <div class="data3"><span>电表信息</span></div>
      <div class="data3_con">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
             <tr>
              <td width="10%" class="label">
               <font color="red">* </font>表 号：
              </td>
              <td width="23%" class="dom">
               <input type="text" id="assetNo" name="assetNo" value="${object_meter.assetNo}" />
              </td>
              <td width="10%" class="label">表 类 型：</td>
              <td width="23%" class="dom">
                <peis:selectlist name="meterType" sql="SL_ARCHIVE_0024"/>
              </td>
              <td width="10%" class="label">
               <font color="red">* </font>表 序 号：
              </td>
              <td width="23%" class="dom">
               <input type="text" id="gpSn" name="gpSn" value="${object_gp.gpSn}" />
              </td>
             </tr>
             <tr>
              <td width="10%" class="label">
               <font color="red">* </font>表 地 址：
              </td>
              <td width="23%" class="dom">
               <input type="text" id="gpAddr" name="gpAddr" value="${object_gp.gpAddr}" />
              </td>
              <td width="10%" class="label">
               CT变比：
              </td>
              <td width="23%" class="dom">
                <peis:selectlist name="ctRatio" sql="SL_ARCHIVE_0019"/>
              </td>
              <td width="10%" class="label">
               PT变比：
              </td>
              <td width="23%" class="dom">
               <peis:selectlist name="ptRatio" sql="SL_ARCHIVE_0020"/>
              </td>
             </tr>
             <tr>
              <td width="10%" class="label">波 特 率：
              </td>
              <td width="23%" class="dom">
              <peis:selectlist name="btl" sql="SL_ARCHIVE_0023"/>
              </td>
              <td width="10%" class="label">表 规 约：
              </td>
              <td width="23%" class="dom">
                 <peis:selectlist name="protocolNo" sql="SL_ARCHIVE_0022"/>
              </td>
              <td width="10%" class="label">
               通讯方式：
              </td>
              <td width="23%" class="dom">
                <peis:selectlist name="commMode" sql="SL_ARCHIVE_0010"/>
              </td>
             </tr>
             <tr>
              <td width="10%" class="label">
               电表厂家：
              </td>
              <td width="23%" class="dom">
                <peis:selectlist name="madeFactory" sql="SL_ARCHIVE_0008"/>
              </td>
              <td width="10%" class="label">
               拉闸功能：
              </td>
              <td width="23%" class="dom">
                <peis:selectlist name="canBreak" sql="SL_ARCHIVE_0034"/>
              </td>
              <td width="10%" class="label">
               初抄相位：
              </td>
              <td width="23%" class="dom">
                 <peis:selectlist name="frPhase" sql="SL_ARCHIVE_0035"/>
              </td>
             </tr>
             <tr>
              <td width="10%" class="label">
               表 位 数：
              </td>
              <td width="23%" class="dom">
               <input type="text" id="meterDigits" name="meterDigits" value="${object_meter.meterDigits}" />
              </td>
              <td width="10%" class="label">
               是否预付费：
              </td>
              <td width="23%" class="dom">
               <select name="isPrepay" id="isPrepay">
                <option value="1">
                 是
                </option>
                <option value="0">
                 否
                </option>
               </select>
              </td>
              <td width="10%" class="label">
               装表日期：
              </td>
              <td width="23%" class="dom_date">
               <input type="text" name="mpDate" id="mpDate" value="<fmt:formatDate value="${object_meter.mpDate}" type="date"/>" onfocus="peisDatePicker()" readonly="readonly" />
              </td>
             </tr>
             <tr>
              <td width="10%" class="label" id="portLabel">
                                             端 口 号：
              </td>
              <td width="23%" class="dom" id="portDom">
                <input type="text" id="port" name="port" value="${object_gp.port}"/>
              </td>
              <td width="10%" class="label" id="commPwdLabel">
                                               通信密码：
              </td>
              <td width="23%" class="dom" id="commPwdDom">
                <input type="password" id="commPwd" name="commPwd" value="${object_meter.commPwd}"/>
              </td>
              <td width="10%" class="label" id="ratedVoltLabel">
                                               额定电压：
              </td>
              <td width="23%" class="dom" id="ratedVoltDom">
                   <peis:selectlist name="ratedVolt" sql="SL_ARCHIVE_0026"/>
	               <script type="text/javascript">
	                   $("#ratedVolt").val(${object_meter.ratedVolt});
	               </script> 
              </td>
             </tr>
             <tr>
              <td width="10%" class="label" id="ratedEcLabel">
                                               额定电流：
              </td>
              <td width="23%" class="dom" id="ratedEcDom">
                <peis:selectlist name="ratedEc" sql="SL_ARCHIVE_0025"/>
                <script type="text/javascript">
                   $("#ratedEc").val(${object_meter.ratedEc});
                </script>
              </td>
              <td width="10%" class="label" id="repeatTypeLable">
               中继类型：
              </td>
              <td width="23%" class="dom_date" id="repeatTypeDom">
               <peis:selectlist name="relayType" sql="SL_ARCHIVE_0036"/>
               <input type="button" id="addRepeat" value="..."
                onclick="openRepeat();" style="width: 20px" />
              </td>
             </tr>
            </table>
      </div>
    <div id="relatedShow">
      <div class="data3"><span>关联信息</span></div>
      <div class="data3_con">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
           <tr>
             <td width="10%" class="label">集 中 器：</td>
             <td width="17%" class="dom">
                <logic:notEqual name="tgId" value="">
                   <select name="termId" id="termId">
                      <option value="">未接集中器</option>
                   </select>
                </logic:notEqual>
                <logic:equal name="tgId" value="">
                   <select name="termId" id="termId">
                      <option value="">未接集中器</option>
                   </select>
                </logic:equal>
                <input type="button" value="..." onclick="addTerminal();" style="width:22px;cursor: pointer;"/>
             </td>
             <td width="10%" class="label">采 集 器：</td>
             <td width="17%" class="dom">
                <select name="gmId" id="gmId"></select>
             </td>
             <td width="17%" class="label"><font color="red">注：两层式无采集器</font></td>
             <td>&nbsp;</td>
           </tr>
           <tr>
             <td width="10%" class="label">变 压 器：</td>
             <td width="17%" class="dom">
                 <select name="tranId" id="tranId">
                   <option value="">未接变压器</option>
                 </select>  
             </td>
           </tr>
         </table>
      </div>
    </div>
  </div>
  </div>
    <div class="guidePanel">
      <input type="button" id="last" value="上一步" class="input1" onclick="lastStep();" />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="c_button" value="完 成" class="input1" />
    </div>
  </div>
</div>
</body>
</html>