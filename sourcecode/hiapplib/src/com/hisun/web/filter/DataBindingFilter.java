/*    */ package com.hisun.web.filter;
/*    */ 
/*    */ import com.hisun.common.HiForm2Etf;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiETFFactory;
/*    */ import java.io.IOException;
/*    */ import java.util.Enumeration;
/*    */ import javax.servlet.FilterConfig;
/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.ServletRequest;
/*    */ import javax.servlet.ServletResponse;
/*    */ 
/*    */ public class DataBindingFilter extends TemplateFilter
/*    */ {
/*    */   private HiDataConvert _dataConvert;
/*    */ 
/*    */   public DataBindingFilter()
/*    */   {
/* 38 */     this._dataConvert = new HiDataConvert();
/*    */   }
/*    */ 
/*    */   public boolean doPreProcessing(ServletRequest request, ServletResponse response)
/*    */     throws IOException, ServletException
/*    */   {
/* 46 */     HiETF requestEtf = HiETFFactory.createETF();
/* 47 */     HiForm2Etf form2Etf = new HiForm2Etf(this._dataConvert);
/*    */     try {
/* 49 */       form2Etf.process(request, requestEtf);
/*    */     } catch (Exception e) {
/* 51 */       throw new ServletException(e);
/*    */     }
/* 53 */     request.setAttribute("REQ_ETF", requestEtf);
/* 54 */     return true;
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
/* 71 */     Enumeration en = filterConfig.getInitParameterNames();
/* 72 */     while (en.hasMoreElements()) {
/* 73 */       String name = (String)en.nextElement();
/* 74 */       String value = filterConfig.getInitParameter(name);
/* 75 */       this._dataConvert.add(name, value);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void destroy()
/*    */   {
/* 83 */     super.destroy();
/*    */   }
/*    */ }