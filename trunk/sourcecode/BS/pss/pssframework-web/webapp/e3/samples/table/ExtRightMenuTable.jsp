<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ include file="/e3/commons/Common.jsp"%>
<%@ taglib prefix="e3t" uri="/e3/table/E3Table.tld" %>
<%@ page contentType="text/html; charset=utf-8"%>

<HTML>
<HEAD>
<!-- 换行样式  -->
<style>
.x-grid3-cell-text-visible .x-grid3-cell-inner{overflow:visible;;white-space:normal;}
</style>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<script>


function userTableE3ConfigHandler(pConfig){
  pConfig.emptyReload = true;
}

function userTableConfigHandler(pConfig){
    pConfig.autoHeight = false;
    pConfig.autoWidth = false;
}

//表格显示前,通常在这注册单击，双击事件
function userTableRenderBeforeHandler(pGrid){
//换行显示
pGrid.store.on('load', function() {
    pGrid.getEl().select("table[class=x-grid3-row-table]").each(function(x) {
        x.addClass('x-grid3-cell-text-visible');
    });
});
var rightClick = new Ext.menu.Menu( {
                id : 'rightClickCont',
                items : [ 
                {
                    id:'addUser',
                    text : '新增用户',
                    handler : function(){
                      alert('跳转到新增用户页面');
                    }
                    
                },
                {
                    id:'editUser',
                    text : '修改用户',
                    handler : function(){
                      alert('跳转到修改用户页面');
                    }
                    
                },                  
                {
                    id:'delUser',
                    text : '删除用户',
                    handler : function(){
                      alert('用户已被删除....');
                    }
                },
                {
                    id:'viewUser',
                    text : '查看用户',
                    handler : function(){
                      alert('跳转到查看用户页面');
                    }
                }
                
                ]
            });

   pGrid.on("rowcontextmenu", function( pGrid, pRowIndex, pEventObj){
      pEventObj.preventDefault();
     rightClick.showAt(pEventObj.getXY());
   });
   
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

</script>

</HEAD>
<BODY >

 

<c:url var="uri" value="/servlet/tableServlet?_actionType=showExtRightMenuTable" />

<e3t:table id="userTable"
           var="user"
           items="users"
           caption="用户管理"
           uri="${uri}"
           skin="E3002"
           height="200"
           subComponent="true"
         >
    <e3t:column property="userID" align="right"  title="用户ID" sortName="USER" />          
    <e3t:column property="userName"  title="用户名称" >
       ${user.userName}ddddd
        ddddddddddddddddddddd 
    </e3t:column>            
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
            userTableE3Grid
            ]
   });
  });
</script>
