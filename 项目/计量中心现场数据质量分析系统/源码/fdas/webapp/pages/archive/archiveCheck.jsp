<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>档案审核</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
$(document).ready( function() {
  dateInit();
});

//查询
function queryData(){
  archiveCheckForm.submit();
}
//显示详细信息
function showDetail(checkStatus,objectType){
  var START_TIME = $("#START_TIME").val();
  var END_TIME = $("#END_TIME").val();
  var CHECK_STATUS = "";
  if(checkStatus == "1"){ //未审核

    CHECK_STATUS = "10";
  }else if(checkStatus == "2"){ //审核通过
    CHECK_STATUS = "20";
  }else if(checkStatus == "3"){ //审核未通过
    CHECK_STATUS = "30";
  }else if(checkStatus == "4"){ //已审核

    CHECK_STATUS = "40";
  }
  var params={
        "START_TIME":START_TIME,
        "END_TIME":END_TIME,
        "CHECK_STATUS":CHECK_STATUS,
        "WANTEDPERPAGE":"20"
  }
  var url = "";
  var title="";
  if(objectType == "1"){ //供电所
     url = contextPath+"/114002.dow?" + jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
     title = "供电所详细信息";
  }else if(objectType == "2"){ //线路
     url = contextPath+"/114004.dow?" + jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
     title = "线路详细信息";
  }else if(objectType == "3"){ //客户
     url = contextPath+"/114005.dow?" + jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);
     title = "客户详细信息";
  }
  top.showDialogBox(title,url, 575, 980);
}
//日期初始化

function dateInit(){
   var startTime = $("#START_TIME").val();
   var endTime = $("#END_TIME").val();
   if(startTime == ''){
      $("#START_TIME").val(addData(0));
   }
   if(endTime == ''){
      $("#END_TIME").val(addData(1));
   }
}
//日期计算
function addData(n)  
{  
	var uom = new Date();  
	uom.setDate(uom.getDate()+n);  
	uom = uom.getFullYear() + "-" +   (uom.getMonth()+1) + "-" + uom.getDate();  
	return uom;  
}
</script>
</head>
<body>
<form name="archiveCheckForm" action="114001.dow" method="post" id="mainform"> 
<div id="body">
    <jsp:include page="archiveTabs.jsp" flush="true">
      <jsp:param value="10" name="pageType"/>
    </jsp:include>
  <div id="main">
    <div id="tool">
      <div class="opbutton1">
        <input type="button" name="query" onclick="queryData()" value="查 询" class="input1" />
      </div>
      <table border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td class="label" id="objectNameLable">开始时间：</td>
          <td class="dom_date"> 
            <input type="text" id="START_TIME" name="START_TIME" value="${ETF.START_TIME}" onfocus="peisDatePicker()" readonly="readonly"/>
          </td>
          <td class="label" id="objectNameLable">截止时间：</td>
          <td class="dom_date">
            <input type="text" id="END_TIME" name="END_TIME" value="${ETF.END_TIME}" onfocus="peisDatePicker()" readonly="readonly"/>
          </td>
        </tr>
      </table>
    </div>
    <div class="content">
      <div id="cont_1">
        <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 150));">
          <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <thead id="DATA_THEAD">
             <tr>
              <th>&nbsp;&nbsp;</th>
              <th>总数</th>
              <th>已审核</th>
              <th>审核通过</th>
              <th>审核未通过</th>
              <th>未审核</th>
             </tr>
            </thead>
            <tbody id="DATA_TBODY">
            	  <c:forEach items="${ETF.REC}" var="OBJECT" varStatus="status">
	                <tr onClick="selectSingleRow(this);" style="cursor:pointer;" align="left">
	                 <td align="center">${OBJECT.NAME}</td>
	                 <td align="center">${OBJECT.TOTAL}</td> 
	                 <td align="center"><a href="javascript:showDetail('4','${OBJECT.OBJECT_TYPE}');">${OBJECT.CHECKED}</a></td> 
	                 <td align="center"><a href="javascript:showDetail('2','${OBJECT.OBJECT_TYPE}');">${OBJECT.CHECKED_PASS}</a></td> 
	                 <td align="center"><a href="javascript:showDetail('3','${OBJECT.OBJECT_TYPE}');">${OBJECT.CHECKED_NOPASS}</a></td> 
	                 <td align="center"><a href="javascript:showDetail('1','${OBJECT.OBJECT_TYPE}');">${OBJECT.NOCHECKED}</a></td> 
	                </tr> 
	              </c:forEach>
            </tbody>
          </table>
        </div>
        <div class="pageContainer">
	    </div>
      </div>
    </div>
  </div>
</div>
</form>
</body>
</html>