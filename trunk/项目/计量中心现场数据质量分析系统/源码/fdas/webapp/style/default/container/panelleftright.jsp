<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Left and Right Panel</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/container.css" />
<script type="text/javascript">
/*
function switchPanel(value) {
    if(value == "show") {
        showPanel.style.display="none";
        hidePanel.style.display="";
    }
    else if(value == "hide") {
        showPanel.style.display="";
        hidePanel.style.display="none";
    }
}

function MM_swapImgRestore() { //v3.0
    var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
    var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
    var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
    if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
    for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
    if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
    var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
    if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}*/
</script>
</head>
<body bgcolor="#0a5757">
<div class="panel_leftandright">
  <!-- 
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td id="hidePanel" name="hidePanel"><img src="<pss:path type="bgcolor"/>/img/bt_hide.gif" alt="隐藏功能面板" name="bt_hide" width="8" height="54" border="0" id="bt_hide" style="cursor:pointer; " onClick="switchPanel('hide')" onMouseOver="MM_swapImage('bt_hide','','<pss:path type="bgcolor"/>/img/bt_hide_o.gif',1)" onMouseOut="MM_swapImgRestore()"></td>
    </tr>
    <tr>
      <td id="showPanel" name="showPanel"><img src="<pss:path type="bgcolor"/>/img/bt_show.gif" alt="显示功能面板" name="bt_show" width="8" height="54" border="0" id="bt_show" style="cursor:pointer; " onClick="switchPanel('show')" onMouseOver="MM_swapImage('bt_show','','<pss:path type="bgcolor"/>/img/bt_show_o.gif',1)" onMouseOut="MM_swapImgRestore()"></td>
    </tr>
  </table>
   -->
</div>
</body>
</html>