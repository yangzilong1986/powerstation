<%@page contentType="text/html; charset=UTF-8"%>

<%@include file="../common/blockloading.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>变电站录入第一步</title>
<link rel="stylesheet" type="text/css" href="../../css/main.css" />
<script type="text/javascript">
	$(document).ready(function(){
    $(':radio').click(function(){
        var url="";
        if($(':radio:checked').val()==1){ //新增
           url="<peis:contextPath/>/jsp/archive/addStranSubstation.jsp";
           window.location.href=url;
        }else { //维护
           url="<peis:contextPath/>/jsp/archive/updateStranSubstation.jsp";
           window.location.href=url;
        }
    })
});
//选择档案类型
function nextStep(archiveType){
	if(archiveType == "1"){//变电站
     window.location.href="<peis:contextPath/>/jsp/archive/addSubstationDetail.jsp";
  }
  if(archiveType == "2"){//电厂
     window.location.href="<peis:contextPath/>/jsp/archive/addElectricDetail.jsp";
  }
  if(archiveType == "3"){//开关站
	     window.location.href="<peis:contextPath/>/jsp/archive/addSwitchStationDetail.jsp";
	  }
}
</script>
</head>
<body>
<div id="body">
    <jsp:include page="archiveTabs.jsp" flush="true">
      <jsp:param value="4" name="pageType"/>
    </jsp:include>
  <div id="main" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 42));">
    <div class="select_box">
    	<div>
        <span><input type="radio" name="selectType" value="1" checked="checked">新增</span>
        <span><input type="radio" name="selectType" value="0">维护</span>
      </div>
      <div class="mgt50 font14 b">请选择档案类型</div>
      <div class="mgt50"><a href="#" onclick="nextStep(1); return false;"><img src="../../img/archive41.jpg" width="200" height="43" border="0" /></a></div>
      <div class="mgt50"><a href="#" onclick="nextStep(2); return false;"><img src="../../img/archive42.jpg" width="200" height="43" border="0" /></a></div>
      <div class="mgt50"><a href="#" onclick="nextStep(3); return false;"><img src="../../img/archive43.jpg" width="200" height="43" border="0" /></a></div>
    </div>
  </div>
</div>
</body>
</html>