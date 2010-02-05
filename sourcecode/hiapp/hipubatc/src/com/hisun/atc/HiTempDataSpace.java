 package com.hisun.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.database.HiResultSet;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.util.HiStringManager;
 import java.util.concurrent.ConcurrentHashMap;
 import org.apache.commons.lang.StringUtils;
 
 public class HiTempDataSpace
 {
   private static HiStringManager sm = HiStringManager.getManager();
 
   private static ConcurrentHashMap memoryTD = new ConcurrentHashMap();
 
   public int DeleteTDS(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     String recKey = HiArgUtils.getStringNotNull(argsMap, "RecKey");
     String txnCode = argsMap.get("TxnCod");
     if (StringUtils.isEmpty(txnCode)) {
       txnCode = HiArgUtils.getStringNotNull(etf, "TXN_CD");
     }
     String type = argsMap.get("type");
     String brNo = etf.getChildValue("BR_NO");
     if (isMemoryDS(type)) {
       for (int k = 1; ; ++k) {
         String tmpReckey = getMemRecKey(brNo, txnCode, recKey, k);
         if (!(memoryTD.containsKey(tmpReckey))) {
           if (k != 1) break;
           return 2;
         }
 
         memoryTD.remove(tmpReckey);
       }
     } else {
       String sqlCmd = "DELETE FROM PUBTDSTBL WHERE BR_NO = '%s' AND TXN_CD = '%s' AND REC_KEY = '%s'";
       int ret = ctx.getDataBaseUtil().execUpdate(sqlCmd, brNo, txnCode, recKey);
 
       if (ret == 0)
         return 2;
     }
     return 0;
   }
 
   public int UpdateTDS(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     int idx = 0;
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     String recKey = HiArgUtils.getStringNotNull(argsMap, "RecKey");
 
     String type = null;
     if (argsMap.contains("type")) {
       type = argsMap.get("type");
     }
 
     ++idx;
     String value = null;
     String[] fields = StringUtils.split(recKey, '|');
     for (int i = 0; i < fields.length; ++i) {
       value = (String)ctx.getSpecExpre(etf, fields[i]);
       if (fields[i].startsWith("$"))
         value = (String)ctx.getSpecExpre(etf, fields[i]);
       else {
         value = fields[i];
       }
       if (i == 0)
         recKey = value;
       else {
         recKey = recKey + value;
       }
 
     }
 
     String txnCode = argsMap.get("TxnCod");
     if (StringUtils.isEmpty(txnCode))
       txnCode = HiArgUtils.getStringNotNull(etf, "TXN_CD");
     else {
       ++idx;
     }
 
     String brNo = etf.getChildValue("BR_NO");
 
     HiETF root = null;
     for (int k = 1; idx < argsMap.size(); ++k) {
       fields = StringUtils.split(argsMap.getValue(idx), "|");
       root = HiETFFactory.createETF("REC_" + idx, "");
       for (int j = 0; j < fields.length; ++j) {
         value = etf.getChildValue(fields[j]);
         root.setChildValue(fields[j], value);
       }
       String recIdx = StringUtils.leftPad(String.valueOf(idx), 2, '0');
       String recValue = root.toString();
       if (isMemoryDS(type)) {
         String tmpReckey = getMemRecKey(brNo, txnCode, recKey, k);
         if (!(memoryTD.containsKey(tmpReckey))) {
           return 2;
         }
         memoryTD.put(tmpReckey, recValue);
       } else {
         String sqlCmd = "UPDATE PUBTDSTBL SET REC='%s' WHERE BR_NO = '%s' AND TXN_CD = '%s' AND REC_KEY = '%s' AND REC_IDX = '%s'";
         int ret = ctx.getDataBaseUtil().execUpdate(sqlCmd, recValue, brNo, txnCode, recKey, recIdx);
 
         if (ret == 0)
           return 2;
       }
       ++idx;
     }
 
     return 0;
   }
 
   public int SaveToTDS(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     int idx = 0;
     String recKey = HiArgUtils.getStringNotNull(argsMap, "RecKey");
     ++idx;
     String type = null;
     if (argsMap.contains("type")) {
       type = argsMap.get("type");
       ++idx;
     }
     String value = null;
     String[] fields = StringUtils.split(recKey, '|');
     for (int i = 0; i < fields.length; ++i) {
       if (fields[i].startsWith("$"))
         value = (String)ctx.getSpecExpre(etf, fields[i]);
       else {
         value = fields[i];
       }
       if (i == 0)
         recKey = value;
       else {
         recKey = recKey + value;
       }
     }
 
     String txnCode = argsMap.get("TxnCod");
     if (StringUtils.isEmpty(txnCode))
       txnCode = HiArgUtils.getStringNotNull(etf, "TXN_CD");
     else {
       ++idx;
     }
 
     String brNo = etf.getChildValue("BR_NO");
 
     HiETF root = null;
     for (int k = 1; idx < argsMap.size(); ++idx) {
       fields = StringUtils.split(argsMap.getValue(idx), "|");
       root = HiETFFactory.createETF("REC_" + idx, "");
       for (int j = 0; j < fields.length; ++j) {
         value = etf.getChildValue(fields[j]);
         root.setChildValue(fields[j], value);
       }
       String recIdx = StringUtils.leftPad(String.valueOf(idx), 2, '0');
       String recValue = root.toString();
       if (isMemoryDS(type)) {
         String tmpReckey = getMemRecKey(brNo, txnCode, recKey, k);
         if (memoryTD.containsKey(tmpReckey)) {
           return 1;
         }
         memoryTD.put(tmpReckey, recValue);
       } else {
         String sqlCmd = "INSERT INTO PUBTDSTBL VALUES('%s', '%s', '%s', '%s', '%s')";
         int ret = ctx.getDataBaseUtil().execUpdate(sqlCmd, brNo, txnCode, recKey, recIdx, recValue);
 
         if (ret == 0)
           return 1;
       }
       ++k;
     }
 
     return 0;
   }
 
   public int LoadFromTDSToGroup(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     String recKey = HiArgUtils.getStringNotNull(argsMap, "RecKey");
     String value = null;
     String[] fields = StringUtils.split(recKey, '|');
     for (int i = 0; i < fields.length; ++i) {
       if (fields[i].startsWith("$"))
         value = (String)ctx.getSpecExpre(etf, fields[i]);
       else {
         value = fields[i];
       }
       if (i == 0)
         recKey = value;
       else {
         recKey = recKey + value;
       }
     }
 
     String txnCode = argsMap.get("TxnCod");
     if (StringUtils.isEmpty(txnCode)) {
       txnCode = HiArgUtils.getStringNotNull(etf, "TXN_CD");
     }
 
     String brNo = etf.getChildValue("BR_NO");
     String type = argsMap.get("type");
     HiETF root = null;
     if (isMemoryDS(type)) {
       for (int idx = 1; ; ++idx) {
         String tmpReckey = getMemRecKey(brNo, txnCode, recKey, idx);
         if ((!(memoryTD.containsKey(tmpReckey))) && 
           (idx == 1)) {
           return 2;
         }
 
         root = HiETFFactory.createETF((String)memoryTD.get(tmpReckey));
         etf.combine(root, false);
       }
     }
 
     String sqlCmd = "SELECT Rec  FROM pubtdstbl WHERE BR_NO='%s' AND TXN_CD='%s' AND REC_KEY='%s'";
     HiResultSet resultSet = ctx.getDataBaseUtil().execQuerySQL(sqlCmd, brNo, txnCode, recKey);
 
     if (resultSet.size() == 0) {
       return 2;
     }
     for (int i = 0; i < resultSet.size(); ++i) {
       root = HiETFFactory.createETF(resultSet.getValue(i, 0));
       etf.combine(root, false);
     }
 
     return 0;
   }
 
   public int LoadFromTDS(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     int idx = 0;
     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
     String recKey = HiArgUtils.getStringNotNull(argsMap, "RecKey");
     ++idx;
     String value = null;
     String[] fields = StringUtils.split(recKey, '|');
     for (int i = 0; i < fields.length; ++i) {
       if (fields[i].startsWith("$"))
         value = (String)ctx.getSpecExpre(etf, fields[i]);
       else {
         value = fields[i];
       }
       if (i == 0)
         recKey = value;
       else {
         recKey = recKey + value;
       }
     }
 
     String txnCode = argsMap.get("TxnCod");
     if (StringUtils.isEmpty(txnCode))
       txnCode = HiArgUtils.getStringNotNull(etf, "TXN_CD");
     else {
       ++idx;
     }
     String brNo = etf.getChildValue("BR_NO");
 
     HiETF root = null;
     String type = null;
     if (argsMap.contains("type")) {
       type = argsMap.get("type");
       ++idx;
     }
     if (isMemoryDS(type)) {
       if (log.isDebugEnabled()) {
         log.debug("Memory TDS:" + memoryTD);
       }
       for (int k = 1; ; ++k) {
         String tmpReckey = getMemRecKey(brNo, txnCode, recKey, k);
         if (!(memoryTD.containsKey(tmpReckey))) {
           if (k != 1) break;
           return 2;
         }
 
         root = HiETFFactory.createETF((String)memoryTD.get(tmpReckey));
         etf.combine(root, false);
       }
     } else {
       String sqlCmd = "SELECT Rec  FROM pubtdstbl WHERE BR_NO='%s' AND TXN_CD='%s' AND REC_KEY='%s'";
       HiResultSet resultSet = ctx.getDataBaseUtil().execQuerySQL(sqlCmd, brNo, txnCode, recKey);
 
       if (resultSet.size() == 0) {
         return 2;
       }
 
       for (int i = 0; i < resultSet.size(); ++i) {
         root = HiETFFactory.createETF(resultSet.getValue(i, 0));
         etf.combine(root, true);
       }
     }
     return 0;
   }
 
   private String getMemRecKey(String brNo, String txnCd, String recKey, int recIdx)
   {
     if (brNo == null) {
       return "999999_" + txnCd + "_" + recKey + "_" + recIdx;
     }
     return brNo + "_" + txnCd + "_" + recKey + "_" + recIdx;
   }
 
   private boolean isMemoryDS(String type)
   {
     return "MEM".equals(type);
   }
 }