<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<html>
<head>
<title>配变/台区用户档案(终端)</title>
<link href='<pss:path type="bgcolor"/>/css/content.css' type="text/css" rel="stylesheet" />
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';

$(document).ready(function() {
  jQuery("#c_button").click(function(){
    submitByAjax();
  })
  $("#deviceLable").hide();
  $("#deviceDom").hide();
  if($("input[name='termId']").val()==''){
    loadParamTblTbody();
  }
  selectInit();
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
  function loadParamTblTbody() {
    var protocolNo = $("#protocolNo").val();
    var commMode = $("#commMode").val();
    var termType = $("#termType").val();
    var params = {"action": "loadParamTblTbodyByAjax",
                  "protocolNo": protocolNo,
                  "commMode": commMode,
                  "termType": termType};
    var url = contextPath + "/archive/terminalAction3.do";
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

function submitByAjax(){
	var url=contextPath+"/archive/terminalAction1.do?action=addTermInfoSecond";
	if(modelTermCheck()) {
	    $("#c_button").attr("disabled",true);
		var data = getFormData("form");
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
                       parent.GB_hide();
                       var url1=contextPath+"/archive/commAction.do?action=clearSessionByTerm";
					      $.ajax({
					        url: url1,
					        cache: false,
					        success: function(html){
					          //top.getMainFrameObj().location.href=contextPath+"/archive/addTgAction.do?action=showDeviceInfoByTgID";
					          top.getMainFrameObj().loadTgRelevevance();
					        }
					      });                 
                       
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
   $("#protocolNo").val(${object_term.protocolNo});
   $("#commMode").val(${object_term.commMode});
   $("#wiringMode").val(${object_term.wiringMode});
   $("#madeFac").val('${object_term.madeFac}');
   $("#termType").val('${object_term.termType}');
   $("#tranId").val('${object_gp.tranId}');
   $("#pr").val(${object_term.pr});
   $("#modelCode").val(${object_term.modelCode});
   //$("#lineId").val(${object_gp.lineId});
   lineIdComboBox.setValue('${object_gp.lineId}');
   $("#commPattern").val(${object_term.commPattern});
   $("#tgId").val(${tgId});
   $("#ctRatio").val(${object_ct});
   $("#ptRatio").val(${object_pt});
   $("#termIdCasc").val(${object_termCascade.termIdCasc});
   $("#machNo").val('${object_term.machNo}');
   $("#channelType").val('${object_term.channelType}');
}
</script>
</head>
<body style="overflow: hidden;">
<div id="main" style="height: 100%;">
  <div class="tab"><em>终端信息</em></div>
  <div class="tab_con" style="height: expression(((     document.documentElement.clientHeight ||     document.body.clientHeight) -       85 ) );">
   <div id="form">
     <input type="hidden" name="tgId" value="${tgId}" />
     <input type="hidden" name="orgNo" value="${orgNo}" />
     <input type="hidden" name="termId" value="${object_term.termId}" />
     <div class="main">
      <table border="0" cellpadding="0" cellspacing="0" width="900" align="center">
        <tr>
         <td width="13%" class="label"><font color="red">* </font>资产编号：</td>
         <td width="20%" class="dom"><input type="text" name="assetNo" id="assetNo" value="${object_term.assetNo}"/></td>
         <td width="13%" class="label"><font color="red">* </font>逻辑地址：</td>
         <td width="20%" class="dom"><input type="text" name="logicalAddr" id="logicalAddr" value="${object_term.logicalAddr}"/></td>
         <td colspan="2"></td>
        </tr>
        <tr>
         <td width="13%" class="label">当前状态：</td> 
         <td  class="dom"> 
           <peis:selectlist name="curStatus" sql="SL_ARCHIVE_0012"/>
         </td>
         <td width="13%" class="label">设备规约：</td> 
         <td  class="dom">
           <peis:selectlist name="protocolNo" sql="SL_ARCHIVE_0058"/>
         </td>
         <td width="13%" class="label">通讯方式：</td>  
         <td  class="dom">
           <peis:selectlist name="commMode" sql="SL_ARCHIVE_0011"/>
         </td>
        </tr>
        <tr>
         <td  class="label">相　线：</td>
         <td  class="dom">
           <peis:selectlist name="wiringMode" sql="SL_ARCHIVE_0027"/>
         </td>
         <td  class="label">设备厂家：</td>
         <td  class="dom">
           <peis:selectlist name="madeFac" sql="SL_ARCHIVE_0008"/>
         </td>
         <td  class="label">终端类型：</td>
         <td width="24%" class="dom">
          <select name="termType" id="termType">
           <option value="03">配变终端</option>
           <option value="04">配变监控终端</option>
          </select>
         </td>
        </tr>
        <tr>
        <td class="label">机器编号：</td>
        <td class="dom">
         <peis:selectlist name="machNo" sql="SL_ARCHIVE_0063" extendProperty="disabled='disabled'"/>
        </td>
        <td class="label">前置机通道：</td>
        <td class="dom">
         <input type="text" name="fepCnl" id="fepCnl"  value="${object_term.fepCnl}" disabled="disabled"/>
        </td> 
        <td class="label">通道类型：</td>
        <td width="24%" class="dom">
         <peis:selectlist name="channelType" sql="SL_ARCHIVE_0064" />
        </td>
       </tr>
        <tr>
         <td class="label">产　权：</td>
         <td class="dom">
	        <peis:selectlist name="pr" sql="SL_ARCHIVE_0016" />
	     </td>       
         <td class="label">安装日期：</td>
         <td class="dom_date">
           <input type="text" id="installDate" name="installDate" value="<fmt:formatDate value="${object_term.installDate}" type="date"/>" onfocus="peisDatePicker()" readonly="readonly" />
         </td>
         <td class="label">入库日期：</td>
         <td class="dom_date">
          <input type="text" name="leaveFacDate" id="leaveFacDate" value="<fmt:formatDate value="${object_term.leaveFacDate}" type="date"/>" onfocus="peisDatePicker()" readonly="readonly" />
         </td>
       </tr>
        <tr>
         <td class="label">安装单位：</td>
         <td class="dom">
          <input type="text" id="constrGang" name="constrGang" value="${object_term.constrGang}"/>
         </td>
         <td  class="label">终端型号：</td>
         <td  class="dom">
          <peis:selectlist name="modelCode" sql="SL_ARCHIVE_0017" />
         </td>
         <td  class="label">SIM卡号：</td>
         <td  class="dom">
          <input type="text" id="simNo" name="simNo" value="${object_term.simNo}" />
         </td>
        </tr>
        <tr>
         <td  colspan="2" align="center">
            <input type="checkbox" name="isac" id="isac" value="${object_term.isac!=" " ? "1" : "0"}" ${object_term.isac==1? "checked" : ""}>接交采
         </td>
         <td class="label">物理地址：</td>
         <td colspan="3" class="dom">
          <input type="text" id="physicsAddr" name="physicsAddr" style="width: 390px" value="${object_term.physicsAddr}"/>
         </td>
        </tr>
       </table>
    </div>
    <div id="ACShow">
      <div class="data3"><span>交采信息</span></div>
      <div class="data3_con">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td class="label">计量点序号：</td>
            <td class="dom">
              <input type="text" id="gpSn" name="gpSn" value="0" readonly/>
            </td>
            <td class="label">供电线路：</td>
            <td class="dom">
              <peis:selectlist name="lineId" sql="SL_ARCHIVE_0018" associate="true"/>
            </td>
            <td colspan="2" align="center">
              <input type="checkbox" id="sucratCptId" name="sucratCptId" value="${object_gp.sucratCptId!=" " ? "1" : "0"}" ${object_gp.sucratCptId==1?"checked" : ""}/>功率累计&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <input type="checkbox" id="computeFlag" name="computeFlag" value="${object_gp.computeFlag!=" " ? "1" : "0"}" ${object_gp.computeFlag==1 ? "checked" : ""}/>电量累计
            </td>
            <td  class="label">变 压 器：</td> 
            <td  class="dom">
              <peis:selectlist name="tranId" sql="SL_ARCHIVE_0021"/>
            </td>
          </tr>
          <tr>
            <td class="label">端 口 号：</td>
            <td class="dom">
              <input type="text" id="port" name="port" value="${object_gp.port }"/>
            </td>
            <td class="label">CT变比：</td>
            <td class="dom">
              <peis:selectlist name="ctRatio" sql="SL_ARCHIVE_0019" />
            </td>
            <td class="label">PT变比：</td>
            <td class="dom">
              <peis:selectlist name="ptRatio" sql="SL_ARCHIVE_0020" />
            </td>
          </tr>
        </table>
      </div>
    </div>
      <div class="data3"><span>级联信息</span></div>
      <div class="data3_con">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tr>
              <td width="20%" class="label">通信模式：</td>
              <td width="30%" class="dom">
                <select name="commPattern" id="commPattern" onchange="showDevice();">
                  <option value="1">主</option>
                  <option value="2">从</option>
                </select>
              </td>
              <td width="20%" class="label" id="deviceLable">下级设备：</td>
              <td width="30%" class="dom" id="deviceDom">
              <logic:present name="termId"><peis:selectlist name="termIdCasc" sql="SL_ARCHIVE_0056" /></logic:present>
              <logic:notPresent name="termId"><peis:selectlist name="termIdCasc" sql="SL_ARCHIVE_0049" /></logic:notPresent>  
                <input type="hidden" name="termIdCascTemp" id="termIdCascTemp" value="${object_termCascade.termIdCasc}" />
              </td>
            </tr>
          </table>
      </div>
    <div id="relatedShow">
      <div class="data3"><span>关联信息</span></div>
      <div class="data3_con">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td align="right" class="lable">电表：</td>
            <logic:present name="object_meter">
	         <logic:iterate id="meter" name="object_meter">
	          <td>
	           <input type="checkbox" <c:if test="${object_term.logicalAddr==meter.col4 &&  meter.col4!=''}">checked</c:if> id="mpIdList" name="mpIdList" value="<bean:write name="meter" property="col8"/>" />
	           <bean:write name="meter" property="col2" />
	           /
	           <bean:write name="meter" property="col1" />
	          </td>
	         </logic:iterate>
	        </logic:present>
          </tr>
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
    <input type="button" id="c_button" value="保 存" class="input1" />
  </div>
</div>
</div>
</body>
</html>