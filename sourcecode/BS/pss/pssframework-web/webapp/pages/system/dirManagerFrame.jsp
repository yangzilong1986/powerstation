<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
<html:html lang="true">
<head>
<html:base />
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title><bean:message bundle="system" key="dept.manage.title" /></title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
function nodeEvent(objectType, objectID, objectName, orgType) {
    var dir_no = objectID;
    var dir_prop = objectType;
    document.frames["rep"].location.href = '<peis:contextPath/>/do/report/freeReportList.do?action=normalMode&sqlCode=AL_REPORT_0001&showType=pag&pageRows=20&DIR_NO='+dir_no+'&DIR_PORP='+dir_prop+'&random=' + Math.random();
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

	$("ul li").click(function(){
		var file = "";
    var rep = "";
		  if($(this).attr('class')=='tab_off'){
		      $(this).siblings().removeClass('tab_on').addClass('tab_off')
		    $(this).attr('class','tab_on');
		  }else{
		    return true;
		  }
          var sql = "";
          var dir = "";
          if($(this).attr('id') =='public'){
            sql = 'TREE_DIR_0001';
            dir = '1'; 
          }else{
        	  sql = 'TREE_DIR_0002';
        	  dir = '2'; 
          }
	      file ="<peis:contextPath/>/windowTreeAction.do?action=getTree&WINDOW=TREE&SMXBH="+sql+"&objectType=10&queryFunction=query()&typicalType=1&limited=false&limitedtype=#null&DIR_NO="+dir+'&random=' + Math.random();
          rep ='<peis:contextPath/>/do/report/freeReportList.do?action=normalMode&sqlCode=AL_REPORT_0001&showType=pag&pageRows=20&random=' + Math.random()+"&DIR_NO="+dir;
		    $('#DTree').attr('src',file);
		    $('#rep').attr('src',rep);
		  })
      
		  $('#public').click();
          switchPanel("show");
});

</script>
</head>
<body>
<div id="body">
<div class="tab">
<ul>
  <li class="tab_off" id="public">共有报表</li>
  <li class="tab_off" id="private">私有报表</li>
  <li class="clear"></li>
</ul>
<h1><a href="#"><img src="<peis:contextPath/>/img/bt_help.gif" width="14" height="15" /></a></h1>
</div>
<div id="main2"
  style="height: expression(((  document.documentElement.clientHeight ||   document.body.clientHeight) -42 ) );">
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="200" valign="top" id="treePanel"><iframe width="100%" height="100%" frameborder="0" id="DTree"
      name="DTree" src=""></iframe></td>
    <td width="8" class="bg_panel" valign="middle">
    <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td id="hidePanel" valign="middle"><img src="<peis:contextPath/>/img/bt_hide.gif" alt="隐藏对象树" id="bt_hide"
          width="8" height="54" border="0" id="bt_hide" style="cursor: pointer;" onclick="switchPanel('hide')"
          onmouseover="swapImage('bt_hide', '../../img/bt_hide_o.gif');"
          onmouseout="swapImage('bt_hide', '../../img/bt_hide.gif');"></td>
      </tr>
      <tr>
        <td id="showPanel" valign="middle"><img src="<peis:contextPath/>/img/bt_show.gif" alt="显示对象树" id="bt_show"
          width="8" height="54" border="0" id="bt_show" style="cursor: pointer;" onclick="switchPanel('show')"
          onmouseover="swapImage('bt_show', '../../img/bt_show_o.gif');"
          onmouseout="swapImage('bt_show', '../../img/bt_show.gif');"></td>
      </tr>
    </table>
    </td>
    <td class="pad10"><iframe width="100%" height="100%" frameborder="0" id="rep" src="" name='rep'></iframe></td>
  </tr>
</table>
</div>
</div>
</body>
</html:html>
