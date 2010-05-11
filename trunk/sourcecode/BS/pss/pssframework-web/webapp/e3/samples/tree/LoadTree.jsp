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
var selectedNode =  getTreeRoot().getSelected();
if  ( selectedNode ){
 alert(  selectedNode.text );
}
}

function reloadNode(){
var selectedNode =  getTreeRoot().getSelected();
if  ( selectedNode ){
 selectedNode.reload();
}

}
 

</script>
</HEAD>
<BODY> 
<input type="button" value="刷新节点" onclick="reloadNode()"/>
<input type="button" value="选种节点" onclick="showSelectedNode()"/>
<%= request.getAttribute("treeScript")%>

<script>
org001.collapseAll();
</script>

</BODY>
</HTML>


