/*     */ package com.hisun.web.service;
/*     */ 
/*     */ import com.hisun.common.HiETF2HashMapList;
/*     */ import com.hisun.common.HiForm2Etf;
/*     */ import com.hisun.common.HiSecurityFilter;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.register.HiRegisterService;
/*     */ import com.hisun.register.HiServiceObject;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import net.sf.json.JSONObject;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiWebServiceImpl extends HiAbstractChannelService
/*     */   implements IChannelService
/*     */ {
/*     */   public void execute(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  41 */     HiETF requestEtf = HiETFFactory.createETF();
/*  42 */     HiForm2Etf form2Etf = new HiForm2Etf(this._dataConvert);
/*     */     try {
/*  44 */       form2Etf.process(request, requestEtf);
/*     */     } catch (Exception e) {
/*  46 */       throw new ServletException(e);
/*     */     }
/*  48 */     if (this._log.isInfoEnabled()) {
/*  49 */       this._log.info("REQ ETF: [" + requestEtf + "]");
/*     */     }
/*     */ 
/*  54 */     String service = getService(request.getRequestURL().toString());
/*  55 */     if (service == null) {
/*  56 */       throw new ServletException("service can't empty");
/*     */     }
/*     */ 
/*  59 */     if (this._log.isInfoEnabled())
/*  60 */       this._log.info("service: [" + service + "]");
/*     */     try
/*     */     {
/*  63 */       if ((this._securityFilter != null) && (!(this._securityFilter.contains(service)))) {
/*  64 */         this._log.info("service: [" + service + "] deny");
/*  65 */         request.getRequestDispatcher(this._redirectURL).forward(request, response);
/*     */ 
/*  67 */         return;
/*     */       }
/*     */     } catch (HiException e) {
/*  70 */       throw new ServletException("load security file failure", e);
/*     */     }
/*     */ 
/*  73 */     String serverName = this._serverName;
/*     */     try {
/*  75 */       HiServiceObject serviceObject = HiRegisterService.getService(service);
/*     */ 
/*  77 */       serverName = serviceObject.getServerName();
/*     */     }
/*     */     catch (HiException e) {
/*     */     }
/*  81 */     HiMessage msg = new HiMessage(serverName, this._msgType);
/*     */ 
/*  83 */     msg.setHeadItem("SCH", "rq");
/*  84 */     msg.setHeadItem("STM", new Long(System.currentTimeMillis()));
/*  85 */     msg.setHeadItem("STC", service);
/*  86 */     msg.setHeadItem("SDT", serverName);
/*  87 */     msg.setHeadItem("ECT", "text/etf");
/*     */ 
/*  89 */     msg.setBody(requestEtf);
/*     */ 
/*  94 */     bindsRqData(msg, request, response);
/*     */     try {
/*  96 */       msg = this._serviceBean.invoke(msg);
/*     */     } catch (HiException e) {
/*  98 */       e.printStackTrace();
/*  99 */       throw new ServletException(e);
/*     */     }
/* 101 */     HiETF rspetf = msg.getETFBody();
/* 102 */     if (this._log.isInfoEnabled()) {
/* 103 */       this._log.info("RSP ETF: [" + rspetf + "]");
/*     */     }
/*     */ 
/* 106 */     request.setAttribute("ETF", new HiETF2HashMapList(rspetf).map());
/*     */ 
/* 108 */     String output = msg.getHeadItem("SEND_OUTPUT");
/* 109 */     if (StringUtils.isBlank(output))
/*     */     {
/* 111 */       output = msg.getHeadItem("WSO");
/*     */     }
/* 113 */     if (StringUtils.isNotBlank(output)) {
/* 114 */       if (this._log.isInfoEnabled()) {
/* 115 */         this._log.info("forward PAGE:  [" + output + "]");
/*     */       }
/* 117 */       request.getRequestDispatcher(output).forward(request, response);
/*     */     } else {
/* 119 */       output = msg.getHeadItem("SEND_REDIRECT");
/* 120 */       if (StringUtils.isBlank(output))
/*     */       {
/* 122 */         output = msg.getHeadItem("WSR");
/*     */       }
/* 124 */       if (StringUtils.isNotBlank(output)) {
/* 125 */         if (this._log.isInfoEnabled()) {
/* 126 */           this._log.info("sendRedirect PAGE: :  [" + output + "]");
/*     */         }
/* 128 */         response.sendRedirect(output);
/*     */       } else {
/* 130 */         if (StringUtils.isNotBlank(this._encoding)) {
/* 131 */           response.setCharacterEncoding(this._encoding);
/* 132 */           response.setContentType("text/html;charset=" + this._encoding);
/*     */         } else {
/* 134 */           response.setCharacterEncoding("GBK");
/* 135 */           response.setContentType("text/html;charset=GBK");
/*     */         }
/*     */ 
/* 139 */         if ((response.isCommitted()) || 
/* 140 */           (request.getAttribute("ETF") == null)) return;
/* 141 */         response.getWriter().print(JSONObject.fromObject(request.getAttribute("ETF")));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected String getService(String url)
/*     */   {
/* 151 */     int idx1 = url.lastIndexOf("/");
/* 152 */     int idx2 = url.lastIndexOf(".dow");
/* 153 */     if (idx1 == -1) {
/* 154 */       return null;
/*     */     }
/* 156 */     if (idx2 != -1) {
/* 157 */       return url.substring(idx1 + 1, idx2);
/*     */     }
/* 159 */     return url.substring(idx1 + 1);
/*     */   }
/*     */ }