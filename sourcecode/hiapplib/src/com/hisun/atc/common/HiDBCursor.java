 package com.hisun.atc.common;
 
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringUtils;
 import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.ResultSetMetaData;
 import java.sql.SQLException;
 import java.util.HashMap;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 
 public class HiDBCursor
 {
   public int ret = 0;
 
   private Connection conn = null;
 
   private PreparedStatement stmt = null;
 
   private ResultSet rs = null;
 
   private ResultSetMetaData meta = null;
 
   private int colNum = 0;
 
   private HiMessageContext tranData = null;
 
   public HiDBCursor(HiMessage mess, String strSql, HiMessageContext tranData) throws HiException
   {
     Logger log = HiLog.getLogger(mess);
     if (log.isInfoEnabled())
       log.info(strSql);
     try
     {
       this.tranData = tranData;
       this.conn = tranData.getDataBaseUtil().getConnection();
       if (log.isInfoEnabled()) {
         log.info("HiDBCursor.strSql:[" + strSql + "]");
       }
       this.stmt = this.conn.prepareStatement(strSql);
       this.stmt.setFetchSize(1000);
       this.rs = this.stmt.executeQuery();
       if (this.rs != null) {
         this.meta = this.rs.getMetaData();
         this.colNum = this.meta.getColumnCount();
       }
     }
     catch (Exception e) {
       tranData.getDataBaseUtil().close(this.stmt);
       throw HiException.makeException("220205", e);
     }
   }
 
   public HiDBCursor(HiMessage mess, String strSql, List paramList, HiMessageContext tranData)
     throws HiException
   {
     try
     {
       this.tranData = tranData;
       strSql = HiStringUtils.format(strSql, paramList);
 
       Logger log = HiLog.getLogger(mess);
       if (log.isInfoEnabled()) {
         log.info("HiDBCursor.strSql:[" + strSql + "]");
       }
       this.conn = tranData.getDataBaseUtil().getConnection();
       this.stmt = this.conn.prepareStatement(strSql);
       this.stmt.setFetchSize(1000);
 
       this.rs = this.stmt.executeQuery();
       if (this.rs != null) {
         this.meta = this.rs.getMetaData();
         this.colNum = this.meta.getColumnCount();
       }
     } catch (Exception e) {
       tranData.getDataBaseUtil().close(this.stmt);
 
       throw HiException.makeException("220205", e);
     }
   }
 
   public HiDBCursor(HiMessageContext ctx, String strSql, List paramList) throws HiException {
     try {
       this.tranData = ctx;
       Logger log = HiLog.getLogger(ctx.getCurrentMsg());
       if (log.isInfoEnabled()) {
         log.info("HiDBCursor.strSql:[" + strSql + "]");
       }
       this.conn = this.tranData.getDataBaseUtil().getConnection();
       this.stmt = this.conn.prepareStatement(strSql);
       this.stmt.setFetchSize(1000);
 
       for (int i = 0; (paramList != null) && (i < paramList.size()); ++i) {
         String value = (String)paramList.get(i);
         if (log.isInfoEnabled()) {
           log.info("[" + i + "]:" + value);
         }
         this.stmt.setObject(i + 1, value);
       }
 
       this.rs = this.stmt.executeQuery();
       if (this.rs != null) {
         this.meta = this.rs.getMetaData();
         this.colNum = this.meta.getColumnCount();
       }
     } catch (Exception e) {
       ctx.getDataBaseUtil().close(this.stmt);
       throw HiException.makeException("220205", e);
     }
   }
 
   public HashMap next() throws HiException
   {
     if (this.rs == null)
     {
       throw new HiException("220206", "当前ResultSet为null");
     }
 
     try
     {
       if (this.rs.next()) {
         HashMap values = new HashMap();
         for (int i = 0; i < this.colNum; ++i) {
           String strColName = this.meta.getColumnName(i + 1);
           String colVal = this.rs.getString(i + 1);
           if (colVal == null) {
             colVal = "";
           }
           values.put(strColName.toUpperCase(), StringUtils.trim(colVal));
         }
 
         return values;
       }
       return null;
     }
     catch (SQLException e)
     {
       throw new HiException("220206", "cursor:next", e);
     }
   }
 
   public void close() throws HiException
   {
     try {
       if (this.rs != null) {
         this.rs.close();
       }
       if (this.stmt != null)
         this.stmt.close();
     }
     catch (SQLException e)
     {
       throw new HiException("220206", "关闭失败!", e);
     }
   }
 }