/*     */ package com.hisun.hilog4j;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class HiIndexLogCache
/*     */   implements ILogCache
/*     */ {
/*  16 */   private static final IFileName TXN_DATA_FILE = new HiTrcFileName("TXN.dat");
/*  17 */   private static final IFileName TXN_INDEX_FILE = new HiTrcFileName("TXN.idx");
/*  18 */   private RandomAccessFile dataBos = null;
/*  19 */   private RandomAccessFile idxBos = null;
/*     */   private int limitsSize;
/*  21 */   private String currentIndexFileName = TXN_DATA_FILE.get();
/*  22 */   private String currentDataFileName = TXN_INDEX_FILE.get();
/*     */ 
/*     */   public HiIndexLogCache(int limitsSize) {
/*  25 */     this.limitsSize = limitsSize;
/*     */   }
/*     */ 
/*     */   public void put(HiLogInfo info) throws IOException {
/*  29 */     open();
/*  30 */     int len = info.getBuf().length();
/*  31 */     long offset = this.dataBos.length();
/*  32 */     this.idxBos.writeBytes(info.getName().name() + "|" + offset + "|" + this.dataBos.length() + "\n");
/*  33 */     this.dataBos.write(info.getBuf().toString().getBytes());
/*     */   }
/*     */ 
/*     */   public void flush() throws IOException {
/*  37 */     if (this.dataBos != null) {
/*  38 */       this.dataBos.close();
/*     */     }
/*  40 */     if (this.idxBos != null)
/*  41 */       this.idxBos.close();
/*     */   }
/*     */ 
/*     */   public synchronized void open() throws IOException
/*     */   {
/*  46 */     openData();
/*  47 */     openIndex();
/*     */   }
/*     */ 
/*     */   private void openData() throws IOException {
/*  51 */     String name = TXN_DATA_FILE.get();
/*  52 */     if (!(name.equals(this.currentDataFileName))) {
/*  53 */       if (this.dataBos != null) {
/*  54 */         this.dataBos.close();
/*  55 */         this.dataBos = null;
/*     */       }
/*  57 */       this.currentDataFileName = name;
/*     */     }
/*  59 */     File f1 = new File(name);
/*  60 */     if ((!(f1.exists())) && 
/*  61 */       (this.dataBos != null)) {
/*  62 */       this.dataBos.close();
/*  63 */       this.dataBos = null;
/*     */     }
/*     */ 
/*  67 */     if (f1.getParent() != null) {
/*  68 */       File f2 = new File(f1.getParent());
/*  69 */       if (!(f2.exists())) {
/*  70 */         f2.mkdirs();
/*     */       }
/*  72 */       f2 = null;
/*     */     }
/*     */ 
/*  75 */     long l = f1.length();
/*  76 */     if (l > this.limitsSize) {
/*  77 */       if (this.dataBos != null) {
/*  78 */         this.dataBos.close();
/*  79 */         this.dataBos = null;
/*     */       }
/*  81 */       SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
/*  82 */       File f2 = new File(name + "." + df.format(new Date()));
/*  83 */       f1.renameTo(f2);
/*     */     }
/*     */ 
/*  86 */     f1 = null;
/*  87 */     if (this.dataBos == null)
/*  88 */       this.dataBos = new RandomAccessFile(name, "rw");
/*     */   }
/*     */ 
/*     */   private void openIndex()
/*     */     throws IOException
/*     */   {
/*  94 */     String name = TXN_INDEX_FILE.get();
/*  95 */     if (!(name.equals(this.currentIndexFileName))) {
/*  96 */       if (this.idxBos != null) {
/*  97 */         this.idxBos.close();
/*  98 */         this.idxBos = null;
/*     */       }
/* 100 */       this.currentIndexFileName = name;
/*     */     }
/*     */ 
/* 103 */     File f1 = new File(name);
/* 104 */     if ((!(f1.exists())) && 
/* 105 */       (this.idxBos != null)) {
/* 106 */       this.idxBos.close();
/* 107 */       this.idxBos = null;
/*     */     }
/*     */ 
/* 110 */     if (f1.getParent() != null) {
/* 111 */       File f2 = new File(f1.getParent());
/* 112 */       if (!(f2.exists())) {
/* 113 */         f2.mkdirs();
/*     */       }
/* 115 */       f2 = null;
/*     */     }
/*     */ 
/* 118 */     if (this.idxBos == null)
/* 119 */       this.idxBos = new RandomAccessFile(name, "rw");
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*     */   }
/*     */ }