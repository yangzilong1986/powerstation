/*     */ package com.hisun.web.action.other;
/*     */ 
/*     */ import com.opensymphony.xwork2.ActionSupport;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import javax.servlet.ServletContext;
/*     */ import org.apache.struts2.ServletActionContext;
/*     */ import sun.net.TelnetInputStream;
/*     */ import sun.net.ftp.FtpClient;
/*     */ 
/*     */ public class DownloadAction extends ActionSupport
/*     */ {
/*     */   private String serverIP;
/*     */   private String strUsername;
/*     */   private String strPassword;
/*     */   private String strServPath;
/*     */   private String localFileName;
/*     */   private String prefilename;
/*     */   private String date1;
/*     */ 
/*     */   public DownloadAction()
/*     */   {
/*  17 */     this.serverIP = "";
/*  18 */     this.strUsername = "";
/*  19 */     this.strPassword = "";
/*  20 */     this.strServPath = "";
/*     */ 
/*  22 */     this.prefilename = "";
/*     */ 
/*  24 */     this.date1 = null; }
/*     */ 
/*     */   public void setDate1(String dwDate) { this.date1 = dwDate;
/*     */   }
/*     */ 
/*     */   public String getDate1() {
/*  30 */     return this.date1;
/*     */   }
/*     */ 
/*     */   public void setServerIP(String serverIP) {
/*  34 */     this.serverIP = serverIP;
/*     */   }
/*     */ 
/*     */   public void setStrUsername(String strUsername) {
/*  38 */     this.strUsername = strUsername;
/*     */   }
/*     */ 
/*     */   public void setStrPassword(String strPassword) {
/*  42 */     this.strPassword = strPassword;
/*     */   }
/*     */ 
/*     */   public void setStrServPath(String strServPath) {
/*  46 */     this.strServPath = strServPath;
/*     */   }
/*     */ 
/*     */   public void setLocalFileName(String localFileName) {
/*  50 */     this.localFileName = localFileName;
/*     */   }
/*     */ 
/*     */   public void setPrefilename(String prefilename) {
/*  54 */     this.prefilename = prefilename;
/*     */   }
/*     */ 
/*     */   public InputStream getInputStream() throws Exception {
/*  58 */     return ServletActionContext.getServletContext().getResourceAsStream("/conf/" + this.prefilename + ".tar");
/*     */   }
/*     */ 
/*     */   public String execute() throws Exception
/*     */   {
/*  63 */     String filename = this.prefilename + "_" + this.date1 + ".tar";
/*  64 */     this.localFileName = ServletActionContext.getServletContext().getRealPath("/conf/" + this.prefilename + ".tar");
/*     */ 
/*  66 */     System.out.println("filename : " + filename);
/*  67 */     System.out.println("localFileName : " + this.localFileName);
/*     */     try
/*     */     {
/*  75 */       FtpClient ftpClient = new FtpClient();
/*     */ 
/*  78 */       ftpClient.openServer(this.serverIP);
/*     */ 
/*  80 */       ftpClient.login(this.strUsername, this.strPassword);
/*     */ 
/*  82 */       if (this.strServPath.length() != 0) {
/*  83 */         ftpClient.cd(this.strServPath);
/*     */       }
/*  85 */       ftpClient.binary();
/*     */ 
/*  87 */       TelnetInputStream tis = ftpClient.get(filename);
/*  88 */       File fileBuffer = new File(this.localFileName);
/*  89 */       FileOutputStream fos = new FileOutputStream(fileBuffer);
/*  90 */       byte[] bytes = new byte[1024];
/*     */ 
/*  92 */       while ((c = tis.read(bytes)) != -1)
/*     */       {
/*     */         int c;
/*  94 */         fos.write(bytes, 0, c);
/*     */       }
/*  96 */       tis.close();
/*  97 */       fos.close();
/*  98 */       ftpClient.closeServer();
/*     */ 
/* 101 */       ftpClient.closeServer();
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 106 */       ex.printStackTrace();
/* 107 */       return "";
/*     */     }
/*     */ 
/* 114 */     return "success";
/*     */   }
/*     */ }