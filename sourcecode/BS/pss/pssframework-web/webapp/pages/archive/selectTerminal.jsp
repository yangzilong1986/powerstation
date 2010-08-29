<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>专变用户档案编辑第二步</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/jquery.autocomplete.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
<script type="text/javascript">
var contextPath = "<peis:contextPath/>";
//初始化加载
$(function(){
  autoCompleter("logicalAddr","50",154);
  showViaKeypress();
})
//上一步
function lastStep(){
  //window.location.href=contextPath+"/jsp/archive/archiveTypeUpdate.jsp";
  var url=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=archiveTypeUpdate&selectType=0";
  window.location.href=url;
}
//下一步
function nextStep(){
   var termId=$(".selected").attr("id");
   if(termId==null){
     alert("请选中一个终端！");
   }else{
      window.location.href=contextPath+"/archive/terminalAction2.do?action=forwardToEditBigCustTermInfo&termId="+termId+"&custFlag=1";
   }
}
//删除终端
function deleteTerm(termId){
   var url=contextPath+"/archive/terminalAction2.do?action=deleteTerminalInBigCust&termId="+termId;
     if(confirm("确定要删除该终端?")){
          $.ajax({
            url: url,
            dataType:'json',
            cache: false,
            success: function(json){
              var msg=json['msg'];
               if(msg=="1"){
                alert("删除成功");
                addCustQueryForm.submit();
                }else if(msg=="3"){
                   alert("该终端下存在电表不允许删除");
                }
                else{
                   alert("删除失败");
                }
            }
          });
     }
}
//查询
function queryData(){
  $("input[name='sqlCode']").val("AL_ARCHIVE_0043");
  addCustQueryForm.submit();
}
//高级查询通用JS
function comfirmAfterQuery(data){
  var objectId=data.TERM_ID;
  $("input[name='objectId']").val(objectId);
  $("input[name='sqlCode']").val("AL_ARCHIVE_0056");
  addCustQueryForm.submit();
}
//导出excel
function exprotexcel(){
    var url = contextPath+"/archive/bigCustTermListQuery.do?action=exportExcel&" 
        + $("form input[name!=action]").serialize() + '&' 
        + $("form select").serialize()+ "&xmlId=bigCustTermQuery" 
        + "&r=" + parseInt(Math.random() * 1000);
    window.location.href = encodeURI(url,"utf-8");
}
</script>
</head>
<body>
<html:form action="/archive/bigCustTermListQuery" method="post">
<html:hidden property="action" value="normalMode"/>
<html:hidden property="objectId"/>
<html:hidden property="pageType" value="page"/>
<html:hidden property="sqlCode"/>
<div id="body">
   <jsp:include page="archiveTabs.jsp" flush="true">
    <jsp:param value="1" name="pageType"/>
   </jsp:include>
   <div id="main">
    <div id="tool">
     <div class="opbutton1">
       <input type="button" name="query" id="query" value="查 询" onclick="queryData()" class="input1" />
       <input type="button"  value="高级查询" onclick="qry_adv_click({OBJECT_TYPE : '1',ARCHIVE_TYPE : '0',QUERY_TYPE : '0'})" class="input2" />
     </div>
     <table border="0" cellpadding="0" cellspacing="0">
      <tr>
       <td width="100" class="label">终端逻辑地址：</td>
       <td width="120">
         <html:text property="logicalAddr" styleId="logicalAddr"></html:text>
       </td>
       <td colspan="4"></td>
      </tr>
     </table>
    </div>
    <div class="content">
      <div id="tableContainer" class="tableContainer"
       style="height: expression((( document.documentElement.clientHeight || document.body.clientHeight) - 160 ) );">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
         <thead>
         <tr>
           <th>序号</th>
           <th>资产号</th>
           <th>逻辑地址</th>
           <th>终端类型</th>
           <th>相线</th>
           <th>设备厂家</th>
           <th>运行状态</th>
           <th>操作</th>
         </tr></thead>
         <tbody>
           <logic:present name="PG_QUERY_RESULT">
             <logic:iterate id="dataInfo" name="PG_QUERY_RESULT">
               <tr onclick="selectSingleRow(this)" style="cursor:pointer;" id="<bean:write name="dataInfo" property="col7"/>">
                 <td><bean:write name="dataInfo" property="rowNo" /></td>
                 <td><bean:write name="dataInfo" property="col1"/></td>
                 <td><bean:write name="dataInfo" property="col2"/></td>
                 <td><bean:write name="dataInfo" property="col3"/></td>
                 <td><bean:write name="dataInfo" property="col4"/></td>
                 <td><bean:write name="dataInfo" property="col5"/></td>
                 <td><bean:write name="dataInfo" property="col6"/></td>
                 <td>
                   <a href="javascript:deleteTerm('<bean:write name="dataInfo" property="col7"/>')"><bean:message  key="global.delete"/></a>
                 </td>
               </tr>
             </logic:iterate>
           </logic:present>
         </tbody>
       </table>
      </div>
      <div class="pageContainer">
        <!-- 导出excel -->
        <a href="#" onclick="javascript:exprotexcel();"><img src="<peis:contextPath/>/img/bt_excel.gif" width="16" height="16" title="导出Excel" /></a>
        <!-- 分页 -->
        <peis:paging actionForm="addCustQueryForm" rowsChange="true" />
      </div>
     </div>
     <div class="guidePanel">
      <input type="button" id="last" value="上一步" class="input1" onclick="lastStep();" />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="next" value="下一步" class="input1" onclick="nextStep();" />
      &nbsp;&nbsp;&nbsp;&nbsp;
    </div>
    </div>
   </div>
</html:form>
</body>
</html>
