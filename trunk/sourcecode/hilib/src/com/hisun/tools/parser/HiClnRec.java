/*     */ package com.hisun.tools.parser;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.tools.HiClnParam;
/*     */ import com.hisun.tools.HiDBUtil;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import com.hisun.util.HiStringUtils;
/*     */ import com.hisun.util.HiSystemUtils;
/*     */ import java.sql.SQLException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.time.DateFormatUtils;
/*     */ import org.apache.commons.lang.time.DateUtils;
/*     */ 
/*     */ public class HiClnRec
/*     */ {
/*     */   private String _tablename;
/*     */   private String _datefield;
/*     */   private String _datefmt;
/*     */   private int _holddays;
/*     */   private String _condition;
/*     */   private String _script;
/*     */   private int _dateType;
/*     */   private static final String DATE_PATTERN = "yyyyMMdd";
/*     */   private int _type;
/*     */ 
/*     */   public HiClnRec()
/*     */   {
/*  39 */     this._dateType = 0;
/*     */ 
/*  46 */     this._type = 0; }
/*     */ 
/*     */   public int getDateType() {
/*  49 */     return this._dateType;
/*     */   }
/*     */ 
/*     */   public void setDateType(int dateType) {
/*  53 */     this._dateType = dateType;
/*     */   }
/*     */ 
/*     */   public String getTablename() {
/*  57 */     return this._tablename;
/*     */   }
/*     */ 
/*     */   public void setTablename(String tablename) {
/*  61 */     this._tablename = tablename;
/*     */   }
/*     */ 
/*     */   public String getDatefield() {
/*  65 */     return this._datefield;
/*     */   }
/*     */ 
/*     */   public void setDatefield(String datefield) {
/*  69 */     this._datefield = datefield;
/*     */   }
/*     */ 
/*     */   public String getDatefmt() {
/*  73 */     return this._datefmt;
/*     */   }
/*     */ 
/*     */   public void setDatefmt(String datefmt) {
/*  77 */     if (StringUtils.equals("YYYYMMDD", datefmt))
/*  78 */       this._datefmt = "yyyyMMdd";
/*     */     else
/*  80 */       this._datefmt = datefmt;
/*     */   }
/*     */ 
/*     */   public int getHolddays()
/*     */   {
/*  85 */     return this._holddays;
/*     */   }
/*     */ 
/*     */   public void setHolddays(int holddays) {
/*  89 */     this._holddays = holddays;
/*     */   }
/*     */ 
/*     */   public String getCondition() {
/*  93 */     return this._condition;
/*     */   }
/*     */ 
/*     */   public void setCondition(String condition) {
/*  97 */     this._condition = condition;
/*     */   }
/*     */ 
/*     */   public void process(HiClnParam param) throws Exception {
/* 101 */     switch (this._type)
/*     */     {
/*     */     case 0:
/* 103 */       processNormal(param);
/* 104 */       break;
/*     */     case 1:
/* 106 */       processSqlScript(param);
/* 107 */       break;
/*     */     case 2:
/* 109 */       processShellScript(param);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void processNormal(HiClnParam param) throws Exception
/*     */   {
/* 115 */     StringBuffer sqlCmd = new StringBuffer();
/* 116 */     boolean flag = false;
/*     */ 
/* 118 */     sqlCmd.append("DELETE FROM ");
/* 119 */     sqlCmd.append(this._tablename);
/* 120 */     sqlCmd.append(" WHERE ");
/* 121 */     if (StringUtils.isNotEmpty(this._datefield))
/*     */     {
/* 123 */       if (this._dateType == 0)
/* 124 */         date = DateUtils.parseDate(param._sysDate, new String[] { this._datefmt });
/* 125 */       else if (this._dateType == 1)
/* 126 */         date = new Date();
/*     */       else {
/* 128 */         date = new Date();
/*     */       }
/*     */ 
/* 131 */       Date date = DateUtils.addDays(date, -1 * this._holddays);
/* 132 */       sqlCmd.append(this._datefield);
/* 133 */       sqlCmd.append(" < ");
/* 134 */       sqlCmd.append("'");
/* 135 */       sqlCmd.append(DateFormatUtils.format(date, this._datefmt));
/* 136 */       sqlCmd.append("'");
/* 137 */       flag = true;
/*     */     }
/* 139 */     String[] args = null;
/* 140 */     if (StringUtils.isNotEmpty(this._condition)) {
/* 141 */       if (flag) {
/* 142 */         sqlCmd.append(" AND ");
/* 143 */         args = new String[param._args.length - 1];
/* 144 */         System.arraycopy(param._args, 1, args, 0, args.length);
/*     */       } else {
/* 146 */         args = param._args;
/*     */       }
/* 148 */       sqlCmd.append(HiStringUtils.format(this._condition, args));
/*     */     }
/* 150 */     if (param._log.isInfoEnabled())
/* 151 */       param._log.info("sql:[" + sqlCmd + "]");
/*     */     try
/*     */     {
/* 154 */       param._dbUtil.execUpdate(sqlCmd.toString());
/* 155 */       param._dbUtil.commit();
/*     */     } catch (SQLException e) {
/* 157 */       param._log.error("execute sql:[" + sqlCmd + "] failure", e);
/* 158 */       param._dbUtil.rollback();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void processSqlScript(HiClnParam param) throws Exception {
/*     */     try {
/* 164 */       this._script = HiStringUtils.format(this._script, param._args);
/* 165 */       if (param._log.isInfoEnabled()) {
/* 166 */         param._log.info("sql:[" + this._script + "]");
/*     */       }
/*     */ 
/* 169 */       param._dbUtil.execUpdate(this._script);
/* 170 */       param._dbUtil.commit();
/*     */     } catch (SQLException e) {
/* 172 */       param._log.error("excute script:[" + this._script + "] failure", e);
/* 173 */       param._dbUtil.rollback();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void processShellScript(HiClnParam param) throws Exception {
/* 178 */     String workdir = HiICSProperty.getWorkDir();
/*     */     try {
/* 180 */       this._script = HiStringUtils.format(this._script, param._args);
/* 181 */       if (param._log.isInfoEnabled()) {
/* 182 */         SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
/* 183 */         param._log.info(df.format(new Date()) + "; sql:[" + this._script + "]");
/*     */       }
/*     */ 
/* 186 */       HiSystemUtils.exec("sh " + workdir + "/" + this._script, true);
/*     */     } catch (HiException e) {
/* 188 */       param._log.error("excute script:[" + this._script + "] failure", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getScript() {
/* 193 */     return this._script;
/*     */   }
/*     */ 
/*     */   public void setScript(String script) {
/* 197 */     this._script = script;
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/* 202 */     return this._type;
/*     */   }
/*     */ 
/*     */   public void setType(int type) {
/* 206 */     this._type = type;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/* 214 */     HiClnRec clnRec = new HiClnRec();
/* 215 */     clnRec._condition = "TM < 1000 AND TM1 < 10000";
/* 216 */     clnRec._datefield = "ChgDat";
/* 217 */     clnRec._datefmt = "yyyyMMdd";
/* 218 */     clnRec._tablename = "ATMTXNJNL";
/* 219 */     HiClnParam param = new HiClnParam();
/* 220 */     param._dbUtil = null;
/* 221 */     param._log = HiLog.getLogger("test.trc");
/* 222 */     param._sysDate = "20050601";
/* 223 */     clnRec.processNormal(param);
/*     */   }
/*     */ }