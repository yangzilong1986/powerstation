/*    */ package com.hisun.encrypt;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class HiDirectoryUtil
/*    */ {
/*    */   public static void createDirectory(String dirname)
/*    */   {
/*  7 */     File dir = new File(dirname);
/*  8 */     if (!(dir.exists()))
/*  9 */       dir.mkdirs();
/*    */   }
/*    */ 
/*    */   public static void deleteDirectory(String dirname)
/*    */   {
/* 14 */     File dir = new File(dirname);
/*    */ 
/* 16 */     if (dir.isDirectory()) {
/* 17 */       File[] filelist = dir.listFiles();
/* 18 */       for (int i = 0; i < filelist.length; ++i) {
/* 19 */         File thisfile = filelist[i];
/* 20 */         if (thisfile.isDirectory())
/* 21 */           deleteDirectory(thisfile.getAbsolutePath());
/*    */         else {
/* 23 */           thisfile.delete();
/*    */         }
/*    */       }
/* 26 */       dir.delete();
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void copyFile(String srcFile, String destFile) {
/* 31 */     copyFile(new File(srcFile), new File(destFile));
/*    */   }
/*    */ 
/*    */   public static void copyFile(File srcFile, File destFile) {
/*    */     try {
/* 36 */       byte[] b = new byte[(int)srcFile.length()];
/* 37 */       FileInputStream fis = new FileInputStream(srcFile);
/* 38 */       fis.read(b);
/* 39 */       fis.close();
/*    */ 
/* 41 */       FileOutputStream fos = new FileOutputStream(destFile);
/* 42 */       fos.write(b);
/* 43 */       fos.close();
/*    */     } catch (Exception e) {
/* 45 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public static String loadFileContent(String filename) {
/* 50 */     String s = "";
/*    */     try
/*    */     {
/* 53 */       File file = new File(filename);
/* 54 */       if (file.exists()) {
/* 55 */         byte[] b = new byte[(int)file.length()];
/* 56 */         FileInputStream fis = new FileInputStream(file);
/* 57 */         fis.read(b);
/* 58 */         fis.close();
/* 59 */         s = new String(b);
/*    */       }
/*    */     } catch (Exception e) {
/* 62 */       e.printStackTrace();
/* 63 */       s = "";
/*    */     }
/* 65 */     return s;
/*    */   }
/*    */ 
/*    */   public static void writeFileContent(String filename, String content) {
/*    */     try {
/* 70 */       File file = new File(filename);
/* 71 */       FileOutputStream fos = new FileOutputStream(file);
/* 72 */       fos.write(content.getBytes());
/* 73 */       fos.close();
/*    */     } catch (Exception e) {
/* 75 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public String loadResourceFileContent(String filename) {
/* 80 */     String s = "";
/* 81 */     InputStream is = super.getClass().getResourceAsStream(filename);
/* 82 */     if (is != null) {
/*    */       try {
/* 84 */         byte[] b = new byte[is.available()];
/* 85 */         is.read(b);
/* 86 */         is.close();
/* 87 */         s = new String(b);
/*    */       } catch (Exception e) {
/* 89 */         e.printStackTrace();
/* 90 */         s = "";
/*    */       }
/*    */     }
/* 93 */     return s;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 97 */     deleteDirectory("d:/project/eclipse/tmp-extract");
/*    */   }
/*    */ }