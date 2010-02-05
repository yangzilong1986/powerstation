 package com.hisun.sqn;
 
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.database.HiResultSet;
 import com.hisun.exception.HiException;
 import com.hisun.framework.event.IServerInitListener;
 import com.hisun.framework.event.ServerEvent;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiSqnMngMain
   implements IHandler, IServerInitListener
 {
   private HiLogNoInfo logNoInfo;
   private HiDumTlrInfo dumTlrInfo;
   private Logger _log;
   private static HiDataBaseUtil dbUtil = new HiDataBaseUtil();
 
   public HiSqnMngMain()
   {
     this.logNoInfo = new HiLogNoInfo();
     this.dumTlrInfo = new HiDumTlrInfo();
   }
 
   public void setGetNumPer(int num) {
     this.logNoInfo.setGetNumPer(num);
   }
 
   public void process(HiMessageContext arg0) throws HiException {
     HiMessage msg = arg0.getCurrentMsg();
     String cmd = msg.getHeadItem("CMD");
     if (StringUtils.isEmpty(cmd)) {
       throw new HiException("241001", "CMD");
     }
 
     if (StringUtils.equalsIgnoreCase(cmd, "GETTRC"))
       getLogNo(msg);
     else if (StringUtils.equalsIgnoreCase(cmd, "GETTLR"))
       getDumTlr(msg);
     else if (StringUtils.equalsIgnoreCase(cmd, "UPDDAT"))
       updActDat(msg);
     else
       throw new HiException("241003", "CMD", cmd);
   }
 
   private void getLogNo(HiMessage msg) throws HiException
   {
     String actDat = null;
     if (!(msg.hasHeadItem("ACC_DT")))
       actDat = getActDat();
     else {
       actDat = msg.getHeadItem("ACC_DT");
     }
     int num = NumberUtils.toInt(msg.getHeadItem("NUM"));
     if (num <= 0) {
       num = 1;
     }
     msg.setHeadItem("LSH", this.logNoInfo.getLogNoFromMem(num, actDat));
     if (this._log.isInfoEnabled())
       this._log.info(msg);
   }
 
   public void getDumTlr(HiMessage msg) throws HiException {
     String brNo = msg.getHeadItem("BRNO");
     if (StringUtils.isEmpty(brNo)) {
       throw new HiException("241001", "BRNO");
     }
     String txnCnl = msg.getHeadItem("APP");
     if (StringUtils.isEmpty(txnCnl)) {
       throw new HiException("241001", "APP");
     }
     String cnlSub = msg.getHeadItem("SUB");
     msg.setHeadItem("TLR", this.dumTlrInfo.getDumTlrFromMem(brNo, txnCnl, cnlSub));
     if (this._log.isInfoEnabled())
       this._log.info(msg);
   }
 
   public static String getActDat() throws HiException {
     try {
       HiResultSet rs = dbUtil.execQuerySQL("SELECT ACC_DT FROM pubpltinf");
 
       if (rs.size() == 0) {
         throw new HiException("241006", "pubpltinf");
       }
       return rs.getValue(0, 0);
     }
     finally {
     }
   }
 
   public static void updActDat(HiMessage msg) throws HiException {
     String oldDat = msg.getHeadItem("OLD");
     if (StringUtils.isEmpty(oldDat)) {
       throw new HiException("241001", "OLD");
     }
     String newDat = msg.getHeadItem("NEW");
     if (StringUtils.isEmpty(newDat)) {
       throw new HiException("241001", "NEW");
     }
     updActDat(oldDat, newDat);
   }
 
   public static void updActDat(String oldDat, String newDat) throws HiException {
     if ((!(isValidDate(oldDat))) || (!(isValidDate(newDat)))) {
       throw new HiException("日期不合法");
     }
 
     if (newDat.compareTo(oldDat) <= 0) {
       throw new HiException("新会计日期小于或等于旧会计日期!");
     }
 
     try
     {
       dbUtil.execUpdate("UPDATE pubpltinf SET LOG_NO='00000001', ACC_DT='" + newDat + "'");
     }
     finally
     {
       dbUtil.commit();
     }
   }
 
   public static boolean isValidDate(String dateStr)
   {
     try {
       if (dateStr == null) {
         return false;
       }
       dateStr = dateStr.trim();
       SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
       Date date = df.parse(dateStr);
       String dateStr2 = df.format(date);
 
       return (!(dateStr.equals(dateStr2)));
     }
     catch (Exception e)
     {
     }
     return false;
   }
 
   public void serverInit(ServerEvent arg0) throws HiException
   {
     this._log = arg0.getLog();
     this.logNoInfo.setLogger(arg0.getLog());
     this.logNoInfo.init();
     this.dumTlrInfo.setLogger(arg0.getLog());
     this.dumTlrInfo.init();
   }
 }