<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ include file="/e3/commons/Common.jsp"%>
<%@ taglib prefix="e3t" uri="/e3/table/E3Table.tld" %>
<%@ page contentType="text/html; charset=utf-8"%>

<HTML>
<HEAD>
<style>
</style>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="<c:url value='/e3/commons/ext/resources/css/ext-all.css'/>" />
    <script type="text/javascript" src="<c:url value='/e3/commons/ext/adapter/ext/ext-base.js'/>"></script>
 	<script type="text/javascript" src="<c:url value='/e3/commons/ext/ext-all.js'/>"></script>

<script>
//导出excel

function orgTableConfigHandler(pConfig){
    pConfig.autoHeight = false;
    pConfig.autoWidth = false;
pConfig.tbar = [
              {text:'导出excel',
               iconCls:'refreshIcon',
               handler:function(){
                  //要导出excel 只需要调用xxxExportExcel()函数就可以
                  //xxx是table的id值
                  orgTableExportExcelWithout("viewOrder");
               }
              }];   
    
}

</script>

</HEAD>
<BODY >

<c:url var="uri" value="/servlet/tableServlet?_actionType=showExportTable" />
<e3t:table id="orgTable"
           items="orgs"
           caption="机构管理"
           uri="${uri}"
           skin="E3002"
           subComponent="true"
          >
    <e3t:column property="id"   title="机构ID" hidden="true" />             
    <e3t:column property="parentId" title="父亲机构ID" hidden="true"/>                
    <e3t:column property="name"      title="机构名称" />
    <e3t:column property="viewOrder"   title="显示序号" hidden="false"/>
</e3t:table>          
</BODY>
</HTML>
<script>
  Ext.onReady(function(){
  var viewport = new Ext.Viewport({
            layout:'border',
            items:[
            orgTableE3Grid
            ]
   });
  });
</script>

