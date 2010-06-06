<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>低压用户信息</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/greybox.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.dataForAjax.js"></script>
<script type="text/javascript">
var contextPath='<peis:contextPath/>';

//初始化加载
$(document).ready(function(){
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
        }
        else{
            $("#meterSnLable").hide();
            $("#meterSnDom").hide();
        }
    });
    $('#commMode').change(showRepeat);
   
    loadSelect();
    meterSnInit();
});

//初始化重点表序号
function meterSnInit(){
    if($('#isEph').attr("checked")==true){
        $("#meterSnLable").show();
        $("#meterSnDom").show();
    }
    else{
        $("#meterSnLable").hide();
        $("#meterSnDom").hide();
    }
}

//保存低压用户信息
function save(){
    var url = contextPath + "/archive/addLowCustInfoAction.do?action=addLowCustInfo";
    if(lowCustCheck()){
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
                    }
                    else if(msg=="2"){
                        alert("该用户已经存在");
                    }
                    else if(msg=="3"){
                        alert("表号已经存在");
                    }
                    else{
                        alert("保存失败");
                    }
                }
            });
        }
    }
}

//当通讯方式为载波信息表，显示中继类型
function showRepeat(){
    if($("#commMode").val()==2){
        $("#repeatTypeLable").show();
        $("#repeatTypeDom").show();
    }
    else{
        $("#repeatTypeLable").hide();
        $("#repeatTypeDom").hide();
    }
}

//打开中继器页面
function openRepeat(){
    var relayType = $('#relayType').val();
    var url = contextPath + "/jsp/archive/addRepeat.jsp?relayType=" + relayType;
    showDialogBox("中继器类型维护", url, 495, 800);
}

