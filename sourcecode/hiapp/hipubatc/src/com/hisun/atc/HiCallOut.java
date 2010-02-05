 package com.hisun.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.atc.common.HiAtcLib;
 import com.hisun.atc.common.HiCmpParam;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.dispatcher.HiRouterOut;
 import com.hisun.engine.invoke.impl.HiAttributesHelper;
 import com.hisun.engine.invoke.impl.HiRunStatus;
 import com.hisun.exception.HiAppException;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiCallOut
 {
   public int CallThirdAcc(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String TxnCod = null;
     String ObjSvr = null;
     HiMessage mess = ctx.getCurrentMsg();
     HiArgUtils.judgeArgsEnough(argsMap, 0, -1);
 
     HiETF etfRoot = (HiETF)mess.getBody();
     TxnCod = HiArgUtils.getStringNotNull(argsMap, 1);
     etfRoot.setChildValue("RTxnCd", TxnCod);
 
     if (argsMap.size() > 1) {
       ObjSvr = HiArgUtils.getStringNotNull(argsMap, 2);
     }
 
     int iRet = HiAtcLib.hstThdTxn(argsMap, ctx, TxnCod, ObjSvr, 0, 0, 1);
     if (iRet < 0) {
       throw new HiAppException(iRet, "220064");
     }
     return iRet;
   }
 
   public int CallThirdAccSaveMode(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String TxnCod = null;
     String ObjSvr = null;
     HiMessage mess = ctx.getCurrentMsg();
     HiArgUtils.judgeArgsEnough(argsMap, 0, -4);
 
     TxnCod = HiArgUtils.getStringNotNull(argsMap, 1);
 
     HiETF etfRoot = (HiETF)mess.getBody();
     etfRoot.setChildValue("RTxnCd", TxnCod);
 
     if (argsMap.size() > 1) {
       ObjSvr = HiArgUtils.getStringNotNull(argsMap, 2);
     }
 
     int iRet = HiAtcLib.hstThdTxn(argsMap, ctx, TxnCod, ObjSvr, 1, 0, 1);
     if (iRet < 0) {
       throw new HiAppException(iRet, "220064");
     }
     return iRet;
   }
 
   public int CallHostAcc(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String TxnCod = null;
     String ObjSvr = null;
 
     HiArgUtils.judgeArgsEnough(argsMap, 0, -4);
 
     TxnCod = HiArgUtils.getStringNotNull(argsMap, 1);
 
     if (argsMap.size() > 1) {
       ObjSvr = HiArgUtils.getStringNotNull(argsMap, 2);
     }
 
     int iRet = HiAtcLib.hstThdTxn(argsMap, ctx, TxnCod, ObjSvr, 0, 1, 1);
     if (iRet < 0) {
       throw new HiAppException(iRet, "220064");
     }
     return iRet;
   }
 
   public int CallHostAccSaveMode(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String TxnCod = null;
     String ObjSvr = null;
 
     HiArgUtils.judgeArgsEnough(argsMap, 0, -1);
 
     TxnCod = HiArgUtils.getStringNotNull(argsMap, 1);
 
     if (argsMap.size() > 1) {
       ObjSvr = HiArgUtils.getStringNotNull(argsMap, 2);
     }
 
     int iRet = HiAtcLib.hstThdTxn(argsMap, ctx, TxnCod, ObjSvr, 1, 1, 1);
     if (iRet < 0) {
       throw new HiAppException(iRet, "220064");
     }
     return iRet;
   }
 
   public int CallThirdOther(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     String TxnCod = null;
     String ObjSvr = null;
 
     HiArgUtils.judgeArgsEnough(argsMap, 0, -1);
 
     TxnCod = HiArgUtils.getStringNotNull(argsMap, 1);
 
     HiETF etfRoot = (HiETF)mess.getBody();
     etfRoot.setChildValue("RTxnCd", TxnCod);
 
     if (argsMap.size() > 1) {
       ObjSvr = HiArgUtils.getStringNotNull(argsMap, 2);
     }
 
     int iRet = HiAtcLib.hstThdTxn(argsMap, ctx, TxnCod, ObjSvr, 0, 0, 0);
     if (iRet < 0) {
       throw new HiAppException(iRet, "220064");
     }
     return iRet;
   }
 
   public int CallThirdOtherSaveMode(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String TxnCod = null;
     String ObjSvr = null;
     HiMessage mess = ctx.getCurrentMsg();
     HiArgUtils.judgeArgsEnough(argsMap, 0, -1);
 
     TxnCod = HiArgUtils.getStringNotNull(argsMap, 1);
 
     HiETF etfRoot = (HiETF)mess.getBody();
     etfRoot.setChildValue("RTxnCd", TxnCod);
 
     if (argsMap.size() > 1) {
       ObjSvr = HiArgUtils.getStringNotNull(argsMap, 2);
     }
 
     int iRet = HiAtcLib.hstThdTxn(argsMap, ctx, TxnCod, ObjSvr, 1, 0, 0);
     if (iRet < 0) {
       throw new HiAppException(iRet, "220064");
     }
     return iRet;
   }
 
   public int CallHostOther(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String TxnCod = null;
     String ObjSvr = null;
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     HiArgUtils.judgeArgsEnough(argsMap, 0, -1);
 
     TxnCod = HiArgUtils.getStringNotNull(argsMap, 1);
 
     if (argsMap.size() > 1) {
       ObjSvr = HiArgUtils.getStringNotNull(argsMap, 2);
     }
 
     if (log.isInfoEnabled()) {
       log.info("HiCallOut.CallHostOther02");
     }
     int iRet = HiAtcLib.hstThdTxn(argsMap, ctx, TxnCod, ObjSvr, 0, 1, 0);
     if (log.isInfoEnabled()) {
       log.info("HiCallOut.CallHostOther03");
     }
     if (iRet < 0) {
       throw new HiAppException(iRet, "220064", "上主机失败！");
     }
 
     return iRet;
   }
 
   public int CallHostOtherSaveMode(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     String TxnCod = null;
     String ObjSvr = null;
 
     HiArgUtils.judgeArgsEnough(argsMap, 0, -1);
 
     TxnCod = HiArgUtils.getStringNotNull(argsMap, 1);
 
     if (argsMap.size() > 1) {
       ObjSvr = HiArgUtils.getStringNotNull(argsMap, 2);
     }
     int iRet = 0;
     iRet = HiAtcLib.hstThdTxn(argsMap, ctx, TxnCod, ObjSvr, 1, 1, 0);
 
     if (iRet < 0) {
       throw new HiAppException(iRet, "220064");
     }
     return iRet;
   }
 
   public static int CallLocal(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiRunStatus runStatus;
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("Enter CallLocal");
     }
     String TxnCod = HiArgUtils.getStringNotNull(argsMap, "TxnCod");
     String CrcFlg = argsMap.get("CrcFlg");
     int iCrcFlg = NumberUtils.toInt(CrcFlg);
 
     String GetFlg = argsMap.get("GetFlg");
     int iFlag = NumberUtils.toInt(GetFlg);
 
     String ObjSvr = argsMap.get("ObjSvr");
 
     HiETF etfRoot = msg.getETFBody();
 
     if (log.isDebugEnabled()) {
       log.debug("CallLocal:[" + TxnCod + "]:[" + ObjSvr + "]");
     }
 
     HiMessage newMsg = msg.cloneNoBody();
     if (newMsg.hasHeadItem("plain_type")) {
       newMsg.setHeadItem("plain_type", "byte");
     }
     newMsg.setHeadItem("STC", TxnCod);
     newMsg.setHeadItem("SCH", "rq");
     newMsg.setHeadItem("ECT", "text/etf");
 
     if (StringUtils.isNotEmpty(ObjSvr)) {
       newMsg.setHeadItem("SDT", ObjSvr);
     }
 
     String inParam = argsMap.get("In");
     String outParam = argsMap.get("Out");
     newMsg = HiAtcLib.buildInParams(inParam, msg, newMsg);
 
     int retcode = 0;
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
 
       if (e.getCode() == "211007")
       {
         if (iCrcFlg == 1)
         {
           HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
           runStatus.setSCRCStart(true);
         }
         retcode = 1;
       } else {
         retcode = -1;
       }
     }
 
     if (log.isDebugEnabled()) {
       log.debug("CallLocal:[" + ctx.getCurrentMsg() + "]");
     }
     HiETF rcvRoot = (HiETF)ctx.getCurrentMsg().getBody();
     ctx.setCurrentMsg(msg);
 
     if (iFlag == 0)
     {
       HiAtcLib.buildOutParams(outParam, rcvRoot, etfRoot, true);
     } else if (iFlag == 1)
     {
       HiAtcLib.buildOutParams(outParam, rcvRoot, etfRoot, false);
     }
 
     String RspCod = rcvRoot.getChildValue("RSP_CD");
     if (RspCod == null) {
       if (iCrcFlg == 1)
       {
         runStatus = HiRunStatus.getRunStatus(ctx);
         runStatus.setSCRCStart(true);
       }
       throw new HiException("220058", "RSP_CD");
     }
     etfRoot.setChildValue("MSG_CD", RspCod);
     if (RspCod.equals("000000"))
       if (iCrcFlg == 1)
       {
         runStatus = HiRunStatus.getRunStatus(ctx);
         runStatus.setSCRCStart(true);
       }
     else {
       retcode = 3;
     }
 
     return retcode;
   }
 
   public int DefaultErrorProc(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     if (log.isInfoEnabled()) {
       log.info("txn-attr:[" + attr + "];runstatus:[" + runStatus + "]");
     }
 
     if ((attr.isSysCrct()) || ((attr.isCndCrct()) && (runStatus.isSCRCStart())))
       if (!(runStatus.isCRCTReg()))
       {
         HiIntegrity.RegisterCorrect(argsMap, ctx);
       }
     else if ((((attr.isSysRsnd()) || ((attr.isCndRsnd()) && (runStatus.isSCRCStart())))) && 
       (runStatus.isRSNDNotReg())) {
       HiIntegrity.RegisterRedo(argsMap, ctx);
     }
 
     String tmp = ctx.getStrProp("@CMP", "DefaultErrorProc.rollback");
 
     if ((HiCmpParam.getBoolean(ctx, "DefaultErrorProc", "ROLLBACK")) && 
       (ctx.getDataBaseUtil() != null)) {
       if (log.isInfoEnabled()) {
         log.info("DefaultErrorProc:[ database rollback ]");
       }
       ctx.getDataBaseUtil().rollback();
     }
 
     return 0;
   }
 
   public static int CallThirdComm(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if (log.isDebugEnabled()) {
       log.debug("Enter CallThirdComm");
     }
     String TxnCod = HiArgUtils.getStringNotNull(argsMap, "HTxnCd");
 
     String GetFlg = argsMap.get("GetFlg");
     int iFlag = NumberUtils.toInt(GetFlg);
 
     String ObjSvr = argsMap.get("ObjSvr");
 
     HiETF etfRoot = msg.getETFBody();
 
     if (log.isDebugEnabled()) {
       log.debug("CallThirdComm:[" + TxnCod + "]:[" + ObjSvr + "]");
     }
 
     HiMessage newMsg = msg.cloneNoBody();
     if (newMsg.hasHeadItem("plain_type")) {
       newMsg.setHeadItem("plain_type", "byte");
     }
     newMsg.setHeadItem("STC", TxnCod);
     newMsg.setHeadItem("SCH", "rq");
     newMsg.setHeadItem("ECT", "text/etf");
     newMsg.setType("PLTOUT");
 
     String inParam = argsMap.get("In");
     String outParam = argsMap.get("Out");
     newMsg = HiAtcLib.buildInParams(inParam, msg, newMsg);
 
     if (StringUtils.isNotEmpty(ObjSvr)) {
       newMsg.setHeadItem("SDT", ObjSvr);
     }
 
     int retcode = 0;
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
         retcode = 10;
       else if ((StringUtils.equals(e.getCode(), "231206")) || (StringUtils.equals(e.getCode(), "231205")))
       {
         retcode = 1;
       }
       else if (StringUtils.equals(e.getCode(), "231207"))
       {
         retcode = 10;
       }
       else retcode = -1;
     }
     finally
     {
       newMsg = ctx.getCurrentMsg();
       ctx.setCurrentMsg(msg);
     }
     if (retcode != 0) {
       return retcode;
     }
 
     if (log.isDebugEnabled()) {
       log.debug("CallThirdComm:[" + ctx.getCurrentMsg() + "]");
     }
     if (!(newMsg.getBody() instanceof HiETF)) {
       return retcode;
     }
 
     HiETF rcvRoot = (HiETF)newMsg.getBody();
 
     if (iFlag == 0)
     {
       HiAtcLib.buildOutParams(outParam, rcvRoot, etfRoot, true);
     } else if (iFlag == 1)
     {
       HiAtcLib.buildOutParams(outParam, rcvRoot, etfRoot, false);
     }
 
     String strTRspCd = etfRoot.getChildValue("TRSP_CD");
     if ((strTRspCd != null) && (!(strTRspCd.equals("000000")))) {
       retcode = 3;
     }
 
     return retcode;
   }
 }