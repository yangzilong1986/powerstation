 package com.hisun.web.service;

 import com.hisun.exception.HiException;
 import com.hisun.message.HiMessage;
 import com.hisun.register.HiRegisterService;
 import com.hisun.register.HiServiceObject;
 import com.hisun.util.HiByteBuffer;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.SystemUtils;

 import javax.servlet.RequestDispatcher;
 import javax.servlet.ServletException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import java.io.IOException;
 import java.io.InputStream;
 
 public class HiRawServiceImpl extends HiAbstractChannelService
   implements IChannelService
 {
   public void execute(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException
   {
     InputStream is = request.getInputStream();
     int len1 = request.getContentLength();
     if (len1 == -1) {
       throw new ServletException("invalid request data(length=-1)");
     }
     byte[] buffer = new byte[request.getContentLength()];
     int len2 = is.read(buffer);
 
     StringBuffer tmpbuf = new StringBuffer();
     for (int i = 0; i < buffer.length; ++i) {
       if (i % 8 == 0) {
         tmpbuf.append(SystemUtils.LINE_SEPARATOR);
       }
       tmpbuf.append(Integer.toHexString(buffer[i] & 0xFF));
       tmpbuf.append(" ");
     }
     this._log.info("REQ DATA01:[" + tmpbuf.toString());
     this._log.info("REQ DATA02:[" + buffer);
 
     if (this._log.isInfoEnabled()) {
       this._log.info("REQ DATA:[" + new String(buffer, 0, len1) + "]");
     }
 
     if (len1 != len2) {
       throw new ServletException(String.format("read inputstream error[%d][%d]", new Object[] { Integer.valueOf(len1), Integer.valueOf(len2) }));
     }
 
     if (StringUtils.isNotBlank(this._encoding)) {
       buffer = new String(buffer, this._encoding).getBytes();
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
     try
     {
       HiServiceObject serviceObject = HiRegisterService.getService(service);
 
       this._serverName = serviceObject.getServerName();
     }
     catch (HiException serviceObject)
     {
     }
     HiMessage msg = new HiMessage(this._serverName, this._msgType);
     msg.setBody(new HiByteBuffer(buffer));
     if (!(this._serverName.equalsIgnoreCase(service)))
       msg.setHeadItem("STC", service);
     else {
       msg.setHeadItem("SDT", service);
     }
     msg.setHeadItem("ECT", "text/plain");
     msg.setHeadItem("SIP", request.getRemoteAddr());
     try
     {
       msg = this._serviceBean.invoke(msg);
     } catch (Throwable e) {
       throw new ServletException(e);
     }
 
     if (this._log.isInfoEnabled()) {
       this._log.info("RSP DATA: [" + ((HiByteBuffer)msg.getBody()) + "]");
     }
     if (StringUtils.isNotBlank(this._encoding))
       response.setCharacterEncoding(this._encoding);
     else {
       response.setCharacterEncoding("GBK");
     }
     response.getWriter().print((HiByteBuffer)msg.getBody());
   }
 
   protected String getService(String url) {
     int idx1 = url.lastIndexOf("/");
     int idx2 = url.lastIndexOf(".dor");
     if ((idx1 == -1) || (idx2 == -1)) {
       return null;
     }
     return url.substring(idx1 + 1, idx2);
   }
 }