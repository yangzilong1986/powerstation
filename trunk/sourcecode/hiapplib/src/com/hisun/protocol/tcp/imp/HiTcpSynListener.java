/*    */ package com.hisun.protocol.tcp.imp;
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
/*    */ import com.hisun.protocol.tcp.filters.SendMessageFilter;
/*    */ import com.hisun.protocol.tcp.filters.SocketHandlers;
/*    */ import java.net.Socket;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class HiTcpSynListener extends HiTcpListener
/*    */ {
/*    */   public void serverInit(ServerEvent arg0)
/*    */     throws HiException
/*    */   {
/* 30 */     super.serverInit(arg0);
/*    */   }
/*    */ 
/*    */   public ISocketHandler buildSocketHandler()
/*    */     throws HiException
/*    */   {
/* 41 */     List filters = new ArrayList();
/*    */ 
/* 43 */     filters.add(new ExceptionCloseSocketFilter(this.log));
/*    */ 
/* 45 */     if (this.ipset != null) {
/* 46 */       this.ipcheck = new IpCheckFilter(this.ipset, this.log);
/* 47 */       filters.add(this.ipcheck);
/*    */     }
/*    */ 
/* 50 */     filters.add(this.opthandler);
/* 51 */     filters.add(getPoolhandler());
/*    */ 
/* 53 */     filters.add(new CloseSocketFilter(this.log));
/* 54 */     filters.add(SocketHandlers.createContextFilter(getServer().getName(), getMsgType()));
/*    */ 
/* 56 */     filters.add(SocketHandlers.receiveMessageFilter(this.log, this.msginout));
/*    */ 
/* 58 */     filters.add(new SendMessageFilter(this.log, this.msginout));
/*    */ 
/* 60 */     ISocketHandler handler = new ISocketHandler() {
/*    */       public void process(Socket socket, HiMessageContext ctx) {
/*    */         try {
/* 63 */           HiTcpSynListener.this.getServer().process(ctx);
/*    */         } catch (Throwable e) {
/* 65 */           HiTcpSynListener.this.log.error("process error:" + ctx.getCurrentMsg().getRequestId(), e);
/* 66 */           if (e instanceof RuntimeException);
/* 69 */           throw new RuntimeException(e);
/*    */         } finally {
/* 71 */           HiLog.close(ctx.getCurrentMsg());
/*    */         }
/*    */       }
/*    */     };
/* 76 */     return buildSocketHandler(filters, handler);
/*    */   }
/*    */ }