/*    */ package com.hisun.hilog4j;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.File;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class HiDirectLogCache
/*    */   implements ILogCache
/*    */ {
/* 15 */   private BufferedWriter bos = null;
/*    */   private int limitsSize;
/*    */   private String currentFileName;
/*    */ 
/*    */   public HiDirectLogCache(int limitsSize)
/*    */   {
/* 20 */     this.limitsSize = limitsSize;
/*    */   }
/*    */ 
/*    */   public void put(HiLogInfo info) throws IOException {
/* 24 */     log(info);
/*    */   }
/*    */ 
/*    */   protected synchronized void log(HiLogInfo info) throws IOException {
/* 28 */     BufferedWriter bos = open(info);
/* 29 */     bos.write(info.getBuf().toString());
/* 30 */     bos.flush();
/*    */   }
/*    */ 
/*    */   public BufferedWriter open(HiLogInfo info) throws IOException {
/* 34 */     String name = info.getName().get();
/* 35 */     if (!(name.equals(this.currentFileName))) {
/* 36 */       if (this.bos != null) {
/* 37 */         this.bos.close();
/* 38 */         this.bos = null;
/*    */       }
/* 40 */       this.currentFileName = name;
/*    */     }
/*    */ 
/* 43 */     File f1 = new File(name);
/* 44 */     if (f1.getParent() != null) {
/* 45 */       File f2 = new File(f1.getParent());
/* 46 */       if (!(f2.exists())) {
/* 47 */         f2.mkdirs();
/*    */       }
/* 49 */       f2 = null;
/*    */     }
/*    */ 
/* 52 */     if ((!(f1.exists())) && 
/* 53 */       (this.bos != null)) {
/* 54 */       this.bos.close();
/* 55 */       this.bos = null;
/*    */     }
/*    */ 
/* 59 */     long l = f1.length();
/* 60 */     if ((info.getName().isFixedSizeable()) && (l > this.limitsSize)) {
/* 61 */       if (this.bos != null) {
/* 62 */         this.bos.close();
/* 63 */         this.bos = null;
/*    */       }
/* 65 */       SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
/* 66 */       File f2 = new File(name + "." + df.format(new Date()));
/* 67 */       f1.renameTo(f2);
/*    */     }
/*    */ 
/* 70 */     f1 = null;
/* 71 */     if (this.bos == null) {
/* 72 */       this.bos = new BufferedWriter(new FileWriter(name, true));
/*    */     }
/* 74 */     return this.bos; }
/*    */ 
/*    */   public void clear() { }
/*    */ 
/*    */   public void flush() throws IOException {
/*    */   }
/*    */ 
/*    */   public synchronized void close() throws IOException {
/* 82 */     if (this.bos == null) {
/* 83 */       return;
/*    */     }
/* 85 */     this.bos.close();
/* 86 */     this.bos = null;
/*    */   }
/*    */ }