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
function treeRenderBeforeHandler(pTree){
    pTree.on('beforerender', function(pTree){ 
    // pTree.setTitle("请选择节点!:d");  
    });

    pTree.on('click', function(node){ 
     pTree.setTitle("当前节点:" + node.text);
    });  
    
    pTree.on('movenode',function(pTree,pNode, pOldParent, pNewParent, pIndex){
      //修改父亲机构ID
      pNode.attributes.newParentOrgID = pNewParent.attributes.orgID;
    });
    
    /*
    加上这个事件就可以控制，只能调整兄弟节点的顺序.
    pTree.on("beforenodedrop",function(pEvent){
      if ( pEvent.point == "append" ){//追加的拖动取消
        return false;
      }
      if ( pEvent.dropNode.parentNode == pEvent.target.parentNode ){//只有兄弟节点可以移动，其他节点之间不能移动.
         return true;
      } else {
         return false;
      }
      
    });
    */ 

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
}

var changedNodes = "";
function saveChanged(){
  var root = tree.getRootNode() ;
  //遍历节点，检查父亲节点被修改过的节点
  visitNode( root );
  //提交变更.
  document.submitForm.parentChangedOrgs.value = changedNodes;
  document.submitForm.submit();//这是个简单的例子，实际应用使用ajax方式提交比较.
  alert("保存成功");
}

function visitNode(pNode){
  var root = tree.getRootNode() ;
  if ( root != pNode ){//因为跟节点不可能移动，所以不需要判断.
     //节点父亲修改过
     if (pNode.attributes.newParentOrgID != pNode.attributes.oldParentOrgID ){
       changedNodes = changedNodes +  pNode.attributes.orgID +  "," + pNode.attributes.newParentOrgID + ",";
     }
   }
 var children =  pNode.childNodes;//获取儿子节点
 for(var i=0; i<children.length; i++){
   var child = children[i];
   visitNode(child);
 }
 
}

</script>
</HEAD>
<BODY> 

<form name="submitForm" method="post" action="<c:url value='/servlet/xtreeServlet?_actionType=saveTree'/>" >
   <input type="hidden" name="parentChangedOrgs" />
</form>
<input type="button" value="选种节点" onclick="showSelectedNode()"/>
<input type="button" value="保存修改" onclick="saveChanged()"/>

<%= request.getAttribute("treeScript") %>
</BODY>
</HTML>



