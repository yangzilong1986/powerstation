<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<%@include file="../common/loading.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>批量导入</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/loading.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/loading.js"></script>
<script type="text/javascript"> 
var contextPath = '<peis:contextPath/>';

//上传文件
function upload(){
  if(isExcel()){
    show_loading();
    uploadForm.submit();
  }else{
    alert("请选择EXCEL文件类型！");
    return false;
  }
}
//判断文件类型是否是excel文件
function isExcel(){
  var uploadFile=$("input[name='uploadFile']").val();
  var ext=uploadFile.substring(uploadFile.lastIndexOf(".")+1,uploadFile.length);
  if (ext != "xls" && ext != "XLS" && ext != "Xls" && ext != "XLs" && ext != "xLS" && ext != "xlS") {
    return false;
  }else if(uploadFile==""){
    alert("上传文件不能为空！")
    return false;
  }else{
    return true;
  }
}
//删除低压用户
function deleteLowCustByLogicalAddr(){
   var logicalAddr = $("#logicalAddr").val();
   var url = contextPath + "/archive/ImportLowCust.do?action=deleteLowCustByLogicalAddr&logicalAddr="+logicalAddr+"&r=" + parseInt(Math.random() * 1000);
   $.ajax({
     url: url,
     cache: false,
     success: function(result){
        if(result == "1"){
           alert("删除用户成功！");
        }else{
           alert("删除用户失败！");
        }
     }
   });
}
//按钮权限验证
function buttonRoleValidae(){
  var url = contextPath + "/archive/commAction.do?action=buttonRoleValidae&roleId=20&r=" + parseInt(Math.random() * 1000);
  if(confirm("是否要删除该集中器下的所有小用户")==true){
     $.ajax({
     url: url,
     cache: false,
     success: function(result){
        if(result >0 ){
          deleteLowCustByLogicalAddr();
        }else{
          alert("当前操作员没有档案编辑权限！");
        }
      }
     });
  }
}
</script>
</head>
<body>
<div id="body">
   <html:form action="/archive/ImportLowCust.do?action=importFile" enctype="multipart/form-data">
    <input type="hidden" name="fileName" value="${fileName}"/>
    <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%" align="center">
      <tr align="center">
        <td class="dom">
          <font color="red">是否需要系统自动新增采集器:</font><html:radio property="isAddGm" value="1">是</html:radio><html:radio property="isAddGm" value="0">否</html:radio>
        </td>
      </tr>
      <tr align="center">
        <td class="dom">
                             请选择低压用户档案文件(.xls):
           <html:file property="uploadFile" style=" border: 1px #7f9db9 solid; background: #fff; width: 280px; height: 21px; font-size: 12px; color: #4a5c8f;"/><br/>
        </td>
      </tr>
      <tr align="center">
        <td>
          <logic:equal value="1" name="uploadResult"><font color="green">文件上传成功！</font></logic:equal>
          <logic:equal value="0" name="uploadResult"><font color="red">文件上传失败！</font></logic:equal>
        </td>
      </tr>
      <tr align="center">
        <td><input type="button" class="input1" value="上传" onClick="upload();"/>
        </td>
      </tr>
      <tr align="center">
        <td>
                           要删除低压用户的集中器地址:
          <input type="text" id="logicalAddr" style=" border: 1px #7f9db9 solid; background: #fff; width: 150px; height: 21px; font-size: 12px; color: #4a5c8f;" name="logicalAddr" />
          <input type="button" class="input1" value="删除" onClick="buttonRoleValidae();"/>
        </td>
      </tr>
    </table>
   </html:form>
</div>
</body>
</html>
