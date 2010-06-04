<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>批量导入</title>
<link rel="stylesheet" type="text/css" href="<peis:contextPath/>/css/mainframe.css" />
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveCheck.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/archive/archiveComm.js"></script>
<script type="text/javascript">
var contextPath = '<peis:contextPath/>';
function changeType(type) {
    var show1 = document.getElementById("show1");
    var show2 = document.getElementById("show2");
    var show3 = document.getElementById("show3");
    if(type == "1") { //上传资料
        $('#show1').attr("class","");
        $('#show2').attr("class","off");
        $('#show3').attr("class","off");
        $('#contentArea').attr("src",contextPath+"/archive/ImportLowCust.do?action=forwardToFileUp");
    }else if(type == "2"){ //合法性检查
        var fileName=encodeURI($("input[name='fileName']",document.frames('contentArea').document).val(),"utf-8");
        var isAddGm=$("input[name='isAddGm'][checked]",document.frames('contentArea').document).val();
        if(fileName==''){
          alert("文件未上传，请重新上传！");
        }else{
          $('#show1').attr("class","off");
          $('#show2').attr("class","");
          $('#show3').attr("class","off");
          var params = {
                  "fileName":fileName,
                  "isAddGm":isAddGm
                };
          $('#contentArea').attr("src",contextPath+"/archive/ImportLowCust.do?action=forwardToFileCheck&" + jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000)); 
        }
    }
    else { //批量导入
        //文件是否通过
        var isPass=$("input[name='isPass']",document.frames('contentArea').document).val();
        var isAddGm=$("input[name='isAddGm']",document.frames('contentArea').document).val();
        if(isPass=="pass"){
          $('#show1').attr("class","off");
          $('#show2').attr("class","off");
          $('#show3').attr("class","");
          var fileName=encodeURI($("input[name='fileName']",document.frames('contentArea').document).val(),"utf-8");
          var params = {
                    "fileName":fileName,
                    "isAddGm":isAddGm  
                };
          $('#contentArea').attr("src",contextPath+"/archive/ImportLowCust.do?action=forwardToFileImport&" + jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000));
        }else{
          alert("文件未通过验证，无法导入！");
        }
    }
}

</script>
</head>
<body>
<div id="body">
    <div class="tab">
      <em id="show1"><a href="javascript:changeType('1');" onfocus="blur()">1.上传资料</a></em>
      <em id="show2" class="off"><a href="javascript:changeType('2');" onfocus="blur()">2.合法性检查</a></em>
      <em id="show3" class="off"><a href="javascript:changeType('3');" onfocus="blur()">3.批量导入</a></em>
    </div>
    <div id="main" style="border-left: 1px #5d90d7 solid; border-right: 1px #5d90d7 solid; border-bottom: 1px #5d90d7 solid;height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 35));">
        <iframe id="contentArea" src="<peis:contextPath/>/archive/ImportLowCust.do?action=forwardToFileUp" width="100%" height="100%" frameborder="0" scrolling="no"></iframe>
    </div>
</div>
</body>
</html>
