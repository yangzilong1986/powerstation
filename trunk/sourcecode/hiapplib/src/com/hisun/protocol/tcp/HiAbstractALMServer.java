/*     */ package com.hisun.protocol.tcp;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.HiDefaultServer;
/*     */ import com.hisun.framework.event.IServerEventListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.protocol.tcp.parser.HiIPXMLParser;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiResource;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import com.hisun.util.HiThreadPool;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Vector;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.net.ServerSocketFactory;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public abstract class HiAbstractALMServer
/*     */   implements IServerEventListener, Runnable
/*     */ {
/*  49 */   protected static HiStringManager _sm = HiStringManager.getManager();
/*     */   protected Logger _log;
/*     */   protected int _port;
/*     */   protected String _msgType;
/*     */   protected int _timeOut;
/*     */   protected int _maxThreads;
/*     */   protected int _minThreads;
/*     */   protected int _queueSize;
/*     */   protected int _checkItv;
/*     */   private ICheckConn _checkConn;
/*     */   protected HiMessageInOut _msginout;
/*     */   protected boolean _paused;
/*     */   protected boolean _running;
/*     */   protected ServerSocket _serverSocket;
/*     */   protected Vector _readers;
/*     */   protected Thread _listenThread;
/*     */   protected HiDefaultServer _server;
/*     */   protected HiThreadPool _tp;
/*     */   protected HashSet _ipset;
/*     */   protected IPreProcHandler _preProcHandler;
/*     */   protected HiSSLHandler sslHandler;
/*     */   private ServerSocketFactory factory;
/*     */   private InetAddress inet;
/*     */ 
/*     */   public HiAbstractALMServer()
/*     */   {
/*  50 */     this._log = null;
/*     */ 
/*  53 */     this._msgType = "PLTIN0";
/*  54 */     this._timeOut = 30;
/*  55 */     this._maxThreads = 50;
/*  56 */     this._minThreads = 5;
/*  57 */     this._queueSize = 100;
/*  58 */     this._checkItv = 30;
/*  59 */     this._checkConn = null;
/*  60 */     this._msginout = null;
/*  61 */     this._paused = false;
/*  62 */     this._running = false;
/*     */ 
/*  64 */     this._serverSocket = null;
/*     */ 
/*  66 */     this._readers = new Vector();
/*     */ 
/*  72 */     this.sslHandler = HiSSLHandler.getInstance();
/*     */   }
/*     */ 
/*     */   public void setSslMode(String mode)
/*     */     throws HiException
/*     */   {
/*  81 */     this.sslHandler.setSslMode(Integer.parseInt(mode));
/*     */   }
/*     */ 
/*     */   public void setTrustKS(String certPath)
/*     */     throws HiException
/*     */   {
/*  90 */     this.sslHandler.setTrustKS(certPath);
/*     */   }
/*     */ 
/*     */   public void setIdentityKS(String priKey)
/*     */     throws HiException
/*     */   {
/*  99 */     this.sslHandler.setIdentityKS(priKey);
/*     */   }
/*     */ 
/*     */   public void setKeyPsw(String keyPsw)
/*     */     throws HiException
/*     */   {
/* 108 */     this.sslHandler.setKeyPsw(keyPsw);
/*     */   }
/*     */ 
/*     */   public void setAlg(String alg)
/*     */     throws HiException
/*     */   {
/* 116 */     this.sslHandler.setAlg(alg);
/*     */   }
/*     */ 
/*     */   public void setAuthFlag(String authFlag)
/*     */     throws HiException
/*     */   {
/* 125 */     this.sslHandler.setAuthFlag(Integer.parseInt(authFlag));
/*     */   }
/*     */ 
/*     */   public void setLocalPort(int port) {
/* 129 */     this._port = port;
/*     */   }
/*     */ 
/*     */   public void setPreLen(int preLen) {
/* 133 */     this._msginout.setPreLen(preLen);
/*     */   }
/*     */ 
/*     */   public void setPreLenType(String type) {
/* 137 */     this._msginout.setPreLenType(type);
/*     */   }
/*     */ 
/*     */   public void setTimeOut(int timeOut) {
/* 141 */     this._timeOut = timeOut;
/*     */   }
/*     */ 
/*     */   private ServerSocket createTcpEndpoint(int port)
/*     */     throws HiException
/*     */   {
/*     */     try
/*     */     {
/*     */       ServerSocket serverSocket;
/* 147 */       if ((this.sslHandler != null) && (this.sslHandler.isSSLMode()))
/*     */       {
/* 149 */         this.sslHandler.init();
/* 150 */         this.factory = this.sslHandler.getServerSocketFactory();
/*     */       }
/* 152 */       if (this.factory == null) {
/* 153 */         this.factory = ServerSocketFactory.getDefault();
/*     */       }
/* 155 */       if (this.inet == null) {
/* 156 */         serverSocket = this.factory.createServerSocket(port, 500);
/*     */       }
/*     */       else {
/* 159 */         serverSocket = this.factory.createServerSocket(port, 500, this.inet);
/*     */       }
/*     */ 
/* 162 */       if (this._log.isInfoEnabled()) {
/* 163 */         this._log.info("listen:[" + port + "] started");
/*     */       }
/* 165 */       if ((this.sslHandler != null) && (this.sslHandler.isSSLMode()))
/*     */       {
/* 167 */         this.sslHandler.initServerSocket(serverSocket);
/*     */       }
/* 169 */       return serverSocket;
/*     */     } catch (Exception e) {
/* 171 */       throw new HiException("231208", e.toString(), e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/*     */     try {
/* 178 */       doRun();
/*     */     } catch (InterruptedException e) {
/*     */     }
/*     */     catch (Throwable t) {
/* 182 */       this._log.error(t, t);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doRun() throws IOException, InterruptedException {
/*     */     while (true) {
/*     */       Socket socket;
/*     */       try {
/* 190 */         socket = this._serverSocket.accept();
/*     */       } catch (IOException e) {
/* 192 */         return;
/*     */       }
/*     */ 
/* 195 */       String ip = socket.getInetAddress().getHostAddress();
/* 196 */       if ((this._ipset != null) && (!(this._ipset.contains(ip)))) {
/* 197 */         this._log.warn("invalid ip:[" + ip + "] connected");
/* 198 */         Thread.sleep(100L);
/*     */       }
/*     */ 
/* 201 */       int port = socket.getPort();
/*     */ 
/* 203 */       if (this._log.isInfoEnabled()) {
/* 204 */         this._log.info("client [" + ip + "]:[" + port + "] connected");
/*     */       }
/*     */ 
/* 207 */       socket.setSoTimeout(this._timeOut * 1000);
/* 208 */       if (this._preProcHandler != null) {
/*     */         try {
/* 210 */           while (!(this._preProcHandler.process(socket)))
/*     */           {
/* 212 */             closeConnection(socket);
/*     */           }
/*     */         }
/*     */         catch (Throwable t) {
/*     */           while (true) {
/* 217 */             closeConnection(socket);
/* 218 */             this._log.error(t, t);
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 225 */       Object writeLock = new Object();
/* 226 */       socket.setSoTimeout(0);
/* 227 */       HiReader reader = new HiReader(socket, writeLock);
/* 228 */       Thread thd = new Thread(reader, "Reader:[" + socket + "]");
/*     */ 
/* 231 */       this._readers.add(reader);
/* 232 */       thd.start();
/*     */     }
/*     */   }
/*     */ 
/*     */   private synchronized void closeConnection(Socket socket) {
/* 237 */     if (socket == null)
/* 238 */       return;
/*     */     try {
/* 240 */       socket.close();
/*     */     } catch (IOException e) {
/*     */     }
/* 243 */     socket = null;
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0) throws HiException {
/* 247 */     this._log = arg0.getLog();
/* 248 */     this._server = ((HiDefaultServer)arg0.getServer());
/* 249 */     if (this._queueSize != 0) {
/* 250 */       this._tp = HiThreadPool.createThreadPool(this._minThreads, this._maxThreads, this._queueSize);
/*     */     }
/*     */     else
/* 253 */       this._tp = HiThreadPool.createThreadPool(this._minThreads, this._maxThreads);
/*     */   }
/*     */ 
/*     */   public void serverStart(ServerEvent arg0)
/*     */     throws HiException
/*     */   {
/* 459 */     this._serverSocket = createTcpEndpoint(this._port);
/* 460 */     this._listenThread = new Thread(this, "Listener:[" + this._port + "]");
/*     */ 
/* 462 */     this._listenThread.start();
/* 463 */     this._running = true;
/* 464 */     this._paused = false;
/*     */   }
/*     */ 
/*     */   public void serverStop(ServerEvent arg0) throws HiException {
/* 468 */     if (!(this._running))
/* 469 */       return;
/* 470 */     this._running = false;
/* 471 */     this._log.debug("serverStop start ... ");
/* 472 */     this._tp.shutdown();
/* 473 */     this._log.debug("tp shutdown ...");
/*     */     try {
/* 475 */       this._tp.awaitTermination(5L, TimeUnit.SECONDS);
/*     */     } catch (InterruptedException e2) {
/*     */     }
/* 478 */     this._log.debug("tp shutdown finished");
/*     */ 
/* 480 */     this._log.debug("_serverSocket closing ...");
/*     */     try {
/* 482 */       this._listenThread.interrupt();
/* 483 */       if (this._serverSocket != null)
/* 484 */         this._serverSocket.close();
/*     */     }
/*     */     catch (IOException e1) {
/* 487 */       e1.printStackTrace();
/*     */     }
/* 489 */     this._serverSocket = null;
/* 490 */     this._log.debug("_serverSocket closed");
/*     */ 
/* 492 */     for (int i = 0; i < this._readers.size(); ++i) {
/* 493 */       HiReader reader = (HiReader)this._readers.get(i);
/* 494 */       this._log.debug("_reader socket, " + reader.getName() + " stopping");
/* 495 */       reader.close();
/* 496 */       this._log.debug("_reader socket, " + reader.getName() + " stoppped");
/*     */     }
/* 498 */     this._readers.clear();
/*     */   }
/*     */ 
/*     */   public void serverDestroy(ServerEvent arg0) throws HiException {
/* 502 */     serverStop(arg0);
/*     */   }
/*     */ 
/*     */   public void serverPause(ServerEvent arg0) {
/* 506 */     this._paused = true;
/*     */   }
/*     */ 
/*     */   public void serverResume(ServerEvent arg0) {
/* 510 */     this._paused = false;
/*     */   }
/*     */ 
/*     */   public void setCheckConn(ICheckConn checkConn) {
/* 514 */     this._checkConn = checkConn;
/*     */   }
/*     */ 
/*     */   public int getCheckItv() {
/* 518 */     return this._checkItv;
/*     */   }
/*     */ 
/*     */   public void setCheckItv(int itv) {
/* 522 */     this._checkItv = itv;
/*     */   }
/*     */ 
/*     */   public String getMsgType() {
/* 526 */     return this._msgType;
/*     */   }
/*     */ 
/*     */   public void setMsgType(String type) {
/* 530 */     this._msgType = type;
/*     */   }
/*     */ 
/*     */   public int getMaxThreads() {
/* 534 */     return this._maxThreads;
/*     */   }
/*     */ 
/*     */   public void setMaxThreads(int threads) {
/* 538 */     this._maxThreads = threads;
/*     */   }
/*     */ 
/*     */   public int getMinThreads() {
/* 542 */     return this._minThreads;
/*     */   }
/*     */ 
/*     */   public void setMinThreads(int threads) {
/* 546 */     this._minThreads = threads;
/*     */   }
/*     */ 
/*     */   public int getQueueSize() {
/* 550 */     return this._queueSize;
/*     */   }
/*     */ 
/*     */   public void setQueueSize(int size) {
/* 554 */     this._queueSize = size;
/*     */   }
/*     */ 
/*     */   public void setMsginout(HiMessageInOut msginout) {
/* 558 */     this._msginout = msginout; }
/*     */ 
/*     */   public HiMessageInOut getMsginout() {
/* 561 */     return this._msginout;
/*     */   }
/*     */ 
/*     */   public void setIpLstFil(String ipLstFil)
/*     */     throws Exception
/*     */   {
/* 569 */     if (this._ipset == null)
/* 570 */       this._ipset = new HashSet();
/* 571 */     if (!(StringUtils.isEmpty(ipLstFil))) {
/* 572 */       HiIPXMLParser ipParser = new HiIPXMLParser();
/* 573 */       URL url = HiResource.getResource(ipLstFil);
/* 574 */       if (url == null) {
/* 575 */         throw new HiException("212004", ipLstFil);
/*     */       }
/*     */ 
/* 578 */       this._ipset.addAll((ArrayList)ipParser.parse(url));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setPreProcHandler(IPreProcHandler handler)
/*     */   {
/* 584 */     this._preProcHandler = handler;
/*     */   }
/*     */ 
/*     */   class HiManager
/*     */     implements Runnable
/*     */   {
/*     */     private int itv;
/*     */     private Socket _socket;
/*     */ 
/*     */     public HiManager(Socket paramSocket)
/*     */     {
/* 415 */       this._socket = paramSocket;
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/*     */       try {
/* 421 */         if (Thread.interrupted())
/*     */           break label96;
/* 423 */         Thread.sleep(1000L);
/* 424 */         if (!(HiAbstractALMServer.this._running))
/*     */           break label96;
/* 426 */         if ((this._socket == null) || (this._socket.isClosed()))
/*     */           break label96;
/* 428 */         doRun();
/*     */       } catch (InterruptedException e1) {
/*     */       }
/*     */       catch (Throwable e) {
/* 432 */         if (HiAbstractALMServer.this._running)
/* 433 */           HiAbstractALMServer.this._log.error(e, e);
/* 434 */         HiAbstractALMServer.this.closeConnection(this._socket);
/*     */       }
/*     */ 
/* 438 */       label96: HiAbstractALMServer.this.closeConnection(this._socket);
/* 439 */       HiAbstractALMServer.this._readers.remove(Thread.currentThread());
/*     */     }
/*     */ 
/*     */     public void doRun() throws IOException {
/* 443 */       this.itv += 1;
/* 444 */       if (this.itv == HiAbstractALMServer.this._checkItv) {
/* 445 */         this.itv = 0;
/* 446 */         String data = HiAbstractALMServer.this._checkConn.getCheckData();
/* 447 */         HiByteBuffer buf = new HiByteBuffer(data.getBytes());
/* 448 */         synchronized (this._socket.getOutputStream()) {
/* 449 */           HiAbstractALMServer.this._msginout.write(this._socket.getOutputStream(), buf);
/*     */         }
/* 451 */         String ip = this._socket.getInetAddress().getHostAddress();
/* 452 */         int port = this._socket.getPort();
/* 453 */         HiAbstractALMServer.this._log.info("[" + ip + "]:[" + port + "]; send check data:[" + buf.length() + "]:[" + buf + "]");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   class HiReader
/*     */     implements Runnable
/*     */   {
/*     */     private Socket _socket;
/*     */     private Thread _thd;
/*     */     private long _lastRecvDataTime;
/*     */     private long _lastSendDataTime;
/*     */     private Object _writeLock;
/*     */ 
/*     */     public void close()
/*     */     {
/* 279 */       this._thd.interrupt();
/* 280 */       if (this._socket == null)
/* 281 */         return;
/*     */       try
/*     */       {
/* 284 */         this._socket.close();
/*     */       } catch (IOException e) {
/* 286 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/*     */     public void setLastRecvDataTime(long lastRecvDataTime) {
/* 291 */       this._lastRecvDataTime = lastRecvDataTime;
/*     */     }
/*     */ 
/*     */     public long getLastRecvDataTime() {
/* 295 */       return this._lastRecvDataTime;
/*     */     }
/*     */ 
/*     */     public void setLastSendDataTime(long lastSendDataTime) {
/* 299 */       this._lastSendDataTime = lastSendDataTime;
/*     */     }
/*     */ 
/*     */     public long getLastSendDataTime() {
/* 303 */       return this._lastSendDataTime;
/*     */     }
/*     */ 
/*     */     public String getName() {
/* 307 */       return this._thd.getName();
/*     */     }
/*     */ 
/*     */     HiReader(Socket paramSocket, Object paramObject) {
/* 311 */       this._socket = paramSocket;
/* 312 */       this._writeLock = writeLock;
/*     */     }
/*     */ 
/*     */     public void run() {
/* 316 */       this._thd = Thread.currentThread();
/*     */       while (true) try {
/*     */           while (true) {
/* 319 */             if (Thread.interrupted()) {
/*     */               break label116;
/*     */             }
/* 322 */             if ((this._socket == null) || (this._socket.isClosed()))
/*     */               break label116;
/* 324 */             if (!(HiAbstractALMServer.this._running))
/*     */               break label116;
/* 326 */             if (!(HiAbstractALMServer.this._paused)) break;
/* 327 */             Thread.sleep(1000L);
/*     */           }
/*     */ 
/* 331 */           doRun();
/*     */         } catch (InterruptedException e1) {
/* 333 */           break label116:
/*     */         } catch (Throwable e) {
/* 335 */           HiAbstractALMServer.this.closeConnection(this._socket);
/* 336 */           if (HiAbstractALMServer.this._running) {
/* 337 */             HiAbstractALMServer.this._log.error(e, e);
/*     */           }
/*     */         }
/*     */ 
/* 341 */       label116: HiAbstractALMServer.this.closeConnection(this._socket);
/* 342 */       if (HiAbstractALMServer.this._running)
/* 343 */         HiAbstractALMServer.this._readers.remove(this);
/*     */     }
/*     */ 
/*     */     public void doRun()
/*     */       throws IOException, HiException, InterruptedException
/*     */     {
/*     */       int rdlen;
/* 349 */       HiByteBuffer buf = new HiByteBuffer(1024);
/*     */       try
/*     */       {
/* 352 */         rdlen = HiAbstractALMServer.this._msginout.read(this._socket.getInputStream(), buf);
/* 353 */         HiAbstractALMServer.this._log.debug(this._socket + " reading");
/*     */       } catch (IOException e) {
/* 355 */         throw new HiException("231205", "connector receive error", e);
/*     */       }
/*     */ 
/* 359 */       if (rdlen == 0) {
/* 360 */         throw new IOException("peer socket close");
/*     */       }
/* 362 */       this._lastRecvDataTime = System.currentTimeMillis();
/* 363 */       if (HiAbstractALMServer.this._checkConn.isCheckData(buf))
/*     */       {
/* 365 */         String ip = this._socket.getInetAddress().getHostAddress();
/* 366 */         int port = this._socket.getPort();
/* 367 */         HiAbstractALMServer.this._log.info("[" + ip + "]:[" + port + "]; recv check data:[" + buf.length() + "]:[" + buf + "]");
/* 368 */         synchronized (this._writeLock) {
/* 369 */           HiAbstractALMServer.this._msginout.write(this._socket.getOutputStream(), HiAbstractALMServer.this._checkConn.getRspCheckData(buf));
/*     */         }
/* 371 */         HiAbstractALMServer.this._log.info("[" + ip + "]:[" + port + "]; send check data:[" + buf.length() + "]:[" + buf + "]");
/* 372 */         Thread.sleep(100L);
/* 373 */         return;
/*     */       }
/* 375 */       HiMessage msg = new HiMessage(HiAbstractALMServer.this._server.getName(), HiAbstractALMServer.this._msgType);
/* 376 */       msg.setBody(buf);
/* 377 */       if (HiAbstractALMServer.this._log.isInfoEnabled()) {
/* 378 */         String ip = this._socket.getInetAddress().getHostAddress();
/* 379 */         int port = this._socket.getPort();
/* 380 */         HiAbstractALMServer.this._log.info(HiAbstractALMServer._sm.getString("HiPoolTcpConnector.receive00", msg.getRequestId(), ip, String.valueOf(port), String.valueOf(buf.length()), buf));
/*     */       }
/*     */ 
/* 386 */       HiMessageContext ctx = new HiMessageContext();
/* 387 */       msg.setHeadItem("ECT", "text/plain");
/* 388 */       msg.setHeadItem("SCH", "rq");
/* 389 */       msg.setHeadItem("STM", new Long(System.currentTimeMillis()));
/* 390 */       msg.setHeadItem("SIP", this._socket.getInetAddress().getHostAddress());
/* 391 */       HiMessageContext.setCurrentMessageContext(ctx);
/* 392 */       ctx.setCurrentMsg(msg);
/* 393 */       HiWorker worker = new HiWorker(this._writeLock);
/* 394 */       worker.setLogger(HiAbstractALMServer.this._log);
/* 395 */       worker.setTp(HiAbstractALMServer.this._tp);
/* 396 */       worker.setServer(HiAbstractALMServer.this._server);
/* 397 */       worker.setMessageContext(ctx);
/* 398 */       worker.setSocket(this._socket);
/* 399 */       worker.setMsginout(HiAbstractALMServer.this._msginout);
/* 400 */       worker.execute();
/*     */     }
/*     */   }
/*     */ }