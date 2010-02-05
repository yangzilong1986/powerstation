 package com.hisun.web.filter;

 import javax.servlet.ServletContext;
 import javax.servlet.ServletContextEvent;
 import javax.servlet.ServletContextListener;
 import javax.servlet.http.HttpSession;
 import javax.servlet.http.HttpSessionEvent;
 import javax.servlet.http.HttpSessionListener;
 import java.util.ArrayList;
 
 public class SessionListener
   implements HttpSessionListener, ServletContextListener
 {
   public void sessionCreated(HttpSessionEvent event)
   {
     ArrayList list = (ArrayList)event.getSession().getServletContext().getAttribute("_SESSION_LIST");
 
     list.add(event.getSession());
   }
 
   public void sessionDestroyed(HttpSessionEvent event)
   {
     ArrayList list = (ArrayList)event.getSession().getServletContext().getAttribute("_SESSION_LIST");
 
     list.remove(event.getSession());
   }
 
   public void contextDestroyed(ServletContextEvent event)
   {
     event.getServletContext().removeAttribute("_SESSION_LIST");
   }
 
   public void contextInitialized(ServletContextEvent event)
   {
     event.getServletContext().setAttribute("_SESSION_LIST", new ArrayList());
   }
 }