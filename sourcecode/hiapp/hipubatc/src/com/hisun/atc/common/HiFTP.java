 package com.hisun.atc.common;
 
 import com.hisun.exception.HiException;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.net.SocketException;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.SystemUtils;
 import org.apache.commons.net.ftp.FTPClient;
 import org.apache.commons.net.ftp.FTPReply;
 
 public class HiFTP
 {
   private String _server = null;
   private String _username = null;
   private int _port = -1;
   private String _password = null;
   private String _remoteFile = null;
   private String _localFile = null;
   private String _param = null;
   private boolean _binaryTransfer = true;
   private boolean _storeFile = false;
   private String _localDir = null;
   private String _remoteDir = null;
   private FTPClient _ftp = new FTPClient();
 
   public final String ERR_FTP = "241190";
 
   public void get()
     throws HiException
   {
     this._storeFile = false;
     process();
   }
 
   private void process() throws HiException {
     InputStream input = null;
     OutputStream output = null;
     try
     {
       String localFile;
       String remoteFile;
       if (this._port != -1)
         this._ftp.connect(this._server, this._port);
       else {
         this._ftp.connect(this._server);
       }
       int reply = this._ftp.getReplyCode();
       if (!(FTPReply.isPositiveCompletion(reply))) {
         this._ftp.disconnect();
         throw new HiException("241190", "FTP server refused connection.");
       }
       if (!(this._ftp.login(this._username, this._password))) {
         this._ftp.logout();
         throw new HiException("241190", "user or password error");
       }
       if (this._binaryTransfer) {
         this._ftp.setFileType(2);
       }
       this._ftp.enterLocalPassiveMode();
       if (!(StringUtils.isEmpty(this._param))) {
         this._ftp.sendSiteCommand(this._param);
       }
 
       if (this._storeFile)
       {
         if (StringUtils.isEmpty(this._remoteFile)) {
           this._remoteFile = this._localFile;
         }
 
       }
       else if (StringUtils.isEmpty(this._localFile)) {
         this._localFile = this._remoteFile;
       }
 
       if (StringUtils.isEmpty(this._localDir)) {
         localFile = this._localFile;
       }
       else if (this._localDir.endsWith(SystemUtils.FILE_SEPARATOR))
         localFile = this._localDir + this._localFile;
       else {
         localFile = this._localDir + SystemUtils.FILE_SEPARATOR + this._localFile;
       }
 
       if (StringUtils.isEmpty(this._remoteDir)) {
         remoteFile = this._remoteFile;
       }
       else if (this._remoteDir.endsWith(SystemUtils.FILE_SEPARATOR))
         remoteFile = this._remoteDir + this._remoteFile;
       else {
         remoteFile = this._remoteDir + SystemUtils.FILE_SEPARATOR + this._remoteFile;
       }
 
       if (this._storeFile)
       {
         input = new FileInputStream(localFile);
 
         if (!(this._ftp.storeFile(remoteFile, input))) {
           input.close();
           throw new HiException("241190", remoteFile + ":" + this._ftp.getReplyCode() + ":" + this._ftp.getReplyString());
         }
 
         input.close();
       } else {
         output = new FileOutputStream(localFile);
 
         if (!(this._ftp.retrieveFile(remoteFile, output))) {
           output.close();
           throw new HiException("241190", remoteFile + ":" + this._ftp.getReplyCode() + ":" + this._ftp.getReplyString());
         }
 
         output.close();
       }
       this._ftp.logout();
     } catch (SocketException e) {
     }
     catch (IOException e) {
     }
     finally {
       if (this._ftp.isConnected())
         try {
           this._ftp.disconnect();
         }
         catch (IOException f)
         {
         }
       if (input != null)
         try {
           input.close();
         }
         catch (IOException e)
         {
         }
       if (output != null)
         try {
           output.close();
         }
         catch (IOException e)
         {
         }
     }
   }
 
   public void put() throws HiException
   {
     this._storeFile = true;
     process();
   }
 
   public String getServer() {
     return this._server;
   }
 
   public void setServer(String _server) {
     this._server = _server;
   }
 
   public String getUsername() {
     return this._username;
   }
 
   public void setUsername(String _username) {
     this._username = _username;
   }
 
   public String getPassword() {
     return this._password;
   }
 
   public void setPassword(String _password) {
     this._password = _password;
   }
 
   public boolean isBinaryTransfer() {
     return this._binaryTransfer;
   }
 
   public void setBinaryTransfer(boolean binaryTransfer) {
     this._binaryTransfer = binaryTransfer;
   }
 
   public boolean isStoreFile() {
     return this._storeFile;
   }
 
   public void setStoreFile(boolean storeFile) {
     this._storeFile = storeFile;
   }
 
   public String getLocalFile() {
     return this._localFile;
   }
 
   public void setLocalFile(String localFile) {
     this._localFile = localFile;
   }
 
   public String getLocalDir() {
     return this._localDir;
   }
 
   public void setLocalDir(String localDir) {
     this._localDir = localDir;
   }
 
   public String getRemoteDir() {
     return this._remoteDir;
   }
 
   public void setRemoteDir(String dir) {
     this._remoteDir = dir;
   }
 
   public String getRemoteFile() {
     return this._remoteFile;
   }
 
   public void setRemoteFile(String file) {
     this._remoteFile = file;
   }
 
   public String toString() {
     StringBuffer result = new StringBuffer();
     result.append(";server=");
     result.append(this._server);
     result.append(";username=");
     result.append(this._username);
     result.append(";password=");
     result.append(this._password);
     result.append(";remoteDir=");
     result.append(this._remoteDir);
     result.append(";remoteFile=");
     result.append(this._remoteFile);
     result.append(";localDir=");
     result.append(this._localDir);
     result.append(";localFile=");
     result.append(this._localFile);
     return result.toString();
   }
 
   public int getPort() {
     return this._port;
   }
 
   public void setPort(int _port) {
     this._port = _port;
   }
 
   public String getParam() {
     return this._param;
   }
 
   public void setParam(String _param) {
     this._param = _param;
   }
 }