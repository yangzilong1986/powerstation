<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>线路变更批修改</title>
    <link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/main.css" />
    <script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
    <script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
    <script type="text/javascript">
var contextPath = "<peis:contextPath/>";
var lineId;
var lineName;
var lineNo;
//初始化加载
$(function(){
if($.trim($('#lineNo').val())==""){
      $('#lineNo').val("请输入线路号或线路名");
      $('#lineNo').attr({style: "color:#aaaaaa;"});
    };
})
function select(row,name){
  selectSingleRow(row);
  lineId=$(".selected").attr("id");
  lineName=name;
}

lineNoFocus=function(obj){
if($.trim(obj.value)=="请输入线路号或线路名"){
       obj.value='';
       $('#lineNo').attr({style: "color:black;"});
     }
}
lineNoBlur=function(obj){
 if($.trim(obj.value)==''){
      obj.value="请输入线路号或线路名";
      $('#lineNo').attr({style: "color:#aaaaaa;"});
    }
}
</script>
  </head>
  <body >
    <html:form action="/archive/changeLineQuery" method="post">
      <html:hidden property="action" value="normalMode" />
      <html:hidden property="pageType" value="all" />
      <html:hidden property="sqlCode" value="AL_ARCHIVE_0080" />
      <html:hidden property="showType" value="all" />
      <div class="main" >
        <div id="tool">
          <div class="opbutton1">
            <input type="submit" name="query" id="query" value="查 询" class="input1" />
          </div>
          <table border="0" cellpadding="0" cellspacing="0">
            <tr>  
              <td width="70" class="label">
                线路：
              </td>
              <td width="120">
              <html:text property="lineNo" styleId="lineNo" style="color:#aaaaaa;" onfocus="lineNoFocus(this)" onblur="lineNoBlur(this)"/>
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
                    线路编号
                  </th>
                  <th>
                    线路名称
                  </th>
                  <th>
                    供电单位
                  </th>
                  <th>
                    电压等级
                  </th>
                  <th>
                    所属厂站1
                  </th>
                  <th>
                    所属厂站2
                  </th>
                  <th>
                    所属厂站3
                  </th>
                </tr>
              </thead>
              <tbody>
                <logic:present name="PG_QUERY_RESULT">
                  <logic:iterate id="dataInfo" name="PG_QUERY_RESULT">
                    <tr onclick="select(this,'<bean:write name="dataInfo" property="col2" />')" style="cursor: pointer;" id="<bean:write name="dataInfo" property="col8"/>">
                      <td align="center">
                        <bean:write name="dataInfo" property="rowNo" />
                      </td>
                      <td align="center">
                        <bean:write name="dataInfo" property="col1" />
                      </td>
                      <td>
                        <bean:write name="dataInfo" property="col2" />
                      </td>
                      <td align="center">
                        <bean:write name="dataInfo" property="col3" />
                      </td>
                      <td align="center">
                        <bean:write name="dataInfo" property="col4" />
                      </td>
                      <td align="center">
                        <bean:write name="dataInfo" property="col5" />
                      </td>
                      <td align="center">
                        <bean:write name="dataInfo" property="col6" />
                      </td>
                      <td align="center">
                        <bean:write name="dataInfo" property="col7" />
                      <br></td>
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