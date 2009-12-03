/*     */ package com.hisun.atc;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ 
/*     */ public class HiDataObject
/*     */ {
/*     */   public int CreateDataObject(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  33 */     String type = HiArgUtils.getStringNotNull(argsMap, "Type");
/*  34 */     String name = HiArgUtils.getStringNotNull(argsMap, "Name");
/*  35 */     if (HiDataSource.get(ctx, name) != null) {
/*  36 */       throw new HiException("220319", name);
/*     */     }
/*  38 */     HiDataSource.create(ctx, name, type);
/*  39 */     return 0;
/*     */   }
/*     */ 
/*     */   public int DeleteDataObject(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  53 */     String name = HiArgUtils.getStringNotNull(argsMap, "Name");
/*  54 */     Object o = null;
/*  55 */     if ((o = HiDataSource.get(ctx, name)) == null) {
/*  56 */       return 2;
/*     */     }
/*     */ 
/*  59 */     if (o == ctx.getResponseMsg().getBody()) {
/*  60 */       throw new HiException("220323", name);
/*     */     }
/*  62 */     HiDataSource.delete(ctx, name);
/*  63 */     return 0;
/*     */   }
/*     */ 
/*     */   public int SetOutputArea(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  79 */     String areaName = HiArgUtils.getStringNotNull(argsMap, "AreaName");
/*  80 */     Object ds = HiDataSource.get(ctx, areaName);
/*  81 */     if ((ds != null) && (!(ds instanceof HiETF))) {
/*  82 */       throw new HiException("220321", areaName, "ETF");
/*     */     }
/*     */ 
/*  85 */     if (ds == null) {
/*  86 */       ds = HiDataSource.create(ctx, areaName, "ETF");
/*     */     }
/*  88 */     HiMessage msg = new HiMessage(ctx.getCurrentMsg());
/*  89 */     msg.setBody(ds);
/*  90 */     ctx.setResponseMsg(msg);
/*  91 */     return 0;
/*     */   }
/*     */ 
/*     */   public int ClearOutputArea(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 103 */     HiMessage msg = ctx.getResponseMsg();
/* 104 */     if (msg == ctx.getCurrentMsg()) {
/* 105 */       return 2;
/*     */     }
/*     */ 
/* 108 */     Object o = HiETFFactory.createETF();
/* 109 */     String name = HiDataSource.getName(ctx, msg.getBody());
/* 110 */     HiDataSource.set(ctx, name, o);
/* 111 */     msg.setBody(o);
/* 112 */     return 0;
/*     */   }
/*     */ 
/*     */   public int ResetOutputArea(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 128 */     HiMessage msg = ctx.popResponseMsg();
/* 129 */     if (msg == null) {
/* 130 */       return 2;
/*     */     }
/* 132 */     String flag = argsMap.get("DeleteFlag");
/* 133 */     if ("Y".equalsIgnoreCase(flag)) {
/* 134 */       HiDataSource.delete(ctx, HiDataSource.getName(ctx, msg.getBody()));
/*     */     }
/* 136 */     return 0;
/*     */   }
/*     */ }