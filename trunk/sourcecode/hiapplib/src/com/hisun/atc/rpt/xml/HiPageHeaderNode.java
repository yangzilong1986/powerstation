/*    */ package com.hisun.atc.rpt.xml;
/*    */ 
/*    */ import com.hisun.atc.rpt.HiReportConstants;
/*    */ import com.hisun.atc.rpt.HiRptContext;
/*    */ import com.hisun.atc.rpt.data.Appender;
/*    */ import com.hisun.atc.rpt.data.Appenders;
/*    */ import com.hisun.atc.rpt.data.RecordWriter;
/*    */ import com.hisun.xml.Located;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class HiPageHeaderNode extends Located
/*    */   implements HiReportConstants
/*    */ {
/*    */   List sqls;
/*    */   Appender typeAppender;
/*    */ 
/*    */   public HiPageHeaderNode()
/*    */   {
/* 21 */     this.sqls = new ArrayList();
/*    */ 
/* 23 */     this.typeAppender = Appenders.type(0);
/*    */   }
/*    */ 
/*    */   public void addSqlNode(HiSqlNode sql)
/*    */   {
/* 34 */     this.sqls.add(sql);
/*    */   }
/*    */ 
/*    */   public void process(HiRptContext ctx, RecordWriter datafile) {
/* 38 */     ctx.info("开始处理PageHeader节点");
/* 39 */     this.typeAppender.append(datafile);
/* 40 */     Iterator it = this.sqls.iterator();
/* 41 */     while (it.hasNext()) {
/* 42 */       HiSqlNode sql = (HiSqlNode)it.next();
/* 43 */       sql.process(ctx, datafile);
/*    */     }
/* 45 */     ctx.info("PageHeader节点处理完毕");
/*    */   }
/*    */ }