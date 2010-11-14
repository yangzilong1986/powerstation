<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter"%>
<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter"%>
<%@ page import="org.springframework.security.core.AuthenticationException"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>低压漏保及配变管理系统</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/container.css" />
<script src="${ctx}/scripts/jquery.js" type="text/javascript"></script>
<script src="${ctx}/scripts/validate/jquery.validate.js" type="text/javascript"></script>
<script src="${ctx}/scripts/validate/messages_cn.js" type="text/javascript"></script>
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
   /* if($("#j_username").val() == "admin" && $("#j_password").val() == "admin") {
        return true;
    }*/
    if($("#j_username").val() == "") {
        alert("请输入用户名");
        $("#j_username").focus();
        return false;
    }
    else if($("#j_password").val() == "") {
        alert("请输入密码");
        $("#j_password").focus();
        return false;
    }else if($("#j_captcha").val() == ""){
    	alert("请输入验证码");
        $("#j_captcha").focus();
        return false;
    }

    return true;
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
    
    //判断是够存在父页面
    var url ='${pageContext.request.contextPath}/pss-web/j_spring_security_logout';
   try{
	   if(opener != null){
		   closeWin();
		   opener.top.location.href = url;
	   }
	   if(parent.parent.tabscontainermain != null){
		   //alert(parent.parent.tabscontainermain.location)
		   top.location.href = url;
	   }
   }catch(e){
	   
   }
    
});

function closeWin() {
	window.close();
};

function refreshCaptcha() {
	$('#captchaImg').hide().attr('src','${ctx}/security/jcaptcha.jpg?' + Math.floor(Math.random()*100)).fadeIn();
}
</script>
</head>
<body bgcolor="#042f2f" style="text-align: center;">
<!-- <form id="loginForm" action="<pss:path type="style"/>/container/main.jsp" method="post"> -->
<form id="loginForm" action="${ctx}/j_spring_security_check" method="post">
<div class="login_bg">
<div class="login_bg1">
<div class="pt30 tc"><img src="<pss:path type="bgcolor"/>/img/login_logo1.png" /></div>
<div class="pt40"><c:choose>
  <c:when test="${error == 1}">
    <div class="error"><font color="white">用户名密码错误,请重试.</font></div>
  </c:when>
  <c:when test="${error == 2}">
    <div class="error"><font color="white">验证码错误,请重试.</font></div>
  </c:when>
  <c:when test="${error == 3}">
    <div class="error"><font color="white">此帐号已从别处登录.</font></div>
  </c:when>
  <c:when test="${error == 4}">
    <div class="error"><font color="white">会话超时.</font></div>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose> <c:choose>
  <c:when test="${succ == 1}">
    <div class="error"><font color="white">成功注销.</font></div>
  </c:when>
  <c:otherwise></c:otherwise>
</c:choose> ${SPRING_SECURITY_LAST_EXCEPTION.message}
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="39%" height="30" align="right" class="fontw">用户名：</td>
    <td width="61%" align="left" style="position: relative;"><input type="text" name="j_username" id="j_username"
      class="input1 required" style="width: 180px;" /></td>
  </tr>
  <tr>
    <td height="30" align="right" class="fontw">密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
    <td align="left" style="position: relative;"><input type="password" name="j_password" id="j_password"
      class="input1 required" style="width: 180px;" /></td>
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
    <td align="right" class="fontw">验证码：</td>
    <td height="30" align="left" style="position: relative;"><input type='text' name='j_captcha' id="j_captcha"
      style="width: 63px;" class="input1 required" />&nbsp; <span> <img id="captchaImg"
      src="${ctx}/security/jcaptcha.jpg" width="110" height="26" style="vertical-align: bottom; cursor: pointer"
      onclick="javascript:refreshCaptcha()" /></span></td>
  </tr>
  <tr>
    <td height="30"></td>
    <td align="left" class="fontw"><input type="checkbox" name="_spring_security_remember_me" /> 两周内记住我</td>
  </tr>
  <tr>
    <td height="50">&nbsp;</td>
    <td align="left" style="position: relative;"><a href="#"><img id="submit1"
      src="<pss:path type="bgcolor"/>/img/login_bt1.png" width="75" height="42" /></a><a href="#"><img
      src="<pss:path type="bgcolor"/>/img/login_bt2.png" width="75" height="42" class="mgl15" /></a></td>
  </tr>
</table>
</div>
</div>
</div>
</form>
</body>
</html>