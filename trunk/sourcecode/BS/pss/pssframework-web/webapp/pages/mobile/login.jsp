<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>低压漏保及配变管理系统</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/mobile.css" />
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
          <input id="btnLogin" type="submit" value=" 登 录 " style="font-size: 16px;" />
          <input id="btnReset" type="reset"" value=" 重 置 " style="font-size: 16px;" />
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