<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ include file="/e3/commons/Common.jsp"%>
<%@ taglib prefix="e3t" uri="/e3/table/E3Table.tld" %>
<%@ page contentType="text/html; charset=utf-8"%>
<HTML>
<HEAD>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
</HEAD>
<BODY> 

<c:url var="uri" value="/servlet/tableServlet?_actionType=showStateTable"/>
<e3t:table id="userTable"
           var="user"
           varStatus="userStatus"
           items="users"
           caption="用户管理"
           uri="${uri}"
          >
    <e3t:param name="_e3State" value="true"/>      
    <e3t:column property="userID"   title="用户ID" />          
    <e3t:column property="userName" title="用户名称" />            
    <e3t:column property="sex"      title="性别" >                
             <c:choose>
               <c:when test="${user.sex == 1}">
                   男
               </c:when>
               <c:when test="${user.sex == 0}">
                   女
               </c:when>
               <c:otherwise>
                   没填
               </c:otherwise>
             </c:choose>  
    </e3t:column>
    <e3t:column property="birthday"      title="生日" >                    
       <fmt:formatDate value="${user.birthday}" pattern="yyyy年MM月dd日"/>
    </e3t:column>
    <e3t:column property="remark"   title="备注" />
    
</e3t:table>          

</BODY>
</HTML>
