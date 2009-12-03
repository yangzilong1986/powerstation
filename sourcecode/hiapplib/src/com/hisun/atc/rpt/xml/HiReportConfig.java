/*     */ package com.hisun.atc.rpt.xml;
/*     */ 
/*     */ import com.hisun.atc.rpt.HiDataFile;
/*     */ import com.hisun.atc.rpt.HiReportConstants;
/*     */ import com.hisun.atc.rpt.HiRptContext;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.xml.Located;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Level;
/*     */ 
/*     */ public class HiReportConfig extends Located
/*     */   implements HiReportConstants
/*     */ {
/*     */   public HiVarDefNode vardef;
/*     */   public HiMsgConvertNode convert;
/*     */   private boolean db_connect;
/*     */   private String deli;
/*     */   public HiDataSourceNode ds;
/*     */   public HiFormatDefineNode fmt;
/*     */   public Logger log;
/*     */   private String rptNAME;
/*     */   private String trclog;
/*     */ 
/*     */   public HiReportConfig()
/*     */   {
/*  25 */     this.deli = "|";
/*     */ 
/*  33 */     this.trclog = "HIRPT.trc"; }
/*     */ 
/*     */   public String getDeli() {
/*  36 */     return this.deli;
/*     */   }
/*     */ 
/*     */   public String getTrclog() {
/*  40 */     return this.trclog;
/*     */   }
/*     */ 
/*     */   private void initGlobalValue(HiRptContext ctx)
/*     */   {
/*  51 */     Map vars = ctx.vars;
/*  52 */     vars.put("RPTNAME", this.rptNAME);
/*     */ 
/*  54 */     ctx.info("报表名为:[" + this.rptNAME + "]");
/*     */ 
/*  56 */     Date d = new Date();
/*     */ 
/*  58 */     SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
/*  59 */     String s = sf.format(d);
/*  60 */     vars.put("DATE", s);
/*     */ 
/*  62 */     sf = new SimpleDateFormat("HHmmss");
/*  63 */     s = sf.format(d);
/*  64 */     vars.put("TIME", s);
/*     */   }
/*     */ 
/*     */   public boolean isConnect() {
/*  68 */     return this.db_connect;
/*     */   }
/*     */ 
/*     */   public void process(HiRptContext ctx, String rptNAME)
/*     */   {
/*  78 */     this.rptNAME = rptNAME;
/*  79 */     ctx.deli = this.deli;
/*  80 */     ctx.logger = createLogger(this.trclog);
/*  81 */     ctx.logger.setLevel(Level.toLevel(ctx.trc));
/*  82 */     initGlobalValue(ctx);
/*  83 */     this.vardef.init(ctx);
/*  84 */     HiDataFile datafile = this.ds.process(ctx);
/*     */ 
/*  86 */     ctx.info("格式输出中间文件:" + datafile.getPath());
/*  87 */     this.fmt.process(ctx, datafile, this.rptNAME);
/*     */ 
/*  93 */     if (!(ctx.logger.isInfoEnabled()))
/*  94 */       ctx.closeDataFile();
/*     */   }
/*     */ 
/*     */   public void setDataSource(HiDataSourceNode ds) {
/*  98 */     this.ds = ds;
/*     */   }
/*     */ 
/*     */   public void setDbconnect(String dbconnect) {
/* 102 */     this.db_connect = dbconnect.equalsIgnoreCase("YES");
/*     */   }
/*     */ 
/*     */   public void setDeli(String deli) {
/* 106 */     this.deli = deli;
/*     */   }
/*     */ 
/*     */   public void setFormatDefine(HiFormatDefineNode fmt) {
/* 110 */     this.fmt = fmt;
/*     */   }
/*     */ 
/*     */   public void setMsgConvert(HiMsgConvertNode convert) {
/* 114 */     this.convert = convert;
/*     */   }
/*     */ 
/*     */   public void setTrclog(String trclog) {
/* 118 */     this.trclog = trclog;
/*     */   }
/*     */ 
/*     */   private Logger createLogger(String trclog)
/*     */   {
/* 123 */     return HiLog.getLogger(trclog);
/*     */   }
/*     */ 
/*     */   public void setVarDef(HiVarDefNode vardef) {
/* 127 */     this.vardef = vardef;
/*     */   }
/*     */ }