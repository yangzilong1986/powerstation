 package com.hisun.mon.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.mon.HiServiceRequest;
 import java.io.IOException;
 import java.util.Collection;
 import java.util.Iterator;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.DocumentHelper;
 import org.dom4j.Element;
 
 public class ServerLogSwitch
 {
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String cur_server_nm;
     Element serverNode;
     String host = HiArgUtils.getStringNotNull(args, "host");
     int port = Integer.parseInt(HiArgUtils.getStringNotNull(args, "port"));
 
     String mode = HiArgUtils.getStringNotNull(args, "mode");
 
     String name = HiArgUtils.getStringNotNull(args, "name");
 
     String type = args.get("type");
 
     String cnd_grp = null;
     if ("g".equalsIgnoreCase(type)) {
       cnd_grp = HiArgUtils.getStringNotNull(args, "group");
     }
 
     StringBuffer reqCmd = new StringBuffer("<ROOT>");
     StringBuffer reqStsCmd = new StringBuffer("<ROOT><CODE>090829</CODE>");
     if ("1".equalsIgnoreCase(mode))
       reqCmd.append("<CODE>090821</CODE>");
     else {
       reqCmd.append("<CODE>090822</CODE>");
     }
     String[] nameArr = StringUtils.split(name, '|');
     for (int i = 0; i < nameArr.length; ++i) {
       reqCmd.append("<server>");
       reqCmd.append(nameArr[i]);
       reqCmd.append("</server>");
 
       reqStsCmd.append("<server>");
       reqStsCmd.append(nameArr[i]);
       reqStsCmd.append("</server>");
     }
     reqCmd.append("</ROOT>");
     reqStsCmd.append("</ROOT>");
 
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
     "000000".equals(root.elementText("RspCod"));
 
     Map serverStsMap = (Map)ctx.getServerContext().getProperty("_SERVER_LIST");
     try {
       result = HiServiceRequest.callService(host, port, 8, reqStsCmd.toString().getBytes());
       root = DocumentHelper.parseText(new String(result)).getRootElement();
     } catch (IOException e) {
       HiException.makeException(e);
     } catch (DocumentException e) {
       HiException.makeException(e);
     }
     Iterator it = root.elementIterator("server");
 
     int idx = 1;
 
     while (it.hasNext()) {
       Element typeNode = (Element)it.next();
       cur_server_nm = typeNode.attributeValue("name").trim();
       serverNode = (Element)serverStsMap.get(cur_server_nm);
 
       if (serverNode == null)
         continue;
       serverNode.addAttribute("type", typeNode.elementText("type"));
       serverNode.addAttribute("l_flag", typeNode.elementText("l_flag"));
     }
 
     HiETF body = ctx.getCurrentMsg().getETFBody();
     Iterator svrIt = serverStsMap.values().iterator();
     while (svrIt.hasNext()) {
       serverNode = (Element)svrIt.next();
       String cur_grp_nm = serverNode.attributeValue("g_name");
       String cur_grp_desc = serverNode.attributeValue("g_desc");
       cur_server_nm = serverNode.attributeValue("s_name");
       if (("g".equalsIgnoreCase(type)) && (!(cnd_grp.equalsIgnoreCase(cur_grp_nm)))) {
         continue;
       }
 
       String group_pre = "SERVER_" + idx;
 
       HiETF cur_grp = body.addNode(group_pre);
 
       cur_grp.setChildValue("SVR_NM", cur_server_nm);
       cur_grp.setChildValue("SVR_DESC", serverNode.attributeValue("s_desc"));
       cur_grp.setChildValue("START_MIN", serverNode.attributeValue("s_min"));
       cur_grp.setChildValue("START_MAX", serverNode.attributeValue("s_max"));
       cur_grp.setChildValue("GRP_NM", cur_grp_nm);
       cur_grp.setChildValue("GRP_DESC", cur_grp_desc);
 
       String run_flg = serverNode.attributeValue("r_num");
       cur_grp.setChildValue("RUN_NUM", run_flg);
       if ("0".equals(run_flg))
         cur_grp.setChildValue("RUN_FLG", "0");
       else {
         cur_grp.setChildValue("RUN_FLG", "1");
       }
       cur_grp.setChildValue("SVR_TYP", serverNode.attributeValue("type"));
       cur_grp.setChildValue("LOG_LVL", serverNode.attributeValue("l_flag"));
 
       ++idx;
     }
     return 0;
   }
 }