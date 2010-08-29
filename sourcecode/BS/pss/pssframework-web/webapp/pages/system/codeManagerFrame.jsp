<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/taglibs.jsp"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title><bean:message bundle="system" key="code.manage.title"/></title>
	<link href="<peis:contextPath/>/css/main.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
	<script type="text/javascript">
	var contextPath = "<peis:contextPath/>";	
	function fresh() {
		var sSelectedCodeCate = codeList.sSelectedCodeCate;
		var codeType = codeList.codeForm.codeType.value;
		codeManager.location.href = contextPath + '/system/codeAction.do?action=detail&codeCate=' + sSelectedCodeCate + '&codeType=' + codeType + '&random=' + Math.random();
	}
  </script>
  </head>  
  <body>	
    <div id="body">
      <div class="tab">
        <ul>
          <li class="tab_on"><bean:message bundle="system" key="code.manage.title"/></li>
          <li class="clear"></li>
        </ul>
      </div>
      <div id="main" style="height: expression(((document.documentElement.clientHeight||document.body.clientHeight) - 44));"> 
        <table border="0" cellpadding="0" cellspacing="0" style="width:100%;height:100%;">
          <tr>
            <td width="400" style="display: block; border-right: 1px solid #085952;" align="center">
              <iframe width="98%" height="100%" frameborder="0" name="codeList" id="codeList" src="<peis:contextPath/>/system/codeAction.do?action=getQuery&codeType=1" ></iframe>
            </td>
            <td height="100%" align="center">
              <iframe width="98%" height="100%" frameborder="0" name="codeManager" id="codeManager"></iframe>
            </td>
          </tr>
        </table>
        <iframe id="hideframe" name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"></iframe>
      </div>
    </div>
  </body>
</html>
