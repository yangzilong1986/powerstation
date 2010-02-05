 package com.hisun.protocol.tcp;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.HiDefaultServer;
 import com.hisun.framework.event.IServerEventListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiStringManager;
 import com.hisun.util.HiThreadPool;
 import java.io.IOException;
 import java.net.ConnectException;
 import java.net.InetAddress;
 import java.net.InetSocketAddress;
 import java.net.Socket;
 import java.net.UnknownHostException;
 import java.util.concurrent.TimeUnit;
 import javax.net.SocketFactory;
 
 public abstract class HiAbstractALMClient
   implements IHandler, IServerEventListener
 {
   protected static HiStringManager _sm = HiStringManager.getManager();
   protected String _msgType;
   protected int _port;
   protected String _host;
   protected int _timeOut;
   protected int _maxThreads;
   protected int _minThreads;
   protected int _queueSize;
   protected int _checkItv;
   private ICheckConn _checkConn;
   protected HiMessageInOut _msginout;
   protected Logger _log;
   protected Object _writeLock;
   protected Socket _socket;
   protected Thread _mngThd;
   protected Thread _readerThd;
   protected HiDefaultServer _server;
   protected HiThreadPool _tp;
   protected boolean _paused;
   protected boolean _running;
   protected IPreProcHandler _preProcHandler;
   protected HiSSLHandler sslHandler;
   protected SocketFactory socketFactory;
 
   public HiAbstractALMClient()
   {
     this._msgType = "PLTIN0";
 
     this._timeOut = 30;
     this._maxThreads = 50;
     this._minThreads = 5;
     this._queueSize = 100;
     this._checkItv = 30;
 
     this._checkConn = null;
     this._msginout = null;
     this._log = null;
 
     this._writeLock = new Object();
     this._socket = null;
     this._mngThd = null;
     this._readerThd = null;
 
     this._paused = false;
     this._running = false;
     this._preProcHandler = null;
     this.sslHandler = HiSSLHandler.getInstance();
     this.socketFactory = null;
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
 
   public void setAuthFlag(String authFlag)
     throws HiException
   {
     this.sslHandler.setAuthFlag(Integer.parseInt(authFlag));
   }
 
   public void setPort(int port) {
     this._port = port;
   }
 
   public void setHost(String host) {
     this._host = host;
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
 
   public void process(HiMessageContext ctx) throws HiException {
     try {
       HiMessage msg = ctx.getCurrentMsg();
       send(msg, this._host, this._port);
     } catch (HiException e) {
       this._log.error(e);
       throw e;
     }
   }
 
   public void send(HiMessage msg, String ip, int port) throws HiException {
     if (this._socket == null) {
       throw new HiException("231204", "connector error");
     }
 
     if (this._log.isInfoEnabled())
     {
       HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
       this._log.info(_sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), ip, String.valueOf(port), String.valueOf(byteBuffer.length()), byteBuffer));
     }
 
     try
     {
       synchronized (this._writeLock) {
         this._msginout.write(this._socket.getOutputStream(), msg);
       }
     } catch (IOException e) {
       closeConnection(this._socket);
       throw new HiException("231207", "connector send error", e);
     }
 
     if (this._log.isDebugEnabled())
       this._log.debug("[connector] return");
   }
 
   private Socket createConnection(String host, int port)
     throws UnknownHostException, IOException, HiException
   {
     if (this.sslHandler.isSSLMode()) {
       this.sslHandler.init();
       this.socketFactory = this.sslHandler.getSocketFactory();
     } else {
       this.socketFactory = SocketFactory.getDefault();
     }
 
     Socket socket = this.socketFactory.createSocket();
     if (this._timeOut > 0)
       socket.connect(new InetSocketAddress(host, port), this._timeOut * 1000);
     else
       socket.connect(new InetSocketAddress(host, port));
     socket.setSoTimeout(0);
     return socket;
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
     this._paused = false;
     this._running = true;
     this._mngThd = new Thread(new HiMananger(), "Manager:[" + this._host + ":" + this._port + "]");
 
     this._readerThd = new Thread(new HiReader(), "Reader:[" + this._host + ":" + this._port + "]");
 
     this._mngThd.start();
     this._readerThd.start();
   }
 
   public void serverStop(ServerEvent arg0) throws HiException {
     this._running = false;
     closeConnection(this._socket);
     this._log.debug("serverStop start ... ");
     this._tp.shutdown();
     this._log.debug("tp shutdown ...");
     try {
       this._tp.awaitTermination(5L, TimeUnit.SECONDS);
     } catch (InterruptedException e1) {
       e1.printStackTrace();
     }
     this._log.debug("tp shutdown finished");
     this._log.debug(this._mngThd + " stopping");
     try {
       if (this._socket != null)
         this._socket.close();
     }
     catch (IOException e1) {
       e1.printStackTrace();
     }
     if (this._mngThd != null) {
       this._mngThd.interrupt();
       try {
         this._mngThd.join(5000L);
       } catch (InterruptedException e) {
       }
       this._mngThd = null;
     }
     this._log.debug(this._mngThd + " stopped");
     this._log.debug(this._readerThd + " stopping");
 
     if (this._readerThd != null) {
       this._readerThd.interrupt();
       try {
         this._readerThd.join(5000L);
       } catch (InterruptedException e) {
       }
       this._readerThd = null;
     }
     this._log.debug(this._readerThd + " stopped");
   }
 
   public void serverDestroy(ServerEvent arg0) throws HiException
   {
     serverStop(arg0);
   }
 
   public void serverPause(ServerEvent arg0) {
     this._paused = true;
   }
 
   public void serverResume(ServerEvent arg0) {
     this._paused = false;
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
 
   public int getCheckItv() {
     return this._checkItv;
   }
 
   public void setCheckItv(int checkItv) {
     this._checkItv = checkItv;
   }
 
   public void setCheckConn(ICheckConn checkConn) {
     this._checkConn = checkConn;
   }
 
   public void setMsginout(HiMessageInOut msginout) {
     this._msginout = msginout;
   }
 
   public HiMessageInOut getMsginout() {
     return this._msginout;
   }
 
   public IPreProcHandler getPreProcHandler() {
     return this._preProcHandler;
   }
 
   public void setPreProcHandler(IPreProcHandler preProcHandler) {
     this._preProcHandler = preProcHandler;
   }
 
   class HiMananger
     implements Runnable
   {
     private int itv;
 
     HiMananger()
     {
       this.itv = 0;
     }
 
     public void run() {
       try {
         if (Thread.interrupted())
           return;
         if (!(HiAbstractALMClient.this._running))
           return;
         Thread.sleep(1000L);
         doRun();
       } catch (InterruptedException e1) {
       }
       catch (ConnectException e2) {
         HiAbstractALMClient.this._log.info("[" + HiAbstractALMClient.this._host + "]:[" + HiAbstractALMClient.this._port + "] Connection refused");
       }
       catch (Throwable e)
       {
         if (HiAbstractALMClient.this._running)
           HiAbstractALMClient.this._log.error(e, e);
         HiAbstractALMClient.this.closeConnection(HiAbstractALMClient.this._socket);
       }
     }
 
     public void doRun() throws IOException, HiException
     {
       checkConnection(HiAbstractALMClient.this._socket);
       if (HiAbstractALMClient.this._socket == null)
         return;
       this.itv += 1;
       if (this.itv == HiAbstractALMClient.this._checkItv) {
         this.itv = 0;
         String data = HiAbstractALMClient.this._checkConn.getCheckData();
         HiByteBuffer buf = new HiByteBuffer(data.getBytes());
         synchronized (HiAbstractALMClient.this._writeLock) {
           HiAbstractALMClient.this._msginout.write(HiAbstractALMClient.this._socket.getOutputStream(), buf);
         }
         String ip = HiAbstractALMClient.this._socket.getInetAddress().getHostAddress();
         int port = HiAbstractALMClient.this._socket.getPort();
         HiAbstractALMClient.this._log.info("[" + ip + "]:[" + port + "]; send check data:[" + buf.length() + "]:[" + buf + "]");
       }
     }
 
     protected void checkConnection(Socket socket)
       throws IOException, HiException
     {
       if ((HiAbstractALMClient.this._socket == null) || (HiAbstractALMClient.this._socket.isClosed())) {
         socket = HiAbstractALMClient.this.createConnection(HiAbstractALMClient.this._host, HiAbstractALMClient.this._port);
         if (HiAbstractALMClient.this._log.isInfoEnabled()) {
           HiAbstractALMClient.this._log.info("connect [" + HiAbstractALMClient.this._host + "]:[" + HiAbstractALMClient.this._port + "] successful!");
         }
 
         if ((HiAbstractALMClient.this._preProcHandler != null) && 
           (!(HiAbstractALMClient.this._preProcHandler.process(socket))))
         {
           HiAbstractALMClient.this.closeConnection(socket);
         }
 
         HiAbstractALMClient.this._socket = socket;
       }
     }
   }
 
   class HiReader
     implements Runnable
   {
     public void run()
     {
       try
       {
         while (true)
         {
           while (true)
           {
             if (Thread.interrupted()) {
               return;
             }
             if (!(HiAbstractALMClient.this._paused)) break;
             Thread.sleep(1000L);
           }
 
           if (!(HiAbstractALMClient.this._running)) {
             return;
           }
 
           if ((HiAbstractALMClient.this._socket != null) && (!(HiAbstractALMClient.this._socket.isClosed()))) break;
           Thread.sleep(1000L);
         }
 
         doRun();
       } catch (InterruptedException e1) {
       }
       catch (Throwable e) {
         if (HiAbstractALMClient.this._running)
           HiAbstractALMClient.this._log.error(e, e);
         HiAbstractALMClient.this.closeConnection(HiAbstractALMClient.this._socket);
       }
     }
 
     public void doRun()
       throws IOException, HiException, InterruptedException
     {
       int rdlen;
       HiByteBuffer buf = new HiByteBuffer(1024);
       try
       {
         rdlen = HiAbstractALMClient.this._msginout.read(HiAbstractALMClient.this._socket.getInputStream(), buf);
       } catch (IOException e) {
         HiAbstractALMClient.this.closeConnection(HiAbstractALMClient.this._socket);
         throw new HiException("231205", "connector receive error", e);
       }
 
       if (rdlen == 0) {
         throw new IOException("peer socket close");
       }
 
       if (HiAbstractALMClient.this._checkConn.isCheckData(buf)) {
         String ip = HiAbstractALMClient.this._socket.getInetAddress().getHostAddress();
         int port = HiAbstractALMClient.this._socket.getPort();
         HiAbstractALMClient.this._log.info("[" + ip + "]:[" + port + "]; recv check data:[" + buf.length() + "]:[" + buf + "]");
 
         Thread.sleep(100L);
         return;
       }
       HiMessage msg = new HiMessage(HiAbstractALMClient.this._server.getName(), HiAbstractALMClient.this._msgType);
       msg.setHeadItem("ECT", "text/plain");
       msg.setHeadItem("SCH", "rp");
 
       msg.setHeadItem("STM", new Long(System.currentTimeMillis()));
 
       msg.setHeadItem("SIP", HiAbstractALMClient.this._socket.getInetAddress().getHostAddress());
 
       msg.setBody(buf);
       if (HiAbstractALMClient.this._log.isInfoEnabled()) {
         HiAbstractALMClient.this._log.info(HiAbstractALMClient._sm.getString("HiPoolTcpConnector.receive00", msg.getRequestId(), HiAbstractALMClient.this._host, String.valueOf(HiAbstractALMClient.this._port), String.valueOf(buf.length()), buf));
       }
 
       HiMessageContext ctx = new HiMessageContext();
       HiMessageContext.setCurrentMessageContext(ctx);
       ctx.setCurrentMsg(msg);
       HiWorker worker = new HiWorker(HiAbstractALMClient.this._writeLock);
       worker.setLogger(HiAbstractALMClient.this._log);
       worker.setTp(HiAbstractALMClient.this._tp);
       worker.setServer(HiAbstractALMClient.this._server);
       worker.setMessageContext(ctx);
       worker.execute();
     }
   }
 }