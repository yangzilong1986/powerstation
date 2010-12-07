<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

function logout() {
    if( confirm("确认退出系统？")) {
        top.location="${ctx}/j_spring_security_logout";
    }
}
</script>
</head>
<body>
<div class="bg_top" style="min-width: 1014px; _width: expression((document.documentElement.clientWidth||document.body.clientWidth)<1014 ? '1014px' : '');">
  <div id="logo" style="font-size: 40px; color: #FFF; font-weight: bold;">计量中心现场数据质量分析系统</div>
  <div id="top_r">
    <div id="top_r_t">
      <ul>
        <li>&nbsp;</li>
      </ul>
    </div>
  </div>
</div>
</body>
</html>