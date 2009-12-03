/*      */ package com.hisun.atc;
/*      */ 
/*      */ import com.hisun.atc.common.HiDBCursor;
/*      */ import com.hisun.atc.common.HiDbtSqlHelper;
/*      */ import com.hisun.atc.common.HiDbtUtils;
/*      */ import com.hisun.database.HiDataBaseUtil;
/*      */ import com.hisun.dispatcher.HiRouterOut;
/*      */ import com.hisun.exception.HiAppException;
/*      */ import com.hisun.exception.HiException;
/*      */ import com.hisun.hilib.HiATLParam;
/*      */ import com.hisun.hilog4j.HiLog;
/*      */ import com.hisun.hilog4j.Logger;
/*      */ import com.hisun.message.HiETF;
/*      */ import com.hisun.message.HiMessage;
/*      */ import com.hisun.message.HiMessageContext;
/*      */ import com.hisun.util.HiStringManager;
/*      */ import java.util.HashMap;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ 
/*      */ public class HiChkOnlAtl
/*      */ {
/*      */   public int CheckDayEnd(HiATLParam args, HiMessageContext ctx)
/*      */   {
/*   27 */     return 0;
/*      */   }
/*      */ 
/*      */   public int checkManyOnline(HiATLParam args, HiMessageContext ctx, int flag)
/*      */     throws HiException
/*      */   {
/*   49 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/*   50 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*   51 */     String aBrNo = etf.getChildValue("BrNo");
/*   52 */     if (aBrNo == null)
/*      */     {
/*   54 */       throw new HiAppException(-7, "220082", "BrNo");
/*      */     }
/*   56 */     String BusTyp = etf.getChildValue("BusTyp");
/*   57 */     if (BusTyp == null)
/*      */     {
/*   59 */       throw new HiAppException(-7, "220082", "BusTyp");
/*      */     }
/*   61 */     String CrpCod = etf.getChildValue("CrpCod");
/*   62 */     if (CrpCod == null)
/*      */     {
/*   64 */       throw new HiAppException(-7, "220082", "CrpCod");
/*      */     }
/*   66 */     String strSql = "SELECT OnlTbl FROM pubjnldef WHERE BRNO='" + aBrNo + "' ADN BUSTYP='" + BusTyp + "' AND CRPCOD='" + CrpCod + "'";
/*      */ 
/*   68 */     HashMap value = null;
/*      */     try
/*      */     {
/*   71 */       value = ctx.getDataBaseUtil().readRecord(strSql);
/*      */     }
/*      */     catch (HiException e)
/*      */     {
/*   75 */       throw new HiAppException(-7, "215019");
/*      */     }
/*   77 */     String aThdTbl = null;
/*      */ 
/*   79 */     if ((value != null) && (!(value.isEmpty())))
/*      */     {
/*   81 */       aThdTbl = (String)value.get("ONLTBL");
/*      */     }
/*   83 */     if ((value == null) || (value.isEmpty()) || (StringUtils.isEmpty(aThdTbl)))
/*      */     {
/*   85 */       throw new HiAppException(-1, "220301", "CrpCod");
/*      */     }
/*      */ 
/*   88 */     String ActDat = etf.getChildValue("ActDat");
/*   89 */     if (StringUtils.isEmpty(ActDat))
/*      */     {
/*   91 */       ActDat = ctx.getStrProp("ActDat");
/*   92 */       etf.setChildValue("ActDat", ActDat);
/*      */     }
/*   94 */     String aStmt1 = null;
/*      */ 
/*   96 */     if (!(args.contains("CheckSqlNameSingle")))
/*      */     {
/*   98 */       String LogNo = etf.getChildValue("LogNo");
/*   99 */       if (LogNo == null)
/*      */       {
/*  101 */         throw new HiAppException(-7, "220082", "LogNo");
/*      */       }
/*      */ 
/*  104 */       aStmt1 = "SELECT LogNo, HPrChk, TxnAmt, LChkTm FROM " + aThdTbl + " WHERE BrNo='" + aBrNo + "' AND LogNo = '" + LogNo + "' AND BusTyp = '" + BusTyp + "' AND HPrChk = '0' AND HTxnSt != 'U' AND MstChk = '1' AND CrpCod = '" + CrpCod + "'" + "AND ActDat = '" + ActDat + "'";
/*      */     }
/*      */     else
/*      */     {
/*  117 */       String strDynSentence = null;
/*      */       try
/*      */       {
/*  120 */         strDynSentence = HiDbtSqlHelper.getDynSentence(ctx, aStmt1);
/*      */       }
/*      */       catch (HiException e)
/*      */       {
/*  124 */         throw new HiAppException(-7, e.getCode());
/*      */       }
/*  126 */       aStmt1 = "SELECT LogNo, HPrChk, TxnAmt, LChkTm FROM " + aThdTbl + " " + strDynSentence + " AND BusTyp = '" + BusTyp + "' AND CrpCod = '" + CrpCod + "' AND HPrChk = '0' AND HTxnSt != 'U' AND MstChk = '1'";
/*      */     }
/*      */ 
/*  131 */     HiDBCursor cur = null;
/*      */     try
/*      */     {
/*  134 */       cur = HiDbtUtils.dbtsqlcursor(aStmt1, "O", null, etf, ctx);
/*      */     }
/*      */     catch (HiException e)
/*      */     {
/*  138 */       throw new HiAppException(-7, "打开数据库游标c_tranlist4失败!");
/*      */     }
/*  140 */     int iTimeOutOrErrNum = 0;
/*  141 */     int iDataErrFlg = 0;
/*  142 */     int iInterval = 0;
/*  143 */     int i = 0;
/*  144 */     int iCrcFlg = 0;
/*  145 */     int iRdrFlg = 0;
/*  146 */     i = 0; for (iTimeOutOrErrNum = 0; iTimeOutOrErrNum < 10; ++i)
/*      */     {
/*  148 */       long iTime = System.currentTimeMillis() / 1000L;
/*  149 */       HashMap info = null;
/*      */       try
/*      */       {
/*  152 */         info = cur.next();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  156 */         iDataErrFlg = 1;
/*  157 */         break label1052:
/*      */       }
/*  159 */       if (info == null) break; if (info.isEmpty()) {
/*      */         break;
/*      */       }
/*      */ 
/*  163 */       String aLogNo = (String)info.get("LOGNO");
/*  164 */       String aHPrChk = (String)info.get("HPRCHK");
/*  165 */       String aTxnAmt = (String)info.get("TXNAMT");
/*  166 */       String aLChkTm = (String)info.get("LCHKTM");
/*      */ 
/*  168 */       if (!(aHPrChk.equals("0")))
/*      */       {
/*      */         continue;
/*      */       }
/*      */ 
/*  173 */       if ((aHPrChk.equals("0")) && (StringUtils.isNotEmpty(aLChkTm)) && (iTime - Long.parseLong(aLChkTm) < 300L))
/*      */       {
/*  177 */         if (log.isInfoEnabled())
/*      */         {
/*  179 */           log.info(HiStringManager.getManager().getString("HiChkOnlAtl.checkSingleOnline2", String.valueOf(300)));
/*      */         }
/*      */ 
/*  183 */         label906: iInterval = 1;
/*      */       }
/*      */       else
/*      */       {
/*  187 */         if (flag == 1)
/*      */         {
/*  192 */           boolean iRet = HiTransJnlHelper.commitWork(ctx);
/*  193 */           if (iRet)
/*      */           {
/*  196 */             iCrcFlg = 1;
/*  197 */             String aStmt2 = "UPDATE " + aThdTbl + " SET LChkTm = '" + iTime + "' WHERE LogNo = '" + aLogNo + "'";
/*      */             try
/*      */             {
/*  201 */               ctx.getDataBaseUtil().execUpdate(aStmt2);
/*      */             }
/*      */             catch (HiException e)
/*      */             {
/*  206 */               ctx.getDataBaseUtil().rollback();
/*  207 */               iDataErrFlg = 1;
/*  208 */               break label1052:
/*      */             }
/*      */ 
/*  211 */             ctx.getDataBaseUtil().commit();
/*  212 */             continue;
/*      */           }
/*  214 */           if (!(iRet))
/*      */           {
/*  216 */             iDataErrFlg = 1;
/*  217 */             break;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  222 */         if (flag == 0)
/*      */         {
/*  224 */           String aStatus = HiTransJnlHelper.queryTansaction(ctx);
/*      */ 
/*  226 */           if (StringUtils.isNotEmpty(aStatus))
/*      */           {
/*  228 */             if ("012".indexOf(aStatus.substring(0, 1)) == -1)
/*      */               break label906;
/*  230 */             iCrcFlg = 1;
/*  231 */             continue;
/*      */           }
/*      */ 
/*  236 */           log.warn("取冲正流水状态失败");
/*  237 */           iDataErrFlg = 1;
/*  238 */           break;
/*      */         }
/*      */ 
/*  241 */         int iRet = checkHostAccount(args, ctx, aLogNo, aTxnAmt, aHPrChk, "");
/*  242 */         if (iRet != 0)
/*      */         {
/*  245 */           ++iTimeOutOrErrNum;
/*      */         }
/*      */         else {
/*  248 */           String aHTxnSt = etf.getChildValue("HTxnSt");
/*      */ 
/*  250 */           String aStmt2 = "UPDATE " + aThdTbl + " SET HPrChk = '" + aHPrChk + "', HTxnSt = '" + aHTxnSt + "' WHERE BrNo='" + aBrNo + "' AND LogNo = '" + aLogNo + "'";
/*      */           try
/*      */           {
/*  256 */             ctx.getDataBaseUtil().execUpdate(aStmt2);
/*      */           }
/*      */           catch (HiException e)
/*      */           {
/*  261 */             ctx.getDataBaseUtil().rollback();
/*  262 */             iDataErrFlg = 1;
/*  263 */             break label1052:
/*      */           }
/*      */ 
/*  266 */           ctx.getDataBaseUtil().commit(); } }
/*      */     }
/*  268 */     label1052: cur.close();
/*  269 */     if (iDataErrFlg == 1)
/*      */     {
/*  272 */       throw new HiAppException(-7, "数据库操作错误!");
/*      */     }
/*  274 */     if (iCrcFlg == 1)
/*      */     {
/*  277 */       throw new HiAppException(-5, "存在正在冲正的交易!");
/*      */     }
/*  279 */     if (iRdrFlg == 1)
/*      */     {
/*  282 */       throw new HiAppException(-6, "存在正在重发的交易!");
/*      */     }
/*  284 */     if (iTimeOutOrErrNum > 0)
/*      */     {
/*  287 */       throw new HiAppException(-4, "已处理的交易中存在主机超时或错误!");
/*      */     }
/*  289 */     if (iInterval == 1)
/*      */     {
/*  292 */       throw new HiAppException(-8, "存在与上次对帐的时间间隔不足的记录!");
/*      */     }
/*  294 */     return 0;
/*      */   }
/*      */ 
/*      */   public int CheckMulti(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  327 */     return checkManyOnline(args, ctx, 0);
/*      */   }
/*      */ 
/*      */   public int CheckMultiSpecific(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  360 */     return checkManyOnline(args, ctx, 1);
/*      */   }
/*      */ 
/*      */   public int CheckSingle(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  399 */     return checkSingleOnline(args, ctx, 0);
/*      */   }
/*      */ 
/*      */   public int checkSingleOnline(HiATLParam args, HiMessageContext ctx, int flag)
/*      */     throws HiException
/*      */   {
/*  436 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/*  437 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*  438 */     String aBrNo = etf.getChildValue("BrNo");
/*  439 */     if (aBrNo == null)
/*      */     {
/*  441 */       throw new HiAppException(-7, "220082", "BrNo");
/*      */     }
/*  443 */     String BusTyp = etf.getChildValue("BusTyp");
/*  444 */     if (BusTyp == null)
/*      */     {
/*  446 */       throw new HiAppException(-7, "220082", "BusTyp");
/*      */     }
/*  448 */     String CrpCod = etf.getChildValue("CrpCod");
/*  449 */     if (CrpCod == null)
/*      */     {
/*  451 */       throw new HiAppException(-7, "220082", "CrpCod");
/*      */     }
/*  453 */     String strSql = "SELECT OnlTbl FROM pubjnldef WHERE BRNO='" + aBrNo + "' ADN BUSTYP='" + BusTyp + "' AND CRPCOD='" + CrpCod + "'";
/*      */ 
/*  455 */     HashMap value = null;
/*      */     try
/*      */     {
/*  458 */       value = ctx.getDataBaseUtil().readRecord(strSql);
/*      */     }
/*      */     catch (HiException e)
/*      */     {
/*  462 */       throw new HiAppException(-7, "215019");
/*      */     }
/*  464 */     String aThdTbl = null;
/*      */ 
/*  466 */     if ((value != null) && (!(value.isEmpty())))
/*      */     {
/*  468 */       aThdTbl = (String)value.get("ONLTBL");
/*      */     }
/*  470 */     if ((value == null) || (value.isEmpty()) || (StringUtils.isEmpty(aThdTbl)))
/*      */     {
/*  472 */       throw new HiAppException(-1, "220301", "CrpCod");
/*      */     }
/*      */ 
/*  475 */     String aStmt1 = null;
/*  476 */     if (!(args.contains("CheckSqlNameSingle")))
/*      */     {
/*  478 */       String LogNo = etf.getChildValue("LogNo");
/*  479 */       if (LogNo == null)
/*      */       {
/*  481 */         throw new HiAppException(-7, "220082", "LogNo");
/*      */       }
/*      */ 
/*  484 */       aStmt1 = "SELECT LogNo, HPrChk, HTxnSt, TxnAmt, MstChk, LChkTm FROM " + aThdTbl + " WHERE BrNo='" + aBrNo + "' AND LogNo = '" + LogNo + "' AND BusTyp = '" + BusTyp + "' AND CrpCod = '" + CrpCod + "'";
/*      */     }
/*      */     else
/*      */     {
/*  497 */       String strDynSentence = null;
/*      */       try
/*      */       {
/*  500 */         strDynSentence = HiDbtSqlHelper.getDynSentence(ctx, aStmt1);
/*      */       }
/*      */       catch (HiException e)
/*      */       {
/*  504 */         throw new HiAppException(-7, e.getCode());
/*      */       }
/*  506 */       aStmt1 = "SELECT  LogNo, HPrChk, HTxnSt, TxnAmt, MstChk, LChkTm FROM " + aThdTbl + " " + strDynSentence + " AND BusTyp = '" + BusTyp + "' AND CrpCod = '" + CrpCod + "'";
/*      */     }
/*      */ 
/*  513 */     HiDBCursor cur = null;
/*      */     try
/*      */     {
/*  516 */       cur = HiDbtUtils.dbtsqlcursor(aStmt1, "O", null, etf, ctx);
/*      */     }
/*      */     catch (HiException e)
/*      */     {
/*  520 */       throw new HiAppException(-7, "打开数据库游标c_tranlist4失败!");
/*      */     }
/*  522 */     long iTime = System.currentTimeMillis() / 1000L;
/*  523 */     int iDataErrFlg = 0;
/*  524 */     int iPreFlg = 0;
/*  525 */     int iCheck = 0;
/*  526 */     int iInterval = 0;
/*  527 */     int i = 0;
/*  528 */     int iCrcFlg = 0;
/*  529 */     int iRdrFlg = 0;
/*  530 */     int iTimeOutOrErrNum = 0;
/*  531 */     for (i = 0; ; ++i)
/*      */     {
/*  533 */       HashMap info = null;
/*      */       try
/*      */       {
/*  536 */         info = cur.next();
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  540 */         iDataErrFlg = 1;
/*  541 */         break label1092:
/*      */       }
/*  543 */       if (info == null) break; if (info.isEmpty()) {
/*      */         break;
/*      */       }
/*      */ 
/*  547 */       String aLogNo = (String)info.get("LOGNO");
/*  548 */       String aHPrChk = (String)info.get("HPRCHK");
/*  549 */       String aHTxnSt = (String)info.get("HTXNST");
/*  550 */       String aTxnAmt = (String)info.get("TXNAMT");
/*  551 */       String aMstChk = (String)info.get("MSTCHK");
/*  552 */       String aLChkTm = (String)info.get("LCHKTM");
/*  553 */       if (log.isInfoEnabled())
/*      */       {
/*  555 */         log.info(HiStringManager.getManager().getString("HiChkOnlAtl.checkSingleOnline1", aLogNo));
/*      */       }
/*      */ 
/*  559 */       if (aMstChk.equals("0"))
/*      */       {
/*      */         continue;
/*      */       }
/*      */ 
/*  564 */       if (aHTxnSt.equalsIgnoreCase("U"))
/*      */       {
/*  566 */         if (log.isInfoEnabled())
/*      */         {
/*  568 */           log.info(HiStringManager.getManager().getString("HiChkOnlAtl.checkSingleOnline", aLogNo, aThdTbl));
/*      */         }
/*      */ 
/*  571 */         iPreFlg = 1;
/*      */       }
/*      */       else
/*      */       {
/*  575 */         if (!(aHPrChk.equalsIgnoreCase("0")))
/*      */         {
/*  577 */           iCheck = 1;
/*  578 */           etf.setChildValue("HPrChk", aHPrChk);
/*  579 */           etf.setChildValue("HTxnSt", aHTxnSt);
/*      */ 
/*  581 */           if (StringUtils.equals(aHTxnSt, "S"))
/*      */             continue;
/*  583 */           break;
/*      */         }
/*      */ 
/*  588 */         if ((aHPrChk.equals("0")) && (aLChkTm != null) && (iTime - Long.parseLong(aLChkTm) < 300L))
/*      */         {
/*  592 */           if (log.isInfoEnabled())
/*      */           {
/*  594 */             log.info(HiStringManager.getManager().getString("HiChkOnlAtl.checkSingleOnline2", String.valueOf(300)));
/*      */           }
/*      */ 
/*  598 */           iInterval = 1;
/*      */         }
/*      */         else
/*      */         {
/*      */           String aStmt2;
/*  601 */           if (flag == 1)
/*      */           {
/*  605 */             boolean iRet = HiTransJnlHelper.commitWork(ctx);
/*  606 */             if (iRet)
/*      */             {
/*  608 */               iCrcFlg = 1;
/*  609 */               aStmt2 = "UPDATE " + aThdTbl + " SET LChkTm = '" + iTime + "' WHERE BrNo='" + aBrNo + "' AND LogNo = '" + aLogNo + "'";
/*      */               try
/*      */               {
/*  614 */                 ctx.getDataBaseUtil().execUpdate(aStmt2);
/*      */               }
/*      */               catch (HiException e)
/*      */               {
/*  619 */                 ctx.getDataBaseUtil().rollback();
/*  620 */                 iDataErrFlg = 1;
/*  621 */                 break label1092:
/*      */               }
/*      */ 
/*  624 */               ctx.getDataBaseUtil().commit();
/*  625 */               continue;
/*      */             }
/*  627 */             if (!(iRet))
/*      */             {
/*  629 */               iDataErrFlg = 1;
/*  630 */               break;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  661 */           int iRet = checkHostAccount(args, ctx, aLogNo, aTxnAmt, aHPrChk, aHTxnSt);
/*      */ 
/*  663 */           if (iRet != 0)
/*      */           {
/*  666 */             ++iTimeOutOrErrNum;
/*      */           }
/*      */           else
/*      */           {
/*  671 */             if (flag == 1)
/*      */             {
/*  673 */               aStmt2 = "UPDATE " + aThdTbl + " SET HPrChk = '" + aHPrChk + "', HTxnSt = '" + aHTxnSt + "' WHERE BrNo='" + aBrNo + "' AND LogNo = '" + aLogNo + "'";
/*      */               try
/*      */               {
/*  679 */                 ctx.getDataBaseUtil().execUpdate(aStmt2);
/*      */               }
/*      */               catch (HiException e)
/*      */               {
/*  683 */                 ctx.getDataBaseUtil().rollback();
/*  684 */                 iDataErrFlg = 1;
/*  685 */                 break label1092:
/*      */               }
/*  687 */               ctx.getDataBaseUtil().commit();
/*      */             }
/*      */ 
/*  690 */             if (StringUtils.equals("S", aHTxnSt)) break;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  695 */     label1092: cur.close();
/*      */ 
/*  697 */     if (iDataErrFlg == 1)
/*      */     {
/*  700 */       throw new HiAppException(-7, "数据库操作错误!");
/*      */     }
/*  702 */     if (iCrcFlg == 1)
/*      */     {
/*  705 */       throw new HiAppException(-5, "存在正在冲正的交易!");
/*      */     }
/*  707 */     if (iRdrFlg == 1)
/*      */     {
/*  710 */       throw new HiAppException(-6, "存在正在重发的交易!");
/*      */     }
/*  712 */     if (iTimeOutOrErrNum > 0)
/*      */     {
/*  715 */       throw new HiAppException(-4, "已处理的交易中存在主机超时或错误!");
/*      */     }
/*  717 */     if (iPreFlg == 1)
/*      */     {
/*  720 */       throw new HiAppException(-3, "主机交易状态为预记时不能对帐!");
/*      */     }
/*  722 */     if ((i == 0) && (iCheck == 0))
/*      */     {
/*  725 */       throw new HiAppException(-2, "找不到符合条件的前置流水!");
/*      */     }
/*  727 */     if (iInterval == 1)
/*      */     {
/*  730 */       throw new HiAppException(-8, "存在与上次对帐的时间间隔不足的记录!");
/*      */     }
/*  732 */     return 0;
/*      */   }
/*      */ 
/*      */   public int CheckSingleSpecific(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  766 */     return checkSingleOnline(args, ctx, 1);
/*      */   }
/*      */ 
/*      */   public int checkHostAccount(HiATLParam args, HiMessageContext ctx, String LogNo, String TxnAmt, String HPrChk, String HTxnSt)
/*      */     throws HiException
/*      */   {
/*  794 */     String aValue = "888888";
/*  795 */     if (args.contains("CheckTxnCod"))
/*      */     {
/*  797 */       aValue = args.get("CheckTxnCod");
/*      */     }
/*  799 */     if (aValue.length() != 6)
/*      */     {
/*  801 */       throw new HiAppException(-1, "220302");
/*      */     }
/*  803 */     HiMessage oldMsg = ctx.getCurrentMsg();
/*  804 */     HiETF etf = oldMsg.getETFBody();
/*  805 */     etf.setChildValue("HTxnCd", "888888");
/*  806 */     etf.setChildValue("LogNo", LogNo);
/*      */ 
/*  808 */     HiMessage newMsg = new HiMessage(ctx.getCurrentMsg());
/*  809 */     newMsg.setHeadItem("STC", aValue);
/*  810 */     ctx.setCurrentMsg(newMsg);
/*      */     try
/*      */     {
/*  813 */       HiRouterOut.process(ctx);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  817 */       ctx.setCurrentMsg(oldMsg);
/*  818 */       if (e instanceof HiException)
/*  819 */         throw new HiAppException(-1, "220303");
/*      */     }
/*  821 */     ctx.setCurrentMsg(oldMsg);
/*  822 */     if ((newMsg.getETFBody() == null) || (newMsg.getETFBody().getRootNode() == null))
/*      */     {
/*  825 */       throw new HiAppException(-1, "220304");
/*      */     }
/*  827 */     String aError = newMsg.getHeadItem("SSC");
/*  828 */     if ((StringUtils.isNotEmpty(aError)) && (aError.equals("000000")))
/*      */     {
/*  830 */       throw new HiAppException(-1, "220305");
/*      */     }
/*  832 */     String MsgTyp = newMsg.getETFBody().getChildValue("MsgTyp");
/*  833 */     if (MsgTyp == null)
/*      */     {
/*  835 */       throw new HiAppException(-7, "220082", "MsgTyp");
/*      */     }
/*  837 */     String HRspCd = newMsg.getETFBody().getChildValue("HRspCd");
/*  838 */     if (HRspCd == null)
/*      */     {
/*  840 */       throw new HiAppException(-7, "220082", "HRspCd");
/*      */     }
/*  842 */     String aTxnAmt = newMsg.getETFBody().getChildValue("TxnAmt");
/*  843 */     if (aTxnAmt == null)
/*      */     {
/*  845 */       throw new HiAppException(-7, "220082", "TxnAmt");
/*      */     }
/*      */ 
/*  848 */     if (HRspCd.substring(2).compareTo("0000") == 0)
/*      */     {
/*  851 */       if (Math.abs(Float.valueOf(aTxnAmt).compareTo(Float.valueOf(TxnAmt))) > 1.4E-45F)
/*      */       {
/*  854 */         HPrChk = "2";
/*  855 */         HTxnSt = "E";
/*  856 */         etf.setChildValue("HPrChk", HPrChk);
/*  857 */         etf.setChildValue("HTxnSt", HTxnSt);
/*  858 */         return 0;
/*      */       }
/*      */ 
/*  861 */       HPrChk = "1";
/*  862 */       HTxnSt = "S";
/*      */ 
/*  864 */       etf.setChildValue("HPrChk", HPrChk);
/*  865 */       etf.setChildValue("HTxnSt", HTxnSt);
/*  866 */       return 0;
/*      */     }
/*      */ 
/*  869 */     if ((HRspCd.compareTo("120113") == 0) && 
/*  872 */       (Math.abs(Float.valueOf(aTxnAmt).compareTo(Float.valueOf(TxnAmt))) > 1.4E-45F))
/*      */     {
/*  875 */       HPrChk = "2";
/*  876 */       HTxnSt = "F";
/*  877 */       etf.setChildValue("HPrChk", HPrChk);
/*  878 */       etf.setChildValue("HTxnSt", HTxnSt);
/*  879 */       return 0;
/*      */     }
/*      */ 
/*  883 */     if ((HRspCd.compareTo("120087") == 0) && 
/*  886 */       (Math.abs(Float.valueOf(aTxnAmt).compareTo(Float.valueOf(TxnAmt))) > 1.4E-45F))
/*      */     {
/*  889 */       HPrChk = "1";
/*  890 */       HTxnSt = "R";
/*  891 */       etf.setChildValue("HPrChk", HPrChk);
/*  892 */       etf.setChildValue("HTxnSt", HTxnSt);
/*  893 */       return 0;
/*      */     }
/*      */ 
/*  896 */     return -1;
/*      */   }
/*      */ 
/*      */   public int InitCheckDayEnd(HiATLParam args, HiMessageContext ctx)
/*      */   {
/*  901 */     return 0;
/*      */   }
/*      */ 
/*      */   public int CollateDetail(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  940 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*      */ 
/*  942 */     if (args.contains("ATableName"))
/*      */     {
/*  944 */       throw new HiAppException(-1, "220207", "ATableName");
/*      */     }
/*      */ 
/*  947 */     String aTableA = args.get("ATableName");
/*  948 */     if (args.contains("BTableName"))
/*      */     {
/*  950 */       throw new HiAppException(-1, "220207", "BTableName");
/*      */     }
/*      */ 
/*  953 */     String aTableB = args.get("BTableName");
/*  954 */     if (args.contains("ACheckScope"))
/*      */     {
/*  956 */       throw new HiAppException(-1, "220207", "ACheckScope");
/*      */     }
/*      */ 
/*  959 */     String ACheckScope = args.get("ACheckScope");
/*  960 */     if (args.contains("BCheckScope"))
/*      */     {
/*  962 */       throw new HiAppException(-1, "220207", "BCheckScope");
/*      */     }
/*      */ 
/*  965 */     String BCheckScope = args.get("BCheckScope");
/*      */ 
/*  967 */     String aStmt1 = HiDbtSqlHelper.getDynSentence(ctx, ACheckScope);
/*  968 */     String aStmt2 = HiDbtSqlHelper.getDynSentence(ctx, BCheckScope);
/*      */ 
/*  970 */     String aChkFlg1 = args.get("AFlagField");
/*      */ 
/*  972 */     if (log.isInfoEnabled())
/*      */     {
/*  974 */       log.info(HiStringManager.getManager().getString("HiChkOnlAtl.CollateDetail", "A", aChkFlg1));
/*      */     }
/*      */ 
/*  978 */     String aChkFlg2 = args.get("BFlagField");
/*  979 */     if (log.isInfoEnabled())
/*      */     {
/*  981 */       log.info(HiStringManager.getManager().getString("HiChkOnlAtl.CollateDetail", "B", aChkFlg2));
/*      */     }
/*      */ 
/*  985 */     String aChkDat1 = "ActDat";
/*  986 */     if (args.contains("AChkDate"))
/*  987 */       aChkDat1 = args.get("AChkDate");
/*  988 */     if (log.isInfoEnabled())
/*      */     {
/*  990 */       log.info(HiStringManager.getManager().getString("HiChkOnlAtl.CollateDetail1", "A", aChkDat1));
/*      */     }
/*      */ 
/*  993 */     String aChkDat2 = "ActDat";
/*  994 */     if (args.contains("BChkDate"))
/*  995 */       aChkDat2 = args.get("BChkDate");
/*  996 */     if (log.isInfoEnabled())
/*      */     {
/*  998 */       log.info(HiStringManager.getManager().getString("HiChkOnlAtl.CollateDetail1", "B", aChkDat2));
/*      */     }
/*      */ 
/* 1001 */     String ChkTyp = "N";
/* 1002 */     if (args.contains("ChkTyp")) {
/* 1003 */       ChkTyp = args.get("ChkTyp");
/*      */     }
/* 1005 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 1006 */     String aActDat = etf.getChildValue("ActDat");
/* 1007 */     if (StringUtils.isEmpty(aActDat))
/*      */     {
/* 1009 */       throw new HiAppException(-1, "220082", "ActDat");
/*      */     }
/*      */ 
/* 1012 */     if (StringUtils.isNotEmpty(aChkFlg1))
/*      */     {
/* 1014 */       String aStmt = "UPDATE " + aTableA + " SET " + aTableA.trim() + "." + aChkFlg1.trim() + " " + aStmt1;
/*      */       try
/*      */       {
/* 1018 */         int rows = ctx.getDataBaseUtil().execUpdate(aStmt);
/* 1019 */         if (rows == 0)
/*      */         {
/* 1021 */           ctx.getDataBaseUtil().rollback();
/* 1022 */           throw new HiAppException(-1, "220208", aStmt);
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (HiException e)
/*      */       {
/* 1028 */         ctx.getDataBaseUtil().rollback();
/* 1029 */         throw new HiAppException(-1, "220208", aStmt);
/*      */       }
/*      */     }
/*      */ 
/* 1033 */     return 0;
/*      */   }
/*      */ }