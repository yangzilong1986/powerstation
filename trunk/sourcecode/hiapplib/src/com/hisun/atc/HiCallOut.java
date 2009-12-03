/*     */ package com.hisun.atc;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.atc.common.HiAtcLib;
/*     */ import com.hisun.atc.common.HiCmpParam;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.dispatcher.HiRouterOut;
/*     */ import com.hisun.engine.invoke.impl.HiAttributesHelper;
/*     */ import com.hisun.engine.invoke.impl.HiRunStatus;
/*     */ import com.hisun.exception.HiAppException;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiCallOut
/*     */ {
/*     */   public int CallThirdAcc(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  59 */     String TxnCod = null;
/*  60 */     String ObjSvr = null;
/*  61 */     HiMessage mess = ctx.getCurrentMsg();
/*  62 */     HiArgUtils.judgeArgsEnough(argsMap, 0, -1);
/*     */ 
/*  64 */     HiETF etfRoot = (HiETF)mess.getBody();
/*  65 */     TxnCod = HiArgUtils.getStringNotNull(argsMap, 1);
/*  66 */     etfRoot.setChildValue("RTxnCd", TxnCod);
/*     */ 
/*  68 */     if (argsMap.size() > 1) {
/*  69 */       ObjSvr = HiArgUtils.getStringNotNull(argsMap, 2);
/*     */     }
/*     */ 
/*  72 */     int iRet = HiAtcLib.hstThdTxn(argsMap, ctx, TxnCod, ObjSvr, 0, 0, 1);
/*  73 */     if (iRet < 0) {
/*  74 */       throw new HiAppException(iRet, "220064");
/*     */     }
/*  76 */     return iRet;
/*     */   }
/*     */ 
/*     */   public int CallThirdAccSaveMode(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 105 */     String TxnCod = null;
/* 106 */     String ObjSvr = null;
/* 107 */     HiMessage mess = ctx.getCurrentMsg();
/* 108 */     HiArgUtils.judgeArgsEnough(argsMap, 0, -4);
/*     */ 
/* 110 */     TxnCod = HiArgUtils.getStringNotNull(argsMap, 1);
/*     */ 
/* 112 */     HiETF etfRoot = (HiETF)mess.getBody();
/* 113 */     etfRoot.setChildValue("RTxnCd", TxnCod);
/*     */ 
/* 115 */     if (argsMap.size() > 1) {
/* 116 */       ObjSvr = HiArgUtils.getStringNotNull(argsMap, 2);
/*     */     }
/*     */ 
/* 119 */     int iRet = HiAtcLib.hstThdTxn(argsMap, ctx, TxnCod, ObjSvr, 1, 0, 1);
/* 120 */     if (iRet < 0) {
/* 121 */       throw new HiAppException(iRet, "220064");
/*     */     }
/* 123 */     return iRet;
/*     */   }
/*     */ 
/*     */   public int CallHostAcc(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 155 */     String TxnCod = null;
/* 156 */     String ObjSvr = null;
/*     */ 
/* 158 */     HiArgUtils.judgeArgsEnough(argsMap, 0, -4);
/*     */ 
/* 160 */     TxnCod = HiArgUtils.getStringNotNull(argsMap, 1);
/*     */ 
/* 162 */     if (argsMap.size() > 1) {
/* 163 */       ObjSvr = HiArgUtils.getStringNotNull(argsMap, 2);
/*     */     }
/*     */ 
/* 166 */     int iRet = HiAtcLib.hstThdTxn(argsMap, ctx, TxnCod, ObjSvr, 0, 1, 1);
/* 167 */     if (iRet < 0) {
/* 168 */       throw new HiAppException(iRet, "220064");
/*     */     }
/* 170 */     return iRet;
/*     */   }
/*     */ 
/*     */   public int CallHostAccSaveMode(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 202 */     String TxnCod = null;
/* 203 */     String ObjSvr = null;
/*     */ 
/* 205 */     HiArgUtils.judgeArgsEnough(argsMap, 0, -1);
/*     */ 
/* 207 */     TxnCod = HiArgUtils.getStringNotNull(argsMap, 1);
/*     */ 
/* 209 */     if (argsMap.size() > 1) {
/* 210 */       ObjSvr = HiArgUtils.getStringNotNull(argsMap, 2);
/*     */     }
/*     */ 
/* 213 */     int iRet = HiAtcLib.hstThdTxn(argsMap, ctx, TxnCod, ObjSvr, 1, 1, 1);
/* 214 */     if (iRet < 0) {
/* 215 */       throw new HiAppException(iRet, "220064");
/*     */     }
/* 217 */     return iRet;
/*     */   }
/*     */ 
/*     */   public int CallThirdOther(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 247 */     HiMessage mess = ctx.getCurrentMsg();
/* 248 */     String TxnCod = null;
/* 249 */     String ObjSvr = null;
/*     */ 
/* 251 */     HiArgUtils.judgeArgsEnough(argsMap, 0, -1);
/*     */ 
/* 253 */     TxnCod = HiArgUtils.getStringNotNull(argsMap, 1);
/*     */ 
/* 255 */     HiETF etfRoot = (HiETF)mess.getBody();
/* 256 */     etfRoot.setChildValue("RTxnCd", TxnCod);
/*     */ 
/* 258 */     if (argsMap.size() > 1) {
/* 259 */       ObjSvr = HiArgUtils.getStringNotNull(argsMap, 2);
/*     */     }
/*     */ 
/* 262 */     int iRet = HiAtcLib.hstThdTxn(argsMap, ctx, TxnCod, ObjSvr, 0, 0, 0);
/* 263 */     if (iRet < 0) {
/* 264 */       throw new HiAppException(iRet, "220064");
/*     */     }
/* 266 */     return iRet;
/*     */   }
/*     */ 
/*     */   public int CallThirdOtherSaveMode(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 296 */     String TxnCod = null;
/* 297 */     String ObjSvr = null;
/* 298 */     HiMessage mess = ctx.getCurrentMsg();
/* 299 */     HiArgUtils.judgeArgsEnough(argsMap, 0, -1);
/*     */ 
/* 301 */     TxnCod = HiArgUtils.getStringNotNull(argsMap, 1);
/*     */ 
/* 303 */     HiETF etfRoot = (HiETF)mess.getBody();
/* 304 */     etfRoot.setChildValue("RTxnCd", TxnCod);
/*     */ 
/* 306 */     if (argsMap.size() > 1) {
/* 307 */       ObjSvr = HiArgUtils.getStringNotNull(argsMap, 2);
/*     */     }
/*     */ 
/* 310 */     int iRet = HiAtcLib.hstThdTxn(argsMap, ctx, TxnCod, ObjSvr, 1, 0, 0);
/* 311 */     if (iRet < 0) {
/* 312 */       throw new HiAppException(iRet, "220064");
/*     */     }
/* 314 */     return iRet;
/*     */   }
/*     */ 
/*     */   public int CallHostOther(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 345 */     String TxnCod = null;
/* 346 */     String ObjSvr = null;
/* 347 */     HiMessage mess = ctx.getCurrentMsg();
/* 348 */     Logger log = HiLog.getLogger(mess);
/* 349 */     HiArgUtils.judgeArgsEnough(argsMap, 0, -1);
/*     */ 
/* 351 */     TxnCod = HiArgUtils.getStringNotNull(argsMap, 1);
/*     */ 
/* 353 */     if (argsMap.size() > 1) {
/* 354 */       ObjSvr = HiArgUtils.getStringNotNull(argsMap, 2);
/*     */     }
/*     */ 
/* 357 */     if (log.isInfoEnabled()) {
/* 358 */       log.info("HiCallOut.CallHostOther02");
/*     */     }
/* 360 */     int iRet = HiAtcLib.hstThdTxn(argsMap, ctx, TxnCod, ObjSvr, 0, 1, 0);
/* 361 */     if (log.isInfoEnabled()) {
/* 362 */       log.info("HiCallOut.CallHostOther03");
/*     */     }
/* 364 */     if (iRet < 0) {
/* 365 */       throw new HiAppException(iRet, "220064", "上主机失败！");
/*     */     }
/*     */ 
/* 368 */     return iRet;
/*     */   }
/*     */ 
/*     */   public int CallHostOtherSaveMode(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 399 */     String TxnCod = null;
/* 400 */     String ObjSvr = null;
/*     */ 
/* 402 */     HiArgUtils.judgeArgsEnough(argsMap, 0, -1);
/*     */ 
/* 404 */     TxnCod = HiArgUtils.getStringNotNull(argsMap, 1);
/*     */ 
/* 406 */     if (argsMap.size() > 1) {
/* 407 */       ObjSvr = HiArgUtils.getStringNotNull(argsMap, 2);
/*     */     }
/* 409 */     int iRet = 0;
/* 410 */     iRet = HiAtcLib.hstThdTxn(argsMap, ctx, TxnCod, ObjSvr, 1, 1, 0);
/*     */ 
/* 412 */     if (iRet < 0) {
/* 413 */       throw new HiAppException(iRet, "220064");
/*     */     }
/* 415 */     return iRet;
/*     */   }
/*     */ 
/*     */   public static int CallLocal(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     HiRunStatus runStatus;
/* 444 */     HiMessage msg = ctx.getCurrentMsg();
/* 445 */     Logger log = HiLog.getLogger(msg);
/* 446 */     if (log.isDebugEnabled()) {
/* 447 */       log.debug("Enter CallLocal");
/*     */     }
/* 449 */     String TxnCod = HiArgUtils.getStringNotNull(argsMap, "TxnCod");
/* 450 */     String CrcFlg = argsMap.get("CrcFlg");
/* 451 */     int iCrcFlg = NumberUtils.toInt(CrcFlg);
/*     */ 
/* 453 */     String GetFlg = argsMap.get("GetFlg");
/* 454 */     int iFlag = NumberUtils.toInt(GetFlg);
/*     */ 
/* 456 */     String ObjSvr = argsMap.get("ObjSvr");
/*     */ 
/* 458 */     HiETF etfRoot = msg.getETFBody();
/*     */ 
/* 460 */     if (log.isDebugEnabled()) {
/* 461 */       log.debug("CallLocal:[" + TxnCod + "]:[" + ObjSvr + "]");
/*     */     }
/*     */ 
/* 465 */     HiMessage newMsg = msg.cloneNoBody();
/* 466 */     if (newMsg.hasHeadItem("plain_type")) {
/* 467 */       newMsg.setHeadItem("plain_type", "byte");
/*     */     }
/* 469 */     newMsg.setHeadItem("STC", TxnCod);
/* 470 */     newMsg.setHeadItem("SCH", "rq");
/* 471 */     newMsg.setHeadItem("ECT", "text/etf");
/*     */ 
/* 473 */     if (StringUtils.isNotEmpty(ObjSvr)) {
/* 474 */       newMsg.setHeadItem("SDT", ObjSvr);
/*     */     }
/*     */ 
/* 477 */     String inParam = argsMap.get("In");
/* 478 */     String outParam = argsMap.get("Out");
/* 479 */     newMsg = HiAtcLib.buildInParams(inParam, msg, newMsg);
/*     */ 
/* 481 */     int retcode = 0;
/*     */     try
/*     */     {
/* 484 */       ctx.setCurrentMsg(newMsg);
/* 485 */       HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
/* 486 */       if (!(attr.isLongDbConn())) {
/* 487 */         ctx.getDataBaseUtil().close();
/*     */       }
/* 489 */       HiRouterOut.process(ctx);
/*     */     } catch (HiException e) {
/* 491 */       HiLog.logServiceError(msg, e);
/*     */ 
/* 493 */       if (e.getCode() == "211007")
/*     */       {
/* 495 */         if (iCrcFlg == 1)
/*     */         {
/* 497 */           HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
/* 498 */           runStatus.setSCRCStart(true);
/*     */         }
/* 500 */         retcode = 1;
/*     */       } else {
/* 502 */         retcode = -1;
/*     */       }
/*     */     }
/*     */ 
/* 506 */     if (log.isDebugEnabled()) {
/* 507 */       log.debug("CallLocal:[" + ctx.getCurrentMsg() + "]");
/*     */     }
/* 509 */     HiETF rcvRoot = (HiETF)ctx.getCurrentMsg().getBody();
/* 510 */     ctx.setCurrentMsg(msg);
/*     */ 
/* 512 */     if (iFlag == 0)
/*     */     {
/* 514 */       HiAtcLib.buildOutParams(outParam, rcvRoot, etfRoot, true);
/* 515 */     } else if (iFlag == 1)
/*     */     {
/* 517 */       HiAtcLib.buildOutParams(outParam, rcvRoot, etfRoot, false);
/*     */     }
/*     */ 
/* 520 */     String RspCod = rcvRoot.getChildValue("RSP_CD");
/* 521 */     if (RspCod == null) {
/* 522 */       if (iCrcFlg == 1)
/*     */       {
/* 524 */         runStatus = HiRunStatus.getRunStatus(ctx);
/* 525 */         runStatus.setSCRCStart(true);
/*     */       }
/* 527 */       throw new HiException("220058", "RSP_CD");
/*     */     }
/* 529 */     etfRoot.setChildValue("MSG_CD", RspCod);
/* 530 */     if (RspCod.equals("000000"))
/* 531 */       if (iCrcFlg == 1)
/*     */       {
/* 533 */         runStatus = HiRunStatus.getRunStatus(ctx);
/* 534 */         runStatus.setSCRCStart(true);
/*     */       }
/*     */     else {
/* 537 */       retcode = 3;
/*     */     }
/*     */ 
/* 540 */     return retcode;
/*     */   }
/*     */ 
/*     */   public int DefaultErrorProc(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 561 */     HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
/* 562 */     HiRunStatus runStatus = HiRunStatus.getRunStatus(ctx);
/* 563 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 564 */     if (log.isInfoEnabled()) {
/* 565 */       log.info("txn-attr:[" + attr + "];runstatus:[" + runStatus + "]");
/*     */     }
/*     */ 
/* 568 */     if ((attr.isSysCrct()) || ((attr.isCndCrct()) && (runStatus.isSCRCStart())))
/* 569 */       if (!(runStatus.isCRCTReg()))
/*     */       {
/* 571 */         HiIntegrity.RegisterCorrect(argsMap, ctx);
/*     */       }
/* 573 */     else if ((((attr.isSysRsnd()) || ((attr.isCndRsnd()) && (runStatus.isSCRCStart())))) && 
/* 575 */       (runStatus.isRSNDNotReg())) {
/* 576 */       HiIntegrity.RegisterRedo(argsMap, ctx);
/*     */     }
/*     */ 
/* 580 */     String tmp = ctx.getStrProp("@CMP", "DefaultErrorProc.rollback");
/*     */ 
/* 582 */     if ((HiCmpParam.getBoolean(ctx, "DefaultErrorProc", "ROLLBACK")) && 
/* 583 */       (ctx.getDataBaseUtil() != null)) {
/* 584 */       if (log.isInfoEnabled()) {
/* 585 */         log.info("DefaultErrorProc:[ database rollback ]");
/*     */       }
/* 587 */       ctx.getDataBaseUtil().rollback();
/*     */     }
/*     */ 
/* 590 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int CallThirdComm(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 617 */     HiMessage msg = ctx.getCurrentMsg();
/* 618 */     Logger log = HiLog.getLogger(msg);
/* 619 */     if (log.isDebugEnabled()) {
/* 620 */       log.debug("Enter CallThirdComm");
/*     */     }
/* 622 */     String TxnCod = HiArgUtils.getStringNotNull(argsMap, "HTxnCd");
/*     */ 
/* 624 */     String GetFlg = argsMap.get("GetFlg");
/* 625 */     int iFlag = NumberUtils.toInt(GetFlg);
/*     */ 
/* 627 */     String ObjSvr = argsMap.get("ObjSvr");
/*     */ 
/* 629 */     HiETF etfRoot = msg.getETFBody();
/*     */ 
/* 631 */     if (log.isDebugEnabled()) {
/* 632 */       log.debug("CallThirdComm:[" + TxnCod + "]:[" + ObjSvr + "]");
/*     */     }
/*     */ 
/* 635 */     HiMessage newMsg = msg.cloneNoBody();
/* 636 */     if (newMsg.hasHeadItem("plain_type")) {
/* 637 */       newMsg.setHeadItem("plain_type", "byte");
/*     */     }
/* 639 */     newMsg.setHeadItem("STC", TxnCod);
/* 640 */     newMsg.setHeadItem("SCH", "rq");
/* 641 */     newMsg.setHeadItem("ECT", "text/etf");
/* 642 */     newMsg.setType("PLTOUT");
/*     */ 
/* 644 */     String inParam = argsMap.get("In");
/* 645 */     String outParam = argsMap.get("Out");
/* 646 */     newMsg = HiAtcLib.buildInParams(inParam, msg, newMsg);
/*     */ 
/* 648 */     if (StringUtils.isNotEmpty(ObjSvr)) {
/* 649 */       newMsg.setHeadItem("SDT", ObjSvr);
/*     */     }
/*     */ 
/* 652 */     int retcode = 0;
/*     */     try
/*     */     {
/* 655 */       ctx.setCurrentMsg(newMsg);
/* 656 */       HiAttributesHelper attr = HiAttributesHelper.getAttribute(ctx);
/* 657 */       if (!(attr.isLongDbConn())) {
/* 658 */         ctx.getDataBaseUtil().close();
/*     */       }
/* 660 */       HiRouterOut.process(ctx);
/*     */     } catch (HiException e) {
/* 662 */       HiLog.logServiceError(msg, e);
/* 663 */       if (StringUtils.equals(e.getCode(), "231204"))
/* 664 */         retcode = 10;
/* 665 */       else if ((StringUtils.equals(e.getCode(), "231206")) || (StringUtils.equals(e.getCode(), "231205")))
/*     */       {
/* 670 */         retcode = 1;
/*     */       }
/* 672 */       else if (StringUtils.equals(e.getCode(), "231207"))
/*     */       {
/* 674 */         retcode = 10;
/*     */       }
/*     */       else retcode = -1;
/*     */     }
/*     */     finally
/*     */     {
/* 680 */       newMsg = ctx.getCurrentMsg();
/* 681 */       ctx.setCurrentMsg(msg);
/*     */     }
/* 683 */     if (retcode != 0) {
/* 684 */       return retcode;
/*     */     }
/*     */ 
/* 687 */     if (log.isDebugEnabled()) {
/* 688 */       log.debug("CallThirdComm:[" + ctx.getCurrentMsg() + "]");
/*     */     }
/* 690 */     if (!(newMsg.getBody() instanceof HiETF)) {
/* 691 */       return retcode;
/*     */     }
/*     */ 
/* 694 */     HiETF rcvRoot = (HiETF)newMsg.getBody();
/*     */ 
/* 696 */     if (iFlag == 0)
/*     */     {
/* 698 */       HiAtcLib.buildOutParams(outParam, rcvRoot, etfRoot, true);
/* 699 */     } else if (iFlag == 1)
/*     */     {
/* 701 */       HiAtcLib.buildOutParams(outParam, rcvRoot, etfRoot, false);
/*     */     }
/*     */ 
/* 704 */     String strTRspCd = etfRoot.getChildValue("TRSP_CD");
/* 705 */     if ((strTRspCd != null) && (!(strTRspCd.equals("000000")))) {
/* 706 */       retcode = 3;
/*     */     }
/*     */ 
/* 709 */     return retcode;
/*     */   }
/*     */ }