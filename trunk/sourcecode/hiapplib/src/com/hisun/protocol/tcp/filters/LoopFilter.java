/*    */ package com.hisun.protocol.tcp.filters;
/*    */ 
/*    */ import com.hisun.framework.HiDefaultServer;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.protocol.tcp.ISocketHandler;
/*    */ import com.hisun.protocol.tcp.ISocketHandlerFilter;
/*    */ import java.io.IOException;
/*    */ import java.net.Socket;
/*    */ 
/*    */ public class LoopFilter
/*    */   implements ISocketHandlerFilter
/*    */ {
/*    */   HiDefaultServer server;
/*    */   private Logger log;
/*    */ 
/*    */   public LoopFilter(HiDefaultServer server)
/*    */   {
/* 28 */     this.server = server;
/* 29 */     this.log = server.getLog();
/*    */   }
/*    */ 
/*    */   public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler)
/*    */   {
/*    */     while (true)
/*    */     {
/* 39 */       if ((!(this.server.isRunning())) || 
/* 41 */         (this.server.isPaused()));
/*    */       try
/*    */       {
/* 43 */         Thread.sleep(1000L);
/*    */       }
/*    */       catch (InterruptedException e)
/*    */       {
/*    */         do
/*    */         {
/* 49 */           if (Thread.interrupted())
/*    */             break;
/* 51 */           if (socket.isClosed())
/*    */             break;
/*    */           try {
/* 54 */             handler.process(socket, ctx);
/*    */           } catch (Throwable t) {
/* 56 */             if ((t instanceof IOException) || (t instanceof RuntimeException) || (t instanceof Error) || (t instanceof InterruptedException))
/*    */             {
/*    */               try
/*    */               {
/* 63 */                 socket.close();
/*    */               } catch (IOException e) {
/*    */               }
/* 66 */               this.log.error("loop err:", t);
/* 67 */               break label161:
/*    */             }
/*    */ 
/* 70 */             this.log.error("ignore loop error:" + t.toString());
/*    */           }
/*    */         }
/* 73 */         while (!(socket.isClosed()));
/*    */ 
/* 78 */         if (!(socket.isClosed()))
/*    */           try {
/* 80 */             label161: socket.close();
/*    */           }
/*    */           catch (IOException e) {
/*    */           }
/* 84 */         if (this.log.isInfoEnabled())
/* 85 */           this.log.info("exit read thread on socket:" + socket);
/*    */       }
/*    */     }
/*    */   }
/*    */ }