 package com.hisun.server.manage.servlet;
 
 import java.io.IOException;
 import java.io.PrintStream;
 import java.io.PrintWriter;
 import java.util.Enumeration;
 import javax.servlet.ServletConfig;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 public class HiTestServlet extends HttpServlet
 {
   public void init(ServletConfig config)
     throws ServletException
   {
   }
 
   public void doPost(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
   {
     doGet(request, response);
   }
 
   public void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
   {
     String name;
     Enumeration enum1 = request.getAttributeNames();
     while (enum1.hasMoreElements()) {
       name = (String)enum1.nextElement();
       System.out.println("Attribute:" + name + ":" + request.getAttribute(name));
     }
     enum1 = request.getParameterNames();
     while (enum1.hasMoreElements()) {
       name = (String)enum1.nextElement();
       System.out.println("Parameter:" + name + ":" + request.getParameter(name));
     }
 
     int num = Integer.parseInt(request.getParameter("arg_num"));
     String[] args = new String[num];
     for (int i = 0; i < num; ++i) {
       args[i] = request.getParameter("arg_" + (i + 1));
       System.out.println("arg_" + (i + 1) + ":" + args[i]);
     }
 
     PrintWriter writer = response.getWriter();
     writer.print("hello1");
     writer.print("hello2");
     writer.print("hello3");
     throw new ServletException("hello world");
   }
 }