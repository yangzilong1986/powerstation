/*      */ package com.hisun.atc.common;
/*      */ 
/*      */ import com.hisun.atc.bat.HiRdoJnl;
/*      */ import com.hisun.database.HiDataBaseUtil;
/*      */ import com.hisun.database.HiResultSet;
/*      */ import com.hisun.dispatcher.HiRouterOut;
/*      */ import com.hisun.engine.invoke.impl.HiAttributesHelper;
/*      */ import com.hisun.engine.invoke.impl.HiRunStatus;
/*      */ import com.hisun.exception.HiAppException;
/*      */ import com.hisun.exception.HiException;
/*      */ import com.hisun.hilib.HiATLParam;
/*      */ import com.hisun.hilog4j.HiLog;
/*      */ import com.hisun.hilog4j.Logger;
/*      */ import com.hisun.message.HiETF;
/*      */ import com.hisun.message.HiETFFactory;
/*      */ import com.hisun.message.HiMessage;
/*      */ import com.hisun.message.HiMessageContext;
/*      */ import com.hisun.util.HiStringManager;
/*      */ import com.hisun.util.HiStringUtils;
/*      */ import java.lang.reflect.Field;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.lang.math.NumberUtils;
/*      */ 
/*      */ public class HiAtcLib
/*      */ {
/*   39 */   private static HiStringManager sm = HiStringManager.getManager();
/*      */ 
/* 1500 */   private static List dumtlrs = new ArrayList();
/*      */ 
/* 1502 */   private static Random random = new Random();
/*      */ 
/* 1504 */   private static Object _virtual_teller_lock = new Object();
/*      */ 
/* 1823 */   private static int _currLogNo = 0;
/*      */ 
/* 1828 */   private static int _limitLogNo = -1;
/*      */ 
/* 1833 */   private static int _scale = 100;
/*      */ 
/* 1835 */   private static Object _sqn_get_logno_lock = new Object();
/*      */ 
/*      */   public static int hstThdTxn(HiATLParam args, HiMessageContext ctx, String TxnCod, String ObjSvr, int iFlag, int iHstFlg, int iCrcFlg)
/*      */     throws HiException
/*      */   {
/*   76 */     String strHTxnSt = null;
/*      */ 
/*   80 */     String strTTxnSt = null;
/*      */ 
/*   84 */     String strType = null;
/*   85 */     int retcode = 0;
/*      */ 
/*   87 */     HiMessage msg = ctx.getCurrentMsg();
/*   88 */     Logger log = HiLog.getLogger(msg);
/*   89 */     HiETF etfRoot = (HiETF)msg.getBody();
/*   90 */     String strTxnSts = "X";
/*      */ 
/*   92 */     HiMessage newMsg = msg.cloneNoBody();
/*   93 */     if (newMsg.hasHeadItem("plain_type")) {
/*   94 */       newMsg.setHeadItem("plain_type", "byte");
/*      */     }
/*   96 */     newMsg.setType("PLTOUT");
/*      */ 
/*   99 */     String inParam = args.get("In");
/*  100 */     String outParam = args.get("Out");
/*  101 */     newMsg = buildInParams(inParam, msg, newMsg);
/*      */ 
/*  105 */     if (newMsg == null) {
/*  106 */       throw new HiException("220051");
/*      */     }
/*  108 */     newMsg.setHeadItem("STC", TxnCod);
/*  109 */     newMsg.setHeadItem("SCH", "rq");
/*  110 */     newMsg.setHeadItem("ECT", "text/etf");
/*      */ 
/*  112 */     if (ObjSvr != null) {
/*  113 */       newMsg.setHeadItem("SDT", ObjSvr);
/*      */     }
/*      */ 
/*  119 */     if (log.isDebugEnabled()) {
/*  120 */       log.debug("CALLOUT:SERVICE-" + TxnCod);
/*  121 */       log.debug("发送前消息报文为：" + newMsg);
/*      */     }
/*      */     try
/*      */     {
/*  125 */       ctx.setCurrentMsg(newMsg);
/*      */ 
/*  127 */       HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
/*  128 */       if (!(attr.isLongDbConn())) {
/*  129 */         ctx.getDataBaseUtil().close();
/*      */       }
/*      */ 
/*  132 */       HiRouterOut.process(ctx);
/*      */     } catch (HiException e) {
/*  134 */       HiLog.logServiceError(msg, e);
/*  135 */       if (StringUtils.equals(e.getCode(), "231204"))
/*      */       {
/*  137 */         retcode = 10;
/*  138 */         strTxnSts = "X";
/*  139 */         etfRoot.setChildValue("TXN_STS", strTxnSts);
/*  140 */       } else if ((StringUtils.equals(e.getCode(), "231206")) || (StringUtils.equals(e.getCode(), "231205")))
/*      */       {
/*  145 */         strTxnSts = "T";
/*  146 */         if (iCrcFlg == 1)
/*      */         {
/*  148 */           HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
/*  149 */           runStatus.setSCRCStart(true);
/*      */         }
/*      */ 
/*  153 */         retcode = 1;
/*      */       }
/*  155 */       else if (StringUtils.equals(e.getCode(), "231207"))
/*      */       {
/*  157 */         retcode = 10;
/*  158 */         strTxnSts = "X";
/*  159 */         etfRoot.setChildValue("TXN_STS", strTxnSts);
/*      */       } else {
/*  161 */         retcode = -1;
/*      */ 
/*  163 */         strTxnSts = "X";
/*  164 */         etfRoot.setChildValue("TXN_STS", strTxnSts);
/*      */       }
/*      */     }
/*      */     finally {
/*  168 */       newMsg = ctx.getCurrentMsg();
/*  169 */       ctx.setCurrentMsg(msg);
/*      */     }
/*  171 */     if (log.isDebugEnabled()) {
/*  172 */       log.debug("接收到的消息报文为：" + newMsg);
/*  173 */       log.debug("HiRouterOut.process sucess");
/*      */     }
/*      */ 
/*  176 */     if (retcode != 0)
/*      */     {
/*      */       int ret1;
/*  178 */       String txnjnl = ctx.getJnlTable();
/*      */ 
/*  180 */       if (StringUtils.isEmpty(txnjnl)) {
/*  181 */         return retcode;
/*      */       }
/*      */ 
/*  184 */       if (iHstFlg == 1) {
/*  185 */         strType = "HST";
/*  186 */         strHTxnSt = strTxnSts;
/*  187 */         etfRoot.setChildValue("HTXN_STS", strTxnSts);
/*      */       } else {
/*  189 */         strType = "THD";
/*  190 */         strTTxnSt = strTxnSts;
/*  191 */         etfRoot.setChildValue("TTXN_STS", strTxnSts);
/*      */       }
/*      */ 
/*  194 */       if (iCrcFlg == 1) {
/*  195 */         String strLogNo = etfRoot.getChildValue("LOG_NO");
/*  196 */         if (strLogNo == null) {
/*  197 */           throw new HiException("220058", "LOG_NO");
/*      */         }
/*      */ 
/*  201 */         if (log.isInfoEnabled()) {
/*  202 */           log.info("正交易发送失败或超时，更新流水表中交易状态");
/*      */         }
/*  204 */         ret1 = updTxnJnlSts(ctx, strType, strLogNo, strHTxnSt, strTTxnSt, null);
/*      */       }
/*      */ 
/*  210 */       return retcode;
/*      */     }
/*      */ 
/*  216 */     HiETF rcvRoot = (HiETF)newMsg.getBody();
/*      */ 
/*  218 */     if (iFlag == 0)
/*      */     {
/*  220 */       buildOutParams(outParam, rcvRoot, etfRoot, true);
/*      */     }
/*  224 */     else if (iFlag == 1)
/*      */     {
/*  226 */       buildOutParams(outParam, rcvRoot, etfRoot, false);
/*      */     }
/*      */ 
/*  232 */     if (log.isInfoEnabled()) {
/*  233 */       log.info(sm.getString("HiAtcLib.hstThdTxn00", TxnCod, etfRoot));
/*      */     }
/*      */ 
/*  236 */     if (iHstFlg == 1)
/*      */     {
/*      */       HiRunStatus runStatus;
/*  238 */       String strMsgTyp = rcvRoot.getChildValue("MSG_TYP");
/*  239 */       String strHRspCd = rcvRoot.getChildValue("HRSP_CD");
/*      */ 
/*  242 */       if ((strMsgTyp == null) || (strHRspCd == null)) {
/*  243 */         if (iCrcFlg == 1) {
/*  244 */           runStatus = HiRunStatus.getRunStatus(ctx);
/*  245 */           runStatus.setSCRCStart(true);
/*      */         }
/*      */ 
/*  249 */         throw new HiException("220058", "MSG_TYP:HRSP_CD");
/*      */       }
/*      */ 
/*  253 */       etfRoot.setChildValue("MSG_CD", strHRspCd);
/*      */ 
/*  255 */       if (strMsgTyp.equals("N")) {
/*  256 */         if (iCrcFlg == 1) {
/*  257 */           runStatus = HiRunStatus.getRunStatus(ctx);
/*  258 */           runStatus.setSCRCStart(true);
/*      */         }
/*      */ 
/*  262 */         etfRoot.setChildValue("RSP_CD", "000000");
/*      */       }
/*  264 */       else if (strMsgTyp.equals("A")) {
/*  265 */         etfRoot.setChildValue("RSP_CD", strHRspCd);
/*  266 */         retcode = authAppend(ctx, msg);
/*  267 */         if (retcode < 0) {
/*  268 */           log.warn("添加前置授权失败");
/*      */         }
/*  270 */         retcode = 9;
/*  271 */       } else if (strMsgTyp.equals("E")) {
/*  272 */         retcode = 3;
/*  273 */         etfRoot.setChildValue("RSP_CD", strHRspCd);
/*      */       } else {
/*  275 */         throw new HiException("220066", "MSG_TYP");
/*      */       }
/*      */ 
/*  279 */       if (ctx.getJnlTable() != null)
/*  280 */         if (strMsgTyp.equals("N")) {
/*  281 */           etfRoot.setChildValue("FRSP_CD", "000000");
/*      */ 
/*  283 */           etfRoot.setChildValue("HTXN_STS", "S");
/*      */ 
/*  285 */           etfRoot.setChildValue("TXN_STS", "S");
/*      */         }
/*      */         else {
/*  288 */           etfRoot.setChildValue("FRSP_CD", strHRspCd);
/*  289 */           etfRoot.setChildValue("HTXN_STS", "F");
/*      */ 
/*  291 */           etfRoot.setChildValue("TXN_STS", "F");
/*      */         }
/*      */     }
/*      */     else
/*      */     {
/*      */       HiRunStatus runStatus;
/*  296 */       String strTRspCd = rcvRoot.getChildValue("TRSP_CD");
/*  297 */       if (strTRspCd == null) {
/*  298 */         if (iCrcFlg == 1) {
/*  299 */           runStatus = HiRunStatus.getRunStatus(ctx);
/*  300 */           runStatus.setSCRCStart(true);
/*      */         }
/*      */ 
/*  304 */         throw new HiException("220058", "TRSP_CD");
/*      */       }
/*      */ 
/*  307 */       etfRoot.setChildValue("MSG_CD", strTRspCd);
/*  308 */       if (strTRspCd.equals("000000")) {
/*  309 */         if (iCrcFlg == 1) {
/*  310 */           runStatus = HiRunStatus.getRunStatus(ctx);
/*  311 */           runStatus.setSCRCStart(true);
/*      */         }
/*      */ 
/*  315 */         etfRoot.setChildValue("RSP_CD", "000000");
/*      */ 
/*  317 */         etfRoot.setChildValue("MSG_TYP", "N");
/*      */       }
/*      */       else {
/*  320 */         retcode = 3;
/*  321 */         etfRoot.setChildValue("RSP_CD", strTRspCd);
/*  322 */         etfRoot.setChildValue("MSG_TYP", "E");
/*      */       }
/*      */ 
/*  326 */       if (ctx.getJnlTable() != null) {
/*  327 */         if (strTRspCd.equals("000000")) {
/*  328 */           etfRoot.setChildValue("FRSP_CD", "000000");
/*      */ 
/*  330 */           etfRoot.setChildValue("TTXN_STS", "S");
/*      */ 
/*  332 */           etfRoot.setChildValue("TXN_STS", "S");
/*      */         }
/*      */         else {
/*  335 */           etfRoot.setChildValue("FRSP_CD", strTRspCd);
/*  336 */           etfRoot.setChildValue("TTXN_STS", "F");
/*      */ 
/*  338 */           etfRoot.setChildValue("TXN_STS", "F");
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  344 */     return retcode;
/*      */   }
/*      */ 
/*      */   public static int updTxnJnlSts(HiMessageContext ctx, String type, String logno, String HTxnSt, String TTxnSt, String TxnSts)
/*      */     throws HiException
/*      */   {
/*  363 */     String txnjnltbl = null;
/*  364 */     HiMessage mess = ctx.getCurrentMsg();
/*  365 */     Logger log = HiLog.getLogger(mess);
/*      */ 
/*  367 */     txnjnltbl = ctx.getJnlTable();
/*  368 */     if (txnjnltbl == null) {
/*  369 */       throw new HiException("220052", "未定义流水表");
/*      */     }
/*  371 */     StringBuffer sqlcmd = new StringBuffer();
/*  372 */     sqlcmd.append("update ");
/*  373 */     sqlcmd.append(txnjnltbl);
/*  374 */     sqlcmd.append(" set ");
/*      */ 
/*  376 */     if (type.equals("HST")) {
/*  377 */       sqlcmd.append("HTXN_STS = '" + HTxnSt + "'");
/*  378 */     } else if (type.equals("THD")) {
/*  379 */       sqlcmd.append("TTXN_STS = '" + TTxnSt + "'");
/*  380 */     } else if (type.equals("FRT")) {
/*  381 */       sqlcmd.append("TXN_STS = '" + TxnSts + "'");
/*  382 */     } else if (type.equals("HAF")) {
/*  383 */       sqlcmd.append("HTXN_STS = '" + HTxnSt + "'");
/*  384 */       sqlcmd.append("TXN_STS = '" + TxnSts + "'");
/*  385 */     } else if (type.equals("TAF")) {
/*  386 */       sqlcmd.append("TTXN_STS = '" + TTxnSt + "'");
/*  387 */       sqlcmd.append("TXN_STS = '" + TxnSts + "'");
/*  388 */     } else if (type.equals("HAT")) {
/*  389 */       sqlcmd.append("HTXN_STS = '" + HTxnSt + "'");
/*  390 */       sqlcmd.append("TTXN_STS = '" + TTxnSt + "'");
/*  391 */     } else if (type.equals("ALL")) {
/*  392 */       sqlcmd.append("HTXN_STS = '" + HTxnSt + "'");
/*  393 */       sqlcmd.append("TTXN_STS = '" + TTxnSt + "'");
/*  394 */       sqlcmd.append("TXN_STS = '" + TxnSts + "'");
/*      */     } else {
/*  396 */       return -1;
/*      */     }
/*      */ 
/*  399 */     sqlcmd.append(" where ");
/*  400 */     sqlcmd.append("LOG_NO='" + logno + "'");
/*      */ 
/*  402 */     if (log.isInfoEnabled()) {
/*  403 */       log.info(sm.getString("HiAtcLib.sqlCmd00", txnjnltbl, sqlcmd));
/*      */     }
/*  405 */     int ret = ctx.getDataBaseUtil().execUpdate(sqlcmd.toString());
/*  406 */     if (ret == 0) {
/*  407 */       return 2;
/*      */     }
/*  409 */     return 0;
/*      */   }
/*      */ 
/*      */   public static int authAppend(HiMessageContext trandata, HiMessage mess)
/*      */   {
/*  470 */     return 0;
/*      */   }
/*      */ 
/*      */   public static ArrayList getAuthTableMaxLvl(HiMessage mess, HashMap authTable)
/*      */   {
/*  520 */     return new ArrayList();
/*      */   }
/*      */ 
/*      */   public static StringBuffer judgeStrcat(StringBuffer sqlcmd, String NodeName, String Value, boolean bflag)
/*      */   {
/*  588 */     if ((Value == null) || (Value.length() == 0)) {
/*  589 */       return sqlcmd;
/*      */     }
/*      */ 
/*  597 */     if (bflag) {
/*  598 */       sqlcmd.append(",");
/*      */     }
/*  600 */     sqlcmd.append(NodeName);
/*  601 */     sqlcmd.append("='");
/*  602 */     sqlcmd.append(Value);
/*  603 */     sqlcmd.append("'");
/*      */ 
/*  605 */     return sqlcmd;
/*      */   }
/*      */ 
/*      */   public static int insertTable(String tablename, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  620 */     HiMessage mess = ctx.getCurrentMsg();
/*  621 */     HiETF root = mess.getETFBody();
/*  622 */     Logger log = HiLog.getLogger(mess);
/*      */ 
/*  624 */     HashMap colsMap = ctx.getTableMetaData(tablename);
/*  625 */     if ((colsMap == null) || (colsMap.size() == 0)) {
/*  626 */       throw new HiException("220056", "取表结构失败：" + tablename);
/*      */     }
/*      */ 
/*  630 */     StringBuffer names = new StringBuffer();
/*  631 */     StringBuffer values = new StringBuffer();
/*  632 */     for (Iterator it = colsMap.entrySet().iterator(); it.hasNext(); ) {
/*  633 */       Map.Entry entry = (Map.Entry)it.next();
/*  634 */       String name = (String)entry.getKey();
/*      */ 
/*  636 */       String value = root.getChildValue(name);
/*      */ 
/*  638 */       if (value != null) {
/*  639 */         if (names.length() > 0) {
/*  640 */           names.append(",");
/*  641 */           names.append(name);
/*      */ 
/*  643 */           values.append(",'");
/*  644 */           values.append(HiDbtSqlHelper.sqlEscape(value));
/*  645 */           values.append("'");
/*      */         } else {
/*  647 */           names.append(name);
/*  648 */           values.append("'");
/*  649 */           values.append(HiDbtSqlHelper.sqlEscape(value));
/*  650 */           values.append("'");
/*      */         }
/*      */       }
/*      */     }
/*  654 */     StringBuffer sqlCmd = new StringBuffer();
/*  655 */     sqlCmd.append("INSERT INTO ");
/*  656 */     sqlCmd.append(tablename);
/*  657 */     sqlCmd.append("(");
/*  658 */     sqlCmd.append(names);
/*  659 */     sqlCmd.append(") VALUES(");
/*  660 */     sqlCmd.append(values);
/*  661 */     sqlCmd.append(")");
/*  662 */     if (log.isInfoEnabled()) {
/*  663 */       log.info("插入记录[" + sqlCmd + "]");
/*      */     }
/*  665 */     int ret = ctx.getDataBaseUtil().execUpdate(sqlCmd.toString());
/*  666 */     if (ret == 0) {
/*  667 */       return 1;
/*      */     }
/*  669 */     return 0;
/*      */   }
/*      */ 
/*      */   public static int queryToETF(HiMessageContext ctx, String sqlcmd)
/*      */     throws HiException
/*      */   {
/*  683 */     HiMessage mess = ctx.getCurrentMsg();
/*  684 */     Logger log = HiLog.getLogger(mess);
/*      */ 
/*  688 */     List queryRs = ctx.getDataBaseUtil().execQuery(sqlcmd);
/*      */ 
/*  692 */     if ((queryRs != null) && (queryRs.size() == 0)) {
/*  693 */       return -1;
/*      */     }
/*      */ 
/*  696 */     Map queryRec = (HashMap)queryRs.get(0);
/*      */ 
/*  698 */     Map.Entry recEntry = null;
/*  699 */     Iterator recIt = queryRec.entrySet().iterator();
/*      */ 
/*  701 */     HiETF etfBody = mess.getETFBody();
/*      */ 
/*  703 */     while (recIt.hasNext()) {
/*  704 */       recEntry = (Map.Entry)recIt.next();
/*  705 */       etfBody.setChildValue((String)recEntry.getKey(), (String)recEntry.getValue());
/*      */     }
/*      */ 
/*  709 */     return 0;
/*      */   }
/*      */ 
/*      */   public static void extInsertGroup(String tablename, String GroupName, boolean isIgnore, String FldLst, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  724 */     extInsertGroupDeep("011", tablename, GroupName, isIgnore, FldLst, ctx);
/*      */   }
/*      */ 
/*      */   private static void extInsertGroupDeep(String ExtKey, String tablename, String GroupName, boolean isIgnore, String FldLst, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  730 */     HiMessage mess = ctx.getCurrentMsg();
/*  731 */     HiETF root = mess.getETFBody();
/*  732 */     Logger log = HiLog.getLogger(mess);
/*      */ 
/*  734 */     String childTableName = null;
/*  735 */     String newExtKey = null;
/*      */ 
/*  737 */     childTableName = HiDbtSqlHelper.getChildTableName(tablename, ctx);
/*      */ 
/*  740 */     HashMap colsMap = ctx.getTableMetaData(tablename);
/*  741 */     if ((colsMap == null) || (colsMap.size() == 0)) {
/*  742 */       throw new HiException("220056", "取表结构失败：" + tablename);
/*      */     }
/*      */ 
/*  747 */     Map refEtfFlds = null;
/*  748 */     if (FldLst != null) {
/*  749 */       refEtfFlds = HiArgUtils.getEtfFields(root, FldLst, "|");
/*      */     }
/*      */ 
/*  752 */     HiETF groupNodeRec = null;
/*  753 */     List groupNodes = root.getGrandChildFuzzyEnd(GroupName + "_");
/*  754 */     Iterator grpIt = groupNodes.iterator();
/*      */ 
/*  756 */     Map.Entry refEtf = null;
/*      */ 
/*  758 */     StringBuffer sqlCmd = new StringBuffer();
/*  759 */     StringBuffer names = new StringBuffer();
/*  760 */     StringBuffer values = new StringBuffer();
/*  761 */     while (grpIt.hasNext()) {
/*  762 */       groupNodeRec = ((HiETF)grpIt.next()).cloneNode();
/*  763 */       sqlCmd.setLength(0);
/*  764 */       names.setLength(0);
/*  765 */       values.setLength(0);
/*  766 */       Iterator refEtfIt = refEtfFlds.entrySet().iterator();
/*      */ 
/*  769 */       while (refEtfIt.hasNext()) {
/*  770 */         refEtf = (Map.Entry)refEtfIt.next();
/*  771 */         groupNodeRec.setChildValue((String)refEtf.getKey(), (String)refEtf.getValue());
/*      */       }
/*      */ 
/*  777 */       String value = null;
/*      */ 
/*  779 */       for (Iterator it = colsMap.entrySet().iterator(); it.hasNext(); ) {
/*  780 */         Map.Entry entry = (Map.Entry)it.next();
/*  781 */         String name = (String)entry.getKey();
/*  782 */         value = groupNodeRec.getChildValue(name);
/*      */ 
/*  785 */         if (name.equalsIgnoreCase("EXTKEY"))
/*      */         {
/*  787 */           if (ExtKey.equals("011"))
/*  788 */             newExtKey = HiDbtSqlHelper.getExtKey(ctx);
/*      */           else {
/*  790 */             newExtKey = ExtKey;
/*      */           }
/*      */ 
/*  801 */           if (sqlCmd.length() > 0) {
/*  802 */             names.append(",");
/*  803 */             names.append(name);
/*  804 */             values.append(",");
/*  805 */             values.append(newExtKey);
/*      */           }
/*      */           else {
/*  808 */             names.append(name);
/*  809 */             values.append(newExtKey);
/*      */           }
/*      */ 
/*      */         }
/*  813 */         else if (name.equalsIgnoreCase("CTBLNM"))
/*      */         {
/*  823 */           if (names.length() > 0) {
/*  824 */             names.append(",");
/*  825 */             names.append(name);
/*  826 */             values.append(",'");
/*  827 */             values.append(childTableName);
/*  828 */             values.append("'");
/*      */           } else {
/*  830 */             names.append(name);
/*  831 */             values.append("'");
/*  832 */             values.append(childTableName);
/*  833 */             values.append("'");
/*      */           }
/*      */ 
/*      */         }
/*  837 */         else if (value != null)
/*      */         {
/*  847 */           if (names.length() > 0) {
/*  848 */             names.append(",");
/*  849 */             names.append(name);
/*  850 */             values.append(",'");
/*  851 */             values.append(value);
/*  852 */             values.append("'");
/*      */           } else {
/*  854 */             names.append(name);
/*  855 */             values.append("'");
/*  856 */             values.append(value);
/*  857 */             values.append("'");
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  862 */       sqlCmd.append("INSERT INTO ");
/*  863 */       sqlCmd.append(tablename);
/*  864 */       sqlCmd.append(" (");
/*  865 */       sqlCmd.append(names);
/*  866 */       sqlCmd.append(") VALUES(");
/*  867 */       sqlCmd.append(values);
/*  868 */       sqlCmd.append(")");
/*      */ 
/*  872 */       if (log.isInfoEnabled()) {
/*  873 */         log.info("插入记录[" + sqlCmd + "]");
/*      */       }
/*      */       try
/*      */       {
/*  877 */         ctx.getDataBaseUtil().execUpdate(sqlCmd.toString());
/*      */       }
/*      */       catch (HiException e) {
/*  880 */         if (!(isIgnore))
/*      */         {
/*  882 */           throw e;
/*      */         }
/*      */       }
/*  885 */       if ((StringUtils.isNotEmpty(childTableName)) && (StringUtils.isNotEmpty(newExtKey)))
/*      */       {
/*  887 */         extInsertGroupDeep(newExtKey, childTableName, GroupName, isIgnore, FldLst, ctx);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  892 */     groupNodes.clear();
/*      */   }
/*      */ 
/*      */   public static void extInsertRecord(String tablename, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  906 */     extInsertRecordDeep("011", tablename, ctx);
/*      */   }
/*      */ 
/*      */   private static void extInsertRecordDeep(String ExtKey, String tablename, HiMessageContext ctx) throws HiException
/*      */   {
/*  911 */     HiMessage mess = ctx.getCurrentMsg();
/*  912 */     HiETF root = mess.getETFBody();
/*  913 */     Logger log = HiLog.getLogger(mess);
/*      */ 
/*  915 */     String childTableName = null;
/*      */ 
/*  917 */     childTableName = HiDbtSqlHelper.getChildTableName(tablename, ctx);
/*      */ 
/*  919 */     if (ExtKey.equals("011")) {
/*  920 */       ExtKey = HiDbtSqlHelper.getExtKey(ctx);
/*      */     }
/*      */ 
/*  925 */     HashMap colsMap = ctx.getTableMetaData(tablename);
/*  926 */     if ((colsMap == null) || (colsMap.size() == 0)) {
/*  927 */       throw new HiException("220056", "取表结构失败：" + tablename);
/*      */     }
/*      */ 
/*  933 */     StringBuffer names = new StringBuffer();
/*  934 */     StringBuffer values = new StringBuffer();
/*  935 */     StringBuffer sqlCmd = new StringBuffer();
/*  936 */     for (Iterator it = colsMap.entrySet().iterator(); it.hasNext(); ) {
/*  937 */       Map.Entry entry = (Map.Entry)it.next();
/*  938 */       String name = (String)entry.getKey();
/*  939 */       String value = root.getChildValue(name);
/*      */ 
/*  942 */       if (name.equalsIgnoreCase("EXTKEY"))
/*      */       {
/*  952 */         if (names.length() > 0) {
/*  953 */           names.append(",");
/*  954 */           names.append(name);
/*      */ 
/*  956 */           values.append(",");
/*  957 */           values.append(ExtKey);
/*      */         }
/*      */         else {
/*  960 */           names.append(name);
/*      */ 
/*  962 */           values.append(ExtKey);
/*      */         }
/*      */ 
/*      */       }
/*  966 */       else if (name.equalsIgnoreCase("CTBLNM"))
/*      */       {
/*  976 */         if (names.length() > 0) {
/*  977 */           names.append(",");
/*  978 */           names.append(name);
/*      */ 
/*  980 */           values.append(",'");
/*  981 */           values.append(childTableName);
/*  982 */           values.append("'");
/*      */         } else {
/*  984 */           names.append(name);
/*      */ 
/*  986 */           values.append("'");
/*  987 */           values.append(childTableName);
/*  988 */           values.append("'");
/*      */         }
/*      */ 
/*      */       }
/*  992 */       else if (value != null) {
/*  993 */         if (names.length() > 0) {
/*  994 */           names.append(",");
/*  995 */           names.append(name);
/*      */ 
/*  997 */           values.append(",'");
/*  998 */           values.append(value);
/*  999 */           values.append("'");
/*      */         } else {
/* 1001 */           names.append(name);
/*      */ 
/* 1003 */           values.append("'");
/* 1004 */           values.append(value);
/* 1005 */           values.append("'");
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1010 */     sqlCmd.append("INSERT INTO ");
/* 1011 */     sqlCmd.append(tablename);
/* 1012 */     sqlCmd.append(" (");
/* 1013 */     sqlCmd.append(names);
/* 1014 */     sqlCmd.append(") VALUES(");
/* 1015 */     sqlCmd.append(values);
/* 1016 */     sqlCmd.append(")");
/*      */ 
/* 1020 */     if (log.isInfoEnabled()) {
/* 1021 */       log.info("插入记录[" + sqlCmd + "]");
/*      */     }
/* 1023 */     ctx.getDataBaseUtil().execUpdate(sqlCmd.toString());
/*      */ 
/* 1027 */     if (!(StringUtils.isEmpty(childTableName)))
/* 1028 */       extInsertRecordDeep(ExtKey, childTableName, ctx);
/*      */   }
/*      */ 
/*      */   public static void extReadRecordCondition(String tablename, String CndSts, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1044 */     HiMessage mess = ctx.getCurrentMsg();
/* 1045 */     Logger log = HiLog.getLogger(mess);
/* 1046 */     String sqlcmd = HiStringUtils.format("SELECT * FROM %s WHERE %s", tablename, CndSts);
/*      */ 
/* 1049 */     if (log.isInfoEnabled()) {
/* 1050 */       log.info("查询语句:[" + sqlcmd + "]");
/*      */     }
/* 1052 */     List queryRs = ctx.getDataBaseUtil().execQuery(sqlcmd);
/*      */ 
/* 1056 */     if ((queryRs != null) && (queryRs.size() == 0)) {
/* 1057 */       throw new HiAppException(-100, "220040", "数据库无此记录");
/*      */     }
/*      */ 
/* 1061 */     Map queryRec = (HashMap)queryRs.get(0);
/*      */ 
/* 1063 */     Map.Entry recEntry = null;
/* 1064 */     Iterator recIt = queryRec.entrySet().iterator();
/*      */ 
/* 1066 */     HiETF etfBody = mess.getETFBody();
/* 1067 */     String childTable = null;
/* 1068 */     String ExtKey = null;
/*      */ 
/* 1070 */     while (recIt.hasNext()) {
/* 1071 */       recEntry = (Map.Entry)recIt.next();
/* 1072 */       String colname = (String)recEntry.getKey();
/* 1073 */       String colvalue = (String)recEntry.getValue();
/* 1074 */       if (colname.equalsIgnoreCase("CTBLNM"))
/* 1075 */         childTable = colvalue;
/* 1076 */       else if (colname.equalsIgnoreCase("EXTKEY"))
/* 1077 */         ExtKey = colvalue;
/*      */       else {
/* 1079 */         etfBody.setChildValue(colname, colvalue);
/*      */       }
/*      */     }
/*      */ 
/* 1083 */     if ((!(StringUtils.isNotEmpty(childTable))) || (!(StringUtils.isNotEmpty(ExtKey))))
/*      */       return;
/* 1085 */     CndSts = HiStringUtils.format("EXTKEY = %s ", ExtKey);
/* 1086 */     extReadRecordCondition(childTable, CndSts, ctx);
/*      */   }
/*      */ 
/*      */   public static void extUpdateRecordCondition(String tablename, String CndSts, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1103 */     HiMessage mess = ctx.getCurrentMsg();
/* 1104 */     Logger log = HiLog.getLogger(mess);
/*      */ 
/* 1106 */     HiETF etfBody = mess.getETFBody();
/*      */ 
/* 1108 */     String sqlQueryChild = HiStringUtils.format("SELECT CTBLNM, EXTKEY FROM %s WHERE %s", tablename, CndSts);
/*      */ 
/* 1110 */     List queryRs = ctx.getDataBaseUtil().execQuery(sqlQueryChild);
/*      */ 
/* 1114 */     if ((queryRs != null) && (queryRs.size() == 0)) {
/* 1115 */       throw new HiAppException(-100, "220040", "数据库无此记录");
/*      */     }
/*      */ 
/* 1120 */     Map queryRec = (HashMap)queryRs.get(0);
/* 1121 */     String childTable = (String)queryRec.get("CTBLNM");
/* 1122 */     String ExtKey = (String)queryRec.get("EXTKEY");
/*      */ 
/* 1125 */     HashMap tblMeta = ctx.getTableMetaData(tablename);
/*      */ 
/* 1128 */     StringBuffer sqlSentence = new StringBuffer();
/* 1129 */     sqlSentence.append("UPDATE ");
/* 1130 */     sqlSentence.append(tablename);
/* 1131 */     sqlSentence.append(" SET ");
/*      */ 
/* 1133 */     Iterator metaIt = tblMeta.entrySet().iterator();
/*      */ 
/* 1135 */     Map.Entry metaEntry = null;
/* 1136 */     String colNam = null;
/* 1137 */     HiETF colEtf = null;
/* 1138 */     boolean first = true;
/* 1139 */     while (metaIt.hasNext()) {
/* 1140 */       metaEntry = (Map.Entry)metaIt.next();
/*      */ 
/* 1142 */       colNam = (String)metaEntry.getKey();
/* 1143 */       if (colNam.equalsIgnoreCase("CTBLNM")) continue; if (colNam.equalsIgnoreCase("EXTKEY"))
/*      */       {
/*      */         continue;
/*      */       }
/*      */ 
/* 1148 */       colEtf = etfBody.getChildNode(colNam);
/* 1149 */       if (colEtf == null)
/*      */       {
/*      */         continue;
/*      */       }
/*      */ 
/* 1155 */       if (!(first)) {
/* 1156 */         sqlSentence.append(",");
/*      */       }
/* 1158 */       sqlSentence.append(colNam);
/* 1159 */       sqlSentence.append("='");
/* 1160 */       sqlSentence.append(colEtf.getValue());
/* 1161 */       sqlSentence.append("'");
/* 1162 */       first = false;
/*      */     }
/*      */ 
/* 1166 */     sqlSentence.append(" WHERE ");
/*      */ 
/* 1168 */     sqlSentence.append(CndSts);
/*      */ 
/* 1170 */     if (log.isInfoEnabled()) {
/* 1171 */       log.info("更新语句:[" + sqlSentence + "]");
/*      */     }
/*      */ 
/* 1174 */     ctx.getDataBaseUtil().execUpdate(sqlSentence.toString());
/*      */ 
/* 1177 */     if ((!(StringUtils.isNotEmpty(childTable))) || (!(StringUtils.isNotEmpty(ExtKey))))
/*      */       return;
/* 1179 */     CndSts = HiStringUtils.format("EXTKEY = %s ", ExtKey);
/* 1180 */     extUpdateRecord(childTable, CndSts, ctx);
/*      */   }
/*      */ 
/*      */   private static void expUpdateRecord(String tablename, String CndSts, HiETF root, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1186 */     HiMessage mess = ctx.getCurrentMsg();
/* 1187 */     Logger log = HiLog.getLogger(mess);
/*      */ 
/* 1189 */     HashMap tblMeta = ctx.getTableMetaData(tablename);
/*      */ 
/* 1192 */     StringBuffer sqlSentence = new StringBuffer();
/* 1193 */     sqlSentence.append("UPDATE ");
/* 1194 */     sqlSentence.append(tablename);
/* 1195 */     sqlSentence.append(" SET ");
/* 1196 */     Iterator metaIt = tblMeta.entrySet().iterator();
/*      */ 
/* 1198 */     Map.Entry metaEntry = null;
/* 1199 */     String colNam = null;
/* 1200 */     HiETF colEtf = null;
/* 1201 */     boolean first = true;
/* 1202 */     while (metaIt.hasNext()) {
/* 1203 */       metaEntry = (Map.Entry)metaIt.next();
/*      */ 
/* 1205 */       colNam = (String)metaEntry.getKey();
/* 1206 */       if (colNam.equalsIgnoreCase("CTBLNM")) continue; if (colNam.equalsIgnoreCase("EXTKEY"))
/*      */       {
/*      */         continue;
/*      */       }
/*      */ 
/* 1211 */       colEtf = root.getChildNode(colNam);
/* 1212 */       if (colEtf == null) {
/*      */         continue;
/*      */       }
/* 1215 */       if (!(first))
/* 1216 */         sqlSentence.append(",");
/* 1217 */       sqlSentence.append(colNam);
/* 1218 */       sqlSentence.append("='");
/* 1219 */       sqlSentence.append(colEtf.getValue());
/* 1220 */       sqlSentence.append("'");
/* 1221 */       first = false;
/*      */     }
/*      */ 
/* 1227 */     sqlSentence.append(" WHERE ");
/* 1228 */     sqlSentence.append(CndSts);
/*      */ 
/* 1231 */     if (log.isInfoEnabled()) {
/* 1232 */       log.info("更新语句:[" + sqlSentence + "]");
/*      */     }
/*      */ 
/* 1235 */     ctx.getDataBaseUtil().execUpdate(sqlSentence.toString());
/*      */   }
/*      */ 
/*      */   private static void extUpdateRecord(String tablename, String CndSts, HiMessageContext ctx) throws HiException
/*      */   {
/* 1240 */     expUpdateRecord(tablename, CndSts, ctx.getCurrentMsg().getETFBody(), ctx);
/*      */   }
/*      */ 
/*      */   public static void extUpdateGroupCondition(String tablename, String CndSts, String GroupName, boolean isIgnore, String FldLst, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1259 */     HiMessage mess = ctx.getCurrentMsg();
/* 1260 */     Logger log = HiLog.getLogger(mess);
/*      */ 
/* 1262 */     HiETF etfBody = mess.getETFBody();
/* 1263 */     String childTable = null;
/* 1264 */     String ExtKey = null;
/*      */ 
/* 1267 */     Map refEtfFlds = null;
/* 1268 */     if (FldLst != null) {
/* 1269 */       refEtfFlds = HiArgUtils.getEtfFields(etfBody, FldLst, "|");
/*      */     }
/*      */ 
/* 1272 */     HiETF groupNodeRec = null;
/* 1273 */     List groupNodes = etfBody.getGrandChildFuzzyEnd(GroupName + "_");
/* 1274 */     Iterator grpIt = groupNodes.iterator();
/*      */ 
/* 1276 */     Map.Entry refEtf = null;
/*      */ 
/* 1278 */     while (grpIt.hasNext()) {
/* 1279 */       groupNodeRec = ((HiETF)grpIt.next()).cloneNode();
/*      */ 
/* 1281 */       Iterator refEtfIt = refEtfFlds.entrySet().iterator();
/*      */ 
/* 1284 */       while (refEtfIt.hasNext()) {
/* 1285 */         refEtf = (Map.Entry)refEtfIt.next();
/* 1286 */         groupNodeRec.setChildValue((String)refEtf.getKey(), (String)refEtf.getValue());
/*      */       }
/*      */ 
/* 1291 */       String sqlCondition = HiDbtSqlHelper.getDynSentence(ctx, CndSts);
/*      */ 
/* 1293 */       String sqlQueryChild = HiStringUtils.format("SELECT CTBLNM, EXTKEY FROM %s WHERE %s", tablename, sqlCondition);
/*      */ 
/* 1296 */       List queryRs = ctx.getDataBaseUtil().execQuery(sqlQueryChild);
/*      */ 
/* 1299 */       if ((queryRs != null) && (queryRs.size() == 0) && 
/* 1301 */         (!(isIgnore))) {
/* 1302 */         log.error("查询数据库无记录[" + sqlQueryChild + "]");
/* 1303 */         throw new HiAppException(-100, "220040", "数据库无此记录");
/*      */       }
/*      */ 
/* 1309 */       Map queryRec = (HashMap)queryRs.get(0);
/* 1310 */       childTable = (String)queryRec.get("CTBLNM");
/* 1311 */       ExtKey = (String)queryRec.get("EXTKEY");
/*      */       try
/*      */       {
/* 1315 */         HashMap tblMeta = ctx.getTableMetaData(tablename);
/*      */ 
/* 1318 */         StringBuffer sqlSentence = new StringBuffer();
/* 1319 */         sqlSentence.append("UPDATE ");
/* 1320 */         sqlSentence.append(tablename);
/* 1321 */         sqlSentence.append(" SET ");
/*      */ 
/* 1323 */         Iterator metaIt = tblMeta.entrySet().iterator();
/*      */ 
/* 1325 */         Map.Entry metaEntry = null;
/* 1326 */         String colNam = null;
/* 1327 */         HiETF colEtf = null;
/* 1328 */         boolean first = true;
/* 1329 */         while (metaIt.hasNext()) {
/* 1330 */           metaEntry = (Map.Entry)metaIt.next();
/*      */ 
/* 1332 */           colNam = (String)metaEntry.getKey();
/* 1333 */           if (colNam.equalsIgnoreCase("CTBLNM")) continue; if (colNam.equalsIgnoreCase("EXTKEY"))
/*      */           {
/*      */             continue;
/*      */           }
/*      */ 
/* 1338 */           colEtf = groupNodeRec.getChildNode(colNam);
/* 1339 */           if (colEtf == null) {
/*      */             continue;
/*      */           }
/* 1342 */           if (!(first))
/* 1343 */             sqlSentence.append(",");
/* 1344 */           sqlSentence.append(colNam);
/* 1345 */           sqlSentence.append("='");
/* 1346 */           sqlSentence.append(colEtf.getValue());
/* 1347 */           sqlSentence.append("'");
/* 1348 */           first = false;
/*      */         }
/*      */ 
/* 1356 */         sqlSentence.append(" WHERE ");
/* 1357 */         sqlSentence.append(sqlCondition);
/* 1358 */         if (log.isInfoEnabled()) {
/* 1359 */           log.info("更新语句:[" + sqlSentence + "]");
/*      */         }
/*      */ 
/* 1362 */         ctx.getDataBaseUtil().execUpdate(sqlSentence.toString());
/*      */ 
/* 1365 */         if ((StringUtils.isNotEmpty(childTable)) && (StringUtils.isNotEmpty(ExtKey)))
/*      */         {
/* 1367 */           CndSts = HiStringUtils.format("EXTKEY = %s ", ExtKey);
/* 1368 */           expUpdateRecord(tablename, CndSts, groupNodeRec, ctx);
/*      */         }
/*      */       }
/*      */       catch (HiException e)
/*      */       {
/* 1373 */         if (!(isIgnore)) {
/* 1374 */           log.error("extUpdateGroupCondition执行有误: 至少有一条执行更新失败");
/* 1375 */           throw e;
/*      */         }
/*      */       }
/*      */     }
/* 1379 */     groupNodes.clear();
/*      */   }
/*      */ 
/*      */   public static int dbtsqlupdrec(HiETF etf, String strTableName, String strKeyName, String strKeyValue, int keyType, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1397 */     String strCondition = strKeyName + " = ";
/* 1398 */     if (keyType == 0)
/* 1399 */       strCondition = strCondition + "'" + strKeyValue + "'";
/*      */     else {
/* 1401 */       strCondition = strCondition + strKeyValue;
/*      */     }
/* 1403 */     String strSQL = updateFormat(strTableName, strCondition, etf, ctx);
/*      */ 
/* 1406 */     ctx.getDataBaseUtil().execUpdate(strSQL);
/*      */ 
/* 1408 */     return 0;
/*      */   }
/*      */ 
/*      */   public static int sqngetbatno(HiMessageContext ctx, int num)
/*      */     throws HiException
/*      */   {
/*      */     int logNo;
/*      */     try
/*      */     {
/* 1447 */       String sqlCmd = "UPDATE PUBPLTINF SET BLOG_NO = BLOG_NO";
/* 1448 */       ctx.getDataBaseUtil().execUpdate(sqlCmd);
/*      */ 
/* 1450 */       sqlCmd = "SELECT BLOG_NO FROM PUBPLTINF";
/* 1451 */       HiResultSet rs = ctx.getDataBaseUtil().execQuerySQL(sqlCmd);
/*      */ 
/* 1453 */       if (rs.size() == 0) {
/* 1454 */         throw new HiException("220040", sqlCmd);
/*      */       }
/*      */ 
/* 1457 */       logNo = rs.getInt(0, 0);
/*      */ 
/* 1459 */       int nextLogNo = logNo + num;
/*      */ 
/* 1469 */       if (nextLogNo < logNo) {
/* 1470 */         throw new HiException("220062", "PUBPLTINF");
/*      */       }
/*      */ 
/* 1473 */       String value = StringUtils.leftPad(String.valueOf(nextLogNo), 12, "0");
/*      */ 
/* 1475 */       sqlCmd = "UPDATE PUBPLTINF SET BLOG_NO='" + value + "'";
/* 1476 */       ctx.getDataBaseUtil().execUpdate(sqlCmd);
/*      */     } finally {
/* 1478 */       ctx.getDataBaseUtil().commit();
/*      */     }
/* 1480 */     return logNo;
/*      */   }
/*      */ 
/*      */   public static String bpbgetbatno(HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1493 */     String strActDat = ctx.getStrProp("ACC_DT");
/* 1494 */     String actdat = StringUtils.substring(strActDat, 2, 8);
/* 1495 */     String blog = StringUtils.leftPad(String.valueOf(sqngetbatno(ctx, 1)), 6, '0');
/*      */ 
/* 1497 */     return actdat + blog;
/*      */   }
/*      */ 
/*      */   public static String sqnGetDumTlr(HiMessageContext ctx, String brNo, String txnCnl, String cnlSub)
/*      */     throws HiException
/*      */   {
/* 1509 */     String sqlCmd = "SELECT DUM_TLR FROM PUBDUMTLR WHERE BR_NO = '%s' AND TXN_CNL = '%s' AND CNL_SUB = '%s'";
/* 1510 */     sqlCmd = HiStringUtils.format(sqlCmd, brNo, txnCnl, cnlSub);
/* 1511 */     synchronized (_virtual_teller_lock) {
/* 1512 */       if (dumtlrs.size() == 0) {
/* 1513 */         dumtlrs = ctx.getDataBaseUtil().execQuery(sqlCmd);
/* 1514 */         if (dumtlrs.size() == 0) {
/* 1515 */           throw new HiException("220040", "PUBDUMTLR");
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1520 */     HashMap dumtlr = (HashMap)dumtlrs.get(random.nextInt(dumtlrs.size()));
/* 1521 */     return ((String)dumtlr.get("DUM_TLR"));
/*      */   }
/*      */ 
/*      */   public static int insertRecord(String strTableName, Object o, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1556 */     Map tableInfo = ctx.getTableMetaData(strTableName);
/*      */ 
/* 1558 */     StringBuffer sqlCmd = new StringBuffer();
/* 1559 */     StringBuffer names = new StringBuffer();
/* 1560 */     StringBuffer values = new StringBuffer();
/*      */ 
/* 1563 */     int index = 0;
/* 1564 */     Field[] fields = o.getClass().getDeclaredFields();
/* 1565 */     for (int i = 0; i < fields.length; ++i)
/*      */     {
/*      */       String value;
/* 1567 */       String strColName = fields[i].getName().toUpperCase();
/* 1568 */       if (!(tableInfo.containsKey(strColName)))
/*      */         continue;
/*      */       try
/*      */       {
/* 1572 */         value = (String)fields[i].get(o);
/*      */       } catch (Exception e) {
/* 1574 */         throw HiException.makeException(e);
/*      */       }
/*      */ 
/* 1577 */       if (value == null) {
/*      */         continue;
/*      */       }
/* 1580 */       if (index > 0) {
/* 1581 */         names.append(",");
/* 1582 */         values.append(",");
/*      */       }
/*      */ 
/* 1589 */       names.append(strColName);
/*      */ 
/* 1591 */       values.append("'");
/* 1592 */       values.append(HiDbtSqlHelper.sqlEscape(value));
/* 1593 */       values.append("'");
/*      */ 
/* 1595 */       ++index;
/*      */     }
/*      */ 
/* 1599 */     sqlCmd.append("INSERT INTO ");
/* 1600 */     sqlCmd.append(strTableName);
/* 1601 */     sqlCmd.append(" (");
/* 1602 */     sqlCmd.append(names);
/* 1603 */     sqlCmd.append(")VALUES(");
/* 1604 */     sqlCmd.append(values);
/* 1605 */     sqlCmd.append(")");
/* 1606 */     ctx.getDataBaseUtil().execUpdate(sqlCmd.toString());
/* 1607 */     return 0;
/*      */   }
/*      */ 
/*      */   public static int appLock(String recKey, String timeOut, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*      */     int ret;
/* 1624 */     HiMessage mess = ctx.getCurrentMsg();
/* 1625 */     Logger log = HiLog.getLogger(mess);
/*      */ 
/* 1627 */     long curLockTime = System.currentTimeMillis();
/* 1628 */     String lockTimeStr = StringUtils.substring(String.valueOf(curLockTime), 0, 10);
/*      */ 
/* 1630 */     curLockTime = Long.parseLong(lockTimeStr);
/*      */ 
/* 1632 */     recKey = recKey.trim();
/*      */ 
/* 1634 */     String sqlStr = "update publckrec set upd_flg='1' where rec_key='" + recKey + "'";
/*      */ 
/* 1636 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/*      */     try
/*      */     {
/* 1639 */       ret = dbUtil.execUpdate(sqlStr);
/*      */ 
/* 1641 */       if (ret == 0) {
/* 1642 */         sqlStr = "insert into publckrec values('" + recKey + "','" + lockTimeStr + "','" + timeOut + "','1')";
/*      */ 
/* 1644 */         dbUtil.execUpdate(sqlStr);
/* 1645 */         if (log.isInfoEnabled())
/* 1646 */           log.info("AppLock: 登记锁成功. aRecKey=[" + recKey + "] aLckTim=[" + curLockTime + "] aTimOut=[" + timeOut + "]");
/*      */       }
/*      */       else
/*      */       {
/* 1650 */         sqlStr = "select lck_tm, tim_out from publckrec where rec_key='" + recKey + "'";
/*      */ 
/* 1653 */         List queryRs = dbUtil.execQuery(sqlStr);
/* 1654 */         Map queryRec = (HashMap)queryRs.get(0);
/*      */ 
/* 1656 */         String oldLckTimStr = (String)queryRec.get("LCK_TM");
/* 1657 */         String oldTimOutStr = (String)queryRec.get("TIM_OUT");
/* 1658 */         long oldLckTim = Long.parseLong(oldLckTimStr.trim());
/* 1659 */         long oldTimOut = Long.parseLong(oldTimOutStr.trim());
/*      */ 
/* 1661 */         if ((curLockTime - oldLckTim < oldTimOut) || (oldTimOut == 0L))
/*      */         {
/* 1663 */           log.error("AppLock: 加锁失败. aRecKey=[" + recKey + "] aLckTim=[" + curLockTime + "] aOldLckTim=[" + oldLckTimStr + "] aOldTimOut=[" + oldTimOutStr + "]");
/*      */ 
/* 1666 */           int i = 2;
/*      */           return i;
/*      */         }
/* 1670 */         sqlStr = "update publckrec set lck_tm='" + lockTimeStr + "', tim_out='" + timeOut + "' where rec_key='" + recKey + "'";
/*      */ 
/* 1674 */         dbUtil.execUpdate(sqlStr);
/*      */ 
/* 1676 */         if (log.isInfoEnabled()) {
/* 1677 */           log.info("AppLock: 加锁成功. aRecKey=[" + recKey + "] aLckTim=[" + curLockTime + "] aOldLckTim=[" + oldLckTimStr + "] aOldTimOut=[" + oldTimOutStr + "]");
/*      */         }
/*      */ 
/* 1681 */         ret = 0; }
/*      */     } finally {
/* 1683 */       dbUtil.commit();
/*      */     }
/* 1685 */     return ret;
/*      */   }
/*      */ 
/*      */   public static int unAppLock(String recKey, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1699 */     int ret = 0;
/* 1700 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*      */ 
/* 1702 */     recKey = recKey.trim();
/* 1703 */     String sqlStr = "delete from publckrec where rec_key='" + recKey + "'";
/*      */ 
/* 1705 */     ret = ctx.getDataBaseUtil().execUpdate(sqlStr);
/*      */ 
/* 1707 */     if (ret == 0) {
/* 1708 */       if (log.isInfoEnabled()) {
/* 1709 */         log.info("AppUnlock: 没有加锁或已经被解锁! sRecKey=[" + recKey + "]");
/*      */       }
/* 1711 */       ret = 1;
/*      */     } else {
/* 1713 */       ctx.getDataBaseUtil().commit();
/*      */     }
/*      */ 
/* 1716 */     return 0;
/*      */   }
/*      */ 
/*      */   public static int tryLock(String recKey, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1730 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*      */ 
/* 1732 */     recKey = recKey.trim();
/* 1733 */     String sqlStr = "select lck_tm, tim_out from publckrec where rec_key='" + recKey + "'";
/*      */ 
/* 1736 */     List queryRs = ctx.getDataBaseUtil().execQuery(sqlStr);
/*      */ 
/* 1738 */     if (queryRs.size() == 0) {
/* 1739 */       if (log.isInfoEnabled()) {
/* 1740 */         log.info("AppTrylock: 没有加锁或已经被解锁! sRecKey=[" + recKey + "]");
/*      */       }
/*      */ 
/* 1743 */       return 0;
/*      */     }
/*      */ 
/* 1747 */     long curLockTime = System.currentTimeMillis();
/* 1748 */     String lockTimeStr = StringUtils.substring(String.valueOf(curLockTime), 0, 10);
/*      */ 
/* 1750 */     curLockTime = Long.parseLong(lockTimeStr);
/*      */ 
/* 1753 */     Map queryRec = (HashMap)queryRs.get(0);
/*      */ 
/* 1755 */     String oldLckTimStr = (String)queryRec.get("LCK_TM");
/* 1756 */     String oldTimOutStr = (String)queryRec.get("TIM_OUT");
/* 1757 */     long oldLckTim = Long.parseLong(oldLckTimStr.trim());
/* 1758 */     long oldTimOut = Long.parseLong(oldTimOutStr.trim());
/*      */ 
/* 1760 */     if (oldLckTim + oldTimOut > curLockTime) {
/* 1761 */       if (log.isInfoEnabled()) {
/* 1762 */         log.info("AppTrylock: 加锁中! sRecKey=[" + recKey + "]");
/*      */       }
/*      */ 
/* 1765 */       return 1;
/*      */     }
/*      */ 
/* 1768 */     if (log.isInfoEnabled()) {
/* 1769 */       log.info("AppTrylock: 锁已经超时失效! sRecKey=[" + recKey + "]");
/*      */     }
/* 1771 */     return 0;
/*      */   }
/*      */ 
/*      */   public static void setBeanPropertyFromETF(Object o, HiETF root) throws HiException
/*      */   {
/* 1776 */     Field[] fields = o.getClass().getDeclaredFields();
/* 1777 */     for (int i = 0; i < fields.length; ++i)
/*      */       try {
/* 1779 */         fields[i].setAccessible(true);
/* 1780 */         fields[i].set(o, root.getChildValue(fields[i].getName()));
/*      */       } catch (IllegalAccessException e) {
/* 1782 */         throw HiException.makeException(e);
/*      */       }
/*      */   }
/*      */ 
/*      */   public static void setBeanPropertyFromMap(Object o, Map map)
/*      */     throws HiException
/*      */   {
/* 1789 */     Field[] fields = o.getClass().getDeclaredFields();
/* 1790 */     for (int i = 0; i < fields.length; ++i)
/*      */       try {
/* 1792 */         fields[i].setAccessible(true);
/* 1793 */         fields[i].set(o, map.get(fields[i].getName().toUpperCase()));
/*      */       } catch (IllegalAccessException e) {
/* 1795 */         throw HiException.makeException(e);
/*      */       }
/*      */   }
/*      */ 
/*      */   public static void setETFFromBeanProperty(Object o, HiETF root)
/*      */     throws HiException
/*      */   {
/* 1802 */     Field[] fields = o.getClass().getDeclaredFields();
/* 1803 */     for (int i = 0; i < fields.length; ++i)
/*      */       try {
/* 1805 */         if ((fields[i].get(o) instanceof String) && (fields[i].get(o) != null))
/*      */         {
/* 1808 */           root.setChildValue(fields[i].getName(), (String)fields[i].get(o));
/*      */         }
/*      */       } catch (IllegalAccessException e) {
/* 1811 */         throw HiException.makeException(e);
/*      */       }
/*      */   }
/*      */ 
/*      */   public static int rdoTranRegister(HiRdoJnl sRdr) throws HiException
/*      */   {
/* 1817 */     return 0;
/*      */   }
/*      */ 
/*      */   public static int sqnGetLogNo(HiMessageContext ctx, int num)
/*      */     throws HiException
/*      */   {
/* 1894 */     synchronized (_sqn_get_logno_lock)
/*      */     {
/*      */       int logNo;
/* 1896 */       if (num <= 0) {
/* 1897 */         num = 1;
/*      */       }
/*      */ 
/* 1900 */       if (_currLogNo + num <= _limitLogNo) {
/* 1901 */         logNo = _currLogNo;
/* 1902 */         _currLogNo += num;
/* 1903 */         return logNo;
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/* 1910 */         String sqlCmd = "UPDATE PUBPLTINF SET LOG_NO = LOG_NO";
/* 1911 */         ctx.getDataBaseUtil().execUpdate(sqlCmd);
/*      */ 
/* 1913 */         sqlCmd = "SELECT LOG_NO FROM PUBPLTINF";
/* 1914 */         HiResultSet rs = ctx.getDataBaseUtil().execQuerySQL(sqlCmd);
/*      */ 
/* 1916 */         if (rs.size() == 0) {
/* 1917 */           throw new HiException("220040", sqlCmd);
/*      */         }
/*      */ 
/* 1921 */         logNo = rs.getInt(0, 0);
/*      */ 
/* 1923 */         int nextLogNo = logNo + _scale;
/* 1924 */         if (_currLogNo == 0)
/* 1925 */           _currLogNo = logNo;
/*      */         else {
/* 1927 */           logNo = _currLogNo;
/*      */         }
/* 1929 */         _limitLogNo = nextLogNo;
/* 1930 */         _currLogNo += num;
/*      */ 
/* 1933 */         if (nextLogNo % 100000000 == 0) {
/* 1934 */           throw new HiException("220062", "PUBPLTINF");
/*      */         }
/*      */ 
/* 1937 */         String value = StringUtils.leftPad(String.valueOf(nextLogNo), 14, "0");
/*      */ 
/* 1939 */         sqlCmd = "UPDATE PUBPLTINF SET LOG_NO='" + value + "'";
/* 1940 */         ctx.getDataBaseUtil().execUpdate(sqlCmd);
/*      */       } finally {
/* 1942 */         ctx.getDataBaseUtil().commit();
/*      */       }
/* 1944 */       return logNo;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static String updateFormat(String tableName, String selectSql, HiETF etfRoot, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1996 */     if ((StringUtils.isEmpty(tableName)) || (StringUtils.isEmpty(selectSql)) || (etfRoot == null))
/*      */     {
/* 1998 */       throw new HiException("220026", "前置系统错误");
/*      */     }
/* 2000 */     StringBuffer sqlcmd = new StringBuffer();
/*      */ 
/* 2002 */     sqlcmd.append("UPDATE " + tableName + " SET ");
/* 2003 */     HashMap colInfo = null;
/*      */     try {
/* 2005 */       colInfo = ctx.getTableMetaData(tableName);
/*      */     } catch (HiException e) {
/* 2007 */       throw e;
/*      */     }
/* 2009 */     Iterator iter = colInfo.keySet().iterator();
/* 2010 */     String val = null;
/* 2011 */     String col = null;
/* 2012 */     while (iter.hasNext()) {
/* 2013 */       col = (String)iter.next();
/* 2014 */       val = etfRoot.getChildValue(col);
/* 2015 */       if (val != null);
/* 2016 */       sqlcmd.append(col);
/* 2017 */       sqlcmd.append("='");
/* 2018 */       sqlcmd.append(val);
/* 2019 */       sqlcmd.append("',");
/*      */     }
/*      */ 
/* 2023 */     sqlcmd.deleteCharAt(sqlcmd.length() - 1);
/* 2024 */     sqlcmd.append(" WHERE ");
/* 2025 */     sqlcmd.append(selectSql);
/*      */ 
/* 2027 */     return sqlcmd.toString();
/*      */   }
/*      */ 
/*      */   public static HiMessage buildInParams(String param, HiMessage inMsg, HiMessage outMsg)
/*      */   {
/* 2032 */     if (param == null) {
/* 2033 */       HiETF root = HiETFFactory.createETF();
/* 2034 */       inMsg.getETFBody().copyTo(root);
/* 2035 */       outMsg.setBody(root);
/* 2036 */       return outMsg;
/*      */     }
/* 2038 */     if (StringUtils.isBlank(param)) {
/* 2039 */       outMsg.setBody(HiETFFactory.createETF());
/* 2040 */       return outMsg;
/*      */     }
/* 2042 */     HiETF inRoot = inMsg.getETFBody();
/* 2043 */     HiETF outRoot = HiETFFactory.createETF();
/* 2044 */     outMsg.setBody(outRoot);
/* 2045 */     String[] tmps = param.split("\\|");
/* 2046 */     for (int i = 0; i < tmps.length; ++i) {
/* 2047 */       if (StringUtils.isBlank(tmps[i])) {
/*      */         continue;
/*      */       }
/* 2050 */       int idx = StringUtils.indexOf(tmps[i], "[]");
/* 2051 */       if (idx == -1)
/*      */       {
/* 2053 */         HiETF node1 = inRoot.getChildNode(tmps[i]);
/* 2054 */         if (node1 == null) {
/*      */           continue;
/*      */         }
/* 2057 */         HiETF node2 = outRoot.getChildNode(tmps[i]);
/* 2058 */         if (node2 != null) {
/* 2059 */           outRoot.removeChildNode(node2);
/*      */         }
/* 2061 */         outRoot.appendNode(node1.cloneNode());
/*      */       }
/*      */       else {
/* 2064 */         String groupName = StringUtils.substring(tmps[i], 0, idx);
/* 2065 */         String tmp = inRoot.getChildValue(groupName + "_NUM");
/* 2066 */         int groupNum = -1;
/* 2067 */         if ((StringUtils.isNotEmpty(tmp)) && (StringUtils.isNumeric(tmp))) {
/* 2068 */           groupNum = NumberUtils.toInt(tmp);
/* 2069 */           outRoot.setChildValue(groupName + "_NUM", tmp);
/*      */         }
/* 2071 */         for (int j = 1; ; ++j) {
/* 2072 */           HiETF node1 = inRoot.getChildNode(groupName + "_" + j);
/* 2073 */           if (((groupNum != -1) && (j > groupNum)) || (node1 == null)) break; if (node1.isEndNode()) {
/*      */             break;
/*      */           }
/* 2076 */           HiETF node2 = outRoot.getChildNode(groupName + "_" + j);
/* 2077 */           if (node2 != null) {
/* 2078 */             outRoot.removeChildNode(node2);
/*      */           }
/* 2080 */           outRoot.appendNode(node1.cloneNode());
/*      */         }
/*      */       }
/*      */     }
/* 2084 */     outMsg.setBody(outRoot);
/* 2085 */     return outMsg;
/*      */   }
/*      */ 
/*      */   public static HiETF buildOutParams(String param, HiETF inRoot, HiETF outRoot, boolean overFlag) throws HiException
/*      */   {
/* 2090 */     if (param == null) {
/* 2091 */       if (overFlag) {
/* 2092 */         if (outRoot.combine(inRoot, true)) break label58;
/* 2093 */         throw new HiException("220059", "合并ETF树失败");
/*      */       }
/*      */ 
/* 2097 */       if (!(outRoot.combine(inRoot, false))) {
/* 2098 */         throw new HiException("220059", "合并ETF树失败");
/*      */       }
/*      */ 
/* 2102 */       label58: return outRoot;
/*      */     }
/*      */ 
/* 2105 */     if (StringUtils.isBlank(param)) {
/* 2106 */       return outRoot;
/*      */     }
/*      */ 
/* 2109 */     String[] tmps = param.split("\\|");
/* 2110 */     for (int i = 0; i < tmps.length; ++i) {
/* 2111 */       if (StringUtils.isBlank(tmps[i])) {
/*      */         continue;
/*      */       }
/* 2114 */       int idx = StringUtils.indexOf(tmps[i], "[]");
/* 2115 */       if (idx == -1)
/*      */       {
/* 2117 */         HiETF node1 = inRoot.getChildNode(tmps[i]);
/* 2118 */         if (node1 == null) {
/*      */           continue;
/*      */         }
/* 2121 */         HiETF node2 = outRoot.getChildNode(tmps[i]);
/* 2122 */         if ((node2 != null) && (overFlag)) {
/* 2123 */           outRoot.removeChildNode(node2);
/*      */         }
/* 2125 */         outRoot.appendNode(node1.cloneNode());
/*      */       }
/*      */       else {
/* 2128 */         String groupName = StringUtils.substring(tmps[i], 0, idx);
/* 2129 */         String tmp = inRoot.getChildValue(groupName + "_NUM");
/* 2130 */         int groupNum = -1;
/* 2131 */         if ((StringUtils.isNotEmpty(tmp)) && (StringUtils.isNumeric(tmp))) {
/* 2132 */           groupNum = NumberUtils.toInt(tmp);
/* 2133 */           outRoot.setChildValue(groupName + "_NUM", tmp);
/*      */         }
/* 2135 */         for (int j = 1; ; ++j) {
/* 2136 */           HiETF node1 = inRoot.getChildNode(groupName + "_" + j);
/* 2137 */           if (((groupNum != -1) && (j > groupNum)) || (node1 == null)) break; if (node1.isEndNode()) {
/*      */             break;
/*      */           }
/* 2140 */           HiETF node2 = outRoot.getChildNode(groupName + "_" + j);
/* 2141 */           if ((node2 != null) && (overFlag)) {
/* 2142 */             outRoot.removeChildNode(node2);
/*      */           }
/* 2144 */           outRoot.appendNode(node1.cloneNode());
/*      */         }
/*      */       }
/*      */     }
/* 2148 */     return outRoot;
/*      */   }
/*      */ }