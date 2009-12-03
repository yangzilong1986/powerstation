/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.engine.HiEngineUtilities;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiStringManager;
/*    */ 
/*    */ public class HiHeadDel extends HiEngineModel
/*    */ {
/*    */   private String strHead_name;
/*    */ 
/*    */   public void setHead_name(String strHead_name)
/*    */   {
/* 18 */     this.strHead_name = strHead_name;
/*    */   }
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 24 */     return "DelHead";
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 30 */     return super.toString() + ":Head_Name[" + this.strHead_name + "]";
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext messContext) throws HiException
/*    */   {
/* 35 */     HiMessage mess = messContext.getCurrentMsg();
/* 36 */     Logger log = HiLog.getLogger(mess);
/* 37 */     if (log.isInfoEnabled()) {
/* 38 */       log.info(sm.getString("HiHeadDel.process00", HiEngineUtilities.getCurFlowStep(), this.strHead_name));
/*    */     }
/* 40 */     mess.delHeadItem(this.strHead_name);
/*    */   }
/*    */ }