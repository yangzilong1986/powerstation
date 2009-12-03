/*    */ package com.hisun.engine.invoke.impl;
/*    */ 
/*    */ import com.hisun.engine.HiEngineUtilities;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ import com.hisun.util.HiStringManager;
/*    */ 
/*    */ public class HiPackItem extends HiAbstractPackItem
/*    */ {
/*    */   public String getNodeName()
/*    */   {
/* 19 */     return "PackItem";
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException
/*    */   {
/*    */     try
/*    */     {
/* 26 */       HiMessage msg = ctx.getCurrentMsg();
/* 27 */       Logger log = HiLog.getLogger(msg);
/* 28 */       if (log.isInfoEnabled()) {
/* 29 */         log.info(sm.getString("HiPackItem.process00", HiEngineUtilities.getCurFlowStep(), this.name));
/*    */       }
/*    */ 
/* 33 */       HiByteBuffer plainBuf = (HiByteBuffer)msg.getObjectHeadItem("PlainText");
/*    */ 
/* 35 */       int plainOffset = HiItemHelper.getPlainOffset(msg);
/* 36 */       String msgType = msg.getHeadItem("ECT");
/*    */ 
/* 38 */       initMsgState(msg, "text/etf", new HiByteBuffer(128));
/*    */ 
/* 40 */       super.process(ctx);
/*    */ 
/* 42 */       HiByteBuffer packValue = (HiByteBuffer)msg.getObjectHeadItem("PlainText");
/* 43 */       String packValStr = packValue.toString();
/* 44 */       HiItemHelper.addEtfItem(msg, getName(), packValStr);
/* 45 */       if (log.isInfoEnabled())
/* 46 */         log.info(sm.getString("HiPackItem.process01", HiEngineUtilities.getCurFlowStep(), this.name, packValStr));
/* 47 */       restoreMsgState(msg, plainBuf, plainOffset, msgType);
/*    */     }
/*    */     catch (Throwable te)
/*    */     {
/* 52 */       throw HiException.makeException("213141", this.name, te);
/*    */     }
/*    */   }
/*    */ }