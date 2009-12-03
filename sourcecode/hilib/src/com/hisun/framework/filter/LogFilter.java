/*    */ package com.hisun.framework.filter;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.pubinterface.IHandler;
/*    */ import com.hisun.pubinterface.IHandlerFilter;
/*    */ import com.hisun.stat.util.IStat;
/*    */ 
/*    */ public class LogFilter
/*    */   implements IHandlerFilter
/*    */ {
/*    */   private final Logger _log;
/*    */   private final String _desc;
/*    */   private IStat _stat;
/*    */ 
/*    */   public LogFilter(String desc, Logger log)
/*    */   {
/* 19 */     this._desc = desc;
/* 20 */     this._log = log;
/*    */   }
/*    */ 
/*    */   public LogFilter(String desc, Logger log, IStat stat) {
/* 24 */     this._desc = desc;
/* 25 */     this._log = log;
/* 26 */     this._stat = stat;
/*    */   }
/*    */ 
/*    */   public void process(HiMessageContext ctx, IHandler handler) throws HiException
/*    */   {
/* 31 */     if (this._log.isDebugEnabled()) {
/* 32 */       this._log.debug(this._desc + " - start");
/*    */     }
/* 34 */     long time = System.currentTimeMillis();
/* 35 */     long size = Runtime.getRuntime().freeMemory();
/*    */     try {
/* 37 */       handler.process(ctx);
/*    */     } finally {
/* 39 */       HiLog.close(ctx.getCurrentMsg());
/*    */     }
/* 41 */     time = System.currentTimeMillis() - time;
/* 42 */     size -= Runtime.getRuntime().freeMemory();
/* 43 */     if (this._stat != null)
/* 44 */       this._stat.once(time, size);
/* 45 */     if (this._log.isDebugEnabled())
/* 46 */       this._log.debug(this._desc + " - end, exec time(ms) :" + time);
/*    */   }
/*    */ }