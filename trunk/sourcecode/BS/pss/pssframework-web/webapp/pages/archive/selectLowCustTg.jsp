<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>低压集抄档案编辑第二步</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/jquery.autocomplete.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';
//初始化加载
$(document).ready(function(){
    autoCompleter("tgNo","2",154);
    inpuTextStyle();
    showViaKeypress();
    var tgNo = $('#tgNo');
    if(tgNo != null && tgNo.val() == ''){
        tgNo.val('请输入台区号或台区名');
    }
});

//上一步
function lastStep(){
    //window.location.href=contextPath+"/jsp/archive/lowVoltCustTypeUpdate.jsp";
    var url=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=lowVoltCustTypeUpdate&selectType=0";
    window.location.href=url;
}
//下一步
function nextStep(){
    var tgId=$(".selected").attr("id");  //取到选中台区
    if(tgId==null){
        alert("请选中一个台区！");
    }
    else{
        window.location.href=contextPath+"/archive/updateLowCustInfoAction.do?action=showTgEdit&tgId="+tgId;
    }
}
//删除终端
function deleteTg(){
    if(confirm("是否删除该台区")==true){
        alert("删除成功！");
    }
}
//输入框文字效果
function inpuTextStyle(){
    $('#tgNo').attr({style: "color:#aaaaaa;"});
    $('#tgNo').blur(function(){
        if(this.value==''){
            this.value="请输入台区号或台区名";
            $('#tgNo').attr({style: "color:#aaaaaa;"});
        }
    });
    $('#tgNo').focus(function(){
        if(this.value=="请输入台区号或台区名"){
            this.value='';
            $('#tgNo').attr({style: "color:black;"});
        }
    });
}
//删除台区
function delTg(tgId){
    var url = contextPath + "/archive/updateLowCustInfoAction.do?action=deleteTg&tgId=" + tgId;
    if(confirm("确定要删除该台区?")){
        $.ajax({
            url: url,
            dataType:'json',
            cache: false,
            success: function(json){
                var msg = json['msg'];
	            if(msg == "1"){
	                alert("删除成功");
	                //top.getMainFrameObj().location.href = contextPath + "/archive/tgListQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0028";
	                //window.location.href = contextPath + "/archive/tgListQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0028";
	                tgListQueryForm.submit();
	            }
	            else if(msg == "3"){
	                alert("该台区下存在其它设备，不允许删除");
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
  $("input[name='sqlCode']").val("AL_ARCHIVE_0028");
  tgListQueryForm.submit();
}
//高级查询
function comfirmAfterQuery(data){
  var objectId=data.OBJECT_ID;
  $("input[name='objectId']").val(objectId);
  $("input[name='sqlCode']").val("AL_ARCHIVE_0057");
  tgListQueryForm.submit();
}
//导出excel
function exprotexcel(){
    var url = contextPath+"/archive/tgListQuery.do?action=exportExcel&" 
        + $("form input[name!=action]").serialize() + '&' 
        + $("form select").serialize()+ "&xmlId=tgQuery" 
        + "&r=" + parseInt(Math.random() * 1000);
    window.location.href = encodeURI(url,"utf-8");
}
</script>
</head>
<body>
<html:form action="/archive/tgListQuery" method="post">
<html:hidden property="action" value="normalMode"/>
<html:hidden property="objectId"/>
<html:hidden property="pageType" value="page"/>
<html:hidden property="sqlCode"/>
<input type="hidden" name="showType"/>
<div id="body">
  <jsp:include page="archiveTabs.jsp" flush="true">
      <jsp:param value="3" name="pageType"/>
   </jsp:include>
  <div id="main">
    <div id="tool">
      <div class="opbutton1">
        <input type="button" name="query1" id="query1" value="查 询" onclick="queryData()" class="input1" />
        <input type="button" name="query2" id="query2" onclick="qry_adv_click({OBJECT_TYPE : '2',ARCHIVE_TYPE : '0',QUERY_TYPE : '0'})" value="高级查询" class="input2" />
      </div>
      <table border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="70" class="label">配　变：</td>
          <td width="180">
            <html:text property="tgNo" styleId="tgNo"/>
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
                <th width="10%">序号</th>
                <th width="20%">台区名称</th>
                <th width="15%">台区编号</th>
                <th width="15%">容量(kVA)</th>
                <th width="10%">状态</th>
                <th width="20%">供电单位</th>
                <th width="10%">操作</th>
              </tr>
            </thead>
            <tbody align="center">
              <logic:present name="PG_QUERY_RESULT">
                <logic:iterate id="dataInfo" name="PG_QUERY_RESULT">
                  <tr onclick="selectSingleRow(this)" style="cursor:pointer;" id="<bean:write name="dataInfo" property="col6"/>">
                    <td><bean:write name="dataInfo" property="rowNo" /></td>
                    <td><bean:write name="dataInfo" property="col1"/></td>
                    <td><bean:write name="dataInfo" property="col2"/></td>
                    <td><bean:write name="dataInfo" property="col3"/></td>
                    <td><bean:write name="dataInfo" property="col4"/></td>
                    <td><bean:write name="dataInfo" property="col5"/></td>
                    <td><a href="javascript:delTg('<bean:write name="dataInfo" property="col6"/>')"><bean:message  key="global.delete"/></a></td>
                  </tr>
                </logic:iterate>
              </logic:present>
            </tbody>
          </table>
        </div>
        <div class="pageContainer">
          <!-- 导出excel -->
          <a href="#" onclick="javascript:exprotexcel();"><img src="<peis:contextPath/>/img/bt_excel.gif" width="16" height="16" title="导出Excel" /></a>
          <peis:paging actionForm="tgListQueryForm" rowsChange="true" />
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
