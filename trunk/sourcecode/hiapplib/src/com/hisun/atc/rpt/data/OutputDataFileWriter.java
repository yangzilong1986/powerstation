/*     */ package com.hisun.atc.rpt.data;
/*     */ 
/*     */ import com.hisun.atc.rpt.HiDataRecord;
/*     */ import com.hisun.atc.rpt.HiReportConstants;
/*     */ import com.hisun.atc.rpt.HiReportRuntimeException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class OutputDataFileWriter
/*     */   implements RecordWriter, HiReportConstants
/*     */ {
/*     */   private static final int BUF_SIZE = 256;
/*     */   private final OutputStream out;
/*     */   private final String aDeli;
/*     */   private final Logger log;
/*     */   private StringBuffer linebuf;
/*     */ 
/*     */   public OutputDataFileWriter(OutputStream out, String deli, Logger log)
/*     */   {
/*  24 */     this.out = out;
/*  25 */     this.aDeli = deli;
/*  26 */     this.log = log;
/*     */   }
/*     */ 
/*     */   public void appendRecord(HiDataRecord rec) {
/*  30 */     if (this.linebuf == null) {
/*  31 */       this.linebuf = new StringBuffer(256);
/*     */     } else {
/*  33 */       endRecord();
/*  34 */       this.linebuf.append("\n");
/*     */     }
/*  36 */     this.linebuf.append(rec.record);
/*     */     try
/*     */     {
/*  40 */       this.out.write(this.linebuf.toString().getBytes());
/*     */     }
/*     */     catch (IOException e) {
/*  43 */       throw new HiReportRuntimeException(e);
/*     */     }
/*     */ 
/*  48 */     this.linebuf.setLength(0);
/*     */   }
/*     */ 
/*     */   public void appendRecordValue(String name, String value) {
/*  52 */     this.linebuf.append(name).append("=").append(value);
/*  53 */     if (this.aDeli != null)
/*  54 */       this.linebuf.append(this.aDeli);
/*     */   }
/*     */ 
/*     */   private void endRecord()
/*     */   {
/*  60 */     if ((this.linebuf == null) || (this.linebuf.length() <= 0)) return;
/*     */     try {
/*  62 */       this.out.write(this.linebuf.toString().getBytes());
/*     */     } catch (IOException e) {
/*  64 */       e.printStackTrace();
/*  65 */       return;
/*     */     }
/*     */ 
/*  68 */     this.linebuf.delete(0, this.linebuf.length());
/*     */   }
/*     */ 
/*     */   public void appendSeq(int seq)
/*     */   {
/*  73 */     if (seq != 0)
/*  74 */       this.linebuf.append(seq).append(":");
/*     */   }
/*     */ 
/*     */   public int newRecord(int iType)
/*     */   {
/*  92 */     if (this.linebuf == null) {
/*  93 */       this.linebuf = new StringBuffer(256);
/*     */     } else {
/*  95 */       endRecord();
/*  96 */       this.linebuf.append("\n");
/*     */     }
/*  98 */     switch (iType)
/*     */     {
/*     */     case 0:
/* 100 */       this.linebuf.append("[0]");
/* 101 */       break;
/*     */     case 1:
/* 103 */       this.linebuf.append("[1]");
/* 104 */       break;
/*     */     case 2:
/* 106 */       this.linebuf.append("[2]");
/* 107 */       break;
/*     */     case 3:
/* 109 */       this.linebuf.append("[3]");
/* 110 */       break;
/*     */     case 4:
/* 112 */       this.linebuf.append("[4]");
/* 113 */       break;
/*     */     case 5:
/* 115 */       this.linebuf.append("[5]");
/* 116 */       break;
/*     */     case 6:
/* 118 */       this.linebuf.append("[6]");
/* 119 */       break;
/*     */     case 7:
/* 121 */       this.linebuf.append("[7]");
/* 122 */       break;
/*     */     case 8:
/* 124 */       this.linebuf.append("[8]");
/* 125 */       break;
/*     */     default:
/* 127 */       return -1;
/*     */     }
/*     */ 
/* 130 */     this.linebuf.append(":");
/*     */ 
/* 146 */     return 0;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/* 155 */     endRecord();
/*     */     try {
/* 157 */       this.out.close();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*     */     }
/*     */   }
/*     */ }