/*     */ package com.hisun.file;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ 
/*     */ public class HiFileUtil
/*     */ {
/*     */   private static void saveFile(String parent, String name, String data, boolean append)
/*     */     throws HiException
/*     */   {
/*  23 */     FileWriter fw = null;
/*     */     try
/*     */     {
/*  26 */       File f1 = new File(parent);
/*  27 */       if (!(f1.exists()))
/*     */       {
/*  29 */         f1.mkdirs();
/*     */       }
/*     */ 
/*  32 */       File f2 = new File(f1, name);
/*  33 */       f2.createNewFile();
/*  34 */       fw = new FileWriter(f2, append);
/*  35 */       fw.write(data);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/*  45 */         if (fw != null)
/*  46 */           fw.close();
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void saveFile(String name, String data)
/*     */     throws HiException
/*     */   {
/*  63 */     saveFile(null, name, data, false);
/*     */   }
/*     */ 
/*     */   public static void appendFile(String name, String data)
/*     */     throws HiException
/*     */   {
/*  76 */     saveFile(null, name, data, true);
/*     */   }
/*     */ 
/*     */   public static void saveTempFile(String name, String data)
/*     */     throws HiException
/*     */   {
/*  89 */     saveFile(HiICSProperty.getTmpDir(), name, data, false);
/*     */   }
/*     */ 
/*     */   public static void saveTempFile(URL url, String data)
/*     */     throws HiException
/*     */   {
/* 102 */     File f = new File(url.getFile());
/* 103 */     saveFile(HiICSProperty.getTmpDir(), f.getName(), data, false);
/*     */   }
/*     */ }