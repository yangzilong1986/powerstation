<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>集中器及采集器信息</title>
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
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
  $(document).ready(function(){
    jQuery("#finish").click(function(){
       frames['contentArea'].finish();
    });
   $('#contentArea').attr("src",contextPath+"/jsp/archive/termCollectorInfo2.jsp");
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
 
//上一步
function lastStep(){

   //window.location.href=contextPath+"/jsp/archive/lowVoltCustType.jsp";
   var url=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=lowVoltCustType&selectType=1";
   window.location.href=url;
=======
   //var url=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=lowVoltCustType&selectType=1";
   //window.location.href=url;
   history.go(-1);
>>>>>>> .r837
}
</script>
</head>
<body style="overflow: hidden;">
<div id="body">
  <jsp:include page="archiveTabs.jsp" flush="true">
      <jsp:param value="3" name="pageType"/>
   </jsp:include>
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
   </div>
  <div class="guidePanel">
      <input type="button" id="last" value="上一步" class="input1" onclick="lastStep();" />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="finish" value="完 成" class="input1"/>
    </div>
</div>
</body>
</html>
