 package com.hisun.atc.rpt;
 
 import com.hisun.atc.rpt.xml.HiReportConfig;
 import com.hisun.atc.rpt.xml.HiReportConfigParser;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiSQLException;
 import java.io.File;
 import java.io.PrintStream;
 import java.net.URL;
 import java.sql.Connection;
 import java.util.HashMap;
 import java.util.Map;
 import javax.naming.Context;
 import javax.naming.InitialContext;
 import javax.sql.DataSource;
 
 public class HiReportMain
 {
   public static void main(String[] args)
     throws Exception
   {
     if (args.length < 2) {
       log("usage:HiReportMain [-jndi jndiname] [-d] rptname xmlfile [field1][field2]...");
       return;
     }
 
     int argIdx = 0;
 
     String jndi = null;
     if (args[argIdx].equals("-jndi")) {
       jndi = args[(++argIdx)];
     }
 
     ++argIdx;
 
     boolean bDebug = false;
     if (args[argIdx].equals("-d")) {
       bDebug = true;
       ++argIdx;
     }
 
     String rptname = args[(argIdx++)];
     String xmlfile = args[(argIdx++)];
 
     HiCmdLineContext cmdctx = new HiCmdLineContext();
     cmdctx.put("RptName", rptname);
     cmdctx.put("RptFile", xmlfile);
     for (int i = 1; argIdx < args.length; ++i) {
       cmdctx.put(i, args[argIdx]);
 
       ++argIdx;
     }
 
     if (jndi != null) {
       cmdctx.setDBUtil(new HiJNDIDataBaseUtil(jndi));
     }
     if (bDebug)
       cmdctx.trc = "DEBUG";
     else {
       cmdctx.trc = "ERROR";
     }
     URL url = new File(xmlfile).toURL();
     HiReportConfig config = new HiReportConfigParser().parse(url);
     config.process(cmdctx, HiReportUtil.getFullPath(rptname));
   }
 
   private static void log(String str)
   {
     System.out.println(str);
   }
 
   public static class HiJNDIDataBaseUtil extends HiDataBaseUtil
   {
     private String jnid;
     private Connection connection;
 
     public HiJNDIDataBaseUtil(String jnid)
     {
       this.jnid = jnid;
     }
 
     public Connection getConnection() throws HiException {
       if (this.connection != null)
         return this.connection;
       try
       {
         Context ctx = new InitialContext();
         Object datasourceRef = ctx.lookup(this.jnid);
 
         DataSource ds = (DataSource)datasourceRef;
         this.connection = ds.getConnection();
       } catch (Exception e) {
         throw new HiSQLException("215022", e, "");
       }
 
       return this.connection;
     }
   }
 
   public static class HiCmdLineContext extends HiRptContext
   {
     private HiDataBaseUtil dbutil;
     private Map map;
     String[] list;
 
     public HiCmdLineContext()
     {
       this.map = new HashMap();
       this.list = new String[20]; }
 
     public void setDBUtil(HiDataBaseUtil dbutil) {
       this.dbutil = dbutil;
     }
 
     public HiDataBaseUtil getDBUtil() {
       return this.dbutil;
     }
 
     public String getValueByName(String name) {
       return ((String)this.map.get(name));
     }
 
     public String getValueByPos(int pos) {
       return this.list[pos];
     }
 
     public void put(String key, String value) {
       this.map.put(key, value);
     }
 
     public void put(int index, String value) {
       this.list[index] = value;
     }
   }
 }