<%@ page contentType="text/html; charset=utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="e3" uri="/e3/tree/E3Tree.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
</HEAD>
<BODY> 
<%
  java.util.List datas  = new java.util.ArrayList();
  java.util.Map data = new java.util.HashMap();
  data.put("id",new Integer(10));
  data.put("parentId", null );
  data.put("name","总部");
  data.put("viewOrder", new Integer(0) );
  datas.add( data );
  
  java.util.Map data1 = new java.util.HashMap();
  data1.put("id", new Integer(1010) );
  data1.put("parentId", new Integer(10) );
  data1.put("name","子公司1");
  data1.put("viewOrder", new Integer(1) );
  datas.add( data1 );
  
      java.util.Map data2 = new java.util.HashMap();
  data2.put("id", new Integer(1020) );
  data2.put("parentId", new Integer(10) );
  data2.put("name","子公司2");
  data2.put("viewOrder", new Integer(2) );
  datas.add( data2 );  
    
  pageContext.setAttribute("orgs", datas);
  
%>
<e3:tree var="org" items="orgs"   >
  <e3:node id="${org.id}" parentId="${org.parentId}" name="${org.name}"
  />
</e3:tree>
</BODY>
</HTML>


