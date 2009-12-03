/*     */ package com.hisun.atc.rpt.xml;
/*     */ 
/*     */ import com.hisun.atc.rpt.HiDataFile;
/*     */ import com.hisun.atc.rpt.HiDataRecord;
/*     */ import com.hisun.atc.rpt.HiReportConstants;
/*     */ import com.hisun.atc.rpt.HiRptContext;
/*     */ import com.hisun.atc.rpt.data.Appender;
/*     */ import com.hisun.atc.rpt.data.Appenders;
/*     */ import com.hisun.atc.rpt.data.RecordReader;
/*     */ import com.hisun.atc.rpt.data.RecordWriter;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ 
/*     */ public class HiFieldSummedNode extends HiSummedNode
/*     */   implements HiReportConstants
/*     */ {
/*     */   LinkedList sums;
/*     */   Appender typeAppender;
/*     */ 
/*     */   public HiFieldSummedNode()
/*     */   {
/*  20 */     this.sums = new LinkedList();
/*     */ 
/*  22 */     this.typeAppender = Appenders.type(5); }
/*     */ 
/*     */   public void addSumNode(HiSumNode sum) {
/*  25 */     sum.init();
/*  26 */     this.sums.add(sum);
/*     */   }
/*     */ 
/*     */   public void load(HiDataRecord rec)
/*     */   {
/*  34 */     Iterator it = this.sums.iterator();
/*  35 */     while (it.hasNext()) {
/*  36 */       HiSumNode sum = (HiSumNode)it.next();
/*  37 */       sum.load(rec);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void append(RecordWriter tmpfile)
/*     */   {
/*  43 */     this.typeAppender.append(tmpfile);
/*  44 */     Iterator it = this.sums.iterator();
/*  45 */     while (it.hasNext()) {
/*  46 */       HiSumNode sum = (HiSumNode)it.next();
/*  47 */       sum.append(tmpfile);
/*     */     }
/*     */   }
/*     */ 
/*     */   public HiDataFile process(HiRptContext ctx, HiDataFile datafile, HiDataFile tmpfile) {
/*  52 */     boolean bFlag = false;
/*  53 */     int lastType = -1;
/*     */ 
/*  56 */     RecordReader reader = datafile.getReader();
/*     */ 
/*  58 */     RecordWriter writer = tmpfile.getWriter();
/*     */ 
/*  60 */     while ((rec = reader.readRecord()) != null)
/*     */     {
/*     */       HiDataRecord rec;
/*  62 */       bFlag = true;
/*     */ 
/*  64 */       if (rec.type == 99)
/*     */         continue;
/*  66 */       if (rec.type == 3) {
/*  67 */         lastType = rec.type;
/*     */ 
/*  69 */         load(rec);
/*  70 */         writer.appendRecord(rec);
/*     */       }
/*  72 */       if ((rec.type < 5) && (lastType > rec.type)) {
/*  73 */         lastType = rec.type;
/*     */ 
/*  75 */         append(writer);
/*  76 */         writer.appendRecord(rec);
/*  77 */         bFlag = false;
/*     */       }
/*  79 */       if ((rec.type < 5) && (lastType <= rec.type)) {
/*  80 */         lastType = rec.type;
/*     */ 
/*  82 */         writer.appendRecord(rec);
/*     */       }
/*  84 */       if (rec.type >= 5);
/*  85 */       lastType = rec.type;
/*     */ 
/*  87 */       append(writer);
/*     */ 
/*  89 */       writer.appendRecord(rec);
/*     */ 
/*  91 */       bFlag = false;
/*     */     }
/*     */ 
/*  98 */     if (bFlag)
/*     */     {
/* 100 */       append(writer);
/*     */     }
/* 102 */     writer.close();
/* 103 */     reader.close();
/*     */ 
/* 105 */     return tmpfile;
/*     */   }
/*     */ }