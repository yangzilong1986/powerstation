/*    */ package com.hisun.handler;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ 
/*    */ public class HiTimeOutFilter
/*    */   implements IHandler
/*    */ {
/*    */   private int _timeOut;
/*    */   Logger log;
/*    */ 
/*    */   public HiTimeOutFilter()
/*    */   {
/* 18 */     this._timeOut = 1000;
/*    */ 
/* 20 */     this.log = HiLog.getLogger("timeout.trc"); }
/*    */ 
/*    */   public void setTimeOut(int timeOut) {
/* 23 */     this._timeOut = timeOut;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx) throws HiException {
/* 27 */     HiMessage msg1 = ctx.getCurrentMsg();
/* 28 */     Long tm = (Long)msg1.getObjectHeadItem("STM");
/* 29 */     if (tm == null) {
/* 30 */       return;
/*    */     }
/* 32 */     if ((System.currentTimeMillis() - tm.longValue() <= this._timeOut) || 
/* 33 */       (!(this.log.isDebugEnabled()))) return;
/* 34 */     this.log.debug(msg1.getRequestId() + ":超过指定的超时时间:[" + this._timeOut + "ms][" + (System.currentTimeMillis() - tm.longValue()) + "ms]");
/*    */   }
/*    */ }