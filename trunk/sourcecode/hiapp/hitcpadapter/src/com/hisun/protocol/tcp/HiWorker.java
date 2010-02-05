 package com.hisun.protocol.tcp;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.HiDefaultServer;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiStringManager;
 import com.hisun.util.HiThreadPool;
 import edu.emory.mathcs.backport.java.util.concurrent.RejectedExecutionException;
 import java.io.IOException;
 import java.net.InetAddress;
 import java.net.Socket;
 
 class HiWorker
   implements Runnable
 {
   protected static HiStringManager _sm = HiStringManager.getManager();
   private HiDefaultServer _server;
   private HiMessageInOut _msginout;
   private Logger _log;
   private HiMessageContext _ctx;
   private HiThreadPool _tp;
   private Socket _socket;
   private Object _writeLock;
 
   public HiWorker(Object writeLock)
   {
     this._writeLock = writeLock; }
 
   public void setSocket(Socket socket) {
     this._socket = socket; }
 
   public void setTp(HiThreadPool tp) {
     this._tp = tp;
   }
 
   public void setLogger(Logger log) {
     this._log = log;
   }
 
   public void setServer(HiDefaultServer server) {
     this._server = server;
   }
 
   public void setMessageContext(HiMessageContext ctx) {
     this._ctx = ctx;
   }
 
   public void execute()
   {
     try {
       if ((this._tp.isShutdown()) || (Thread.currentThread().isInterrupted())) {
         return;
       }
       this._tp.execute(this);
     } catch (RejectedExecutionException e) {
       while (true) {
         this._log.warn("Please increase maxThreads!");
         Thread.yield();
         try {
           Thread.sleep(1000L);
         } catch (InterruptedException e1) {
           return;
         }
       }
     }
   }
 
   public void run() {
     try {
       doRun();
     } catch (Throwable t) {
       this._log.error(t, t);
     }
   }
 
   protected void doRun() throws HiException, IOException {
     this._server.process(this._ctx);
     if (this._socket == null)
       return;
     HiMessage msg = this._ctx.getCurrentMsg();
     synchronized (this._writeLock) {
       this._msginout.write(this._socket.getOutputStream(), this._ctx.getCurrentMsg());
     }
     if (this._log.isInfoEnabled()) {
       HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
       int port = this._socket.getPort();
       String ip = this._socket.getInetAddress().getHostAddress();
       this._log.info(_sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), ip, String.valueOf(port), String.valueOf(byteBuffer.length()), byteBuffer));
     }
   }
 
   public HiMessageInOut getMsginout()
   {
     return this._msginout; }
 
   public void setMsginout(HiMessageInOut msginout) {
     this._msginout = msginout;
   }
 }