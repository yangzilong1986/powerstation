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

//tree是树对象名.当然这个名字是可以修改的，调用builder的setTreeID(String)方法
//可以修改
function treeRenderBeforeHandler(pTree){

    pTree.on('click', function(node){ 
     pTree.setTitle("当前节点:" + node.text);
    });  
    
  pTree.on('onChecked', function(node){ 
     alert( "选种" + node.text);
   });      
  pTree.on('onUnchecked', function(node){ 
     alert( "反选" + node.text);
   });      


  
  Ext.get("checkedNodeBtn").on("click",function(){
     var checkedNodes = pTree.getChecked();//获取所有选种节点
     var texts = "";
	 for(var i = 0; i < checkedNodes.length; i++) {
	   var checkNode = checkedNodes[i];
       texts = texts + checkNode.text + "," ;   
       //alert("viewOrder=" + checkNode.attributes.viewOrder);//checkNode.attributes可以取节点扩展属性
       //alert("checked=" + checkNode.attributes.checked);	   
	 }
	 alert(texts);
    
  });
}

 

</script>
</HEAD>
<BODY> 
<input type="button" value="选种节点" id="checkedNodeBtn" />

<%= request.getAttribute("treeScript") %>

</BODY>
</HTML>



