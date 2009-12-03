/*     */ package com.hisun.protocol.tcp;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.HiDefaultServer;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import com.hisun.util.HiThreadPool;
/*     */ import edu.emory.mathcs.backport.java.util.concurrent.RejectedExecutionException;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ 
/*     */ class HiWorker
/*     */   implements Runnable
/*     */ {
/*  18 */   protected static HiStringManager _sm = HiStringManager.getManager();
/*     */   private HiDefaultServer _server;
/*     */   private HiMessageInOut _msginout;
/*     */   private Logger _log;
/*     */   private HiMessageContext _ctx;
/*     */   private HiThreadPool _tp;
/*     */   private Socket _socket;
/*     */   private Object _writeLock;
/*     */ 
/*     */   public HiWorker(Object writeLock)
/*     */   {
/*  30 */     this._writeLock = writeLock; }
/*     */ 
/*     */   public void setSocket(Socket socket) {
/*  33 */     this._socket = socket; }
/*     */ 
/*     */   public void setTp(HiThreadPool tp) {
/*  36 */     this._tp = tp;
/*     */   }
/*     */ 
/*     */   public void setLogger(Logger log) {
/*  40 */     this._log = log;
/*     */   }
/*     */ 
/*     */   public void setServer(HiDefaultServer server) {
/*  44 */     this._server = server;
/*     */   }
/*     */ 
/*     */   public void setMessageContext(HiMessageContext ctx) {
/*  48 */     this._ctx = ctx;
/*     */   }
/*     */ 
/*     */   public void execute()
/*     */   {
/*     */     try {
/*  54 */       if ((this._tp.isShutdown()) || (Thread.currentThread().isInterrupted())) {
/*     */         return;
/*     */       }
/*  57 */       this._tp.execute(this);
/*     */     } catch (RejectedExecutionException e) {
/*     */       while (true) {
/*  60 */         this._log.warn("Please increase maxThreads!");
/*  61 */         Thread.yield();
/*     */         try {
/*  63 */           Thread.sleep(1000L);
/*     */         } catch (InterruptedException e1) {
/*  65 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void run() {
/*     */     try {
/*  73 */       doRun();
/*     */     } catch (Throwable t) {
/*  75 */       this._log.error(t, t);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doRun() throws HiException, IOException {
/*  80 */     this._server.process(this._ctx);
/*  81 */     if (this._socket == null)
/*  82 */       return;
/*  83 */     HiMessage msg = this._ctx.getCurrentMsg();
/*  84 */     synchronized (this._writeLock) {
/*  85 */       this._msginout.write(this._socket.getOutputStream(), this._ctx.getCurrentMsg());
/*     */     }
/*  87 */     if (this._log.isInfoEnabled()) {
/*  88 */       HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
/*  89 */       int port = this._socket.getPort();
/*  90 */       String ip = this._socket.getInetAddress().getHostAddress();
/*  91 */       this._log.info(_sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), ip, String.valueOf(port), String.valueOf(byteBuffer.length()), byteBuffer));
/*     */     }
/*     */   }
/*     */ 
/*     */   public HiMessageInOut getMsginout()
/*     */   {
/*  98 */     return this._msginout; }
/*     */ 
/*     */   public void setMsginout(HiMessageInOut msginout) {
/* 101 */     this._msginout = msginout;
/*     */   }
/*     */ }