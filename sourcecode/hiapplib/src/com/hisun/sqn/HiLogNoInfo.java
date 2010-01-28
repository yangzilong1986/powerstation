 package com.hisun.sqn;
 
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import java.util.HashMap;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiLogNoInfo
 {
   private Logger _log;
   private int _nextLogNo;
   private int _remainNum;
   private int _getNumPer;
   private String _actDat;
 
   public HiLogNoInfo()
   {
     this._getNumPer = 1000;
   }
 
   public synchronized String getLogNoFromMem(int num, String actDat)
     throws HiException
   {
     if ((!(StringUtils.equals(this._actDat, actDat))) || (num > this._remainNum)) {
       int seqno = getLogNoFromDB(this._getNumPer + num);
       this._nextLogNo = seqno;
       this._remainNum = (this._getNumPer + num);
       this._actDat = actDat;
     }
     this._nextLogNo += num;
     this._remainNum -= num;
     return StringUtils.substring(actDat, 2) + StringUtils.leftPad(String.valueOf(this._nextLogNo), 8, '0');
   }
 
   private int getLogNoFromDB(int num) throws HiException
   {
     if ((num <= 0) || (num >= 99999999)) {
       throw new HiException("241005", String.valueOf(num), "0-99999999");
     }
     HiDataBaseUtil dbUtil = new HiDataBaseUtil();
     try
     {
       int rc = dbUtil.execUpdate("UPDATE pubpltinf SET Log_No = Log_No");
       if (rc == 0) {
         throw new HiException("211007");
       }
       List records = dbUtil.execQuery("SELECT LOG_NO, ACC_DT FROM pubpltinf");
 
       if (records.isEmpty()) {
         throw new HiException("241006");
       }
       HashMap record = (HashMap)records.get(0);
       String logNo = (String)record.get("LOG_NO");
       this._actDat = ((String)record.get("ACC_DT"));
       int iLogNo = NumberUtils.toInt(logNo);
       if (iLogNo == 0) {
         iLogNo = 1;
       }
       if (iLogNo + num > 100000000) {
         throw new HiException("241008", "100000000");
       }
       logNo = StringUtils.leftPad(String.valueOf(iLogNo + num), 6, '0');
       rc = dbUtil.execUpdate("UPDATE pubpltinf SET LOG_NO = '" + logNo + "'");
 
       if (rc == 0) {
         throw new HiException("211007");
       }
       int i = iLogNo;
 
       return i;
     }
     catch (HiException e)
     {
     }
     finally
     {
       dbUtil.commit();
       dbUtil.close();
     }
   }
 
   public void setLogger(Logger log) {
     this._log = log;
   }
 
   public void setGetNumPer(int num) {
     this._getNumPer = num;
   }
 
   public void init() throws HiException {
     this._nextLogNo = getLogNoFromDB(this._getNumPer);
     this._remainNum = this._getNumPer;
   }
 }