 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.HiReportConstants;
 import com.hisun.atc.rpt.HiRptContext;
 import com.hisun.atc.rpt.data.Appender;
 import com.hisun.atc.rpt.data.Appenders;
 import com.hisun.atc.rpt.data.RecordWriter;
 import com.hisun.xml.Located;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 
 public class HiPageHeaderNode extends Located
   implements HiReportConstants
 {
   List sqls;
   Appender typeAppender;
 
   public HiPageHeaderNode()
   {
     this.sqls = new ArrayList();
 
     this.typeAppender = Appenders.type(0);
   }
 
   public void addSqlNode(HiSqlNode sql)
   {
     this.sqls.add(sql);
   }
 
   public void process(HiRptContext ctx, RecordWriter datafile) {
     ctx.info("开始处理PageHeader节点");
     this.typeAppender.append(datafile);
     Iterator it = this.sqls.iterator();
     while (it.hasNext()) {
       HiSqlNode sql = (HiSqlNode)it.next();
       sql.process(ctx, datafile);
     }
     ctx.info("PageHeader节点处理完毕");
   }
 }