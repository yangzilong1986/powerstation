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



</script>
</HEAD>
<BODY> 
<%= request.getAttribute("treeScript") %>

</BODY>
</HTML>



