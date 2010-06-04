<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>台区变更批修改</title>
    <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
    <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
    <script type="text/javascript">
var contextPath = "<peis:contextPath/>";
var tgId;
var tgName;
//初始化加载
$(function(){
if($.trim($('#tgNo').val())==""){
      $('#tgNo').val("请输入台区号或台区名");
      $('#tgNo').attr({style: "color:#aaaaaa;"});
    };
})
function select(row,name){
  selectSingleRow(row);
  tgId=$(".selected").attr("id");
  tgName=name;
}

tgNoFocus=function(obj){
if($.trim(obj.value)=="请输入台区号或台区名"){
       obj.value='';
       $('#tgNo').attr({style: "color:black;"});
     }
}
tgNoBlur=function(obj){
 if($.trim(obj.value)==''){
      obj.value="请输入台区号或台区名";
      $('#tgNo').attr({style: "color:#aaaaaa;"});
    }
}
//查询
function queryData(){
  $("input[name='sqlCode']").val("AL_ARCHIVE_0083");
  tgListQueryForm.submit();
}
//高级查询
function comfirmAfterQuery(data){
  var objectId=data.OBJECT_ID;
  $("input[name='objectId']").val(objectId);
  $("input[name='sqlCode']").val("AL_ARCHIVE_0090");
  tgListQueryForm.submit();
}
</script>
  </head>
  <body >
    <html:form action="/archive/changeTgQuery" method="post">
      <html:hidden property="action" value="normalMode" />
      <html:hidden property="pageType" value="all" />
      <html:hidden property="sqlCode"/>
      <html:hidden property="showType" value="all" />
      <input type="hidden" name="objectId"/>
      <div class="main" >
        <div id="tool">
          <div class="opbutton1">
            <input type="button" name="query1" id="query1" value="查 询" onclick="queryData()" class="input1" />
            <input type="button" class="input2"
              onclick="qry_adv_click({OBJECT_TYPE : '2',ARCHIVE_TYPE : '0',QUERY_TYPE : '0',IFRAME_NAME : '<%=request.getParameter("framName")%>'})" value="高级查询" />
          </div>
          <table border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="70" class="label">
                台区：
              </td>
              <td width="120">
                <html:text property="tgNo" styleId="tgNo"  style="color:#aaaaaa;" onfocus="tgNoFocus(this)" onblur="tgNoBlur(this)"/>
              </td>
              <td colspan="2"></td>
            </tr>
          </table>
        </div>
        <div class="content">
          <div id="tableContainer" class="tableContainer" style="height: expression((( document.documentElement.clientHeight || document.body.clientHeight) - 68) );width: expression((( document.documentElement.clientWidth || document.body.clientWidth) - 0 ) );">
            <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <thead>
                <tr>
                  <th>
                    编号
                  </th>
                  <th>
                    台区编号
                  </th>
                  <th>
                    台区名称
                  </th>
                  <th>
                    供电单位
                  </th>
                  <th>
                    容量(kVA)
                  </th>
                  <th>
                    联系人
                  </th>
                  <th>
                    联系电话
                  </th>
                  <th>
                    地址
                  </th>
                </tr>
              </thead>
              <tbody>
                <logic:present name="PG_QUERY_RESULT">
                  <logic:iterate id="dataInfo" name="PG_QUERY_RESULT">
                    <tr onclick="select(this,'<bean:write name="dataInfo" property="col1" />')" style="cursor: pointer;" id="<bean:write name="dataInfo" property="col6"/>">
                      <td align="center">
                        <bean:write name="dataInfo" property="rowNo" />
                      </td>
                      <td align="center">
                        <bean:write name="dataInfo" property="col2" />
                      </td>
                      <td>
                        <bean:write name="dataInfo" property="col1" />
                      </td>
                      <td align="center">
                        <bean:write name="dataInfo" property="col5" />
                      </td>
                      <td align="center">
                        <bean:write name="dataInfo" property="col3" />
                      </td>
                      <td>
                        <bean:write name="dataInfo" property="col7" />
                      </td>
                      <td>
                        <bean:write name="dataInfo" property="col8" />
                      </td>
                      <td>
                        <bean:write name="dataInfo" property="col9" />
                      </td>
                    </tr>
                  </logic:iterate>
                </logic:present>
              </tbody>
            </table>
          </div>          
          <div class="pageContainer" >
            <peis:paging actionForm="lineQueryForm" rowsChange="true" />
          </div>      
        </div>
      </div>
    </html:form> 
  </body>
</html>