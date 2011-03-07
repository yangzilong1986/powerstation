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
 var check = '0';
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
        $("#name").html("请输入用户名");
        $("#j_username").focus();
        return false;
    }else{
    	 $("#name").html("");
    }
    
    if($("#j_password").val() == "") {
        $("#password").html("请输入密码");
        $("#j_password").focus();
        return false;
    }else{
    	 $("#password").html("");
    }
    	
  	if($("#j_captcha").val() == "" ){
  		 $("#captcha").html("请输入验证码");
        return false;
    }else{
    	$("#captcha").html("");
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
        	if(!checkUser()){
        		return false;
        	}
        	 if(check =='1'){
        		$("#loginForm").submit();
        	}
            
        }
    });
    
    /* $("form:first").submit(function(){
    	
    });*/

   $("#submit1").click( function() {
	    if(check =='0'){
   		$("#j_captcha").blur();
 		}
	   if(checkUser() && check =='1') {
	            $("#loginForm").submit();
	        }else{
	        	return false;
	        } 
    });
   
   


	
    //判断是够存在父页面
    var url ='${pageContext.request.contextPath}/j_spring_security_logout';
    
    if('${error}' == 3 || '${error}' ==4){
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
    }
    ;
    
    $("#j_captcha").blur(function(){
        jQuery.ajax({
            url: '${ctx}/security/checkCaptcha.json',
            data:getData(),
            dataType:'json',
            type:'POST',
            cache: false,
            success: function(json){
              
             check = json;
             if(json=='1'){
           		check = '1';
           		$("#captcha").html("正确");
             }else{
            	 $("#captcha").html("验证码不对，请重输入！");
            	 refreshCaptcha();
            	 check = '0';
             }
       
              
            },error:function(XmlHttpRequest,textStatus, errorThrown){
           	 alert(XmlHttpRequest.responseText);
            }
          });
    })
});
function toggleButton(data){
	if(data){
		    $("#submit1").attr("disabled","");
	}else{
		 $("#submit1").attr("disabled","true");
	}
};

getData= function(){
	var data;
	  data = jQuery("form[id=loginForm]").serialize(); 
	return data;
	};
function closeWin() {
	window.close();
};


 function checkSubmit(){
	
	}
        
function refreshCaptcha() {
	$('#captchaImg').hide().attr('src','${ctx}/security/jcaptcha.jpg?' + Math.floor(Math.random()*100)).fadeIn();
}
</script>
</head>
<body bgcolor="#042f2f" style="text-align: center;">
<!-- <form id="loginForm" action="<pss:path type="style"/>/container/main.jsp" method="post"> -->
<form id="loginForm" action="${ctx}/j_spring_security_check" method="post" >
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
</c:choose> 
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td width="39%" height="30" align="right" class="fontw" >用户名：</td>
    <td  align="left" style="position: relative;"><input type="text" name="j_username" id="j_username"
      class="input1 required" style="width: 180px;" tabindex="1" value="${SPRING_SECURITY_LAST_USERNAME}"/>&nbsp;&nbsp;<span id="name" style="width: 150px;color: white;"></span></td>
  </tr>
  <tr>
    <td height="30" align="right" class="fontw">密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
    <td align="left" style="position: relative;"><input type="password" name="j_password" id="j_password"
      class="input1 required" style="width: 180px;" tabindex="2" />&nbsp;&nbsp;<span id="password" style="width: 150px;color: white;"></span></td>
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
      style="width: 63px;" class="input1 required" tabindex="3" />&nbsp; <span> <img id="captchaImg"
      src="${ctx}/security/jcaptcha.jpg" width="110" height="26" style="vertical-align: bottom; cursor: pointer"
      onclick="javascript:refreshCaptcha()" /></span>&nbsp;&nbsp;<span id="captcha" style="width: 150px;color: white;"></span></td>
  </tr>
   
  <tr>
    <td height="10"></td>
     <!--<td align="left" class="fontw"><input type="checkbox" name="_spring_security_remember_me" /> 两周内记住我</td>-->
  </tr>
  <tr>
    <td height="50">&nbsp;</td>
    <td align="left" style="position: relative;"><a href="#" tabindex="4"><img id="submit1"
      src="<pss:path type="bgcolor"/>/img/login_bt1.png" width="75" height="42" /></a><a href="#" tabindex="5"><img
      src="<pss:path type="bgcolor"/>/img/login_bt2.png" width="75" height="42" class="mgl15" /></a></td>
  </tr>
</table>
</div>
</div>
</div>
</form>
</body>
</html>