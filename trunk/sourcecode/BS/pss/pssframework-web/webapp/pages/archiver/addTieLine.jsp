<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>线路新增</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/window.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/greybox.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/js/ext/resources/css/xtheme-gray.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/ext/ext-all.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.dataForAjax.js"></script>
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';

$(document).ready(function(){
 selectInit();
});
//保存线路信息
function save(){
   var url=contextPath+"/archive/lineInfoAcrion.do?action=saveLine";
     if(lineCheck()){
       $("#save").attr("disabled",true);
       var data = getFormData("form");
       if(data){
           jQuery.ajax({
               type:'post',
               url:url,
               data:data,
               dataType:'json',
               success:function(json){
                   var msg=json['msg'];
                   $("#save").attr("disabled",false);
                   if(msg=="1"){
                      alert("保存成功");
                      //top.getMainFrameObj().treeframe.location.href=contextPath+"/windowTreeAction.do?action=getTree&WINDOW=TREE&SMXBH=TREE_ARCHIVE_0003&objectType=10&queryFunction=query()&typicalType=1&limited=false&limitedtype=";//刷新树节点


                      //top.getMainFrameObj().mainframe.lineInfoForm.submit(); //刷新主页面


                      top.getMainFrameObj().mainframe.location.reload();
                      top.getMainFrameObj().treeframe.location.reload();
                      parent.GB_hide();
                   }else if(msg=="2"){
                      alert("该线路编号已经存在");
                   }else{
                      alert("保存失败");
                   }
               }
           });
       }
     }
}
//select框初始化
function selectInit(){
  $("#orgNo").val('${object_line.orgNo}');
  $("#voltGrade").val(${object_line.voltGrade});
  $("#lineType").val(${object_line.lineType});
  //$("#upperLine").val(${object_line.upperLine});
  upperLineComboBox.setValue('${object_line.upperLine}');
  $("#subsId").val(${object_line.subsId});
  $("#subsId2").val(${object_line.subsId2});
  $("#subsId3").val(${object_line.subsId3});
  $("#lineStatus").val('${object_line.lineStatus}');
}
</script>
</head>
<body>
<div id="form">
<input type="hidden" id="lineId" name="lineId" value="${object_line.lineId}">
<input type="hidden" id="objectType" name="objectType" value="9">
<div id="main" style="height: 100%;">
  <div class="tab"><em>线路信息</em></div>
  <div class="tab_con" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 80));">
    <div class="main">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
      <tr>
       <td width="15%" class="label"><font color="red">* </font>线路编号：</td>
       <td width="25%" class="dom">
         <input type="text" id="lineNo" name="lineNo" value="${object_line.lineNo}">
       </td>
       <td width="15%" class="label"><font color="red">* </font>线路名称：</td>
       <td width="25%" class="dom"><input type="text" id="lineName" name="lineName" value="${object_line.lineName}"></td>
      </tr>
      <tr>
       <td width="15%" class="label">管理单位：</td>
       <td width="25%" class="dom">
          <peis:selectlist name="orgNo" sql="SL_COMMON_0001"/>
       </td>
       <td width="15%" class="label">电压等级：</td>
       <td width="25%" class="dom">
          <peis:selectlist name="voltGrade" sql="SL_ARCHIVE_0007"/>
       </td>
      </tr>
      <tr>
       <td width="15%" class="label">线路类型：</td>
       <td width="25%" class="dom">
         <select name="lineType" id="lineType" >
           <option value="2">联络线</option>
         </select>
       </td>
       <td width="15%" class="label">上线线路：</td>
       <td width="25%" class="dom">
          <peis:selectlist name="upperLine" sql="SL_ARCHIVE_0018" associate="true"/>
       </td>
      </tr>
      <tr>
       <td width="15%" class="label"><font color="red">* </font>线路阈值：</td>
       <td width="25%" class="dom">
         <input type="text" id="lineThreshold" name="lineThreshold"  value="<c:if test="${object_line.lineThreshold!=null}"><fmt:formatNumber value="${object_line.lineThreshold*100}" pattern="#0.0000#"  minFractionDigits="0"/></c:if>" style="width: 125px;"/> %
       </td>
       <td width="15%" class="label">所属变电站1：</td>
       <td width="25%" class="dom">
          <peis:selectlist name="subsId" sql="SL_ARCHIVE_0059"/>
       </td>
      </tr>
      <tr>
       <td width="15%" class="label">所属变电站2：</td>
       <td width="25%" class="dom">
         <peis:selectlist name="subsId2" sql="SL_ARCHIVE_0059"/>
       </td>
       <td width="15%" class="label">所属变电站3：</td>
       <td width="25%" class="dom">
         <peis:selectlist name="subsId3" sql="SL_ARCHIVE_0059"/>
       </td>
      </tr>
      <tr>
       <td width="15%" class="label">线路状态：</td>
       <td width="25%" class="dom">
         <peis:selectlist name="lineStatus" sql="SL_ARCHIVE_0061"/>
       </td>
       <td colspan="2"></td>
      </tr>
      <tr>
         <td rowspan="3" class="label">备　注：</td>
          <td class="dom" colspan="3">
            <textarea class="input_textarea3" name="remark" style="width:450;height:80">${object_line.remark}</textarea>
          </td>
      </tr>
     </table>
    </div>
  </div>
  <div class="guidePanel">
    <input class="input1" type="button" id="save" value="保 存" onclick="save();" />
  </div>
</div>
</div>
</body>
</html>