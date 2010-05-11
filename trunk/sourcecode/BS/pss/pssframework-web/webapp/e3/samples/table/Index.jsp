<%@ include file="/e3/commons/Common.jsp"%>
<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML><HEAD>
<META http-equiv=Content-Type content="text/html; charset=utf-8"></HEAD>
    <FRAMESET COLS="200,*">
     <FRAME name="leftFrame"  SRC="<c:url value='/servlet/tableServlet?_actionType=showNavTree'/>">
     <FRAME name="rightFrame" SRC="<c:url value='/e3/samples/tree/Blank.jsp'/>" scrolling="no" >
    </FRAMESET>
</HTML>




