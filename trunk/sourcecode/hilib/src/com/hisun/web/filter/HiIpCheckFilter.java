/*     */ package com.hisun.web.filter;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javax.servlet.Filter;
/*     */ import javax.servlet.FilterChain;
/*     */ import javax.servlet.FilterConfig;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiIpCheckFilter
/*     */   implements Filter
/*     */ {
/*     */   private HashMap ipMap;
/*     */ 
/*     */   public HiIpCheckFilter()
/*     */   {
/*  23 */     this.ipMap = new HashMap();
/*     */   }
/*     */ 
/*     */   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
/*     */   {
/*  28 */     HttpServletRequest request = (HttpServletRequest)servletRequest;
/*  29 */     HttpServletResponse response = (HttpServletResponse)servletResponse;
/*  30 */     Iterator iter = this.ipMap.keySet().iterator();
/*     */ 
/*  32 */     while (iter.hasNext()) {
/*  33 */       String url = (String)iter.next();
/*  34 */       if (!(request.getServletPath().matches(url))) {
/*     */         continue;
/*     */       }
/*  37 */       ArrayList ipLst = (ArrayList)this.ipMap.get(url);
/*  38 */       if (!(ipLst.contains(getIpAddr(request)))) {
/*  39 */         response.getWriter().write("client ip:[" + getIpAddr(request) + "] deny");
/*     */ 
/*  41 */         return;
/*     */       }
/*     */     }
/*     */ 
/*  45 */     filterChain.doFilter(request, response);
/*     */   }
/*     */ 
/*     */   public void destroy() {
/*  49 */     this.ipMap.clear();
/*     */   }
/*     */ 
/*     */   public void init(FilterConfig filterConfig) throws ServletException {
/*  53 */     String ipLst = filterConfig.getInitParameter("ipLst");
/*  54 */     if (StringUtils.isNotBlank(ipLst))
/*  55 */       parseIpLst(ipLst);
/*     */   }
/*     */ 
/*     */   private String getIpAddr(HttpServletRequest request)
/*     */   {
/*  60 */     String ip = request.getHeader("x-forwarded-for");
/*  61 */     if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
/*  62 */       ip = request.getHeader("Proxy-Client-IP");
/*     */     }
/*  64 */     if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
/*  65 */       ip = request.getHeader("WL-Proxy-Client-IP");
/*     */     }
/*  67 */     if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
/*  68 */       ip = request.getRemoteAddr();
/*     */     }
/*  70 */     return ip;
/*     */   }
/*     */ 
/*     */   private void parseIpLst(String ipLst) {
/*  74 */     int state = 0;
/*  75 */     StringBuffer tmp = new StringBuffer();
/*  76 */     String url = null;
/*  77 */     ArrayList tmpIpLst = null;
/*  78 */     for (int i = 0; i < ipLst.length(); ++i)
/*  79 */       switch (state)
/*     */       {
/*     */       case 0:
/*  82 */         if (ipLst.charAt(i) == ':') {
/*  83 */           state = 1;
/*  84 */           url = tmp.toString();
/*  85 */           tmp.setLength(0);
/*     */         }
/*     */         else {
/*  88 */           tmp.append(ipLst.charAt(i)); }
/*  89 */         break;
/*     */       case 1:
/*  91 */         if (ipLst.charAt(i) == '{') {
/*  92 */           tmpIpLst = new ArrayList();
/*     */         }
/*  96 */         else if (ipLst.charAt(i) == '}') {
/*  97 */           state = 2;
/*     */         }
/* 101 */         else if (ipLst.charAt(i) == '|') {
/* 102 */           tmpIpLst.add(tmp.toString());
/* 103 */           tmp.setLength(0);
/*     */         }
/*     */         else {
/* 106 */           tmp.append(ipLst.charAt(i)); }
/* 107 */         break;
/*     */       case 2:
/* 110 */         if ((i == ipLst.length() - 1) || (ipLst.charAt(i) == '|')) {
/* 111 */           this.ipMap.put(url, tmpIpLst);
/* 112 */           state = 0;
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 121 */     HiIpCheckFilter ipCheck = new HiIpCheckFilter();
/* 122 */     ipCheck.parseIpLst("url1:{ip1|ip2|ip3|ip4|}|url2:{ip1|ip2|ip3|ip4|}|");
/* 123 */     System.out.println(ipCheck.ipMap);
/*     */   }
/*     */ }