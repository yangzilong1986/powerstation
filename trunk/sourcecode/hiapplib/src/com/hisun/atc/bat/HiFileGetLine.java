/*     */ package com.hisun.atc.bat;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ 
/*     */ public class HiFileGetLine
/*     */ {
/*     */   int record;
/*  18 */   File file = null;
/*     */ 
/*  20 */   FileReader fr = null;
/*     */ 
/*  22 */   BufferedReader reader = null;
/*     */ 
/*  49 */   private String strLine = "";
/*     */ 
/*  51 */   private String strBackLine = null;
/*     */ 
/*     */   HiFileGetLine(File file, int record)
/*     */   {
/*  11 */     this.file = file;
/*  12 */     this.record = record;
/*  13 */     init();
/*     */   }
/*     */ 
/*     */   void init()
/*     */   {
/*     */     try
/*     */     {
/*  28 */       this.fr = new FileReader(this.file);
/*  29 */       this.reader = new BufferedReader(this.fr);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   void close()
/*     */   {
/*     */     try
/*     */     {
/*  41 */       this.reader.close();
/*  42 */       this.fr.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public String readLine()
/*     */   {
/*     */     try
/*     */     {
/*  57 */       if (this.strBackLine == null)
/*     */       {
/*  59 */         if (this.strLine == null)
/*     */           break label231;
/*  61 */         this.strLine = this.reader.readLine();
/*  62 */         if (this.strLine == null) {
/*  63 */           return null;
/*     */         }
/*  65 */         b = this.strLine.getBytes();
/*  66 */         lineLength = b.length;
/*  67 */         if (this.record >= lineLength)
/*     */         {
/*  69 */           return this.strLine;
/*     */         }
/*     */ 
/*  73 */         newby = new byte[this.record];
/*  74 */         System.arraycopy(b, 0, newby, 0, this.record);
/*  75 */         strNewLine = new String(newby);
/*     */ 
/*  78 */         backby = new byte[lineLength - this.record];
/*  79 */         System.arraycopy(b, this.record, backby, 0, lineLength - this.record);
/*  80 */         this.strBackLine = new String(backby);
/*     */ 
/*  83 */         return strNewLine;
/*     */       }
/*     */ 
/*  89 */       byte[] b = this.strBackLine.getBytes();
/*  90 */       int lineLength = b.length;
/*  91 */       if (this.record >= lineLength)
/*     */       {
/*  93 */         String strLine = this.strBackLine;
/*  94 */         this.strBackLine = null;
/*  95 */         return strLine;
/*     */       }
/*     */ 
/*  99 */       byte[] newby = new byte[this.record];
/* 100 */       System.arraycopy(b, 0, newby, 0, this.record);
/* 101 */       String strNewLine = new String(newby);
/*     */ 
/* 104 */       byte[] backby = new byte[lineLength - this.record];
/* 105 */       System.arraycopy(b, this.record, backby, 0, lineLength - this.record);
/* 106 */       this.strBackLine = new String(backby);
/*     */ 
/* 109 */       label231: return strNewLine;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 115 */       e.printStackTrace();
/*     */     }
/* 117 */     return null;
/*     */   }
/*     */ }