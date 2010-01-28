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
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.DocumentHelper;
 import org.dom4j.Element;
 
 public class GetServerGroupList
 {
   private static final String S_G = "s_g";
   private static final String ATTR_G_NM = "g_name";
   private static final String ATTR_G_DESC = "g_desc";
 
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String host = HiArgUtils.getStringNotNull(args, "host");
     int port = Integer.parseInt(HiArgUtils.getStringNotNull(args, "port"));
 
     String cmd = "<ROOT><CODE>090809</CODE></ROOT>";
     byte[] result = (byte[])null;
     Element root = null;
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     try {
       result = HiServiceRequest.callService(host, port, 8, cmd.getBytes());
 
       root = DocumentHelper.parseText("<?xml version='1.0' encoding='gb2312' ?>" + new String(result)).getRootElement();
     } catch (IOException e) {
       HiException.makeException(e);
     } catch (DocumentException e) {
       HiException.makeException(e);
     }
 
     HiETF body = ctx.getCurrentMsg().getETFBody();
     Iterator it = root.elementIterator("s_g");
     Element serverNode = null;
     Element node = null;
     int idx = 1;
 
     List groupList = new ArrayList();
 
     while (it.hasNext()) {
       serverNode = (Element)it.next();
       String cur_grp_nm = serverNode.attributeValue("g_name");
 
       if (!(groupList.contains(cur_grp_nm))) {
         groupList.add(cur_grp_nm);
         body.setGrandChildNode("GRP_" + idx + ".GRP_NM", cur_grp_nm);
         body.setGrandChildNode("GRP_" + idx + ".GRP_DESC", serverNode.attributeValue("g_desc"));
       }
 
       ++idx;
     }
     return 0;
   }
 }