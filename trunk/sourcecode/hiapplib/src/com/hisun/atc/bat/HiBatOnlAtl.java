 package com.hisun.atc.bat;
 
 import com.hisun.atc.HiDBUtils;
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.atc.common.HiAtcLib;
 import com.hisun.atc.common.HiDBCursor;
 import com.hisun.atc.common.HiDbtSqlHelper;
 import com.hisun.atc.common.HiDbtUtils;
 import com.hisun.atc.common.HiFile;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.engine.invoke.impl.HiAttributesHelper;
 import com.hisun.engine.invoke.load.HiDelegate;
 import com.hisun.exception.HiAppException;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiICSProperty;
 import com.hisun.util.HiStringManager;
 import com.hisun.util.HiStringUtils;
 import com.hisun.util.HiXmlHelper;
 import java.io.File;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 import org.dom4j.Element;
 
 public class HiBatOnlAtl
 {
   public static final String BATCH_FORMAT = "BatFormat";
   private static HashMap gDskNoPool = new HashMap();
 
   private static HiStringManager sm = HiStringManager.getManager();
   private final String BATCH_CONFIG = "BatConfig";
   private final String LOCALDATDIR = "dat/local";
 
   public HiBatOnlAtl()
   {
     this.BATCH_CONFIG = "BatConfig";
 
     this.LOCALDATDIR = "dat/local";
   }
 
   public static synchronized boolean containsPool(String strKey)
   {
     return gDskNoPool.containsKey(strKey);
   }
 
   public static synchronized void putPool(String strKey)
     throws HiException
   {
     if (gDskNoPool.containsKey(strKey)) {
       throw new HiAppException(-1, "220021", "");
     }
     gDskNoPool.put(strKey, strKey);
   }
 
   public static synchronized void removePool(String strKey) {
     gDskNoPool.remove(strKey);
   }
 
   public int AppendDiskNo(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String strDskNo = null;
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     if (args.contains("REGITM")) {
       strDskNo = args.get("REGITM");
     } else {
       strDskNo = etf.getChildValue("DSK_NO");
       if (StringUtils.isEmpty(strDskNo)) {
         throw new HiAppException(-1, "220019", "");
       }
     }
     if (StringUtils.isEmpty(strDskNo)) {
       throw new HiAppException(-1, "220020", "");
     }
 
     putPool(strDskNo);
     return 0;
   }
 
   public int BatchEtfToPriSpace(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
     if (priBatchInfo == null) {
       priBatchInfo = new HiPriBatchInfo();
     }
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isDebugEnabled()) {
       log.debug("BatchEtfToPriSpace before:" + priBatchInfo);
     }
 
     String brno = etf.getChildValue("BR_NO");
     if (StringUtils.isEmpty(brno)) {
       throw new HiAppException(-1, "220022", "");
     }
     priBatchInfo.pubBatchInfo.br_no = brno;
 
     String BusTyp = etf.getChildValue("BUS_TYP");
     if (StringUtils.isEmpty(BusTyp)) {
       throw new HiAppException(-1, "220023", "");
     }
     priBatchInfo.pubBatchInfo.bus_typ = BusTyp;
 
     String CrpCod = etf.getChildValue("CRP_CD");
     if (StringUtils.isEmpty(CrpCod)) {
       throw new HiAppException(-1, "220024", "");
     }
     priBatchInfo.pubBatchInfo.crp_cd = CrpCod;
 
     String ActDat = etf.getChildValue("ACC_DT");
     if (StringUtils.isEmpty(ActDat)) {
       etf.setChildValue("ACC_DT", ctx.getStrProp("ACC_DT"));
     }
     priBatchInfo.pubBatchInfo.acc_dt = ActDat;
 
     priBatchInfo.pubBatchInfo.rcv_tm = String.valueOf(System.currentTimeMillis());
 
     priBatchInfo.setPriBatchInfo(ctx);
 
     priBatchInfo.pubBatchInfo.setValuesFromETF(etf);
 
     if (log.isDebugEnabled()) {
       log.debug("BatchEtfToPriSpace after:" + priBatchInfo);
     }
 
     return 0;
   }
 
   public int BatchPriSpaceToEtf(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
     if (priBatchInfo == null)
       throw new HiAppException(-1, "220025", "");
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isDebugEnabled()) {
       log.debug("before:" + priBatchInfo);
     }
 
     priBatchInfo.setETFValues(ctx.getCurrentMsg().getETFBody());
 
     if (log.isDebugEnabled()) {
       log.debug("after:" + priBatchInfo);
     }
     return 0;
   }
 
   public int CancelBatchRedo(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String dskNo = HiArgUtils.getStringNotNull(ctx.getCurrentMsg().getETFBody(), "DskNo");
 
     return 0;
   }
 
   public int CollateBatchResult(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
     if (priBatchInfo == null) {
       throw new HiAppException(-1, "220025", "");
     }
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isDebugEnabled()) {
       log.debug("before:" + priBatchInfo);
     }
     HiPubBatchInfo sBatchInfo = new HiPubBatchInfo();
     sBatchInfo.dsk_no = priBatchInfo.pubBatchInfo.dsk_no;
 
     String aValue1 = "pubbatrec";
     if (args.contains("BatTxnJnl")) {
       aValue1 = getBatchName(args, "BatTxnJnl", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
     }
 
     String aValue2 = "pubbatrsl";
     if (args.contains("BatTxnJnl")) {
       aValue2 = getBatchName(args, "BatHstRsl", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
     }
 
     String strSql = "SELECT LogNo, TxnAmt FROM " + aValue1 + " WHERE DskNo = '" + sBatchInfo.dsk_no + "' ORDER BY LogNo";
 
     if (log.isInfoEnabled()) {
       log.info(HiStringManager.getManager().getString("HiBatOnlAtl.CollateBatchResult.strSQL1", strSql));
     }
 
     String strSql2 = "SELECT LogNo, TxnAmt, HLogNo, HRspCd, HTxnSt FROM " + aValue2 + " WHERE DskNo = '" + sBatchInfo.dsk_no + "' ORDER BY LogNo";
 
     if (log.isInfoEnabled()) {
       log.info(HiStringManager.getManager().getString("HiBatOnlAtl.CollateBatchResult.strSQL2", strSql2));
     }
 
     HiDBCursor cursor1 = HiDbtUtils.dbtsqlcursor(strSql, "O", null, null, ctx);
 
     HiDBCursor cursor2 = HiDbtUtils.dbtsqlcursor(strSql2, "O", null, null, ctx);
 
     int iOverFlag1 = 0;
     int iOverFlag2 = 0;
     int iFlag = 0;
     int iErrFlag = 0;
     int lSucCnt = 0;
     double fSucAmt = 0.0D;
     int i = 0;
     String aRecLogNo = "";
     String aRecTxnAmt = "";
     String aRslLogNo = "";
     String aRslTxnAmt = "";
     String aHLogNo = "";
     String aHRspCd = "";
     String aHTxnSt = "";
     for (; (iOverFlag1 == 0) || (iOverFlag2 == 0); ++i)
     {
       HashMap values;
       if ((iFlag <= 0) && (iOverFlag1 == 0)) {
         values = cursor1.next();
         if (values == null) {
           iOverFlag1 = 1;
         } else {
           aRecLogNo = (String)values.get("LOGNO");
           aRecTxnAmt = (String)values.get("TXNAMT");
         }
       }
 
       if ((iFlag >= 0) && (iOverFlag2 == 0))
       {
         values = cursor2.next();
         if (values == null) {
           iOverFlag2 = 1;
         } else {
           aRslLogNo = (String)values.get("LOGNO");
           aRslTxnAmt = (String)values.get("TXNAMT");
           aHLogNo = (String)values.get("HLOGNO");
           aHRspCd = (String)values.get("HRSPCD");
           aHTxnSt = (String)values.get("HTXNST");
         }
       }
       if ((iOverFlag1 == 1) && (iOverFlag2 == 1)) {
         break;
       }
       if ((((iOverFlag1 == 1) || (aRecLogNo.compareTo(aRslLogNo) > 0))) && (iOverFlag2 == 0))
       {
         iFlag = 1;
         iErrFlag = 1;
 
         strSql2 = "UPDATE " + aValue2 + " SET ChkFlg = '2' WHERE LogNo = '" + aRslLogNo + "'";
 
         if (log.isInfoEnabled()) {
           log.info(HiStringManager.getManager().getString("HiBatOnlAtl.CollateBatchResult.strSQL2", strSql2));
         }
         try
         {
           ctx.getDataBaseUtil().execUpdate(strSql2);
         } catch (HiException e) {
           if (log.isInfoEnabled()) {
             log.info(HiStringManager.getManager().getString("HiBatOnAtl.Collate.Update", "CollateBatchResult", strSql2));
           }
 
           ctx.getDataBaseUtil().rollback();
           iErrFlag = 1;
           break label1395:
         }
         ctx.getDataBaseUtil().commit();
         log.warn(HiStringManager.getManager().getString("HiBatOnAtl.Collate.Update2", aValue2, aRslLogNo));
       }
       else if ((((iOverFlag2 == 1) || (aRecLogNo.compareTo(aRslLogNo) < 0))) && (iOverFlag1 == 0))
       {
         iFlag = -1;
         strSql = "UPDATE " + aValue1 + " SET HPrChk = '2' WHERE LogNo = '" + aRecLogNo + "'";
 
         if (log.isInfoEnabled()) {
           log.info(HiStringManager.getManager().getString("HiBatOnlAtl.CollateBatchResult.strSQL1", strSql));
         }
         try
         {
           ctx.getDataBaseUtil().execUpdate(strSql);
         } catch (HiException e) {
           log.warn(HiStringManager.getManager().getString("HiBatOnAtl.Collate.Update3", strSql));
 
           ctx.getDataBaseUtil().rollback();
           iErrFlag = 1;
           break label1395:
         }
         ctx.getDataBaseUtil().commit();
       }
       else {
         iFlag = 0;
 
         if (Math.abs(Float.valueOf(aRslTxnAmt).compareTo(Float.valueOf(aRecTxnAmt))) > 1.4E-45F)
         {
           iErrFlag = 1;
           strSql = "UPDATE " + aValue1 + " SET HPrChk = '2', HLogNo = '" + aHLogNo + "', HRspCd = '" + aHRspCd + "', HTxnSt = '" + aHTxnSt + "' WHERE LogNo = '" + aRecLogNo + "'";
 
           if (log.isInfoEnabled()) {
             log.info(HiStringManager.getManager().getString("HiBatOnlAtl.CollateBatchResult.strSQL1", strSql));
           }
 
           try
           {
             ctx.getDataBaseUtil().execUpdate(strSql);
           } catch (HiException e) {
             log.warn(HiStringManager.getManager().getString("HiBatOnAtl.Collate.Update4", strSql));
 
             ctx.getDataBaseUtil().rollback();
             iErrFlag = 1;
             break label1395:
           }
           ctx.getDataBaseUtil().commit();
           log.warn(HiStringManager.getManager().getString("HiBatOnAtl.Collate.Update5", strSql));
 
           strSql = "UPDATE " + aValue2 + " SET  ChkFlg = '2' WHERE  LogNo = '" + aRslLogNo + "'";
 
           if (log.isInfoEnabled()) {
             log.info(HiStringManager.getManager().getString("HiBatOnlAtl.CollateBatchResult.strSQL1", strSql));
           }
 
           try
           {
             ctx.getDataBaseUtil().execUpdate(strSql);
           } catch (HiException e) {
             log.warn(HiStringManager.getManager().getString("HiBatOnAtl.Collate.Update4", strSql));
 
             ctx.getDataBaseUtil().rollback();
             iErrFlag = 1;
             break label1395:
           }
           ctx.getDataBaseUtil().commit();
         }
         else
         {
           ++lSucCnt;
           fSucAmt += Float.valueOf(aRecTxnAmt).doubleValue();
           strSql = "UPDATE " + aValue1 + " SET HPrChk = '1', HLogNo = '" + aHLogNo + "', HRspCd = '" + aHRspCd + "', HTxnSt = '" + aHTxnSt + "' WHERE LogNo = '" + aRecLogNo + "'";
 
           if (log.isInfoEnabled()) {
             log.info(HiStringManager.getManager().getString("HiBatOnlAtl.CollateBatchResult.strSQL1", strSql));
           }
 
           try
           {
             ctx.getDataBaseUtil().execUpdate(strSql);
           } catch (HiException e) {
             log.warn(HiStringManager.getManager().getString("HiBatOnAtl.Collate.Update6", strSql));
 
             ctx.getDataBaseUtil().rollback();
             iErrFlag = 1;
             break label1395:
           }
           ctx.getDataBaseUtil().commit();
 
           strSql = "UPDATE " + aValue2 + " SET ChkFlg = '1' WHERE LogNo = '" + aRslLogNo + "'";
 
           if (log.isInfoEnabled()) {
             log.info(HiStringManager.getManager().getString("HiBatOnlAtl.CollateBatchResult.strSQL1", strSql));
           }
 
           try
           {
             ctx.getDataBaseUtil().execUpdate(strSql);
           } catch (HiException e) {
             log.warn(HiStringManager.getManager().getString("HiBatOnAtl.Collate.Update6", strSql));
 
             ctx.getDataBaseUtil().rollback();
             iErrFlag = 1;
             break label1395:
           }
           ctx.getDataBaseUtil().commit();
         }
       }
     }
     label1395: cursor1.close();
     cursor2.close();
     if (iErrFlag == 1) {
       throw new HiAppException(-1, "数据库操作错误或对帐致命错误");
     }
     if ("Y".equals(priBatchInfo.pubBatchInfo.sum_flg)) {
       sBatchInfo.suc_cnt = StringUtils.leftPad(String.valueOf(lSucCnt), 8, "0");
 
       sBatchInfo.suc_amt = String.valueOf(fSucAmt);
     }
     return 0;
   }
 
   public int CollateRepeatRecord(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
 
     HiETF etf = ctx.getCurrentMsg().getETFBody();
 
     HiPubBatchInfo pubBatchInfo = new HiPubBatchInfo();
 
     String brno = etf.getChildValue("BR_NO");
     if (StringUtils.isEmpty(brno)) {
       throw new HiAppException(-2, "220022", "");
     }
     pubBatchInfo.br_no = brno;
 
     String BusTyp = etf.getChildValue("BUS_TYP");
     if (StringUtils.isEmpty(BusTyp)) {
       throw new HiAppException(-2, "220023", "");
     }
     pubBatchInfo.bus_typ = BusTyp;
 
     String CrpCod = etf.getChildValue("CRP_CD");
     if (StringUtils.isEmpty(CrpCod)) {
       throw new HiAppException(-2, "220024", "");
     }
     pubBatchInfo.crp_cd = CrpCod;
 
     String strDskNam = null;
     if (args.contains("CHECKFILENAME")) {
       strDskNam = getBatchName(args, "CheckFileName", ctx, BusTyp, CrpCod);
     } else {
       if (log.isDebugEnabled()) {
         log.debug(sm.getString("HiBatOnlAtl.CollateRepeatRecord"));
       }
       strDskNam = ctx.getCurrentMsg().getETFBody().getChildValue("DSK_NM");
     }
 
     if (StringUtils.isEmpty(strDskNam)) {
       throw new HiAppException(-2, "220015");
     }
     pubBatchInfo.dsk_nm = strDskNam;
 
     String strActDat = (String)ctx.getBaseSource("ACC_DT");
 
     String strSQL = "SELECT  Dsk_No, Txn_Sqn, Sts, Chk_Flg FROM pubbatinf WHERE Acc_Dt = '" + strActDat + "' and Br_No='" + brno + "' and Bus_Typ='" + BusTyp + "' and Crp_Cd ='" + CrpCod + "' and Dsk_Nm='" + strDskNam + "' and Cmt_Flg != 'R'";
 
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiBatOnlAtl.CollateRepeatRecord1", strSQL));
     }
 
     List list = ctx.getDataBaseUtil().execQuery(strSQL);
     if ((list == null) || (list.size() == 0)) {
       return 0;
     }
 
     HashMap info = (HashMap)list.get(0);
     String strStatus = (String)info.get("STS");
     if ("N".equalsIgnoreCase(strStatus)) {
       throw new HiAppException(-2, "220016");
     }
     pubBatchInfo.sts = strStatus;
 
     String strTxnSqn = (String)info.get("TXN_SQN");
     int nTxnSqn = Integer.parseInt(strTxnSqn);
     if ((((nTxnSqn >= 500) ? 1 : 0) & ((nTxnSqn <= 999) ? 1 : 0)) != 0) {
       throw new HiAppException(-2, "220017", strTxnSqn);
     }
 
     pubBatchInfo.txn_sqn = strTxnSqn;
 
     String strChkFlg = (String)info.get("CHK_FLG");
     if ("1".equalsIgnoreCase(strChkFlg)) {
       throw new HiAppException(-2, "220018");
     }
     pubBatchInfo.chk_flg = strChkFlg;
 
     String strDskNo = (String)info.get("DSK_NO");
 
     pubBatchInfo.dsk_no = strDskNo;
 
     etf.setChildValue("DSK_NO", strDskNo);
     etf.setChildValue("FRSP_CD", "341510");
     try {
       AppendDiskNo(args, ctx);
     }
     catch (HiException e) {
       etf.setChildValue("NODEL_FLG", "1");
       throw e;
     }
     try
     {
       BatchEtfToPriSpace(args, ctx);
     } catch (HiException e) {
       throw new HiAppException(-2, "设置批量私有数据错误");
     }
     HiAtcLib.dbtsqlupdrec(etf, "pubbatinf", "DSK_NO", strDskNo, 0, ctx);
 
     return 1;
   }
 
   public int ConfirmBatch(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiETF etf = ctx.getCurrentMsg().getETFBody();
 
     String dskNo = etf.getChildValue("DskNo");
     if (dskNo == null)
       throw new HiException("220082", "DskNo");
     try
     {
       AppendDiskNo(args, ctx);
     } catch (HiException e) {
       etf.setChildValue("ProMsg", "本批次正在处理中");
       etf.setChildValue("NoDelFlag", "1");
       DeleteDiskNo(args, ctx);
       throw new HiAppException(-1, "220081");
     }
 
     String tlrId = etf.getChildValue("TlrId");
     if (tlrId == null) {
       throw new HiAppException(-1, "220082", "TlrId");
     }
 
     HiDbtUtils.dbtsqlrdrec("pubbatinf", "DskNo", dskNo, 0, etf, ctx);
 
     String ChkFlg = etf.getChildValue("ChkFlg");
     if (ChkFlg == null) {
       throw new HiAppException(-1, "220082", "ChkFlg");
     }
 
     if ("1".equals(ChkFlg)) {
       throw new HiAppException(-1, "220084");
     }
     if (!("P".equals(ChkFlg))) {
       throw new HiAppException(-1, "220085");
     }
 
     String TxnMod = etf.getChildValue("TxnMod");
     if (TxnMod == null) {
       throw new HiAppException(-1, "220082", "TxnMod");
     }
 
     if ((TxnMod.equals("2")) || (TxnMod.equals("3"))) {
       return 1;
     }
 
     String SndTlr = etf.getChildValue("SndTlr");
     if (SndTlr == null) {
       throw new HiAppException(-1, "220082", "SndTlr");
     }
 
     if (!(SndTlr.equals(tlrId))) {
       throw new HiAppException(-1, "220086");
     }
 
     String strSql = "UPDATE pubbatinf SET ChkFlg = '1',ChkTlr = '" + tlrId + "' WHERE  DskNo  = '" + dskNo + "'";
     try
     {
       ctx.getDataBaseUtil().execUpdate(strSql);
     } catch (HiException e) {
       ctx.getDataBaseUtil().rollback();
       throw e;
     }
     ctx.getDataBaseUtil().commit();
 
     etf.setChildValue("ChkFlg", "1");
     etf.setChildValue("ChkTlr", tlrId);
     return 0;
   }
 
   public int CopyBatchFile(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     SetBatchFileName(args, ctx);
     SetBatchCopyName(args, ctx);
     CopyFileFromLocal(args, ctx);
     return 0;
   }
 
   public int CopyFileFromLocal(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
     if (priBatchInfo == null) {
       throw new HiAppException(-1, "220025", "");
     }
     HiFile.copy(priBatchInfo.fileCurr, priBatchInfo.filOut);
     return 0;
   }
 
   public int SetBatchCopyName(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
     if (priBatchInfo == null)
       throw new HiAppException(-1, "220025");
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isDebugEnabled()) {
       log.debug("before:" + priBatchInfo);
     }
 
     if (!(args.contains("PathOut"))) {
       throw new HiAppException(-1, "");
     }
     String pathOut = args.get("PathOut");
     if (!(args.contains("FileNameOut"))) {
       throw new HiAppException(-1, "");
     }
     String fileNameOut = args.get("FileNameOut");
     priBatchInfo.pubBatchInfo.fil_nm = fileNameOut;
     ctx.getCurrentMsg().getETFBody().setChildValue("FIL_NM", fileNameOut);
 
     if (!(pathOut.endsWith(File.separator))) {
       pathOut = File.separator + pathOut;
     }
     String strPath = HiICSProperty.getWorkDir() + pathOut + File.separator + fileNameOut;
 
     priBatchInfo.filOut = strPath;
     if (log.isDebugEnabled()) {
       log.debug("after:" + priBatchInfo);
     }
     return 0;
   }
 
   public int CreateBatchFile(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     SetBatchFormatName(args, ctx);
     SetBatchFileName(args, ctx);
     CreateFile(args, ctx);
     return 0;
   }
 
   private int CreateFile(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
     if (priBatchInfo == null) {
       throw new HiAppException(-1, "220025");
     }
     HiBatchProcess formatNode = findFormatNode(priBatchInfo.formatName, ctx);
     HiPackInfo packInfo = new HiPackInfo();
     packInfo.formatName = args.get("FormatName");
 
     packInfo.fileName = priBatchInfo.fileCurr;
     if (!(packInfo.fileName.startsWith(File.separator))) {
       packInfo.fileName = HiICSProperty.getWorkDir() + File.separator + packInfo.fileName;
     }
 
     packInfo.applyLogNoFlag = args.get("ApplyLogNoFlag");
 
     String sqlName = args.get("SqlName");
     if (StringUtils.isEmpty(sqlName)) {
       throw new HiException("220026", "SqlName");
     }
     packInfo.sqlCond = HiDbtSqlHelper.getDynSentence(ctx, sqlName);
     if (StringUtils.isEmpty(packInfo.sqlCond)) {
       throw new HiException("220080");
     }
     packInfo.tableName = args.get("TableName");
     formatNode.exportFromDB(ctx, packInfo);
     return 0;
   }
 
   public int DeleteDiskNo(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String strDskNo = null;
     if (args.contains("RegItm")) {
       strDskNo = args.get("RegItm");
     } else {
       strDskNo = ctx.getCurrentMsg().getETFBody().getChildValue("DSK_NO");
       if (strDskNo == null)
         return 0;
     }
     String strNoDelFlag = ctx.getCurrentMsg().getETFBody().getChildValue("NODEL_FLG");
 
     if (strNoDelFlag != null) {
       return 0;
     }
     if (gDskNoPool.size() == 0) {
       return 0;
     }
     if (containsPool(strDskNo)) {
       removePool(strDskNo);
     }
 
     return 0;
   }
 
   public int DeleteDiskNoAndRollback(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiDBUtils.RollbackWork(args, ctx);
     return DeleteDiskNo(args, ctx);
   }
 
   public int ExportFromDB(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiPackInfo packInfo = new HiPackInfo();
     packInfo.formatName = args.get("FormatName");
     packInfo.applyLogNoFlag = args.get("ApplyLogNoFlag");
     packInfo.fileName = args.get("FileName");
     if (StringUtils.isEmpty(packInfo.fileName)) {
       throw new HiException("220026", "FileName");
     }
 
     if (!(packInfo.fileName.startsWith(File.separator))) {
       packInfo.fileName = HiICSProperty.getWorkDir() + File.separator + packInfo.fileName;
     }
 
     String sqlName = args.get("SqlName");
     if (StringUtils.isEmpty(sqlName)) {
       throw new HiException("220026", "SqlName");
     }
     packInfo.sqlCond = HiDbtSqlHelper.getDynSentence(ctx, sqlName);
     if (StringUtils.isEmpty(packInfo.sqlCond)) {
       throw new HiException("220080");
     }
     packInfo.tableName = args.get("TableName");
     HiBatchProcess formatNode = findFormatNode(packInfo.formatName, ctx);
     return formatNode.exportFromDB(ctx, packInfo);
   }
 
   private HiBatchProcess findFormatNode(String formatName, HiMessageContext ctx)
     throws HiException
   {
     if (StringUtils.isEmpty(formatName)) {
       ctx.getCurrentMsg().getETFBody().setChildValue("FRSP_CD", "341796");
       throw new HiAppException(-1, "220033", "");
     }
 
     HiDelegate rootObj = (HiDelegate)ctx.getProperty("CONFIGDECLARE", "BatFormat");
 
     if (rootObj == null) {
       throw new HiException("220204", formatName);
     }
 
     HiBatchProcess pro = (HiBatchProcess)rootObj.getChildsMap().get(formatName);
 
     if (pro == null) {
       throw new HiException("220204", formatName);
     }
 
     return pro;
   }
 
   public String getBatchName(HiATLParam args, String name, HiMessageContext ctx, String busTyp, String crpCod)
     throws HiException
   {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isInfoEnabled()) {
       log.info("getBatchName:name[" + name + "]");
     }
     String value = null;
     String name1 = args.get(name);
     if (name1.startsWith("#")) {
       if (StringUtils.isEmpty(busTyp)) {
         throw new HiException("220013");
       }
 
       if (StringUtils.isEmpty(crpCod)) {
         throw new HiException("220013");
       }
 
       Element rootNode = (Element)ctx.getProperty("CONFIGDECLARE", "BatConfig");
 
       Element busNode = HiXmlHelper.getNodeByAttr(rootNode, "BUS_TYP", "code", busTyp);
 
       Element crpNode = HiXmlHelper.getNodeByAttr(busNode, "CRP_CD", "code", crpCod);
 
       String name2 = name1.substring(1);
 
       Iterator list = crpNode.elementIterator();
       while (list.hasNext()) {
         Element item = (Element)list.next();
         String name3 = item.attributeValue("name");
         if (name2.equals(name3)) {
           value = item.attributeValue("value");
           break;
         }
       }
     } else {
       value = name1;
     }
 
     return value;
   }
 
   public int GetDiskNo(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
     if (priBatchInfo == null) {
       throw new HiAppException(-1, "220025", "");
     }
     priBatchInfo.pubBatchInfo.dsk_no = HiAtcLib.bpbgetbatno(ctx);
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     etf.setChildValue("DSK_NO", priBatchInfo.pubBatchInfo.dsk_no);
 
     return 0;
   }
 
   public int ImportToDB(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiPackInfo packInfo = new HiPackInfo();
     packInfo.formatName = args.get("FormatName");
     packInfo.applyLogNoFlag = args.get("ApplyLogNoFlag");
     packInfo.fileName = args.get("FileName");
     if (StringUtils.isEmpty(packInfo.fileName)) {
       throw new HiException("220026", "FileName");
     }
 
     if (!(packInfo.fileName.startsWith(File.separator))) {
       packInfo.fileName = HiICSProperty.getWorkDir() + File.separator + packInfo.fileName;
     }
 
     packInfo.tableName = args.get("TableName");
     if (StringUtils.equalsIgnoreCase(args.get("IsUpdate"), "1"))
     {
       packInfo.isUpdate = true;
       packInfo.conSts = args.get("CndSts");
     }
 
     HiBatchProcess batchProcess = findFormatNode(packInfo.formatName, ctx);
     return batchProcess.importToDB(ctx, packInfo);
   }
 
   public int IsRepeatBatch(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     String LogNo = etf.getChildValue("LOG_NO");
     HiPubBatchInfo pubBatchInfo = new HiPubBatchInfo();
     if (LogNo.length() == 12) {
       pubBatchInfo.dsk_no = LogNo.trim();
       if (log.isInfoEnabled()) {
         log.info(HiStringManager.getManager().getString("HiBatOnlAtl.IsRepeatBatch", "IsRepeatBatch", LogNo));
       }
 
       etf.setChildValue("DSK_NO", pubBatchInfo.dsk_no);
       try
       {
         AppendDiskNo(args, ctx);
       } catch (HiException e) {
         etf.setChildValue("NODEL_FLG", "1");
         throw new HiAppException(-2, "220092", "IsRepeatBatch", e);
       }
 
       try
       {
         HiDbtUtils.dbtsqlrdrec("pubbatinf", "DSK_NO", pubBatchInfo.dsk_no, 0, etf, ctx);
       }
       catch (HiException e) {
         throw new HiAppException(-3, "220093", "IsRepeatBatch", e);
       }
 
       String Status = etf.getChildValue("STS");
       if (Status == null) {
         throw new HiAppException(-3, "220082", "Sts");
       }
 
       if (Status.equals("N")) {
         throw new HiAppException(-2, "220094", "IsRepeatBatch");
       }
       try
       {
         BatchEtfToPriSpace(args, ctx);
       } catch (HiException e) {
         throw new HiAppException(-3, "220091", "IsRepeatBatch", e);
       }
 
       String ChkFlg = etf.getChildValue("CHK_FLG");
       if (ChkFlg == null) {
         throw new HiAppException(-3, "220082", "CHK_FLG");
       }
 
       if (ChkFlg.equals("1")) {
         throw new HiAppException(-1, "220095");
       }
       return 1;
     }
     try {
       BatchEtfToPriSpace(args, ctx);
     } catch (HiException Status) {
       throw new HiAppException(-3, "220091", "IsRepeatBatch");
     }
 
     return 0;
   }
 
   public int ParseBatchFile(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     SetBatchTableName(args, ctx);
     SetBatchFormatName(args, ctx);
     SetBatchFileName(args, ctx);
     SetBatchApplyFlag(args, ctx);
 
     ParseFile(args, ctx);
     return 0;
   }
 
   private int ParseFile(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
     if (priBatchInfo == null) {
       throw new HiAppException(-1, "220025");
     }
 
     HiBatchProcess formatNode = findFormatNode(priBatchInfo.formatName, ctx);
 
     ctx.getDataBaseUtil().execUpdate("DELETE FROM %s WHERE Dsk_No = '%s'", priBatchInfo.tableName, priBatchInfo.pubBatchInfo.dsk_no);
 
     ctx.getDataBaseUtil().commit();
     HiPackInfo packInfo = new HiPackInfo();
     packInfo.fileName = priBatchInfo.fileCurr;
     packInfo.ornAmt = NumberUtils.toInt(priBatchInfo.pubBatchInfo.orn_amt);
     packInfo.ornCnt = NumberUtils.toInt(priBatchInfo.pubBatchInfo.orn_cnt);
     packInfo.tableName = priBatchInfo.tableName;
     packInfo.applyLogNoFlag = priBatchInfo.applyLogNoFlag;
     formatNode.importToDB(ctx, packInfo);
 
     if ((packInfo.ornAmt == 0L) && (packInfo.ornCnt == 0) && (packInfo.dTotalCnt != 0) && (packInfo.dTotalAmt != 0L))
     {
       String sqlCmd = "UPDATE pubbatinf SET Orn_Cnt = '%s', Orn_Amt = '%s' WHERE Dsk_No = '%s'";
 
       sqlCmd = HiStringUtils.format(sqlCmd, HiStringUtils.leftPad(packInfo.dTotalCnt, 8), HiStringUtils.leftPad(packInfo.dTotalAmt, 15), priBatchInfo.pubBatchInfo.dsk_no);
       ctx.getDataBaseUtil().execUpdate(sqlCmd);
     }
     return 0;
   }
 
   public int PasteBatchFile(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     SetBatchPasteName(args, ctx);
     SetBatchFileName(args, ctx);
     PasteFileInLocal(args, ctx);
     return 0;
   }
 
   private int PasteFileInLocal(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
     if (priBatchInfo == null) {
       throw new HiAppException(-1, "220025");
     }
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isInfoEnabled()) {
       log.info("copy file from [" + priBatchInfo.filIn + "] to [" + priBatchInfo.fileCurr + "]");
     }
 
     HiFile.copy(priBatchInfo.filIn, priBatchInfo.fileCurr);
     return 0;
   }
 
   public int QueryBatchStatus(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiETF etf = ctx.getCurrentMsg().getETFBody();
 
     String strDskNo = etf.getChildValue("DskNo");
     if (strDskNo == null) {
       throw new HiAppException(-1, "220082", "DskNo");
     }
 
     HiDbtUtils.dbtsqlrdrec("pubbatinf", "DskNo", strDskNo, 0, etf, ctx);
 
     int iFlag = 0;
     try {
       AppendDiskNo(args, ctx);
     } catch (HiException e) {
       iFlag = 1;
       etf.setChildValue("ProMsg", "本批次正在处理中");
       etf.setChildValue("NoDelFlag", "1");
     }
     DeleteDiskNo(args, ctx);
 
     HiPubBatchInfo pubBatchInfo = new HiPubBatchInfo();
 
     String ChkFlg = etf.getChildValue("ChkFlg");
     if (ChkFlg == null) {
       throw new HiAppException(-1, "220082", "ChkFlg");
     }
     pubBatchInfo.chk_flg = ChkFlg;
 
     String Status = etf.getChildValue("Status");
     if (Status == null) {
       throw new HiAppException(-1, "220082", "Status");
     }
     pubBatchInfo.sts = Status;
 
     HiPubBatchInfo sBatchInfo = pubBatchInfo;
 
     if (iFlag == 0) {
       if (StringUtils.equals(sBatchInfo.chk_flg, "0")) {
         etf.setChildValue("ProMsg", "本批次因出错停止处理");
       } else if (StringUtils.equals(sBatchInfo.chk_flg, "P")) {
         etf.setChildValue("ProMsg", "本批次需要被确认后才能继续处理");
       } else if ((StringUtils.equals(sBatchInfo.chk_flg, "1")) && (StringUtils.equals(sBatchInfo.sts, "N")))
       {
         etf.setChildValue("ProMsg", "本批次已被确认并已完成");
       }
       else if (StringUtils.equals(sBatchInfo.chk_flg, "1")) {
         etf.setChildValue("ProMsg", "本批次已被确认等待完成");
       } else {
         etf.setChildValue("ProMsg", "本批次确认状态不在规定范围内");
 
         Logger log = HiLog.getLogger(ctx.getCurrentMsg());
         log.error(HiStringManager.getManager().getString("220087", "QueryBatchStatus", sBatchInfo.chk_flg));
       }
 
     }
 
     return 0;
   }
 
   public int RegisterBatch(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
     if (priBatchInfo == null)
       throw new HiAppException(-1, "220025", "");
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isDebugEnabled()) {
       log.debug("before:" + priBatchInfo);
     }
     HiPubBatchInfo sBatchInfo = priBatchInfo.pubBatchInfo;
 
     if ("1".equals(sBatchInfo.txn_mod))
       sBatchInfo.cmt_flg = "0";
     else {
       sBatchInfo.cmt_flg = "1";
     }
 
     HiETF etf = ctx.getCurrentMsg().getETFBody();
 
     String chkFlg = etf.getChildValue("CHK_FLG");
     if (chkFlg != null)
       sBatchInfo.chk_flg = chkFlg;
     else {
       sBatchInfo.chk_flg = "0";
     }
     String SumFlg = etf.getChildValue("SUM_FLG");
     if (SumFlg != null)
       sBatchInfo.sum_flg = SumFlg;
     else {
       sBatchInfo.sum_flg = "N";
     }
     String UpdFlg = etf.getChildValue("UPD_FLG");
     if (UpdFlg != null)
       sBatchInfo.upd_flg = UpdFlg;
     else {
       sBatchInfo.upd_flg = "N";
     }
     sBatchInfo.sts = "E";
     sBatchInfo.txn_sqn = "000";
 
     sBatchInfo.snd_cnt = "000";
     sBatchInfo.lst_tm = "0000000000";
     sBatchInfo.frsp_cd = "120001";
 
     if (log.isDebugEnabled()) {
       log.debug("after:" + sBatchInfo);
     }
     try
     {
       HiAtcLib.insertRecord("pubbatinf", sBatchInfo, ctx);
     } catch (HiException e) {
       ctx.getDataBaseUtil().rollback();
       throw e;
     }
 
     ctx.getDataBaseUtil().commit();
     return 0;
   }
 
   public int RegisterBatchDiskNo(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     String strDskNo = etf.getChildValue("DSK_NO");
 
     if (StringUtils.isNotEmpty(strDskNo)) {
       return 0;
     }
     BatchEtfToPriSpace(args, ctx);
 
     GetDiskNo(args, ctx);
 
     RegisterBatch(args, ctx);
 
     AppendDiskNo(args, ctx);
 
     return 0;
   }
 
   public int PreRegisterBatchRedo(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiETF etf = ctx.getCurrentMsg().getETFBody();
 
     String strDskNo = etf.getChildValue("DskNo");
     if (strDskNo == null) {
       throw new HiAppException(-1, "220082", "DskNo");
     }
 
     HiRdoJnl sRdr = new HiRdoJnl();
     sRdr.LogNo = strDskNo;
     HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
     sRdr.TxnCod = attr.code();
     sRdr.Itv = attr.interval();
     sRdr.TmOut = attr.timeout();
     sRdr.MaxTms = attr.maxtimes();
     sRdr.RvsSts = "0";
     sRdr.ObjSvr = attr.objsvr();
     sRdr.ActDat = ((String)ctx.getBaseSource("ActDat"));
 
     int iRet = HiAtcLib.rdoTranRegister(sRdr);
     if (iRet != 0) {
       throw new HiAppException(-1, "220083");
     }
     return 0;
   }
 
   public int RegisterRedoDiskNo(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
     if (priBatchInfo == null) {
       throw new HiAppException(-1, "220025", "");
     }
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isDebugEnabled()) {
       log.debug("before:" + priBatchInfo);
     }
 
     String strDskNo = etf.getChildValue("DskNo");
     if (strDskNo != null) {
       priBatchInfo.pubBatchInfo.dsk_no = strDskNo;
       return 0;
     }
     BatchEtfToPriSpace(args, ctx);
 
     GetDiskNo(args, ctx);
 
     RegisterBatch(args, ctx);
 
     PreRegisterBatchRedo(args, ctx);
     try
     {
       AppendDiskNo(args, ctx);
     } catch (HiException e) {
       etf.setChildValue("NoDelFlag", "1");
       throw e;
     }
     if (log.isDebugEnabled()) {
       log.debug("after:" + priBatchInfo);
     }
     return 0;
   }
 
   public int SetBatchApplyFlag(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
     if (priBatchInfo == null) {
       throw new HiAppException(-1, "220025");
     }
     String strApplyFlag = "0";
     if (args.contains("ApplyLogNoFlag")) {
       strApplyFlag = getBatchName(args, "ApplyLogNoFlag", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
     }
 
     if ((!(strApplyFlag.equals("0"))) && (!(strApplyFlag.equals("1")))) {
       throw new HiAppException(-1, "220032", strApplyFlag);
     }
 
     priBatchInfo.applyLogNoFlag = strApplyFlag;
     return 0;
   }
 
   public int SetBatchFileName(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String strPathCurr = "dat/local";
     String strFileName = "01";
 
     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
     if (priBatchInfo == null)
       throw new HiAppException(-1, "220025", "");
     if (args.contains("PathCurr")) {
       strPathCurr = getBatchName(args, "PathCurr", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
     }
 
     if (args.contains("FileNameCurr")) {
       strFileName = getBatchName(args, "FileNameCurr", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
     }
 
     String strDskNo = priBatchInfo.pubBatchInfo.dsk_no;
     String strPath = HiICSProperty.getWorkDir() + File.separator + strPathCurr + File.separator + strDskNo + strFileName.trim();
 
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     if (log.isInfoEnabled()) {
       log.info(priBatchInfo.pubBatchInfo);
       log.info(sm.getString("HiBatOnlAtl.SetBatchPasteName", strPath));
     }
 
     priBatchInfo.fileCurr = strPath;
 
     return 0;
   }
 
   public int SetBatchFormatName(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
     if (priBatchInfo == null) {
       throw new HiAppException(-1, "220025");
     }
     if (!(args.contains("FormatName"))) {
       throw new HiAppException(-1, "220031", "");
     }
 
     String strFormatName = args.get("FormatName");
     if (strFormatName.startsWith("#")) {
       strFormatName = getBatchName(args, "FormatName", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
     }
 
     priBatchInfo.formatName = strFormatName;
     return 0;
   }
 
   public int SetBatchPasteName(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     if (!(args.contains("PathIn"))) {
       throw new HiAppException(-1, "220028", "");
     }
     if (!(args.contains("FileNameIn"))) {
       throw new HiAppException(-1, "220029", "");
     }
 
     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
     if (priBatchInfo == null) {
       throw new HiAppException(-1, "220025");
     }
 
     String strFileNameIn = getBatchName(args, "FileNameIn", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
 
     priBatchInfo.pubBatchInfo.dsk_nm = strFileNameIn;
 
     String strPathIn = getBatchName(args, "PathIn", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
 
     if (!(strPathIn.endsWith(File.separator))) {
       strPathIn = File.separator + strPathIn;
     }
 
     String strPath = HiICSProperty.getWorkDir() + strPathIn + File.separator + strFileNameIn.trim();
 
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiBatOnlAtl.SetBatchPasteName", strPath));
     }
 
     priBatchInfo.filIn = strPath;
     return 0;
   }
 
   public int SetBatchTableName(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     if (!(args.contains("TableName"))) {
       throw new HiAppException(-1, "220027", "");
     }
     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
     if (priBatchInfo == null)
       throw new HiAppException(-1, "220025", "");
     String strTableName = getBatchName(args, "TableName", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
 
     priBatchInfo.tableName = strTableName;
 
     return 0;
   }
 
   public int UpdateBatchStatus(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String strSql;
     String strSql;
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
     if (priBatchInfo == null) {
       throw new HiAppException(-1, "220025", "");
     }
     if (args.contains("CmtFlg")) {
       String CmtFlg = args.get("CmtFlg");
       if ((!(CmtFlg.equals("1"))) && (!(CmtFlg.equals("0"))) && (!(CmtFlg.equals("R"))))
       {
         throw new HiAppException(-1, "220088", CmtFlg);
       }
 
       priBatchInfo.pubBatchInfo.cmt_flg = CmtFlg;
       strSql = " UPDATE pubbatinf SET    Cmt_Flg = '" + CmtFlg + "' WHERE  Dsk_No  = '" + priBatchInfo.pubBatchInfo.dsk_no + "'";
       try
       {
         ctx.getDataBaseUtil().execUpdate(strSql);
       } catch (HiException e) {
         ctx.getDataBaseUtil().rollback();
         throw e;
       }
     }
 
     String RspCod = null;
     if (args.contains("RspCod"))
       RspCod = args.get("RspCod");
     else {
       RspCod = etf.getChildValue("RSP_CD");
     }
     if (RspCod != null) {
       if (RspCod.length() != 6) {
         throw new HiAppException(-1, "220089", "UpdateBatchStatus", RspCod);
       }
 
       strSql = " UPDATE pubbatinf SET FRsp_Cd = '" + RspCod + "' WHERE  Dsk_No  = '" + priBatchInfo.pubBatchInfo.dsk_no + "'";
       try
       {
         ctx.getDataBaseUtil().execUpdate(strSql);
       } catch (HiException e) {
         ctx.getDataBaseUtil().rollback();
         throw e;
       }
     }
 
     if (args.contains("TxnSqn")) {
       String TxnSqn = args.get("TxnSqn");
 
       strSql = " UPDATE pubbatinf SET Txn_Sqn = '" + TxnSqn + "' WHERE  Dsk_No  = '" + priBatchInfo.pubBatchInfo.dsk_no + "'";
       try
       {
         ctx.getDataBaseUtil().execUpdate(strSql);
       } catch (HiException e) {
         ctx.getDataBaseUtil().rollback();
         throw e;
       }
     }
     if (args.contains("Status")) {
       String Status = args.get("Status");
       if (!(Status.equals("N"))) {
         throw new HiAppException(-1, "220090", "UpdateBatchStatus", "Status");
       }
 
       strSql = " UPDATE pubbatinf SET    Sts = '" + Status + "' WHERE  Dsk_No  = '" + priBatchInfo.pubBatchInfo.dsk_no + "'";
       try
       {
         ctx.getDataBaseUtil().execUpdate(strSql);
       } catch (HiException e) {
         ctx.getDataBaseUtil().rollback();
         throw e;
       }
     }
     if (args.contains("ChkFlg")) {
       String ChkFlg = args.get("ChkFlg");
       if ((!(ChkFlg.equals("1"))) && (!(ChkFlg.equals("P")))) {
         throw new HiAppException(-1, "220090", "UpdateBatchStatus", "ChkFlg");
       }
 
       strSql = " UPDATE pubbatinf SET Chk_Flg = '" + ChkFlg + "' WHERE  Dsk_No  = '" + priBatchInfo.pubBatchInfo.dsk_no + "'";
       try
       {
         ctx.getDataBaseUtil().execUpdate(strSql);
       } catch (HiException e) {
         ctx.getDataBaseUtil().rollback();
         throw e;
       }
     }
     if (args.contains("FilNam")) {
       String FilNam = getBatchName(args, "FilNam", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
 
       strSql = " UPDATE pubbatinf SET Fil_Nm = '" + FilNam + "' WHERE  Dsk_No  = '" + priBatchInfo.pubBatchInfo.dsk_no + "'";
       try
       {
         ctx.getDataBaseUtil().execUpdate(strSql);
       } catch (HiException e) {
         ctx.getDataBaseUtil().rollback();
         throw e;
       }
     }
     ctx.getDataBaseUtil().commit();
     return 0;
   }
 }