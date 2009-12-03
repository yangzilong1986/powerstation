/*      */ package com.hisun.atc;
/*      */ 
/*      */ import com.hisun.atc.common.HiArgUtils;
/*      */ import com.hisun.atc.common.HiAtcLib;
/*      */ import com.hisun.atc.common.HiDBCursor;
/*      */ import com.hisun.atc.common.HiDbtSqlHelper;
/*      */ import com.hisun.atc.common.HiDbtUtils;
/*      */ import com.hisun.database.HiDataBaseUtil;
/*      */ import com.hisun.exception.HiException;
/*      */ import com.hisun.hilib.HiATLParam;
/*      */ import com.hisun.hilog4j.HiLog;
/*      */ import com.hisun.hilog4j.Logger;
/*      */ import com.hisun.message.HiETF;
/*      */ import com.hisun.message.HiMessage;
/*      */ import com.hisun.message.HiMessageContext;
/*      */ import com.hisun.util.HiStringManager;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.lang.math.NumberUtils;
/*      */ 
/*      */ public class HiDBUtils
/*      */ {
/*   36 */   private static HiStringManager sm = HiStringManager.getManager();
/*      */ 
/*      */   public int ExecSqlBind(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*      */     String sqlArg;
/*   63 */     HiMessage msg = ctx.getCurrentMsg();
/*   64 */     Logger log = HiLog.getLogger(msg);
/*   65 */     if (log.isDebugEnabled()) {
/*   66 */       log.debug("开始执行原子组件: ExecSql ");
/*      */     }
/*   68 */     if ((args == null) || (args.size() == 0)) {
/*   69 */       throw new HiException("215110");
/*      */     }
/*      */ 
/*   73 */     String sqlSentence = null;
/*   74 */     int ret = 0;
/*      */ 
/*   78 */     HiETF etfBody = (HiETF)msg.getBody();
/*      */ 
/*   80 */     String groupName = args.get("GrpNam");
/*      */ 
/*   82 */     if (StringUtils.isBlank(groupName))
/*      */     {
/*   84 */       sqlArg = HiArgUtils.getFirstNotNull(args);
/*   85 */       if (log.isDebugEnabled()) {
/*   86 */         log.debug("ExecSql 参数:[" + sqlArg + "]");
/*      */       }
/*   88 */       sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + sqlArg);
/*      */ 
/*   90 */       String sqlFldLst = HiArgUtils.getString(ctx, "FIELDS." + sqlArg);
/*      */ 
/*   92 */       List etfFields = HiArgUtils.getFldValues(etfBody, sqlFldLst, "|");
/*      */ 
/*   94 */       if (log.isDebugEnabled()) {
/*   95 */         log.debug("ExecSql执行SQL语句: [" + sqlSentence + "]");
/*      */       }
/*      */ 
/*   98 */       if (ctx.getDataBaseUtil().execUpdateBind(sqlSentence, etfFields) == 0)
/*      */       {
/*  101 */         return 2;
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  106 */       sqlArg = HiArgUtils.getStringNotNull(args, "SqlCmd").toUpperCase();
/*  107 */       if (log.isDebugEnabled()) {
/*  108 */         log.debug("ExecSql 参数:[" + sqlArg + "]");
/*      */       }
/*      */ 
/*  114 */       boolean isIgnore = args.getBoolean("Ignore");
/*      */ 
/*  117 */       String rootFldLst = args.get("FldLst");
/*      */ 
/*  120 */       Map refEtfFlds = null;
/*  121 */       if (rootFldLst != null) {
/*  122 */         refEtfFlds = HiArgUtils.getEtfFields(etfBody, rootFldLst, "|");
/*      */       }
/*      */ 
/*  125 */       HiETF groupNodeRec = null;
/*  126 */       List groupNodes = etfBody.getGrandChildFuzzyEnd(groupName + "_");
/*  127 */       Iterator grpIt = groupNodes.iterator();
/*      */ 
/*  129 */       Map.Entry refEtf = null;
/*      */ 
/*  131 */       while (grpIt.hasNext()) {
/*  132 */         HiETF tmpNod = (HiETF)grpIt.next();
/*  133 */         if (tmpNod.isEndNode()) {
/*      */           continue;
/*      */         }
/*  136 */         groupNodeRec = tmpNod.cloneNode();
/*      */ 
/*  138 */         if (refEtfFlds != null) {
/*  139 */           Iterator refEtfIt = refEtfFlds.entrySet().iterator();
/*      */ 
/*  141 */           while (refEtfIt.hasNext()) {
/*  142 */             refEtf = (Map.Entry)refEtfIt.next();
/*  143 */             groupNodeRec.setChildValue((String)refEtf.getKey(), (String)refEtf.getValue());
/*      */           }
/*      */         }
/*      */ 
/*      */         try
/*      */         {
/*  149 */           sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + sqlArg);
/*      */ 
/*  151 */           String sqlFldLst = HiArgUtils.getString(ctx, "FIELDS." + sqlArg);
/*      */ 
/*  154 */           List etfFields = HiArgUtils.getFldValues(etfBody, sqlFldLst, "|");
/*      */ 
/*  156 */           if (log.isDebugEnabled()) {
/*  157 */             log.debug("从Group取数据, 执行SQL语句: [" + sqlSentence + "]");
/*      */           }
/*  159 */           if (ctx.getDataBaseUtil().execUpdateBind(sqlSentence, etfFields) == 0) {
/*  160 */             if (log.isInfoEnabled()) {
/*  161 */               log.info(sm.getString("215021", sqlSentence));
/*      */             }
/*      */ 
/*  166 */             ret = 2;
/*  167 */             if (!(isIgnore))
/*  168 */               return ret;
/*      */           }
/*      */         }
/*      */         catch (HiException e) {
/*  172 */           if (log.isInfoEnabled()) {
/*  173 */             log.info(sm.getString("215021", sqlSentence));
/*      */           }
/*      */ 
/*  176 */           ret = 2;
/*  177 */           if (!(isIgnore)) {
/*  178 */             throw e;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  183 */       groupNodes.clear();
/*      */     }
/*      */ 
/*  186 */     return ret;
/*      */   }
/*      */ 
/*      */   public int ExecSql(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*      */     String sqlArg;
/*  214 */     HiMessage msg = ctx.getCurrentMsg();
/*  215 */     Logger log = HiLog.getLogger(msg);
/*  216 */     if (log.isDebugEnabled()) {
/*  217 */       log.debug("开始执行原子组件: ExecSql ");
/*      */     }
/*  219 */     if ((args == null) || (args.size() == 0)) {
/*  220 */       throw new HiException("215110");
/*      */     }
/*      */ 
/*  224 */     String sqlSentence = null;
/*  225 */     int ret = 0;
/*      */ 
/*  229 */     HiETF etfBody = (HiETF)msg.getBody();
/*      */ 
/*  231 */     boolean escape = args.getBoolean("escape");
/*  232 */     String groupName = args.get("GrpNam");
/*      */ 
/*  234 */     if (StringUtils.isBlank(groupName))
/*      */     {
/*  237 */       sqlArg = HiArgUtils.getFirstNotNull(args);
/*  238 */       if (log.isDebugEnabled()) {
/*  239 */         log.debug("ExecSql 参数:[" + sqlArg + "]");
/*      */       }
/*  241 */       String clobName = args.get("clobs");
/*  242 */       if (StringUtils.isBlank(clobName)) {
/*  243 */         sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, sqlArg, etfBody, escape);
/*      */ 
/*  245 */         if (log.isDebugEnabled()) {
/*  246 */           log.debug("ExecSql执行SQL语句: [" + sqlSentence + "]");
/*      */         }
/*      */ 
/*  249 */         if (ctx.getDataBaseUtil().execUpdate(sqlSentence) != 0) {
/*      */           break label306;
/*      */         }
/*      */ 
/*  253 */         return 2;
/*      */       }
/*      */ 
/*  256 */       String[] clobNames = clobName.split("\\|");
/*  257 */       ArrayList params = new ArrayList();
/*  258 */       sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, sqlArg, etfBody, clobNames, params);
/*      */ 
/*  260 */       if (log.isInfoEnabled()) {
/*  261 */         log.info("ExecSql [" + sqlSentence + "][" + params + "]");
/*      */       }
/*  263 */       label306: if (ctx.getDataBaseUtil().execUpdate(sqlSentence, params) == 0) {
/*  264 */         return 2;
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  271 */       sqlArg = HiArgUtils.getStringNotNull(args, "SqlCmd").toUpperCase();
/*  272 */       if (log.isDebugEnabled()) {
/*  273 */         log.debug("ExecSql 参数:[" + sqlArg + "]");
/*      */       }
/*      */ 
/*  279 */       boolean isIgnore = args.getBoolean("Ignore");
/*      */ 
/*  282 */       String rootFldLst = args.get("FldLst");
/*      */ 
/*  285 */       Map refEtfFlds = null;
/*  286 */       if (rootFldLst != null) {
/*  287 */         refEtfFlds = HiArgUtils.getEtfFields(etfBody, rootFldLst, "|");
/*      */       }
/*      */ 
/*  290 */       HiETF groupNodeRec = null;
/*  291 */       List groupNodes = etfBody.getGrandChildFuzzyEnd(groupName + "_");
/*  292 */       Iterator grpIt = groupNodes.iterator();
/*      */ 
/*  294 */       Map.Entry refEtf = null;
/*      */ 
/*  296 */       while (grpIt.hasNext()) {
/*  297 */         HiETF tmpNod = (HiETF)grpIt.next();
/*  298 */         if (tmpNod.isEndNode()) {
/*      */           continue;
/*      */         }
/*  301 */         groupNodeRec = tmpNod.cloneNode();
/*      */ 
/*  303 */         if (refEtfFlds != null) {
/*  304 */           Iterator refEtfIt = refEtfFlds.entrySet().iterator();
/*      */ 
/*  306 */           while (refEtfIt.hasNext()) {
/*  307 */             refEtf = (Map.Entry)refEtfIt.next();
/*  308 */             groupNodeRec.setChildValue((String)refEtf.getKey(), (String)refEtf.getValue());
/*      */           }
/*      */         }
/*      */         try
/*      */         {
/*  313 */           sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, sqlArg, groupNodeRec, escape);
/*      */ 
/*  315 */           if (log.isDebugEnabled()) {
/*  316 */             log.debug("从Group取数据, 执行SQL语句: [" + sqlSentence + "]");
/*      */           }
/*  318 */           if (ctx.getDataBaseUtil().execUpdate(sqlSentence) == 0) {
/*  319 */             if (log.isInfoEnabled()) {
/*  320 */               log.info(sm.getString("215021", sqlSentence));
/*      */             }
/*      */ 
/*  325 */             ret = 2;
/*  326 */             if (!(isIgnore))
/*  327 */               return ret;
/*      */           }
/*      */         }
/*      */         catch (HiException e)
/*      */         {
/*  332 */           if (log.isInfoEnabled()) {
/*  333 */             log.info(sm.getString("215021", sqlSentence));
/*      */           }
/*      */ 
/*  336 */           ret = 2;
/*  337 */           if (!(isIgnore)) {
/*  338 */             throw e;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  343 */       groupNodes.clear();
/*      */     }
/*      */ 
/*  346 */     return ret;
/*      */   }
/*      */ 
/*      */   public int InsertRecord(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*      */     String sqlTblRef;
/*  375 */     HiMessage msg = ctx.getCurrentMsg();
/*  376 */     Logger log = HiLog.getLogger(msg);
/*  377 */     if (log.isDebugEnabled()) {
/*  378 */       log.debug("开始执行原子组件: InsertRecord");
/*      */     }
/*  380 */     if ((args == null) || (args.size() == 0)) {
/*  381 */       throw new HiException("215110");
/*      */     }
/*      */ 
/*  384 */     int ret = 0;
/*      */ 
/*  388 */     HiETF etfBody = (HiETF)msg.getBody();
/*      */ 
/*  390 */     if (args.size() == 1)
/*      */     {
/*  392 */       sqlTblRef = HiArgUtils.getFirstNotNull(args).toUpperCase();
/*  393 */       if (log.isDebugEnabled()) {
/*  394 */         log.debug("InsertRecord: 表名[" + sqlTblRef + "]");
/*      */       }
/*      */ 
/*  397 */       ret = HiDbtUtils.dbtsqlinsrec(sqlTblRef, etfBody, ctx);
/*      */     }
/*      */     else
/*      */     {
/*  401 */       sqlTblRef = HiArgUtils.getStringNotNull(args, "TblNam").toUpperCase();
/*      */ 
/*  403 */       if (log.isDebugEnabled()) {
/*  404 */         log.debug("InsertRecord: 表名[" + sqlTblRef + "]");
/*      */       }
/*      */ 
/*  407 */       String groupName = HiArgUtils.getStringNotNull(args, "GrpNam");
/*      */ 
/*  410 */       boolean isIgnore = args.getBoolean("Ignore");
/*      */ 
/*  413 */       String rootFldLst = args.get("FldLst");
/*      */ 
/*  416 */       Map refEtfFlds = null;
/*  417 */       if (rootFldLst != null) {
/*  418 */         refEtfFlds = HiArgUtils.getEtfFields(etfBody, rootFldLst, "|");
/*      */       }
/*      */ 
/*  421 */       HiETF groupNodeRec = null;
/*      */ 
/*  423 */       Map.Entry refEtf = null;
/*      */ 
/*  425 */       int num = NumberUtils.toInt(etfBody.getChildValue(groupName + "_NUM"));
/*      */ 
/*  427 */       if (log.isDebugEnabled()) {
/*  428 */         log.debug("GRP NUM:[" + num + "]");
/*      */       }
/*  430 */       for (int i = 1; ; ++i) {
/*  431 */         if (log.isDebugEnabled()) {
/*  432 */           log.debug("LOOP NUM:[" + i + "]");
/*      */         }
/*  434 */         if ((num != 0) && (i > num)) {
/*      */           break;
/*      */         }
/*      */ 
/*  438 */         groupNodeRec = etfBody.getChildNode(groupName + "_" + i);
/*  439 */         if (groupNodeRec == null) break; if (groupNodeRec.isEndNode()) {
/*      */           break;
/*      */         }
/*  442 */         groupNodeRec = groupNodeRec.cloneNode();
/*      */ 
/*  444 */         if (refEtfFlds != null) {
/*  445 */           Iterator refEtfIt = refEtfFlds.entrySet().iterator();
/*      */ 
/*  447 */           while (refEtfIt.hasNext()) {
/*  448 */             refEtf = (Map.Entry)refEtfIt.next();
/*  449 */             groupNodeRec.setChildValue((String)refEtf.getKey(), (String)refEtf.getValue());
/*      */           }
/*      */         }
/*      */ 
/*  453 */         ArrayList params = new ArrayList();
/*      */ 
/*  455 */         String sqlSentence = HiDbtSqlHelper.buildInsertSentence(ctx, groupNodeRec, sqlTblRef, params);
/*      */ 
/*  458 */         if (log.isDebugEnabled()) {
/*  459 */           log.debug("从Group上取数据, 执行Insert语句: [" + sqlSentence + "][" + params + "]");
/*      */         }
/*      */         try
/*      */         {
/*  463 */           if (ctx.getDataBaseUtil().execUpdate(sqlSentence, params) == 0) {
/*  464 */             if (log.isInfoEnabled()) {
/*  465 */               log.info(sm.getString("215021", sqlSentence));
/*      */             }
/*      */ 
/*  470 */             ret = 2;
/*  471 */             if (!(isIgnore))
/*  472 */               return ret;
/*      */           }
/*      */         }
/*      */         catch (HiException e) {
/*  476 */           if (log.isInfoEnabled()) {
/*  477 */             log.info(sm.getString("215021", sqlSentence, e));
/*      */           }
/*      */ 
/*  480 */           ret = 2;
/*      */ 
/*  482 */           if (!(isIgnore)) {
/*  483 */             throw e;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  488 */     return ret;
/*      */   }
/*      */ 
/*      */   public int insertRecordExt(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*      */     String sqlTblRef;
/*  519 */     HiMessage msg = ctx.getCurrentMsg();
/*  520 */     Logger log = HiLog.getLogger(msg);
/*  521 */     if (log.isDebugEnabled()) {
/*  522 */       log.debug("开始执行原子组件: InsertRecordExt");
/*      */     }
/*  524 */     if ((args == null) || (args.size() == 0)) {
/*  525 */       throw new HiException("215110");
/*      */     }
/*      */ 
/*  528 */     int ret = 0;
/*      */ 
/*  531 */     HiETF etfBody = (HiETF)msg.getBody();
/*      */ 
/*  533 */     if (args.size() == 1)
/*      */     {
/*  536 */       sqlTblRef = HiArgUtils.getFirstNotNull(args).toUpperCase();
/*  537 */       if (log.isDebugEnabled()) {
/*  538 */         log.debug("InsertRecord: 表名[" + sqlTblRef + "]");
/*      */       }
/*      */ 
/*  541 */       ret = HiDbtUtils.dbtextinsrec(sqlTblRef, etfBody, ctx);
/*      */     }
/*      */     else
/*      */     {
/*  545 */       sqlTblRef = HiArgUtils.getStringNotNull(args, "TblNam").toUpperCase();
/*      */ 
/*  547 */       if (log.isDebugEnabled()) {
/*  548 */         log.debug("InsertRecord: 表名[" + sqlTblRef + "]");
/*      */       }
/*      */ 
/*  551 */       String groupName = HiArgUtils.getStringNotNull(args, "GrpNam");
/*      */ 
/*  554 */       boolean isIgnore = args.getBoolean("Ignore");
/*      */ 
/*  557 */       String rootFldLst = args.get("FldLst");
/*      */ 
/*  560 */       Map refEtfFlds = null;
/*  561 */       if (rootFldLst != null) {
/*  562 */         refEtfFlds = HiArgUtils.getEtfFields(etfBody, rootFldLst, "|");
/*      */       }
/*      */ 
/*  565 */       HiETF groupNodeRec = null;
/*  566 */       List groupNodes = etfBody.getGrandChildFuzzyEnd(groupName + "_");
/*  567 */       Iterator grpIt = groupNodes.iterator();
/*      */ 
/*  569 */       Map.Entry refEtf = null;
/*      */ 
/*  571 */       while (grpIt.hasNext()) {
/*  572 */         groupNodeRec = ((HiETF)grpIt.next()).cloneNode();
/*      */ 
/*  574 */         if (refEtfFlds != null) {
/*  575 */           Iterator refEtfIt = refEtfFlds.entrySet().iterator();
/*      */ 
/*  577 */           while (refEtfIt.hasNext()) {
/*  578 */             refEtf = (Map.Entry)refEtfIt.next();
/*  579 */             groupNodeRec.setChildValue((String)refEtf.getKey(), (String)refEtf.getValue());
/*      */           }
/*      */         }
/*      */         try
/*      */         {
/*  584 */           if (HiDbtUtils.dbtextinsrec(sqlTblRef, groupNodeRec, ctx) == 1) {
/*  585 */             if (log.isInfoEnabled()) {
/*  586 */               log.info(sm.getString("215021", sqlTblRef));
/*      */             }
/*      */ 
/*  589 */             ret = 2;
/*      */ 
/*  591 */             if (!(isIgnore))
/*  592 */               return ret;
/*      */           }
/*      */         }
/*      */         catch (HiException e) {
/*  596 */           if (log.isInfoEnabled()) {
/*  597 */             log.info(sm.getString("215021", sqlTblRef));
/*      */           }
/*      */ 
/*  600 */           ret = 2;
/*      */ 
/*  602 */           if (!(isIgnore)) {
/*  603 */             throw e;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  608 */       groupNodes.clear();
/*      */     }
/*  610 */     return ret;
/*      */   }
/*      */ 
/*      */   public int ReadRecordBind(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  631 */     HiMessage msg = ctx.getCurrentMsg();
/*  632 */     Logger log = HiLog.getLogger(msg);
/*  633 */     if (log.isDebugEnabled()) {
/*  634 */       log.debug("开始执行原子组件: readRecord");
/*      */     }
/*  636 */     if ((args == null) || (args.size() == 0)) {
/*  637 */       throw new HiException("215110");
/*      */     }
/*      */ 
/*  641 */     String sqlCmd = (String)args.values().iterator().next();
/*      */ 
/*  643 */     HiETF etfBody = (HiETF)msg.getBody();
/*  644 */     String sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + sqlCmd);
/*      */ 
/*  646 */     String sqlFldLst = HiArgUtils.getString(ctx, "FIELDS." + sqlCmd);
/*      */ 
/*  649 */     List etfFields = HiArgUtils.getFldValues(etfBody, sqlFldLst, "|");
/*      */ 
/*  651 */     List queryRs = ctx.getDataBaseUtil().execQueryBind(sqlSentence, etfFields);
/*      */ 
/*  654 */     if ((queryRs != null) && (queryRs.size() == 0)) {
/*  655 */       return 2;
/*      */     }
/*      */ 
/*  658 */     Map queryRec = (HashMap)queryRs.get(0);
/*      */ 
/*  660 */     Map.Entry recEntry = null;
/*  661 */     Iterator recIt = queryRec.entrySet().iterator();
/*  662 */     while (recIt.hasNext()) {
/*  663 */       recEntry = (Map.Entry)recIt.next();
/*  664 */       etfBody.setChildValue((String)recEntry.getKey(), (String)recEntry.getValue());
/*      */     }
/*      */ 
/*  667 */     return 0;
/*      */   }
/*      */ 
/*      */   public int readRecord(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  688 */     HiMessage msg = ctx.getCurrentMsg();
/*  689 */     Logger log = HiLog.getLogger(msg);
/*  690 */     if (log.isDebugEnabled()) {
/*  691 */       log.debug("开始执行原子组件: readRecord");
/*      */     }
/*  693 */     if ((args == null) || (args.size() == 0)) {
/*  694 */       throw new HiException("215110");
/*      */     }
/*      */ 
/*  698 */     String sqlCmd = (String)args.values().iterator().next();
/*      */ 
/*  700 */     HiETF etfBody = (HiETF)msg.getBody();
/*  701 */     boolean escape = args.getBoolean("escape");
/*  702 */     String sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, sqlCmd, etfBody, escape);
/*      */ 
/*  705 */     if (HiAtcLib.queryToETF(ctx, sqlSentence) == -1)
/*      */     {
/*  709 */       return 2;
/*      */     }
/*      */ 
/*  712 */     return 0;
/*      */   }
/*      */ 
/*      */   public int updateRecord(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*      */     String sqlTblRef;
/*      */     String sqlCndSts;
/*      */     String sqlSentence;
/*  745 */     HiMessage msg = ctx.getCurrentMsg();
/*  746 */     Logger log = HiLog.getLogger(msg);
/*  747 */     if (log.isDebugEnabled()) {
/*  748 */       log.debug("开始执行原子组件: updateRecord");
/*      */     }
/*  750 */     if ((args == null) || (args.size() < 2)) {
/*  751 */       throw new HiException("215110");
/*      */     }
/*      */ 
/*  754 */     int ret = 0;
/*      */ 
/*  760 */     HiETF etfBody = (HiETF)msg.getBody();
/*  761 */     boolean escape = args.getBoolean("escape");
/*  762 */     String groupName = args.get("GrpNam");
/*      */ 
/*  765 */     if (StringUtils.isBlank(groupName))
/*      */     {
/*  768 */       sqlTblRef = HiArgUtils.getStringNotNull(args, "TblNam").toUpperCase();
/*      */ 
/*  770 */       sqlCndSts = HiArgUtils.getStringNotNull(args, "CndSts").toUpperCase();
/*      */ 
/*  773 */       if (log.isDebugEnabled()) {
/*  774 */         log.debug("updateRecord: 表名[" + sqlTblRef + "] 执行语句别名[" + sqlCndSts + "]");
/*      */       }
/*      */ 
/*  778 */       sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, sqlCndSts, etfBody, escape);
/*      */ 
/*  780 */       ArrayList list = new ArrayList();
/*      */ 
/*  782 */       sqlSentence = HiDbtSqlHelper.buildUpdateSentence(ctx, etfBody, sqlTblRef, sqlSentence, false, list);
/*      */ 
/*  785 */       if (log.isInfoEnabled()) {
/*  786 */         log.info("updateRecord执行语句: [" + sqlSentence + "]:[" + list + "]");
/*      */       }
/*      */ 
/*  789 */       if (ctx.getDataBaseUtil().execUpdate(sqlSentence, list) == 0)
/*      */       {
/*  791 */         ret = 2;
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  796 */       sqlTblRef = HiArgUtils.getStringNotNull(args, "TblNam").toUpperCase();
/*      */ 
/*  798 */       sqlCndSts = HiArgUtils.getStringNotNull(args, "CndSts").toUpperCase();
/*      */ 
/*  801 */       if (log.isDebugEnabled()) {
/*  802 */         log.debug("updateRecord: 表名[" + sqlTblRef + "] 执行语句别名[" + sqlCndSts + "]");
/*      */       }
/*      */ 
/*  806 */       sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + sqlCndSts);
/*      */ 
/*  808 */       String sqlFldLst = HiArgUtils.getString(ctx, "FIELDS." + sqlCndSts);
/*      */ 
/*  814 */       boolean isIgnore = args.getBoolean("Ignore");
/*      */ 
/*  817 */       String rootFldLst = args.get("FldLst");
/*      */ 
/*  820 */       Map refEtfFlds = null;
/*  821 */       if (rootFldLst != null) {
/*  822 */         refEtfFlds = HiArgUtils.getEtfFields(etfBody, rootFldLst, "|");
/*      */       }
/*      */ 
/*  825 */       HiETF groupNodeRec = null;
/*  826 */       List groupNodes = etfBody.getGrandChildFuzzyEnd(groupName + "_");
/*  827 */       Iterator grpIt = groupNodes.iterator();
/*      */ 
/*  829 */       Map.Entry refEtf = null;
/*      */ 
/*  831 */       while (grpIt.hasNext()) {
/*  832 */         groupNodeRec = ((HiETF)grpIt.next()).cloneNode();
/*      */ 
/*  834 */         if (refEtfFlds != null) {
/*  835 */           Iterator refEtfIt = refEtfFlds.entrySet().iterator();
/*      */ 
/*  837 */           while (refEtfIt.hasNext()) {
/*  838 */             refEtf = (Map.Entry)refEtfIt.next();
/*  839 */             groupNodeRec.setChildValue((String)refEtf.getKey(), (String)refEtf.getValue());
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  844 */         String sqlCmd = HiDbtSqlHelper.replace(ctx, groupNodeRec, sqlSentence, sqlFldLst, "|");
/*      */ 
/*  848 */         sqlCmd = HiDbtSqlHelper.buildUpdateSentence(ctx, groupNodeRec, sqlTblRef, sqlCmd, false);
/*      */         try
/*      */         {
/*  852 */           if (log.isDebugEnabled()) {
/*  853 */             log.debug("updateRecord执行语句: [" + sqlCmd + "]");
/*      */           }
/*  855 */           if (ctx.getDataBaseUtil().execUpdate(sqlCmd) == 0) {
/*  856 */             if (log.isInfoEnabled()) {
/*  857 */               log.info(sm.getString("215021", sqlSentence));
/*      */             }
/*      */ 
/*  862 */             ret = 2;
/*  863 */             if (!(isIgnore))
/*  864 */               return ret;
/*      */           }
/*      */         }
/*      */         catch (HiException e)
/*      */         {
/*  869 */           if (log.isInfoEnabled()) {
/*  870 */             log.info(sm.getString("215021", sqlSentence));
/*      */           }
/*      */ 
/*  873 */           ret = 2;
/*  874 */           if (!(isIgnore)) {
/*  875 */             throw e;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  880 */       groupNodes.clear();
/*      */     }
/*  882 */     return ret;
/*      */   }
/*      */ 
/*      */   public int OpenCursorBind(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  910 */     HiMessage msg = ctx.getCurrentMsg();
/*  911 */     if ((args == null) || (args.size() == 0)) {
/*  912 */       throw new HiException("215110");
/*      */     }
/*      */ 
/*  915 */     HiDBCursor cursor = null;
/*      */ 
/*  919 */     Iterator argIt = args.values().iterator();
/*  920 */     String sqlCmd = (String)argIt.next();
/*  921 */     String cursorName = null;
/*  922 */     if (argIt.hasNext()) {
/*  923 */       cursorName = (String)argIt.next();
/*      */     }
/*      */ 
/*  926 */     String sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + sqlCmd);
/*      */ 
/*  928 */     String sqlFldLst = HiArgUtils.getString(ctx, "FIELDS." + sqlCmd);
/*      */ 
/*  931 */     HiETF etfBody = (HiETF)msg.getBody();
/*  932 */     List etfFields = HiArgUtils.getFldValues(etfBody, sqlFldLst, "|");
/*  933 */     cursor = new HiDBCursor(ctx, sqlSentence, etfFields);
/*      */ 
/*  935 */     if (cursorName == null)
/*  936 */       ctx.setBaseSource("CURSOR.CURSOR_1", cursor);
/*      */     else {
/*  938 */       ctx.setBaseSource("CURSOR." + cursorName.toUpperCase(), cursor);
/*      */     }
/*  940 */     return 0;
/*      */   }
/*      */ 
/*      */   public int openCursor(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  968 */     HiMessage msg = ctx.getCurrentMsg();
/*  969 */     if ((args == null) || (args.size() == 0)) {
/*  970 */       throw new HiException("215110");
/*      */     }
/*      */ 
/*  973 */     HiDBCursor cursor = null;
/*      */ 
/*  977 */     Iterator argIt = args.values().iterator();
/*  978 */     String sqlCmd = (String)argIt.next();
/*  979 */     String cursorName = null;
/*  980 */     if (argIt.hasNext()) {
/*  981 */       cursorName = (String)argIt.next();
/*      */     }
/*      */ 
/*  984 */     String sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + sqlCmd);
/*      */ 
/*  986 */     String sqlFldLst = HiArgUtils.getString(ctx, "FIELDS." + sqlCmd);
/*      */ 
/*  989 */     if (StringUtils.isEmpty(sqlFldLst)) {
/*  990 */       cursor = new HiDBCursor(msg, sqlSentence, ctx);
/*      */     } else {
/*  992 */       HiETF etfBody = (HiETF)msg.getBody();
/*  993 */       List etfFields = HiArgUtils.getFldValues(etfBody, sqlFldLst, "|");
/*      */ 
/*  995 */       cursor = new HiDBCursor(msg, sqlSentence, etfFields, ctx);
/*      */     }
/*      */ 
/*  998 */     if (cursorName == null)
/*  999 */       ctx.setBaseSource("CURSOR.CURSOR_1", cursor);
/*      */     else {
/* 1001 */       ctx.setBaseSource("CURSOR." + cursorName.toUpperCase(), cursor);
/*      */     }
/* 1003 */     return 0;
/*      */   }
/*      */ 
/*      */   public int fetchCursor(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1027 */     HiMessage msg = ctx.getCurrentMsg();
/* 1028 */     String cursorName = null;
/* 1029 */     String objName = null;
/* 1030 */     if (args != null) {
/* 1031 */       cursorName = args.get("CursorName");
/* 1032 */       objName = args.get("ObjectName");
/*      */     }
/*      */ 
/* 1035 */     if (StringUtils.isEmpty(cursorName))
/* 1036 */       cursorName = "CURSOR_1";
/*      */     else {
/* 1038 */       cursorName = cursorName.toUpperCase();
/*      */     }
/*      */ 
/* 1042 */     HiDBCursor cursor = (HiDBCursor)ctx.getBaseSource("CURSOR." + cursorName);
/*      */ 
/* 1044 */     if (cursor == null) {
/* 1045 */       throw new HiException("220310", cursorName);
/*      */     }
/*      */ 
/* 1048 */     Map curRec = cursor.next();
/*      */ 
/* 1050 */     if (curRec == null) {
/* 1051 */       return 2;
/*      */     }
/*      */ 
/* 1055 */     HiETF etfBody = (HiETF)msg.getBody();
/*      */ 
/* 1057 */     if (StringUtils.isNotEmpty(objName)) {
/* 1058 */       objName = objName.toUpperCase();
/*      */ 
/* 1060 */       etfBody = etfBody.getGrandChildNode(objName);
/* 1061 */       if (etfBody == null) {
/* 1062 */         throw new HiException("220311", objName);
/*      */       }
/*      */     }
/*      */ 
/* 1066 */     Iterator recIt = curRec.entrySet().iterator();
/*      */ 
/* 1069 */     while (recIt.hasNext()) {
/* 1070 */       Map.Entry recEntry = (Map.Entry)recIt.next();
/* 1071 */       etfBody.setChildValue((String)recEntry.getKey(), (String)recEntry.getValue());
/*      */     }
/*      */ 
/* 1075 */     return 0;
/*      */   }
/*      */ 
/*      */   public int closeCursor(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1097 */     String cursorName = null;
/* 1098 */     if (args != null) {
/* 1099 */       cursorName = args.get("CursorName");
/*      */     }
/*      */ 
/* 1102 */     if (StringUtils.isEmpty(cursorName)) {
/* 1103 */       cursorName = "CURSOR_1";
/*      */     }
/*      */ 
/* 1106 */     cursorName = "CURSOR." + cursorName;
/*      */ 
/* 1109 */     HiDBCursor cursor = (HiDBCursor)ctx.getBaseSource(cursorName);
/* 1110 */     if (cursor == null) {
/* 1111 */       throw new HiException("220310", cursorName);
/*      */     }
/*      */ 
/* 1114 */     cursor.close();
/*      */ 
/* 1116 */     return 0;
/*      */   }
/*      */ 
/*      */   public int CommitWork(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1135 */     ctx.getDataBaseUtil().commit();
/* 1136 */     return 0;
/*      */   }
/*      */ 
/*      */   public static int RollbackWork(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1155 */     ctx.getDataBaseUtil().rollback();
/* 1156 */     return 0;
/*      */   }
/*      */ 
/*      */   public int QueryInGroupBind(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1180 */     HiMessage msg = ctx.getCurrentMsg();
/* 1181 */     Logger log = HiLog.getLogger(msg);
/* 1182 */     if (log.isDebugEnabled()) {
/* 1183 */       log.debug("开始执行原子组件: queryInGroup");
/*      */     }
/* 1185 */     if ((args == null) || (args.size() == 0)) {
/* 1186 */       throw new HiException("215110");
/*      */     }
/*      */ 
/* 1190 */     String sqlCmd = HiArgUtils.getFirstNotNull(args);
/*      */ 
/* 1193 */     String recName = args.get("RecordName");
/* 1194 */     if (StringUtils.isEmpty(recName)) {
/* 1195 */       recName = "ROOT";
/*      */     }
/*      */ 
/* 1198 */     HiETF etfBody = (HiETF)msg.getBody();
/* 1199 */     String sqlSentence = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + sqlCmd);
/*      */ 
/* 1201 */     String sqlFldLst = HiArgUtils.getString(ctx, "FIELDS." + sqlCmd);
/*      */ 
/* 1204 */     List etfFields = HiArgUtils.getFldValues(etfBody, sqlFldLst, "|");
/*      */ 
/* 1206 */     if (log.isDebugEnabled()) {
/* 1207 */       log.debug("QueryInGroup执行SQL语句: [" + sqlSentence + "]");
/*      */     }
/*      */ 
/* 1210 */     List queryRs = ctx.getDataBaseUtil().execQueryBind(sqlSentence, etfFields);
/* 1211 */     if ((queryRs == null) || (queryRs.size() == 0)) {
/* 1212 */       etfBody.setChildValue("REC_NUM", "0");
/* 1213 */       return 2;
/*      */     }
/*      */ 
/* 1216 */     Map queryRec = null;
/* 1217 */     Map.Entry recEntry = null;
/* 1218 */     Iterator recIt = null;
/*      */ 
/* 1221 */     int i = 0;
/* 1222 */     for (i = 0; i < queryRs.size(); ++i) {
/* 1223 */       queryRec = (Map)queryRs.get(i);
/* 1224 */       recIt = queryRec.entrySet().iterator();
/*      */ 
/* 1226 */       HiETF grpRoot = etfBody.addNode(recName + "_" + (i + 1));
/*      */ 
/* 1228 */       while (recIt.hasNext()) {
/* 1229 */         recEntry = (Map.Entry)recIt.next();
/* 1230 */         grpRoot.setChildValue((String)recEntry.getKey(), (String)recEntry.getValue());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1236 */     etfBody.setChildValue("REC_NUM", String.valueOf(i));
/*      */ 
/* 1238 */     return 0;
/*      */   }
/*      */ 
/*      */   public int QueryInGroup(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1262 */     HiMessage msg = ctx.getCurrentMsg();
/* 1263 */     Logger log = HiLog.getLogger(msg);
/* 1264 */     if (log.isDebugEnabled()) {
/* 1265 */       log.debug("开始执行原子组件: queryInGroup");
/*      */     }
/* 1267 */     if ((args == null) || (args.size() == 0)) {
/* 1268 */       throw new HiException("215110");
/*      */     }
/*      */ 
/* 1272 */     String sqlCmd = HiArgUtils.getFirstNotNull(args);
/*      */ 
/* 1275 */     String recName = args.get("RecordName");
/* 1276 */     if (StringUtils.isEmpty(recName)) {
/* 1277 */       recName = "ROOT";
/*      */     }
/*      */ 
/* 1280 */     HiETF etfBody = (HiETF)msg.getBody();
/* 1281 */     boolean escape = args.getBoolean("escape");
/* 1282 */     String sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, sqlCmd, etfBody, escape);
/*      */ 
/* 1284 */     if (log.isDebugEnabled()) {
/* 1285 */       log.debug("QueryInGroup执行SQL语句: [" + sqlSentence + "]");
/*      */     }
/*      */ 
/* 1288 */     List queryRs = ctx.getDataBaseUtil().execQuery(sqlSentence);
/* 1289 */     if ((queryRs == null) || (queryRs.size() == 0)) {
/* 1290 */       etfBody.setChildValue("REC_NUM", "0");
/* 1291 */       return 2;
/*      */     }
/*      */ 
/* 1294 */     Map queryRec = null;
/* 1295 */     Map.Entry recEntry = null;
/* 1296 */     Iterator recIt = null;
/*      */ 
/* 1299 */     int i = 0;
/* 1300 */     for (i = 0; i < queryRs.size(); ++i) {
/* 1301 */       queryRec = (Map)queryRs.get(i);
/* 1302 */       recIt = queryRec.entrySet().iterator();
/*      */ 
/* 1304 */       HiETF grpRoot = etfBody.addNode(recName + "_" + (i + 1));
/*      */ 
/* 1306 */       while (recIt.hasNext()) {
/* 1307 */         recEntry = (Map.Entry)recIt.next();
/* 1308 */         grpRoot.setChildValue((String)recEntry.getKey(), (String)recEntry.getValue());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1314 */     etfBody.setChildValue("REC_NUM", String.valueOf(i));
/*      */ 
/* 1316 */     return 0;
/*      */   }
/*      */ 
/*      */   public int QueryInGroupExt(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1339 */     HiMessage msg = ctx.getCurrentMsg();
/* 1340 */     Logger log = HiLog.getLogger(msg);
/* 1341 */     if (log.isDebugEnabled()) {
/* 1342 */       log.debug("开始执行原子组件: queryInGroup");
/*      */     }
/* 1344 */     if ((argsMap == null) || (argsMap.size() == 0)) {
/* 1345 */       throw new HiException("215110");
/*      */     }
/* 1347 */     String aSqlName = argsMap.get("CndSts");
/* 1348 */     String tblnam = argsMap.get("TblNam").toUpperCase();
/*      */ 
/* 1350 */     String cndsts = HiDbtSqlHelper.getDynSentence(ctx, aSqlName);
/*      */ 
/* 1352 */     String recNam = "ROOT";
/* 1353 */     if (argsMap.contains("RecordName")) {
/* 1354 */       recNam = argsMap.get("RecordName");
/*      */     }
/* 1356 */     int rsSize = HiDbtUtils.dbtextquery_limit(tblnam, cndsts, -1, msg.getETFBody(), recNam, ctx);
/*      */ 
/* 1358 */     if (rsSize == 0) {
/* 1359 */       return 2;
/*      */     }
/* 1361 */     msg.getETFBody().setChildValue("RecNum", String.valueOf(rsSize));
/* 1362 */     return 0;
/*      */   }
/*      */ 
/*      */   public int ReadRecordExt(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1385 */     HiMessage msg = ctx.getCurrentMsg();
/* 1386 */     Logger log = HiLog.getLogger(msg);
/* 1387 */     if (log.isDebugEnabled()) {
/* 1388 */       log.debug("开始执行原子组件: queryInGroup");
/*      */     }
/* 1390 */     if ((argsMap == null) || (argsMap.size() < 2)) {
/* 1391 */       throw new HiException("215110");
/*      */     }
/* 1393 */     String aSqlName = argsMap.get("CndSts");
/* 1394 */     String tblnam = argsMap.get("TblNam").toUpperCase();
/*      */ 
/* 1396 */     String cndsts = HiDbtSqlHelper.getDynSentence(ctx, aSqlName);
/*      */ 
/* 1398 */     int rsSize = HiDbtUtils.dbtextquery_limit(tblnam, cndsts, 1, msg.getETFBody(), ctx);
/*      */ 
/* 1400 */     if (rsSize == 0)
/* 1401 */       return 2;
/* 1402 */     return 0;
/*      */   }
/*      */ 
/*      */   public int DeleteRecordExt(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1424 */     HiMessage msg = ctx.getCurrentMsg();
/* 1425 */     Logger log = HiLog.getLogger(msg);
/* 1426 */     if (log.isDebugEnabled()) {
/* 1427 */       log.debug("开始执行原子组件: DeleteRecordExt");
/*      */     }
/* 1429 */     if ((args == null) || (args.size() != 2))
/*      */     {
/* 1431 */       throw new HiException("215110");
/*      */     }
/*      */ 
/* 1434 */     int ret = 0;
/*      */ 
/* 1439 */     HiETF etfBody = (HiETF)msg.getBody();
/*      */ 
/* 1443 */     String sqlTblNam = HiArgUtils.getStringNotNull(args, "TblNam").toUpperCase();
/* 1444 */     String sqlCndSts = HiArgUtils.getStringNotNull(args, "CndSts").toUpperCase();
/*      */ 
/* 1446 */     if (log.isDebugEnabled()) {
/* 1447 */       log.debug("DeleteRecordExt: 表名[" + sqlTblNam + "] 执行语句别名[" + sqlCndSts + "]");
/*      */     }
/*      */ 
/* 1450 */     boolean escape = args.getBoolean("escape");
/*      */ 
/* 1452 */     String sqlCond = HiDbtSqlHelper.getDynSentence(ctx, sqlCndSts, etfBody, escape);
/*      */ 
/* 1455 */     if (log.isDebugEnabled()) {
/* 1456 */       log.debug("DeleteRecordExt执行语句条件部分: [" + sqlCond + "]");
/*      */     }
/* 1458 */     if (HiDbtUtils.dbtextdelreccon(sqlTblNam, sqlCond, etfBody, ctx) == 0) {
/* 1459 */       ret = 2;
/*      */     }
/*      */ 
/* 1462 */     return ret;
/*      */   }
/*      */ 
/*      */   public int ConnectDB(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1481 */     ctx.getDataBaseUtil().getConnection();
/* 1482 */     return 0;
/*      */   }
/*      */ 
/*      */   public int DisconnectDB(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1501 */     ctx.getDataBaseUtil().close();
/* 1502 */     return 0;
/*      */   }
/*      */ 
/*      */   public int UpdateRecordExt(HiATLParam args, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*      */     String sqlTblNam;
/*      */     String sqlCndSts;
/*      */     String sqlCond;
/* 1529 */     HiMessage msg = ctx.getCurrentMsg();
/* 1530 */     Logger log = HiLog.getLogger(msg);
/* 1531 */     if (log.isDebugEnabled()) {
/* 1532 */       log.debug("开始执行原子组件: UpdateRecordExt");
/*      */     }
/* 1534 */     if ((args == null) || (args.size() < 2)) {
/* 1535 */       throw new HiException("215110");
/*      */     }
/*      */ 
/* 1538 */     int ret = 0;
/*      */ 
/* 1544 */     HiETF etfBody = (HiETF)msg.getBody();
/* 1545 */     boolean escape = args.getBoolean("escape");
/* 1546 */     String groupName = HiArgUtils.getStringNotNull(args, "GrpNam");
/*      */ 
/* 1548 */     if (StringUtils.isBlank(groupName))
/*      */     {
/* 1551 */       sqlTblNam = HiArgUtils.getStringNotNull(args, "TblNam").toUpperCase();
/*      */ 
/* 1553 */       sqlCndSts = HiArgUtils.getStringNotNull(args, "CndSts").toUpperCase();
/*      */ 
/* 1556 */       if (log.isDebugEnabled()) {
/* 1557 */         log.debug("updateRecord: 表名[" + sqlTblNam + "] 执行语句别名[" + sqlCndSts + "]");
/*      */       }
/*      */ 
/* 1562 */       sqlCond = HiDbtSqlHelper.getDynSentence(ctx, sqlCndSts, etfBody, escape);
/*      */ 
/* 1565 */       if (log.isDebugEnabled()) {
/* 1566 */         log.debug("updateRecordExt执行语句条件部分: [" + sqlCond + "]");
/*      */       }
/* 1568 */       if (HiDbtUtils.dbtextupdreccon(sqlTblNam, sqlCond, etfBody, ctx) == 0) {
/* 1569 */         ret = 2;
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1574 */       sqlTblNam = HiArgUtils.getStringNotNull(args, "TblNam").toUpperCase();
/*      */ 
/* 1576 */       sqlCndSts = HiArgUtils.getStringNotNull(args, "CndSts").toUpperCase();
/*      */ 
/* 1579 */       if (log.isDebugEnabled()) {
/* 1580 */         log.debug("updateRecord: 表名[" + sqlTblNam + "] 执行语句别名[" + sqlCndSts + "]");
/*      */       }
/*      */ 
/* 1584 */       sqlCond = HiArgUtils.getStringNotNull(ctx, "SENTENCE." + sqlCndSts);
/*      */ 
/* 1586 */       String sqlFldLst = HiArgUtils.getString(ctx, "FIELDS." + sqlCndSts);
/*      */ 
/* 1592 */       boolean isIgnore = args.getBoolean("Ignore");
/*      */ 
/* 1595 */       String rootFldLst = args.get("FldLst");
/*      */ 
/* 1598 */       Map refEtfFlds = null;
/* 1599 */       if (rootFldLst != null) {
/* 1600 */         refEtfFlds = HiArgUtils.getEtfFields(etfBody, rootFldLst, "|");
/*      */       }
/*      */ 
/* 1603 */       HiETF groupNodeRec = null;
/* 1604 */       List groupNodes = etfBody.getGrandChildFuzzyEnd(groupName + "_");
/* 1605 */       Iterator grpIt = groupNodes.iterator();
/*      */ 
/* 1607 */       Map.Entry refEtf = null;
/*      */ 
/* 1609 */       while (grpIt.hasNext()) {
/* 1610 */         groupNodeRec = ((HiETF)grpIt.next()).cloneNode();
/*      */ 
/* 1612 */         if (refEtfFlds != null) {
/* 1613 */           Iterator refEtfIt = refEtfFlds.entrySet().iterator();
/*      */ 
/* 1615 */           while (refEtfIt.hasNext()) {
/* 1616 */             refEtf = (Map.Entry)refEtfIt.next();
/* 1617 */             groupNodeRec.setChildValue((String)refEtf.getKey(), (String)refEtf.getValue());
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1622 */         sqlCond = HiDbtSqlHelper.replace(ctx, groupNodeRec, sqlCond, sqlFldLst, "|");
/*      */         try
/*      */         {
/* 1627 */           if (log.isDebugEnabled()) {
/* 1628 */             log.debug("updateRecordExt执行语句: [" + sqlCond + "]");
/*      */           }
/* 1630 */           if (HiDbtUtils.dbtextupdreccon(sqlTblNam, sqlCond, groupNodeRec, ctx) == 0)
/*      */           {
/* 1632 */             if (log.isInfoEnabled()) {
/* 1633 */               log.info(sm.getString("215021", sqlTblNam));
/*      */             }
/*      */ 
/* 1637 */             ret = 2;
/* 1638 */             if (!(isIgnore))
/* 1639 */               return ret;
/*      */           }
/*      */         }
/*      */         catch (HiException e)
/*      */         {
/* 1644 */           if (log.isInfoEnabled()) {
/* 1645 */             log.info(sm.getString("215021", sqlTblNam));
/*      */           }
/*      */ 
/* 1648 */           ret = 2;
/* 1649 */           if (!(isIgnore)) {
/* 1650 */             throw e;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 1655 */       groupNodes.clear();
/*      */     }
/* 1657 */     return ret;
/*      */   }
/*      */ }