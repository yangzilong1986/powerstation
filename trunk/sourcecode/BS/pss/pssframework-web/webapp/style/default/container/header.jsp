<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>header container</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/container.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript">
function isIE() {
    if($.browser.msie && $.browser.version == '6.0') { return true; }
    return false;
}

function correctPNG() {
    for(var i = 0; i < document.images.length; i++) {
        var img = document.images[i];
        var LW = img.width;
        var LH = img.height;
        var imgName = img.src.toUpperCase();
        if(imgName.substring(imgName.length-3, imgName.length) == "PNG") { 
            img.style.filter += 'progid:DXImageTransform.Microsoft.AlphaImageLoader(src=' + img.src + ', sizingmethod=scale);';
            img.src = '<pss:path type="bgcolor"/>/img/blank.gif';
            img.width = LW;
            img.height = LH;
        }
    }
}

$(document).ready( function() {
    if(isIE()) {
        correctPNG();
    }
});

function showBar(firstLevel) {
    parent.menu.showBar(firstLevel);
}
</script>
</head>
<body>
<div class="bg_top" style="min-width: 1004px; _width: expression((document.documentElement.clientWidth||document.body.clientWidth)<1004 ? '1004px' : '');">
  <div id="logo"><img src="<pss:path type="bgcolor"/>/img/logo1.png" width="541" height="59" /></div>
  <div id="top_r">
    <div id="top_r_t">
      <ul>
      <security:authorize ifAnyGranted="ROLE_RESOURCE_10">
        <li><img src="<pss:path type="bgcolor"/>/img/icon01.png" width="65" height="30" /><a href="#" onclick="showBar(1); return false;">基本应用</a></li></security:authorize>
       <security:authorize ifAnyGranted="ROLE_RESOURCE_20">
        <li><img src="<pss:path type="bgcolor"/>/img/icon02.png" width="65" height="31" /><a href="#" onclick="showBar(2); return false;">高级应用</a></li></security:authorize>
       <security:authorize ifAnyGranted="ROLE_RESOURCE_30">
        <li><img src="<pss:path type="bgcolor"/>/img/icon03.png" width="65" height="30" /><a href="#" onclick="showBar(3); return false;">运行管理</a></li></security:authorize>
       <security:authorize ifAnyGranted="ROLE_RESOURCE_40">
        <li><img src="<pss:path type="bgcolor"/>/img/icon04.png" width="65" height="30" /><a href="#" onclick="showBar(4); return false;">统计查询</a></li></security:authorize>
       <security:authorize ifAnyGranted="ROLE_RESOURCE_50">
        <li><img src="<pss:path type="bgcolor"/>/img/icon05.png" width="65" height="30" /><a href="#" onclick="showBar(5); return false;">系统管理</a></li></security:authorize>
      </ul>
    </div>
    <div id="top_menu">
      <ul>
        <li><a title="帮助" href="#"><img src="<pss:path type="bgcolor"/>/img/top_menu3.gif" width="16" height="16" /></a></li>
        <li><a title="返回" href="#"><img src="<pss:path type="bgcolor"/>/img/top_menu2.gif" width="16" height="16" /></a></li>
        <li><a title="主页" href="#"><img src="<pss:path type="bgcolor"/>/img/top_menu1.gif" width="16" height="16" /></a></li>
      </ul>
    </div>
  </div>
</div>
</body>
</html>