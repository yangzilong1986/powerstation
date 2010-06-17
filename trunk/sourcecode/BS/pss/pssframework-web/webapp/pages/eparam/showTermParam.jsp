<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>终端参数设置</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/plugin/easyui/easyui.css" />
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/plugin/easyui/icon.css" />
<script type="text/javascript" src="<pss:path type="bgcolor"/>/plugin/easyui/jquery.js"></script>
<script type="text/javascript" src="<pss:path type="bgcolor"/>/plugin/easyui/jquery.easyui.js"></script>
<script type="text/javascript">
$(function(){
    $('#test').datagrid({
        width: $(window).width(),
        height:$(window).height()-95,
        nowrap: false,
        striped: true,
        url: '',
        sortName: 'code',
        sortOrder: 'desc',
        idField: 'code',
        frozenColumns:[[
            {field:'ck',checkbox:true},
            {field:'type',title:'参数类型 ',width:100},
            {field:'code',title:'命令项标识',width:100}
        ]],
        columns:[[
            {field:'name',title:'命令项名称 / 数据项名称',width:200},
            {field:'value',title:'待设值',width:100},
            {field:'lstValue',title:'上次设置值',width:100},
            {field:'unit',title:'单位',width:100},
            {field:'result',title:'操作结果',width:200}
        ]],
        pagination:false,
        rownumbers:false,
        singleSelect:false
    });
});
</script>
</head>
<body>
<div class="electric_lcon" id="electric_Con">
  <ul class=default id=electric_Con_1>
    <div id="inquiry" style="margin-top: 5px;">
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
    <div id="bg" style="height: 30px; text-align: center;">
      <ul id=”datamenu_Option“ class="cb font1">
        <li class="curr" id=datamenu_Option_0 style="cursor: pointer;">终端参数</li>
        <li id=datamenu_Option_1 style="cursor: pointer;">测量点参数</li>
      </ul>
    </div>
    <div class="datamenu_lcon" id="datamenu_Con">
      <ul class=default id=datamenu_Con_0>
        <div id="bg2">
          <ul>
            <li>设　置</li>
            <li>读　取</li>
          </ul>
        </div>
        <div class="content">
          <div id="cont_1">
            <table id="test"></table>
          </div>
        </div>
      </ul>
    </div>
  </ul>
</div>
</body>
</html>