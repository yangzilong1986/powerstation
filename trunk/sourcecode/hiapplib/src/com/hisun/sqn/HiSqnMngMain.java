/*     */ package com.hisun.sqn;
/*     */ 
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.database.HiResultSet;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.framework.event.IServerInitListener;
/*     */ import com.hisun.framework.event.ServerEvent;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.pubinterface.IHandler;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiSqnMngMain
/*     */   implements IHandler, IServerInitListener
/*     */ {
/*     */   private HiLogNoInfo logNoInfo;
/*     */   private HiDumTlrInfo dumTlrInfo;
/*     */   private Logger _log;
/*  23 */   private static HiDataBaseUtil dbUtil = new HiDataBaseUtil();
/*     */ 
/*     */   public HiSqnMngMain()
/*     */   {
/*  20 */     this.logNoInfo = new HiLogNoInfo();
/*  21 */     this.dumTlrInfo = new HiDumTlrInfo();
/*     */   }
/*     */ 
/*     */   public void setGetNumPer(int num) {
/*  25 */     this.logNoInfo.setGetNumPer(num);
/*     */   }
/*     */ 
/*     */   public void process(HiMessageContext arg0) throws HiException {
/*  29 */     HiMessage msg = arg0.getCurrentMsg();
/*  30 */     String cmd = msg.getHeadItem("CMD");
/*  31 */     if (StringUtils.isEmpty(cmd)) {
/*  32 */       throw new HiException("241001", "CMD");
/*     */     }
/*     */ 
/*  35 */     if (StringUtils.equalsIgnoreCase(cmd, "GETTRC"))
/*  36 */       getLogNo(msg);
/*  37 */     else if (StringUtils.equalsIgnoreCase(cmd, "GETTLR"))
/*  38 */       getDumTlr(msg);
/*  39 */     else if (StringUtils.equalsIgnoreCase(cmd, "UPDDAT"))
/*  40 */       updActDat(msg);
/*     */     else
/*  42 */       throw new HiException("241003", "CMD", cmd);
/*     */   }
/*     */ 
/*     */   private void getLogNo(HiMessage msg) throws HiException
/*     */   {
/*  47 */     String actDat = null;
/*  48 */     if (!(msg.hasHeadItem("ACC_DT")))
/*  49 */       actDat = getActDat();
/*     */     else {
/*  51 */       actDat = msg.getHeadItem("ACC_DT");
/*     */     }
/*  53 */     int num = NumberUtils.toInt(msg.getHeadItem("NUM"));
/*  54 */     if (num <= 0) {
/*  55 */       num = 1;
/*     */     }
/*  57 */     msg.setHeadItem("LSH", this.logNoInfo.getLogNoFromMem(num, actDat));
/*  58 */     if (this._log.isInfoEnabled())
/*  59 */       this._log.info(msg);
/*     */   }
/*     */ 
/*     */   public void getDumTlr(HiMessage msg) throws HiException {
/*  63 */     String brNo = msg.getHeadItem("BRNO");
/*  64 */     if (StringUtils.isEmpty(brNo)) {
/*  65 */       throw new HiException("241001", "BRNO");
/*     */     }
/*  67 */     String txnCnl = msg.getHeadItem("APP");
/*  68 */     if (StringUtils.isEmpty(txnCnl)) {
/*  69 */       throw new HiException("241001", "APP");
/*     */     }
/*  71 */     String cnlSub = msg.getHeadItem("SUB");
/*  72 */     msg.setHeadItem("TLR", this.dumTlrInfo.getDumTlrFromMem(brNo, txnCnl, cnlSub));
/*  73 */     if (this._log.isInfoEnabled())
/*  74 */       this._log.info(msg);
/*     */   }
/*     */ 
/*     */   public static String getActDat() throws HiException {
/*     */     try {
/*  79 */       HiResultSet rs = dbUtil.execQuerySQL("SELECT ACC_DT FROM pubpltinf");
/*     */ 
/*  81 */       if (rs.size() == 0) {
/*  82 */         throw new HiException("241006", "pubpltinf");
/*     */       }
/*  84 */       return rs.getValue(0, 0);
/*     */     }
/*     */     finally {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void updActDat(HiMessage msg) throws HiException {
/*  91 */     String oldDat = msg.getHeadItem("OLD");
/*  92 */     if (StringUtils.isEmpty(oldDat)) {
/*  93 */       throw new HiException("241001", "OLD");
/*     */     }
/*  95 */     String newDat = msg.getHeadItem("NEW");
/*  96 */     if (StringUtils.isEmpty(newDat)) {
/*  97 */       throw new HiException("241001", "NEW");
/*     */     }
/*  99 */     updActDat(oldDat, newDat);
/*     */   }
/*     */ 
/*     */   public static void updActDat(String oldDat, String newDat) throws HiException {
/* 103 */     if ((!(isValidDate(oldDat))) || (!(isValidDate(newDat)))) {
/* 104 */       throw new HiException("日期不合法");
/*     */     }
/*     */ 
/* 107 */     if (newDat.compareTo(oldDat) <= 0) {
/* 108 */       throw new HiException("新会计日期小于或等于旧会计日期!");
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 113 */       dbUtil.execUpdate("UPDATE pubpltinf SET LOG_NO='00000001', ACC_DT='" + newDat + "'");
/*     */     }
/*     */     finally
/*     */     {
/* 117 */       dbUtil.commit();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean isValidDate(String dateStr)
/*     */   {
/*     */     try {
/* 124 */       if (dateStr == null) {
/* 125 */         return false;
/*     */       }
/* 127 */       dateStr = dateStr.trim();
/* 128 */       SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
/* 129 */       Date date = df.parse(dateStr);
/* 130 */       String dateStr2 = df.format(date);
/*     */ 
/* 132 */       return (!(dateStr.equals(dateStr2)));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */   public void serverInit(ServerEvent arg0) throws HiException
/*     */   {
/* 141 */     this._log = arg0.getLog();
/* 142 */     this.logNoInfo.setLogger(arg0.getLog());
/* 143 */     this.logNoInfo.init();
/* 144 */     this.dumTlrInfo.setLogger(arg0.getLog());
/* 145 */     this.dumTlrInfo.init();
/*     */   }
/*     */ }