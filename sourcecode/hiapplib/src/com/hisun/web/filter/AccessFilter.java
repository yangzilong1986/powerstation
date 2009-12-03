/*     */ package com.hisun.web.filter;
/*     */ 
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import javax.servlet.Filter;
/*     */ import javax.servlet.FilterChain;
/*     */ import javax.servlet.FilterConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.SAXReader;
/*     */ 
/*     */ public class AccessFilter
/*     */   implements Filter
/*     */ {
/*     */   protected FilterConfig filterConfig;
/*     */   private String redirectURL;
/*     */   private ArrayList excludeURLList;
/*     */   private ArrayList includeURLList;
/*     */   private String file;
/*     */   private String sessionKey;
/*     */   private long lastModified;
/*     */   private Logger log;
/*     */ 
/*     */   public AccessFilter()
/*     */   {
/*  29 */     this.filterConfig = null;
/*  30 */     this.redirectURL = null;
/*  31 */     this.excludeURLList = new ArrayList();
/*  32 */     this.includeURLList = new ArrayList();
/*  33 */     this.file = "/conf/checkurl.xml";
/*  34 */     this.sessionKey = null;
/*  35 */     this.lastModified = -1L;
/*  36 */     this.log = HiLog.getLogger("SYS.trc");
/*     */   }
/*     */ 
/*     */   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
/*  40 */     HttpServletRequest request = (HttpServletRequest)servletRequest;
/*  41 */     HttpServletResponse response = (HttpServletResponse)servletResponse;
/*     */ 
/*  43 */     HttpSession session = request.getSession();
/*  44 */     if (this.sessionKey == null) {
/*  45 */       filterChain.doFilter(request, response);
/*  46 */       return;
/*     */     }
/*     */ 
/*  49 */     if (isCanLoaded(this.file)) {
/*  50 */       this.excludeURLList.clear();
/*  51 */       this.includeURLList.clear();
/*  52 */       loadFilterURL(this.file, this.excludeURLList, this.includeURLList);
/*     */     }
/*     */ 
/*  55 */     if ((checkRequestURIIntFilterList(request)) && 
/*  56 */       (!(checkSession(session, this.sessionKey)))) {
/*  57 */       response.sendRedirect(request.getContextPath() + this.redirectURL);
/*  58 */       return;
/*     */     }
/*     */ 
/*  61 */     filterChain.doFilter(servletRequest, servletResponse);
/*     */   }
/*     */ 
/*     */   public void destroy() {
/*  65 */     this.excludeURLList.clear();
/*     */   }
/*     */ 
/*     */   private boolean checkSession(HttpSession session, String key) {
/*  69 */     int idx = key.indexOf(46);
/*  70 */     String objNam = null;
/*  71 */     if (idx != -1) {
/*  72 */       objNam = key.substring(idx - 1);
/*  73 */       key = key.substring(idx);
/*     */     }
/*  75 */     if (StringUtils.isNotBlank(objNam)) {
/*  76 */       HashMap map = (HashMap)session.getAttribute(objNam);
/*  77 */       if (map == null) {
/*  78 */         return false;
/*     */       }
/*  80 */       return map.containsKey(key);
/*     */     }
/*  82 */     return (session.getAttribute(key) != null);
/*     */   }
/*     */ 
/*     */   private boolean checkRequestURIIntFilterList(HttpServletRequest request)
/*     */   {
/*     */     String tmp;
/*  87 */     String uri = request.getServletPath() + ((request.getPathInfo() == null) ? "" : request.getPathInfo());
/*     */ 
/*  90 */     for (int i = 0; i < this.includeURLList.size(); ++i) {
/*  91 */       tmp = (String)this.includeURLList.get(i);
/*  92 */       this.log.info("include: [" + uri + "][" + tmp + "]");
/*  93 */       if (uri.matches(tmp)) {
/*  94 */         return true;
/*     */       }
/*     */     }
/*     */ 
/*  98 */     for (i = 0; i < this.excludeURLList.size(); ++i) {
/*  99 */       tmp = (String)this.excludeURLList.get(i);
/* 100 */       this.log.info("exclude: [" + uri + "][" + tmp + "]");
/* 101 */       if (uri.matches(tmp)) {
/* 102 */         return false;
/*     */       }
/*     */     }
/*     */ 
/* 106 */     return false;
/*     */   }
/*     */ 
/*     */   public void init(FilterConfig filterConfig) throws ServletException {
/* 110 */     this.filterConfig = filterConfig;
/* 111 */     this.redirectURL = filterConfig.getInitParameter("redirectURL");
/* 112 */     this.sessionKey = filterConfig.getInitParameter("checkSessionKey");
/* 113 */     this.file = filterConfig.getInitParameter("file");
/* 114 */     if (StringUtils.isBlank(this.file)) {
/* 115 */       this.file = "/conf/checkurl.xml";
/*     */     }
/* 117 */     else if (!(this.file.startsWith("/")))
/* 118 */       this.file = "/" + this.file;
/*     */   }
/*     */ 
/*     */   public boolean isCanLoaded(String file)
/*     */     throws ServletException
/*     */   {
/* 135 */     File f = new File(this.filterConfig.getServletContext().getRealPath(file));
/* 136 */     if (f.lastModified() != this.lastModified) {
/* 137 */       this.lastModified = f.lastModified();
/* 138 */       return true;
/*     */     }
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */   public void loadFilterURL(String file, ArrayList excludeList, ArrayList includeList)
/*     */     throws ServletException
/*     */   {
/*     */     Element element;
/*     */     String url;
/* 146 */     SAXReader reader = new SAXReader();
/* 147 */     Document doc = null;
/*     */     try {
/* 149 */       doc = reader.read(this.filterConfig.getServletContext().getResource(file));
/*     */     }
/*     */     catch (Exception e) {
/* 152 */       throw new ServletException(file, e);
/*     */     }
/* 154 */     Element root = doc.getRootElement();
/* 155 */     Element include = root.element("include");
/* 156 */     Element exclude = root.element("exclude");
/* 157 */     Iterator iter = null;
/* 158 */     if (include != null) {
/* 159 */       iter = include.elementIterator();
/* 160 */       while (iter.hasNext()) {
/* 161 */         element = (Element)iter.next();
/*     */ 
/* 163 */         url = element.attributeValue("url");
/* 164 */         if (StringUtils.isBlank(url)) {
/*     */           continue;
/*     */         }
/* 167 */         includeList.add(url);
/*     */       }
/*     */     }
/*     */ 
/* 171 */     if (exclude != null) {
/* 172 */       iter = exclude.elementIterator();
/* 173 */       while (iter.hasNext()) {
/* 174 */         element = (Element)iter.next();
/*     */ 
/* 176 */         url = element.attributeValue("url");
/* 177 */         if (StringUtils.isBlank(url)) {
/*     */           continue;
/*     */         }
/* 180 */         excludeList.add(url);
/*     */       }
/*     */     }
/*     */   }
/*     */ }