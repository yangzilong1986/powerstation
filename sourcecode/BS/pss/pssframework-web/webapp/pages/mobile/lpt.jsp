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
    $("#btnTripping").click( function() {
        tripping();
    });
    
    $("#btnClosing").click( function() {
        closing();
    });
});

function tripping() {
    //alert("tripping");
}

function closing() {
    //alert("closing");
}
</script>
</head>
<body>
<div align="right"><a href="<pss:path type="webapp"/>/mobile/"> 退 出 </a></div>
<div style="height: 50px;"><input type="hidden" id="psId" name="psId" value="${param.psId}" /></div>
<div style="height: 60px; text-align: center; vertical-align: bottom;">
  <input id="btnTripping" type="button" value=" 跳 闸 " style="font-size: 24px; width: 120px; height: 40px; vertical-align: middle;" />
</div>
<div style="height: 60px; text-align: center; vertical-align: bottom;">
  <input id="btnClosing" type="button" value=" 合 闸 " style="font-size: 24px; width: 120px; height: 40px; vertical-align: middle;" />
</div>
<div style="height: 50px;"></div>
</body>
</html>