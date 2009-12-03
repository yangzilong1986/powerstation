/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineStack;
/*    */ import com.hisun.engine.HiEngineUtilities;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiStringManager;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiFunction extends HiTransactionCTLImpl
/*    */ {
/*    */   private String _strName;
/*    */   private String[] _inParams;
/*    */   private String[] _outParams;
/*    */ 
/*    */   public HiFunction()
/*    */   {
/* 22 */     this.isFunc = true;
/*    */   }
/*    */ 
/*    */   public void setName(String strName)
/*    */   {
/* 33 */     this._strName = strName;
/* 34 */     this.context.setId("FUNC." + strName);
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 39 */     return this._strName;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException
/*    */   {
/* 44 */     HiMessage msg = ctx.getCurrentMsg();
/* 45 */     Logger log = HiLog.getLogger(msg);
/* 46 */     HiETF root = msg.getETFBody();
/* 47 */     if (log.isInfo2Enabled()) {
/* 48 */       log.info2(this.sm.getString("HiFunction.process00", HiEngineUtilities.getCurFlowStep(), this._strName));
/*    */     }
/*    */ 
/* 51 */     HiEngineStack stack = HiEngineStack.getCurrentStack(ctx);
/* 52 */     super.doProcess(ctx, stack);
/*    */   }
/*    */ 
/*    */   public void beforeProcess(HiMessageContext ctx) throws HiException {
/* 56 */     if (this.context != null)
/* 57 */       ctx.pushParent(this.context);
/*    */   }
/*    */ 
/*    */   public void afterProcess(HiMessageContext ctx) throws HiException
/*    */   {
/* 62 */     if (this.context != null)
/* 63 */       ctx.popParent();
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 67 */     return "Function:[" + this._strName + "]";
/*    */   }
/*    */ 
/*    */   public String[] getInParams() {
/* 71 */     return this._inParams;
/*    */   }
/*    */ 
/*    */   public void setInput(String input) {
/* 75 */     this._inParams = StringUtils.split(input, " |");
/*    */   }
/*    */ 
/*    */   public String[] getOutParams() {
/* 79 */     return this._outParams;
/*    */   }
/*    */ 
/*    */   public void setOutput(String output) {
/* 83 */     this._outParams = StringUtils.split(output, " |");
/*    */   }
/*    */ }