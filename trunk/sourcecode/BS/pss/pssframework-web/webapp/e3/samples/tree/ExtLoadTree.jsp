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
      pTree.setTitle("请选择节点!:");
    });

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
}

function refreshNode(){
 var selectModel= tree.getSelectionModel();
 var selectNode = selectModel.getSelectedNode();
 if ( selectNode == null ){
   return;
 }
 selectNode.reload();
}

function refreshParentNode(){
 var selectModel= tree.getSelectionModel();
 var selectNode = selectModel.getSelectedNode();
 if ( selectNode == null ){
   return;
 }
 var parentNode = selectNode.parentNode;
 if ( parentNode == null ){
   return;
 }
 parentNode.reload();
 parentNode.select();

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


function treeRenderBeforeHandler(pTree){
    var selectedNode;//用来记录当前右键选种的书节点  
    var rightClick = new Ext.menu.Menu( {
                  id : 'rightClickCont',
                  items : [ 
                  {
                      id:'addUser',
                      text : '新增机构',
                      handler : function(){
                        alert('跳转到新增机构页面');
                      }
                      
                  },
                  {
                      id:'editUser',
                      text : '修改机构',
                      handler : function(){
                        alert('跳转到修改:[' + selectedNode.text + ']机构的页面');
                      }
                      
                  },                  
                  {
                      id:'delUser',
                      text : '删除机构',
                      handler : function(){
                        alert('机构[' + selectedNode.text + ']已被删除....');
                      }
                  },
                  {
                      id:'viewUser',
                      text : '查看机构',
                      handler : function(){
                        alert('跳转到查看机构[' + selectedNode.text + ']的页面');
                      }
                  }
                  
                  ]
              });
              
       pTree.on('contextmenu',function(node,pEventObj){  
        pEventObj.preventDefault();
        rightClick.showAt(pEventObj.getXY());
        selectedNode = node;
      });
        
  }

searchNode = function(){
 var name= document.getElementById("node").value;alert(name);
 
}

</script>

</HEAD>
<BODY> 
<input type="button" value="选种节点" onclick="showSelectedNode()"/>
<input type="button" value="遍历所有节点" onclick="visitAllNodes()"/>
<input type="button" value="刷新当前节点" onclick="refreshNode()"/>
<input type="button" value="刷新父亲节点" onclick="refreshParentNode()"/>
<input type="text" name="node" id="node">
<input type="button" value="查找节点" onclick="searchNode()"/>
<%= request.getAttribute("treeScript") %>
</BODY>
</HTML>



