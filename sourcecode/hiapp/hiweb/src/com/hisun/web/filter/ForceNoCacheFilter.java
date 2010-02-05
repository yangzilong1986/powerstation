 package com.hisun.web.filter;
 
 import java.io.IOException;
 import javax.servlet.Filter;
 import javax.servlet.FilterChain;
 import javax.servlet.FilterConfig;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import javax.servlet.http.HttpServletResponse;
 
 public class ForceNoCacheFilter
   implements Filter
 {
   public void destroy()
   {
   }
 
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
     throws IOException, ServletException
   {
     ((HttpServletResponse)response).setHeader("Cache-Control", "no-cache");
     ((HttpServletResponse)response).setHeader("Pragma", "no-cache");
     ((HttpServletResponse)response).setDateHeader("Expires", -1L);
     chain.doFilter(request, response);
   }
 
   public void init(FilterConfig filterConfig)
     throws ServletException
   {
   }
 }