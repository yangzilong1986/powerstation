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
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript">
function showMainTab(tabTitle, url) {
    if(url.indexOf('/') == 0) {
        url = '<pss:path type="webapp"/>' + url;
    }
    parent.tabscontainermain.showTab(tabTitle, url);
}

function selectMenu(menuId, url) {
    var tabTitle = $("#a" + menuId).html();
    if(url == '') {
        url = '/commons/inmaking.jsp';
    }
    showMainTab(tabTitle, url);
}

function showBar(firstLevel) {
    for(var i = 1; i <= 5; i++) {
        if(i == firstLevel) {
            //alert("#dl0" + i + ":" + "block");
            $("#dl0" + i).css("display", "block");
        }
        else {
            //alert("#dl0" + i + ":" + "none");
            $("#dl0" + i).css("display", "none");
        }
    }
}

function changeFod(obj) {
    
}
</script>
</head>
<body>
<div id="menu">
  <dl id="dl01" style="display: block;">
    <dt>
      <div id="Fod">
      <security:authorize ifAnyGranted="ROLE_RESOURCE_1001">
        <div onmouseover=changeFod(this)><a id="a1001" href="#" onclick="selectMenu('1001', '/autorm/realTimeReading'); return false;">数据召测</a></div></security:authorize>
        <security:authorize ifAnyGranted="ROLE_RESOURCE_1002">
        <div onmouseover=changeFod(this)><a id="a1002" href="#" onclick="selectMenu('1002', '/psmanage/psmon/frame'); return false;">漏保监测</a></div></security:authorize>
        <security:authorize ifAnyGranted="ROLE_RESOURCE_1003">
        <div onmouseover=changeFod(this)><a id="a1003" href="#" onclick="selectMenu('1003', '/tgmanage/tgmon/frame'); return false;">台区监测</a></div></security:authorize>
      </div>
    </dt>
  </dl>
  <!--  
  <dl id="dl02" style="display: none;">
    <dt>
      <div id="Fod">
      <security:authorize ifAnyGranted="ROLE_RESOURCE_2001">
        <div onmouseover=changeFod(this)><a id="a2001" href="#" onclick="selectMenu('2001', ''); return false;">电量分析</a></div></security:authorize>
        <security:authorize ifAnyGranted="ROLE_RESOURCE_2002">
        <div onmouseover=changeFod(this)><a id="a2002" href="#" onclick="selectMenu('2002', ''); return false;">极值分析</a></div></security:authorize>
        <security:authorize ifAnyGranted="ROLE_RESOURCE_2003">
        <div onmouseover=changeFod(this)><a id="a2003" href="#" onclick="selectMenu('2003', ''); return false;">三相不平衡</a></div></security:authorize>
        <security:authorize ifAnyGranted="ROLE_RESOURCE_2004">
        <div onmouseover=changeFod(this)><a id="a2004" href="#" onclick="selectMenu('2004', ''); return false;">增值服务</a></div></security:authorize>
      </div>
    </dt>
  </dl>
  -->
  <dl id="dl03" style="display: none;">
    <dt>
      <div id="Fod">
      <security:authorize ifAnyGranted="ROLE_RESOURCE_3001">
        <div onmouseover=changeFod(this)><a id="a3001" href="#" onclick="selectMenu('3001', '/archive/tginfo'); return false;">台区档案</a></div></security:authorize>
       <security:authorize ifAnyGranted="ROLE_RESOURCE_3002">
        <div onmouseover=changeFod(this)><a id="a3002" href="#" onclick="selectMenu('3002', '/eparam/termparam'); return false;">终端参数</a></div></security:authorize>
       <security:authorize ifAnyGranted="ROLE_RESOURCE_3003">
        <div onmouseover=changeFod(this)><a id="a3003" href="#" onclick="selectMenu('3003', ''); return false;">设备校时</a></div></security:authorize>
        <security:authorize ifAnyGranted="ROLE_RESOURCE_3004">
        <div onmouseover=changeFod(this)><a id="a3004" href="#" onclick="selectMenu('3004', ''); return false;">终端复位</a></div></security:authorize>
        <!--<security:authorize ifAnyGranted="ROLE_RESOURCE_3005">
        <div onmouseover=changeFod(this)><a id="a3005" href="#" onclick="selectMenu('3005', ''); return false;">异常处理</a></div></security:authorize>
        <security:authorize ifAnyGranted="ROLE_RESOURCE_3006">
        <div onmouseover=changeFod(this)><a id="a3006" href="#" onclick="selectMenu('3006', ''); return false;">日志查询</a></div></security:authorize> -->
      </div>
    </dt>
  </dl>
  <dl id="dl04" style="display: none;">
    <dt>
      <div id="Fod">
      <security:authorize ifAnyGranted="ROLE_RESOURCE_4001">
        <div onmouseover=changeFod(this)><a id="a4001" href="#" onclick="selectMenu('4001', '/statistics/psEventQuery'); return false;">跳闸信息</a></div></security:authorize>
        <security:authorize ifAnyGranted="ROLE_RESOURCE_4002">
        <div onmouseover=changeFod(this)><a id="a4002" href="#" onclick="selectMenu('4002', '/statistics/sumMeterDataQuery'); return false;">总表数据</a></div></security:authorize>
        <security:authorize ifAnyGranted="ROLE_RESOURCE_4003">
        <div onmouseover=changeFod(this)><a id="a4003" href="#" onclick="selectMenu('4003', '/statistics/psDataQuery'); return false;">开关数据</a></div></security:authorize>
              <security:authorize ifAnyGranted="ROLE_RESOURCE_4004">
        <div onmouseover=changeFod(this)><a id="a4004" href="#" onclick="selectMenu('4004', '/statistics/termData'); return false;">终端事件</a></div></security:authorize>
      </div>
    </dt>
  </dl>
  <dl id="dl05" style="display: none;">
    <dt>
      <div id="Fod">
      <security:authorize ifAnyGranted="ROLE_RESOURCE_5001">
        <div onmouseover=changeFod(this)><a id="a5001" href="#" onclick="selectMenu('5001', '/system/permission');">权限管理</a></div></security:authorize>
         <!--<security:authorize ifAnyGranted="ROLE_RESOURCE_5002">
        <div onmouseover=changeFod(this)><a id="a5002" href="#" onclick="selectMenu('5002', ''); return false;">单位管理</a></div></security:authorize>
        <security:authorize ifAnyGranted="ROLE_RESOURCE_5003">
        <div onmouseover=changeFod(this)><a id="a5003" href="#" onclick="selectMenu('5003', ''); return false;">编码管理</a></div></security:authorize>
        <security:authorize ifAnyGranted="ROLE_RESOURCE_5004">
        <div onmouseover=changeFod(this)><a id="a5004" href="#" onclick="selectMenu('5004', ''); return false;">异常编码管理</a></div></security:authorize>
        <security:authorize ifAnyGranted="ROLE_RESOURCE_5005">
        <div onmouseover=changeFod(this)><a id="a5005" href="#" onclick="selectMenu('5005', ''); return false;">参数模板管理</a></div></security:authorize>
        <security:authorize ifAnyGranted="ROLE_RESOURCE_5006">
        <div onmouseover=changeFod(this)><a id="a5006" href="#" onclick="selectMenu('5006', ''); return false;">配置管理</a></div></security:authorize>
        <security:authorize ifAnyGranted="ROLE_RESOURCE_5007">
        <div onmouseover=changeFod(this)><a id="a5007" href="#" onclick="selectMenu('5007', ''); return false;">群组管理</a></div></security:authorize>-->
      </div>
    </dt>
  </dl>
</div>
</body>
</html>