 package com.hisun.mon.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.mon.HiServiceRequest;
 import java.io.IOException;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.DocumentHelper;
 import org.dom4j.Element;
 
 public class GetAppTransList
 {
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiETF grpNode;
     String host = HiArgUtils.getStringNotNull(args, "host");
     int port = Integer.parseInt(HiArgUtils.getStringNotNull(args, "port"));
 
     String appId = HiArgUtils.getStringNotNull(args, "app");
     String serverList = args.get("servers");
     String brno = args.get("brno");
     HiETF body = ctx.getCurrentMsg().getETFBody();
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
 
     String cmd = null;
     if (brno != null)
       cmd = "<ROOT><CODE>090806</CODE><AppID>" + appId + "</AppID><bk_no>" + brno + "</bk_no></ROOT>";
     else {
       cmd = "<ROOT><CODE>090806</CODE><AppID>" + appId + "</AppID></ROOT>";
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
 
     int num = Integer.parseInt(root.elementText("codnum"));
 
     Map map = new HashMap();
     String cod = null;
     for (int i = 1; i <= num; ++i) {
       Element tranNode = root.element("cod_" + i);
       if (tranNode == null) {
         continue;
       }
       grpNode = body.addNode("GRP_" + i);
 
       cod = tranNode.elementText("TxnCod");
       grpNode.setChildValue("TXN_CD", cod);
       grpNode.setChildValue("TXN_NM", tranNode.elementText("TxnNam"));
 
       map.put(cod, grpNode);
     }
 
     if (serverList == null) {
       return 0;
     }
 
     String[] serverArr = StringUtils.split(serverList, '|');
 
     String transCmdPre = null;
 
     for (int i = 0; i < serverArr.length; ++i) {
       cmd = "<ROOT><CODE>090819</CODE><server>" + serverArr[i] + "</server></ROOT>";
       try
       {
         result = HiServiceRequest.callService(host, port, 8, cmd.getBytes());
         root = DocumentHelper.parseText(new String(result)).getRootElement();
       } catch (IOException e) {
         HiException.makeException(e);
       } catch (DocumentException e) {
         HiException.makeException(e);
       }
 
       if (!("000000".equals(root.elementText("RspCod")))) {
         continue;
       }
 
       Iterator it = root.elementIterator("c_g");
       while (it.hasNext()) {
         Element transNode2 = (Element)it.next();
         cod = transNode2.attributeValue("t_code");
 
         grpNode = (HiETF)map.get(cod);
         if (grpNode == null)
           continue;
         grpNode.setChildValue("SVR_DESC", serverArr[i]);
         grpNode.setChildValue("RUN_FLG", transNode2.attributeValue("r_flag"));
         grpNode.setChildValue("LOG_LVL", transNode2.attributeValue("t_flag"));
         grpNode.setChildValue("MON_SW", transNode2.attributeValue("m_flag"));
         log.info(grpNode.toString());
       }
 
     }
 
     map.clear();
     return 0;
   }
 }