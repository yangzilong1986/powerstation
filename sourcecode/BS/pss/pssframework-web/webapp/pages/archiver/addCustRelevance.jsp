<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<%@include file="../common/loading.jsp"%>

<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>专变用户档案录入第三步</title>
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/ext-all.css" />
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/xtheme-gray.css" />
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/loading.css" />
  <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.dataForAjax.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/loading.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/ext/ext-base.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/ext/ext-all.js"></script>
  <script type="text/javascript">
var contextPath = '<peis:contextPath/>';
//初始化加载
$(function(){
selectInit();
inputAndSelectInit();
})
//打开新增电表页面
function openMeter(){
   var url=contextPath+"/jsp/archive/addMeterInfo.jsp";
   top.showDialogBox("电表信息录入", url, 575, 900);
}
//打开新增开关页面
function openSwitch(){
   var url=contextPath+"/jsp/archive/addSwitch.jsp";
   top.showDialogBox("开关信息录入", url, 575, 900);
}
//打开新增终端页面
function openTerm(){
   var url=contextPath+"/archive/terminalAction2.do?action=showTermInBigCust";
   top.showDialogBox("终端信息录入", url, 575, 960);
}
//打开新增变压器页面
function openTransformer(){
   var url=contextPath+"/jsp/archive/transformer.jsp";
   top.showDialogBox("变压器信息录入", url, 575, 960);
}
//上一步
function lastStep(){
  var bigCustFlag=$("input[name='bigCustFlag']").val();
  if(bigCustFlag=="1"){//专变用户编辑
    //window.location.href = contextPath + "/archive/bigCustListQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0041&pageRows=20";
    //window.location.href = contextPath + "/jsp/archive/selectCustomer.jsp";
    history.back();
  }else{//专变用户录入
    var custId=$("input[name='custId']").val();
    window.location.href=contextPath+"/archive/addBigCustAction.do?action=showCustInfo&custId="+custId;
  }
}
//完成
function finish(){
   var bigCustFlag=$("input[name='bigCustFlag']").val();
   if(bigCustFlag=="1"){//专变用户编辑
     var url=contextPath+"/archive/addBigCustAction.do?action=addBigCustInfo"; //保存专变用户信息
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
                          if(confirm("是否继续维护用户")==true){
                             //window.location.href = contextPath + "/archive/bigCustListQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0041&pageRows=20";
                            window.location.href = contextPath + "/jsp/archive/selectCustomer.jsp";
                          }else{
                             //window.location.href=contextPath+"/jsp/archive/archiveTypeUpdate.jsp";
                             window.location.href=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=archiveTypeUpdate&selectType=0";
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
   }else{//专变用户录入
     if(confirm("是否继续新增用户")==true){
        window.location.href=contextPath+"/jsp/archive/addCustomer.jsp";
     }else{
        //window.location.href=contextPath+"/jsp/archive/archiveType.jsp";
        window.location.href=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=archiveType&selectType=1";
     }
   }
}
//打开变压器编辑页面
function showTran(tranId){
   var url=contextPath+"/archive/tranAction.do?action=showTranByLowCustNew&tranId="+tranId+"&flag=2&r=" + parseInt(Math.random() * 1000);
   top.showDialogBox("变压器信息编辑",url, 575, 960);
}
//打开电表编辑页面
function showMeter(mpId){
   var url=contextPath+"/archive/meterAction.do?action=showMeterByCust&mpId="+mpId+"&r=" + parseInt(Math.random() * 1000);
   top.showDialogBox("电表信息编辑",url, 575, 960);
}
//打开开关编辑页面
function showSwitch(termSwId){
   var url=contextPath+"/archive/switchAction.do?action=showSwitchByCust&termSwId="+termSwId+"&r=" + parseInt(Math.random() * 1000);
   top.showDialogBox("开关信息编辑",url, 575, 960);
}
//打开终端编辑页面
function showTerm(termId){
   var url=contextPath+"/archive/terminalAction2.do?action=showTermInBigCust&termId="+termId+"&r=" + parseInt(Math.random() * 1000);
   top.showDialogBox("终端信息编辑", url, 575, 960);
}
//删除变压器
function delteTran(tranId){
  var url=contextPath+"/archive/tranAction.do?action=deleteTran&tranId="+tranId;
  if(confirm("确定要删除该变压器?")){
         $.ajax({
          url: url,
          dataType:'json',
          cache: false,
          success: function(json){
            var msg=json['msg'];
             if(msg=="1"){
              alert("删除成功");
              //window.location.href=contextPath+"/archive/addBigCustAction.do?action=showDeviceInfoByCustID";
              //动态加载用户关联设备信息
              loadCustRelevevance();
              }else if(msg=="3"){
                  alert("变压器下存在终端（集中器）或电表，不允许删除");
              }
              else{
                 alert("删除失败");
              }
          }
        });
  }
}
//删除开关
function deleteSwitch(termSwId){
  var url=contextPath+"/archive/switchAction.do?action=deleteSwitch&termSwId="+termSwId;
  if(confirm("确定要删除该开关?")){
           $.ajax({
            url: url,
            dataType:'json',
            cache: false,
            success: function(json){
              var msg=json['msg'];
               if(msg=="1"){
                alert("删除成功");
                //window.location.href=contextPath+"/archive/addBigCustAction.do?action=showDeviceInfoByCustID";
                //动态加载用户关联设备信息
                 loadCustRelevevance();
                }else{
                   alert("删除失败");
                }
            }
    });
  }
}
 //删除电表
   function deleteMeter(mpId){
     var url=contextPath+"/archive/meterAction.do?action=deleteTotalMeter&mpId="+mpId;
     if(confirm("确定要删除该电表?")){
             $.ajax({
            url: url,
            dataType:'json',
            cache: false,
            success: function(json){
              var msg=json['msg'];
               if(msg=="1"){
                alert("删除成功");
                //window.location.href=contextPath+"/archive/addBigCustAction.do?action=showDeviceInfoByCustID";
                //动态加载用户关联设备信息
               loadCustRelevevance();
                }
                else{
                   alert("删除失败");
                }
            }
       });
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
                //动态加载用户关联设备信息
                loadCustRelevevance();
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
  //控件样式初始化
  function inputAndSelectInit(){
  var bigCustFlag=$("input[name='bigCustFlag']").val();
   if(bigCustFlag=="1"){//专变用户编辑
     $("input").attr("disabled","disabled").each(function(){
         $(this).attr("disabled","");
     });
     $("select").attr("disabled","disabled").each(function(){
         $(this).attr("disabled","");
     });
     $("textarea").attr("disabled","disabled").each(function(){
         $(this).attr("disabled","");
     });
     //tradeSortComboBox.disabled = false;
     //$('#tab_1 > a').html("档案维护第三步");
   }else{
    // $('#tab_1 > a').html("档案录入第三步");
   }
  }
  //select框初始化
function selectInit(){
   $("#orgNo").val('${object_cust.orgNo}');
   $("#isMulLine").val(${object_cust.isMulLine});
   $("#tradeSort").val('${object_cust.tradeSort}');
   //tradeSortComboBox.setValue('${object_cust.tradeSort}');
   //tradeSortComboBox.disabled = true;
   $("#elecAppType").val(${object_cust.elecAppType});
   $("#loadChar").val(${object_cust.loadChar});
   $("#highPowerType").val(${object_cust.highPowerType});
   $("#voltGrade").val(${object_cust.voltGrade});
   $("#shiftNo").val(${object_cust.shiftNo});
   $("#sar").val(${object_cust.sar});
   $("#custStatus").val(${object_cust.custStatus});
}
//调用json方法获取list
function getJsonObjectList(htmlId,className,methodName,objectType){
  var objectId=$("input[name='custId']").val();
  var params = {
                 "objectId":objectId,
                 "methodsName":methodName,
                 "className":className
			   };
   var url=contextPath+"/archive/commAction.do?action=getJsonByListData&"+ jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
   $.ajax({
        url: url,
        dataType:'json',
        cache: false,
        success: function(json){
           var htmlTbody="";
           $(json).each(function(i){ //遍历结果数组 
               htmlTbody+=buildTbody(objectType,json[i]);
		   }); 
		   $("#"+htmlId).html(htmlTbody);
        }
    });
}
//动态加载用户关联设备信息
function loadCustRelevevance(){
  show_loading();
  getJsonObjectList("tranDataBody","CustomerService","getTransByCustId","12");//加载变压器列表
  getJsonObjectList("meterDataBody","CustomerService","getMetersByCustId","23");//加载电表列表
  getJsonObjectList("switchDataBody","CustomerService","getSwitchsByCustId","10");//加载开关列表
  getJsonObjectList("termDataBody","CustomerService","getTermsByCustId","5");//加载终端列表
  getJsonObjectList("gpDataBody","CustomerService","getGpsByCustId","7");//加载测量点列表
  remove_loading();
}
</script>
 </head>
 <body>
  <div id="body">
   <jsp:include page="archiveTabs.jsp" flush="true">
    <jsp:param value="1" name="pageType"/>
   </jsp:include>
   <div id="main">
    <div class="tab">
     <em>专变用户信息</em>
    </div>
    <div class="tab_con" style="height: expression((( document.documentElement.clientHeight || document.body.clientHeight) -   123 ) );">
     <div class="main">
      <div id="form">
      <input type="hidden" name="custId" value="${object_cust.custId}" />
      <!-- //标记位，用来区分专变用户录入还是编辑 -->
      <input type="hidden" name="bigCustFlag" value="${bigCustFlag}" />
      <table border="0" cellpadding="0" cellspacing="0" width="900" align="center">
       <tr>
        <td width="13%" class="label">
         <font color="red">* </font>户 号：
        </td>
        <td width="20%" class="dom">
         <input type="text" id="custNo" name="custNo" value="${object_cust.custNo}" disabled="disabled" />
        </td>
        <td width="13%" class="label">
         <font color="red">* </font>户 名：
        </td>
        <td width="54%" colspan="3" class="dom2">
         <input type="text" id="custName" name="custName" value="${object_cust.custName}" disabled="disabled" />
        </td>
       </tr>
       <tr>
        <td class="label">
         <font color="red">* </font>供电单位：
        </td>
        <td class="dom">
         <peis:selectlist name="orgNo" sql="SL_COMMON_0001" extendProperty="disabled='disabled'" />
        </td>
        <td class="label">
         用电地址：
        </td>
        <td colspan="3" class="dom2">
         <input type="text" id="custAddr" name="custAddr" value="${object_cust.custAddr}" disabled="disabled" />
        </td>
       </tr>
       <tr>
        <td width="13%" class="label">
         合同容量：
        </td>
        <td width="20%" class="dom">
         <input type="text" id="contractCapa" name="contractCapa" value="<fmt:formatNumber value="${object_cust.contractCapa}" pattern="#0.0000#"  minFractionDigits="0"/>" disabled="disabled" style="width: 110px;" />
         kVA
        </td>
        <td width="13%" class="label">
         运行容量：
        </td>
        <td width="20%" class="dom">
         <input type="text" id="runCapa" name="runCapa" value="<fmt:formatNumber value="${object_cust.runCapa}" pattern="#0.0000#"  minFractionDigits="0"/>" disabled="disabled" style="width: 110px;" />
         kVA
        </td>
        <td width="13%" class="label">
         保安容量：
        </td>
        <td width="21%" class="dom">
         <input type="text" id="secuCapa" name="secuCapa" value="<fmt:formatNumber value="${object_cust.secuCapa}" pattern="#0.0000#"  minFractionDigits="0"/>" disabled="disabled" style="width: 110px;" />
         kVA
        </td>
       </tr>
       <tr>
        <td class="label">
         供电电源：
        </td>
        <td class="dom">
         <peis:selectlist name="isMulLine" sql="SL_ARCHIVE_0038" extendProperty="disabled='disabled'" /></td>
        <td class="label">
         行 业：
        </td>
        <td class="dom">
         <peis:selectlist name="tradeSort" sql="SL_ARCHIVE_0039" extendProperty="disabled='disabled'"/>
        </td>
        <td class="label">
         用电类别：
        </td>
        <td class="dom">
         <peis:selectlist name="elecAppType" sql="SL_ARCHIVE_0032" extendProperty="disabled='disabled'" />
        </td>
       </tr>
       <tr>
        <td class="label">
         高能耗类别：
        </td>
        <td class="dom">
         <peis:selectlist name="highPowerType" sql="SL_ARCHIVE_0040" extendProperty="disabled='disabled'" />
        </td>
        <td class="label">
         负荷性质：
        </td>
        <td class="dom">
         <peis:selectlist name="loadChar" sql="SL_ARCHIVE_0041" extendProperty="disabled='disabled'" />
        </td>
        <td class="label">
         电压等级：
        </td>
        <td class="dom">
         <peis:selectlist name="voltGrade" sql="SL_ARCHIVE_0007" extendProperty="disabled='disabled'" />
        </td>
       </tr>
       <tr>
        <td class="label">
         功率因数：
        </td>
        <td class="dom">
         <input type="text" name="powerFactor" value="<fmt:formatNumber value="${object_cust.powerFactor}" pattern="#0.0000#"  minFractionDigits="0"/>" disabled="disabled" />
        </td>
        <td class="label">
         生产班次：
        </td>
        <td class="dom">
         <peis:selectlist name="shiftNo" sql="SL_ARCHIVE_0042" extendProperty="disabled='disabled'" />
        </td>
        <td class="label">
         行 政 区：
        </td>
        <td class="dom">
         <peis:selectlist name="sar" sql="SL_ARCHIVE_0002" extendProperty="disabled='disabled'" />
        </td>
       </tr>
       <tr>
        <td class="label">
         状 态：
        </td>
        <td class="dom">
         <peis:selectlist name="custStatus" sql="SL_ARCHIVE_0033" extendProperty="disabled='disabled'" />
        </td>
        <td class="label">
         联 系 人：
        </td>
        <td class="dom">
         <input type="text" name="contact" id="contact" value="${object_cust.contact}" disabled="disabled" />
        </td>
        <td class="label">
         联系电话：
        </td>
        <td class="dom">
         <input type="text" name="phone" id="phone" value="${object_cust.phone}" disabled="disabled" />
        </td>
       </tr>
       <tr>
        <td class="label">
         抄 表 日：
        </td>
        <td class="dom">
         <input type="text" name="rmDay" id="rmDay" value="${object_cust.rmDay}" disabled="disabled" />
        </td>
        <td class="label">
         抄表区号：
        </td>
        <td class="dom">
         <input type="text" name="rmSectNo" id="rmSectNo" value="${object_cust.rmSectNo}" disabled="disabled" />
        </td>
        <td class="label">
         抄表顺序号：
        </td>
        <td class="dom">
         <input type="text" name="rmSn" id="rmSn" value="${object_cust.rmSn}" disabled="disabled" />
        </td>
       </tr>
       <tr>
        <td align="right" rowspan="2" class="label">
         备 注：
        </td>
        <td class="dom">
         <textarea class="input_textarea3" name="remark" disabled="disabled">${object_cust.remark}</textarea>
        </td>
        <td align="right" class="label">
         <br />
         厂 休 日：
        </td>
        <td colspan="3" rowspan="2">
         <div>
          <span><input type="checkbox" name="isSpecial" value="${object_cust.isSpecial!=" " ? "1" : "0"}" ${object_cust.isSpecial==1 ? "checked" : ""} disabled="disabled" />
          </span>
          <span>专线用户</span>
          <span><input type="checkbox" name="isEph" value="${object_cust.isEph!="" ? "1" : "0"}" ${object_cust.isEph==1 ? "checked" : ""} disabled="disabled"/></span>
          <span>重点用户</span>
         </div>
         <input type="checkbox" name="holiday" value="1" disabled="disabled" />
         周一
         <input type="checkbox" name="holiday" value="2" disabled="disabled" />
         周二
         <input type="checkbox" name="holiday" value="3" disabled="disabled" />
         周三
         <input type="checkbox" name="holiday" value="4" disabled="disabled" />
         周四
         <input type="checkbox" name="holiday" value="5" disabled="disabled" />
         周五
         <input type="checkbox" name="holiday" value="6" disabled="disabled" />
         周六
         <input type="checkbox" name="holiday" value="7" disabled="disabled" />
         周日
         <input type="hidden" name="holidays" id="holidays" value="${object_cust.holiday}" />
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
     <div class="data2">
      <span>变压器信息</span>
      <h1>
       <a href="#" onclick="openTransformer(); return false;"><img src="<peis:contextPath/>/img/bt_add.png" width="19" height="19" class="mgt5" />
       </a>
      </h1>
     </div>
     <div class="data2_con">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
       <thead>
        <tr>
         <th align="center">
          资产号
         </th>
         <th align="center">
          名称
         </th>
         <th align="center">
          容量
         </th>
         <th align="center">
          型号
         </th>
         <th align="center">
          安装地址
         </th>
         <th align="center">
          操作
         </th>
        </tr>
       </thead>
       <tbody id="tranDataBody">
        <logic:present name="object_tran">
         <logic:iterate id="tran" name="object_tran">
          <tr style="cursor: pointer;">
           <td align="center">
            <bean:write name="tran" property="col1" />
           </td>
           <td>
            <bean:write name="tran" property="col2" />
           </td>
           <td align="center">
            <bean:write name="tran" property="col3" />
           </td>
           <td align="center">
            <bean:write name="tran" property="col4" />
           </td>
           <td>
            <bean:write name="tran" property="col5" />
           </td>
           <td align="center">
            <a href="javascript:showTran('<bean:write name="tran" property="col6"/>');"><bean:message key="global.edit" />
            </a>|
            <a href="javascript:delteTran('<bean:write name="tran" property="col6"/>')"><bean:message key="global.delete" />
            </a>
           </td>
          </tr>
         </logic:iterate>
        </logic:present>
       </tbody>
      </table>
     </div>
     <div class="data2">
      <span>电表信息</span>
      <h1>
       <a href="#" onclick="openMeter(); return false;"><img src="<peis:contextPath/>/img/bt_add.png" width="19" height="19" class="mgt5" />
       </a>
      </h1>
     </div>
     <div class="data2_con">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
       <thead>
        <tr>
         <th>
          计量点名称
         </th>
         <th>
          资产编号
         </th>
         <th>
          表地址
         </th>
         <th>
          采集终端
         </th>
         <th>
          表类型
         </th>
         <th>
          设备厂家
         </th>
         <th>
          运行状态
         </th>
         <th>
          操作
         </th>
        </tr>
       </thead>
       <tbody id="meterDataBody">
        <logic:present name="object_meter">
         <logic:iterate id="meter" name="object_meter">
          <tr style="cursor: pointer;">
           <td align="center">
            <bean:write name="meter" property="col1" />
           </td>
           <td>
            <bean:write name="meter" property="col2" />
           </td>
           <td align="center">
            <bean:write name="meter" property="col3" />
           </td>
           <td align="center">
            <bean:write name="meter" property="col4" />
           </td>
           <td align="center">
            <bean:write name="meter" property="col5" />
           </td>
           <td align="center">
            <bean:write name="meter" property="col6" />
           </td>
           <td align="center">
            <bean:write name="meter" property="col7" />
           </td>
           <td align="center">
            <a href="javascript:showMeter('<bean:write name="meter" property="col8"/>');"><bean:message key="global.edit" />
            </a>|
            <a href="javascript:deleteMeter('<bean:write name="meter" property="col8"/>')"><bean:message key="global.delete" />
            </a>
           </td>
          </tr>
         </logic:iterate>
        </logic:present>
       </tbody>
      </table>
     </div>
     <div class="data2">
      <span>采集点信息</span>
     </div>
     <div class="data2_con">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
       <thead>
        <tr>
         <th>
          计量点序号
         </th>
         <th>
          采集终端
         </th>
         <th>
          性质
         </th>
         <th>
          CT
         </th>
         <th>
          PT
         </th>
         <th>
          资产编号
         </th>
        </tr>
       </thead>
       <tbody id="gpDataBody">
        <logic:present name="object_gp">
         <logic:iterate id="gp" name="object_gp">
          <tr style="cursor: pointer;">
           <td align="center">
            <bean:write name="gp" property="col1" />
           </td>
           <td>
            <bean:write name="gp" property="col2" />
           </td>
           <td align="center">
            <bean:write name="gp" property="col3" />
           </td>
           <td align="center">
            <bean:write name="gp" property="col4" />
           </td>
           <td>
            <bean:write name="gp" property="col5" />
           </td>
           <td>
            <bean:write name="gp" property="col7" />
           </td>
          </tr>
         </logic:iterate>
        </logic:present>
       </tbody>
      </table>
     </div>
     <div class="data2">
      <span>开关信息</span>
      <h1>
       <a href="#" onclick="openSwitch(); return false;"><img src="<peis:contextPath/>/img/bt_add.png" width="19" height="19" class="mgt5" />
       </a>
      </h1>
     </div>
     <div class="data2_con">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
       <thead>
        <tr>
         <th>
          开关序号
         </th>
         <th>
          开关名称
         </th>
         <th>
          所属终端
         </th>
         <th>
          开关轮次
         </th>
         <th>
          开关容量
         </th>
         <th>
          开关属性
         </th>
         <th>
          开关状态
         </th>
         <th>
          操作
         </th>
        </tr>
       </thead>
       <tbody id="switchDataBody">
        <logic:present name="object_term_switch">
         <logic:iterate id="term_switch" name="object_term_switch">
          <tr style="cursor: pointer;">
           <td align="center">
            <bean:write name="term_switch" property="col1" />
           </td>
           <td>
            <bean:write name="term_switch" property="col2" />
           </td>
           <td align="center">
            <bean:write name="term_switch" property="col3" />
           </td>
           <td align="center">
            <bean:write name="term_switch" property="col4" />
           </td>
           <td>
            <bean:write name="term_switch" property="col5" />
           </td>
           <td>
            <bean:write name="term_switch" property="col6" />
           </td>
           <td>
            <bean:write name="term_switch" property="col7" />
           </td>
           <td align="center">
            <a href="javascript:showSwitch('<bean:write name="term_switch" property="col8"/>');"><bean:message key="global.edit" />
            </a>|
            <a href="javascript:deleteSwitch('<bean:write name="term_switch" property="col8"/>')"><bean:message key="global.delete" />
            </a>
           </td>
          </tr>
         </logic:iterate>
        </logic:present>
       </tbody>
      </table>
     </div>
     <div class="data2">
      <span>终端列表</span>
      <h1>
       <a href="#" onclick="openTerm(); return false;"><img src="<peis:contextPath/>/img/bt_add.png" width="19" height="19" class="mgt5" />
       </a>
      </h1>
     </div>
     <div class="data2_con">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
       <thead>
        <tr>
         <th>
          资产号
         </th>
         <th>
          逻辑地址
         </th>
         <th>
          终端类型
         </th>
         <th>
          相线
         </th>
         <th>
          设备厂家
         </th>
         <th>
          运行状态
         </th>
         <th>
          操作
         </th>
        </tr>
       </thead>
       <tbody id="termDataBody">
        <logic:present name="object_term">
         <logic:iterate id="term" name="object_term">
          <tr style="cursor: pointer;">
           <td align="center">
            <bean:write name="term" property="col1" />
           </td>
           <td>
            <bean:write name="term" property="col2" />
           </td>
           <td align="center">
            <bean:write name="term" property="col3" />
           </td>
           <td align="center">
            <bean:write name="term" property="col4" />
           </td>
           <td>
            <bean:write name="term" property="col5" />
           </td>
           <td>
            <bean:write name="term" property="col6" />
           </td>
           <td align="center">
            <a href="javascript:showTerm('<bean:write name="term" property="col7"/>');"><bean:message key="global.edit" />
            </a>|
            <a href="javascript:deleteTerm('<bean:write name="term" property="col7"/>')"><bean:message key="global.delete" />
            </a>
           </td>
          </tr>
         </logic:iterate>
        </logic:present>
       </tbody>
      </table>
     </div>
    </div>
    <div class="guidePanel">
     <input type="button" id="last" value="上一步" class="input1" onclick="lastStep();" />
     &nbsp;&nbsp;&nbsp;&nbsp;
     <input type="button" id="finish" value="完 成" class="input1" onclick="finish();" />
    </div>
   </div>
  </div>
 </body>
</html>
