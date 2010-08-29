<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>



<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title><bean:message bundle="gather" key="gather.task.taskmanager.title"/></title>
    <link href="<peis:contextPath/>/css/main.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">
    function showTask(taskId, protocol_type){
        var operate = document.getElementById("taskoperate");
        operate.src = "<peis:contextPath/>/autorm/taskAction.do?action=taskShow&task_id=" + taskId + "&protocol_type=" + protocol_type + "&rand=" + Math.random();
    }
    
    function flushList(){
        window.taskList.location.reload();
    }
    </script>
  </head>
  
  <body>
  <div id="body">
    <div class="tab">
      <ul>
        <li id="tab_1" class="tab_on"><bean:message bundle="gather" key="gather.task.taskmanager.title"/></li>
        <li class="clear"></li>
      </ul>
      <h1><a href="#"><img src="<peis:contextPath/>/img/bt_help.gif" width="14" height="15" /></a></h1>
    </div>
    <div id="main2" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 42));">
      <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
        <tr>
          <td width="450" valign="top" class="pad10" style="border-right:1px solid #5d90d7;">
            <iframe id="taskList" src="<peis:contextPath/>/autorm/taskAction.do?action=taskList" width="100%" height="100%" scrolling="no" frameborder="0"></iframe>
          </td>
          <td valign="top" class="pad5">
            <iframe id="taskoperate" src="<peis:contextPath/>/autorm/taskAction.do?action=taskShow" width="100%" height="100%" scrolling="no" frameborder="0"></iframe>
          </td>
        </tr>
      </table>
    </div>
    <!-- 前置机任务虚拟下发使用的iframe -->
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
    <iframe name="preMachTask" src="" scrolling="no" frameborder="0" style="display:none;"></iframe>
  </div>
  </body>
</html>
