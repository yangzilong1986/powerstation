 package com.hisun.protocol.tcp.imp;
 
 import com.hisun.exception.HiException;
 import com.hisun.framework.HiDefaultServer;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.protocol.tcp.ISocketHandler;
 import com.hisun.protocol.tcp.filters.ExceptionCloseSocketFilter;
 import com.hisun.protocol.tcp.filters.IpCheckFilter;
 import com.hisun.protocol.tcp.filters.LoopFilter;
 import com.hisun.protocol.tcp.filters.PoolFilter;
 import com.hisun.protocol.tcp.filters.SocketHandlers;
 import com.hisun.util.HiThreadPool;
 import java.net.Socket;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.concurrent.TimeUnit;
 
 public class HiALSListener extends HiTcpListener
 {
   private HiThreadPool readerPool;
 
   public void serverInit(ServerEvent arg0)
     throws HiException
   {
     super.serverInit(arg0);
   }
 
   public void serverStop(ServerEvent arg0) throws HiException {
     super.serverStop(arg0);
 
     shutdownReaderThread(this.readerPool);
   }
 
   private void shutdownReaderThread(HiThreadPool pool)
   {
     pool.shutdownNow();
     try
     {
       pool.awaitTermination(10L, TimeUnit.SECONDS);
     }
     catch (InterruptedException e)
     {
       this.log.error("shut down thread pool err!", e);
     }
   }
 
   public ISocketHandler buildSocketHandler()
     throws HiException
   {
     List filters = new ArrayList();
 
     filters.add(new ExceptionCloseSocketFilter(this.log));
 
     if (this.ipset != null) {
       this.ipcheck = new IpCheckFilter(this.ipset, this.log);
       filters.add(this.ipcheck);
     }
     filters.add(this.opthandler);
     addListenerPoolFilter(filters);
     filters.add(new LoopFilter(getServer()));
 
     filters.add(SocketHandlers.createContextFilter(getServer().getName(), getMsgType()));
 
     filters.add(SocketHandlers.receiveMessageFilter(this.log, this.msginout));
 
     filters.add(this.poolhandler);
 
     ISocketHandler handler = new ISocketHandler() {
       public void process(Socket socket, HiMessageContext ctx) {
         try {
           HiALSListener.this.getServer().process(ctx);
         } catch (Throwable e) {
         }
         HiLog.close(ctx.getCurrentMsg());
       }
     };
     return buildSocketHandler(filters, handler);
   }
 
   private void addListenerPoolFilter(List filters) {
     this.readerPool = HiThreadPool.createThreadPool();
     this.readerPool.setCorePoolSize(5);
     this.readerPool.setMaximumPoolSize(5);
     PoolFilter listenerPoolFilter = new PoolFilter();
     listenerPoolFilter.setLog(this.log);
     listenerPoolFilter.setTp(this.readerPool);
     filters.add(listenerPoolFilter);
   }
 }