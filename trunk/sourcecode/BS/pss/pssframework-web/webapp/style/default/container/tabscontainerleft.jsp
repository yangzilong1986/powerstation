<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href='<pss:path type="bgcolor"/>/css/container.css' type="text/css" rel="stylesheet" />
<title>Object View Container</title>
</head>
<body>
<iframe id="tree" name="tree" scrolling="auto" frameborder="0" src='${ctx}/tree' width="100%" height="100%"></iframe>
</body>
</html>