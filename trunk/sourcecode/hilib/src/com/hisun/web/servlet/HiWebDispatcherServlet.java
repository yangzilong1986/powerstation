/*    */ package com.hisun.web.servlet;
/*    */ 
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.web.service.HiWebServiceImpl;
/*    */ import com.hisun.web.service.IChannelService;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class HiWebDispatcherServlet extends HttpServlet
/*    */ {
/* 21 */   private Logger _log = HiLog.getLogger("SYS.trc");
/* 22 */   IChannelService channelService = new HiWebServiceImpl();
/*    */ 
/*    */   public void destroy()
/*    */   {
/* 35 */     super.destroy();
/*    */   }
/*    */ 
/*    */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*    */     throws ServletException, IOException
/*    */   {
/* 55 */     doPost(request, response);
/*    */   }
/*    */ 
/*    */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*    */     throws ServletException, IOException
/*    */   {
/*    */     try
/*    */     {
/* 76 */       this._log.info("doPost:[" + request.getRequestURL().toString() + "]");
/* 77 */       this.channelService.execute(request, response);
/*    */     } catch (Throwable t) {
/* 79 */       Logger log = HiLog.getErrorLogger("SYS.log");
/* 80 */       log.error(t, t);
/* 81 */       if (t instanceof ServletException) {
/* 82 */         throw ((ServletException)t);
/*    */       }
/* 84 */       throw new ServletException(t);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void init() throws ServletException
/*    */   {
/* 90 */     this.channelService.init(getServletConfig());
/*    */   }
/*    */ }