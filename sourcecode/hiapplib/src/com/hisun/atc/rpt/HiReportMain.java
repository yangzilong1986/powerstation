/*     */ package com.hisun.atc.rpt;
/*     */ 
/*     */ import com.hisun.atc.rpt.xml.HiReportConfig;
/*     */ import com.hisun.atc.rpt.xml.HiReportConfigParser;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.exception.HiSQLException;
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import java.sql.Connection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.naming.Context;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.sql.DataSource;
/*     */ 
/*     */ public class HiReportMain
/*     */ {
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/*  38 */     if (args.length < 2) {
/*  39 */       log("usage:HiReportMain [-jndi jndiname] [-d] rptname xmlfile [field1][field2]...");
/*  40 */       return;
/*     */     }
/*     */ 
/*  43 */     int argIdx = 0;
/*     */ 
/*  45 */     String jndi = null;
/*  46 */     if (args[argIdx].equals("-jndi")) {
/*  47 */       jndi = args[(++argIdx)];
/*     */     }
/*     */ 
/*  50 */     ++argIdx;
/*     */ 
/*  52 */     boolean bDebug = false;
/*  53 */     if (args[argIdx].equals("-d")) {
/*  54 */       bDebug = true;
/*  55 */       ++argIdx;
/*     */     }
/*     */ 
/*  58 */     String rptname = args[(argIdx++)];
/*  59 */     String xmlfile = args[(argIdx++)];
/*     */ 
/*  61 */     HiCmdLineContext cmdctx = new HiCmdLineContext();
/*  62 */     cmdctx.put("RptName", rptname);
/*  63 */     cmdctx.put("RptFile", xmlfile);
/*  64 */     for (int i = 1; argIdx < args.length; ++i) {
/*  65 */       cmdctx.put(i, args[argIdx]);
/*     */ 
/*  64 */       ++argIdx;
/*     */     }
/*     */ 
/*  68 */     if (jndi != null) {
/*  69 */       cmdctx.setDBUtil(new HiJNDIDataBaseUtil(jndi));
/*     */     }
/*  71 */     if (bDebug)
/*  72 */       cmdctx.trc = "DEBUG";
/*     */     else {
/*  74 */       cmdctx.trc = "ERROR";
/*     */     }
/*  76 */     URL url = new File(xmlfile).toURL();
/*  77 */     HiReportConfig config = new HiReportConfigParser().parse(url);
/*  78 */     config.process(cmdctx, HiReportUtil.getFullPath(rptname));
/*     */   }
/*     */ 
/*     */   private static void log(String str)
/*     */   {
/*  84 */     System.out.println(str);
/*     */   }
/*     */ 
/*     */   public static class HiJNDIDataBaseUtil extends HiDataBaseUtil
/*     */   {
/*     */     private String jnid;
/*     */     private Connection connection;
/*     */ 
/*     */     public HiJNDIDataBaseUtil(String jnid)
/*     */     {
/* 129 */       this.jnid = jnid;
/*     */     }
/*     */ 
/*     */     public Connection getConnection() throws HiException {
/* 133 */       if (this.connection != null)
/* 134 */         return this.connection;
/*     */       try
/*     */       {
/* 137 */         Context ctx = new InitialContext();
/* 138 */         Object datasourceRef = ctx.lookup(this.jnid);
/*     */ 
/* 140 */         DataSource ds = (DataSource)datasourceRef;
/* 141 */         this.connection = ds.getConnection();
/*     */       } catch (Exception e) {
/* 143 */         throw new HiSQLException("215022", e, "");
/*     */       }
/*     */ 
/* 146 */       return this.connection;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class HiCmdLineContext extends HiRptContext
/*     */   {
/*     */     private HiDataBaseUtil dbutil;
/*     */     private Map map;
/*     */     String[] list;
/*     */ 
/*     */     public HiCmdLineContext()
/*     */     {
/*  90 */       this.map = new HashMap();
/*  91 */       this.list = new String[20]; }
/*     */ 
/*     */     public void setDBUtil(HiDataBaseUtil dbutil) {
/*  94 */       this.dbutil = dbutil;
/*     */     }
/*     */ 
/*     */     public HiDataBaseUtil getDBUtil() {
/*  98 */       return this.dbutil;
/*     */     }
/*     */ 
/*     */     public String getValueByName(String name) {
/* 102 */       return ((String)this.map.get(name));
/*     */     }
/*     */ 
/*     */     public String getValueByPos(int pos) {
/* 106 */       return this.list[pos];
/*     */     }
/*     */ 
/*     */     public void put(String key, String value) {
/* 110 */       this.map.put(key, value);
/*     */     }
/*     */ 
/*     */     public void put(int index, String value) {
/* 114 */       this.list[index] = value;
/*     */     }
/*     */   }
/*     */ }