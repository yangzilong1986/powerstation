 package com.hisun.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.atc.common.HiAtcLib;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.engine.invoke.impl.HiAttributesHelper;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 
 public class HiTxnJnl
 {
   public int InsertJournal(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String txnjnl = ctx.getJnlTable();
     if (txnjnl == null) {
       throw new HiException("220052");
     }
 
     return HiAtcLib.insertTable(txnjnl, ctx);
   }
 
   public int UpdateOldJournalStatus(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     String strType = null;
     String HTxnSt = args.get("HTxnSt");
     String TTxnSt = args.get("TTxnSt");
     String TxnSts = args.get("TxnSts");
 
     if ((HTxnSt != null) && (TTxnSt != null) && (TxnSts != null))
       strType = "ALL";
     else if ((HTxnSt != null) && (TxnSts != null))
       strType = "HAF";
     else if ((TTxnSt != null) && (TxnSts != null))
       strType = "TAF";
     else if ((HTxnSt != null) && (TTxnSt != null))
       strType = "HAT";
     else if (HTxnSt != null)
       strType = "HST";
     else if (TTxnSt != null)
       strType = "THD";
     else if (TxnSts != null)
       strType = "FRT";
     else {
       throw new HiException("220026", "参数定义错误");
     }
 
     HiETF etfRoot = (HiETF)mess.getBody();
     String strOLogNo = HiArgUtils.getStringNotNull(etfRoot, "OLOG_NO");
 
     int ret = HiAtcLib.updTxnJnlSts(ctx, strType, strOLogNo, HTxnSt, TTxnSt, TxnSts);
 
     if (ret == -1) {
       throw new HiException("220053", "更新原交易流水状态失败");
     }
 
     return ret;
   }
 
   public int UpdateJournalHost(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     String txnjnltbl = ctx.getJnlTable();
     if (txnjnltbl == null) {
       throw new HiException("220052");
     }
 
     HiETF etfRoot = (HiETF)msg.getBody();
 
     StringBuffer sqlcmd = new StringBuffer();
     sqlcmd.append("UPDATE ");
     sqlcmd.append(txnjnltbl);
     sqlcmd.append(" SET ");
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "ACC_DT", HiArgUtils.getStringNull(etfRoot, "ACC_DT"), false);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "AC_NO", HiArgUtils.getStringNull(etfRoot, "AC_NO"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "CRD_NO", HiArgUtils.getStringNull(etfRoot, "CRD_NO"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "ORG_NO", HiArgUtils.getStringNull(etfRoot, "ORG_NO"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "NOD_NO", HiArgUtils.getStringNull(etfRoot, "NOD_NO"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "NOD_TRC", HiArgUtils.getStringNull(etfRoot, "NOD_TRC"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "HLOG_NO", HiArgUtils.getStringNull(etfRoot, "HLOG_NO"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TCK_NO", HiArgUtils.getStringNull(etfRoot, "TCK_NO"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TCUS_NM", HiArgUtils.getStringNull(etfRoot, "TCUS_NM"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "HRSP_CD", HiArgUtils.getStringNull(etfRoot, "HRSP_CD"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "HTXN_STS", HiArgUtils.getStringNull(etfRoot, "HTXN_STS"), true);
 
     for (int i = 0; i < args.size(); ++i) {
       String key = args.getName(i);
       String value = args.getValue(i);
       sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, key, value, true);
     }
 
     String strLogNo = HiArgUtils.getStringNotNull(etfRoot, "LOG_NO");
 
     sqlcmd.append(" WHERE LOG_NO='");
     sqlcmd.append(strLogNo);
     sqlcmd.append("'");
 
     Logger log = HiLog.getLogger(msg);
     if (log.isInfoEnabled()) {
       log.info(sqlcmd);
     }
     int ret = ctx.getDataBaseUtil().execUpdate(sqlcmd.toString());
     if (ret == 0) {
       return 2;
     }
     return 0;
   }
 
   public int UpdateJournal(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     String txnjnltbl = ctx.getJnlTable();
     if (txnjnltbl == null) {
       throw new HiException("220052");
     }
 
     HiETF etfRoot = (HiETF)msg.getBody();
     StringBuffer sqlcmd = new StringBuffer();
     sqlcmd.append("UPDATE ");
     sqlcmd.append(txnjnltbl);
     sqlcmd.append(" SET ");
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "ACC_DT", HiArgUtils.getStringNull(etfRoot, "ACC_DT"), false);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "AC_NO", HiArgUtils.getStringNull(etfRoot, "AC_NO"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "CRD_NO", HiArgUtils.getStringNull(etfRoot, "CRD_NO"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "ORG_NO", HiArgUtils.getStringNull(etfRoot, "ORG_NO"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "NOD_NO", HiArgUtils.getStringNull(etfRoot, "NOD_NO"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "NOD_TRC", HiArgUtils.getStringNull(etfRoot, "NOD_TRC"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "HLOG_NO", HiArgUtils.getStringNull(etfRoot, "HLOG_NO"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TCK_NO", HiArgUtils.getStringNull(etfRoot, "TCK_NO"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "HRSP_CD", HiArgUtils.getStringNull(etfRoot, "HRSP_CD"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "HTXN_STS", HiArgUtils.getStringNull(etfRoot, "HTXN_STS"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "FRSP_CD", HiArgUtils.getStringNull(etfRoot, "FRSP_CD"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TCUS_ID", HiArgUtils.getStringNull(etfRoot, "TCUS_ID"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TCUS_NM", HiArgUtils.getStringNull(etfRoot, "TCUS_NM"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TACC_DT", HiArgUtils.getStringNull(etfRoot, "TACC_DT"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TLOG_NO", HiArgUtils.getStringNull(etfRoot, "TLOG_NO"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TRSP_CD", HiArgUtils.getStringNull(etfRoot, "TRSP_CD"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TTXN_STS", HiArgUtils.getStringNull(etfRoot, "TTXN_STS"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TXN_STS", HiArgUtils.getStringNull(etfRoot, "TXN_STS"), true);
 
     for (int i = 0; i < args.size(); ++i) {
       String key = args.getName(i);
       String value = args.getValue(i);
       sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, key, value, true);
     }
 
     String LogNo = HiArgUtils.getStringNotNull(etfRoot, "LOG_NO");
 
     sqlcmd.append(" WHERE LOG_NO='");
     sqlcmd.append(LogNo);
     sqlcmd.append("'");
     Logger log = HiLog.getLogger(msg);
     if (log.isInfoEnabled()) {
       log.info(sqlcmd);
     }
     int ret = ctx.getDataBaseUtil().execUpdate(sqlcmd.toString());
     if (ret == 0) {
       return 2;
     }
     return 0;
   }
 
   public int UpdateJournalThird(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     String txnjnltbl = ctx.getJnlTable();
     if (txnjnltbl == null) {
       throw new HiException("220052", "未定义流水表");
     }
 
     HiETF etfRoot = (HiETF)mess.getBody();
 
     StringBuffer sqlcmd = new StringBuffer();
     sqlcmd.append("UPDATE ");
     sqlcmd.append(txnjnltbl);
     sqlcmd.append(" SET ");
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TTXN_STS", HiArgUtils.getStringNull(etfRoot, "TTXN_STS"), false);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TCUS_ID", HiArgUtils.getStringNull(etfRoot, "TCUS_ID"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TCUS_NM", HiArgUtils.getStringNull(etfRoot, "TCUS_NM"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TACC_DT", HiArgUtils.getStringNull(etfRoot, "TACC_DT"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TLOG_NO", HiArgUtils.getStringNull(etfRoot, "TLOG_NO"), true);
 
     sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, "TRSP_CD", HiArgUtils.getStringNull(etfRoot, "TRSP_CD"), true);
 
     for (int i = 0; i < args.size(); ++i) {
       String key = args.getName(i);
       String value = args.getValue(i);
       sqlcmd = HiAtcLib.judgeStrcat(sqlcmd, key, value, true);
     }
 
     String LogNo = HiArgUtils.getStringNotNull(etfRoot, "LOG_NO");
 
     sqlcmd.append(" WHERE LOG_NO='");
     sqlcmd.append(LogNo);
     sqlcmd.append("'");
 
     Logger log = HiLog.getLogger(mess);
     if (log.isInfoEnabled()) {
       log.info(sqlcmd);
     }
     int ret = ctx.getDataBaseUtil().execUpdate(sqlcmd.toString());
     if (ret == 0) {
       return 2;
     }
     return 0;
   }
 
   public int UpdateOldJournalHost(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
 
     HiETF etfRoot = (HiETF)msg.getBody();
     String strOLogNo = HiArgUtils.getStringNotNull(etfRoot, "OLOG_NO");
     String strHTxnSt = HiArgUtils.getStringNotNull(etfRoot, "HTXN_STS");
     if (!(strHTxnSt.equals("S"))) {
       throw new HiException("220054", "交易失败，不更新原交易流水");
     }
 
     HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
     if ((attr.isIntegtypeHand()) || (attr.isIntegtypeSys()))
       strHTxnSt = "C";
     else if (attr.isIntegtypeCCL())
       strHTxnSt = "D";
     else {
       throw new HiException("220055", "交易属性非冲正及撤销，不需更新原交易流水,配置错");
     }
 
     int ret = HiAtcLib.updTxnJnlSts(ctx, "HST", strOLogNo, strHTxnSt, null, null);
 
     if (ret == -1) {
       throw new HiException("220053", "更新原交易流水状态失败");
     }
 
     return ret;
   }
 
   public int UpdateOldJournalThird(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     HiETF etfRoot = (HiETF)msg.getBody();
     String strOLogNo = HiArgUtils.getStringNotNull(etfRoot, "OLOG_NO");
     String strTTxnSt = HiArgUtils.getStringNotNull(etfRoot, "TTXN_STS");
     if (!(strTTxnSt.equals("S"))) {
       throw new HiException("220054", "交易失败，不更新原交易流水");
     }
 
     HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
 
     if ((attr.isIntegtypeHand()) || (attr.isIntegtypeSys()))
       strTTxnSt = "C";
     else if (attr.isIntegtypeCCL())
       strTTxnSt = "D";
     else {
       throw new HiException("220055", "交易属性非冲正及撤销，不需更新原交易流水,配置错");
     }
 
     int ret = HiAtcLib.updTxnJnlSts(ctx, "THD", strOLogNo, null, strTTxnSt, null);
 
     if (ret == -1) {
       throw new HiException("220053", "更新原交易流水状态失败");
     }
 
     return ret;
   }
 
   public int UpdateOldJournal(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     HiETF etfRoot = (HiETF)msg.getBody();
     String strOLogNo = HiArgUtils.getStringNotNull(etfRoot, "OLOG_NO");
     String strTTxnSt = HiArgUtils.getStringNull(etfRoot, "TTXN_STS");
     String strHTxnSt = HiArgUtils.getStringNull(etfRoot, "HTXN_STS");
     if (((strTTxnSt != null) && (!(strTTxnSt.equals("S")))) || ((strHTxnSt != null) && (!(strHTxnSt.equals("S")))) || ((strTTxnSt == null) && (strHTxnSt == null)))
     {
       throw new HiException("220054", "交易失败，不更新原交易流水");
     }
 
     String strTxnSts = null;
 
     HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
 
     if ((attr.isIntegtypeHand()) || (attr.isIntegtypeSys()))
       strTTxnSt = "C";
     else if (attr.isIntegtypeCCL())
       strTTxnSt = "D";
     else {
       throw new HiException("220055", "交易属性非冲正及撤销，不需更新原交易流水,配置错");
     }
 
     int ret = HiAtcLib.updTxnJnlSts(ctx, "ALL", strOLogNo, strTxnSts, strTxnSts, strTxnSts);
 
     if (ret == -1) {
       throw new HiException("220053", "更新原交易流水状态失败");
     }
 
     return ret;
   }
 }