/*    */ package com.hisun.web.servlet;
/*    */ 
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.web.service.HiRawServiceImpl;
/*    */ import com.hisun.web.service.IChannelService;
/*    */ import java.io.IOException;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ 
/*    */ public class HiRawDispatcherServlet extends HttpServlet
/*    */ {
/* 22 */   private Logger _log = HiLog.getLogger("SYS.trc");
/* 23 */   IChannelService channelService = new HiRawServiceImpl();
/*    */ 
/*    */   public void destroy()
/*    */   {
/* 36 */     super.destroy();
/*    */   }
/*    */ 
/*    */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*    */     throws ServletException, IOException
/*    */   {
/* 56 */     doPost(request, response);
/*    */   }
/*    */ 
/*    */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*    */     throws ServletException, IOException
/*    */   {
/*    */     try
/*    */     {
/* 77 */       this._log.info("doPost:[" + request.getRequestURL().toString() + "]");
/* 78 */       this.channelService.execute(request, response);
/*    */     } catch (Throwable t) {
/* 80 */       Logger log = HiLog.getErrorLogger("SYS.log");
/* 81 */       log.error(t, t);
/* 82 */       if (t instanceof ServletException) {
/* 83 */         throw ((ServletException)t);
/*    */       }
/* 85 */       throw new ServletException(t);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void init() throws ServletException
/*    */   {
/* 91 */     this.channelService.init(getServletConfig());
/*    */   }
/*    */ }