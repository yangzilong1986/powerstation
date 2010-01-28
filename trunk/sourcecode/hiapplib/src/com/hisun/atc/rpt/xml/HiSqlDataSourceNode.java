 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.HiDataFile;
 import com.hisun.atc.rpt.HiRptContext;
 import com.hisun.atc.rpt.data.RecordWriter;
 import java.util.Map;
 
 public class HiSqlDataSourceNode extends HiDataSourceNode
 {
   public HiIterativeNode iterative;
   public HiPageHeaderNode pageheader;
 
   public HiDataFile process(HiRptContext ctx)
   {
     ctx.info("开始处理DataSource节点");
     Map vars = ctx.vars;
     String path = (String)vars.get("RPTNAME");
     path = path + ".data";
 
     HiDataFile datafile = ctx.createDataFile(path);
     ctx.info("打开中间文件:" + path);
 
     RecordWriter writer = datafile.getWriter();
     if (this.pageheader != null) {
       ctx.info("开始处理DataSource/PageHeader节点");
       this.pageheader.process(ctx, writer);
       ctx.info("DataSource/PageHeader节点处理完毕");
     }
 
     if (this.iterative != null) {
       this.iterative.process(ctx, writer);
     }
     writer.close();
     ctx.info("关闭中间文件:" + path);
     HiDataFile tmpfile = datafile;
 
     HiDataFile tmpfile1 = null;
     if (this.subtotal != null) {
       ctx.info("开始处理DataSource/SubTotal节点");
       tmpfile1 = ctx.createDataFile(path + ".subtotal.tmp");
       datafile = this.subtotal.process(ctx, datafile, tmpfile1);
       ctx.info("DataSource/SubTotal节点处理完毕");
     }
 
     HiDataFile tmpfile2 = null;
     if (this.summed != null) {
       ctx.info("开始处理DataSource/Summed节点");
       tmpfile2 = ctx.createDataFile(path + ".summed.tmp");
       ctx.info("打开中间文件:" + tmpfile2.getPath());
       datafile = this.summed.process(ctx, datafile, tmpfile2);
       ctx.info("DataSource/Summed节点处理完毕");
     }
 
     ctx.info("DataSource节点处理完毕");
     return datafile;
   }
 
   public void setIterative(HiIterativeNode iterative) {
     this.iterative = iterative;
   }
 
   public void setPageheader(HiPageHeaderNode pageheader) {
     this.pageheader = pageheader;
   }
 }