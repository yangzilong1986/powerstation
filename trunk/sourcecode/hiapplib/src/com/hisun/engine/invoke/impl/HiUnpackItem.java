/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineUtilities;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ import com.hisun.util.HiStringManager;
/*    */ 
/*    */ public class HiUnpackItem extends HiAbstractPackItem
/*    */ {
/*    */   public void process(HiMessageContext messContext)
/*    */     throws HiException
/*    */   {
/*    */     try
/*    */     {
/* 22 */       HiMessage mess = messContext.getCurrentMsg();
/* 23 */       Logger log = HiLog.getLogger(mess);
/*    */ 
/* 25 */       HiETF etfBody = (HiETF)mess.getBody();
/* 26 */       String item = etfBody.getGrandChildValue(HiItemHelper.getCurEtfLevel(mess) + getName());
/*    */ 
/* 30 */       if (item == null)
/*    */       {
/* 33 */         throw new HiException("213142", HiItemHelper.getCurEtfLevel(mess) + this.name);
/*    */       }
/* 35 */       if (log.isInfoEnabled()) {
/* 36 */         log.info(sm.getString("HiUnpackItem.process00", HiEngineUtilities.getCurFlowStep(), this.name, item));
/*    */       }
/*    */ 
/* 39 */       HiByteBuffer plainBuf = (HiByteBuffer)mess.getObjectHeadItem("PlainText");
/*    */ 
/* 41 */       int plainOffset = HiItemHelper.getPlainOffset(mess);
/* 42 */       String msgType = mess.getHeadItem("ECT");
/*    */ 
/* 44 */       initMsgState(mess, "text/plain", new HiByteBuffer(item.getBytes()));
/*    */ 
/* 46 */       super.process(messContext);
/* 47 */       if (log.isInfoEnabled()) {
/* 48 */         log.info(sm.getString("HiUnpackItem.process01", HiEngineUtilities.getCurFlowStep(), this.name));
/*    */       }
/* 50 */       restoreMsgState(mess, plainBuf, plainOffset, msgType);
/*    */     }
/*    */     catch (Throwable te)
/*    */     {
/* 55 */       throw HiException.makeException("213143", this.name, te);
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getNodeName()
/*    */   {
/* 61 */     return "UnPackItem";
/*    */   }
/*    */ }