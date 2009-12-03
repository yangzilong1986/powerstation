/*     */ package com.hisun.hilog4j;
/*     */ 
/*     */ import com.hisun.util.HiObjectPoolUtils;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class HiBufferedLogCache
/*     */   implements ILogCache
/*     */ {
/*  16 */   private StringBuilder logBuf = new StringBuilder(81920);
/*  17 */   private FileWriter bos = null;
/*     */   private int limitsSize;
/*     */   private String currentFileName;
/*     */   private IFileName fileName;
/*     */ 
/*     */   public HiBufferedLogCache(int limitsSize, IFileName fileName)
/*     */   {
/*  23 */     this.limitsSize = limitsSize;
/*  24 */     this.fileName = fileName;
/*     */   }
/*     */ 
/*     */   public synchronized void put(HiLogInfo info) throws IOException {
/*  28 */     open(info);
/*  29 */     StringBuilder buf = info.getBuf();
/*  30 */     if (this.logBuf.capacity() < buf.length())
/*     */     {
/*  32 */       this.bos.write(this.logBuf.toString());
/*  33 */       this.logBuf.setLength(0);
/*  34 */       this.bos.write(buf.toString());
/*  35 */       HiObjectPoolUtils.getInstance().returnStringBuilder(info.getBuf());
/*  36 */       return;
/*     */     }
/*  38 */     if (this.logBuf.capacity() - this.logBuf.length() < buf.length())
/*     */     {
/*  40 */       this.bos.write(this.logBuf.toString());
/*  41 */       this.logBuf.setLength(0);
/*  42 */       return;
/*     */     }
/*  44 */     this.logBuf.append(buf.toString());
/*  45 */     HiObjectPoolUtils.getInstance().returnStringBuilder(info.getBuf());
/*     */   }
/*     */ 
/*     */   public synchronized FileWriter open(HiLogInfo info) throws IOException {
/*  49 */     String name = this.fileName.get();
/*  50 */     if (!(name.equals(this.currentFileName))) {
/*  51 */       if (this.bos != null) {
/*  52 */         this.bos.close();
/*  53 */         this.bos = null;
/*     */       }
/*  55 */       this.currentFileName = name;
/*     */     }
/*     */ 
/*  58 */     File f1 = new File(name);
/*  59 */     if (f1.getParent() != null) {
/*  60 */       File f2 = new File(f1.getParent());
/*  61 */       if (!(f2.exists())) {
/*  62 */         f2.mkdirs();
/*     */       }
/*  64 */       f2 = null;
/*     */     }
/*     */ 
/*  67 */     if ((!(f1.exists())) && 
/*  68 */       (this.bos != null)) {
/*  69 */       this.bos.close();
/*  70 */       this.bos = null;
/*     */     }
/*     */ 
/*  74 */     long l = f1.length();
/*  75 */     if (l > this.limitsSize) {
/*  76 */       if (this.bos != null) {
/*  77 */         this.bos.close();
/*  78 */         this.bos = null;
/*     */       }
/*  80 */       SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
/*  81 */       File f2 = new File(name + "." + df.format(new Date()));
/*  82 */       f1.renameTo(f2);
/*     */     }
/*     */ 
/*  85 */     f1 = null;
/*  86 */     if (this.bos == null) {
/*  87 */       this.bos = new FileWriter(name, true);
/*     */     }
/*  89 */     return this.bos;
/*     */   }
/*     */ 
/*     */   public void flush() throws IOException
/*     */   {
/*  94 */     if (this.bos != null) {
/*  95 */       this.bos.write(this.logBuf.toString());
/*     */     }
/*  97 */     this.logBuf.setLength(0);
/*     */   }
/*     */ 
/*     */   public void clear() {
/* 101 */     File f = new File(this.fileName.get());
/* 102 */     if (f.exists()) {
/* 103 */       f.delete();
/*     */     }
/* 105 */     this.logBuf.setLength(0);
/*     */   }
/*     */ 
/*     */   public void close() throws IOException {
/* 109 */     flush();
/* 110 */     if (this.bos == null) {
/* 111 */       return;
/*     */     }
/* 113 */     this.bos.close();
/*     */   }
/*     */ }