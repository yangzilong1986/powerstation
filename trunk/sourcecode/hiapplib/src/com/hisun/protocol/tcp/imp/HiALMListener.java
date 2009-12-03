/*     */ package com.hisun.protocol.tcp.imp;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.HiDefaultServer;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.protocol.tcp.ISocketHandler;
/*     */ import com.hisun.protocol.tcp.ISocketHandlerFilter;
/*     */ import com.hisun.protocol.tcp.filters.ExceptionCloseSocketFilter;
/*     */ import com.hisun.protocol.tcp.filters.IpCheckFilter;
/*     */ import com.hisun.protocol.tcp.filters.LoopFilter;
/*     */ import com.hisun.protocol.tcp.filters.PoolFilter;
/*     */ import com.hisun.protocol.tcp.filters.SocketHandlers;
/*     */ import com.hisun.util.HiThreadPool;
/*     */ import java.io.IOException;
/*     */ import java.net.Socket;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ public class HiALMListener extends HiTcpListener
/*     */ {
/*     */   private HiThreadPool readerPool;
/*     */   private List conns;
/*     */ 
/*     */   public HiALMListener()
/*     */   {
/*  30 */     this.conns = new ArrayList(); }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0) throws HiException {
/*  33 */     super.serverInit(arg0);
/*     */   }
/*     */ 
/*     */   public void serverStop(ServerEvent arg0) throws HiException {
/*  37 */     super.serverStop(arg0);
/*  38 */     for (int i = 0; i < this.conns.size(); ++i)
/*     */       try {
/*  40 */         ((Socket)this.conns.get(i)).close();
/*     */       }
/*     */       catch (IOException e) {
/*     */       }
/*  44 */     this.conns.clear();
/*     */ 
/*  46 */     shutdownReaderThread(this.readerPool);
/*     */   }
/*     */ 
/*     */   private void shutdownReaderThread(HiThreadPool pool) {
/*  50 */     pool.shutdownNow();
/*     */     try
/*     */     {
/*  54 */       pool.awaitTermination(10L, TimeUnit.SECONDS);
/*     */     }
/*     */     catch (InterruptedException e)
/*     */     {
/*  58 */       this.log.error("shut down thread pool err!", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ISocketHandler buildSocketHandler()
/*     */     throws HiException
/*     */   {
/*  70 */     List filters = new ArrayList();
/*     */ 
/*  72 */     filters.add(new ExceptionCloseSocketFilter(this.log));
/*     */ 
/*  74 */     if (this.ipset != null) {
/*  75 */       this.ipcheck = new IpCheckFilter(this.ipset, this.log);
/*  76 */       filters.add(this.ipcheck);
/*     */     }
/*     */ 
/*  79 */     filters.add(this.opthandler);
/*  80 */     addListenerPoolFilter(filters);
/*     */ 
/*  83 */     filters.add(new ISocketHandlerFilter()
/*     */     {
/*     */       public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler) throws HiException {
/*  86 */         HiALMListener.this.conns.add(socket);
/*  87 */         handler.process(socket, ctx);
/*     */       }
/*     */     });
/*  90 */     filters.add(new LoopFilter(getServer()));
/*     */ 
/*  92 */     filters.add(SocketHandlers.createContextFilter(getServer().getName(), getMsgType()));
/*     */ 
/*  94 */     filters.add(SocketHandlers.receiveMessageFilter(this.log, this.msginout));
/*     */ 
/*  96 */     filters.add(this.poolhandler);
/*     */ 
/*  98 */     filters.add(SocketHandlers.lockSendMessageFilter(this.log, this.msginout));
/*     */ 
/* 100 */     ISocketHandler handler = new ISocketHandler() {
/*     */       public void process(Socket socket, HiMessageContext ctx) {
/*     */         try {
/* 103 */           HiALMListener.this.getServer().process(ctx);
/*     */         }
/*     */         catch (Throwable e) {
/* 106 */           throw new RuntimeException(e);
/*     */         } finally {
/* 108 */           HiLog.close(ctx.getCurrentMsg());
/*     */         }
/*     */       }
/*     */     };
/* 112 */     return buildSocketHandler(filters, handler);
/*     */   }
/*     */ 
/*     */   private void addListenerPoolFilter(List filters) {
/* 116 */     this.readerPool = HiThreadPool.createThreadPool();
/* 117 */     this.readerPool.setCorePoolSize(5);
/* 118 */     this.readerPool.setMaximumPoolSize(5);
/* 119 */     PoolFilter listenerPoolFilter = new PoolFilter();
/* 120 */     listenerPoolFilter.setLog(this.log);
/* 121 */     listenerPoolFilter.setTp(this.readerPool);
/* 122 */     filters.add(listenerPoolFilter);
/*     */   }
/*     */ }