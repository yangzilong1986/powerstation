/*     */ package com.hisun.mon.atc;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.mon.HiServiceRequest;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class GetServerList
/*     */ {
/*     */   public int execute(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     HiETF cur_grp;
/*     */     String cur_grp_nm;
/*     */     String cur_grp_desc;
/*     */     String cur_server_nm;
/*     */     String group_pre;
/*     */     String run_flg;
/*  40 */     String host = HiArgUtils.getStringNotNull(args, "host");
/*  41 */     int port = Integer.parseInt(HiArgUtils.getStringNotNull(args, "port"));
/*     */ 
/*  44 */     String type = args.get("type");
/*  45 */     String cnd_grp = null;
/*  46 */     if ("g".equalsIgnoreCase(type)) {
/*  47 */       cnd_grp = HiArgUtils.getStringNotNull(args, "group");
/*     */     }
/*     */ 
/*  50 */     boolean isGetGrpList = args.getBoolean("getGroup");
/*  51 */     boolean isUpdate = args.getBoolean("update");
/*     */ 
/*  53 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*  54 */     HiETF body = ctx.getCurrentMsg().getETFBody();
/*     */ 
/*  56 */     Element serverNode = null;
/*     */ 
/*  62 */     int idx = 1;
/*  63 */     Map serverStsMap = (Map)ctx.getServerContext().getProperty("_SERVER_LIST");
/*     */ 
/*  65 */     if ((!(isUpdate)) && (serverStsMap != null))
/*     */     {
/*  67 */       Iterator svrIt = serverStsMap.values().iterator();
/*  68 */       break label409:
/*     */       while (true) { serverNode = (Element)svrIt.next();
/*  70 */         cur_grp_nm = serverNode.attributeValue("g_name");
/*  71 */         cur_grp_desc = serverNode.attributeValue("g_desc");
/*  72 */         cur_server_nm = serverNode.attributeValue("s_name");
/*  73 */         if ((!("g".equalsIgnoreCase(type))) || (cnd_grp.equalsIgnoreCase(cur_grp_nm)))
/*     */         {
/*  77 */           group_pre = "SERVER_" + idx;
/*     */ 
/*  79 */           cur_grp = body.addNode(group_pre);
/*     */ 
/*  81 */           cur_grp.setChildValue("SVR_NM", cur_server_nm);
/*  82 */           cur_grp.setChildValue("SVR_DESC", serverNode.attributeValue("s_desc"));
/*  83 */           cur_grp.setChildValue("START_MIN", serverNode.attributeValue("s_min"));
/*  84 */           cur_grp.setChildValue("START_MAX", serverNode.attributeValue("s_max"));
/*  85 */           cur_grp.setChildValue("GRP_NM", cur_grp_nm);
/*  86 */           cur_grp.setChildValue("GRP_DESC", cur_grp_desc);
/*     */ 
/*  88 */           run_flg = serverNode.attributeValue("r_num");
/*  89 */           cur_grp.setChildValue("RUN_NUM", run_flg);
/*  90 */           if ("0".equals(run_flg))
/*  91 */             cur_grp.setChildValue("RUN_FLG", "0");
/*     */           else {
/*  93 */             cur_grp.setChildValue("RUN_FLG", "1");
/*     */           }
/*  95 */           cur_grp.setChildValue("SVR_TYP", serverNode.attributeValue("type"));
/*  96 */           cur_grp.setChildValue("LOG_LVL", serverNode.attributeValue("l_flag"));
/*     */ 
/*  98 */           ++idx;
/*     */         }
/*  68 */         if (!(svrIt.hasNext()))
/*     */         {
/* 100 */           label409: return 0; } }
/*     */     }
/* 102 */     if (serverStsMap != null) {
/* 103 */       serverStsMap.clear();
/*     */     } else {
/* 105 */       serverStsMap = new HashMap();
/* 106 */       ctx.getServerContext().setProperty("_SERVER_LIST", serverStsMap);
/*     */     }
/*     */ 
/* 110 */     String cmd = "<ROOT><CODE>090809</CODE></ROOT>";
/* 111 */     byte[] result = (byte[])null;
/* 112 */     Element root = null;
/*     */     try {
/* 114 */       result = HiServiceRequest.callService(host, port, 8, cmd.getBytes());
/* 115 */       root = DocumentHelper.parseText(new String(result)).getRootElement();
/*     */     } catch (IOException e) {
/* 117 */       HiException.makeException(e);
/*     */     } catch (DocumentException e) {
/* 119 */       HiException.makeException(e);
/*     */     }
/*     */ 
/* 123 */     Iterator it = root.elementIterator("s_g");
/*     */ 
/* 125 */     int g_idx = 1;
/*     */ 
/* 127 */     List groupList = null;
/* 128 */     if (isGetGrpList) {
/* 129 */       groupList = new ArrayList();
/*     */     }
/* 131 */     int getServerStsNum = 0;
/* 132 */     Map serverGroupMap = new HashMap();
/*     */ 
/* 134 */     idx = 1;
/* 135 */     StringBuffer serverStsSb = new StringBuffer("<ROOT><CODE>090829</CODE>");
/* 136 */     while (it.hasNext()) {
/* 137 */       serverNode = (Element)it.next();
/* 138 */       cur_grp_nm = serverNode.attributeValue("g_name");
/* 139 */       cur_grp_desc = serverNode.attributeValue("g_desc");
/* 140 */       cur_server_nm = serverNode.attributeValue("s_name").trim();
/*     */ 
/* 142 */       serverStsMap.put(cur_server_nm, serverNode);
/*     */ 
/* 144 */       if ((isGetGrpList) && (!(groupList.contains(cur_grp_nm)))) {
/* 145 */         groupList.add(cur_grp_nm);
/* 146 */         body.setGrandChildNode("GRP_" + g_idx + ".GRP_NM", cur_grp_nm);
/* 147 */         body.setGrandChildNode("GRP_" + g_idx + ".GRP_DESC", cur_grp_desc);
/* 148 */         ++g_idx;
/*     */       }
/*     */ 
/* 151 */       run_flg = serverNode.attributeValue("r_num");
/*     */ 
/* 153 */       if (!("0".equals(run_flg))) {
/* 154 */         serverStsSb.append("<server>");
/* 155 */         serverStsSb.append(cur_server_nm);
/* 156 */         serverStsSb.append("</server>");
/*     */ 
/* 158 */         ++getServerStsNum;
/*     */       }
/*     */ 
/* 161 */       if (("g".equalsIgnoreCase(type)) && (!(cnd_grp.equalsIgnoreCase(cur_grp_nm)))) {
/*     */         continue;
/*     */       }
/*     */ 
/* 165 */       group_pre = "SERVER_" + idx;
/*     */ 
/* 167 */       cur_grp = body.addNode(group_pre);
/*     */ 
/* 169 */       cur_grp.setChildValue("SVR_NM", cur_server_nm);
/* 170 */       cur_grp.setChildValue("SVR_DESC", serverNode.attributeValue("s_desc"));
/* 171 */       cur_grp.setChildValue("START_MIN", serverNode.attributeValue("s_min"));
/* 172 */       cur_grp.setChildValue("START_MAX", serverNode.attributeValue("s_max"));
/* 173 */       cur_grp.setChildValue("GRP_NM", cur_grp_nm);
/* 174 */       cur_grp.setChildValue("GRP_DESC", cur_grp_desc);
/* 175 */       cur_grp.setChildValue("RUN_NUM", run_flg);
/* 176 */       if ("0".equals(run_flg))
/* 177 */         cur_grp.setChildValue("RUN_FLG", "0");
/*     */       else {
/* 179 */         cur_grp.setChildValue("RUN_FLG", "1");
/*     */       }
/* 181 */       serverGroupMap.put(cur_server_nm, cur_grp);
/*     */ 
/* 183 */       ++idx;
/*     */     }
/* 185 */     serverStsSb.append("</ROOT>");
/*     */ 
/* 187 */     if (getServerStsNum == 0)
/*     */     {
/* 189 */       return 0;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 194 */       result = HiServiceRequest.callService(host, port, 8, serverStsSb.toString().getBytes());
/* 195 */       root = DocumentHelper.parseText(new String(result)).getRootElement();
/*     */     } catch (IOException e) {
/* 197 */       HiException.makeException(e);
/*     */     } catch (DocumentException e) {
/* 199 */       HiException.makeException(e);
/*     */     }
/*     */ 
/* 202 */     it = root.elementIterator("server");
/*     */ 
/* 204 */     while (it.hasNext()) {
/* 205 */       Element typeNode = (Element)it.next();
/* 206 */       cur_server_nm = typeNode.attributeValue("name").trim();
/* 207 */       serverNode = (Element)serverStsMap.get(cur_server_nm);
/* 208 */       cur_grp = (HiETF)serverGroupMap.get(cur_server_nm);
/*     */ 
/* 210 */       if (serverNode == null)
/*     */         continue;
/* 212 */       serverNode.addAttribute("type", typeNode.elementText("type"));
/* 213 */       serverNode.addAttribute("l_flag", typeNode.elementText("l_flag"));
/* 214 */       if (cur_grp != null) {
/* 215 */         cur_grp.setChildValue("SVR_TYP", typeNode.elementText("type"));
/* 216 */         cur_grp.setChildValue("LOG_LVL", typeNode.elementText("l_flag"));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 223 */     return 0;
/*     */   }
/*     */ }