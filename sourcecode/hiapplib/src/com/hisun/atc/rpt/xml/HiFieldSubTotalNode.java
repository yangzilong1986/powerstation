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
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class HiFieldSubTotalNode extends HiSubTotalNode
/*     */   implements HiReportConstants
/*     */ {
/*     */   Map fields;
/*     */   String[] aItemValue;
/*     */   Appender typeAppender;
/*     */ 
/*     */   public HiFieldSubTotalNode()
/*     */   {
/*  21 */     this.fields = new HashMap();
/*     */ 
/*  25 */     this.typeAppender = Appenders.type(4); }
/*     */ 
/*     */   public void addField(HiFieldNode field) {
/*  28 */     this.fields.put(field.field, field);
/*     */   }
/*     */ 
/*     */   public void setGroupby(String groups)
/*     */   {
/*  33 */     super.setGroupby(groups);
/*  34 */     this.aItemValue = new String[getGroupNum()];
/*     */   }
/*     */ 
/*     */   public HiDataFile process(HiRptContext ctx, HiDataFile datafile, HiDataFile tmpfile)
/*     */   {
/*  39 */     boolean bfirstRec = true;
/*  40 */     int lastType = -1;
/*     */ 
/*  43 */     RecordReader reader = datafile.getReader();
/*     */ 
/*  45 */     RecordWriter writer = tmpfile.getWriter();
/*     */ 
/*  47 */     while ((rec = reader.readRecord()) != null)
/*     */     {
/*     */       HiDataRecord rec;
/*  48 */       if (rec.type == 99)
/*     */         continue;
/*  50 */       if ((rec.type < 3) && (lastType > rec.type)) {
/*  51 */         lastType = rec.type;
/*     */ 
/*  54 */         for (i = getGroupNum() - 1; i >= 0; --i) {
/*  55 */           addSTRecord(writer, this.groupName[i], this.aItemValue[i]);
/*     */         }
/*  57 */         bfirstRec = true;
/*     */       }
/*  59 */       if ((rec.type < 3) && (lastType <= rec.type))
/*     */       {
/*  61 */         writer.appendRecord(rec);
/*     */       }
/*  63 */       if (rec.type > 3) {
/*  64 */         lastType = rec.type;
/*     */ 
/*  66 */         for (i = getGroupNum() - 1; i >= 0; --i) {
/*  67 */           addSTRecord(writer, this.groupName[i], this.aItemValue[i]);
/*     */         }
/*  69 */         bfirstRec = true;
/*     */       }
/*     */ 
/*  74 */       if (bfirstRec) {
/*  75 */         for (i = 0; i < getGroupNum(); ++i) {
/*  76 */           this.aItemValue[i] = rec.get(this.groupName[i]);
/*     */         }
/*  78 */         bfirstRec = false;
/*     */       }
/*     */ 
/*  81 */       for (int i = 0; i < getGroupNum(); ++i) {
/*  82 */         String srcvar = rec.get(this.groupName[i]);
/*  83 */         if (!(srcvar.equals(this.aItemValue[i]))) {
/*  84 */           for (int j = getGroupNum() - 1; j >= i; --j) {
/*  85 */             addSTRecord(writer, this.groupName[j], this.aItemValue[j]);
/*     */           }
/*  87 */           bfirstRec = true;
/*  88 */           break;
/*     */         }
/*     */       }
/*  91 */       writer.appendRecord(rec);
/*     */ 
/*  93 */       loadSubTotal(rec);
/*     */ 
/*  95 */       if (bfirstRec) {
/*  96 */         for (i = 0; i < getGroupNum(); ++i) {
/*  97 */           this.aItemValue[i] = rec.get(this.groupName[i]);
/*     */         }
/*  99 */         bfirstRec = false;
/*     */       }
/* 101 */       lastType = 3;
/*     */     }
/*     */ 
/* 107 */     if (lastType <= 3)
/*     */     {
/* 109 */       for (int j = getGroupNum() - 1; j >= 0; --j) {
/* 110 */         addSTRecord(writer, this.groupName[j], this.aItemValue[j]);
/*     */       }
/*     */     }
/* 113 */     writer.close();
/* 114 */     reader.close();
/*     */ 
/* 116 */     return tmpfile;
/*     */   }
/*     */ 
/*     */   void loadSubTotal(HiDataRecord rec)
/*     */   {
/* 121 */     Iterator it = this.fields.values().iterator();
/* 122 */     while (it.hasNext()) {
/* 123 */       HiFieldNode field = (HiFieldNode)it.next();
/* 124 */       field.load(rec);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addSTRecord(RecordWriter tmpfile, String name, String value)
/*     */   {
/* 135 */     HiFieldNode field = (HiFieldNode)this.fields.get(name);
/*     */ 
/* 137 */     this.typeAppender.append(tmpfile);
/* 138 */     field.append(tmpfile);
/* 139 */     tmpfile.appendRecordValue(name, value);
/*     */   }
/*     */ }