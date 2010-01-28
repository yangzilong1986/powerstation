 package com.hisun.protocol.tcp.filters;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.protocol.tcp.ISocketHandler;
 import java.io.IOException;
 import java.net.InetAddress;
 import java.net.Socket;
 import java.util.Collections;
 import java.util.Iterator;
 import java.util.LinkedList;
 import java.util.List;
 
 public final class MultiReaderPoolHandler
   implements ISocketHandler
 {
   private final Logger log;
   final ISocketHandler reader;
   List readerThreads = Collections.synchronizedList(new LinkedList());
 
   MultiReaderPoolHandler(Logger log, ISocketHandler handler) {
     this.log = log;
     this.reader = SocketHandlers.cycReader(log, handler);
   }
 
   public void process(Socket socket, HiMessageContext ctx) {
     ThreadedHandler readerThread = new ThreadedHandler(socket, "ReaderOn:" + socket);
 
     this.readerThreads.add(readerThread);
     readerThread.start();
 
     if (this.log.isInfoEnabled()) {
       String aClientIp = socket.getInetAddress().getHostAddress();
       String s = "接收连接请求：IP=" + aClientIp + ",本地端口=" + socket.getLocalPort();
 
       this.log.info(s);
     }
   }
 
   public void stop()
   {
     ThreadedHandler[] copy = new ThreadedHandler[this.readerThreads.size()];
     Iterator it = this.readerThreads.iterator();
     int i = 0;
     while (it.hasNext()) {
       ThreadedHandler readerThread = (ThreadedHandler)it.next();
       copy[(i++)] = readerThread;
     }
     for (i = 0; i < copy.length; ++i) {
       copy[i].stopme();
     }
     this.readerThreads.clear();
   }
 
   class ThreadedHandler extends Thread {
     final Socket incoming;
 
     ThreadedHandler(Socket paramSocket, String paramString) {
       super(name);
       this.incoming = paramSocket;
     }
 
     public void run() {
       try {
         MultiReaderPoolHandler.this.reader.process(this.incoming, null);
       } catch (HiException e) {
         MultiReaderPoolHandler.this.log.error(e, e);
       }
       MultiReaderPoolHandler.this.readerThreads.remove(this);
       if (MultiReaderPoolHandler.this.log.isInfoEnabled())
         MultiReaderPoolHandler.this.log.info("读线程退出:" + this.incoming);
     }
 
     public void stopme()
     {
       if (this.incoming != null)
         try {
           this.incoming.close();
         }
         catch (IOException e) {
         }
       interrupt();
       try
       {
         join(1000L);
       }
       catch (InterruptedException e)
       {
       }
     }
   }
 }