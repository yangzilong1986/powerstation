<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>总表数据查询</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/effects.js"></script>
</head>
<body>
<div class="electric_lcon" id="electric_Con">
  <ul class="default" id="electric_Con_0">
    <form:form name="/query/sumMeterDataQuery" modelAttribute="smdata">
      <div id="inquiry" style="margin-top: 5px;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="100" align="right" class="green" height="30">单　位：</td>
            <td width="120" align="left">
              <select id="orgId" name="orgId" style="width: 140px; height: 18px;">
                <option value="4">乾龙托管</option>
              </select>
            </td>
            <td width="100" align="right" class="green">台　区：</td>
            <td width="120" align="left">
              <select id="tgId" name="tgId" style="width: 140px; height: 18px;">
                <option value="3">乾龙1#</option>
              </select>
            </td>
            <td width="100" align="center"><img src="<pss:path type="bgcolor"/>/img/inquiry.gif" width="62" height="21" style="cursor: pointer;" /></td>
            <td>&nbsp;</td>
          </tr>
        </table>
      </div>
      <div id="bg" style="height: 30px; text-align: center;">
        <ul id=”datamenu_Option“ class="cb font1">
          <li class="curr" id=datamenu_Option_0 style="cursor: pointer;" onmouseover="SwitchTab('datamenu_',0,4)">表码数据</li>
          <li id=datamenu_Option_1 style="cursor: pointer;" onmouseover="SwitchTab('datamenu_',1,4)">功率数据</li>
          <li id=datamenu_Option_2 style="cursor: pointer;" onmouseover="SwitchTab('datamenu_',2,4)">电压数据</li>
          <li id=datamenu_Option_3 style="cursor: pointer;" onmouseover="SwitchTab('datamenu_',3,4)">电流数据</li>
        </ul>
      </div>
      <div class="datamenu_lcon" id="datamenu_Con">
        <ul class="default" id="datamenu_Con_0">
          <div class="content">
            <div id="cont_1">
              <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-75));">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <thead>
                    <tr>
                      <th width="7%" class="bg01" height="30">序号</th>
                      <th width="8%" class="bg01">参数类型</th>
                      <th width="30%" class="bg01">参数名称</th>
                      <th width="18%" class="bg01">待设值</th>
                      <th width="18%" class="bg01">上次设置值</th>
                      <th width="9%" class="bg01">单位</th>
                      <th width="10%" class="bg01">操作结果</th>
                    </tr>
                  </thead>
                  <tbody class="tblcontent2">
                    <tr class="cicontent">
                      <td height="20">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </ul>
        <ul class="disNone" id="datamenu_Con_1">
          <div class="content">
            <div id="cont_2">
              <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-75));">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <thead>
                    <tr>
                      <th width="7%" class="bg01" height="30">序号</th>
                      <th width="8%" class="bg01">参数类型</th>
                      <th width="30%" class="bg01">参数名称</th>
                      <th width="18%" class="bg01">待设值</th>
                      <th width="18%" class="bg01">上次设置值</th>
                      <th width="9%" class="bg01">单位</th>
                      <th width="10%" class="bg01">操作结果</th>
                    </tr>
                  </thead>
                  <tbody class="tblcontent2">
                    <tr class="cicontent">
                      <td height="20">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </ul>
        <ul class="disNone" id="datamenu_Con_2">
          <div class="content">
            <div id="cont_3">
              <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-75));">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <thead>
                    <tr>
                      <th width="7%" class="bg01" height="30">序号</th>
                      <th width="8%" class="bg01">参数类型</th>
                      <th width="30%" class="bg01">参数名称</th>
                      <th width="18%" class="bg01">待设值</th>
                      <th width="18%" class="bg01">上次设置值</th>
                      <th width="9%" class="bg01">单位</th>
                      <th width="10%" class="bg01">操作结果</th>
                    </tr>
                  </thead>
                  <tbody class="tblcontent2">
                    <tr class="cicontent">
                      <td height="20">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </ul>
        <ul class="disNone" id="datamenu_Con_3">
          <div class="content">
            <div id="cont_4">
              <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-75));">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <thead>
                    <tr>
                      <th width="7%" class="bg01" height="30">序号</th>
                      <th width="8%" class="bg01">参数类型</th>
                      <th width="30%" class="bg01">参数名称</th>
                      <th width="18%" class="bg01">待设值</th>
                      <th width="18%" class="bg01">上次设置值</th>
                      <th width="9%" class="bg01">单位</th>
                      <th width="10%" class="bg01">操作结果</th>
                    </tr>
                  </thead>
                  <tbody class="tblcontent2">
                    <tr class="cicontent">
                      <td height="20">&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </ul>
      </div>
    </form:form>
  </ul>
</div>
</body>
</html>