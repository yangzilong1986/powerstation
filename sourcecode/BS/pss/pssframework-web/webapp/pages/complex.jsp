<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ include file="/commons/extjs.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>

<head>
	<%@ include file="/commons/meta.jsp" %>
	<base href="<%=basePath%>">
	<title>Complex Layout</title>
</head>


<head>
  <title>Complex Layout</title>
    
    <style type="text/css">
    html, body {
        font:normal 12px verdana;
        margin:0;
        padding:0;
        border:0 none;
        overflow:hidden;
        height:100%;
    }
    p {
        margin:5px;
    }
    .settings {
        background-image:url(../shared/icons/fam/folder_wrench.png);
    }
    .nav {
        background-image:url(../shared/icons/fam/folder_go.png);
    }
    </style>
  
    <script type="text/javascript">
    Ext.onReady(function(){
    
        // NOTE: This is an example showing simple state management. During development,
        // it is generally best to disable state management as dynamically-generated ids
        // can change across page loads, leading to unpredictable results.  The developer
        // should ensure that stable state ids are set for stateful components in real apps.
        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        
        var viewport = new Ext.Viewport({
            layout: 'border',
            items: [
            // create instance immediately
            new Ext.BoxComponent({
                region: 'north',
                height: 32, // give north and south regions a height
                autoEl: {
                    tag: 'div',
                    html:'<p>north - generally for menus, toolbars and/or advertisements</p>'
                }
            }), {
                region: 'east',
                title: 'East Side',
                collapsible: true,
                split: true,
                width: 225, // give east and west regions a width
                minSize: 175,
                maxSize: 400,
                margins: '0 0 0 0',
                layout: 'fit', // specify layout manager for items
                items:            // this TabPanel is wrapped by another Panel so the title will be applied
                new Ext.TabPanel({
                    border: false, // already wrapped so don't add another border
                    activeTab: 1, // second tab initially active
                    tabPosition: 'bottom',
                    items: [{
                        html: '<p>A TabPanel component can be a region.</p>',
                        title: 'A Tab',
                        autoScroll: true
                    }, new Ext.grid.PropertyGrid({
                        title: 'Property Grid',
                        closable: true,
                        source: {
                            "(name)": "Properties Grid",
                            "grouping": false,
                            "autoFitColumns": true,
                            "productionQuality": false,
                            "created": new Date(Date.parse('10/15/2006')),
                            "tested": false,
                            "version": 0.01,
                            "borderWidth": 1
                        }
                    })]
                })
            }, 
            // in this instance the TabPanel is not wrapped by another panel
            // since no title is needed, this Panel is added directly
            // as a Container
            new Ext.TabPanel({
                region: 'center', // a center region is ALWAYS required for border layout
                deferredRender: false,
                activeTab: 0,     // first tab initially active
				               
                items: [{
                    contentEl: 'center1',
                    title: 'Close Me',
                    closable: true,
                    autoScroll: true
                }, {
                    contentEl: 'center2',
                    title: 'Center Panel',
                    autoScroll: true
                }]
            })
			, {
                region: 'west',
                title: 'West Side',
                collapsible: true,
                split: true,
                width: 225, // give east and west regions a width
                minSize: 175,
                maxSize: 400,
                margins: '0 0 0 0',
                layout: 'fit', // specify layout manager for items
                items:            // this TabPanel is wrapped by another Panel so the title will be applied
                new Ext.TabPanel({
                    border: false, // already wrapped so don't add another border
                    activeTab: 1, // second tab initially active
                    tabPosition: 'bottom',
                    items: [{
                        html: '<p>A TabPanel component can be a region.</p>',
                        title: 'A Tab',
                        autoScroll: true
                    },
					new Ext.Panel(
					{
						id: 'west-panel', // see Ext.getCmp() below
						title: 'West',
						layout: {
							type: 'accordion',
							animate: true
						},
						items:[{
						contentEl: 'west',
						title: 'Navigation',
						border: false,
						iconCls: 'nav' // see the HEAD section for style used
						}, {
							title: 'Settings',
							html: '<p>Some settings in here.</p>',
							border: false,
							iconCls: 'settings'
						}]
					})]
                })
            }
			, {
                region: 'south',
                title: 'South Side',
				id:'south',
				                collapsible: true,
                split: true,
                minSize: 50,
                maxSize: 400

				}
			]});
        // get a reference to the HTML element with id "hideit" and add a click listener to it 
        Ext.get("hideit").on('click', function(){
            // get a reference to the Panel that was created with id = 'west-panel' 
            var w = Ext.getCmp('west-panel');
            // expand or collapse that Panel based on its collapsed property state
            w.collapsed ? w.expand() : w.collapse();
        });
    });



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
</head>
<body>
    <!-- use class="x-hide-display" to prevent a brief flicker of the content -->
    <div id="west" class="x-hide-display">
     <link type='text/css' rel='stylesheet' href='/pss-web/e3/commons/ext/resources/css/ext-all.css' />
