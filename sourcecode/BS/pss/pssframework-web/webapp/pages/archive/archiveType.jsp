<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>专变用户档案录入第一步</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';

$(document).ready(function(){
    $(':radio').click(function(){
        var url="";
        if($(':radio:checked').val()==1){ //新增
           url=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=archiveType&selectType=1";
           window.location.href=url;
        }else { //维护
           url=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=archiveTypeUpdate&selectType=0";
           window.location.href=url;
        }
    })
});
//选择档案类型
function nextStep(archiveType){
    if(archiveType == "1") { //用户信息
         var url1=contextPath+"/archive/commAction.do?action=clearSessionByBigCust";//清除之前的session
         $.ajax({
           url: url1,
           cache: false,
           success: function(html){
             window.location.href = contextPath+"/jsp/archive/addCustomer.jsp";
           }
         }); 
    }
    if(archiveType == "2") { //终端信息
        window.location.href = contextPath+"/jsp/archive/addTerminalSecond.jsp";
    }
}
</script>
</head>
<body>
<div id="body">
  <jsp:include page="archiveTabs.jsp" flush="true">
    <jsp:param value="1" name="pageType"/>
  </jsp:include>
  <div id="main" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 42));">
    <div class="select_box">
      <div>
        <html:form action="/archive/commAction.do">
        <span><html:radio property="selectType" value="1">新增</html:radio></span>
        <span><html:radio property="selectType" value="0">维护</html:radio></span>
        </html:form>
      </div>
      <div class="mgt50 font14 b">请选择档案类型</div>
      <div class="mgt50"><a href="#" onclick="nextStep(1); return false;"><img src="<peis:contextPath/>/img/archive11.jpg" width="200" height="43" border="0" /></a></div>
      <div class="mgt50"><a href="#" onclick="nextStep(2); return false;"><img src="<peis:contextPath/>/img/archive12.jpg" width="200" height="43" border="0" /></a></div>
    </div>
  </div>
</div>
</body>
</html>