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
/*    */ public class HiSaveMsgHead extends HiEngineModel
/*    */ {
/*    */   private String strKey_name;
/* 52 */   private int timeout = 30;
/*    */ 
/*    */   public HiSaveMsgHead()
/*    */   {
/* 24 */     HiContext ctx = HiContext.getCurrentContext();
/* 25 */     HiMessagePool.setMessagePool(ctx.getServerContext());
/*    */   }
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 32 */     return "SaveMsgHead";
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 37 */     return super.toString() + ":key_name[" + this.strKey_name + "]timeout[" + this.timeout + "]";
/*    */   }
/*    */ 
/*    */   public void setKey_name(String strKey_name)
/*    */   {
/* 55 */     this.strKey_name = strKey_name;
/*    */   }
/*    */ 
/*    */   public void setTimeout(int timeout) {
/* 59 */     this.timeout = timeout;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/* 68 */     HiMessage mess = messContext.getCurrentMsg();
/* 69 */     Logger log = HiLog.getLogger(mess);
/* 70 */     if (log.isInfoEnabled()) {
/* 71 */       log.info(sm.getString("HiSaveMsgHead.process00", HiEngineUtilities.getCurFlowStep(), this.strKey_name));
/*    */     }
/*    */ 
/* 74 */     HiETF etf = (HiETF)mess.getBody();
/* 75 */     String value = etf.getGrandChildValue(HiItemHelper.getCurEtfLevel(mess) + this.strKey_name);
/*    */ 
/* 79 */     HiMessagePool mp = HiMessagePool.getMessagePool(messContext);
/* 80 */     messContext.setProperty("_MSG_TIME_OUT", new Integer(this.timeout * 1000));
/* 81 */     mp.saveHeader(value, mess);
/*    */   }
/*    */ }