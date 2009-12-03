/*    */ package com.hisun.engine.invoke.load;
/*    */ 
/*    */ import com.hisun.database.HiDataBaseUtil;
/*    */ import com.hisun.engine.HiEngineModel;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiContext;
/*    */ import java.sql.Connection;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class HiTableDeclare extends HiEngineModel
/*    */ {
/*    */   public String getNodeName()
/*    */   {
/* 23 */     return "TableDeclare";
/*    */   }
/*    */ 
/*    */   public void setTableInfo(String strTableName, String strAliasName)
/*    */     throws HiException
/*    */   {
/* 29 */     Connection conn = null;
/*    */     try
/*    */     {
/* 32 */       HiDataBaseUtil db = new HiDataBaseUtil();
/* 33 */       conn = db.getConnection();
/* 34 */       HashMap map = db.getTableMetaData(strTableName, conn);
/* 35 */       HiContext.getCurrentContext().setProperty("TABLEDECLARE", strAliasName, map);
/*    */ 
/* 37 */       HiContext.getCurrentContext().setProperty("TABLEDECLARE", strTableName, map);
/*    */     }
/*    */     catch (HiException e)
/*    */     {
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/*    */     }
/*    */     finally
/*    */     {
/*    */       try
/*    */       {
/* 52 */         if (conn != null)
/* 53 */           conn.close();
/*    */       }
/*    */       catch (Exception e)
/*    */       {
/*    */       }
/*    */     }
/*    */   }
/*    */ }