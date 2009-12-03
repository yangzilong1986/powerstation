/*     */ package com.hisun.atmp.tcp;
/*     */ 
/*     */ import com.hisun.atmp.handler.HiGetTxnCode;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.HiDefaultServer;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.protocol.tcp.HiMessageInOut;
/*     */ import com.hisun.protocol.tcp.ISocketHandler;
/*     */ import com.hisun.protocol.tcp.filters.CloseSocketFilter;
/*     */ import com.hisun.protocol.tcp.filters.ExceptionCloseSocketFilter;
/*     */ import com.hisun.protocol.tcp.filters.IpCheckFilter;
/*     */ import com.hisun.protocol.tcp.filters.SocketHandlers;
/*     */ import com.hisun.protocol.tcp.imp.HiTcpListener;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class HiTcpSynListener2 extends HiTcpListener
/*     */ {
/*     */   private String _DIBAOMsgType;
/*     */ 
/*     */   public HiTcpSynListener2()
/*     */   {
/*  34 */     this._DIBAOMsgType = "PLTIN2"; }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0) throws HiException {
/*  37 */     super.serverInit(arg0);
/*     */   }
/*     */ 
/*     */   public ISocketHandler buildSocketHandler()
/*     */   {
/*  47 */     List filters = new ArrayList();
/*     */ 
/*  49 */     filters.add(new ExceptionCloseSocketFilter(this.log));
/*     */ 
/*  51 */     if (getIpset() != null) {
/*  52 */       setIpcheck(new IpCheckFilter(getIpset(), this.log));
/*  53 */       filters.add(getIpcheck());
/*     */     }
/*     */ 
/*  56 */     filters.add(getOpthandler());
/*  57 */     filters.add(getPoolhandler());
/*     */ 
/*  59 */     filters.add(new CloseSocketFilter(this.log));
/*  60 */     filters.add(SocketHandlers.createContextFilter(getServer().getName(), getMsgType()));
/*     */ 
/*  62 */     filters.add(SocketHandlers.receiveMessageFilter(this.log, this.msginout));
/*     */ 
/*  66 */     ISocketHandler handler = new ISocketHandler() {
/*     */       public void process(Socket socket, HiMessageContext ctx) {
/*  68 */         HiMessage msg = ctx.getCurrentMsg();
/*     */         try {
/*  70 */           int type = HiGetTxnCode.getType(ctx);
/*  71 */           switch (type)
/*     */           {
/*     */           case 1:
/*  73 */             msg.setType(HiTcpSynListener2.this._DIBAOMsgType);
/*  74 */             socket.close();
/*  75 */             break;
/*     */           case 0:
/*     */           }
/*     */ 
/*  81 */           if (HiTcpSynListener2.this.log.isInfoEnabled()) {
/*  82 */             HiTcpSynListener2.this.log.info("recv msg3:[" + msg + "]");
/*     */           }
/*  84 */           HiTcpSynListener2.this.getServer().process(ctx);
/*     */ 
/*  86 */           switch (type)
/*     */           {
/*     */           case 1:
/*  88 */             break;
/*     */           case 0:
/*  90 */             HiTcpSynListener2.this.send(socket, ctx);
/*     */           }
/*     */ 
/*     */         }
/*     */         catch (Throwable e)
/*     */         {
/*  96 */           HiTcpSynListener2.this.log.error("[" + msg.getRequestId() + "]:" + socket, e);
/*     */         }
/*  98 */         HiLog.close(ctx.getCurrentMsg());
/*     */       }
/*     */     };
/* 104 */     return buildSocketHandler(filters, handler);
/*     */   }
/*     */ 
/*     */   private void send(Socket socket, HiMessageContext ctx) throws HiException {
/* 108 */     HiMessage msg = ctx.getCurrentMsg();
/*     */     try {
/* 110 */       this.msginout.write(socket.getOutputStream(), msg);
/* 111 */       if (this.log.isInfoEnabled()) {
/* 112 */         HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
/* 113 */         String ip = socket.getInetAddress().getHostAddress();
/* 114 */         this.log.info(sm.getString("tcplistener.send", msg.getRequestId(), ip, String.valueOf(byteBuffer.length()), byteBuffer.toString()));
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 119 */       this.log.error(sm.getString("tcplistener.err.write", socket.toString()), e);
/* 120 */       throw HiException.makeException("231201", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getMsgType1() {
/* 125 */     return getMsgType();
/*     */   }
/*     */ 
/*     */   public void setMsgType1(String type) {
/* 129 */     setMsgType(type);
/*     */   }
/*     */ 
/*     */   public String getMsgType2()
/*     */   {
/* 134 */     return this._DIBAOMsgType;
/*     */   }
/*     */ 
/*     */   public void setMsgType2(String type) {
/* 138 */     this._DIBAOMsgType = type;
/*     */   }
/*     */ }