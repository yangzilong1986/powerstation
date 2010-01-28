 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.HiDataFile;
 import com.hisun.atc.rpt.HiDataRecord;
 import com.hisun.atc.rpt.HiReportConstants;
 import com.hisun.atc.rpt.HiRptContext;
 import com.hisun.atc.rpt.data.Appender;
 import com.hisun.atc.rpt.data.Appenders;
 import com.hisun.atc.rpt.data.Matcher;
 import com.hisun.atc.rpt.data.Matchers;
 import com.hisun.atc.rpt.data.RecordReader;
 import com.hisun.atc.rpt.data.RecordWriter;
 import com.hisun.database.HiDataBaseUtil;
 
 public class HiSqlSubTotalNode extends HiSubTotalNode
   implements HiReportConstants
 {
   HiSubTotalSqlNode[] sqls;
 
   public void addSqlNode(HiSubTotalSqlNode sql)
   {
     if (this.sqls == null) {
       this.sqls = new HiSubTotalSqlNode[getGroupNum()];
     }
 
     Appender typeAppender = Appenders.type(4);
     sql.prefixAppender = typeAppender;
     sql.beforeAppender = typeAppender;
 
     if (sql.seq != 0) {
       sql.prefixAppender = Appenders.seq(new Appender[] { typeAppender, Appenders.fmtseq(sql.seq) });
     }
 
     if (sql.beforeseq != 0) {
       sql.beforeAppender = Appenders.seq(new Appender[] { typeAppender, Appenders.fmtseq(sql.beforeseq) });
     }
 
     for (int i = 0; i < getGroupNum(); ++i)
       if (this.groupName[i].equals(sql.field)) {
         this.sqls[i] = sql;
         return;
       }
   }
 
   public HiDataFile process(HiRptContext ctx, HiDataFile datafile, HiDataFile tmpfile)
   {
     HiDataBaseUtil dbutil = ctx.getDBUtil();
 
     HiDataFile[] tmps = new HiDataFile[this.sqls.length];
     for (int i = 0; i < getGroupNum(); ++i) {
       tmps[i] = runsql(this.sqls[i], ctx, datafile.getPath());
     }
 
     RecordReader[] readers = new RecordReader[tmps.length];
     for (int i = 0; i < readers.length; ++i) {
       readers[i] = tmps[i].getReader();
     }
     RecordReader datareader = datafile.getReader();
     RecordWriter writer = tmpfile.getWriter();
 
     Matcher[] matchers = new Matcher[getGroupNum()];
     for (int i = 0; i < getGroupNum(); ++i)
       matchers[i] = Matchers.valueChange(this.groupName[i]);
     try
     {
       fileMerge(ctx, datareader, readers, writer, matchers);
     }
     catch (RuntimeException e) {
     }
     finally {
       datareader.close();
       writer.close();
       for (int i = 0; i < readers.length; ++i) {
         readers[i].close();
       }
 
     }
 
     return tmpfile;
   }
 
   public HiDataFile runsql(HiSubTotalSqlNode sql, HiRptContext ctx, String basepath)
   {
     HiDataFile sqlfile = ctx.createDataFile(basepath + ".sql" + sql.field);
 
     RecordWriter writer = sqlfile.getWriter();
     sql.process(ctx, writer);
     writer.close();
     return sqlfile;
   }
 
   private void fileMerge(HiRptContext ctx, RecordReader datafile, RecordReader[] readers, RecordWriter subfile, Matcher[] matchers)
   {
     HiDataRecord rec;
     ctx.info("group数:" + getGroupNum());
     for (int i = 0; i < getGroupNum(); ++i) {
       ctx.info(this.groupName[i]);
     }
 
     for (i = 0; i < getGroupNum(); ++i) {
       if (this.sqls[i].before()) {
         rec = readers[i].readRecord();
         ctx.info("插入before小计:" + this.groupName[i] + ":" + rec);
         if (rec != null) {
           subfile.appendRecord(rec);
         }
       }
     }
     while ((itRec = datafile.readRecord()) != null)
     {
       HiDataRecord itRec;
       if (itRec.type != 3) {
         continue;
       }
       ctx.info("读取记录:" + itRec.toString());
 
       for (i = 0; i < matchers.length; ++i)
       {
         HiDataRecord rec;
         if (!(matchers[i].match(itRec)))
           continue;
         for (int j = readers.length - 1; j >= i; --j)
         {
           if (this.sqls[j].after()) {
             rec = readers[j].readRecord();
             ctx.info("插入after小计:" + this.groupName[j] + ":" + rec);
             if (rec != null) {
               subfile.appendRecord(rec);
             }
           }
           matchers[j] = Matchers.valueChange(this.groupName[j]);
           matchers[j].match(itRec);
         }
 
         for (j = i; j < readers.length; ++j) {
           if (this.sqls[j].before()) {
             rec = readers[j].readRecord();
             ctx.info("插入before小计:" + this.groupName[j] + ":" + rec);
             if (rec != null) {
               subfile.appendRecord(rec);
             }
           }
         }
       }
 
       subfile.appendRecord(itRec);
     }
 
     for (int j = getGroupNum() - 1; j >= 0; --j)
       if (this.sqls[j].after()) {
         rec = readers[j].readRecord();
         ctx.info("插入after小计:" + this.groupName[j] + ":" + rec);
         if (rec != null)
           subfile.appendRecord(rec);
       }
   }
 }