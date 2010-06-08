<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>menu</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/container.css" />
<script type="text/javascript">
function selectMenu(menuId) {
    if(menuId = '1001') {
        parent.tabscontainermain.location = '<pss:path type="webapp"/>/archive/tginfo';
    }
}
</script>
</head>
<body>
<div id="menu">
  <dl id="dl1">
    <dt>
      <div id="Fod">
        <div><a href="#" onclick="selectMenu('1001')">档案维护</a></div>
        <!-- <div><a href="#">数据采集管理</a></div>
        <div><a href="#">设有序用电</a></div>
        <div><a href="#">预付费管理</a></div>
        <div><a href="#">接口管理</a></div>
        <div><a href="#">线损分析</a></div>
        <div><a href="#">有序用电</a></div>
        <div><a href="#">档案管理</a></div>
        <div><a href="#">档案管理</a></div> -->
      </div>
    </dt>
  </dl>
  <!-- <dl id="dl2" style=>
    <dt>
      <div id="Fod">
        <div onmouseover=changeFod(this)><a href="#">采集点设置</a></div>
        <div onmouseover=changeFod(this)><a href="#">数据采集管理</a></div>
        <div onmouseover=changeFod(this)><a href="#">设有序用电</a></div>
        <div onmouseover=changeFod(this)><a href="#">预付费管理</a></div>
        <div onmouseover=changeFod(this)><a href="#">接口管理</a></div>
        <div onmouseover=changeFod(this)><a href="#">线损分析</a></div>
        <div onmouseover=changeFod(this)><a href="#">有序用电</a></div>
        <div onmouseover=changeFod(this)><a href="#">档案管理</a></div>
        <div onmouseover=changeFod(this)><a href="#">档案管理</a></div>
      </div>
    </dt>
  </dl>
  <dl id="dl3">
    <dt>
      <div id="Fod">
        <div onmouseover=changeFod(this)><a href="#">采集点设置</a></div>
        <div onmouseover=changeFod(this)><a href="#">数据采集管理</a></div>
        <div onmouseover=changeFod(this)><a href="#">设有序用电</a></div>
        <div onmouseover=changeFod(this)><a href="#">预付费管理</a></div>
        <div onmouseover=changeFod(this)><a href="#">接口管理</a></div>
        <div onmouseover=changeFod(this)><a href="#">线损分析</a></div>
        <div onmouseover=changeFod(this)><a href="#">有序用电</a></div>
        <div onmouseover=changeFod(this)><a href="#">档案管理</a></div>
        <div onmouseover=changeFod(this)><a href="#">档案管理</a></div>
      </div>
    </dt>
  </dl> -->
</div>
</body>
</html>