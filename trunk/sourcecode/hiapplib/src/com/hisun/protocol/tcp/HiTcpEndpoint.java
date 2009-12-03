/*     */ package com.hisun.protocol.tcp;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.IOException;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.net.BindException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.security.AccessControlException;
/*     */ import javax.net.ServerSocketFactory;
/*     */ 
/*     */ public class HiTcpEndpoint
/*     */   implements Runnable
/*     */ {
/*     */   Logger log;
/*  40 */   HiStringManager sm = HiStringManager.getManager();
/*     */   private static final int BACKLOG = 100;
/*     */   private static final int TIMEOUT = 1000;
/*  46 */   private final Object threadSync = new Object();
/*     */ 
/*  48 */   private int backlog = 100;
/*     */ 
/*  53 */   private int serverTimeout = 0;
/*     */   private InetAddress inet;
/*     */   private int port;
/*     */   private ServerSocketFactory factory;
/*     */   private ServerSocket serverSocket;
/*  64 */   private volatile boolean running = false;
/*     */ 
/*  66 */   private volatile boolean paused = false;
/*     */ 
/*  68 */   private boolean initialized = false;
/*     */ 
/*  70 */   private boolean reinitializing = false;
/*     */ 
/*  73 */   private HiSSLHandler sslHandler = null;
/*     */ 
/* 441 */   private Thread thread = null;
/*     */   ISocketHandler handler;
/*     */ 
/*     */   public void setLog(Logger log)
/*     */   {
/*  83 */     this.log = log;
/*     */   }
/*     */ 
/*     */   public int getLocalPort() {
/*  87 */     return this.port;
/*     */   }
/*     */ 
/*     */   public void setLocalPort(int port) {
/*  91 */     this.port = port;
/*     */   }
/*     */ 
/*     */   public boolean isRunning()
/*     */   {
/* 115 */     return this.running;
/*     */   }
/*     */ 
/*     */   public boolean isPaused() {
/* 119 */     return this.paused;
/*     */   }
/*     */ 
/*     */   public void setBacklog(int backlog)
/*     */   {
/* 127 */     if (backlog > 0)
/* 128 */       this.backlog = backlog;
/*     */   }
/*     */ 
/*     */   public int getBacklog() {
/* 132 */     return this.backlog;
/*     */   }
/*     */ 
/*     */   public int getServerSoTimeout() {
/* 136 */     return this.serverTimeout;
/*     */   }
/*     */ 
/*     */   public void setServerSoTimeout(int i) {
/* 140 */     this.serverTimeout = i;
/*     */   }
/*     */ 
/*     */   public void initEndpoint()
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/* 148 */       if ((this.sslHandler != null) && (this.sslHandler.isSSLMode()))
/*     */       {
/* 150 */         this.sslHandler.init();
/* 151 */         this.factory = this.sslHandler.getServerSocketFactory();
/*     */       }
/* 153 */       if (this.factory == null)
/* 154 */         this.factory = ServerSocketFactory.getDefault();
/* 155 */       if (this.serverSocket == null) {
/*     */         try {
/* 157 */           if (this.inet == null) {
/* 158 */             this.serverSocket = this.factory.createServerSocket(this.port, this.backlog);
/*     */           }
/*     */           else {
/* 161 */             this.serverSocket = this.factory.createServerSocket(this.port, this.backlog, this.inet);
/*     */           }
/*     */         }
/*     */         catch (BindException be)
/*     */         {
/* 166 */           throw new HiException("231208", String.valueOf(this.port), be.getMessage());
/*     */         }
/*     */       }
/*     */ 
/* 170 */       if (this.serverTimeout >= 0)
/* 171 */         this.serverSocket.setSoTimeout(this.serverTimeout);
/* 172 */       if ((this.sslHandler != null) && (this.sslHandler.isSSLMode()))
/*     */       {
/* 174 */         this.sslHandler.initServerSocket(this.serverSocket);
/*     */       }
/*     */     }
/*     */     catch (IOException ex)
/*     */     {
/* 179 */       throw new HiException("231208", String.valueOf(this.port), ex.getMessage());
/*     */     }
/*     */ 
/* 182 */     this.initialized = true;
/*     */   }
/*     */ 
/*     */   public void startEndpoint() throws HiException
/*     */   {
/* 187 */     if (!(this.initialized)) {
/* 188 */       initEndpoint();
/*     */     }
/*     */ 
/* 193 */     this.running = true;
/* 194 */     this.paused = false;
/*     */ 
/* 199 */     threadStart();
/*     */   }
/*     */ 
/*     */   public void pauseEndpoint() {
/* 203 */     if ((this.running) && (!(this.paused))) {
/* 204 */       this.paused = true;
/* 205 */       unlockAccept();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void resumeEndpoint() {
/* 210 */     if (this.running)
/* 211 */       this.paused = false;
/*     */   }
/*     */ 
/*     */   public void stopEndpoint()
/*     */   {
/* 217 */     if (this.running)
/*     */     {
/* 219 */       this.running = false;
/* 220 */       if (this.serverSocket != null) {
/* 221 */         closeServerSocket();
/*     */       }
/*     */ 
/* 225 */       threadStop();
/* 226 */       this.initialized = false;
/*     */     }
/* 228 */     else if (this.serverSocket != null) {
/* 229 */       closeServerSocket();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void closeServerSocket()
/*     */   {
/* 235 */     if (!(this.paused))
/* 236 */       unlockAccept();
/*     */     try {
/* 238 */       if (this.serverSocket != null)
/* 239 */         this.serverSocket.close();
/*     */     } catch (Exception e) {
/* 241 */       this.log.error(this.sm.getString("endpoint.err.close"), e);
/*     */     }
/* 243 */     this.serverSocket = null;
/*     */   }
/*     */ 
/*     */   protected void unlockAccept() {
/* 247 */     Socket s = null;
/*     */     try
/*     */     {
/* 250 */       if (this.inet == null)
/* 251 */         s = new Socket("127.0.0.1", this.port);
/*     */       else {
/* 253 */         s = new Socket(this.inet, this.port);
/*     */       }
/*     */ 
/* 257 */       s.setSoLinger(true, 0);
/*     */     } catch (Exception e) {
/* 259 */       if (this.log.isDebugEnabled())
/* 260 */         this.log.debug(this.sm.getString("endpoint.debug.unlock", "" + this.port), e);
/*     */     }
/*     */     finally {
/* 263 */       if (s != null)
/*     */         try {
/* 265 */           s.close();
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   Socket acceptSocket()
/*     */   {
/*     */     String msg;
/* 276 */     if ((!(this.running)) || (this.serverSocket == null)) {
/* 277 */       return null;
/*     */     }
/* 279 */     Socket accepted = null;
/*     */     try
/*     */     {
/* 282 */       accepted = this.serverSocket.accept();
/*     */ 
/* 284 */       if (null == accepted) {
/* 285 */         this.log.warn(this.sm.getString("endpoint.warn.nullSocket"));
/*     */       }
/* 287 */       else if (!(this.running)) {
/* 288 */         accepted.close();
/* 289 */         accepted = null;
/*     */ 
/* 291 */         return accepted;
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (InterruptedIOException iioe)
/*     */     {
/*     */     }
/*     */     catch (AccessControlException ace)
/*     */     {
/* 303 */       msg = this.sm.getString("endpoint.warn.security", this.serverSocket, ace);
/*     */ 
/* 305 */       this.log.warn(msg);
/*     */     }
/*     */     catch (IOException e) {
/* 308 */       msg = null;
/*     */ 
/* 310 */       if (this.running) {
/* 311 */         msg = this.sm.getString("endpoint.err.nonfatal", this.serverSocket, e);
/* 312 */         this.log.error(msg, e);
/*     */       }
/*     */ 
/* 315 */       if (accepted != null) {
/*     */         try {
/* 317 */           accepted.close();
/*     */         } catch (Throwable ex) {
/* 319 */           msg = this.sm.getString("endpoint.err.nonfatal", accepted, ex);
/* 320 */           this.log.warn(msg, ex);
/*     */         }
/* 322 */         accepted = null;
/*     */       }
/*     */ 
/* 325 */       if (!(this.running))
/* 326 */         return null;
/* 327 */       restartendpoint();
/*     */     }
/*     */ 
/* 331 */     return accepted;
/*     */   }
/*     */ 
/*     */   private void restartendpoint()
/*     */     throws ThreadDeath
/*     */   {
/* 342 */     this.reinitializing = true;
/*     */ 
/* 345 */     synchronized (this.threadSync) {
/* 346 */       if (this.reinitializing)
/*     */       {
/*     */         String msg;
/* 347 */         this.reinitializing = false;
/*     */ 
/* 349 */         closeServerSocket();
/* 350 */         this.initialized = false;
/*     */         try
/*     */         {
/* 353 */           msg = this.sm.getString("endpoint.warn.reinit");
/* 354 */           this.log.warn(msg);
/* 355 */           initEndpoint();
/*     */         } catch (Throwable t) {
/* 357 */           msg = this.sm.getString("endpoint.err.nonfatal", this.serverSocket, t);
/*     */ 
/* 359 */           this.log.error(msg, t);
/*     */         }
/*     */ 
/* 362 */         if (!(this.initialized)) {
/* 363 */           msg = this.sm.getString("endpoint.warn.restart");
/* 364 */           this.log.warn(msg);
/*     */           try {
/* 366 */             stopEndpoint();
/* 367 */             initEndpoint();
/* 368 */             startEndpoint();
/*     */           } catch (Throwable t) {
/* 370 */             msg = this.sm.getString("endpoint.err.fatal", this.serverSocket, t);
/*     */ 
/* 372 */             this.log.error(msg, t);
/*     */           }
/*     */ 
/* 375 */           throw new ThreadDeath();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 387 */     if (this.log.isInfoEnabled()) {
/* 388 */       this.log.info(this.sm.getString("tcplistener.info.startthread", Integer.toString(this.port)));
/*     */     }
/*     */ 
/* 392 */     while (this.running)
/*     */     {
/* 395 */       while (this.paused) {
/*     */         try {
/* 397 */           Thread.sleep(1000L);
/*     */         }
/*     */         catch (InterruptedException e)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/* 404 */       Socket socket = acceptSocket();
/*     */ 
/* 407 */       if (socket == null)
/*     */       {
/*     */         continue;
/*     */       }
/*     */ 
/* 412 */       if ((((this.paused) || (!(this.running)))) && 
/* 413 */         (socket.getInetAddress().getHostAddress().equals("127.0.0.1"))) {
/*     */         try
/*     */         {
/* 416 */           socket.close();
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 425 */         this.handler.process(socket, null);
/*     */       } catch (Throwable t) {
/* 427 */         if (this.log.isInfoEnabled()) {
/* 428 */           this.log.info("handler process fail:" + t);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 434 */     if (this.log.isInfoEnabled())
/* 435 */       this.log.info(this.sm.getString("tcplistener.info.exit", Integer.toString(this.port)));
/*     */   }
/*     */ 
/*     */   private void threadStart()
/*     */   {
/* 449 */     this.thread = new Thread(this, "PortListener:" + this.port);
/*     */ 
/* 453 */     this.thread.start();
/*     */   }
/*     */ 
/*     */   private void threadStop()
/*     */   {
/*     */     try
/*     */     {
/* 462 */       this.thread.join();
/*     */     } catch (InterruptedException e) {
/*     */     }
/* 465 */     this.thread = null;
/*     */   }
/*     */ 
/*     */   public void setHandler(ISocketHandler processor)
/*     */   {
/* 471 */     this.handler = processor;
/*     */   }
/*     */ 
/*     */   public void setSslHandler(HiSSLHandler processor) {
/* 475 */     this.sslHandler = processor;
/*     */   }
/*     */ }