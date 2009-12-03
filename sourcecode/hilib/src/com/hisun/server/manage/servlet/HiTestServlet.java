/*    */ package com.hisun.server.manage.servlet;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import java.io.PrintWriter;
/*    */ import java.util.Enumeration;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class HiTestServlet extends HttpServlet
/*    */ {
/*    */   public void init(ServletConfig config)
/*    */     throws ServletException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*    */     throws ServletException, IOException
/*    */   {
/* 25 */     doGet(request, response);
/*    */   }
/*    */ 
/*    */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*    */     throws ServletException, IOException
/*    */   {
/*    */     String name;
/* 29 */     Enumeration enum1 = request.getAttributeNames();
/* 30 */     while (enum1.hasMoreElements()) {
/* 31 */       name = (String)enum1.nextElement();
/* 32 */       System.out.println("Attribute:" + name + ":" + request.getAttribute(name));
/*    */     }
/* 34 */     enum1 = request.getParameterNames();
/* 35 */     while (enum1.hasMoreElements()) {
/* 36 */       name = (String)enum1.nextElement();
/* 37 */       System.out.println("Parameter:" + name + ":" + request.getParameter(name));
/*    */     }
/*    */ 
/* 40 */     int num = Integer.parseInt(request.getParameter("arg_num"));
/* 41 */     String[] args = new String[num];
/* 42 */     for (int i = 0; i < num; ++i) {
/* 43 */       args[i] = request.getParameter("arg_" + (i + 1));
/* 44 */       System.out.println("arg_" + (i + 1) + ":" + args[i]);
/*    */     }
/*    */ 
/* 47 */     PrintWriter writer = response.getWriter();
/* 48 */     writer.print("hello1");
/* 49 */     writer.print("hello2");
/* 50 */     writer.print("hello3");
/* 51 */     throw new ServletException("hello world");
/*    */   }
/*    */ }