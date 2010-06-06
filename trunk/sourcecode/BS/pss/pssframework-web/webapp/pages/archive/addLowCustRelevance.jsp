<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<%@include file="../common/loading.jsp"%>

<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>低压集抄档案录入第三步</title>
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/loading.css" />
  <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/loading.js"></script>
  <script type="text/javascript">
var contextPath = '<peis:contextPath/>';

//打开新增变压器页面
function openTransformer(tgId){
   var url=contextPath+"/jsp/archive/addTransformer.jsp?tgId="+tgId;
   top.showDialogBox("变压器信息录入",url, 575, 960);
}
//打开新增总表页面
function openTotalMeter(tgId){
   var orgNo=$("#orgNo").val();
   var params = {
                 "tgId":tgId,
                 "orgNo":orgNo
                };
   var url=contextPath+"/archive/meterAction.do?action=addTotalMeterInit&"+ jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
   top.showDialogBox("新增电表",url, 575, 960);
}
//打开新增集中器页面
function openTerm(){
   var orgNo=$("#orgNo").val();
   var tgId=$("input[name='tgId']").val();
   var params = {
                 "tgId":tgId,
                 "orgNo":orgNo,
                 "termId":""
                };
   var url=contextPath+"/jsp/archive/lowCustTermFrame.jsp?"+ jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
   top.showDialogBox("集中器及采集器信息录入",url, 575, 960);
}
//上一步
function lastStep(){
     $("input[name='action']").val("showTgInfoByTgID");
     aTgForm.submit();
}
//下一步
function nextStep(){
  var params = {
                 "sqlCode":"AL_ARCHIVE_0024",
                 "pageRows":20
               };
  var url=contextPath+"/archive/lowCustListQuery.do?action=normalMode&"+ jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
  window.location.href=url;
}
//完成
function finish(){
   if(confirm("是否继续新增台区")==true){
      window.location.href=contextPath+"/jsp/archive/addLowVoltCust.jsp";
   }else{
      //window.location.href=contextPath+"/archive/addLowCustAction.do?action=finish";
      window.location.href=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=lowVoltCustType&selectType=1";
   }
}
//打开变压器编辑页面
function showTran(tranId){
   var url=contextPath+"/archive/tranAction.do?action=showTranByLowCustNew&tranId="+tranId;
   top.showDialogBox("变压器信息编辑",url, 575, 960);
}
//打开总表编辑页面
function showMeter(mpId,gpId){
   var url=contextPath+"/archive/meterAction.do?action=showMeterByLowCustNew&mpId="+mpId+"&gpId="+gpId+"";
   top.showDialogBox("总表信息编辑",url, 575, 960);
}
//编辑集中器
function showTerminal(termId){
   var url=contextPath+"/jsp/archive/lowCustTermFrame.jsp?termId="+termId;
   top.showDialogBox("集中器信息编辑",url, 575, 960);
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
                //window.location.href=contextPath+"/archive/addLowCustAction.do?action=showDeviceInfoByTgID";
                loadTgRelevevance();
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
  //删除总表
   function delMeter(mpId){
     var url=contextPath+"/archive/meterAction.do?action=deleteTotalMeter&mpId="+mpId;
     if(confirm("确定要删除该总表?")){
             $.ajax({
            url: url,
            dataType:'json',
            cache: false,
            success: function(json){
              var msg=json['msg'];
               if(msg=="1"){
                alert("删除成功");
                //window.location.href=contextPath+"/archive/addLowCustAction.do?action=showDeviceInfoByTgID";
                loadTgRelevevance();
                }
                else{
                   alert("删除失败");
                }
            }
       });
     }
   }
   //删除集中器
   function deleteTerminal(termId){  
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
                //window.location.href=contextPath+"/archive/addLowCustAction.do?action=showDeviceInfoByTgID";
                loadTgRelevevance();
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
//调用json方法获取list
function getJsonObjectList(htmlId,className,methodName,objectType){
  var objectId=$("input[name='tgId']").val();
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
//动态加载台区关联设备信息
function loadTgRelevevance(){
  show_loading();
  getJsonObjectList("tranDataBody","TgService","getTransByTgId","12");
  getJsonObjectList("meterDataBody","TgService","getMeterByTgId","232");
  getJsonObjectList("termDataBody","TgService","getTerminalsByTgId","53");//加载终端列表
  remove_loading();
}
</script>
 </head>
 <body>
  <div id="body">
   <jsp:include page="archiveTabs.jsp" flush="true">
      <jsp:param value="3" name="pageType"/>
   </jsp:include>
   <div id="main">
    <html:form action="/archive/addLowCustAction" method="get">
     <input type="hidden" name="tgId" value="${tgId}" />
     <input type="hidden" name="action" value="showDeviceInfoByTgID" />
     <div class="tab">
      <em>变压器,总表,终端</em>
     </div>
     <div class="tab_con" style="height: expression(((   document.documentElement.clientHeight ||   document.body.clientHeight) -     123 ) );">
      <div class="main">
       <table border="0" cellpadding="0" cellspacing="0" width="900" align="center">
        <tr>
         <td width="13%" class="label">
          <font color="red">* </font>台区号：
         </td>
         <td width="20%" class="dom">
          <input type="text" id="tgNo" name="tgNo" value="${object_tg.tgNo}" disabled="disabled" />
         </td>
         <td width="13%" class="label">
          <font color="red">* </font>台区名：
         </td>
         <td width="20%" class="dom">
          <input type="text" id="tgName" name="tgName" value="${object_tg.tgName}" disabled="disabled" />
         </td>
         <td width="13%" class="label">
          <input type="checkbox" name="isChecked" value="${object_tg.isChecked}" ${object_tg.isChecked== "1" ? "checked" : ""} disabled="disabled" />
         </td>
         <td width="21%" class="dom">
          投入考核
         </td>
        </tr>
        <tr>
         <td class="label">
          <font color="red">* </font>供电单位：
         </td>
         <td class="dom">
          <peis:selectlist name="orgNo" sql="SL_COMMON_0001" extendProperty="disabled='disabled'" />
          <script type="text/javascript">
               $("#orgNo").val('${object_tg.orgNo}');
              </script>
         </td>
         <td class="label">
          容 量：
         </td>
         <td class="dom">
          <input type="text" id="tgCapa" name="tgCapa" value="<fmt:formatNumber value="${object_tg.tgCapa}" pattern="#0.0000#"  minFractionDigits="0"/>" disabled="disabled" style="width: 113px" />
          kVA
         </td>
         <td class="label">
          线损阈值：
         </td>
         <td class="dom">
          <input type="text" id="tgThreshold" name="tgThreshold"  value="<c:if test="${object_tg.tgThreshold!=null}"><fmt:formatNumber value="${object_tg.tgThreshold*100}" pattern="#0.0000#"  minFractionDigits="0"/></c:if>" style="width: 125px;"/> %</td>
        </tr>
        <tr>
         <td class="label">
          行政区：
         </td>
         <td class="dom">
          <peis:selectlist name="sar" sql="SL_ARCHIVE_0002" extendProperty="disabled='disabled'" />
          <script type="text/javascript">
               $("#sar").val(${object_tg.sar});
              </script>
         </td>
         <td class="label">
          地 域：
         </td>
         <td class="dom">
          <peis:selectlist name="area" sql="SL_ARCHIVE_0003" extendProperty="disabled='disabled'" />
          <script type="text/javascript">
               $("#area").val(${object_tg.area});
             </script>
         </td>
         <td class="label">
          台区状态：
         </td>
         <td class="dom">
          <peis:selectlist name="tgStatus" sql="SL_ARCHIVE_0004" extendProperty="disabled='disabled'" />
          <script type="text/javascript">
               $("#tgStatus").val(${object_tg.tgStatus});
              </script>
         </td>
        </tr>
        <tr>
         <td class="label">
          联 系 人：
         </td>
         <td class="dom">
          <input type="text" name="contact" id="contact" value="${object_tg.contact}" disabled="disabled">
         </td>
         <td class="label">
          联系电话：
         </td>
         <td class="dom">
          <input type="text" name="phone" id="phone" value="${object_tg.phone}" disabled="disabled">
         </td>
         <td class="label">
          地 址：
         </td>
         <td class="dom">
          <input type="text" name="installAddr" id="installAddr" value="${object_tg.installAddr}" disabled="disabled">
         </td>
        </tr>
        <tr>
         <td rowspan="3" class="label">
          备 注：
         </td>
         <td class="dom" colspan="5">
          <textarea class="input_textarea3" name="remark" style="width: 734px; height: 80px" disabled="disabled">${object_tg.remark}</textarea>
         </td>
        </tr>
       </table>
      </div>
      <div class="data2">
       <span>变压器信息</span>
       <h1>
        <a href="#" onclick="openTransformer(${tgId}); return false;"><img src="<peis:contextPath/>/img/bt_add.png" width="19" height="19" class="mgt5" /> </a>
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
           名称
          </th>
          <th>
           容量(kVA)
          </th>
          <th>
           型号
          </th>
          <th>
           安装地址
          </th>
          <th>
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
             <a href="javascript:showTran('<bean:write name="tran" property="col6"/>');"><bean:message key="global.edit" /> </a>|
             <a href="javascript:delteTran('<bean:write name="tran" property="col6"/>')"><bean:message key="global.delete" /> </a>
            </td>
           </tr>
          </logic:iterate>
         </logic:present>
        </tbody>
       </table>
      </div>
      <div class="data2">
       <span>总表信息</span>
       <h1>
        <a href="#" onclick="openTotalMeter(${tgId}); return false;"><img src="<peis:contextPath/>/img/bt_add.png" width="19" height="19" class="mgt5" /> </a>
       </h1>
      </div>
      <div class="data2_con">
       <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <thead>
         <tr>
          <th>
           资产编号
          </th>
          <th>
           计量点名称
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
             <bean:write name="meter" property="col2" />
            </td>
            <td>
             <bean:write name="meter" property="col1" />
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
             <a href="javascript:showMeter('<bean:write name="meter" property="col8"/>','<bean:write name="meter" property="col9"/>');"><bean:message key="global.edit" /> </a>|
             <a href="javascript:delMeter('<bean:write name="meter" property="col8"/>')"><bean:message key="global.delete" /> </a>
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
        <a href="#" onclick="openTerm(); return false;"><img src="<peis:contextPath/>/img/bt_add.png" width="19" height="19" class="mgt5" /> </a>
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
         <logic:present name="object_terminal">
          <logic:iterate id="terminal" name="object_terminal">
           <tr style="cursor: pointer;">
            <td align="center">
             <bean:write name="terminal" property="col1" />
            </td>
            <td align="center">
             <bean:write name="terminal" property="col2" />
            </td>
            <td align="center">
             <bean:write name="terminal" property="col3" />
            </td>
            <td align="center">
             <bean:write name="terminal" property="col4" />
            </td>
            <td align="center">
             <bean:write name="terminal" property="col5" />
            </td>
            <td align="center">
             <bean:write name="terminal" property="col6" />
            </td>
            <td align="center">
             <a href="javascript:showTerminal('<bean:write name="terminal" property="col7"/>');"><bean:message key="global.edit" /> </a>|
             <a href="javascript:deleteTerminal('<bean:write name="terminal" property="col7"/>')"><bean:message key="global.delete" /> </a>
            </td>
           </tr>
          </logic:iterate>
         </logic:present>
        </tbody>
       </table>
      </div>
     </div>
    </html:form>
    <div class="guidePanel">
     <input type="button" id="last" value="上一步" class="input1" onclick="lastStep();" />
     &nbsp;&nbsp;&nbsp;&nbsp;
     <input type="button" id="last" value="下一步" class="input1" onclick="nextStep();" />
     &nbsp;&nbsp;&nbsp;&nbsp;
     <input type="button" id="finish" value="完 成" class="input1" onclick="finish();" />
    </div>
   </div>
  </div>
 </body>
</html>
