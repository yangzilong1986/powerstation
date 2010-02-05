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
 
 public class TransLogSwitch
 {
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String host = HiArgUtils.getStringNotNull(args, "host");
     int port = Integer.parseInt(HiArgUtils.getStringNotNull(args, "port"));
 
     String transList = HiArgUtils.getStringNotNull(args, "trans");
     String serverList = HiArgUtils.getStringNotNull(args, "servers");
     String mode = HiArgUtils.getStringNotNull(args, "mode");
 
     String[] transArr = StringUtils.split(transList, "|");
     String[] serverArr = StringUtils.split(serverList, "|");
 
     String cmdPre = "<ROOT><CODE>090813</CODE><server>";
     if ("0".equalsIgnoreCase(mode)) {
       cmdPre = "<ROOT><CODE>090814</CODE><server>";
     }
 
     byte[] result = (byte[])null;
     Element root = null;
     for (int i = 0; i < transArr.length; ++i) {
       String reqCmd = cmdPre + serverArr[i] + "</server><code>" + transArr[i] + "</code></ROOT>";
       try
       {
         result = HiServiceRequest.callService(host, port, 8, reqCmd.getBytes());
         root = DocumentHelper.parseText(new String(result)).getRootElement();
       } catch (IOException e) {
         HiException.makeException(e);
       } catch (DocumentException e) {
         HiException.makeException(e);
       }
     }
     return 0;
   }
 }