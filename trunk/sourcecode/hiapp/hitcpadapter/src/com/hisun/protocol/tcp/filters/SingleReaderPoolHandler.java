 package com.hisun.protocol.tcp.filters;
 
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.protocol.tcp.ISocketHandler;
 import java.io.IOException;
 import java.net.Socket;
 
 public final class SingleReaderPoolHandler
   implements ISocketHandler
 {
   private final Logger log;
   final ISocketHandler reader;
   Thread readerThread;
   Socket prevSocket;
 
   SingleReaderPoolHandler(Logger log, ISocketHandler handler)
   {
     this.log = log;
     this.reader = SocketHandlers.cycReader(log, handler);
   }
 
   public void process(Socket socket, HiMessageContext ctx) {
     stop();
     this.prevSocket = socket;
     this.readerThread = new Thread(SocketHandlers.handler2Runnable(this.log, this.reader, socket, ctx), "ReaderOn:" + socket);
     this.readerThread.start();
   }
 
   public void stop() {
     if (this.readerThread == null) return;
     try {
       this.prevSocket.close();
     } catch (IOException e1) {
     }
     this.readerThread.interrupt();
     try {
       this.readerThread.join();
     } catch (InterruptedException e) {
       this.log.error("can not shutdown reader thread on socket:" + this.prevSocket);
     }
     this.readerThread = null;
   }
 }