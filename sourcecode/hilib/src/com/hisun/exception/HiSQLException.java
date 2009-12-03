/*    */ package com.hisun.exception;
/*    */ 
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class HiSQLException extends HiException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private String strSql;
/* 23 */   private int retCode = -1;
/*    */   private String SQLState;
/*    */   private int errorCode;
/*    */ 
/*    */   public HiSQLException(String code, Throwable ex, String strSql)
/*    */   {
/* 27 */     super(code, strSql, ex);
/* 28 */     if (ex instanceof SQLException) {
/* 29 */       this.SQLState = ((SQLException)ex).getSQLState();
/* 30 */       this.errorCode = ((SQLException)ex).getErrorCode();
/*    */     }
/* 32 */     this.strSql = strSql;
/* 33 */     writeLog();
/*    */   }
/*    */ 
/*    */   public HiSQLException(String code, SQLException ex, String strSql)
/*    */   {
/* 43 */     super(code, strSql, ex);
/* 44 */     this.strSql = strSql;
/* 45 */     this.SQLState = ex.getSQLState();
/* 46 */     this.errorCode = ex.getErrorCode();
/* 47 */     writeLog();
/*    */   }
/*    */ 
/*    */   public String getSQLState() {
/* 51 */     return this.SQLState;
/*    */   }
/*    */ 
/*    */   public void setRetCode(int retCode) {
/* 55 */     this.retCode = retCode;
/*    */   }
/*    */ 
/*    */   public int getRetCode() {
/* 59 */     return this.retCode;
/*    */   }
/*    */ 
/*    */   public int getSqlErrorCode() {
/* 63 */     return this.errorCode;
/*    */   }
/*    */ 
/*    */   public void writeLog() {
/* 67 */     Logger log = HiLog.getErrorLogger("database.log");
/*    */ 
/* 69 */     log.error(this.strSql, this);
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 73 */     StringBuffer result = new StringBuffer();
/* 74 */     result.append(super.toString());
/* 75 */     result.append(", ErrorCode:[");
/* 76 */     result.append(this.errorCode);
/* 77 */     result.append("], SQLState:[");
/* 78 */     result.append(this.SQLState);
/* 79 */     result.append("]");
/* 80 */     return result.toString();
/*    */   }
/*    */ }