 package com.hisun.protocol.tcp.filters;
 
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.protocol.tcp.ISocketHandler;
 import com.hisun.protocol.tcp.ISocketHandlerFilter;
 import com.hisun.util.HiStringManager;
 import com.hisun.util.HiThreadPool;
 import java.net.Socket;
 import java.util.concurrent.BlockingQueue;
 import java.util.concurrent.RejectedExecutionException;
 import java.util.concurrent.TimeUnit;
 
 public class PoolFilter
   implements ISocketHandlerFilter
 {
   private static HiStringManager sm = HiStringManager.getManager();
   Logger log;
   HiThreadPool tp;
 
   public HiThreadPool getTp()
   {
     return this.tp;
   }
 
   public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler)
   {
     Runnable worker = handler2Runnable(socket, ctx, handler);
     try
     {
       StringBuffer msg;
       if (this.log.isDebugEnabled()) {
         msg = new StringBuffer();
         debuginfo(msg.append("before submit: "));
         this.log.debug(msg.toString());
       }
       while (true) {
         try {
           this.tp.execute(worker);
           if (this.log.isDebugEnabled()) {
             msg = new StringBuffer();
             debuginfo(msg.append("after submit: "));
             this.log.debug(msg.toString());
           }
         }
         catch (RejectedExecutionException e) {
           this.log.warn("Please increase maxThreads!");
           Thread.currentThread(); Thread.yield();
           try {
             Thread.currentThread(); Thread.sleep(1000L);
           } catch (InterruptedException e1) {
             break label166:
           }
           if (!(this.tp.isShutdown())) if (!(Thread.currentThread().isInterrupted())) break label163; 
         }
         label163: label166: break;
       }
     }
     catch (RejectedExecutionException e)
     {
       this.log.error(sm.getString("endpoint.noProcessor", socket.toString()));
 
       throw e;
     }
   }
 
   private Runnable handler2Runnable(Socket socket, HiMessageContext ctx, ISocketHandler handler)
   {
     Runnable worker = new Runnable(handler, socket, ctx) { private final ISocketHandler val$handler;
       private final Socket val$socket;
       private final HiMessageContext val$ctx;
 
       public void run() { try { this.val$handler.process(this.val$socket, this.val$ctx);
         } catch (Throwable t) {
           PoolFilter.this.log.error("handle socket error:" + t, t);
         }
       }
     };
     return worker;
   }
 
   private void debuginfo(StringBuffer msg) {
     msg.append(this.tp.toString()).append("[Active=").append(this.tp.getActiveCount()).append(",Current=").append(this.tp.getPoolSize()).append(",Largest=").append(this.tp.getLargestPoolSize()).append(",Core=").append(this.tp.getCorePoolSize()).append(",Max=").append(this.tp.getMaximumPoolSize()).append(",TaskCount=").append(this.tp.getTaskCount()).append(",QueueSize=").append(this.tp.getQueue().size()).append("]");
   }
 
   public void setLog(Logger log)
   {
     this.log = log;
   }
 
   public void setTp(HiThreadPool tp) {
     this.tp = tp;
   }
 
   public void shutdown() {
     this.tp.shutdown();
     try
     {
       this.tp.awaitTermination(5L, TimeUnit.SECONDS);
     }
     catch (InterruptedException e)
     {
       this.log.error("shut down thread pool err!", e);
     }
   }
 }