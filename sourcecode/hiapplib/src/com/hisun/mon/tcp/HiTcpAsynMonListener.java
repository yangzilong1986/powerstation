/*    */ package com.hisun.mon.tcp;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.HiDefaultServer;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.protocol.tcp.ISocketHandler;
/*    */ import com.hisun.protocol.tcp.filters.CloseSocketFilter;
/*    */ import com.hisun.protocol.tcp.filters.ExceptionCloseSocketFilter;
/*    */ import com.hisun.protocol.tcp.filters.IpCheckFilter;
/*    */ import com.hisun.protocol.tcp.filters.SocketHandlers;
/*    */ import com.hisun.protocol.tcp.imp.HiTcpListener;
/*    */ import java.net.Socket;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class HiTcpAsynMonListener extends HiTcpListener
/*    */ {
/*    */   public void serverInit(ServerEvent arg0)
/*    */     throws HiException
/*    */   {
/* 23 */     super.serverInit(arg0);
/*    */   }
/*    */ 
/*    */   public ISocketHandler buildSocketHandler()
/*    */   {
/* 34 */     List filters = new ArrayList();
/*    */ 
/* 36 */     filters.add(new ExceptionCloseSocketFilter(this.log));
/*    */ 
/* 39 */     if (getIpset() != null) {
/* 40 */       setIpcheck(new IpCheckFilter(getIpset(), this.log));
/* 41 */       filters.add(getIpcheck());
/*    */     }
/*    */ 
/* 44 */     filters.add(getOpthandler());
/*    */ 
/* 46 */     filters.add(getPoolhandler());
/*    */ 
/* 48 */     filters.add(new CloseSocketFilter(this.log));
/* 49 */     filters.add(SocketHandlers.createContextFilter(getServer().getName(), getMsgType()));
/* 50 */     filters.add(SocketHandlers.receiveMessageFilter(this.log, this.msginout));
/*    */ 
/* 54 */     ISocketHandler handler = new ISocketHandler() {
/*    */       public void process(Socket socket, HiMessageContext ctx) {
/*    */         try {
/* 57 */           HiTcpAsynMonListener.this.getServer().process(ctx);
/*    */         } catch (Throwable e) {
/* 59 */           HiTcpAsynMonListener.this.log.error("process error:" + ctx.getCurrentMsg().getRequestId(), e);
/* 60 */           if (e instanceof RuntimeException) {
/* 61 */             throw ((RuntimeException)e);
/*    */           }
/* 63 */           throw new RuntimeException(e);
/*    */         } finally {
/* 65 */           HiLog.close(ctx.getCurrentMsg());
/*    */         }
/*    */       }
/*    */     };
/* 70 */     return buildSocketHandler(filters, handler);
/*    */   }
/*    */ }