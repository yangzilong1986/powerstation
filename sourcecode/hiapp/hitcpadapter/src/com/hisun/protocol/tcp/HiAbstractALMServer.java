 package com.hisun.protocol.tcp;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.HiDefaultServer;
 import com.hisun.framework.event.IServerEventListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.protocol.tcp.parser.HiIPXMLParser;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiResource;
 import com.hisun.util.HiStringManager;
 import com.hisun.util.HiThreadPool;
 import java.io.IOException;
 import java.net.InetAddress;
 import java.net.ServerSocket;
 import java.net.Socket;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.HashSet;
 import java.util.Vector;
 import java.util.concurrent.TimeUnit;
 import javax.net.ServerSocketFactory;
 import org.apache.commons.lang.StringUtils;
 
 public abstract class HiAbstractALMServer
   implements IServerEventListener, Runnable
 {
   protected static HiStringManager _sm = HiStringManager.getManager();
   protected Logger _log;
   protected int _port;
   protected String _msgType;
   protected int _timeOut;
   protected int _maxThreads;
   protected int _minThreads;
   protected int _queueSize;
   protected int _checkItv;
   private ICheckConn _checkConn;
   protected HiMessageInOut _msginout;
   protected boolean _paused;
   protected boolean _running;
   protected ServerSocket _serverSocket;
   protected Vector _readers;
   protected Thread _listenThread;
   protected HiDefaultServer _server;
   protected HiThreadPool _tp;
   protected HashSet _ipset;
   protected IPreProcHandler _preProcHandler;
   protected HiSSLHandler sslHandler;
   private ServerSocketFactory factory;
   private InetAddress inet;
 
   public HiAbstractALMServer()
   {
     this._log = null;
 
     this._msgType = "PLTIN0";
     this._timeOut = 30;
     this._maxThreads = 50;
     this._minThreads = 5;
     this._queueSize = 100;
     this._checkItv = 30;
     this._checkConn = null;
     this._msginout = null;
     this._paused = false;
     this._running = false;
 
     this._serverSocket = null;
 
     this._readers = new Vector();
 
     this.sslHandler = HiSSLHandler.getInstance();
   }
 
   public void setSslMode(String mode)
     throws HiException
   {
     this.sslHandler.setSslMode(Integer.parseInt(mode));
   }
 
   public void setTrustKS(String certPath)
     throws HiException
   {
     this.sslHandler.setTrustKS(certPath);
   }
 
   public void setIdentityKS(String priKey)
     throws HiException
   {
     this.sslHandler.setIdentityKS(priKey);
   }
 
   public void setKeyPsw(String keyPsw)
     throws HiException
   {
     this.sslHandler.setKeyPsw(keyPsw);
   }
 
   public void setAlg(String alg)
     throws HiException
   {
     this.sslHandler.setAlg(alg);
   }
 
   public void setAuthFlag(String authFlag)
     throws HiException
   {
     this.sslHandler.setAuthFlag(Integer.parseInt(authFlag));
   }
 
   public void setLocalPort(int port) {
     this._port = port;
   }
 
   public void setPreLen(int preLen) {
     this._msginout.setPreLen(preLen);
   }
 
   public void setPreLenType(String type) {
     this._msginout.setPreLenType(type);
   }
 
   public void setTimeOut(int timeOut) {
     this._timeOut = timeOut;
   }
 
   private ServerSocket createTcpEndpoint(int port)
     throws HiException
   {
     try
     {
       ServerSocket serverSocket;
       if ((this.sslHandler != null) && (this.sslHandler.isSSLMode()))
       {
         this.sslHandler.init();
         this.factory = this.sslHandler.getServerSocketFactory();
       }
       if (this.factory == null) {
         this.factory = ServerSocketFactory.getDefault();
       }
       if (this.inet == null) {
         serverSocket = this.factory.createServerSocket(port, 500);
       }
       else {
         serverSocket = this.factory.createServerSocket(port, 500, this.inet);
       }
 
       if (this._log.isInfoEnabled()) {
         this._log.info("listen:[" + port + "] started");
       }
       if ((this.sslHandler != null) && (this.sslHandler.isSSLMode()))
       {
         this.sslHandler.initServerSocket(serverSocket);
       }
       return serverSocket;
     } catch (Exception e) {
       throw new HiException("231208", e.toString(), e);
     }
   }
 
   public void run()
   {
     try {
       doRun();
     } catch (InterruptedException e) {
     }
     catch (Throwable t) {
       this._log.error(t, t);
     }
   }
 
   public void doRun() throws IOException, InterruptedException {
     while (true) {
       Socket socket;
       try {
         socket = this._serverSocket.accept();
       } catch (IOException e) {
         return;
       }
 
       String ip = socket.getInetAddress().getHostAddress();
       if ((this._ipset != null) && (!(this._ipset.contains(ip)))) {
         this._log.warn("invalid ip:[" + ip + "] connected");
         Thread.sleep(100L);
       }
 
       int port = socket.getPort();
 
       if (this._log.isInfoEnabled()) {
         this._log.info("client [" + ip + "]:[" + port + "] connected");
       }
 
       socket.setSoTimeout(this._timeOut * 1000);
       if (this._preProcHandler != null) {
         try {
           while (!(this._preProcHandler.process(socket)))
           {
             closeConnection(socket);
           }
         }
         catch (Throwable t) {
           while (true) {
             closeConnection(socket);
             this._log.error(t, t);
           }
 
         }
 
       }
 
       Object writeLock = new Object();
       socket.setSoTimeout(0);
       HiReader reader = new HiReader(socket, writeLock);
       Thread thd = new Thread(reader, "Reader:[" + socket + "]");
 
       this._readers.add(reader);
       thd.start();
     }
   }
 
   private synchronized void closeConnection(Socket socket) {
     if (socket == null)
       return;
     try {
       socket.close();
     } catch (IOException e) {
     }
     socket = null;
   }
 
   public void serverInit(ServerEvent arg0) throws HiException {
     this._log = arg0.getLog();
     this._server = ((HiDefaultServer)arg0.getServer());
     if (this._queueSize != 0) {
       this._tp = HiThreadPool.createThreadPool(this._minThreads, this._maxThreads, this._queueSize);
     }
     else
       this._tp = HiThreadPool.createThreadPool(this._minThreads, this._maxThreads);
   }
 
   public void serverStart(ServerEvent arg0)
     throws HiException
   {
     this._serverSocket = createTcpEndpoint(this._port);
     this._listenThread = new Thread(this, "Listener:[" + this._port + "]");
 
     this._listenThread.start();
     this._running = true;
     this._paused = false;
   }
 
   public void serverStop(ServerEvent arg0) throws HiException {
     if (!(this._running))
       return;
     this._running = false;
     this._log.debug("serverStop start ... ");
     this._tp.shutdown();
     this._log.debug("tp shutdown ...");
     try {
       this._tp.awaitTermination(5L, TimeUnit.SECONDS);
     } catch (InterruptedException e2) {
     }
     this._log.debug("tp shutdown finished");
 
     this._log.debug("_serverSocket closing ...");
     try {
       this._listenThread.interrupt();
       if (this._serverSocket != null)
         this._serverSocket.close();
     }
     catch (IOException e1) {
       e1.printStackTrace();
     }
     this._serverSocket = null;
     this._log.debug("_serverSocket closed");
 
     for (int i = 0; i < this._readers.size(); ++i) {
       HiReader reader = (HiReader)this._readers.get(i);
       this._log.debug("_reader socket, " + reader.getName() + " stopping");
       reader.close();
       this._log.debug("_reader socket, " + reader.getName() + " stoppped");
     }
     this._readers.clear();
   }
 
   public void serverDestroy(ServerEvent arg0) throws HiException {
     serverStop(arg0);
   }
 
   public void serverPause(ServerEvent arg0) {
     this._paused = true;
   }
 
   public void serverResume(ServerEvent arg0) {
     this._paused = false;
   }
 
   public void setCheckConn(ICheckConn checkConn) {
     this._checkConn = checkConn;
   }
 
   public int getCheckItv() {
     return this._checkItv;
   }
 
   public void setCheckItv(int itv) {
     this._checkItv = itv;
   }
 
   public String getMsgType() {
     return this._msgType;
   }
 
   public void setMsgType(String type) {
     this._msgType = type;
   }
 
   public int getMaxThreads() {
     return this._maxThreads;
   }
 
   public void setMaxThreads(int threads) {
     this._maxThreads = threads;
   }
 
   public int getMinThreads() {
     return this._minThreads;
   }
 
   public void setMinThreads(int threads) {
     this._minThreads = threads;
   }
 
   public int getQueueSize() {
     return this._queueSize;
   }
 
   public void setQueueSize(int size) {
     this._queueSize = size;
   }
 
   public void setMsginout(HiMessageInOut msginout) {
     this._msginout = msginout; }
 
   public HiMessageInOut getMsginout() {
     return this._msginout;
   }
 
   public void setIpLstFil(String ipLstFil)
     throws Exception
   {
     if (this._ipset == null)
       this._ipset = new HashSet();
     if (!(StringUtils.isEmpty(ipLstFil))) {
       HiIPXMLParser ipParser = new HiIPXMLParser();
       URL url = HiResource.getResource(ipLstFil);
       if (url == null) {
         throw new HiException("212004", ipLstFil);
       }
 
       this._ipset.addAll((ArrayList)ipParser.parse(url));
     }
   }
 
   public void setPreProcHandler(IPreProcHandler handler)
   {
     this._preProcHandler = handler;
   }
 
   class HiManager
     implements Runnable
   {
     private int itv;
     private Socket _socket;
 
     public HiManager(Socket paramSocket)
     {
       this._socket = paramSocket;
     }
 
     public void run()
     {
       try {
         if (Thread.interrupted())
           break label96;
         Thread.sleep(1000L);
         if (!(HiAbstractALMServer.this._running))
           break label96;
         if ((this._socket == null) || (this._socket.isClosed()))
           break label96;
         doRun();
       } catch (InterruptedException e1) {
       }
       catch (Throwable e) {
         if (HiAbstractALMServer.this._running)
           HiAbstractALMServer.this._log.error(e, e);
         HiAbstractALMServer.this.closeConnection(this._socket);
       }
 
       label96: HiAbstractALMServer.this.closeConnection(this._socket);
       HiAbstractALMServer.this._readers.remove(Thread.currentThread());
     }
 
     public void doRun() throws IOException {
       this.itv += 1;
       if (this.itv == HiAbstractALMServer.this._checkItv) {
         this.itv = 0;
         String data = HiAbstractALMServer.this._checkConn.getCheckData();
         HiByteBuffer buf = new HiByteBuffer(data.getBytes());
         synchronized (this._socket.getOutputStream()) {
           HiAbstractALMServer.this._msginout.write(this._socket.getOutputStream(), buf);
         }
         String ip = this._socket.getInetAddress().getHostAddress();
         int port = this._socket.getPort();
         HiAbstractALMServer.this._log.info("[" + ip + "]:[" + port + "]; send check data:[" + buf.length() + "]:[" + buf + "]");
       }
     }
   }
 
   class HiReader
     implements Runnable
   {
     private Socket _socket;
     private Thread _thd;
     private long _lastRecvDataTime;
     private long _lastSendDataTime;
     private Object _writeLock;
 
     public void close()
     {
       this._thd.interrupt();
       if (this._socket == null)
         return;
       try
       {
         this._socket.close();
       } catch (IOException e) {
         e.printStackTrace();
       }
     }
 
     public void setLastRecvDataTime(long lastRecvDataTime) {
       this._lastRecvDataTime = lastRecvDataTime;
     }
 
     public long getLastRecvDataTime() {
       return this._lastRecvDataTime;
     }
 
     public void setLastSendDataTime(long lastSendDataTime) {
       this._lastSendDataTime = lastSendDataTime;
     }
 
     public long getLastSendDataTime() {
       return this._lastSendDataTime;
     }
 
     public String getName() {
       return this._thd.getName();
     }
 
     HiReader(Socket paramSocket, Object paramObject) {
       this._socket = paramSocket;
       this._writeLock = writeLock;
     }
 
     public void run() {
       this._thd = Thread.currentThread();
       while (true) try {
           while (true) {
             if (Thread.interrupted()) {
               break label116;
             }
             if ((this._socket == null) || (this._socket.isClosed()))
               break label116;
             if (!(HiAbstractALMServer.this._running))
               break label116;
             if (!(HiAbstractALMServer.this._paused)) break;
             Thread.sleep(1000L);
           }
 
           doRun();
         } catch (InterruptedException e1) {
           break label116:
         } catch (Throwable e) {
           HiAbstractALMServer.this.closeConnection(this._socket);
           if (HiAbstractALMServer.this._running) {
             HiAbstractALMServer.this._log.error(e, e);
           }
         }
 
       label116: HiAbstractALMServer.this.closeConnection(this._socket);
       if (HiAbstractALMServer.this._running)
         HiAbstractALMServer.this._readers.remove(this);
     }
 
     public void doRun()
       throws IOException, HiException, InterruptedException
     {
       int rdlen;
       HiByteBuffer buf = new HiByteBuffer(1024);
       try
       {
         rdlen = HiAbstractALMServer.this._msginout.read(this._socket.getInputStream(), buf);
         HiAbstractALMServer.this._log.debug(this._socket + " reading");
       } catch (IOException e) {
         throw new HiException("231205", "connector receive error", e);
       }
 
       if (rdlen == 0) {
         throw new IOException("peer socket close");
       }
       this._lastRecvDataTime = System.currentTimeMillis();
       if (HiAbstractALMServer.this._checkConn.isCheckData(buf))
       {
         String ip = this._socket.getInetAddress().getHostAddress();
         int port = this._socket.getPort();
         HiAbstractALMServer.this._log.info("[" + ip + "]:[" + port + "]; recv check data:[" + buf.length() + "]:[" + buf + "]");
         synchronized (this._writeLock) {
           HiAbstractALMServer.this._msginout.write(this._socket.getOutputStream(), HiAbstractALMServer.this._checkConn.getRspCheckData(buf));
         }
         HiAbstractALMServer.this._log.info("[" + ip + "]:[" + port + "]; send check data:[" + buf.length() + "]:[" + buf + "]");
         Thread.sleep(100L);
         return;
       }
       HiMessage msg = new HiMessage(HiAbstractALMServer.this._server.getName(), HiAbstractALMServer.this._msgType);
       msg.setBody(buf);
       if (HiAbstractALMServer.this._log.isInfoEnabled()) {
         String ip = this._socket.getInetAddress().getHostAddress();
         int port = this._socket.getPort();
         HiAbstractALMServer.this._log.info(HiAbstractALMServer._sm.getString("HiPoolTcpConnector.receive00", msg.getRequestId(), ip, String.valueOf(port), String.valueOf(buf.length()), buf));
       }
 
       HiMessageContext ctx = new HiMessageContext();
       msg.setHeadItem("ECT", "text/plain");
       msg.setHeadItem("SCH", "rq");
       msg.setHeadItem("STM", new Long(System.currentTimeMillis()));
       msg.setHeadItem("SIP", this._socket.getInetAddress().getHostAddress());
       HiMessageContext.setCurrentMessageContext(ctx);
       ctx.setCurrentMsg(msg);
       HiWorker worker = new HiWorker(this._writeLock);
       worker.setLogger(HiAbstractALMServer.this._log);
       worker.setTp(HiAbstractALMServer.this._tp);
       worker.setServer(HiAbstractALMServer.this._server);
       worker.setMessageContext(ctx);
       worker.setSocket(this._socket);
       worker.setMsginout(HiAbstractALMServer.this._msginout);
       worker.execute();
     }
   }
 }