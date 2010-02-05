 package com.hisun.mon.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.message.HiMessageContext;
 import com.hisun.mon.HiServiceRequest;
 import java.io.IOException;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.DocumentHelper;
 import org.dom4j.Element;
 
 public class ServerManage
 {
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String host = HiArgUtils.getStringNotNull(args, "host");
     int port = Integer.parseInt(HiArgUtils.getStringNotNull(args, "port"));
 
     String mode = HiArgUtils.getStringNotNull(args, "mode");
 
     String name = HiArgUtils.getStringNotNull(args, "name");
 
     StringBuffer reqCmd = new StringBuffer("<ROOT>");
     if ("start".equalsIgnoreCase(mode))
       reqCmd.append("<CODE>090801</CODE>");
     else {
       reqCmd.append("<CODE>090802</CODE>");
     }
     String[] nameArr = StringUtils.split(name, '|');
     for (int i = 0; i < nameArr.length; ++i) {
       reqCmd.append("<server>");
       reqCmd.append(nameArr[i]);
       reqCmd.append("</server>");
     }
     reqCmd.append("</ROOT>");
 
     byte[] result = (byte[])null;
     Element root = null;
     try {
       result = HiServiceRequest.callService(host, port, 8, reqCmd.toString().getBytes());
       root = DocumentHelper.parseText(new String(result)).getRootElement();
     } catch (IOException e) {
       HiException.makeException(e);
     } catch (DocumentException e) {
       HiException.makeException(e);
     }
     if (!("000000".equals(root.elementText("RspCod")))) {
       return 1;
     }
 
     return 0;
   }
 }