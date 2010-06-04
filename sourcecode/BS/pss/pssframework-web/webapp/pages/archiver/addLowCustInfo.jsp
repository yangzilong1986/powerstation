<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>低压集抄档案录入第四步</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';

//初始化加载
$(document).ready(function(){

})
//上一步
function lastStep(){
  //window.location.href="<peis:contextPath/>/jsp/archive/addLowCustRelevance.html";
  //history.back(-1);
  window.location.href=contextPath+"/archive/addLowCustAction.do?action=showDeviceInfoByTgID";
}
//完成
function finish(){
   if(confirm("是否继续新增台区")==true){
      window.location.href=contextPath+"/jsp/archive/addLowVoltCust.jsp";
   }else{
      //window.location.href=contextPath+"/archive/addLowCustAction.do?action=finish"; 
     window.location.href=contextPath+"/archive/commAction.do?action=forwardSelectType&jspName=lowVoltCustType&selectType=1";
   }
}
//打开低压用户页面
function openLowCust(){
   var url=contextPath+"/jsp/archive/addLowCustDetail.jsp";
   top.showDialogBox("低压用户信息",url, 575,960);
}
//打开编辑低压用户页面
function showLowCust(custId){
   var url=contextPath+"/archive/addLowCustInfoAction.do?action=showLowCustInfo&custId="+custId;
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
		                window.location.href=contextPath+"/archive/lowCustListQuery.do?action=normalMode&sqlCode=AL_ARCHIVE_0024";
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
<html:form action="/archive/lowCustListQuery" method="post">
<html:hidden property="action" value="normalMode"/>
<html:hidden property="pageType" value="page"/>
<html:hidden property="sqlCode" value="AL_ARCHIVE_0024"/>
<html:hidden property="showType"/>
<div id="body">
   <jsp:include page="archiveTabs.jsp" flush="true">
      <jsp:param value="3" name="pageType"/>
   </jsp:include>
   <div id="main">
    <div id="tool">
     <div class="opbutton1">
     </div>
    </div>
    <div class="content">
     <div id="cont_1">
      <div class="data1"><span>用户列表</span><h1><a href="#" onclick="openLowCust(); return false;"><img src="<peis:contextPath/>/img/bt_add.png" width="19" height="19" class="mgt5" /></a></h1></div>
      <div class="data1_con" style="height: expression((( document.documentElement.clientHeight||document.body.clientHeight)-156));">
        <div class="main">
        <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <thead>
             <tr>
              <th>序号</th>
              <th>户名</th>
              <th>户号</th>
              <th>表号</th>
              <th>表地址</th>
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
                      <a href="javascript:showLowCust('<bean:write name="datainfo" property="col7"/>');"><bean:message  key="global.edit"/></a>|
                      <a href="javascript:deleteLowCust('<bean:write name="datainfo" property="col7"/>')"><bean:message  key="global.delete"/></a>
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
         <peis:paging actionForm="archiveQueryForm" rowsChange="true" /></div>
      </div>
      <div class="guidePanel">
      <input type="button" id="last" value="上一步" class="input1" onclick="lastStep();" />
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="next" value="完 成" class="input1" onclick="finish();" />
      &nbsp;&nbsp;&nbsp;&nbsp;
      </div>
    </div>
   </div>
</html:form>
</body>
</html>
