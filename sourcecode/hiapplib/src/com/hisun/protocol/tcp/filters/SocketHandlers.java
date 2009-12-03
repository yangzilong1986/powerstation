/*     */ package com.hisun.protocol.tcp.filters;
/*     */ 
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.protocol.tcp.HiMessageInOut;
/*     */ import com.hisun.protocol.tcp.ISocketHandler;
/*     */ import com.hisun.protocol.tcp.ISocketHandlerFilter;
/*     */ import com.hisun.util.HiThreadPool;
/*     */ import java.io.IOException;
/*     */ import java.net.Socket;
/*     */ 
/*     */ public class SocketHandlers
/*     */ {
/*     */   public static Runnable handler2Runnable(Logger log, ISocketHandler handler, Socket socket, HiMessageContext ctx)
/*     */   {
/*  17 */     return new Runnable(handler, socket, ctx, log) { private final ISocketHandler val$handler;
/*     */       private final Socket val$socket;
/*     */       private final HiMessageContext val$ctx;
/*     */       private final Logger val$log;
/*     */ 
/*     */       public void run() { try { this.val$handler.process(this.val$socket, this.val$ctx);
/*     */         } catch (Throwable t) {
/*  23 */           this.val$log.error("handle socket error:" + t, t);
/*     */         }
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static ISocketHandler cycReader(Logger log, ISocketHandler handler)
/*     */   {
/*  32 */     return new ISocketHandler(handler, log) { private final ISocketHandler val$handler;
/*     */       private final Logger val$log;
/*     */ 
/*     */       public void process(Socket socket, HiMessageContext ctx) { do { if (Thread.interrupted())
/*     */             break;
/*  37 */           if (socket.isClosed())
/*     */             break;
/*     */           try {
/*  40 */             this.val$handler.process(socket, ctx);
/*     */           } catch (Throwable t) {
/*  42 */             if ((t instanceof IOException) || (t instanceof RuntimeException) || (t instanceof Error) || (t instanceof InterruptedException))
/*     */             {
/*     */               try
/*     */               {
/*  49 */                 socket.close();
/*     */               } catch (IOException e) {
/*     */               }
/*  52 */               this.val$log.error("loop err:", t);
/*  53 */               break label123:
/*     */             }
/*     */ 
/*  56 */             this.val$log.error("ignore loop error:" + t.toString());
/*     */           }
/*     */         }
/*  59 */         while (!(socket.isClosed()));
/*     */ 
/*  63 */         if (!(socket.isClosed()))
/*     */           try {
/*  65 */             label123: socket.close();
/*     */           }
/*     */           catch (IOException e) {
/*     */           }
/*  69 */         if (this.val$log.isInfoEnabled())
/*  70 */           this.val$log.info("exit read thread on socket:" + socket);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static MultiReaderPoolHandler multiReaderPool(Logger log, ISocketHandler handler)
/*     */   {
/*  79 */     return new MultiReaderPoolHandler(log, handler);
/*     */   }
/*     */ 
/*     */   public static ISocketHandlerFilter createContextFilter(String name, String type)
/*     */   {
/*  84 */     return new CreateContextFilter(name, type);
/*     */   }
/*     */ 
/*     */   public static ISocketHandlerFilter receiveMessageFilter(Logger log, HiMessageInOut msginout)
/*     */   {
/*  89 */     return new ReceiveMessageFilter(log, msginout);
/*     */   }
/*     */ 
/*     */   public static LockSendMessageFilter lockSendMessageFilter(Logger log, HiMessageInOut msginout)
/*     */   {
/*  94 */     return new LockSendMessageFilter(log, msginout);
/*     */   }
/*     */ 
/*     */   public static ISocketHandlerFilter poolFilter(Logger log, HiThreadPool tp) {
/*  98 */     PoolFilter filter = new PoolFilter();
/*  99 */     filter.setLog(log);
/* 100 */     filter.setTp(tp);
/* 101 */     return filter;
/*     */   }
/*     */ }