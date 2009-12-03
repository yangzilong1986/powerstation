/*    */ package com.hisun.dispatcher.servlet;
/*    */ 
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.startup.HiStartup;
/*    */ import com.hisun.util.HiStringManager;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class HiStartupServlet extends HttpServlet
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/* 20 */   private String serverName = null;
/* 21 */   private HiStartup startup = null;
/* 22 */   private static HiStringManager sm = HiStringManager.getManager();
/* 23 */   private static Logger log = HiLog.getErrorLogger("SYS.log");
/*    */ 
/*    */   public void destroy()
/*    */   {
/* 31 */     super.destroy();
/*    */   }
/*    */ 
/*    */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*    */     throws ServletException, IOException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*    */     throws ServletException, IOException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void init()
/*    */     throws ServletException
/*    */   {
/* 48 */     this.serverName = getInitParameter("serverName");
/* 49 */     if (this.serverName == null) {
/* 50 */       System.out.println(sm.getString("HiRouterInBean.initialize00"));
/* 51 */       log.error(sm.getString("HiRouterInBean.initialize00"));
/* 52 */       return;
/*    */     }
/*    */     try
/*    */     {
/* 56 */       this.startup = HiStartup.initialize(this.serverName);
/*    */     } catch (Throwable e) {
/* 58 */       HiLog.logSysError(sm.getString("HiRouterInBean.initialize01", this.serverName), e);
/*    */     }
/*    */   }
/*    */ }