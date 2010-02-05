 package com.hisun.web.service;

 import com.hisun.common.HiETF2HashMapList;
 import com.hisun.common.HiForm2Etf;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.register.HiRegisterService;
 import com.hisun.register.HiServiceObject;
 import net.sf.json.JSONObject;
 import org.apache.commons.lang.StringUtils;

 import javax.servlet.RequestDispatcher;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import java.io.IOException;
 
 public class HiWebServiceImpl extends HiAbstractChannelService
   implements IChannelService
 {
   public void execute(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
   {
     HiETF requestEtf = HiETFFactory.createETF();
     HiForm2Etf form2Etf = new HiForm2Etf(this._dataConvert);
     try {
       form2Etf.process(request, requestEtf);
     } catch (Exception e) {
       throw new ServletException(e);
     }
     if (this._log.isInfoEnabled()) {
       this._log.info("REQ ETF: [" + requestEtf + "]");
     }
 
     String service = getService(request.getRequestURL().toString());
     if (service == null) {
       throw new ServletException("service can't empty");
     }
 
     if (this._log.isInfoEnabled())
       this._log.info("service: [" + service + "]");
     try
     {
       if ((this._securityFilter != null) && (!(this._securityFilter.contains(service)))) {
         this._log.info("service: [" + service + "] deny");
         request.getRequestDispatcher(this._redirectURL).forward(request, response);
 
         return;
       }
     } catch (HiException e) {
       throw new ServletException("load security file failure", e);
     }
 
     String serverName = this._serverName;
     try {
       HiServiceObject serviceObject = HiRegisterService.getService(service);
 
       serverName = serviceObject.getServerName();
     }
     catch (HiException e) {
     }
     HiMessage msg = new HiMessage(serverName, this._msgType);
 
     msg.setHeadItem("SCH", "rq");
     msg.setHeadItem("STM", new Long(System.currentTimeMillis()));
     msg.setHeadItem("STC", service);
     msg.setHeadItem("SDT", serverName);
     msg.setHeadItem("ECT", "text/etf");
 
     msg.setBody(requestEtf);
 
     bindsRqData(msg, request, response);
     try {
       msg = this._serviceBean.invoke(msg);
     } catch (HiException e) {
       e.printStackTrace();
       throw new ServletException(e);
     }
     HiETF rspetf = msg.getETFBody();
     if (this._log.isInfoEnabled()) {
       this._log.info("RSP ETF: [" + rspetf + "]");
     }
 
     request.setAttribute("ETF", new HiETF2HashMapList(rspetf).map());
 
     String output = msg.getHeadItem("SEND_OUTPUT");
     if (StringUtils.isBlank(output))
     {
       output = msg.getHeadItem("WSO");
     }
     if (StringUtils.isNotBlank(output)) {
       if (this._log.isInfoEnabled()) {
         this._log.info("forward PAGE:  [" + output + "]");
       }
       request.getRequestDispatcher(output).forward(request, response);
     } else {
       output = msg.getHeadItem("SEND_REDIRECT");
       if (StringUtils.isBlank(output))
       {
         output = msg.getHeadItem("WSR");
       }
       if (StringUtils.isNotBlank(output)) {
         if (this._log.isInfoEnabled()) {
           this._log.info("sendRedirect PAGE: :  [" + output + "]");
         }
         response.sendRedirect(output);
       } else {
         if (StringUtils.isNotBlank(this._encoding)) {
           response.setCharacterEncoding(this._encoding);
           response.setContentType("text/html;charset=" + this._encoding);
         } else {
           response.setCharacterEncoding("GBK");
           response.setContentType("text/html;charset=GBK");
         }
 
         if ((response.isCommitted()) || 
           (request.getAttribute("ETF") == null)) return;
         response.getWriter().print(JSONObject.fromObject(request.getAttribute("ETF")));
       }
     }
   }
 
   protected String getService(String url)
   {
     int idx1 = url.lastIndexOf("/");
     int idx2 = url.lastIndexOf(".dow");
     if (idx1 == -1) {
       return null;
     }
     if (idx2 != -1) {
       return url.substring(idx1 + 1, idx2);
     }
     return url.substring(idx1 + 1);
   }
 }