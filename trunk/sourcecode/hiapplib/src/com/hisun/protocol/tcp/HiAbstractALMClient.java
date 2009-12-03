/*     */ package com.hisun.protocol.tcp;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.HiDefaultServer;
/*     */ import com.hisun.framework.event.IServerEventListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import com.hisun.util.HiThreadPool;
/*     */ import java.io.IOException;
/*     */ import java.net.ConnectException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.net.SocketFactory;
/*     */ 
/*     */ public abstract class HiAbstractALMClient
/*     */   implements IHandler, IServerEventListener
/*     */ {
/*  43 */   protected static HiStringManager _sm = HiStringManager.getManager();
/*     */   protected String _msgType;
/*     */   protected int _port;
/*     */   protected String _host;
/*     */   protected int _timeOut;
/*     */   protected int _maxThreads;
/*     */   protected int _minThreads;
/*     */   protected int _queueSize;
/*     */   protected int _checkItv;
/*     */   private ICheckConn _checkConn;
/*     */   protected HiMessageInOut _msginout;
/*     */   protected Logger _log;
/*     */   protected Object _writeLock;
/*     */   protected Socket _socket;
/*     */   protected Thread _mngThd;
/*     */   protected Thread _readerThd;
/*     */   protected HiDefaultServer _server;
/*     */   protected HiThreadPool _tp;
/*     */   protected boolean _paused;
/*     */   protected boolean _running;
/*     */   protected IPreProcHandler _preProcHandler;
/*     */   protected HiSSLHandler sslHandler;
/*     */   protected SocketFactory socketFactory;
/*     */ 
/*     */   public HiAbstractALMClient()
/*     */   {
/*  44 */     this._msgType = "PLTIN0";
/*     */ 
/*  48 */     this._timeOut = 30;
/*  49 */     this._maxThreads = 50;
/*  50 */     this._minThreads = 5;
/*  51 */     this._queueSize = 100;
/*  52 */     this._checkItv = 30;
/*     */ 
/*  54 */     this._checkConn = null;
/*  55 */     this._msginout = null;
/*  56 */     this._log = null;
/*     */ 
/*  60 */     this._writeLock = new Object();
/*  61 */     this._socket = null;
/*  62 */     this._mngThd = null;
/*  63 */     this._readerThd = null;
/*     */ 
/*  66 */     this._paused = false;
/*  67 */     this._running = false;
/*  68 */     this._preProcHandler = null;
/*  69 */     this.sslHandler = HiSSLHandler.getInstance();
/*  70 */     this.socketFactory = null;
/*     */   }
/*     */ 
/*     */   public void setSslMode(String mode)
/*     */     throws HiException
/*     */   {
/*  76 */     this.sslHandler.setSslMode(Integer.parseInt(mode));
/*     */   }
/*     */ 
/*     */   public void setTrustKS(String certPath)
/*     */     throws HiException
/*     */   {
/*  86 */     this.sslHandler.setTrustKS(certPath);
/*     */   }
/*     */ 
/*     */   public void setIdentityKS(String priKey)
/*     */     throws HiException
/*     */   {
/*  95 */     this.sslHandler.setIdentityKS(priKey);
/*     */   }
/*     */ 
/*     */   public void setKeyPsw(String keyPsw)
/*     */     throws HiException
/*     */   {
/* 104 */     this.sslHandler.setKeyPsw(keyPsw);
/*     */   }
/*     */ 
/*     */   public void setAuthFlag(String authFlag)
/*     */     throws HiException
/*     */   {
/* 113 */     this.sslHandler.setAuthFlag(Integer.parseInt(authFlag));
/*     */   }
/*     */ 
/*     */   public void setPort(int port) {
/* 117 */     this._port = port;
/*     */   }
/*     */ 
/*     */   public void setHost(String host) {
/* 121 */     this._host = host;
/*     */   }
/*     */ 
/*     */   public void setPreLen(int preLen) {
/* 125 */     this._msginout.setPreLen(preLen);
/*     */   }
/*     */ 
/*     */   public void setPreLenType(String type) {
/* 129 */     this._msginout.setPreLenType(type);
/*     */   }
/*     */ 
/*     */   public void setTimeOut(int timeOut) {
/* 133 */     this._timeOut = timeOut;
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException {
/*     */     try {
/* 138 */       HiMessage msg = ctx.getCurrentMsg();
/* 139 */       send(msg, this._host, this._port);
/*     */     } catch (HiException e) {
/* 141 */       this._log.error(e);
/* 142 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void send(HiMessage msg, String ip, int port) throws HiException {
/* 147 */     if (this._socket == null) {
/* 148 */       throw new HiException("231204", "connector error");
/*     */     }
/*     */ 
/* 152 */     if (this._log.isInfoEnabled())
/*     */     {
/* 154 */       HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
/* 155 */       this._log.info(_sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), ip, String.valueOf(port), String.valueOf(byteBuffer.length()), byteBuffer));
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 161 */       synchronized (this._writeLock) {
/* 162 */         this._msginout.write(this._socket.getOutputStream(), msg);
/*     */       }
/*     */     } catch (IOException e) {
/* 165 */       closeConnection(this._socket);
/* 166 */       throw new HiException("231207", "connector send error", e);
/*     */     }
/*     */ 
/* 170 */     if (this._log.isDebugEnabled())
/* 171 */       this._log.debug("[connector] return");
/*     */   }
/*     */ 
/*     */   private Socket createConnection(String host, int port)
/*     */     throws UnknownHostException, IOException, HiException
/*     */   {
/* 177 */     if (this.sslHandler.isSSLMode()) {
/* 178 */       this.sslHandler.init();
/* 179 */       this.socketFactory = this.sslHandler.getSocketFactory();
/*     */     } else {
/* 181 */       this.socketFactory = SocketFactory.getDefault();
/*     */     }
/*     */ 
/* 184 */     Socket socket = this.socketFactory.createSocket();
/* 185 */     if (this._timeOut > 0)
/* 186 */       socket.connect(new InetSocketAddress(host, port), this._timeOut * 1000);
/*     */     else
/* 188 */       socket.connect(new InetSocketAddress(host, port));
/* 189 */     socket.setSoTimeout(0);
/* 190 */     return socket;
/*     */   }
/*     */ 
/*     */   private synchronized void closeConnection(Socket socket) {
/* 194 */     if (socket == null)
/* 195 */       return;
/*     */     try {
/* 197 */       socket.close();
/*     */     } catch (IOException e) {
/*     */     }
/* 200 */     socket = null;
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0) throws HiException {
/* 204 */     this._log = arg0.getLog();
/* 205 */     this._server = ((HiDefaultServer)arg0.getServer());
/* 206 */     if (this._queueSize != 0) {
/* 207 */       this._tp = HiThreadPool.createThreadPool(this._minThreads, this._maxThreads, this._queueSize);
/*     */     }
/*     */     else
/* 210 */       this._tp = HiThreadPool.createThreadPool(this._minThreads, this._maxThreads);
/*     */   }
/*     */ 
/*     */   public void serverStart(ServerEvent arg0)
/*     */     throws HiException
/*     */   {
/* 377 */     this._paused = false;
/* 378 */     this._running = true;
/* 379 */     this._mngThd = new Thread(new HiMananger(), "Manager:[" + this._host + ":" + this._port + "]");
/*     */ 
/* 381 */     this._readerThd = new Thread(new HiReader(), "Reader:[" + this._host + ":" + this._port + "]");
/*     */ 
/* 383 */     this._mngThd.start();
/* 384 */     this._readerThd.start();
/*     */   }
/*     */ 
/*     */   public void serverStop(ServerEvent arg0) throws HiException {
/* 388 */     this._running = false;
/* 389 */     closeConnection(this._socket);
/* 390 */     this._log.debug("serverStop start ... ");
/* 391 */     this._tp.shutdown();
/* 392 */     this._log.debug("tp shutdown ...");
/*     */     try {
/* 394 */       this._tp.awaitTermination(5L, TimeUnit.SECONDS);
/*     */     } catch (InterruptedException e1) {
/* 396 */       e1.printStackTrace();
/*     */     }
/* 398 */     this._log.debug("tp shutdown finished");
/* 399 */     this._log.debug(this._mngThd + " stopping");
/*     */     try {
/* 401 */       if (this._socket != null)
/* 402 */         this._socket.close();
/*     */     }
/*     */     catch (IOException e1) {
/* 405 */       e1.printStackTrace();
/*     */     }
/* 407 */     if (this._mngThd != null) {
/* 408 */       this._mngThd.interrupt();
/*     */       try {
/* 410 */         this._mngThd.join(5000L);
/*     */       } catch (InterruptedException e) {
/*     */       }
/* 413 */       this._mngThd = null;
/*     */     }
/* 415 */     this._log.debug(this._mngThd + " stopped");
/* 416 */     this._log.debug(this._readerThd + " stopping");
/*     */ 
/* 418 */     if (this._readerThd != null) {
/* 419 */       this._readerThd.interrupt();
/*     */       try {
/* 421 */         this._readerThd.join(5000L);
/*     */       } catch (InterruptedException e) {
/*     */       }
/* 424 */       this._readerThd = null;
/*     */     }
/* 426 */     this._log.debug(this._readerThd + " stopped");
/*     */   }
/*     */ 
/*     */   public void serverDestroy(ServerEvent arg0) throws HiException
/*     */   {
/* 431 */     serverStop(arg0);
/*     */   }
/*     */ 
/*     */   public void serverPause(ServerEvent arg0) {
/* 435 */     this._paused = true;
/*     */   }
/*     */ 
/*     */   public void serverResume(ServerEvent arg0) {
/* 439 */     this._paused = false;
/*     */   }
/*     */ 
/*     */   public String getMsgType() {
/* 443 */     return this._msgType;
/*     */   }
/*     */ 
/*     */   public void setMsgType(String type) {
/* 447 */     this._msgType = type;
/*     */   }
/*     */ 
/*     */   public int getMaxThreads() {
/* 451 */     return this._maxThreads;
/*     */   }
/*     */ 
/*     */   public void setMaxThreads(int threads) {
/* 455 */     this._maxThreads = threads;
/*     */   }
/*     */ 
/*     */   public int getMinThreads() {
/* 459 */     return this._minThreads;
/*     */   }
/*     */ 
/*     */   public void setMinThreads(int threads) {
/* 463 */     this._minThreads = threads;
/*     */   }
/*     */ 
/*     */   public int getQueueSize() {
/* 467 */     return this._queueSize;
/*     */   }
/*     */ 
/*     */   public void setQueueSize(int size) {
/* 471 */     this._queueSize = size;
/*     */   }
/*     */ 
/*     */   public int getCheckItv() {
/* 475 */     return this._checkItv;
/*     */   }
/*     */ 
/*     */   public void setCheckItv(int checkItv) {
/* 479 */     this._checkItv = checkItv;
/*     */   }
/*     */ 
/*     */   public void setCheckConn(ICheckConn checkConn) {
/* 483 */     this._checkConn = checkConn;
/*     */   }
/*     */ 
/*     */   public void setMsginout(HiMessageInOut msginout) {
/* 487 */     this._msginout = msginout;
/*     */   }
/*     */ 
/*     */   public HiMessageInOut getMsginout() {
/* 491 */     return this._msginout;
/*     */   }
/*     */ 
/*     */   public IPreProcHandler getPreProcHandler() {
/* 495 */     return this._preProcHandler;
/*     */   }
/*     */ 
/*     */   public void setPreProcHandler(IPreProcHandler preProcHandler) {
/* 499 */     this._preProcHandler = preProcHandler;
/*     */   }
/*     */ 
/*     */   class HiMananger
/*     */     implements Runnable
/*     */   {
/*     */     private int itv;
/*     */ 
/*     */     HiMananger()
/*     */     {
/* 313 */       this.itv = 0;
/*     */     }
/*     */ 
/*     */     public void run() {
/*     */       try {
/* 318 */         if (Thread.interrupted())
/*     */           return;
/* 320 */         if (!(HiAbstractALMClient.this._running))
/*     */           return;
/* 322 */         Thread.sleep(1000L);
/* 323 */         doRun();
/*     */       } catch (InterruptedException e1) {
/*     */       }
/*     */       catch (ConnectException e2) {
/* 327 */         HiAbstractALMClient.this._log.info("[" + HiAbstractALMClient.this._host + "]:[" + HiAbstractALMClient.this._port + "] Connection refused");
/*     */       }
/*     */       catch (Throwable e)
/*     */       {
/* 331 */         if (HiAbstractALMClient.this._running)
/* 332 */           HiAbstractALMClient.this._log.error(e, e);
/* 333 */         HiAbstractALMClient.this.closeConnection(HiAbstractALMClient.this._socket);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void doRun() throws IOException, HiException
/*     */     {
/* 339 */       checkConnection(HiAbstractALMClient.this._socket);
/* 340 */       if (HiAbstractALMClient.this._socket == null)
/* 341 */         return;
/* 342 */       this.itv += 1;
/* 343 */       if (this.itv == HiAbstractALMClient.this._checkItv) {
/* 344 */         this.itv = 0;
/* 345 */         String data = HiAbstractALMClient.this._checkConn.getCheckData();
/* 346 */         HiByteBuffer buf = new HiByteBuffer(data.getBytes());
/* 347 */         synchronized (HiAbstractALMClient.this._writeLock) {
/* 348 */           HiAbstractALMClient.this._msginout.write(HiAbstractALMClient.this._socket.getOutputStream(), buf);
/*     */         }
/* 350 */         String ip = HiAbstractALMClient.this._socket.getInetAddress().getHostAddress();
/* 351 */         int port = HiAbstractALMClient.this._socket.getPort();
/* 352 */         HiAbstractALMClient.this._log.info("[" + ip + "]:[" + port + "]; send check data:[" + buf.length() + "]:[" + buf + "]");
/*     */       }
/*     */     }
/*     */ 
/*     */     protected void checkConnection(Socket socket)
/*     */       throws IOException, HiException
/*     */     {
/* 359 */       if ((HiAbstractALMClient.this._socket == null) || (HiAbstractALMClient.this._socket.isClosed())) {
/* 360 */         socket = HiAbstractALMClient.this.createConnection(HiAbstractALMClient.this._host, HiAbstractALMClient.this._port);
/* 361 */         if (HiAbstractALMClient.this._log.isInfoEnabled()) {
/* 362 */           HiAbstractALMClient.this._log.info("connect [" + HiAbstractALMClient.this._host + "]:[" + HiAbstractALMClient.this._port + "] successful!");
/*     */         }
/*     */ 
/* 365 */         if ((HiAbstractALMClient.this._preProcHandler != null) && 
/* 366 */           (!(HiAbstractALMClient.this._preProcHandler.process(socket))))
/*     */         {
/* 368 */           HiAbstractALMClient.this.closeConnection(socket);
/*     */         }
/*     */ 
/* 371 */         HiAbstractALMClient.this._socket = socket;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   class HiReader
/*     */     implements Runnable
/*     */   {
/*     */     public void run()
/*     */     {
/*     */       try
/*     */       {
/*     */         while (true)
/*     */         {
/*     */           while (true)
/*     */           {
/* 227 */             if (Thread.interrupted()) {
/*     */               return;
/*     */             }
/* 230 */             if (!(HiAbstractALMClient.this._paused)) break;
/* 231 */             Thread.sleep(1000L);
/*     */           }
/*     */ 
/* 234 */           if (!(HiAbstractALMClient.this._running)) {
/*     */             return;
/*     */           }
/*     */ 
/* 238 */           if ((HiAbstractALMClient.this._socket != null) && (!(HiAbstractALMClient.this._socket.isClosed()))) break;
/* 239 */           Thread.sleep(1000L);
/*     */         }
/*     */ 
/* 243 */         doRun();
/*     */       } catch (InterruptedException e1) {
/*     */       }
/*     */       catch (Throwable e) {
/* 247 */         if (HiAbstractALMClient.this._running)
/* 248 */           HiAbstractALMClient.this._log.error(e, e);
/* 249 */         HiAbstractALMClient.this.closeConnection(HiAbstractALMClient.this._socket);
/*     */       }
/*     */     }
/*     */ 
/*     */     public void doRun()
/*     */       throws IOException, HiException, InterruptedException
/*     */     {
/*     */       int rdlen;
/* 256 */       HiByteBuffer buf = new HiByteBuffer(1024);
/*     */       try
/*     */       {
/* 259 */         rdlen = HiAbstractALMClient.this._msginout.read(HiAbstractALMClient.this._socket.getInputStream(), buf);
/*     */       } catch (IOException e) {
/* 261 */         HiAbstractALMClient.this.closeConnection(HiAbstractALMClient.this._socket);
/* 262 */         throw new HiException("231205", "connector receive error", e);
/*     */       }
/*     */ 
/* 265 */       if (rdlen == 0) {
/* 266 */         throw new IOException("peer socket close");
/*     */       }
/*     */ 
/* 269 */       if (HiAbstractALMClient.this._checkConn.isCheckData(buf)) {
/* 270 */         String ip = HiAbstractALMClient.this._socket.getInetAddress().getHostAddress();
/* 271 */         int port = HiAbstractALMClient.this._socket.getPort();
/* 272 */         HiAbstractALMClient.this._log.info("[" + ip + "]:[" + port + "]; recv check data:[" + buf.length() + "]:[" + buf + "]");
/*     */ 
/* 274 */         Thread.sleep(100L);
/* 275 */         return;
/*     */       }
/* 277 */       HiMessage msg = new HiMessage(HiAbstractALMClient.this._server.getName(), HiAbstractALMClient.this._msgType);
/* 278 */       msg.setHeadItem("ECT", "text/plain");
/* 279 */       msg.setHeadItem("SCH", "rp");
/*     */ 
/* 282 */       msg.setHeadItem("STM", new Long(System.currentTimeMillis()));
/*     */ 
/* 285 */       msg.setHeadItem("SIP", HiAbstractALMClient.this._socket.getInetAddress().getHostAddress());
/*     */ 
/* 287 */       msg.setBody(buf);
/* 288 */       if (HiAbstractALMClient.this._log.isInfoEnabled()) {
/* 289 */         HiAbstractALMClient.this._log.info(HiAbstractALMClient._sm.getString("HiPoolTcpConnector.receive00", msg.getRequestId(), HiAbstractALMClient.this._host, String.valueOf(HiAbstractALMClient.this._port), String.valueOf(buf.length()), buf));
/*     */       }
/*     */ 
/* 294 */       HiMessageContext ctx = new HiMessageContext();
/* 295 */       HiMessageContext.setCurrentMessageContext(ctx);
/* 296 */       ctx.setCurrentMsg(msg);
/* 297 */       HiWorker worker = new HiWorker(HiAbstractALMClient.this._writeLock);
/* 298 */       worker.setLogger(HiAbstractALMClient.this._log);
/* 299 */       worker.setTp(HiAbstractALMClient.this._tp);
/* 300 */       worker.setServer(HiAbstractALMClient.this._server);
/* 301 */       worker.setMessageContext(ctx);
/* 302 */       worker.execute();
/*     */     }
/*     */   }
/*     */ }