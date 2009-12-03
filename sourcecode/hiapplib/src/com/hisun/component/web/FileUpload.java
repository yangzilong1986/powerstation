/*     */ package com.hisun.component.web;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.io.File;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.servlet.ServletContext;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.apache.commons.fileupload.FileItem;
/*     */ import org.apache.commons.fileupload.disk.DiskFileItemFactory;
/*     */ import org.apache.commons.fileupload.servlet.ServletFileUpload;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class FileUpload
/*     */ {
/*     */   public int execute(HiATLParam args, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  34 */     int maxPostSize = args.getInt("MaxSize");
/*  35 */     if (maxPostSize == 0) {
/*  36 */       maxPostSize = 102400;
/*     */     }
/*  38 */     maxPostSize *= 1024;
/*  39 */     String uploadPath = args.get("UpLoadPath");
/*  40 */     if (StringUtils.isBlank(uploadPath)) {
/*  41 */       uploadPath = "upload/";
/*     */     }
/*  43 */     HiMessage msg = ctx.getCurrentMsg();
/*  44 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/*  46 */     HttpServletRequest request = (HttpServletRequest)msg.getObjectHeadItem("_WEB_REQUEST");
/*     */ 
/*  48 */     String path = request.getSession().getServletContext().getRealPath(uploadPath);
/*  49 */     File f = new File(path);
/*     */ 
/*  51 */     if (!(f.exists())) {
/*  52 */       f.mkdirs();
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  58 */       DiskFileItemFactory factory = new DiskFileItemFactory();
/*  59 */       factory.setSizeThreshold(4096);
/*  60 */       ServletFileUpload upload = new ServletFileUpload(factory);
/*  61 */       upload.setSizeMax(maxPostSize);
/*  62 */       upload.setHeaderEncoding("GBK");
/*     */ 
/*  64 */       List fileItems = upload.parseRequest(request);
/*  65 */       Iterator iter = fileItems.iterator();
/*     */ 
/*  67 */       HiETF root = msg.getETFBody();
/*  68 */       int j = 1;
/*     */ 
/*  70 */       boolean overFlg = false;
/*  71 */       while (iter.hasNext()) {
/*  72 */         FileItem item = (FileItem)iter.next();
/*     */ 
/*  74 */         if (item.isFormField()) {
/*  75 */           if (StringUtils.equalsIgnoreCase(item.getFieldName(), "UPLOAD_PATH")) {
/*  76 */             uploadPath = item.getString();
/*  77 */             path = request.getSession().getServletContext().getRealPath(uploadPath);
/*  78 */             f = new File(path);
/*  79 */             if (!(f.exists())) {
/*  80 */               f.mkdirs();
/*     */             }
/*     */           }
/*  83 */           if (StringUtils.equalsIgnoreCase(item.getFieldName(), "OVR_FLG")) {
/*  84 */             overFlg = true;
/*     */           }
/*  86 */           setValue(root, item.getFieldName(), item.getString());
/*     */         }
/*     */ 
/*  89 */         if (!(uploadPath.endsWith("/"))) {
/*  90 */           uploadPath = uploadPath + "/";
/*     */         }
/*     */ 
/*  93 */         String orgName = item.getName();
/*  94 */         String fldName = item.getFieldName();
/*     */ 
/*  96 */         if (StringUtils.isBlank(orgName)) {
/*     */           continue;
/*     */         }
/*     */ 
/* 100 */         int idx = orgName.lastIndexOf(92);
/* 101 */         if (idx != -1) {
/* 102 */           orgName = orgName.substring(idx + 1);
/*     */         }
/* 104 */         idx = fldName.indexOf(46);
/* 105 */         String name = null;
/* 106 */         if (idx != -1) {
/* 107 */           name = fldName.substring(idx + 1);
/* 108 */           fldName = fldName.substring(0, idx);
/* 109 */           setValue(root, fldName, orgName);
/*     */         } else {
/* 111 */           if (!(overFlg)) {
/* 112 */             SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
/* 113 */             name = orgName + "." + df.format(new Date());
/*     */           } else {
/* 115 */             name = orgName;
/*     */           }
/* 117 */           setValue(root, fldName, orgName);
/*     */         }
/*     */ 
/* 120 */         File f1 = new File(request.getSession().getServletContext().getRealPath(uploadPath + name));
/*     */ 
/* 123 */         log.info(f1);
/*     */ 
/* 125 */         item.write(f1);
/* 126 */         ++j;
/* 127 */         setValue(root, fldName + "_NEW", uploadPath + name);
/*     */       }
/*     */     } catch (Exception e) {
/* 130 */       throw new HiException(e);
/*     */     }
/*     */ 
/* 133 */     return 0;
/*     */   }
/*     */ 
/*     */   private void setValue(HiETF root, String name, String value) {
/* 137 */     if (!(StringUtils.isBlank(value)))
/* 138 */       root.setChildValue(name, value);
/*     */   }
/*     */ }