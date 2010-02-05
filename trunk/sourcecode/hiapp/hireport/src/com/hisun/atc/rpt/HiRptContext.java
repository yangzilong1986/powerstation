 package com.hisun.atc.rpt;
 
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.hilog4j.Logger;
 import java.io.PrintStream;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 
 public abstract class HiRptContext
 {
   public Map vars;
   public Logger logger;
   public String deli;
   public List datafiles;
   public String trc;
 
   public HiRptContext()
   {
     this.vars = new HashMap();
 
     this.deli = "|";
 
     this.datafiles = new ArrayList();
   }
 
   public abstract String getValueByPos(int paramInt);
 
   public abstract String getValueByName(String paramString);
 
   public abstract HiDataBaseUtil getDBUtil();
 
   public Object getValue(Object name)
   {
     return this.vars.get(name);
   }
 
   public void putValue(Object name, Object value) {
     this.vars.put(name, value);
   }
 
   public void info(String info)
   {
     if (this.logger != null)
       this.logger.info(info);
     else
       System.out.println(info);
   }
 
   public void warn(String warn) {
     if (this.logger != null)
       this.logger.warn(warn);
     else
       System.out.println(warn); 
   }
 
   public void error(String err, Throwable t) {
     if (this.logger != null)
       this.logger.error(err, t);
     else
       System.out.println(err);
   }
 
   public void runtimeException(Throwable t)
   {
     if (t instanceof HiReportRuntimeException)
       throw ((HiReportRuntimeException)t);
     throw new HiReportRuntimeException(t);
   }
 
   public HiDataFile createDataFile(String path)
   {
     HiDataFile file = new HiDataFile(path, this.deli, this.logger);
     this.datafiles.add(file);
     return file;
   }
 
   public void closeDataFile()
   {
     for (int i = 0; i < this.datafiles.size(); ++i) {
       HiDataFile file = (HiDataFile)this.datafiles.get(i);
       file.delete();
       warn("删除中间文件:" + file.getPath());
     }
     this.datafiles.clear();
   }
 }