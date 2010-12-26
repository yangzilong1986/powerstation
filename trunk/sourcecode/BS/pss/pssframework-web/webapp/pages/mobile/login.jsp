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
    $("#btnLogin").click( function() {
        login();
    });
    
    $("#btnReset").click( function() {
        reset();
    });
});

function login() {
    //alert("login");
    top.location = "<pss:path type="webapp"/>" + "/mobile/clp";
}

function reset() {
    $("#j_username").val("");
    $("#j_password").val("");
}
</script>
</head>
<body>
<div>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="50" colspan="2" align="center" style="font-size: 24px;">乾龙电器</td>
  </tr>
  <tr>
    <td height="30" colspan="2" align="center">低压漏保及配变管理系统</td>
  </tr>
  <tr>
    <td height="20" colspan="2" align="center"></td>
  </tr>
  <tr>
    <td height="30" colspan="2" align="center">用户名：
      <input type="text" name="j_username" id="j_username" class="input1 required" style="width: 120px;" />
    </td>
  </tr>
  <tr>
    <td height="30" colspan="2" align="center">密　码：
      <input type="password" name="j_password" id="j_password" class="input1 required" style="width: 120px;" />
    </td>
  </tr>
  <tr>
    <td height="20" colspan="2" align="center"></td>
  </tr>
  <tr>
    <td height="40" colspan="2" align="center">
      <input id="btnLogin" type="button" value=" 登 录 " style="font-size: 16px;" />
      <input id="btnReset" type="button" value=" 重 置 " style="font-size: 16px;" />
    </td>
  </tr>
  <tr>
    <td height="20" colspan="2" align="center"></td>
  </tr>
</table>
</div>
</body>
</html>