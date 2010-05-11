<%@ include file="/e3/commons/Common.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv=Content-Type content="text/html; charset=utf-8">

<script>
function showTree1(){
   document.treeFrame.document.location.href="<c:url value="/servlet/xtreeServlet?_actionType=showTree1"/>";
}
function showTree2(){
   document.treeFrame.document.location.href="<c:url value="/servlet/xtreeServlet?_actionType=showTree2"/>";
}

</script>
</HEAD>
<BODY> 
<input id="tree1Btn" type="button" value="第一棵树" onclick="showTree1()"/>
<input  id="tree2Btn" type="button" value="第二棵树" onclick="showTree2()"/>
<iframe name="treeFrame" src="<c:url value="/servlet/xtreeServlet?_actionType=showTree1"/>">
</iframe>
</BODY>
</HTML>



