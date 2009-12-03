/*    */ package com.hisun.data.cache;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.util.HiServiceLocator;
/*    */ import java.sql.Connection;
/*    */ import java.sql.SQLException;
/*    */ import javax.sql.DataSource;
/*    */ 
/*    */ public class HiDBConnection
/*    */ {
/*    */   private DataSource dataSource;
/*    */ 
/*    */   public HiDBConnection()
/*    */   {
/* 17 */     this.dataSource = null; }
/*    */ 
/*    */   public Connection getConnection(String dsName) throws HiException {
/*    */     try { return getDBDataSource(dsName).getConnection();
/*    */     } catch (SQLException e) {
/* 22 */       throw new HiException(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   private DataSource getDBDataSource(String name) throws HiException {
/* 27 */     if (this.dataSource != null)
/* 28 */       return this.dataSource;
/*    */     try
/*    */     {
/* 31 */       this.dataSource = HiServiceLocator.getInstance().getDataSource(name);
/* 32 */       return this.dataSource;
/*    */     } catch (Exception e) {
/* 34 */       throw new HiException(e);
/*    */     }
/*    */   }
/*    */ }