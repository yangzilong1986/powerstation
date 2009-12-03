/*     */ package com.hisun.mng;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.register.HiRegisterService;
/*     */ import com.hisun.register.HiServiceObject;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiServiceManage
/*     */ {
/*     */   public int serviceMonSwitch(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  48 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*  49 */     String tmp = HiArgUtils.getStringNotNull(args, "flag");
/*  50 */     boolean flag = false;
/*  51 */     if ("1".equals(tmp)) {
/*  52 */       flag = true;
/*     */     }
/*  54 */     String svrTyp = args.get("svrTyp");
/*     */ 
/*  56 */     ArrayList list = getServiceList(args, ctx);
/*  57 */     for (int i = 0; i < list.size(); ++i) {
/*  58 */       HiServiceObject serviceObject = (HiServiceObject)list.get(i);
/*  59 */       if ((!(StringUtils.isBlank(svrTyp))) && (!(StringUtils.equalsIgnoreCase(svrTyp, serviceObject.getServerType())))) {
/*     */         continue;
/*     */       }
/*  62 */       HiRegisterService.setMonSwitch(serviceObject.getServiceCode(), flag);
/*     */     }
/*     */ 
/*  66 */     return 0;
/*     */   }
/*     */ 
/*     */   public int serviceLogSwitch(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  84 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*  85 */     String type = HiArgUtils.getStringNotNull(args, "type");
/*  86 */     String logLvl = HiArgUtils.getStringNotNull(args, "flag");
/*  87 */     String svrTyp = args.get("svrTyp");
/*     */ 
/*  89 */     ArrayList list = getServiceList(args, ctx);
/*  90 */     for (int i = 0; i < list.size(); ++i) {
/*  91 */       HiServiceObject serviceObject = (HiServiceObject)list.get(i);
/*  92 */       if ((!(StringUtils.isBlank(svrTyp))) && (!(StringUtils.equalsIgnoreCase(svrTyp, serviceObject.getServerType())))) {
/*     */         continue;
/*     */       }
/*  95 */       HiRegisterService.setLogSwitch(serviceObject.getServiceCode(), logLvl);
/*     */     }
/*     */ 
/*  99 */     return 0;
/*     */   }
/*     */ 
/*     */   public int oneServiceQuery(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 113 */     String code = HiArgUtils.getStringNotNull(args, "Code");
/* 114 */     String curNod = args.get("curNod");
/* 115 */     if (StringUtils.isBlank(curNod)) {
/* 116 */       curNod = "ROOT";
/*     */     }
/* 118 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 119 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/*     */     try {
/* 121 */       HiServiceObject serviceObject = HiRegisterService.getService(code);
/* 122 */       root.setGrandChildNode(curNod + ".LOG_LVL", serviceObject.getLogLevel());
/*     */ 
/* 124 */       root.setGrandChildNode(curNod + ".MON_SW", serviceObject.getMonSwitch());
/*     */ 
/* 126 */       root.setGrandChildNode(curNod + ".RUN_FLG", serviceObject.getRunning());
/*     */     }
/*     */     catch (HiException e) {
/* 129 */       root.setGrandChildNode(curNod + ".LOG_LVL", "0");
/* 130 */       root.setGrandChildNode(curNod + ".MON_SW", "0");
/* 131 */       root.setGrandChildNode(curNod + ".RUN_FLG", "0");
/*     */     }
/* 133 */     return 0;
/*     */   }
/*     */ 
/*     */   public int serviceQuery(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 149 */     String svrTyp = args.get("svrTyp");
/* 150 */     String grpNam = args.get("grpNam");
/* 151 */     if (StringUtils.isBlank(grpNam)) {
/* 152 */       grpNam = "GRP";
/*     */     }
/*     */ 
/* 155 */     ArrayList list = getServiceList(args, ctx);
/*     */ 
/* 157 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 158 */     int k = 1;
/* 159 */     for (int i = 0; i < list.size(); ++i) {
/* 160 */       HiServiceObject serviceObject = (HiServiceObject)list.get(i);
/* 161 */       if ((!(StringUtils.isBlank(svrTyp))) && (!(StringUtils.equalsIgnoreCase(svrTyp, serviceObject.getServerType())))) {
/*     */         continue;
/*     */       }
/* 164 */       HiETF grpNode = root.getChildNode(grpNam + "_" + k);
/* 165 */       if (grpNode == null) {
/* 166 */         grpNode = root.addNode(grpNam + "_" + k);
/*     */       }
/* 168 */       oneServiceQuery(serviceObject, grpNode);
/* 169 */       ++k;
/*     */     }
/*     */ 
/* 172 */     root.setChildValue(grpNam + "_NUM", String.valueOf(k - 1));
/* 173 */     return 0;
/*     */   }
/*     */ 
/*     */   public void oneServiceQuery(HiServiceObject serviceObject, HiETF node)
/*     */   {
/* 178 */     node.setChildValue("APP_CD", serviceObject.getAppCode());
/* 179 */     node.setChildValue("APP_NM", serviceObject.getAppName());
/* 180 */     node.setChildValue("DESC", serviceObject.getDesc());
/* 181 */     node.setChildValue("LOG_LVL", serviceObject.getLogLevel());
/* 182 */     node.setChildValue("MON_SW", serviceObject.getMonSwitch());
/* 183 */     node.setChildValue("RUN_FLG", serviceObject.getRunning());
/* 184 */     node.setChildValue("SVR_NM", serviceObject.getServerName());
/* 185 */     node.setChildValue("SVR_TYP", serviceObject.getServerType());
/* 186 */     node.setChildValue("SVC_CD", serviceObject.getServiceCode());
/* 187 */     node.setChildValue("SVC_TM", serviceObject.getTime());
/*     */   }
/*     */ 
/*     */   public int serviceManage(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 203 */     String svrTyp = args.get("svrTyp");
/*     */ 
/* 205 */     String mode = HiArgUtils.getStringNotNull(args, "mode");
/*     */ 
/* 207 */     ArrayList list = getServiceList(args, ctx);
/*     */ 
/* 209 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/* 210 */     int k = 1;
/*     */     try {
/* 212 */       for (int i = 0; i < list.size(); ++i) {
/* 213 */         HiServiceObject serviceObject = (HiServiceObject)list.get(i);
/* 214 */         if ((!(StringUtils.isBlank(svrTyp))) && (!(StringUtils.equalsIgnoreCase(svrTyp, serviceObject.getServerType())))) {
/*     */           continue;
/*     */         }
/* 217 */         if (StringUtils.equalsIgnoreCase(mode, "start"))
/* 218 */           HiRegisterService.start(serviceObject.getServiceCode());
/*     */         else
/* 220 */           HiRegisterService.stop(serviceObject.getServiceCode());
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 225 */       HiMessageContext.setCurrentMessageContext(ctx);
/*     */     }
/* 227 */     return 0;
/*     */   }
/*     */ 
/*     */   private ArrayList getServiceList(HiATLParam args, HiMessageContext ctx) throws HiException
/*     */   {
/* 232 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 233 */     ArrayList list = null;
/* 234 */     String type = HiArgUtils.getStringNotNull(args, "type");
/*     */ 
/* 236 */     if (StringUtils.equalsIgnoreCase(type, "a")) {
/* 237 */       list = HiRegisterService.listServices("");
/*     */     }
/*     */     else
/*     */     {
/*     */       String name;
/*     */       String[] tmps;
/*     */       int i;
/*     */       ArrayList list1;
/* 238 */       if (StringUtils.equalsIgnoreCase(type, "g")) {
/* 239 */         name = HiArgUtils.getStringNotNull(args, "name");
/* 240 */         tmps = name.split("\\|");
/* 241 */         list = new ArrayList();
/* 242 */         for (i = 0; i < tmps.length; ++i) {
/* 243 */           list1 = HiRegisterService.listServicesByAppName(tmps[i]);
/*     */ 
/* 245 */           if (list1 == null) {
/* 246 */             log.info("application:[" + tmps[i] + "] not existed!");
/*     */           }
/*     */           else
/* 249 */             list.addAll(list1);
/*     */         }
/* 251 */       } else if (StringUtils.equalsIgnoreCase(type, "s")) {
/* 252 */         name = HiArgUtils.getStringNotNull(args, "name");
/* 253 */         tmps = name.split("\\|");
/* 254 */         list = new ArrayList();
/* 255 */         for (i = 0; i < tmps.length; ++i) {
/* 256 */           list1 = HiRegisterService.listServicesBySvrName(tmps[i]);
/*     */ 
/* 258 */           if (list1 == null) {
/* 259 */             log.info("server:[" + tmps[i] + "] not existed!");
/*     */           }
/*     */           else
/* 262 */             list.addAll(list1);
/*     */         }
/* 264 */       } else if (StringUtils.equalsIgnoreCase(type, "t")) {
/* 265 */         name = HiArgUtils.getStringNotNull(args, "name");
/* 266 */         tmps = name.split("\\|");
/* 267 */         list = new ArrayList();
/* 268 */         for (i = 0; i < tmps.length; ++i) {
/* 269 */           HiServiceObject serviceObject = HiRegisterService.getService(tmps[i]);
/*     */ 
/* 271 */           if (serviceObject == null) {
/* 272 */             log.info("transaction:[" + tmps[i] + "] not existed!");
/*     */           }
/*     */           else
/* 275 */             list.add(HiRegisterService.getService(tmps[i])); 
/*     */         }
/*     */       }
/*     */     }
/* 278 */     return list;
/*     */   }
/*     */ }