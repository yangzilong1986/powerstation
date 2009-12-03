/*     */ package com.hisun.server.manage.servlet;
/*     */ 
/*     */ import com.hisun.dispatcher.HiRouterOut;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Enumeration;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiInvokeServiceServlet extends HttpServlet
/*     */ {
/*     */   private String _server;
/*     */   private String _msgType;
/*     */   private HiIpCheck _ipCheck;
/*     */ 
/*     */   public HiInvokeServiceServlet()
/*     */   {
/*  32 */     this._ipCheck = new HiIpCheck();
/*     */   }
/*     */ 
/*     */   public void init(ServletConfig config)
/*     */     throws ServletException
/*     */   {
/*  38 */     super.init(config);
/*  39 */     this._server = config.getInitParameter("server");
/*  40 */     this._msgType = config.getInitParameter("msgType");
/*  41 */     String tmp = config.getInitParameter("ipLst");
/*  42 */     if (tmp == null) {
/*  43 */       tmp = getServletContext().getInitParameter("ipLst");
/*     */     }
/*  45 */     this._ipCheck.setIpCheck(tmp);
/*     */   }
/*     */ 
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
/*     */   {
/*  50 */     doGet(request, response);
/*     */   }
/*     */ 
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
/*     */   {
/*  55 */     if (!(this._ipCheck.check(request))) {
/*  56 */       response.getWriter().write("client ip:[" + this._ipCheck.getIpAddr(request) + "] deny");
/*  57 */       return;
/*     */     }
/*     */ 
/*  60 */     String service = null;
/*  61 */     String objSvr = null;
/*  62 */     HiETF etf = HiETFFactory.createETF();
/*  63 */     Enumeration en = request.getParameterNames();
/*  64 */     while (en.hasMoreElements()) {
/*  65 */       String name = (String)en.nextElement();
/*  66 */       String value = request.getParameter(name);
/*  67 */       if (name.equalsIgnoreCase("Service")) {
/*  68 */         service = value;
/*     */       }
/*     */ 
/*  71 */       if (name.equalsIgnoreCase("ObjSvr")) {
/*  72 */         objSvr = value;
/*     */       }
/*     */ 
/*  75 */       etf.setChildValue(name, value);
/*     */     }
/*  77 */     if (service == null) {
/*  78 */       throw new ServletException("service is null");
/*     */     }
/*     */ 
/*  81 */     HiMessage msg = null;
/*  82 */     if (StringUtils.isNotBlank(objSvr))
/*  83 */       msg = new HiMessage(objSvr, this._msgType);
/*     */     else {
/*  85 */       msg = new HiMessage(this._server, this._msgType);
/*     */     }
/*  87 */     msg.setBody(etf);
/*     */ 
/*  89 */     msg.setHeadItem("STC", service);
/*  90 */     if (StringUtils.isNotBlank(objSvr)) {
/*  91 */       msg.setHeadItem("SDT", objSvr);
/*     */     }
/*  93 */     msg.setHeadItem("SCH", "rq");
/*  94 */     long curtime = System.currentTimeMillis();
/*  95 */     msg.setHeadItem("STM", new Long(curtime));
/*  96 */     HiMessageContext ctx = new HiMessageContext();
/*  97 */     ctx.setCurrentMsg(msg);
/*  98 */     HiMessageContext.setCurrentMessageContext(ctx);
/*     */     try
/*     */     {
/* 101 */       msg = HiRouterOut.syncProcess(msg);
/*     */     } catch (HiException e) {
/*     */     }
/*     */     finally {
/* 105 */       HiLog.close(msg);
/*     */     }
/* 107 */     etf = msg.getETFBody();
/*     */   }
/*     */ }