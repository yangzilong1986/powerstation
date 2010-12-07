<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>漏保监测</title>
<link type="text/css" rel="stylesheet" href="<pss:path type="bgcolor"/>/css/content.css" />
<script type="text/javascript" src="<pss:path type="webapp"/>/scripts/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    chgOrgId('init');

    $("#inquiryBtn").click( function() {
        if($("#objId").val() == '-1' || $("#objId").val() == '') {
            alert("请选择台区");
            return;
        }
        var urlInquiry = '<pss:path type="webapp"/>' + '/psmanage/psmon/pstree/' + $("#objId").val();
        $("#psTreeFrame").attr("src", urlInquiry);
    });
});

function chgOrgId(type) {
    var url = '<pss:path type="webapp"/>/linkage/cblinkage/tgLinkedByOrg';
    
    var selectedTg = '';
    if(type == 'init') {
        if('${pageRequest.objId}' == '' || '${pageRequest.objId}' == null || '${pageRequest.objId}' == 'null') {
            selectedTg = '-1';
        }
        else {
            selectedTg = '${pageRequest.objId}';
        }
    }
    else {
        selectedTg = '-1';
    }
    
    var params = {
            defType: 2,
            formId: 'objId',
            formName: 'objId',
            actionChange: 'changeTg()',
            orgId: $("#orgId").val(), 
            tgId: selectedTg
    };
    $("#tdLinkedByOrg").load(url, params, function(){
        //alert($("#tdLinkedByOrg").html());
        //alert($("#objId").val());
        if(type == 'init') {
            var urlTree = '<pss:path type="webapp"/>' + '/psmanage/psmon/pstree/' + $("#objId").val();
            $("#psTreeFrame").attr("src", urlTree);
        }
    });
}

function changeTg() {
    var urlInquiry = '<pss:path type="webapp"/>' + '/psmanage/psmon/pstree/' + $("#objId").val();
    $("#psTreeFrame").attr("src", urlInquiry);
}
</script>
</head>
<body>
<div class="electric_lcon" id="electric_Con">
  <ul class=default id=electric_Con_1>
    <div id="inquiry" style="margin-top: 5px;">
      <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="100" height="30" class="green" align="right">单 位：</td>
          <td width="120">
            <select id="orgId" name="orgId" style="width: 140px;" onchange="chgOrgId('chg')">
              <c:forEach var="item" items="${orglist}">
                <option <c:if test="${item.orgId eq pageRequest.orgId}">selected</c:if> value="<c:out value="${item.orgId}"/>"><c:out value="${item.orgName}" /></option>
              </c:forEach>
            </select>
          </td>
          <td width="100" class="green" align="right">台 区：</td>
          <td width="120" id="tdLinkedByOrg"></td>
          <td width="100" align="right">
            <img id="inquiryBtn" src="<pss:path type="bgcolor"/>/img/inquiry.gif" align="middle" width="62" height="21" onclick="return false;" style="cursor: pointer;" />
          </td>
        </tr>
      </table>
    </div>
    <div id="bg" style="height: 30px; text-align: center;">
      <ul id="datamenu_Option" class="cb font1">
        <li class="curr" id=datamenu_Option_0 style="cursor: pointer;">漏保监测</li>
      </ul>
    </div>
    <div class="datamenu_lcon" id="datamenu_Con">
      <ul class=default id=datamenu_Con_0>
        <div class="content">
          <div id="cont_1">
            <div style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight)-65));">
              <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="120" height="100%">
                    <iframe id="psTreeFrame" name="psTreeFrame" src="" scrolling="auto" width="100%" height="100%" frameborder="0"></iframe>
                  </td>
                  <td width="3" height="100%" style="background-color: #bbdcd8;"></td>
                  <td>
                    <iframe id="psMonitorFrame" name="psMonitorFrame" src="" scrolling="auto" width="100%" height="100%" frameborder="0"></iframe>
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