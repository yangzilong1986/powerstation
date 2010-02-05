 package com.hisun.web.filter;
 
 import java.io.IOException;
 import javax.servlet.Filter;
 import javax.servlet.FilterChain;
 import javax.servlet.FilterConfig;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 
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