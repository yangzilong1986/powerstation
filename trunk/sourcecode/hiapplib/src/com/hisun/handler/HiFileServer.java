/*     */ package com.hisun.handler;
/*     */ 
/*     */ import com.hisun.dispatcher.HiRouterOut;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.protocol.tcp.HiTcpConnectionHandler;
/*     */ import com.hisun.protocol.tcp.imp.HiTcpListener;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.Socket;
/*     */ import org.apache.commons.lang.SystemUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiFileServer extends HiTcpListener
/*     */   implements HiTcpConnectionHandler
/*     */ {
/*     */   private static final int INNER_MSG_LENGTH = 136;
/*  37 */   private String fileSaveDir = null;
/*     */   private int fileNameLen;
/*     */   private int packageLenLen;
/*  43 */   private int fileFlag = 0;
/*     */ 
/*     */   public HiFileServer()
/*     */   {
/*  48 */     setMinThreads(1);
/*  49 */     setMaxThreads(10);
/*     */   }
/*     */ 
/*     */   public int getFileNameLen() {
/*  53 */     return this.fileNameLen;
/*     */   }
/*     */ 
/*     */   public void setFileNameLen(int fileNameLen) {
/*  57 */     this.fileNameLen = fileNameLen;
/*     */   }
/*     */ 
/*     */   public String getFileSaveDir() {
/*  61 */     return this.fileSaveDir;
/*     */   }
/*     */ 
/*     */   public void setFileSaveDir(String fileSaveDir) {
/*  65 */     this.fileSaveDir = fileSaveDir;
/*     */   }
/*     */ 
/*     */   public int getListenPort() {
/*  69 */     return getLocalPort();
/*     */   }
/*     */ 
/*     */   public void setListenPort(int listenPort) {
/*  73 */     setLocalPort(listenPort);
/*     */   }
/*     */ 
/*     */   public int getPackageLenLen() {
/*  77 */     return this.packageLenLen;
/*     */   }
/*     */ 
/*     */   public void setPackageLenLen(int packageLenLen) {
/*  81 */     this.packageLenLen = packageLenLen;
/*     */   }
/*     */ 
/*     */   public void setFileFlag(int fileFlag) {
/*  85 */     this.fileFlag = fileFlag;
/*     */   }
/*     */ 
/*     */   public Object[] initParam() {
/*  89 */     return null;
/*     */   }
/*     */ 
/*     */   public void processConnection(Socket socket, Object[] thData)
/*     */   {
/*  97 */     FileOutputStream fos = null;
/*  98 */     OutputStream os = null;
/*  99 */     InputStream is = null;
/*     */     try {
/* 101 */       os = socket.getOutputStream();
/* 102 */       is = socket.getInputStream();
/* 103 */       if (this.log.isInfoEnabled()) {
/* 104 */         this.log.info("文件接收服务器： 文件接收函数开始。");
/*     */       }
/* 106 */       byte[] msgBuffer = new byte[256];
/* 107 */       byte[] tmpBuffer = new byte[256];
/* 108 */       byte[] buffer = new byte[10240];
/* 109 */       if (this.log.isDebugEnabled()) {
/* 110 */         this.log.debug("文件接收服务器： 接收数据包长度.");
/*     */       }
/* 112 */       int len = is.read(tmpBuffer, 0, this.packageLenLen);
/* 113 */       if (len != this.packageLenLen) {
/* 114 */         throw new HiException("231419");
/*     */       }
/*     */ 
/* 117 */       int packageLen = 0;
/* 118 */       packageLen = NumberUtils.toInt(new String(tmpBuffer, 0, this.packageLenLen));
/*     */ 
/* 120 */       if (packageLen <= 0) {
/* 121 */         throw new HiException("231419");
/*     */       }
/* 123 */       if (this.log.isDebugEnabled()) {
/* 124 */         this.log.debug("文件接收服务器： 数据包长度[" + String.valueOf(packageLen) + "]。");
/*     */       }
/*     */ 
/* 128 */       if (this.log.isDebugEnabled()) {
/* 129 */         this.log.debug("文件接收服务器： 接收文件名。");
/*     */       }
/*     */ 
/* 132 */       len = is.read(tmpBuffer, 0, this.fileNameLen);
/* 133 */       if (len != this.fileNameLen) {
/* 134 */         throw new HiException("231420");
/*     */       }
/*     */ 
/* 137 */       String fileName = HiICSProperty.getWorkDir() + SystemUtils.FILE_SEPARATOR + this.fileSaveDir + SystemUtils.FILE_SEPARATOR + new String(tmpBuffer, 0, this.fileNameLen).trim();
/*     */ 
/* 141 */       if (this.log.isInfoEnabled()) {
/* 142 */         this.log.info("文件接收服务器： 组合文件名:[" + fileName + "]");
/*     */       }
/*     */ 
/* 145 */       if (this.log.isDebugEnabled()) {
/* 146 */         this.log.debug("文件接收服务器： 创建文件。");
/*     */       }
/*     */ 
/* 149 */       File f = new File(fileName);
/* 150 */       fos = new FileOutputStream(f);
/* 151 */       if (fos == null) {
/* 152 */         throw new HiException("231421");
/*     */       }
/*     */ 
/* 155 */       if (this.fileFlag == 1) {
/* 156 */         if (this.log.isDebugEnabled()) {
/* 157 */           this.log.debug("文件接收服务器： 接收内部文件消息数据。");
/*     */         }
/* 159 */         len = is.read(msgBuffer, 0, 136);
/* 160 */         if (len != 136) {
/* 161 */           throw new HiException("231422");
/*     */         }
/*     */ 
/* 164 */         if (this.log.isDebugEnabled()) {
/* 165 */           this.log.debug("文件接收服务器： 数据[" + new String(msgBuffer, 0, len) + "]");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 170 */       int totalLen = 0;
/* 171 */       if (this.fileFlag == 1)
/* 172 */         totalLen = this.fileNameLen + 136;
/*     */       else {
/* 174 */         totalLen = this.fileNameLen;
/*     */       }
/*     */ 
/* 177 */       if (this.log.isDebugEnabled()) {
/* 178 */         this.log.debug("文件接收服务器： 接收文件数据。");
/*     */       }
/*     */ 
/* 181 */       int remainLen = 0;
/* 182 */       while (totalLen < packageLen) {
/* 183 */         remainLen = packageLen - totalLen;
/* 184 */         if (remainLen > buffer.length)
/* 185 */           remainLen = buffer.length;
/* 186 */         len = is.read(buffer, 0, remainLen);
/* 187 */         if (this.log.isDebugEnabled()) {
/* 188 */           this.log.debug("文件接收服务器： 接收文件数据:[" + new String(buffer, 0, len) + "]:[" + String.valueOf(totalLen) + "]:[" + String.valueOf(len) + "]");
/*     */         }
/*     */ 
/* 192 */         fos.write(buffer, 0, len);
/* 193 */         totalLen += len;
/*     */       }
/* 195 */       fos.close();
/*     */ 
/* 197 */       if (this.fileFlag == 1) {
/* 198 */         if (this.log.isDebugEnabled()) {
/* 199 */           this.log.debug("文件接收服务器： 发送内部文件消息数据。");
/*     */         }
/*     */ 
/* 202 */         HiMessage msg = new HiMessage("S.FilSvr", "SAF001");
/* 203 */         msg.setHeadItem("SCH", "rq");
/*     */ 
/* 205 */         HiETF etf = HiETFFactory.createETF(new String(msgBuffer, 0, 136));
/*     */ 
/* 207 */         msg.setBody(etf);
/* 208 */         HiRouterOut.asyncProcess(msg);
/*     */       }
/* 210 */       if (this.log.isInfoEnabled()) {
/* 211 */         this.log.info("文件接收服务器： 文件接收成功");
/*     */       }
/* 213 */       os.write("SUCCESS".getBytes());
/* 214 */       socket.close();
/*     */     } catch (IOException e) {
/*     */       try {
/* 217 */         if (os != null)
/* 218 */           os.write("ERROR".getBytes());
/*     */       } catch (IOException e1) {
/* 220 */         this.log.error(e1);
/*     */       }
/* 222 */       this.log.error(sm.getString(String.valueOf("231422")), e);
/*     */     }
/*     */     catch (HiException e) {
/*     */       try {
/* 226 */         if (os != null)
/* 227 */           os.write("ERROR".getBytes());
/*     */       } catch (IOException e1) {
/* 229 */         this.log.error(e1);
/*     */       }
/* 231 */       this.log.error(e);
/*     */     } finally {
/*     */       try {
/* 234 */         if (fos != null)
/* 235 */           fos.close();
/* 236 */         if (socket != null)
/* 237 */           socket.close();
/*     */       } catch (IOException e) {
/* 239 */         this.log.error(e);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean checkAccessable(Socket arg0) {
/* 245 */     return true;
/*     */   }
/*     */ }