<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<html>
<head>
<title>对象选择</title>
<link href="<peis:contextPath/>/css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/common-ajax.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';
</script>

</head>
<body>
<div id="body">
  <div id="tab" class="tab">
    <ul>
      <li id="tab_1" class="tab_off"><a href="volPassRatPoint.html" onfocus="blur()">对象选择</a></li>     
    </ul>
    <h1><a href="#"><img src="/peis/img/bt_help.gif" width="14" height="15" /></a></h1>
  </div>
  <div id="main">
    <div id="tool">
     <div class="opbutton2"><input type="submit" name="query" id="query" value="查 询" class="input1" /></div>
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <tr>
            <td colspan="4">提示：新增A类监测点(各变电站的6~10KV母线电压计量点)</td>
          </tr>
          <tr>
            <td width="70" class="label">变电站：</td>
            <td width="120" class="dom" id="subsNo"> </td>
            <td width="70" class="label">装表位置：</td>
            <td width="120" class="dom">
              <select name="zbwz" id="zbwz">
                <option value="1">主变高压侧</option>
                <option value="2">主变中压侧</option>
                <option value="3">主变低压侧</option>
                <option value="4">母线</option>
                <option value="5">线路</option>
              </select>
            </td>
          </tr>
        </table>
     </div>
    <div id="tableContainer" class="tableContainer" style="height: expression((( document . documentElement . clientHeight || document . body . clientHeight) -   142 ) );">
      <table border="0" cellpadding="0" cellspacing="0" width="100%" id="zbdata">
        <thead><tr>
           <th width="2%"><input type="checkbox" id="selectall_1" name="selectall_1" checked="checked" /></th>
          <th width="2%">序号</th>
          <th width="4%">变电站名称</th>    
          <th width="5%">变电站号</th>
          <th width="5%">电压等级</th>
          <th width="4%">母线名称</th>
          <th width="4%">计量点名称</th>
          <th width="4%">表号</th>
        </tr></thead>
        <tbody id="databody">                       
        </tbody>
      </table>    
    </div>
    <div class="pageContainer">
      <input type="hidden" id="showType" name="showType" value="pag" />显示行数：<select id="pageRows" name="pageRows" size="1"><option value="10">10</option><option value="20" selected>20</option><option value="30">30</option><option value="40">40</option><option value="50">50</option><option value="100">100</option><option value="200">200</option><option value="500">500</option></select> 　第1页 / 共7页　转到：<input type="text" id="page" name="page" value="1" /><img align="middle" src="../../img/paging/btn_go.gif" width="20" height="20" border="0" style="cursor: pointer;" /> <font style="font-size: 14px; font-family: webdings">9</font> <font style="font-size: 14px; font-family: webdings">7</font> <a title="下一页" href='#'><font style="font-size: 14px; font-family: webdings">8</font></a> <a title="末页" href="#"><font style="font-size: 14px; font-family: webdings">:</font></a> 
   </div>
 </div>
</div>
</body>
</html>