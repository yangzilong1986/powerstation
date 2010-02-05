 package com.hisun.web.filter;

 import javax.servlet.*;
 import java.io.IOException;
 
 public abstract class TemplateFilter
   implements Filter
 {
   private FilterConfig filterConfig;
 
   protected final FilterConfig getFilterConfig()
   {
     return this.filterConfig;
   }
 
   public final void init(FilterConfig filterConfig)
     throws ServletException
   {
     this.filterConfig = filterConfig;
     initFilter(filterConfig);
   }
 
   public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
     throws IOException, ServletException
   {
     if (doPreProcessing(request, response)) {
       chain.doFilter(request, response);
       doPostProcessing(request, response);
     }
   }
 
   public abstract boolean doPreProcessing(ServletRequest paramServletRequest, ServletResponse paramServletResponse)
     throws IOException, ServletException;
 
   public abstract void doPostProcessing(ServletRequest paramServletRequest, ServletResponse paramServletResponse)
     throws IOException, ServletException;
 
   public void initFilter(FilterConfig filterConfig)
     throws ServletException
   {
   }
 
   public void destroy()
   {
     this.filterConfig = null;
   }
 }