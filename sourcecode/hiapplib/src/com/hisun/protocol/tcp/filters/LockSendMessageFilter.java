 package com.hisun.protocol.tcp.filters;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.protocol.tcp.HiMessageInOut;
 import com.hisun.protocol.tcp.ISocketHandler;
 import com.hisun.protocol.tcp.ISocketHandlerFilter;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiStringManager;
 import java.io.IOException;
 import java.net.InetAddress;
 import java.net.Socket;
 
 public class LockSendMessageFilter
   implements ISocketHandlerFilter
 {
   private static HiStringManager sm = HiStringManager.getManager();
   private final Logger log;
   private final HiMessageInOut msginout;
 
   public LockSendMessageFilter(Logger log, HiMessageInOut msginout)
   {
     this.log = log;
     this.msginout = msginout;
   }
 
   protected void closeSocketQuietly(Socket socket) {
     try {
       socket.close();
     }
     catch (IOException e)
     {
     }
   }
 
   public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler) throws HiException {
     handler.process(socket, ctx);
 
     HiMessage msg = ctx.getCurrentMsg();
     if (msg.getBody() instanceof HiByteBuffer) {
       try
       {
         synchronized (socket) {
           this.msginout.write(socket.getOutputStream(), msg);
         }
         if (this.log.isInfoEnabled())
         {
           HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
 
           String ip = socket.getInetAddress().getHostAddress();
           this.log.info(sm.getString("tcplistener.send", msg.getRequestId(), ip, String.valueOf(byteBuffer.length()), byteBuffer.toString()));
         }
 
       }
       catch (IOException e)
       {
         this.log.error(sm.getString("tcplistener.err.write", socket.toString()));
 
         throw new HiException("231201", e.toString(), e);
       }
     }
     this.log.error("msg body is not HiByteBuffer!");
   }
 }