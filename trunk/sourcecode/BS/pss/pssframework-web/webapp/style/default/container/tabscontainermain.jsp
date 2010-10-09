<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Main Tabs Container</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/plugin/easyui/easyui.css" />
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/plugin/easyui/icon.css" />
<script type="text/javascript" src="<pss:path type="bgcolor"/>/plugin/easyui/jquery.js"></script>
<script type="text/javascript" src="<pss:path type="bgcolor"/>/plugin/easyui/jquery.easyui.js"></script>
<script type="text/javascript">
var selectedTabTitle = null;
function showTab(tabTitle, url) {
    var exists = $('#MultiTaskTabs').tabs('exists', tabTitle);
    if(exists) {
        $('#MultiTaskTabs').tabs('select', tabTitle);
    }
    else {
        $('#MultiTaskTabs').tabs('add', {
            title: tabTitle,
            content: '<iframe scrolling="auto" frameborder="0" src="' + url + '" width="100%" height="100%"></iframe>',
            closable: true
        });
    }
    selectedTabTitle = tabTitle;
}

$().ready(function() {
    var url = '<pss:path type="webapp"/>' + '/homepage/homepage';
    $('#MultiTaskTabs').tabs('add', {
        title: '首页',
        content: '<iframe scrolling="auto" frameborder="0" src="' + url + '" width="100%" height="100%"></iframe>',
        closable: false
    });
    selectedTabTitle = '首页';

    $('#MultiTaskTabs').tabs('resize');
    //alert(1);
    $(window).resize(function() {
        //alert(2);
        $('#MultiTaskTabs').tabs('resize');
        $('#MultiTaskTabs').tabs('select', selectedTabTitle);
    });
});
</script>
</head>
<body style="width: 100%; height: 100%; margin: 0; padding: 0; overflow: hidden;">
<div id="MultiTaskTabs" class="easyui-tabs" fit="true"></div>
</body>
</html>