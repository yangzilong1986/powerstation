 package com.hisun.atc.common;
 
 import com.hisun.atc.bat.HiRdoJnl;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.database.HiResultSet;
 import com.hisun.dispatcher.HiRouterOut;
 import com.hisun.engine.invoke.impl.HiAttributesHelper;
 import com.hisun.engine.invoke.impl.HiRunStatus;
 import com.hisun.exception.HiAppException;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import com.hisun.util.HiStringUtils;
 import java.lang.reflect.Field;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Random;
 import java.util.Set;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiAtcLib
 {
   private static HiStringManager sm = HiStringManager.getManager();
 
   private static List dumtlrs = new ArrayList();
 
   private static Random random = new Random();
 
   private static Object _virtual_teller_lock = new Object();
 
   private static int _currLogNo = 0;
 
   private static int _limitLogNo = -1;
 
   private static int _scale = 100;
 
   private static Object _sqn_get_logno_lock = new Object();
 
   public static int hstThdTxn(HiATLParam args, HiMessageContext ctx, String TxnCod, String ObjSvr, int iFlag, int iHstFlg, int iCrcFlg)
     throws HiException
   {
     String strHTxnSt = null;
 
     String strTTxnSt = null;
 
     String strType = null;
     int retcode = 0;
 
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     HiETF etfRoot = (HiETF)msg.getBody();
     String strTxnSts = "X";
 
     HiMessage newMsg = msg.cloneNoBody();
     if (newMsg.hasHeadItem("plain_type")) {
       newMsg.setHeadItem("plain_type", "byte");
     }
     newMsg.setType("PLTOUT");
 
     String inParam = args.get("In");
     String outParam = args.get("Out");
     newMsg = buildInParams(inParam, msg, newMsg);
 
     if (newMsg == null) {
       throw new HiException("220051");
     }
     newMsg.setHeadItem("STC", TxnCod);
     newMsg.setHeadItem("SCH", "rq");
     newMsg.setHeadItem("ECT", "text/etf");
 
     if (ObjSvr != null) {
       newMsg.setHeadItem("SDT", ObjSvr);
     }
 
     if (log.isDebugEnabled()) {
       log.debug("CALLOUT:SERVICE-" + TxnCod);
       log.debug("发送前消息报文为：" + newMsg);
     }
     try
     {
       ctx.setCurrentMsg(newMsg);
 
       HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
       if (!(attr.isLongDbConn())) {
         ctx.getDataBaseUtil().close();
       }
 
       HiRouterOut.process(ctx);
     } catch (HiException e) {
       HiLog.logServiceError(msg, e);
       if (StringUtils.equals(e.getCode(), "231204"))
       {
         retcode = 10;
         strTxnSts = "X";
         etfRoot.setChildValue("TXN_STS", strTxnSts);
       } else if ((StringUtils.equals(e.getCode(), "231206")) || (StringUtils.equals(e.getCode(), "231205")))
       {
         strTxnSts = "T";
         if (iCrcFlg == 1)
         {
           HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
           runStatus.setSCRCStart(true);
         }
 
         retcode = 1;
       }
       else if (StringUtils.equals(e.getCode(), "231207"))
       {
         retcode = 10;
         strTxnSts = "X";
         etfRoot.setChildValue("TXN_STS", strTxnSts);
       } else {
         retcode = -1;
 
         strTxnSts = "X";
         etfRoot.setChildValue("TXN_STS", strTxnSts);
       }
     }
     finally {
       newMsg = ctx.getCurrentMsg();
       ctx.setCurrentMsg(msg);
     }
     if (log.isDebugEnabled()) {
       log.debug("接收到的消息报文为：" + newMsg);
       log.debug("HiRouterOut.process sucess");
     }
 
     if (retcode != 0)
     {
       int ret1;
       String txnjnl = ctx.getJnlTable();
 
       if (StringUtils.isEmpty(txnjnl)) {
         return retcode;
       }
 
       if (iHstFlg == 1) {
         strType = "HST";
         strHTxnSt = strTxnSts;
         etfRoot.setChildValue("HTXN_STS", strTxnSts);
       } else {
         strType = "THD";
         strTTxnSt = strTxnSts;
         etfRoot.setChildValue("TTXN_STS", strTxnSts);
       }
 
       if (iCrcFlg == 1) {
         String strLogNo = etfRoot.getChildValue("LOG_NO");
         if (strLogNo == null) {
           throw new HiException("220058", "LOG_NO");
         }
 
         if (log.isInfoEnabled()) {
           log.info("正交易发送失败或超时，更新流水表中交易状态");
         }
         ret1 = updTxnJnlSts(ctx, strType, strLogNo, strHTxnSt, strTTxnSt, null);
       }
 
       return retcode;
     }
 
     HiETF rcvRoot = (HiETF)newMsg.getBody();
 
     if (iFlag == 0)
     {
       buildOutParams(outParam, rcvRoot, etfRoot, true);
     }
     else if (iFlag == 1)
     {
       buildOutParams(outParam, rcvRoot, etfRoot, false);
     }
 
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiAtcLib.hstThdTxn00", TxnCod, etfRoot));
     }
 
     if (iHstFlg == 1)
     {
       HiRunStatus runStatus;
       String strMsgTyp = rcvRoot.getChildValue("MSG_TYP");
       String strHRspCd = rcvRoot.getChildValue("HRSP_CD");
 
       if ((strMsgTyp == null) || (strHRspCd == null)) {
         if (iCrcFlg == 1) {
           runStatus = HiRunStatus.getRunStatus(ctx);
           runStatus.setSCRCStart(true);
         }
 
         throw new HiException("220058", "MSG_TYP:HRSP_CD");
       }
 
       etfRoot.setChildValue("MSG_CD", strHRspCd);
 
       if (strMsgTyp.equals("N")) {
         if (iCrcFlg == 1) {
           runStatus = HiRunStatus.getRunStatus(ctx);
           runStatus.setSCRCStart(true);
         }
 
         etfRoot.setChildValue("RSP_CD", "000000");
       }
       else if (strMsgTyp.equals("A")) {
         etfRoot.setChildValue("RSP_CD", strHRspCd);
         retcode = authAppend(ctx, msg);
         if (retcode < 0) {
           log.warn("添加前置授权失败");
         }
         retcode = 9;
       } else if (strMsgTyp.equals("E")) {
         retcode = 3;
         etfRoot.setChildValue("RSP_CD", strHRspCd);
       } else {
         throw new HiException("220066", "MSG_TYP");
       }
 
       if (ctx.getJnlTable() != null)
         if (strMsgTyp.equals("N")) {
           etfRoot.setChildValue("FRSP_CD", "000000");
 
           etfRoot.setChildValue("HTXN_STS", "S");
 
           etfRoot.setChildValue("TXN_STS", "S");
         }
         else {
           etfRoot.setChildValue("FRSP_CD", strHRspCd);
           etfRoot.setChildValue("HTXN_STS", "F");
 
           etfRoot.setChildValue("TXN_STS", "F");
         }
     }
     else
     {
       HiRunStatus runStatus;
       String strTRspCd = rcvRoot.getChildValue("TRSP_CD");
       if (strTRspCd == null) {
         if (iCrcFlg == 1) {
           runStatus = HiRunStatus.getRunStatus(ctx);
           runStatus.setSCRCStart(true);
         }
 
         throw new HiException("220058", "TRSP_CD");
       }
 
       etfRoot.setChildValue("MSG_CD", strTRspCd);
       if (strTRspCd.equals("000000")) {
         if (iCrcFlg == 1) {
           runStatus = HiRunStatus.getRunStatus(ctx);
           runStatus.setSCRCStart(true);
         }
 
         etfRoot.setChildValue("RSP_CD", "000000");
 
         etfRoot.setChildValue("MSG_TYP", "N");
       }
       else {
         retcode = 3;
         etfRoot.setChildValue("RSP_CD", strTRspCd);
         etfRoot.setChildValue("MSG_TYP", "E");
       }
 
       if (ctx.getJnlTable() != null) {
         if (strTRspCd.equals("000000")) {
           etfRoot.setChildValue("FRSP_CD", "000000");
 
           etfRoot.setChildValue("TTXN_STS", "S");
 
           etfRoot.setChildValue("TXN_STS", "S");
         }
         else {
           etfRoot.setChildValue("FRSP_CD", strTRspCd);
           etfRoot.setChildValue("TTXN_STS", "F");
 
           etfRoot.setChildValue("TXN_STS", "F");
         }
       }
 
     }
 
     return retcode;
   }
 
   public static int updTxnJnlSts(HiMessageContext ctx, String type, String logno, String HTxnSt, String TTxnSt, String TxnSts)
     throws HiException
   {
     String txnjnltbl = null;
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     txnjnltbl = ctx.getJnlTable();
     if (txnjnltbl == null) {
       throw new HiException("220052", "未定义流水表");
     }
     StringBuffer sqlcmd = new StringBuffer();
     sqlcmd.append("update ");
     sqlcmd.append(txnjnltbl);
     sqlcmd.append(" set ");
 
     if (type.equals("HST")) {
       sqlcmd.append("HTXN_STS = '" + HTxnSt + "'");
     } else if (type.equals("THD")) {
       sqlcmd.append("TTXN_STS = '" + TTxnSt + "'");
     } else if (type.equals("FRT")) {
       sqlcmd.append("TXN_STS = '" + TxnSts + "'");
     } else if (type.equals("HAF")) {
       sqlcmd.append("HTXN_STS = '" + HTxnSt + "'");
       sqlcmd.append("TXN_STS = '" + TxnSts + "'");
     } else if (type.equals("TAF")) {
       sqlcmd.append("TTXN_STS = '" + TTxnSt + "'");
       sqlcmd.append("TXN_STS = '" + TxnSts + "'");
     } else if (type.equals("HAT")) {
       sqlcmd.append("HTXN_STS = '" + HTxnSt + "'");
       sqlcmd.append("TTXN_STS = '" + TTxnSt + "'");
     } else if (type.equals("ALL")) {
       sqlcmd.append("HTXN_STS = '" + HTxnSt + "'");
       sqlcmd.append("TTXN_STS = '" + TTxnSt + "'");
       sqlcmd.append("TXN_STS = '" + TxnSts + "'");
     } else {
       return -1;
     }
 
     sqlcmd.append(" where ");
     sqlcmd.append("LOG_NO='" + logno + "'");
 
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiAtcLib.sqlCmd00", txnjnltbl, sqlcmd));
     }
     int ret = ctx.getDataBaseUtil().execUpdate(sqlcmd.toString());
     if (ret == 0) {
       return 2;
     }
     return 0;
   }
 
   public static int authAppend(HiMessageContext trandata, HiMessage mess)
   {
     return 0;
   }
 
   public static ArrayList getAuthTableMaxLvl(HiMessage mess, HashMap authTable)
   {
     return new ArrayList();
   }
 
   public static StringBuffer judgeStrcat(StringBuffer sqlcmd, String NodeName, String Value, boolean bflag)
   {
     if ((Value == null) || (Value.length() == 0)) {
       return sqlcmd;
     }
 
     if (bflag) {
       sqlcmd.append(",");
     }
     sqlcmd.append(NodeName);
     sqlcmd.append("='");
     sqlcmd.append(Value);
     sqlcmd.append("'");
 
     return sqlcmd;
   }
 
   public static int insertTable(String tablename, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     HiETF root = mess.getETFBody();
     Logger log = HiLog.getLogger(mess);
 
     HashMap colsMap = ctx.getTableMetaData(tablename);
     if ((colsMap == null) || (colsMap.size() == 0)) {
       throw new HiException("220056", "取表结构失败：" + tablename);
     }
 
     StringBuffer names = new StringBuffer();
     StringBuffer values = new StringBuffer();
     for (Iterator it = colsMap.entrySet().iterator(); it.hasNext(); ) {
       Map.Entry entry = (Map.Entry)it.next();
       String name = (String)entry.getKey();
 
       String value = root.getChildValue(name);
 
       if (value != null) {
         if (names.length() > 0) {
           names.append(",");
           names.append(name);
 
           values.append(",'");
           values.append(HiDbtSqlHelper.sqlEscape(value));
           values.append("'");
         } else {
           names.append(name);
           values.append("'");
           values.append(HiDbtSqlHelper.sqlEscape(value));
           values.append("'");
         }
       }
     }
     StringBuffer sqlCmd = new StringBuffer();
     sqlCmd.append("INSERT INTO ");
     sqlCmd.append(tablename);
     sqlCmd.append("(");
     sqlCmd.append(names);
     sqlCmd.append(") VALUES(");
     sqlCmd.append(values);
     sqlCmd.append(")");
     if (log.isInfoEnabled()) {
       log.info("插入记录[" + sqlCmd + "]");
     }
     int ret = ctx.getDataBaseUtil().execUpdate(sqlCmd.toString());
     if (ret == 0) {
       return 1;
     }
     return 0;
   }
 
   public static int queryToETF(HiMessageContext ctx, String sqlcmd)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     List queryRs = ctx.getDataBaseUtil().execQuery(sqlcmd);
 
     if ((queryRs != null) && (queryRs.size() == 0)) {
       return -1;
     }
 
     Map queryRec = (HashMap)queryRs.get(0);
 
     Map.Entry recEntry = null;
     Iterator recIt = queryRec.entrySet().iterator();
 
     HiETF etfBody = mess.getETFBody();
 
     while (recIt.hasNext()) {
       recEntry = (Map.Entry)recIt.next();
       etfBody.setChildValue((String)recEntry.getKey(), (String)recEntry.getValue());
     }
 
     return 0;
   }
 
   public static void extInsertGroup(String tablename, String GroupName, boolean isIgnore, String FldLst, HiMessageContext ctx)
     throws HiException
   {
     extInsertGroupDeep("011", tablename, GroupName, isIgnore, FldLst, ctx);
   }
 
   private static void extInsertGroupDeep(String ExtKey, String tablename, String GroupName, boolean isIgnore, String FldLst, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     HiETF root = mess.getETFBody();
     Logger log = HiLog.getLogger(mess);
 
     String childTableName = null;
     String newExtKey = null;
 
     childTableName = HiDbtSqlHelper.getChildTableName(tablename, ctx);
 
     HashMap colsMap = ctx.getTableMetaData(tablename);
     if ((colsMap == null) || (colsMap.size() == 0)) {
       throw new HiException("220056", "取表结构失败：" + tablename);
     }
 
     Map refEtfFlds = null;
     if (FldLst != null) {
       refEtfFlds = HiArgUtils.getEtfFields(root, FldLst, "|");
     }
 
     HiETF groupNodeRec = null;
     List groupNodes = root.getGrandChildFuzzyEnd(GroupName + "_");
     Iterator grpIt = groupNodes.iterator();
 
     Map.Entry refEtf = null;
 
     StringBuffer sqlCmd = new StringBuffer();
     StringBuffer names = new StringBuffer();
     StringBuffer values = new StringBuffer();
     while (grpIt.hasNext()) {
       groupNodeRec = ((HiETF)grpIt.next()).cloneNode();
       sqlCmd.setLength(0);
       names.setLength(0);
       values.setLength(0);
       Iterator refEtfIt = refEtfFlds.entrySet().iterator();
 
       while (refEtfIt.hasNext()) {
         refEtf = (Map.Entry)refEtfIt.next();
         groupNodeRec.setChildValue((String)refEtf.getKey(), (String)refEtf.getValue());
       }
 
       String value = null;
 
       for (Iterator it = colsMap.entrySet().iterator(); it.hasNext(); ) {
         Map.Entry entry = (Map.Entry)it.next();
         String name = (String)entry.getKey();
         value = groupNodeRec.getChildValue(name);
 
         if (name.equalsIgnoreCase("EXTKEY"))
         {
           if (ExtKey.equals("011"))
             newExtKey = HiDbtSqlHelper.getExtKey(ctx);
           else {
             newExtKey = ExtKey;
           }
 
           if (sqlCmd.length() > 0) {
             names.append(",");
             names.append(name);
             values.append(",");
             values.append(newExtKey);
           }
           else {
             names.append(name);
             values.append(newExtKey);
           }
 
         }
         else if (name.equalsIgnoreCase("CTBLNM"))
         {
           if (names.length() > 0) {
             names.append(",");
             names.append(name);
             values.append(",'");
             values.append(childTableName);
             values.append("'");
           } else {
             names.append(name);
             values.append("'");
             values.append(childTableName);
             values.append("'");
           }
 
         }
         else if (value != null)
         {
           if (names.length() > 0) {
             names.append(",");
             names.append(name);
             values.append(",'");
             values.append(value);
             values.append("'");
           } else {
             names.append(name);
             values.append("'");
             values.append(value);
             values.append("'");
           }
         }
       }
 
       sqlCmd.append("INSERT INTO ");
       sqlCmd.append(tablename);
       sqlCmd.append(" (");
       sqlCmd.append(names);
       sqlCmd.append(") VALUES(");
       sqlCmd.append(values);
       sqlCmd.append(")");
 
       if (log.isInfoEnabled()) {
         log.info("插入记录[" + sqlCmd + "]");
       }
       try
       {
         ctx.getDataBaseUtil().execUpdate(sqlCmd.toString());
       }
       catch (HiException e) {
         if (!(isIgnore))
         {
           throw e;
         }
       }
       if ((StringUtils.isNotEmpty(childTableName)) && (StringUtils.isNotEmpty(newExtKey)))
       {
         extInsertGroupDeep(newExtKey, childTableName, GroupName, isIgnore, FldLst, ctx);
       }
 
     }
 
     groupNodes.clear();
   }
 
   public static void extInsertRecord(String tablename, HiMessageContext ctx)
     throws HiException
   {
     extInsertRecordDeep("011", tablename, ctx);
   }
 
   private static void extInsertRecordDeep(String ExtKey, String tablename, HiMessageContext ctx) throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     HiETF root = mess.getETFBody();
     Logger log = HiLog.getLogger(mess);
 
     String childTableName = null;
 
     childTableName = HiDbtSqlHelper.getChildTableName(tablename, ctx);
 
     if (ExtKey.equals("011")) {
       ExtKey = HiDbtSqlHelper.getExtKey(ctx);
     }
 
     HashMap colsMap = ctx.getTableMetaData(tablename);
     if ((colsMap == null) || (colsMap.size() == 0)) {
       throw new HiException("220056", "取表结构失败：" + tablename);
     }
 
     StringBuffer names = new StringBuffer();
     StringBuffer values = new StringBuffer();
     StringBuffer sqlCmd = new StringBuffer();
     for (Iterator it = colsMap.entrySet().iterator(); it.hasNext(); ) {
       Map.Entry entry = (Map.Entry)it.next();
       String name = (String)entry.getKey();
       String value = root.getChildValue(name);
 
       if (name.equalsIgnoreCase("EXTKEY"))
       {
         if (names.length() > 0) {
           names.append(",");
           names.append(name);
 
           values.append(",");
           values.append(ExtKey);
         }
         else {
           names.append(name);
 
           values.append(ExtKey);
         }
 
       }
       else if (name.equalsIgnoreCase("CTBLNM"))
       {
         if (names.length() > 0) {
           names.append(",");
           names.append(name);
 
           values.append(",'");
           values.append(childTableName);
           values.append("'");
         } else {
           names.append(name);
 
           values.append("'");
           values.append(childTableName);
           values.append("'");
         }
 
       }
       else if (value != null) {
         if (names.length() > 0) {
           names.append(",");
           names.append(name);
 
           values.append(",'");
           values.append(value);
           values.append("'");
         } else {
           names.append(name);
 
           values.append("'");
           values.append(value);
           values.append("'");
         }
       }
     }
 
     sqlCmd.append("INSERT INTO ");
     sqlCmd.append(tablename);
     sqlCmd.append(" (");
     sqlCmd.append(names);
     sqlCmd.append(") VALUES(");
     sqlCmd.append(values);
     sqlCmd.append(")");
 
     if (log.isInfoEnabled()) {
       log.info("插入记录[" + sqlCmd + "]");
     }
     ctx.getDataBaseUtil().execUpdate(sqlCmd.toString());
 
     if (!(StringUtils.isEmpty(childTableName)))
       extInsertRecordDeep(ExtKey, childTableName, ctx);
   }
 
   public static void extReadRecordCondition(String tablename, String CndSts, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     String sqlcmd = HiStringUtils.format("SELECT * FROM %s WHERE %s", tablename, CndSts);
 
     if (log.isInfoEnabled()) {
       log.info("查询语句:[" + sqlcmd + "]");
     }
     List queryRs = ctx.getDataBaseUtil().execQuery(sqlcmd);
 
     if ((queryRs != null) && (queryRs.size() == 0)) {
       throw new HiAppException(-100, "220040", "数据库无此记录");
     }
 
     Map queryRec = (HashMap)queryRs.get(0);
 
     Map.Entry recEntry = null;
     Iterator recIt = queryRec.entrySet().iterator();
 
     HiETF etfBody = mess.getETFBody();
     String childTable = null;
     String ExtKey = null;
 
     while (recIt.hasNext()) {
       recEntry = (Map.Entry)recIt.next();
       String colname = (String)recEntry.getKey();
       String colvalue = (String)recEntry.getValue();
       if (colname.equalsIgnoreCase("CTBLNM"))
         childTable = colvalue;
       else if (colname.equalsIgnoreCase("EXTKEY"))
         ExtKey = colvalue;
       else {
         etfBody.setChildValue(colname, colvalue);
       }
     }
 
     if ((!(StringUtils.isNotEmpty(childTable))) || (!(StringUtils.isNotEmpty(ExtKey))))
       return;
     CndSts = HiStringUtils.format("EXTKEY = %s ", ExtKey);
     extReadRecordCondition(childTable, CndSts, ctx);
   }
 
   public static void extUpdateRecordCondition(String tablename, String CndSts, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     HiETF etfBody = mess.getETFBody();
 
     String sqlQueryChild = HiStringUtils.format("SELECT CTBLNM, EXTKEY FROM %s WHERE %s", tablename, CndSts);
 
     List queryRs = ctx.getDataBaseUtil().execQuery(sqlQueryChild);
 
     if ((queryRs != null) && (queryRs.size() == 0)) {
       throw new HiAppException(-100, "220040", "数据库无此记录");
     }
 
     Map queryRec = (HashMap)queryRs.get(0);
     String childTable = (String)queryRec.get("CTBLNM");
     String ExtKey = (String)queryRec.get("EXTKEY");
 
     HashMap tblMeta = ctx.getTableMetaData(tablename);
 
     StringBuffer sqlSentence = new StringBuffer();
     sqlSentence.append("UPDATE ");
     sqlSentence.append(tablename);
     sqlSentence.append(" SET ");
 
     Iterator metaIt = tblMeta.entrySet().iterator();
 
     Map.Entry metaEntry = null;
     String colNam = null;
     HiETF colEtf = null;
     boolean first = true;
     while (metaIt.hasNext()) {
       metaEntry = (Map.Entry)metaIt.next();
 
       colNam = (String)metaEntry.getKey();
       if (colNam.equalsIgnoreCase("CTBLNM")) continue; if (colNam.equalsIgnoreCase("EXTKEY"))
       {
         continue;
       }
 
       colEtf = etfBody.getChildNode(colNam);
       if (colEtf == null)
       {
         continue;
       }
 
       if (!(first)) {
         sqlSentence.append(",");
       }
       sqlSentence.append(colNam);
       sqlSentence.append("='");
       sqlSentence.append(colEtf.getValue());
       sqlSentence.append("'");
       first = false;
     }
 
     sqlSentence.append(" WHERE ");
 
     sqlSentence.append(CndSts);
 
     if (log.isInfoEnabled()) {
       log.info("更新语句:[" + sqlSentence + "]");
     }
 
     ctx.getDataBaseUtil().execUpdate(sqlSentence.toString());
 
     if ((!(StringUtils.isNotEmpty(childTable))) || (!(StringUtils.isNotEmpty(ExtKey))))
       return;
     CndSts = HiStringUtils.format("EXTKEY = %s ", ExtKey);
     extUpdateRecord(childTable, CndSts, ctx);
   }
 
   private static void expUpdateRecord(String tablename, String CndSts, HiETF root, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     HashMap tblMeta = ctx.getTableMetaData(tablename);
 
     StringBuffer sqlSentence = new StringBuffer();
     sqlSentence.append("UPDATE ");
     sqlSentence.append(tablename);
     sqlSentence.append(" SET ");
     Iterator metaIt = tblMeta.entrySet().iterator();
 
     Map.Entry metaEntry = null;
     String colNam = null;
     HiETF colEtf = null;
     boolean first = true;
     while (metaIt.hasNext()) {
       metaEntry = (Map.Entry)metaIt.next();
 
       colNam = (String)metaEntry.getKey();
       if (colNam.equalsIgnoreCase("CTBLNM")) continue; if (colNam.equalsIgnoreCase("EXTKEY"))
       {
         continue;
       }
 
       colEtf = root.getChildNode(colNam);
       if (colEtf == null) {
         continue;
       }
       if (!(first))
         sqlSentence.append(",");
       sqlSentence.append(colNam);
       sqlSentence.append("='");
       sqlSentence.append(colEtf.getValue());
       sqlSentence.append("'");
       first = false;
     }
 
     sqlSentence.append(" WHERE ");
     sqlSentence.append(CndSts);
 
     if (log.isInfoEnabled()) {
       log.info("更新语句:[" + sqlSentence + "]");
     }
 
     ctx.getDataBaseUtil().execUpdate(sqlSentence.toString());
   }
 
   private static void extUpdateRecord(String tablename, String CndSts, HiMessageContext ctx) throws HiException
   {
     expUpdateRecord(tablename, CndSts, ctx.getCurrentMsg().getETFBody(), ctx);
   }
 
   public static void extUpdateGroupCondition(String tablename, String CndSts, String GroupName, boolean isIgnore, String FldLst, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     HiETF etfBody = mess.getETFBody();
     String childTable = null;
     String ExtKey = null;
 
     Map refEtfFlds = null;
     if (FldLst != null) {
       refEtfFlds = HiArgUtils.getEtfFields(etfBody, FldLst, "|");
     }
 
     HiETF groupNodeRec = null;
     List groupNodes = etfBody.getGrandChildFuzzyEnd(GroupName + "_");
     Iterator grpIt = groupNodes.iterator();
 
     Map.Entry refEtf = null;
 
     while (grpIt.hasNext()) {
       groupNodeRec = ((HiETF)grpIt.next()).cloneNode();
 
       Iterator refEtfIt = refEtfFlds.entrySet().iterator();
 
       while (refEtfIt.hasNext()) {
         refEtf = (Map.Entry)refEtfIt.next();
         groupNodeRec.setChildValue((String)refEtf.getKey(), (String)refEtf.getValue());
       }
 
       String sqlCondition = HiDbtSqlHelper.getDynSentence(ctx, CndSts);
 
       String sqlQueryChild = HiStringUtils.format("SELECT CTBLNM, EXTKEY FROM %s WHERE %s", tablename, sqlCondition);
 
       List queryRs = ctx.getDataBaseUtil().execQuery(sqlQueryChild);
 
       if ((queryRs != null) && (queryRs.size() == 0) && 
         (!(isIgnore))) {
         log.error("查询数据库无记录[" + sqlQueryChild + "]");
         throw new HiAppException(-100, "220040", "数据库无此记录");
       }
 
       Map queryRec = (HashMap)queryRs.get(0);
       childTable = (String)queryRec.get("CTBLNM");
       ExtKey = (String)queryRec.get("EXTKEY");
       try
       {
         HashMap tblMeta = ctx.getTableMetaData(tablename);
 
         StringBuffer sqlSentence = new StringBuffer();
         sqlSentence.append("UPDATE ");
         sqlSentence.append(tablename);
         sqlSentence.append(" SET ");
 
         Iterator metaIt = tblMeta.entrySet().iterator();
 
         Map.Entry metaEntry = null;
         String colNam = null;
         HiETF colEtf = null;
         boolean first = true;
         while (metaIt.hasNext()) {
           metaEntry = (Map.Entry)metaIt.next();
 
           colNam = (String)metaEntry.getKey();
           if (colNam.equalsIgnoreCase("CTBLNM")) continue; if (colNam.equalsIgnoreCase("EXTKEY"))
           {
             continue;
           }
 
           colEtf = groupNodeRec.getChildNode(colNam);
           if (colEtf == null) {
             continue;
           }
           if (!(first))
             sqlSentence.append(",");
           sqlSentence.append(colNam);
           sqlSentence.append("='");
           sqlSentence.append(colEtf.getValue());
           sqlSentence.append("'");
           first = false;
         }
 
         sqlSentence.append(" WHERE ");
         sqlSentence.append(sqlCondition);
         if (log.isInfoEnabled()) {
           log.info("更新语句:[" + sqlSentence + "]");
         }
 
         ctx.getDataBaseUtil().execUpdate(sqlSentence.toString());
 
         if ((StringUtils.isNotEmpty(childTable)) && (StringUtils.isNotEmpty(ExtKey)))
         {
           CndSts = HiStringUtils.format("EXTKEY = %s ", ExtKey);
           expUpdateRecord(tablename, CndSts, groupNodeRec, ctx);
         }
       }
       catch (HiException e)
       {
         if (!(isIgnore)) {
           log.error("extUpdateGroupCondition执行有误: 至少有一条执行更新失败");
           throw e;
         }
       }
     }
     groupNodes.clear();
   }
 
   public static int dbtsqlupdrec(HiETF etf, String strTableName, String strKeyName, String strKeyValue, int keyType, HiMessageContext ctx)
     throws HiException
   {
     String strCondition = strKeyName + " = ";
     if (keyType == 0)
       strCondition = strCondition + "'" + strKeyValue + "'";
     else {
       strCondition = strCondition + strKeyValue;
     }
     String strSQL = updateFormat(strTableName, strCondition, etf, ctx);
 
     ctx.getDataBaseUtil().execUpdate(strSQL);
 
     return 0;
   }
 
   public static int sqngetbatno(HiMessageContext ctx, int num)
     throws HiException
   {
     int logNo;
     try
     {
       String sqlCmd = "UPDATE PUBPLTINF SET BLOG_NO = BLOG_NO";
       ctx.getDataBaseUtil().execUpdate(sqlCmd);
 
       sqlCmd = "SELECT BLOG_NO FROM PUBPLTINF";
       HiResultSet rs = ctx.getDataBaseUtil().execQuerySQL(sqlCmd);
 
       if (rs.size() == 0) {
         throw new HiException("220040", sqlCmd);
       }
 
       logNo = rs.getInt(0, 0);
 
       int nextLogNo = logNo + num;
 
       if (nextLogNo < logNo) {
         throw new HiException("220062", "PUBPLTINF");
       }
 
       String value = StringUtils.leftPad(String.valueOf(nextLogNo), 12, "0");
 
       sqlCmd = "UPDATE PUBPLTINF SET BLOG_NO='" + value + "'";
       ctx.getDataBaseUtil().execUpdate(sqlCmd);
     } finally {
       ctx.getDataBaseUtil().commit();
     }
     return logNo;
   }
 
   public static String bpbgetbatno(HiMessageContext ctx)
     throws HiException
   {
     String strActDat = ctx.getStrProp("ACC_DT");
     String actdat = StringUtils.substring(strActDat, 2, 8);
     String blog = StringUtils.leftPad(String.valueOf(sqngetbatno(ctx, 1)), 6, '0');
 
     return actdat + blog;
   }
 
   public static String sqnGetDumTlr(HiMessageContext ctx, String brNo, String txnCnl, String cnlSub)
     throws HiException
   {
     String sqlCmd = "SELECT DUM_TLR FROM PUBDUMTLR WHERE BR_NO = '%s' AND TXN_CNL = '%s' AND CNL_SUB = '%s'";
     sqlCmd = HiStringUtils.format(sqlCmd, brNo, txnCnl, cnlSub);
     synchronized (_virtual_teller_lock) {
       if (dumtlrs.size() == 0) {
         dumtlrs = ctx.getDataBaseUtil().execQuery(sqlCmd);
         if (dumtlrs.size() == 0) {
           throw new HiException("220040", "PUBDUMTLR");
         }
       }
     }
 
     HashMap dumtlr = (HashMap)dumtlrs.get(random.nextInt(dumtlrs.size()));
     return ((String)dumtlr.get("DUM_TLR"));
   }
 
   public static int insertRecord(String strTableName, Object o, HiMessageContext ctx)
     throws HiException
   {
     Map tableInfo = ctx.getTableMetaData(strTableName);
 
     StringBuffer sqlCmd = new StringBuffer();
     StringBuffer names = new StringBuffer();
     StringBuffer values = new StringBuffer();
 
     int index = 0;
     Field[] fields = o.getClass().getDeclaredFields();
     for (int i = 0; i < fields.length; ++i)
     {
       String value;
       String strColName = fields[i].getName().toUpperCase();
       if (!(tableInfo.containsKey(strColName)))
         continue;
       try
       {
         value = (String)fields[i].get(o);
       } catch (Exception e) {
         throw HiException.makeException(e);
       }
 
       if (value == null) {
         continue;
       }
       if (index > 0) {
         names.append(",");
         values.append(",");
       }
 
       names.append(strColName);
 
       values.append("'");
       values.append(HiDbtSqlHelper.sqlEscape(value));
       values.append("'");
 
       ++index;
     }
 
     sqlCmd.append("INSERT INTO ");
     sqlCmd.append(strTableName);
     sqlCmd.append(" (");
     sqlCmd.append(names);
     sqlCmd.append(")VALUES(");
     sqlCmd.append(values);
     sqlCmd.append(")");
     ctx.getDataBaseUtil().execUpdate(sqlCmd.toString());
     return 0;
   }
 
   public static int appLock(String recKey, String timeOut, HiMessageContext ctx)
     throws HiException
   {
     int ret;
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     long curLockTime = System.currentTimeMillis();
     String lockTimeStr = StringUtils.substring(String.valueOf(curLockTime), 0, 10);
 
     curLockTime = Long.parseLong(lockTimeStr);
 
     recKey = recKey.trim();
 
     String sqlStr = "update publckrec set upd_flg='1' where rec_key='" + recKey + "'";
 
     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
     try
     {
       ret = dbUtil.execUpdate(sqlStr);
 
       if (ret == 0) {
         sqlStr = "insert into publckrec values('" + recKey + "','" + lockTimeStr + "','" + timeOut + "','1')";
 
         dbUtil.execUpdate(sqlStr);
         if (log.isInfoEnabled())
           log.info("AppLock: 登记锁成功. aRecKey=[" + recKey + "] aLckTim=[" + curLockTime + "] aTimOut=[" + timeOut + "]");
       }
       else
       {
         sqlStr = "select lck_tm, tim_out from publckrec where rec_key='" + recKey + "'";
 
         List queryRs = dbUtil.execQuery(sqlStr);
         Map queryRec = (HashMap)queryRs.get(0);
 
         String oldLckTimStr = (String)queryRec.get("LCK_TM");
         String oldTimOutStr = (String)queryRec.get("TIM_OUT");
         long oldLckTim = Long.parseLong(oldLckTimStr.trim());
         long oldTimOut = Long.parseLong(oldTimOutStr.trim());
 
         if ((curLockTime - oldLckTim < oldTimOut) || (oldTimOut == 0L))
         {
           log.error("AppLock: 加锁失败. aRecKey=[" + recKey + "] aLckTim=[" + curLockTime + "] aOldLckTim=[" + oldLckTimStr + "] aOldTimOut=[" + oldTimOutStr + "]");
 
           int i = 2;
           return i;
         }
         sqlStr = "update publckrec set lck_tm='" + lockTimeStr + "', tim_out='" + timeOut + "' where rec_key='" + recKey + "'";
 
         dbUtil.execUpdate(sqlStr);
 
         if (log.isInfoEnabled()) {
           log.info("AppLock: 加锁成功. aRecKey=[" + recKey + "] aLckTim=[" + curLockTime + "] aOldLckTim=[" + oldLckTimStr + "] aOldTimOut=[" + oldTimOutStr + "]");
         }
 
         ret = 0; }
     } finally {
       dbUtil.commit();
     }
     return ret;
   }
 
   public static int unAppLock(String recKey, HiMessageContext ctx)
     throws HiException
   {
     int ret = 0;
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
 
     recKey = recKey.trim();
     String sqlStr = "delete from publckrec where rec_key='" + recKey + "'";
 
     ret = ctx.getDataBaseUtil().execUpdate(sqlStr);
 
     if (ret == 0) {
       if (log.isInfoEnabled()) {
         log.info("AppUnlock: 没有加锁或已经被解锁! sRecKey=[" + recKey + "]");
       }
       ret = 1;
     } else {
       ctx.getDataBaseUtil().commit();
     }
 
     return 0;
   }
 
   public static int tryLock(String recKey, HiMessageContext ctx)
     throws HiException
   {
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
 
     recKey = recKey.trim();
     String sqlStr = "select lck_tm, tim_out from publckrec where rec_key='" + recKey + "'";
 
     List queryRs = ctx.getDataBaseUtil().execQuery(sqlStr);
 
     if (queryRs.size() == 0) {
       if (log.isInfoEnabled()) {
         log.info("AppTrylock: 没有加锁或已经被解锁! sRecKey=[" + recKey + "]");
       }
 
       return 0;
     }
 
     long curLockTime = System.currentTimeMillis();
     String lockTimeStr = StringUtils.substring(String.valueOf(curLockTime), 0, 10);
 
     curLockTime = Long.parseLong(lockTimeStr);
 
     Map queryRec = (HashMap)queryRs.get(0);
 
     String oldLckTimStr = (String)queryRec.get("LCK_TM");
     String oldTimOutStr = (String)queryRec.get("TIM_OUT");
     long oldLckTim = Long.parseLong(oldLckTimStr.trim());
     long oldTimOut = Long.parseLong(oldTimOutStr.trim());
 
     if (oldLckTim + oldTimOut > curLockTime) {
       if (log.isInfoEnabled()) {
         log.info("AppTrylock: 加锁中! sRecKey=[" + recKey + "]");
       }
 
       return 1;
     }
 
     if (log.isInfoEnabled()) {
       log.info("AppTrylock: 锁已经超时失效! sRecKey=[" + recKey + "]");
     }
     return 0;
   }
 
   public static void setBeanPropertyFromETF(Object o, HiETF root) throws HiException
   {
     Field[] fields = o.getClass().getDeclaredFields();
     for (int i = 0; i < fields.length; ++i)
       try {
         fields[i].setAccessible(true);
         fields[i].set(o, root.getChildValue(fields[i].getName()));
       } catch (IllegalAccessException e) {
         throw HiException.makeException(e);
       }
   }
 
   public static void setBeanPropertyFromMap(Object o, Map map)
     throws HiException
   {
     Field[] fields = o.getClass().getDeclaredFields();
     for (int i = 0; i < fields.length; ++i)
       try {
         fields[i].setAccessible(true);
         fields[i].set(o, map.get(fields[i].getName().toUpperCase()));
       } catch (IllegalAccessException e) {
         throw HiException.makeException(e);
       }
   }
 
   public static void setETFFromBeanProperty(Object o, HiETF root)
     throws HiException
   {
     Field[] fields = o.getClass().getDeclaredFields();
     for (int i = 0; i < fields.length; ++i)
       try {
         if ((fields[i].get(o) instanceof String) && (fields[i].get(o) != null))
         {
           root.setChildValue(fields[i].getName(), (String)fields[i].get(o));
         }
       } catch (IllegalAccessException e) {
         throw HiException.makeException(e);
       }
   }
 
   public static int rdoTranRegister(HiRdoJnl sRdr) throws HiException
   {
     return 0;
   }
 
   public static int sqnGetLogNo(HiMessageContext ctx, int num)
     throws HiException
   {
     synchronized (_sqn_get_logno_lock)
     {
       int logNo;
       if (num <= 0) {
         num = 1;
       }
 
       if (_currLogNo + num <= _limitLogNo) {
         logNo = _currLogNo;
         _currLogNo += num;
         return logNo;
       }
 
       try
       {
         String sqlCmd = "UPDATE PUBPLTINF SET LOG_NO = LOG_NO";
         ctx.getDataBaseUtil().execUpdate(sqlCmd);
 
         sqlCmd = "SELECT LOG_NO FROM PUBPLTINF";
         HiResultSet rs = ctx.getDataBaseUtil().execQuerySQL(sqlCmd);
 
         if (rs.size() == 0) {
           throw new HiException("220040", sqlCmd);
         }
 
         logNo = rs.getInt(0, 0);
 
         int nextLogNo = logNo + _scale;
         if (_currLogNo == 0)
           _currLogNo = logNo;
         else {
           logNo = _currLogNo;
         }
         _limitLogNo = nextLogNo;
         _currLogNo += num;
 
         if (nextLogNo % 100000000 == 0) {
           throw new HiException("220062", "PUBPLTINF");
         }
 
         String value = StringUtils.leftPad(String.valueOf(nextLogNo), 14, "0");
 
         sqlCmd = "UPDATE PUBPLTINF SET LOG_NO='" + value + "'";
         ctx.getDataBaseUtil().execUpdate(sqlCmd);
       } finally {
         ctx.getDataBaseUtil().commit();
       }
       return logNo;
     }
   }
 
   public static String updateFormat(String tableName, String selectSql, HiETF etfRoot, HiMessageContext ctx)
     throws HiException
   {
     if ((StringUtils.isEmpty(tableName)) || (StringUtils.isEmpty(selectSql)) || (etfRoot == null))
     {
       throw new HiException("220026", "前置系统错误");
     }
     StringBuffer sqlcmd = new StringBuffer();
 
     sqlcmd.append("UPDATE " + tableName + " SET ");
     HashMap colInfo = null;
     try {
       colInfo = ctx.getTableMetaData(tableName);
     } catch (HiException e) {
       throw e;
     }
     Iterator iter = colInfo.keySet().iterator();
     String val = null;
     String col = null;
     while (iter.hasNext()) {
       col = (String)iter.next();
       val = etfRoot.getChildValue(col);
       if (val != null);
       sqlcmd.append(col);
       sqlcmd.append("='");
       sqlcmd.append(val);
       sqlcmd.append("',");
     }
 
     sqlcmd.deleteCharAt(sqlcmd.length() - 1);
     sqlcmd.append(" WHERE ");
     sqlcmd.append(selectSql);
 
     return sqlcmd.toString();
   }
 
   public static HiMessage buildInParams(String param, HiMessage inMsg, HiMessage outMsg)
   {
     if (param == null) {
       HiETF root = HiETFFactory.createETF();
       inMsg.getETFBody().copyTo(root);
       outMsg.setBody(root);
       return outMsg;
     }
     if (StringUtils.isBlank(param)) {
       outMsg.setBody(HiETFFactory.createETF());
       return outMsg;
     }
     HiETF inRoot = inMsg.getETFBody();
     HiETF outRoot = HiETFFactory.createETF();
     outMsg.setBody(outRoot);
     String[] tmps = param.split("\\|");
     for (int i = 0; i < tmps.length; ++i) {
       if (StringUtils.isBlank(tmps[i])) {
         continue;
       }
       int idx = StringUtils.indexOf(tmps[i], "[]");
       if (idx == -1)
       {
         HiETF node1 = inRoot.getChildNode(tmps[i]);
         if (node1 == null) {
           continue;
         }
         HiETF node2 = outRoot.getChildNode(tmps[i]);
         if (node2 != null) {
           outRoot.removeChildNode(node2);
         }
         outRoot.appendNode(node1.cloneNode());
       }
       else {
         String groupName = StringUtils.substring(tmps[i], 0, idx);
         String tmp = inRoot.getChildValue(groupName + "_NUM");
         int groupNum = -1;
         if ((StringUtils.isNotEmpty(tmp)) && (StringUtils.isNumeric(tmp))) {
           groupNum = NumberUtils.toInt(tmp);
           outRoot.setChildValue(groupName + "_NUM", tmp);
         }
         for (int j = 1; ; ++j) {
           HiETF node1 = inRoot.getChildNode(groupName + "_" + j);
           if (((groupNum != -1) && (j > groupNum)) || (node1 == null)) break; if (node1.isEndNode()) {
             break;
           }
           HiETF node2 = outRoot.getChildNode(groupName + "_" + j);
           if (node2 != null) {
             outRoot.removeChildNode(node2);
           }
           outRoot.appendNode(node1.cloneNode());
         }
       }
     }
     outMsg.setBody(outRoot);
     return outMsg;
   }
 
   public static HiETF buildOutParams(String param, HiETF inRoot, HiETF outRoot, boolean overFlag) throws HiException
   {
     if (param == null) {
       if (overFlag) {
         if (outRoot.combine(inRoot, true)) break label58;
         throw new HiException("220059", "合并ETF树失败");
       }
 
       if (!(outRoot.combine(inRoot, false))) {
         throw new HiException("220059", "合并ETF树失败");
       }
 
       label58: return outRoot;
     }
 
     if (StringUtils.isBlank(param)) {
       return outRoot;
     }
 
     String[] tmps = param.split("\\|");
     for (int i = 0; i < tmps.length; ++i) {
       if (StringUtils.isBlank(tmps[i])) {
         continue;
       }
       int idx = StringUtils.indexOf(tmps[i], "[]");
       if (idx == -1)
       {
         HiETF node1 = inRoot.getChildNode(tmps[i]);
         if (node1 == null) {
           continue;
         }
         HiETF node2 = outRoot.getChildNode(tmps[i]);
         if ((node2 != null) && (overFlag)) {
           outRoot.removeChildNode(node2);
         }
         outRoot.appendNode(node1.cloneNode());
       }
       else {
         String groupName = StringUtils.substring(tmps[i], 0, idx);
         String tmp = inRoot.getChildValue(groupName + "_NUM");
         int groupNum = -1;
         if ((StringUtils.isNotEmpty(tmp)) && (StringUtils.isNumeric(tmp))) {
           groupNum = NumberUtils.toInt(tmp);
           outRoot.setChildValue(groupName + "_NUM", tmp);
         }
         for (int j = 1; ; ++j) {
           HiETF node1 = inRoot.getChildNode(groupName + "_" + j);
           if (((groupNum != -1) && (j > groupNum)) || (node1 == null)) break; if (node1.isEndNode()) {
             break;
           }
           HiETF node2 = outRoot.getChildNode(groupName + "_" + j);
           if ((node2 != null) && (overFlag)) {
             outRoot.removeChildNode(node2);
           }
           outRoot.appendNode(node1.cloneNode());
         }
       }
     }
     return outRoot;
   }
 }