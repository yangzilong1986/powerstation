 package com.hisun.atc.bat;
 
 import com.hisun.atc.common.HiAtcLib;
 import com.hisun.atc.common.HiDBCursor;
 import com.hisun.atc.common.HiDbtSqlHelper;
 import com.hisun.atc.common.HiDbtUtils;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.engine.HiEngineModel;
 import com.hisun.engine.HiEngineUtilities;
 import com.hisun.engine.invoke.HiIEngineModel;
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.message.HiMessageHelper;
 import com.hisun.util.HiByteBuffer;
 import com.hisun.util.HiFileOutputStream;
 import com.hisun.util.HiStringManager;
 import java.util.List;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.SystemUtils;
 import org.apache.commons.lang.math.NumberUtils;
 
 public class HiBatchProcess extends HiEngineModel
 {
   private boolean _ignoreError = false;
 
   private boolean _sqnFlag = true;
 
   private int _commitRecords = 1000;
 
   private String _sqnNodeName = "Sqn_No";
 
   private boolean _statFlag = true;
 
   private boolean _ignore_blank_line = false;
 
   private int _begin_ignore_line = 0;
 
   private int _end_ignore_line = 0;
   private String _debugFile;
   private String _errFile;
   private HiData _dataNode;
   private HiHead _headNode;
   private HiEnd _endNode;
   private String _name;
 
   public String getNodeName()
   {
     return "Process";
   }
 
   public void setBegin_ignore_line(int begin_ignore_line) {
     this._begin_ignore_line = begin_ignore_line;
   }
 
   public void setDebug_file(String debug_file) {
     this._debugFile = debug_file;
   }
 
   public void setEnd_ignore_line(int end_ignore_line) {
     this._end_ignore_line = end_ignore_line;
   }
 
   public void setIgnore_blank_line(String ignore_blank_line) {
     if (StringUtils.equalsIgnoreCase(ignore_blank_line, "Y"))
       this._ignore_blank_line = true;
   }
 
   public void setErr_file(String err_file) {
     this._errFile = err_file;
   }
 
   public void setName(String name) {
     this._name = name;
   }
 
   public String getName() {
     return this._name;
   }
 
   public void setIgnore_error(String ignore_error) {
     if (StringUtils.equalsIgnoreCase(ignore_error, "Y"))
       this._ignoreError = true;
   }
 
   public void setSqn_flag(String sqn_flag) {
     if (StringUtils.equalsIgnoreCase(sqn_flag, "N"))
       this._sqnFlag = false;
   }
 
   public void setSqn_node_name(String sqn_node_name) {
     this._sqnNodeName = sqn_node_name;
   }
 
   public void setStat_flag(String stat_flag) throws HiException {
     if (StringUtils.equalsIgnoreCase(stat_flag, "N"))
       this._statFlag = false;
   }
 
   public void addChilds(HiIEngineModel child)
     throws HiException
   {
     if (child instanceof HiData)
       this._dataNode = ((HiData)child);
     else if (child instanceof HiHead)
       this._headNode = ((HiHead)child);
     else
       this._endNode = ((HiEnd)child);
     super.addChilds(child);
   }
 
   public void loadAfter() throws HiException
   {
     List childs = getChilds();
     boolean isExistData = false;
     for (int i = 0; i < childs.size(); ++i) {
       HiEngineModel child = (HiEngineModel)childs.get(i);
       if (child instanceof HiData) {
         isExistData = true;
         break;
       }
     }
     if (!(isExistData))
       throw new HiException("220067");
   }
 
   public int importToDB(HiMessageContext ctx, HiPackInfo packInfo)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     HiAbstractFMT sumNode = null;
 
     HiBatchFile batchFile = new HiBatchFile();
     packInfo.batchFile = batchFile;
 
     batchFile.setBeginIgnoreLine(this._begin_ignore_line);
     batchFile.setIgnoreBlankLine(this._ignore_blank_line);
     batchFile.setEndIgnoreLine(this._end_ignore_line);
 
     if (this._headNode != null) {
       batchFile.setSumFlag(1);
       batchFile.setSumRecordLength(this._headNode.getRecordLength());
       sumNode = this._headNode;
       packInfo.sumFlag = true;
     } else if (this._endNode != null) {
       batchFile.setSumFlag(2);
       batchFile.setSumRecordLength(this._endNode.getRecordLength());
       sumNode = this._endNode;
       packInfo.sumFlag = true;
     }
     batchFile.setDataRecordLength(this._dataNode.getRecordLength());
     if (this._sqnFlag) {
       String strSqnValue = msg.getETFBody().getChildValue(this._sqnNodeName);
       packInfo.seqNo = NumberUtils.toInt(strSqnValue);
       if (log.isInfoEnabled()) {
         log.info("顺序号节点:[" + packInfo.seqNo + "]");
       }
 
     }
 
     batchFile.setFile(packInfo.fileName);
     packInfo.statFlag = this._statFlag;
     batchFile.open();
     if (log.isInfoEnabled()) {
       log.info("批量文件信息:" + batchFile);
     }
 
     if ((packInfo.ornCnt != 0) && (packInfo.ornCnt != batchFile.getDataCnt())) {
       throw new HiException("220076", packInfo.fileName, String.valueOf(batchFile.getDataCnt()), String.valueOf(packInfo.ornCnt));
     }
 
     if (StringUtils.equals(packInfo.applyLogNoFlag, "1")) {
       packInfo.logNo = HiAtcLib.sqnGetLogNo(ctx, batchFile.getTotalCnt());
       if (log.isInfoEnabled()) {
         log.info("初始日志流水号:[" + packInfo.logNo + "]");
       }
     }
 
     HiByteBuffer byteBuffer = new HiByteBuffer(1024, 256);
     int ret = 2;
     HiETF sum = HiETFFactory.createETF("SUM", "");
     try {
       while (batchFile.getNextRecord(byteBuffer) != null) {
         if (batchFile.isSumRecord()) {
           ret1 = processSumRecord(batchFile, sumNode, sum, packInfo, ctx);
 
           byteBuffer.clear();
           if (ret1 == 1) {
             break;
           }
         }
         if (ret != 0) {
           ret = 0;
         }
         int ret1 = processDataRecord(batchFile, this._dataNode, packInfo, ctx);
 
         byteBuffer.clear();
         if (ret1 == 1) {
           break;
         }
         if (ret1 != 2)
         {
           if ((ret1 == 3) && (!(this._ignoreError))) {
             int i = 1;
             return i;
           }
         }
         if (batchFile.getCurrRecordNo() % this._commitRecords == 0) {
           ctx.getDataBaseUtil().commit();
         }
       }
 
     }
     catch (HiException e)
     {
     }
     finally
     {
       batchFile.close();
       ctx.setCurrentMsg(msg);
     }
 
     ctx.getDataBaseUtil().commit();
     msg.getETFBody().appendNode(sum);
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiBatchProcess.suminfo", String.valueOf(packInfo.totalAmt), String.valueOf(packInfo.totalCnt), String.valueOf(packInfo.dTotalAmt), String.valueOf(packInfo.dTotalCnt), String.valueOf(packInfo.ornAmt), String.valueOf(packInfo.ornCnt)));
     }
 
     packInfo.check();
     return ret;
   }
 
   private int processDataRecord(HiBatchFile batchFile, HiData dataNode, HiPackInfo packInfo, HiMessageContext ctx)
     throws HiException
   {
     String sqlSentence;
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     String value = null;
     int ret = 0;
 
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiBatchProcess.parseFileData", String.valueOf(batchFile.getCurrRecordNo())));
 
       log.info(batchFile.getCurrentRecord());
     }
 
     HiMessage newMsg = new HiMessage(msg);
     HiMessageHelper.setUnpackMessage(newMsg, batchFile.getCurrentRecord());
     HiETF root = newMsg.getETFBody();
     root.setChildValue("HTXN_STS", "U");
     root.setChildValue("HPR_CHK", "0");
     root.setChildValue("LST_CHK", "0");
     root.setChildValue("CHK_FLG", "0");
     root.setChildValue("IS_TXN", "Y");
     ctx.setCurrentMsg(newMsg);
     if (packInfo.logNo != -1L) {
       root.setChildValue("LOG_NO", StringUtils.leftPad(String.valueOf(packInfo.logNo), 14, '0'));
 
       packInfo.logNo += 1L;
     }
 
     if (packInfo.seqNo != -1L) {
       root.setChildValue(this._sqnNodeName, String.valueOf(packInfo.seqNo));
       packInfo.seqNo += 1L;
     }
     dataNode.process(ctx);
     ctx.setCurrentMsg(msg);
     if ((value = root.getChildValue("STOP")) != null) {
       if (log.isInfoEnabled()) {
         log.info(sm.getString("HiBatchProcess.stop00", String.valueOf(batchFile.getCurrRecordNo()), value));
       }
 
       return 1;
     }
     packInfo.dTotalCnt += 1;
     String txnAmt = null; String sign = null;
     int recAmtFlg = 0;
     if ((txnAmt = root.getChildValue("TXN_AMT")) == null) {
       recAmtFlg = 2;
       packInfo.recAmtFlg = false;
       log.info("TXN_AMT:不统计");
     }
 
     if (recAmtFlg != 2) {
       packInfo.recAmtFlg = true;
       sign = root.getChildValue("SIGN");
       long iTxnAmt = NumberUtils.toLong(txnAmt.trim());
       log.info("TXN_AMT:" + String.valueOf(txnAmt) + " :" + iTxnAmt);
 
       if (StringUtils.equals(sign, "-")) {
         packInfo.dTotalAmt -= iTxnAmt;
         root.setChildValue("TXN_AMT", StringUtils.leftPad(String.valueOf(-1L * iTxnAmt), 15, '0'));
       }
       else {
         packInfo.dTotalAmt += iTxnAmt;
       }
     }
 
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiBatchProcess.parseFileData01", String.valueOf(packInfo.dTotalAmt)));
     }
 
     if (root.getChildValue("JUMP") != null)
     {
       if (log.isInfoEnabled()) {
         log.info(sm.getString("HiBatchProcess.jump00", String.valueOf(batchFile.getCurrRecordNo() - 1)));
       }
 
       return 2;
     }
 
     if (dataNode.isExtendFlag()) {
       if (HiDbtUtils.dbtextinsrec(packInfo.tableName, root, ctx) == 1)
       {
         if (packInfo.isUpdate)
         {
           sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, packInfo.conSts, root);
           HiDbtUtils.dbtsqlupdreccon(packInfo.tableName, sqlSentence, root, ctx);
         }
         else
         {
           ret = 3;
         }
       }
     }
     else if (HiDbtUtils.dbtsqlinsrec(packInfo.tableName, root, ctx) == 1)
     {
       if (packInfo.isUpdate)
       {
         sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, packInfo.conSts, root);
         HiDbtUtils.dbtsqlupdreccon(packInfo.tableName, sqlSentence, root, ctx);
       }
       else
       {
         ret = 3;
       }
 
     }
 
     return ret;
   }
 
   private int processSumRecord(HiBatchFile batchFile, HiAbstractFMT sumNode, HiETF sum, HiPackInfo packInfo, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     String value = null;
 
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiBatchProcess.parseFileSum", String.valueOf(batchFile.getCurrRecordNo())));
     }
 
     HiMessage newMsg = msg.cloneNoBody();
     HiMessageHelper.setUnpackMessage(newMsg, batchFile.getCurrentRecord());
     newMsg.setBody(sum);
     ctx.setCurrentMsg(newMsg);
     sumNode.process(ctx);
     ctx.setCurrentMsg(msg);
     if ((value = sum.getChildValue("STOP")) != null) {
       if (log.isInfoEnabled()) {
         log.info(sm.getString("HiBatchProcess.stop00", String.valueOf(batchFile.getCurrRecordNo()), value));
       }
 
       return 1;
     }
 
     value = sum.getChildValue("ORN_CNT");
     packInfo.totalCnt = NumberUtils.toInt(value);
     value = sum.getChildValue("ORN_AMT");
     packInfo.totalAmt = NumberUtils.toLong(value);
     value = sum.getChildValue("SIGN");
     if (StringUtils.equalsIgnoreCase(value, "-")) {
       packInfo.totalAmt *= -1L;
     }
 
     if (log.isInfoEnabled()) {
       log.info(sm.getString("HiBatchProcess.parseFileSum01", String.valueOf(packInfo.totalCnt), String.valueOf(packInfo.totalAmt)));
     }
 
     return 0;
   }
 
   public int exportFromDB(HiMessageContext ctx, HiPackInfo packInfo)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     int ret = 0;
     int step = 0;
     int startSeqNo = -1;
     if (this._sqnFlag) {
       String strSqnValue = msg.getETFBody().getChildValue(this._sqnNodeName);
       startSeqNo = NumberUtils.toInt(strSqnValue);
     }
 
     if ((this._dataNode.isExtendFlag()) && 
       (StringUtils.isEmpty(packInfo.tableName))) {
       throw new HiException("220026", "TableName");
     }
 
     if (log.isDebugEnabled()) {
       log.debug(sm.getString("HiBatchProcess.doPack00", packInfo.fileName, packInfo.sqlCond, String.valueOf(startSeqNo), packInfo.tableName));
     }
 
     if (this._dataNode == null);
     HiFileOutputStream fos = new HiFileOutputStream(packInfo.fileName);
     HiDBCursor dbCursor = null;
     HiByteBuffer byteBuffer = new HiByteBuffer(1024, 256);
     try {
       if (this._headNode != null) {
         HiEngineUtilities.setCurFlowStep(step++);
         byteBuffer.clear();
         HiMessage msg1 = new HiMessage(msg);
         HiMessageHelper.setPackMessage(msg1, byteBuffer);
         ctx.setCurrentMsg(msg1);
         this._headNode.process(ctx);
         ctx.setCurrentMsg(msg);
         byteBuffer = HiMessageHelper.getUnpackMessageBuffer(msg1);
         if (this._headNode.isLineWrap()) {
           byteBuffer.append(SystemUtils.LINE_SEPARATOR);
         }
         fos.write(byteBuffer.getBytes());
       }
 
       if (this._dataNode.isExtendFlag()) {
         dbCursor = HiDbtUtils.dbtextcursor(packInfo.tableName, packInfo.sqlCond, "O", dbCursor, null, ctx);
       }
       else {
         dbCursor = HiDbtUtils.dbtsqlcursor(packInfo.sqlCond, "O", dbCursor, null, ctx);
       }
 
       if (log.isDebugEnabled()) {
         log.debug("HiBatchProcess.doPack09");
       }
 
       for (int i = 0; ; ++i) {
         HiETF root_rec = HiETFFactory.createETF();
         if (this._dataNode.isExtendFlag()) {
           dbCursor = HiDbtUtils.dbtextcursor(packInfo.tableName, packInfo.sqlCond, "F", dbCursor, root_rec, ctx);
         }
         else {
           dbCursor = HiDbtUtils.dbtsqlcursor(packInfo.sqlCond, "F", dbCursor, root_rec, ctx);
         }
 
         if (dbCursor.ret == 100) {
           break;
         }
 
         HiEngineUtilities.setCurFlowStep(step++);
         root_rec.combine(msg.getETFBody(), false);
 
         if (startSeqNo != -1) {
           root_rec.setChildValue(this._sqnNodeName, String.valueOf(startSeqNo));
 
           ++startSeqNo;
         }
         HiMessage msg1 = msg.cloneNoBody();
         msg1.setBody(root_rec);
         byteBuffer.clear();
         HiMessageHelper.setPackMessage(msg1, byteBuffer);
         ctx.setCurrentMsg(msg1);
         this._dataNode.process(ctx);
         ctx.setCurrentMsg(msg);
         byteBuffer = HiMessageHelper.getUnpackMessageBuffer(msg1);
         if (this._dataNode.isLineWrap()) {
           byteBuffer.append(SystemUtils.LINE_SEPARATOR);
         }
         fos.write(byteBuffer.getBytes());
       }
 
       if (this._dataNode.isExtendFlag()) {
         HiDbtUtils.dbtextcursor(packInfo.tableName, packInfo.sqlCond, "C", dbCursor, null, ctx);
       }
       else {
         HiDbtUtils.dbtsqlcursor(packInfo.sqlCond, "C", dbCursor, null, ctx);
       }
 
       if (this._endNode != null) {
         HiEngineUtilities.setCurFlowStep(step++);
         byteBuffer.clear();
         HiMessage msg1 = new HiMessage(msg);
         HiMessageHelper.setPackMessage(msg1, byteBuffer);
         ctx.setCurrentMsg(msg1);
         this._endNode.process(ctx);
         ctx.setCurrentMsg(msg);
         byteBuffer = HiMessageHelper.getUnpackMessageBuffer(msg1);
         if (this._endNode.isLineWrap()) {
           byteBuffer.append(SystemUtils.LINE_SEPARATOR);
         }
         fos.write(byteBuffer.getBytes());
       }
 
       if (i == 0)
         ret = 1;
       else
         ret = 0;
     }
     finally {
       fos.close();
       ctx.setCurrentMsg(msg);
       if (dbCursor != null) {
         if (this._dataNode.isExtendFlag()) {
           HiDbtUtils.dbtextcursor(packInfo.tableName, packInfo.sqlCond, "C", dbCursor, null, ctx);
         }
         else {
           HiDbtUtils.dbtsqlcursor(packInfo.sqlCond, "C", dbCursor, null, ctx);
         }
       }
     }
 
     return ret;
   }
 
   public int getCommitRecords() {
     return this._commitRecords;
   }
 
   public void setCommit_records(int records) {
     this._commitRecords = records;
   }
 
   public String toString() {
     StringBuffer result = new StringBuffer();
     result.append("_sqnFlag:" + this._sqnFlag);
     result.append(";_commitRecords:" + this._commitRecords);
     result.append(";_sqnNodeName:" + this._sqnNodeName);
     result.append(";_statFlag:" + this._statFlag);
     result.append(";_ignore_blank_line:" + this._ignore_blank_line);
     result.append(";_begin_ignore_line:" + this._begin_ignore_line);
     result.append(";_end_ignore_line:" + this._end_ignore_line);
     result.append(";_debugFile:" + this._debugFile);
     result.append(";_errFile:" + this._errFile);
     result.append(";_name:" + this._name);
     return result.toString();
   }
 }