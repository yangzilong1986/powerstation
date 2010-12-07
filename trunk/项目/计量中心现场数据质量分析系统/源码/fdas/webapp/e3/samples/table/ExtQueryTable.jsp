<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ include file="/e3/commons/Common.jsp"%>
<%@ taglib prefix="e3t" uri="/e3/table/E3Table.tld" %>
<%@ page contentType="text/html; charset=utf-8"%>

<HTML>
<HEAD>
<style type="text/css">
	.x-selectable, .x-selectable * {
		-moz-user-select: text!important;
		-khtml-user-select: text!important;
	}
</style>

<META http-equiv=Content-Type content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="<c:url value='/e3/commons/ext/resources/css/ext-all.css'/>" />
    <script type="text/javascript" src="<c:url value='/e3/commons/ext/adapter/ext/ext-base.js'/>"></script>
 	<script type="text/javascript" src="<c:url value='/e3/commons/ext/ext-all.js'/>"></script>

<script>


function orgTableE3ConfigHandler(pConfig){
  pConfig.emptyReload = false;
  //参数form,pConfig指定form的参数会提交到后台 
  pConfig.form = "paramsForm";
  pConfig.showLoadingMsg = true;
}
function orgTableConfigHandler(pConfig){
    pConfig.autoHeight = false;
    pConfig.autoWidth = false;
pConfig.viewConfig = {
		templates: {
			cell: new Ext.Template(
				'<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style="{style}" tabIndex="0" {cellAttr}>',
				'<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>',
				'</td>'
			)
		}
	};
    
}




</script>

</HEAD>
<BODY >

<c:url var="uri" value="/servlet/tableServlet?_actionType=showQueryTable" />

<!-- 这是参数form,这里的参数会提交到后台 -->
 <div id="queryDiv"> 
<form  id="paramsForm"   method="post" > 
  机构名称<input type="text" name="name" />
        <!-- 执行查询操作,只需要调用xxxQuery函数即可. xxx是table id -->
        <input type="button" value="查询" onclick="orgTableQuery()"/>
</form>
</div>


<e3t:table id="orgTable"
           items="orgs"
           caption="机构管理"
           uri="${uri}"
           skin="E3002"
           subComponent="true"
          >
    <e3t:column property="id"   title="机构ID" />             
    <e3t:column property="parentId" title="父亲机构ID" />                
    <e3t:column property="name"      title="机构名称" />
    <e3t:column property="viewOrder"   title="显示序号" />
</e3t:table>          
</BODY>
</HTML>
<script>

  Ext.onReady(function(){
  
     var viewport = new Ext.Viewport({
            layout:'border',
            items:[
     {
                region :'north',
                title : '查询条件',
                height : 60,
                contentEl : 'queryDiv'
              }
              ,
          orgTableE3Grid     ]
     });
     
  });

</script>

