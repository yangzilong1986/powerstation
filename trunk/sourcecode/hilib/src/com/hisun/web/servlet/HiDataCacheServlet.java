/*     */ package com.hisun.web.servlet;
/*     */ 
/*     */ import com.hisun.data.cache.HiDBUtil;
/*     */ import com.hisun.data.cache.HiDataCacheConfig;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.server.manage.servlet.HiIpCheck;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiDataCacheServlet extends HttpServlet
/*     */ {
/*  28 */   private String file = "/conf/data_cache.xml";
/*     */   private HiDataCacheConfig dataCacheConfig;
/*  30 */   public static boolean isLoaded = false;
/*  31 */   private HiIpCheck ipCheck = new HiIpCheck();
/*     */ 
/*     */   public void destroy()
/*     */   {
/*  41 */     super.destroy();
/*     */   }
/*     */ 
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  61 */     doPost(request, response);
/*     */   }
/*     */ 
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  81 */     if (!(this.ipCheck.check(request))) {
/*  82 */       response.getWriter().write("client ip:[" + this.ipCheck.getIpAddr(request) + "] deny");
/*  83 */       return;
/*     */     }
/*     */ 
/*  87 */     String id = request.getParameter("ID");
/*     */ 
/*  89 */     if (StringUtils.equalsIgnoreCase(request.getParameter("CMD"), "reloadall"))
/*     */     {
/*  92 */       if (this.dataCacheConfig != null) {
/*  93 */         this.dataCacheConfig.clear();
/*     */       }
/*  95 */       load();
/*  96 */       return;
/*     */     }
/*     */ 
/*  99 */     if (StringUtils.isBlank(id)) {
/* 100 */       throw new ServletException("id is empty");
/*     */     }
/*     */ 
/* 103 */     if (!(StringUtils.equalsIgnoreCase(request.getParameter("CMD"), "reloadid"))) {
/*     */       return;
/*     */     }
/* 106 */     if (this.dataCacheConfig == null) {
/* 107 */       load();
/*     */     }
/*     */     else {
/* 110 */       HiDBUtil dbUtil = new HiDBUtil();
/*     */       try {
/* 112 */         this.dataCacheConfig.validate(id, dbUtil);
/*     */       } catch (HiException e) {
/*     */       }
/*     */       finally {
/* 116 */         dbUtil.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void init(ServletConfig config)
/*     */     throws ServletException
/*     */   {
/* 129 */     if (isLoaded) {
/* 130 */       return;
/*     */     }
/* 132 */     super.init();
/* 133 */     String tmp = config.getInitParameter("ipLst");
/* 134 */     if (tmp == null) {
/* 135 */       tmp = getServletContext().getInitParameter("ipLst");
/*     */     }
/* 137 */     this.ipCheck.setIpCheck(tmp);
/* 138 */     load();
/*     */   }
/*     */ 
/*     */   private void load() throws ServletException
/*     */   {
/* 143 */     String tmpFile = getServletConfig().getInitParameter("FILE");
/* 144 */     if (StringUtils.isNotBlank(tmpFile)) {
/* 145 */       this.file = tmpFile;
/*     */     }
/*     */ 
/* 148 */     HiDBUtil dbUtil = new HiDBUtil();
/*     */     try {
/* 150 */       this.dataCacheConfig = HiDataCacheConfig.loadStream(getServletConfig().getServletContext().getResource(this.file).openStream());
/*     */ 
/* 152 */       this.dataCacheConfig.process(dbUtil);
/* 153 */       HiDataCacheConfig.setInstance(this.dataCacheConfig);
/*     */     } catch (Exception e) {
/*     */     }
/*     */     finally {
/* 157 */       dbUtil.close();
/*     */     }
/* 159 */     HashMap map = this.dataCacheConfig.getDataMap();
/* 160 */     Iterator iter = map.keySet().iterator();
/* 161 */     while (iter.hasNext()) {
/* 162 */       String key = (String)iter.next();
/* 163 */       getServletConfig().getServletContext().setAttribute(key, map.get(key));
/*     */     }
/* 165 */     isLoaded = true;
/*     */   }
/*     */ }