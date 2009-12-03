/*     */ package com.hisun.atc.bat;
/*     */ 
/*     */ import com.hisun.atc.common.HiAtcLib;
/*     */ import com.hisun.atc.common.HiDBCursor;
/*     */ import com.hisun.atc.common.HiDbtSqlHelper;
/*     */ import com.hisun.atc.common.HiDbtUtils;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.engine.HiEngineModel;
/*     */ import com.hisun.engine.HiEngineUtilities;
/*     */ import com.hisun.engine.invoke.HiIEngineModel;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.message.HiMessageHelper;
/*     */ import com.hisun.util.HiByteBuffer;
/*     */ import com.hisun.util.HiFileOutputStream;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.SystemUtils;
/*     */ import org.apache.commons.lang.math.NumberUtils;
/*     */ 
/*     */ public class HiBatchProcess extends HiEngineModel
/*     */ {
/*  42 */   private boolean _ignoreError = false;
/*     */ 
/*  46 */   private boolean _sqnFlag = true;
/*     */ 
/*  51 */   private int _commitRecords = 1000;
/*     */ 
/*  56 */   private String _sqnNodeName = "Sqn_No";
/*     */ 
/*  62 */   private boolean _statFlag = true;
/*     */ 
/*  64 */   private boolean _ignore_blank_line = false;
/*     */ 
/*  66 */   private int _begin_ignore_line = 0;
/*     */ 
/*  68 */   private int _end_ignore_line = 0;
/*     */   private String _debugFile;
/*     */   private String _errFile;
/*     */   private HiData _dataNode;
/*     */   private HiHead _headNode;
/*     */   private HiEnd _endNode;
/*     */   private String _name;
/*     */ 
/*     */   public String getNodeName()
/*     */   {
/*  95 */     return "Process";
/*     */   }
/*     */ 
/*     */   public void setBegin_ignore_line(int begin_ignore_line) {
/*  99 */     this._begin_ignore_line = begin_ignore_line;
/*     */   }
/*     */ 
/*     */   public void setDebug_file(String debug_file) {
/* 103 */     this._debugFile = debug_file;
/*     */   }
/*     */ 
/*     */   public void setEnd_ignore_line(int end_ignore_line) {
/* 107 */     this._end_ignore_line = end_ignore_line;
/*     */   }
/*     */ 
/*     */   public void setIgnore_blank_line(String ignore_blank_line) {
/* 111 */     if (StringUtils.equalsIgnoreCase(ignore_blank_line, "Y"))
/* 112 */       this._ignore_blank_line = true;
/*     */   }
/*     */ 
/*     */   public void setErr_file(String err_file) {
/* 116 */     this._errFile = err_file;
/*     */   }
/*     */ 
/*     */   public void setName(String name) {
/* 120 */     this._name = name;
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 124 */     return this._name;
/*     */   }
/*     */ 
/*     */   public void setIgnore_error(String ignore_error) {
/* 128 */     if (StringUtils.equalsIgnoreCase(ignore_error, "Y"))
/* 129 */       this._ignoreError = true;
/*     */   }
/*     */ 
/*     */   public void setSqn_flag(String sqn_flag) {
/* 133 */     if (StringUtils.equalsIgnoreCase(sqn_flag, "N"))
/* 134 */       this._sqnFlag = false;
/*     */   }
/*     */ 
/*     */   public void setSqn_node_name(String sqn_node_name) {
/* 138 */     this._sqnNodeName = sqn_node_name;
/*     */   }
/*     */ 
/*     */   public void setStat_flag(String stat_flag) throws HiException {
/* 142 */     if (StringUtils.equalsIgnoreCase(stat_flag, "N"))
/* 143 */       this._statFlag = false;
/*     */   }
/*     */ 
/*     */   public void addChilds(HiIEngineModel child)
/*     */     throws HiException
/*     */   {
/* 149 */     if (child instanceof HiData)
/* 150 */       this._dataNode = ((HiData)child);
/* 151 */     else if (child instanceof HiHead)
/* 152 */       this._headNode = ((HiHead)child);
/*     */     else
/* 154 */       this._endNode = ((HiEnd)child);
/* 155 */     super.addChilds(child);
/*     */   }
/*     */ 
/*     */   public void loadAfter() throws HiException
/*     */   {
/* 160 */     List childs = getChilds();
/* 161 */     boolean isExistData = false;
/* 162 */     for (int i = 0; i < childs.size(); ++i) {
/* 163 */       HiEngineModel child = (HiEngineModel)childs.get(i);
/* 164 */       if (child instanceof HiData) {
/* 165 */         isExistData = true;
/* 166 */         break;
/*     */       }
/*     */     }
/* 169 */     if (!(isExistData))
/* 170 */       throw new HiException("220067");
/*     */   }
/*     */ 
/*     */   public int importToDB(HiMessageContext ctx, HiPackInfo packInfo)
/*     */     throws HiException
/*     */   {
/* 194 */     HiMessage msg = ctx.getCurrentMsg();
/* 195 */     Logger log = HiLog.getLogger(msg);
/* 196 */     HiAbstractFMT sumNode = null;
/*     */ 
/* 198 */     HiBatchFile batchFile = new HiBatchFile();
/* 199 */     packInfo.batchFile = batchFile;
/*     */ 
/* 201 */     batchFile.setBeginIgnoreLine(this._begin_ignore_line);
/* 202 */     batchFile.setIgnoreBlankLine(this._ignore_blank_line);
/* 203 */     batchFile.setEndIgnoreLine(this._end_ignore_line);
/*     */ 
/* 205 */     if (this._headNode != null) {
/* 206 */       batchFile.setSumFlag(1);
/* 207 */       batchFile.setSumRecordLength(this._headNode.getRecordLength());
/* 208 */       sumNode = this._headNode;
/* 209 */       packInfo.sumFlag = true;
/* 210 */     } else if (this._endNode != null) {
/* 211 */       batchFile.setSumFlag(2);
/* 212 */       batchFile.setSumRecordLength(this._endNode.getRecordLength());
/* 213 */       sumNode = this._endNode;
/* 214 */       packInfo.sumFlag = true;
/*     */     }
/* 216 */     batchFile.setDataRecordLength(this._dataNode.getRecordLength());
/* 217 */     if (this._sqnFlag) {
/* 218 */       String strSqnValue = msg.getETFBody().getChildValue(this._sqnNodeName);
/* 219 */       packInfo.seqNo = NumberUtils.toInt(strSqnValue);
/* 220 */       if (log.isInfoEnabled()) {
/* 221 */         log.info("顺序号节点:[" + packInfo.seqNo + "]");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 226 */     batchFile.setFile(packInfo.fileName);
/* 227 */     packInfo.statFlag = this._statFlag;
/* 228 */     batchFile.open();
/* 229 */     if (log.isInfoEnabled()) {
/* 230 */       log.info("批量文件信息:" + batchFile);
/*     */     }
/*     */ 
/* 233 */     if ((packInfo.ornCnt != 0) && (packInfo.ornCnt != batchFile.getDataCnt())) {
/* 234 */       throw new HiException("220076", packInfo.fileName, String.valueOf(batchFile.getDataCnt()), String.valueOf(packInfo.ornCnt));
/*     */     }
/*     */ 
/* 240 */     if (StringUtils.equals(packInfo.applyLogNoFlag, "1")) {
/* 241 */       packInfo.logNo = HiAtcLib.sqnGetLogNo(ctx, batchFile.getTotalCnt());
/* 242 */       if (log.isInfoEnabled()) {
/* 243 */         log.info("初始日志流水号:[" + packInfo.logNo + "]");
/*     */       }
/*     */     }
/*     */ 
/* 247 */     HiByteBuffer byteBuffer = new HiByteBuffer(1024, 256);
/* 248 */     int ret = 2;
/* 249 */     HiETF sum = HiETFFactory.createETF("SUM", "");
/*     */     try {
/* 251 */       while (batchFile.getNextRecord(byteBuffer) != null) {
/* 252 */         if (batchFile.isSumRecord()) {
/* 253 */           ret1 = processSumRecord(batchFile, sumNode, sum, packInfo, ctx);
/*     */ 
/* 255 */           byteBuffer.clear();
/* 256 */           if (ret1 == 1) {
/*     */             break;
/*     */           }
/*     */         }
/* 260 */         if (ret != 0) {
/* 261 */           ret = 0;
/*     */         }
/* 263 */         int ret1 = processDataRecord(batchFile, this._dataNode, packInfo, ctx);
/*     */ 
/* 265 */         byteBuffer.clear();
/* 266 */         if (ret1 == 1) {
/*     */           break;
/*     */         }
/* 269 */         if (ret1 != 2)
/*     */         {
/* 272 */           if ((ret1 == 3) && (!(this._ignoreError))) {
/* 273 */             int i = 1;
/*     */             return i;
/*     */           }
/*     */         }
/* 275 */         if (batchFile.getCurrRecordNo() % this._commitRecords == 0) {
/* 276 */           ctx.getDataBaseUtil().commit();
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (HiException e)
/*     */     {
/*     */     }
/*     */     finally
/*     */     {
/* 286 */       batchFile.close();
/* 287 */       ctx.setCurrentMsg(msg);
/*     */     }
/*     */ 
/* 290 */     ctx.getDataBaseUtil().commit();
/* 291 */     msg.getETFBody().appendNode(sum);
/* 292 */     if (log.isInfoEnabled()) {
/* 293 */       log.info(sm.getString("HiBatchProcess.suminfo", String.valueOf(packInfo.totalAmt), String.valueOf(packInfo.totalCnt), String.valueOf(packInfo.dTotalAmt), String.valueOf(packInfo.dTotalCnt), String.valueOf(packInfo.ornAmt), String.valueOf(packInfo.ornCnt)));
/*     */     }
/*     */ 
/* 302 */     packInfo.check();
/* 303 */     return ret;
/*     */   }
/*     */ 
/*     */   private int processDataRecord(HiBatchFile batchFile, HiData dataNode, HiPackInfo packInfo, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*     */     String sqlSentence;
/* 322 */     HiMessage msg = ctx.getCurrentMsg();
/* 323 */     Logger log = HiLog.getLogger(msg);
/* 324 */     String value = null;
/* 325 */     int ret = 0;
/*     */ 
/* 327 */     if (log.isInfoEnabled()) {
/* 328 */       log.info(sm.getString("HiBatchProcess.parseFileData", String.valueOf(batchFile.getCurrRecordNo())));
/*     */ 
/* 330 */       log.info(batchFile.getCurrentRecord());
/*     */     }
/*     */ 
/* 333 */     HiMessage newMsg = new HiMessage(msg);
/* 334 */     HiMessageHelper.setUnpackMessage(newMsg, batchFile.getCurrentRecord());
/* 335 */     HiETF root = newMsg.getETFBody();
/* 336 */     root.setChildValue("HTXN_STS", "U");
/* 337 */     root.setChildValue("HPR_CHK", "0");
/* 338 */     root.setChildValue("LST_CHK", "0");
/* 339 */     root.setChildValue("CHK_FLG", "0");
/* 340 */     root.setChildValue("IS_TXN", "Y");
/* 341 */     ctx.setCurrentMsg(newMsg);
/* 342 */     if (packInfo.logNo != -1L) {
/* 343 */       root.setChildValue("LOG_NO", StringUtils.leftPad(String.valueOf(packInfo.logNo), 14, '0'));
/*     */ 
/* 345 */       packInfo.logNo += 1L;
/*     */     }
/*     */ 
/* 348 */     if (packInfo.seqNo != -1L) {
/* 349 */       root.setChildValue(this._sqnNodeName, String.valueOf(packInfo.seqNo));
/* 350 */       packInfo.seqNo += 1L;
/*     */     }
/* 352 */     dataNode.process(ctx);
/* 353 */     ctx.setCurrentMsg(msg);
/* 354 */     if ((value = root.getChildValue("STOP")) != null) {
/* 355 */       if (log.isInfoEnabled()) {
/* 356 */         log.info(sm.getString("HiBatchProcess.stop00", String.valueOf(batchFile.getCurrRecordNo()), value));
/*     */       }
/*     */ 
/* 359 */       return 1;
/*     */     }
/* 361 */     packInfo.dTotalCnt += 1;
/* 362 */     String txnAmt = null; String sign = null;
/* 363 */     int recAmtFlg = 0;
/* 364 */     if ((txnAmt = root.getChildValue("TXN_AMT")) == null) {
/* 365 */       recAmtFlg = 2;
/* 366 */       packInfo.recAmtFlg = false;
/* 367 */       log.info("TXN_AMT:不统计");
/*     */     }
/*     */ 
/* 370 */     if (recAmtFlg != 2) {
/* 371 */       packInfo.recAmtFlg = true;
/* 372 */       sign = root.getChildValue("SIGN");
/* 373 */       long iTxnAmt = NumberUtils.toLong(txnAmt.trim());
/* 374 */       log.info("TXN_AMT:" + String.valueOf(txnAmt) + " :" + iTxnAmt);
/*     */ 
/* 376 */       if (StringUtils.equals(sign, "-")) {
/* 377 */         packInfo.dTotalAmt -= iTxnAmt;
/* 378 */         root.setChildValue("TXN_AMT", StringUtils.leftPad(String.valueOf(-1L * iTxnAmt), 15, '0'));
/*     */       }
/*     */       else {
/* 381 */         packInfo.dTotalAmt += iTxnAmt;
/*     */       }
/*     */     }
/*     */ 
/* 385 */     if (log.isInfoEnabled()) {
/* 386 */       log.info(sm.getString("HiBatchProcess.parseFileData01", String.valueOf(packInfo.dTotalAmt)));
/*     */     }
/*     */ 
/* 390 */     if (root.getChildValue("JUMP") != null)
/*     */     {
/* 392 */       if (log.isInfoEnabled()) {
/* 393 */         log.info(sm.getString("HiBatchProcess.jump00", String.valueOf(batchFile.getCurrRecordNo() - 1)));
/*     */       }
/*     */ 
/* 396 */       return 2;
/*     */     }
/*     */ 
/* 399 */     if (dataNode.isExtendFlag()) {
/* 400 */       if (HiDbtUtils.dbtextinsrec(packInfo.tableName, root, ctx) == 1)
/*     */       {
/* 402 */         if (packInfo.isUpdate)
/*     */         {
/* 404 */           sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, packInfo.conSts, root);
/* 405 */           HiDbtUtils.dbtsqlupdreccon(packInfo.tableName, sqlSentence, root, ctx);
/*     */         }
/*     */         else
/*     */         {
/* 409 */           ret = 3;
/*     */         }
/*     */       }
/*     */     }
/* 413 */     else if (HiDbtUtils.dbtsqlinsrec(packInfo.tableName, root, ctx) == 1)
/*     */     {
/* 415 */       if (packInfo.isUpdate)
/*     */       {
/* 417 */         sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, packInfo.conSts, root);
/* 418 */         HiDbtUtils.dbtsqlupdreccon(packInfo.tableName, sqlSentence, root, ctx);
/*     */       }
/*     */       else
/*     */       {
/* 422 */         ret = 3;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 427 */     return ret;
/*     */   }
/*     */ 
/*     */   private int processSumRecord(HiBatchFile batchFile, HiAbstractFMT sumNode, HiETF sum, HiPackInfo packInfo, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 449 */     HiMessage msg = ctx.getCurrentMsg();
/* 450 */     Logger log = HiLog.getLogger(msg);
/* 451 */     String value = null;
/*     */ 
/* 453 */     if (log.isInfoEnabled()) {
/* 454 */       log.info(sm.getString("HiBatchProcess.parseFileSum", String.valueOf(batchFile.getCurrRecordNo())));
/*     */     }
/*     */ 
/* 457 */     HiMessage newMsg = msg.cloneNoBody();
/* 458 */     HiMessageHelper.setUnpackMessage(newMsg, batchFile.getCurrentRecord());
/* 459 */     newMsg.setBody(sum);
/* 460 */     ctx.setCurrentMsg(newMsg);
/* 461 */     sumNode.process(ctx);
/* 462 */     ctx.setCurrentMsg(msg);
/* 463 */     if ((value = sum.getChildValue("STOP")) != null) {
/* 464 */       if (log.isInfoEnabled()) {
/* 465 */         log.info(sm.getString("HiBatchProcess.stop00", String.valueOf(batchFile.getCurrRecordNo()), value));
/*     */       }
/*     */ 
/* 468 */       return 1;
/*     */     }
/*     */ 
/* 471 */     value = sum.getChildValue("ORN_CNT");
/* 472 */     packInfo.totalCnt = NumberUtils.toInt(value);
/* 473 */     value = sum.getChildValue("ORN_AMT");
/* 474 */     packInfo.totalAmt = NumberUtils.toLong(value);
/* 475 */     value = sum.getChildValue("SIGN");
/* 476 */     if (StringUtils.equalsIgnoreCase(value, "-")) {
/* 477 */       packInfo.totalAmt *= -1L;
/*     */     }
/*     */ 
/* 480 */     if (log.isInfoEnabled()) {
/* 481 */       log.info(sm.getString("HiBatchProcess.parseFileSum01", String.valueOf(packInfo.totalCnt), String.valueOf(packInfo.totalAmt)));
/*     */     }
/*     */ 
/* 485 */     return 0;
/*     */   }
/*     */ 
/*     */   public int exportFromDB(HiMessageContext ctx, HiPackInfo packInfo)
/*     */     throws HiException
/*     */   {
/* 498 */     HiMessage msg = ctx.getCurrentMsg();
/* 499 */     Logger log = HiLog.getLogger(msg);
/* 500 */     int ret = 0;
/* 501 */     int step = 0;
/* 502 */     int startSeqNo = -1;
/* 503 */     if (this._sqnFlag) {
/* 504 */       String strSqnValue = msg.getETFBody().getChildValue(this._sqnNodeName);
/* 505 */       startSeqNo = NumberUtils.toInt(strSqnValue);
/*     */     }
/*     */ 
/* 508 */     if ((this._dataNode.isExtendFlag()) && 
/* 509 */       (StringUtils.isEmpty(packInfo.tableName))) {
/* 510 */       throw new HiException("220026", "TableName");
/*     */     }
/*     */ 
/* 514 */     if (log.isDebugEnabled()) {
/* 515 */       log.debug(sm.getString("HiBatchProcess.doPack00", packInfo.fileName, packInfo.sqlCond, String.valueOf(startSeqNo), packInfo.tableName));
/*     */     }
/*     */ 
/* 520 */     if (this._dataNode == null);
/* 528 */     HiFileOutputStream fos = new HiFileOutputStream(packInfo.fileName);
/* 529 */     HiDBCursor dbCursor = null;
/* 530 */     HiByteBuffer byteBuffer = new HiByteBuffer(1024, 256);
/*     */     try {
/* 532 */       if (this._headNode != null) {
/* 533 */         HiEngineUtilities.setCurFlowStep(step++);
/* 534 */         byteBuffer.clear();
/* 535 */         HiMessage msg1 = new HiMessage(msg);
/* 536 */         HiMessageHelper.setPackMessage(msg1, byteBuffer);
/* 537 */         ctx.setCurrentMsg(msg1);
/* 538 */         this._headNode.process(ctx);
/* 539 */         ctx.setCurrentMsg(msg);
/* 540 */         byteBuffer = HiMessageHelper.getUnpackMessageBuffer(msg1);
/* 541 */         if (this._headNode.isLineWrap()) {
/* 542 */           byteBuffer.append(SystemUtils.LINE_SEPARATOR);
/*     */         }
/* 544 */         fos.write(byteBuffer.getBytes());
/*     */       }
/*     */ 
/* 547 */       if (this._dataNode.isExtendFlag()) {
/* 548 */         dbCursor = HiDbtUtils.dbtextcursor(packInfo.tableName, packInfo.sqlCond, "O", dbCursor, null, ctx);
/*     */       }
/*     */       else {
/* 551 */         dbCursor = HiDbtUtils.dbtsqlcursor(packInfo.sqlCond, "O", dbCursor, null, ctx);
/*     */       }
/*     */ 
/* 555 */       if (log.isDebugEnabled()) {
/* 556 */         log.debug("HiBatchProcess.doPack09");
/*     */       }
/*     */ 
/* 562 */       for (int i = 0; ; ++i) {
/* 563 */         HiETF root_rec = HiETFFactory.createETF();
/* 564 */         if (this._dataNode.isExtendFlag()) {
/* 565 */           dbCursor = HiDbtUtils.dbtextcursor(packInfo.tableName, packInfo.sqlCond, "F", dbCursor, root_rec, ctx);
/*     */         }
/*     */         else {
/* 568 */           dbCursor = HiDbtUtils.dbtsqlcursor(packInfo.sqlCond, "F", dbCursor, root_rec, ctx);
/*     */         }
/*     */ 
/* 572 */         if (dbCursor.ret == 100) {
/*     */           break;
/*     */         }
/*     */ 
/* 576 */         HiEngineUtilities.setCurFlowStep(step++);
/* 577 */         root_rec.combine(msg.getETFBody(), false);
/*     */ 
/* 579 */         if (startSeqNo != -1) {
/* 580 */           root_rec.setChildValue(this._sqnNodeName, String.valueOf(startSeqNo));
/*     */ 
/* 582 */           ++startSeqNo;
/*     */         }
/* 584 */         HiMessage msg1 = msg.cloneNoBody();
/* 585 */         msg1.setBody(root_rec);
/* 586 */         byteBuffer.clear();
/* 587 */         HiMessageHelper.setPackMessage(msg1, byteBuffer);
/* 588 */         ctx.setCurrentMsg(msg1);
/* 589 */         this._dataNode.process(ctx);
/* 590 */         ctx.setCurrentMsg(msg);
/* 591 */         byteBuffer = HiMessageHelper.getUnpackMessageBuffer(msg1);
/* 592 */         if (this._dataNode.isLineWrap()) {
/* 593 */           byteBuffer.append(SystemUtils.LINE_SEPARATOR);
/*     */         }
/* 595 */         fos.write(byteBuffer.getBytes());
/*     */       }
/*     */ 
/* 598 */       if (this._dataNode.isExtendFlag()) {
/* 599 */         HiDbtUtils.dbtextcursor(packInfo.tableName, packInfo.sqlCond, "C", dbCursor, null, ctx);
/*     */       }
/*     */       else {
/* 602 */         HiDbtUtils.dbtsqlcursor(packInfo.sqlCond, "C", dbCursor, null, ctx);
/*     */       }
/*     */ 
/* 606 */       if (this._endNode != null) {
/* 607 */         HiEngineUtilities.setCurFlowStep(step++);
/* 608 */         byteBuffer.clear();
/* 609 */         HiMessage msg1 = new HiMessage(msg);
/* 610 */         HiMessageHelper.setPackMessage(msg1, byteBuffer);
/* 611 */         ctx.setCurrentMsg(msg1);
/* 612 */         this._endNode.process(ctx);
/* 613 */         ctx.setCurrentMsg(msg);
/* 614 */         byteBuffer = HiMessageHelper.getUnpackMessageBuffer(msg1);
/* 615 */         if (this._endNode.isLineWrap()) {
/* 616 */           byteBuffer.append(SystemUtils.LINE_SEPARATOR);
/*     */         }
/* 618 */         fos.write(byteBuffer.getBytes());
/*     */       }
/*     */ 
/* 621 */       if (i == 0)
/* 622 */         ret = 1;
/*     */       else
/* 624 */         ret = 0;
/*     */     }
/*     */     finally {
/* 627 */       fos.close();
/* 628 */       ctx.setCurrentMsg(msg);
/* 629 */       if (dbCursor != null) {
/* 630 */         if (this._dataNode.isExtendFlag()) {
/* 631 */           HiDbtUtils.dbtextcursor(packInfo.tableName, packInfo.sqlCond, "C", dbCursor, null, ctx);
/*     */         }
/*     */         else {
/* 634 */           HiDbtUtils.dbtsqlcursor(packInfo.sqlCond, "C", dbCursor, null, ctx);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 639 */     return ret;
/*     */   }
/*     */ 
/*     */   public int getCommitRecords() {
/* 643 */     return this._commitRecords;
/*     */   }
/*     */ 
/*     */   public void setCommit_records(int records) {
/* 647 */     this._commitRecords = records;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 651 */     StringBuffer result = new StringBuffer();
/* 652 */     result.append("_sqnFlag:" + this._sqnFlag);
/* 653 */     result.append(";_commitRecords:" + this._commitRecords);
/* 654 */     result.append(";_sqnNodeName:" + this._sqnNodeName);
/* 655 */     result.append(";_statFlag:" + this._statFlag);
/* 656 */     result.append(";_ignore_blank_line:" + this._ignore_blank_line);
/* 657 */     result.append(";_begin_ignore_line:" + this._begin_ignore_line);
/* 658 */     result.append(";_end_ignore_line:" + this._end_ignore_line);
/* 659 */     result.append(";_debugFile:" + this._debugFile);
/* 660 */     result.append(";_errFile:" + this._errFile);
/* 661 */     result.append(";_name:" + this._name);
/* 662 */     return result.toString();
/*     */   }
/*     */ }