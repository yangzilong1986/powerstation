 package com.hisun.protocol.tcp.filters;
 
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessageContext;
 import com.hisun.protocol.tcp.ISocketHandler;
 import com.hisun.protocol.tcp.ISocketHandlerFilter;
 import com.hisun.util.HiStringManager;
 import java.io.IOException;
 import java.net.Socket;
 
 public class ExceptionCloseSocketFilter
   implements ISocketHandlerFilter
 {
   static HiStringManager sm = HiStringManager.getManager();
   private Logger log;
 
   public ExceptionCloseSocketFilter(Logger log)
   {
     this.log = log;
   }
 
   protected void closeSocketQuietly(Socket socket) {
     try {
       socket.close();
     }
     catch (IOException e) {
     }
   }
 
   public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler) {
     try {
       handler.process(socket, ctx);
     }
     catch (Throwable t) {
       closeSocketQuietly(socket);
     }
   }
 }