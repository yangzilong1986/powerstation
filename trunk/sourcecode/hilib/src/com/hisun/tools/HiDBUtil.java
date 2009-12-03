/*    */ package com.hisun.tools;
/*    */ 
/*    */ import com.hisun.message.HiContext;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Hashtable;
/*    */ import javax.naming.InitialContext;
/*    */ import javax.sql.DataSource;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiDBUtil
/*    */ {
/*    */   private Connection _conn;
/*    */ 
/*    */   public Connection getConnection()
/*    */     throws Exception
/*    */   {
/* 27 */     if (this._conn != null)
/* 28 */       return this._conn;
/* 29 */     InitialContext ic = getContext();
/* 30 */     DataSource ds = (DataSource)ic.lookup(HiContext.getRootContext().getStrProp("@PARA", "_DB_NAME"));
/*    */ 
/* 32 */     this._conn = ds.getConnection();
/* 33 */     this._conn.setAutoCommit(false);
/* 34 */     return this._conn;
/*    */   }
/*    */ 
/*    */   public InitialContext getContext() throws Exception {
/* 38 */     Hashtable env = new Hashtable();
/* 39 */     HiContext ctx = HiContext.getRootContext();
/*    */ 
/* 41 */     String value = (String)ctx.getProperty("@PARA", "_INITIAL_CONTEXT_FACTORY");
/*    */ 
/* 43 */     if (StringUtils.isEmpty(value)) {
/* 44 */       throw new Exception("_INITIAL_CONTEXT_FACTORY is empty");
/*    */     }
/* 46 */     env.put("java.naming.factory.initial", value);
/*    */ 
/* 48 */     value = (String)ctx.getProperty("@PARA", "_PROVIDER_URL");
/*    */ 
/* 50 */     if (StringUtils.isEmpty(value)) {
/* 51 */       throw new Exception("_PROVIDER_URL is empty");
/*    */     }
/* 53 */     env.put("java.naming.provider.url", value);
/* 54 */     InitialContext ic = new InitialContext(env);
/* 55 */     return ic;
/*    */   }
/*    */ 
/*    */   public int execUpdate(String sql)
/*    */     throws Exception
/*    */   {
/* 66 */     this._conn = getConnection();
/* 67 */     PreparedStatement stmt = this._conn.prepareStatement(sql);
/* 68 */     return stmt.executeUpdate();
/*    */   }
/*    */ 
/*    */   public void commit() throws SQLException {
/* 72 */     if (this._conn == null)
/* 73 */       return;
/* 74 */     this._conn.commit();
/*    */   }
/*    */ 
/*    */   public void rollback() throws SQLException {
/* 78 */     if (this._conn == null)
/* 79 */       return;
/* 80 */     this._conn.rollback();
/*    */   }
/*    */ }