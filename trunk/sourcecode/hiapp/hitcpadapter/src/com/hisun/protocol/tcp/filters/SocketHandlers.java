 package com.hisun.protocol.tcp.filters;
 
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.protocol.tcp.HiMessageInOut;
 import com.hisun.protocol.tcp.ISocketHandler;
 import com.hisun.protocol.tcp.ISocketHandlerFilter;
 import com.hisun.util.HiThreadPool;
 import java.io.IOException;
 import java.net.Socket;
 
 public class SocketHandlers
 {
   public static Runnable handler2Runnable(Logger log, ISocketHandler handler, Socket socket, HiMessageContext ctx)
   {
     return new Runnable(handler, socket, ctx, log) { private final ISocketHandler val$handler;
       private final Socket val$socket;
       private final HiMessageContext val$ctx;
       private final Logger val$log;
 
       public void run() { try { this.val$handler.process(this.val$socket, this.val$ctx);
         } catch (Throwable t) {
           this.val$log.error("handle socket error:" + t, t);
         }
       }
     };
   }
 
   public static ISocketHandler cycReader(Logger log, ISocketHandler handler)
   {
     return new ISocketHandler(handler, log) { private final ISocketHandler val$handler;
       private final Logger val$log;
 
       public void process(Socket socket, HiMessageContext ctx) { do { if (Thread.interrupted())
             break;
           if (socket.isClosed())
             break;
           try {
             this.val$handler.process(socket, ctx);
           } catch (Throwable t) {
             if ((t instanceof IOException) || (t instanceof RuntimeException) || (t instanceof Error) || (t instanceof InterruptedException))
             {
               try
               {
                 socket.close();
               } catch (IOException e) {
               }
               this.val$log.error("loop err:", t);
               break label123:
             }
 
             this.val$log.error("ignore loop error:" + t.toString());
           }
         }
         while (!(socket.isClosed()));
 
         if (!(socket.isClosed()))
           try {
             label123: socket.close();
           }
           catch (IOException e) {
           }
         if (this.val$log.isInfoEnabled())
           this.val$log.info("exit read thread on socket:" + socket);
       }
     };
   }
 
   public static MultiReaderPoolHandler multiReaderPool(Logger log, ISocketHandler handler)
   {
     return new MultiReaderPoolHandler(log, handler);
   }
 
   public static ISocketHandlerFilter createContextFilter(String name, String type)
   {
     return new CreateContextFilter(name, type);
   }
 
   public static ISocketHandlerFilter receiveMessageFilter(Logger log, HiMessageInOut msginout)
   {
     return new ReceiveMessageFilter(log, msginout);
   }
 
   public static LockSendMessageFilter lockSendMessageFilter(Logger log, HiMessageInOut msginout)
   {
     return new LockSendMessageFilter(log, msginout);
   }
 
   public static ISocketHandlerFilter poolFilter(Logger log, HiThreadPool tp) {
     PoolFilter filter = new PoolFilter();
     filter.setLog(log);
     filter.setTp(tp);
     return filter;
   }
 }