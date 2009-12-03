/*    */ package com.hisun.atc.rpt;
/*    */ 
/*    */ import com.hisun.database.HiDataBaseUtil;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import java.io.PrintStream;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public abstract class HiRptContext
/*    */ {
/*    */   public Map vars;
/*    */   public Logger logger;
/*    */   public String deli;
/*    */   public List datafiles;
/*    */   public String trc;
/*    */ 
/*    */   public HiRptContext()
/*    */   {
/* 28 */     this.vars = new HashMap();
/*    */ 
/* 74 */     this.deli = "|";
/*    */ 
/* 92 */     this.datafiles = new ArrayList();
/*    */   }
/*    */ 
/*    */   public abstract String getValueByPos(int paramInt);
/*    */ 
/*    */   public abstract String getValueByName(String paramString);
/*    */ 
/*    */   public abstract HiDataBaseUtil getDBUtil();
/*    */ 
/*    */   public Object getValue(Object name)
/*    */   {
/* 34 */     return this.vars.get(name);
/*    */   }
/*    */ 
/*    */   public void putValue(Object name, Object value) {
/* 38 */     this.vars.put(name, value);
/*    */   }
/*    */ 
/*    */   public void info(String info)
/*    */   {
/* 44 */     if (this.logger != null)
/* 45 */       this.logger.info(info);
/*    */     else
/* 47 */       System.out.println(info);
/*    */   }
/*    */ 
/*    */   public void warn(String warn) {
/* 51 */     if (this.logger != null)
/* 52 */       this.logger.warn(warn);
/*    */     else
/* 54 */       System.out.println(warn); 
/*    */   }
/*    */ 
/*    */   public void error(String err, Throwable t) {
/* 57 */     if (this.logger != null)
/* 58 */       this.logger.error(err, t);
/*    */     else
/* 60 */       System.out.println(err);
/*    */   }
/*    */ 
/*    */   public void runtimeException(Throwable t)
/*    */   {
/* 69 */     if (t instanceof HiReportRuntimeException)
/* 70 */       throw ((HiReportRuntimeException)t);
/* 71 */     throw new HiReportRuntimeException(t);
/*    */   }
/*    */ 
/*    */   public HiDataFile createDataFile(String path)
/*    */   {
/* 77 */     HiDataFile file = new HiDataFile(path, this.deli, this.logger);
/* 78 */     this.datafiles.add(file);
/* 79 */     return file;
/*    */   }
/*    */ 
/*    */   public void closeDataFile()
/*    */   {
/* 84 */     for (int i = 0; i < this.datafiles.size(); ++i) {
/* 85 */       HiDataFile file = (HiDataFile)this.datafiles.get(i);
/* 86 */       file.delete();
/* 87 */       warn("删除中间文件:" + file.getPath());
/*    */     }
/* 89 */     this.datafiles.clear();
/*    */   }
/*    */ }