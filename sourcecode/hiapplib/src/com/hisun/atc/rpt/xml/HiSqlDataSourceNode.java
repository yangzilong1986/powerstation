/*    */ package com.hisun.atc.rpt.xml;
/*    */ 
/*    */ import com.hisun.atc.rpt.HiDataFile;
/*    */ import com.hisun.atc.rpt.HiRptContext;
/*    */ import com.hisun.atc.rpt.data.RecordWriter;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class HiSqlDataSourceNode extends HiDataSourceNode
/*    */ {
/*    */   public HiIterativeNode iterative;
/*    */   public HiPageHeaderNode pageheader;
/*    */ 
/*    */   public HiDataFile process(HiRptContext ctx)
/*    */   {
/* 26 */     ctx.info("开始处理DataSource节点");
/* 27 */     Map vars = ctx.vars;
/* 28 */     String path = (String)vars.get("RPTNAME");
/* 29 */     path = path + ".data";
/*    */ 
/* 31 */     HiDataFile datafile = ctx.createDataFile(path);
/* 32 */     ctx.info("打开中间文件:" + path);
/*    */ 
/* 34 */     RecordWriter writer = datafile.getWriter();
/* 35 */     if (this.pageheader != null) {
/* 36 */       ctx.info("开始处理DataSource/PageHeader节点");
/* 37 */       this.pageheader.process(ctx, writer);
/* 38 */       ctx.info("DataSource/PageHeader节点处理完毕");
/*    */     }
/*    */ 
/* 41 */     if (this.iterative != null) {
/* 42 */       this.iterative.process(ctx, writer);
/*    */     }
/* 44 */     writer.close();
/* 45 */     ctx.info("关闭中间文件:" + path);
/* 46 */     HiDataFile tmpfile = datafile;
/*    */ 
/* 48 */     HiDataFile tmpfile1 = null;
/* 49 */     if (this.subtotal != null) {
/* 50 */       ctx.info("开始处理DataSource/SubTotal节点");
/* 51 */       tmpfile1 = ctx.createDataFile(path + ".subtotal.tmp");
/* 52 */       datafile = this.subtotal.process(ctx, datafile, tmpfile1);
/* 53 */       ctx.info("DataSource/SubTotal节点处理完毕");
/*    */     }
/*    */ 
/* 56 */     HiDataFile tmpfile2 = null;
/* 57 */     if (this.summed != null) {
/* 58 */       ctx.info("开始处理DataSource/Summed节点");
/* 59 */       tmpfile2 = ctx.createDataFile(path + ".summed.tmp");
/* 60 */       ctx.info("打开中间文件:" + tmpfile2.getPath());
/* 61 */       datafile = this.summed.process(ctx, datafile, tmpfile2);
/* 62 */       ctx.info("DataSource/Summed节点处理完毕");
/*    */     }
/*    */ 
/* 65 */     ctx.info("DataSource节点处理完毕");
/* 66 */     return datafile;
/*    */   }
/*    */ 
/*    */   public void setIterative(HiIterativeNode iterative) {
/* 70 */     this.iterative = iterative;
/*    */   }
/*    */ 
/*    */   public void setPageheader(HiPageHeaderNode pageheader) {
/* 74 */     this.pageheader = pageheader;
/*    */   }
/*    */ }