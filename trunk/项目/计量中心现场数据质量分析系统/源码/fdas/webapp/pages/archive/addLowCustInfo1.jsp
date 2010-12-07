<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>低压用户主页面</title>
  <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
  <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
  <script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
  <script type="text/javascript"> 
var contextPath = '<peis:contextPath/>';

//初始化加载
$(document).ready(function(){
    inpuTextStyle();
    var custNo = $('#custNo');
    if(custNo != null && custNo.val() == ''){
        custNo.val('请输入户号或户名');
    }
});

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

//打开编辑低压用户页面
function showLowCust(custId){
   var url=contextPath+"/archive/addLowCustInfoAction.do?action=showLowCustInfo&custId="+custId+"&flag=1";
   top.showDialogBox("低压用户信息",url, 575,960);
}
//删除低压用户
function deleteLowCust(custId){
  var url=contextPath+"/archive/addLowCustInfoAction.do?action=deleteCust&custId="+custId;
     if(confirm("确定要删除该用户?")){
        $.ajax({
            url: url,
            dataType:'json',
            cache: false,
            success: function(json){
             var msg=json['msg'];
             if(msg=="1"){
                alert("删除成功");
                var tgId=$("input[name='tgId']").val();
                top.getMainFrameObj().location.href=contextPath+"/archive/updateLowCustInfoAction.do?action=showTgEdit&tgId="+tgId;;
             }else{
                alert("删除失败");
             }
          }
       });
     }
}
</script>
</head>
<body>
<html:form action="/archive/addLowCustQuery" method="post">
<html:hidden property="action" value="normalMode"/>
<html:hidden property="pageType" value="page"/>
<html:hidden property="sqlCode" value="AL_ARCHIVE_0032"/>
<html:hidden property="termId"/>
<input type="hidden" name="tgId" value="${tgId}"/>
<div id="body">
   <div id="main">
    <div id="tool">
     <div class="opbutton1">
       <input type="submit" name="query1" id="query1" onclick="" value="查 询" class="input1" />
     </div>
     <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="17%" class="label">用　户：</td>
          <td width="23%">
            <html:text property="custNo" styleId="custNo"/>
          </td>
          <td width="17%" class="label" align="center">表地址/表号：</td>
          <td width="23%" class="dom">
            <html:text property="mpNo"/>
          </td>
          <td colspan="2"></td>
        </tr>
     </table>
    </div>
    <div class="content">
     <div id="cont_1">
      <div id="tableContainer" class="tableContainer"
       style="height: expression((( document.documentElement.clientHeight || document.body.clientHeight) - 80 ) );">
       <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <thead>
         <tr>
          <th>序号</th>
          <th>户名</th>
          <th>户号</th>
          <th>表号</th>
          <th>联系人</th>
          <th>联系电话</th>
          <th>所属集中器</th>
          <th>表序号</th>
          <th>操作</th>
         </tr>
        </thead>
        <tbody align="center">
                <logic:present name="PG_QUERY_RESULT">
                <logic:iterate id="datainfo" name="PG_QUERY_RESULT">
                  <tr align="center" onclick="selectSingleRow(this)" style="cursor: pointer;" id="<bean:write name='datainfo' property='col7' />">
                    <td>
                      <bean:write name="datainfo" property="rowNo" />
                    </td>
                    <td>
                      <bean:write name="datainfo" property="col1" />
                    </td>
                    <td>
                      <bean:write name="datainfo" property="col2" />
                    </td>
                    <td>
                      <bean:write name="datainfo" property="col3" />
                    </td>
                    <td>
                      <bean:write name="datainfo" property="col4" />
                    </td>
                    <td>
                      <bean:write name="datainfo" property="col5" />
                    </td>
                    <td>
                      <bean:write name="datainfo" property="col6" />
                    </td>
                    <td>
                      <bean:write name="datainfo" property="col7" />
                    </td>
                    <td>
                      <a href="javascript:showLowCust('<bean:write name="datainfo" property="col8"/>');"><bean:message  key="global.edit"/></a>|
                      <a href="javascript:deleteLowCust('<bean:write name="datainfo" property="col8"/>')"><bean:message  key="global.delete"/></a>
                    </td>
                    <td>
                    </td>
                  </tr>
                </logic:iterate>
              </logic:present>
            </tbody>
       </table>
      </div>
     </div>
    </div>
     <div class="pageContainer">
        <peis:paging actionForm="addLowCustQueryForm" rowsChange="true" />
     </div>
   </div>
  </div>
  </html:form>
</body>
</html>
