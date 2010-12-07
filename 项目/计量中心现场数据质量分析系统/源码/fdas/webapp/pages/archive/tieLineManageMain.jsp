<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>联络线管理主页面</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';

//新增线路
function newLine(){
  var url=contextPath+"/jsp/archive/addTieLine.jsp";
  top.showDialogBox("新增联络线",url, 575, 900);
}
//编辑线路 
function editLine(){
  var lineId=$(".selected").attr("id");  //取到选中行线路编号
  if(lineId==null){
    alert("请选中一条线路！");
  }else{
    var url=contextPath+"/archive/lineInfoAcrion.do?action=showLineInfo&lineId="+lineId;
    top.showDialogBox("编辑线路",url, 575, 900);
  }
}
//删除线路
function delLine(){
  var lineId=$(".selected").attr("id");  //取到选中行线路编号
  if(lineId==null){
    alert("请选中一条线路！");
  }else{
    var url=contextPath+"/archive/lineInfoAcrion.do?action=delLine&lineId="+lineId+"&objectType=9";
    if(confirm("是否删除该条线路")==true){
         $.ajax({
         url: url,
         dataType:'json',
         cache: false,
         success: function(json){
           var msg=json['msg'];
            if(msg=="1"){
             alert("删除成功");
             top.getMainFrameObj().treeframe.location.href=contextPath+"/windowTreeAction.do?action=getTree&WINDOW=TREE&SMXBH=TREE_ARCHIVE_0003&objectType=10&queryFunction=query()&typicalType=1&limited=false&limitedtype=";//刷新树节点
             lineInfoForm.submit();
             }else if(msg=="5"){
                 alert("该线路下存在电表，不允许删除");
             }
             else{
                alert("删除失败");
             }
         }
        });
    }
  }
}
</script>
</head>
<body>
<html:form action="/archive/lineInfoAcrion" method="post">
<html:hidden property="action" value="queryTieLineList"/>
<html:hidden property="pageType" value="page"/>
<html:hidden property="sqlCode" value="AL_ARCHIVE_0054"/>
<input type="hidden" name="showType"/>
<div id="tool">
  <div class="opbutton1"><input type="submit" name="query" id="query" value="查 询" class="input1" /></div>
  <table border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="66" class="label">变 电 站：</td>
      <td width="120" class="dom">
         <peis:selectlist name="subsId" sql="SL_ARCHIVE_0062"/>
      </td>
      <td width="80" class="label">联络线名称：</td>
      <td width="120" class="dom"><input type="text" id="lineName" name="lineName" value="${lineName}"/></td>
    </tr>
  </table>
</div>
<div class="content">
  <div id="cont_1">
    <div class="target2">
      <ul>
        <li class="target_on"><a href="#" onclick="setTarget(1); return false;">线路列表</a></li>
        <li class="clear"></li>
      </ul>
      <h1><img src="<peis:contextPath/>/img/bt_data.gif" width="10" height="10" align="middle"" /> <a href="#">修改显示字段</a></h1>
    </div>
    <div class="pad5">
       <input type="button" class="input1" name="add" value='新增' onclick="newLine()" />
       <input type="button" class="input1" name="edit" value='编辑' onclick="editLine()" />
       <input type="button" class="input1" name="del" value='删除' onclick="delLine()" />
    </div>
    <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 142));">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <thead><tr>
          <th width="3%">序号</th>
          <th width="7%">联络线编号</th>
          <th width="12%">联络线名称</th>
          <th width="5%">电压等级</th>
          <th width="10%">管理单位</th>
          <th width="17%">所属变电站</th>
          <th width="7%">线损率阀值(%)</th>
        </tr></thead>
        <tbody>
          <logic:present name="PG_QUERY_RESULT">
            <logic:iterate id="dataInfo" name="PG_QUERY_RESULT">
              <tr onclick="selectSingleRow(this)" style="cursor:pointer;" id="<bean:write name="dataInfo" property="col1"/>">
                <td><bean:write name="dataInfo" property="rowNo" /></td>
                <td><bean:write name="dataInfo" property="col2"/></td>
                <td><bean:write name="dataInfo" property="col3"/></td>
                <td><bean:write name="dataInfo" property="col4"/></td>
                <td><bean:write name="dataInfo" property="col5"/></td>
                <td><bean:write name="dataInfo" property="col6"/></td>
                <td><c:if test="${dataInfo.col7!=null}"><fmt:formatNumber value="${dataInfo.col7*100}" pattern="#0.0000#"  minFractionDigits="0"/></c:if></td>
              </tr>
            </logic:iterate>
          </logic:present>
        </tbody>
      </table>
    </div>
    <div class="pageContainer">
       <peis:paging actionForm="lineInfoForm" rowsChange="true" />
    </div>
  </div>
</div>
</html:form>
</body>
</html>