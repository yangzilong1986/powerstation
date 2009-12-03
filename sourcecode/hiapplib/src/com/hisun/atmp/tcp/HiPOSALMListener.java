/*    */ package com.hisun.atmp.tcp;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.framework.HiDefaultServer;
/*    */ import com.hisun.framework.event.ServerEvent;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.protocol.tcp.ISocketHandler;
/*    */ import com.hisun.protocol.tcp.filters.MultiReaderPoolHandler;
/*    */ import com.hisun.protocol.tcp.filters.SocketHandlers;
/*    */ import com.hisun.protocol.tcp.imp.HiTcpListener;
/*    */ import java.net.Socket;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class HiPOSALMListener extends HiTcpListener
/*    */ {
/*    */   private MultiReaderPoolHandler readerhandler;
/*    */ 
/*    */   public HiPOSALMListener()
/*    */   {
/* 28 */     this.msginout = new HiPOSMessageInOut(null);
/*    */   }
/*    */ 
/*    */   public void serverInit(ServerEvent arg0) throws HiException {
/* 32 */     HiPOSMessageInOut msginout1 = (HiPOSMessageInOut)this.msginout;
/* 33 */     msginout1.setLog(arg0.getLog());
/* 34 */     super.serverInit(arg0);
/*    */   }
/*    */ 
/*    */   public void serverStop(ServerEvent arg0)
/*    */     throws HiException
/*    */   {
/* 40 */     super.serverStop(arg0);
/* 41 */     this.readerhandler.stop();
/*    */   }
/*    */ 
/*    */   public ISocketHandler buildSocketHandler()
/*    */   {
/* 51 */     List filters = new ArrayList();
/*    */ 
/* 53 */     filters.add(SocketHandlers.createContextFilter(getServer().getName(), getMsgType()));
/*    */ 
/* 55 */     filters.add(SocketHandlers.receiveMessageFilter(this.log, this.msginout));
/*    */ 
/* 58 */     filters.add(getPoolhandler());
/*    */ 
/* 64 */     filters.add(SocketHandlers.lockSendMessageFilter(this.log, this.msginout));
/*    */ 
/* 66 */     ISocketHandler handler = new ISocketHandler() {
/*    */       public void process(Socket socket, HiMessageContext ctx) {
/*    */         try {
/* 69 */           HiPOSALMListener.this.getServer().process(ctx);
/*    */         }
/*    */         catch (Throwable e) {
/* 72 */           throw new RuntimeException(e);
/*    */         } finally {
/* 74 */           HiLog.close(ctx.getCurrentMsg());
/*    */         }
/*    */       }
/*    */     };
/* 78 */     ISocketHandler ret_handler = buildSocketHandler(filters, handler);
/* 79 */     this.readerhandler = SocketHandlers.multiReaderPool(this.log, ret_handler);
/* 80 */     return this.readerhandler;
/*    */   }
/*    */ }