/*     */ package com.hisun.mng;
/*     */ 
/*     */ import com.hisun.atc.common.HiArgUtils;
/*     */ import com.hisun.exception.HiAppException;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiContext;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.parser.svrlst.HiFrontTabNode;
/*     */ import com.hisun.parser.svrlst.HiGroupNode;
/*     */ import com.hisun.parser.svrlst.HiSVRLSTParser;
/*     */ import com.hisun.parser.svrlst.HiServerNode;
/*     */ import com.hisun.register.HiRegisterService;
/*     */ import com.hisun.register.HiServiceObject;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.DocumentException;
/*     */ import org.dom4j.DocumentHelper;
/*     */ import org.dom4j.Element;
/*     */ 
/*     */ public class HiMonitor extends HiMngUtils
/*     */ {
/*  42 */   public static Logger log = HiLog.getLogger("mon.trc");
/*     */ 
/* 587 */   private static final String[][] NODE_TABLE = { { "d_code", "STC" }, { "TCODE", "STC" }, { "t_list", "LogNo" }, { "a_code", "ActNo" }, { "t_tlr", "TlrId" }, { "t_amt", "TxnAmt" }, { "t_rlt", "RSP_CD" }, { "t_chnl", "ChnlId" }, { "t_node", "NodNo" } };
/*     */ 
/* 592 */   private static final boolean[] fromHead = { true, true, false, false, false, false, false, false, false };
/*     */ 
/*     */   public int StartService(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  56 */     HiMessage msg = ctx.getCurrentMsg();
/*  57 */     HiETF root = msg.getETFBody();
/*  58 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/*  62 */     String on_off = HiArgUtils.getStringNotNull(root, "ON_OFF");
/*  63 */     if ((!(StringUtils.equals(on_off, "1"))) && (!(StringUtils.equals(on_off, "0"))))
/*     */     {
/*  65 */       throw new HiAppException(-1, "220026", "ON_OFF");
/*     */     }
/*     */ 
/*  68 */     List ls = root.getChildNodes("TCODE");
/*  69 */     for (int i = 0; i < ls.size(); ++i) {
/*  70 */       HiETF node = (HiETF)ls.get(i);
/*  71 */       String code = node.getValue();
/*  72 */       if (StringUtils.isEmpty(code)) {
/*  73 */         throw new HiAppException(-1, "220026", "TCODE");
/*     */       }
/*  75 */       if (StringUtils.equals(on_off, "1")) {
/*  76 */         HiRegisterService.start(code);
/*  77 */         if (log.isDebugEnabled())
/*  78 */           log.debug("启动服务:[" + code + "]成功");
/*     */       }
/*     */       else {
/*  81 */         HiRegisterService.stop(code);
/*  82 */         if (log.isDebugEnabled()) {
/*  83 */           log.debug("停止服务:[" + code + "]成功");
/*     */         }
/*     */       }
/*     */     }
/*  87 */     return 0;
/*     */   }
/*     */ 
/*     */   public int GetServiceStatus(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 100 */     HiMessage msg = ctx.getCurrentMsg();
/* 101 */     String code = HiArgUtils.getStringNotNull(argsMap, "code");
/* 102 */     HiETF root = msg.getETFBody();
/* 103 */     String curNodeName = argsMap.get("curNode");
/* 104 */     if (curNodeName == null)
/* 105 */       curNodeName = "ROOT";
/*     */     try
/*     */     {
/* 108 */       HiServiceObject service = HiRegisterService.getService(code);
/* 109 */       root.setGrandChildNode(curNodeName + ".Type", service.getServerType());
/*     */ 
/* 111 */       root.setGrandChildNode(curNodeName + ".Log_LVL", service.getLogLevel());
/*     */ 
/* 113 */       root.setGrandChildNode(curNodeName + ".Running", service.getRunning());
/*     */ 
/* 115 */       root.setGrandChildNode(curNodeName + ".Mon_SWT", service.getMonSwitch());
/*     */     }
/*     */     catch (HiException e)
/*     */     {
/*     */     }
/* 120 */     return 0;
/*     */   }
/*     */ 
/*     */   public int GetServerList(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 126 */     HiSVRLSTParser parser = new HiSVRLSTParser();
/* 127 */     String file = argsMap.get("file");
/* 128 */     if (StringUtils.isBlank(file)) {
/* 129 */       file = "etc/SVRLST_PUB.XML";
/*     */     }
/* 131 */     String groupName = argsMap.get("GrpNam");
/* 132 */     if (StringUtils.isBlank(groupName)) {
/* 133 */       groupName = "GRP";
/*     */     }
/* 135 */     int idx = 0;
/* 136 */     HiETF root = ctx.getCurrentMsg().getETFBody();
/*     */     try {
/* 138 */       HiFrontTabNode frontTabNode = parser.parser();
/* 139 */       for (int i = 0; i < frontTabNode.size(); ++i) {
/* 140 */         HiGroupNode groupNode = frontTabNode.getGroup(i);
/* 141 */         for (int j = 0; j < groupNode.size(); ++j) {
/* 142 */           ++idx;
/* 143 */           HiETF grp = root.addNode(groupName + "_" + idx);
/* 144 */           HiServerNode server = groupNode.getServer(j);
/* 145 */           grp.setChildValue("name", server.getName());
/* 146 */           grp.setChildValue("desc", server.getDesc());
/*     */         }
/*     */       }
/*     */     } catch (Exception e) {
/* 150 */       throw HiException.makeException(e);
/*     */     }
/*     */ 
/* 153 */     return 0;
/*     */   }
/*     */ 
/*     */   public int GetServerStatus(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 165 */     HiMessage msg = ctx.getCurrentMsg();
/* 166 */     String code = HiArgUtils.getStringNotNull(argsMap, "code");
/* 167 */     HiETF root = msg.getETFBody();
/* 168 */     String curNodeName = argsMap.get("curNode");
/* 169 */     if (curNodeName == null)
/* 170 */       curNodeName = "ROOT";
/* 171 */     Logger log = HiLog.getLogger(msg);
/* 172 */     if (log.isInfoEnabled())
/* 173 */       log.info("code:[" + code + "]:[" + curNodeName + "]");
/*     */     try
/*     */     {
/* 176 */       HiServiceObject service = HiRegisterService.getService(code);
/* 177 */       root.setGrandChildNode(curNodeName + ".Type", service.getServerType());
/*     */ 
/* 179 */       root.setGrandChildNode(curNodeName + ".Log_LVL", service.getLogLevel());
/*     */ 
/* 181 */       root.setGrandChildNode(curNodeName + ".Running", service.getRunning());
/*     */ 
/* 183 */       root.setGrandChildNode(curNodeName + ".Mon_Swt", service.getMonSwitch());
/*     */     }
/*     */     catch (HiException e)
/*     */     {
/*     */     }
/* 188 */     return 0;
/*     */   }
/*     */ 
/*     */   public int SetServiceLogSwitch(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 203 */     HiMessage msg = ctx.getCurrentMsg();
/* 204 */     HiETF root = msg.getETFBody();
/* 205 */     String on_off = HiArgUtils.getStringNotNull(root, "ON_OFF");
/* 206 */     if ((!(StringUtils.equals(on_off, "1"))) && (!(StringUtils.equals(on_off, "0"))))
/*     */     {
/* 208 */       throw new HiAppException(-1, "220026", "ON_OFF");
/*     */     }
/*     */ 
/* 211 */     List ls = root.getChildNodes("TCODE");
/* 212 */     for (int i = 0; i < ls.size(); ++i) {
/* 213 */       HiETF node = (HiETF)ls.get(i);
/* 214 */       String code = node.getValue();
/* 215 */       if (StringUtils.isEmpty(code)) {
/* 216 */         throw new HiAppException(-1, "220026", "TCODE");
/*     */       }
/* 218 */       HiRegisterService.setLogSwitch(code, on_off);
/*     */     }
/* 220 */     return 0;
/*     */   }
/*     */ 
/*     */   public int StartServer(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 235 */     HiMessage msg = ctx.getCurrentMsg();
/* 236 */     HiETF root = msg.getETFBody();
/* 237 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/* 241 */     String on_off = HiArgUtils.getStringNotNull(root, "ON_OFF");
/* 242 */     if ((!(StringUtils.equals(on_off, "1"))) && (!(StringUtils.equals(on_off, "0"))))
/*     */     {
/* 244 */       throw new HiAppException(-1, "220026", "ON_OFF");
/*     */     }
/*     */ 
/* 247 */     List ls = root.getChildNodes("TCODE");
/* 248 */     Runtime run = Runtime.getRuntime();
/*     */ 
/* 250 */     for (int i = 0; i < ls.size(); ++i) {
/* 251 */       HiETF node = (HiETF)ls.get(i);
/* 252 */       String code = node.getValue();
/* 253 */       if (StringUtils.isEmpty(code)) {
/* 254 */         throw new HiAppException(-1, "220026", "TCODE");
/*     */       }
/* 256 */       if (StringUtils.equals(on_off, "1"))
/*     */       {
/*     */         try {
/* 259 */           Process process = run.exec("hiboot -s " + code);
/* 260 */           process.waitFor();
/*     */         } catch (Exception e) {
/* 262 */           log.error("startServer Exception");
/* 263 */           log.error(e);
/*     */         }
/*     */ 
/* 266 */         if (log.isDebugEnabled())
/* 267 */           log.debug("启动服务:[" + code + "]成功");
/*     */       }
/*     */       else {
/* 270 */         HiRegisterService.stop(code);
/* 271 */         if (log.isDebugEnabled()) {
/* 272 */           log.debug("停止服务:[" + code + "]成功");
/*     */         }
/*     */       }
/*     */     }
/* 276 */     return 0;
/*     */   }
/*     */ 
/*     */   public int SetServerLogSwitch(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 291 */     HiMessage msg = ctx.getCurrentMsg();
/* 292 */     HiETF root = msg.getETFBody();
/* 293 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/* 297 */     String on_off = HiArgUtils.getStringNotNull(root, "ON_OFF");
/* 298 */     if ((!(StringUtils.equals(on_off, "1"))) && (!(StringUtils.equals(on_off, "0"))))
/*     */     {
/* 300 */       throw new HiAppException(-1, "220026", "ON_OFF");
/*     */     }
/*     */ 
/* 303 */     List ls = root.getChildNodes("TCODE");
/* 304 */     for (int i = 0; i < ls.size(); ++i) {
/* 305 */       HiETF node = (HiETF)ls.get(i);
/* 306 */       String code = node.getValue();
/* 307 */       if (StringUtils.isEmpty(code)) {
/* 308 */         throw new HiAppException(-1, "220026", "TCODE");
/*     */       }
/* 310 */       if (StringUtils.equals(on_off, "1")) {
/* 311 */         HiRegisterService.start(code);
/* 312 */         if (log.isDebugEnabled())
/* 313 */           log.debug("启动服务:[" + code + "]成功");
/*     */       }
/*     */       else {
/* 316 */         HiRegisterService.stop(code);
/* 317 */         if (log.isDebugEnabled()) {
/* 318 */           log.debug("停止服务:[" + code + "]成功");
/*     */         }
/*     */       }
/*     */     }
/* 322 */     return 0;
/*     */   }
/*     */ 
/*     */   public int MonRegiste(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 337 */     HiMessage msg = ctx.getCurrentMsg();
/* 338 */     HiETF etf = msg.getETFBody();
/*     */ 
/* 340 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/* 342 */     String on_off = etf.getChildValue("ON_OFF");
/* 343 */     if (StringUtils.isEmpty(on_off)) {
/* 344 */       createErrReturn(etf, "ERR804", "接收数据不合法－获得监控开关失败!");
/* 345 */       return -1;
/*     */     }
/*     */ 
/* 356 */     String uid = etf.getChildValue("UID");
/* 357 */     if (StringUtils.isEmpty(uid)) {
/* 358 */       createErrReturn(etf, "ERR805", "接收数据不合法－获得目标uid失败。");
/* 359 */       return 0;
/*     */     }
/* 361 */     log.info("uid:[" + uid + "]");
/*     */ 
/* 363 */     if (regTranMonSwitch(etf, on_off, uid) != 0) {
/* 364 */       createErrReturn(etf, "ERR808", "监控请求失败。");
/* 365 */       return 0;
/*     */     }
/*     */ 
/* 368 */     etf.addNode("RSP_CD", "000000");
/*     */ 
/* 370 */     String retmsg = (on_off.equals("1")) ? "监控启动成功。" : "监控停止成功。";
/* 371 */     etf.addNode("RSP_MSG", retmsg);
/*     */ 
/* 374 */     return 0;
/*     */   }
/*     */ 
/*     */   private int regTranMonSwitch(HiETF etf, String on_off, String uid) throws HiException
/*     */   {
/* 379 */     Map MON_MAP = getMonMap();
/* 380 */     boolean bb = on_off.equals("1");
/* 381 */     log.info("regTranMonSwitch");
/*     */ 
/* 383 */     List ls = etf.getChildNodes("TCODE");
/* 384 */     for (int i = 0; i < ls.size(); ++i) {
/* 385 */       HiETF node = (HiETF)ls.get(i);
/* 386 */       String code = node.getValue();
/* 387 */       if (StringUtils.isNotBlank(code))
/*     */       {
/*     */         LinkedHashSet uids;
/* 388 */         if (log.isInfoEnabled()) {
/* 389 */           log.info("启动监控:[" + code + "]:[" + uid + "]");
/*     */         }
/* 391 */         if (bb) {
/* 392 */           if (MON_MAP.containsKey(code)) {
/* 393 */             uids = (LinkedHashSet)MON_MAP.get(code);
/* 394 */             uids.add(uid);
/*     */           } else {
/* 396 */             uids = new LinkedHashSet();
/* 397 */             uids.add(uid);
/* 398 */             MON_MAP.put(code, uids);
/* 399 */             HiRegisterService.setMonSwitch(code, bb);
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 405 */           if (log.isInfoEnabled()) {
/* 406 */             log.info("关闭监控:[" + code + "]:[" + uid + "]");
/*     */           }
/* 408 */           if (MON_MAP.containsKey(code)) {
/* 409 */             uids = (LinkedHashSet)MON_MAP.get(code);
/* 410 */             uids.remove(uid);
/* 411 */             if (uids.isEmpty()) {
/* 412 */               MON_MAP.remove(code);
/* 413 */               HiRegisterService.setMonSwitch(code, bb);
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 418 */         if (bb)
/* 419 */           etf.addNode("code", code);
/*     */       }
/*     */     }
/* 422 */     if (log.isInfoEnabled()) {
/* 423 */       log.info("regTranMonSwitch:[ " + MON_MAP + "]");
/*     */     }
/*     */ 
/* 426 */     return 0;
/*     */   }
/*     */ 
/*     */   public int MonCloseMonitor(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 441 */     Map MON_MAP = getMonMap();
/* 442 */     HiMessage msg = ctx.getCurrentMsg();
/* 443 */     HiETF etf = msg.getETFBody();
/*     */ 
/* 445 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/* 447 */     String closeall = etf.getChildValue("CLOSEALL");
/* 448 */     if (!(StringUtils.isEmpty(closeall)))
/*     */     {
/* 450 */       if (log.isInfoEnabled())
/* 451 */         log.info("关闭所有监控！！！");
/* 452 */       closeAllMonitor(MON_MAP);
/* 453 */       return 0;
/*     */     }
/*     */ 
/* 456 */     List ls = etf.getChildNodes("REMOVE_UID");
/* 457 */     for (int i = 0; i < ls.size(); ++i) {
/* 458 */       HiETF node = (HiETF)ls.get(i);
/* 459 */       String uid = node.getValue();
/*     */ 
/* 461 */       if (log.isInfoEnabled())
/* 462 */         log.info("关闭用户监控：" + uid);
/* 463 */       closeMonitor(MON_MAP, uid);
/*     */     }
/* 465 */     return 0;
/*     */   }
/*     */ 
/*     */   public static void closeMonitor(Map MON_MAP, String uid) {
/* 469 */     Iterator it = MON_MAP.entrySet().iterator();
/* 470 */     log.info("closeMonitor:[" + MON_MAP + "]");
/*     */ 
/* 472 */     while (it.hasNext()) {
/* 473 */       Map.Entry entry = (Map.Entry)it.next();
/* 474 */       String code = (String)entry.getKey();
/* 475 */       LinkedHashSet uids = (LinkedHashSet)entry.getValue();
/* 476 */       log.info("closeMonitor:[" + code + "]:[" + uid + "]");
/*     */ 
/* 478 */       uids.remove(uid);
/* 479 */       if (uids.isEmpty()) {
/* 480 */         log.info("closeMonitor:[" + uids + "]");
/* 481 */         it.remove();
/*     */         try {
/* 483 */           HiRegisterService.setMonSwitch(code, false);
/*     */         }
/*     */         catch (HiException e) {
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void closeAllMonitor(Map MON_MAP) {
/* 492 */     Iterator it = MON_MAP.entrySet().iterator();
/* 493 */     log.info("closeAllMonitor01:[" + MON_MAP + "]");
/* 494 */     while (it.hasNext()) {
/* 495 */       Map.Entry entry = (Map.Entry)it.next();
/* 496 */       String code = (String)entry.getKey();
/* 497 */       log.info("closeAllMonitor01:[" + code + "]");
/* 498 */       LinkedHashSet uids = (LinkedHashSet)entry.getValue();
/*     */ 
/* 500 */       it.remove();
/*     */       try {
/* 502 */         HiRegisterService.setMonSwitch(code, false);
/*     */       }
/*     */       catch (HiException e)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int MonSwitch(HiATLParam argsMap, HiMessageContext ctx) throws HiException {
/* 511 */     HiMessage msg = ctx.getCurrentMsg();
/* 512 */     HiETF etf = msg.getETFBody();
/*     */ 
/* 514 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/* 516 */     String on_off = argsMap.get("ON_OFF");
/* 517 */     if (log.isDebugEnabled()) {
/* 518 */       log.debug("on_off:" + on_off);
/*     */     }
/*     */ 
/* 522 */     if (doTranMonSwitch(etf, on_off, log) != 0) {
/* 523 */       log.error("ERR808");
/* 524 */       createErrReturn(etf, "ERR808", "监控请求失败。");
/* 525 */       return 0;
/*     */     }
/*     */ 
/* 528 */     etf.addNode("RSP_CD", "000000");
/* 529 */     String retmsg = (on_off.equals("1")) ? "监控启动成功。" : "监控停止成功。";
/* 530 */     etf.addNode("RSP_MSG", retmsg);
/* 531 */     return 0;
/*     */   }
/*     */ 
/*     */   private int doTranMonSwitch(HiETF etf, String on_off, Logger log)
/*     */   {
/* 539 */     boolean bb = on_off.equals("1");
/*     */ 
/* 542 */     List ls = etf.getChildNodes("TCODE");
/* 543 */     for (int i = 0; i < ls.size(); ++i) {
/* 544 */       HiETF node = (HiETF)ls.get(i);
/* 545 */       String code = node.getValue();
/* 546 */       if (!(StringUtils.isNotBlank(code))) continue;
/*     */       try {
/* 548 */         HiRegisterService.setMonSwitch(code, bb);
/*     */       } catch (HiException e) {
/* 550 */         log.error("操作交易开关失败:" + e.toString(), e);
/* 551 */         return -1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 556 */     return 0;
/*     */   }
/*     */ 
/*     */   public int MonDispatch(HiATLParam argsMap, HiMessageContext ctx) throws HiException
/*     */   {
/* 561 */     Map MON_MAP = getMonMap();
/* 562 */     HiMessage msg = ctx.getCurrentMsg();
/* 563 */     HiETF etf = msg.getETFBody();
/*     */ 
/* 566 */     msg.delHeadItemVal("STC");
/*     */ 
/* 568 */     String code = etf.getChildValue("MonCod");
/* 569 */     if (log.isInfoEnabled()) {
/* 570 */       log.info("MonDispatch:" + MON_MAP + ";" + code);
/*     */     }
/*     */ 
/* 573 */     if (MON_MAP.containsKey(code))
/*     */     {
/* 575 */       LinkedHashSet uids = (LinkedHashSet)MON_MAP.get(code);
/* 576 */       for (Iterator i = uids.iterator(); i.hasNext(); ) {
/* 577 */         String uid = (String)i.next();
/* 578 */         etf.addNode("UID", uid);
/*     */       }
/*     */ 
/* 581 */       return 0;
/*     */     }
/*     */ 
/* 584 */     return -1;
/*     */   }
/*     */ 
/*     */   private void buildMonMsg(HiMessage msg, Logger log)
/*     */   {
/* 596 */     HiETF etf = msg.getETFBody();
/*     */ 
/* 598 */     StringBuffer sf = new StringBuffer("<root>");
/*     */ 
/* 600 */     for (int i = 0; i < NODE_TABLE.length; ++i)
/*     */     {
/*     */       String value;
/* 602 */       if (fromHead[i] != 0)
/* 603 */         value = msg.getHeadItem(NODE_TABLE[i][1]);
/*     */       else {
/* 605 */         value = etf.getChildValue(NODE_TABLE[i][1]);
/*     */       }
/* 607 */       if (value == null) {
/* 608 */         value = "";
/*     */       }
/* 610 */       String node = NODE_TABLE[i][0];
/* 611 */       sf.append("<").append(node).append(">").append(value).append("</").append(node).append(">");
/*     */     }
/*     */ 
/* 615 */     sf.append("</root>");
/*     */     try {
/* 617 */       Element xmlContent = DocumentHelper.parseText(sf.toString()).getRootElement();
/*     */ 
/* 619 */       if (log.isDebugEnabled()) {
/* 620 */         log.debug(xmlContent.asXML());
/*     */       }
/* 622 */       msg.setHeadItem("MNGXML", xmlContent);
/*     */     } catch (DocumentException e) {
/* 624 */       log.error(e, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Map getMonMap() {
/* 629 */     HiContext rootCtx = HiContext.getRootContext();
/* 630 */     Map monmap = (Map)rootCtx.getProperty("MON_MAP");
/* 631 */     if (monmap == null) {
/* 632 */       monmap = Collections.synchronizedMap(new HashMap());
/* 633 */       rootCtx.setProperty("MON_MAP", monmap);
/* 634 */       if (log.isDebugEnabled())
/* 635 */         log.debug("create monitor map");
/*     */     }
/* 637 */     return monmap;
/*     */   }
/*     */ }