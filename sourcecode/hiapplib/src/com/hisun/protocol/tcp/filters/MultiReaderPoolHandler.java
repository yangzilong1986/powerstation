/*    */ package com.hisun.protocol.tcp.filters;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.protocol.tcp.ISocketHandler;
/*    */ import java.io.IOException;
/*    */ import java.net.InetAddress;
/*    */ import java.net.Socket;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ public final class MultiReaderPoolHandler
/*    */   implements ISocketHandler
/*    */ {
/*    */   private final Logger log;
/*    */   final ISocketHandler reader;
/* 22 */   List readerThreads = Collections.synchronizedList(new LinkedList());
/*    */ 
/*    */   MultiReaderPoolHandler(Logger log, ISocketHandler handler) {
/* 25 */     this.log = log;
/* 26 */     this.reader = SocketHandlers.cycReader(log, handler);
/*    */   }
/*    */ 
/*    */   public void process(Socket socket, HiMessageContext ctx) {
/* 30 */     ThreadedHandler readerThread = new ThreadedHandler(socket, "ReaderOn:" + socket);
/*    */ 
/* 32 */     this.readerThreads.add(readerThread);
/* 33 */     readerThread.start();
/*    */ 
/* 35 */     if (this.log.isInfoEnabled()) {
/* 36 */       String aClientIp = socket.getInetAddress().getHostAddress();
/* 37 */       String s = "接收连接请求：IP=" + aClientIp + ",本地端口=" + socket.getLocalPort();
/*    */ 
/* 39 */       this.log.info(s);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void stop()
/*    */   {
/* 45 */     ThreadedHandler[] copy = new ThreadedHandler[this.readerThreads.size()];
/* 46 */     Iterator it = this.readerThreads.iterator();
/* 47 */     int i = 0;
/* 48 */     while (it.hasNext()) {
/* 49 */       ThreadedHandler readerThread = (ThreadedHandler)it.next();
/* 50 */       copy[(i++)] = readerThread;
/*    */     }
/* 52 */     for (i = 0; i < copy.length; ++i) {
/* 53 */       copy[i].stopme();
/*    */     }
/* 55 */     this.readerThreads.clear();
/*    */   }
/*    */ 
/*    */   class ThreadedHandler extends Thread {
/*    */     final Socket incoming;
/*    */ 
/*    */     ThreadedHandler(Socket paramSocket, String paramString) {
/* 62 */       super(name);
/* 63 */       this.incoming = paramSocket;
/*    */     }
/*    */ 
/*    */     public void run() {
/*    */       try {
/* 68 */         MultiReaderPoolHandler.this.reader.process(this.incoming, null);
/*    */       } catch (HiException e) {
/* 70 */         MultiReaderPoolHandler.this.log.error(e, e);
/*    */       }
/* 72 */       MultiReaderPoolHandler.this.readerThreads.remove(this);
/* 73 */       if (MultiReaderPoolHandler.this.log.isInfoEnabled())
/* 74 */         MultiReaderPoolHandler.this.log.info("读线程退出:" + this.incoming);
/*    */     }
/*    */ 
/*    */     public void stopme()
/*    */     {
/* 79 */       if (this.incoming != null)
/*    */         try {
/* 81 */           this.incoming.close();
/*    */         }
/*    */         catch (IOException e) {
/*    */         }
/* 85 */       interrupt();
/*    */       try
/*    */       {
/* 88 */         join(1000L);
/*    */       }
/*    */       catch (InterruptedException e)
/*    */       {
/*    */       }
/*    */     }
/*    */   }
/*    */ }