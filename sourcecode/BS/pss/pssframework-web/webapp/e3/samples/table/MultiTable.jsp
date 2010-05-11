<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ include file="/e3/commons/Common.jsp"%>
<%@ taglib prefix="e3t" uri="/e3/table/E3Table.tld" %>
<%@ page contentType="text/html; charset=utf-8"%>

<HTML>
<HEAD>
<style>
  .addIcon{ background-image:url(<c:url value='/e3/samples/table/img/add.gif'/>) !important;}  
  .editIcon{ background-image:url(<c:url value='/e3/samples/table/img/edit.png'/>) !important;}
  .deleteIcon{ background-image:url(<c:url value='/e3/samples/table/img/delete.gif'/>) !important;}
  .viewIcon{ background-image:url(<c:url value='/e3/samples/table/img/view.png'/>) !important;}
  .refreshIcon{ background-image:url(<c:url value='/e3/samples/table/img/refresh.png'/>) !important;}
   
</style>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="<c:url value='/e3/commons/ext/resources/css/ext-all.css'/>" />
    <script type="text/javascript" src="<c:url value='/e3/commons/ext/adapter/ext/ext-base.js'/>"></script>
 	<script type="text/javascript" src="<c:url value='/e3/commons/ext/ext-all.js'/>"></script>
    


<script>
function userTableE3ConfigHandler(pConfig){
  pConfig.emptyReload = true;
  pConfig.timeout = 30000;//30秒
  //控制是否显示loading信息
  pConfig.showLoadingMsg = true;
}
function userTable2E3ConfigHandler(pConfig){
  pConfig.emptyReload = true;
  pConfig.timeout = 30000;//30秒
  //控制是否显示loading信息
  pConfig.showLoadingMsg = true;
}

 

function userTable2ConfigHandler(pConfig){
    pConfig.autoHeight = false;
    pConfig.autoWidth = false;
    pConfig.region = "north";
    //pConfig.loadMask = { msg  :'loading2...' };
}

function userTableConfigHandler(pConfig){
    pConfig.autoHeight = false;
    pConfig.autoWidth = false;
    //pConfig.loadMask = { msg  :'loading...' };
    //当grid不能直接放td里，必须在td里设置个div,否则不正常
    //el属性用于设置grid的div id

pConfig.tbar = [{text:'新增',
               iconCls:'addIcon',
               handler:function(){
 	            //....
	            refresh_userTable();//刷新grid
               }},
               '','-','',
               {text:'修改',
               iconCls:'editIcon',
               handler:function(){
                 var record = userTableE3ExtGrid.getSelectionModel().getSelected();
                if ( record == undefined ){
                 Ext.Msg.alert('系统提示','请选种要修改的记录');
                 return;
                }
 	            var userID = record.get("userID");//获取单元格值
 	            //....
	            refresh_userTable();//刷新grid

               }},
              '','-','',
               {text:'删除',
               iconCls:'deleteIcon',
               handler:function(){
                var record = userTableE3ExtGrid.getSelectionModel().getSelected();
                if ( record == undefined ){
                 Ext.Msg.alert('系统提示','请选种要删除的记录');
                 return;
                }                
 	            var userID = record.get("userID");//获取单元格值
 	            //....
	            refresh_userTable();//刷新grid
               }},
              '','-','',
              {text:'刷新',
               iconCls:'refreshIcon',
               handler:function(){
                 refresh_userTable();
               }
              }];   

  //pConfig.autoExpandColumn='userName';
  //pConfig.el = 'yy';
  //pConfig.height=200;
  //pConfig.width='400';
  //pConfig.bodyStyle='width:100%';
}
//表格显示前,通常在这注册单击，双击事件
function userTableRenderBeforeHandler(pGrid){
 
/*
  pGrid.on("rowclick", function( pGrid, pRowIndex, pEventObj){
     var record = pGrid.getStore().getAt(pRowIndex);   //Get the Record
	 var userID = record.get("userID");//获取单元格值
	 Ext.MessageBox.alert('show','当前选中的数据是'+userID);
  });
  
 
  pGrid.on("rowdblclick", function( pGrid, pRowIndex, pEventObj){
     var record = pGrid.getStore().getAt(pRowIndex);   //Get the Record
	 var userID = record.get("userIDB");//获取单元格值
	 Ext.MessageBox.alert('show','当前选中的数据是'+userID);
  });
  */
 
   
}

function userTableRenderAfterHandler(pGrid){
}


 
  
</script>

</HEAD>
<BODY >
<c:url var="uri" value="/servlet/tableServlet?_actionType=showMultiTable" />
<e3t:table id="userTable"
           var="user"
           items="users"
           uri="${uri}"
           caption="用户管理1"         
           skin="E3002"
           subComponent="true"
         >
    <e3t:param name="a" value="达到" />
    <e3t:param name="dto(a)" value="达到2" />
    <e3t:column property="userID" align="right"  title="用户ID" sortName="USER" />          
    <e3t:column property="userName"  title="用户名称" />
    <e3t:column property="sex"      title="性别"  />
    <e3t:column property="birthday"   hidden="false"   title="生日" width="60" />
</e3t:table>
<e3t:table id="userTable2"
           var="user"
           items="users"
           uri="${uri}"
           caption="用户管理2"         
           skin="E3002"
           subComponent="true"
           height="200"
         >
    <e3t:param name="a" value="达到" />
    <e3t:param name="dto(a)" value="达到2" />
    <e3t:column property="userID" align="right"  title="用户ID" sortName="USER" />          
    <e3t:column property="userName"  title="用户名称" />
    <e3t:column property="sex"      title="性别"  />
    <e3t:column property="birthday"   hidden="false"   title="生日" width="60" />
</e3t:table>


</BODY>
</HTML>
<script>

  Ext.onReady(function(){
  var viewport = new Ext.Viewport({
            layout:'border',
            items:[
            userTableE3Grid,userTable2E3Grid
            ]
   });
  });
  
</script>
