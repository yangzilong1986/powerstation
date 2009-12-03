/*     */ package com.hisun.protocol.tcp.imp;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.protocol.tcp.HiMessageInOut;
/*     */ import com.hisun.protocol.tcp.HiSSLHandler;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.UnknownHostException;
/*     */ import javax.net.SocketFactory;
/*     */ 
/*     */ public class HiTcpSynConnector01
/*     */   implements IHandler, IServerInitListener
/*     */ {
/*  24 */   protected static HiStringManager g_sm = HiStringManager.getManager();
/*     */   protected int _port;
/*     */   protected String _host;
/*     */   protected int _preLen;
/*     */   protected Logger _log;
/*     */   protected String _preLenType;
/*     */   protected int _timeOut;
/*     */   protected HiSSLHandler sslHandler;
/*     */   protected final HiMessageInOut msginout;
/*     */ 
/*     */   public HiTcpSynConnector01()
/*     */   {
/*  28 */     this._log = null;
/*  29 */     this._preLenType = null;
/*  30 */     this._timeOut = 30;
/*  31 */     this.sslHandler = HiSSLHandler.getInstance();
/*     */ 
/*  33 */     this.msginout = new HiMessageInOut(); }
/*     */ 
/*     */   public void setPort(int port) {
/*  36 */     this._port = port;
/*     */   }
/*     */ 
/*     */   public void setHost(String host) {
/*  40 */     this._host = host;
/*     */   }
/*     */ 
/*     */   public void setPreLen(int preLen) {
/*  44 */     this._preLen = preLen;
/*  45 */     this.msginout.setPreLen(this._preLen);
/*     */   }
/*     */ 
/*     */   public void setPreLenType(String type) {
/*  49 */     this._preLenType = type;
/*  50 */     this.msginout.setPreLenType(type);
/*     */   }
/*     */ 
/*     */   public void setTimeOut(int timeOut) {
/*  54 */     this._timeOut = timeOut;
/*     */   }
/*     */ 
/*     */   public void setLog(Logger log)
/*     */   {
/*  59 */     this._log = log;
/*     */   }
/*     */ 
/*     */   public void setSslMode(int sslMode)
/*     */   {
/*  66 */     this.sslHandler.setSslMode(sslMode);
/*     */   }
/*     */ 
/*     */   public void setIdentityKS(String identityKS)
/*     */   {
/*  74 */     this.sslHandler.setIdentityKS(identityKS);
/*     */   }
/*     */ 
/*     */   public void setTrustKS(String trustKS)
/*     */   {
/*  82 */     this.sslHandler.setTrustKS(trustKS);
/*     */   }
/*     */ 
/*     */   public void setKeyPsw(String keyPsw)
/*     */   {
/*  90 */     this.sslHandler.setKeyPsw(keyPsw);
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException {
/*     */     try {
/*  95 */       HiMessage msg = ctx.getCurrentMsg();
/*  96 */       String ip = msg.getHeadItem("OIP");
/*  97 */       String port = msg.getHeadItem("OPT");
/*  98 */       if (ip == null) {
/*  99 */         sendReceive(msg);
/*     */       } else {
/* 101 */         int nport = Integer.parseInt(port);
/* 102 */         sendReceive(msg, ip, nport);
/*     */       }
/*     */     } catch (HiException e) {
/* 105 */       this._log.error(e);
/* 106 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public HiMessage sendReceive(HiMessage msg) throws HiException {
/* 111 */     return sendReceive(msg, this._host, this._port);
/*     */   }
/*     */ 
/*     */   public HiMessage sendReceive(HiMessage msg, String ip, int port) throws HiException
/*     */   {
/* 116 */     Socket socket = null;
/*     */     try {
/*     */       int rdlen;
/*     */       try {
/* 120 */         if (!(this.sslHandler.isSSLMode())) {
/* 121 */           socket = createConnection(ip, port);
/*     */         }
/*     */         else
/* 124 */           socket = createSSLConnection(ip, port);
/*     */       }
/*     */       catch (IOException e) {
/* 127 */         throw new HiException("231204", "connector error", e);
/*     */       }
/*     */ 
/* 131 */       if (this._log.isInfoEnabled())
/*     */       {
/* 133 */         HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
/* 134 */         this._log.info(g_sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), ip, String.valueOf(port), String.valueOf(byteBuffer.length()), byteBuffer));
/*     */       }
/*     */ 
/* 139 */       HiByteBuffer tmp1 = (HiByteBuffer)msg.getBody();
/* 140 */       HiByteBuffer tmp2 = new HiByteBuffer(1024);
/* 141 */       tmp2.append(49);
/* 142 */       tmp2.append(tmp1.getBytes());
/* 143 */       msg.setBody(tmp2);
/*     */       try
/*     */       {
/* 146 */         this.msginout.write(socket.getOutputStream(), msg);
/*     */       } catch (IOException e) {
/* 148 */         closeConnection(socket);
/* 149 */         throw new HiException("231207", "connector send error", e);
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 154 */         rdlen = this.msginout.read(socket.getInputStream(), msg);
/*     */       } catch (IOException rdlen) {
/* 156 */         closeConnection(socket);
/* 157 */         throw new HiException("231205", "connector receive error", e);
/*     */       }
/*     */ 
/* 161 */       if (this._log.isInfoEnabled())
/*     */       {
/* 163 */         HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
/* 164 */         this._log.info(g_sm.getString("HiPoolTcpConnector.receive00", msg.getRequestId(), ip, String.valueOf(port), String.valueOf(byteBuffer.length()), byteBuffer));
/*     */       }
/*     */ 
/* 170 */       msg.setHeadItem("SCH", "rp");
/*     */     }
/*     */     finally
/*     */     {
/* 174 */       closeConnection(socket);
/*     */     }
/* 176 */     if (this._log.isDebugEnabled())
/* 177 */       this._log.debug("[connector] return");
/* 178 */     return msg;
/*     */   }
/*     */ 
/*     */   private Socket createConnection(String host, int port) throws UnknownHostException, IOException {
/* 182 */     Socket socket = new Socket();
/* 183 */     if (this._timeOut > 0)
/* 184 */       socket.connect(new InetSocketAddress(host, port), this._timeOut * 1000);
/*     */     else
/* 186 */       socket.connect(new InetSocketAddress(host, port));
/* 187 */     if (this._timeOut > 0)
/* 188 */       socket.setSoTimeout(this._timeOut * 1000);
/* 189 */     socket.setTcpNoDelay(true);
/* 190 */     return socket;
/*     */   }
/*     */ 
/*     */   private Socket createSSLConnection(String host, int port) throws UnknownHostException, IOException, HiException {
/* 194 */     this.sslHandler.init();
/* 195 */     SocketFactory socketFactory = this.sslHandler.getSocketFactory();
/*     */ 
/* 197 */     Socket socket = socketFactory.createSocket();
/* 198 */     if (this._timeOut > 0)
/* 199 */       socket.connect(new InetSocketAddress(host, port), this._timeOut * 1000);
/*     */     else {
/* 201 */       socket.connect(new InetSocketAddress(host, port));
/*     */     }
/* 203 */     if (this._timeOut > 0) {
/* 204 */       socket.setSoTimeout(this._timeOut * 1000);
/*     */     }
/* 206 */     return socket;
/*     */   }
/*     */ 
/*     */   private void closeConnection(Socket socket) {
/* 210 */     if (socket == null)
/* 211 */       return;
/*     */     try {
/* 213 */       socket.close();
/*     */     } catch (IOException e) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0) throws HiException {
/* 219 */     this._log = arg0.getLog();
/*     */   }
/*     */ }