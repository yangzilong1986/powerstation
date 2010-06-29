<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<jsp:useBean id="now" class="java.util.Date" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>其他数据项主页面</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';
//初始化加载
$(function(){
   var proNo = $('#proNo', window.parent.document).val();
   if(isZheGrid(proNo)){
      disabledButton();
   }
   $("#selectAll").click(function() { 
     $("input[name='ItemID']").each(function() { 
        var vOrderId=$(this).val();
        //alert($("#selectAll").attr("checked"));
        if($("#selectAll").attr("checked") == false){
          $(this).attr("checked", false); 
        }else{
          $(this).attr("checked", true); 
        }
        selectOne(vOrderId,$(this));  
     });
   });
   $("input[name='ItemID']").click(function() { 
      var vOrderId=$(this).val();
      selectOne(vOrderId,$(this));
   });
})
//组装命令项
function assembleCommItems(){
   var sSelectedList = getSelectedIdString(0);
   if(sSelectedList == "") {
       alert("至少选择一个命令项");
       return;
   }
   return sSelectedList;
}
//组装数据项
function assembleDataItems(){
   var dataItemArr = {};
   $("input[name='dataItemId']").each(function(i){
     if($(this).attr("checked")==true){
         var dataKey = $(this).val();
         var dataValue = $("#"+dataKey).parent().find("td:eq(1)").html();
         //alert(dataKey+"-----------"+$("#"+dataKey).parent().find("td:eq(1)").html());
         dataItemArr[dataKey]=dataValue;
     }
   });
   return dataItemArr;
}
//单选
function selectOne(vOrderId,curObject){
    for(var i = 0; i < 100; i++) {
      if($("#otr_" + vOrderId + "_" + i).length > 0) {
          var dataItemObject = $("#otr_" + vOrderId + "_" + i).find("input[name=dataItemId]");
          if(curObject.attr("checked") == false){
            $(dataItemObject).attr("checked", false); 
          }else{
            $(dataItemObject).attr("checked", true); 
          }    
      }
      else {
          break;
      }
    }
}
//
function folder(objImg, vOrderId) {
    var srcImg = $(objImg).attr("src");
    if(srcImg == contextPath + "/images/common/jsFlexGrid/plus1.gif") {
        $(objImg).attr("src", contextPath + "/images/common/jsFlexGrid/minus1.gif");
    }
    else {
        $(objImg).attr("src", contextPath + "/images/common/jsFlexGrid/plus1.gif");
    }
    
    for(var i = 0; i < 100; i++) {
        if($("#otr_" + vOrderId + "_" + i).length > 0) {
            if(srcImg == contextPath + "/images/common/jsFlexGrid/plus1.gif") {
                $("#otr_" + vOrderId + "_" + i).attr("class", "styleShow");
            }
            else {
                $("#otr_" + vOrderId + "_" + i).attr("class", "styleHidden");
            }
        }
        else {
            break;
        }
    }
}
//控件失效
function disabledButton(){
  $("#date").attr("disabled", true);
  $("#hour").attr("disabled", true);
  $("#minute").attr("disabled", true);
  $("#timeStep").attr("disabled", true);
  $("#point").attr("disabled", true);
}
//判断是否是浙网
function isZheGrid(proNo){
  var result = false;
  if(proNo == '120' || proNo == '121' || proNo == '122' || proNo == '123' || proNo == '124' || proNo == '125' || proNo == '126' || proNo == '127' || proNo == '129'){
    result = true;
  }
  return result;
}
</script>
</head>
<body>
<div id="tool">
  <table border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="66" class="label">开始时间：</td>
      <td> 
         <input type="text" id="date" name="date" value="<fmt:formatDate value="${now}" type="date" pattern="yyyy-MM-dd"/>"  onfocus="peisDatePicker()" readonly="readonly" />
         <select name="hour" id="hour">
            <option value="00">00</option>
            <option value="01">01</option>
            <option value="02">02</option>
            <option value="03">03</option>
            <option value="04">04</option>
            <option value="05">05</option>
            <option value="06">06</option>
            <option value="07">07</option>
            <option value="08">08</option>
            <option value="09">09</option>
            <option value="10">10</option>
            <option value="11">11</option>
         </select>时
         <select name="minute" id="minute">
            <option value="00">00</option>
            <option value="15">15</option>
            <option value="30">30</option>
            <option value="45">45</option>
         </select>分
      </td>
      <td width="66" class="label">时间间隔：</td>
      <td width="120" class="dom">
         <select name="timeStep" id="timeStep">
            <option value="0">不冻结</option>
            <option value="1">15分钟</option>
            <option value="2">30分钟</option>
            <option value="3">60分钟</option>
         </select>
      </td>
      <td width="66" class="label">点  数：</td>
      <td width="120" class="dom">
         <input type="text" id="point" name="point" value="20"/>
      </td>
    </tr>
  </table>
</div>
<div class="content">
  <div id="cont_1">
    <div class="target2">
      <ul>
        <li class="target_on"><a href="#" onclick="setTarget(1); return false;">数据项列表</a></li>
        <li class="clear"></li>
      </ul>
      <h1><img src="<peis:contextPath/>/img/bt_data.gif" width="10" height="10" align="middle"" /> <a href="#">修改显示字段</a></h1>
    </div>
    <div class="tableContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 108));">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <thead><tr>
          <th width="10%">
             <input type="checkbox" name="selectAll" id="selectAll" checked />
          </th>
          <th>数据项名称</th>
          <th>数据项编码</th>
          <th>单位</th>
          <th>选中</th>
        </tr></thead>
        <tbody id="dataTbody">
          <% int index = 0;%>
          <logic:present name="PG_QUERY_RESULT">
             <logic:iterate id="dataInfo" name="PG_QUERY_RESULT">
                 <logic:empty name="dataInfo" property="col3"> <!-- 命令项 -->
                   <%index = 0;%>
                   <tr onclick="selectSingleRow(this)" style="cursor:pointer;">
                    <td>
                      <img align="absmiddle" src="<peis:contextPath/>/images/common/jsFlexGrid/plus1.gif" style="cursor:pointer;" onclick="folder(this,'<bean:write name='dataInfo' property='col1'/>')" />
                      <input type="checkbox" name="ItemID" value="<bean:write name='dataInfo' property='col1'/>" checked="checked"/>
                    </td>
                    <td colspan="4"><bean:write name="dataInfo" property="col2"/></td>
                   </tr>
                 </logic:empty>
                 <logic:notEmpty name="dataInfo" property="col3"> <!-- 数据项 -->
                   <tr onclick="selectSingleRow(this)" style="cursor:pointer;" id="otr_<bean:write name='dataInfo' property='col1'/>_<%=index++%>" class="styleHidden">
                    <td></td>
                    <td align="right"><bean:write name="dataInfo" property="col2"/></td>
                    <td align="right"><bean:write name="dataInfo" property="col3"/></td>
                    <td align="right"><bean:write name="dataInfo" property="col4"/></td>
                    <td align="center" id="<bean:write name='dataInfo' property='col1'/>_<bean:write name='dataInfo' property='col3'/>">  
                        <input type="checkbox" name="dataItemId" value="<bean:write name='dataInfo' property='col1'/>_<bean:write name='dataInfo' property='col3'/>" checked="checked"/>
                    </td>
                   </tr>
                 </logic:notEmpty>
             </logic:iterate>
           </logic:present>
        </tbody>
      </table>
    </div>
    <div class="pageContainer">
    </div>
  </div>
</div>
</body>
</html>