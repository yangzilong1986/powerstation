/*    */ package com.hisun.web.filter;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ 
/*    */ public class CharacterEncodingFilter extends TemplateFilter
/*    */ {
/*    */   private String encoding;
/*    */   private boolean ignore;
/*    */ 
/*    */   public CharacterEncodingFilter()
/*    */   {
/* 26 */     this.encoding = null;
/*    */ 
/* 28 */     this.ignore = true;
/*    */   }
/*    */ 
/*    */   public boolean doPreProcessing(ServletRequest request, ServletResponse response)
/*    */     throws IOException, ServletException
/*    */   {
/* 37 */     if ((((this.ignore) || (request.getCharacterEncoding() == null))) && 
/* 38 */       (this.encoding != null)) {
/* 39 */       request.setCharacterEncoding(this.encoding);
/*    */     }
/*    */ 
/* 42 */     return true;
/*    */   }
/*    */ 
/*    */   public void doPostProcessing(ServletRequest request, ServletResponse response)
/*    */     throws IOException, ServletException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void initFilter(FilterConfig filterConfig)
/*    */     throws ServletException
/*    */   {
/* 60 */     this.encoding = filterConfig.getInitParameter("encoding");
/* 61 */     if ("false".equals(filterConfig.getInitParameter("ignore")))
/* 62 */       this.ignore = false;
/*    */   }
/*    */ 
/*    */   public void destroy()
/*    */   {
/* 70 */     this.encoding = null;
/* 71 */     super.destroy();
/*    */   }
/*    */ }