/*    */ package com.hisun.protocol.tcp.filters;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.protocol.tcp.ISocketHandler;
/*    */ import com.hisun.protocol.tcp.ISocketHandlerFilter;
/*    */ import java.net.Socket;
/*    */ import java.net.SocketException;
/*    */ 
/*    */ public class SetOptionFilter
/*    */   implements ISocketHandlerFilter
/*    */ {
/*    */   protected int linger;
/*    */   private Logger log;
/*    */   protected int socketTimeout;
/*    */   protected boolean tcpNoDelay;
/*    */ 
/*    */   public SetOptionFilter()
/*    */   {
/* 18 */     this.linger = 0;
/*    */ 
/* 24 */     this.socketTimeout = 30000;
/* 25 */     this.tcpNoDelay = true;
/*    */   }
/*    */ 
/*    */   public void process(Socket socket, HiMessageContext ctx, ISocketHandler handler) throws HiException {
/*    */     try {
/* 30 */       setSocketOptions(socket);
/*    */     } catch (SocketException e) {
/* 32 */       this.log.error("set socket option failed!" + e);
/*    */ 
/* 34 */       throw new HiException("231201", e.toString(), e);
/*    */     }
/*    */ 
/* 37 */     handler.process(socket, ctx);
/*    */   }
/*    */ 
/*    */   public void setLog(Logger log) {
/* 41 */     this.log = log;
/*    */   }
/*    */ 
/*    */   void setSocketOptions(Socket socket) throws SocketException {
/* 45 */     if (this.linger >= 0)
/* 46 */       socket.setSoLinger(true, this.linger);
/* 47 */     if (this.tcpNoDelay)
/* 48 */       socket.setTcpNoDelay(this.tcpNoDelay);
/* 49 */     if (this.socketTimeout > 0) {
/* 50 */       socket.setSoTimeout(this.socketTimeout);
/*    */     }
/* 52 */     if (this.log.isDebugEnabled())
/* 53 */       this.log.debug("socket timeout:" + socket.getSoTimeout());
/*    */   }
/*    */ 
/*    */   public void setSoLinger(int i)
/*    */   {
/* 58 */     this.linger = i;
/*    */   }
/*    */ 
/*    */   public void setSoTimeout(int i) {
/* 62 */     this.socketTimeout = i;
/*    */   }
/*    */ 
/*    */   public void setTcpNoDelay(boolean b) {
/* 66 */     this.tcpNoDelay = b;
/*    */   }
/*    */ }