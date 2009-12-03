/*     */ package com.hisun.atc.rpt.data;
/*     */ 
/*     */ import com.hisun.atc.rpt.HiDataRecord;
/*     */ import com.hisun.atc.rpt.HiReportRuntimeException;
/*     */ import com.hisun.common.util.pattern.Pattern;
/*     */ import com.hisun.common.util.pattern.Patterns;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class BufferDataFileReader
/*     */   implements RecordReader
/*     */ {
/*     */   private BufferedReader in;
/*     */   private Logger log;
/*     */   private final String sDeli;
/*     */   private static final Pattern head;
/*     */ 
/*     */   public BufferDataFileReader(BufferedReader in, Logger log, String deli)
/*     */   {
/*  23 */     this.in = in;
/*  24 */     this.log = log;
/*  25 */     this.sDeli = deli;
/*     */   }
/*     */ 
/*     */   public void close() {
/*     */     try {
/*  30 */       this.in.close();
/*     */     }
/*     */     catch (IOException e) {
/*     */     }
/*  34 */     this.in = null;
/*     */   }
/*     */ 
/*     */   public HiDataRecord readRecord()
/*     */   {
/*     */     try
/*     */     {
/*  43 */       String str = this.in.readLine();
/*  44 */       if ((str != null) && (str.length() > 0))
/*  45 */         return parseRecord(str);
/*     */     }
/*     */     catch (IOException e) {
/*  48 */       throw new HiReportRuntimeException(e);
/*     */     }
/*  50 */     return null;
/*     */   }
/*     */ 
/*     */   private HiDataRecord parseRecord(String record) {
/*  54 */     HiDataRecord rec = new HiDataRecord();
/*  55 */     rec.record = record;
/*     */ 
/*  71 */     int headlen = getHeadLen(record);
/*  72 */     if (headlen == -1) {
/*  73 */       this.log.warn("记录解析失败:" + record);
/*  74 */       return null;
/*     */     }
/*     */ 
/*  77 */     if (headlen == 1) {
/*  78 */       rec.type = 99;
/*  79 */       return rec;
/*     */     }
/*     */ 
/*  82 */     rec.type = (record.charAt(1) - '0');
/*  83 */     rec.seq = 0;
/*  84 */     if (headlen == 6) {
/*  85 */       rec.seq = (record.charAt(4) - '0');
/*     */     }
/*     */ 
/*  88 */     String[] vars = record.substring(headlen).split(this.sDeli);
/*  89 */     for (int i = 0; i < vars.length; ++i) {
/*  90 */       String[] nv = vars[i].split("=");
/*  91 */       if (nv.length == 2)
/*     */       {
/*  93 */         rec.put(nv[0], nv[1]); } else {
/*  94 */         if (nv.length != 1)
/*     */           continue;
/*  96 */         rec.put(nv[0], " ");
/*     */       }
/*     */     }
/*  99 */     return rec;
/*     */   }
/*     */ 
/*     */   private static int getHeadLen(String rec)
/*     */   {
/* 120 */     return head.match(rec, rec.length(), 0);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 104 */     Pattern type = Patterns.seq(new Pattern[] { Patterns.isChar('['), Patterns.range('0', '9'), Patterns.isString("]:") });
/*     */ 
/* 109 */     Pattern seq = Patterns.range('0', '9').seq(Patterns.isChar(':'));
/* 110 */     Pattern head1 = type.seq(seq.optional());
/* 111 */     head = Patterns.or(head1, Patterns.isChar('#'));
/*     */   }
/*     */ }