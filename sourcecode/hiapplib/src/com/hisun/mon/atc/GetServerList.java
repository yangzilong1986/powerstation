 package com.hisun.mon.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.mon.HiServiceRequest;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import org.dom4j.Document;
 import org.dom4j.DocumentException;
 import org.dom4j.DocumentHelper;
 import org.dom4j.Element;
 
 public class GetServerList
 {
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiETF cur_grp;
     String cur_grp_nm;
     String cur_grp_desc;
     String cur_server_nm;
     String group_pre;
     String run_flg;
     String host = HiArgUtils.getStringNotNull(args, "host");
     int port = Integer.parseInt(HiArgUtils.getStringNotNull(args, "port"));
 
     String type = args.get("type");
     String cnd_grp = null;
     if ("g".equalsIgnoreCase(type)) {
       cnd_grp = HiArgUtils.getStringNotNull(args, "group");
     }
 
     boolean isGetGrpList = args.getBoolean("getGroup");
     boolean isUpdate = args.getBoolean("update");
 
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     HiETF body = ctx.getCurrentMsg().getETFBody();
 
     Element serverNode = null;
 
     int idx = 1;
     Map serverStsMap = (Map)ctx.getServerContext().getProperty("_SERVER_LIST");
 
     if ((!(isUpdate)) && (serverStsMap != null))
     {
       Iterator svrIt = serverStsMap.values().iterator();
       break label409:
       while (true) { serverNode = (Element)svrIt.next();
         cur_grp_nm = serverNode.attributeValue("g_name");
         cur_grp_desc = serverNode.attributeValue("g_desc");
         cur_server_nm = serverNode.attributeValue("s_name");
         if ((!("g".equalsIgnoreCase(type))) || (cnd_grp.equalsIgnoreCase(cur_grp_nm)))
         {
           group_pre = "SERVER_" + idx;
 
           cur_grp = body.addNode(group_pre);
 
           cur_grp.setChildValue("SVR_NM", cur_server_nm);
           cur_grp.setChildValue("SVR_DESC", serverNode.attributeValue("s_desc"));
           cur_grp.setChildValue("START_MIN", serverNode.attributeValue("s_min"));
           cur_grp.setChildValue("START_MAX", serverNode.attributeValue("s_max"));
           cur_grp.setChildValue("GRP_NM", cur_grp_nm);
           cur_grp.setChildValue("GRP_DESC", cur_grp_desc);
 
           run_flg = serverNode.attributeValue("r_num");
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
         if (!(svrIt.hasNext()))
         {
           label409: return 0; } }
     }
     if (serverStsMap != null) {
       serverStsMap.clear();
     } else {
       serverStsMap = new HashMap();
       ctx.getServerContext().setProperty("_SERVER_LIST", serverStsMap);
     }
 
     String cmd = "<ROOT><CODE>090809</CODE></ROOT>";
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
 
     Iterator it = root.elementIterator("s_g");
 
     int g_idx = 1;
 
     List groupList = null;
     if (isGetGrpList) {
       groupList = new ArrayList();
     }
     int getServerStsNum = 0;
     Map serverGroupMap = new HashMap();
 
     idx = 1;
     StringBuffer serverStsSb = new StringBuffer("<ROOT><CODE>090829</CODE>");
     while (it.hasNext()) {
       serverNode = (Element)it.next();
       cur_grp_nm = serverNode.attributeValue("g_name");
       cur_grp_desc = serverNode.attributeValue("g_desc");
       cur_server_nm = serverNode.attributeValue("s_name").trim();
 
       serverStsMap.put(cur_server_nm, serverNode);
 
       if ((isGetGrpList) && (!(groupList.contains(cur_grp_nm)))) {
         groupList.add(cur_grp_nm);
         body.setGrandChildNode("GRP_" + g_idx + ".GRP_NM", cur_grp_nm);
         body.setGrandChildNode("GRP_" + g_idx + ".GRP_DESC", cur_grp_desc);
         ++g_idx;
       }
 
       run_flg = serverNode.attributeValue("r_num");
 
       if (!("0".equals(run_flg))) {
         serverStsSb.append("<server>");
         serverStsSb.append(cur_server_nm);
         serverStsSb.append("</server>");
 
         ++getServerStsNum;
       }
 
       if (("g".equalsIgnoreCase(type)) && (!(cnd_grp.equalsIgnoreCase(cur_grp_nm)))) {
         continue;
       }
 
       group_pre = "SERVER_" + idx;
 
       cur_grp = body.addNode(group_pre);
 
       cur_grp.setChildValue("SVR_NM", cur_server_nm);
       cur_grp.setChildValue("SVR_DESC", serverNode.attributeValue("s_desc"));
       cur_grp.setChildValue("START_MIN", serverNode.attributeValue("s_min"));
       cur_grp.setChildValue("START_MAX", serverNode.attributeValue("s_max"));
       cur_grp.setChildValue("GRP_NM", cur_grp_nm);
       cur_grp.setChildValue("GRP_DESC", cur_grp_desc);
       cur_grp.setChildValue("RUN_NUM", run_flg);
       if ("0".equals(run_flg))
         cur_grp.setChildValue("RUN_FLG", "0");
       else {
         cur_grp.setChildValue("RUN_FLG", "1");
       }
       serverGroupMap.put(cur_server_nm, cur_grp);
 
       ++idx;
     }
     serverStsSb.append("</ROOT>");
 
     if (getServerStsNum == 0)
     {
       return 0;
     }
 
     try
     {
       result = HiServiceRequest.callService(host, port, 8, serverStsSb.toString().getBytes());
       root = DocumentHelper.parseText(new String(result)).getRootElement();
     } catch (IOException e) {
       HiException.makeException(e);
     } catch (DocumentException e) {
       HiException.makeException(e);
     }
 
     it = root.elementIterator("server");
 
     while (it.hasNext()) {
       Element typeNode = (Element)it.next();
       cur_server_nm = typeNode.attributeValue("name").trim();
       serverNode = (Element)serverStsMap.get(cur_server_nm);
       cur_grp = (HiETF)serverGroupMap.get(cur_server_nm);
 
       if (serverNode == null)
         continue;
       serverNode.addAttribute("type", typeNode.elementText("type"));
       serverNode.addAttribute("l_flag", typeNode.elementText("l_flag"));
       if (cur_grp != null) {
         cur_grp.setChildValue("SVR_TYP", typeNode.elementText("type"));
         cur_grp.setChildValue("LOG_LVL", typeNode.elementText("l_flag"));
       }
 
     }
 
     return 0;
   }
 }