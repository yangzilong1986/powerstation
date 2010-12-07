<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Up and Down Panel</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/container.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript">
var stretch = true;
function stretchPanel() {
    if(stretch == true) {
        parent.frames['mainframeset'].rows = '0,8,26,*,24';
        stretch = false;
    }
    else {
        parent.frames['mainframeset'].rows = '81,8,26,*,24';
        stretch = true;
    }
}
</script>
</head>
<body>
<div class="panel_upanddown">
  <p><a href="#" onclick="stretchPanel(); return false;"><img src="<pss:path type="bgcolor"/>/img/up_panel_bot.gif" width="116" height="4" /></a></p>
</div>
</body>
</html>