 package com.hisun.protocol.tcp;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.util.HiStringManager;
 import java.io.IOException;
 import java.io.InterruptedIOException;
 import java.net.BindException;
 import java.net.InetAddress;
 import java.net.ServerSocket;
 import java.net.Socket;
 import java.security.AccessControlException;
 import javax.net.ServerSocketFactory;
 
 public class HiTcpEndpoint
   implements Runnable
 {
   Logger log;
   HiStringManager sm = HiStringManager.getManager();
   private static final int BACKLOG = 100;
   private static final int TIMEOUT = 1000;
   private final Object threadSync = new Object();
 
   private int backlog = 100;
 
   private int serverTimeout = 0;
   private InetAddress inet;
   private int port;
   private ServerSocketFactory factory;
   private ServerSocket serverSocket;
   private volatile boolean running = false;
 
   private volatile boolean paused = false;
 
   private boolean initialized = false;
 
   private boolean reinitializing = false;
 
   private HiSSLHandler sslHandler = null;
 
   private Thread thread = null;
   ISocketHandler handler;
 
   public void setLog(Logger log)
   {
     this.log = log;
   }
 
   public int getLocalPort() {
     return this.port;
   }
 
   public void setLocalPort(int port) {
     this.port = port;
   }
 
   public boolean isRunning()
   {
     return this.running;
   }
 
   public boolean isPaused() {
     return this.paused;
   }
 
   public void setBacklog(int backlog)
   {
     if (backlog > 0)
       this.backlog = backlog;
   }
 
   public int getBacklog() {
     return this.backlog;
   }
 
   public int getServerSoTimeout() {
     return this.serverTimeout;
   }
 
   public void setServerSoTimeout(int i) {
     this.serverTimeout = i;
   }
 
   public void initEndpoint()
     throws HiException
   {
     try
     {
       if ((this.sslHandler != null) && (this.sslHandler.isSSLMode()))
       {
         this.sslHandler.init();
         this.factory = this.sslHandler.getServerSocketFactory();
       }
       if (this.factory == null)
         this.factory = ServerSocketFactory.getDefault();
       if (this.serverSocket == null) {
         try {
           if (this.inet == null) {
             this.serverSocket = this.factory.createServerSocket(this.port, this.backlog);
           }
           else {
             this.serverSocket = this.factory.createServerSocket(this.port, this.backlog, this.inet);
           }
         }
         catch (BindException be)
         {
           throw new HiException("231208", String.valueOf(this.port), be.getMessage());
         }
       }
 
       if (this.serverTimeout >= 0)
         this.serverSocket.setSoTimeout(this.serverTimeout);
       if ((this.sslHandler != null) && (this.sslHandler.isSSLMode()))
       {
         this.sslHandler.initServerSocket(this.serverSocket);
       }
     }
     catch (IOException ex)
     {
       throw new HiException("231208", String.valueOf(this.port), ex.getMessage());
     }
 
     this.initialized = true;
   }
 
   public void startEndpoint() throws HiException
   {
     if (!(this.initialized)) {
       initEndpoint();
     }
 
     this.running = true;
     this.paused = false;
 
     threadStart();
   }
 
   public void pauseEndpoint() {
     if ((this.running) && (!(this.paused))) {
       this.paused = true;
       unlockAccept();
     }
   }
 
   public void resumeEndpoint() {
     if (this.running)
       this.paused = false;
   }
 
   public void stopEndpoint()
   {
     if (this.running)
     {
       this.running = false;
       if (this.serverSocket != null) {
         closeServerSocket();
       }
 
       threadStop();
       this.initialized = false;
     }
     else if (this.serverSocket != null) {
       closeServerSocket();
     }
   }
 
   protected void closeServerSocket()
   {
     if (!(this.paused))
       unlockAccept();
     try {
       if (this.serverSocket != null)
         this.serverSocket.close();
     } catch (Exception e) {
       this.log.error(this.sm.getString("endpoint.err.close"), e);
     }
     this.serverSocket = null;
   }
 
   protected void unlockAccept() {
     Socket s = null;
     try
     {
       if (this.inet == null)
         s = new Socket("127.0.0.1", this.port);
       else {
         s = new Socket(this.inet, this.port);
       }
 
       s.setSoLinger(true, 0);
     } catch (Exception e) {
       if (this.log.isDebugEnabled())
         this.log.debug(this.sm.getString("endpoint.debug.unlock", "" + this.port), e);
     }
     finally {
       if (s != null)
         try {
           s.close();
         }
         catch (Exception e)
         {
         }
     }
   }
 
   Socket acceptSocket()
   {
     String msg;
     if ((!(this.running)) || (this.serverSocket == null)) {
       return null;
     }
     Socket accepted = null;
     try
     {
       accepted = this.serverSocket.accept();
 
       if (null == accepted) {
         this.log.warn(this.sm.getString("endpoint.warn.nullSocket"));
       }
       else if (!(this.running)) {
         accepted.close();
         accepted = null;
 
         return accepted;
       }
 
     }
     catch (InterruptedIOException iioe)
     {
     }
     catch (AccessControlException ace)
     {
       msg = this.sm.getString("endpoint.warn.security", this.serverSocket, ace);
 
       this.log.warn(msg);
     }
     catch (IOException e) {
       msg = null;
 
       if (this.running) {
         msg = this.sm.getString("endpoint.err.nonfatal", this.serverSocket, e);
         this.log.error(msg, e);
       }
 
       if (accepted != null) {
         try {
           accepted.close();
         } catch (Throwable ex) {
           msg = this.sm.getString("endpoint.err.nonfatal", accepted, ex);
           this.log.warn(msg, ex);
         }
         accepted = null;
       }
 
       if (!(this.running))
         return null;
       restartendpoint();
     }
 
     return accepted;
   }
 
   private void restartendpoint()
     throws ThreadDeath
   {
     this.reinitializing = true;
 
     synchronized (this.threadSync) {
       if (this.reinitializing)
       {
         String msg;
         this.reinitializing = false;
 
         closeServerSocket();
         this.initialized = false;
         try
         {
           msg = this.sm.getString("endpoint.warn.reinit");
           this.log.warn(msg);
           initEndpoint();
         } catch (Throwable t) {
           msg = this.sm.getString("endpoint.err.nonfatal", this.serverSocket, t);
 
           this.log.error(msg, t);
         }
 
         if (!(this.initialized)) {
           msg = this.sm.getString("endpoint.warn.restart");
           this.log.warn(msg);
           try {
             stopEndpoint();
             initEndpoint();
             startEndpoint();
           } catch (Throwable t) {
             msg = this.sm.getString("endpoint.err.fatal", this.serverSocket, t);
 
             this.log.error(msg, t);
           }
 
           throw new ThreadDeath();
         }
       }
     }
   }
 
   public void run()
   {
     if (this.log.isInfoEnabled()) {
       this.log.info(this.sm.getString("tcplistener.info.startthread", Integer.toString(this.port)));
     }
 
     while (this.running)
     {
       while (this.paused) {
         try {
           Thread.sleep(1000L);
         }
         catch (InterruptedException e)
         {
         }
       }
 
       Socket socket = acceptSocket();
 
       if (socket == null)
       {
         continue;
       }
 
       if ((((this.paused) || (!(this.running)))) && 
         (socket.getInetAddress().getHostAddress().equals("127.0.0.1"))) {
         try
         {
           socket.close();
         }
         catch (IOException e)
         {
         }
       }
 
       try
       {
         this.handler.process(socket, null);
       } catch (Throwable t) {
         if (this.log.isInfoEnabled()) {
           this.log.info("handler process fail:" + t);
         }
       }
 
     }
 
     if (this.log.isInfoEnabled())
       this.log.info(this.sm.getString("tcplistener.info.exit", Integer.toString(this.port)));
   }
 
   private void threadStart()
   {
     this.thread = new Thread(this, "PortListener:" + this.port);
 
     this.thread.start();
   }
 
   private void threadStop()
   {
     try
     {
       this.thread.join();
     } catch (InterruptedException e) {
     }
     this.thread = null;
   }
 
   public void setHandler(ISocketHandler processor)
   {
     this.handler = processor;
   }
 
   public void setSslHandler(HiSSLHandler processor) {
     this.sslHandler = processor;
   }
 }