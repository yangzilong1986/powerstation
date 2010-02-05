 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.HiDataFile;
 import com.hisun.atc.rpt.HiRptContext;
 import com.hisun.atc.rpt.HiRptExp;
 import java.util.Map;
 
 public class HiFileDataSourceNode extends HiDataSourceNode
 {
   private String file;
 
   String getFile()
   {
     return this.file;
   }
 
   public HiDataFile process(HiRptContext ctx)
   {
     String path;
     ctx.info("开始处理DataSource节点");
     Map vars = ctx.vars;
     HiRptExp exp = new HiRptExp(this.file);
     try
     {
       path = exp.getValue(vars);
       ctx.info("数据源文件为:" + path);
     } catch (Exception e) {
       ctx.error("数据源文件配置出错:" + this.file, e);
       ctx.runtimeException(e);
       return null;
     }
 
     HiDataFile datafile = ctx.createDataFile(path);
     HiDataFile tmpfile = null;
     if (this.subtotal != null) {
       ctx.info("开始处理DataSource/SubTotal节点");
       tmpfile = ctx.createDataFile(path + ".subtotal.tmp");
       datafile = this.subtotal.process(ctx, datafile, tmpfile);
       ctx.info("DataSource/SubTotal节点处理完毕");
     }
 
     HiDataFile tmpfile2 = null;
     if (this.summed != null) {
       ctx.info("开始处理DataSource/Summed节点");
       tmpfile2 = ctx.createDataFile(path + ".summed.tmp");
       datafile = this.summed.process(ctx, datafile, tmpfile2);
       ctx.info("DataSource/Summed节点处理完毕");
     }
 
     ctx.info("DataSource节点处理完毕");
     return datafile;
   }
 
   public void setFile(String file) {
     this.file = file;
   }
 }