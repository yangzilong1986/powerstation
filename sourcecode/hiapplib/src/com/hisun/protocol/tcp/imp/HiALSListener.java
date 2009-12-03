/*     */ package com.hisun.protocol.tcp.imp;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.HiDefaultServer;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.protocol.tcp.ISocketHandler;
/*     */ import com.hisun.protocol.tcp.filters.ExceptionCloseSocketFilter;
/*     */ import com.hisun.protocol.tcp.filters.IpCheckFilter;
/*     */ import com.hisun.protocol.tcp.filters.LoopFilter;
/*     */ import com.hisun.protocol.tcp.filters.PoolFilter;
/*     */ import com.hisun.protocol.tcp.filters.SocketHandlers;
/*     */ import com.hisun.util.HiThreadPool;
/*     */ import java.net.Socket;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ public class HiALSListener extends HiTcpListener
/*     */ {
/*     */   private HiThreadPool readerPool;
/*     */ 
/*     */   public void serverInit(ServerEvent arg0)
/*     */     throws HiException
/*     */   {
/*  32 */     super.serverInit(arg0);
/*     */   }
/*     */ 
/*     */   public void serverStop(ServerEvent arg0) throws HiException {
/*  36 */     super.serverStop(arg0);
/*     */ 
/*  39 */     shutdownReaderThread(this.readerPool);
/*     */   }
/*     */ 
/*     */   private void shutdownReaderThread(HiThreadPool pool)
/*     */   {
/*  44 */     pool.shutdownNow();
/*     */     try
/*     */     {
/*  48 */       pool.awaitTermination(10L, TimeUnit.SECONDS);
/*     */     }
/*     */     catch (InterruptedException e)
/*     */     {
/*  52 */       this.log.error("shut down thread pool err!", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ISocketHandler buildSocketHandler()
/*     */     throws HiException
/*     */   {
/*  66 */     List filters = new ArrayList();
/*     */ 
/*  69 */     filters.add(new ExceptionCloseSocketFilter(this.log));
/*     */ 
/*  71 */     if (this.ipset != null) {
/*  72 */       this.ipcheck = new IpCheckFilter(this.ipset, this.log);
/*  73 */       filters.add(this.ipcheck);
/*     */     }
/*  75 */     filters.add(this.opthandler);
/*  76 */     addListenerPoolFilter(filters);
/*  77 */     filters.add(new LoopFilter(getServer()));
/*     */ 
/*  79 */     filters.add(SocketHandlers.createContextFilter(getServer().getName(), getMsgType()));
/*     */ 
/*  81 */     filters.add(SocketHandlers.receiveMessageFilter(this.log, this.msginout));
/*     */ 
/*  83 */     filters.add(this.poolhandler);
/*     */ 
/*  85 */     ISocketHandler handler = new ISocketHandler() {
/*     */       public void process(Socket socket, HiMessageContext ctx) {
/*     */         try {
/*  88 */           HiALSListener.this.getServer().process(ctx);
/*     */         } catch (Throwable e) {
/*     */         }
/*  91 */         HiLog.close(ctx.getCurrentMsg());
/*     */       }
/*     */     };
/*  94 */     return buildSocketHandler(filters, handler);
/*     */   }
/*     */ 
/*     */   private void addListenerPoolFilter(List filters) {
/*  98 */     this.readerPool = HiThreadPool.createThreadPool();
/*  99 */     this.readerPool.setCorePoolSize(5);
/* 100 */     this.readerPool.setMaximumPoolSize(5);
/* 101 */     PoolFilter listenerPoolFilter = new PoolFilter();
/* 102 */     listenerPoolFilter.setLog(this.log);
/* 103 */     listenerPoolFilter.setTp(this.readerPool);
/* 104 */     filters.add(listenerPoolFilter);
/*     */   }
/*     */ }