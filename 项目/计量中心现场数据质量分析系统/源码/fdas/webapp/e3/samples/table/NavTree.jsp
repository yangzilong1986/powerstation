<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="e3" uri="/e3/tree/E3Tree.tld" %>
<HTML>
<HEAD>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<script>
<%
  String contextPath = request.getContextPath();
  String path = null;
  if ( "/".equals(contextPath) ){
	  path = "";
  } else {
	  path = contextPath;
  }
%>
function openURL(pURL){
  if ( "goHome" == pURL ){
  top.location.href = "<%=path%>" + "/Index.jsp" ;
}else{
 parent.rightFrame.location.href = pURL;
 }
}

function goHome(){
alert('d');
 
}

</script>
</HEAD>
<BODY> 
<%
  java.util.List datas  = new java.util.ArrayList();
  java.util.Map data = new java.util.HashMap();
  data.put("id","root");
  data.put("parentId", null );
  data.put("name","E3.Table");
  data.put("action", path + "/e3/samples/table/Blank.jsp" );
  datas.add( data );
  
  java.util.Map data111 = new java.util.HashMap();
  data111.put("id","exit");
  data111.put("parentId", "root" );
  data111.put("name","返回主页");
  data111.put("action", "goHome");
  datas.add( data111 );
  
  java.util.Map data112 = new java.util.HashMap();
  data112.put("id","ad");
  data112.put("parentId", "root" );
  data112.put("name","赞助商");
  data112.put("action", path + "/e3/ad/Ad.jsp" );
  datas.add( data112 );
  
  java.util.Map data1 = new java.util.HashMap();
  data1.put("id","std");
  data1.put("parentId", "root" );
  data1.put("name","1:标准");
  data1.put("action", path + "/e3/samples/table/Blank.jsp" );
  datas.add( data1 );
  
  java.util.Map data2 = new java.util.HashMap();
  data2.put("id","simpleTable");
  data2.put("parentId", "std" );
  data2.put("name","1:简单");
  data2.put("action", path + "/servlet/tableServlet?_actionType=showSimpleTable" );
  datas.add( data2 );
  
  java.util.Map data3 = new java.util.HashMap();
  data3.put("id","decorator_simple");
  data3.put("parentId", "std" );
  data3.put("name","2:修饰器");
  data3.put("action", path + "/servlet/tableServlet?_actionType=showDecoratorTable" );
  datas.add( data3 );
  
  java.util.Map data4 = new java.util.HashMap();
  data4.put("id","pageTable");
  data4.put("parentId", "std" );
  data4.put("name","3:分页");
  data4.put("action", path + "/servlet/tableServlet?_actionType=showPageTable" );
  datas.add( data4 );
  
  java.util.Map data5 = new java.util.HashMap();
  data5.put("id","stateTable");
  data5.put("parentId", "std" );
  data5.put("name","4:记录分页状态");
  data5.put("action", path + "/servlet/tableServlet?_actionType=showStateTable&_e3State=true" );
  datas.add( data5 );
  
  java.util.Map data6 = new java.util.HashMap();
  data6.put("id","columnGroup");
  data6.put("parentId", "std" );
  data6.put("name","5:表头分组");
  data6.put("action", path + "/e3/samples/table/JspTable.jsp" );
  datas.add( data6 );
  
  java.util.Map data7 = new java.util.HashMap();
  data7.put("id","noData");
  data7.put("parentId", "std" );
  data7.put("name","6:没有记录表");
  data7.put("action", path + "/e3/samples/table/EmptyTable.jsp" );
  datas.add( data7 );
  
  java.util.Map data71 = new java.util.HashMap();
  data71.put("id","NoTable");
  data71.put("parentId", "std" );
  data71.put("name","7:显示序号");
  data71.put("action", path + "/e3/samples/table/NoTable.jsp" );
  datas.add( data71 );
  
  java.util.Map data72 = new java.util.HashMap();
  data72.put("id","RowStyleTable");
  data72.put("parentId", "std" );
  data72.put("name","8:设置行样式");
  data72.put("action", path + "/e3/samples/table/RowStyleTable.jsp" );
  datas.add( data72 );
  
  java.util.Map data73 = new java.util.HashMap();
  data73.put("id","RowEventTable");
  data73.put("parentId", "std" );
  data73.put("name","9:设置行事件");
  data73.put("action", path + "/e3/samples/table/RowEventTable.jsp" );
  datas.add( data73 );
  
  java.util.Map data74 = new java.util.HashMap();
  data74.put("id","JdbcPageTable");
  data74.put("parentId", "std" );
  data74.put("name","10:JDBC 分页");
  data74.put("action", path + "/servlet/tableServlet?_actionType=showJdbcPageTable" );
  datas.add( data74 );  
  
  java.util.Map data75 = new java.util.HashMap();
  data75.put("id","HbnPageTable");
  data75.put("parentId", "std" );
  data75.put("name","11: Hibernate分页");
  data75.put("action", path + "/servlet/tableServlet?_actionType=showHbnPageTable" );
  datas.add( data75 );
  
  
  java.util.Map data76 = new java.util.HashMap();
  data76.put("id","SelectTable");
  data76.put("parentId", "std" );
  data76.put("name","12: 全选/反选");
  data76.put("action", path + "/servlet/tableServlet?_actionType=showSelectAllTable" );
  datas.add( data76 );
  
  java.util.Map data77 = new java.util.HashMap();
  data77.put("id","ajaxTable");
  data77.put("parentId", "std" );
  data77.put("name","13: ajax表格");
  data77.put("action", path + "/servlet/tableServlet?_actionType=showAjaxTable" );
  datas.add( data77 );  
  
  
  java.util.Map data8 = new java.util.HashMap();
  data8.put("id","skin");
  data8.put("parentId", "std" );
  data8.put("name","皮肤");
  datas.add( data8 );
  
  java.util.Map data9 = new java.util.HashMap();
  data9.put("id","skin002");
  data9.put("parentId", "skin" );
  data9.put("name","valuelist皮肤");
  data9.put("action", path + "/servlet/tableServlet?_actionType=showSkinTable&skin=E3001_001" );
  datas.add( data9 );

  java.util.Map data10 = new java.util.HashMap();
  data10.put("id","skin003");
  data10.put("parentId", "skin" );
  data10.put("name","dispalytag皮肤");
  data10.put("action", path + "/servlet/tableServlet?_actionType=showSkinTable&skin=E3001_002" );
  datas.add( data10 );

  java.util.Map data11 = new java.util.HashMap();
  data11.put("id","skin004");
  data11.put("parentId", "skin" );
  data11.put("name","图片导航条皮肤");
  data11.put("action", path + "/servlet/tableServlet?_actionType=showSkinTable&skin=E3001_003" );
  datas.add( data11 );
  
  
  java.util.Map data12 = new java.util.HashMap();
  data12.put("id","ext");
  data12.put("parentId", "root" );
  data12.put("name","2:ext");
  data12.put("action", path + "/e3/samples/table/Blank.jsp" );
  datas.add( data12 );
  
  java.util.Map data13 = new java.util.HashMap();
  data13.put("id","extsimple");
  data13.put("parentId", "ext" );
  data13.put("name","简单");
  data13.put("action", path + "/servlet/tableServlet?_actionType=showExtTable" );  
  datas.add( data13 );
  
  java.util.Map data14 = new java.util.HashMap();
  data14.put("id","ext14");
  data14.put("parentId", "ext" );
  data14.put("name","传统AJAX处理");
  data14.put("action", path + "/e3/samples/table/ExtTable.jsp" );  
  datas.add( data14 );
  
  java.util.Map data15 = new java.util.HashMap();
  data15.put("id","ext15");
  data15.put("parentId", "ext" );
  data15.put("name","右键菜单");
  data15.put("action", path + "/e3/samples/table/ExtRightMenuTable.jsp" );  
  datas.add( data15 );

  java.util.Map data16 = new java.util.HashMap();
  data16.put("id","ext16");
  data16.put("parentId", "ext" );
  data16.put("name","查询数据");
  data16.put("action", path + "/servlet/tableServlet?_actionType=showQueryTable" );  
  datas.add( data16 );
  
  java.util.Map data17 = new java.util.HashMap();
  data17.put("id","ext17");
  data17.put("parentId", "ext" );
  data17.put("name","导出excel");
  data17.put("action", path + "/servlet/tableServlet?_actionType=showExportTable" );  
  datas.add( data17 );

  

  pageContext.setAttribute("datas", datas);
  
%>


<e3:tree var="data" items="datas" builder="extTree" defaultSort="false">
  <e3:node id="B${data.id}" parentId="B${data.parentId}" name="${data.name}" 
           action="javascript:openURL('${data.action}')" 
  />
</e3:tree>
</BODY>
</HTML>





