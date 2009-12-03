/*     */ package com.hisun.atc;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.atc.common.HiDbtSqlHelper;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ public class HiLoadData
/*     */ {
/*     */   public int QueryLoadData(HiATLParam args, HiMessageContext ctx)
/*     */   {
/*  38 */     return 0;
/*     */   }
/*     */ 
/*     */   public int LoadData(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     HiContext ctx1;
/*  63 */     boolean escape = args.getBoolean("escape");
/*  64 */     String sqlCmd = HiArgUtils.getStringNotNull(args, "sqlCmd");
/*  65 */     String storeKey = HiArgUtils.getStringNotNull(args, "storeKey");
/*  66 */     String type = args.get("type");
/*  67 */     String scope = args.get("scope");
/*  68 */     String keyNam = args.get("keyNam");
/*  69 */     String valNam = args.get("valNam");
/*     */ 
/*  71 */     HiMessage msg = ctx.getCurrentMsg();
/*  72 */     HiETF root = msg.getETFBody();
/*  73 */     Logger log = HiLog.getLogger(msg);
/*  74 */     String sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, sqlCmd, root, escape);
/*     */ 
/*  76 */     List queryRs = ctx.getDataBaseUtil().execQuery(sqlSentence);
/*     */ 
/*  78 */     if ("GLOBAL".equalsIgnoreCase(scope))
/*  79 */       ctx1 = HiMessageContext.getRootContext();
/*     */     else {
/*  81 */       ctx1 = ctx.getServerContext();
/*     */     }
/*  83 */     if ("HashMap".equalsIgnoreCase(type)) {
/*  84 */       ctx1.setProperty(storeKey, listHashMap2HashMap(queryRs, keyNam, valNam));
/*     */     }
/*  86 */     else if ("ListHashMap".equalsIgnoreCase(type)) {
/*  87 */       ctx1.setProperty(storeKey, queryRs);
/*     */     }
/*  89 */     return 0;
/*     */   }
/*     */ 
/*     */   private HashMap listHashMap2HashMap(List listHashMap, String keyNam, String valNam)
/*     */   {
/*  99 */     HashMap map = new HashMap();
/* 100 */     for (int i = 0; i < listHashMap.size(); ++i) {
/* 101 */       HashMap tmpMap = (HashMap)listHashMap.get(i);
/* 102 */       map.put(tmpMap.get(keyNam), tmpMap.get(valNam));
/*     */     }
/* 104 */     return map;
/*     */   }
/*     */ }