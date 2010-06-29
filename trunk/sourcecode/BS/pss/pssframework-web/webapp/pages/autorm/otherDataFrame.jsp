<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>其他数据项</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/greybox.css"/>
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
     //$("iframe[name='mainframe']").attr("src",url);
     //alert(objectId+"---"+objectName);
     var datagroupRm = objectId ; 
     var proNo = $("#proNo").val();
     if(datagroupRm != "" && datagroupRm != null){
        //commanditem = commCode;
        var url =  contextPath + "/do/autorm/otherDataQuery.do?action=normalMode&datagroupRm='"+datagroupRm+"'&proNo='"+proNo+"'&sqlCode=AL_AUTORM_3000&showType=all";
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
    initTree();
});

//初始化对象树
function initTree(){
   var proNo = $("#proNo").val();
   var gpChar = $("#gpChar").val();
   var sysObject = '${sysObject}';
   var url = contextPath + "/windowTreeAction.do?action=getTree&WINDOW=TREE&SMXBH=TREE_AUTORM_0001&proNo='"+proNo+"'&gpChar='"+gpChar+"'&sysObject='"+sysObject+"'&objectType=10&queryFunction=query()&typicalType=1&limited=false&limitedtype=";
   $("iframe[name='treeframe']").attr("src",url);
}
//确定
function confirm(){
   var proNo = $("#proNo").val();
   var sysObject = '${sysObject}';
   var commd ;
   var commanditem = mainframe.assembleCommItems();
   if(commanditem != "" && commanditem != null){
       commd = proNo + "#" + sysObject + "#" + commanditem + ";";
       //组装数据项
       var dataItemArr = mainframe.assembleDataItems();
       var date = $("#date",document.frames('mainframe').document).val().replace("-","").replace("-","");
       var hour = $("#hour",document.frames('mainframe').document).val();
       var minute = $("#minute",document.frames('mainframe').document).val(); 
       var timeData = date + hour + minute + "00";
       var dataGap = $("#timeStep",document.frames('mainframe').document).val();
       var points = $("#point",document.frames('mainframe').document).val();
       //alert(timeData + "---" + dataGap + "---" + points);
       //读取数据项
       //alert(commd);
       top.getMainFrameObj().readOtherData(commd,dataItemArr,timeData,dataGap,points,proNo);
       close();
   }
}
//关闭窗口
function close(){
  parent.GB_hide(); 
}
</script>
</head>
<body>
<div id="body">
  <div id="tool">
    <table border="0" cellpadding="0" cellspacing="0">
      <tr>
       <td class="label">规约类型：</td>
       <td class="dom">
          <c:if test="${sysObject=='1'}">
            <peis:selectlist name="proNo" sql="SL_ARCHIVE_0055" onChange="initTree();"/>
          </c:if>
          <c:if test="${sysObject=='2'}">
            <peis:selectlist name="proNo" sql="SL_ARCHIVE_0058" onChange="initTree();"/>
          </c:if>
          <c:if test="${sysObject=='3'}">
            <peis:selectlist name="proNo" sql="SL_ARCHIVE_0072" onChange="initTree();"/>
          </c:if>
          <c:if test="${sysObject=='4'}">
            <peis:selectlist name="proNo" sql="SL_ARCHIVE_0101" onChange="initTree();"/>
          </c:if>
       </td>
       <td class="label">测量点类型：</td>
       <td class="dom">
          <peis:selectlist name="gpChar" sql="SL_ARCHIVE_0093" onChange="initTree();"/>
       </td>
      </tr>
    </table>
  </div>
  <div id="main2" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 100));border-top: 1px #5d90d7 solid;">
    <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
      <tr>
        <td width="200" valign="top" id="treePanel">
          <iframe name="treeframe" width="100%" height="100%" frameborder="0" scrolling="auto" src=""></iframe>
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
          <iframe name="mainframe" id="mainframe" width="100%" height="100%" frameborder="0" scrolling="no" src="<peis:contextPath/>/jsp/autorm/otherDataMain.jsp"></iframe>
        </td>
      </tr>
    </table>
  </div>
  <div class="guidePanel">
    <input class="input1" type="button" value="确  定" onclick="confirm();" />
    <input class="input1" type="button" value="取  消" onclick="javascript:parent.GB_hide();" />
  </div>
</div>
</body>
</html>