/*     */ package com.hisun.atc.rpt;
/*     */ 
/*     */ import com.hisun.atc.rpt.data.BufferDataFileReader;
/*     */ import com.hisun.atc.rpt.data.OutputDataFileWriter;
/*     */ import com.hisun.atc.rpt.data.RecordReader;
/*     */ import com.hisun.atc.rpt.data.RecordWriter;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class HiDataFile
/*     */   implements HiReportConstants
/*     */ {
/*     */   private final String path;
/*     */   private final String aDeli;
/*     */   private final String sDeli;
/*     */   private Logger log;
/*     */ 
/*     */   HiDataFile(String path, String deli, Logger log)
/*     */   {
/*  32 */     this.path = path;
/*  33 */     this.aDeli = deli;
/*  34 */     this.sDeli = convertDeli(deli);
/*  35 */     this.log = log;
/*     */   }
/*     */ 
/*     */   private String convertDeli(String deli) {
/*  39 */     String sDeli = deli;
/*     */ 
/*  41 */     if ((deli.equals("|")) || (deli.equals(".")) || (deli.equals("*")))
/*  42 */       sDeli = "\\" + deli;
/*  43 */     return sDeli;
/*     */   }
/*     */ 
/*     */   private static OutputStream open(String path)
/*     */   {
/*     */     try
/*     */     {
/*  52 */       return new FileOutputStream(path);
/*     */     }
/*     */     catch (FileNotFoundException e) {
/*  55 */       throw new HiReportRuntimeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static OutputStream openAppend(String path)
/*     */   {
/*     */     try
/*     */     {
/*  65 */       return new FileOutputStream(path, true);
/*     */     }
/*     */     catch (FileNotFoundException e) {
/*  68 */       throw new HiReportRuntimeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private BufferedReader startRead() {
/*     */     try {
/*  74 */       return new BufferedReader(new InputStreamReader(new FileInputStream(this.path)));
/*     */     }
/*     */     catch (FileNotFoundException e) {
/*  77 */       this.log.error("can not open file:" + this.path, e);
/*  78 */       throw new HiReportRuntimeException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getPath() {
/*  83 */     return this.path;
/*     */   }
/*     */ 
/*     */   public void delete() {
/*  87 */     boolean b = new File(this.path).delete();
/*     */ 
/*  89 */     this.log.info("delete file:" + this.path + " " + b);
/*     */   }
/*     */ 
/*     */   public RecordWriter getWriter() {
/*  93 */     OutputStream out = open(this.path);
/*  94 */     return new OutputDataFileWriter(out, this.aDeli, this.log);
/*     */   }
/*     */ 
/*     */   public RecordWriter getAppendWriter() {
/*  98 */     if (new File(this.path).length() == 0L) {
/*  99 */       return getWriter();
/*     */     }
/* 101 */     OutputStream out = openAppend(this.path);
/*     */     try
/*     */     {
/* 104 */       out.write(10);
/*     */     } catch (IOException e) {
/*     */     }
/* 107 */     return new OutputDataFileWriter(out, this.aDeli, this.log);
/*     */   }
/*     */ 
/*     */   public RecordReader getReader() {
/* 111 */     BufferedReader in = startRead();
/* 112 */     return new BufferDataFileReader(in, this.log, this.sDeli);
/*     */   }
/*     */ }