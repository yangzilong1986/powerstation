<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>集中器及采集器信息</title>
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/greybox.css" />
  <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
  <script type="text/javascript">
  var contextPath = '<peis:contextPath/>';
  var tgId=$.url.param("tgId");
  var orgNo=$.url.param("orgNo");
  var termId=$.url.param("termId");
  var termTypeFlag=$.url.param("termTypeFlag");  //标记位，用来区分配变台区页面跳转还是低压集抄跳转过来
  $(document).ready(function(){
   $('#contentArea').attr("src",contextPath+"/archive/terminalAction1.do?action=addTermInit&termId="+termId);
  });
  function setTarget(i) {
    if(i == 1) {
       $('#te').attr("class","target_on");
       $('#gm').attr("class","target_off");
       //$('#contentArea').attr("src",contextPath+"/archive/terminalAction1.do?action=showTerm&termId="+termId+"&gpId="+gpId+"");
       frames['contentArea'].forwardTerm();
    }
    else if(i == 2) {
       $('#te').attr("class","target_off");
       $('#gm').attr("class","target_on");
       frames['contentArea'].saveAndShowCollect();
       //$('#contentArea').attr("src",contextPath+"/jsp/archive/gmInfo.jsp");
    }
  }
 
//打开编辑采集器页面
function updateCollector(){
   var url="../../jsp/archive/updateCollector.html";
   showDialogBox("采集器信息",url, 495,800);
}
//删除采集器
function deleteCollector(){
  if(confirm("是否删除该采集器")==true){
     alert("删除成功！");
   }
}

function extra(){
   var url="";
   if(termTypeFlag==1){ //配变台区页面跳转过来
      var url1=contextPath+"/archive/commAction.do?action=clearSessionByTerm";
      $.ajax({
        url: url1,
        cache: false,
        success: function(html) {
            //url=contextPath+"/archive/addTgAction.do?action=showDeviceInfoByTgID";
            //top.getMainFrameObj().location.href=url;
            top.getMainFrameObj().loadTgRelevevance();
        }
      });
   }else{
     //url=contextPath+"/archive/terminalAction1.do?action=clearTermSession";
     //top.getMainFrameObj().location.href=url;
     var url1=contextPath+"/archive/commAction.do?action=clearSessionByTerm";
      $.ajax({
        url: url1,
        cache: false,
        success: function(html) {
            //url=contextPath+"/archive/addTgAction.do?action=showDeviceInfoByTgID";
            //top.getMainFrameObj().location.href=url;
            top.getMainFrameObj().loadTgRelevevance();
        }
      });
   }
}
</script>
</head>
<body style="overflow: hidden;">
<div>
  <div id="main">
    <div class="target">
      <ul>
       <li id="te" class="target_on">
        <a href="#" onclick="setTarget(1); return false;">集中器信息</a>
       </li>
       <li id="gm" class="target_off">
        <a href="#" onclick="setTarget(2); return false;">采集器信息</a>
       </li>
       <li class="clear"></li>
      </ul>
     </div>
    <table width="936" height="550" cellpadding="0" cellspacing="0" align="center">
     <tr>
      <td width="100%" height="100%">
       <iframe name="contentArea" id="contentArea" src='' frameborder="0" scrolling="auto" width="100%" height="100%"></iframe>
      </td>
     </tr>
    </table>
  </div>
</div>
</body>
</html>
