<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>border bottom</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/container.css" />
</head>
<body>
<div class="footer cb">
<p align="right">操作员：<security:authentication property="principal" var="loginUser"></security:authentication>${loginUser.username}
&nbsp; 登录时间： <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${loginUser.loginTime}"></fmt:formatDate><span></span></p>
<p style="width: 8px"></p>
</div>
</body>
</html>