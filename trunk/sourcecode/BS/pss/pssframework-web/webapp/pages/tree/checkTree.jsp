<%@ include file="/commons/taglibs.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<script>

function openURL(pURL){
 parent.rightFrame.location.href = pURL;
}
function showSelectedNode(){
 alert(  getCheckValues() );
}
</script>
</HEAD>
<BODY>
${treeScript}
<script>
var checked = '${checked}';
var checkArray = [];
checkArray = checked.split(",")


 var root = getTreeRoot();// 获取跟节点
 var children = root.childNodes;  

if(checkArray !=''){
for(i in checkArray){
  var o = "FUN_"+checkArray[i]
  var obj = eval(o);
  obj.setChecked(true);
}
}

</script>
</BODY>
</HTML>
