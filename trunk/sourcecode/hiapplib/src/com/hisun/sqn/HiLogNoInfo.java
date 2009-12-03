/*     */ package com.hisun.sqn;
/*     */ 
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiLogNoInfo
/*     */ {
/*     */   private Logger _log;
/*     */   private int _nextLogNo;
/*     */   private int _remainNum;
/*     */   private int _getNumPer;
/*     */   private String _actDat;
/*     */ 
/*     */   public HiLogNoInfo()
/*     */   {
/*  28 */     this._getNumPer = 1000;
/*     */   }
/*     */ 
/*     */   public synchronized String getLogNoFromMem(int num, String actDat)
/*     */     throws HiException
/*     */   {
/*  37 */     if ((!(StringUtils.equals(this._actDat, actDat))) || (num > this._remainNum)) {
/*  38 */       int seqno = getLogNoFromDB(this._getNumPer + num);
/*  39 */       this._nextLogNo = seqno;
/*  40 */       this._remainNum = (this._getNumPer + num);
/*  41 */       this._actDat = actDat;
/*     */     }
/*  43 */     this._nextLogNo += num;
/*  44 */     this._remainNum -= num;
/*  45 */     return StringUtils.substring(actDat, 2) + StringUtils.leftPad(String.valueOf(this._nextLogNo), 8, '0');
/*     */   }
/*     */ 
/*     */   private int getLogNoFromDB(int num) throws HiException
/*     */   {
/*  50 */     if ((num <= 0) || (num >= 99999999)) {
/*  51 */       throw new HiException("241005", String.valueOf(num), "0-99999999");
/*     */     }
/*  53 */     HiDataBaseUtil dbUtil = new HiDataBaseUtil();
/*     */     try
/*     */     {
/*  56 */       int rc = dbUtil.execUpdate("UPDATE pubpltinf SET Log_No = Log_No");
/*  57 */       if (rc == 0) {
/*  58 */         throw new HiException("211007");
/*     */       }
/*  60 */       List records = dbUtil.execQuery("SELECT LOG_NO, ACC_DT FROM pubpltinf");
/*     */ 
/*  62 */       if (records.isEmpty()) {
/*  63 */         throw new HiException("241006");
/*     */       }
/*  65 */       HashMap record = (HashMap)records.get(0);
/*  66 */       String logNo = (String)record.get("LOG_NO");
/*  67 */       this._actDat = ((String)record.get("ACC_DT"));
/*  68 */       int iLogNo = NumberUtils.toInt(logNo);
/*  69 */       if (iLogNo == 0) {
/*  70 */         iLogNo = 1;
/*     */       }
/*  72 */       if (iLogNo + num > 100000000) {
/*  73 */         throw new HiException("241008", "100000000");
/*     */       }
/*  75 */       logNo = StringUtils.leftPad(String.valueOf(iLogNo + num), 6, '0');
/*  76 */       rc = dbUtil.execUpdate("UPDATE pubpltinf SET LOG_NO = '" + logNo + "'");
/*     */ 
/*  78 */       if (rc == 0) {
/*  79 */         throw new HiException("211007");
/*     */       }
/*  81 */       int i = iLogNo;
/*     */ 
/*  87 */       return i;
/*     */     }
/*     */     catch (HiException e)
/*     */     {
/*     */     }
/*     */     finally
/*     */     {
/*  86 */       dbUtil.commit();
/*  87 */       dbUtil.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setLogger(Logger log) {
/*  92 */     this._log = log;
/*     */   }
/*     */ 
/*     */   public void setGetNumPer(int num) {
/*  96 */     this._getNumPer = num;
/*     */   }
/*     */ 
/*     */   public void init() throws HiException {
/* 100 */     this._nextLogNo = getLogNoFromDB(this._getNumPer);
/* 101 */     this._remainNum = this._getNumPer;
/*     */   }
/*     */ }