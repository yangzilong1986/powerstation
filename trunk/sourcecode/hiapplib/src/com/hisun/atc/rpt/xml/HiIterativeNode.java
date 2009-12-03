/*    */ package com.hisun.atc.rpt.xml;
/*    */ 
/*    */ import com.hisun.atc.rpt.HiReportConstants;
/*    */ import com.hisun.atc.rpt.HiReportRuntimeException;
/*    */ import com.hisun.atc.rpt.HiRptContext;
/*    */ import com.hisun.atc.rpt.data.Appenders;
/*    */ import com.hisun.atc.rpt.data.RecordWriter;
/*    */ import com.hisun.xml.Located;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class HiIterativeNode extends Located
/*    */   implements HiReportConstants
/*    */ {
/*    */   private List sqls;
/*    */ 
/*    */   public HiIterativeNode()
/*    */   {
/* 22 */     this.sqls = new ArrayList();
/*    */   }
/*    */ 
/*    */   public void addSqlNode(HiSqlNode sql)
/*    */   {
/* 29 */     sql.prefixAppender = Appenders.type(3);
/* 30 */     this.sqls.add(sql);
/*    */   }
/*    */ 
/*    */   public void process(HiRptContext ctx, RecordWriter datafile) {
/* 34 */     ctx.info("开始处理DataSource/Iterative节点");
/* 35 */     Iterator it = this.sqls.iterator();
/* 36 */     int n = 0;
/* 37 */     while (it.hasNext()) {
/* 38 */       HiSqlNode sql = (HiSqlNode)it.next();
/* 39 */       n += sql.process(ctx, datafile);
/*    */     }
/* 41 */     ctx.info("DataSource/Iterative节点处理完毕");
/* 42 */     if (n != 0)
/*    */       return;
/* 44 */     datafile.close();
/* 45 */     HiReportRuntimeException e = new HiReportRuntimeException(2, "没有明细记录");
/* 46 */     ctx.error("没有明细记录,中断执行!" + e.getErrCode(), e);
/* 47 */     throw e;
/*    */   }
/*    */ }