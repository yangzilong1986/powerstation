<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>低压集抄档案编辑</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';
/**
 * 
 * @param {int} objectType    : 对象类型
 * @param {String} objectId   : 对象编号
 * @param {String} objectName : 对象名称
 * @param {String} orgType    : 部门类型
 */
function nodeEvent(objectType, objectId, objectName, orgType) {
	 if(objectType==12){//变压器
         var url=contextPath+"/archive/tranAction.do?action=showTranByLowCustNew&tranId="+objectId+"&flag=1&r=" + parseInt(Math.random() * 1000);
	    $("iframe[name='mainframe']").attr("src",url);
	 }
	 else if(objectType==23){//总表
        var url=contextPath+"/archive/meterAction.do?action=showMeterByLowCustNew&mpId="+objectId+"&flag=1&r=" + parseInt(Math.random() * 1000);
	    $("iframe[name='mainframe']").attr("src",url);
	 }
	 else if(objectType==5){//终端
        if(orgType=="03" || orgType=="04"){ //配变终端
          var url=contextPath+"/archive/terminalAction3.do?action=addTermInit&termId="+objectId+"&flag=2&r=" + parseInt(Math.random() * 1000);
          $("iframe[name='mainframe']").attr("src",url);
        }else if(orgType=="01" || orgType=="02"){ //负控终端
          var url=contextPath+"/archive/terminalAction2.do?action=showTermInTg&termId="+objectId+"&r=" + parseInt(Math.random() * 1000);
          $("iframe[name='mainframe']").attr("src",url);
        }else{
         var url1=contextPath+"/archive/commAction.do?action=clearSessionByTerm";
            $.ajax({
              url: url1,
              cache: false,
              success: function(html){
              var tgId = $("input[name='tgId']").val();
              var url=contextPath+"/jsp/archive/updateLowCustTermFrame1.jsp?termId="+objectId+"&tgId="+tgId+"&r=" + parseInt(Math.random() * 1000);
              $("iframe[name='mainframe']").attr("src",url);
             }
           });
        }
	} else if (objectType == -1) {//低压用户
		var url = contextPath
				+ "/archive/lowCustListQueryUpdate.do?action=normalMode&sqlCode=AL_ARCHIVE_0031&termId="
				+ objectId+"&pageRows=20&r=" + parseInt(Math.random() * 1000);
		$("iframe[name='mainframe']").attr("src", url);
	} else if (objectType == 2) {
		var url = contextPath + "/jsp/archive/tgMain.jsp";
		$("iframe[name='mainframe']").attr("src", url);
	}
    }
	//上一步
	function lastStep() {
		var url1=contextPath+"/archive/commAction.do?action=clearSessionByTg";
        $.ajax({
          url: url1,
          cache: false,
          success: function(html){
             //window.location.href = contextPath + "/archive/tgListQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0028&pageRows=20";
             history.go(-2);
          }
        });
	}
	//新建变压器
	function openTran() {
		var url = contextPath + "/jsp/archive/updateTransformer.jsp";
		top.showDialogBox("变压器信息", url, 575, 960);
	}
	//新建总表
	function openTotalMeter() {
		var url = contextPath + "/jsp/archive/updateTotalMeter.jsp";
		top.showDialogBox("总表信息", url, 575, 900);
	}
	//新建集中器
	function openLowCustTerm() {
		var tgId = $("input[name='tgId']").val();
		var params = {
			"termId" :"",
			"tgId" :tgId
		};
		var url = contextPath + "/jsp/archive/updateLowCustTermFrame.jsp?"
				+ jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
	    var url1=contextPath+"/archive/commAction.do?action=clearSessionByTerm";
	    $.ajax({
	       url: url1,
	       cache: false,
	       success: function(html){
	         top.showDialogBox("集中器及采集器信息录入", url, 575, 960);
	       }
	    });
	}
	//新建低压用户
	function openLowCust() {
		var url = contextPath + "/jsp/archive/updateLowCustDetail.jsp";
		top.showDialogBox("低压用户信息", url, 575, 960);
	}
    //新增专变终端
    function openBigCustTerm(){
        var url = contextPath + "/jsp/archive/addTerminalInfoInTg.jsp";
        top.showDialogBox("专变终端", url, 575, 960);
    }
	//完成
	function finish() {
		if (confirm("是否继续编辑") == true) {
			window.location.href = contextPath
					+ "/archive/tgListQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0028";
		} else {
			window.location.href = contextPath
					+ "/archive/updateLowCustInfoAction.do?action=finishSecond";
		}
	}

	/**
	 * 
	 */
	function switchPanel(value) {
		if (value == "show") {
			$("#showPanel").css("display", "none");
			$("#hidePanel").css("display", "block");
			$("#treePanel").css("display", "block");
		} else if (value == "hide") {
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
      <jsp:param value="3" name="pageType"/>
   </jsp:include>
   <div id="main">
    <div id="tool">
      <div class="opbutton1">
        <input class="input3" type="button" value="新增变压器" onClick="openTran();" />
        <input class="input4" type="button" value="新增台区总表" onClick="openTotalMeter();" />
        <input class="input3" type="button" value="新增集中器" onClick="openLowCustTerm();" />
        <input class="input4" type="button" value="新增低压用户" onClick="openLowCust();" />
        <input class="input4" type="button" value="新增负控终端" onClick="openBigCustTerm();" />
        <input type="hidden" name="tgId" value="${tgId}">
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
              <iframe name="treeframe" width="100%" height="100%" frameborder="0" scrolling="auto" src="<peis:contextPath/>/windowTreeAction.do?action=getTree&WINDOW=TREE&SMXBH=TREE_ARCHIVE_0001&tgId=<peis:param type="PAGE" paramName="tgId"/>&objectType=10&queryFunction=query()&typicalType=1&limited=false&limitedtype="></iframe>
            </td>
            <td width="8" class="bg_panel" valign="middle">
              <table width="100%" height="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td id="hidePanel" valign="middle"><img src="<peis:contextPath/>/img/bt_hide.gif" alt="隐藏对象树" id="bt_hide" width="8" height="54" border="0" id="bt_hide" style="cursor:pointer;" onclick="switchPanel('hide')" onmouseover="swapImage('bt_hide', '<peis:contextPath/>/img/bt_hide_o.gif');" onmouseout="swapImage('bt_hide', '<peis:contextPath/>/img/bt_hide.gif');"></td>
                </tr>
                <tr>
                  <td id="showPanel" valign="middle"><img src="<peis:contextPath/>/img/bt_show.gif" alt="显示对象树" id="bt_show" width="8" height="54" border="0" id="bt_show" style="cursor:pointer;" onclick="switchPanel('show')" onmouseover="swapImage('bt_show', '<peis:contextPath/>/img/bt_show_o.gif');" onmouseout="swapImage('bt_show', '<peis:contextPath/>/img/bt_show.gif');"></td>
                </tr>
              </table>
            </td>
            <td class="pad10">
              <iframe name="mainframe" width="100%" height="100%" frameborder="0" scrolling="no" src="<peis:contextPath/>/jsp/archive/tgMain.jsp"></iframe>
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