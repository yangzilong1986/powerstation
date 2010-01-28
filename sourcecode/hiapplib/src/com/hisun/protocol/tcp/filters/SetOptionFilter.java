 package com.hisun.protocol.tcp.filters;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.protocol.tcp.ISocketHandler;
 import com.hisun.protocol.tcp.ISocketHandlerFilter;
 import java.net.Socket;
 import java.net.SocketException;
 
 public class SetOptionFilter
   implements ISocketHandlerFilter
 {
   protected int linger;
   private Logger log;
   protected int socketTimeout;
   protected boolean tcpNoDelay;
 
   public SetOptionFilter()
   {
     this.linger = 0;
 
     this.socketTimeout = 30000;
     this.tcpNoDelay = true;
   }
 
   public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler) throws HiException {
     try {
       setSocketOptions(socket);
     } catch (SocketException e) {
       this.log.error("set socket option failed!" + e);
 
       throw new HiException("231201", e.toString(), e);
     }
 
     handler.process(socket, ctx);
   }
 
   public void setLog(Logger log) {
     this.log = log;
   }
 
   void setSocketOptions(Socket socket) throws SocketException {
     if (this.linger >= 0)
       socket.setSoLinger(true, this.linger);
     if (this.tcpNoDelay)
       socket.setTcpNoDelay(this.tcpNoDelay);
     if (this.socketTimeout > 0) {
       socket.setSoTimeout(this.socketTimeout);
     }
     if (this.log.isDebugEnabled())
       this.log.debug("socket timeout:" + socket.getSoTimeout());
   }
 
   public void setSoLinger(int i)
   {
     this.linger = i;
   }
 
   public void setSoTimeout(int i) {
     this.socketTimeout = i;
   }
 
   public void setTcpNoDelay(boolean b) {
     this.tcpNoDelay = b;
   }
 }