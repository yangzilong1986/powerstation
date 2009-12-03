/*     */ package com.hisun.atc.common;
/*     */ 
/*     */ import com.hisun.atc.fil.HiRoot;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiAppException;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.message.HiMessageHelper;
/*     */ import com.hisun.parse.HiCfgFile;
/*     */ import com.hisun.parse.HiResourceRule;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiICSProperty;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiMultiQueryLib
/*     */ {
/*     */   private static final int MAXRECORD = 1000;
/*     */   private static final int RecNumPerPage = 19;
/*     */   private static final String MULT_FOLDER = "MULTIDATDIR";
/* 644 */   private static String MULT_FOLDER_TEST = null;
/*     */ 
/*     */   public static int queryFirst(HiATLParam argsMap, HiMessage mess, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  46 */     Logger log = HiLog.getLogger(mess);
/*     */ 
/*  48 */     String strValue = "MULTIQUERY";
/*  49 */     if (argsMap.contains("SqlCmd")) {
/*  50 */       strValue = argsMap.get("SqlCmd");
/*     */     }
/*  52 */     int iMaxLinePerPage = 19;
/*  53 */     if (argsMap.contains("RecNumPerPage")) {
/*  54 */       iMaxLinePerPage = Integer.valueOf(argsMap.get("RecNumPerPage")).intValue();
/*     */     }
/*  56 */     String strFileKey = getMultiQueryKey(argsMap, mess);
/*  57 */     File file = null;
/*     */     try {
/*  59 */       file = getMultFile(strFileKey);
/*  60 */       if (log.isDebugEnabled()) {
/*  61 */         log.debug(file.getPath());
/*     */       }
/*  63 */       File parentFile = file.getParentFile();
/*  64 */       if (!(parentFile.exists())) {
/*  65 */         if (log.isDebugEnabled()) {
/*  66 */           log.debug(parentFile.getPath());
/*     */         }
/*  68 */         parentFile.mkdirs();
/*     */       }
/*  70 */       file.createNewFile();
/*     */     } catch (IOException e) {
/*  72 */       throw new HiException(e);
/*     */     }
/*     */ 
/*  75 */     String strSQL = HiDbtSqlHelper.getDynSentence(ctx, strValue);
/*     */ 
/*  81 */     List list = HiDbtSqlHelper.execMultiQuery(file, strSQL, 1000, ctx, iMaxLinePerPage);
/*     */ 
/*  83 */     if ((list == null) || (list.size() == 0)) {
/*  84 */       return 2;
/*     */     }
/*  86 */     HiETF etf = mess.getETFBody();
/*     */ 
/* 105 */     if (list.size() > iMaxLinePerPage) {
/* 106 */       etf.addNode("REC_NUM", String.valueOf(iMaxLinePerPage));
/*     */     }
/*     */     else
/*     */     {
/* 110 */       etf.addNode("REC_NUM", String.valueOf(list.size()));
/*     */     }
/*     */ 
/* 114 */     savePageNo(mess, "0", list.size(), strFileKey, ctx, iMaxLinePerPage);
/* 115 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int QueryFirstExt(HiATLParam argsMap, HiMessage mess, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 128 */     Logger log = HiLog.getLogger(mess);
/* 129 */     if ((argsMap == null) || (argsMap.size() == 0)) {
/* 130 */       throw new HiException("220026", "参数为空");
/*     */     }
/* 132 */     String aSqlName = argsMap.get("CndSts");
/* 133 */     String tblnam = argsMap.get("TblNam").toUpperCase();
/*     */ 
/* 135 */     String cndsts = HiDbtSqlHelper.getDynSentence(ctx, aSqlName);
/* 136 */     String sqlcmd = "SELECT * FROM " + tblnam + " WHERE " + cndsts;
/*     */ 
/* 138 */     int iMaxLinePerPage = 19;
/* 139 */     if (argsMap.contains("RecNumPerPage")) {
/* 140 */       iMaxLinePerPage = Integer.valueOf(argsMap.get("RecNumPerPage")).intValue();
/*     */     }
/*     */ 
/* 143 */     String strFileKey = getMultiQueryKey(argsMap, mess);
/* 144 */     File file = null;
/*     */     try {
/* 146 */       file = getMultFile(strFileKey);
/* 147 */       if (log.isDebugEnabled()) {
/* 148 */         log.debug(file.getPath());
/*     */       }
/* 150 */       File parentFile = file.getParentFile();
/* 151 */       if (!(parentFile.exists())) {
/* 152 */         if (log.isDebugEnabled()) {
/* 153 */           log.debug(parentFile.getPath());
/*     */         }
/* 155 */         parentFile.mkdirs();
/*     */       }
/* 157 */       file.createNewFile();
/*     */     } catch (IOException e) {
/* 159 */       throw new HiException(e);
/*     */     }
/*     */ 
/* 162 */     List list = HiDbtSqlHelper.execMultiQueryExt(tblnam, file, sqlcmd, iMaxLinePerPage, ctx, iMaxLinePerPage);
/*     */ 
/* 164 */     if ((list == null) || (list.size() == 0)) {
/* 165 */       return 2;
/*     */     }
/* 167 */     HiETF etf = mess.getETFBody();
/*     */ 
/* 186 */     if (list.size() > iMaxLinePerPage) {
/* 187 */       etf.addNode("REC_NUM", String.valueOf(iMaxLinePerPage));
/*     */     }
/*     */     else
/*     */     {
/* 191 */       etf.addNode("REC_NUM", String.valueOf(list.size()));
/*     */     }
/* 193 */     savePageNo(mess, "0", list.size(), strFileKey, ctx, iMaxLinePerPage);
/* 194 */     return 0;
/*     */   }
/*     */ 
/*     */   public static String getMultiQueryKey(HiATLParam argsMap, HiMessage mess) throws HiException
/*     */   {
/* 199 */     String strFileKey = null;
/*     */ 
/* 201 */     if (argsMap.contains("RECKEY")) {
/* 202 */       strFileKey = argsMap.get("RECKEY");
/*     */     }
/*     */     else {
/* 205 */       HiETF etf = mess.getETFBody();
/* 206 */       String strNodNo = etf.getChildValue("NOD_NO");
/* 207 */       String strTlrId = etf.getChildValue("TLR_ID");
/* 208 */       if ((StringUtils.isEmpty(strTlrId)) || (StringUtils.isEmpty(strNodNo))) {
/* 209 */         throw new HiException("交易要素不全:NOD_NO[" + strNodNo + "]," + "TLR_ID" + "[" + strTlrId + "]");
/*     */       }
/*     */ 
/* 212 */       strFileKey = strNodNo + strTlrId;
/*     */     }
/* 214 */     return strFileKey;
/*     */   }
/*     */ 
/*     */   public static void savePageNo(HiMessage mess, String strCurPag, int nColCounts, String strRecKey, HiMessageContext ctx, int GETCORD)
/*     */     throws HiException
/*     */   {
/* 224 */     Logger log = HiLog.getLogger(mess);
/* 225 */     mess.getETFBody().setChildValue("PAG_NO", StringUtils.leftPad(strCurPag, 5, "0"));
/*     */ 
/* 227 */     String strSql = null;
/* 228 */     if (strCurPag.equals("0")) {
/* 229 */       strCurPag = "1";
/* 230 */       mess.getETFBody().setChildValue("PAG_NO", StringUtils.leftPad(strCurPag, 5, "0"));
/*     */ 
/* 234 */       strSql = "SELECT REC_KEY FROM PUBUSRDAT WHERE  REC_KEY='" + strRecKey + "'";
/*     */ 
/* 236 */       List list = ctx.getDataBaseUtil().execQuery(strSql);
/* 237 */       int nEndPag = nColCounts / GETCORD;
/* 238 */       if (nColCounts % GETCORD != 0) {
/* 239 */         ++nEndPag;
/*     */       }
/* 241 */       if ((list != null) && (list.size() > 0))
/*     */       {
/* 243 */         strSql = "UPDATE PUBUSRDAT SET CUR_PAG='%s',END_PAG='%s' WHERE REC_KEY='%s'";
/* 244 */         ctx.getDataBaseUtil().execUpdate(strSql, strCurPag, String.valueOf(nEndPag), strRecKey);
/* 245 */         return;
/*     */       }
/*     */ 
/* 249 */       strSql = "INSERT INTO PUBUSRDAT(REC_KEY,USR_DAT,CUR_PAG,END_PAG) VALUES ('" + strRecKey + "','','" + strCurPag + "','" + String.valueOf(nEndPag) + "')";
/*     */     }
/*     */     else
/*     */     {
/* 256 */       strSql = "UPDATE PUBUSRDAT SET CUR_PAG='" + strCurPag + "' WHERE REC_KEY='" + strRecKey + "'";
/*     */     }
/*     */ 
/* 259 */     if (log.isDebugEnabled()) {
/* 260 */       log.debug(nColCounts + "=" + strSql);
/*     */     }
/* 262 */     ctx.getDataBaseUtil().execUpdate(strSql);
/*     */   }
/*     */ 
/*     */   public static int queryNext(HiATLParam argsMap, HiMessage mess, HiMessageContext ctx) throws HiException
/*     */   {
/* 267 */     Logger log = HiLog.getLogger(mess);
/* 268 */     if (log.isInfoEnabled()) {
/* 269 */       log.info("多页查询中的翻页查询");
/*     */     }
/* 271 */     int iMaxLinePerPage = 19;
/* 272 */     if (argsMap.contains("RecNumPerPage")) {
/* 273 */       iMaxLinePerPage = Integer.valueOf(argsMap.get("RecNumPerPage")).intValue();
/*     */     }
/* 275 */     String strKey = getMultiQueryKey(argsMap, mess);
/* 276 */     List list = ctx.getDataBaseUtil().execQuery("SELECT CUR_PAG,END_PAG FROM PUBUSRDAT WHERE REC_KEY='" + strKey + "'");
/*     */ 
/* 279 */     if ((list == null) || (list.size() == 0)) {
/* 280 */       return 2;
/*     */     }
/* 282 */     HashMap values = (HashMap)list.get(0);
/* 283 */     String strCurPag = (String)values.get("CUR_PAG");
/* 284 */     String strEndPag = (String)values.get("END_PAG");
/* 285 */     if (log.isInfoEnabled()) {
/* 286 */       log.info("CURPAG[" + strCurPag + "],ENDPAG[" + strEndPag + "]");
/*     */     }
/* 288 */     queryCmdConvert(mess, Integer.valueOf(strCurPag).intValue(), Integer.valueOf(strEndPag).intValue(), strKey, ctx, iMaxLinePerPage);
/*     */ 
/* 290 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int queryCmdConvert(HiMessage mess, int nCurPag, int nEndPag, String strRecKey, HiMessageContext ctx, int iMaxLinePerPage)
/*     */     throws HiException
/*     */   {
/* 296 */     File file = getMultFile(strRecKey);
/*     */ 
/* 298 */     String strValue = mess.getETFBody().getChildValue("PAG_IDX");
/*     */ 
/* 301 */     if (strValue.equals("030000"))
/*     */     {
/* 303 */       return deleteMultFile(file); }
/* 304 */     if (strValue.equals("050000"))
/*     */     {
/* 306 */       getFirstPage(file, strRecKey, ctx, iMaxLinePerPage);
/* 307 */     } else if (strValue.equals("060000"))
/*     */     {
/* 309 */       getLastPage(file, strRecKey, mess, nEndPag, ctx, iMaxLinePerPage);
/* 310 */     } else if (strValue.equals("070000"))
/*     */     {
/* 312 */       getBackPage(file, strRecKey, mess, nCurPag, ctx, iMaxLinePerPage);
/* 313 */     } else if (strValue.equals("080000"))
/*     */     {
/* 315 */       getNextPage(file, strRecKey, mess, nCurPag, nEndPag, ctx, iMaxLinePerPage);
/*     */     }
/* 317 */     else if (strValue.equals("00"))
/*     */     {
/* 319 */       getAllPage(file, mess);
/*     */     } else {
/* 321 */       Logger log = HiLog.getLogger(mess);
/* 322 */       if (log.isInfoEnabled()) {
/* 323 */         log.info("页码不正确");
/*     */       }
/* 325 */       return -1;
/*     */     }
/* 327 */     return 0;
/*     */   }
/*     */ 
/*     */   private static File getMultFile(String strRecKey)
/*     */   {
/* 335 */     File file = new File(getMultFolder() + File.separator + "MULTIDATDIR" + File.separator + "MultiQuery" + strRecKey + ".dat");
/*     */ 
/* 337 */     return file;
/*     */   }
/*     */ 
/*     */   private static int deleteMultFile(File file) throws HiException {
/* 341 */     if (file.exists()) {
/* 342 */       file.delete();
/*     */     }
/* 344 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int getFirstPage(File file, String strRecKey, HiMessageContext ctx, int iMaxLinePerPage) throws HiException
/*     */   {
/* 349 */     savePageNo(ctx.getCurrentMsg(), "1", -1, strRecKey, ctx, iMaxLinePerPage);
/*     */ 
/* 351 */     return readPageNo(file, ctx.getCurrentMsg(), 1, iMaxLinePerPage);
/*     */   }
/*     */ 
/*     */   public static int getBackPage(File file, String strRecKey, HiMessage mess, int nCurPag, HiMessageContext ctx, int iMaxLinePerPage)
/*     */     throws HiException
/*     */   {
/* 357 */     int startLine = 1;
/* 358 */     if (nCurPag - 1 >= 1)
/* 359 */       --nCurPag;
/* 360 */     if (nCurPag > 1) {
/* 361 */       startLine = (nCurPag - 1) * iMaxLinePerPage + 1;
/*     */     }
/* 363 */     savePageNo(mess, String.valueOf(nCurPag), -1, strRecKey, ctx, iMaxLinePerPage);
/*     */ 
/* 365 */     return readPageNo(file, mess, startLine, iMaxLinePerPage);
/*     */   }
/*     */ 
/*     */   public static int getNextPage(File file, String strRecKey, HiMessage mess, int nCurPag, int nEndPag, HiMessageContext ctx, int iMaxLinePerPage)
/*     */     throws HiException
/*     */   {
/* 371 */     int startLine = 1;
/* 372 */     if (nCurPag + 1 <= nEndPag) {
/* 373 */       ++nCurPag;
/*     */     }
/* 375 */     startLine = (nCurPag - 1) * iMaxLinePerPage + 1;
/* 376 */     savePageNo(mess, String.valueOf(nCurPag), -1, strRecKey, ctx, iMaxLinePerPage);
/*     */ 
/* 378 */     return readPageNo(file, mess, startLine, iMaxLinePerPage);
/*     */   }
/*     */ 
/*     */   public static int getLastPage(File file, String strRecKey, HiMessage mess, int nEndPag, HiMessageContext ctx, int iMaxLinePerPage)
/*     */     throws HiException
/*     */   {
/* 384 */     int startLine = (nEndPag - 1) * iMaxLinePerPage + 1;
/* 385 */     savePageNo(mess, String.valueOf(nEndPag), -1, strRecKey, ctx, iMaxLinePerPage);
/*     */ 
/* 387 */     return readPageNo(file, mess, startLine, iMaxLinePerPage);
/*     */   }
/*     */ 
/*     */   public static int getAllPage(File file, HiMessage mess) throws HiException {
/* 391 */     return readPageNo(file, mess, -1, -1);
/*     */   }
/*     */ 
/*     */   public static int readPageNo(File file, HiMessage mess, int startLine, int GETCORD) throws HiException
/*     */   {
/* 396 */     BufferedReader br = null;
/*     */     try {
/* 398 */       Logger log = HiLog.getLogger(mess);
/* 399 */       if (log.isInfoEnabled()) {
/* 400 */         log.info("startLine=" + startLine);
/*     */       }
/* 402 */       HiETF etf = mess.getETFBody();
/* 403 */       br = new BufferedReader(new FileReader(file));
/* 404 */       int line = 1;
/*     */       while (true)
/*     */       {
/*     */         HiETF recEtf;
/* 406 */         strLine = br.readLine();
/* 407 */         if (StringUtils.isEmpty(strLine)) {
/*     */           break;
/*     */         }
/* 410 */         if (startLine == -1) {
/* 411 */           recEtf = HiETFFactory.createETF(strLine);
/* 412 */           etf.appendNode(recEtf);
/*     */         } else {
/* 414 */           if ((line >= startLine) && (line < startLine + GETCORD)) {
/* 415 */             recEtf = HiETFFactory.createETF(strLine);
/* 416 */             recEtf.setName("REC_" + (line - startLine + 1));
/* 417 */             etf.appendNode(recEtf);
/*     */           }
/* 419 */           if (line >= startLine + GETCORD) {
/*     */             break;
/*     */           }
/*     */         }
/* 423 */         ++line;
/*     */       }
/* 425 */       br.close();
/*     */ 
/* 427 */       etf.addNode("REC_NUM", String.valueOf(line - startLine));
/*     */ 
/* 429 */       if (log.isInfoEnabled()) {
/* 430 */         log.info("add RecNum[" + String.valueOf(line - startLine) + "]");
/*     */       }
/*     */ 
/* 434 */       String strLine = 0;
/*     */ 
/* 442 */       return strLine;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*     */     }
/*     */     finally
/*     */     {
/* 438 */       if (br != null)
/*     */         try {
/* 440 */           br.close();
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void setETFNode(String strLine, HiETF recNode, HashMap colMap) throws HiException {
/* 449 */     StringTokenizer st = new StringTokenizer(strLine, "\t");
/* 450 */     int index = 1;
/* 451 */     while (st.hasMoreTokens()) {
/* 452 */       String strColValue = st.nextToken();
/* 453 */       String strArgName = (String)colMap.get(String.valueOf(index));
/* 454 */       recNode.addNode(strArgName, strColValue);
/* 455 */       ++index;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String getMultFolder() {
/* 460 */     return HiICSProperty.getWorkDir();
/*     */   }
/*     */ 
/*     */   public static int MultiQueryFromFile(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 489 */     int iMaxLinePerPage = 19;
/* 490 */     if (argsMap.contains("RecNumPerPage")) {
/* 491 */       iMaxLinePerPage = Integer.valueOf(argsMap.get("RecNumPerPage")).intValue();
/*     */     }
/*     */ 
/* 494 */     if (!(argsMap.contains("CfgFil"))) {
/* 495 */       throw new HiAppException(-1, "220307");
/*     */     }
/*     */ 
/* 498 */     String aCfgFil = argsMap.get("CfgFil");
/* 499 */     if (!(aCfgFil.startsWith("/"))) {
/* 500 */       aCfgFil = getMultFolder() + File.separator + aCfgFil;
/*     */     }
/* 502 */     if (!(argsMap.contains("InFile"))) {
/* 503 */       throw new HiAppException(-1, "220308");
/*     */     }
/*     */ 
/* 506 */     String aInFile = argsMap.get("InFile");
/* 507 */     if (!(aInFile.startsWith("/"))) {
/* 508 */       aInFile = getMultFolder() + File.separator + aInFile;
/*     */     }
/* 510 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/*     */ 
/* 512 */     String strFileKey = getMultiQueryKey(argsMap, ctx.getCurrentMsg());
/* 513 */     File file = null;
/*     */     try {
/* 515 */       file = getMultFile(strFileKey);
/* 516 */       if (log.isDebugEnabled()) {
/* 517 */         log.debug(file.getPath());
/*     */       }
/* 519 */       File parentFile = file.getParentFile();
/* 520 */       if (!(parentFile.exists())) {
/* 521 */         if (log.isDebugEnabled()) {
/* 522 */           log.debug(parentFile.getPath());
/*     */         }
/* 524 */         parentFile.mkdirs();
/*     */       }
/* 526 */       file.createNewFile();
/*     */     } catch (IOException e) {
/* 528 */       throw new HiException(e);
/*     */     }
/* 530 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/*     */ 
/* 532 */     List list = ParseFileToEtf(aCfgFil, aInFile, ctx);
/*     */ 
/* 534 */     writeFile(iMaxLinePerPage, log, file, etf, list);
/*     */ 
/* 536 */     if (list.size() > iMaxLinePerPage) {
/* 537 */       etf.addNode("REC_NUM", String.valueOf(iMaxLinePerPage));
/*     */     }
/*     */     else
/*     */     {
/* 541 */       etf.addNode("REC_NUM", String.valueOf(list.size()));
/*     */     }
/*     */ 
/* 545 */     savePageNo(ctx.getCurrentMsg(), "0", list.size(), strFileKey, ctx, iMaxLinePerPage);
/*     */ 
/* 547 */     return 0;
/*     */   }
/*     */ 
/*     */   private static void writeFile(int iMaxLinePerPage, Logger log, File file, HiETF etf, List list) throws HiException
/*     */   {
/*     */     try {
/* 553 */       if (log.isDebugEnabled()) {
/* 554 */         log.debug("RecNumPerPage[" + String.valueOf(iMaxLinePerPage) + "]");
/*     */       }
/*     */ 
/* 557 */       FileOutputStream out = new FileOutputStream(file);
/*     */ 
/* 559 */       for (int i = 0; i < list.size(); ++i) {
/* 560 */         HiETF recETF = (HiETF)list.get(i);
/*     */ 
/* 562 */         if (log.isDebugEnabled()) {
/* 563 */           log.debug("index [" + String.valueOf(i + 1) + "]");
/* 564 */           log.debug("recETF [" + recETF.toString() + "]");
/*     */         }
/* 566 */         if (i < iMaxLinePerPage) {
/* 567 */           etf.appendNode(recETF);
/*     */         }
/* 569 */         out.write(recETF.toString().getBytes());
/*     */ 
/* 571 */         out.write("\n".getBytes());
/*     */       }
/* 573 */       if (log.isDebugEnabled()) {
/* 574 */         log.debug("execMultiQuery is end.......");
/*     */       }
/* 576 */       out.close();
/*     */     } catch (Exception e) {
/* 578 */       throw new HiException(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static List ParseFileToEtf(String strCfgFile, String strInFile, HiMessageContext ctx) throws HiException
/*     */   {
/* 584 */     URL rule = HiResourceRule.getRuleForFile(strCfgFile);
/*     */ 
/* 586 */     File file = new File(strCfgFile);
/* 587 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 588 */     if (log.isDebugEnabled()) {
/* 589 */       log.debug("rule[" + rule + "] file[" + strCfgFile + "]");
/*     */     }
/* 591 */     if (file == null)
/* 592 */       throw new HiException("213302", strCfgFile);
/* 593 */     HiCfgFile cfg = HiCfgFile.getCfgFile(file, rule);
/*     */ 
/* 595 */     HiRoot root = (HiRoot)cfg.getRootInstance();
/*     */ 
/* 597 */     HiMessage msg = ctx.getCurrentMsg();
/* 598 */     HiMessage newMsg = msg.cloneNoBody();
/* 599 */     List list = new ArrayList();
/*     */     try {
/* 601 */       FileReader fr = new FileReader(new File(strInFile));
/* 602 */       BufferedReader br = new BufferedReader(fr);
/* 603 */       String Line = br.readLine();
/* 604 */       HiByteBuffer byteBuffer = new HiByteBuffer(1024, 256);
/* 605 */       int row = 0;
/*     */ 
/* 607 */       while (Line != null) {
/* 608 */         ++row;
/* 609 */         if (row == 1000) {
/* 610 */           log.warn("[Warning!] 超过最大限制数[1000]");
/* 611 */           break;
/*     */         }
/*     */ 
/* 614 */         byteBuffer.append(Line);
/*     */ 
/* 616 */         HiETF recNode = HiETFFactory.createETF("REC_" + row, "");
/* 617 */         newMsg.setBody(recNode);
/*     */ 
/* 619 */         HiMessageHelper.setUnpackMessage(newMsg, byteBuffer);
/* 620 */         ctx.setCurrentMsg(newMsg);
/*     */ 
/* 622 */         root.processInNode(ctx);
/*     */ 
/* 624 */         list.add(recNode);
/*     */ 
/* 626 */         HiMessageHelper.setPlainOffset(newMsg, 0);
/*     */ 
/* 628 */         byteBuffer.clear();
/*     */ 
/* 630 */         Line = br.readLine();
/*     */       }
/* 632 */       br.close();
/* 633 */       fr.close();
/*     */     } catch (Exception e) {
/*     */     }
/*     */     finally {
/* 637 */       ctx.setCurrentMsg(msg);
/*     */     }
/* 639 */     return list;
/*     */   }
/*     */ }