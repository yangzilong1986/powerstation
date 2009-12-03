/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.engine.HiEngineUtilities;
/*    */ import com.hisun.engine.exception.HiBreakException;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiStringManager;
/*    */ 
/*    */ public class HiBreakProcess extends HiEngineModel
/*    */ {
/*    */   public void process(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/* 14 */     Logger log = HiLog.getLogger(messContext.getCurrentMsg());
/* 15 */     if (log.isInfoEnabled()) {
/* 16 */       log.info(sm.getString("HiBreakProcess.process00", HiEngineUtilities.getCurFlowStep()));
/*    */     }
/*    */ 
/* 20 */     throw new HiBreakException();
/*    */   }
/*    */ 
/*    */   public String getNodeName() {
/* 24 */     return "Break";
/*    */   }
/*    */ }