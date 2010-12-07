<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<html>
<head>
<title>数据分析查询</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<link type="text/css" rel="stylesheet" href="<pss:path type="webapp"/>/widgets/simpletable/simpletable.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/widgets/simpletable/simpletable.js"></script>
<script type="text/javascript">
getFrom = function(){
    var data = $("form[0]").serialize(); 
    return data;
}

$(document).ready(function() {
    $("#inquiryBtn").click( function() {
        queryData();
    });
    queryData();

    chgOrgId('init');
});

function queryData() {
    var url = '<pss:path type="webapp"/>' + '/fdas/dataAnalysisQuery/query?' + getFrom();
    //alert(url);
    document.getElementById("fdata").src = url;
}
</script>
</head>
<body>
<div class="electric_lcon" id="electric_Con">
  <ul class="default" id="electric_Con_0">
    <form:form name="dataAnalysisQueryForm" modelAttribute="dadata">
      <div id="inquiry" style="margin-top: 5px;">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="100" align="right" class="green" height="30">开始日期：</td>
          <td width="120" align="left">
            <input type="text" class="input_time" id="sdate" name="sdate" value="${sdate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" readonly="readonly" style="cursor: pointer; height: 22px; width: 152px;" />
          </td>
          <td width="100" align="right" class="green">结束日期：</td>
          <td width="120" align="left">
            <input type="text" class="input_time" id="edate" name="edate" value="${edate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" readonly="readonly" style="cursor: pointer; height: 22px; width: 152px;" />
          </td>
          <td width="100" align="center">
            <img id="inquiryBtn" src="<pss:path type="bgcolor"/>/img/inquiry.gif" width="62" height="21" style="cursor: pointer;" />
          </td>
          <td>&nbsp;</td>
        </tr>
      </table>
      </div>
      <div id="bg" style="height: 30px; text-align: center;">
        <ul id="datamenu_Option" class="cb font1">
          <li id=datamenu_Option_0 style="cursor: pointer;" onclick="changeType(0)">数据分析</li>
        </ul>
      </div>
      <div align="center" style="height: expression(((document.documentElement.clientHeight || document.body.clientHeight)-65));">
        <iframe id="fdata" scrolling="auto" frameborder="0" style="display: block; overflow-y: hidden; overflow-x: hidden; width: 100%; height: 100%"></iframe>
      </div>
    </form:form>
  </ul>
</div>
</body>
</html>