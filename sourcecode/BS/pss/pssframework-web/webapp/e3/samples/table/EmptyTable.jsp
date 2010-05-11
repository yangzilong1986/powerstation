<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ taglib prefix="e3t" uri="/e3/table/E3Table.tld" %>
<%@ page contentType="text/html; charset=utf-8"%>

<HTML>
<HEAD>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
</HEAD>
<BODY> 

<%
  request.setAttribute("tests", null);
%>
<e3t:table id="testTable"
           items="tests"
           caption="测试"
           noDataTip = "没找到用户哦<a href='http://www.csdn.net'>注册用户</a>!"
          >
    <e3t:group title="基础信息" >          
      <e3t:column property="id"    />          
      <e3t:column property="name"  title="用户名称" />
    </e3t:group>
    
    <e3t:group title="扩展信息">            
      <e3t:column property="email"      title="电子邮件" >
        <e3t:decorator cls="url" />
      </e3t:column>                
      <e3t:column property="status"      title="状态" />                    
      <e3t:column property="description"   title="描述" />
    </e3t:group>
      
</e3t:table>          

</BODY>
</HTML>
