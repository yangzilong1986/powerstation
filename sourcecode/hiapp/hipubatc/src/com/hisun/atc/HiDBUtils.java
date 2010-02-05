 package com.hisun.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.atc.common.HiAtcLib;
 import com.hisun.atc.common.HiDBCursor;
 import com.hisun.atc.common.HiDbtSqlHelper;
 import com.hisun.atc.common.HiDbtUtils;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiDBUtils
 {
   private static HiStringManager sm = HiStringManager.getManager();
 
   public int ExecSqlBind(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String sqlArg;
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("开始执行原子组件: ExecSql ");
     }
     if ((args == null) || (args.size() == 0)) {
       throw new HiException("215110");
     }
 
     String sqlSentence = null;
     int ret = 0;
 
     HiETF etfBody = (HiETF)msg.getBody();
 
     String groupName = args.get("GrpNam");
 
     if (StringUtils.isBlank(groupName))
     {
       sqlArg = HiArgUtils.getFirstNotNull(args);
       if (log.isDebugEnabled()) {
         log.debug("ExecSql 参数:[" + sqlArg + "]");
       }
       sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + sqlArg);
 
       String sqlFldLst = HiArgUtils.getString(ctx, "FIELDS." + sqlArg);
 
       List etfFields = HiArgUtils.getFldValues(etfBody, sqlFldLst, "|");
 
       if (log.isDebugEnabled()) {
         log.debug("ExecSql执行SQL语句: [" + sqlSentence + "]");
       }
 
       if (ctx.getDataBaseUtil().execUpdateBind(sqlSentence, etfFields) == 0)
       {
         return 2;
       }
     }
     else
     {
       sqlArg = HiArgUtils.getStringNotNull(args, "SqlCmd").toUpperCase();
       if (log.isDebugEnabled()) {
         log.debug("ExecSql 参数:[" + sqlArg + "]");
       }
 
       boolean isIgnore = args.getBoolean("Ignore");
 
       String rootFldLst = args.get("FldLst");
 
       Map refEtfFlds = null;
       if (rootFldLst != null) {
         refEtfFlds = HiArgUtils.getEtfFields(etfBody, rootFldLst, "|");
       }
 
       HiETF groupNodeRec = null;
       List groupNodes = etfBody.getGrandChildFuzzyEnd(groupName + "_");
       Iterator grpIt = groupNodes.iterator();
 
       Map.Entry refEtf = null;
 
       while (grpIt.hasNext()) {
         HiETF tmpNod = (HiETF)grpIt.next();
         if (tmpNod.isEndNode()) {
           continue;
         }
         groupNodeRec = tmpNod.cloneNode();
 
         if (refEtfFlds != null) {
           Iterator refEtfIt = refEtfFlds.entrySet().iterator();
 
           while (refEtfIt.hasNext()) {
             refEtf = (Map.Entry)refEtfIt.next();
             groupNodeRec.setChildValue((String)refEtf.getKey(), (String)refEtf.getValue());
           }
         }
 
         try
         {
           sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + sqlArg);
 
           String sqlFldLst = HiArgUtils.getString(ctx, "FIELDS." + sqlArg);
 
           List etfFields = HiArgUtils.getFldValues(etfBody, sqlFldLst, "|");
 
           if (log.isDebugEnabled()) {
             log.debug("从Group取数据, 执行SQL语句: [" + sqlSentence + "]");
           }
           if (ctx.getDataBaseUtil().execUpdateBind(sqlSentence, etfFields) == 0) {
             if (log.isInfoEnabled()) {
               log.info(sm.getString("215021", sqlSentence));
             }
 
             ret = 2;
             if (!(isIgnore))
               return ret;
           }
         }
         catch (HiException e) {
           if (log.isInfoEnabled()) {
             log.info(sm.getString("215021", sqlSentence));
           }
 
           ret = 2;
           if (!(isIgnore)) {
             throw e;
           }
         }
       }
 
       groupNodes.clear();
     }
 
     return ret;
   }
 
   public int ExecSql(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String sqlArg;
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("开始执行原子组件: ExecSql ");
     }
     if ((args == null) || (args.size() == 0)) {
       throw new HiException("215110");
     }
 
     String sqlSentence = null;
     int ret = 0;
 
     HiETF etfBody = (HiETF)msg.getBody();
 
     boolean escape = args.getBoolean("escape");
     String groupName = args.get("GrpNam");
 
     if (StringUtils.isBlank(groupName))
     {
       sqlArg = HiArgUtils.getFirstNotNull(args);
       if (log.isDebugEnabled()) {
         log.debug("ExecSql 参数:[" + sqlArg + "]");
       }
       String clobName = args.get("clobs");
       if (StringUtils.isBlank(clobName)) {
         sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, sqlArg, etfBody, escape);
 
         if (log.isDebugEnabled()) {
           log.debug("ExecSql执行SQL语句: [" + sqlSentence + "]");
         }
 
         if (ctx.getDataBaseUtil().execUpdate(sqlSentence) != 0) {
           break label306;
         }
 
         return 2;
       }
 
       String[] clobNames = clobName.split("\\|");
       ArrayList params = new ArrayList();
       sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, sqlArg, etfBody, clobNames, params);
 
       if (log.isInfoEnabled()) {
         log.info("ExecSql [" + sqlSentence + "][" + params + "]");
       }
       label306: if (ctx.getDataBaseUtil().execUpdate(sqlSentence, params) == 0) {
         return 2;
       }
 
     }
     else
     {
       sqlArg = HiArgUtils.getStringNotNull(args, "SqlCmd").toUpperCase();
       if (log.isDebugEnabled()) {
         log.debug("ExecSql 参数:[" + sqlArg + "]");
       }
 
       boolean isIgnore = args.getBoolean("Ignore");
 
       String rootFldLst = args.get("FldLst");
 
       Map refEtfFlds = null;
       if (rootFldLst != null) {
         refEtfFlds = HiArgUtils.getEtfFields(etfBody, rootFldLst, "|");
       }
 
       HiETF groupNodeRec = null;
       List groupNodes = etfBody.getGrandChildFuzzyEnd(groupName + "_");
       Iterator grpIt = groupNodes.iterator();
 
       Map.Entry refEtf = null;
 
       while (grpIt.hasNext()) {
         HiETF tmpNod = (HiETF)grpIt.next();
         if (tmpNod.isEndNode()) {
           continue;
         }
         groupNodeRec = tmpNod.cloneNode();
 
         if (refEtfFlds != null) {
           Iterator refEtfIt = refEtfFlds.entrySet().iterator();
 
           while (refEtfIt.hasNext()) {
             refEtf = (Map.Entry)refEtfIt.next();
             groupNodeRec.setChildValue((String)refEtf.getKey(), (String)refEtf.getValue());
           }
         }
         try
         {
           sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, sqlArg, groupNodeRec, escape);
 
           if (log.isDebugEnabled()) {
             log.debug("从Group取数据, 执行SQL语句: [" + sqlSentence + "]");
           }
           if (ctx.getDataBaseUtil().execUpdate(sqlSentence) == 0) {
             if (log.isInfoEnabled()) {
               log.info(sm.getString("215021", sqlSentence));
             }
 
             ret = 2;
             if (!(isIgnore))
               return ret;
           }
         }
         catch (HiException e)
         {
           if (log.isInfoEnabled()) {
             log.info(sm.getString("215021", sqlSentence));
           }
 
           ret = 2;
           if (!(isIgnore)) {
             throw e;
           }
         }
       }
 
       groupNodes.clear();
     }
 
     return ret;
   }
 
   public int InsertRecord(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String sqlTblRef;
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("开始执行原子组件: InsertRecord");
     }
     if ((args == null) || (args.size() == 0)) {
       throw new HiException("215110");
     }
 
     int ret = 0;
 
     HiETF etfBody = (HiETF)msg.getBody();
 
     if (args.size() == 1)
     {
       sqlTblRef = HiArgUtils.getFirstNotNull(args).toUpperCase();
       if (log.isDebugEnabled()) {
         log.debug("InsertRecord: 表名[" + sqlTblRef + "]");
       }
 
       ret = HiDbtUtils.dbtsqlinsrec(sqlTblRef, etfBody, ctx);
     }
     else
     {
       sqlTblRef = HiArgUtils.getStringNotNull(args, "TblNam").toUpperCase();
 
       if (log.isDebugEnabled()) {
         log.debug("InsertRecord: 表名[" + sqlTblRef + "]");
       }
 
       String groupName = HiArgUtils.getStringNotNull(args, "GrpNam");
 
       boolean isIgnore = args.getBoolean("Ignore");
 
       String rootFldLst = args.get("FldLst");
 
       Map refEtfFlds = null;
       if (rootFldLst != null) {
         refEtfFlds = HiArgUtils.getEtfFields(etfBody, rootFldLst, "|");
       }
 
       HiETF groupNodeRec = null;
 
       Map.Entry refEtf = null;
 
       int num = NumberUtils.toInt(etfBody.getChildValue(groupName + "_NUM"));
 
       if (log.isDebugEnabled()) {
         log.debug("GRP NUM:[" + num + "]");
       }
       for (int i = 1; ; ++i) {
         if (log.isDebugEnabled()) {
           log.debug("LOOP NUM:[" + i + "]");
         }
         if ((num != 0) && (i > num)) {
           break;
         }
 
         groupNodeRec = etfBody.getChildNode(groupName + "_" + i);
         if (groupNodeRec == null) break; if (groupNodeRec.isEndNode()) {
           break;
         }
         groupNodeRec = groupNodeRec.cloneNode();
 
         if (refEtfFlds != null) {
           Iterator refEtfIt = refEtfFlds.entrySet().iterator();
 
           while (refEtfIt.hasNext()) {
             refEtf = (Map.Entry)refEtfIt.next();
             groupNodeRec.setChildValue((String)refEtf.getKey(), (String)refEtf.getValue());
           }
         }
 
         ArrayList params = new ArrayList();
 
         String sqlSentence = HiDbtSqlHelper.buildInsertSentence(ctx, groupNodeRec, sqlTblRef, params);
 
         if (log.isDebugEnabled()) {
           log.debug("从Group上取数据, 执行Insert语句: [" + sqlSentence + "][" + params + "]");
         }
         try
         {
           if (ctx.getDataBaseUtil().execUpdate(sqlSentence, params) == 0) {
             if (log.isInfoEnabled()) {
               log.info(sm.getString("215021", sqlSentence));
             }
 
             ret = 2;
             if (!(isIgnore))
               return ret;
           }
         }
         catch (HiException e) {
           if (log.isInfoEnabled()) {
             log.info(sm.getString("215021", sqlSentence, e));
           }
 
           ret = 2;
 
           if (!(isIgnore)) {
             throw e;
           }
         }
       }
     }
     return ret;
   }
 
   public int insertRecordExt(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String sqlTblRef;
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("开始执行原子组件: InsertRecordExt");
     }
     if ((args == null) || (args.size() == 0)) {
       throw new HiException("215110");
     }
 
     int ret = 0;
 
     HiETF etfBody = (HiETF)msg.getBody();
 
     if (args.size() == 1)
     {
       sqlTblRef = HiArgUtils.getFirstNotNull(args).toUpperCase();
       if (log.isDebugEnabled()) {
         log.debug("InsertRecord: 表名[" + sqlTblRef + "]");
       }
 
       ret = HiDbtUtils.dbtextinsrec(sqlTblRef, etfBody, ctx);
     }
     else
     {
       sqlTblRef = HiArgUtils.getStringNotNull(args, "TblNam").toUpperCase();
 
       if (log.isDebugEnabled()) {
         log.debug("InsertRecord: 表名[" + sqlTblRef + "]");
       }
 
       String groupName = HiArgUtils.getStringNotNull(args, "GrpNam");
 
       boolean isIgnore = args.getBoolean("Ignore");
 
       String rootFldLst = args.get("FldLst");
 
       Map refEtfFlds = null;
       if (rootFldLst != null) {
         refEtfFlds = HiArgUtils.getEtfFields(etfBody, rootFldLst, "|");
       }
 
       HiETF groupNodeRec = null;
       List groupNodes = etfBody.getGrandChildFuzzyEnd(groupName + "_");
       Iterator grpIt = groupNodes.iterator();
 
       Map.Entry refEtf = null;
 
       while (grpIt.hasNext()) {
         groupNodeRec = ((HiETF)grpIt.next()).cloneNode();
 
         if (refEtfFlds != null) {
           Iterator refEtfIt = refEtfFlds.entrySet().iterator();
 
           while (refEtfIt.hasNext()) {
             refEtf = (Map.Entry)refEtfIt.next();
             groupNodeRec.setChildValue((String)refEtf.getKey(), (String)refEtf.getValue());
           }
         }
         try
         {
           if (HiDbtUtils.dbtextinsrec(sqlTblRef, groupNodeRec, ctx) == 1) {
             if (log.isInfoEnabled()) {
               log.info(sm.getString("215021", sqlTblRef));
             }
 
             ret = 2;
 
             if (!(isIgnore))
               return ret;
           }
         }
         catch (HiException e) {
           if (log.isInfoEnabled()) {
             log.info(sm.getString("215021", sqlTblRef));
           }
 
           ret = 2;
 
           if (!(isIgnore)) {
             throw e;
           }
         }
       }
 
       groupNodes.clear();
     }
     return ret;
   }
 
   public int ReadRecordBind(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("开始执行原子组件: readRecord");
     }
     if ((args == null) || (args.size() == 0)) {
       throw new HiException("215110");
     }
 
     String sqlCmd = (String)args.values().iterator().next();
 
     HiETF etfBody = (HiETF)msg.getBody();
     String sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + sqlCmd);
 
     String sqlFldLst = HiArgUtils.getString(ctx, "FIELDS." + sqlCmd);
 
     List etfFields = HiArgUtils.getFldValues(etfBody, sqlFldLst, "|");
 
     List queryRs = ctx.getDataBaseUtil().execQueryBind(sqlSentence, etfFields);
 
     if ((queryRs != null) && (queryRs.size() == 0)) {
       return 2;
     }
 
     Map queryRec = (HashMap)queryRs.get(0);
 
     Map.Entry recEntry = null;
     Iterator recIt = queryRec.entrySet().iterator();
     while (recIt.hasNext()) {
       recEntry = (Map.Entry)recIt.next();
       etfBody.setChildValue((String)recEntry.getKey(), (String)recEntry.getValue());
     }
 
     return 0;
   }
 
   public int readRecord(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("开始执行原子组件: readRecord");
     }
     if ((args == null) || (args.size() == 0)) {
       throw new HiException("215110");
     }
 
     String sqlCmd = (String)args.values().iterator().next();
 
     HiETF etfBody = (HiETF)msg.getBody();
     boolean escape = args.getBoolean("escape");
     String sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, sqlCmd, etfBody, escape);
 
     if (HiAtcLib.queryToETF(ctx, sqlSentence) == -1)
     {
       return 2;
     }
 
     return 0;
   }
 
   public int updateRecord(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String sqlTblRef;
     String sqlCndSts;
     String sqlSentence;
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("开始执行原子组件: updateRecord");
     }
     if ((args == null) || (args.size() < 2)) {
       throw new HiException("215110");
     }
 
     int ret = 0;
 
     HiETF etfBody = (HiETF)msg.getBody();
     boolean escape = args.getBoolean("escape");
     String groupName = args.get("GrpNam");
 
     if (StringUtils.isBlank(groupName))
     {
       sqlTblRef = HiArgUtils.getStringNotNull(args, "TblNam").toUpperCase();
 
       sqlCndSts = HiArgUtils.getStringNotNull(args, "CndSts").toUpperCase();
 
       if (log.isDebugEnabled()) {
         log.debug("updateRecord: 表名[" + sqlTblRef + "] 执行语句别名[" + sqlCndSts + "]");
       }
 
       sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, sqlCndSts, etfBody, escape);
 
       ArrayList list = new ArrayList();
 
       sqlSentence = HiDbtSqlHelper.buildUpdateSentence(ctx, etfBody, sqlTblRef, sqlSentence, false, list);
 
       if (log.isInfoEnabled()) {
         log.info("updateRecord执行语句: [" + sqlSentence + "]:[" + list + "]");
       }
 
       if (ctx.getDataBaseUtil().execUpdate(sqlSentence, list) == 0)
       {
         ret = 2;
       }
     }
     else
     {
       sqlTblRef = HiArgUtils.getStringNotNull(args, "TblNam").toUpperCase();
 
       sqlCndSts = HiArgUtils.getStringNotNull(args, "CndSts").toUpperCase();
 
       if (log.isDebugEnabled()) {
         log.debug("updateRecord: 表名[" + sqlTblRef + "] 执行语句别名[" + sqlCndSts + "]");
       }
 
       sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + sqlCndSts);
 
       String sqlFldLst = HiArgUtils.getString(ctx, "FIELDS." + sqlCndSts);
 
       boolean isIgnore = args.getBoolean("Ignore");
 
       String rootFldLst = args.get("FldLst");
 
       Map refEtfFlds = null;
       if (rootFldLst != null) {
         refEtfFlds = HiArgUtils.getEtfFields(etfBody, rootFldLst, "|");
       }
 
       HiETF groupNodeRec = null;
       List groupNodes = etfBody.getGrandChildFuzzyEnd(groupName + "_");
       Iterator grpIt = groupNodes.iterator();
 
       Map.Entry refEtf = null;
 
       while (grpIt.hasNext()) {
         groupNodeRec = ((HiETF)grpIt.next()).cloneNode();
 
         if (refEtfFlds != null) {
           Iterator refEtfIt = refEtfFlds.entrySet().iterator();
 
           while (refEtfIt.hasNext()) {
             refEtf = (Map.Entry)refEtfIt.next();
             groupNodeRec.setChildValue((String)refEtf.getKey(), (String)refEtf.getValue());
           }
 
         }
 
         String sqlCmd = HiDbtSqlHelper.replace(ctx, groupNodeRec, sqlSentence, sqlFldLst, "|");
 
         sqlCmd = HiDbtSqlHelper.buildUpdateSentence(ctx, groupNodeRec, sqlTblRef, sqlCmd, false);
         try
         {
           if (log.isDebugEnabled()) {
             log.debug("updateRecord执行语句: [" + sqlCmd + "]");
           }
           if (ctx.getDataBaseUtil().execUpdate(sqlCmd) == 0) {
             if (log.isInfoEnabled()) {
               log.info(sm.getString("215021", sqlSentence));
             }
 
             ret = 2;
             if (!(isIgnore))
               return ret;
           }
         }
         catch (HiException e)
         {
           if (log.isInfoEnabled()) {
             log.info(sm.getString("215021", sqlSentence));
           }
 
           ret = 2;
           if (!(isIgnore)) {
             throw e;
           }
         }
       }
 
       groupNodes.clear();
     }
     return ret;
   }
 
   public int OpenCursorBind(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     if ((args == null) || (args.size() == 0)) {
       throw new HiException("215110");
     }
 
     HiDBCursor cursor = null;
 
     Iterator argIt = args.values().iterator();
     String sqlCmd = (String)argIt.next();
     String cursorName = null;
     if (argIt.hasNext()) {
       cursorName = (String)argIt.next();
     }
 
     String sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + sqlCmd);
 
     String sqlFldLst = HiArgUtils.getString(ctx, "FIELDS." + sqlCmd);
 
     HiETF etfBody = (HiETF)msg.getBody();
     List etfFields = HiArgUtils.getFldValues(etfBody, sqlFldLst, "|");
     cursor = new HiDBCursor(ctx, sqlSentence, etfFields);
 
     if (cursorName == null)
       ctx.setBaseSource("CURSOR.CURSOR_1", cursor);
     else {
       ctx.setBaseSource("CURSOR." + cursorName.toUpperCase(), cursor);
     }
     return 0;
   }
 
   public int openCursor(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     if ((args == null) || (args.size() == 0)) {
       throw new HiException("215110");
     }
 
     HiDBCursor cursor = null;
 
     Iterator argIt = args.values().iterator();
     String sqlCmd = (String)argIt.next();
     String cursorName = null;
     if (argIt.hasNext()) {
       cursorName = (String)argIt.next();
     }
 
     String sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + sqlCmd);
 
     String sqlFldLst = HiArgUtils.getString(ctx, "FIELDS." + sqlCmd);
 
     if (StringUtils.isEmpty(sqlFldLst)) {
       cursor = new HiDBCursor(msg, sqlSentence, ctx);
     } else {
       HiETF etfBody = (HiETF)msg.getBody();
       List etfFields = HiArgUtils.getFldValues(etfBody, sqlFldLst, "|");
 
       cursor = new HiDBCursor(msg, sqlSentence, etfFields, ctx);
     }
 
     if (cursorName == null)
       ctx.setBaseSource("CURSOR.CURSOR_1", cursor);
     else {
       ctx.setBaseSource("CURSOR." + cursorName.toUpperCase(), cursor);
     }
     return 0;
   }
 
   public int fetchCursor(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     String cursorName = null;
     String objName = null;
     if (args != null) {
       cursorName = args.get("CursorName");
       objName = args.get("ObjectName");
     }
 
     if (StringUtils.isEmpty(cursorName))
       cursorName = "CURSOR_1";
     else {
       cursorName = cursorName.toUpperCase();
     }
 
     HiDBCursor cursor = (HiDBCursor)ctx.getBaseSource("CURSOR." + cursorName);
 
     if (cursor == null) {
       throw new HiException("220310", cursorName);
     }
 
     Map curRec = cursor.next();
 
     if (curRec == null) {
       return 2;
     }
 
     HiETF etfBody = (HiETF)msg.getBody();
 
     if (StringUtils.isNotEmpty(objName)) {
       objName = objName.toUpperCase();
 
       etfBody = etfBody.getGrandChildNode(objName);
       if (etfBody == null) {
         throw new HiException("220311", objName);
       }
     }
 
     Iterator recIt = curRec.entrySet().iterator();
 
     while (recIt.hasNext()) {
       Map.Entry recEntry = (Map.Entry)recIt.next();
       etfBody.setChildValue((String)recEntry.getKey(), (String)recEntry.getValue());
     }
 
     return 0;
   }
 
   public int closeCursor(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String cursorName = null;
     if (args != null) {
       cursorName = args.get("CursorName");
     }
 
     if (StringUtils.isEmpty(cursorName)) {
       cursorName = "CURSOR_1";
     }
 
     cursorName = "CURSOR." + cursorName;
 
     HiDBCursor cursor = (HiDBCursor)ctx.getBaseSource(cursorName);
     if (cursor == null) {
       throw new HiException("220310", cursorName);
     }
 
     cursor.close();
 
     return 0;
   }
 
   public int CommitWork(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     ctx.getDataBaseUtil().commit();
     return 0;
   }
 
   public static int RollbackWork(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     ctx.getDataBaseUtil().rollback();
     return 0;
   }
 
   public int QueryInGroupBind(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("开始执行原子组件: queryInGroup");
     }
     if ((args == null) || (args.size() == 0)) {
       throw new HiException("215110");
     }
 
     String sqlCmd = HiArgUtils.getFirstNotNull(args);
 
     String recName = args.get("RecordName");
     if (StringUtils.isEmpty(recName)) {
       recName = "ROOT";
     }
 
     HiETF etfBody = (HiETF)msg.getBody();
     String sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + sqlCmd);
 
     String sqlFldLst = HiArgUtils.getString(ctx, "FIELDS." + sqlCmd);
 
     List etfFields = HiArgUtils.getFldValues(etfBody, sqlFldLst, "|");
 
     if (log.isDebugEnabled()) {
       log.debug("QueryInGroup执行SQL语句: [" + sqlSentence + "]");
     }
 
     List queryRs = ctx.getDataBaseUtil().execQueryBind(sqlSentence, etfFields);
     if ((queryRs == null) || (queryRs.size() == 0)) {
       etfBody.setChildValue("REC_NUM", "0");
       return 2;
     }
 
     Map queryRec = null;
     Map.Entry recEntry = null;
     Iterator recIt = null;
 
     int i = 0;
     for (i = 0; i < queryRs.size(); ++i) {
       queryRec = (Map)queryRs.get(i);
       recIt = queryRec.entrySet().iterator();
 
       HiETF grpRoot = etfBody.addNode(recName + "_" + (i + 1));
 
       while (recIt.hasNext()) {
         recEntry = (Map.Entry)recIt.next();
         grpRoot.setChildValue((String)recEntry.getKey(), (String)recEntry.getValue());
       }
 
     }
 
     etfBody.setChildValue("REC_NUM", String.valueOf(i));
 
     return 0;
   }
 
   public int QueryInGroup(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("开始执行原子组件: queryInGroup");
     }
     if ((args == null) || (args.size() == 0)) {
       throw new HiException("215110");
     }
 
     String sqlCmd = HiArgUtils.getFirstNotNull(args);
 
     String recName = args.get("RecordName");
     if (StringUtils.isEmpty(recName)) {
       recName = "ROOT";
     }
 
     HiETF etfBody = (HiETF)msg.getBody();
     boolean escape = args.getBoolean("escape");
     String sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, sqlCmd, etfBody, escape);
 
     if (log.isDebugEnabled()) {
       log.debug("QueryInGroup执行SQL语句: [" + sqlSentence + "]");
     }
 
     List queryRs = ctx.getDataBaseUtil().execQuery(sqlSentence);
     if ((queryRs == null) || (queryRs.size() == 0)) {
       etfBody.setChildValue("REC_NUM", "0");
       return 2;
     }
 
     Map queryRec = null;
     Map.Entry recEntry = null;
     Iterator recIt = null;
 
     int i = 0;
     for (i = 0; i < queryRs.size(); ++i) {
       queryRec = (Map)queryRs.get(i);
       recIt = queryRec.entrySet().iterator();
 
       HiETF grpRoot = etfBody.addNode(recName + "_" + (i + 1));
 
       while (recIt.hasNext()) {
         recEntry = (Map.Entry)recIt.next();
         grpRoot.setChildValue((String)recEntry.getKey(), (String)recEntry.getValue());
       }
 
     }
 
     etfBody.setChildValue("REC_NUM", String.valueOf(i));
 
     return 0;
   }
 
   public int QueryInGroupExt(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("开始执行原子组件: queryInGroup");
     }
     if ((argsMap == null) || (argsMap.size() == 0)) {
       throw new HiException("215110");
     }
     String aSqlName = argsMap.get("CndSts");
     String tblnam = argsMap.get("TblNam").toUpperCase();
 
     String cndsts = HiDbtSqlHelper.getDynSentence(ctx, aSqlName);
 
     String recNam = "ROOT";
     if (argsMap.contains("RecordName")) {
       recNam = argsMap.get("RecordName");
     }
     int rsSize = HiDbtUtils.dbtextquery_limit(tblnam, cndsts, -1, msg.getETFBody(), recNam, ctx);
 
     if (rsSize == 0) {
       return 2;
     }
     msg.getETFBody().setChildValue("RecNum", String.valueOf(rsSize));
     return 0;
   }
 
   public int ReadRecordExt(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("开始执行原子组件: queryInGroup");
     }
     if ((argsMap == null) || (argsMap.size() < 2)) {
       throw new HiException("215110");
     }
     String aSqlName = argsMap.get("CndSts");
     String tblnam = argsMap.get("TblNam").toUpperCase();
 
     String cndsts = HiDbtSqlHelper.getDynSentence(ctx, aSqlName);
 
     int rsSize = HiDbtUtils.dbtextquery_limit(tblnam, cndsts, 1, msg.getETFBody(), ctx);
 
     if (rsSize == 0)
       return 2;
     return 0;
   }
 
   public int DeleteRecordExt(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("开始执行原子组件: DeleteRecordExt");
     }
     if ((args == null) || (args.size() != 2))
     {
       throw new HiException("215110");
     }
 
     int ret = 0;
 
     HiETF etfBody = (HiETF)msg.getBody();
 
     String sqlTblNam = HiArgUtils.getStringNotNull(args, "TblNam").toUpperCase();
     String sqlCndSts = HiArgUtils.getStringNotNull(args, "CndSts").toUpperCase();
 
     if (log.isDebugEnabled()) {
       log.debug("DeleteRecordExt: 表名[" + sqlTblNam + "] 执行语句别名[" + sqlCndSts + "]");
     }
 
     boolean escape = args.getBoolean("escape");
 
     String sqlCond = HiDbtSqlHelper.getDynSentence(ctx, sqlCndSts, etfBody, escape);
 
     if (log.isDebugEnabled()) {
       log.debug("DeleteRecordExt执行语句条件部分: [" + sqlCond + "]");
     }
     if (HiDbtUtils.dbtextdelreccon(sqlTblNam, sqlCond, etfBody, ctx) == 0) {
       ret = 2;
     }
 
     return ret;
   }
 
   public int ConnectDB(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     ctx.getDataBaseUtil().getConnection();
     return 0;
   }
 
   public int DisconnectDB(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     ctx.getDataBaseUtil().close();
     return 0;
   }
 
   public int UpdateRecordExt(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String sqlTblNam;
     String sqlCndSts;
     String sqlCond;
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("开始执行原子组件: UpdateRecordExt");
     }
     if ((args == null) || (args.size() < 2)) {
       throw new HiException("215110");
     }
 
     int ret = 0;
 
     HiETF etfBody = (HiETF)msg.getBody();
     boolean escape = args.getBoolean("escape");
     String groupName = HiArgUtils.getStringNotNull(args, "GrpNam");
 
     if (StringUtils.isBlank(groupName))
     {
       sqlTblNam = HiArgUtils.getStringNotNull(args, "TblNam").toUpperCase();
 
       sqlCndSts = HiArgUtils.getStringNotNull(args, "CndSts").toUpperCase();
 
       if (log.isDebugEnabled()) {
         log.debug("updateRecord: 表名[" + sqlTblNam + "] 执行语句别名[" + sqlCndSts + "]");
       }
 
       sqlCond = HiDbtSqlHelper.getDynSentence(ctx, sqlCndSts, etfBody, escape);
 
       if (log.isDebugEnabled()) {
         log.debug("updateRecordExt执行语句条件部分: [" + sqlCond + "]");
       }
       if (HiDbtUtils.dbtextupdreccon(sqlTblNam, sqlCond, etfBody, ctx) == 0) {
         ret = 2;
       }
     }
     else
     {
       sqlTblNam = HiArgUtils.getStringNotNull(args, "TblNam").toUpperCase();
 
       sqlCndSts = HiArgUtils.getStringNotNull(args, "CndSts").toUpperCase();
 
       if (log.isDebugEnabled()) {
         log.debug("updateRecord: 表名[" + sqlTblNam + "] 执行语句别名[" + sqlCndSts + "]");
       }
 
       sqlCond = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + sqlCndSts);
 
       String sqlFldLst = HiArgUtils.getString(ctx, "FIELDS." + sqlCndSts);
 
       boolean isIgnore = args.getBoolean("Ignore");
 
       String rootFldLst = args.get("FldLst");
 
       Map refEtfFlds = null;
       if (rootFldLst != null) {
         refEtfFlds = HiArgUtils.getEtfFields(etfBody, rootFldLst, "|");
       }
 
       HiETF groupNodeRec = null;
       List groupNodes = etfBody.getGrandChildFuzzyEnd(groupName + "_");
       Iterator grpIt = groupNodes.iterator();
 
       Map.Entry refEtf = null;
 
       while (grpIt.hasNext()) {
         groupNodeRec = ((HiETF)grpIt.next()).cloneNode();
 
         if (refEtfFlds != null) {
           Iterator refEtfIt = refEtfFlds.entrySet().iterator();
 
           while (refEtfIt.hasNext()) {
             refEtf = (Map.Entry)refEtfIt.next();
             groupNodeRec.setChildValue((String)refEtf.getKey(), (String)refEtf.getValue());
           }
 
         }
 
         sqlCond = HiDbtSqlHelper.replace(ctx, groupNodeRec, sqlCond, sqlFldLst, "|");
         try
         {
           if (log.isDebugEnabled()) {
             log.debug("updateRecordExt执行语句: [" + sqlCond + "]");
           }
           if (HiDbtUtils.dbtextupdreccon(sqlTblNam, sqlCond, groupNodeRec, ctx) == 0)
           {
             if (log.isInfoEnabled()) {
               log.info(sm.getString("215021", sqlTblNam));
             }
 
             ret = 2;
             if (!(isIgnore))
               return ret;
           }
         }
         catch (HiException e)
         {
           if (log.isInfoEnabled()) {
             log.info(sm.getString("215021", sqlTblNam));
           }
 
           ret = 2;
           if (!(isIgnore)) {
             throw e;
           }
         }
       }
 
       groupNodes.clear();
     }
     return ret;
   }
 }