<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>专变用户档案录入第二步</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/loading.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/xtheme-gray.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.dataForAjax.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/loading.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/ext/ext-all.js"></script>
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';
show_loading();
$(document).ready(function(){
  selectInit();
  remove_loading();
});
//上一步
function lastStep(){
  //window.location.href=contextPath+"/jsp/archive/archiveType.jsp";
  var url=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=archiveType&selectType=1";
  window.location.href=url;
}
//下一步
function nextStep(){
  var url=contextPath+"/archive/addBigCustAction.do?action=addBigCustInfo";
   if(custCheck()){
     $("#next").attr("disabled",true);
     var data = getFormData("form");
     if(data){
         $.ajax({
             type:'POST',
             url:url,
             data:data,
             dataType:'json',
             success:function(json){
                 var msg=json['msg'];
                 $("#next").attr("disabled",false);
                 if(msg=="1"){
                    window.location.href=contextPath+"/archive/addBigCustAction.do?action=showDeviceInfoByCustID";
                 }else if(msg=="2"){
                    alert("该用户已经存在");
                 }else{
                    alert("保存失败");
                 }
             }
         });
     }else{
       $("#next").attr("disabled",false);
     }
   }
}
//完成
function finish(){
    var url=contextPath+"/archive/addBigCustAction.do?action=addBigCustInfo";
   if(custCheck()){
     $("#finish").attr("disabled",true);
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
                    alert("保存成功！");
                    if(confirm("是否继续新增用户")==true){
                      window.location.href=contextPath+"/jsp/archive/addCustomer.jsp";
                    }else{
                      //window.location.href=contextPath+"/jsp/archive/archiveType.jsp"; 
                      window.location.href=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=archiveType&selectType=1";
                    }
                 }else if(msg=="2"){
                    alert("该用户已经存在");
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
   $("#orgNo").val('${object_cust.orgNo}');
   $("#isMulLine").val(${object_cust.isMulLine});
   //$("#tradeSort").val('${object_cust.tradeSort}');
   tradeSortComboBox.setValue('${object_cust.tradeSort}');
   $("#elecAppType").val(${object_cust.elecAppType});
   $("#loadChar").val(${object_cust.loadChar});
   $("#highPowerType").val(${object_cust.highPowerType});
   $("#voltGrade").val(${object_cust.voltGrade});
   $("#shiftNo").val(${object_cust.shiftNo});
   $("#sar").val(${object_cust.sar});
   $("#custStatus").val(${object_cust.custStatus});
}
</script>
</head>
<body>
<div id="body">
  <jsp:include page="archiveTabs.jsp" flush="true">
    <jsp:param value="1" name="pageType"/>
  </jsp:include>
  <div id="main">
    <div class="tab"><em>专变用户基本信息</em></div>
    <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 123));">
      <div class="main">
        <div id="form">
        <input type="hidden" name="custId" value="${object_cust.custId}"/>
        <table border="0" cellpadding="0" cellspacing="0" width="900" align="center">
          <tr>
            <td width="13%" class="label"><font color="red">* </font>户　号：</td>
            <td width="20%" class="dom"><input type="text" id="custNo" name="custNo" value="${object_cust.custNo}" /></td>
            <td width="13%" class="label"><font color="red">* </font>户　名：</td>
            <td width="54%" colspan="3" class="dom2"><input type="text" id="custName" name="custName" value="${object_cust.custName}" /></td>
          </tr>
          <tr>
            <td class="label"><font color="red">* </font>供电单位：</td>
            <td class="dom">
              <peis:selectlist name="orgNo" sql="SL_COMMON_0001"/>
            </td>
            <td class="label">用电地址：</td>
            <td colspan="3" class="dom2"><input type="text" id="custAddr" name="custAddr" value="${object_cust.custAddr}" /></td>
          </tr>
          <tr>
            <td width="13%" class="label">合同容量：</td>
            <td width="20%" class="dom"><input type="text" id="contractCapa" name="contractCapa" value="<fmt:formatNumber value="${object_cust.contractCapa}" pattern="#0.0000#"  minFractionDigits="0"/>" style="width: 115px;" /> kVA</td>
            <td width="13%" class="label">运行容量：</td>
            <td width="20%" class="dom"><input type="text" id="runCapa" name="runCapa" value="<fmt:formatNumber value="${object_cust.runCapa}" pattern="#0.0000#"  minFractionDigits="0"/>" style="width: 115px;" /> kVA</td>
            <td width="13%" class="label">保安容量：</td>
            <td width="21%" class="dom"><input type="text" id="secuCapa" name="secuCapa" value="<fmt:formatNumber value="${object_cust.secuCapa}" pattern="#0.0000#"  minFractionDigits="0"/>" style="width: 115px;" /> kVA</td>
          </tr>
          <tr>
            <td class="label">供电电源：</td>
            <td class="dom">
              <peis:selectlist name="isMulLine" sql="SL_ARCHIVE_0038"/>
            </td>
            <td class="label">行　业：</td>
            <td class="dom">
              <peis:selectlist name="tradeSort" sql="SL_ARCHIVE_0039" associate="true"/>
            </td>
            <td class="label">用电类别：</td>
            <td class="dom">
              <peis:selectlist name="elecAppType" sql="SL_ARCHIVE_0032"/>
            </td>
          </tr>
          <tr>
            <td class="label">高能耗类别：</td>
            <td class="dom">
              <peis:selectlist name="highPowerType" sql="SL_ARCHIVE_0040"/>
            </td>
            <td class="label">负荷性质：</td>
            <td class="dom">
              <peis:selectlist name="loadChar" sql="SL_ARCHIVE_0041"/>
            </td>
            <td class="label">电压等级：</td>
            <td class="dom">
              <peis:selectlist name="voltGrade" sql="SL_ARCHIVE_0007"/>
            </td>
          </tr>
          <tr>
            <td class="label">功率因数：</td>
            <td class="dom">
              <input type="text" name="powerFactor" value="<fmt:formatNumber value="${object_cust.powerFactor}" pattern="#0.0000#"  minFractionDigits="0"/>"/>
            </td>
            <td class="label">生产班次：</td>
            <td class="dom">
              <peis:selectlist name="shiftNo" sql="SL_ARCHIVE_0042"/>
            </td>
            <td class="label">行 政 区：</td>
            <td class="dom">
              <peis:selectlist name="sar" sql="SL_ARCHIVE_0002"/>
            </td>
          </tr>
          <tr>
            <td class="label">状　态：</td>
            <td class="dom">
              <peis:selectlist name="custStatus" sql="SL_ARCHIVE_0033"/>
            </td>
            <td class="label">联 系 人：</td>
            <td class="dom">
              <input type="text" name="contact" id="contact" value="${object_cust.contact}"/>
            </td>
            <td class="label">联系电话：</td>
            <td class="dom">
              <input type="text" name="phone" id="phone" value="${object_cust.phone}"/>
            </td>
          </tr>
          <tr>
            <td class="label">抄 表 日：</td>
            <td class="dom">
              <input type="text" name="rmDay" id="rmDay" value="${object_cust.rmDay}"/>
            </td>
            <td class="label">抄表区号：</td>
            <td class="dom">
              <input type="text" name="rmSectNo" id="rmSectNo" value="${object_cust.rmSectNo}"/>
            </td>
            <td class="label">抄表顺序号：</td>
            <td class="dom">
              <input type="text" name="rmSn" id="rmSn" value="${object_cust.rmSn}"/>
            </td>
          </tr>
          <tr>
            <td align="right" rowspan="2" class="label">备　注：</td>
            <td class="dom">
              <textarea class="input_textarea3" name="remark">${object_cust.remark}</textarea>
            </td>
            <td align="right" class="label">
            <br/>厂 休 日：</td>
            <td colspan="3" rowspan="2">
              <div>
                <span><input type="checkbox" name="isSpecial" value="${object_cust.isSpecial!="" ? "1" : "0"}" ${object_cust.isSpecial==1 ? "checked" : ""}/></span>
                <span>专线用户</span>
                <span><input type="checkbox" name="isEph" value="${object_cust.isEph!="" ? "1" : "0"}" ${object_cust.isEph==1 ? "checked" : ""}/></span>
                <span>重点用户</span>
              </div>
              <input type="checkbox" name="holiday" id="holiday" value="1"/>周一
              <input type="checkbox" name="holiday" id="holiday" value="2"/>周二
              <input type="checkbox" name="holiday" id="holiday" value="3"/>周三
              <input type="checkbox" name="holiday" id="holiday" value="4"/>周四
              <input type="checkbox" name="holiday" id="holiday" value="5"/>周五
              <input type="checkbox" name="holiday" id="holiday" value="6"/>周六
              <input type="checkbox" name="holiday" id="holiday" value="7"/>周日
               <input type="hidden" name="holidays" id="holidays" value="${object_cust.holiday}"/>
              <script type="text/javascript">
                var holidays=$('#holidays').val();
                if(holidays!=''){
                   var holidayArray=holidays.split(",");
                   for(var i=0;i<holidayArray.length;i++){
                   var index=holidayArray[i]-1;
                   holiday[index].checked="true";
                 }
               }
              </script>
            </td>
          </tr>
        </table>
        </div>
      </div>
    </div>
    <div class="guidePanel">
      <input type="button" id="last" value="上一步" class="input1" onclick="lastStep();" />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="next" value="下一步" class="input1" onclick="nextStep();" />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="finish" value="完 成" class="input1" onclick="finish();" />
    </div>
  </div>
</div>
</body>
</html>
