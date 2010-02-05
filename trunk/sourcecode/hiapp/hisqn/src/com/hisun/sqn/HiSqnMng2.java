 package com.hisun.sqn;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 
 class HiSqnMng2
 {
   private HiDumTlrInfo _dumTlrInfo;
   private HiLogNoInfo _logNoInfo;
   private Logger _log;
 
   HiSqnMng2()
   {
     this._dumTlrInfo = new HiDumTlrInfo();
     this._logNoInfo = new HiLogNoInfo();
     this._log = HiLog.getLogger("SYS.trc"); }
 
   public void init() throws HiException { this._logNoInfo.init();
     this._logNoInfo.setLogger(this._log);
     this._dumTlrInfo.init();
     this._dumTlrInfo.setLogger(this._log);
   }
 
   public void updActDat(String oldDat, String newDat)
     throws HiException
   {
     HiSqnMngMain.updActDat(oldDat, newDat);
   }
 
   public String getDumTlr(String brNo, String aplCod, String aplSub)
     throws HiException
   {
     return this._dumTlrInfo.getDumTlrFromMem(brNo, aplCod, aplSub);
   }
 
   public String getLogNo(int num)
     throws HiException
   {
     String actDat = HiSqnMngMain.getActDat();
     return this._logNoInfo.getLogNoFromMem(num, actDat);
   }
 
   public String getLogNo(String actDat, int num)
     throws HiException
   {
     return this._logNoInfo.getLogNoFromMem(num, actDat);
   }
 }