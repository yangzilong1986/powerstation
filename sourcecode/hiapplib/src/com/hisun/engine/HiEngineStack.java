/*     */ package com.hisun.engine;
/*     */ 
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.engine.invoke.impl.HiTransactionCTLImpl;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.util.Stack;
/*     */ 
/*     */ public class HiEngineStack extends Stack
/*     */   implements Runnable
/*     */ {
/*     */   private static final String KEY = "ENGINE_STACK";
/*     */   private HiMessageContext ctx;
/*     */ 
/*     */   public static HiEngineStack getEngineStack(HiMessageContext ctx)
/*     */   {
/*  20 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */ 
/*  22 */     Object obj = ctx.getProperty("ENGINE_STACK");
/*  23 */     if (obj == null)
/*     */     {
/*  25 */       if (log.isInfoEnabled())
/*     */       {
/*  27 */         log.info("HiEngineStack is start");
/*     */       }
/*  29 */       HiEngineStack esk = new HiEngineStack();
/*  30 */       ctx.setProperty("ENGINE_STACK", esk);
/*  31 */       return esk;
/*     */     }
/*     */ 
/*  34 */     return ((HiEngineStack)obj);
/*     */   }
/*     */ 
/*     */   public void setMessContext(HiMessageContext ctx)
/*     */   {
/*  42 */     this.ctx = ctx;
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*  47 */     this.ctx.setProperty("ENGINE_STACK", null);
/*  48 */     HiMessageContext.setCurrentMessageContext(this.ctx);
/*  49 */     Logger log = HiLog.getLogger(this.ctx.getCurrentMsg());
/*  50 */     if (log.isDebugEnabled())
/*     */     {
/*  52 */       for (int i = 0; i < size(); ++i)
/*     */       {
/*  54 */         log.debug("stack:" + get(i).getClass());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  72 */     HiEngineModel lastEn = (HiEngineModel)pop();
/*  73 */     this.ctx.setProperty("NoResponseObj", this);
/*     */     try {
/*  75 */       if (lastEn instanceof HiTransactionCTLImpl)
/*  76 */         ((HiTransactionCTLImpl)lastEn).doProcess(this.ctx, this);
/*     */     }
/*     */     catch (Throwable e) {
/*  79 */       log.warn(e);
/*     */     } finally {
/*  81 */       if (this.ctx.getDataBaseUtil() != null)
/*  82 */         this.ctx.getDataBaseUtil().closeAll();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static HiEngineModel getCurrentEngineModel(HiMessageContext ctx)
/*     */   {
/*  89 */     HiEngineStack stack = (HiEngineStack)ctx.getProperty("NoResponseObj");
/*     */ 
/*  93 */     HiEngineModel en = (HiEngineModel)stack.pop();
/*  94 */     return en;
/*     */   }
/*     */ 
/*     */   public static HiEngineStack getCurrentStack(HiMessageContext ctx) {
/*  98 */     HiEngineStack stack = (HiEngineStack)ctx.getProperty("NoResponseObj");
/*     */ 
/* 101 */     if ((stack != null) && (stack.isEmpty()))
/* 102 */       return null;
/* 103 */     return stack;
/*     */   }
/*     */ }