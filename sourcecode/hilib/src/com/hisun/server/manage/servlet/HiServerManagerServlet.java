/*    */ package com.hisun.server.manage.servlet;
/*    */ 
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.util.HiICSProperty;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.io.PrintWriter;
/*    */ import javax.servlet.ServletConfig;
/*    */ import javax.servlet.ServletContext;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.apache.commons.lang.math.NumberUtils;
/*    */ 
/*    */ public class HiServerManagerServlet extends HttpServlet
/*    */ {
/*    */   private String _config;
/*    */   private HiIpCheck _ipCheck;
/*    */ 
/*    */   public HiServerManagerServlet()
/*    */   {
/* 23 */     this._config = null;
/* 24 */     this._ipCheck = new HiIpCheck();
/*    */   }
/*    */ 
/*    */   public void init(ServletConfig config) throws ServletException
/*    */   {
/* 29 */     super.init(config);
/* 30 */     this._config = config.getInitParameter("config");
/* 31 */     String tmp = config.getInitParameter("ipLst");
/* 32 */     if (tmp == null) {
/* 33 */       tmp = getServletContext().getInitParameter("ipLst");
/*    */     }
/* 35 */     this._ipCheck.setIpCheck(tmp);
/*    */   }
/*    */ 
/*    */   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
/*    */   {
/* 40 */     doGet(request, response);
/*    */   }
/*    */ 
/*    */   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
/*    */   {
/* 45 */     if (!(this._ipCheck.check(request))) {
/* 46 */       response.getWriter().write("client ip:[" + this._ipCheck.getIpAddr(request) + "] deny");
/* 47 */       return;
/*    */     }
/*    */ 
/* 50 */     Logger log = HiLog.getErrorLogger("SYS.log");
/*    */     try {
/* 52 */       StringBuffer buffer = new StringBuffer();
/* 53 */       int num = NumberUtils.toInt(request.getParameter("arg_num"));
/* 54 */       String[] args = new String[num + 1];
/* 55 */       for (int i = 0; i < num; ++i) {
/* 56 */         args[(i + 1)] = request.getParameter("arg_" + (i + 1));
/*    */       }
/* 58 */       args[0] = this._config;
/* 59 */       boolean successed = true;
/* 60 */       if ("EJB".equalsIgnoreCase(HiICSProperty.getProperty("framework")))
/* 61 */         successed = HiServerManager.invoke(args, buffer);
/*    */       else {
/* 63 */         successed = HiServerManagerPOJO.invoke(args, buffer);
/*    */       }
/*    */ 
/* 68 */       OutputStream os = response.getOutputStream();
/*    */ 
/* 70 */       if (!(successed)) {
/* 71 */         buffer.append("\n");
/* 72 */         buffer.append("## one and more servers failed! see SYS.log file for complete information");
/* 73 */         buffer.append("\n");
/*    */       }
/* 75 */       os.write(buffer.toString().getBytes());
/*    */     }
/*    */     catch (Throwable t) {
/* 78 */       log.error(t, t);
/* 79 */       throw new ServletException(t.getMessage());
/*    */     }
/*    */   }
/*    */ }