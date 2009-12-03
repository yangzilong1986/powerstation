/*    */ package com.hisun.bank.component;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import java.util.HashMap;
/*    */ import java.util.concurrent.Semaphore;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public abstract class TrafficControlBase
/*    */ {
/*    */   private HashMap semaphores;
/*    */   private Object lock;
/* 18 */   private static String ID = "id";
/* 19 */   private static String COUNT = "count";
/* 20 */   private static String TIMEOUT = "timeout";
/*    */ 
/*    */   public TrafficControlBase()
/*    */   {
/* 16 */     this.semaphores = new HashMap(10);
/* 17 */     this.lock = new Object();
/*    */   }
/*    */ 
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 33 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 34 */     String id = args.get(ID);
/* 35 */     int count = args.getInt(COUNT);
/* 36 */     if (StringUtils.isBlank(id)) {
/* 37 */       throw new HiException("213307", ID);
/*    */     }
/* 39 */     if (count == 0) {
/* 40 */       throw new HiException("213309", COUNT, "the args [" + COUNT + "] not found  or value is 0");
/*    */     }
/*    */ 
/* 43 */     if (count < 0) {
/* 44 */       throw new HiException("213309", COUNT, "the args [" + COUNT + "] value is invaild");
/*    */     }
/*    */ 
/* 47 */     long timeout = args.getInt(TIMEOUT);
/* 48 */     if (timeout == 0L) {
/* 49 */       timeout = 200L;
/*    */     }
/* 51 */     if (log.isDebugEnabled()) {
/* 52 */       log.debug("traffic control： id = [" + id + "]" + " count =[" + count + "]" + " timeout = [" + timeout + "]");
/*    */     }
/*    */ 
/* 55 */     Semaphore semaphore = null;
/* 56 */     synchronized (this.lock) {
/* 57 */       boolean existed = this.semaphores.containsKey(id);
/* 58 */       if (existed) {
/* 59 */         semaphore = (Semaphore)this.semaphores.get(id);
/*    */       } else {
/* 61 */         semaphore = new Semaphore(count);
/* 62 */         this.semaphores.put(id, semaphore);
/*    */       }
/*    */     }
/*    */ 
/* 66 */     boolean oked = false;
/*    */     try {
/* 68 */       oked = semaphore.tryAcquire(timeout, TimeUnit.MILLISECONDS);
/*    */     } catch (InterruptedException e) {
/* 70 */       throw new HiException(e);
/*    */     }
/*    */ 
/* 73 */     if (!(oked)) {
/* 74 */       log.warn("[" + id + "]; traffic full");
/* 75 */       return 90;
/*    */     }
/*    */ 
/* 78 */     if (log.isDebugEnabled()) {
/* 79 */       log.debug(Thread.currentThread().getName() + "[" + id + "]; free traffic:[" + semaphore.availablePermits() + "]");
/*    */     }
/*    */ 
/*    */     try
/*    */     {
/* 84 */       e = doExecute(args, ctx);
/*    */ 
/* 87 */       return e;
/*    */     }
/*    */     finally
/*    */     {
/* 86 */       semaphore.release();
/*    */     }
/*    */   }
/*    */ 
/*    */   protected abstract int doExecute(HiATLParam paramHiATLParam, HiMessageContext paramHiMessageContext)
/*    */     throws HiException;
/*    */ }