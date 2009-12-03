/*    */ package com.hisun.database;
/*    */ 
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.ResultSetMetaData;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class HiResultSet
/*    */ {
/* 11 */   private ArrayList records = new ArrayList(1);
/*    */ 
/*    */   public HiResultSet(ResultSet rs) throws SQLException {
/* 14 */     ResultSetMetaData meta = rs.getMetaData();
/* 15 */     int cols = meta.getColumnCount();
/* 16 */     while (rs.next()) {
/* 17 */       HiATLParam param = new HiATLParam();
/* 18 */       for (int i = 0; i < cols; ++i) {
/* 19 */         String strColName = meta.getColumnName(i + 1);
/* 20 */         param.put(strColName, String.valueOf(rs.getObject(i + 1)));
/*    */       }
/* 22 */       this.records.add(param);
/*    */     }
/*    */   }
/*    */ 
/*    */   public HiATLParam getFirstRecord()
/*    */   {
/* 30 */     return ((HiATLParam)this.records.get(0));
/*    */   }
/*    */ 
/*    */   public HiATLParam getRecord(int idx)
/*    */   {
/* 38 */     return ((HiATLParam)this.records.get(idx));
/*    */   }
/*    */ 
/*    */   public int size()
/*    */   {
/* 46 */     return this.records.size();
/*    */   }
/*    */ 
/*    */   public int getInt(int row, int columnIndex)
/*    */   {
/* 54 */     HiATLParam params = (HiATLParam)this.records.get(row);
/* 55 */     return params.getInt(columnIndex);
/*    */   }
/*    */ 
/*    */   public int getInt(int row, String name)
/*    */   {
/* 63 */     HiATLParam params = (HiATLParam)this.records.get(row);
/* 64 */     return params.getInt(name);
/*    */   }
/*    */ 
/*    */   public String getValue(int row, int columnIndex)
/*    */   {
/* 72 */     HiATLParam params = (HiATLParam)this.records.get(row);
/* 73 */     return params.getValue(columnIndex);
/*    */   }
/*    */ 
/*    */   public String getValue(int row, String name)
/*    */   {
/* 81 */     HiATLParam params = (HiATLParam)this.records.get(row);
/* 82 */     return params.get(name);
/*    */   }
/*    */ }