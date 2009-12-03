/*    */ package com.hisun.server.manage.servlet;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.jms.bean.HiASyncProcess;
/*    */ import com.hisun.util.HiServiceLocator;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiMDBManagerServlet extends HttpServlet
/*    */ {
/*    */   String[] mdbArr;
/*    */ 
/*    */   public HiMDBManagerServlet()
/*    */   {
/* 22 */     this.mdbArr = null; }
/*    */ 
/*    */   public void init(ServletConfig config) throws ServletException {
/* 25 */     String mdb_list = config.getInitParameter("list");
/* 26 */     this.mdbArr = StringUtils.split(mdb_list, "|");
/*    */     try
/*    */     {
/* 30 */       start();
/*    */     }
/*    */     catch (HiException e)
/*    */     {
/* 34 */       throw new ServletException(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
/*    */   {
/* 40 */     doGet(request, response);
/*    */   }
/*    */ 
/*    */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*    */     throws ServletException, IOException
/*    */   {
/*    */     try
/*    */     {
/* 48 */       if (StringUtils.equalsIgnoreCase(request.getParameter("CMD"), "start"))
/*    */       {
/* 50 */         start();
/*    */       }
/* 52 */       else if (StringUtils.equalsIgnoreCase(request.getParameter("CMD"), "stop"))
/*    */       {
/* 54 */         stop();
/*    */       }
/*    */     }
/*    */     catch (HiException e) {
/* 58 */       throw new ServletException(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   private void start()
/*    */     throws HiException
/*    */   {
/* 65 */     if (this.mdbArr == null) {
/*    */       return;
/*    */     }
/*    */ 
/* 69 */     for (int i = 0; i < this.mdbArr.length; ++i)
/*    */     {
/* 71 */       String serverName = this.mdbArr[i];
/* 72 */       String jndi = "ibs/jms/" + serverName;
/*    */ 
/* 75 */       HiServiceLocator locator = HiServiceLocator.getInstance();
/*    */ 
/* 77 */       Object o = locator.lookup(jndi);
/* 78 */       if (o == null) {
/* 79 */         HiASyncProcess asynProcess = HiASyncProcess.getInstance(serverName);
/* 80 */         locator.bind(jndi, asynProcess);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   private void stop()
/*    */     throws HiException
/*    */   {
/* 88 */     if ((this.mdbArr == null) || 
/* 90 */       (this.mdbArr == null)) {
/*    */       return;
/*    */     }
/*    */ 
/* 94 */     for (int i = 0; i < this.mdbArr.length; ++i)
/*    */     {
/* 96 */       String serverName = this.mdbArr[i];
/* 97 */       String jndi = "ibs/jms/" + serverName;
/*    */ 
/* 100 */       HiServiceLocator locator = HiServiceLocator.getInstance();
/*    */ 
/* 102 */       Object o = locator.lookup(jndi);
/* 103 */       if (o != null) {
/* 104 */         HiASyncProcess asynProcess = (HiASyncProcess)o;
/* 105 */         asynProcess.destory();
/* 106 */         locator.unbind(jndi);
/*    */       }
/*    */     }
/*    */   }
/*    */ }