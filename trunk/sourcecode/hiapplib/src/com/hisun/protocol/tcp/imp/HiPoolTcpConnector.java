/*     */ package com.hisun.protocol.tcp.imp;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.protocol.tcp.HiConnection;
/*     */ import com.hisun.protocol.tcp.HiConnectionPool;
/*     */ import com.hisun.protocol.tcp.HiMessageInOut;
/*     */ import com.hisun.protocol.tcp.HiSocketUtil;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.IOException;
/*     */ import java.net.Socket;
/*     */ 
/*     */ public class HiPoolTcpConnector extends HiConnectionPool
/*     */ {
/*     */   private final HiMessageInOut msginout;
/*  20 */   private static final HiStringManager sm = HiStringManager.getManager();
/*     */ 
/*     */   public HiPoolTcpConnector() {
/*  23 */     this.msginout = new HiMessageInOut();
/*     */   }
/*     */ 
/*     */   public HiPoolTcpConnector(HiMessageInOut inout) {
/*  27 */     this.msginout = inout;
/*     */   }
/*     */ 
/*     */   public int getSocketBuffer() {
/*  31 */     return this.msginout.getSocketBuffer();
/*     */   }
/*     */ 
/*     */   public void setSocketBuffer(int valueI) {
/*  35 */     this.msginout.setSocketBuffer(valueI);
/*     */   }
/*     */ 
/*     */   public int getPreLen() {
/*  39 */     return this.msginout.getPreLen();
/*     */   }
/*     */ 
/*     */   public void setPreLen(int preLen) {
/*  43 */     this.msginout.setPreLen(preLen);
/*     */   }
/*     */ 
/*     */   public void setPreLenType(String type) {
/*  47 */     this.msginout.setPreLenType(type);
/*     */   }
/*     */ 
/*     */   public String getPreLenType() {
/*  51 */     return this.msginout.getPreLenType();
/*     */   }
/*     */ 
/*     */   public HiMessage send(HiMessage msg) throws HiException {
/*  55 */     HiConnection conn = null;
/*     */     try {
/*  57 */       if ((this.log.isInfoEnabled()) && 
/*  59 */         (this.log.isInfoEnabled())) {
/*  60 */         HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
/*  61 */         this.log.info(sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), this.host, String.valueOf(this.port), String.valueOf(byteBuffer.length()), byteBuffer));
/*     */       }
/*     */ 
/*  67 */       conn = getConnection();
/*     */     }
/*     */     catch (IOException e) {
/*  70 */       if (conn != null) {
/*  71 */         conn.close();
/*     */       }
/*  73 */       throw new HiException("231204", "connector error", e);
/*     */     }
/*     */     catch (InterruptedException e) {
/*  76 */       if (conn != null) {
/*  77 */         conn.close();
/*     */       }
/*  79 */       throw new HiException("231204", "connector interrupt error", e);
/*     */     }
/*     */ 
/*  83 */     Socket socket = conn.getSocket();
/*  84 */     HiSocketUtil.printSocketStatus(socket, this.log);
/*     */     try {
/*  86 */       this.msginout.write(socket.getOutputStream(), msg);
/*     */     } catch (IOException e) {
/*  88 */       if (conn != null)
/*  89 */         conn.close();
/*  90 */       throw new HiException("231207", "connector send error", e);
/*     */     }
/*     */ 
/*  94 */     conn.returnToPool();
/*  95 */     return msg;
/*     */   }
/*     */ 
/*     */   public HiMessage send(HiMessage msg, String ip, int port)
/*     */     throws HiException
/*     */   {
/* 101 */     HiConnection conn = null;
/*     */     try {
/* 103 */       if ((this.log.isInfoEnabled()) && 
/* 105 */         (this.log.isInfoEnabled())) {
/* 106 */         HiByteBuffer byteBuffer = (HiByteBuffer)msg.getBody();
/* 107 */         this.log.info(sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), ip, String.valueOf(port), String.valueOf(byteBuffer.length()), byteBuffer));
/*     */       }
/*     */ 
/* 112 */       conn = getConnection(ip, port);
/*     */     } catch (IOException e) {
/* 114 */       if (conn != null) {
/* 115 */         conn.close();
/*     */       }
/* 117 */       throw new HiException("231204", "connector error", e);
/*     */     }
/*     */     catch (InterruptedException e) {
/* 120 */       if (conn != null) {
/* 121 */         conn.close();
/*     */       }
/* 123 */       throw new HiException("231204", "connector interrupt error", e);
/*     */     }
/*     */ 
/* 127 */     Socket socket = conn.getSocket();
/*     */     try {
/* 129 */       this.msginout.write(socket.getOutputStream(), msg);
/*     */     } catch (IOException e) {
/* 131 */       if (conn != null)
/* 132 */         conn.close();
/* 133 */       throw new HiException("231207", "connector send error", e);
/*     */     }
/*     */ 
/* 137 */     conn.returnToPool();
/* 138 */     return msg;
/*     */   }
/*     */ 
/*     */   public HiMessage sendReceive(HiMessage msg, String ip, int port) throws HiException
/*     */   {
/* 143 */     HiConnection conn = null;
/*     */     try
/*     */     {
/* 146 */       conn = getConnection(ip, port);
/*     */     } catch (IOException e) {
/* 148 */       if (conn != null) {
/* 149 */         conn.close();
/*     */       }
/* 151 */       throw new HiException("231204", "connector error", e);
/*     */     }
/*     */     catch (InterruptedException rdlen)
/*     */     {
/*     */       int rdlen;
/* 154 */       if (conn != null) {
/* 155 */         conn.close();
/*     */       }
/* 157 */       throw new HiException("231204", "connector interrupt error", e);
/*     */ 
/* 160 */       Socket socket = conn.getSocket();
/* 161 */       HiSocketUtil.printSocketStatus(socket, this.log);
/*     */ 
/* 163 */       if ((this.log.isInfoEnabled()) && 
/* 165 */         (this.log.isInfoEnabled())) {
/* 166 */         byteBuffer = (HiByteBuffer)msg.getBody();
/* 167 */         this.log.info(sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), this.host, String.valueOf(port), String.valueOf(byteBuffer.length()), byteBuffer));
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 173 */         this.msginout.write(socket.getOutputStream(), msg);
/*     */       } catch (IOException byteBuffer) {
/* 175 */         if (conn != null)
/* 176 */           conn.close();
/* 177 */         throw new HiException("231207", "connector send error", byteBuffer);
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 182 */         rdlen = this.msginout.read(socket.getInputStream(), msg);
/*     */       } catch (IOException rdlen) {
/* 184 */         if (conn != null)
/* 185 */           conn.close();
/* 186 */         throw new HiException("231205", "connector receive error", byteBuffer);
/*     */       }
/*     */ 
/* 190 */       if (this.log.isInfoEnabled())
/*     */       {
/* 192 */         byteBuffer = (HiByteBuffer)msg.getBody();
/* 193 */         this.log.info(sm.getString("HiPoolTcpConnector.receive00", msg.getRequestId(), this.host, String.valueOf(port), String.valueOf(byteBuffer.length()), byteBuffer));
/*     */       }
/*     */ 
/* 199 */       msg.setHeadItem("SCH", "rp");
/*     */ 
/* 203 */       if (conn == null) break label309;
/* 204 */       label309: conn.close();
/*     */     }
/*     */     finally
/*     */     {
/* 203 */       if (conn != null) {
/* 204 */         conn.close();
/*     */       }
/*     */     }
/*     */ 
/* 208 */     return msg;
/*     */   }
/*     */ 
/*     */   public HiMessage sendReceive(HiMessage msg) throws HiException
/*     */   {
/* 213 */     HiConnection conn = null;
/*     */     try
/*     */     {
/* 216 */       conn = getConnection();
/*     */     } catch (IOException e) {
/* 218 */       if (conn != null) {
/* 219 */         conn.close();
/*     */       }
/* 221 */       throw new HiException("231204", "connector error", e);
/*     */     }
/*     */     catch (InterruptedException rdlen)
/*     */     {
/*     */       int rdlen;
/* 224 */       if (conn != null) {
/* 225 */         conn.close();
/*     */       }
/* 227 */       throw new HiException("231204", "connector interrupt error", e);
/*     */ 
/* 230 */       Socket socket = conn.getSocket();
/* 231 */       HiSocketUtil.printSocketStatus(socket, this.log);
/*     */ 
/* 233 */       if ((this.log.isInfoEnabled()) && 
/* 235 */         (this.log.isInfoEnabled())) {
/* 236 */         byteBuffer = (HiByteBuffer)msg.getBody();
/* 237 */         this.log.info(sm.getString("HiPoolTcpConnector.send00", msg.getRequestId(), this.host, String.valueOf(this.port), String.valueOf(byteBuffer.length()), byteBuffer));
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 243 */         this.msginout.write(socket.getOutputStream(), msg);
/*     */       } catch (IOException byteBuffer) {
/* 245 */         if (conn != null)
/* 246 */           conn.close();
/* 247 */         throw new HiException("231207", "connector send error", byteBuffer);
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 252 */         rdlen = this.msginout.read(socket.getInputStream(), msg);
/*     */       } catch (IOException rdlen) {
/* 254 */         if (conn != null)
/* 255 */           conn.close();
/* 256 */         throw new HiException("231205", "connector receive error", byteBuffer);
/*     */       }
/*     */ 
/* 260 */       if (this.log.isInfoEnabled())
/*     */       {
/* 262 */         byteBuffer = (HiByteBuffer)msg.getBody();
/* 263 */         this.log.info(sm.getString("HiPoolTcpConnector.receive00", msg.getRequestId(), this.host, String.valueOf(this.port), String.valueOf(byteBuffer.length()), byteBuffer));
/*     */       }
/*     */ 
/* 269 */       msg.setHeadItem("SCH", "rp");
/*     */ 
/* 273 */       if (conn == null) break label296;
/* 274 */       label296: conn.close();
/*     */     }
/*     */     finally
/*     */     {
/* 273 */       if (conn != null) {
/* 274 */         conn.close();
/*     */       }
/*     */     }
/*     */ 
/* 278 */     return msg;
/*     */   }
/*     */ }