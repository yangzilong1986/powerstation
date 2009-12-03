/*     */ package com.hisun.database;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiDBCache
/*     */ {
/*  20 */   private static final Logger log = HiLog.getLogger("dbcache.trc");
/*     */   private static final int cachesize = 10000;
/*     */   private static Collection tables;
/*  25 */   private static boolean bCache = false;
/*     */   public static LRUMap caches;
/*     */ 
/*     */   public static void discard()
/*     */   {
/*  36 */     caches.clear();
/*     */   }
/*     */ 
/*     */   public static void discard(String table)
/*     */   {
/*  42 */     discard();
/*     */   }
/*     */ 
/*     */   public static List execQuery(String strSql, HiJDBCProvider dbutil) throws HiException
/*     */   {
/*  47 */     if (!(bCache)) {
/*  48 */       return dbutil.internal_execQuery(strSql);
/*     */     }
/*  50 */     Collection tb = HiSqlParser.parse_select(strSql);
/*  51 */     if (log.isDebugEnabled()) {
/*  52 */       log.debug("sql:" + strSql);
/*  53 */       log.debug("tables:" + Arrays.toString(tb.toArray()));
/*     */     }
/*  55 */     if (tables.containsAll(tb))
/*     */     {
/*  57 */       if (log.isDebugEnabled()) {
/*  58 */         log.debug("use cache for sql:" + strSql);
/*     */       }
/*  60 */       if (caches.containsKey(strSql)) {
/*  61 */         return ((List)caches.get(strSql));
/*     */       }
/*     */ 
/*  64 */       ret = dbutil.internal_execQuery(strSql);
/*     */ 
/*  66 */       caches.put(strSql, ret);
/*  67 */       if (log.isDebugEnabled()) {
/*  68 */         log.debug("save cache for sql:" + strSql);
/*     */       }
/*  70 */       return ret;
/*     */     }
/*     */ 
/*  74 */     List ret = dbutil.internal_execQuery(strSql);
/*  75 */     return ret;
/*     */   }
/*     */ 
/*     */   public static List execQuery(String strSql, int limits, HiJDBCProvider dbutil)
/*     */     throws HiException
/*     */   {
/*  81 */     if (!(bCache)) {
/*  82 */       return dbutil.internal_execQuery(strSql, limits);
/*     */     }
/*  84 */     Collection tb = HiSqlParser.parse_select(strSql);
/*  85 */     if (log.isDebugEnabled()) {
/*  86 */       log.debug("sql:" + strSql);
/*  87 */       log.debug("tables:" + Arrays.toString(tb.toArray()));
/*     */     }
/*  89 */     if (tables.containsAll(tb))
/*     */     {
/*  91 */       if (log.isDebugEnabled()) {
/*  92 */         log.debug("use cache for sql:" + strSql);
/*     */       }
/*  94 */       if (caches.containsKey(strSql)) {
/*  95 */         return ((List)caches.get(strSql));
/*     */       }
/*     */ 
/*  98 */       ret = dbutil.internal_execQuery(strSql, limits);
/*     */ 
/* 100 */       caches.put(strSql, ret);
/* 101 */       if (log.isDebugEnabled()) {
/* 102 */         log.debug("save cache for sql:" + strSql);
/*     */       }
/* 104 */       return ret;
/*     */     }
/*     */ 
/* 108 */     List ret = dbutil.internal_execQuery(strSql, limits);
/* 109 */     return ret;
/*     */   }
/*     */ 
/*     */   public static int execUpdate(String strSql, HiJDBCProvider dbutil)
/*     */     throws HiException
/*     */   {
/* 115 */     if (!(bCache)) {
/* 116 */       return dbutil.internal_execUpdate(strSql);
/*     */     }
/* 118 */     if (log.isDebugEnabled()) {
/* 119 */       log.debug("update sql:" + strSql);
/*     */     }
/* 121 */     String tb = HiSqlParser.parse_exec(strSql);
/* 122 */     if (log.isDebugEnabled()) {
/* 123 */       log.debug("table:" + tb);
/*     */     }
/*     */ 
/* 126 */     int ret = dbutil.internal_execUpdate(strSql);
/*     */ 
/* 128 */     if (tables.contains(tb))
/*     */     {
/* 130 */       discard(tb);
/* 131 */       if (log.isDebugEnabled()) {
/* 132 */         log.debug("discard cache for sql:" + strSql);
/*     */       }
/*     */     }
/* 135 */     return ret;
/*     */   }
/*     */ 
/*     */   public static HashMap readRecord(String strSql, HiJDBCProvider dbutil) throws HiException
/*     */   {
/* 140 */     if (!(bCache)) {
/* 141 */       return dbutil.internal_readRecord(strSql);
/*     */     }
/* 143 */     Collection tb = HiSqlParser.parse_select(strSql);
/* 144 */     if (log.isDebugEnabled()) {
/* 145 */       log.debug("sql:" + strSql);
/* 146 */       log.debug("tables:" + Arrays.toString(tb.toArray()));
/*     */     }
/* 148 */     if (tables.containsAll(tb))
/*     */     {
/* 150 */       if (log.isDebugEnabled()) {
/* 151 */         log.debug("use cache for sql:" + strSql);
/*     */       }
/* 153 */       if (caches.containsKey(strSql)) {
/* 154 */         return ((HashMap)caches.get(strSql));
/*     */       }
/*     */ 
/* 157 */       ret = dbutil.internal_readRecord(strSql);
/*     */ 
/* 159 */       caches.put(strSql, ret);
/* 160 */       if (log.isDebugEnabled()) {
/* 161 */         log.debug("save cache for sql:" + strSql);
/*     */       }
/* 163 */       return ret;
/*     */     }
/*     */ 
/* 167 */     HashMap ret = dbutil.internal_readRecord(strSql);
/* 168 */     return ret;
/*     */   }
/*     */ 
/*     */   public static HiResultSet execQuerySQL(String strSql, HiJDBCProvider dbutil)
/*     */     throws HiException
/*     */   {
/* 174 */     if (!(bCache)) {
/* 175 */       return dbutil.internal_execQuerySQL(strSql);
/*     */     }
/* 177 */     Collection tb = HiSqlParser.parse_select(strSql);
/* 178 */     if (log.isDebugEnabled()) {
/* 179 */       log.debug("sql:" + strSql);
/* 180 */       log.debug("tables:" + Arrays.toString(tb.toArray()));
/*     */     }
/* 182 */     if (tables.containsAll(tb))
/*     */     {
/* 184 */       if (log.isDebugEnabled()) {
/* 185 */         log.debug("use cache for sql:" + strSql);
/*     */       }
/* 187 */       if (caches.containsKey(strSql)) {
/* 188 */         return ((HiResultSet)caches.get(strSql));
/*     */       }
/*     */ 
/* 191 */       ret = dbutil.internal_execQuerySQL(strSql);
/*     */ 
/* 193 */       caches.put(strSql, ret);
/* 194 */       if (log.isDebugEnabled()) {
/* 195 */         log.debug("save cache for sql:" + strSql);
/*     */       }
/* 197 */       return ret;
/*     */     }
/*     */ 
/* 201 */     HiResultSet ret = dbutil.internal_execQuerySQL(strSql);
/* 202 */     return ret;
/*     */   }
/*     */ 
/*     */   private static void loadProperties()
/*     */   {
/* 207 */     String sbcache = HiICSProperty.getProperty("sys.cacheenable");
/* 208 */     if (!(StringUtils.isEmpty(sbcache))) {
/* 209 */       bCache = StringUtils.equalsIgnoreCase("true", sbcache);
/*     */     }
/*     */ 
/* 212 */     if (!(bCache)) {
/* 213 */       return;
/*     */     }
/*     */ 
/* 216 */     String stables = HiICSProperty.getProperty("sys.cachetables");
/* 217 */     if (!(StringUtils.isEmpty(stables))) {
/* 218 */       log.info("sys.cachetables = " + stables);
/* 219 */       String[] tbs = StringUtils.split(stables, ',');
/* 220 */       Set tbset = new HashSet();
/* 221 */       for (int i = 0; i < tbs.length; ++i)
/* 222 */         tbset.add(tbs[i].toUpperCase());
/* 223 */       tables = tbset;
/*     */     }
/*     */ 
/* 226 */     String size = HiICSProperty.getProperty("sys.cachesize");
/* 227 */     if (!(StringUtils.isEmpty(size))) {
/* 228 */       log.info("sys.cachesize = " + size);
/* 229 */       caches = new LRUMap(Integer.parseInt(size));
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  28 */     loadProperties();
/*     */   }
/*     */ }