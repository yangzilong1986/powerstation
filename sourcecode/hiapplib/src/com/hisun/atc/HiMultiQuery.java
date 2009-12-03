/*     */ package com.hisun.atc;
/*     */ 
/*     */ import com.hisun.atc.common.HiMultiQueryLib;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiMultiQuery
/*     */ {
/*  22 */   private static HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*     */   public int MultiQuery(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  50 */     HiMessage msg = ctx.getCurrentMsg();
/*  51 */     Logger log = HiLog.getLogger(msg);
/*  52 */     if (log.isInfoEnabled())
/*     */     {
/*  54 */       log.info(sm.getString("HiMultiQuery.MultiQuery", "MultiQuery"));
/*     */     }
/*  56 */     String strTiaTyp = msg.getETFBody().getChildValue("TIA_TYP");
/*  57 */     if (log.isInfoEnabled())
/*     */     {
/*  59 */       log.info(sm.getString("HiMultiQuery.MultiQuery.Type", strTiaTyp));
/*     */     }
/*     */ 
/*  62 */     if (!(StringUtils.equalsIgnoreCase(strTiaTyp, "P"))) {
/*  63 */       return HiMultiQueryLib.queryFirst(args, msg, ctx);
/*     */     }
/*  65 */     return HiMultiQueryLib.queryNext(args, msg, ctx);
/*     */   }
/*     */ 
/*     */   public int QueryExt(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  99 */     HiMessage msg = ctx.getCurrentMsg();
/* 100 */     Logger log = HiLog.getLogger(msg);
/* 101 */     if (log.isInfoEnabled())
/*     */     {
/* 103 */       log.info(sm.getString("HiMultiQuery.MultiQuery", "QueryExt"));
/*     */     }
/* 105 */     String strTiaTyp = msg.getETFBody().getChildValue("TIA_TYP");
/* 106 */     if (log.isInfoEnabled())
/*     */     {
/* 108 */       log.info(sm.getString("HiMultiQuery.MultiQuery.Type", strTiaTyp));
/*     */     }
/*     */ 
/* 111 */     if (!(StringUtils.equalsIgnoreCase(strTiaTyp, "P"))) {
/* 112 */       return HiMultiQueryLib.QueryFirstExt(args, msg, ctx);
/*     */     }
/* 114 */     return HiMultiQueryLib.queryNext(args, msg, ctx);
/*     */   }
/*     */ 
/*     */   public int MultiQueryFromFile(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 143 */     HiMessage msg = ctx.getCurrentMsg();
/* 144 */     Logger log = HiLog.getLogger(msg);
/* 145 */     if (log.isInfoEnabled())
/*     */     {
/* 147 */       log.info(sm.getString("HiMultiQuery.MultiQuery", "MultiQuery"));
/*     */     }
/* 149 */     String strTiaTyp = msg.getETFBody().getChildValue("TIA_TYP");
/* 150 */     if (log.isInfoEnabled())
/*     */     {
/* 152 */       log.info(sm.getString("HiMultiQuery.MultiQuery.Type", strTiaTyp));
/*     */     }
/*     */ 
/* 155 */     if (!(StringUtils.equalsIgnoreCase(strTiaTyp, "P"))) {
/* 156 */       return HiMultiQueryLib.MultiQueryFromFile(args, ctx);
/*     */     }
/* 158 */     return HiMultiQueryLib.queryNext(args, msg, ctx);
/*     */   }
/*     */ }