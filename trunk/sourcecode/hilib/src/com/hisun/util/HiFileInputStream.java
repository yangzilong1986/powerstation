/*    */ package com.hisun.util;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class HiFileInputStream
/*    */ {
/* 13 */   private FileInputStream fis = null;
/*    */ 
/* 15 */   private String file = null;
/*    */ 
/*    */   public HiFileInputStream(File f) throws HiException {
/* 18 */     this.file = f.getName();
/*    */     try {
/* 20 */       this.fis = new FileInputStream(f);
/*    */     } catch (FileNotFoundException e) {
/* 22 */       throw new HiException("212004", f.getName(), e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public HiFileInputStream(String file) throws HiException
/*    */   {
/* 28 */     this.file = file;
/*    */     try {
/* 30 */       this.fis = new FileInputStream(file);
/*    */     } catch (FileNotFoundException e) {
/* 32 */       throw new HiException("212004", file, e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public HiByteBuffer read(HiByteBuffer buffer) throws HiException {
/* 37 */     int len = 0;
/* 38 */     byte[] bs = new byte[1024];
/*    */     try {
/* 40 */       while ((len = this.fis.read(bs, 0, bs.length)) != -1)
/* 41 */         buffer.append(bs, 0, len);
/*    */     }
/*    */     catch (IOException e) {
/* 44 */       throw new HiException("220079", this.file, e);
/*    */     }
/* 46 */     return buffer;
/*    */   }
/*    */ 
/*    */   public void close() throws HiException {
/*    */     try {
/* 51 */       if (this.fis != null)
/* 52 */         this.fis.close();
/*    */     } catch (IOException e) {
/* 54 */       throw new HiException("220079", this.file, e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static HiByteBuffer read(String file, HiByteBuffer buffer) throws HiException {
/* 59 */     HiFileInputStream fis = null;
/*    */     try {
/* 61 */       fis = new HiFileInputStream(file);
/* 62 */       buffer = fis.read(buffer);
/*    */     } finally {
/* 64 */       if (fis != null)
/* 65 */         fis.close();
/*    */     }
/* 67 */     return buffer;
/*    */   }
/*    */ 
/*    */   protected void finalize() throws Throwable {
/* 71 */     super.finalize();
/* 72 */     close();
/*    */   }
/*    */ }