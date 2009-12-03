/*    */ package com.hisun.atc.rpt.xml;
/*    */ 
/*    */ import com.hisun.atc.rpt.HiDataFile;
/*    */ import com.hisun.atc.rpt.HiRptContext;
/*    */ import com.hisun.atc.rpt.HiRptExp;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class HiFileDataSourceNode extends HiDataSourceNode
/*    */ {
/*    */   private String file;
/*    */ 
/*    */   String getFile()
/*    */   {
/* 17 */     return this.file;
/*    */   }
/*    */ 
/*    */   public HiDataFile process(HiRptContext ctx)
/*    */   {
/*    */     String path;
/* 28 */     ctx.info("开始处理DataSource节点");
/* 29 */     Map vars = ctx.vars;
/* 30 */     HiRptExp exp = new HiRptExp(this.file);
/*    */     try
/*    */     {
/* 33 */       path = exp.getValue(vars);
/* 34 */       ctx.info("数据源文件为:" + path);
/*    */     } catch (Exception e) {
/* 36 */       ctx.error("数据源文件配置出错:" + this.file, e);
/* 37 */       ctx.runtimeException(e);
/* 38 */       return null;
/*    */     }
/*    */ 
/* 41 */     HiDataFile datafile = ctx.createDataFile(path);
/* 42 */     HiDataFile tmpfile = null;
/* 43 */     if (this.subtotal != null) {
/* 44 */       ctx.info("开始处理DataSource/SubTotal节点");
/* 45 */       tmpfile = ctx.createDataFile(path + ".subtotal.tmp");
/* 46 */       datafile = this.subtotal.process(ctx, datafile, tmpfile);
/* 47 */       ctx.info("DataSource/SubTotal节点处理完毕");
/*    */     }
/*    */ 
/* 50 */     HiDataFile tmpfile2 = null;
/* 51 */     if (this.summed != null) {
/* 52 */       ctx.info("开始处理DataSource/Summed节点");
/* 53 */       tmpfile2 = ctx.createDataFile(path + ".summed.tmp");
/* 54 */       datafile = this.summed.process(ctx, datafile, tmpfile2);
/* 55 */       ctx.info("DataSource/Summed节点处理完毕");
/*    */     }
/*    */ 
/* 58 */     ctx.info("DataSource节点处理完毕");
/* 59 */     return datafile;
/*    */   }
/*    */ 
/*    */   public void setFile(String file) {
/* 63 */     this.file = file;
/*    */   }
/*    */ }