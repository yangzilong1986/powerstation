<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<div id="tab" class="tab1">
<ul>
   <li id="tab_1" class="tab_on"><a href="javascript:changeTab(1);" onfocus="blur()">专变用户</a></li>
   <li id="tab_2" class="tab_off"><a href="javascript:changeTab(2);"  onfocus="blur()">配变台区</a></li>
   <li id="tab_3" class="tab_off"><a href="javascript:changeTab(3);"  onfocus="blur()">低压集抄</a></li>
   <li id="tab_4" class="tab_off"><a href="javascript:changeTab(4);"  onfocus="blur()">变电站</a></li>
   <li id="tab_5" class="tab_off"><a href="javascript:changeTab(5);"  onfocus="blur()">线路</a></li>
   <li id="tab_6" class="tab_off"><a href="javascript:changeTab(6);"  onfocus="blur()">联络线</a></li>
   <li id="tab_7" class="tab_off"><a href="javascript:changeTab(7);"  onfocus="blur()">线路批修改</a></li>
   <li id="tab_8" class="tab_off"><a href="javascript:changeTab(8);"  onfocus="blur()">台区批修改</a></li>
   <li id="tab_9" class="tab_off"><a href="javascript:changeTab(9);"  onfocus="blur()">集抄批导入</a></li>
   <li id="tab_10" class="tab_off"><a href="javascript:changeTab(10);"  onfocus="blur()">档案审核</a></li>
   <li class="clear"></li>
</ul>
<h1><a href="#"><img src="<peis:contextPath/>/img/bt_help.gif" width="14" height="15" /></a></h1>
</div>
<script type="text/javascript">
var pageType=<%=request.getParameter("pageType")%>; //1:专变页面 2:配变页面 3:低压集抄页面 4:变电站页面 5:线路页面 6:联络线页面 7:线路批修改页面8:台区批修改页面 9:集抄批量导入页面10:档案审核页面
var contextPath = '<peis:contextPath/>';
initTabs(pageType);
//页面切换
function changeTab(index) {
   if(index == "1"){ //专变用户
      window.location.href= contextPath + "/archive/commAction.do?action=forwardSelectType&jspName=archiveType&selectType=1";
   }else if (index == "2"){ //配变台区
      window.location.href= contextPath + "/archive/commAction.do?action=forwardSelectType&jspName=tgType&selectType=1";
   }else if (index == "3"){ //低压集抄
      window.location.href= contextPath + "/archive/commAction.do?action=forwardSelectType&jspName=lowVoltCustType&selectType=1";
   }else if (index == "4"){ //变电站

      window.location.href= contextPath + "/jsp/archive/addStranSubstation.jsp";
   }else if (index == "5"){ //线路
      window.location.href= contextPath + "/jsp/archive/lineManageFrame.jsp";
   }else if (index == "6"){ //联络线

      window.location.href= contextPath + "/jsp/archive/tieLineManageFrame.jsp";
   }else if (index == "7"){ //线路批修改

      window.location.href= contextPath + "/jsp/archive/batModifyFrame.jsp?pageType=7";
   }else if (index == "8"){ //台区批修改

     window.location.href= contextPath  + "/jsp/archive/batModifyFrame.jsp?pageType=8";
   }else if (index == "9"){ //集抄批量导入
      window.location.href= contextPath + "/jsp/archive/archiveBatImportTabsFrame.jsp";
   }else if (index == "10"){ //档案审核
      window.location.href= contextPath + "/jsp/archive/archiveCheck.jsp";
   }
}
//页面TAB页初始化
function initTabs(pageType){
   $("li[class^='tab_']").attr("class", "tab_off");
   $("#tab_" + pageType).attr("class", "tab_on");
}
</script>