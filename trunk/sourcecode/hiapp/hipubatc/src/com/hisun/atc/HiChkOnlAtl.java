 package com.hisun.atc;
 
 import com.hisun.atc.common.HiDBCursor;
 import com.hisun.atc.common.HiDbtSqlHelper;
 import com.hisun.atc.common.HiDbtUtils;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.dispatcher.HiRouterOut;
 import com.hisun.exception.HiAppException;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import java.util.HashMap;
 import org.apache.commons.lang.StringUtils;
 
 public class HiChkOnlAtl
 {
   public int CheckDayEnd(HiATLParam args, HiMessageContext ctx)
   {
     return 0;
   }
 
   public int checkManyOnline(HiATLParam args, HiMessageContext ctx, int flag)
     throws HiException
   {
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     String aBrNo = etf.getChildValue("BrNo");
     if (aBrNo == null)
     {
       throw new HiAppException(-7, "220082", "BrNo");
     }
     String BusTyp = etf.getChildValue("BusTyp");
     if (BusTyp == null)
     {
       throw new HiAppException(-7, "220082", "BusTyp");
     }
     String CrpCod = etf.getChildValue("CrpCod");
     if (CrpCod == null)
     {
       throw new HiAppException(-7, "220082", "CrpCod");
     }
     String strSql = "SELECT OnlTbl FROM pubjnldef WHERE BRNO='" + aBrNo + "' ADN BUSTYP='" + BusTyp + "' AND CRPCOD='" + CrpCod + "'";
 
     HashMap value = null;
     try
     {
       value = ctx.getDataBaseUtil().readRecord(strSql);
     }
     catch (HiException e)
     {
       throw new HiAppException(-7, "215019");
     }
     String aThdTbl = null;
 
     if ((value != null) && (!(value.isEmpty())))
     {
       aThdTbl = (String)value.get("ONLTBL");
     }
     if ((value == null) || (value.isEmpty()) || (StringUtils.isEmpty(aThdTbl)))
     {
       throw new HiAppException(-1, "220301", "CrpCod");
     }
 
     String ActDat = etf.getChildValue("ActDat");
     if (StringUtils.isEmpty(ActDat))
     {
       ActDat = ctx.getStrProp("ActDat");
       etf.setChildValue("ActDat", ActDat);
     }
     String aStmt1 = null;
 
     if (!(args.contains("CheckSqlNameSingle")))
     {
       String LogNo = etf.getChildValue("LogNo");
       if (LogNo == null)
       {
         throw new HiAppException(-7, "220082", "LogNo");
       }
 
       aStmt1 = "SELECT LogNo, HPrChk, TxnAmt, LChkTm FROM " + aThdTbl + " WHERE BrNo='" + aBrNo + "' AND LogNo = '" + LogNo + "' AND BusTyp = '" + BusTyp + "' AND HPrChk = '0' AND HTxnSt != 'U' AND MstChk = '1' AND CrpCod = '" + CrpCod + "'" + "AND ActDat = '" + ActDat + "'";
     }
     else
     {
       String strDynSentence = null;
       try
       {
         strDynSentence = HiDbtSqlHelper.getDynSentence(ctx, aStmt1);
       }
       catch (HiException e)
       {
         throw new HiAppException(-7, e.getCode());
       }
       aStmt1 = "SELECT LogNo, HPrChk, TxnAmt, LChkTm FROM " + aThdTbl + " " + strDynSentence + " AND BusTyp = '" + BusTyp + "' AND CrpCod = '" + CrpCod + "' AND HPrChk = '0' AND HTxnSt != 'U' AND MstChk = '1'";
     }
 
     HiDBCursor cur = null;
     try
     {
       cur = HiDbtUtils.dbtsqlcursor(aStmt1, "O", null, etf, ctx);
     }
     catch (HiException e)
     {
       throw new HiAppException(-7, "打开数据库游标c_tranlist4失败!");
     }
     int iTimeOutOrErrNum = 0;
     int iDataErrFlg = 0;
     int iInterval = 0;
     int i = 0;
     int iCrcFlg = 0;
     int iRdrFlg = 0;
     i = 0; for (iTimeOutOrErrNum = 0; iTimeOutOrErrNum < 10; ++i)
     {
       long iTime = System.currentTimeMillis() / 1000L;
       HashMap info = null;
       try
       {
         info = cur.next();
       }
       catch (Exception e)
       {
         iDataErrFlg = 1;
         break label1052:
       }
       if (info == null) break; if (info.isEmpty()) {
         break;
       }
 
       String aLogNo = (String)info.get("LOGNO");
       String aHPrChk = (String)info.get("HPRCHK");
       String aTxnAmt = (String)info.get("TXNAMT");
       String aLChkTm = (String)info.get("LCHKTM");
 
       if (!(aHPrChk.equals("0")))
       {
         continue;
       }
 
       if ((aHPrChk.equals("0")) && (StringUtils.isNotEmpty(aLChkTm)) && (iTime - Long.parseLong(aLChkTm) < 300L))
       {
         if (log.isInfoEnabled())
         {
           log.info(HiStringManager.getManager().getString("HiChkOnlAtl.checkSingleOnline2", String.valueOf(300)));
         }
 
         label906: iInterval = 1;
       }
       else
       {
         if (flag == 1)
         {
           boolean iRet = HiTransJnlHelper.commitWork(ctx);
           if (iRet)
           {
             iCrcFlg = 1;
             String aStmt2 = "UPDATE " + aThdTbl + " SET LChkTm = '" + iTime + "' WHERE LogNo = '" + aLogNo + "'";
             try
             {
               ctx.getDataBaseUtil().execUpdate(aStmt2);
             }
             catch (HiException e)
             {
               ctx.getDataBaseUtil().rollback();
               iDataErrFlg = 1;
               break label1052:
             }
 
             ctx.getDataBaseUtil().commit();
             continue;
           }
           if (!(iRet))
           {
             iDataErrFlg = 1;
             break;
           }
 
         }
 
         if (flag == 0)
         {
           String aStatus = HiTransJnlHelper.queryTansaction(ctx);
 
           if (StringUtils.isNotEmpty(aStatus))
           {
             if ("012".indexOf(aStatus.substring(0, 1)) == -1)
               break label906;
             iCrcFlg = 1;
             continue;
           }
 
           log.warn("取冲正流水状态失败");
           iDataErrFlg = 1;
           break;
         }
 
         int iRet = checkHostAccount(args, ctx, aLogNo, aTxnAmt, aHPrChk, "");
         if (iRet != 0)
         {
           ++iTimeOutOrErrNum;
         }
         else {
           String aHTxnSt = etf.getChildValue("HTxnSt");
 
           String aStmt2 = "UPDATE " + aThdTbl + " SET HPrChk = '" + aHPrChk + "', HTxnSt = '" + aHTxnSt + "' WHERE BrNo='" + aBrNo + "' AND LogNo = '" + aLogNo + "'";
           try
           {
             ctx.getDataBaseUtil().execUpdate(aStmt2);
           }
           catch (HiException e)
           {
             ctx.getDataBaseUtil().rollback();
             iDataErrFlg = 1;
             break label1052:
           }
 
           ctx.getDataBaseUtil().commit(); } }
     }
     label1052: cur.close();
     if (iDataErrFlg == 1)
     {
       throw new HiAppException(-7, "数据库操作错误!");
     }
     if (iCrcFlg == 1)
     {
       throw new HiAppException(-5, "存在正在冲正的交易!");
     }
     if (iRdrFlg == 1)
     {
       throw new HiAppException(-6, "存在正在重发的交易!");
     }
     if (iTimeOutOrErrNum > 0)
     {
       throw new HiAppException(-4, "已处理的交易中存在主机超时或错误!");
     }
     if (iInterval == 1)
     {
       throw new HiAppException(-8, "存在与上次对帐的时间间隔不足的记录!");
     }
     return 0;
   }
 
   public int CheckMulti(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     return checkManyOnline(args, ctx, 0);
   }
 
   public int CheckMultiSpecific(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     return checkManyOnline(args, ctx, 1);
   }
 
   public int CheckSingle(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     return checkSingleOnline(args, ctx, 0);
   }
 
   public int checkSingleOnline(HiATLParam args, HiMessageContext ctx, int flag)
     throws HiException
   {
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     String aBrNo = etf.getChildValue("BrNo");
     if (aBrNo == null)
     {
       throw new HiAppException(-7, "220082", "BrNo");
     }
     String BusTyp = etf.getChildValue("BusTyp");
     if (BusTyp == null)
     {
       throw new HiAppException(-7, "220082", "BusTyp");
     }
     String CrpCod = etf.getChildValue("CrpCod");
     if (CrpCod == null)
     {
       throw new HiAppException(-7, "220082", "CrpCod");
     }
     String strSql = "SELECT OnlTbl FROM pubjnldef WHERE BRNO='" + aBrNo + "' ADN BUSTYP='" + BusTyp + "' AND CRPCOD='" + CrpCod + "'";
 
     HashMap value = null;
     try
     {
       value = ctx.getDataBaseUtil().readRecord(strSql);
     }
     catch (HiException e)
     {
       throw new HiAppException(-7, "215019");
     }
     String aThdTbl = null;
 
     if ((value != null) && (!(value.isEmpty())))
     {
       aThdTbl = (String)value.get("ONLTBL");
     }
     if ((value == null) || (value.isEmpty()) || (StringUtils.isEmpty(aThdTbl)))
     {
       throw new HiAppException(-1, "220301", "CrpCod");
     }
 
     String aStmt1 = null;
     if (!(args.contains("CheckSqlNameSingle")))
     {
       String LogNo = etf.getChildValue("LogNo");
       if (LogNo == null)
       {
         throw new HiAppException(-7, "220082", "LogNo");
       }
 
       aStmt1 = "SELECT LogNo, HPrChk, HTxnSt, TxnAmt, MstChk, LChkTm FROM " + aThdTbl + " WHERE BrNo='" + aBrNo + "' AND LogNo = '" + LogNo + "' AND BusTyp = '" + BusTyp + "' AND CrpCod = '" + CrpCod + "'";
     }
     else
     {
       String strDynSentence = null;
       try
       {
         strDynSentence = HiDbtSqlHelper.getDynSentence(ctx, aStmt1);
       }
       catch (HiException e)
       {
         throw new HiAppException(-7, e.getCode());
       }
       aStmt1 = "SELECT  LogNo, HPrChk, HTxnSt, TxnAmt, MstChk, LChkTm FROM " + aThdTbl + " " + strDynSentence + " AND BusTyp = '" + BusTyp + "' AND CrpCod = '" + CrpCod + "'";
     }
 
     HiDBCursor cur = null;
     try
     {
       cur = HiDbtUtils.dbtsqlcursor(aStmt1, "O", null, etf, ctx);
     }
     catch (HiException e)
     {
       throw new HiAppException(-7, "打开数据库游标c_tranlist4失败!");
     }
     long iTime = System.currentTimeMillis() / 1000L;
     int iDataErrFlg = 0;
     int iPreFlg = 0;
     int iCheck = 0;
     int iInterval = 0;
     int i = 0;
     int iCrcFlg = 0;
     int iRdrFlg = 0;
     int iTimeOutOrErrNum = 0;
     for (i = 0; ; ++i)
     {
       HashMap info = null;
       try
       {
         info = cur.next();
       }
       catch (Exception e)
       {
         iDataErrFlg = 1;
         break label1092:
       }
       if (info == null) break; if (info.isEmpty()) {
         break;
       }
 
       String aLogNo = (String)info.get("LOGNO");
       String aHPrChk = (String)info.get("HPRCHK");
       String aHTxnSt = (String)info.get("HTXNST");
       String aTxnAmt = (String)info.get("TXNAMT");
       String aMstChk = (String)info.get("MSTCHK");
       String aLChkTm = (String)info.get("LCHKTM");
       if (log.isInfoEnabled())
       {
         log.info(HiStringManager.getManager().getString("HiChkOnlAtl.checkSingleOnline1", aLogNo));
       }
 
       if (aMstChk.equals("0"))
       {
         continue;
       }
 
       if (aHTxnSt.equalsIgnoreCase("U"))
       {
         if (log.isInfoEnabled())
         {
           log.info(HiStringManager.getManager().getString("HiChkOnlAtl.checkSingleOnline", aLogNo, aThdTbl));
         }
 
         iPreFlg = 1;
       }
       else
       {
         if (!(aHPrChk.equalsIgnoreCase("0")))
         {
           iCheck = 1;
           etf.setChildValue("HPrChk", aHPrChk);
           etf.setChildValue("HTxnSt", aHTxnSt);
 
           if (StringUtils.equals(aHTxnSt, "S"))
             continue;
           break;
         }
 
         if ((aHPrChk.equals("0")) && (aLChkTm != null) && (iTime - Long.parseLong(aLChkTm) < 300L))
         {
           if (log.isInfoEnabled())
           {
             log.info(HiStringManager.getManager().getString("HiChkOnlAtl.checkSingleOnline2", String.valueOf(300)));
           }
 
           iInterval = 1;
         }
         else
         {
           String aStmt2;
           if (flag == 1)
           {
             boolean iRet = HiTransJnlHelper.commitWork(ctx);
             if (iRet)
             {
               iCrcFlg = 1;
               aStmt2 = "UPDATE " + aThdTbl + " SET LChkTm = '" + iTime + "' WHERE BrNo='" + aBrNo + "' AND LogNo = '" + aLogNo + "'";
               try
               {
                 ctx.getDataBaseUtil().execUpdate(aStmt2);
               }
               catch (HiException e)
               {
                 ctx.getDataBaseUtil().rollback();
                 iDataErrFlg = 1;
                 break label1092:
               }
 
               ctx.getDataBaseUtil().commit();
               continue;
             }
             if (!(iRet))
             {
               iDataErrFlg = 1;
               break;
             }
 
           }
 
           int iRet = checkHostAccount(args, ctx, aLogNo, aTxnAmt, aHPrChk, aHTxnSt);
 
           if (iRet != 0)
           {
             ++iTimeOutOrErrNum;
           }
           else
           {
             if (flag == 1)
             {
               aStmt2 = "UPDATE " + aThdTbl + " SET HPrChk = '" + aHPrChk + "', HTxnSt = '" + aHTxnSt + "' WHERE BrNo='" + aBrNo + "' AND LogNo = '" + aLogNo + "'";
               try
               {
                 ctx.getDataBaseUtil().execUpdate(aStmt2);
               }
               catch (HiException e)
               {
                 ctx.getDataBaseUtil().rollback();
                 iDataErrFlg = 1;
                 break label1092:
               }
               ctx.getDataBaseUtil().commit();
             }
 
             if (StringUtils.equals("S", aHTxnSt)) break;
           }
         }
       }
     }
     label1092: cur.close();
 
     if (iDataErrFlg == 1)
     {
       throw new HiAppException(-7, "数据库操作错误!");
     }
     if (iCrcFlg == 1)
     {
       throw new HiAppException(-5, "存在正在冲正的交易!");
     }
     if (iRdrFlg == 1)
     {
       throw new HiAppException(-6, "存在正在重发的交易!");
     }
     if (iTimeOutOrErrNum > 0)
     {
       throw new HiAppException(-4, "已处理的交易中存在主机超时或错误!");
     }
     if (iPreFlg == 1)
     {
       throw new HiAppException(-3, "主机交易状态为预记时不能对帐!");
     }
     if ((i == 0) && (iCheck == 0))
     {
       throw new HiAppException(-2, "找不到符合条件的前置流水!");
     }
     if (iInterval == 1)
     {
       throw new HiAppException(-8, "存在与上次对帐的时间间隔不足的记录!");
     }
     return 0;
   }
 
   public int CheckSingleSpecific(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     return checkSingleOnline(args, ctx, 1);
   }
 
   public int checkHostAccount(HiATLParam args, HiMessageContext ctx, String LogNo, String TxnAmt, String HPrChk, String HTxnSt)
     throws HiException
   {
     String aValue = "888888";
     if (args.contains("CheckTxnCod"))
     {
       aValue = args.get("CheckTxnCod");
     }
     if (aValue.length() != 6)
     {
       throw new HiAppException(-1, "220302");
     }
     HiMessage oldMsg = ctx.getCurrentMsg();
     HiETF etf = oldMsg.getETFBody();
     etf.setChildValue("HTxnCd", "888888");
     etf.setChildValue("LogNo", LogNo);
 
     HiMessage newMsg = new HiMessage(ctx.getCurrentMsg());
     newMsg.setHeadItem("STC", aValue);
     ctx.setCurrentMsg(newMsg);
     try
     {
       HiRouterOut.process(ctx);
     }
     catch (Exception e)
     {
       ctx.setCurrentMsg(oldMsg);
       if (e instanceof HiException)
         throw new HiAppException(-1, "220303");
     }
     ctx.setCurrentMsg(oldMsg);
     if ((newMsg.getETFBody() == null) || (newMsg.getETFBody().getRootNode() == null))
     {
       throw new HiAppException(-1, "220304");
     }
     String aError = newMsg.getHeadItem("SSC");
     if ((StringUtils.isNotEmpty(aError)) && (aError.equals("000000")))
     {
       throw new HiAppException(-1, "220305");
     }
     String MsgTyp = newMsg.getETFBody().getChildValue("MsgTyp");
     if (MsgTyp == null)
     {
       throw new HiAppException(-7, "220082", "MsgTyp");
     }
     String HRspCd = newMsg.getETFBody().getChildValue("HRspCd");
     if (HRspCd == null)
     {
       throw new HiAppException(-7, "220082", "HRspCd");
     }
     String aTxnAmt = newMsg.getETFBody().getChildValue("TxnAmt");
     if (aTxnAmt == null)
     {
       throw new HiAppException(-7, "220082", "TxnAmt");
     }
 
     if (HRspCd.substring(2).compareTo("0000") == 0)
     {
       if (Math.abs(Float.valueOf(aTxnAmt).compareTo(Float.valueOf(TxnAmt))) > 1.4E-45F)
       {
         HPrChk = "2";
         HTxnSt = "E";
         etf.setChildValue("HPrChk", HPrChk);
         etf.setChildValue("HTxnSt", HTxnSt);
         return 0;
       }
 
       HPrChk = "1";
       HTxnSt = "S";
 
       etf.setChildValue("HPrChk", HPrChk);
       etf.setChildValue("HTxnSt", HTxnSt);
       return 0;
     }
 
     if ((HRspCd.compareTo("120113") == 0) && 
       (Math.abs(Float.valueOf(aTxnAmt).compareTo(Float.valueOf(TxnAmt))) > 1.4E-45F))
     {
       HPrChk = "2";
       HTxnSt = "F";
       etf.setChildValue("HPrChk", HPrChk);
       etf.setChildValue("HTxnSt", HTxnSt);
       return 0;
     }
 
     if ((HRspCd.compareTo("120087") == 0) && 
       (Math.abs(Float.valueOf(aTxnAmt).compareTo(Float.valueOf(TxnAmt))) > 1.4E-45F))
     {
       HPrChk = "1";
       HTxnSt = "R";
       etf.setChildValue("HPrChk", HPrChk);
       etf.setChildValue("HTxnSt", HTxnSt);
       return 0;
     }
 
     return -1;
   }
 
   public int InitCheckDayEnd(HiATLParam args, HiMessageContext ctx)
   {
     return 0;
   }
 
   public int CollateDetail(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
 
     if (args.contains("ATableName"))
     {
       throw new HiAppException(-1, "220207", "ATableName");
     }
 
     String aTableA = args.get("ATableName");
     if (args.contains("BTableName"))
     {
       throw new HiAppException(-1, "220207", "BTableName");
     }
 
     String aTableB = args.get("BTableName");
     if (args.contains("ACheckScope"))
     {
       throw new HiAppException(-1, "220207", "ACheckScope");
     }
 
     String ACheckScope = args.get("ACheckScope");
     if (args.contains("BCheckScope"))
     {
       throw new HiAppException(-1, "220207", "BCheckScope");
     }
 
     String BCheckScope = args.get("BCheckScope");
 
     String aStmt1 = HiDbtSqlHelper.getDynSentence(ctx, ACheckScope);
     String aStmt2 = HiDbtSqlHelper.getDynSentence(ctx, BCheckScope);
 
     String aChkFlg1 = args.get("AFlagField");
 
     if (log.isInfoEnabled())
     {
       log.info(HiStringManager.getManager().getString("HiChkOnlAtl.CollateDetail", "A", aChkFlg1));
     }
 
     String aChkFlg2 = args.get("BFlagField");
     if (log.isInfoEnabled())
     {
       log.info(HiStringManager.getManager().getString("HiChkOnlAtl.CollateDetail", "B", aChkFlg2));
     }
 
     String aChkDat1 = "ActDat";
     if (args.contains("AChkDate"))
       aChkDat1 = args.get("AChkDate");
     if (log.isInfoEnabled())
     {
       log.info(HiStringManager.getManager().getString("HiChkOnlAtl.CollateDetail1", "A", aChkDat1));
     }
 
     String aChkDat2 = "ActDat";
     if (args.contains("BChkDate"))
       aChkDat2 = args.get("BChkDate");
     if (log.isInfoEnabled())
     {
       log.info(HiStringManager.getManager().getString("HiChkOnlAtl.CollateDetail1", "B", aChkDat2));
     }
 
     String ChkTyp = "N";
     if (args.contains("ChkTyp")) {
       ChkTyp = args.get("ChkTyp");
     }
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     String aActDat = etf.getChildValue("ActDat");
     if (StringUtils.isEmpty(aActDat))
     {
       throw new HiAppException(-1, "220082", "ActDat");
     }
 
     if (StringUtils.isNotEmpty(aChkFlg1))
     {
       String aStmt = "UPDATE " + aTableA + " SET " + aTableA.trim() + "." + aChkFlg1.trim() + " " + aStmt1;
       try
       {
         int rows = ctx.getDataBaseUtil().execUpdate(aStmt);
         if (rows == 0)
         {
           ctx.getDataBaseUtil().rollback();
           throw new HiAppException(-1, "220208", aStmt);
         }
 
       }
       catch (HiException e)
       {
         ctx.getDataBaseUtil().rollback();
         throw new HiAppException(-1, "220208", aStmt);
       }
     }
 
     return 0;
   }
 }