/*    */ package com.hisun.engine.pojo;
/*    */ 
/*    */ import com.hisun.engine.invoke.HiIAction;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiStringManager;
/*    */ 
/*    */ public abstract class HiPojoEngineModel
/*    */   implements HiIAction
/*    */ {
/*    */   protected HiContext context;
/* 16 */   protected static final HiStringManager sm = HiStringManager.getManager();
/*    */ 
/*    */   public HiPojoEngineModel()
/*    */   {
/* 14 */     this.context = null;
/*    */   }
/*    */ 
/*    */   public void loadAfter()
/*    */     throws HiException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void afterProcess(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/* 26 */     if (this.context != null)
/* 27 */       messContext.popParent();
/*    */   }
/*    */ 
/*    */   public void beforeProcess(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/* 34 */     if (this.context != null)
/* 35 */       messContext.pushParent(this.context);
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/*    */   }
/*    */ 
/*    */   public void popOwnerContext()
/*    */   {
/* 46 */     HiContext.popCurrentContext();
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 51 */     return getNodeName();
/*    */   }
/*    */ }