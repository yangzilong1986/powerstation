/*    */ package com.hisun.web.filter;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.Filter;
/*    */ import javax.servlet.FilterChain;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ 
/*    */ public abstract class TemplateFilter
/*    */   implements Filter
/*    */ {
/*    */   private FilterConfig filterConfig;
/*    */ 
/*    */   protected final FilterConfig getFilterConfig()
/*    */   {
/* 33 */     return this.filterConfig;
/*    */   }
/*    */ 
/*    */   public final void init(FilterConfig filterConfig)
/*    */     throws ServletException
/*    */   {
/* 40 */     this.filterConfig = filterConfig;
/* 41 */     initFilter(filterConfig);
/*    */   }
/*    */ 
/*    */   public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
/*    */     throws IOException, ServletException
/*    */   {
/* 51 */     if (doPreProcessing(request, response)) {
/* 52 */       chain.doFilter(request, response);
/* 53 */       doPostProcessing(request, response);
/*    */     }
/*    */   }
/*    */ 
/*    */   public abstract boolean doPreProcessing(ServletRequest paramServletRequest, ServletResponse paramServletResponse)
/*    */     throws IOException, ServletException;
/*    */ 
/*    */   public abstract void doPostProcessing(ServletRequest paramServletRequest, ServletResponse paramServletResponse)
/*    */     throws IOException, ServletException;
/*    */ 
/*    */   public void initFilter(FilterConfig filterConfig)
/*    */     throws ServletException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void destroy()
/*    */   {
/* 86 */     this.filterConfig = null;
/*    */   }
/*    */ }