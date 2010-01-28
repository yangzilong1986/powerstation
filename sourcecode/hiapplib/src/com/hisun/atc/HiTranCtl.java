 package com.hisun.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.atc.common.HiDbtSqlHelper;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.database.HiResultSet;
 import com.hisun.exception.HiAppException;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.sqn.HiSqnMng;
 import com.hisun.util.HiStringManager;
 import com.hisun.util.HiStringUtils;
 import org.apache.commons.lang.StringUtils;
 
 public class HiTranCtl
 {
   private static HiStringManager sm = HiStringManager.getManager();
 
   public int getVirtualTeller(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String txnCnl;
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     HiETF root = (HiETF)msg.getBody();
 
     String brNo = HiArgUtils.getStringNotNull(root, "BR_NO");
     if (args.size() > 0)
       txnCnl = HiArgUtils.getStringNotNull(args, "TxnCnl");
     else {
       txnCnl = HiArgUtils.getStringNotNull(root, "TXN_CNL");
     }
 
     String cnlSub = args.get("CnlSub");
     if (StringUtils.isEmpty(cnlSub)) {
       cnlSub = HiArgUtils.getString(root, "CNL_SUB");
     }
     if (log.isDebugEnabled()) {
       log.debug(sm.getString("HiTranCtl.getVirtualTeller.param", brNo, txnCnl, cnlSub));
     }
 
     root.setChildValue("TLR_ID", HiSqnMng.getDumTlr(brNo, txnCnl, cnlSub));
 
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiTranCtl.getVirtualTeller.TlrId", root.getChildValue("TLR_ID")));
     }
 
     return 0;
   }
 
   public int getLogNo(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     int num;
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     HiETF root = (HiETF)mess.getBody();
 
     if ((num = args.getInt("Num")) < 0) {
       throw new HiException("220026", String.valueOf(num));
     }
 
     if (num == 0) {
       num = 1;
     }
     if (log.isDebugEnabled()) {
       log.debug(sm.getString("HiTranCtl.getLogNo.param", String.valueOf(num)));
     }
 
     HiMessage msg = ctx.getCurrentMsg();
     String logNo = HiSqnMng.getLogNo(msg, num);
     root.setChildValue("LOG_NO", logNo);
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiTranCtl.getLogNo.LogNo", logNo));
     }
     return 0;
   }
 
   public int GetSeqNo(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     int seqNo;
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     HiETF root = (HiETF)mess.getBody();
     String sqlCmd = null;
 
     boolean circled = false;
 
     String tblName = HiArgUtils.getStringNotNull(args, "TblNam");
     String seqCol = HiArgUtils.getStringNotNull(args, "SeqCol");
     int maxLen = args.getInt("Len");
     String colName = args.get("ColNam");
     if (StringUtils.isEmpty(colName)) {
       colName = seqCol;
     }
 
     String cndStsDef = args.get("CndSts");
     if (StringUtils.isEmpty(cndStsDef)) {
       sqlCmd = "SELECT %s FROM %s ";
       sqlCmd = HiStringUtils.format(sqlCmd, seqCol, tblName);
     } else {
       sqlCmd = "SELECT %s FROM %s WHERE %s ";
 
       cndStsDef = HiDbtSqlHelper.getDynSentence(ctx, cndStsDef);
       sqlCmd = HiStringUtils.format(sqlCmd, seqCol, tblName, cndStsDef);
     }
     circled = args.getBoolean("Circle");
     if (log.isDebugEnabled()) {
       log.debug(sm.getString("HiTranClt.getSeqNo.param", new Object[] { tblName, seqCol, String.valueOf(maxLen), cndStsDef, colName, String.valueOf(circled) }));
     }
 
     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
     try
     {
       String value;
       String sqlCmd1 = null;
 
       if (StringUtils.isEmpty(cndStsDef)) {
         sqlCmd1 = "UPDATE %s SET %s = %s";
         sqlCmd1 = HiStringUtils.format(sqlCmd1, tblName, seqCol, seqCol);
       }
       else {
         sqlCmd1 = "UPDATE %s SET %s = %s WHERE %s ";
         sqlCmd1 = HiStringUtils.format(sqlCmd1, tblName, seqCol, seqCol, cndStsDef);
       }
 
       dbUtil.execUpdate(sqlCmd1);
 
       HiResultSet rs = dbUtil.execQuerySQL(sqlCmd);
       if (rs.size() == 0) {
         throw new HiException("220040", sqlCmd);
       }
       seqNo = rs.getInt(0, 0);
       int nextSeqNo = seqNo + 1;
       if (String.valueOf(nextSeqNo).length() > maxLen) {
         if (circled)
           seqNo = 1;
         else {
           throw new HiException("220062", tblName, String.valueOf(seqCol), String.valueOf(nextSeqNo));
         }
 
       }
 
       nextSeqNo = seqNo + 1;
       if (StringUtils.isEmpty(cndStsDef)) {
         sqlCmd = "UPDATE %s SET %s = '%s'";
         value = StringUtils.leftPad(String.valueOf(nextSeqNo), maxLen, "0");
 
         sqlCmd = HiStringUtils.format(sqlCmd, tblName, seqCol, value);
       } else {
         sqlCmd = "UPDATE %s SET %s = '%s' WHERE %s ";
 
         value = StringUtils.leftPad(String.valueOf(nextSeqNo), maxLen, "0");
 
         sqlCmd = HiStringUtils.format(sqlCmd, tblName, seqCol, value, cndStsDef);
       }
 
       dbUtil.execUpdate(sqlCmd);
     } finally {
       dbUtil.commit();
     }
 
     if (log.isDebugEnabled()) {
       log.debug(sm.getString("HiTranCtl.getSeqNo.value", colName, StringUtils.leftPad(String.valueOf(seqNo), maxLen, "0")));
     }
 
     root.setChildValue(colName, StringUtils.leftPad(String.valueOf(seqNo), maxLen, "0"));
 
     return 0;
   }
 
   public int getSeqNoCircle(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     args.put("Circle", "true");
     return GetSeqNo(args, ctx);
   }
 
   public int getPubSeqNo(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     int seqNo;
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     HiETF root = (HiETF)mess.getBody();
 
     int ret = 0;
     boolean circled = false;
 
     String brNo = root.getChildValue("BR_NO");
 
     if (StringUtils.isEmpty(brNo)) {
       brNo = ctx.getBCFG("BR_NO");
     }
     if (StringUtils.isEmpty(brNo)) {
       throw new HiException("220082", "BR_NO");
     }
 
     String seqName = HiArgUtils.getStringNotNull(args, "SeqNam");
     String seqCond = args.get("SeqCnd");
     if (seqCond == null)
       seqCond = "";
     int maxLen = args.getInt("Len");
     if (maxLen <= 0) {
       throw new HiAppException(-1, "220026", "Len");
     }
 
     int seqCnt = args.getInt("SeqCnt");
 
     String acDate = HiArgUtils.getString(root, "ACC_DT");
 
     if (seqCnt <= 0) {
       seqCnt = 1;
     }
 
     circled = args.getBoolean("Circle");
     if (log.isDebugEnabled()) {
       log.debug(sm.getString("HiTranCtl.getPubSeqNo.param", new Object[] { brNo, seqName, seqCond, String.valueOf(maxLen), String.valueOf(seqCnt), String.valueOf(circled) }));
     }
 
     String sqlCmd = "SELECT SEL_VAL FROM PUBSEQREC WHERE BR_NO='%s' AND SEQ_NM='%s' AND SEQ_CND='%s'";
     sqlCmd = HiStringUtils.format(sqlCmd, brNo, seqName, seqCond);
 
     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
     try
     {
       String sqlCmd1 = null;
       sqlCmd1 = "UPDATE PUBSEQREC SET SEL_VAL = SEL_VAL WHERE BR_NO='%s' AND SEQ_NM='%s' AND SEQ_CND='%s'";
       sqlCmd1 = HiStringUtils.format(sqlCmd1, brNo, seqName, seqCond);
 
       dbUtil.execUpdate(sqlCmd1);
 
       if (log.isDebugEnabled()) {
         log.debug(sm.getString("HiTranCtl.sqlCmd", sqlCmd));
       }
 
       HiResultSet rs = dbUtil.execQuerySQL(sqlCmd);
 
       if (rs.size() == 0) {
         seqNo = 1;
         sqlCmd = "INSERT INTO PUBSEQREC VALUES('%s', '%s', '%s', %s)";
         sqlCmd = HiStringUtils.format(sqlCmd, brNo, seqName, seqCond, String.valueOf(seqCnt + 1));
 
         if (log.isDebugEnabled()) {
           log.debug(sm.getString("HiTranCtl.sqlCmd", sqlCmd));
         }
         dbUtil.execUpdate(sqlCmd);
       } else {
         seqNo = rs.getInt(0, 0);
 
         if (ret > 0) {
           seqNo = 1;
         } else if (ret < 0) {
           dbUtil.rollback();
           throw new HiException("220061", acDate, seqCond);
         }
 
         int nextSeqNo = seqNo + seqCnt;
 
         if (String.valueOf(nextSeqNo).length() > maxLen) {
           if (!(circled)) {
             dbUtil.rollback();
             throw new HiException("220062", "PUBSEQREC", seqName, seqCond, String.valueOf(nextSeqNo));
           }
 
           if (circled) {
             seqNo = 1;
             nextSeqNo = seqCnt + seqNo;
           }
         }
         sqlCmd = "UPDATE PUBSEQREC SET SEL_VAL = %s WHERE BR_NO='%s' AND SEQ_NM='%s' AND SEQ_CND='%s'";
         String value = StringUtils.leftPad(String.valueOf(nextSeqNo), maxLen, "0");
 
         sqlCmd = HiStringUtils.format(sqlCmd, value, brNo, seqName, seqCond);
 
         dbUtil.execUpdate(sqlCmd);
       }
     } finally {
       dbUtil.commit();
     }
 
     root.setChildValue("SEL_VAL", StringUtils.leftPad(String.valueOf(seqNo), maxLen, "0"));
 
     if (log.isDebugEnabled()) {
       log.debug(sm.getString("HiTranCtl.getPubSeqNo.SelVal", root.getChildValue("SEL_VAL")));
     }
 
     return 0;
   }
 
   public int getPubSeqNoCircle(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     args.put("Circle", "true");
     return getPubSeqNo(args, ctx);
   }
 
   public int nGetPubSeqNo(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     int seqNo;
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     HiETF root = (HiETF)mess.getBody();
 
     int ret = 0;
     boolean circled = false;
 
     String brNo = HiArgUtils.getStringNotNull(root, "BR_NO");
     String seqName = HiArgUtils.getStringNotNull(args, "SeqNam");
     int maxLen = args.getInt("Len");
     int seqCnt = args.getInt("SeqCnt");
     String cycCond = args.get("CycCnd");
     String seqCond = null;
 
     String acDate = HiArgUtils.getStringNotNull(root, "ACC_DT");
 
     if (seqCnt <= 0) {
       seqCnt = 1;
     }
     circled = args.getBoolean("Circle");
     if (log.isDebugEnabled()) {
       log.debug(sm.getString("HiTranCtl.getPubSeqNo.param", new Object[] { brNo, seqName, seqCond, String.valueOf(maxLen), String.valueOf(seqCnt), String.valueOf(circled) }));
     }
 
     String sqlCmd = "SELECT SEL_VAL, SEQ_CND FROM PUBSEQREC WHERE BR_NO='%s' AND SEQ_NM='%s'";
     sqlCmd = HiStringUtils.format(sqlCmd, brNo, seqName);
 
     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
     try {
       String sqlCmd1 = null;
       sqlCmd1 = "UPDATE PUBSEQREC SET SEL_VAL = SE_VAL WHERE BR_NO='%s' AND SEQ_NM='%s'";
       sqlCmd1 = HiStringUtils.format(sqlCmd1, brNo, seqName);
 
       dbUtil.execUpdate(sqlCmd1);
 
       if (log.isDebugEnabled()) {
         log.debug(sm.getString("HiTranCtl.sqlCmd", sqlCmd));
       }
 
       HiResultSet rs = dbUtil.execQuerySQL(sqlCmd);
 
       if (rs.size() == 0) {
         seqNo = 1;
         sqlCmd = "INSERT INTO PUBSEQREC VALUES('%s', '%s', '%s', %s)";
         sqlCmd = HiStringUtils.format(sqlCmd, brNo, seqName, acDate, String.valueOf(seqCnt + 1));
 
         if (log.isDebugEnabled()) {
           log.debug(sm.getString("HiTranCtl.sqlCmd", sqlCmd));
         }
         dbUtil.execUpdate(sqlCmd);
       } else {
         seqNo = rs.getInt(0, 0);
         seqCond = rs.getValue(0, 1);
 
         if (StringUtils.equalsIgnoreCase(cycCond, "Y")) {
           ret = StringUtils.substring(acDate, 0, 4).compareTo(StringUtils.substring(seqCond, 0, 4));
         }
         else if (StringUtils.equalsIgnoreCase(cycCond, "M")) {
           ret = StringUtils.substring(acDate, 0, 6).compareTo(StringUtils.substring(seqCond, 0, 6));
         }
         else {
           ret = StringUtils.substring(acDate, 0, 8).compareTo(StringUtils.substring(seqCond, 0, 8));
         }
 
         if (ret > 0) {
           seqNo = 1;
         } else if (ret < 0) {
           dbUtil.rollback();
           throw new HiException("220061", acDate, seqCond);
         }
 
         int nextSeqNo = seqNo + seqCnt;
 
         if (String.valueOf(nextSeqNo).length() > maxLen) {
           if (!(circled)) {
             dbUtil.rollback();
             throw new HiException("220062", "PUBSEQREC", seqName, seqCond, String.valueOf(nextSeqNo));
           }
 
           if (circled) {
             seqNo = 1;
             nextSeqNo = seqCnt + seqNo;
           }
         }
         sqlCmd = "UPDATE PUBSEQREC SET SEL_VAL = %s, SEQ_CND = '%s' WHERE BR_NO='%s' AND SEQ_NM='%s'";
         String value = StringUtils.leftPad(String.valueOf(nextSeqNo), maxLen, "0");
 
         sqlCmd = HiStringUtils.format(sqlCmd, value, acDate, brNo, seqName);
 
         dbUtil.execUpdate(sqlCmd);
       }
     } finally {
       dbUtil.commit();
     }
     root.setChildValue("SEL_VAL", StringUtils.leftPad(String.valueOf(seqNo), maxLen, "0"));
 
     if (log.isDebugEnabled()) {
       log.debug(sm.getString("HiTranCtl.getPubSeqNo.SelVal", root.getChildValue("SEL_VAL")));
     }
 
     return 0;
   }
 }