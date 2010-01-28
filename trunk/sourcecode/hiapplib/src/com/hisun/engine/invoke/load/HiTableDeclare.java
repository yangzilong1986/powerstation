 package com.hisun.engine.invoke.load;
 
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.engine.HiEngineModel;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiContext;
 import java.sql.Connection;
 import java.util.HashMap;
 
 public class HiTableDeclare extends HiEngineModel
 {
   public String getNodeName()
   {
     return "TableDeclare";
   }
 
   public void setTableInfo(String strTableName, String strAliasName)
     throws HiException
   {
     Connection conn = null;
     try
     {
       HiDataBaseUtil db = new HiDataBaseUtil();
       conn = db.getConnection();
       HashMap map = db.getTableMetaData(strTableName, conn);
       HiContext.getCurrentContext().setProperty("TABLEDECLARE", strAliasName, map);
 
       HiContext.getCurrentContext().setProperty("TABLEDECLARE", strTableName, map);
     }
     catch (HiException e)
     {
     }
     catch (Exception e)
     {
     }
     finally
     {
       try
       {
         if (conn != null)
           conn.close();
       }
       catch (Exception e)
       {
       }
     }
   }
 }