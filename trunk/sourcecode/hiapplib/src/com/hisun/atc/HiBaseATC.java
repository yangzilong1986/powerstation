/*      */ package com.hisun.atc;
/*      */ 
/*      */ import com.hisun.atc.common.HiArgUtils;
/*      */ import com.hisun.atc.common.HiAtcLib;
/*      */ import com.hisun.atc.common.HiDbtSqlHelper;
/*      */ import com.hisun.atc.common.HiTriggerMsg;
/*      */ import com.hisun.atc.common.HiWorkDateHelper;
/*      */ import com.hisun.database.HiDataBaseUtil;
/*      */ import com.hisun.dispatcher.HiRouterOut;
/*      */ import com.hisun.engine.invoke.impl.HiAttributesHelper;
/*      */ import com.hisun.engine.invoke.impl.HiItemHelper;
/*      */ import com.hisun.engine.invoke.impl.HiRunStatus;
/*      */ import com.hisun.exception.HiAppException;
/*      */ import com.hisun.exception.HiException;
/*      */ import com.hisun.exception.HiResponseException;
/*      */ import com.hisun.hiexpression.HiExpFactory;
/*      */ import com.hisun.hiexpression.HiExpression;
/*      */ import com.hisun.hilib.HiATLParam;
/*      */ import com.hisun.hilog4j.HiLog;
/*      */ import com.hisun.hilog4j.Logger;
/*      */ import com.hisun.message.HiETF;
/*      */ import com.hisun.message.HiMessage;
/*      */ import com.hisun.message.HiMessageContext;
/*      */ import com.hisun.message.HiMessageHelper;
/*      */ import com.hisun.stat.util.HiStats;
/*      */ import com.hisun.util.HiDateUtils;
/*      */ import com.hisun.util.HiStringManager;
/*      */ import com.hisun.util.HiStringUtils;
/*      */ import com.hisun.util.HiSystemUtils;
/*      */ import com.hisun.util.HiXmlHelper;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import org.apache.commons.lang.StringUtils;
/*      */ import org.apache.commons.lang.math.NumberUtils;
/*      */ import org.dom4j.Attribute;
/*      */ import org.dom4j.Element;
/*      */ 
/*      */ public class HiBaseATC
/*      */ {
/*      */   private HiStringManager sm;
/*      */ 
/*      */   public HiBaseATC()
/*      */   {
/*   53 */     this.sm = HiStringManager.getManager();
/*      */   }
/*      */ 
/*      */   public int SendMessage(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*   67 */     String reqMsgName = argsMap.get("ReqMsg");
/*      */ 
/*   69 */     if (StringUtils.isEmpty(reqMsgName)) {
/*   70 */       reqMsgName = "MSG";
/*      */     }
/*      */ 
/*   73 */     String reqETFName = argsMap.get("ReqETF");
/*   74 */     if (StringUtils.isEmpty(reqETFName)) {
/*   75 */       reqETFName = "ETF";
/*      */     }
/*   77 */     HiMessage msg = (HiMessage)ctx.getBaseSource(reqMsgName);
/*   78 */     if (msg == null) {
/*   79 */       msg = ctx.getCurrentMsg();
/*      */     }
/*      */ 
/*   82 */     HiETF etf = (HiETF)ctx.getBaseSource(reqETFName);
/*   83 */     HiMessage msg1 = new HiMessage(msg);
/*   84 */     if (etf != null) {
/*   85 */       msg1.setBody(etf);
/*      */     }
/*   87 */     HiRouterOut.asyncProcess(msg1);
/*   88 */     return 0;
/*      */   }
/*      */ 
/*      */   public int CallMessage(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  111 */     if (!(argsMap.contains("TimeOut"))) {
/*  112 */       throw new HiException("220026", "TimeOut");
/*      */     }
/*      */ 
/*  115 */     int tmOut = argsMap.getInt("TimeOut");
/*      */ 
/*  117 */     String rspMsgName = HiArgUtils.getStringNotNull(argsMap, "RspMsg");
/*      */ 
/*  119 */     String rspETFName = argsMap.get("RspETF");
/*      */ 
/*  121 */     String reqMsgName = argsMap.get("ReqMsg");
/*      */ 
/*  123 */     if (StringUtils.isEmpty(reqMsgName)) {
/*  124 */       reqMsgName = "MSG";
/*      */     }
/*      */ 
/*  127 */     String reqETFName = argsMap.get("ReqETF");
/*  128 */     if (StringUtils.isEmpty(reqETFName)) {
/*  129 */       reqETFName = "ETF";
/*      */     }
/*      */ 
/*  132 */     HiMessage msg = (HiMessage)ctx.getBaseSource(reqMsgName);
/*  133 */     if (msg == null) {
/*  134 */       msg = ctx.getCurrentMsg();
/*      */     }
/*      */ 
/*  137 */     HiETF etf = (HiETF)ctx.getBaseSource(reqETFName);
/*  138 */     HiMessage msg1 = new HiMessage(msg);
/*  139 */     if (etf != null) {
/*  140 */       msg1.setBody(etf);
/*      */     }
/*      */ 
/*  143 */     HiMessageHelper.setMessageTmOut(msg1, tmOut);
/*  144 */     HiRouterOut.syncProcess(msg1);
/*  145 */     ctx.setBaseSource(rspMsgName, msg1);
/*  146 */     ctx.setBaseSource(rspETFName, msg1.getBody());
/*  147 */     return 0;
/*      */   }
/*      */ 
/*      */   public int PutResponse(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  159 */     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
/*  160 */     runStatus.setResponse(true);
/*  161 */     throw new HiResponseException();
/*      */   }
/*      */ 
/*      */   public int SetNoResponse(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  173 */     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
/*  174 */     runStatus.setResponse(false);
/*  175 */     return 0;
/*      */   }
/*      */ 
/*      */   public int SetInitResponse(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  186 */     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
/*  187 */     runStatus.setResponse(true);
/*  188 */     return 0;
/*      */   }
/*      */ 
/*      */   public int CodeSwitching(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  205 */     HiMessage msg = ctx.getCurrentMsg();
/*  206 */     Logger log = HiLog.getLogger(msg);
/*  207 */     String strAliasName = argsMap.get("DATSRC");
/*  208 */     if (StringUtils.isEmpty(strAliasName)) {
/*  209 */       throw new HiException("220002");
/*      */     }
/*  211 */     String strSourName = argsMap.get("SOURNAM");
/*  212 */     if (StringUtils.isEmpty(strSourName)) {
/*  213 */       throw new HiException("220003");
/*      */     }
/*      */ 
/*  216 */     String strSourETF = msg.getETFBody().getGrandChildValue(strSourName);
/*      */ 
/*  218 */     String strDestName = argsMap.get("DESTNAM");
/*  219 */     if (StringUtils.isEmpty(strDestName)) {
/*  220 */       throw new HiException("220004");
/*      */     }
/*  222 */     String strTableName = argsMap.get("TBLNAM");
/*      */ 
/*  224 */     Element rootNode = (Element)ctx.getProperty("CONFIGDECLARE", strAliasName);
/*      */ 
/*  227 */     if (rootNode == null) {
/*  228 */       throw new HiException("220314", strAliasName);
/*      */     }
/*      */ 
/*  231 */     Element tableNode = null;
/*  232 */     if (StringUtils.isEmpty(strTableName))
/*  233 */       tableNode = rootNode;
/*      */     else {
/*  235 */       tableNode = HiXmlHelper.getNodeByAttr(rootNode, "Table", "name", strTableName);
/*      */     }
/*      */ 
/*  239 */     if (tableNode == null) {
/*  240 */       throw new HiException("220327", strAliasName, strTableName);
/*      */     }
/*      */ 
/*  244 */     Iterator elementIter = tableNode.elementIterator();
/*  245 */     String defaultValue = null;
/*  246 */     String destValue = null;
/*  247 */     while (elementIter.hasNext()) {
/*  248 */       Element item = (Element)elementIter.next();
/*  249 */       String strNodeName = item.getName();
/*  250 */       if ("Default".equals(strNodeName)) {
/*  251 */         defaultValue = item.attribute(0).getValue();
/*      */       }
/*      */ 
/*  254 */       if (item.attributeCount() < 2)
/*      */         continue;
/*  256 */       String tmp1 = item.attribute(0).getValue();
/*  257 */       String tmp2 = item.attribute(1).getValue();
/*  258 */       if (StringUtils.equals(tmp1, strSourETF)) {
/*  259 */         destValue = tmp2;
/*  260 */         break; }
/*  261 */       if (StringUtils.equals(tmp2, strSourETF)) {
/*  262 */         destValue = tmp1;
/*  263 */         break;
/*      */       }
/*      */     }
/*      */ 
/*  267 */     if (destValue == null) {
/*  268 */       destValue = defaultValue;
/*      */     }
/*      */ 
/*  271 */     if (destValue == null) {
/*  272 */       throw new HiException("220328");
/*      */     }
/*      */ 
/*  275 */     msg.getETFBody().setGrandChildNode(strDestName, destValue);
/*      */ 
/*  277 */     if (log.isInfoEnabled()) {
/*  278 */       log.info(this.sm.getString("HiBaseATC.CodeSwitching00", strSourName, strSourETF, strDestName, destValue));
/*      */     }
/*      */ 
/*  282 */     return 0;
/*      */   }
/*      */ 
/*      */   public int Sleep(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  295 */     int iSec = 1;
/*      */     try {
/*  297 */       if (argsMap.contains("SLEEPTIME")) {
/*  298 */         Integer sec = new Integer(argsMap.get("SLEEPTIME"));
/*  299 */         iSec = sec.intValue();
/*      */       }
/*  301 */       Thread.currentThread(); Thread.sleep(iSec * 1000);
/*      */     } catch (Exception e) {
/*  303 */       throw HiException.makeException(e);
/*      */     }
/*  305 */     return 0;
/*      */   }
/*      */ 
/*      */   public int Lock(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  329 */     HiMessage mess = ctx.getCurrentMsg();
/*  330 */     Logger log = HiLog.getLogger(mess);
/*      */ 
/*  332 */     String recKey = argsMap.get("RECKEY");
/*  333 */     if (recKey == null) {
/*  334 */       throw new HiException("220026", "RECKEY");
/*      */     }
/*      */ 
/*  337 */     if (recKey.length() > 80) {
/*  338 */       throw new HiException("220026", "RECKEY参数值有误, 长度不允许大于80字节");
/*      */     }
/*      */ 
/*  343 */     String timeOut = argsMap.get("TIMOUT");
/*  344 */     if (timeOut == null) {
/*  345 */       timeOut = "0";
/*      */     }
/*      */ 
/*  349 */     String autoUnlockFlg = argsMap.get("AUTOUNLOCK");
/*  350 */     if (autoUnlockFlg == null) {
/*  351 */       autoUnlockFlg = "no";
/*      */     }
/*      */ 
/*  354 */     int ret = 0;
/*      */     try {
/*  356 */       ret = HiAtcLib.appLock(recKey, timeOut, ctx);
/*      */     } catch (HiException e) {
/*  358 */       if (log.isInfoEnabled()) {
/*  359 */         log.info("Lock: 登记锁失败. aRecKey=[" + recKey + "]");
/*      */       }
/*      */ 
/*  362 */       throw e;
/*      */     }
/*      */ 
/*  365 */     if ((ret == 0) && (autoUnlockFlg.equals("yes")) && 
/*  366 */       (log.isInfoEnabled())) {
/*  367 */       log.info("Lock: 登记交易结束时, 自动解锁. aRecKey=[" + recKey + "]");
/*      */     }
/*      */ 
/*  372 */     return ret;
/*      */   }
/*      */ 
/*      */   public int Trylock(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  389 */     HiMessage mess = ctx.getCurrentMsg();
/*  390 */     Logger log = HiLog.getLogger(mess);
/*      */ 
/*  393 */     String recKey = argsMap.get("RECKEY");
/*  394 */     if (recKey == null) {
/*  395 */       throw new HiException("220026", "RECKEY");
/*      */     }
/*      */ 
/*  398 */     if (recKey.length() > 80) {
/*  399 */       throw new HiException("220026", "RECKEY参数值有误, 长度不允许大于80字节");
/*      */     }
/*      */ 
/*  403 */     int ret = 0;
/*      */     try
/*      */     {
/*  406 */       ret = HiAtcLib.tryLock(recKey, ctx);
/*      */     } catch (HiException e) {
/*  408 */       if (log.isInfoEnabled()) {
/*  409 */         log.info("TryLock: 检查锁失败, aRecKey=[" + recKey + "]");
/*      */       }
/*  411 */       ret = -1;
/*  412 */       throw e;
/*      */     }
/*  414 */     return ret;
/*      */   }
/*      */ 
/*      */   public int Unlock(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  428 */     HiMessage mess = ctx.getCurrentMsg();
/*  429 */     Logger log = HiLog.getLogger(mess);
/*      */ 
/*  432 */     String recKey = argsMap.get("RECKEY");
/*  433 */     if (recKey == null) {
/*  434 */       throw new HiException("220026", "RECKEY");
/*      */     }
/*      */ 
/*  437 */     if (recKey.length() > 80) {
/*  438 */       throw new HiException("220026", "RECKEY参数值有误, 长度不允许大于80字节");
/*      */     }
/*      */ 
/*  442 */     int ret = 0;
/*      */     try
/*      */     {
/*  445 */       ret = HiAtcLib.unAppLock(recKey, ctx);
/*      */     } catch (HiException e) {
/*  447 */       if (log.isInfoEnabled()) {
/*  448 */         log.info("UnLock: 解锁失败, aRecKey=[" + recKey + "]");
/*      */       }
/*  450 */       throw e;
/*      */     }
/*  452 */     return ret;
/*      */   }
/*      */ 
/*      */   public int Monitor(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  466 */     throw new HiException("not implement");
/*      */   }
/*      */ 
/*      */   public int isExistNode(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  486 */     HiMessage mess = ctx.getCurrentMsg();
/*  487 */     if ((argsMap == null) || (argsMap.size() == 0))
/*      */     {
/*  489 */       throw new HiException("220026", "参数为空");
/*      */     }
/*      */ 
/*  492 */     HiETF etf = (HiETF)mess.getBody();
/*  493 */     if (etf == null)
/*      */     {
/*  495 */       throw new HiException("220026", "ETF为null.");
/*      */     }
/*      */ 
/*  503 */     if (etf.getGrandChildValue(argsMap.getValue(0)) == null) {
/*  504 */       return 0;
/*      */     }
/*      */ 
/*  507 */     return 1;
/*      */   }
/*      */ 
/*      */   public int getValue(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  527 */     HiMessage mess = ctx.getCurrentMsg();
/*  528 */     if ((argsMap == null) || (argsMap.size() == 0))
/*      */     {
/*  530 */       throw new HiException("220026", "参数为空");
/*      */     }
/*      */ 
/*  533 */     HiETF etf = (HiETF)mess.getBody();
/*  534 */     if (etf == null)
/*      */     {
/*  536 */       throw new HiException("220026", "消息体中的ETF为null.");
/*      */     }
/*      */ 
/*  541 */     String sourName = argsMap.get("SourName");
/*      */ 
/*  543 */     if (sourName == null)
/*      */     {
/*  545 */       throw new HiException("220026", "参数错误, 没有指定的源域.");
/*      */     }
/*      */ 
/*  548 */     String destName = argsMap.get("DestName");
/*  549 */     if (destName == null)
/*      */     {
/*  551 */       throw new HiException("220026", "参数错误, 没有指定保存的目标域.");
/*      */     }
/*      */ 
/*  554 */     etf.setGrandChildNode(destName, etf.getGrandChildValue(sourName));
/*      */ 
/*  556 */     return 0;
/*      */   }
/*      */ 
/*      */   public int SetValue(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  576 */     HiMessage mess = ctx.getCurrentMsg();
/*  577 */     String fieldName = HiArgUtils.getStringNotNull(argsMap, "FieldName");
/*  578 */     String fieldValue = HiArgUtils.getStringNotNull(argsMap, "FieldValue");
/*  579 */     HiETF etf = (HiETF)mess.getBody();
/*  580 */     HiItemHelper.addEtfItem(mess, fieldName, fieldValue);
/*  581 */     return 0;
/*      */   }
/*      */ 
/*      */   public int IsWorkDate(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  595 */     HiETF etfRoot = ctx.getCurrentMsg().getETFBody();
/*      */ 
/*  598 */     String areCod = argsMap.get("AreCod");
/*  599 */     if (areCod == null) {
/*  600 */       areCod = etfRoot.getChildValue("NodNo");
/*  601 */       if (areCod == null) {
/*  602 */         throw new HiException("220026", "从参数或ETF节点[NodeNo]中取地区码失败");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  608 */     String aDat = argsMap.get("Date");
/*  609 */     if (aDat == null) {
/*  610 */       throw new HiException("220026", "取参数[Date]失败!");
/*      */     }
/*  612 */     if (!(HiDateUtils.chkDateFormat(aDat))) {
/*  613 */       throw new HiException("220026", "日期参数[Date]不是合法格式日期!输入日期=[" + aDat + "]");
/*      */     }
/*      */ 
/*  618 */     String[] hldFlgInfo = HiWorkDateHelper.getHoliday(ctx, areCod);
/*  619 */     if ((hldFlgInfo == null) || (hldFlgInfo.length <= 1)) {
/*  620 */       throw new HiException("220097", "IsWorkDate: 取假期表失败");
/*      */     }
/*      */ 
/*  625 */     int seq = HiWorkDateHelper.date2pos(aDat, hldFlgInfo[0], hldFlgInfo[1].length() / 372);
/*      */ 
/*  628 */     if (seq == -1)
/*      */     {
/*  631 */       return 2;
/*      */     }
/*      */ 
/*  635 */     if (hldFlgInfo[1].charAt(seq) == 'W') {
/*  636 */       return 1;
/*      */     }
/*      */ 
/*  639 */     return 0;
/*      */   }
/*      */ 
/*      */   public int InitTransaction(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  659 */     HiMessage mess = ctx.getCurrentMsg();
/*  660 */     Logger log = HiLog.getLogger(mess);
/*  661 */     HiETF etfRoot = (HiETF)mess.getBody();
/*  662 */     String strActDat = null;
/*      */ 
/*  664 */     String sqlCmd = "SELECT ACC_DT,SYS_ID FROM PUBPLTINF";
/*  665 */     List lRec = ctx.getDataBaseUtil().execQuery(sqlCmd);
/*      */ 
/*  667 */     if ((lRec == null) || (lRec.size() == 0)) {
/*  668 */       log.fatal(this.sm.getString("HiTransactionCTLImpl.GetSysInfoError"));
/*  669 */       throw new HiException("211007");
/*      */     }
/*      */ 
/*  672 */     HashMap tmp = (HashMap)lRec.get(0);
/*      */ 
/*  674 */     etfRoot.setChildValue("SYS_ID", (String)tmp.get("SYS_ID"));
/*      */ 
/*  678 */     strActDat = (String)tmp.get("ACC_DT");
/*  679 */     etfRoot.setChildValue("ACC_DT", strActDat);
/*  680 */     ctx.setBaseSource("SYS_ID", (String)tmp.get("SYS_ID"));
/*  681 */     ctx.setBaseSource("ACC_DT", strActDat);
/*      */ 
/*  683 */     if (log.isInfoEnabled()) {
/*  684 */       log.info(this.sm.getString("HiTransactionCTLImpl.GetActDat", strActDat));
/*  685 */       log.info(this.sm.getString("HiTransactionCTLImpl.GetAppCode", ctx.getStrProp("app_code")));
/*      */     }
/*      */ 
/*  689 */     String AplSub = etfRoot.getChildValue("APP_SUB");
/*  690 */     if (log.isInfoEnabled()) {
/*  691 */       log.info("APP_SUB:[" + AplSub + "]");
/*      */     }
/*      */ 
/*  694 */     String AplCls = etfRoot.getChildValue("APP_CLS");
/*  695 */     if (AplCls == null) {
/*  696 */       throw new HiException("220058", "APP_CLS");
/*      */     }
/*      */ 
/*  700 */     if (log.isInfoEnabled()) {
/*  701 */       log.info("取AplCls,值为:[" + AplCls + "]");
/*      */     }
/*      */ 
/*  704 */     String BrNo = etfRoot.getChildValue("BR_NO");
/*  705 */     if (BrNo == null) {
/*  706 */       if (log.isInfoEnabled()) {
/*  707 */         log.info(" 取不到分行代码,不读取业务信息");
/*      */       }
/*  709 */       return 0;
/*      */     }
/*      */ 
/*  712 */     String sqlcmd = HiStringUtils.format("SELECT BR_SEQ,BR_NM,SYS_ID FROM PUBBCHLST WHERE BR_NO='%s'", BrNo);
/*      */ 
/*  716 */     lRec = ctx.getDataBaseUtil().execQuery(sqlcmd);
/*  717 */     if (lRec != null) if (lRec.size() != 0)
/*      */       {
/*  720 */         tmp = (HashMap)lRec.get(0);
/*  721 */         etfRoot.setChildValue("BR_SEQ", (String)tmp.get("BR_SEQ"));
/*      */ 
/*  723 */         etfRoot.setChildValue("BR_NM", (String)tmp.get("BR_NM"));
/*      */ 
/*  725 */         etfRoot.setChildValue("SYS_ID", (String)tmp.get("SYS_ID"));
/*      */ 
/*  727 */         ctx.setBaseSource("SYS_ID", (String)tmp.get("SYS_ID"));
/*      */       }
/*      */ 
/*      */ 
/*  734 */     String BusTyp = null;
/*      */ 
/*  736 */     sqlcmd = HiStringUtils.format("SELECT BUS_TYP FROM PUBAPLBUS WHERE BR_NO='%s' AND APP_CLS='%s' AND (APP_SUB='%s' OR APP_SUB=' ')", BrNo, AplCls, AplSub);
/*      */ 
/*  741 */     lRec = ctx.getDataBaseUtil().execQuery(sqlcmd);
/*  742 */     if (lRec.size() != 0) {
/*  743 */       tmp = (HashMap)lRec.get(0);
/*  744 */       BusTyp = (String)tmp.get("BUS_TYP");
/*  745 */       ctx.setBaseSource("BUS_TYP", BusTyp);
/*  746 */       if (log.isInfoEnabled()) {
/*  747 */         log.info("--->业务类型 = " + BusTyp);
/*      */       }
/*      */     }
/*      */ 
/*  751 */     if (!(StringUtils.isEmpty(BusTyp))) {
/*  752 */       etfRoot.setChildValue("BUS_TYP", BusTyp);
/*      */ 
/*  755 */       sqlcmd = HiStringUtils.format("SELECT ONL_TBL FROM PUBJNLDEF WHERE BR_NO ='%s' AND BUS_TYP = '%s'", BrNo, BusTyp);
/*      */ 
/*  762 */       lRec = ctx.getDataBaseUtil().execQuery(sqlcmd);
/*  763 */       if (lRec.size() != 0) {
/*  764 */         tmp = (HashMap)lRec.get(0);
/*  765 */         String OnlTbl = (String)tmp.get("ONL_TBL");
/*      */ 
/*  767 */         if (log.isInfoEnabled()) {
/*  768 */           log.info("--->流水表 = " + OnlTbl);
/*      */         }
/*  770 */         ctx.setJnlTable(OnlTbl);
/*      */ 
/*  772 */         ctx.setBaseSource("LstTab", OnlTbl);
/*  773 */         HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
/*  774 */         if (attr.isNoChk())
/*  775 */           etfRoot.setChildValue("MST_CHK", "0");
/*      */         else {
/*  777 */           etfRoot.setChildValue("MST_CHK", "1");
/*      */         }
/*      */ 
/*  780 */         etfRoot.setChildValue("ITG_TYP", attr.integtype());
/*  781 */         etfRoot.setChildValue("TXN_TYP", attr.getTxnTyp());
/*      */ 
/*  783 */         etfRoot.setChildValue("FRSP_CD", "211007");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  789 */     return 0;
/*      */   }
/*      */ 
/*      */   public int printEtfToLog(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  808 */     HiMessage mess = ctx.getCurrentMsg();
/*  809 */     Logger log = HiLog.getLogger(mess);
/*      */ 
/*  811 */     if (log.isDebugEnabled()) {
/*  812 */       log.debug("----------------------printEtfToLog begin ------------------------");
/*      */ 
/*  814 */       log.debug(mess.getBody());
/*      */ 
/*  816 */       log.debug("----------------------printEtfToLog end ------------------------");
/*      */     }
/*      */ 
/*  819 */     return 0;
/*      */   }
/*      */ 
/*      */   public int CompareRecordCondition(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/*  861 */     HiMessage mess = ctx.getCurrentMsg();
/*  862 */     if ((argsMap.size() < 2) || (argsMap.size() > 20)) {
/*  863 */       throw new HiAppException(-1, "", "参数不正确");
/*      */     }
/*      */ 
/*  866 */     StringBuffer sb = new StringBuffer("SELECT ");
/*      */ 
/*  868 */     String strSQL = null;
/*  869 */     for (int i = 0; i < argsMap.size(); ++i)
/*      */     {
/*  871 */       String strArgName = argsMap.getName(i);
/*  872 */       String strValue = argsMap.getValue(i);
/*  873 */       if (i == 0) {
/*  874 */         strSQL = HiDbtSqlHelper.getDynSentence(ctx, strValue);
/*      */       }
/*      */       else {
/*  877 */         if (i > 1)
/*  878 */           sb.append(",");
/*  879 */         sb.append(strArgName);
/*      */       }
/*      */     }
/*  882 */     sb.append(" " + strSQL);
/*      */ 
/*  884 */     List list = ctx.getDataBaseUtil().execQuery(sb.toString());
/*      */ 
/*  886 */     if ((list == null) || (list.size() == 0)) {
/*  887 */       throw new HiAppException(-2, "", "数据库无记录");
/*      */     }
/*  889 */     if (list.size() > 1) {
/*  890 */       throw new HiAppException(-1, "", "数据库存在多条记录");
/*      */     }
/*  892 */     boolean isError = false;
/*      */ 
/*  894 */     HiETF etf = mess.getETFBody();
/*  895 */     HashMap values = (HashMap)list.get(0);
/*      */ 
/*  897 */     Iterator fieldIter = values.entrySet().iterator();
/*  898 */     String strArgName = null;
/*  899 */     String strValue = null;
/*  900 */     String strCompare = null;
/*  901 */     while (fieldIter.hasNext()) {
/*  902 */       Map.Entry en = (Map.Entry)fieldIter.next();
/*  903 */       strArgName = (String)en.getKey();
/*  904 */       strValue = (String)en.getValue();
/*      */ 
/*  907 */       etf.setChildValue(strArgName, strValue);
/*      */ 
/*  909 */       if (argsMap.contains(strArgName)) {
/*  910 */         strCompare = argsMap.get(strArgName);
/*  911 */         isError = compareRecordCondition(strCompare, strValue, ctx);
/*      */ 
/*  913 */         if (isError) {
/*  914 */           throw new HiAppException(-3, "", "比较错:[" + strCompare + "][" + strValue + "]");
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  920 */     return 0;
/*      */   }
/*      */ 
/*      */   private boolean compareRecordCondition(String strCompare, String strValue, HiMessageContext ctx) throws HiException
/*      */   {
/*  925 */     HiMessage mess = ctx.getCurrentMsg();
/*  926 */     Logger log = HiLog.getLogger(mess);
/*      */ 
/*  928 */     HiETF etf = mess.getETFBody();
/*  929 */     String value = null;
/*  930 */     boolean isCompare = false;
/*  931 */     StringTokenizer st = new StringTokenizer(strCompare, "| ");
/*  932 */     while (st.hasMoreTokens())
/*      */     {
/*      */       int cmp;
/*  933 */       value = st.nextToken();
/*  934 */       if (log.isDebugEnabled()) {
/*  935 */         log.debug("value[" + value + "]");
/*      */       }
/*      */ 
/*  938 */       HiExpression exp = HiExpFactory.createExp(value.substring(1));
/*  939 */       String expValue = exp.getValue(ctx);
/*  940 */       String cmpToken = value.substring(0, 1);
/*  941 */       if (log.isDebugEnabled()) {
/*  942 */         log.debug("value[" + expValue + "]");
/*      */       }
/*      */ 
/*  945 */       if ((cmpToken.equalsIgnoreCase("=")) && (!(isCompare))) {
/*  946 */         cmp = strValue.compareTo(expValue);
/*  947 */         if (log.isDebugEnabled()) {
/*  948 */           log.debug("cmp=" + cmp);
/*      */         }
/*  950 */         if (cmp == 0)
/*  951 */           isCompare = true;
/*  952 */       } else if ((cmpToken.equalsIgnoreCase("!")) && (!(isCompare))) {
/*  953 */         cmp = strValue.compareTo(expValue);
/*  954 */         if (log.isDebugEnabled()) {
/*  955 */           log.debug("cmp=" + cmp);
/*      */         }
/*  957 */         if (cmp != 0)
/*  958 */           isCompare = true;
/*  959 */       } else if ((cmpToken.equalsIgnoreCase(">")) && (!(isCompare))) {
/*  960 */         cmp = strValue.compareTo(expValue);
/*  961 */         if (log.isDebugEnabled()) {
/*  962 */           log.debug("cmp>" + cmp);
/*      */         }
/*  964 */         if (cmp > 0)
/*  965 */           isCompare = true;
/*  966 */       } else if ((cmpToken.equalsIgnoreCase("<")) && (!(isCompare))) {
/*  967 */         cmp = strValue.compareTo(expValue);
/*  968 */         if (log.isDebugEnabled()) {
/*  969 */           log.debug("cmp<" + cmp);
/*      */         }
/*  971 */         if (cmp < 0) {
/*  972 */           isCompare = true;
/*      */         }
/*      */       }
/*  975 */       else if (!(isCompare)) {
/*  976 */         etf.setChildValue("RSP_CD", value);
/*      */       }
/*      */     }
/*      */ 
/*  980 */     return isCompare;
/*      */   }
/*      */ 
/*      */   public int GetBranchNoByNodeNo(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1000 */     HiMessage mess = ctx.getCurrentMsg();
/* 1001 */     Logger log = HiLog.getLogger(mess);
/*      */ 
/* 1003 */     HiETF etfRoot = mess.getETFBody();
/*      */ 
/* 1005 */     String NodNo = null;
/*      */ 
/* 1007 */     NodNo = argsMap.get("NodNo");
/* 1008 */     if (NodNo == null) {
/* 1009 */       NodNo = etfRoot.getChildValue("NodNo");
/*      */     }
/*      */ 
/* 1012 */     if (NodNo == null) {
/* 1013 */       throw new HiException("223100", "取网点号NodNo失败！");
/*      */     }
/*      */ 
/* 1016 */     if (log.isInfoEnabled()) {
/* 1017 */       log.info("网点号:[" + NodNo + "]");
/*      */     }
/*      */ 
/* 1020 */     String sqlcmd = HiStringUtils.format("SELECT DISTINCT BRNO FROM PUBORGINF WHERE NODNO='%s'", NodNo);
/*      */ 
/* 1022 */     if (log.isInfoEnabled()) {
/* 1023 */       log.info("取行号，语句:[" + sqlcmd + "]");
/*      */     }
/*      */ 
/* 1026 */     List lRec = ctx.getDataBaseUtil().execQuery(sqlcmd);
/* 1027 */     if (lRec.size() == 0) {
/* 1028 */       throw new HiAppException(2, "223101", "数据库表PUBORGINF中无网点号对应的记录！");
/*      */     }
/*      */ 
/* 1032 */     HashMap tmp = (HashMap)lRec.get(0);
/* 1033 */     String BrNo = (String)tmp.get("BRNO");
/* 1034 */     etfRoot.setChildValue("BrNo", BrNo);
/* 1035 */     if (log.isInfoEnabled()) {
/* 1036 */       log.info("行号：" + BrNo);
/*      */     }
/*      */ 
/* 1039 */     return 0;
/*      */   }
/*      */ 
/*      */   public int Null(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1051 */     return 0;
/*      */   }
/*      */ 
/*      */   public int Notity(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1087 */     throw new HiException("not implement");
/*      */   }
/*      */ 
/*      */   public int NotityOther(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1127 */     throw new HiException("not implement");
/*      */   }
/*      */ 
/*      */   public int SendFileMessage(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1157 */     throw new HiException("not implement");
/*      */   }
/*      */ 
/*      */   public int SendFileMessage2(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1189 */     throw new HiException("not implement");
/*      */   }
/*      */ 
/*      */   public int SendFileToOther(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1219 */     throw new HiException("not implement");
/*      */   }
/*      */ 
/*      */   public int CallSaveAndForward(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1231 */     throw new HiException("not implement");
/*      */   }
/*      */ 
/*      */   public int DumpETF(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1242 */     HiMessage msg = ctx.getCurrentMsg();
/* 1243 */     Logger log = HiLog.getLogger(msg);
/* 1244 */     if (log.isInfoEnabled())
/* 1245 */       log.info(msg.getETFBody());
/* 1246 */     return 0;
/*      */   }
/*      */ 
/*      */   public int DumpMsg(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1257 */     HiMessage msg = ctx.getCurrentMsg();
/* 1258 */     Logger log = HiLog.getLogger(msg);
/* 1259 */     if (log.isInfoEnabled())
/* 1260 */       log.info(msg);
/* 1261 */     return 0;
/*      */   }
/*      */ 
/*      */   public int DumpContext(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1272 */     HiMessage msg = ctx.getCurrentMsg();
/* 1273 */     Logger log = HiLog.getLogger(msg);
/* 1274 */     if (log.isInfoEnabled())
/* 1275 */       log.info(ctx);
/* 1276 */     return 0;
/*      */   }
/*      */ 
/*      */   public int SystemAndWait(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1292 */     String cmd = HiArgUtils.getStringNotNull(argsMap, "cmd");
/* 1293 */     String[] params = null;
/* 1294 */     if (argsMap.size() > 1) {
/* 1295 */       params = new String[argsMap.size() - 1];
/* 1296 */       for (int i = 1; i < argsMap.size(); ++i) {
/* 1297 */         params[(i - 1)] = argsMap.getValue(i);
/*      */       }
/*      */     }
/* 1300 */     return HiSystemUtils.exec(cmd, params, true);
/*      */   }
/*      */ 
/*      */   public int System(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1316 */     String cmd = HiArgUtils.getStringNotNull(argsMap, "cmd");
/* 1317 */     String[] params = null;
/* 1318 */     if (argsMap.size() > 1) {
/* 1319 */       params = new String[argsMap.size() - 1];
/* 1320 */       for (int i = 1; i < argsMap.size(); ++i) {
/* 1321 */         params[(i - 1)] = argsMap.getValue(i);
/*      */       }
/*      */     }
/* 1324 */     return HiSystemUtils.exec(cmd, params, false);
/*      */   }
/*      */ 
/*      */   public int GetPrevWorkDate(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1339 */     HiETF etfRoot = ctx.getCurrentMsg().getETFBody();
/*      */ 
/* 1342 */     String areCod = argsMap.get("AreCod");
/* 1343 */     if (areCod == null) {
/* 1344 */       areCod = etfRoot.getChildValue("NodNo");
/* 1345 */       if (areCod == null) {
/* 1346 */         throw new HiException("220026", "从参数或ETF节点[NodeNo]中取地区码失败");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1352 */     String refDat = argsMap.get("Date");
/* 1353 */     if (refDat == null) {
/* 1354 */       throw new HiException("220026", "取参数[Date]失败!");
/*      */     }
/* 1356 */     if (!(HiDateUtils.chkDateFormat(refDat))) {
/* 1357 */       throw new HiException("220026", "日期参数[Date]不是合法格式日期!输入日期=[" + refDat + "]");
/*      */     }
/*      */ 
/* 1362 */     int days = argsMap.getInt("Days");
/* 1363 */     if (days == 0) {
/* 1364 */       days = 1;
/*      */     }
/*      */ 
/* 1368 */     String[] hldFlgInfo = HiWorkDateHelper.getHoliday(ctx, areCod);
/* 1369 */     if ((hldFlgInfo == null) || (hldFlgInfo.length <= 1)) {
/* 1370 */       throw new HiException("220097", "GetPrevWorkDate: 取假期表失败");
/*      */     }
/*      */ 
/* 1375 */     int seq = HiWorkDateHelper.date2pos(refDat, hldFlgInfo[0], hldFlgInfo[1].length() / 372);
/*      */ 
/* 1377 */     if (seq == -1) {
/* 1378 */       return 2;
/*      */     }
/*      */ 
/* 1386 */     char[] hldFlgs = hldFlgInfo[1].toCharArray();
/*      */ 
/* 1388 */     --seq;
/* 1389 */     while (seq >= 0) {
/* 1390 */       if (hldFlgs[seq] == 'W') {
/* 1391 */         if (days == 1) {
/*      */           break;
/*      */         }
/* 1394 */         --days;
/*      */       }
/* 1396 */       --seq;
/*      */     }
/*      */ 
/* 1399 */     if (seq < 0)
/*      */     {
/* 1402 */       throw new HiException("220099", refDat, hldFlgInfo[0], String.valueOf(hldFlgInfo.length - 1));
/*      */     }
/*      */ 
/* 1407 */     String lastWorkDate = HiWorkDateHelper.pos2date(hldFlgInfo[0], seq);
/*      */ 
/* 1410 */     etfRoot.setChildValue("PrevDat", lastWorkDate);
/*      */ 
/* 1412 */     return 0;
/*      */   }
/*      */ 
/*      */   public int GetNextWorkDate(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1426 */     HiETF etfRoot = ctx.getCurrentMsg().getETFBody();
/*      */ 
/* 1429 */     String areCod = argsMap.get("AreCod");
/* 1430 */     if (areCod == null) {
/* 1431 */       areCod = etfRoot.getChildValue("NodNo");
/* 1432 */       if (areCod == null) {
/* 1433 */         throw new HiException("220026", "从参数或ETF节点[NodeNo]中取地区码失败");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1439 */     String refDat = argsMap.get("Date");
/* 1440 */     if (refDat == null) {
/* 1441 */       throw new HiException("220026", "取参数[Date]失败!");
/*      */     }
/* 1443 */     if (!(HiDateUtils.chkDateFormat(refDat))) {
/* 1444 */       throw new HiException("220026", "日期参数[Date]不是合法格式日期!输入日期=[" + refDat + "]");
/*      */     }
/*      */ 
/* 1449 */     int days = argsMap.getInt("Days");
/* 1450 */     if (days == 0) {
/* 1451 */       days = 1;
/*      */     }
/*      */ 
/* 1455 */     String[] hldFlgInfo = HiWorkDateHelper.getHoliday(ctx, areCod);
/* 1456 */     if ((hldFlgInfo == null) || (hldFlgInfo.length <= 1)) {
/* 1457 */       throw new HiException("220097", "GetNextWorkDate: 取假期表失败");
/*      */     }
/*      */ 
/* 1461 */     int hldFlgLen = hldFlgInfo[1].length();
/*      */ 
/* 1463 */     int seq = HiWorkDateHelper.date2pos(refDat, hldFlgInfo[0], hldFlgLen / 372);
/*      */ 
/* 1465 */     if (seq == -1) {
/* 1466 */       return 2;
/*      */     }
/*      */ 
/* 1474 */     char[] hldFlgs = hldFlgInfo[1].toCharArray();
/*      */ 
/* 1476 */     ++seq;
/* 1477 */     while (seq < hldFlgLen) {
/* 1478 */       if (hldFlgs[seq] == 'W') {
/* 1479 */         if (days == 1) {
/*      */           break;
/*      */         }
/* 1482 */         --days;
/*      */       }
/* 1484 */       ++seq;
/*      */     }
/*      */ 
/* 1487 */     if (seq == hldFlgLen)
/*      */     {
/* 1490 */       throw new HiException("220100", refDat, hldFlgInfo[0], String.valueOf(hldFlgInfo.length - 1));
/*      */     }
/*      */ 
/* 1495 */     String nextWorkDate = HiWorkDateHelper.pos2date(hldFlgInfo[0], seq);
/*      */ 
/* 1498 */     etfRoot.setChildValue("NextDat", nextWorkDate);
/*      */ 
/* 1500 */     return 0;
/*      */   }
/*      */ 
/*      */   public int CalcWorkDays(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1514 */     HiETF etfRoot = ctx.getCurrentMsg().getETFBody();
/*      */ 
/* 1517 */     String areCod = argsMap.get("AreCod");
/* 1518 */     if (areCod == null) {
/* 1519 */       areCod = etfRoot.getChildValue("NodNo");
/* 1520 */       if (areCod == null) {
/* 1521 */         throw new HiException("220026", "从参数或ETF节点[NodeNo]中取地区码失败");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1527 */     String bgnDat = argsMap.get("BeginDate");
/* 1528 */     if (bgnDat == null) {
/* 1529 */       throw new HiException("220026", "取参数[BeginDate]失败!");
/*      */     }
/* 1531 */     if (!(HiDateUtils.chkDateFormat(bgnDat))) {
/* 1532 */       throw new HiException("220026", "日期参数[BeginDate]不是合法格式日期!输入日期=[" + bgnDat + "]");
/*      */     }
/*      */ 
/* 1537 */     String endDat = argsMap.get("EndDate");
/* 1538 */     if (endDat == null) {
/* 1539 */       throw new HiException("220026", "取参数[EndDate]失败!");
/*      */     }
/* 1541 */     if (!(HiDateUtils.chkDateFormat(endDat))) {
/* 1542 */       throw new HiException("220026", "日期参数[EndDate]不是合法格式日期!输入日期=[" + endDat + "]");
/*      */     }
/*      */ 
/* 1547 */     String[] hldFlgInfo = HiWorkDateHelper.getHoliday(ctx, areCod);
/* 1548 */     if ((hldFlgInfo == null) || (hldFlgInfo.length <= 1)) {
/* 1549 */       throw new HiException("220097", "CalcWorkDays: 取假期表失败");
/*      */     }
/*      */ 
/* 1554 */     int bgnSeq = HiWorkDateHelper.date2pos(bgnDat, hldFlgInfo[0], hldFlgInfo[1].length() / 372);
/*      */ 
/* 1556 */     if (bgnSeq == -1) {
/* 1557 */       throw new HiException("220098", bgnDat, hldFlgInfo[0], String.valueOf(hldFlgInfo.length - 1));
/*      */     }
/*      */ 
/* 1562 */     int endSeq = HiWorkDateHelper.date2pos(endDat, hldFlgInfo[0], hldFlgInfo[1].length() / 372);
/*      */ 
/* 1564 */     if (endSeq == -1) {
/* 1565 */       return 2;
/*      */     }
/*      */ 
/* 1571 */     char[] hldFlgs = hldFlgInfo[1].toCharArray();
/*      */ 
/* 1574 */     int days = 0;
/* 1575 */     for (int i = bgnSeq; i < endSeq; ++i) {
/* 1576 */       if (hldFlgs[i] == 'W') {
/* 1577 */         ++days;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1582 */     etfRoot.setChildValue("WrkDays", String.valueOf(days));
/*      */ 
/* 1584 */     return 0;
/*      */   }
/*      */ 
/*      */   public int InvokeService(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1599 */     String service = HiArgUtils.getStringNotNull(argsMap, "ServiceName");
/* 1600 */     String mode = argsMap.get("Mode");
/* 1601 */     String logID = argsMap.get("LogID");
/* 1602 */     String msgType = argsMap.get("MsgType");
/* 1603 */     if (StringUtils.isEmpty(msgType)) {
/* 1604 */       msgType = ctx.getCurrentMsg().getType();
/*      */     }
/*      */ 
/* 1607 */     if (StringUtils.isEmpty(logID)) {
/* 1608 */       logID = service;
/*      */     }
/* 1610 */     HiMessage msg = ctx.getCurrentMsg();
/*      */ 
/* 1612 */     HiMessage msg1 = new HiMessage(logID, msgType);
/* 1613 */     HiETF root = ctx.getCurrentMsg().getETFBody().cloneNode();
/* 1614 */     msg1.setBody(root);
/* 1615 */     msg1.setHeadItem("STC", service);
/* 1616 */     msg1.setHeadItem("STF", msg.getHeadItem("STF"));
/* 1617 */     msg1.setHeadItem("ECT", "text/etf");
/* 1618 */     msg1.setHeadItem("STM", msg.getObjectHeadItem("STM"));
/* 1619 */     msg1.setHeadItem("ETM", msg.getObjectHeadItem("ETM"));
/* 1620 */     msg1.setHeadItem("SCH", "rq");
/*      */ 
/* 1622 */     if (StringUtils.equalsIgnoreCase(mode, "ASYNC")) {
/* 1623 */       HiRouterOut.asyncProcess(msg1);
/*      */     } else {
/* 1625 */       msg1 = HiRouterOut.syncProcess(msg1);
/* 1626 */       if (!(msg.getETFBody().combine(msg1.getETFBody(), true))) {
/* 1627 */         throw new HiException("220059", "合并ETF树失败");
/*      */       }
/*      */     }
/*      */ 
/* 1631 */     return 0;
/*      */   }
/*      */ 
/*      */   public int DumpStat(HiATLParam argsMap, HiMessageContext ctx)
/*      */   {
/* 1636 */     HiMessage msg = ctx.getCurrentMsg();
/* 1637 */     Logger log = HiLog.getLogger(msg);
/* 1638 */     if (log.isInfoEnabled()) {
/* 1639 */       log.info("========DUMP INFO START=========");
/* 1640 */       log.info(HiStats.dumpAllStat());
/* 1641 */       log.info("========DUMP INFO END=========");
/*      */     }
/* 1643 */     return 0;
/*      */   }
/*      */ 
/*      */   public int ClearStat(HiATLParam argsMap, HiMessageContext ctx)
/*      */   {
/* 1648 */     HiStats.clearAllStat();
/* 1649 */     return 0;
/*      */   }
/*      */ 
/*      */   public int SwitchDS(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1660 */     String name = argsMap.get("Name");
/* 1661 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/* 1662 */     if (StringUtils.isEmpty(name)) {
/* 1663 */       dbUtil.close();
/* 1664 */       dbUtil.popConnection();
/*      */     } else {
/* 1666 */       dbUtil.pushConnection();
/* 1667 */       dbUtil.setDsName(name);
/*      */     }
/* 1669 */     return 0;
/*      */   }
/*      */ 
/*      */   public int DebugInfo(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1682 */     String msg = argsMap.get("msg");
/* 1683 */     String type = argsMap.get("type");
/* 1684 */     HiMessage mess = ctx.getCurrentMsg();
/* 1685 */     Logger log = HiLog.getLogger(mess);
/* 1686 */     if (!(log.isInfo3Enabled())) {
/* 1687 */       return 0;
/*      */     }
/*      */ 
/* 1690 */     if (msg != null) {
/* 1691 */       log.info3(msg);
/*      */     }
/*      */ 
/* 1694 */     switch (NumberUtils.toInt(type))
/*      */     {
/*      */     case 1:
/* 1696 */       log.info3(mess.getBody());
/* 1697 */       break;
/*      */     case 2:
/* 1699 */       log.info3(mess);
/* 1700 */       break;
/*      */     case 3:
/* 1702 */       log.info3(ctx);
/*      */     }
/*      */ 
/* 1708 */     return 0;
/*      */   }
/*      */ 
/*      */   public static int VerifyId(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1724 */     int ret = 0;
/*      */ 
/* 1726 */     int[] PowerGene = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
/* 1727 */     char[] Remainder = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
/* 1728 */     int[] District = { 11, 12, 13, 14, 15, 21, 22, 23, 31, 32, 33, 34, 35, 36, 37, 41, 42, 43, 44, 45, 46, 50, 51, 52, 53, 54, 61, 62, 63, 64, 65 };
/*      */ 
/* 1730 */     String IDNo = HiArgUtils.getStringNotNull(argsMap, "IDNO");
/* 1731 */     boolean isConv = argsMap.getBoolean("CONVERTFLAG");
/*      */ 
/* 1734 */     int IDNoLen = IDNo.trim().length();
/* 1735 */     if ((IDNoLen != 15) && (IDNoLen != 18))
/*      */     {
/* 1737 */       throw new HiException("215027", "身份证校验: 身份证长度错[15｜18]");
/*      */     }
/*      */ 
/* 1740 */     int brflg = NumberUtils.toInt(IDNo.substring(0, 2));
/* 1741 */     int num = District.length;
/*      */ 
/* 1743 */     for (int i = 0; i < num; ++i)
/*      */     {
/* 1745 */       if (District[i] == brflg)
/*      */         break;
/*      */     }
/* 1748 */     if (i == num)
/*      */     {
/* 1750 */       throw new HiException("215027", "身份证校验: 行政区标识不合法");
/*      */     }
/*      */ 
/* 1753 */     if (IDNoLen == 15)
/*      */     {
/* 1755 */       IDNo = IDNo.substring(0, 6) + "19" + IDNo.substring(6);
/*      */     }
/*      */ 
/* 1758 */     if (!(HiDateUtils.chkDateFormat(IDNo.substring(6, 14))))
/*      */     {
/* 1760 */       throw new HiException("215027", "身份证校验: 日期不合法");
/*      */     }
/*      */ 
/* 1764 */     int iSum = 0;
/*      */ 
/* 1767 */     for (i = 0; i < 17; ++i)
/*      */     {
/* 1769 */       char ch = IDNo.charAt(i);
/* 1770 */       if ((ch < '0') || (ch > '9'))
/*      */       {
/* 1772 */         throw new HiException("215027", "身份证校验: 身份证号不全为数字");
/*      */       }
/* 1774 */       iSum += (ch - '0') * PowerGene[i];
/*      */     }
/*      */ 
/* 1777 */     int remain = iSum % 11;
/* 1778 */     if (IDNo.length() == 17)
/*      */     {
/* 1780 */       IDNo = IDNo + Remainder[remain];
/*      */     }
/* 1782 */     if (IDNo.charAt(17) != Remainder[remain])
/*      */     {
/* 1784 */       throw new HiException("215027", "身份证校验: 身份证校验位错");
/*      */     }
/*      */ 
/* 1788 */     if (isConv)
/*      */     {
/* 1790 */       ctx.getCurrentMsg().getETFBody().setChildValue("IDNO", IDNo);
/*      */     }
/* 1792 */     return ret;
/*      */   }
/*      */ 
/*      */   public static int TriggerMsg(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1803 */     String msgId = HiArgUtils.getStringNotNull(argsMap, "MsgId");
/* 1804 */     HiTriggerMsg.triggerMsg(HiMessageContext.getRootContext(), msgId);
/* 1805 */     return 0;
/*      */   }
/*      */ 
/*      */   public static int WaitMsg(HiATLParam argsMap, HiMessageContext ctx)
/*      */     throws HiException
/*      */   {
/* 1815 */     String msgId = HiArgUtils.getStringNotNull(argsMap, "MsgId");
/* 1816 */     String tmOut = argsMap.get("TmOut");
/* 1817 */     int iTmOut = -1;
/* 1818 */     if (tmOut != null)
/* 1819 */       iTmOut = NumberUtils.toInt(tmOut);
/*      */     try
/*      */     {
/* 1822 */       HiTriggerMsg.waitMsg(HiMessageContext.getRootContext(), msgId, iTmOut);
/*      */     } catch (InterruptedException e) {
/* 1824 */       throw new HiException(e);
/*      */     }
/* 1826 */     return 0;
/*      */   }
/*      */ }