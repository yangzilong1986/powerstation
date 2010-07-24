<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>电力用户用电信息采集系统</title>
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

function checkUser() {
    if($("#username").val() == "admin" && $("#password").val() == "admin") {
        return true;
    }
    else if($("#username").val() == "") {
        alert("请输入用户名");
        $("#username").focus();
        return false;
    }
    else if($("#password").val() == "") {
        alert("请输入密码");
        $("#password").focus();
        return false;
    }
    else {
        alert("用户名或密码不正确，请重新输入");
        $("#username").focus();
        return false;
    }
}

$(document).ready( function() {
    if(isIE()) {
        correctPNG();
    }

    $(document).keydown( function(e) {
        var keyCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
        if(keyCode == 13) {
            if(checkUser()) {
                $("#loginForm").submit();
            }
        }
    });

    $("#submit1").click( function() {
        if(checkUser()) {
            $("#loginForm").submit();
        }
    });
});
</script>
</head>
<body scroll="no" bgcolor="#042f2f" style="text-align: center;">
<form id="loginForm" action="<pss:path type="style"/>/container/main.jsp" method="post">
  <div class="login_bg">
    <div class="login_bg1">
      <div class="pt30 tc"><img src="<pss:path type="bgcolor"/>/img/login_logo1.png" /></div>
      <div class="pt40">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="39%" height="30" align="right" class="fontw">用户名：</td>
            <td width="61%" align="left" style="position: relative;"><input type="text" name="username" id="username" class="input1" style="width:180px;" /></td>
          </tr>
          <tr>
            <td height="30" align="right" class="fontw">密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
            <td align="left" style="position: relative;"><input type="password" name="password" id="password" class="input1" style="width:180px;" /></td>
          </tr>
          <!-- 
          <tr>
            <td height="30" align="right" class="fontw">单&nbsp;&nbsp;&nbsp;&nbsp;位：</td>
            <td>
                <select class="font1" name="select" size="1" style="width:185px; height:24px;">
                    <option value="2" selected >省电力局</option>
                    <option value="1">省电力局</option>
                </select>
            </td>
          </tr>
           -->
          <tr>
            <td height="50">&nbsp;</td>
            <td align="left" style="position: relative;"><a href="#"><img id="submit1" src="<pss:path type="bgcolor"/>/img/login_bt1.png" width="75" height="42" /></a><a href="#"><img src="<pss:path type="bgcolor"/>/img/login_bt2.png" width="75" height="42" class="mgl15" /></a></td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</form>
</body>
</html>