/*    */ package com.hisun.data.cache;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.LinkedHashMap;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiDataCacheConfigItem
/*    */ {
/*    */   private String sql;
/*    */   private String file;
/*    */   private String id;
/*    */   private String key;
/*    */   private String className;
/*    */   private LinkedHashMap<String, String> colMaps;
/*    */ 
/*    */   public HiDataCacheConfigItem()
/*    */   {
/* 21 */     this.colMaps = new LinkedHashMap(); }
/*    */ 
/*    */   boolean isSql() {
/* 24 */     return (!(StringUtils.isBlank(this.sql)));
/*    */   }
/*    */ 
/*    */   boolean isFile() {
/* 28 */     return (!(StringUtils.isBlank(this.file)));
/*    */   }
/*    */ 
/*    */   public String getKey() {
/* 32 */     return this.key;
/*    */   }
/*    */ 
/*    */   public void setKey(String key) {
/* 36 */     this.key = key;
/*    */   }
/*    */ 
/*    */   public String getSql() {
/* 40 */     return this.sql; }
/*    */ 
/*    */   public void setSql(String sql) {
/* 43 */     this.sql = sql; }
/*    */ 
/*    */   public String getFile() {
/* 46 */     return this.file; }
/*    */ 
/*    */   public void setFile(String file) {
/* 49 */     this.file = file; }
/*    */ 
/*    */   public String getId() {
/* 52 */     return this.id; }
/*    */ 
/*    */   public void setId(String id) {
/* 55 */     this.id = id;
/*    */   }
/*    */ 
/*    */   public void setColInfo(String srcCol, String dstCol) {
/* 59 */     this.colMaps.put(srcCol, dstCol);
/*    */   }
/*    */ 
/*    */   public String getClassName() {
/* 63 */     return this.className;
/*    */   }
/*    */ 
/*    */   public void setClassName(String className) {
/* 67 */     this.className = className;
/*    */   }
/*    */ 
/*    */   public Object getNewObject() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
/* 71 */     super.getClass(); return Class.forName(this.className).newInstance();
/*    */   }
/*    */ 
/*    */   public HashMap<String, String> getColMaps() {
/* 75 */     return this.colMaps;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 79 */     return String.format("[%s][%s][%s][%s][%s][%s]", new Object[] { this.sql, this.file, this.id, this.key, this.className, this.colMaps });
/*    */   }
/*    */ }