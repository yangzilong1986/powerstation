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
  var termId=$.url.param("termId");
  var orgNo=$.url.param("orgNo");
  $(document).ready(function(){
    $('#contentArea').attr("src",contextPath+"/archive/terminalAction1.do?action=updateTermInit&termId="+termId+"&tgId="+tgId+"");
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
function extra(){
   var url=contextPath+"/archive/terminalAction1.do?action=clearSessionByLowCustEdit";
   top.getMainFrameObj().location.href=url;
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
     <div class="content">
     <div id="cont_1">
      <div id="tableContainer" style="height: expression(((   document.documentElement.clientHeight ||   document.body.clientHeight) -   125 ) );">
      <table width="100%" height="100%" cellpadding="0" cellspacing="0" align="center">
       <tr>
        <td width="100%" height="100%">
         <iframe name="contentArea" id="contentArea" src='' frameborder="0" scrolling="auto" width="100%" height="100%"></iframe>
        </td>
       </tr>
      </table>
      </div>
     </div>
    </div>
    <input type="hidden" name="tgId" value="${tgId}"/>
  </div>
</div>
</body>
</html>
