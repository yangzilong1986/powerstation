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
function showSelectedNodes(){
 alert(getCheckValues() );
}

function showSelectedNode(){
 alert( getCheckValue() );
}

</script>
</HEAD>
<BODY> 
<input type="button" value="多选节点值" onclick="showSelectedNodes()"/>
<input type="button" value="单选节点值" onclick="showSelectedNode()"/>
<%= request.getAttribute("treeScript") %>


</BODY>
</HTML>


