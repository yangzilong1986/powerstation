<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/plugin/treeTable/jquery.treeTable.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript" src="<pss:path type="bgcolor"/>/plugin/treeTable/jquery.treeTable.js"></script>
</head>
<body>
<div class="electric_lcon" id="electric_Con">
  <ul class=default id=electric_Con_1>
    <div id="inquiry">
      <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="100" height="30" align="right" class="green">终端逻辑地址：</td>
          <td width="120"><input id="logicalAddr" name="logicalAddr" class="input2" value="" style="width: 140px; height: 18px;"/></td>
          <td width="100" align="right">
            <img src="<pss:path type="bgcolor"/>/img/inquiry.gif" align="middle" width="62" height="21" />
          </td>
        </tr>
      </table>
    </div>
    <div id="bg">
      <ul id=datamenu_Option class="cb font1">
        <li class="curr" id=datamenu_Option_0 onmouseover="SwitchTab('datamenu_',0,4)">one</li>
        <li id=datamenu_Option_1 onmouseover="SwitchTab('datamenu_',1,4)">tow</li>
        <li id=datamenu_Option_2 onmouseover="SwitchTab('datamenu_',2,4)">three</li>
        <li id=datamenu_Option_3 onmouseover="SwitchTab('datamenu_',3,4)">four</li>
      </ul>
    </div>
  </ul>
</div>
</body>
</html>