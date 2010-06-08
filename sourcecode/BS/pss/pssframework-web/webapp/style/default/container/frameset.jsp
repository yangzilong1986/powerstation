<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>frameset container</title>
</head>
<frameset id="mainframeset" name="mainframeset" rows="81,8,26,*,8" frameborder="0" border="0" framespacing="0">
  <frame id="header" name="header" src="<pss:path type="style"/>/container/header.jsp" noresize="noresize" scrolling="no" frameborder="0" />
  <frame id="panelupdown" name="panelupdown" src="<pss:path type="style"/>/container/panelupdown.jsp" noresize="noresize" scrolling="no" frameborder="0" />
  <frame id="menu" name="menu" src="<pss:path type="style"/>/container/menu.jsp" noresize="noresize" scrolling="no" frameborder="0" />
  <frameset id="centerframeset" name="centerframeset" cols="8,240,8,*,8" frameborder="1" border="0" framespacing="0">
    <frame id="borderleft" name="borderleft" src="<pss:path type="style"/>/container/borderleft.jsp" noresize="noresize" scrolling="no" frameborder="0" />
    <frame id="tabscontainerleft" name="tabscontainerleft" src="<pss:path type="style"/>/container/tabscontainerleft.jsp" noresize="noresize" scrolling="no" frameborder="0" />
    <frame id="panelleftright" name="panelleftright" src="<pss:path type="style"/>/container/panelleftright.jsp" noresize="noresize" scrolling="no" frameborder="0" />
    <frame id="tabscontainermain" name="tabscontainermain" src="<pss:path type="style"/>/container/tabscontainermain.jsp" noresize="noresize" scrolling="no" frameborder="0" />
    <frame id="borderright" name="borderright" src="<pss:path type="style"/>/container/borderright.jsp" noresize="noresize" scrolling="no" frameborder="0" />
  </frameset>
  <frame id="borderbottom" name="borderbottom" src="<pss:path type="style"/>/container/borderbottom.jsp" noresize="noresize" scrolling="no" frameborder="0" />
  <noframes>
    <body>
      <p>请使用支持frameset功能的浏览器！</p>
    </body>
  </noframes>
</frameset>
</html>