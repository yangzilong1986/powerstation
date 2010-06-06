<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
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
//自动补全方法
autoCompleter("custNo","1",154);
inpuTextStyle(); 
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
   var custId=$(".selected").attr("id");  //取到选中户号
   if(custId==null){
     alert("请选中一个用户！");
   }else{
     window.location.href=contextPath+"/archive/addBigCustAction.do?action=forwardToEditBigCustDeviceInfo&custId="+custId+"&r=" + parseInt(Math.random() * 1000);
   }
}
//删除用户
function deleteCust(custId){
    var url = contextPath + "/archive/addBigCustAction.do?action=deleteBigCustByCustId&custId=" + custId;
    if(confirm("确定要删除该用户?")){
        $.ajax({
            url: url,
            dataType:'json',
            cache: false,
            success: function(json){
              var msg=json['msg'];
               if(msg=="1"){
                alert("删除成功");
                addCustQueryForm.submit();
                }else if(msg == "3"){
                alert("该用户下存在终端，变压器，开关，或者电表，不允许删除！");
                }
                else{
                   alert("删除失败");
                }
            }
    });
    }
}
//输入框文字效果
function inpuTextStyle(){
  $('#custNo').attr({style: "color:#aaaaaa;"});
  if($('#custNo').val()==''){
    $('#custNo').val("请输入户号或户名");
  }
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

//查询
function queryData(){
  $("input[name='sqlCode']").val("AL_ARCHIVE_0041");
  addCustQueryForm.submit();
}

function comfirmAfterQuery(data){
  //alert(data.OBJECT_ID+"__" + data.TERM_ID);
  var objectId=data.OBJECT_ID;
  $("input[name='objectId']").val(objectId);
  //window.location.href=contextPath+"/archive/bigCustListQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0055&pageRows=20&objectId="+objectId;
  $("input[name='sqlCode']").val("AL_ARCHIVE_0055");
  addCustQueryForm.submit();
}
//导出excel
function exprotexcel(){
    var url = contextPath+"/archive/bigCustListQuery.do?action=exportExcel&" 
        + $("form input[name!=action]").serialize() + '&' 
        + $("form select").serialize()+ "&xmlId=bigCustQuery" 
        + "&r=" + parseInt(Math.random() * 1000);
    window.location.href = encodeURI(url,"utf-8");
}
</script>
</head>
<body>
<html:form action="/archive/bigCustListQuery" method="post">
<html:hidden property="action" value="normalMode"/>
<html:hidden property="pageType" value="page"/>
<html:hidden property="objectId"/>
<html:hidden property="sqlCode"/>
<html:hidden property="showType"/>
<div id="body">
   <jsp:include page="archiveTabs.jsp" flush="true">
    <jsp:param value="1" name="pageType"/>
   </jsp:include>
   <div id="main">
    <div id="tool">
     <div class="opbutton1">
       <input type="button" name="query" id="query" value="查 询" onclick="queryData()" class="input1" />
       <input type="button" class="input2" onclick="qry_adv_click({OBJECT_TYPE : '1',ARCHIVE_TYPE : '0',QUERY_TYPE : '0'})"  value="高级查询" />
     </div>
     <table border="0" cellpadding="0" cellspacing="0">
      <tr>
       <td width="70" class="label">用　户：</td>
       <td width="120">
         <html:text property="custNo" styleId="custNo"></html:text>
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
           <th>序号</th>
           <th>户名</th>
           <th>户号</th>
           <th>供电单位</th>
           <th>行业</th>
           <th>操作</th>
         </tr></thead>
         <tbody>
           <logic:present name="PG_QUERY_RESULT">
             <logic:iterate id="dataInfo" name="PG_QUERY_RESULT">
               <tr onclick="selectSingleRow(this)" style="cursor:pointer;" id="<bean:write name="dataInfo" property="col5"/>">
                 <td><bean:write name="dataInfo" property="rowNo" /></td>
                 <td><bean:write name="dataInfo" property="col1"/></td>
                 <td><bean:write name="dataInfo" property="col2"/></td>
                 <td><bean:write name="dataInfo" property="col3"/></td>
                 <td><bean:write name="dataInfo" property="col4"/></td>
                 <td>
                   <a href="javascript:deleteCust('<bean:write name="dataInfo" property="col5"/>')"><bean:message  key="global.delete"/></a>
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
  </div>
</html:form>
</body>
</html>