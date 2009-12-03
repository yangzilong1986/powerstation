/*     */ package com.hisun.protocol.tcp.filters;
/*     */ 
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.protocol.tcp.ISocketHandler;
/*     */ import com.hisun.protocol.tcp.ISocketHandlerFilter;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import com.hisun.util.HiThreadPool;
/*     */ import java.net.Socket;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.RejectedExecutionException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ public class PoolFilter
/*     */   implements ISocketHandlerFilter
/*     */ {
/*  20 */   private static HiStringManager sm = HiStringManager.getManager();
/*     */   Logger log;
/*     */   HiThreadPool tp;
/*     */ 
/*     */   public HiThreadPool getTp()
/*     */   {
/*  25 */     return this.tp;
/*     */   }
/*     */ 
/*     */   public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler)
/*     */   {
/*  36 */     Runnable worker = handler2Runnable(socket, ctx, handler);
/*     */     try
/*     */     {
/*     */       StringBuffer msg;
/*  39 */       if (this.log.isDebugEnabled()) {
/*  40 */         msg = new StringBuffer();
/*  41 */         debuginfo(msg.append("before submit: "));
/*  42 */         this.log.debug(msg.toString());
/*     */       }
/*     */       while (true) {
/*     */         try {
/*  46 */           this.tp.execute(worker);
/*  47 */           if (this.log.isDebugEnabled()) {
/*  48 */             msg = new StringBuffer();
/*  49 */             debuginfo(msg.append("after submit: "));
/*  50 */             this.log.debug(msg.toString());
/*     */           }
/*     */         }
/*     */         catch (RejectedExecutionException e) {
/*  54 */           this.log.warn("Please increase maxThreads!");
/*  55 */           Thread.currentThread(); Thread.yield();
/*     */           try {
/*  57 */             Thread.currentThread(); Thread.sleep(1000L);
/*     */           } catch (InterruptedException e1) {
/*  59 */             break label166:
/*     */           }
/*  61 */           if (!(this.tp.isShutdown())) if (!(Thread.currentThread().isInterrupted())) break label163; 
/*     */         }
/*  62 */         label163: label166: break;
/*     */       }
/*     */     }
/*     */     catch (RejectedExecutionException e)
/*     */     {
/*  67 */       this.log.error(sm.getString("endpoint.noProcessor", socket.toString()));
/*     */ 
/*  69 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   private Runnable handler2Runnable(Socket socket, HiMessageContext ctx, ISocketHandler handler)
/*     */   {
/*  75 */     Runnable worker = new Runnable(handler, socket, ctx) { private final ISocketHandler val$handler;
/*     */       private final Socket val$socket;
/*     */       private final HiMessageContext val$ctx;
/*     */ 
/*     */       public void run() { try { this.val$handler.process(this.val$socket, this.val$ctx);
/*     */         } catch (Throwable t) {
/*  81 */           PoolFilter.this.log.error("handle socket error:" + t, t);
/*     */         }
/*     */       }
/*     */     };
/*  85 */     return worker;
/*     */   }
/*     */ 
/*     */   private void debuginfo(StringBuffer msg) {
/*  89 */     msg.append(this.tp.toString()).append("[Active=").append(this.tp.getActiveCount()).append(",Current=").append(this.tp.getPoolSize()).append(",Largest=").append(this.tp.getLargestPoolSize()).append(",Core=").append(this.tp.getCorePoolSize()).append(",Max=").append(this.tp.getMaximumPoolSize()).append(",TaskCount=").append(this.tp.getTaskCount()).append(",QueueSize=").append(this.tp.getQueue().size()).append("]");
/*     */   }
/*     */ 
/*     */   public void setLog(Logger log)
/*     */   {
/*  99 */     this.log = log;
/*     */   }
/*     */ 
/*     */   public void setTp(HiThreadPool tp) {
/* 103 */     this.tp = tp;
/*     */   }
/*     */ 
/*     */   public void shutdown() {
/* 107 */     this.tp.shutdown();
/*     */     try
/*     */     {
/* 110 */       this.tp.awaitTermination(5L, TimeUnit.SECONDS);
/*     */     }
/*     */     catch (InterruptedException e)
/*     */     {
/* 114 */       this.log.error("shut down thread pool err!", e);
/*     */     }
/*     */   }
/*     */ }