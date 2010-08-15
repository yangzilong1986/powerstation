<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../../commons/taglibs.jsp"%>
<%@include file="../../commons/meta.jsp"%>
<HTML>
<HEAD>
<link type="text/css" rel="stylesheet" href='<c:url value="/e3/commons/ext/resources/css/ext-all.css"/>' />
<script src="<c:url value="/e3/commons/ext/adapter/ext/ext-base.js"/>"></script>
<script src="<c:url value="/e3/commons/ext/ext-all.js"/>"></script>
<script>
function openURL(pURL){
 parent.rightFrame.location.href = pURL;
}

function showTg(tdId) {
	 var url = "${ctx}/archive/tginfo/"+tdId;
	  parent.parent.tabscontainermain.showTab("台区档案", url);
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

function refreshParentNode(selectedNode){
  var selectNode = selectedNode;
if(typeof selectedNode == 'undefinded'){
	var selectModel= tree.getSelectionModel();
   selectNode = selectModel.getSelectedNode();
}
 
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
    var url;
    var node;
    var type;
    var uid;
    var msg;
    var rightClick = new Ext.menu.Menu();
        pTree.on('contextmenu',function(node,pEventObj){  
        pEventObj.preventDefault();

        if(node.id.split("_")[0] == 'ORG'){
    
            rightClick.removeAll();
            
        	rightClick = new Ext.menu.Menu({
                id : 'rightClickCont',
                items : [ 
                 { 
                      handler : function(){
                     	  node = selectedNode.id;
                     	  type = node.split("_")[0];
                     	  uid  = node.split("_")[1]; 
                      },
                     id:'newOrg',
                     text:'新增部门',
                     disabled: true
                },
                {
                	  handler : function(){
                    	  node = selectedNode.id;
                    	  type = node.split("_")[0];
                    	  uid  = node.split("_")[1]; 
                    	  url = "${ctx}/archive/tginfo/new?orgInfo.orgId="+uid;
                    	  parent.parent.tabscontainermain.showTab("台区档案", url);
                      },
                      id:'newTg',
                      text:'新增台区'
                 },
                      
                { 
                     handler : function(){
                	  node = selectedNode.id;
                	  type = node.split("_")[0];
                	  uid  = node.split("_")[1]; 
                      },
                    id:'newLine',
                    text:'新增线路',
                    disabled: true
                },
                {
                    id:'edit',
                    text : '修改部门',
                    handler : function(){
                    },
                    disabled: true
                    
                },                  
                {
                    id:'delete',
                    text : '删除部门',
                    handler : function(){
                    },
                    disabled: true
                },
                {
                    id:'view',
                    text : '查看部门',
                    handler : function(){
                    },
                    disabled: true
                }
                ]
            });
        	
        }else if(node.id.split("_")[0] == 'TG'){

        	rightClick.removeAll();
          
        	rightClick = new Ext.menu.Menu({
                id : 'rightClickCont',
                items : [ 

                {
                    id:'edit',
                    text : '修改台区',
                    handler : function(){
                	   node = selectedNode.id;
                	  type = node.split("_")[0];
                	  uid  = node.split("_")[1]; 
          	          url = "${ctx}/archive/tginfo/"+uid+"/edit";
               	      parent.parent.tabscontainermain.showTab("台区档案", url);
                    }
                    
                },                  
                {
                    id:'delete',
                    text : '删除台区',
                    handler : function(){
                    	  node = selectedNode.id;
                    	  type = node.split("_")[0];
                    	  uid  = node.split("_")[1]; 
                      	  deletetginfo(uid);
                      	  refreshParentNode(selectedNode)
                     }
                },
                {
                    id:'view',
                    text : '查看台区',
                    handler : function(){
                    	  node = selectedNode.id;
                    	  type = node.split("_")[0];
                    	  uid  = node.split("_")[1]; 
                 	       url = "${ctx}/archive/tginfo/"+uid;
                    	  parent.parent.tabscontainermain.showTab("台区档案", url);
                       
                    }
                }
                ]
            });
        }
        

        
        rightClick.showAt(pEventObj.getXY());
        
        selectedNode = node;
      });
        
  }

searchNode = function(){
 var name= document.getElementById("node").value;alert(name);
 
}

deletetginfo = function(id){
    var url="${ctx}/archive/tginfo/"+id+'.json?_method=delete';
    if(confirm("确定删除该台区?")){
      jQuery.ajax({
           url: url,
           dataType:'json',
           type:'post',
           cache: false,
           success: function(json){
             var msg=json['msg'];
             var isSucc = json['isSucc'];
              alert(msg);
           },error:function(e){
               alert(e.getMessage());
           }
         });
    }
}

</script>
</HEAD>
<BODY>
<!--
<input type="button" value="选种节点" onclick="showSelectedNode()" />
<input type="button" value="遍历所有节点" onclick="visitAllNodes()" />
<input type="button" value="刷新当前节点" onclick="refreshNode()" />
<input type="button" value="刷新父亲节点" onclick="refreshParentNode()" />
<input type="text" name="node" id="node">
<input type="button" value="查找节点" onclick="searchNode()" />-->
<div id="tree" style="overflow: auto; height: 100%; width: 100%;" /></div>
${leafInfo}
</BODY>
</HTML>