<script><!--
if ( typeof(Ext) == "undefined" || typeof(Ext.DomHelper) == "undefined" ){
document.write('<script src="/pss-web/e3/commons/ext/adapter/ext/ext-base.js"></script>');
document.write('<script src="/pss-web/e3/commons/ext/ext-all.js"></script>');
}
--></script>
<style>
</style>
<div id="tree" style="overflow:auto; height:100%;width:100%;" />
<script language='javascript'>
var tree , org001 , root ; 
Ext.onReady(function(){
Ext.SSL_SECURE_URL= '/pss-web/e3/commons/ext/resources/images/default/s.gif';
Ext.BLANK_IMAGE_URL= '/pss-web/e3/commons/ext/resources/images/default/s.gif';
 var initConfig =  {
       autoHeight:false,
       autoWidth:false,
       autoScroll:true,
       animate:false,
       enableDD:true,
       lines:true,
       rootVisible:true,
       title:'请选择节点!',
       border: false,
       containerScroll: true
     };
if ( typeof(treeConfigHandler) == 'function' )
  treeConfigHandler(initConfig);
tree = new Ext.tree.TreePanel( initConfig );
       tree.loader = new Ext.tree.TreeLoader({
          dataUrl: 'temp'
       });
tree.on('beforeload', function(pNode){
    if ( pNode.attributes._e3DataUrl ) 
       tree.loader.dataUrl = pNode.attributes._e3DataUrl;
 });
tree.on('collapsenode', function(pNode){
    if ( pNode.attributes._E3_TREE_NODE_001 ) 
       pNode.ui.getIconEl().src = pNode.attributes._E3_TREE_NODE_001
 });
tree.on('expandnode', function(pNode){
    if ( pNode.attributes._E3_TREE_OPEN_NODE_001 ) 
       pNode.ui.getIconEl().src = pNode.attributes._E3_TREE_OPEN_NODE_001
 });
org001 = new Ext.tree.AsyncTreeNode({
       id:'org001', 
       text:'进创集团',
       href:"",
       hrefTarget:'',
       qtip :'',
       allowDrag:false,
       allowDrop:false,
       _e3DataUrl:'/pss-web/servlet/xtreeServlet?_actionType=loadExtSubOrgs&parentID=001',
              iconCls :'E3-TREE-STYLE-PREFIX1'
     });
root = org001 ; 
tree.setRootNode( root );
if ( typeof(E3TreeExtReadyHandler) == 'function' )
  E3TreeExtReadyHandler(tree, 'tree');
if ( typeof(treeRenderBeforeHandler) == 'function' )
  treeRenderBeforeHandler(tree);
tree.render('tree');
if ( typeof(treeRenderAfterHandler) == 'function' )
  treeRenderAfterHandler(tree);
root.expand();
});
</script>
    </div>
    <div id="center2" class="x-hide-display">
        <a id="hideit" href="#">Toggle the west region</a>
        <p>My closable attribute is set to false so you can't close me. The other center panels can be closed.</p>
        <p>The center panel automatically grows to fit the remaining space in the container that isn't taken up by the border regions.</p>
        <hr>
        <p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed metus nibh, sodales a, porta at, vulputate eget, dui. Pellentesque ut nisl. Maecenas tortor turpis, interdum non, sodales non, iaculis ac, lacus. Vestibulum auctor, tortor quis iaculis malesuada, libero lectus bibendum purus, sit amet tincidunt quam turpis vel lacus. In pellentesque nisl non sem. Suspendisse nunc sem, pretium eget, cursus a, fringilla vel, urna. Aliquam commodo ullamcorper erat. Nullam vel justo in neque porttitor laoreet. Aenean lacus dui, consequat eu, adipiscing eget, nonummy non, nisi. Morbi nunc est, dignissim non, ornare sed, luctus eu, massa. Vivamus eget quam. Vivamus tincidunt diam nec urna. Curabitur velit. Quisque dolor magna, ornare sed, elementum porta, luctus in, leo.</p>
        <p>Donec quis dui. Sed imperdiet. Nunc consequat, est eu sollicitudin gravida, mauris ligula lacinia mauris, eu porta dui nisl in velit. Nam congue, odio id auctor nonummy, augue lectus euismod nunc, in tristique turpis dolor sed urna. Donec sit amet quam eget diam fermentum pharetra. Integer tincidunt arcu ut purus. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nulla blandit malesuada odio. Nam augue. Aenean molestie sapien in mi. Suspendisse tincidunt. Pellentesque tempus dui vitae sapien. Donec aliquam ipsum sit amet pede. Sed scelerisque mi a erat. Curabitur rutrum ullamcorper risus. Maecenas et lorem ut felis dictum viverra. Fusce sem. Donec pharetra nibh sit amet sapien.</p>
        <p>Aenean ut orci sed ligula consectetuer pretium. Aliquam odio. Nam pellentesque enim. Nam tincidunt condimentum nisi. Maecenas convallis luctus ligula. Donec accumsan ornare risus. Vestibulum id magna a nunc posuere laoreet. Integer iaculis leo vitae nibh. Nam vulputate, mauris vitae luctus pharetra, pede neque bibendum tellus, facilisis commodo diam nisi eget lacus. Duis consectetuer pulvinar nisi. Cras interdum ultricies sem. Nullam tristique. Suspendisse elementum purus eu nisl. Nulla facilisi. Phasellus ultricies ullamcorper lorem. Sed euismod ante vitae lacus. Nam nunc leo, congue vehicula, luctus ac, tempus non, ante. Morbi suscipit purus a nulla. Sed eu diam.</p>
        <p>Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Cras imperdiet felis id velit. Ut non quam at sem dictum ullamcorper. Vestibulum pharetra purus sed pede. Aliquam ultrices, nunc in varius mattis, felis justo pretium magna, eget laoreet justo eros id eros. Aliquam elementum diam fringilla nulla. Praesent laoreet sapien vel metus. Cras tempus, sapien condimentum dictum dapibus, lorem augue fringilla orci, ut tincidunt eros nisi eget turpis. Nullam nunc nunc, eleifend et, dictum et, pharetra a, neque. Ut feugiat. Aliquam erat volutpat. Donec pretium odio nec felis. Phasellus sagittis lacus eget sapien. Donec est. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae;</p>
        <p>Vestibulum semper. Nullam non odio. Aliquam quam. Mauris eu lectus non nunc auctor ullamcorper. Sed tincidunt molestie enim. Phasellus lobortis justo sit amet quam. Duis nulla erat, varius a, cursus in, tempor sollicitudin, mauris. Aliquam mi velit, consectetuer mattis, consequat tristique, pulvinar ac, nisl. Aliquam mattis vehicula elit. Proin quis leo sed tellus scelerisque molestie. Quisque luctus. Integer mattis. Donec id augue sed leo aliquam egestas. Quisque in sem. Donec dictum enim in dolor. Praesent non erat. Nulla ultrices vestibulum quam.</p>
        <p>Duis hendrerit, est vel lobortis sagittis, tortor erat scelerisque tortor, sed pellentesque sem enim id metus. Maecenas at pede. Nulla velit libero, dictum at, mattis quis, sagittis vel, ante. Phasellus faucibus rutrum dui. Cras mauris elit, bibendum at, feugiat non, porta id, neque. Nulla et felis nec odio mollis vehicula. Donec elementum tincidunt mauris. Duis vel dui. Fusce iaculis enim ac nulla. In risus.</p>
        <p>Donec gravida. Donec et enim. Morbi sollicitudin, lacus a facilisis pulvinar, odio turpis dapibus elit, in tincidunt turpis felis nec libero. Nam vestibulum tempus ipsum. In hac habitasse platea dictumst. Nulla facilisi. Donec semper ligula. Donec commodo tortor in quam. Etiam massa. Ut tempus ligula eget tellus. Curabitur id velit ut velit varius commodo. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla facilisi. Fusce ornare pellentesque libero. Nunc rhoncus. Suspendisse potenti. Ut consequat, leo eu accumsan vehicula, justo sem lobortis elit, ac sollicitudin ipsum neque nec ante.</p>
        <p>Aliquam elementum mauris id sem. Vivamus varius, est ut nonummy consectetuer, nulla quam bibendum velit, ac gravida nisi felis sit amet urna. Aliquam nec risus. Maecenas lacinia purus ut velit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Suspendisse sit amet dui vitae lacus fermentum sodales. Donec varius dapibus nisl. Praesent at velit id risus convallis bibendum. Aliquam felis nibh, rutrum nec, blandit non, mattis sit amet, magna. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Etiam varius dignissim nibh. Quisque id orci ac ante hendrerit molestie. Aliquam malesuada enim non neque.</p>
    </div>
    <div id="center1" class="x-hide-display">
        <p><b>Done reading me? Close me by clicking the X in the top right corner.</b></p>
        <p>Vestibulum semper. Nullam non odio. Aliquam quam. Mauris eu lectus non nunc auctor ullamcorper. Sed tincidunt molestie enim. Phasellus lobortis justo sit amet quam. Duis nulla erat, varius a, cursus in, tempor sollicitudin, mauris. Aliquam mi velit, consectetuer mattis, consequat tristique, pulvinar ac, nisl. Aliquam mattis vehicula elit. Proin quis leo sed tellus scelerisque molestie. Quisque luctus. Integer mattis. Donec id augue sed leo aliquam egestas. Quisque in sem. Donec dictum enim in dolor. Praesent non erat. Nulla ultrices vestibulum quam.</p>
        <p>Duis hendrerit, est vel lobortis sagittis, tortor erat scelerisque tortor, sed pellentesque sem enim id metus. Maecenas at pede. Nulla velit libero, dictum at, mattis quis, sagittis vel, ante. Phasellus faucibus rutrum dui. Cras mauris elit, bibendum at, feugiat non, porta id, neque. Nulla et felis nec odio mollis vehicula. Donec elementum tincidunt mauris. Duis vel dui. Fusce iaculis enim ac nulla. In risus.</p>
        <p>Donec gravida. Donec et enim. Morbi sollicitudin, lacus a facilisis pulvinar, odio turpis dapibus elit, in tincidunt turpis felis nec libero. Nam vestibulum tempus ipsum. In hac habitasse platea dictumst. Nulla facilisi. Donec semper ligula. Donec commodo tortor in quam. Etiam massa. Ut tempus ligula eget tellus. Curabitur id velit ut velit varius commodo. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla facilisi. Fusce ornare pellentesque libero. Nunc rhoncus. Suspendisse potenti. Ut consequat, leo eu accumsan vehicula, justo sem lobortis elit, ac sollicitudin ipsum neque nec ante.</p>
        <p>Aliquam elementum mauris id sem. Vivamus varius, est ut nonummy consectetuer, nulla quam bibendum velit, ac gravida nisi felis sit amet urna. Aliquam nec risus. Maecenas lacinia purus ut velit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Suspendisse sit amet dui vitae lacus fermentum sodales. Donec varius dapibus nisl. Praesent at velit id risus convallis bibendum. Aliquam felis nibh, rutrum nec, blandit non, mattis sit amet, magna. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Etiam varius dignissim nibh. Quisque id orci ac ante hendrerit molestie. Aliquam malesuada enim non neque.</p>
    </div>
    <div id="props-panel" class="x-hide-display" style="width:200px;height:200px;overflow:hidden;">
    </div>
    <div id="south" class="x-hide-display">
        <p>south - generally for informational stuff, also could be for status bar</p>
    </div>
</body>
</html>