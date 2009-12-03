/*      */ package com.hisun.atc.bat;
/*      */ 
/*      */ import com.hisun.atc.HiDBUtils;
/*      */ import com.hisun.atc.common.HiArgUtils;
/*      */ import com.hisun.atc.common.HiAtcLib;
/*      */ import com.hisun.atc.common.HiDBCursor;
/*      */ import com.hisun.atc.common.HiDbtSqlHelper;
/*      */ import com.hisun.atc.common.HiDbtUtils;
/*      */ import com.hisun.atc.common.HiFile;
/*      */ import com.hisun.database.HiDataBaseUtil;
/*      */ import com.hisun.engine.invoke.impl.HiAttributesHelper;
/*      */ import com.hisun.engine.invoke.load.HiDelegate;
/*      */ import com.hisun.exception.HiAppException;
/*      */ import com.hisun.exception.HiException;
/*      */ import com.hisun.hilib.HiATLParam;
/*      */ import com.hisun.hilog4j.HiLog;
/*      */ import com.hisun.hilog4j.Logger;
/*      */ import com.hisun.message.HiETF;
/*      */ import com.hisun.message.HiMessage;
/*      */ import com.hisun.message.HiMessageContext;
/*      */ import com.hisun.util.HiICSProperty;
/*      */ import com.hisun.util.HiStringManager;
/*      */ import com.hisun.util.HiStringUtils;
/*      */ import com.hisun.util.HiXmlHelper;
/*      */ import java.io.File;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.lang.math.NumberUtils;
/*      */ import org.dom4j.Element;
/*      */ 
/*      */ public class HiBatOnlAtl
/*      */ {
/*      */   public static final String BATCH_FORMAT = "BatFormat";
/*   51 */   private static HashMap gDskNoPool = new HashMap();
/*      */ 
/*   53 */   private static HiStringManager sm = HiStringManager.getManager();
/*      */   private final String BATCH_CONFIG = "BatConfig";
/*      */   private final String LOCALDATDIR = "dat/local";
/*      */ 
/*      */   public HiBatOnlAtl()
/*      */   {
/*   76 */     this.BATCH_CONFIG = "BatConfig";
/*      */ 
/*   78 */     this.LOCALDATDIR = "dat/local";
/*      */   }
/*      */ 
/*      */   public static synchronized boolean containsPool(String strKey)
/*      */   {
/*   56 */     return gDskNoPool.containsKey(strKey);
/*      */   }
/*      */ 
/*      */   public static synchronized void putPool(String strKey)
/*      */     throws HiException
/*      */   {
/*   66 */     if (gDskNoPool.containsKey(strKey)) {
/*   67 */       throw new HiAppException(-1, "220021", "");
/*      */     }
/*   69 */     gDskNoPool.put(strKey, strKey);
/*      */   }
/*      */ 
/*      */   public static synchronized void removePool(String strKey) {
/*   73 */     gDskNoPool.remove(strKey);
/*      */   }
/*      */ 
/*      */   public int AppendDiskNo(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*   96 */     String strDskNo = null;
/*   97 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/*   98 */     if (args.contains("REGITM")) {
/*   99 */       strDskNo = args.get("REGITM");
/*      */     } else {
/*  101 */       strDskNo = etf.getChildValue("DSK_NO");
/*  102 */       if (StringUtils.isEmpty(strDskNo)) {
/*  103 */         throw new HiAppException(-1, "220019", "");
/*      */       }
/*      */     }
/*  106 */     if (StringUtils.isEmpty(strDskNo)) {
/*  107 */       throw new HiAppException(-1, "220020", "");
/*      */     }
/*      */ 
/*  110 */     putPool(strDskNo);
/*  111 */     return 0;
/*      */   }
/*      */ 
/*      */   public int BatchEtfToPriSpace(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  129 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/*  130 */     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
/*  131 */     if (priBatchInfo == null) {
/*  132 */       priBatchInfo = new HiPriBatchInfo();
/*      */     }
/*  134 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*  135 */     if (log.isDebugEnabled()) {
/*  136 */       log.debug("BatchEtfToPriSpace before:" + priBatchInfo);
/*      */     }
/*      */ 
/*  139 */     String brno = etf.getChildValue("BR_NO");
/*  140 */     if (StringUtils.isEmpty(brno)) {
/*  141 */       throw new HiAppException(-1, "220022", "");
/*      */     }
/*  143 */     priBatchInfo.pubBatchInfo.br_no = brno;
/*      */ 
/*  145 */     String BusTyp = etf.getChildValue("BUS_TYP");
/*  146 */     if (StringUtils.isEmpty(BusTyp)) {
/*  147 */       throw new HiAppException(-1, "220023", "");
/*      */     }
/*  149 */     priBatchInfo.pubBatchInfo.bus_typ = BusTyp;
/*      */ 
/*  151 */     String CrpCod = etf.getChildValue("CRP_CD");
/*  152 */     if (StringUtils.isEmpty(CrpCod)) {
/*  153 */       throw new HiAppException(-1, "220024", "");
/*      */     }
/*  155 */     priBatchInfo.pubBatchInfo.crp_cd = CrpCod;
/*      */ 
/*  157 */     String ActDat = etf.getChildValue("ACC_DT");
/*  158 */     if (StringUtils.isEmpty(ActDat)) {
/*  159 */       etf.setChildValue("ACC_DT", ctx.getStrProp("ACC_DT"));
/*      */     }
/*  161 */     priBatchInfo.pubBatchInfo.acc_dt = ActDat;
/*      */ 
/*  163 */     priBatchInfo.pubBatchInfo.rcv_tm = String.valueOf(System.currentTimeMillis());
/*      */ 
/*  166 */     priBatchInfo.setPriBatchInfo(ctx);
/*      */ 
/*  168 */     priBatchInfo.pubBatchInfo.setValuesFromETF(etf);
/*      */ 
/*  170 */     if (log.isDebugEnabled()) {
/*  171 */       log.debug("BatchEtfToPriSpace after:" + priBatchInfo);
/*      */     }
/*      */ 
/*  174 */     return 0;
/*      */   }
/*      */ 
/*      */   public int BatchPriSpaceToEtf(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  193 */     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
/*  194 */     if (priBatchInfo == null)
/*  195 */       throw new HiAppException(-1, "220025", "");
/*  196 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*  197 */     if (log.isDebugEnabled()) {
/*  198 */       log.debug("before:" + priBatchInfo);
/*      */     }
/*      */ 
/*  201 */     priBatchInfo.setETFValues(ctx.getCurrentMsg().getETFBody());
/*      */ 
/*  203 */     if (log.isDebugEnabled()) {
/*  204 */       log.debug("after:" + priBatchInfo);
/*      */     }
/*  206 */     return 0;
/*      */   }
/*      */ 
/*      */   public int CancelBatchRedo(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  221 */     String dskNo = HiArgUtils.getStringNotNull(ctx.getCurrentMsg().getETFBody(), "DskNo");
/*      */ 
/*  225 */     return 0;
/*      */   }
/*      */ 
/*      */   public int CollateBatchResult(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  250 */     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
/*  251 */     if (priBatchInfo == null) {
/*  252 */       throw new HiAppException(-1, "220025", "");
/*      */     }
/*  254 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*  255 */     if (log.isDebugEnabled()) {
/*  256 */       log.debug("before:" + priBatchInfo);
/*      */     }
/*  258 */     HiPubBatchInfo sBatchInfo = new HiPubBatchInfo();
/*  259 */     sBatchInfo.dsk_no = priBatchInfo.pubBatchInfo.dsk_no;
/*      */ 
/*  261 */     String aValue1 = "pubbatrec";
/*  262 */     if (args.contains("BatTxnJnl")) {
/*  263 */       aValue1 = getBatchName(args, "BatTxnJnl", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
/*      */     }
/*      */ 
/*  267 */     String aValue2 = "pubbatrsl";
/*  268 */     if (args.contains("BatTxnJnl")) {
/*  269 */       aValue2 = getBatchName(args, "BatHstRsl", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
/*      */     }
/*      */ 
/*  273 */     String strSql = "SELECT LogNo, TxnAmt FROM " + aValue1 + " WHERE DskNo = '" + sBatchInfo.dsk_no + "' ORDER BY LogNo";
/*      */ 
/*  275 */     if (log.isInfoEnabled()) {
/*  276 */       log.info(HiStringManager.getManager().getString("HiBatOnlAtl.CollateBatchResult.strSQL1", strSql));
/*      */     }
/*      */ 
/*  279 */     String strSql2 = "SELECT LogNo, TxnAmt, HLogNo, HRspCd, HTxnSt FROM " + aValue2 + " WHERE DskNo = '" + sBatchInfo.dsk_no + "' ORDER BY LogNo";
/*      */ 
/*  282 */     if (log.isInfoEnabled()) {
/*  283 */       log.info(HiStringManager.getManager().getString("HiBatOnlAtl.CollateBatchResult.strSQL2", strSql2));
/*      */     }
/*      */ 
/*  287 */     HiDBCursor cursor1 = HiDbtUtils.dbtsqlcursor(strSql, "O", null, null, ctx);
/*      */ 
/*  289 */     HiDBCursor cursor2 = HiDbtUtils.dbtsqlcursor(strSql2, "O", null, null, ctx);
/*      */ 
/*  291 */     int iOverFlag1 = 0;
/*  292 */     int iOverFlag2 = 0;
/*  293 */     int iFlag = 0;
/*  294 */     int iErrFlag = 0;
/*  295 */     int lSucCnt = 0;
/*  296 */     double fSucAmt = 0.0D;
/*  297 */     int i = 0;
/*  298 */     String aRecLogNo = "";
/*  299 */     String aRecTxnAmt = "";
/*  300 */     String aRslLogNo = "";
/*  301 */     String aRslTxnAmt = "";
/*  302 */     String aHLogNo = "";
/*  303 */     String aHRspCd = "";
/*  304 */     String aHTxnSt = "";
/*  305 */     for (; (iOverFlag1 == 0) || (iOverFlag2 == 0); ++i)
/*      */     {
/*      */       HashMap values;
/*  307 */       if ((iFlag <= 0) && (iOverFlag1 == 0)) {
/*  308 */         values = cursor1.next();
/*  309 */         if (values == null) {
/*  310 */           iOverFlag1 = 1;
/*      */         } else {
/*  312 */           aRecLogNo = (String)values.get("LOGNO");
/*  313 */           aRecTxnAmt = (String)values.get("TXNAMT");
/*      */         }
/*      */       }
/*      */ 
/*  317 */       if ((iFlag >= 0) && (iOverFlag2 == 0))
/*      */       {
/*  319 */         values = cursor2.next();
/*  320 */         if (values == null) {
/*  321 */           iOverFlag2 = 1;
/*      */         } else {
/*  323 */           aRslLogNo = (String)values.get("LOGNO");
/*  324 */           aRslTxnAmt = (String)values.get("TXNAMT");
/*  325 */           aHLogNo = (String)values.get("HLOGNO");
/*  326 */           aHRspCd = (String)values.get("HRSPCD");
/*  327 */           aHTxnSt = (String)values.get("HTXNST");
/*      */         }
/*      */       }
/*  330 */       if ((iOverFlag1 == 1) && (iOverFlag2 == 1)) {
/*      */         break;
/*      */       }
/*  333 */       if ((((iOverFlag1 == 1) || (aRecLogNo.compareTo(aRslLogNo) > 0))) && (iOverFlag2 == 0))
/*      */       {
/*  335 */         iFlag = 1;
/*  336 */         iErrFlag = 1;
/*      */ 
/*  338 */         strSql2 = "UPDATE " + aValue2 + " SET ChkFlg = '2' WHERE LogNo = '" + aRslLogNo + "'";
/*      */ 
/*  340 */         if (log.isInfoEnabled()) {
/*  341 */           log.info(HiStringManager.getManager().getString("HiBatOnlAtl.CollateBatchResult.strSQL2", strSql2));
/*      */         }
/*      */         try
/*      */         {
/*  345 */           ctx.getDataBaseUtil().execUpdate(strSql2);
/*      */         } catch (HiException e) {
/*  347 */           if (log.isInfoEnabled()) {
/*  348 */             log.info(HiStringManager.getManager().getString("HiBatOnAtl.Collate.Update", "CollateBatchResult", strSql2));
/*      */           }
/*      */ 
/*  352 */           ctx.getDataBaseUtil().rollback();
/*  353 */           iErrFlag = 1;
/*  354 */           break label1395:
/*      */         }
/*  356 */         ctx.getDataBaseUtil().commit();
/*  357 */         log.warn(HiStringManager.getManager().getString("HiBatOnAtl.Collate.Update2", aValue2, aRslLogNo));
/*      */       }
/*  360 */       else if ((((iOverFlag2 == 1) || (aRecLogNo.compareTo(aRslLogNo) < 0))) && (iOverFlag1 == 0))
/*      */       {
/*  362 */         iFlag = -1;
/*  363 */         strSql = "UPDATE " + aValue1 + " SET HPrChk = '2' WHERE LogNo = '" + aRecLogNo + "'";
/*      */ 
/*  366 */         if (log.isInfoEnabled()) {
/*  367 */           log.info(HiStringManager.getManager().getString("HiBatOnlAtl.CollateBatchResult.strSQL1", strSql));
/*      */         }
/*      */         try
/*      */         {
/*  371 */           ctx.getDataBaseUtil().execUpdate(strSql);
/*      */         } catch (HiException e) {
/*  373 */           log.warn(HiStringManager.getManager().getString("HiBatOnAtl.Collate.Update3", strSql));
/*      */ 
/*  375 */           ctx.getDataBaseUtil().rollback();
/*  376 */           iErrFlag = 1;
/*  377 */           break label1395:
/*      */         }
/*  379 */         ctx.getDataBaseUtil().commit();
/*      */       }
/*      */       else {
/*  382 */         iFlag = 0;
/*      */ 
/*  384 */         if (Math.abs(Float.valueOf(aRslTxnAmt).compareTo(Float.valueOf(aRecTxnAmt))) > 1.4E-45F)
/*      */         {
/*  386 */           iErrFlag = 1;
/*  387 */           strSql = "UPDATE " + aValue1 + " SET HPrChk = '2', HLogNo = '" + aHLogNo + "', HRspCd = '" + aHRspCd + "', HTxnSt = '" + aHTxnSt + "' WHERE LogNo = '" + aRecLogNo + "'";
/*      */ 
/*  391 */           if (log.isInfoEnabled()) {
/*  392 */             log.info(HiStringManager.getManager().getString("HiBatOnlAtl.CollateBatchResult.strSQL1", strSql));
/*      */           }
/*      */ 
/*      */           try
/*      */           {
/*  397 */             ctx.getDataBaseUtil().execUpdate(strSql);
/*      */           } catch (HiException e) {
/*  399 */             log.warn(HiStringManager.getManager().getString("HiBatOnAtl.Collate.Update4", strSql));
/*      */ 
/*  401 */             ctx.getDataBaseUtil().rollback();
/*  402 */             iErrFlag = 1;
/*  403 */             break label1395:
/*      */           }
/*  405 */           ctx.getDataBaseUtil().commit();
/*  406 */           log.warn(HiStringManager.getManager().getString("HiBatOnAtl.Collate.Update5", strSql));
/*      */ 
/*  408 */           strSql = "UPDATE " + aValue2 + " SET  ChkFlg = '2' WHERE  LogNo = '" + aRslLogNo + "'";
/*      */ 
/*  411 */           if (log.isInfoEnabled()) {
/*  412 */             log.info(HiStringManager.getManager().getString("HiBatOnlAtl.CollateBatchResult.strSQL1", strSql));
/*      */           }
/*      */ 
/*      */           try
/*      */           {
/*  417 */             ctx.getDataBaseUtil().execUpdate(strSql);
/*      */           } catch (HiException e) {
/*  419 */             log.warn(HiStringManager.getManager().getString("HiBatOnAtl.Collate.Update4", strSql));
/*      */ 
/*  421 */             ctx.getDataBaseUtil().rollback();
/*  422 */             iErrFlag = 1;
/*  423 */             break label1395:
/*      */           }
/*  425 */           ctx.getDataBaseUtil().commit();
/*      */         }
/*      */         else
/*      */         {
/*  429 */           ++lSucCnt;
/*  430 */           fSucAmt += Float.valueOf(aRecTxnAmt).doubleValue();
/*  431 */           strSql = "UPDATE " + aValue1 + " SET HPrChk = '1', HLogNo = '" + aHLogNo + "', HRspCd = '" + aHRspCd + "', HTxnSt = '" + aHTxnSt + "' WHERE LogNo = '" + aRecLogNo + "'";
/*      */ 
/*  435 */           if (log.isInfoEnabled()) {
/*  436 */             log.info(HiStringManager.getManager().getString("HiBatOnlAtl.CollateBatchResult.strSQL1", strSql));
/*      */           }
/*      */ 
/*      */           try
/*      */           {
/*  441 */             ctx.getDataBaseUtil().execUpdate(strSql);
/*      */           } catch (HiException e) {
/*  443 */             log.warn(HiStringManager.getManager().getString("HiBatOnAtl.Collate.Update6", strSql));
/*      */ 
/*  445 */             ctx.getDataBaseUtil().rollback();
/*  446 */             iErrFlag = 1;
/*  447 */             break label1395:
/*      */           }
/*  449 */           ctx.getDataBaseUtil().commit();
/*      */ 
/*  451 */           strSql = "UPDATE " + aValue2 + " SET ChkFlg = '1' WHERE LogNo = '" + aRslLogNo + "'";
/*      */ 
/*  454 */           if (log.isInfoEnabled()) {
/*  455 */             log.info(HiStringManager.getManager().getString("HiBatOnlAtl.CollateBatchResult.strSQL1", strSql));
/*      */           }
/*      */ 
/*      */           try
/*      */           {
/*  460 */             ctx.getDataBaseUtil().execUpdate(strSql);
/*      */           } catch (HiException e) {
/*  462 */             log.warn(HiStringManager.getManager().getString("HiBatOnAtl.Collate.Update6", strSql));
/*      */ 
/*  464 */             ctx.getDataBaseUtil().rollback();
/*  465 */             iErrFlag = 1;
/*  466 */             break label1395:
/*      */           }
/*  468 */           ctx.getDataBaseUtil().commit();
/*      */         }
/*      */       }
/*      */     }
/*  472 */     label1395: cursor1.close();
/*  473 */     cursor2.close();
/*  474 */     if (iErrFlag == 1) {
/*  475 */       throw new HiAppException(-1, "数据库操作错误或对帐致命错误");
/*      */     }
/*  477 */     if ("Y".equals(priBatchInfo.pubBatchInfo.sum_flg)) {
/*  478 */       sBatchInfo.suc_cnt = StringUtils.leftPad(String.valueOf(lSucCnt), 8, "0");
/*      */ 
/*  480 */       sBatchInfo.suc_amt = String.valueOf(fSucAmt);
/*      */     }
/*  482 */     return 0;
/*      */   }
/*      */ 
/*      */   public int CollateRepeatRecord(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  516 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*      */ 
/*  518 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/*      */ 
/*  522 */     HiPubBatchInfo pubBatchInfo = new HiPubBatchInfo();
/*      */ 
/*  524 */     String brno = etf.getChildValue("BR_NO");
/*  525 */     if (StringUtils.isEmpty(brno)) {
/*  526 */       throw new HiAppException(-2, "220022", "");
/*      */     }
/*  528 */     pubBatchInfo.br_no = brno;
/*      */ 
/*  530 */     String BusTyp = etf.getChildValue("BUS_TYP");
/*  531 */     if (StringUtils.isEmpty(BusTyp)) {
/*  532 */       throw new HiAppException(-2, "220023", "");
/*      */     }
/*  534 */     pubBatchInfo.bus_typ = BusTyp;
/*      */ 
/*  536 */     String CrpCod = etf.getChildValue("CRP_CD");
/*  537 */     if (StringUtils.isEmpty(CrpCod)) {
/*  538 */       throw new HiAppException(-2, "220024", "");
/*      */     }
/*  540 */     pubBatchInfo.crp_cd = CrpCod;
/*      */ 
/*  542 */     String strDskNam = null;
/*  543 */     if (args.contains("CHECKFILENAME")) {
/*  544 */       strDskNam = getBatchName(args, "CheckFileName", ctx, BusTyp, CrpCod);
/*      */     } else {
/*  546 */       if (log.isDebugEnabled()) {
/*  547 */         log.debug(sm.getString("HiBatOnlAtl.CollateRepeatRecord"));
/*      */       }
/*  549 */       strDskNam = ctx.getCurrentMsg().getETFBody().getChildValue("DSK_NM");
/*      */     }
/*      */ 
/*  552 */     if (StringUtils.isEmpty(strDskNam)) {
/*  553 */       throw new HiAppException(-2, "220015");
/*      */     }
/*  555 */     pubBatchInfo.dsk_nm = strDskNam;
/*      */ 
/*  557 */     String strActDat = (String)ctx.getBaseSource("ACC_DT");
/*      */ 
/*  559 */     String strSQL = "SELECT  Dsk_No, Txn_Sqn, Sts, Chk_Flg FROM pubbatinf WHERE Acc_Dt = '" + strActDat + "' and Br_No='" + brno + "' and Bus_Typ='" + BusTyp + "' and Crp_Cd ='" + CrpCod + "' and Dsk_Nm='" + strDskNam + "' and Cmt_Flg != 'R'";
/*      */ 
/*  571 */     if (log.isInfoEnabled()) {
/*  572 */       log.info(sm.getString("HiBatOnlAtl.CollateRepeatRecord1", strSQL));
/*      */     }
/*      */ 
/*  575 */     List list = ctx.getDataBaseUtil().execQuery(strSQL);
/*  576 */     if ((list == null) || (list.size() == 0)) {
/*  577 */       return 0;
/*      */     }
/*      */ 
/*  580 */     HashMap info = (HashMap)list.get(0);
/*  581 */     String strStatus = (String)info.get("STS");
/*  582 */     if ("N".equalsIgnoreCase(strStatus)) {
/*  583 */       throw new HiAppException(-2, "220016");
/*      */     }
/*  585 */     pubBatchInfo.sts = strStatus;
/*      */ 
/*  587 */     String strTxnSqn = (String)info.get("TXN_SQN");
/*  588 */     int nTxnSqn = Integer.parseInt(strTxnSqn);
/*  589 */     if ((((nTxnSqn >= 500) ? 1 : 0) & ((nTxnSqn <= 999) ? 1 : 0)) != 0) {
/*  590 */       throw new HiAppException(-2, "220017", strTxnSqn);
/*      */     }
/*      */ 
/*  593 */     pubBatchInfo.txn_sqn = strTxnSqn;
/*      */ 
/*  595 */     String strChkFlg = (String)info.get("CHK_FLG");
/*  596 */     if ("1".equalsIgnoreCase(strChkFlg)) {
/*  597 */       throw new HiAppException(-2, "220018");
/*      */     }
/*  599 */     pubBatchInfo.chk_flg = strChkFlg;
/*      */ 
/*  601 */     String strDskNo = (String)info.get("DSK_NO");
/*      */ 
/*  603 */     pubBatchInfo.dsk_no = strDskNo;
/*      */ 
/*  605 */     etf.setChildValue("DSK_NO", strDskNo);
/*  606 */     etf.setChildValue("FRSP_CD", "341510");
/*      */     try {
/*  608 */       AppendDiskNo(args, ctx);
/*      */     }
/*      */     catch (HiException e) {
/*  611 */       etf.setChildValue("NODEL_FLG", "1");
/*  612 */       throw e;
/*      */     }
/*      */     try
/*      */     {
/*  616 */       BatchEtfToPriSpace(args, ctx);
/*      */     } catch (HiException e) {
/*  618 */       throw new HiAppException(-2, "设置批量私有数据错误");
/*      */     }
/*  620 */     HiAtcLib.dbtsqlupdrec(etf, "pubbatinf", "DSK_NO", strDskNo, 0, ctx);
/*      */ 
/*  622 */     return 1;
/*      */   }
/*      */ 
/*      */   public int ConfirmBatch(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  639 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/*      */ 
/*  643 */     String dskNo = etf.getChildValue("DskNo");
/*  644 */     if (dskNo == null)
/*  645 */       throw new HiException("220082", "DskNo");
/*      */     try
/*      */     {
/*  648 */       AppendDiskNo(args, ctx);
/*      */     } catch (HiException e) {
/*  650 */       etf.setChildValue("ProMsg", "本批次正在处理中");
/*  651 */       etf.setChildValue("NoDelFlag", "1");
/*  652 */       DeleteDiskNo(args, ctx);
/*  653 */       throw new HiAppException(-1, "220081");
/*      */     }
/*      */ 
/*  657 */     String tlrId = etf.getChildValue("TlrId");
/*  658 */     if (tlrId == null) {
/*  659 */       throw new HiAppException(-1, "220082", "TlrId");
/*      */     }
/*      */ 
/*  663 */     HiDbtUtils.dbtsqlrdrec("pubbatinf", "DskNo", dskNo, 0, etf, ctx);
/*      */ 
/*  666 */     String ChkFlg = etf.getChildValue("ChkFlg");
/*  667 */     if (ChkFlg == null) {
/*  668 */       throw new HiAppException(-1, "220082", "ChkFlg");
/*      */     }
/*      */ 
/*  671 */     if ("1".equals(ChkFlg)) {
/*  672 */       throw new HiAppException(-1, "220084");
/*      */     }
/*  674 */     if (!("P".equals(ChkFlg))) {
/*  675 */       throw new HiAppException(-1, "220085");
/*      */     }
/*      */ 
/*  678 */     String TxnMod = etf.getChildValue("TxnMod");
/*  679 */     if (TxnMod == null) {
/*  680 */       throw new HiAppException(-1, "220082", "TxnMod");
/*      */     }
/*      */ 
/*  684 */     if ((TxnMod.equals("2")) || (TxnMod.equals("3"))) {
/*  685 */       return 1;
/*      */     }
/*      */ 
/*  688 */     String SndTlr = etf.getChildValue("SndTlr");
/*  689 */     if (SndTlr == null) {
/*  690 */       throw new HiAppException(-1, "220082", "SndTlr");
/*      */     }
/*      */ 
/*  693 */     if (!(SndTlr.equals(tlrId))) {
/*  694 */       throw new HiAppException(-1, "220086");
/*      */     }
/*      */ 
/*  697 */     String strSql = "UPDATE pubbatinf SET ChkFlg = '1',ChkTlr = '" + tlrId + "' WHERE  DskNo  = '" + dskNo + "'";
/*      */     try
/*      */     {
/*  700 */       ctx.getDataBaseUtil().execUpdate(strSql);
/*      */     } catch (HiException e) {
/*  702 */       ctx.getDataBaseUtil().rollback();
/*  703 */       throw e;
/*      */     }
/*  705 */     ctx.getDataBaseUtil().commit();
/*      */ 
/*  707 */     etf.setChildValue("ChkFlg", "1");
/*  708 */     etf.setChildValue("ChkTlr", tlrId);
/*  709 */     return 0;
/*      */   }
/*      */ 
/*      */   public int CopyBatchFile(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  732 */     SetBatchFileName(args, ctx);
/*  733 */     SetBatchCopyName(args, ctx);
/*  734 */     CopyFileFromLocal(args, ctx);
/*  735 */     return 0;
/*      */   }
/*      */ 
/*      */   public int CopyFileFromLocal(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  749 */     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
/*  750 */     if (priBatchInfo == null) {
/*  751 */       throw new HiAppException(-1, "220025", "");
/*      */     }
/*  753 */     HiFile.copy(priBatchInfo.fileCurr, priBatchInfo.filOut);
/*  754 */     return 0;
/*      */   }
/*      */ 
/*      */   public int SetBatchCopyName(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  778 */     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
/*  779 */     if (priBatchInfo == null)
/*  780 */       throw new HiAppException(-1, "220025");
/*  781 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*  782 */     if (log.isDebugEnabled()) {
/*  783 */       log.debug("before:" + priBatchInfo);
/*      */     }
/*      */ 
/*  786 */     if (!(args.contains("PathOut"))) {
/*  787 */       throw new HiAppException(-1, "");
/*      */     }
/*  789 */     String pathOut = args.get("PathOut");
/*  790 */     if (!(args.contains("FileNameOut"))) {
/*  791 */       throw new HiAppException(-1, "");
/*      */     }
/*  793 */     String fileNameOut = args.get("FileNameOut");
/*  794 */     priBatchInfo.pubBatchInfo.fil_nm = fileNameOut;
/*  795 */     ctx.getCurrentMsg().getETFBody().setChildValue("FIL_NM", fileNameOut);
/*      */ 
/*  797 */     if (!(pathOut.endsWith(File.separator))) {
/*  798 */       pathOut = File.separator + pathOut;
/*      */     }
/*  800 */     String strPath = HiICSProperty.getWorkDir() + pathOut + File.separator + fileNameOut;
/*      */ 
/*  803 */     priBatchInfo.filOut = strPath;
/*  804 */     if (log.isDebugEnabled()) {
/*  805 */       log.debug("after:" + priBatchInfo);
/*      */     }
/*  807 */     return 0;
/*      */   }
/*      */ 
/*      */   public int CreateBatchFile(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  833 */     SetBatchFormatName(args, ctx);
/*  834 */     SetBatchFileName(args, ctx);
/*  835 */     CreateFile(args, ctx);
/*  836 */     return 0;
/*      */   }
/*      */ 
/*      */   private int CreateFile(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  861 */     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
/*  862 */     if (priBatchInfo == null) {
/*  863 */       throw new HiAppException(-1, "220025");
/*      */     }
/*  865 */     HiBatchProcess formatNode = findFormatNode(priBatchInfo.formatName, ctx);
/*  866 */     HiPackInfo packInfo = new HiPackInfo();
/*  867 */     packInfo.formatName = args.get("FormatName");
/*      */ 
/*  869 */     packInfo.fileName = priBatchInfo.fileCurr;
/*  870 */     if (!(packInfo.fileName.startsWith(File.separator))) {
/*  871 */       packInfo.fileName = HiICSProperty.getWorkDir() + File.separator + packInfo.fileName;
/*      */     }
/*      */ 
/*  874 */     packInfo.applyLogNoFlag = args.get("ApplyLogNoFlag");
/*      */ 
/*  876 */     String sqlName = args.get("SqlName");
/*  877 */     if (StringUtils.isEmpty(sqlName)) {
/*  878 */       throw new HiException("220026", "SqlName");
/*      */     }
/*  880 */     packInfo.sqlCond = HiDbtSqlHelper.getDynSentence(ctx, sqlName);
/*  881 */     if (StringUtils.isEmpty(packInfo.sqlCond)) {
/*  882 */       throw new HiException("220080");
/*      */     }
/*  884 */     packInfo.tableName = args.get("TableName");
/*  885 */     formatNode.exportFromDB(ctx, packInfo);
/*  886 */     return 0;
/*      */   }
/*      */ 
/*      */   public int DeleteDiskNo(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  903 */     String strDskNo = null;
/*  904 */     if (args.contains("RegItm")) {
/*  905 */       strDskNo = args.get("RegItm");
/*      */     } else {
/*  907 */       strDskNo = ctx.getCurrentMsg().getETFBody().getChildValue("DSK_NO");
/*  908 */       if (strDskNo == null)
/*  909 */         return 0;
/*      */     }
/*  911 */     String strNoDelFlag = ctx.getCurrentMsg().getETFBody().getChildValue("NODEL_FLG");
/*      */ 
/*  913 */     if (strNoDelFlag != null) {
/*  914 */       return 0;
/*      */     }
/*  916 */     if (gDskNoPool.size() == 0) {
/*  917 */       return 0;
/*      */     }
/*  919 */     if (containsPool(strDskNo)) {
/*  920 */       removePool(strDskNo);
/*      */     }
/*      */ 
/*  923 */     return 0;
/*      */   }
/*      */ 
/*      */   public int DeleteDiskNoAndRollback(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  940 */     HiDBUtils.RollbackWork(args, ctx);
/*  941 */     return DeleteDiskNo(args, ctx);
/*      */   }
/*      */ 
/*      */   public int ExportFromDB(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  964 */     HiPackInfo packInfo = new HiPackInfo();
/*  965 */     packInfo.formatName = args.get("FormatName");
/*  966 */     packInfo.applyLogNoFlag = args.get("ApplyLogNoFlag");
/*  967 */     packInfo.fileName = args.get("FileName");
/*  968 */     if (StringUtils.isEmpty(packInfo.fileName)) {
/*  969 */       throw new HiException("220026", "FileName");
/*      */     }
/*      */ 
/*  972 */     if (!(packInfo.fileName.startsWith(File.separator))) {
/*  973 */       packInfo.fileName = HiICSProperty.getWorkDir() + File.separator + packInfo.fileName;
/*      */     }
/*      */ 
/*  977 */     String sqlName = args.get("SqlName");
/*  978 */     if (StringUtils.isEmpty(sqlName)) {
/*  979 */       throw new HiException("220026", "SqlName");
/*      */     }
/*  981 */     packInfo.sqlCond = HiDbtSqlHelper.getDynSentence(ctx, sqlName);
/*  982 */     if (StringUtils.isEmpty(packInfo.sqlCond)) {
/*  983 */       throw new HiException("220080");
/*      */     }
/*  985 */     packInfo.tableName = args.get("TableName");
/*  986 */     HiBatchProcess formatNode = findFormatNode(packInfo.formatName, ctx);
/*  987 */     return formatNode.exportFromDB(ctx, packInfo);
/*      */   }
/*      */ 
/*      */   private HiBatchProcess findFormatNode(String formatName, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1000 */     if (StringUtils.isEmpty(formatName)) {
/* 1001 */       ctx.getCurrentMsg().getETFBody().setChildValue("FRSP_CD", "341796");
/* 1002 */       throw new HiAppException(-1, "220033", "");
/*      */     }
/*      */ 
/* 1006 */     HiDelegate rootObj = (HiDelegate)ctx.getProperty("CONFIGDECLARE", "BatFormat");
/*      */ 
/* 1009 */     if (rootObj == null) {
/* 1010 */       throw new HiException("220204", formatName);
/*      */     }
/*      */ 
/* 1013 */     HiBatchProcess pro = (HiBatchProcess)rootObj.getChildsMap().get(formatName);
/*      */ 
/* 1015 */     if (pro == null) {
/* 1016 */       throw new HiException("220204", formatName);
/*      */     }
/*      */ 
/* 1019 */     return pro;
/*      */   }
/*      */ 
/*      */   public String getBatchName(HiATLParam args, String name, HiMessageContext ctx, String busTyp, String crpCod)
/*      */     throws HiException
/*      */   {
/* 1025 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 1026 */     if (log.isInfoEnabled()) {
/* 1027 */       log.info("getBatchName:name[" + name + "]");
/*      */     }
/* 1029 */     String value = null;
/* 1030 */     String name1 = args.get(name);
/* 1031 */     if (name1.startsWith("#")) {
/* 1032 */       if (StringUtils.isEmpty(busTyp)) {
/* 1033 */         throw new HiException("220013");
/*      */       }
/*      */ 
/* 1036 */       if (StringUtils.isEmpty(crpCod)) {
/* 1037 */         throw new HiException("220013");
/*      */       }
/*      */ 
/* 1040 */       Element rootNode = (Element)ctx.getProperty("CONFIGDECLARE", "BatConfig");
/*      */ 
/* 1043 */       Element busNode = HiXmlHelper.getNodeByAttr(rootNode, "BUS_TYP", "code", busTyp);
/*      */ 
/* 1045 */       Element crpNode = HiXmlHelper.getNodeByAttr(busNode, "CRP_CD", "code", crpCod);
/*      */ 
/* 1048 */       String name2 = name1.substring(1);
/*      */ 
/* 1050 */       Iterator list = crpNode.elementIterator();
/* 1051 */       while (list.hasNext()) {
/* 1052 */         Element item = (Element)list.next();
/* 1053 */         String name3 = item.attributeValue("name");
/* 1054 */         if (name2.equals(name3)) {
/* 1055 */           value = item.attributeValue("value");
/* 1056 */           break;
/*      */         }
/*      */       }
/*      */     } else {
/* 1060 */       value = name1;
/*      */     }
/*      */ 
/* 1065 */     return value;
/*      */   }
/*      */ 
/*      */   public int GetDiskNo(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1087 */     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
/* 1088 */     if (priBatchInfo == null) {
/* 1089 */       throw new HiAppException(-1, "220025", "");
/*      */     }
/* 1091 */     priBatchInfo.pubBatchInfo.dsk_no = HiAtcLib.bpbgetbatno(ctx);
/* 1092 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 1093 */     etf.setChildValue("DSK_NO", priBatchInfo.pubBatchInfo.dsk_no);
/*      */ 
/* 1095 */     return 0;
/*      */   }
/*      */ 
/*      */   public int ImportToDB(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1127 */     HiPackInfo packInfo = new HiPackInfo();
/* 1128 */     packInfo.formatName = args.get("FormatName");
/* 1129 */     packInfo.applyLogNoFlag = args.get("ApplyLogNoFlag");
/* 1130 */     packInfo.fileName = args.get("FileName");
/* 1131 */     if (StringUtils.isEmpty(packInfo.fileName)) {
/* 1132 */       throw new HiException("220026", "FileName");
/*      */     }
/*      */ 
/* 1135 */     if (!(packInfo.fileName.startsWith(File.separator))) {
/* 1136 */       packInfo.fileName = HiICSProperty.getWorkDir() + File.separator + packInfo.fileName;
/*      */     }
/*      */ 
/* 1140 */     packInfo.tableName = args.get("TableName");
/* 1141 */     if (StringUtils.equalsIgnoreCase(args.get("IsUpdate"), "1"))
/*      */     {
/* 1143 */       packInfo.isUpdate = true;
/* 1144 */       packInfo.conSts = args.get("CndSts");
/*      */     }
/*      */ 
/* 1147 */     HiBatchProcess batchProcess = findFormatNode(packInfo.formatName, ctx);
/* 1148 */     return batchProcess.importToDB(ctx, packInfo);
/*      */   }
/*      */ 
/*      */   public int IsRepeatBatch(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1170 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 1171 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 1172 */     String LogNo = etf.getChildValue("LOG_NO");
/* 1173 */     HiPubBatchInfo pubBatchInfo = new HiPubBatchInfo();
/* 1174 */     if (LogNo.length() == 12) {
/* 1175 */       pubBatchInfo.dsk_no = LogNo.trim();
/* 1176 */       if (log.isInfoEnabled()) {
/* 1177 */         log.info(HiStringManager.getManager().getString("HiBatOnlAtl.IsRepeatBatch", "IsRepeatBatch", LogNo));
/*      */       }
/*      */ 
/* 1180 */       etf.setChildValue("DSK_NO", pubBatchInfo.dsk_no);
/*      */       try
/*      */       {
/* 1183 */         AppendDiskNo(args, ctx);
/*      */       } catch (HiException e) {
/* 1185 */         etf.setChildValue("NODEL_FLG", "1");
/* 1186 */         throw new HiAppException(-2, "220092", "IsRepeatBatch", e);
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/* 1191 */         HiDbtUtils.dbtsqlrdrec("pubbatinf", "DSK_NO", pubBatchInfo.dsk_no, 0, etf, ctx);
/*      */       }
/*      */       catch (HiException e) {
/* 1194 */         throw new HiAppException(-3, "220093", "IsRepeatBatch", e);
/*      */       }
/*      */ 
/* 1197 */       String Status = etf.getChildValue("STS");
/* 1198 */       if (Status == null) {
/* 1199 */         throw new HiAppException(-3, "220082", "Sts");
/*      */       }
/*      */ 
/* 1202 */       if (Status.equals("N")) {
/* 1203 */         throw new HiAppException(-2, "220094", "IsRepeatBatch");
/*      */       }
/*      */       try
/*      */       {
/* 1207 */         BatchEtfToPriSpace(args, ctx);
/*      */       } catch (HiException e) {
/* 1209 */         throw new HiAppException(-3, "220091", "IsRepeatBatch", e);
/*      */       }
/*      */ 
/* 1212 */       String ChkFlg = etf.getChildValue("CHK_FLG");
/* 1213 */       if (ChkFlg == null) {
/* 1214 */         throw new HiAppException(-3, "220082", "CHK_FLG");
/*      */       }
/*      */ 
/* 1218 */       if (ChkFlg.equals("1")) {
/* 1219 */         throw new HiAppException(-1, "220095");
/*      */       }
/* 1221 */       return 1;
/*      */     }
/*      */     try {
/* 1224 */       BatchEtfToPriSpace(args, ctx);
/*      */     } catch (HiException Status) {
/* 1226 */       throw new HiAppException(-3, "220091", "IsRepeatBatch");
/*      */     }
/*      */ 
/* 1230 */     return 0;
/*      */   }
/*      */ 
/*      */   public int ParseBatchFile(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1268 */     SetBatchTableName(args, ctx);
/* 1269 */     SetBatchFormatName(args, ctx);
/* 1270 */     SetBatchFileName(args, ctx);
/* 1271 */     SetBatchApplyFlag(args, ctx);
/*      */ 
/* 1273 */     ParseFile(args, ctx);
/* 1274 */     return 0;
/*      */   }
/*      */ 
/*      */   private int ParseFile(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1293 */     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
/* 1294 */     if (priBatchInfo == null) {
/* 1295 */       throw new HiAppException(-1, "220025");
/*      */     }
/*      */ 
/* 1298 */     HiBatchProcess formatNode = findFormatNode(priBatchInfo.formatName, ctx);
/*      */ 
/* 1300 */     ctx.getDataBaseUtil().execUpdate("DELETE FROM %s WHERE Dsk_No = '%s'", priBatchInfo.tableName, priBatchInfo.pubBatchInfo.dsk_no);
/*      */ 
/* 1302 */     ctx.getDataBaseUtil().commit();
/* 1303 */     HiPackInfo packInfo = new HiPackInfo();
/* 1304 */     packInfo.fileName = priBatchInfo.fileCurr;
/* 1305 */     packInfo.ornAmt = NumberUtils.toInt(priBatchInfo.pubBatchInfo.orn_amt);
/* 1306 */     packInfo.ornCnt = NumberUtils.toInt(priBatchInfo.pubBatchInfo.orn_cnt);
/* 1307 */     packInfo.tableName = priBatchInfo.tableName;
/* 1308 */     packInfo.applyLogNoFlag = priBatchInfo.applyLogNoFlag;
/* 1309 */     formatNode.importToDB(ctx, packInfo);
/*      */ 
/* 1312 */     if ((packInfo.ornAmt == 0L) && (packInfo.ornCnt == 0) && (packInfo.dTotalCnt != 0) && (packInfo.dTotalAmt != 0L))
/*      */     {
/* 1314 */       String sqlCmd = "UPDATE pubbatinf SET Orn_Cnt = '%s', Orn_Amt = '%s' WHERE Dsk_No = '%s'";
/*      */ 
/* 1316 */       sqlCmd = HiStringUtils.format(sqlCmd, HiStringUtils.leftPad(packInfo.dTotalCnt, 8), HiStringUtils.leftPad(packInfo.dTotalAmt, 15), priBatchInfo.pubBatchInfo.dsk_no);
/* 1317 */       ctx.getDataBaseUtil().execUpdate(sqlCmd);
/*      */     }
/* 1319 */     return 0;
/*      */   }
/*      */ 
/*      */   public int PasteBatchFile(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1343 */     SetBatchPasteName(args, ctx);
/* 1344 */     SetBatchFileName(args, ctx);
/* 1345 */     PasteFileInLocal(args, ctx);
/* 1346 */     return 0;
/*      */   }
/*      */ 
/*      */   private int PasteFileInLocal(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1366 */     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
/* 1367 */     if (priBatchInfo == null) {
/* 1368 */       throw new HiAppException(-1, "220025");
/*      */     }
/* 1370 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 1371 */     if (log.isInfoEnabled()) {
/* 1372 */       log.info("copy file from [" + priBatchInfo.filIn + "] to [" + priBatchInfo.fileCurr + "]");
/*      */     }
/*      */ 
/* 1376 */     HiFile.copy(priBatchInfo.filIn, priBatchInfo.fileCurr);
/* 1377 */     return 0;
/*      */   }
/*      */ 
/*      */   public int QueryBatchStatus(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1394 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/*      */ 
/* 1399 */     String strDskNo = etf.getChildValue("DskNo");
/* 1400 */     if (strDskNo == null) {
/* 1401 */       throw new HiAppException(-1, "220082", "DskNo");
/*      */     }
/*      */ 
/* 1405 */     HiDbtUtils.dbtsqlrdrec("pubbatinf", "DskNo", strDskNo, 0, etf, ctx);
/*      */ 
/* 1407 */     int iFlag = 0;
/*      */     try {
/* 1409 */       AppendDiskNo(args, ctx);
/*      */     } catch (HiException e) {
/* 1411 */       iFlag = 1;
/* 1412 */       etf.setChildValue("ProMsg", "本批次正在处理中");
/* 1413 */       etf.setChildValue("NoDelFlag", "1");
/*      */     }
/* 1415 */     DeleteDiskNo(args, ctx);
/*      */ 
/* 1417 */     HiPubBatchInfo pubBatchInfo = new HiPubBatchInfo();
/*      */ 
/* 1419 */     String ChkFlg = etf.getChildValue("ChkFlg");
/* 1420 */     if (ChkFlg == null) {
/* 1421 */       throw new HiAppException(-1, "220082", "ChkFlg");
/*      */     }
/* 1423 */     pubBatchInfo.chk_flg = ChkFlg;
/*      */ 
/* 1425 */     String Status = etf.getChildValue("Status");
/* 1426 */     if (Status == null) {
/* 1427 */       throw new HiAppException(-1, "220082", "Status");
/*      */     }
/* 1429 */     pubBatchInfo.sts = Status;
/*      */ 
/* 1431 */     HiPubBatchInfo sBatchInfo = pubBatchInfo;
/*      */ 
/* 1433 */     if (iFlag == 0) {
/* 1434 */       if (StringUtils.equals(sBatchInfo.chk_flg, "0")) {
/* 1435 */         etf.setChildValue("ProMsg", "本批次因出错停止处理");
/* 1436 */       } else if (StringUtils.equals(sBatchInfo.chk_flg, "P")) {
/* 1437 */         etf.setChildValue("ProMsg", "本批次需要被确认后才能继续处理");
/* 1438 */       } else if ((StringUtils.equals(sBatchInfo.chk_flg, "1")) && (StringUtils.equals(sBatchInfo.sts, "N")))
/*      */       {
/* 1440 */         etf.setChildValue("ProMsg", "本批次已被确认并已完成");
/*      */       }
/* 1442 */       else if (StringUtils.equals(sBatchInfo.chk_flg, "1")) {
/* 1443 */         etf.setChildValue("ProMsg", "本批次已被确认等待完成");
/*      */       } else {
/* 1445 */         etf.setChildValue("ProMsg", "本批次确认状态不在规定范围内");
/*      */ 
/* 1447 */         Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 1448 */         log.error(HiStringManager.getManager().getString("220087", "QueryBatchStatus", sBatchInfo.chk_flg));
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1454 */     return 0;
/*      */   }
/*      */ 
/*      */   public int RegisterBatch(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1471 */     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
/* 1472 */     if (priBatchInfo == null)
/* 1473 */       throw new HiAppException(-1, "220025", "");
/* 1474 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 1475 */     if (log.isDebugEnabled()) {
/* 1476 */       log.debug("before:" + priBatchInfo);
/*      */     }
/* 1478 */     HiPubBatchInfo sBatchInfo = priBatchInfo.pubBatchInfo;
/*      */ 
/* 1480 */     if ("1".equals(sBatchInfo.txn_mod))
/* 1481 */       sBatchInfo.cmt_flg = "0";
/*      */     else {
/* 1483 */       sBatchInfo.cmt_flg = "1";
/*      */     }
/*      */ 
/* 1486 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/*      */ 
/* 1488 */     String chkFlg = etf.getChildValue("CHK_FLG");
/* 1489 */     if (chkFlg != null)
/* 1490 */       sBatchInfo.chk_flg = chkFlg;
/*      */     else {
/* 1492 */       sBatchInfo.chk_flg = "0";
/*      */     }
/* 1494 */     String SumFlg = etf.getChildValue("SUM_FLG");
/* 1495 */     if (SumFlg != null)
/* 1496 */       sBatchInfo.sum_flg = SumFlg;
/*      */     else {
/* 1498 */       sBatchInfo.sum_flg = "N";
/*      */     }
/* 1500 */     String UpdFlg = etf.getChildValue("UPD_FLG");
/* 1501 */     if (UpdFlg != null)
/* 1502 */       sBatchInfo.upd_flg = UpdFlg;
/*      */     else {
/* 1504 */       sBatchInfo.upd_flg = "N";
/*      */     }
/* 1506 */     sBatchInfo.sts = "E";
/* 1507 */     sBatchInfo.txn_sqn = "000";
/*      */ 
/* 1509 */     sBatchInfo.snd_cnt = "000";
/* 1510 */     sBatchInfo.lst_tm = "0000000000";
/* 1511 */     sBatchInfo.frsp_cd = "120001";
/*      */ 
/* 1513 */     if (log.isDebugEnabled()) {
/* 1514 */       log.debug("after:" + sBatchInfo);
/*      */     }
/*      */     try
/*      */     {
/* 1518 */       HiAtcLib.insertRecord("pubbatinf", sBatchInfo, ctx);
/*      */     } catch (HiException e) {
/* 1520 */       ctx.getDataBaseUtil().rollback();
/* 1521 */       throw e;
/*      */     }
/*      */ 
/* 1524 */     ctx.getDataBaseUtil().commit();
/* 1525 */     return 0;
/*      */   }
/*      */ 
/*      */   public int RegisterBatchDiskNo(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1542 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 1543 */     String strDskNo = etf.getChildValue("DSK_NO");
/*      */ 
/* 1546 */     if (StringUtils.isNotEmpty(strDskNo)) {
/* 1547 */       return 0;
/*      */     }
/* 1549 */     BatchEtfToPriSpace(args, ctx);
/*      */ 
/* 1551 */     GetDiskNo(args, ctx);
/*      */ 
/* 1553 */     RegisterBatch(args, ctx);
/*      */ 
/* 1555 */     AppendDiskNo(args, ctx);
/*      */ 
/* 1557 */     return 0;
/*      */   }
/*      */ 
/*      */   public int PreRegisterBatchRedo(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1571 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/*      */ 
/* 1573 */     String strDskNo = etf.getChildValue("DskNo");
/* 1574 */     if (strDskNo == null) {
/* 1575 */       throw new HiAppException(-1, "220082", "DskNo");
/*      */     }
/*      */ 
/* 1579 */     HiRdoJnl sRdr = new HiRdoJnl();
/* 1580 */     sRdr.LogNo = strDskNo;
/* 1581 */     HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
/* 1582 */     sRdr.TxnCod = attr.code();
/* 1583 */     sRdr.Itv = attr.interval();
/* 1584 */     sRdr.TmOut = attr.timeout();
/* 1585 */     sRdr.MaxTms = attr.maxtimes();
/* 1586 */     sRdr.RvsSts = "0";
/* 1587 */     sRdr.ObjSvr = attr.objsvr();
/* 1588 */     sRdr.ActDat = ((String)ctx.getBaseSource("ActDat"));
/*      */ 
/* 1590 */     int iRet = HiAtcLib.rdoTranRegister(sRdr);
/* 1591 */     if (iRet != 0) {
/* 1592 */       throw new HiAppException(-1, "220083");
/*      */     }
/* 1594 */     return 0;
/*      */   }
/*      */ 
/*      */   public int RegisterRedoDiskNo(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1612 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 1613 */     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
/* 1614 */     if (priBatchInfo == null) {
/* 1615 */       throw new HiAppException(-1, "220025", "");
/*      */     }
/* 1617 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 1618 */     if (log.isDebugEnabled()) {
/* 1619 */       log.debug("before:" + priBatchInfo);
/*      */     }
/*      */ 
/* 1622 */     String strDskNo = etf.getChildValue("DskNo");
/* 1623 */     if (strDskNo != null) {
/* 1624 */       priBatchInfo.pubBatchInfo.dsk_no = strDskNo;
/* 1625 */       return 0;
/*      */     }
/* 1627 */     BatchEtfToPriSpace(args, ctx);
/*      */ 
/* 1630 */     GetDiskNo(args, ctx);
/*      */ 
/* 1633 */     RegisterBatch(args, ctx);
/*      */ 
/* 1636 */     PreRegisterBatchRedo(args, ctx);
/*      */     try
/*      */     {
/* 1640 */       AppendDiskNo(args, ctx);
/*      */     } catch (HiException e) {
/* 1642 */       etf.setChildValue("NoDelFlag", "1");
/* 1643 */       throw e;
/*      */     }
/* 1645 */     if (log.isDebugEnabled()) {
/* 1646 */       log.debug("after:" + priBatchInfo);
/*      */     }
/* 1648 */     return 0;
/*      */   }
/*      */ 
/*      */   public int SetBatchApplyFlag(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1671 */     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
/* 1672 */     if (priBatchInfo == null) {
/* 1673 */       throw new HiAppException(-1, "220025");
/*      */     }
/* 1675 */     String strApplyFlag = "0";
/* 1676 */     if (args.contains("ApplyLogNoFlag")) {
/* 1677 */       strApplyFlag = getBatchName(args, "ApplyLogNoFlag", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
/*      */     }
/*      */ 
/* 1682 */     if ((!(strApplyFlag.equals("0"))) && (!(strApplyFlag.equals("1")))) {
/* 1683 */       throw new HiAppException(-1, "220032", strApplyFlag);
/*      */     }
/*      */ 
/* 1687 */     priBatchInfo.applyLogNoFlag = strApplyFlag;
/* 1688 */     return 0;
/*      */   }
/*      */ 
/*      */   public int SetBatchFileName(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1713 */     String strPathCurr = "dat/local";
/* 1714 */     String strFileName = "01";
/*      */ 
/* 1718 */     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
/* 1719 */     if (priBatchInfo == null)
/* 1720 */       throw new HiAppException(-1, "220025", "");
/* 1721 */     if (args.contains("PathCurr")) {
/* 1722 */       strPathCurr = getBatchName(args, "PathCurr", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
/*      */     }
/*      */ 
/* 1726 */     if (args.contains("FileNameCurr")) {
/* 1727 */       strFileName = getBatchName(args, "FileNameCurr", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
/*      */     }
/*      */ 
/* 1732 */     String strDskNo = priBatchInfo.pubBatchInfo.dsk_no;
/* 1733 */     String strPath = HiICSProperty.getWorkDir() + File.separator + strPathCurr + File.separator + strDskNo + strFileName.trim();
/*      */ 
/* 1735 */     HiMessage mess = ctx.getCurrentMsg();
/* 1736 */     Logger log = HiLog.getLogger(mess);
/* 1737 */     if (log.isInfoEnabled()) {
/* 1738 */       log.info(priBatchInfo.pubBatchInfo);
/* 1739 */       log.info(sm.getString("HiBatOnlAtl.SetBatchPasteName", strPath));
/*      */     }
/*      */ 
/* 1742 */     priBatchInfo.fileCurr = strPath;
/*      */ 
/* 1744 */     return 0;
/*      */   }
/*      */ 
/*      */   public int SetBatchFormatName(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1766 */     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
/* 1767 */     if (priBatchInfo == null) {
/* 1768 */       throw new HiAppException(-1, "220025");
/*      */     }
/* 1770 */     if (!(args.contains("FormatName"))) {
/* 1771 */       throw new HiAppException(-1, "220031", "");
/*      */     }
/*      */ 
/* 1774 */     String strFormatName = args.get("FormatName");
/* 1775 */     if (strFormatName.startsWith("#")) {
/* 1776 */       strFormatName = getBatchName(args, "FormatName", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
/*      */     }
/*      */ 
/* 1782 */     priBatchInfo.formatName = strFormatName;
/* 1783 */     return 0;
/*      */   }
/*      */ 
/*      */   public int SetBatchPasteName(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1806 */     if (!(args.contains("PathIn"))) {
/* 1807 */       throw new HiAppException(-1, "220028", "");
/*      */     }
/* 1809 */     if (!(args.contains("FileNameIn"))) {
/* 1810 */       throw new HiAppException(-1, "220029", "");
/*      */     }
/*      */ 
/* 1813 */     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
/* 1814 */     if (priBatchInfo == null) {
/* 1815 */       throw new HiAppException(-1, "220025");
/*      */     }
/*      */ 
/* 1821 */     String strFileNameIn = getBatchName(args, "FileNameIn", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
/*      */ 
/* 1825 */     priBatchInfo.pubBatchInfo.dsk_nm = strFileNameIn;
/*      */ 
/* 1827 */     String strPathIn = getBatchName(args, "PathIn", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
/*      */ 
/* 1831 */     if (!(strPathIn.endsWith(File.separator))) {
/* 1832 */       strPathIn = File.separator + strPathIn;
/*      */     }
/*      */ 
/* 1835 */     String strPath = HiICSProperty.getWorkDir() + strPathIn + File.separator + strFileNameIn.trim();
/*      */ 
/* 1837 */     HiMessage mess = ctx.getCurrentMsg();
/* 1838 */     Logger log = HiLog.getLogger(mess);
/* 1839 */     if (log.isInfoEnabled()) {
/* 1840 */       log.info(sm.getString("HiBatOnlAtl.SetBatchPasteName", strPath));
/*      */     }
/*      */ 
/* 1843 */     priBatchInfo.filIn = strPath;
/* 1844 */     return 0;
/*      */   }
/*      */ 
/*      */   public int SetBatchTableName(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1867 */     if (!(args.contains("TableName"))) {
/* 1868 */       throw new HiAppException(-1, "220027", "");
/*      */     }
/* 1870 */     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
/* 1871 */     if (priBatchInfo == null)
/* 1872 */       throw new HiAppException(-1, "220025", "");
/* 1873 */     String strTableName = getBatchName(args, "TableName", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
/*      */ 
/* 1877 */     priBatchInfo.tableName = strTableName;
/*      */ 
/* 1879 */     return 0;
/*      */   }
/*      */ 
/*      */   public int UpdateBatchStatus(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*      */     String strSql;
/*      */     String strSql;
/* 1910 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 1911 */     HiPriBatchInfo priBatchInfo = HiPriBatchInfo.getPriBatchInfo(ctx);
/* 1912 */     if (priBatchInfo == null) {
/* 1913 */       throw new HiAppException(-1, "220025", "");
/*      */     }
/* 1915 */     if (args.contains("CmtFlg")) {
/* 1916 */       String CmtFlg = args.get("CmtFlg");
/* 1917 */       if ((!(CmtFlg.equals("1"))) && (!(CmtFlg.equals("0"))) && (!(CmtFlg.equals("R"))))
/*      */       {
/* 1919 */         throw new HiAppException(-1, "220088", CmtFlg);
/*      */       }
/*      */ 
/* 1923 */       priBatchInfo.pubBatchInfo.cmt_flg = CmtFlg;
/* 1924 */       strSql = " UPDATE pubbatinf SET    Cmt_Flg = '" + CmtFlg + "' WHERE  Dsk_No  = '" + priBatchInfo.pubBatchInfo.dsk_no + "'";
/*      */       try
/*      */       {
/* 1929 */         ctx.getDataBaseUtil().execUpdate(strSql);
/*      */       } catch (HiException e) {
/* 1931 */         ctx.getDataBaseUtil().rollback();
/* 1932 */         throw e;
/*      */       }
/*      */     }
/*      */ 
/* 1936 */     String RspCod = null;
/* 1937 */     if (args.contains("RspCod"))
/* 1938 */       RspCod = args.get("RspCod");
/*      */     else {
/* 1940 */       RspCod = etf.getChildValue("RSP_CD");
/*      */     }
/* 1942 */     if (RspCod != null) {
/* 1943 */       if (RspCod.length() != 6) {
/* 1944 */         throw new HiAppException(-1, "220089", "UpdateBatchStatus", RspCod);
/*      */       }
/*      */ 
/* 1947 */       strSql = " UPDATE pubbatinf SET FRsp_Cd = '" + RspCod + "' WHERE  Dsk_No  = '" + priBatchInfo.pubBatchInfo.dsk_no + "'";
/*      */       try
/*      */       {
/* 1952 */         ctx.getDataBaseUtil().execUpdate(strSql);
/*      */       } catch (HiException e) {
/* 1954 */         ctx.getDataBaseUtil().rollback();
/* 1955 */         throw e;
/*      */       }
/*      */     }
/*      */ 
/* 1959 */     if (args.contains("TxnSqn")) {
/* 1960 */       String TxnSqn = args.get("TxnSqn");
/*      */ 
/* 1962 */       strSql = " UPDATE pubbatinf SET Txn_Sqn = '" + TxnSqn + "' WHERE  Dsk_No  = '" + priBatchInfo.pubBatchInfo.dsk_no + "'";
/*      */       try
/*      */       {
/* 1967 */         ctx.getDataBaseUtil().execUpdate(strSql);
/*      */       } catch (HiException e) {
/* 1969 */         ctx.getDataBaseUtil().rollback();
/* 1970 */         throw e;
/*      */       }
/*      */     }
/* 1973 */     if (args.contains("Status")) {
/* 1974 */       String Status = args.get("Status");
/* 1975 */       if (!(Status.equals("N"))) {
/* 1976 */         throw new HiAppException(-1, "220090", "UpdateBatchStatus", "Status");
/*      */       }
/*      */ 
/* 1979 */       strSql = " UPDATE pubbatinf SET    Sts = '" + Status + "' WHERE  Dsk_No  = '" + priBatchInfo.pubBatchInfo.dsk_no + "'";
/*      */       try
/*      */       {
/* 1984 */         ctx.getDataBaseUtil().execUpdate(strSql);
/*      */       } catch (HiException e) {
/* 1986 */         ctx.getDataBaseUtil().rollback();
/* 1987 */         throw e;
/*      */       }
/*      */     }
/* 1990 */     if (args.contains("ChkFlg")) {
/* 1991 */       String ChkFlg = args.get("ChkFlg");
/* 1992 */       if ((!(ChkFlg.equals("1"))) && (!(ChkFlg.equals("P")))) {
/* 1993 */         throw new HiAppException(-1, "220090", "UpdateBatchStatus", "ChkFlg");
/*      */       }
/*      */ 
/* 1996 */       strSql = " UPDATE pubbatinf SET Chk_Flg = '" + ChkFlg + "' WHERE  Dsk_No  = '" + priBatchInfo.pubBatchInfo.dsk_no + "'";
/*      */       try
/*      */       {
/* 2001 */         ctx.getDataBaseUtil().execUpdate(strSql);
/*      */       } catch (HiException e) {
/* 2003 */         ctx.getDataBaseUtil().rollback();
/* 2004 */         throw e;
/*      */       }
/*      */     }
/* 2007 */     if (args.contains("FilNam")) {
/* 2008 */       String FilNam = getBatchName(args, "FilNam", ctx, priBatchInfo.pubBatchInfo.bus_typ, priBatchInfo.pubBatchInfo.crp_cd);
/*      */ 
/* 2012 */       strSql = " UPDATE pubbatinf SET Fil_Nm = '" + FilNam + "' WHERE  Dsk_No  = '" + priBatchInfo.pubBatchInfo.dsk_no + "'";
/*      */       try
/*      */       {
/* 2017 */         ctx.getDataBaseUtil().execUpdate(strSql);
/*      */       } catch (HiException e) {
/* 2019 */         ctx.getDataBaseUtil().rollback();
/* 2020 */         throw e;
/*      */       }
/*      */     }
/* 2023 */     ctx.getDataBaseUtil().commit();
/* 2024 */     return 0;
/*      */   }
/*      */ }