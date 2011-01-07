<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>低压漏保及配变管理系统</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    $("#btnParameterSetting").click( function() {
        //parameterSetting();
    });
    
    $("#btnTripping").click( function() {
        tripping();
    });
});

function parameterSetting() {
    //alert("parameterSetting");
    top.location = "<pss:path type="webapp"/>" + "/mobile/lps?psId=${param.psId}&random=" + Math.random();
}

function tripping() {
    //alert("tripping");
    top.location = "<pss:path type="webapp"/>" + "/mobile/lpt?psId=${param.psId}&random=" + Math.random();
}
</script>
</head>
<body>
<div align="right"><a href="<pss:path type="webapp"/>/mobile/"> 退 出 </a></div>
<div style="height: 30px;"><input type="hidden" id="psId" name="psId" value="${param.psId}" /></div>
<div style="height: 80px; text-align: center; vertical-align: bottom;">
  <input id="btnParameterSetting" type="button" value="漏保参数设置" style="font-size: 24px; width: 160px; height: 60px; vertical-align: middle;" />
</div>
<div style="height: 80px; text-align: center; vertical-align: bottom;">
  <input id="btnTripping" type="button" value="远程分合闸" style="font-size: 24px; width: 160px; height: 60px; vertical-align: middle;" />
</div>
<div style="height: 30px;"></div>
</body>
</html>