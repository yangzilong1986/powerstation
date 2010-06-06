<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>终端定位</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.url.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript">
//初始化加载
$(function(){
showViaKeypress();
})
function finish() {
    var logicAddr = $(".selected td:eq(1)").html();  //取到选中行终端逻辑地址
    var termId=$(".selected").attr("id");
    //刷新父窗口,所属终端控件
    if(logicAddr==null){
       alert("请选择终端");
    }else{
        $('#termId',parent.document).append("<option value='"+termId+"' selected>"+logicAddr+"</option>");
	    $('#termIdCasc',parent.document).append("<option value='"+termId+"' selected>"+logicAddr+"</option>");
	    if(top.getMainFrameObj()!=null){
	      top.getMainFrameObj().$('#termId').append("<option value='"+termId+"' selected>"+logicAddr+"</option>");
	    }
	    if(top.getMainFrameObj().contentArea!=null){
	      top.getMainFrameObj().contentArea.$('#termIdCasc').append("<option value='"+termId+"' selected>"+logicAddr+"</option>");
	    }
	    parent.GB_hide();
    }
}

function query(){
  archiveQueryForm.submit();
}

</script>
</head>
<body style="overflow: hidden;">
<div id="main">
  <html:form  action="/archive/termListQueryInBigCust" method="post">
  <input type="hidden" name="action" value="normalMode">
  <input type="hidden" name="sqlCode" value="AL_ARCHIVE_0040">
  <input type="hidden" name="objectId" value="${custId}">
  <html:hidden property="showType"/>
  <div id="tool">
    <div class="opbutton1">
      <input type="submit" id="query" value="查 询" class="input1"/>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" id="save" value="选定终端" class="input2" onclick="finish();" />
    </div>
    <table border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="100" class="label">终端逻辑地址：</td>
        <td width="120" class="dom">
          <input type="text" id="logicalAddr" name="logicalAddr"/>
        </td>
        <td colspan="3"></td>
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
              <th>终端逻辑地址</th>
              <th>资产编号</th>
              <th>终端类型</th>
              <th>设备规约</th>
              <th>设备厂家</th>
              <th>户名</th>
            </tr>
          </thead>
          <tbody>
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
                  </tr>
                </logic:iterate>
              </logic:present>
          </tbody>
        </table>
      </div>
      <div class="pageContainer">
        <peis:paging actionForm="archiveQueryForm" rowsChange="true" />
      </div>
    </div>
  </div>
  </html:form>
</div>
</body>
</html>