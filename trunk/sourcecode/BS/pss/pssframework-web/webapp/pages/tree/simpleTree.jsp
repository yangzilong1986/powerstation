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
var selectedNode =  getTreeRoot().getSelected();
if  ( selectedNode ){
 alert(  selectedNode.text );
}
 
}
</script>
</HEAD>
<BODY>
${treeScript}
<script>
</script>
</BODY>
</HTML>
