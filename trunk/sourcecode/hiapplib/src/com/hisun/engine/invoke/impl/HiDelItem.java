/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.engine.HiEngineUtilities;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiStringManager;
/*    */ 
/*    */ public class HiDelItem extends HiEngineModel
/*    */ {
/*    */   private String strName;
/*    */ 
/*    */   public void setName(String strName)
/*    */   {
/* 26 */     this.strName = strName;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/* 36 */     HiMessage mess = messContext.getCurrentMsg();
/* 37 */     Logger log = HiLog.getLogger(mess);
/*    */ 
/* 39 */     if (log.isInfoEnabled()) {
/* 40 */       log.info(sm.getString("HiDelItem.process", HiEngineUtilities.getCurFlowStep(), this.strName));
/*    */     }
/*    */ 
/* 43 */     HiETF etf = (HiETF)mess.getBody();
/* 44 */     etf.removeGrandChild(HiItemHelper.getCurEtfLevel(mess) + this.strName);
/*    */   }
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 49 */     return "DelItem";
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 55 */     return super.toString() + ": name[" + this.strName + "]";
/*    */   }
/*    */ }