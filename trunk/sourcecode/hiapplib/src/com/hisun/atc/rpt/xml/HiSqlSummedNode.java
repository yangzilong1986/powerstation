/*    */ package com.hisun.atc.rpt.xml;
/*    */ 
/*    */ import com.hisun.atc.rpt.HiDataFile;
/*    */ import com.hisun.atc.rpt.HiReportConstants;
/*    */ import com.hisun.atc.rpt.HiRptContext;
/*    */ import com.hisun.atc.rpt.data.Appender;
/*    */ import com.hisun.atc.rpt.data.Appenders;
/*    */ import com.hisun.atc.rpt.data.RecordWriter;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class HiSqlSummedNode extends HiSummedNode
/*    */   implements HiReportConstants
/*    */ {
/*    */   private List sqls;
/*    */   Appender typeAppender;
/*    */ 
/*    */   public HiSqlSummedNode()
/*    */   {
/* 18 */     this.sqls = new ArrayList();
/*    */ 
/* 20 */     this.typeAppender = Appenders.type(5);
/*    */   }
/*    */ 
/*    */   public void addSqlNode(HiSqlNode sql)
/*    */   {
/* 30 */     this.sqls.add(sql);
/*    */   }
/*    */ 
/*    */   public HiDataFile process(HiRptContext ctx, HiDataFile datafile, HiDataFile tmpfile)
/*    */   {
/* 35 */     RecordWriter writer = datafile.getAppendWriter();
/*    */ 
/* 37 */     this.typeAppender.append(writer);
/* 38 */     Iterator it = this.sqls.iterator();
/* 39 */     while (it.hasNext()) {
/* 40 */       HiSqlNode sql = (HiSqlNode)it.next();
/* 41 */       sql.process(ctx, writer);
/*    */     }
/* 43 */     writer.close();
/*    */ 
/* 45 */     return datafile;
/*    */   }
/*    */ }