<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>menu</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/container.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript">
function showMainTab(tabTitle, url) {
    if(url.indexOf('/') == 0) {
        url = '<pss:path type="webapp"/>' + url;
    }
    parent.tabscontainermain.showTab(tabTitle, url);
}

function selectMenu(menuId, url) {
    var tabTitle = $("#a" + menuId).html();
    if(url == '') {
        url = '/commons/inmaking.jsp';
    }
    showMainTab(tabTitle, url);
}

function showBar(firstLevel) {
    for(var i = 1; i <= 5; i++) {
        if(i == firstLevel) {
            //alert("#dl0" + i + ":" + "block");
            $("#dl0" + i).css("display", "block");
        }
        else {
            //alert("#dl0" + i + ":" + "none");
            $("#dl0" + i).css("display", "none");
        }
    }
}

function changeFod(obj) {
    
}
</script>
</head>
<body>
<div id="menu">
  <dl id="dl01" style="display: block;">
    <dt>
      <div id="Fod">
        <div onmouseover=changeFod(this)><a id="a1001" href="#" onclick="selectMenu('1001', '/fdas/dataAnalysisQuery'); return false;">数据分析查询</a></div>
      </div>
    </dt>
  </dl>
</div>
</body>
</html>