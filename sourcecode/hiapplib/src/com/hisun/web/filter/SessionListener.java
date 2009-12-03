/*    */ package com.hisun.web.filter;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletContextEvent;
/*    */ import javax.servlet.ServletContextListener;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import javax.servlet.http.HttpSessionEvent;
/*    */ import javax.servlet.http.HttpSessionListener;
/*    */ 
/*    */ public class SessionListener
/*    */   implements HttpSessionListener, ServletContextListener
/*    */ {
/*    */   public void sessionCreated(HttpSessionEvent event)
/*    */   {
/* 19 */     ArrayList list = (ArrayList)event.getSession().getServletContext().getAttribute("_SESSION_LIST");
/*    */ 
/* 21 */     list.add(event.getSession());
/*    */   }
/*    */ 
/*    */   public void sessionDestroyed(HttpSessionEvent event)
/*    */   {
/* 26 */     ArrayList list = (ArrayList)event.getSession().getServletContext().getAttribute("_SESSION_LIST");
/*    */ 
/* 28 */     list.remove(event.getSession());
/*    */   }
/*    */ 
/*    */   public void contextDestroyed(ServletContextEvent event)
/*    */   {
/* 34 */     event.getServletContext().removeAttribute("_SESSION_LIST");
/*    */   }
/*    */ 
/*    */   public void contextInitialized(ServletContextEvent event)
/*    */   {
/* 39 */     event.getServletContext().setAttribute("_SESSION_LIST", new ArrayList());
/*    */   }
/*    */ }