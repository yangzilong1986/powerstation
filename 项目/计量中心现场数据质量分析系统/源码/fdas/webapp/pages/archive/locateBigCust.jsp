<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户定位</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript">
//初始化加载
$(function(){
inpuTextStyle();
showViaKeypress();
})
function finish() {
    var custId=$(".selected").attr("id");
    var custName=$(".selected td:eq(1)").html();
    if(custId==null){
       alert("请选择用户");
    }else{
        //top.getMainFrameObj().custId.value=custId;
        //top.getMainFrameObj().custName.value=custName;
        //$('#custId',top.getMainFrameObj()).append("<option value='"+custId+"'>"+custName+"</option>");
        //alert(top.getMainFrameObj().custId.value);
        top.getMainFrameObj().useSelectInit(custId,custName);
        top.getMainFrameObj().loadMeterAndSwitch(custId); //加载父页面电表开关
        top.getMainFrameObj().loadTran(custId); //加载父页面变压器
	    parent.GB_hide();
    }
}
//输入框文字效果
function inpuTextStyle(){
  $('#custNo').attr({style: "color:#aaaaaa;"});
  $('#custNo').blur(function(){
    if(this.value==''){
      this.value="请输入户号或户名";
      $('#custNo').attr({style: "color:#aaaaaa;"});
    }
  });
  $('#custNo').focus(function(){
    if(this.value=="请输入户号或户名"){
       this.value='';
       $('#custNo').attr({style: "color:black;"});
     }
  });
}
</script>
</head>
<body style="overflow: hidden;">
<div id="main">
 <html:form action="/archive/localBigCustListQuery" method="post">
 <html:hidden property="action" value="normalMode"/>
 <html:hidden property="pageType" value="page"/>
 <html:hidden property="sqlCode" value="AL_ARCHIVE_0041"/>
 <html:hidden property="showType"/>
  <div id="tool">
    <div class="opbutton1">
      <input type="submit" id="query" value="查 询" class="input1"/>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="save" value="选定用户" class="input2" onclick="finish();" />
    </div>
    <table border="0" cellpadding="0" cellspacing="0">
      <tr>
       <td width="70" class="label">用　户：</td>
       <td width="120">
         <input type="text" name="custNo" id="custNo" value="请输入户号或户名"/>
       </td>
       <td colspan="4"></td>
      </tr>
    </table>
  </div>
  <div class="content">
    <div id="cont_1">
      <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 85));">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
          <thead>
            <tr>
             <th>序号</th>
             <th>户名</th>
             <th>户号</th>
             <th>供电单位</th>
             <th>行业</th>
            </tr>
          </thead>
          <tbody>
            <logic:present name="PG_QUERY_RESULT">
             <logic:iterate id="dataInfo" name="PG_QUERY_RESULT">
               <tr onclick="selectSingleRow(this)" style="cursor:pointer;" id="<bean:write name="dataInfo" property="col5"/>">
                 <td><bean:write name="dataInfo" property="rowNo" /></td>
                 <td><bean:write name="dataInfo" property="col1"/></td>
                 <td><bean:write name="dataInfo" property="col2"/></td>
                 <td><bean:write name="dataInfo" property="col3"/></td>
                 <td><bean:write name="dataInfo" property="col4"/></td>
               </tr>
             </logic:iterate>
           </logic:present>
          </tbody>
        </table>
      </div>
      <div class="pageContainer">
        <peis:paging actionForm="addCustQueryForm" rowsChange="true" />
      </div>
    </div>
  </div>
  </html:form>
</div>
</body>
</html>