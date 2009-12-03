/*     */ package com.hisun.web.action.other;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.web.action.BaseAction;
/*     */ import com.hisun.web.service.HiLogFactory;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Random;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import org.apache.struts2.ServletActionContext;
/*     */ 
/*     */ public class FileUploadAction extends BaseAction
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private static final int BUFFER_SIZE = 16384;
/*     */   private File doc;
/*     */   private String fileName;
/*     */   private String contentType;
/*     */   private String tartgetFilePath;
/*     */ 
/*     */   public void setFile(File file)
/*     */   {
/*  40 */     this.doc = file;
/*     */   }
/*     */ 
/*     */   public void setFileFileName(String fileName) {
/*  44 */     this.fileName = fileName;
/*     */   }
/*     */ 
/*     */   public void setFileContentType(String contentType) {
/*  48 */     this.contentType = contentType;
/*     */   }
/*     */ 
/*     */   public String execute() throws Exception {
/*  52 */     Logger _log = this.logFactory.getLogger();
/*  53 */     HttpServletRequest request = ServletActionContext.getRequest();
/*     */ 
/*  55 */     String destpath = request.getParameter("destpath");
/*  56 */     String targetDirectory = destpath;
/*  57 */     String targetFileName = this.fileName;
/*     */ 
/*  59 */     this.tartgetFilePath = destpath + targetFileName;
/*  60 */     File target = new File(targetDirectory, targetFileName);
/*     */ 
/*  62 */     copyFile(this.doc, target);
/*     */ 
/*  67 */     return super.execute();
/*     */   }
/*     */ 
/*     */   private void copyFile(File src, File dst) {
/*     */     try {
/*  72 */       FileOutputStream fos = null;
/*  73 */       InputStream fis = new FileInputStream(src);
/*  74 */       fos = new FileOutputStream(dst);
/*  75 */       byte[] buffer = new byte[1024];
/*     */       while (true) {
/*  77 */         int length = fis.read(buffer);
/*  78 */         if (length < 0) {
/*     */           break;
/*     */         }
/*  81 */         fos.write(buffer, 0, length);
/*     */       }
/*  83 */       fis.close();
/*  84 */       fos.close();
/*     */     }
/*     */     catch (Exception e) {
/*  87 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   private String generateFileName(String fileName) {
/*  92 */     DateFormat format = new SimpleDateFormat("yyMMddHHmmss");
/*  93 */     String formatDate = format.format(new Date());
/*     */ 
/*  95 */     int random = new Random().nextInt(10000);
/*     */ 
/*  97 */     int position = fileName.lastIndexOf(".");
/*  98 */     String extension = fileName.substring(position);
/*     */ 
/* 100 */     return formatDate + random + extension;
/*     */   }
/*     */ 
/*     */   protected HiETF beforeProcess(HttpServletRequest request, Logger _log)
/*     */     throws HiException
/*     */   {
/* 106 */     HiETF etf = super.beforeProcess(request, _log);
/* 107 */     if (etf == null) {
/* 108 */       etf = HiETFFactory.createETF();
/*     */     }
/*     */ 
/* 111 */     etf.setChildValue("FIL_PATH", this.tartgetFilePath);
/* 112 */     return etf;
/*     */   }
/*     */ }