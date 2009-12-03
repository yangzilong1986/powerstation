/*     */ package com.hisun.web.servlet;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.fileupload.FileItem;
/*     */ import org.apache.commons.fileupload.disk.DiskFileItemFactory;
/*     */ import org.apache.commons.fileupload.servlet.ServletFileUpload;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class FileUploadServlet extends HttpServlet
/*     */ {
/*     */   private String uploadPath;
/*     */   private String tempPath;
/*     */   private int maxFileSize;
/*     */ 
/*     */   public void destroy()
/*     */   {
/*  60 */     super.destroy();
/*     */   }
/*     */ 
/*     */   public void doGet(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  80 */     doPost(request, response);
/*     */   }
/*     */ 
/*     */   public void doPost(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/* 101 */     DiskFileItemFactory factory = new DiskFileItemFactory();
/* 102 */     factory.setSizeThreshold(4096);
/* 103 */     ServletFileUpload upload = new ServletFileUpload(factory);
/* 104 */     upload.setFileSizeMax(this.maxFileSize * 1024 * 1024);
/* 105 */     upload.setHeaderEncoding("GBK");
/*     */     try
/*     */     {
/* 109 */       List fileItems = upload.parseRequest(request);
/* 110 */       Iterator iter = fileItems.iterator();
/*     */ 
/* 112 */       while (iter.hasNext()) {
/* 113 */         FileItem item = (FileItem)iter.next();
/* 114 */         if (item.isFormField()) {
/*     */           continue;
/*     */         }
/* 117 */         String orgName = item.getName();
/* 118 */         String fldName = item.getFieldName();
/*     */ 
/* 120 */         if (StringUtils.isBlank(orgName)) {
/*     */           continue;
/*     */         }
/*     */ 
/* 124 */         int idx = orgName.lastIndexOf(92);
/* 125 */         if (idx != -1) {
/* 126 */           orgName = orgName.substring(idx + 1);
/*     */         }
/*     */ 
/* 129 */         File f1 = new File(request.getSession().getServletContext().getRealPath(this.uploadPath + orgName));
/*     */ 
/* 132 */         item.write(f1);
/*     */       }
/*     */     } catch (Exception e) {
/* 135 */       e.printStackTrace();
/* 136 */       throw new ServletException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void init()
/*     */     throws ServletException
/*     */   {
/* 147 */     this.uploadPath = getServletConfig().getInitParameter("UpLoadPath");
/* 148 */     if (!(this.uploadPath.endsWith("/"))) {
/* 149 */       this.uploadPath += "/";
/*     */     }
/*     */ 
/* 152 */     this.tempPath = getServletConfig().getInitParameter("TempPath");
/* 153 */     if (!(this.tempPath.endsWith("/"))) {
/* 154 */       this.tempPath += "/";
/*     */     }
/* 156 */     this.maxFileSize = NumberUtils.toInt(getServletConfig().getInitParameter("MaxFileSize"));
/*     */ 
/* 158 */     if (!(new File(this.uploadPath).isDirectory())) {
/* 159 */       new File(this.uploadPath).mkdirs();
/*     */     }
/* 161 */     if (!(new File(this.tempPath).isDirectory()))
/* 162 */       new File(this.tempPath).mkdirs();
/*     */   }
/*     */ }