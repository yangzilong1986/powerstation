 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.HiReportConstants;
 import com.hisun.atc.rpt.HiReportRuntimeException;
 import com.hisun.atc.rpt.HiRptContext;
 import com.hisun.atc.rpt.data.Appenders;
 import com.hisun.atc.rpt.data.RecordWriter;
 import com.hisun.xml.Located;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 
 public class HiIterativeNode extends Located
   implements HiReportConstants
 {
   private List sqls;
 
   public HiIterativeNode()
   {
     this.sqls = new ArrayList();
   }
 
   public void addSqlNode(HiSqlNode sql)
   {
     sql.prefixAppender = Appenders.type(3);
     this.sqls.add(sql);
   }
 
   public void process(HiRptContext ctx, RecordWriter datafile) {
     ctx.info("开始处理DataSource/Iterative节点");
     Iterator it = this.sqls.iterator();
     int n = 0;
     while (it.hasNext()) {
       HiSqlNode sql = (HiSqlNode)it.next();
       n += sql.process(ctx, datafile);
     }
     ctx.info("DataSource/Iterative节点处理完毕");
     if (n != 0)
       return;
     datafile.close();
     HiReportRuntimeException e = new HiReportRuntimeException(2, "没有明细记录");
     ctx.error("没有明细记录,中断执行!" + e.getErrCode(), e);
     throw e;
   }
 }