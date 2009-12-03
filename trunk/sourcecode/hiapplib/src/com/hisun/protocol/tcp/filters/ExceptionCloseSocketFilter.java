/*    */ package com.hisun.protocol.tcp.filters;
/*    */ 
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.protocol.tcp.ISocketHandler;
/*    */ import com.hisun.protocol.tcp.ISocketHandlerFilter;
/*    */ import com.hisun.util.HiStringManager;
/*    */ import java.io.IOException;
/*    */ import java.net.Socket;
/*    */ 
/*    */ public class ExceptionCloseSocketFilter
/*    */   implements ISocketHandlerFilter
/*    */ {
/* 27 */   static HiStringManager sm = HiStringManager.getManager();
/*    */   private Logger log;
/*    */ 
/*    */   public ExceptionCloseSocketFilter(Logger log)
/*    */   {
/* 33 */     this.log = log;
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
/*    */   public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler) {
/*    */     try {
/* 46 */       handler.process(socket, ctx);
/*    */     }
/*    */     catch (Throwable t) {
/* 49 */       closeSocketQuietly(socket);
/*    */     }
/*    */   }
/*    */ }