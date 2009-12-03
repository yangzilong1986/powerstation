/*    */ package com.hisun.protocol.tcp.filters;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.protocol.tcp.ISocketHandler;
/*    */ import com.hisun.protocol.tcp.ISocketHandlerFilter;
/*    */ import java.net.InetAddress;
/*    */ import java.net.Socket;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class IpCheckFilter
/*    */   implements ISocketHandlerFilter
/*    */ {
/*    */   private final Logger log;
/*    */   private final Set set;
/*    */ 
/*    */   public IpCheckFilter(Set set, Logger log)
/*    */   {
/* 21 */     this.set = set;
/* 22 */     this.log = log;
/*    */   }
/*    */ 
/*    */   boolean check(String clientip) {
/* 26 */     return this.set.contains(clientip);
/*    */   }
/*    */ 
/*    */   public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler) throws HiException
/*    */   {
/* 31 */     String clientip = socket.getInetAddress().getHostAddress();
/* 32 */     boolean canAccess = check(clientip);
/* 33 */     if (!(canAccess)) {
/* 34 */       this.log.error("illegal client connect:" + clientip);
/*    */ 
/* 36 */       throw new HiException("illegal ip:" + clientip);
/*    */     }
/*    */ 
/* 39 */     handler.process(socket, ctx);
/*    */   }
/*    */ }