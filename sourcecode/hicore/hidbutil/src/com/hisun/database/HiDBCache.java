 package com.hisun.database;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.util.HiICSProperty;
 import java.util.Arrays;
 import java.util.Collection;
 import java.util.HashMap;
 import java.util.HashSet;
 import java.util.List;
 import java.util.Set;
 import org.apache.commons.lang.StringUtils;
 
 public class HiDBCache
 {
   private static final Logger log = HiLog.getLogger("dbcache.trc");
   private static final int cachesize = 10000;
   private static Collection tables;
   private static boolean bCache = false;
   public static LRUMap caches;
 
   public static void discard()
   {
     caches.clear();
   }
 
   public static void discard(String table)
   {
     discard();
   }
 
   public static List execQuery(String strSql, HiJDBCProvider dbutil) throws HiException
   {
     if (!(bCache)) {
       return dbutil.internal_execQuery(strSql);
     }
     Collection tb = HiSqlParser.parse_select(strSql);
     if (log.isDebugEnabled()) {
       log.debug("sql:" + strSql);
       log.debug("tables:" + Arrays.toString(tb.toArray()));
     }
     if (tables.containsAll(tb))
     {
       if (log.isDebugEnabled()) {
         log.debug("use cache for sql:" + strSql);
       }
       if (caches.containsKey(strSql)) {
         return ((List)caches.get(strSql));
       }
 
       ret = dbutil.internal_execQuery(strSql);
 
       caches.put(strSql, ret);
       if (log.isDebugEnabled()) {
         log.debug("save cache for sql:" + strSql);
       }
       return ret;
     }
 
     List ret = dbutil.internal_execQuery(strSql);
     return ret;
   }
 
   public static List execQuery(String strSql, int limits, HiJDBCProvider dbutil)
     throws HiException
   {
     if (!(bCache)) {
       return dbutil.internal_execQuery(strSql, limits);
     }
     Collection tb = HiSqlParser.parse_select(strSql);
     if (log.isDebugEnabled()) {
       log.debug("sql:" + strSql);
       log.debug("tables:" + Arrays.toString(tb.toArray()));
     }
     if (tables.containsAll(tb))
     {
       if (log.isDebugEnabled()) {
         log.debug("use cache for sql:" + strSql);
       }
       if (caches.containsKey(strSql)) {
         return ((List)caches.get(strSql));
       }
 
       ret = dbutil.internal_execQuery(strSql, limits);
 
       caches.put(strSql, ret);
       if (log.isDebugEnabled()) {
         log.debug("save cache for sql:" + strSql);
       }
       return ret;
     }
 
     List ret = dbutil.internal_execQuery(strSql, limits);
     return ret;
   }
 
   public static int execUpdate(String strSql, HiJDBCProvider dbutil)
     throws HiException
   {
     if (!(bCache)) {
       return dbutil.internal_execUpdate(strSql);
     }
     if (log.isDebugEnabled()) {
       log.debug("update sql:" + strSql);
     }
     String tb = HiSqlParser.parse_exec(strSql);
     if (log.isDebugEnabled()) {
       log.debug("table:" + tb);
     }
 
     int ret = dbutil.internal_execUpdate(strSql);
 
     if (tables.contains(tb))
     {
       discard(tb);
       if (log.isDebugEnabled()) {
         log.debug("discard cache for sql:" + strSql);
       }
     }
     return ret;
   }
 
   public static HashMap readRecord(String strSql, HiJDBCProvider dbutil) throws HiException
   {
     if (!(bCache)) {
       return dbutil.internal_readRecord(strSql);
     }
     Collection tb = HiSqlParser.parse_select(strSql);
     if (log.isDebugEnabled()) {
       log.debug("sql:" + strSql);
       log.debug("tables:" + Arrays.toString(tb.toArray()));
     }
     if (tables.containsAll(tb))
     {
       if (log.isDebugEnabled()) {
         log.debug("use cache for sql:" + strSql);
       }
       if (caches.containsKey(strSql)) {
         return ((HashMap)caches.get(strSql));
       }
 
       ret = dbutil.internal_readRecord(strSql);
 
       caches.put(strSql, ret);
       if (log.isDebugEnabled()) {
         log.debug("save cache for sql:" + strSql);
       }
       return ret;
     }
 
     HashMap ret = dbutil.internal_readRecord(strSql);
     return ret;
   }
 
   public static HiResultSet execQuerySQL(String strSql, HiJDBCProvider dbutil)
     throws HiException
   {
     if (!(bCache)) {
       return dbutil.internal_execQuerySQL(strSql);
     }
     Collection tb = HiSqlParser.parse_select(strSql);
     if (log.isDebugEnabled()) {
       log.debug("sql:" + strSql);
       log.debug("tables:" + Arrays.toString(tb.toArray()));
     }
     if (tables.containsAll(tb))
     {
       if (log.isDebugEnabled()) {
         log.debug("use cache for sql:" + strSql);
       }
       if (caches.containsKey(strSql)) {
         return ((HiResultSet)caches.get(strSql));
       }
 
       ret = dbutil.internal_execQuerySQL(strSql);
 
       caches.put(strSql, ret);
       if (log.isDebugEnabled()) {
         log.debug("save cache for sql:" + strSql);
       }
       return ret;
     }
 
     HiResultSet ret = dbutil.internal_execQuerySQL(strSql);
     return ret;
   }
 
   private static void loadProperties()
   {
     String sbcache = HiICSProperty.getProperty("sys.cacheenable");
     if (!(StringUtils.isEmpty(sbcache))) {
       bCache = StringUtils.equalsIgnoreCase("true", sbcache);
     }
 
     if (!(bCache)) {
       return;
     }
 
     String stables = HiICSProperty.getProperty("sys.cachetables");
     if (!(StringUtils.isEmpty(stables))) {
       log.info("sys.cachetables = " + stables);
       String[] tbs = StringUtils.split(stables, ',');
       Set tbset = new HashSet();
       for (int i = 0; i < tbs.length; ++i)
         tbset.add(tbs[i].toUpperCase());
       tables = tbset;
     }
 
     String size = HiICSProperty.getProperty("sys.cachesize");
     if (!(StringUtils.isEmpty(size))) {
       log.info("sys.cachesize = " + size);
       caches = new LRUMap(Integer.parseInt(size));
     }
   }
 
   static
   {
     loadProperties();
   }
 }