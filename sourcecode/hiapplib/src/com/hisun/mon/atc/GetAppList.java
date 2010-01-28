 package com.hisun.mon.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.mon.HiServiceRequest;
 import java.io.IOException;
 import java.util.Iterator;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.DocumentHelper;
 import org.dom4j.Element;
 
 public class GetAppList
 {
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String host = HiArgUtils.getStringNotNull(args, "host");
     int port = Integer.parseInt(HiArgUtils.getStringNotNull(args, "port"));
     String brno = args.get("brno");
     HiETF body = ctx.getCurrentMsg().getETFBody();
 
     String cmd = "<ROOT><CODE>090808</CODE></ROOT>";
     if (brno != null) {
       cmd = "<ROOT><CODE>090808</CODE><bk_no>" + brno + "</bk_no></ROOT>";
     }
     byte[] result = (byte[])null;
     Element root = null;
     try {
       result = HiServiceRequest.callService(host, port, 8, cmd.getBytes());
       root = DocumentHelper.parseText(new String(result)).getRootElement();
     } catch (IOException e) {
       HiException.makeException(e);
     } catch (DocumentException e) {
       HiException.makeException(e);
     }
 
     int num = Integer.parseInt(root.elementText("appnum"));
 
     Iterator it = null;
     StringBuffer sb = null;
     for (int i = 1; i <= num; ++i) {
       sb = new StringBuffer();
       Element appNode = root.element("app_" + i);
       if (appNode == null) {
         continue;
       }
       body.setGrandChildNode("GRP_" + i + ".APP_ID", appNode.elementText("AppID"));
       body.setGrandChildNode("GRP_" + i + ".APP_DESC", appNode.elementText("AppNam"));
       it = appNode.elementIterator("Server");
       while (it.hasNext()) {
         Element svrNode = (Element)it.next();
         sb.append(svrNode.getText());
         sb.append("|");
       }
 
       body.setGrandChildNode("GRP_" + i + ".SVR_LIST", sb.toString());
     }
 
     return 0;
   }
 }