 package com.hisun.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.atc.common.HiAtcLib;
 import com.hisun.atc.common.HiDbtSqlHelper;
 import com.hisun.atc.common.HiTriggerMsg;
 import com.hisun.atc.common.HiWorkDateHelper;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.dispatcher.HiRouterOut;
 import com.hisun.engine.invoke.impl.HiAttributesHelper;
 import com.hisun.engine.invoke.impl.HiItemHelper;
 import com.hisun.engine.invoke.impl.HiRunStatus;
 import com.hisun.exception.HiAppException;
 import com.hisun.exception.HiException;
 import com.hisun.exception.HiResponseException;
 import com.hisun.hiexpression.HiExpFactory;
 import com.hisun.hiexpression.HiExpression;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.message.HiMessageHelper;
 import com.hisun.stat.util.HiStats;
 import com.hisun.util.HiDateUtils;
 import com.hisun.util.HiStringManager;
 import com.hisun.util.HiStringUtils;
 import com.hisun.util.HiSystemUtils;
 import com.hisun.util.HiXmlHelper;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map.Entry;
 import java.util.Set;
 import java.util.StringTokenizer;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 import org.dom4j.Attribute;
 import org.dom4j.Element;
 
 public class HiBaseATC
 {
   private HiStringManager sm;
 
   public HiBaseATC()
   {
     this.sm = HiStringManager.getManager();
   }
 
   public int SendMessage(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String reqMsgName = argsMap.get("ReqMsg");
 
     if (StringUtils.isEmpty(reqMsgName)) {
       reqMsgName = "MSG";
     }
 
     String reqETFName = argsMap.get("ReqETF");
     if (StringUtils.isEmpty(reqETFName)) {
       reqETFName = "ETF";
     }
     HiMessage msg = (HiMessage)ctx.getBaseSource(reqMsgName);
     if (msg == null) {
       msg = ctx.getCurrentMsg();
     }
 
     HiETF etf = (HiETF)ctx.getBaseSource(reqETFName);
     HiMessage msg1 = new HiMessage(msg);
     if (etf != null) {
       msg1.setBody(etf);
     }
     HiRouterOut.asyncProcess(msg1);
     return 0;
   }
 
   public int CallMessage(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     if (!(argsMap.contains("TimeOut"))) {
       throw new HiException("220026", "TimeOut");
     }
 
     int tmOut = argsMap.getInt("TimeOut");
 
     String rspMsgName = HiArgUtils.getStringNotNull(argsMap, "RspMsg");
 
     String rspETFName = argsMap.get("RspETF");
 
     String reqMsgName = argsMap.get("ReqMsg");
 
     if (StringUtils.isEmpty(reqMsgName)) {
       reqMsgName = "MSG";
     }
 
     String reqETFName = argsMap.get("ReqETF");
     if (StringUtils.isEmpty(reqETFName)) {
       reqETFName = "ETF";
     }
 
     HiMessage msg = (HiMessage)ctx.getBaseSource(reqMsgName);
     if (msg == null) {
       msg = ctx.getCurrentMsg();
     }
 
     HiETF etf = (HiETF)ctx.getBaseSource(reqETFName);
     HiMessage msg1 = new HiMessage(msg);
     if (etf != null) {
       msg1.setBody(etf);
     }
 
     HiMessageHelper.setMessageTmOut(msg1, tmOut);
     HiRouterOut.syncProcess(msg1);
     ctx.setBaseSource(rspMsgName, msg1);
     ctx.setBaseSource(rspETFName, msg1.getBody());
     return 0;
   }
 
   public int PutResponse(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
     runStatus.setResponse(true);
     throw new HiResponseException();
   }
 
   public int SetNoResponse(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
     runStatus.setResponse(false);
     return 0;
   }
 
   public int SetInitResponse(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
     runStatus.setResponse(true);
     return 0;
   }
 
   public int CodeSwitching(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     String strAliasName = argsMap.get("DATSRC");
     if (StringUtils.isEmpty(strAliasName)) {
       throw new HiException("220002");
     }
     String strSourName = argsMap.get("SOURNAM");
     if (StringUtils.isEmpty(strSourName)) {
       throw new HiException("220003");
     }
 
     String strSourETF = msg.getETFBody().getGrandChildValue(strSourName);
 
     String strDestName = argsMap.get("DESTNAM");
     if (StringUtils.isEmpty(strDestName)) {
       throw new HiException("220004");
     }
     String strTableName = argsMap.get("TBLNAM");
 
     Element rootNode = (Element)ctx.getProperty("CONFIGDECLARE", strAliasName);
 
     if (rootNode == null) {
       throw new HiException("220314", strAliasName);
     }
 
     Element tableNode = null;
     if (StringUtils.isEmpty(strTableName))
       tableNode = rootNode;
     else {
       tableNode = HiXmlHelper.getNodeByAttr(rootNode, "Table", "name", strTableName);
     }
 
     if (tableNode == null) {
       throw new HiException("220327", strAliasName, strTableName);
     }
 
     Iterator elementIter = tableNode.elementIterator();
     String defaultValue = null;
     String destValue = null;
     while (elementIter.hasNext()) {
       Element item = (Element)elementIter.next();
       String strNodeName = item.getName();
       if ("Default".equals(strNodeName)) {
         defaultValue = item.attribute(0).getValue();
       }
 
       if (item.attributeCount() < 2)
         continue;
       String tmp1 = item.attribute(0).getValue();
       String tmp2 = item.attribute(1).getValue();
       if (StringUtils.equals(tmp1, strSourETF)) {
         destValue = tmp2;
         break; }
       if (StringUtils.equals(tmp2, strSourETF)) {
         destValue = tmp1;
         break;
       }
     }
 
     if (destValue == null) {
       destValue = defaultValue;
     }
 
     if (destValue == null) {
       throw new HiException("220328");
     }
 
     msg.getETFBody().setGrandChildNode(strDestName, destValue);
 
     if (log.isInfoEnabled()) {
       log.info(this.sm.getString("HiBaseATC.CodeSwitching00", strSourName, strSourETF, strDestName, destValue));
     }
 
     return 0;
   }
 
   public int Sleep(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     int iSec = 1;
     try {
       if (argsMap.contains("SLEEPTIME")) {
         Integer sec = new Integer(argsMap.get("SLEEPTIME"));
         iSec = sec.intValue();
       }
       Thread.currentThread(); Thread.sleep(iSec * 1000);
     } catch (Exception e) {
       throw HiException.makeException(e);
     }
     return 0;
   }
 
   public int Lock(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     String recKey = argsMap.get("RECKEY");
     if (recKey == null) {
       throw new HiException("220026", "RECKEY");
     }
 
     if (recKey.length() > 80) {
       throw new HiException("220026", "RECKEY参数值有误, 长度不允许大于80字节");
     }
 
     String timeOut = argsMap.get("TIMOUT");
     if (timeOut == null) {
       timeOut = "0";
     }
 
     String autoUnlockFlg = argsMap.get("AUTOUNLOCK");
     if (autoUnlockFlg == null) {
       autoUnlockFlg = "no";
     }
 
     int ret = 0;
     try {
       ret = HiAtcLib.appLock(recKey, timeOut, ctx);
     } catch (HiException e) {
       if (log.isInfoEnabled()) {
         log.info("Lock: 登记锁失败. aRecKey=[" + recKey + "]");
       }
 
       throw e;
     }
 
     if ((ret == 0) && (autoUnlockFlg.equals("yes")) && 
       (log.isInfoEnabled())) {
       log.info("Lock: 登记交易结束时, 自动解锁. aRecKey=[" + recKey + "]");
     }
 
     return ret;
   }
 
   public int Trylock(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     String recKey = argsMap.get("RECKEY");
     if (recKey == null) {
       throw new HiException("220026", "RECKEY");
     }
 
     if (recKey.length() > 80) {
       throw new HiException("220026", "RECKEY参数值有误, 长度不允许大于80字节");
     }
 
     int ret = 0;
     try
     {
       ret = HiAtcLib.tryLock(recKey, ctx);
     } catch (HiException e) {
       if (log.isInfoEnabled()) {
         log.info("TryLock: 检查锁失败, aRecKey=[" + recKey + "]");
       }
       ret = -1;
       throw e;
     }
     return ret;
   }
 
   public int Unlock(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     String recKey = argsMap.get("RECKEY");
     if (recKey == null) {
       throw new HiException("220026", "RECKEY");
     }
 
     if (recKey.length() > 80) {
       throw new HiException("220026", "RECKEY参数值有误, 长度不允许大于80字节");
     }
 
     int ret = 0;
     try
     {
       ret = HiAtcLib.unAppLock(recKey, ctx);
     } catch (HiException e) {
       if (log.isInfoEnabled()) {
         log.info("UnLock: 解锁失败, aRecKey=[" + recKey + "]");
       }
       throw e;
     }
     return ret;
   }
 
   public int Monitor(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     throw new HiException("not implement");
   }
 
   public int isExistNode(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     if ((argsMap == null) || (argsMap.size() == 0))
     {
       throw new HiException("220026", "参数为空");
     }
 
     HiETF etf = (HiETF)mess.getBody();
     if (etf == null)
     {
       throw new HiException("220026", "ETF为null.");
     }
 
     if (etf.getGrandChildValue(argsMap.getValue(0)) == null) {
       return 0;
     }
 
     return 1;
   }
 
   public int getValue(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     if ((argsMap == null) || (argsMap.size() == 0))
     {
       throw new HiException("220026", "参数为空");
     }
 
     HiETF etf = (HiETF)mess.getBody();
     if (etf == null)
     {
       throw new HiException("220026", "消息体中的ETF为null.");
     }
 
     String sourName = argsMap.get("SourName");
 
     if (sourName == null)
     {
       throw new HiException("220026", "参数错误, 没有指定的源域.");
     }
 
     String destName = argsMap.get("DestName");
     if (destName == null)
     {
       throw new HiException("220026", "参数错误, 没有指定保存的目标域.");
     }
 
     etf.setGrandChildNode(destName, etf.getGrandChildValue(sourName));
 
     return 0;
   }
 
   public int SetValue(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     String fieldName = HiArgUtils.getStringNotNull(argsMap, "FieldName");
     String fieldValue = HiArgUtils.getStringNotNull(argsMap, "FieldValue");
     HiETF etf = (HiETF)mess.getBody();
     HiItemHelper.addEtfItem(mess, fieldName, fieldValue);
     return 0;
   }
 
   public int IsWorkDate(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiETF etfRoot = ctx.getCurrentMsg().getETFBody();
 
     String areCod = argsMap.get("AreCod");
     if (areCod == null) {
       areCod = etfRoot.getChildValue("NodNo");
       if (areCod == null) {
         throw new HiException("220026", "从参数或ETF节点[NodeNo]中取地区码失败");
       }
 
     }
 
     String aDat = argsMap.get("Date");
     if (aDat == null) {
       throw new HiException("220026", "取参数[Date]失败!");
     }
     if (!(HiDateUtils.chkDateFormat(aDat))) {
       throw new HiException("220026", "日期参数[Date]不是合法格式日期!输入日期=[" + aDat + "]");
     }
 
     String[] hldFlgInfo = HiWorkDateHelper.getHoliday(ctx, areCod);
     if ((hldFlgInfo == null) || (hldFlgInfo.length <= 1)) {
       throw new HiException("220097", "IsWorkDate: 取假期表失败");
     }
 
     int seq = HiWorkDateHelper.date2pos(aDat, hldFlgInfo[0], hldFlgInfo[1].length() / 372);
 
     if (seq == -1)
     {
       return 2;
     }
 
     if (hldFlgInfo[1].charAt(seq) == 'W') {
       return 1;
     }
 
     return 0;
   }
 
   public int InitTransaction(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     HiETF etfRoot = (HiETF)mess.getBody();
     String strActDat = null;
 
     String sqlCmd = "SELECT ACC_DT,SYS_ID FROM PUBPLTINF";
     List lRec = ctx.getDataBaseUtil().execQuery(sqlCmd);
 
     if ((lRec == null) || (lRec.size() == 0)) {
       log.fatal(this.sm.getString("HiTransactionCTLImpl.GetSysInfoError"));
       throw new HiException("211007");
     }
 
     HashMap tmp = (HashMap)lRec.get(0);
 
     etfRoot.setChildValue("SYS_ID", (String)tmp.get("SYS_ID"));
 
     strActDat = (String)tmp.get("ACC_DT");
     etfRoot.setChildValue("ACC_DT", strActDat);
     ctx.setBaseSource("SYS_ID", (String)tmp.get("SYS_ID"));
     ctx.setBaseSource("ACC_DT", strActDat);
 
     if (log.isInfoEnabled()) {
       log.info(this.sm.getString("HiTransactionCTLImpl.GetActDat", strActDat));
       log.info(this.sm.getString("HiTransactionCTLImpl.GetAppCode", ctx.getStrProp("app_code")));
     }
 
     String AplSub = etfRoot.getChildValue("APP_SUB");
     if (log.isInfoEnabled()) {
       log.info("APP_SUB:[" + AplSub + "]");
     }
 
     String AplCls = etfRoot.getChildValue("APP_CLS");
     if (AplCls == null) {
       throw new HiException("220058", "APP_CLS");
     }
 
     if (log.isInfoEnabled()) {
       log.info("取AplCls,值为:[" + AplCls + "]");
     }
 
     String BrNo = etfRoot.getChildValue("BR_NO");
     if (BrNo == null) {
       if (log.isInfoEnabled()) {
         log.info(" 取不到分行代码,不读取业务信息");
       }
       return 0;
     }
 
     String sqlcmd = HiStringUtils.format("SELECT BR_SEQ,BR_NM,SYS_ID FROM PUBBCHLST WHERE BR_NO='%s'", BrNo);
 
     lRec = ctx.getDataBaseUtil().execQuery(sqlcmd);
     if (lRec != null) if (lRec.size() != 0)
       {
         tmp = (HashMap)lRec.get(0);
         etfRoot.setChildValue("BR_SEQ", (String)tmp.get("BR_SEQ"));
 
         etfRoot.setChildValue("BR_NM", (String)tmp.get("BR_NM"));
 
         etfRoot.setChildValue("SYS_ID", (String)tmp.get("SYS_ID"));
 
         ctx.setBaseSource("SYS_ID", (String)tmp.get("SYS_ID"));
       }
 
 
     String BusTyp = null;
 
     sqlcmd = HiStringUtils.format("SELECT BUS_TYP FROM PUBAPLBUS WHERE BR_NO='%s' AND APP_CLS='%s' AND (APP_SUB='%s' OR APP_SUB=' ')", BrNo, AplCls, AplSub);
 
     lRec = ctx.getDataBaseUtil().execQuery(sqlcmd);
     if (lRec.size() != 0) {
       tmp = (HashMap)lRec.get(0);
       BusTyp = (String)tmp.get("BUS_TYP");
       ctx.setBaseSource("BUS_TYP", BusTyp);
       if (log.isInfoEnabled()) {
         log.info("--->业务类型 = " + BusTyp);
       }
     }
 
     if (!(StringUtils.isEmpty(BusTyp))) {
       etfRoot.setChildValue("BUS_TYP", BusTyp);
 
       sqlcmd = HiStringUtils.format("SELECT ONL_TBL FROM PUBJNLDEF WHERE BR_NO ='%s' AND BUS_TYP = '%s'", BrNo, BusTyp);
 
       lRec = ctx.getDataBaseUtil().execQuery(sqlcmd);
       if (lRec.size() != 0) {
         tmp = (HashMap)lRec.get(0);
         String OnlTbl = (String)tmp.get("ONL_TBL");
 
         if (log.isInfoEnabled()) {
           log.info("--->流水表 = " + OnlTbl);
         }
         ctx.setJnlTable(OnlTbl);
 
         ctx.setBaseSource("LstTab", OnlTbl);
         HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
         if (attr.isNoChk())
           etfRoot.setChildValue("MST_CHK", "0");
         else {
           etfRoot.setChildValue("MST_CHK", "1");
         }
 
         etfRoot.setChildValue("ITG_TYP", attr.integtype());
         etfRoot.setChildValue("TXN_TYP", attr.getTxnTyp());
 
         etfRoot.setChildValue("FRSP_CD", "211007");
       }
 
     }
 
     return 0;
   }
 
   public int printEtfToLog(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     if (log.isDebugEnabled()) {
       log.debug("----------------------printEtfToLog begin ------------------------");
 
       log.debug(mess.getBody());
 
       log.debug("----------------------printEtfToLog end ------------------------");
     }
 
     return 0;
   }
 
   public int CompareRecordCondition(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     if ((argsMap.size() < 2) || (argsMap.size() > 20)) {
       throw new HiAppException(-1, "", "参数不正确");
     }
 
     StringBuffer sb = new StringBuffer("SELECT ");
 
     String strSQL = null;
     for (int i = 0; i < argsMap.size(); ++i)
     {
       String strArgName = argsMap.getName(i);
       String strValue = argsMap.getValue(i);
       if (i == 0) {
         strSQL = HiDbtSqlHelper.getDynSentence(ctx, strValue);
       }
       else {
         if (i > 1)
           sb.append(",");
         sb.append(strArgName);
       }
     }
     sb.append(" " + strSQL);
 
     List list = ctx.getDataBaseUtil().execQuery(sb.toString());
 
     if ((list == null) || (list.size() == 0)) {
       throw new HiAppException(-2, "", "数据库无记录");
     }
     if (list.size() > 1) {
       throw new HiAppException(-1, "", "数据库存在多条记录");
     }
     boolean isError = false;
 
     HiETF etf = mess.getETFBody();
     HashMap values = (HashMap)list.get(0);
 
     Iterator fieldIter = values.entrySet().iterator();
     String strArgName = null;
     String strValue = null;
     String strCompare = null;
     while (fieldIter.hasNext()) {
       Map.Entry en = (Map.Entry)fieldIter.next();
       strArgName = (String)en.getKey();
       strValue = (String)en.getValue();
 
       etf.setChildValue(strArgName, strValue);
 
       if (argsMap.contains(strArgName)) {
         strCompare = argsMap.get(strArgName);
         isError = compareRecordCondition(strCompare, strValue, ctx);
 
         if (isError) {
           throw new HiAppException(-3, "", "比较错:[" + strCompare + "][" + strValue + "]");
         }
       }
 
     }
 
     return 0;
   }
 
   private boolean compareRecordCondition(String strCompare, String strValue, HiMessageContext ctx) throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     HiETF etf = mess.getETFBody();
     String value = null;
     boolean isCompare = false;
     StringTokenizer st = new StringTokenizer(strCompare, "| ");
     while (st.hasMoreTokens())
     {
       int cmp;
       value = st.nextToken();
       if (log.isDebugEnabled()) {
         log.debug("value[" + value + "]");
       }
 
       HiExpression exp = HiExpFactory.createExp(value.substring(1));
       String expValue = exp.getValue(ctx);
       String cmpToken = value.substring(0, 1);
       if (log.isDebugEnabled()) {
         log.debug("value[" + expValue + "]");
       }
 
       if ((cmpToken.equalsIgnoreCase("=")) && (!(isCompare))) {
         cmp = strValue.compareTo(expValue);
         if (log.isDebugEnabled()) {
           log.debug("cmp=" + cmp);
         }
         if (cmp == 0)
           isCompare = true;
       } else if ((cmpToken.equalsIgnoreCase("!")) && (!(isCompare))) {
         cmp = strValue.compareTo(expValue);
         if (log.isDebugEnabled()) {
           log.debug("cmp=" + cmp);
         }
         if (cmp != 0)
           isCompare = true;
       } else if ((cmpToken.equalsIgnoreCase(">")) && (!(isCompare))) {
         cmp = strValue.compareTo(expValue);
         if (log.isDebugEnabled()) {
           log.debug("cmp>" + cmp);
         }
         if (cmp > 0)
           isCompare = true;
       } else if ((cmpToken.equalsIgnoreCase("<")) && (!(isCompare))) {
         cmp = strValue.compareTo(expValue);
         if (log.isDebugEnabled()) {
           log.debug("cmp<" + cmp);
         }
         if (cmp < 0) {
           isCompare = true;
         }
       }
       else if (!(isCompare)) {
         etf.setChildValue("RSP_CD", value);
       }
     }
 
     return isCompare;
   }
 
   public int GetBranchNoByNodeNo(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
 
     HiETF etfRoot = mess.getETFBody();
 
     String NodNo = null;
 
     NodNo = argsMap.get("NodNo");
     if (NodNo == null) {
       NodNo = etfRoot.getChildValue("NodNo");
     }
 
     if (NodNo == null) {
       throw new HiException("223100", "取网点号NodNo失败！");
     }
 
     if (log.isInfoEnabled()) {
       log.info("网点号:[" + NodNo + "]");
     }
 
     String sqlcmd = HiStringUtils.format("SELECT DISTINCT BRNO FROM PUBORGINF WHERE NODNO='%s'", NodNo);
 
     if (log.isInfoEnabled()) {
       log.info("取行号，语句:[" + sqlcmd + "]");
     }
 
     List lRec = ctx.getDataBaseUtil().execQuery(sqlcmd);
     if (lRec.size() == 0) {
       throw new HiAppException(2, "223101", "数据库表PUBORGINF中无网点号对应的记录！");
     }
 
     HashMap tmp = (HashMap)lRec.get(0);
     String BrNo = (String)tmp.get("BRNO");
     etfRoot.setChildValue("BrNo", BrNo);
     if (log.isInfoEnabled()) {
       log.info("行号：" + BrNo);
     }
 
     return 0;
   }
 
   public int Null(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     return 0;
   }
 
   public int Notity(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     throw new HiException("not implement");
   }
 
   public int NotityOther(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     throw new HiException("not implement");
   }
 
   public int SendFileMessage(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     throw new HiException("not implement");
   }
 
   public int SendFileMessage2(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     throw new HiException("not implement");
   }
 
   public int SendFileToOther(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     throw new HiException("not implement");
   }
 
   public int CallSaveAndForward(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     throw new HiException("not implement");
   }
 
   public int DumpETF(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isInfoEnabled())
       log.info(msg.getETFBody());
     return 0;
   }
 
   public int DumpMsg(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isInfoEnabled())
       log.info(msg);
     return 0;
   }
 
   public int DumpContext(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isInfoEnabled())
       log.info(ctx);
     return 0;
   }
 
   public int SystemAndWait(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String cmd = HiArgUtils.getStringNotNull(argsMap, "cmd");
     String[] params = null;
     if (argsMap.size() > 1) {
       params = new String[argsMap.size() - 1];
       for (int i = 1; i < argsMap.size(); ++i) {
         params[(i - 1)] = argsMap.getValue(i);
       }
     }
     return HiSystemUtils.exec(cmd, params, true);
   }
 
   public int System(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String cmd = HiArgUtils.getStringNotNull(argsMap, "cmd");
     String[] params = null;
     if (argsMap.size() > 1) {
       params = new String[argsMap.size() - 1];
       for (int i = 1; i < argsMap.size(); ++i) {
         params[(i - 1)] = argsMap.getValue(i);
       }
     }
     return HiSystemUtils.exec(cmd, params, false);
   }
 
   public int GetPrevWorkDate(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiETF etfRoot = ctx.getCurrentMsg().getETFBody();
 
     String areCod = argsMap.get("AreCod");
     if (areCod == null) {
       areCod = etfRoot.getChildValue("NodNo");
       if (areCod == null) {
         throw new HiException("220026", "从参数或ETF节点[NodeNo]中取地区码失败");
       }
 
     }
 
     String refDat = argsMap.get("Date");
     if (refDat == null) {
       throw new HiException("220026", "取参数[Date]失败!");
     }
     if (!(HiDateUtils.chkDateFormat(refDat))) {
       throw new HiException("220026", "日期参数[Date]不是合法格式日期!输入日期=[" + refDat + "]");
     }
 
     int days = argsMap.getInt("Days");
     if (days == 0) {
       days = 1;
     }
 
     String[] hldFlgInfo = HiWorkDateHelper.getHoliday(ctx, areCod);
     if ((hldFlgInfo == null) || (hldFlgInfo.length <= 1)) {
       throw new HiException("220097", "GetPrevWorkDate: 取假期表失败");
     }
 
     int seq = HiWorkDateHelper.date2pos(refDat, hldFlgInfo[0], hldFlgInfo[1].length() / 372);
 
     if (seq == -1) {
       return 2;
     }
 
     char[] hldFlgs = hldFlgInfo[1].toCharArray();
 
     --seq;
     while (seq >= 0) {
       if (hldFlgs[seq] == 'W') {
         if (days == 1) {
           break;
         }
         --days;
       }
       --seq;
     }
 
     if (seq < 0)
     {
       throw new HiException("220099", refDat, hldFlgInfo[0], String.valueOf(hldFlgInfo.length - 1));
     }
 
     String lastWorkDate = HiWorkDateHelper.pos2date(hldFlgInfo[0], seq);
 
     etfRoot.setChildValue("PrevDat", lastWorkDate);
 
     return 0;
   }
 
   public int GetNextWorkDate(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiETF etfRoot = ctx.getCurrentMsg().getETFBody();
 
     String areCod = argsMap.get("AreCod");
     if (areCod == null) {
       areCod = etfRoot.getChildValue("NodNo");
       if (areCod == null) {
         throw new HiException("220026", "从参数或ETF节点[NodeNo]中取地区码失败");
       }
 
     }
 
     String refDat = argsMap.get("Date");
     if (refDat == null) {
       throw new HiException("220026", "取参数[Date]失败!");
     }
     if (!(HiDateUtils.chkDateFormat(refDat))) {
       throw new HiException("220026", "日期参数[Date]不是合法格式日期!输入日期=[" + refDat + "]");
     }
 
     int days = argsMap.getInt("Days");
     if (days == 0) {
       days = 1;
     }
 
     String[] hldFlgInfo = HiWorkDateHelper.getHoliday(ctx, areCod);
     if ((hldFlgInfo == null) || (hldFlgInfo.length <= 1)) {
       throw new HiException("220097", "GetNextWorkDate: 取假期表失败");
     }
 
     int hldFlgLen = hldFlgInfo[1].length();
 
     int seq = HiWorkDateHelper.date2pos(refDat, hldFlgInfo[0], hldFlgLen / 372);
 
     if (seq == -1) {
       return 2;
     }
 
     char[] hldFlgs = hldFlgInfo[1].toCharArray();
 
     ++seq;
     while (seq < hldFlgLen) {
       if (hldFlgs[seq] == 'W') {
         if (days == 1) {
           break;
         }
         --days;
       }
       ++seq;
     }
 
     if (seq == hldFlgLen)
     {
       throw new HiException("220100", refDat, hldFlgInfo[0], String.valueOf(hldFlgInfo.length - 1));
     }
 
     String nextWorkDate = HiWorkDateHelper.pos2date(hldFlgInfo[0], seq);
 
     etfRoot.setChildValue("NextDat", nextWorkDate);
 
     return 0;
   }
 
   public int CalcWorkDays(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiETF etfRoot = ctx.getCurrentMsg().getETFBody();
 
     String areCod = argsMap.get("AreCod");
     if (areCod == null) {
       areCod = etfRoot.getChildValue("NodNo");
       if (areCod == null) {
         throw new HiException("220026", "从参数或ETF节点[NodeNo]中取地区码失败");
       }
 
     }
 
     String bgnDat = argsMap.get("BeginDate");
     if (bgnDat == null) {
       throw new HiException("220026", "取参数[BeginDate]失败!");
     }
     if (!(HiDateUtils.chkDateFormat(bgnDat))) {
       throw new HiException("220026", "日期参数[BeginDate]不是合法格式日期!输入日期=[" + bgnDat + "]");
     }
 
     String endDat = argsMap.get("EndDate");
     if (endDat == null) {
       throw new HiException("220026", "取参数[EndDate]失败!");
     }
     if (!(HiDateUtils.chkDateFormat(endDat))) {
       throw new HiException("220026", "日期参数[EndDate]不是合法格式日期!输入日期=[" + endDat + "]");
     }
 
     String[] hldFlgInfo = HiWorkDateHelper.getHoliday(ctx, areCod);
     if ((hldFlgInfo == null) || (hldFlgInfo.length <= 1)) {
       throw new HiException("220097", "CalcWorkDays: 取假期表失败");
     }
 
     int bgnSeq = HiWorkDateHelper.date2pos(bgnDat, hldFlgInfo[0], hldFlgInfo[1].length() / 372);
 
     if (bgnSeq == -1) {
       throw new HiException("220098", bgnDat, hldFlgInfo[0], String.valueOf(hldFlgInfo.length - 1));
     }
 
     int endSeq = HiWorkDateHelper.date2pos(endDat, hldFlgInfo[0], hldFlgInfo[1].length() / 372);
 
     if (endSeq == -1) {
       return 2;
     }
 
     char[] hldFlgs = hldFlgInfo[1].toCharArray();
 
     int days = 0;
     for (int i = bgnSeq; i < endSeq; ++i) {
       if (hldFlgs[i] == 'W') {
         ++days;
       }
 
     }
 
     etfRoot.setChildValue("WrkDays", String.valueOf(days));
 
     return 0;
   }
 
   public int InvokeService(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String service = HiArgUtils.getStringNotNull(argsMap, "ServiceName");
     String mode = argsMap.get("Mode");
     String logID = argsMap.get("LogID");
     String msgType = argsMap.get("MsgType");
     if (StringUtils.isEmpty(msgType)) {
       msgType = ctx.getCurrentMsg().getType();
     }
 
     if (StringUtils.isEmpty(logID)) {
       logID = service;
     }
     HiMessage msg = ctx.getCurrentMsg();
 
     HiMessage msg1 = new HiMessage(logID, msgType);
     HiETF root = ctx.getCurrentMsg().getETFBody().cloneNode();
     msg1.setBody(root);
     msg1.setHeadItem("STC", service);
     msg1.setHeadItem("STF", msg.getHeadItem("STF"));
     msg1.setHeadItem("ECT", "text/etf");
     msg1.setHeadItem("STM", msg.getObjectHeadItem("STM"));
     msg1.setHeadItem("ETM", msg.getObjectHeadItem("ETM"));
     msg1.setHeadItem("SCH", "rq");
 
     if (StringUtils.equalsIgnoreCase(mode, "ASYNC")) {
       HiRouterOut.asyncProcess(msg1);
     } else {
       msg1 = HiRouterOut.syncProcess(msg1);
       if (!(msg.getETFBody().combine(msg1.getETFBody(), true))) {
         throw new HiException("220059", "合并ETF树失败");
       }
     }
 
     return 0;
   }
 
   public int DumpStat(HiATLParam argsMap, HiMessageContext ctx)
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isInfoEnabled()) {
       log.info("========DUMP INFO START=========");
       log.info(HiStats.dumpAllStat());
       log.info("========DUMP INFO END=========");
     }
     return 0;
   }
 
   public int ClearStat(HiATLParam argsMap, HiMessageContext ctx)
   {
     HiStats.clearAllStat();
     return 0;
   }
 
   public int SwitchDS(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String name = argsMap.get("Name");
     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
     if (StringUtils.isEmpty(name)) {
       dbUtil.close();
       dbUtil.popConnection();
     } else {
       dbUtil.pushConnection();
       dbUtil.setDsName(name);
     }
     return 0;
   }
 
   public int DebugInfo(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String msg = argsMap.get("msg");
     String type = argsMap.get("type");
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     if (!(log.isInfo3Enabled())) {
       return 0;
     }
 
     if (msg != null) {
       log.info3(msg);
     }
 
     switch (NumberUtils.toInt(type))
     {
     case 1:
       log.info3(mess.getBody());
       break;
     case 2:
       log.info3(mess);
       break;
     case 3:
       log.info3(ctx);
     }
 
     return 0;
   }
 
   public static int VerifyId(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     int ret = 0;
 
     int[] PowerGene = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
     char[] Remainder = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
     int[] District = { 11, 12, 13, 14, 15, 21, 22, 23, 31, 32, 33, 34, 35, 36, 37, 41, 42, 43, 44, 45, 46, 50, 51, 52, 53, 54, 61, 62, 63, 64, 65 };
 
     String IDNo = HiArgUtils.getStringNotNull(argsMap, "IDNO");
     boolean isConv = argsMap.getBoolean("CONVERTFLAG");
 
     int IDNoLen = IDNo.trim().length();
     if ((IDNoLen != 15) && (IDNoLen != 18))
     {
       throw new HiException("215027", "身份证校验: 身份证长度错[15｜18]");
     }
 
     int brflg = NumberUtils.toInt(IDNo.substring(0, 2));
     int num = District.length;
 
     for (int i = 0; i < num; ++i)
     {
       if (District[i] == brflg)
         break;
     }
     if (i == num)
     {
       throw new HiException("215027", "身份证校验: 行政区标识不合法");
     }
 
     if (IDNoLen == 15)
     {
       IDNo = IDNo.substring(0, 6) + "19" + IDNo.substring(6);
     }
 
     if (!(HiDateUtils.chkDateFormat(IDNo.substring(6, 14))))
     {
       throw new HiException("215027", "身份证校验: 日期不合法");
     }
 
     int iSum = 0;
 
     for (i = 0; i < 17; ++i)
     {
       char ch = IDNo.charAt(i);
       if ((ch < '0') || (ch > '9'))
       {
         throw new HiException("215027", "身份证校验: 身份证号不全为数字");
       }
       iSum += (ch - '0') * PowerGene[i];
     }
 
     int remain = iSum % 11;
     if (IDNo.length() == 17)
     {
       IDNo = IDNo + Remainder[remain];
     }
     if (IDNo.charAt(17) != Remainder[remain])
     {
       throw new HiException("215027", "身份证校验: 身份证校验位错");
     }
 
     if (isConv)
     {
       ctx.getCurrentMsg().getETFBody().setChildValue("IDNO", IDNo);
     }
     return ret;
   }
 
   public static int TriggerMsg(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String msgId = HiArgUtils.getStringNotNull(argsMap, "MsgId");
     HiTriggerMsg.triggerMsg(HiMessageContext.getRootContext(), msgId);
     return 0;
   }
 
   public static int WaitMsg(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String msgId = HiArgUtils.getStringNotNull(argsMap, "MsgId");
     String tmOut = argsMap.get("TmOut");
     int iTmOut = -1;
     if (tmOut != null)
       iTmOut = NumberUtils.toInt(tmOut);
     try
     {
       HiTriggerMsg.waitMsg(HiMessageContext.getRootContext(), msgId, iTmOut);
     } catch (InterruptedException e) {
       throw new HiException(e);
     }
     return 0;
   }
 }