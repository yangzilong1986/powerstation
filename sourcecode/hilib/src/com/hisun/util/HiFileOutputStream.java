/*    */ package com.hisun.util;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class HiFileOutputStream
/*    */ {
/* 17 */   private String file = null;
/*    */ 
/* 19 */   private FileOutputStream fos = null;
/*    */ 
/*    */   public HiFileOutputStream(String file)
/*    */     throws HiException
/*    */   {
/*    */     try
/*    */     {
/* 29 */       this.fos = new FileOutputStream(file);
/*    */     } catch (FileNotFoundException e) {
/* 31 */       throw new HiException("220079", file);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void write(byte[] buffer)
/*    */     throws HiException
/*    */   {
/*    */     try
/*    */     {
/* 43 */       this.fos.write(buffer);
/*    */     } catch (IOException e) {
/* 45 */       throw new HiException("220079", this.file);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void write(HiByteBuffer buffer)
/*    */     throws HiException
/*    */   {
/* 56 */     write(buffer.getBytes());
/*    */   }
/*    */ 
/*    */   public void close()
/*    */     throws HiException
/*    */   {
/*    */     try
/*    */     {
/* 66 */       this.fos.close();
/*    */     } catch (IOException e) {
/* 68 */       throw new HiException("220079", this.file);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void write(String file, HiByteBuffer buffer) throws HiException
/*    */   {
/* 74 */     HiFileOutputStream fos = null;
/*    */     try {
/* 76 */       fos = new HiFileOutputStream(file);
/* 77 */       fos.write(buffer);
/*    */     } finally {
/* 79 */       if (fos != null)
/* 80 */         fos.close();
/*    */     }
/*    */   }
/*    */ 
/*    */   protected void finalize() throws Throwable {
/* 85 */     super.finalize();
/* 86 */     if (this.fos != null)
/* 87 */       this.fos.close();
/*    */   }
/*    */ }