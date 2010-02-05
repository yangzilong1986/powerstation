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
 
 public class ReceiveMessageFilter
   implements ISocketHandlerFilter
 {
   private static HiStringManager sm = HiStringManager.getManager();
   private final Logger log;
   private final HiMessageInOut msginout;
 
   public ReceiveMessageFilter(Logger log, HiMessageInOut msginout)
   {
     this.log = log;
     this.msginout = msginout;
   }
 
   protected void closeSocketQuietly(Socket socket) {
     try {
       socket.close();
     }
     catch (IOException e) {
     }
   }
 
   public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
 
     String ip = socket.getInetAddress().getHostAddress();
 
     msg.setHeadItem("SIP", ip);
     try
     {
       int len = this.msginout.read(socket.getInputStream(), msg);
       if (len == 0)
       {
         this.log.error("read to end! socket status may be CLOSE_WAIT,close socket!");
         socket.close();
         return;
       }
       if (this.log.isInfoEnabled())
       {
         HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
         this.log.info(sm.getString("tcplistener.receive", msg.getRequestId(), ip, String.valueOf(byteBuffer.length()), byteBuffer.toString()));
       }
 
     }
     catch (IOException e)
     {
       this.log.error(sm.getString("tcplistener.err.read", socket.toString(), e.toString()));
 
       throw new HiException("231201", e.toString(), e);
     }
 
     long curtime = System.currentTimeMillis();
     msg.setHeadItem("STM", new Long(curtime));
 
     handler.process(socket, ctx);
   }
 }