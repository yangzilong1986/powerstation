/*     */ package com.hisun.atc.common;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.SocketException;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.SystemUtils;
/*     */ import org.apache.commons.net.ftp.FTPClient;
/*     */ import org.apache.commons.net.ftp.FTPReply;
/*     */ 
/*     */ public class HiFTP
/*     */ {
/*  19 */   private String _server = null;
/*  20 */   private String _username = null;
/*  21 */   private int _port = -1;
/*  22 */   private String _password = null;
/*  23 */   private String _remoteFile = null;
/*  24 */   private String _localFile = null;
/*  25 */   private String _param = null;
/*  26 */   private boolean _binaryTransfer = true;
/*  27 */   private boolean _storeFile = false;
/*  28 */   private String _localDir = null;
/*  29 */   private String _remoteDir = null;
/*  30 */   private FTPClient _ftp = new FTPClient();
/*     */ 
/*  32 */   public final String ERR_FTP = "241190";
/*     */ 
/*     */   public void get()
/*     */     throws HiException
/*     */   {
/*  38 */     this._storeFile = false;
/*  39 */     process();
/*     */   }
/*     */ 
/*     */   private void process() throws HiException {
/*  43 */     InputStream input = null;
/*  44 */     OutputStream output = null;
/*     */     try
/*     */     {
/*     */       String localFile;
/*     */       String remoteFile;
/*  46 */       if (this._port != -1)
/*  47 */         this._ftp.connect(this._server, this._port);
/*     */       else {
/*  49 */         this._ftp.connect(this._server);
/*     */       }
/*  51 */       int reply = this._ftp.getReplyCode();
/*  52 */       if (!(FTPReply.isPositiveCompletion(reply))) {
/*  53 */         this._ftp.disconnect();
/*  54 */         throw new HiException("241190", "FTP server refused connection.");
/*     */       }
/*  56 */       if (!(this._ftp.login(this._username, this._password))) {
/*  57 */         this._ftp.logout();
/*  58 */         throw new HiException("241190", "user or password error");
/*     */       }
/*  60 */       if (this._binaryTransfer) {
/*  61 */         this._ftp.setFileType(2);
/*     */       }
/*  63 */       this._ftp.enterLocalPassiveMode();
/*  64 */       if (!(StringUtils.isEmpty(this._param))) {
/*  65 */         this._ftp.sendSiteCommand(this._param);
/*     */       }
/*     */ 
/*  69 */       if (this._storeFile)
/*     */       {
/*  71 */         if (StringUtils.isEmpty(this._remoteFile)) {
/*  72 */           this._remoteFile = this._localFile;
/*     */         }
/*     */ 
/*     */       }
/*  76 */       else if (StringUtils.isEmpty(this._localFile)) {
/*  77 */         this._localFile = this._remoteFile;
/*     */       }
/*     */ 
/*  81 */       if (StringUtils.isEmpty(this._localDir)) {
/*  82 */         localFile = this._localFile;
/*     */       }
/*  84 */       else if (this._localDir.endsWith(SystemUtils.FILE_SEPARATOR))
/*  85 */         localFile = this._localDir + this._localFile;
/*     */       else {
/*  87 */         localFile = this._localDir + SystemUtils.FILE_SEPARATOR + this._localFile;
/*     */       }
/*     */ 
/*  93 */       if (StringUtils.isEmpty(this._remoteDir)) {
/*  94 */         remoteFile = this._remoteFile;
/*     */       }
/*  96 */       else if (this._remoteDir.endsWith(SystemUtils.FILE_SEPARATOR))
/*  97 */         remoteFile = this._remoteDir + this._remoteFile;
/*     */       else {
/*  99 */         remoteFile = this._remoteDir + SystemUtils.FILE_SEPARATOR + this._remoteFile;
/*     */       }
/*     */ 
/* 104 */       if (this._storeFile)
/*     */       {
/* 106 */         input = new FileInputStream(localFile);
/*     */ 
/* 108 */         if (!(this._ftp.storeFile(remoteFile, input))) {
/* 109 */           input.close();
/* 110 */           throw new HiException("241190", remoteFile + ":" + this._ftp.getReplyCode() + ":" + this._ftp.getReplyString());
/*     */         }
/*     */ 
/* 114 */         input.close();
/*     */       } else {
/* 116 */         output = new FileOutputStream(localFile);
/*     */ 
/* 118 */         if (!(this._ftp.retrieveFile(remoteFile, output))) {
/* 119 */           output.close();
/* 120 */           throw new HiException("241190", remoteFile + ":" + this._ftp.getReplyCode() + ":" + this._ftp.getReplyString());
/*     */         }
/*     */ 
/* 123 */         output.close();
/*     */       }
/* 125 */       this._ftp.logout();
/*     */     } catch (SocketException e) {
/*     */     }
/*     */     catch (IOException e) {
/*     */     }
/*     */     finally {
/* 131 */       if (this._ftp.isConnected())
/*     */         try {
/* 133 */           this._ftp.disconnect();
/*     */         }
/*     */         catch (IOException f)
/*     */         {
/*     */         }
/* 138 */       if (input != null)
/*     */         try {
/* 140 */           input.close();
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/*     */         }
/* 145 */       if (output != null)
/*     */         try {
/* 147 */           output.close();
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void put() throws HiException
/*     */   {
/* 157 */     this._storeFile = true;
/* 158 */     process();
/*     */   }
/*     */ 
/*     */   public String getServer() {
/* 162 */     return this._server;
/*     */   }
/*     */ 
/*     */   public void setServer(String _server) {
/* 166 */     this._server = _server;
/*     */   }
/*     */ 
/*     */   public String getUsername() {
/* 170 */     return this._username;
/*     */   }
/*     */ 
/*     */   public void setUsername(String _username) {
/* 174 */     this._username = _username;
/*     */   }
/*     */ 
/*     */   public String getPassword() {
/* 178 */     return this._password;
/*     */   }
/*     */ 
/*     */   public void setPassword(String _password) {
/* 182 */     this._password = _password;
/*     */   }
/*     */ 
/*     */   public boolean isBinaryTransfer() {
/* 186 */     return this._binaryTransfer;
/*     */   }
/*     */ 
/*     */   public void setBinaryTransfer(boolean binaryTransfer) {
/* 190 */     this._binaryTransfer = binaryTransfer;
/*     */   }
/*     */ 
/*     */   public boolean isStoreFile() {
/* 194 */     return this._storeFile;
/*     */   }
/*     */ 
/*     */   public void setStoreFile(boolean storeFile) {
/* 198 */     this._storeFile = storeFile;
/*     */   }
/*     */ 
/*     */   public String getLocalFile() {
/* 202 */     return this._localFile;
/*     */   }
/*     */ 
/*     */   public void setLocalFile(String localFile) {
/* 206 */     this._localFile = localFile;
/*     */   }
/*     */ 
/*     */   public String getLocalDir() {
/* 210 */     return this._localDir;
/*     */   }
/*     */ 
/*     */   public void setLocalDir(String localDir) {
/* 214 */     this._localDir = localDir;
/*     */   }
/*     */ 
/*     */   public String getRemoteDir() {
/* 218 */     return this._remoteDir;
/*     */   }
/*     */ 
/*     */   public void setRemoteDir(String dir) {
/* 222 */     this._remoteDir = dir;
/*     */   }
/*     */ 
/*     */   public String getRemoteFile() {
/* 226 */     return this._remoteFile;
/*     */   }
/*     */ 
/*     */   public void setRemoteFile(String file) {
/* 230 */     this._remoteFile = file;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 234 */     StringBuffer result = new StringBuffer();
/* 235 */     result.append(";server=");
/* 236 */     result.append(this._server);
/* 237 */     result.append(";username=");
/* 238 */     result.append(this._username);
/* 239 */     result.append(";password=");
/* 240 */     result.append(this._password);
/* 241 */     result.append(";remoteDir=");
/* 242 */     result.append(this._remoteDir);
/* 243 */     result.append(";remoteFile=");
/* 244 */     result.append(this._remoteFile);
/* 245 */     result.append(";localDir=");
/* 246 */     result.append(this._localDir);
/* 247 */     result.append(";localFile=");
/* 248 */     result.append(this._localFile);
/* 249 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public int getPort() {
/* 253 */     return this._port;
/*     */   }
/*     */ 
/*     */   public void setPort(int _port) {
/* 257 */     this._port = _port;
/*     */   }
/*     */ 
/*     */   public String getParam() {
/* 261 */     return this._param;
/*     */   }
/*     */ 
/*     */   public void setParam(String _param) {
/* 265 */     this._param = _param;
/*     */   }
/*     */ }