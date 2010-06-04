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
//导入excel
function importFile(){
  var fileName=$("input[name='fileName']").val();
  if(fileName=='' || fileName=='undefined'){ //如果文件为空
    alert("请先上传低压用户档案文件");
  }else{
    show_loading();
    uploadForm.submit();
  }
}
</script>
</head>
<body>
<div id="body">
   <html:form action="/archive/ImportLowCust.do?action=importFileInDataBase">
    <input type="hidden" name="fileName" value="${fileName}"/>
    <input type="hidden" name="isAddGm" value="${isAddGm}"/>
    <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%" align="center">
      <tr align="center">
        <td>
          <logic:equal value="1" name="result">
            <font color="green">文件导入成功！</font>
          </logic:equal>
          <logic:equal value="0" name="result">
            <font color="red">文件导入失败！</font>
          </logic:equal>
        </td>
      </tr>
      <tr align="center">
        <td class="dom">
          <input type="button" onClick="importFile();" value="开始导入" class="input3" />
        </td>
      </tr>
    </table>
   </html:form>
</div>
</body>
</html>
