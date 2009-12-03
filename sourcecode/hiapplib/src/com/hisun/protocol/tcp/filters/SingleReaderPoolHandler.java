/*    */ package com.hisun.protocol.tcp.filters;
/*    */ 
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.protocol.tcp.ISocketHandler;
/*    */ import java.io.IOException;
/*    */ import java.net.Socket;
/*    */ 
/*    */ public final class SingleReaderPoolHandler
/*    */   implements ISocketHandler
/*    */ {
/*    */   private final Logger log;
/*    */   final ISocketHandler reader;
/*    */   Thread readerThread;
/*    */   Socket prevSocket;
/*    */ 
/*    */   SingleReaderPoolHandler(Logger log, ISocketHandler handler)
/*    */   {
/* 21 */     this.log = log;
/* 22 */     this.reader = SocketHandlers.cycReader(log, handler);
/*    */   }
/*    */ 
/*    */   public void process(Socket socket, HiMessageContext ctx) {
/* 26 */     stop();
/* 27 */     this.prevSocket = socket;
/* 28 */     this.readerThread = new Thread(SocketHandlers.handler2Runnable(this.log, this.reader, socket, ctx), "ReaderOn:" + socket);
/* 29 */     this.readerThread.start();
/*    */   }
/*    */ 
/*    */   public void stop() {
/* 33 */     if (this.readerThread == null) return;
/*    */     try {
/* 35 */       this.prevSocket.close();
/*    */     } catch (IOException e1) {
/*    */     }
/* 38 */     this.readerThread.interrupt();
/*    */     try {
/* 40 */       this.readerThread.join();
/*    */     } catch (InterruptedException e) {
/* 42 */       this.log.error("can not shutdown reader thread on socket:" + this.prevSocket);
/*    */     }
/* 44 */     this.readerThread = null;
/*    */   }
/*    */ }