//加载select
function loadSelect(){
    //alert(${object_gp.termId} + ", " + '${gmAddr}');
    var termId = ${object_gp.termId};
    var params = {"action": "getScript",
                  "termId": termId,
                  "gmAddr": '${gmAddr}'
                 };
    var url = contextPath + "/do/para/ammeterParaSet.do";
    $.ajax({
        type: "POST",
        url: url,
        cache: false,
        data: params,
        dateType:'script',
        success: function(data) {
            eval(data);
        }
    });
}
</script>
</head>
<body style="overflow: hidden;">
<div id="main" style="height: 100%;">
  <div id="form">
  <div class="tab"><em>用户信息</em></div>
  <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 80));">
    <div>
      <div class="data3"><span>基本信息</span></div>
      <div class="data3_con">
        <input type="hidden" name="tgId" value="${tgId}"/>
        <input type="hidden" name="orgNo" value="${orgNo}"/>
        <input type="hidden" name="sar" value="${sar}"/>
        <input type="hidden" name="custId" value="${object_customer.custId}"/>
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
            <td colspan="4"></td>
          </tr>
          <tr>
            <td width="10%" class="label">用电类别：</td>
            <td width="17%" class="dom">
              <peis:selectlist name="elecAppType" sql="SL_ARCHIVE_0032"/>
              <script type="text/javascript">
               $("#elecAppType").val(${object_customer.elecAppType});
              </script>
            </td>
            <td width="10%" class="label">用户状态：</td>
            <td width="17%" class="dom">
              <peis:selectlist name="custStatus" sql="SL_ARCHIVE_0033"/>
              <script type="text/javascript">
               $("#custStatus").val(${object_customer.custStatus});
              </script>
            </td>
            <td colspan="2" align="center">
              <span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
                <input type="checkbox" name="isEph" id="isEph" value="${object_customer.isEph}" ${object_customer.isEph == "1" ? "checked" : ""}/>重点用户
              </span>
            </td>
            <td width="10%" class="label" id="meterSnLable">重点表序号：</td>
            <td width="17%" class="dom" id="meterSnDom">
              <span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
                <select name="masMeterSn" id="masMeterSn">
                  <option value="1201">1201</option>
                  <option value="1202">1202</option>
                  <option value="1203">1203</option>
                  <option value="1204">1204</option>
                  <option value="1205">1205</option>
                  <option value="1206">1206</option>
                </select>
              </span> 
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
                <script type="text/javascript">
                $("#meterType").val(${object_meter.meterType});
                </script>
              </td>
              <td width="10%" class="label">
               <font color="red">* </font>表 序 号：
              </td>
              <td width="23%" class="dom">
               <span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
                 <input type="text" id="gpSn" name="gpSn" value="${object_gp.gpSn}" />
               </span>
              </td>
             </tr>
             <tr>
              <td width="10%" class="label">
               <font color="red">* </font>表 地 址：
              </td>
              <td width="23%" class="dom">
                <span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
                  <input type="text" id="gpAddr" name="gpAddr" value="${object_gp.gpAddr}" />
                </span>
              </td>
              <td width="10%" class="label">
               CT变比：
              </td>
              <td width="23%" class="dom">
                <peis:selectlist name="ctRatio" sql="SL_ARCHIVE_0019"/>
                <script type="text/javascript">
                 $("#ctRatio").val(${object_meter.ctRatio});
                </script>
              </td>
              <td width="10%" class="label">
               PT变比：
              </td>
              <td width="23%" class="dom">
               <peis:selectlist name="ptRatio" sql="SL_ARCHIVE_0020"/>
               <script type="text/javascript">
                 $("#ptRatio").val(${object_meter.ptRatio});
                </script>
              </td>
             </tr>
             <tr>
              <td width="10%" class="label">波 特 率：
              </td>
              <td width="23%" class="dom">
              <peis:selectlist name="btl" sql="SL_ARCHIVE_0023"/>
                <script type="text/javascript">
                 $("#btl").val(${object_meter.btl});
                </script>
              </td>
              <td width="10%" class="label">表 规 约：
              </td>
              <td width="23%" class="dom">
                 <peis:selectlist name="protocolNo" sql="SL_ARCHIVE_0022"/>
                 <script type="text/javascript">
                 $("#protocolNo").val(${object_gp.protocolNo});
                 </script>
              </td>
              <td width="10%" class="label">
               通讯方式：
              </td>
              <td width="23%" class="dom">
                <peis:selectlist name="commMode" sql="SL_ARCHIVE_0010"/>
                <script type="text/javascript">
                 $("#commMode").val(${object_meter.commMode});
                </script>
              </td>
             </tr>
             <tr>
              <td width="10%" class="label">
               电表厂家：
              </td>
              <td width="23%" class="dom">
                <peis:selectlist name="madeFactory" sql="SL_ARCHIVE_0008"/>
                <script type="text/javascript">
                 $("#madeFactory").val('${object_meter.madeFactory}');
                </script>
              </td>
              <td width="10%" class="label">
               拉闸功能：
              </td>
              <td width="23%" class="dom">
                <peis:selectlist name="canBreak" sql="SL_ARCHIVE_0034"/>
                <script type="text/javascript">
                 $("#canBreak").val(${object_meter.canBreak});
                </script>
              </td>
              <td width="10%" class="label">
               初抄相位：
              </td>
              <td width="23%" class="dom">
                 <peis:selectlist name="frPhase" sql="SL_ARCHIVE_0035"/>
                 <script type="text/javascript">
                   $("#frPhase").val(${object_meter.frPhase});
                 </script>
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
               <script type="text/javascript">
                   $("#isPrepay").val(${object_meter.isPrepay});
                 </script>
              </td>
              <td width="10%" class="label">
               装表日期：
              </td>
              <td width="23%" class="dom_date">
               <input type="text" name="mpDate" id="mpDate" value="<fmt:formatDate value="${object_meter.mpDate}" type="date"/>" onfocus="peisDatePicker()" readonly="readonly" />
              </td>
             </tr>
             <tr>
              <td width="10%" class="label" id="repeatTypeLable">
               中继类型：
              </td>
              <td width="23%" class="dom_date" id="repeatTypeDom">
               <peis:selectlist name="relayType" sql="SL_ARCHIVE_0036"/>
               <script type="text/javascript">
                   $("#relayType").val(${object_meterRelay.relayType});
               </script>
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
                <select name="termId" id="termId"></select>
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
                <select name="tranId" id="tranId"></select>
             </td>
           </tr>
         </table>
      </div>
    </div>
  </div>
  <div class="guidePanel">
    <input class="input1" type="button" value="保 存" onclick="save();" />
  </div>
  </div>
</div>
</body>
</html>