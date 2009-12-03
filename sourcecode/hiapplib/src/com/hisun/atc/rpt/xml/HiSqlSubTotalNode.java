/*     */ package com.hisun.atc.rpt.xml;
/*     */ 
/*     */ import com.hisun.atc.rpt.HiDataFile;
/*     */ import com.hisun.atc.rpt.HiDataRecord;
/*     */ import com.hisun.atc.rpt.HiReportConstants;
/*     */ import com.hisun.atc.rpt.HiRptContext;
/*     */ import com.hisun.atc.rpt.data.Appender;
/*     */ import com.hisun.atc.rpt.data.Appenders;
/*     */ import com.hisun.atc.rpt.data.Matcher;
/*     */ import com.hisun.atc.rpt.data.Matchers;
/*     */ import com.hisun.atc.rpt.data.RecordReader;
/*     */ import com.hisun.atc.rpt.data.RecordWriter;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ 
/*     */ public class HiSqlSubTotalNode extends HiSubTotalNode
/*     */   implements HiReportConstants
/*     */ {
/*     */   HiSubTotalSqlNode[] sqls;
/*     */ 
/*     */   public void addSqlNode(HiSubTotalSqlNode sql)
/*     */   {
/*  36 */     if (this.sqls == null) {
/*  37 */       this.sqls = new HiSubTotalSqlNode[getGroupNum()];
/*     */     }
/*     */ 
/*  40 */     Appender typeAppender = Appenders.type(4);
/*  41 */     sql.prefixAppender = typeAppender;
/*  42 */     sql.beforeAppender = typeAppender;
/*     */ 
/*  44 */     if (sql.seq != 0) {
/*  45 */       sql.prefixAppender = Appenders.seq(new Appender[] { typeAppender, Appenders.fmtseq(sql.seq) });
/*     */     }
/*     */ 
/*  48 */     if (sql.beforeseq != 0) {
/*  49 */       sql.beforeAppender = Appenders.seq(new Appender[] { typeAppender, Appenders.fmtseq(sql.beforeseq) });
/*     */     }
/*     */ 
/*  53 */     for (int i = 0; i < getGroupNum(); ++i)
/*  54 */       if (this.groupName[i].equals(sql.field)) {
/*  55 */         this.sqls[i] = sql;
/*  56 */         return;
/*     */       }
/*     */   }
/*     */ 
/*     */   public HiDataFile process(HiRptContext ctx, HiDataFile datafile, HiDataFile tmpfile)
/*     */   {
/*  62 */     HiDataBaseUtil dbutil = ctx.getDBUtil();
/*     */ 
/*  65 */     HiDataFile[] tmps = new HiDataFile[this.sqls.length];
/*  66 */     for (int i = 0; i < getGroupNum(); ++i) {
/*  67 */       tmps[i] = runsql(this.sqls[i], ctx, datafile.getPath());
/*     */     }
/*     */ 
/*  70 */     RecordReader[] readers = new RecordReader[tmps.length];
/*  71 */     for (int i = 0; i < readers.length; ++i) {
/*  72 */       readers[i] = tmps[i].getReader();
/*     */     }
/*  74 */     RecordReader datareader = datafile.getReader();
/*  75 */     RecordWriter writer = tmpfile.getWriter();
/*     */ 
/*  78 */     Matcher[] matchers = new Matcher[getGroupNum()];
/*  79 */     for (int i = 0; i < getGroupNum(); ++i)
/*  80 */       matchers[i] = Matchers.valueChange(this.groupName[i]);
/*     */     try
/*     */     {
/*  83 */       fileMerge(ctx, datareader, readers, writer, matchers);
/*     */     }
/*     */     catch (RuntimeException e) {
/*     */     }
/*     */     finally {
/*  88 */       datareader.close();
/*  89 */       writer.close();
/*  90 */       for (int i = 0; i < readers.length; ++i) {
/*  91 */         readers[i].close();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  96 */     return tmpfile;
/*     */   }
/*     */ 
/*     */   public HiDataFile runsql(HiSubTotalSqlNode sql, HiRptContext ctx, String basepath)
/*     */   {
/* 102 */     HiDataFile sqlfile = ctx.createDataFile(basepath + ".sql" + sql.field);
/*     */ 
/* 104 */     RecordWriter writer = sqlfile.getWriter();
/* 105 */     sql.process(ctx, writer);
/* 106 */     writer.close();
/* 107 */     return sqlfile;
/*     */   }
/*     */ 
/*     */   private void fileMerge(HiRptContext ctx, RecordReader datafile, RecordReader[] readers, RecordWriter subfile, Matcher[] matchers)
/*     */   {
/*     */     HiDataRecord rec;
/* 129 */     ctx.info("group数:" + getGroupNum());
/* 130 */     for (int i = 0; i < getGroupNum(); ++i) {
/* 131 */       ctx.info(this.groupName[i]);
/*     */     }
/*     */ 
/* 134 */     for (i = 0; i < getGroupNum(); ++i) {
/* 135 */       if (this.sqls[i].before()) {
/* 136 */         rec = readers[i].readRecord();
/* 137 */         ctx.info("插入before小计:" + this.groupName[i] + ":" + rec);
/* 138 */         if (rec != null) {
/* 139 */           subfile.appendRecord(rec);
/*     */         }
/*     */       }
/*     */     }
/* 143 */     while ((itRec = datafile.readRecord()) != null)
/*     */     {
/*     */       HiDataRecord itRec;
/* 144 */       if (itRec.type != 3) {
/*     */         continue;
/*     */       }
/* 147 */       ctx.info("读取记录:" + itRec.toString());
/*     */ 
/* 178 */       for (i = 0; i < matchers.length; ++i)
/*     */       {
/*     */         HiDataRecord rec;
/* 179 */         if (!(matchers[i].match(itRec)))
/*     */           continue;
/* 181 */         for (int j = readers.length - 1; j >= i; --j)
/*     */         {
/* 184 */           if (this.sqls[j].after()) {
/* 185 */             rec = readers[j].readRecord();
/* 186 */             ctx.info("插入after小计:" + this.groupName[j] + ":" + rec);
/* 187 */             if (rec != null) {
/* 188 */               subfile.appendRecord(rec);
/*     */             }
/*     */           }
/* 191 */           matchers[j] = Matchers.valueChange(this.groupName[j]);
/* 192 */           matchers[j].match(itRec);
/*     */         }
/*     */ 
/* 196 */         for (j = i; j < readers.length; ++j) {
/* 197 */           if (this.sqls[j].before()) {
/* 198 */             rec = readers[j].readRecord();
/* 199 */             ctx.info("插入before小计:" + this.groupName[j] + ":" + rec);
/* 200 */             if (rec != null) {
/* 201 */               subfile.appendRecord(rec);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 207 */       subfile.appendRecord(itRec);
/*     */     }
/*     */ 
/* 227 */     for (int j = getGroupNum() - 1; j >= 0; --j)
/* 228 */       if (this.sqls[j].after()) {
/* 229 */         rec = readers[j].readRecord();
/* 230 */         ctx.info("插入after小计:" + this.groupName[j] + ":" + rec);
/* 231 */         if (rec != null)
/* 232 */           subfile.appendRecord(rec);
/*     */       }
/*     */   }
/*     */ }