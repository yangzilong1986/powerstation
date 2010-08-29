
<%@ page contentType="text/html; charset=utf-8" %>




<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=gb2312">
<title><bean:message bundle="system" key="notice.edit.title"/></title>
<link href="<peis:contextPath/>/css/mainframe.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.xml2json.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/FusionCharts/FusionCharts.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/json2htmlex.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script> 
<script type="text/javascript" src="<peis:contextPath/>/js/elecmon/elecmon.js"></script>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";
var errorFbfw='<bean:message bundle="system" key="notice.errorFbfw"/>';

$(document).ready(function(){    
    var userId = '<bean:write name="noticeForm" property="noticeId"/>';
    if(userId == ""){userId = '-1';}
    var urlS = "<peis:contextPath/>/system/buildFunctionAction.do?action=getOrg&noticeId="+userId;
    document.getElementById("noticeFunctionFrame").src = urlS;
    if($("#validdate").val().length < 10){
      var myDate = new Date(); myDate.addDays(5);  
      var StaticTime = myDate.Format("yyyy-MM-dd"); 
      $("#validdate").val(StaticTime);
    }
});

function setDate(){  
  var myDate = new Date(); //myDate.addDays(5);  
  var StaticTime = myDate.Format("yyyy-MM-dd"); 
  return StaticTime;
}

function submitDisposal() {
    var wmf = window.frames.noticeFunctionFrame;
    var sNoticeFunctionID = "";
    if(wmf.bTreeviewLoaded) {
        sNoticeFunctionID = wmf.getSelectedString2();
        document.all.functionID.value=sNoticeFunctionID;
        if(sNoticeFunctionID=="" && $("#orgId").val() == "20"){
            alert(errorFbfw);
            return false;
        }else if(sNoticeFunctionID=="" && $("#orgId").val() == "10"){
          sNoticeFunctionID = <%=session.getAttribute("SE_SYS_ORG_NO")%>;
        }
    }

    if(sNoticeFunctionID == ""){
       
      alert("请选择发布范围");
      return false;
    }else{
      $("#sNoticeFunctionID").val(sNoticeFunctionID);
    }
    if($("#noticeSubj").val() == "" ){
      alert("请填写标题");
      return false;
    }
    if(document.noticeForm.content.value == "" ){
      alert("请填写内容");
      return false;
    }
    if($("#validdate").val() < setDate()){
      alert("有效日期小于当前日期");
      return false;
    }   
    return true;
}

function fresh(){
    parent.subContent.location.href =contextPath+'/noticeAction.do?action=init';
}

function methodname(i){
  $("#orgId").val(i);
}

</script>
</head>
<body>
<html:form action="/noticeAddAction" onsubmit="return submitDisposal();" method="post" target="hideframe">
<input type="hidden" name="action" value="saveOrUpdate"/>
<div id="body">
<div id="main">
  <div id="tool">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <html:hidden property="noticeId"/>
        <html:hidden property="publisher"/>
        <html:hidden property="publishDate"/>
        <peis:text type="hidden" name="orgId"/>
      <tr>
        <td width="15%"><bean:message bundle="system" key="notice.ggzt"/>:<font color="#ff0000">*</font></td>
        <td><peis:text name="noticeSubj"/></td>
      </tr>
      <tr>
        <td width="15%"><bean:message bundle="system" key="notice.ggnr"/>:<font color="#ff0000">*</font></td>
        <td><html:textarea property="content" cols="49" rows="5"></html:textarea></td>
      </tr>
      <tr>
        <td width="15%"><bean:message bundle="system" key="notice.yxsj"/>:<font color="#ff0000">*</font></td>
        <td width="120" class="dom_date">
          <peis:text name="validdate" value="" extendProperty='onfocus="peisDatePicker()" readonly="readonly"'/>
        </td>
      </tr>
    </table>
   </div>
    <div class="content">
     <div id="cont_1">
      <div class="target4">
        <ul>
          <li class="target_on">&nbsp;<bean:message bundle="system" key="notice.fbfw"/>&nbsp;</a></li>
          <li class="clear"></li>
        </ul>
      </div>
      <div class="graphContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 208));">
        <input type="hidden" name="functionID"/>
        <iframe name="noticeFunctionFrame" src="" scrolling="auto" width="100%" height="100%" frameborder="1"></iframe>
      </div>      
      <div class="opbutton1" style="text-align:center">
        <html:submit styleClass="input1"><bean:message bundle="system" key="button.qd"/></html:submit>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                
      </div>
    </div>
  </div>
</div>
</div>
</html:form>
<iframe name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"></iframe>
</body>
</html>
=======
<%@ page contentType="text/html; charset=utf-8" %>




