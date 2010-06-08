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
    if(menuId = '3001') {
        parent.tabscontainermain.location = '<pss:path type="webapp"/>/archive/tginfo';
    }
    else {
        parent.tabscontainermain.location = "<pss:path type="webapp"/>/commons/inmaking.jsp";
    }
}
</script>
</head>
<body>
<div id="menu">
  <dl id="dl01" style="display: block;">
    <dt>
      <div id="Fod">
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('1001'); return false;">实时召测</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('1002'); return false;">保护开关远程跳合闸</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('1003'); return false;">远程试验跳</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('1004'); return false;">数据检查</a></div>
      </div>
    </dt>
  </dl>
  <dl id="dl02" style="display: none;">
    <dt>
      <div id="Fod">
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('2001'); return false;">电量分析</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('2002'); return false;">极值分析</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('2003'); return false;">三相不平衡</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('2004'); return false;">增值服务</a></div>
      </div>
    </dt>
  </dl>
  <dl id="dl03" style="display: none;">
    <dt>
      <div id="Fod">
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('3001'); return false;">台区档案维护</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('3002'); return false;">单个终端参数维护</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('3003'); return false;">批量终端参数维护</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('3004'); return false;">设备校时</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('3005'); return false;">终端复位</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('3006'); return false;">异常处理</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('3007'); return false;">日志查询</a></div>
      </div>
    </dt>
  </dl>
  <dl id="dl04" style="display: none;">
    <dt>
      <div id="Fod">
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('4001'); return false;">跳闸信息批量查询</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('4002'); return false;">总表数据批量查询</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('4003'); return false;">开关数据和状态批量查询</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('4004'); return false;">台区综合</a></div>
      </div>
    </dt>
  </dl>
  <dl id="dl05" style="display: none;">
    <dt>
      <div id="Fod">
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('5001'); return false;">权限管理</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('5002'); return false;">单位管理</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('5003'); return false;">编码管理</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('5004'); return false;">异常编码管理</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('5005'); return false;">参数模板管理</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('5006'); return false;">配置管理</a></div>
        <div onmouseover=changeFod(this)><a href="#" onclick="selectMenu('5007'); return false;">群组管理</a></div>
      </div>
    </dt>
  </dl>
</div>
</body>
</html>