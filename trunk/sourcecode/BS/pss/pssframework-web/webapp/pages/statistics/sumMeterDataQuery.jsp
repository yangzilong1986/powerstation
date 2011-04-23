<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@page import="org.pssframework.support.system.SystemConst"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<title>总表数据查询</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="${ctx}/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript">
var type = '{_type}';
$(function() {
    chgOrgId('init');
});

var contextPath = "${ctx}";

getFrom = function() {
    var data = $("form[0]").serialize();
    return data;
}

var lastQueryType = 0;
function changeType(obj) {
    if(obj == undefined) {
        obj = lastQueryType;
    }
    var lastTd = document.getElementById("datamenu_Option_" + lastQueryType);
    var nowTd = document.getElementById("datamenu_Option_" + obj);
    lastTd.className = 'none';
    nowTd.className = 'curr';
    lastQueryType = obj;
    var url = contextPath;
    if(lastQueryType == 0) {  // 表码数据
        url += "/statistics/eicurv?showMode=" + showMode + "&" + getFrom() + "&random=" + Math.random();
    }
    if(lastQueryType == 1) {  // 功率数据
        url += "/statistics/powercruv?showMode=" + showMode + "&" + getFrom() + "&random=" + Math.random();
    }
    if(lastQueryType == 2) {  // 功率因数
        url += "/statistics/pfcruv?showMode=" + showMode + "&" + getFrom() + "&random=" + Math.random();
    }
    if(lastQueryType == 3) {  // 电压数据
        url += "/statistics/eccurv/vt?showMode=" + showMode + "&" + getFrom() + "&random=" + Math.random();
    }
    if(lastQueryType == 4) {  // 电流数据
        url += "/statistics/eccurv/ec?showMode=" + showMode + "&" + getFrom() + "&random=" + Math.random();
    }
    if(lastQueryType == 5) {  // 电压统计数据
        url += "/statistics/voltStatisDay?showMode=" + showMode + "&" + getFrom() + "&random=" + Math.random();
    }
    if(lastQueryType == 6) {  // 不平衡数据
        url += "/statistics/imbStatisDay?showMode=" + showMode + "&" + getFrom() + "&random=" + Math.random();
    }
    if(lastQueryType == 7) {  // 电流越限数据
        url += "/statistics/ecurStatisDay?showMode=" + showMode + "&" + getFrom() + "&random=" + Math.random();
    }
    
    document.getElementById("data").src = url;
}

function chgOrgId(type) {
    var url = '<pss:path type="webapp"/>/linkage/cblinkage/tgLinkedByOrg';
    
    var selectedTg = '';
    if(type == 'init') {
        if('${pageRequest.tgId}' == '' || '${pageRequest.tgId}' == null || '${pageRequest.tgId}' == 'null') {
            selectedTg = '-1';
        }
        else {
            selectedTg = '${pageRequest.tgId}';
        }
    }
    else {
        selectedTg = '-1';
    }
    
    var params = {
            defType: 1,
            formId: 'tgId',
            formName: 'tgId',
            actionChange: 'changeTg()',
            orgId: $("#orgId").val(), 
            tgId: selectedTg
    };
    $("#tdLinkedByOrg").load(url, params, function(){
        //alert($("#tdLinkedByOrg").html());
        //alert($("#tgId").val());
        if(type == 'init') {
            changeType(0);
        }
    });
}

function changeTg() {
    changeType();
}

var showMode = 'grids';  // grids / chart
function showGridMode() {
    showMode = 'grids';
    changeType(lastQueryType);
}

function showPictMode() {
    showMode = 'chart';
    if($("#tgId").val() == '-1') {
        showMode = 'grids';
        alert('请选择单个台区进行查看');
        return;
    }
    changeType(lastQueryType);
}
</script>
</head>
<body>
<div class="electric_lcon" id="electric_Con">
<ul class="default" id="electric_Con_0">
  <form:form name="/query/sumMeterDataQuery" modelAttribute="smdata">
    <div id="inquiry" style="margin-top: 5px;">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="100" align="right" class="green" height="30">单 位：</td>
        <td width="120" align="left"><select id="orgId" name="orgId" style="width: 140px;" onchange="chgOrgId('chg')">
          <c:forEach var="item" items="${orglist}">
            <option <c:if test="${item.orgId eq pageRequest.orgId}">selected</c:if> value="<c:out value="${item.orgId}"/>"><c:out value="${item.orgName}" /></option>
          </c:forEach>
        </select></td>
        <td width="100" align="right" class="green">台 区：</td>
        <td width="120" align="left" id="tdLinkedByOrg"></td>
        <td width="100" align="right" class="green">时间：</td>
        <td width="120"><input type="text" class="input_time" id="ddate" name="ddate" value="${qdate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" readonly="readonly" style="cursor: pointer; height: 23px; width: 152px;" /></td>
        <td width="100" align="center">
          <img src="<pss:path type="bgcolor"/>/img/inquiry.gif" width="62" height="21" style="cursor: pointer;" onclick="changeType()" />
        </td>
        <td>&nbsp;</td>
      </tr>
    </table>
    </div>
    <div id="bg" style="height: 30px; text-align: center; white-space:nowrap;">
      <ul id="datamenu_Option" class="cb font1">
        <li id=datamenu_Option_0 style="cursor: pointer;" onclick="changeType(0)">表码数据</li>
        <li id=datamenu_Option_1 style="cursor: pointer;" onclick="changeType(1)">功率数据</li>
        <li id=datamenu_Option_2 style="cursor: pointer;" onclick="changeType(2)">功率因数</li>
        <li id=datamenu_Option_3 style="cursor: pointer;" onclick="changeType(3)">电压数据</li>
        <li id=datamenu_Option_4 style="cursor: pointer;" onclick="changeType(4)">电流数据</li>
        <li id=datamenu_Option_5 style="cursor: pointer;" onclick="changeType(5)">电压统计数据</li>
        <li id=datamenu_Option_6 style="cursor: pointer;" onclick="changeType(6)">不平衡数据</li>
        <li id=datamenu_Option_7 style="cursor: pointer;" onclick="changeType(7)">电流越限数据</li>
      </ul>
    </div>
    <div id="bngMonitor" align="right" style="width: 100%; position: absolute; text-align: right; margin-top: -26px;">
      <img src="<pss:path type="bgcolor"/>/img/grids.png" onclick="showGridMode()" width="16" height="16" alt="表格" style="cursor: pointer; border: 1px #DBDBDB solid;" />
      &nbsp;&nbsp;&nbsp; 
      <img src="<pss:path type="bgcolor"/>/img/chart.png" onclick="showPictMode()" width="16" height="16" alt="图形" style="cursor: pointer; border: 0px #DBDBDB solid;" />
      &nbsp;&nbsp;&nbsp; 
    </div>
    <div align="center" style="height: expression(((document.documentElement.clientHeight || document.body.clientHeight)-65));">
      <iframe id="data" scrolling="auto" frameborder="0" style="display: block; overflow-y: hidden; overflow-x: hidden; width: 100%; height: 100%;"></iframe>
    </div>
  </form:form>
</ul>
</div>
</body>
</html>