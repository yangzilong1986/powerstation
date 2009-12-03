/*     */ package com.hisun.atc.rpt.data;
/*     */ 
/*     */ import com.hisun.atc.rpt.HiDataRecord;
/*     */ 
/*     */ public class RecordReaders
/*     */ {
/*     */   public static RecordReader append(RecordReader r1, RecordReader r2)
/*     */   {
/*   9 */     return new RecordReader(r1, r2) { private RecordReader curReader = this.val$r1;
/*     */       private final RecordReader val$r1;
/*     */       private final RecordReader val$r2;
/*     */ 
/*     */       public void close() { this.val$r1.close();
/*  14 */         this.val$r2.close();
/*     */       }
/*     */ 
/*     */       public HiDataRecord readRecord() {
/*  18 */         HiDataRecord rec = this.curReader.readRecord();
/*  19 */         if ((rec == null) && (this.curReader == this.val$r1)) {
/*  20 */           this.val$r1.close();
/*  21 */           this.curReader = this.val$r2;
/*  22 */           rec = readRecord();
/*     */         }
/*  24 */         return rec;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static RecordReader filter(RecordReader r, Matcher m)
/*     */   {
/*  70 */     return new RecordReader(r, m) { private final RecordReader val$r;
/*     */       private final Matcher val$m;
/*     */ 
/*     */       public void close() { this.val$r.close();
/*     */       }
/*     */ 
/*     */       public HiDataRecord readRecord() {
/*  77 */         HiDataRecord rec = this.val$r.readRecord();
/*  78 */         while ((rec != null) && (!(this.val$m.match(rec))))
/*  79 */           rec = this.val$r.readRecord();
/*  80 */         return rec;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static RecordReader takeWhile(RecordReader r, Matcher m)
/*     */   {
/*  88 */     return new RecordReader(r, m) { boolean isEnd = false;
/*     */       private final RecordReader val$r;
/*     */       private final Matcher val$m;
/*     */ 
/*     */       public void close() { this.val$r.close();
/*     */       }
/*     */ 
/*     */       public HiDataRecord readRecord() {
/*  96 */         if (this.isEnd) {
/*  97 */           return null;
/*     */         }
/*  99 */         HiDataRecord rec = this.val$r.readRecord();
/* 100 */         if ((rec == null) || (!(this.val$m.match(rec)))) {
/* 101 */           this.isEnd = true;
/* 102 */           rec = null;
/*     */         }
/*     */ 
/* 105 */         return rec;
/*     */       }
/*     */     };
/*     */   }
/*     */ }