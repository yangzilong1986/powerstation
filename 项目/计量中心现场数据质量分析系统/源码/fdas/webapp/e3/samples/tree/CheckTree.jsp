<%@ include file="/e3/commons/Common.jsp"%>
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
<input type="button" value="选种节点值" onclick="showSelectedNode()"/>
<%= request.getAttribute("treeScript") %>

<script>

 var root = getTreeRoot();// 获取跟节点
 var children = root.childNodes;     
  for(var i=0; i<children.length; i++){   
    var child = children[i];
    child.setChecked(true); 
 }
</script>



</BODY>
</HTML>


