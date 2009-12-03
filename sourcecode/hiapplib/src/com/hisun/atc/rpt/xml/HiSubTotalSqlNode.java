/*    */ package com.hisun.atc.rpt.xml;
/*    */ 
/*    */ import com.hisun.atc.rpt.data.Appender;
/*    */ import com.hisun.atc.rpt.data.RecordWriter;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.ResultSetMetaData;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class HiSubTotalSqlNode extends HiSqlNode
/*    */ {
/*    */   public int seq;
/*    */   public String field;
/*    */   public Appender beforeAppender;
/*    */   String pos;
/*    */   int beforeseq;
/*    */ 
/*    */   public HiSubTotalSqlNode()
/*    */   {
/* 32 */     this.pos = "after";
/*    */   }
/*    */ 
/*    */   public void setFmtseq(int seq)
/*    */   {
/* 25 */     this.seq = seq;
/*    */   }
/*    */ 
/*    */   public void setField(String field) {
/* 29 */     this.field = field;
/*    */   }
/*    */ 
/*    */   public void setPos(String pos)
/*    */   {
/* 36 */     this.pos = pos;
/*    */   }
/*    */ 
/*    */   public void setBeforeseq(int beforeseq) {
/* 40 */     this.beforeseq = beforeseq;
/*    */   }
/*    */ 
/*    */   protected void dealRecord(RecordWriter out, ResultSet rs, ResultSetMetaData meta, int cols)
/*    */     throws SQLException
/*    */   {
/*    */     int i;
/*    */     String strColName;
/*    */     String value;
/* 45 */     if (before()) {
/* 46 */       this.beforeAppender.append(out);
/* 47 */       for (i = 0; i < cols; ++i) {
/* 48 */         strColName = meta.getColumnName(i + 1);
/* 49 */         value = null;
/* 50 */         if (rs.getObject(i + 1) != null) {
/* 51 */           value = rs.getObject(i + 1).toString();
/*    */         }
/*    */         else
/* 54 */           value = "";
/* 55 */         out.appendRecordValue(strColName.trim(), value.trim());
/*    */       }
/*    */     }
/*    */ 
/* 59 */     if (after()) {
/* 60 */       this.prefixAppender.append(out);
/* 61 */       for (i = 0; i < cols; ++i) {
/* 62 */         strColName = meta.getColumnName(i + 1);
/* 63 */         value = null;
/* 64 */         if (rs.getObject(i + 1) != null) {
/* 65 */           value = rs.getObject(i + 1).toString();
/*    */         }
/*    */         else
/* 68 */           value = "";
/* 69 */         out.appendRecordValue(strColName.trim(), value.trim());
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public boolean after() {
/* 75 */     return ((this.pos.equals("after")) || (this.pos.equals("both")));
/*    */   }
/*    */ 
/*    */   public boolean before() {
/* 79 */     return ((this.pos.equals("before")) || (this.pos.equals("both")));
/*    */   }
/*    */ }