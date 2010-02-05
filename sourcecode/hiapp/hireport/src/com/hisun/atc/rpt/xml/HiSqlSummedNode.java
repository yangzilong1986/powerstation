 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.HiDataFile;
 import com.hisun.atc.rpt.HiReportConstants;
 import com.hisun.atc.rpt.HiRptContext;
 import com.hisun.atc.rpt.data.Appender;
 import com.hisun.atc.rpt.data.Appenders;
 import com.hisun.atc.rpt.data.RecordWriter;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 
 public class HiSqlSummedNode extends HiSummedNode
   implements HiReportConstants
 {
   private List sqls;
   Appender typeAppender;
 
   public HiSqlSummedNode()
   {
     this.sqls = new ArrayList();
 
     this.typeAppender = Appenders.type(5);
   }
 
   public void addSqlNode(HiSqlNode sql)
   {
     this.sqls.add(sql);
   }
 
   public HiDataFile process(HiRptContext ctx, HiDataFile datafile, HiDataFile tmpfile)
   {
     RecordWriter writer = datafile.getAppendWriter();
 
     this.typeAppender.append(writer);
     Iterator it = this.sqls.iterator();
     while (it.hasNext()) {
       HiSqlNode sql = (HiSqlNode)it.next();
       sql.process(ctx, writer);
     }
     writer.close();
 
     return datafile;
   }
 }