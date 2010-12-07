<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>远程跳合闸</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript">

</script>
</head>
<body>
<div class="electric_lcon" id="electric_Con">
  <ul class=default id=electric_Con_1>
    <div id="inquiry" style="margin-top: 5px;">
      <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="100" height="30" align="right" class="green">台区编号：</td>
          <td width="120"><input id="tgNo" name="tgNo" class="input2" value="" style="width: 140px; height: 18px;"/></td>
          <td width="100" height="30" align="right" class="green">台区名称：</td>
          <td width="120"><input id="tgName" name="tgName" class="input2" value="" style="width: 140px; height: 18px;"/></td>
          <td width="100" align="right">
            <img src="<pss:path type="bgcolor"/>/img/inquiry.gif" align="middle" width="62" height="21" onclick="return false;" style="cursor: pointer;" />
          </td>
        </tr>
      </table>
    </div>
    <div id="bg" style="height: 30px; text-align: center;">
      <ul id=”datamenu_Option“ class="cb font1">
        <li class="curr" id=datamenu_Option_0 style="cursor: pointer;">远程跳合闸</li>
      </ul>
    </div>
    <div class="datamenu_lcon" id="datamenu_Con">
      <ul class=default id=datamenu_Con_0>
        <div class="content">
          <div id="cont_1">
            <div style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-75));">
              <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="180" height="100%">
                    <iframe id="psTreeFrame" name="psTreeFrame" src="<pss:path type="webapp"/>/pages/psmanage/psTree.jsp?type=rmttpsw" scrolling="auto" width="100%" height="100%" frameborder="0"></iframe>
                  </td>
                  <td width="3" height="100%" style="background-color: #bbdcd8;"></td>
                  <td>
                    <iframe id="psRemoteTpSwFrame" name="psRemoteTpSwFrame" src="<pss:path type="webapp"/>/pages/psmanage/psRemoteTpSw.jsp" scrolling="auto" width="100%" height="100%" frameborder="0"></iframe>
                  </td>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </ul>
    </div>
  </ul>
</div>
</body>
</html>