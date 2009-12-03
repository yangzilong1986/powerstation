/*     */ package com.hisun.web.service;
/*     */ 
/*     */ import com.hisun.common.HiSecurityFilter;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.web.filter.HiDataConvert;
/*     */ import java.io.IOException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.beanutils.BeanUtils;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public abstract class HiAbstractChannelService
/*     */ {
/*     */   protected IInvokeService _serviceBean;
/*     */   protected HiDataConvert _dataConvert;
/*     */   protected String _msgType;
/*     */   protected String _serverName;
/*     */   protected Logger _log;
/*     */   protected ServletConfig _config;
/*     */   protected HiSecurityFilter _securityFilter;
/*     */   protected String _redirectURL;
/*     */   protected String _encoding;
/*     */ 
/*     */   public HiAbstractChannelService()
/*     */   {
/*  39 */     this._dataConvert = new HiDataConvert();
/*  40 */     this._msgType = "PLTIN0";
/*  41 */     this._serverName = "CAPPWEB1";
/*  42 */     this._log = HiLog.getLogger("SYS.trc");
/*  43 */     this._config = null;
/*  44 */     this._securityFilter = null;
/*  45 */     this._redirectURL = null;
/*  46 */     this._encoding = null;
/*     */   }
/*     */ 
/*     */   public abstract void execute(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
/*     */     throws ServletException, IOException;
/*     */ 
/*     */   protected void bindsRqData(HiMessage msg, HttpServletRequest request, HttpServletResponse response)
/*     */   {
/*  54 */     msg.setHeadItem("_WEB_REQUEST", request);
/*  55 */     msg.setHeadItem("_WEB_RESPONSE", response);
/*  56 */     msg.setHeadItem("_WEB_SESSION", request.getSession());
/*  57 */     msg.setHeadItem("_WEB_APPLICATION", this._config.getServletContext());
/*     */ 
/*  59 */     HttpSession session = request.getSession();
/*  60 */     Enumeration en = session.getAttributeNames();
/*  61 */     HiETF root = msg.getETFBody();
/*  62 */     HiETF sessionRoot = null;
/*  63 */     while (en.hasMoreElements()) {
/*  64 */       String name = (String)en.nextElement();
/*  65 */       Object value = session.getAttribute(name);
/*  66 */       if (value instanceof HashMap) {
/*  67 */         HashMap valueMap = (HashMap)value;
/*  68 */         this._log.debug("[" + name + "][" + valueMap + "]");
/*  69 */         Iterator iter = valueMap.keySet().iterator();
/*  70 */         HiETF group = null;
/*  71 */         if (iter.hasNext()) {
/*  72 */           group = root.addNode(name);
/*     */         }
/*  74 */         while (iter.hasNext()) {
/*  75 */           String name1 = (String)iter.next();
/*  76 */           if (!(valueMap.get(name1) instanceof String)) {
/*     */             continue;
/*     */           }
/*  79 */           String value1 = (String)valueMap.get(name1);
/*  80 */           this._log.debug("[" + name1 + "][" + value1 + "]");
/*  81 */           if (value1 == null) {
/*     */             continue;
/*     */           }
/*  84 */           group.setChildValue(name1, value1);
/*     */         }
/*     */       }
/*     */       else {
/*  88 */         if (value == null) {
/*     */           continue;
/*     */         }
/*  91 */         if (sessionRoot == null) {
/*  92 */           sessionRoot = root.addNode("SESSION");
/*     */         }
/*  94 */         sessionRoot.setChildValue(name, value.toString());
/*     */       }
/*     */     }
/*     */ 
/*  98 */     if (request.getQueryString() == null)
/*  99 */       root.setChildValue("REQ_URL", request.getRequestURL().toString());
/*     */     else {
/* 101 */       root.setChildValue("REQ_URL", request.getRequestURL() + "?" + request.getQueryString());
/*     */     }
/*     */ 
/* 104 */     msg.setHeadItem("SIP", request.getRemoteAddr());
/*     */   }
/*     */ 
/*     */   public void init(ServletConfig config) throws ServletException {
/* 108 */     this._config = config;
/* 109 */     String serviceName = config.getInitParameter("service");
/* 110 */     if (StringUtils.isBlank(serviceName)) {
/* 111 */       throw new ServletException("service is empty");
/*     */     }
/*     */     try
/*     */     {
/* 115 */       Class clazz = Class.forName(serviceName);
/* 116 */       this._serviceBean = ((IInvokeService)clazz.newInstance());
/*     */     } catch (Exception e) {
/* 118 */       throw new ServletException(serviceName, e);
/*     */     }
/* 120 */     String ip = config.getInitParameter("ip");
/* 121 */     if (StringUtils.isNotBlank(ip))
/*     */       try {
/* 123 */         BeanUtils.setProperty(this._serviceBean, "ip", ip);
/*     */       }
/*     */       catch (Exception e1)
/*     */       {
/*     */       }
/* 128 */     String port = config.getInitParameter("port");
/* 129 */     if (StringUtils.isNotBlank(port))
/*     */       try {
/* 131 */         BeanUtils.setProperty(this._serviceBean, "port", port);
/*     */       }
/*     */       catch (Exception e1)
/*     */       {
/*     */       }
/* 136 */     String tmOut = config.getInitParameter("tmOut");
/* 137 */     if (StringUtils.isNotBlank(tmOut)) {
/*     */       try {
/* 139 */         BeanUtils.setProperty(this._serviceBean, "tmOut", tmOut);
/*     */       }
/*     */       catch (Exception e1)
/*     */       {
/*     */       }
/*     */     }
/* 145 */     String logSwitch = config.getInitParameter("logSwitch");
/* 146 */     if (StringUtils.isNotBlank(logSwitch))
/*     */       try {
/* 148 */         BeanUtils.setProperty(this._serviceBean, "logSwitch", logSwitch);
/*     */       }
/*     */       catch (Exception e1)
/*     */       {
/*     */       }
/* 153 */     this._msgType = config.getInitParameter("msgtype");
/* 154 */     if (StringUtils.isBlank(this._msgType)) {
/* 155 */       this._msgType = "PLTIN0";
/*     */     }
/*     */ 
/* 158 */     this._serverName = config.getInitParameter("server");
/* 159 */     if (StringUtils.isBlank(this._serverName)) {
/* 160 */       this._serverName = "CAPPWEB1";
/*     */     }
/*     */ 
/* 163 */     if (config.getInitParameter("encoding") != null) {
/* 164 */       this._encoding = config.getInitParameter("encoding");
/*     */     }
/*     */ 
/* 167 */     Enumeration en = config.getInitParameterNames();
/* 168 */     while (en.hasMoreElements()) {
/* 169 */       String name = (String)en.nextElement();
/* 170 */       if (name.equalsIgnoreCase("service"))
/*     */         continue;
/* 172 */       if (name.equalsIgnoreCase("msgtype")) {
/*     */         continue;
/*     */       }
/* 175 */       String value = config.getInitParameter(name);
/* 176 */       this._dataConvert.add(name.toUpperCase(), value);
/*     */     }
/*     */ 
/* 179 */     String file = config.getInitParameter("securityFile");
/* 180 */     this._log.info(config.getServletName() + " init param:[securityFile=" + file + "]");
/*     */ 
/* 183 */     if (StringUtils.isNotBlank(file)) {
/* 184 */       if (!(file.startsWith("/"))) {
/* 185 */         file = "/" + file;
/*     */       }
/* 187 */       this._redirectURL = config.getInitParameter("redirectURL");
/* 188 */       this._log.info(config.getServletName() + " init param:[redirectURL=" + this._redirectURL + "]");
/*     */ 
/* 190 */       if (StringUtils.isBlank(this._redirectURL))
/* 191 */         throw new ServletException("redirectURL not config");
/*     */       try
/*     */       {
/* 194 */         this._securityFilter = new HiSecurityFilter();
/* 195 */         this._securityFilter.setUrl(config.getServletContext().getResource(file));
/*     */ 
/* 197 */         this._securityFilter.setFile(config.getServletContext().getRealPath(file));
/*     */ 
/* 199 */         this._securityFilter.load();
/*     */       } catch (Exception e) {
/* 201 */         throw new ServletException("load:[" + file + "] failure", e);
/*     */       }
/*     */     }
/*     */   }
/*     */ }