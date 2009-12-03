/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.engine.HiEngineUtilities;
/*    */ import com.hisun.engine.HiMessagePool;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiStringManager;
/*    */ 
/*    */ public class HiRestoreMsgHead extends HiEngineModel
/*    */ {
/* 24 */   private boolean ignore = false;
/*    */   private String strKey_name;
/*    */ 
/*    */   public HiRestoreMsgHead()
/*    */   {
/* 27 */     HiContext ctx = HiContext.getCurrentContext();
/* 28 */     HiMessagePool.setMessagePool(ctx.getServerContext());
/*    */   }
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 34 */     return "RestoreMsgHead ";
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 40 */     return super.toString() + ":key_name[" + this.strKey_name + "]";
/*    */   }
/*    */ 
/*    */   public void setKey_name(String strKey_name)
/*    */   {
/* 47 */     this.strKey_name = strKey_name;
/*    */   }
/*    */ 
/*    */   public void setIgnore(boolean ignore)
/*    */   {
/* 52 */     this.ignore = ignore;
/*    */   }
/*    */ 
/*    */   public boolean getIgnore()
/*    */   {
/* 57 */     return this.ignore;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext messContext) throws HiException
/*    */   {
/* 62 */     HiMessage mess = messContext.getCurrentMsg();
/* 63 */     Logger log = HiLog.getLogger(mess);
/* 64 */     if (log.isInfoEnabled()) {
/* 65 */       log.info(sm.getString("HiRestoreMsgHead.process00", HiEngineUtilities.getCurFlowStep(), this.strKey_name));
/*    */     }
/* 67 */     HiETF etf = (HiETF)mess.getBody();
/* 68 */     String value = etf.getGrandChildValue(HiItemHelper.getCurEtfLevel(mess) + this.strKey_name);
/*    */ 
/* 72 */     HiMessagePool mp = HiMessagePool.getMessagePool(messContext);
/* 73 */     if ((mp.restoreHeader(value, mess) != null) || 
/* 74 */       (this.ignore)) return;
/* 75 */     throw new HiException("231428");
/*    */   }
/*    */ }