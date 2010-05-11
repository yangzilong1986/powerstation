<%@ include file="/e3/commons/Common.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
   <title> New Document </title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/e3/tree/ext/resources/css/ext-all.css'/>" /> 
    <script type="text/javascript" src="<c:url value='/e3/tree/ext/adapter/ext/ext-base.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/e3/tree/ext/ext-all.js'/>"></script>
</head>

<body>
<script>
Ext.onReady(function(){ 
          new Ext.Panel({//创建一个面板
                renderTo:'show',//填充到id为show的区域
                title:'我的可折叠菜单',
                 
        autoHeight:true,
           width : 200,
                layout:'accordion',//声明布局类型
                layoutConfig: {
                     animate: true //动画效果启用
                },
                items:[
                {
                         title:'菜单1',
                         contentEl:'tree1'
                 }
                        , 
                   {
                         title:'菜单2',
                          contentEl:'tree2'
                        }
                        ]
          });
});

   </script>
   <table>
   <tr><td>
   <div id='show'>
   </div>
   </td></tr></table>
   
   <div id="tree1">
   dddddddddddddd
   </div>
   <div id="tree2">
   fffffffffffff
   </div>
   
</body>
</html>



