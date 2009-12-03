/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineUtilities;
/*    */ import com.hisun.engine.HiITFEngineModel;
/*    */ import com.hisun.engine.exception.HiReturnException;
/*    */ import com.hisun.engine.invoke.HiIAction;
/*    */ import com.hisun.engine.invoke.HiIStrategy;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import java.util.List;
/*    */ 
/*    */ public abstract class HiAbstractRqAndRp extends HiITFEngineModel
/*    */ {
/* 29 */   private final Logger logger = (Logger)HiContext.getCurrentContext().getProperty("SVR.log");
/*    */ 
/* 38 */   private HiContext context = HiContext.getCurrentContext();
/*    */ 
/*    */   public HiAbstractRqAndRp()
/*    */   {
/* 34 */     super.setItemAttribute(HiContext.getCurrentContext());
/*    */   }
/*    */ 
/*    */   public void beforeProcess(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/* 44 */     HiIStrategy strategy = (HiIStrategy)messContext.getProperty("STRATEGY_KEY");
/*    */ 
/* 46 */     if (strategy != null)
/* 47 */       strategy.beforeConverMess(messContext);
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 53 */     HiMessage msg = ctx.getCurrentMsg();
/*    */ 
/* 55 */     if (super.getChilds() == null) {
/* 56 */       return;
/*    */     }
/*    */     try
/*    */     {
/* 60 */       for (int i = 0; i < super.getChilds().size(); ++i)
/*    */       {
/* 62 */         HiIAction child = (HiIAction)super.getChilds().get(i);
/*    */ 
/* 64 */         HiEngineUtilities.setCurFlowStep(i);
/* 65 */         HiProcess.process(child, ctx);
/*    */       }
/*    */     }
/*    */     catch (HiReturnException e)
/*    */     {
/*    */     }
/*    */   }
/*    */ 
/*    */   public void afterProcess(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/* 77 */     HiMessage mess = messContext.getCurrentMsg();
/* 78 */     Logger log = HiLog.getLogger(mess);
/* 79 */     if (log.isDebugEnabled())
/*    */     {
/* 81 */       log.debug("HiAbstractRqAndRp:afterProcess(HiMessageContext) - start");
/*    */     }
/*    */ 
/* 85 */     HiIStrategy strategy = (HiIStrategy)messContext.getProperty("STRATEGY_KEY");
/*    */ 
/* 87 */     if (strategy != null) {
/* 88 */       strategy.afterConverMess(messContext);
/*    */     }
/* 90 */     if (!(log.isDebugEnabled()))
/*    */       return;
/* 92 */     log.debug("HiAbstractRqAndRp:afterProcess(HiMessageContext) - end" + mess.getBody().getClass());
/*    */   }
/*    */ }