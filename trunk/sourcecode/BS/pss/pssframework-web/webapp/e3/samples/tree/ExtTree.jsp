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

//当节点对象和树对象构造完了、执行render之前，会调用该方法
//所以可以在这做函数注册处理.
function treeConfigHandler(pConfig){
/*
  pConfig.rootVisible = true;
  pConfig.containerScroll = false;
  pConfig.autoScroll = false;
  pConfig.bodyStyle = '';
  pConfig.ddScroll = false;
  */
  
  pConfig.autoHeight = false;
  pConfig.autoWidth = false;
  //pConfig.height = 400;  
}
function treeRenderBeforeHandler(pTree){
    pTree.on('click', function(node){ 
     pTree.setTitle("当前节点:" + node.text);
    });
}


//tree是树对象名.当然这个名字是可以修改的，调用builder的setTreeID(String)方法
//可以修改
function showSelectedNode(){
 var selectModel= tree.getSelectionModel();
 var selectNode = selectModel.getSelectedNode();
 if ( selectNode == null ){
   alert('没有选种任何节点！');
   return;
 }
 alert(selectNode.text + selectNode.id );   
 alert("viewOrder=" + selectNode.attributes.viewOrder);
 alert("id2=" + selectNode.attributes.id);
 alert("leaf=" + selectNode.childNodes.length);  
}

function visitAllNodes(){
  var root = tree.getRootNode() ;
  visitNode( root );
}
function visitNode(pNode){
 alert( pNode.text );
 var children =  pNode.childNodes;//获取儿子节点
 for(var i=0; i<children.length; i++){
   var child = children[i];
   visitNode(child);
 }
 
}

</script>
</HEAD>
<BODY> 
<input type="button" value="选种节点" onclick="showSelectedNode()"/>
<input type="button" value="遍历所有节点" onclick="visitAllNodes()"/>

<%= request.getAttribute("treeScript") %>

</BODY>
</HTML>



