 package com.hisun.mon;
 
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiSQLException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import java.io.StringReader;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.util.HashMap;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiDataBaseUtilExt
 {
   public static HashMap readRecord(HiMessageContext ctx, String strSql, String[] varArr, String[] varTypeArr)
     throws HiException
   {
     Connection conn = null;
     PreparedStatement stmt = null;
     ResultSet rs = null;
     HiETF etfBody = ctx.getCurrentMsg().getETFBody();
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     int num = contains(strSql, '?');
     if (num > varArr.length) {
       num = varArr.length;
     }
 
     int type = 0;
     try {
       conn = ctx.getDataBaseUtil().getConnection();
       stmt = conn.prepareStatement(strSql);
       log.debug(Integer.valueOf(num));
       if (num > 0)
       {
         for (int i = 0; i < num; ++i)
         {
           if ((varTypeArr == null) || (i >= varTypeArr.length))
             type = 0;
           else {
             type = NumberUtils.toInt(varTypeArr[i], 0);
           }
           setVarValue(stmt, varArr[i], i + 1, type);
         }
 
       }
 
       HashMap values = new HashMap();
       rs = stmt.executeQuery();
       ResultSetMetaData meta = rs.getMetaData();
       int cols = meta.getColumnCount();
       int index = 0;
       while (rs.next()) {
         for (int i = 0; i < cols; ++i) {
           String strColName = meta.getColumnName(i + 1);
           Object value = rs.getObject(i + 1);
           values.put(strColName.toUpperCase(), 
             String.valueOf(value).trim());
         }
 
         ++index;
       }
       if (index > 1) {
         throw new HiException("215025", 
           "检索数据超出范围");
       }
 
       return values;
     }
     catch (Exception e) {
       if (e instanceof HiException);
       throw new HiSQLException("215025", e, 
         strSql);
     } finally {
       ctx.getDataBaseUtil().close(stmt, rs);
     }
   }
 
   private static void setVarValue(PreparedStatement stmt, String val, int idx, int type)
     throws Exception
   {
     switch (type)
     {
     case 0:
       if (val.length() < 2000) {
         stmt.setString(idx, val); return;
       }
       stmt.setCharacterStream(idx, new StringReader(val), val.length());
 
       break;
     case 1:
       stmt.setInt(idx, Integer.parseInt(val));
       break;
     default:
       if (val.length() < 2000) {
         stmt.setString(idx, val); return;
       }
       stmt.setCharacterStream(idx, new StringReader(val), val.length());
     }
   }
 
   public static int contains(String text, char search)
   {
     int num = 0;
     int idx = 0;
     while (true) {
       idx = StringUtils.indexOf(text, search, idx);
       if (idx == -1) {
         break;
       }
       ++num;
       ++idx;
     }
     return num;
   }
 }