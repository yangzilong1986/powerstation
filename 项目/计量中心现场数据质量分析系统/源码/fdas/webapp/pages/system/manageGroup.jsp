<%@ page language="java" pageEncoding="UTF-8"%>


<%@include file="../../commons/taglibs.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>群组管理</title>
<link href="<peis:contextPath/>/css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";

//刷新群组列表
function flushList() {
    window.groupList.location.href = contextPath + '/system/groupAction.do?action=getGroupList&sqlcode=AL_SYSTEM_0100&random=' + Math.random();
}

//初始化
$(document).ready(function(){
    window.groupList.location.href = contextPath + '/system/groupAction.do?action=getGroupList&sqlcode=AL_SYSTEM_0100&random=' + Math.random();
    window.groupContent.location.href = contextPath + '/system/groupAction.do?action=showGroup&mothed=edit&random=' + Math.random();
});
</script>
</head>
<body>
<div id="body">
  <div class="tab">
    <ul>
	  <li class="tab_on"><bean:message bundle="system" key="group.manage.title" /></li>
	  <li class="clear"></li>
    </ul>
    <h1><a href="#"><img src="<peis:contextPath/>/img/bt_help.gif" width="14" height="15" /></a></h1>
  </div>
    <div id="main2" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 42));">
      <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
        <tr>
          <td width="450" valign="top" class="pad10" style="border-right:1px solid #5d90d7;">
            <iframe name="groupList"　src="" width="100%" height="100%" scrolling="no" frameborder="0"></iframe>
          </td>
          <td valign="top" class="pad5">
            <iframe name="groupContent" src="" width="100%" height="100%" scrolling="no" frameborder="0"></iframe>
          </td>
        </tr>
      </table>
    </div>
</div>
</body>
</html>
