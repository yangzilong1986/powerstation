 package com.hisun.protocol.tcp.filters;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.protocol.tcp.ISocketHandler;
 import com.hisun.protocol.tcp.ISocketHandlerFilter;
 import com.hisun.util.HiICSProperty;
 import com.hisun.util.HiStringManager;
 import java.io.File;
 import java.io.IOException;
 import java.net.Socket;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class CloseSocketFilter
   implements ISocketHandlerFilter
 {
   static HiStringManager sm = HiStringManager.getManager();
   private Logger log;
 
   public CloseSocketFilter(Logger log)
   {
     this.log = log;
   }
 
   protected void closeSocketQuietly(Socket socket) {
     try {
       socket.close();
     } catch (IOException e) {
     }
   }
 
   private void debugInfo(HiMessageContext ctx) {
     if (StringUtils.equalsIgnoreCase(System.getProperty("ics.env"), "test")) {
       Long tm = (Long)ctx.getCurrentMsg().getObjectHeadItem("STM");
 
       int tmOut = NumberUtils.toInt(System.getProperty("TMOUT"));
       if (tmOut <= 0) {
         tmOut = 5000;
       }
       if (System.currentTimeMillis() - tm.longValue() > tmOut) {
         if (this.log.isInfoEnabled())
           this.log.info(ctx.getCurrentMsg().getRequestId() + "交易时间:[" + (System.currentTimeMillis() - tm.longValue()) + "]");
       }
       else
       {
         File f = new File(HiICSProperty.getTrcDir() + ctx.getCurrentMsg().getRequestId() + ".trc");
 
         f.delete();
       }
     }
   }
 
   public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler) throws HiException
   {
     try {
       handler.process(socket, ctx);
     } finally {
       closeSocketQuietly(socket);
       if (this.log.isInfoEnabled())
         this.log.info(sm.getString("tcplistener.info.clientclose", socket.toString()));
     }
   }
 }