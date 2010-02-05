 package com.hisun.web.filter;

 import com.hisun.common.HiForm2Etf;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;

 import javax.servlet.FilterConfig;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import java.io.IOException;
 import java.util.Enumeration;
 
 public class DataBindingFilter extends TemplateFilter
 {
   private HiDataConvert _dataConvert;
 
   public DataBindingFilter()
   {
     this._dataConvert = new HiDataConvert();
   }
 
   public boolean doPreProcessing(ServletRequest request, ServletResponse response)
     throws IOException, ServletException
   {
     HiETF requestEtf = HiETFFactory.createETF();
     HiForm2Etf form2Etf = new HiForm2Etf(this._dataConvert);
     try {
       form2Etf.process(request, requestEtf);
     } catch (Exception e) {
       throw new ServletException(e);
     }
     request.setAttribute("REQ_ETF", requestEtf);
     return true;
   }
 
   public void doPostProcessing(ServletRequest request, ServletResponse response)
     throws IOException, ServletException
   {
   }
 
   public void initFilter(FilterConfig filterConfig)
     throws ServletException
   {
     Enumeration en = filterConfig.getInitParameterNames();
     while (en.hasMoreElements()) {
       String name = (String)en.nextElement();
       String value = filterConfig.getInitParameter(name);
       this._dataConvert.add(name, value);
     }
   }
 
   public void destroy()
   {
     super.destroy();
   }
 }