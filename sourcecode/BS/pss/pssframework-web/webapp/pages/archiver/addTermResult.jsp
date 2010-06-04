<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/taglib.jsp"%>
<%@page contentType="text/html; charset=UTF-8"%>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<link href="<peis:contextPath />/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/object/object_biguserinfomation.js">
</script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/calendar.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/common-ajax.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>

<html:html lang="true">
  <head> 
    <html:base />
    <title></title>
  </head>
<script type="text/javascript" language="javascript">
var contextPath = '<peis:contextPath/>';
$(document).ready(function(){
   init(${result});
 });
function init(result){
    if(result=="1"){
        alert("<bean:message bundle="archive" key="archive.add.success"/>");
        var params = {
                 "tgId":tgId,
                 "orgNo":orgNo,
                 "termId":termId,
                 "gpId":gpId
                };
        window.location.href=contextPath+"/archive/terminalAction1.do?action=showTerm&"+ jQuery.param(params) + "&r=" + parseInt(Math.random() * 1000);        return false;
    }
    else if(result=="2") {
        alert("该资产编号已经存在");
        var params = {
                 "tgId":tgId,
                 "orgNo":orgNo
               }; 
        window.location.href=contextPath+"/archive/terminalAction1.do?action=addTermInit&tgId="+tgId+"&orgNo="+orgNo+"";
        return false;
    } else if(result=="3") {
        alert("该逻辑地址已经存在");
        var params = {
                 "tgId":tgId,
                 "orgNo":orgNo
               }; 
        window.location.href=contextPath+"/archive/terminalAction1.do?action=addTermInit&tgId="+tgId+"&orgNo="+orgNo+"";
        return false;
    }
    else{
        alert("<bean:message bundle="archive" key="archive.add.failure"/>");
        var params = {
                 "tgId":tgId,
                 "orgNo":orgNo
               }; 
       window.location.href=contextPath+"/archive/terminalAction1.do?action=addTermInit&tgId="+tgId+"&orgNo="+orgNo+"";
        return false;
    }
    //if(result=="4") {
    //  alert("表地址已经存在，不能新增！");
    //  parent.document.getElementsByName("entry")[0].disabled = false;
    //  return false;
    //}
    //if(result=="0"){
//  alert("<<bean:message bundle="archive" key="archive.meter.addfailure"/>");
    //  parent.document.getElementsByName("entry")[0].disabled = false;
    //}
}
</script>
  <body>
     <input type="hidden" name="termId" value="${termId}"/>
     <input type="hidden" name="gpId" value="${gpId}"/>
  </body>
</html:html>
