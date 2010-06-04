<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%> 
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>配变台区档案编辑第二步</title>
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
  autoCompleter("logicAddr","51",154);
  showViaKeypress();
})
//上一步
function lastStep(){
   //window.location.href = contextPath + "/archive/tgTermQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0049&pageRows=20";
   var url=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=tgTypeUpdate&selectType=0";
   window.location.href=url;
}

//下一步
function nextStep(){
   var termIdAndTgIdAndTermType=$(".selected").attr("id");
   var idArray=termIdAndTgIdAndTermType.split(",");
   var termId=idArray[0];
   var tgId=idArray[1];
   var termType=idArray[2];
   if(termIdAndTgIdAndTermType==null){
     alert("请选中一个终端！");
   }else{
      if(termType=="03" || termType=="04"){//跳转到配变终端编辑页面
         window.location.href=contextPath+"/archive/terminalAction3.do?action=forwardToEditTgTermInfo&termId="+termId+"&tgId="+tgId+"&flag=1&r=" + parseInt(Math.random() * 1000);
      }else{//跳转到集中器编辑页面
         window.location.href=contextPath+"/jsp/archive/updateLowCustTermFrameSecond.jsp?termId="+termId+"&tgId="+tgId+"&tgTermCollectFlag=1&r=" + parseInt(Math.random() * 1000);
      }
   }
}

//删除集中器
function deleteTerminal(termId){
    var url = contextPath + "/archive/terminalAction3.do?action=deleteTerminal&termId=" + termId;
    if(confirm("确定要删除该终端?")){
        $.ajax({
            url: url,
            dataType:'json',
            cache: false,
            success: function(json){
              var msg = json['msg'];
              if(msg == "1"){
                  alert("删除成功");
                  lowCustTermQueryForm.submit();
              }else if(msg=="3"){
                 alert("该终端下存在电表、采集器及低压用户，或者级联其他集中器，不允许删除");
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
   $("input[name='sqlCode']").val("AL_ARCHIVE_0049");
   lowCustTermQueryForm.submit();
}

function comfirmAfterQuery(data){
  var objectId=filterNoRepeatStr(""+data.TERM_ID);
  $("input[name='objectId']").val(objectId);
  $("input[name='sqlCode']").val("AL_ARCHIVE_0058");
  lowCustTermQueryForm.submit();
}
//导出excel
function exprotexcel(){
    var url = contextPath+"/archive/tgTermQuery.do?action=exportExcel&" 
        + $("form input[name!=action]").serialize() + '&' 
        + $("form select").serialize()+ "&xmlId=tgTermQuery" 
        + "&r=" + parseInt(Math.random() * 1000);
    window.location.href = encodeURI(url,"utf-8");
}
</script>
</head>
<body>
<html:form action="/archive/tgTermQuery" method="post">
<html:hidden property="action" value="normalMode"/>
<html:hidden property="objectId"/>
<html:hidden property="pageType" value="page"/>
<html:hidden property="sqlCode"/>
<div id="body">
  <jsp:include page="archiveTabs.jsp" flush="true">
    <jsp:param value="2" name="pageType"/>
  </jsp:include>
  <div id="main">
    <div id="tool">
     <div class="opbutton1">
       <input type="button" name="query1" id="query1" onclick="queryData();" value="查 询" class="input1" />
       <input type="button" name="query2" id="query2" onclick="qry_adv_click({OBJECT_TYPE : '2',ARCHIVE_TYPE : '0',QUERY_TYPE : '0'})" value="高级查询" class="input2" />
     </div>
     <table border="0" cellpadding="0" cellspacing="0">
       <tr>
         <td width="100" class="label">终端逻辑地址：</td>
         <td width="120">
           <html:text property="logicAddr" styleId="logicAddr"/>
         </td>
         <td colspan="4"></td>
      </tr>
     </table>
    </div>
    <div class="content">
      <div id="cont_1">
        <div id="tableContainer" class="tableContainer"
          style="height: expression((( document.documentElement.clientHeight || document.body.clientHeight) - 160 ) );">
          <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <thead>
              <tr>
                <th width="7%">序号</th>
                <th width="13%">资产号</th>
                <th width="12%">配变终端地址</th>
                <th width="10%">运行状态</th>
                <th width="20%">台区名称</th>
                <th width="15%">台区号</th>
                <th width="15%">供电单位</th>
                <th width="8%">操作</th>
              </tr>
            </thead>
            <tbody align="center">
              <logic:present name="PG_QUERY_RESULT">
                <logic:iterate id="dataInfo" name="PG_QUERY_RESULT">
                  <tr onclick="selectSingleRow(this)" style="cursor:pointer;" id="<bean:write name="dataInfo" property="col7"/>,<bean:write name="dataInfo" property="col8"/>,<bean:write name="dataInfo" property="col9"/>">
                    <td><bean:write name="dataInfo" property="rowNo" /></td>
                    <td><bean:write name="dataInfo" property="col1"/></td>
                    <td><bean:write name="dataInfo" property="col2"/></td>
                    <td><bean:write name="dataInfo" property="col3"/></td>
                    <td><bean:write name="dataInfo" property="col4"/></td>
                    <td><bean:write name="dataInfo" property="col5"/></td>
                    <td><bean:write name="dataInfo" property="col6"/></td>
                    <td><a href="javascript:deleteTerminal('<bean:write name="dataInfo" property="col7"/>')"><bean:message key="global.delete"/></a></td>
                  </tr>
                </logic:iterate>
              </logic:present>
            </tbody>
          </table>
        </div>
        <div class="pageContainer">
          <!-- 导出excel -->
          <a href="#" onclick="javascript:exprotexcel();"><img src="<peis:contextPath/>/img/bt_excel.gif" width="16" height="16" title="导出Excel" /></a>
          <peis:paging actionForm="lowCustTermQueryForm" rowsChange="true" />
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
</div>
</html:form>
</body>
</html>
