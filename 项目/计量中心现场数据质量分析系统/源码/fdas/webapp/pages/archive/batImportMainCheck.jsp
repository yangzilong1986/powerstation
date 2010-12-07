<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../common/loading.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>批量导入</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/loading.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/loading.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript"> 
//文件验证
function fileCheck(){
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
<html:form action="/archive/ImportLowCust.do?action=checkFile">
<input type="hidden" name="fileName" value="${fileName}"/>
<input type="hidden" name="isPass" value="${isPass}"/>
<input type="hidden" name="isAddGm" value="${isAddGm}"/>
<div id="tool">
  <table cellpadding="0" cellspacing="0" width="100%">
    <tr align="right">
      <td>
         <logic:present name="msg">
           <font color="red">${msg}</font>
         </logic:present>
         <logic:equal value="pass" name="isPass">
           <font color="green">文件验证通过，请导入数据库</font>
         </logic:equal>
         <input type="button" onClick="fileCheck();" value="开始检查" class="input3" />
      </td>
    </tr>
  </table>
</div>
<div class="content">
    <div class="tableContainer" style="height:expression((document.documentElement.clientHeight||document.body.clientHeight) - 70);">
      <table  cellpadding="0" cellspacing="0" width="100%">
        <thead>
         <tr>
          <th width="5%">序号</th>
          <th>异常提示</th>
         </tr>
        </thead>
        <tbody>
           <c:forEach var="errorInfo" items="${errorInfoList}" varStatus="status">
              <tr onclick="selectSingleRow(this)" style="cursor:pointer;" >
                <td align="center"><c:out value="${status.count}"/></td>
                <td align="center"><c:out value="${errorInfo}"/></td>
              </tr>  
           </c:forEach>
        </tbody>
      </table>
    </div>
    <div class="pageContainer">
    </div>
</div>
</html:form>
</body>
</html>
