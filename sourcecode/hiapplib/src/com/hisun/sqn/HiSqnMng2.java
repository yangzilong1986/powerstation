/*     */ package com.hisun.sqn;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ 
/*     */ class HiSqnMng2
/*     */ {
/*     */   private HiDumTlrInfo _dumTlrInfo;
/*     */   private HiLogNoInfo _logNoInfo;
/*     */   private Logger _log;
/*     */ 
/*     */   HiSqnMng2()
/*     */   {
/*  45 */     this._dumTlrInfo = new HiDumTlrInfo();
/*  46 */     this._logNoInfo = new HiLogNoInfo();
/*  47 */     this._log = HiLog.getLogger("SYS.trc"); }
/*     */ 
/*     */   public void init() throws HiException { this._logNoInfo.init();
/*  50 */     this._logNoInfo.setLogger(this._log);
/*  51 */     this._dumTlrInfo.init();
/*  52 */     this._dumTlrInfo.setLogger(this._log);
/*     */   }
/*     */ 
/*     */   public void updActDat(String oldDat, String newDat)
/*     */     throws HiException
/*     */   {
/*  65 */     HiSqnMngMain.updActDat(oldDat, newDat);
/*     */   }
/*     */ 
/*     */   public String getDumTlr(String brNo, String aplCod, String aplSub)
/*     */     throws HiException
/*     */   {
/*  80 */     return this._dumTlrInfo.getDumTlrFromMem(brNo, aplCod, aplSub);
/*     */   }
/*     */ 
/*     */   public String getLogNo(int num)
/*     */     throws HiException
/*     */   {
/*  92 */     String actDat = HiSqnMngMain.getActDat();
/*  93 */     return this._logNoInfo.getLogNoFromMem(num, actDat);
/*     */   }
/*     */ 
/*     */   public String getLogNo(String actDat, int num)
/*     */     throws HiException
/*     */   {
/* 105 */     return this._logNoInfo.getLogNoFromMem(num, actDat);
/*     */   }
/*     */ }