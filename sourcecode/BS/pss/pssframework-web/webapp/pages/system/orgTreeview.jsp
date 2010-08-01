<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/peis-tag.tld" prefix="peis" %>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>treeview</title>
<link href="<peis:contextPath/>/style/treeviewStyle.css" rel="stylesheet" type="text/css">
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/treeview/ua.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/treeview/ftiens4.js"></script>
<script type="text/javascript" language="javascript" src="<peis:contextPath/>/js/common/treeview/treeviewEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/jquery.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/jquery.xml2json.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/tableEx.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/component.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/FusionCharts/FusionCharts.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/json2htmlex.js"></script>
<script type="text/javascript" src="<peis:contextPath/>/js/frame/const.js"></script>
<script type="text/javascript" language="javascript">
function selectCheckBox(id) {
    if(document.getElementById(id).checked == true) {
        if(id == "ALL") {
            for(var i = 0; i < checkboxCount; i++) {
                if(document.getElementById(checkboxIDArray[i]).checked == false) {
                    document.getElementById(checkboxIDArray[i]).checked = true;
                }
            }
        }
    }
    else {
        if(id == "ALL") {
            for(var i = 0; i < checkboxCount; i++) {
                if(document.getElementById(checkboxIDArray[i]).checked == true) {
                    document.getElementById(checkboxIDArray[i]).checked = false;
                }
            }
        }
    }
}

function checked()
{ 
  var i = 0;
  var indke = -1;
  var inputs=document.all.tags ("INPUT");
  for(var i=0;i<inputs.length;i++)
  {
    if(inputs[i].type=="checkbox"){
       i++;
    }         
  }
  for(var i=0;i<inputs.length;i++)
  {
    if(inputs[i].type=="checkbox"){
      if(inputs[i].checked==true){
        indke = 1;
      }
    }         
  }
  if(indke == -1){
    for(var i=0;i<inputs.length;i++)
    {
      if(inputs[i].type=="checkbox"){
        inputs[i].checked="CHECKED";
      }         
    }
  }
  setOrgId(i);
} 


function setOrgId(i){
  if(i == 1){
    parent.methodname("10");
  }else{
    parent.methodname("20");
  }
}



</script>
</head>
<body onload="checked();">
<peis:jsTreeview type="1" treeID="roleManage"/>

<script type="text/javascript" language="javascript">
var bTreeviewLoaded = true;
</script>
</body>

</html>
