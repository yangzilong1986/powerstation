<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>低压漏保及配变管理系统</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/mobile.css" />
<script type="text/javascript">
function check() {
    var o_username = document.getElementById('j_username');
    var o_password = document.getElementById('j_password');
    if(o_username == null || o_username.value == "") {
        alert("请输入用户名");
        o_username.foucs();
        return false;
    }
    if(o_password == null || o_password == "") {
        alert("请输入密码");
        o_password.foucs();
        return false;
    }
    return true;
}

function reset() {
    var o_username = document.getElementById('j_username');
    var o_password = document.getElementById('j_password');
    o_username.value = "";
    o_password.value = "";
    o_username.foucs();
}
</script>
</head>
<body>
<div>
<form id="loginForm" name="loginForm" action="${ctx}/mobile/login" method="post">
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
      <input type="text" name="j_username" id="j_username" style="width: 120px;" />
    </td>
  </tr>
  <tr>
    <td height="30" colspan="2" align="center">密　码：
      <input type="password" name="j_password" id="j_password" style="width: 120px;" />
    </td>
  </tr>
  <tr>
    <td height="20" colspan="2" align="center"></td>
  </tr>
  <tr>
    <td height="40" colspan="2" align="center">
      <input id="btnLogin" type="submit" value=" 登 录 " style="font-size: 16px;" onclick="return check();" />
      <input id="btnReset" type="button" value=" 重 置 " style="font-size: 16px;" onclick="reset();" />
    </td>
  </tr>
  <tr>
    <td height="20" colspan="2" align="center"></td>
  </tr>
</table>
</form>
</div>
<div>${message}</div>
</body>
</html>