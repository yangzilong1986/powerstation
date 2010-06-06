<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="include/page.htm" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>厂站档案录入</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript">
  var tempSUBS_ID=<%=request.getParameter("SUBS_ID")%>;
  var tempORG_NO=<%=request.getParameter("ORG_NO")%>;
  var tempSUBS_TYPE=<%=request.getParameter("SUBS_TYPE")%>;
  //用来判断‘上一步’按钮应该跳转到哪个画面
  var tempPARAM="<%=request.getParameter("PARAM")%>";
  var tempCZMC="<%=request.getParameter("CZMC")%>";
  var tempVOLT_GRADE="<%=request.getParameter("VOLT_GRADE")%>";
  var tempWantedPerPage="<%=request.getParameter("WANTEDPERPAGE")%>";
  var contextpath="<peis:contextPath/>";
//上一步
 function lastStep(){
 	if(tempPARAM==''||tempPARAM=="null"){
   window.location.href="112022.dow?SUBS_ID="+tempSUBS_ID+"&FLG=QUERY&QUE=1";
    }else{
    	window.location.href="112042.dow?WANTEDPERPAGE="+tempWantedPerPage+"&CZMC="+tempCZMC+"&VOLT_GRADE="+tempVOLT_GRADE;
    }
 }
//完成
 function finish(){
 	if(tempPARAM==''||tempPARAM=="null"){
    if((confirm("是否继续新增电厂")==true)){
	    window.location.href=contextpath+"/jsp/archive/addElectricDetail.jsp";
	  }
    else{
    window.location.href=contextpath+"/jsp/archive/addStranSubstation.jsp";
    }
   }
   else{
   	if((confirm("是否继续编辑")==true)){
	    window.location.href="112042.dow?WANTEDPERPAGE=10&load=1&t="+new Date().getTime();
	  }
    else{
    window.location.href=contextpath+"/jsp/archive/updateStranSubstation.jsp";
    }
   }
 }
//新建变压器
 function openTran(){
   var url=contextpath+"/jsp/archive/addTranDetail.jsp?SUBS_ID="+tempSUBS_ID
                                 +"&SUBS_TYPE=2&PARAM=tempPARAM"
                                 +"&CZMC="+tempCZMC
                                 +"&VOLT_GRADE="+tempVOLT_GRADE
                                 +"&WANTEDPERPAGE="+tempWantedPerPage;
   top.showDialogBox("变压器信息",url, 575, 960);
   //top.open (url, '变压器信息', 'height=575, width=960, top=0,left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no')  
 }
 //新建母线
 function openTotalMeter(){
   var url=contextpath+"/jsp/archive/addMlDetail.jsp?SUBS_ID="+tempSUBS_ID
                                 +"&SUBS_TYPE=2&PARAM=tempPARAM"
                                 +"&CZMC="+tempCZMC
                                 +"&VOLT_GRADE="+tempVOLT_GRADE
                                 +"&WANTEDPERPAGE="+tempWantedPerPage;
   top.showDialogBox("母线信息",url, 400, 900);
 }
 //新建终端
 function openLowCustTerm(){
   var url=contextpath+"/jsp/archive/addTerminalDetail.jsp?SUBS_ID="+tempSUBS_ID
                                 +"&SUBS_TYPE=2&PARAM=tempPARAM"
                                 +"&CZMC="+tempCZMC
                                 +"&VOLT_GRADE="+tempVOLT_GRADE
                                 +"&WANTEDPERPAGE="+tempWantedPerPage;
   top.showDialogBox("终端信息",url, 575, 960);
 }
 //新建电表
 function openLowCust(){
   var url=contextpath+"/jsp/archive/addMpDetail.jsp?SUBS_ID="+tempSUBS_ID
                                 +"&SUBS_TYPE=2&PARAM=tempPARAM"
                                 +"&CZMC="+tempCZMC
                                 +"&VOLT_GRADE="+tempVOLT_GRADE
                                 +"&WANTEDPERPAGE="+tempWantedPerPage;
   top.showDialogBox("电表信息",url, 575, 900);
 }/**
 * 
 */
function switchPanel(value) {
    if(value == "show") {
        $("#showPanel").css("display", "none");
        $("#hidePanel").css("display", "block");
        $("#treePanel").css("display", "block");
    }
    else if(value == "hide") {
        $("#showPanel").css("display", "block");
        $("#hidePanel").css("display", "none");
        $("#treePanel").css("display", "none");
    }
}

function swapImage(id, imgSrc) {
    $("#" + id).attr("src", imgSrc);
}

$(document).ready( function() {
    switchPanel("show");
});
</script>
</head>
<body>
<div id="body">
   <jsp:include page="archiveTabs.jsp" flush="true">
      <jsp:param value="4" name="pageType"/>
    </jsp:include>
   <div id="main">
   <div id="tool">
      <div class="opbutton1">
        <input class="input3" type="button" value="新增变压器" onClick="openTran();" />
        <input class="input2" type="button" value="新增母线" onClick="openTotalMeter();" />
        <input class="input2" type="button" value="新增终端" onClick="openLowCustTerm();" />
        <input class="input2" type="button" value="新增电表" onClick="openLowCust();" />
      </div>
     <table border="0" cellpadding="0" cellspacing="0">
      <tr>
       <td>
       </td>
      </tr>
     </table>
    </div>
    <div class="content">
     <div id="cont_1">
       <div style="border: 1px solid #5d90d7; height: expression((( document.documentElement.clientHeight || document.body.clientHeight) - 130 ) );">
        <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
          <tr>
            <td width="200" valign="top" id="treePanel">
               <iframe name="treeframe" width="100%" height="100%" frameborder="0" scrolling="auto" src="112111.dow?OPEN_NEW=selectElecTree.jsp&subs_id=<%=request.getParameter("SUBS_ID")%>"></iframe>
            </td>
            <td width="8" class="bg_panel" valign="middle">
              <table width="100%" height="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td id="hidePanel" valign="middle"><img src="<peis:contextPath/>/img/bt_hide.gif" alt="隐藏对象树" id="bt_hide" width="8" height="54" border="0" id="bt_hide" style="cursor:pointer;" onclick="switchPanel('hide')" onmouseover="swapImage('bt_hide', '../../img/bt_hide_o.gif');" onmouseout="swapImage('bt_hide', '../../img/bt_hide.gif');"></td>
                </tr>
                <tr>
                  <td id="showPanel" valign="middle"><img src="<peis:contextPath/>/img/bt_show.gif" alt="显示对象树" id="bt_show" width="8" height="54" border="0" id="bt_show" style="cursor:pointer;" onclick="switchPanel('show')" onmouseover="swapImage('bt_show', '../../img/bt_show_o.gif');" onmouseout="swapImage('bt_show', '../../img/bt_show.gif');"></td>
                </tr>
              </table>
            </td>
            <td class="pad10">
             <iframe name="mainframe" width="100%" height="100%" frameborder="0" scrolling="no" src="112022.dow?OPEN_NEW=elecField.jsp&subs_id=<%=request.getParameter("SUBS_ID")%>&FLG=QUERY"></iframe>
            </td>
          </tr>
        </table>
     </div>
     </div>
     <div class="guidePanel">
      <input type="button" id="last" value="上一步" class="input1" onclick="lastStep();" />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="next" value="完 成" class="input1" onclick="finish();" />
      &nbsp;&nbsp;&nbsp;&nbsp;
    </div>
    </div>
   </div>
  </div>
</body>
</html>