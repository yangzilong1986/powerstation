 package com.hisun.mon.tcp;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.HiDefaultServer;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.protocol.tcp.ISocketHandler;
 import com.hisun.protocol.tcp.filters.CloseSocketFilter;
 import com.hisun.protocol.tcp.filters.ExceptionCloseSocketFilter;
 import com.hisun.protocol.tcp.filters.IpCheckFilter;
 import com.hisun.protocol.tcp.filters.SocketHandlers;
 import com.hisun.protocol.tcp.imp.HiTcpListener;
 import java.net.Socket;
 import java.util.ArrayList;
 import java.util.List;
 
 public class HiTcpAsynMonListener extends HiTcpListener
 {
   public void serverInit(ServerEvent arg0)
     throws HiException
   {
     super.serverInit(arg0);
   }
 
   public ISocketHandler buildSocketHandler()
   {
     List filters = new ArrayList();
 
     filters.add(new ExceptionCloseSocketFilter(this.log));
 
     if (getIpset() != null) {
       setIpcheck(new IpCheckFilter(getIpset(), this.log));
       filters.add(getIpcheck());
     }
 
     filters.add(getOpthandler());
 
     filters.add(getPoolhandler());
 
     filters.add(new CloseSocketFilter(this.log));
     filters.add(SocketHandlers.createContextFilter(getServer().getName(), getMsgType()));
     filters.add(SocketHandlers.receiveMessageFilter(this.log, this.msginout));
 
     ISocketHandler handler = new ISocketHandler() {
       public void process(Socket socket, HiMessageContext ctx) {
         try {
           HiTcpAsynMonListener.this.getServer().process(ctx);
         } catch (Throwable e) {
           HiTcpAsynMonListener.this.log.error("process error:" + ctx.getCurrentMsg().getRequestId(), e);
           if (e instanceof RuntimeException) {
             throw ((RuntimeException)e);
           }
           throw new RuntimeException(e);
         } finally {
           HiLog.close(ctx.getCurrentMsg());
         }
       }
     };
     return buildSocketHandler(filters, handler);
   }
 }