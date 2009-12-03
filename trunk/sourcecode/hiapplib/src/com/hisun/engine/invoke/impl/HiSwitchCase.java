/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.engine.HiEngineStack;
/*    */ import com.hisun.engine.HiEngineUtilities;
/*    */ import com.hisun.engine.invoke.HiIAction;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.exception.HiResponseException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiStringManager;
/*    */ import java.util.List;
/*    */ 
/*    */ public class HiSwitchCase extends HiEngineModel
/*    */ {
/*    */   private String strValue;
/*    */ 
/*    */   public void setValue(String strValue)
/*    */   {
/* 20 */     this.strValue = strValue;
/*    */   }
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 26 */     return "Case";
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 32 */     return super.toString() + ":value[" + this.strValue + "]";
/*    */   }
/*    */ 
/*    */   public String getValue()
/*    */   {
/* 37 */     return this.strValue;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException
/*    */   {
/* 42 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 43 */     if (log.isInfoEnabled())
/*    */     {
/* 45 */       log.info(sm.getString("HiEngineModel.process00", HiEngineUtilities.getCurFlowStep(), getNodeName(), toString()));
/*    */     }
/*    */ 
/* 49 */     if (getChilds() == null)
/*    */     {
/* 51 */       return;
/*    */     }
/*    */ 
/* 54 */     HiEngineModel rsEn = null;
/* 55 */     HiEngineStack stack = HiEngineStack.getCurrentStack(ctx);
/* 56 */     if (stack != null)
/*    */     {
/* 58 */       rsEn = (HiEngineModel)stack.pop();
/*    */     }
/* 60 */     for (int i = 0; i < getChilds().size(); ++i)
/*    */     {
/* 62 */       HiIAction child = (HiIAction)getChilds().get(i);
/* 63 */       if (rsEn != null)
/*    */       {
/* 65 */         if (child != rsEn)
/*    */           continue;
/* 67 */         rsEn = null;
/* 68 */         if (stack.size() == 0)
/*    */           continue;
/*    */       }
/* 71 */       HiEngineUtilities.setCurFlowStep(i);
/*    */       try
/*    */       {
/* 74 */         HiProcess.process(child, ctx);
/*    */       }
/*    */       catch (HiResponseException e)
/*    */       {
/* 78 */         HiEngineStack.getEngineStack(ctx).push(this);
/* 79 */         throw e;
/*    */       }
/*    */     }
/*    */   }
/*    */ }