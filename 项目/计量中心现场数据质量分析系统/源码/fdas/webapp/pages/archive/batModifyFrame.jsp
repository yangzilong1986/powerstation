<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>变更批修改</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
var pageType=<%=request.getParameter("pageType")%>
var flag = "line";
//初始化加载
$(function(){  
   initPage(pageType);
})

setTarget = function(i){
    var url="";
    if(i==1){
      flag="line";
      $('#line').attr("class","tab_on");
      $('#tg').attr("class","tab_off");
      url=contextPath+"/jsp/archive/lineBatModify.jsp";
    }else if(i==2){
      flag="tg";
      $('#line').attr("class","tab_off");
      $('#tg').attr("class","tab_on");
      url=contextPath+"/jsp/archive/tgBatModify.jsp";
    };
    $('#oldFrm').attr("src",url);
    $('#newFrm').attr("src",url);
    refreshText();
}

refreshText= function(){
    if(flag=="line"){
      $("#title1").html("请选择被替换线路");
      $("#title2").html("请选择替换线路");
    }else{
      $("#title1").html("请选择被替换台区");
      $("#title2").html("请选择替换台区");
    }
}

replace = function(){
    if(flag=="line"){
        replaceLine();
    }else if(flag=="tg"){
        replaceTg();
    }
}

replaceLine=function(){
    var oldLineId = oldFrm.lineId;
    var newLineId = newFrm.lineId;
    var oldLineNo = oldFrm.lineNo;
    var oldLineName = oldFrm.lineName;
    var newLineName = newFrm.lineName;
    if(oldLineId==null){
       alert("请选择被替换线路");
       return false;
    }
    if(newLineId==null){
       alert("请选择替换线路");
       return false;
    }
    if(oldLineId==newLineId){
       alert("请选择不同线路");
       return false;
    }
    var url = contextPath + "/archive/lineInfoAcrion.do?action=batchModifyLine&oldLineId="+oldLineId+"&newLineId="+newLineId; 
    if(confirm("确定将线路"+"\""+oldLineName+"\""+"替换为线路"+"\""+newLineName+"\""+"吗？")){
        $.ajax({
          url: url,
          dataType: 'json',
          cache: false,
          success: function(json){
            var msg=json['msg'];
            
            if(msg=="1"){
              url = contextPath + "/archive/lineInfoAcrion.do?action=delLine&objectType=8&lineId="+oldLineId;
              if(confirm("替换成功，是否删除线路"+"\""+oldLineName+"\"？")){
                  $.ajax({
                    url: url,
                    dataType: 'json',
                    cache: false,
                    success: function(json){
                      msg=json['msg'];
                      if(msg=="1"){
                        alert("删除成功");
                        $('#oldFrm').attr("src",contextPath+"/archive/changeLineQuery.do?action=normalMode&pageType=all&sqlCode=AL_ARCHIVE_0080&showType=all");
                      }else if(msg=="5"){
                        alert("该线路下存在变电站电表，请手工修改后删除");
                      }else{
                        alert("删除失败");
                      }
                    }
                  })
              }
            }else{
              alert("替换失败");
            }
          }
        })
    }
}

replaceTg=function(){
    var oldTgId = oldFrm.tgId;
    var newTgId = newFrm.tgId;
    var oldTgName = oldFrm.tgName;
    var newTgName = newFrm.tgName;
    if(oldTgId==null){
       alert("请选择被替换台区");
       return false;
    }
    if(newTgId==null){
       alert("请选择替换台区");
       return false;
    }
    if(oldTgId==newTgId){
       alert("请选择不同台区");
       return false;
    }
    var url = contextPath + "/archive/updateLowCustInfoAction.do?action=batchModifyTg&oldTgId="+oldTgId+"&newTgId="+newTgId; 
    if(confirm("确定将台区"+"\""+oldTgName+"\""+"替换为台区"+"\""+newTgName+"\""+"吗？")){
        $.ajax({
          url: url,
          dataType: 'json',
          cache: false,
          success: function(json){
            var msg=json['msg'];
            
            if(msg=="1"){
              url = contextPath + "/archive/updateLowCustInfoAction.do?action=deleteTg&tgId="+oldTgId;
              if(confirm("替换成功，是否删除台区"+"\""+oldTgName+"\"？")){
                  $.ajax({
                    url: url,
                    dataType: 'json',
                    cache: false,
                    success: function(json){
                      msg=json['msg'];
                      if(msg=="1"){
                        alert("删除成功");
                        $('#oldFrm').attr("src",contextPath+"/archive/changeTgQuery.do?action=normalMode&pageType=all&sqlCode=AL_ARCHIVE_0083&showType=all");
                      }else if(msg=="3"){
                        alert("该台区下存在终端，请手工修改后删除");
                      }
                    }
                  })
              }
            }
          }
        })
    }
}

//页面初始化
function initPage(pageType){
  if(pageType == "7"){ //初始化线路
     var url=contextPath+"/jsp/archive/lineBatModify.jsp";
     flag="line";
  }else { //初始化台区
     var url=contextPath+"/jsp/archive/tgBatModify.jsp";
     flag="tg";
  }
  var urlOld=url+"?framName=oldFrm";
  var urlNew=url+"?framName=newFrm";
  $('#oldFrm').attr("src",urlOld);
  $('#newFrm').attr("src",urlNew);
  refreshText();
}
</script>
</head>
<body>
<!--pageType 1:专变页面 2:配变页面 3:低压集抄页面 4:变电站页面 5:线路页面 6:联络线页面 7:线路批修改页面8:台区批修改页面 9:集抄批量导入页面10:档案审核页面 -->
<% String pageType=request.getParameter("pageType");%>   
<div id="body"> 
  <jsp:include page="archiveTabs.jsp" flush="true">
    <jsp:param value="<%=pageType%>" name="pageType"/>
  </jsp:include>
  <div id="main" style="height: expression(((document.documentElement.clientHeight||document.body.clientHeight) - 30));"> 
    <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
      <tr>
        <td>
          <div class="tab" ><em id="title1"></em></div>  
             <div id=main>
               <iframe name="oldFrm" id="oldFrm" src='' frameborder="0" scrolling="auto" style="width: expression((( document.documentElement.clientWidth || document.body.clientWidth) - 63 ) );height: expression((( document.documentElement.clientHeight || document.body.clientHeight) - 325 ) );"></iframe>
           </div>
        </td>
      </tr>
      <tr>
        <td height="1"></td>
      </tr>
      <tr>
        <td>
           <div class="tab"><em id="title2"></em></div>
             <div id=main>
               <iframe name="newFrm" id="newFrm" src='' frameborder="0" scrolling="auto" style="width: expression((( document.documentElement.clientWidth || document.body.clientWidth) - 63 ) );height: expression((( document.documentElement.clientHeight || document.body.clientHeight) - 325 ) );"></iframe>
            </div>
           </td>
      </tr>
      <tr>
        <td>
           <div class="guidePanel">
            <input type="button" id="next" value="替换" class="input1" onclick="replace();" />
           </div>
        </td>
      </tr>
    </table>
  </div>
</div>
</body>
</html>