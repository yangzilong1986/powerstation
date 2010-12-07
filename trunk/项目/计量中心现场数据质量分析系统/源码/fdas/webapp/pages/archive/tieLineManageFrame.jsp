<%@page contentType="text/html; charset=UTF-8"%>

<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>联络线档案管理</title>
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
     var lineName=encodeURI(objectName,"utf-8");
     var params = {
                   "subsId":-1,
                   "lineName":lineName
     };
     if(objectType==-1 || objectType==4){//变电站
          url = contextPath + "/archive/lineInfoAcrion.do?action=queryTieLineList&subsId="+objectId+"&lineName=";
     }else if(objectType==9){//联络线路
          url = contextPath + "/archive/lineInfoAcrion.do?action=queryTieLineList&" + jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
     }
     if(objectId=="0" || objectId=="-1"){ //树节点为变电站或未关联变电站线路 ，右边页面不响应
         $("iframe[name='mainframe']").attr("src",contextPath+"/jsp/archive/tieLineManageMain.jsp");
     }else{
         $("iframe[name='mainframe']").attr("src",url);
     }
}
/**
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
      <jsp:param value="6" name="pageType"/>
    </jsp:include>
  <div id="main2" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 42));">
    <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
      <tr>
        <td width="200" valign="top" id="treePanel">
          <iframe name="treeframe" width="100%" height="100%" frameborder="0" scrolling="auto" src="<peis:contextPath/>/windowTreeAction.do?action=getTree&WINDOW=TREE&SMXBH=TREE_ARCHIVE_0003&objectType=10&queryFunction=query()&typicalType=1&limited=false&limitedtype="></iframe>
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
          <iframe name="mainframe" width="100%" height="100%" frameborder="0" scrolling="no" src="<peis:contextPath/>/jsp/archive/tieLineManageMain.jsp"></iframe>
        </td>
      </tr>
    </table>
  </div>
</div>
</body>
</html>