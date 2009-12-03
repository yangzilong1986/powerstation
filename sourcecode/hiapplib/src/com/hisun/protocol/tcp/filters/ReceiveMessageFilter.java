/*    */ package com.hisun.protocol.tcp.filters;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.protocol.tcp.HiMessageInOut;
/*    */ import com.hisun.protocol.tcp.ISocketHandler;
/*    */ import com.hisun.protocol.tcp.ISocketHandlerFilter;
/*    */ import com.hisun.util.HiByteBuffer;
/*    */ import com.hisun.util.HiStringManager;
/*    */ import java.io.IOException;
/*    */ import java.net.InetAddress;
/*    */ import java.net.Socket;
/*    */ 
/*    */ public class ReceiveMessageFilter
/*    */   implements ISocketHandlerFilter
/*    */ {
/* 27 */   private static HiStringManager sm = HiStringManager.getManager();
/*    */   private final Logger log;
/*    */   private final HiMessageInOut msginout;
/*    */ 
/*    */   public ReceiveMessageFilter(Logger log, HiMessageInOut msginout)
/*    */   {
/* 32 */     this.log = log;
/* 33 */     this.msginout = msginout;
/*    */   }
/*    */ 
/*    */   protected void closeSocketQuietly(Socket socket) {
/*    */     try {
/* 38 */       socket.close();
/*    */     }
/*    */     catch (IOException e) {
/*    */     }
/*    */   }
/*    */ 
/*    */   public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler) throws HiException {
/* 45 */     HiMessage msg = ctx.getCurrentMsg();
/*    */ 
/* 48 */     String ip = socket.getInetAddress().getHostAddress();
/*    */ 
/* 50 */     msg.setHeadItem("SIP", ip);
/*    */     try
/*    */     {
/* 54 */       int len = this.msginout.read(socket.getInputStream(), msg);
/* 55 */       if (len == 0)
/*    */       {
/* 58 */         this.log.error("read to end! socket status may be CLOSE_WAIT,close socket!");
/* 59 */         socket.close();
/* 60 */         return;
/*    */       }
/* 62 */       if (this.log.isInfoEnabled())
/*    */       {
/* 66 */         HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
/* 67 */         this.log.info(sm.getString("tcplistener.receive", msg.getRequestId(), ip, String.valueOf(byteBuffer.length()), byteBuffer.toString()));
/*    */       }
/*    */ 
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 76 */       this.log.error(sm.getString("tcplistener.err.read", socket.toString(), e.toString()));
/*    */ 
/* 81 */       throw new HiException("231201", e.toString(), e);
/*    */     }
/*    */ 
/* 85 */     long curtime = System.currentTimeMillis();
/* 86 */     msg.setHeadItem("STM", new Long(curtime));
/*    */ 
/* 88 */     handler.process(socket, ctx);
/*    */   }
/*    */ }