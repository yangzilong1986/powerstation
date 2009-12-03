/*     */ package com.hisun.mon.atc;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.mon.HiServiceRequest;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class ServerLogSwitch
/*     */ {
/*     */   public int execute(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     String cur_server_nm;
/*     */     Element serverNode;
/*  31 */     String host = HiArgUtils.getStringNotNull(args, "host");
/*  32 */     int port = Integer.parseInt(HiArgUtils.getStringNotNull(args, "port"));
/*     */ 
/*  35 */     String mode = HiArgUtils.getStringNotNull(args, "mode");
/*     */ 
/*  37 */     String name = HiArgUtils.getStringNotNull(args, "name");
/*     */ 
/*  39 */     String type = args.get("type");
/*     */ 
/*  41 */     String cnd_grp = null;
/*  42 */     if ("g".equalsIgnoreCase(type)) {
/*  43 */       cnd_grp = HiArgUtils.getStringNotNull(args, "group");
/*     */     }
/*     */ 
/*  46 */     StringBuffer reqCmd = new StringBuffer("<ROOT>");
/*  47 */     StringBuffer reqStsCmd = new StringBuffer("<ROOT><CODE>090829</CODE>");
/*  48 */     if ("1".equalsIgnoreCase(mode))
/*  49 */       reqCmd.append("<CODE>090821</CODE>");
/*     */     else {
/*  51 */       reqCmd.append("<CODE>090822</CODE>");
/*     */     }
/*  53 */     String[] nameArr = StringUtils.split(name, '|');
/*  54 */     for (int i = 0; i < nameArr.length; ++i) {
/*  55 */       reqCmd.append("<server>");
/*  56 */       reqCmd.append(nameArr[i]);
/*  57 */       reqCmd.append("</server>");
/*     */ 
/*  59 */       reqStsCmd.append("<server>");
/*  60 */       reqStsCmd.append(nameArr[i]);
/*  61 */       reqStsCmd.append("</server>");
/*     */     }
/*  63 */     reqCmd.append("</ROOT>");
/*  64 */     reqStsCmd.append("</ROOT>");
/*     */ 
/*  66 */     byte[] result = (byte[])null;
/*  67 */     Element root = null;
/*     */     try {
/*  69 */       result = HiServiceRequest.callService(host, port, 8, reqCmd.toString().getBytes());
/*  70 */       root = DocumentHelper.parseText(new String(result)).getRootElement();
/*     */     } catch (IOException e) {
/*  72 */       HiException.makeException(e);
/*     */     } catch (DocumentException e) {
/*  74 */       HiException.makeException(e);
/*     */     }
/*  76 */     "000000".equals(root.elementText("RspCod"));
/*     */ 
/*  81 */     Map serverStsMap = (Map)ctx.getServerContext().getProperty("_SERVER_LIST");
/*     */     try {
/*  83 */       result = HiServiceRequest.callService(host, port, 8, reqStsCmd.toString().getBytes());
/*  84 */       root = DocumentHelper.parseText(new String(result)).getRootElement();
/*     */     } catch (IOException e) {
/*  86 */       HiException.makeException(e);
/*     */     } catch (DocumentException e) {
/*  88 */       HiException.makeException(e);
/*     */     }
/*  90 */     Iterator it = root.elementIterator("server");
/*     */ 
/*  98 */     int idx = 1;
/*     */ 
/* 100 */     while (it.hasNext()) {
/* 101 */       Element typeNode = (Element)it.next();
/* 102 */       cur_server_nm = typeNode.attributeValue("name").trim();
/* 103 */       serverNode = (Element)serverStsMap.get(cur_server_nm);
/*     */ 
/* 105 */       if (serverNode == null)
/*     */         continue;
/* 107 */       serverNode.addAttribute("type", typeNode.elementText("type"));
/* 108 */       serverNode.addAttribute("l_flag", typeNode.elementText("l_flag"));
/*     */     }
/*     */ 
/* 113 */     HiETF body = ctx.getCurrentMsg().getETFBody();
/* 114 */     Iterator svrIt = serverStsMap.values().iterator();
/* 115 */     while (svrIt.hasNext()) {
/* 116 */       serverNode = (Element)svrIt.next();
/* 117 */       String cur_grp_nm = serverNode.attributeValue("g_name");
/* 118 */       String cur_grp_desc = serverNode.attributeValue("g_desc");
/* 119 */       cur_server_nm = serverNode.attributeValue("s_name");
/* 120 */       if (("g".equalsIgnoreCase(type)) && (!(cnd_grp.equalsIgnoreCase(cur_grp_nm)))) {
/*     */         continue;
/*     */       }
/*     */ 
/* 124 */       String group_pre = "SERVER_" + idx;
/*     */ 
/* 126 */       HiETF cur_grp = body.addNode(group_pre);
/*     */ 
/* 128 */       cur_grp.setChildValue("SVR_NM", cur_server_nm);
/* 129 */       cur_grp.setChildValue("SVR_DESC", serverNode.attributeValue("s_desc"));
/* 130 */       cur_grp.setChildValue("START_MIN", serverNode.attributeValue("s_min"));
/* 131 */       cur_grp.setChildValue("START_MAX", serverNode.attributeValue("s_max"));
/* 132 */       cur_grp.setChildValue("GRP_NM", cur_grp_nm);
/* 133 */       cur_grp.setChildValue("GRP_DESC", cur_grp_desc);
/*     */ 
/* 135 */       String run_flg = serverNode.attributeValue("r_num");
/* 136 */       cur_grp.setChildValue("RUN_NUM", run_flg);
/* 137 */       if ("0".equals(run_flg))
/* 138 */         cur_grp.setChildValue("RUN_FLG", "0");
/*     */       else {
/* 140 */         cur_grp.setChildValue("RUN_FLG", "1");
/*     */       }
/* 142 */       cur_grp.setChildValue("SVR_TYP", serverNode.attributeValue("type"));
/* 143 */       cur_grp.setChildValue("LOG_LVL", serverNode.attributeValue("l_flag"));
/*     */ 
/* 145 */       ++idx;
/*     */     }
/* 147 */     return 0;
/*     */   }
/*     */ }