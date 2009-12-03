/*     */ package com.hisun.web.service;
/*     */ 
/*     */ import com.hisun.common.HiSecurityFilter;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.register.HiRegisterService;
/*     */ import com.hisun.register.HiServiceObject;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintWriter;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.SystemUtils;
/*     */ 
/*     */ public class HiRawServiceImpl extends HiAbstractChannelService
/*     */   implements IChannelService
/*     */ {
/*     */   public void execute(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  36 */     InputStream is = request.getInputStream();
/*  37 */     int len1 = request.getContentLength();
/*  38 */     if (len1 == -1) {
/*  39 */       throw new ServletException("invalid request data(length=-1)");
/*     */     }
/*  41 */     byte[] buffer = new byte[request.getContentLength()];
/*  42 */     int len2 = is.read(buffer);
/*     */ 
/*  44 */     StringBuffer tmpbuf = new StringBuffer();
/*  45 */     for (int i = 0; i < buffer.length; ++i) {
/*  46 */       if (i % 8 == 0) {
/*  47 */         tmpbuf.append(SystemUtils.LINE_SEPARATOR);
/*     */       }
/*  49 */       tmpbuf.append(Integer.toHexString(buffer[i] & 0xFF));
/*  50 */       tmpbuf.append(" ");
/*     */     }
/*  52 */     this._log.info("REQ DATA01:[" + tmpbuf.toString());
/*  53 */     this._log.info("REQ DATA02:[" + buffer);
/*     */ 
/*  55 */     if (this._log.isInfoEnabled()) {
/*  56 */       this._log.info("REQ DATA:[" + new String(buffer, 0, len1) + "]");
/*     */     }
/*     */ 
/*  59 */     if (len1 != len2) {
/*  60 */       throw new ServletException(String.format("read inputstream error[%d][%d]", new Object[] { Integer.valueOf(len1), Integer.valueOf(len2) }));
/*     */     }
/*     */ 
/*  64 */     if (StringUtils.isNotBlank(this._encoding)) {
/*  65 */       buffer = new String(buffer, this._encoding).getBytes();
/*     */     }
/*     */ 
/*  68 */     String service = getService(request.getRequestURL().toString());
/*  69 */     if (service == null) {
/*  70 */       throw new ServletException("service can't empty");
/*     */     }
/*     */ 
/*  73 */     if (this._log.isInfoEnabled())
/*  74 */       this._log.info("service: [" + service + "]");
/*     */     try
/*     */     {
/*  77 */       if ((this._securityFilter != null) && (!(this._securityFilter.contains(service)))) {
/*  78 */         this._log.info("service: [" + service + "] deny");
/*  79 */         request.getRequestDispatcher(this._redirectURL).forward(request, response);
/*     */ 
/*  81 */         return;
/*     */       }
/*     */     } catch (HiException e) {
/*  84 */       throw new ServletException("load security file failure", e);
/*     */     }
/*     */     try
/*     */     {
/*  88 */       HiServiceObject serviceObject = HiRegisterService.getService(service);
/*     */ 
/*  90 */       this._serverName = serviceObject.getServerName();
/*     */     }
/*     */     catch (HiException serviceObject)
/*     */     {
/*     */     }
/*  95 */     HiMessage msg = new HiMessage(this._serverName, this._msgType);
/*  96 */     msg.setBody(new HiByteBuffer(buffer));
/*  97 */     if (!(this._serverName.equalsIgnoreCase(service)))
/*  98 */       msg.setHeadItem("STC", service);
/*     */     else {
/* 100 */       msg.setHeadItem("SDT", service);
/*     */     }
/* 102 */     msg.setHeadItem("ECT", "text/plain");
/* 103 */     msg.setHeadItem("SIP", request.getRemoteAddr());
/*     */     try
/*     */     {
/* 106 */       msg = this._serviceBean.invoke(msg);
/*     */     } catch (Throwable e) {
/* 108 */       throw new ServletException(e);
/*     */     }
/*     */ 
/* 111 */     if (this._log.isInfoEnabled()) {
/* 112 */       this._log.info("RSP DATA: [" + ((HiByteBuffer)msg.getBody()) + "]");
/*     */     }
/* 114 */     if (StringUtils.isNotBlank(this._encoding))
/* 115 */       response.setCharacterEncoding(this._encoding);
/*     */     else {
/* 117 */       response.setCharacterEncoding("GBK");
/*     */     }
/* 119 */     response.getWriter().print((HiByteBuffer)msg.getBody());
/*     */   }
/*     */ 
/*     */   protected String getService(String url) {
/* 123 */     int idx1 = url.lastIndexOf("/");
/* 124 */     int idx2 = url.lastIndexOf(".dor");
/* 125 */     if ((idx1 == -1) || (idx2 == -1)) {
/* 126 */       return null;
/*     */     }
/* 128 */     return url.substring(idx1 + 1, idx2);
/*     */   }
/*     */ }