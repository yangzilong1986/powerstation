 package com.hisun.atc.rpt.xml;
 
 import com.hisun.atc.rpt.HiDataFile;
 import com.hisun.atc.rpt.HiReportConstants;
 import com.hisun.atc.rpt.HiRptContext;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.xml.Located;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.Map;
 import org.apache.log4j.Level;
 
 public class HiReportConfig extends Located
   implements HiReportConstants
 {
   public HiVarDefNode vardef;
   public HiMsgConvertNode convert;
   private boolean db_connect;
   private String deli;
   public HiDataSourceNode ds;
   public HiFormatDefineNode fmt;
   public Logger log;
   private String rptNAME;
   private String trclog;
 
   public HiReportConfig()
   {
     this.deli = "|";
 
     this.trclog = "HIRPT.trc"; }
 
   public String getDeli() {
     return this.deli;
   }
 
   public String getTrclog() {
     return this.trclog;
   }
 
   private void initGlobalValue(HiRptContext ctx)
   {
     Map vars = ctx.vars;
     vars.put("RPTNAME", this.rptNAME);
 
     ctx.info("报表名为:[" + this.rptNAME + "]");
 
     Date d = new Date();
 
     SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
     String s = sf.format(d);
     vars.put("DATE", s);
 
     sf = new SimpleDateFormat("HHmmss");
     s = sf.format(d);
     vars.put("TIME", s);
   }
 
   public boolean isConnect() {
     return this.db_connect;
   }
 
   public void process(HiRptContext ctx, String rptNAME)
   {
     this.rptNAME = rptNAME;
     ctx.deli = this.deli;
     ctx.logger = createLogger(this.trclog);
     ctx.logger.setLevel(Level.toLevel(ctx.trc));
     initGlobalValue(ctx);
     this.vardef.init(ctx);
     HiDataFile datafile = this.ds.process(ctx);
 
     ctx.info("格式输出中间文件:" + datafile.getPath());
     this.fmt.process(ctx, datafile, this.rptNAME);
 
     if (!(ctx.logger.isInfoEnabled()))
       ctx.closeDataFile();
   }
 
   public void setDataSource(HiDataSourceNode ds) {
     this.ds = ds;
   }
 
   public void setDbconnect(String dbconnect) {
     this.db_connect = dbconnect.equalsIgnoreCase("YES");
   }
 
   public void setDeli(String deli) {
     this.deli = deli;
   }
 
   public void setFormatDefine(HiFormatDefineNode fmt) {
     this.fmt = fmt;
   }
 
   public void setMsgConvert(HiMsgConvertNode convert) {
     this.convert = convert;
   }
 
   public void setTrclog(String trclog) {
     this.trclog = trclog;
   }
 
   private Logger createLogger(String trclog)
   {
     return HiLog.getLogger(trclog);
   }
 
   public void setVarDef(HiVarDefNode vardef) {
     this.vardef = vardef;
   }
 }