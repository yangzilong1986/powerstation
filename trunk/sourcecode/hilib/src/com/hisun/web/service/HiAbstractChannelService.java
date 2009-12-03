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
/*  76 */           String value1 = (String)valueMap.get(name1);
/*  77 */           this._log.debug("[" + name1 + "][" + value1 + "]");
/*  78 */           if (value1 == null) {
/*     */             continue;
/*     */           }
/*  81 */           group.setChildValue(name1, value1);
/*     */         }
/*     */       }
/*     */       else {
/*  85 */         if (value == null) {
/*     */           continue;
/*     */         }
/*  88 */         if (sessionRoot == null) {
/*  89 */           sessionRoot = root.addNode("SESSION");
/*     */         }
/*  91 */         sessionRoot.setChildValue(name, value.toString());
/*     */       }
/*     */     }
/*     */ 
/*  95 */     if (request.getQueryString() == null)
/*  96 */       root.setChildValue("REQ_URL", request.getRequestURL().toString());
/*     */     else {
/*  98 */       root.setChildValue("REQ_URL", request.getRequestURL() + "?" + request.getQueryString());
/*     */     }
/*     */ 
/* 101 */     msg.setHeadItem("SIP", request.getRemoteAddr());
/*     */   }
/*     */ 
/*     */   public void init(ServletConfig config) throws ServletException {
/* 105 */     this._config = config;
/* 106 */     String serviceName = config.getInitParameter("service");
/* 107 */     if (StringUtils.isBlank(serviceName)) {
/* 108 */       throw new ServletException("service is empty");
/*     */     }
/*     */     try
/*     */     {
/* 112 */       Class clazz = Class.forName(serviceName);
/* 113 */       this._serviceBean = ((IInvokeService)clazz.newInstance());
/*     */     } catch (Exception e) {
/* 115 */       throw new ServletException(serviceName, e);
/*     */     }
/* 117 */     String ip = config.getInitParameter("ip");
/* 118 */     if (StringUtils.isNotBlank(ip))
/*     */       try {
/* 120 */         BeanUtils.setProperty(this._serviceBean, "ip", ip);
/*     */       }
/*     */       catch (Exception e1)
/*     */       {
/*     */       }
/* 125 */     String port = config.getInitParameter("port");
/* 126 */     if (StringUtils.isNotBlank(port)) {
/*     */       try {
/* 128 */         BeanUtils.setProperty(this._serviceBean, "port", port);
/*     */       }
/*     */       catch (Exception e1)
/*     */       {
/*     */       }
/*     */     }
/* 134 */     String logSwitch = config.getInitParameter("logSwitch");
/* 135 */     if (StringUtils.isNotBlank(logSwitch))
/*     */       try {
/* 137 */         BeanUtils.setProperty(this._serviceBean, "logSwitch", logSwitch);
/*     */       }
/*     */       catch (Exception e1)
/*     */       {
/*     */       }
/* 142 */     this._msgType = config.getInitParameter("msgtype");
/* 143 */     if (StringUtils.isBlank(this._msgType)) {
/* 144 */       this._msgType = "PLTIN0";
/*     */     }
/*     */ 
/* 147 */     this._serverName = config.getInitParameter("server");
/* 148 */     if (StringUtils.isBlank(this._serverName)) {
/* 149 */       this._serverName = "CAPPWEB1";
/*     */     }
/*     */ 
/* 152 */     if (config.getInitParameter("encoding") != null) {
/* 153 */       this._encoding = config.getInitParameter("encoding");
/*     */     }
/*     */ 
/* 156 */     Enumeration en = config.getInitParameterNames();
/* 157 */     while (en.hasMoreElements()) {
/* 158 */       String name = (String)en.nextElement();
/* 159 */       if (name.equalsIgnoreCase("service"))
/*     */         continue;
/* 161 */       if (name.equalsIgnoreCase("msgtype")) {
/*     */         continue;
/*     */       }
/* 164 */       String value = config.getInitParameter(name);
/* 165 */       this._dataConvert.add(name.toUpperCase(), value);
/*     */     }
/*     */ 
/* 168 */     String file = config.getInitParameter("securityFile");
/* 169 */     this._log.info(config.getServletName() + " init param:[securityFile=" + file + "]");
/*     */ 
/* 172 */     if (StringUtils.isNotBlank(file)) {
/* 173 */       if (!(file.startsWith("/"))) {
/* 174 */         file = "/" + file;
/*     */       }
/* 176 */       this._redirectURL = config.getInitParameter("redirectURL");
/* 177 */       this._log.info(config.getServletName() + " init param:[redirectURL=" + this._redirectURL + "]");
/*     */ 
/* 179 */       if (StringUtils.isBlank(this._redirectURL))
/* 180 */         throw new ServletException("redirectURL not config");
/*     */       try
/*     */       {
/* 183 */         this._securityFilter = new HiSecurityFilter();
/* 184 */         this._securityFilter.setUrl(config.getServletContext().getResource(file));
/*     */ 
/* 186 */         this._securityFilter.setFile(config.getServletContext().getRealPath(file));
/*     */ 
/* 188 */         this._securityFilter.load();
/*     */       } catch (Exception e) {
/* 190 */         throw new ServletException("load:[" + file + "] failure", e);
/*     */       }
/*     */     }
/*     */   }
/*     */ }