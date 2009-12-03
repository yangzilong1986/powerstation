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
/*     */ public class HiTcpSynConnector
/*     */   implements IHandler, IServerInitListener
/*     */ {
/*  24 */   protected static HiStringManager g_sm = HiStringManager.getManager();
/*     */   protected int _port;
/*     */   protected String _host;
/*     */   protected int _preLen;
/*     */   protected Logger _log;
/*     */   protected String _preLenType;
/*     */   protected int _timeOut;
/*     */   protected final HiMessageInOut msginout;
/*     */   protected HiSSLHandler sslHandler;
/*     */   protected SocketFactory socketFactory;
/*     */ 
/*     */   public HiTcpSynConnector()
/*     */   {
/*  28 */     this._log = null;
/*  29 */     this._preLenType = null;
/*  30 */     this._timeOut = 30;
/*  31 */     this.msginout = new HiMessageInOut();
/*  32 */     this.sslHandler = HiSSLHandler.getInstance();
/*  33 */     this.socketFactory = null; }
/*     */ 
/*     */   public void setPort(int port) { this._port = port;
/*     */   }
/*     */ 
/*     */   public void setHost(String host) {
/*  39 */     this._host = host;
/*     */   }
/*     */ 
/*     */   public void setPreLen(int preLen) {
/*  43 */     this._preLen = preLen;
/*  44 */     this.msginout.setPreLen(this._preLen);
/*     */   }
/*     */ 
/*     */   public void setPreLenType(String type) {
/*  48 */     this._preLenType = type;
/*  49 */     this.msginout.setPreLenType(type);
/*     */   }
/*     */ 
/*     */   public void setTimeOut(int timeOut) {
/*  53 */     this._timeOut = timeOut;
/*     */   }
/*     */ 
/*     */   public void setLog(Logger log)
/*     */   {
/*  58 */     this._log = log;
/*     */   }
/*     */ 
/*     */   public void setSslMode(String mode)
/*     */     throws HiException
/*     */   {
/*  66 */     this.sslHandler.setSslMode(Integer.parseInt(mode));
/*     */   }
/*     */ 
/*     */   public void setIdentityKS(String identityKS)
/*     */     throws HiException
/*     */   {
/*  75 */     this.sslHandler.setIdentityKS(identityKS);
/*     */   }
/*     */ 
/*     */   public void setKeyPsw(String keyPsw)
/*     */     throws HiException
/*     */   {
/*  84 */     this.sslHandler.setKeyPsw(keyPsw);
/*     */   }
/*     */ 
/*     */   public void setAlg(String alg)
/*     */     throws HiException
/*     */   {
/*  92 */     this.sslHandler.setAlg(alg);
/*     */   }
/*     */ 
/*     */   public void setTrustKS(String trustKS)
/*     */     throws HiException
/*     */   {
/* 100 */     this.sslHandler.setTrustKS(trustKS);
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext ctx) throws HiException
/*     */   {
/*     */     try {
/* 106 */       HiMessage msg = ctx.getCurrentMsg();
/* 107 */       String ip = msg.getHeadItem("OIP");
/* 108 */       String port = msg.getHeadItem("OPT");
/* 109 */       if (ip == null) {
/* 110 */         sendReceive(msg);
/*     */       } else {
/* 112 */         int nport = Integer.parseInt(port);
/* 113 */         sendReceive(msg, ip, nport);
/*     */       }
/*     */     } catch (HiException e) {
/* 116 */       this._log.error(e);
/* 117 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public HiMessage sendReceive(HiMessage msg) throws HiException {
/* 122 */     return sendReceive(msg, this._host, this._port);
/*     */   }
/*     */ 
/*     */   public HiMessage sendReceive(HiMessage msg, String ip, int port) throws HiException
/*     */   {
/* 127 */     Socket socket = null;
/*     */     try {
/*     */       int rdlen;
/*     */       try { socket = createConnection(ip, port);
/*     */       } catch (IOException e) {
/* 132 */         throw new HiException("231204", "connector error", e);
/*     */       }
/*     */ 
/* 135 */       if (this._log.isInfoEnabled())
/*     */       {
/* 137 */         byteBuffer = (HiByteBuffer)msg.getBody();
/* 138 */         this._log.info(g_sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), ip, String.valueOf(port), String.valueOf(byteBuffer.length()), byteBuffer));
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 143 */         this.msginout.write(socket.getOutputStream(), msg);
/*     */       } catch (IOException byteBuffer) {
/* 145 */         closeConnection(socket);
/* 146 */         throw new HiException("231207", "connector send error", byteBuffer);
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 151 */         rdlen = this.msginout.read(socket.getInputStream(), msg);
/*     */       } catch (IOException rdlen) {
/* 153 */         closeConnection(socket);
/* 154 */         throw new HiException("231205", "connector receive error", byteBuffer);
/*     */       }
/*     */ 
/* 158 */       if (this._log.isInfoEnabled())
/*     */       {
/* 160 */         byteBuffer = (HiByteBuffer)msg.getBody();
/* 161 */         this._log.info(g_sm.getString("HiPoolTcpConnector.receive00", msg.getRequestId(), ip, String.valueOf(port), String.valueOf(byteBuffer.length()), byteBuffer));
/*     */       }
/*     */ 
/* 167 */       msg.setHeadItem("SCH", "rp");
/*     */     }
/*     */     finally
/*     */     {
/* 171 */       closeConnection(socket);
/*     */     }
/* 173 */     if (this._log.isDebugEnabled())
/* 174 */       this._log.debug("[connector] return");
/* 175 */     return msg;
/*     */   }
/*     */ 
/*     */   private Socket createConnection(String host, int port) throws UnknownHostException, IOException {
/* 179 */     Socket socket = null;
/*     */ 
/* 181 */     socket = this.socketFactory.createSocket();
/*     */ 
/* 183 */     if (this._timeOut > 0)
/* 184 */       socket.connect(new InetSocketAddress(host, port), this._timeOut * 1000);
/*     */     else
/* 186 */       socket.connect(new InetSocketAddress(host, port));
/* 187 */     if (this._timeOut > 0) {
/* 188 */       socket.setSoTimeout(this._timeOut * 1000);
/*     */     }
/* 190 */     socket.setTcpNoDelay(true);
/* 191 */     return socket;
/*     */   }
/*     */ 
/*     */   private void closeConnection(Socket socket) {
/* 195 */     if (socket == null)
/* 196 */       return;
/*     */     try {
/* 198 */       socket.close();
/*     */     } catch (IOException e) {
/*     */     }
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0) throws HiException {
/* 204 */     this._log = arg0.getLog();
/* 205 */     if (this.sslHandler.isSSLMode())
/*     */     {
/* 207 */       this.sslHandler.init();
/* 208 */       this.socketFactory = this.sslHandler.getSocketFactory();
/*     */     }
/*     */     else
/*     */     {
/* 212 */       this.socketFactory = SocketFactory.getDefault();
/*     */     }
/*     */   }
/*     */ }