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
var contextPath = "<peis:contextPath/>";

//初始化加载
$(document).ready(function(){
    autoCompleter("custNo","3",154);
    autoCompleter("mpNo","-1",154);
    inpuTextStyle();
    showViaKeypress();
    var custNo = $('#custNo');
    if(custNo != null && custNo.val() == ''){
        custNo.val('请输入户号或户名');
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
    var custIdAndtgId = $(".selected").attr("id");  //取到选中户号
    var idArray=custIdAndtgId.split(",");
    var custId=idArray[0];
    var tgId=idArray[1];
    if(custIdAndtgId==null){
        alert("请选中一个用户！");
    }
    else{
        window.location.href=contextPath+"/archive/updateLowCustInfoAction.do?action=updateLowCust&custId="+custId+"&tgId="+tgId+"&r=" + parseInt(Math.random() * 1000);
    }
}

//删除用户
function deleteCust(custId){
    var url = contextPath + "/archive/addLowCustAction.do?action=deleteCust&custId=" + custId;
    if(confirm("确定要删除该用户?")){
         $.ajax({
            url: url,
            dataType:'json',
            cache: false,
            success: function(json){
                var msg = json['msg'];
	            if(msg == "1"){
	                alert("删除成功");
	                //window.location.href = contextPath + "/archive/lowCustQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0101";
	                lowCustQueryForm.submit();
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
  $("input[name='sqlCode']").val("AL_ARCHIVE_0101");
  lowCustQueryForm.submit();
}
//高级查询
function comfirmAfterQuery(data){
  var objectId=filterNoRepeatStr(""+data.OBJECT_ID);
  $("input[name='objectId']").val(objectId);
  $("input[name='sqlCode']").val("AL_ARCHIVE_0060");
  lowCustQueryForm.submit();
}
//导出excel
function exprotexcel(){
    var url = contextPath+"/archive/lowCustQuery.do?action=exportExcel&" 
        + $("form input[name!=action]").serialize() + '&' 
        + $("form select").serialize()+ "&xmlId=lowCustQuery" 
        + "&r=" + parseInt(Math.random() * 1000);
    window.location.href = encodeURI(url,"utf-8");
}
</script>
</head>
<body>
<html:form action="/archive/lowCustQuery" method="post">
<html:hidden property="action" value="normalMode"/>
<html:hidden property="pageType" value="page"/>
<html:hidden property="objectId"/>
<html:hidden property="sqlCode"/>
<div id="body">
   <jsp:include page="archiveTabs.jsp" flush="true">
      <jsp:param value="3" name="pageType"/>
  </jsp:include>
  <div id="main">
    <div id="tool">
      <div class="opbutton1">
        <input type="button" name="query1" id="query1" value="查 询" onClick="queryData();" class="input1" />
        <input type="button" name="query2" id="query2" onclick="qry_adv_click({OBJECT_TYPE : '3',ARCHIVE_TYPE : '0',QUERY_TYPE : '0'})" value="高级查询" class="input2" />
      </div>
      <table border="0" cellpadding="0" cellspacing="0" width="80%">
        <tr>
          <td width="10%" class="label">用　户：</td>
          <td width="23%">
            <html:text property="custNo" styleId="custNo"/>
          </td>
          <td width="10%" class="label" align="center">表地址/表号：</td>
          <td width="23%" class="dom">
            <html:text property="mpNo" styleId="mpNo"/>
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
                <th width="5%">序号</th>
                <th width="10%">户名</th>
                <th width="10%">户号</th>
                <th width="12%">表号</th>
                <th width="8%">联系人</th>
                <th width="10%">联系电话</th>
                <th width="10%">所属集中器</th>
                <th width="6%">表序号</th>
                <th width="10%">表地址</th>
                <th width="12%">台区名称</th>
                <th width="7%">操作</th>
              </tr>
            </thead>
            <tbody align="center">
              <logic:present name="PG_QUERY_RESULT">
                <logic:iterate id="dataInfo" name="PG_QUERY_RESULT">
                  <tr onclick="selectSingleRow(this)" style="cursor:pointer;" id="<bean:write name="dataInfo" property="col10"/>,<bean:write name="dataInfo" property="col11"/>">
                    <td><bean:write name="dataInfo" property="rowNo" /></td>
                    <td><bean:write name="dataInfo" property="col1"/></td>
                    <td><bean:write name="dataInfo" property="col2"/></td>
                    <td><bean:write name="dataInfo" property="col3"/></td>
                    <td><bean:write name="dataInfo" property="col4"/></td>
                    <td><bean:write name="dataInfo" property="col5"/></td>
                    <td><bean:write name="dataInfo" property="col6"/></td>
                    <td><bean:write name="dataInfo" property="col7"/></td>
                    <td><bean:write name="dataInfo" property="col8"/></td>
                    <td><bean:write name="dataInfo" property="col9"/></td>
                    <td>
                      <a href="javascript:deleteCust('<bean:write name="dataInfo" property="col10"/>')"><bean:message  key="global.delete"/></a>
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
          <peis:paging actionForm="lowCustQueryForm" rowsChange="true" />
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
