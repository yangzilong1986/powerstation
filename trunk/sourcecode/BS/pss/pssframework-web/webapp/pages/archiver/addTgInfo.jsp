<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>配变/台区档案录入第二步</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.dataForAjax.js"></script>
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';
$(document).ready(function(){
  selectInit();
});
//上一步
function lastStep(){
  //window.location.href=contextPath+"/jsp/archive/tgType.jsp";
  var url=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=tgType&selectType=1";
  window.location.href=url;
}
//下一步
function nextStep(){
 var url=contextPath+"/archive/addTgAction.do?action=addTgInfo";
   if(tgCheck()){
     $("#next").attr("disabled",true);
     var data = getFormData("form");
     if(data){
         jQuery.ajax({
             type:'post',
             url:url,
             data:data,
             dataType:'json',
             success:function(json){
                 var msg=json['msg'];
                 $("#next").attr("disabled",false);
                 if(msg=="1"){
                    window.location.href=contextPath+"/archive/addTgAction.do?action=showDeviceInfoByTgID";
                 }else if(msg=="2"){
                    alert("该台区已经存在");
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
  var url=contextPath+"/archive/addTgAction.do?action=addTgInfo";
   if(tgCheck()){
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
                    if(confirm("是否继续新增台区")==true){
                        var url1=contextPath+"/archive/commAction.do?action=clearSessionByTg";
                        $.ajax({
                          url: url1,
                          cache: false,
                          success: function(html) {
                            window.location.href=contextPath+"/jsp/archive/addTgInfo.jsp";
                          }
                        });
                    }else{
                      //window.location.href=contextPath+"/jsp/archive/tgType.jsp";
                      window.location.href=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=tgType&selectType=1";
                    }             
                 }else if(msg=="2"){
                    alert("该台区已经存在");
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
    $("#orgNo").val('${object_tg.orgNo}');
    $("#sar").val(${object_tg.sar});
    $("#area").val(${object_tg.area});
    $("#tgStatus").val(${object_tg.tgStatus}); 
}
</script>
</head>
<body>
<div id="body">
  <jsp:include page="archiveTabs.jsp" flush="true">
    <jsp:param value="2" name="pageType"/>
  </jsp:include>
  <div id="main">
    <div class="tab"><em>台区基本信息</em></div>
    <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 123));">
      <div class="main">
        <div id="form">
        <input type="hidden" name="tgId" value="${tgId}"/>
        <table border="0" cellpadding="0" cellspacing="0" width="900" align="center">
          <tr>
            <td width="13%" class="label"><font color="red">* </font>台区号：</td>
            <td width="20%" class="dom"><input type="text" id="tgNo" name="tgNo" value="${object_tg.tgNo}" /></td>
            <td width="13%" class="label"><font color="red">* </font>台区名：</td>
            <td width="20%" class="dom"><input type="text" id="tgName" name="tgName" value="${object_tg.tgName}" /></td>
            <td width="13%" class="label">
              <input type="checkbox" name="isChecked" value="${object_tg.isChecked!="" ? "1" : "0"}" ${object_tg.isChecked==1 ? "checked" : ""}/>
            </td>
            <td width="21%" class="dom">投入考核</td>
          </tr>
          <tr>
            <td  class="label"><font color="red">* </font>供电单位：</td>
            <td  class="dom">
              <peis:selectlist name="orgNo" sql="SL_COMMON_0001"/>
            </td>
            <td  class="label">容　量：</td>
            <td  class="dom"><input type="text" id="tgCapa" name="tgCapa" value="<fmt:formatNumber value="${object_tg.tgCapa}" pattern="#0.0000#"  minFractionDigits="0"/>" style="width: 115px;"/> kVA</td>
            <td  class="label">线损阈值：</td>
            <td  class="dom">
              <input type="text" id="tgThreshold" name="tgThreshold"  value="<c:if test="${object_tg.tgThreshold!=null}"><fmt:formatNumber value="${object_tg.tgThreshold*100}" pattern="#0.0000#"  minFractionDigits="0"/></c:if>" style="width: 125px;"/> %</td>
          </tr>
          <tr>
            <td  class="label">行政区：</td>
            <td  class="dom">
              <peis:selectlist name="sar" sql="SL_ARCHIVE_0002"/>
            </td>
            <td  class="label">地　域：</td>
            <td  class="dom">
              <peis:selectlist name="area" sql="SL_ARCHIVE_0003"/>
            </td>
            <td  class="label">台区状态：</td>
            <td  class="dom">
              <peis:selectlist name="tgStatus" sql="SL_ARCHIVE_0004"/>
            </td>
          </tr>
          <tr>
            <td class="label">联系人：</td>
            <td class="dom">
             <input type="text" name="contact" id="contact" value="${object_tg.contact}">
            </td>
            <td class="label">联系电话：</td>
            <td class="dom">
             <input type="text" name="phone" id="phone" value="${object_tg.phone}">
            </td>
            <td class="label">地　址：</td>
            <td class="dom">
             <input type="text" name="installAddr" id="installAddr" value="${object_tg.installAddr}">
            </td>
         </tr>
         <tr>
          <td rowspan="3" class="label">备　注：</td>
          <td class="dom" colspan="5">
            <textarea class="textarea" name="remark" style="width: 734px; height: 80px;">${object_tg.remark}</textarea>
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
