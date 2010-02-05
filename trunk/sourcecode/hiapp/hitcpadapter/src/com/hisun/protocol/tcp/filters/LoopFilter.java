 package com.hisun.protocol.tcp.filters;
 
 import com.hisun.framework.HiDefaultServer;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.protocol.tcp.ISocketHandler;
 import com.hisun.protocol.tcp.ISocketHandlerFilter;
 import java.io.IOException;
 import java.net.Socket;
 
 public class LoopFilter
   implements ISocketHandlerFilter
 {
   HiDefaultServer server;
   private Logger log;
 
   public LoopFilter(HiDefaultServer server)
   {
     this.server = server;
     this.log = server.getLog();
   }
 
   public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler)
   {
     while (true)
     {
       if ((!(this.server.isRunning())) || 
         (this.server.isPaused()));
       try
       {
         Thread.sleep(1000L);
       }
       catch (InterruptedException e)
       {
         do
         {
           if (Thread.interrupted())
             break;
           if (socket.isClosed())
             break;
           try {
             handler.process(socket, ctx);
           } catch (Throwable t) {
             if ((t instanceof IOException) || (t instanceof RuntimeException) || (t instanceof Error) || (t instanceof InterruptedException))
             {
               try
               {
                 socket.close();
               } catch (IOException e) {
               }
               this.log.error("loop err:", t);
               break label161:
             }
 
             this.log.error("ignore loop error:" + t.toString());
           }
         }
         while (!(socket.isClosed()));
 
         if (!(socket.isClosed()))
           try {
             label161: socket.close();
           }
           catch (IOException e) {
           }
         if (this.log.isInfoEnabled())
           this.log.info("exit read thread on socket:" + socket);
       }
     }
   }
 }