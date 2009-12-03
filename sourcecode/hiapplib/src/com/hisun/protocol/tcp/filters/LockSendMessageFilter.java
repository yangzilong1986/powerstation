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
/*    */ public class LockSendMessageFilter
/*    */   implements ISocketHandlerFilter
/*    */ {
/* 27 */   private static HiStringManager sm = HiStringManager.getManager();
/*    */   private final Logger log;
/*    */   private final HiMessageInOut msginout;
/*    */ 
/*    */   public LockSendMessageFilter(Logger log, HiMessageInOut msginout)
/*    */   {
/* 32 */     this.log = log;
/* 33 */     this.msginout = msginout;
/*    */   }
/*    */ 
/*    */   protected void closeSocketQuietly(Socket socket) {
/*    */     try {
/* 38 */       socket.close();
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/*    */     }
/*    */   }
/*    */ 
/*    */   public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler) throws HiException {
/* 46 */     handler.process(socket, ctx);
/*    */ 
/* 48 */     HiMessage msg = ctx.getCurrentMsg();
/* 49 */     if (msg.getBody() instanceof HiByteBuffer) {
/*    */       try
/*    */       {
/* 52 */         synchronized (socket) {
/* 53 */           this.msginout.write(socket.getOutputStream(), msg);
/*    */         }
/* 55 */         if (this.log.isInfoEnabled())
/*    */         {
/* 59 */           HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
/*    */ 
/* 61 */           String ip = socket.getInetAddress().getHostAddress();
/* 62 */           this.log.info(sm.getString("tcplistener.send", msg.getRequestId(), ip, String.valueOf(byteBuffer.length()), byteBuffer.toString()));
/*    */         }
/*    */ 
/*    */       }
/*    */       catch (IOException e)
/*    */       {
/* 68 */         this.log.error(sm.getString("tcplistener.err.write", socket.toString()));
/*    */ 
/* 73 */         throw new HiException("231201", e.toString(), e);
/*    */       }
/*    */     }
/* 76 */     this.log.error("msg body is not HiByteBuffer!");
/*    */   }
/*    */ }