<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=gb2312">
<title><bean:message bundle="system" key="notice.edit.title"/></title>
<link href="<peis:contextPath/>/css/mainframe.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.xml2json.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/FusionCharts/FusionCharts.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/json2htmlex.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script> 
<script type="text/javascript" src="<peis:contextPath/>/js/elecmon/elecmon.js"></script>
<script type="text/javascript" language="javascript">
var contextPath = "<peis:contextPath/>";
var errorFbfw='<bean:message bundle="system" key="notice.errorFbfw"/>';

$(document).ready(function(){    
    var userId = '<bean:write name="noticeForm" property="noticeId"/>';
    if(userId == ""){userId = '-1';}
    var urlS = "<peis:contextPath/>/system/buildFunctionAction.do?action=getOrg&noticeId="+userId;
    document.getElementById("noticeFunctionFrame").src = urlS;
    if($("#validdate").val().length < 10){
      var myDate = new Date(); myDate.addDays(5);  
      var StaticTime = myDate.Format("yyyy-MM-dd"); 
      $("#validdate").val(StaticTime);
    }
});

function setDate(){  
  var myDate = new Date(); //myDate.addDays(5);  
  var StaticTime = myDate.Format("yyyy-MM-dd"); 
  return StaticTime;
}

function submitDisposal() {
    var wmf = window.frames.noticeFunctionFrame;
    var sorg_no = '<%=session.getAttribute("SE_SYS_ORG_NO")%>' ;
    var sNoticeFunctionID = "\'" + sorg_no +"\',";
    if(wmf.bTreeviewLoaded) {
        sNoticeFunctionID += wmf.getSelectedString2();
        document.all.functionID.value=sNoticeFunctionID;
        if(sNoticeFunctionID=="" && $("#orgId").val() == "20"){
            alert(errorFbfw);
            return false;
        }else if(sNoticeFunctionID=="" && $("#orgId").val() == "10"){
          sNoticeFunctionID = <%=session.getAttribute("SE_SYS_ORG_NO")%>;
        }
    }

    if(sNoticeFunctionID == ""){
       
      alert("请选择发布范围");
      return false;
    }else{
      $("#sNoticeFunctionID").val(sNoticeFunctionID);
    }
    if($("#noticeSubj").val() == "" ){
      alert("请填写标题");
      return false;
    }
    if(document.noticeForm.content.value == "" ){
      alert("请填写内容");
      return false;
    }
    if($("#validdate").val() < setDate()){
      alert("有效日期小于当前日期");
      return false;
    }   
    return true;
}

function fresh(){
    parent.subContent.location.href =contextPath+'/noticeAction.do?action=init';
}

function methodname(i){
  $("#orgId").val(i);
}

</script>
</head>
<body>
<html:form action="/noticeAddAction" onsubmit="return submitDisposal();" method="post" target="hideframe">
<input type="hidden" name="action" value="saveOrUpdate"/>
<div id="body">
<div id="main">
  <div id="tool">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <html:hidden property="noticeId"/>
        <html:hidden property="publisher"/>
        <html:hidden property="publishDate"/>
        <peis:text type="hidden" name="orgId"/>
      <tr>
        <td width="15%"><bean:message bundle="system" key="notice.ggzt"/>:<font color="#ff0000">*</font></td>
        <td><peis:text name="noticeSubj"/></td>
      </tr>
      <tr>
        <td width="15%"><bean:message bundle="system" key="notice.ggnr"/>:<font color="#ff0000">*</font></td>
        <td><html:textarea property="content" cols="49" rows="5"></html:textarea></td>
      </tr>
      <tr>
        <td width="15%"><bean:message bundle="system" key="notice.yxsj"/>:<font color="#ff0000">*</font></td>
        <td width="120" class="dom_date">
          <peis:text name="validdate" value="" extendProperty='onfocus="peisDatePicker()" readonly="readonly"'/>
        </td>
      </tr>
    </table>
   </div>
    <div class="content">
     <div id="cont_1">
      <div class="target4">
        <ul>
          <li class="target_on">&nbsp;<bean:message bundle="system" key="notice.fbfw"/>&nbsp;</a></li>
          <li class="clear"></li>
        </ul>
      </div>
      <div class="graphContainer" style="height:expression(((document.documentElement.clientHeight||document.body.clientHeight) - 208));">
        <input type="hidden" name="functionID"/>
        <iframe name="noticeFunctionFrame" src="" scrolling="auto" width="100%" height="100%" frameborder="1"></iframe>
      </div>      
      <div class="opbutton1" style="text-align:center">
        <html:submit styleClass="input1"><bean:message bundle="system" key="button.qd"/></html:submit>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                
      </div>
    </div>
  </div>
</div>
</div>
</html:form>
<iframe name="hideframe" src="" scrolling="no" width="0" height="0" frameborder="0"></iframe>
</body>
</html>
>>>>>>> .r837
