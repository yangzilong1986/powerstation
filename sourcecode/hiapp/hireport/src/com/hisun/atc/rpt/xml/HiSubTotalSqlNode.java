 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.data.Appender;
 import com.hisun.atc.rpt.data.RecordWriter;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 
 public class HiSubTotalSqlNode extends HiSqlNode
 {
   public int seq;
   public String field;
   public Appender beforeAppender;
   String pos;
   int beforeseq;
 
   public HiSubTotalSqlNode()
   {
     this.pos = "after";
   }
 
   public void setFmtseq(int seq)
   {
     this.seq = seq;
   }
 
   public void setField(String field) {
     this.field = field;
   }
 
   public void setPos(String pos)
   {
     this.pos = pos;
   }
 
   public void setBeforeseq(int beforeseq) {
     this.beforeseq = beforeseq;
   }
 
   protected void dealRecord(RecordWriter out, ResultSet rs, ResultSetMetaData meta, int cols)
     throws SQLException
   {
     int i;
     String strColName;
     String value;
     if (before()) {
       this.beforeAppender.append(out);
       for (i = 0; i < cols; ++i) {
         strColName = meta.getColumnName(i + 1);
         value = null;
         if (rs.getObject(i + 1) != null) {
           value = rs.getObject(i + 1).toString();
         }
         else
           value = "";
         out.appendRecordValue(strColName.trim(), value.trim());
       }
     }
 
     if (after()) {
       this.prefixAppender.append(out);
       for (i = 0; i < cols; ++i) {
         strColName = meta.getColumnName(i + 1);
         value = null;
         if (rs.getObject(i + 1) != null) {
           value = rs.getObject(i + 1).toString();
         }
         else
           value = "";
         out.appendRecordValue(strColName.trim(), value.trim());
       }
     }
   }
 
   public boolean after() {
     return ((this.pos.equals("after")) || (this.pos.equals("both")));
   }
 
   public boolean before() {
     return ((this.pos.equals("before")) || (this.pos.equals("both")));
   }
 }