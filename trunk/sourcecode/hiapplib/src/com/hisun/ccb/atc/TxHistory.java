 package com.hisun.ccb.atc;
 
 import com.hisun.atc.common.HiDbtSqlHelper;
 import com.hisun.atc.common.HiDbtUtils;
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 
 public class TxHistory
 {
   public static final String OLD_AREA = "OLD_AREA";
   public static final String TBL_NM = "TBL_NM";
   public static final String NEW_AREA = "NEW_AREA";
   public static final String CLS_NM = "CLS_NM";
   public static final String CLS_KEY1 = "CLS_KEY1";
   public static final String CLS_KEY2 = "CLS_KEY2";
   public static final String CLS_KEY3 = "CLS_KEY3";
 
   public int GenInfDetail(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(mess);
     if ((argsMap == null) || (argsMap.size() == 0)) {
       if (log.isErrorEnabled()) {
         log.error("GenInfDetail:参数为空");
       }
       throw new HiException("220026", "GenInfDetail:参数为空");
     }
 
     String oldArea = argsMap.get("OLD_AREA");
     String tableName = argsMap.get("TBL_NM");
     String newArea = argsMap.get("NEW_AREA");
     String clsName = argsMap.get("CLS_NM");
     String clsKey_1 = argsMap.get("CLS_KEY1");
     String clsKey_2 = argsMap.get("CLS_KEY2");
     String clsKey_3 = argsMap.get("CLS_KEY3");
 
     if ((tableName == null) || (tableName.equals(""))) {
       if (log.isErrorEnabled()) {
         log.error("GenInfDetail:table name is null.");
       }
       throw new HiException("220026", "GenInfDetail: table name is null.");
     }
 
     boolean oldAreaExist = (oldArea != null) && (!(oldArea.equals("")));
 
     boolean newAreaExist = (newArea != null) && (!(newArea.equals("")));
 
     if ((((newAreaExist) || (oldAreaExist)) ? 1 : 0) == 0) {
       throw new HiException("220026", "GenInfDetail: OLD_AREA  and NEW_AREA both  null.");
     }
 
     HiETF oldETF = null;
     HiETF newETF = null;
     if (oldAreaExist) {
       oldETF = (HiETF)ctx.getBaseSource(oldArea);
       if (oldETF == null) {
         throw new HiException("213122", "GenInfDetail: OLD_AREA  ETF  data is  null.");
       }
 
     }
 
     if (newAreaExist) {
       newETF = (HiETF)ctx.getBaseSource(newArea);
       if (newETF == null) {
         throw new HiException("213122", "GenInfDetail: NEW AREA  ETF  data is  null.");
       }
 
     }
 
     String opt_type = "";
     if ((newAreaExist) && (oldAreaExist))
       opt_type = "M";
     else if (newAreaExist)
       opt_type = "A";
     else {
       opt_type = "D";
     }
 
     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
     HashMap colsMap = dbUtil.getTableMetaData(tableName, dbUtil.getConnection());
 
     if ((colsMap == null) || (colsMap.size() == 0)) {
       throw new HiException("220040", "GenInfDetail:  no table meta data info :" + tableName);
     }
 
     if (log.isDebugEnabled()) {
       log.debug("map value object is :" + colsMap);
     }
 
     String[] colNames = (String[])(String[])colsMap.keySet().toArray(new String[0]);
 
     StringBuffer oldValue_list = new StringBuffer();
     StringBuffer newValue_list = new StringBuffer();
     StringBuffer fldValue_list = new StringBuffer();
 
     for (int i = 0; i < colNames.length; ++i) {
       fldValue_list.append(colNames[i] + "|");
       if (oldAreaExist) {
         oldValue_list.append(oldETF.getChildValue(colNames[i]));
         oldValue_list.append('|');
       }
       if (newAreaExist) {
         newValue_list.append(newETF.getChildValue(colNames[i]));
         newValue_list.append('|');
       }
     }
     if (log.isDebugEnabled()) {
       log.debug("GenInfDetail:  fldValue_list  is:" + fldValue_list);
       log.debug("GenInfDetail:  oldValue_list  is:" + oldValue_list);
       log.debug("GenInfDetail:  newValue_list  is:" + newValue_list);
     }
 
     HiETF pGWA = (HiETF)ctx.getBaseSource("GWA");
     if (pGWA == null) {
       throw new HiException("213122", "GenInfDetail: 取GWA区数据失败.");
     }
 
     HiETF pHisNode = pGWA.getChildNode("HIS");
     String SEQ = pHisNode.getChildValue("SEQ_NO");
     int seq = 0;
     try {
       seq = Integer.parseInt(SEQ);
     } catch (NumberFormatException nfe) {
     }
     if (log.isDebugEnabled()) {
       log.debug("GenInfDetail: SEQ_NO  :" + seq);
     }
     pHisNode.setChildValue("FLD_LST", fldValue_list.toString());
     pHisNode.setChildValue("OLD_VAL", oldValue_list.toString());
     pHisNode.setChildValue("NEW_VAL", newValue_list.toString());
     pHisNode.setChildValue("CLS_KEY1", clsKey_1);
     pHisNode.setChildValue("CLS_KEY2", clsKey_2);
     pHisNode.setChildValue("CLS_KEY3", clsKey_3);
     pHisNode.setChildValue("OPT_TYP", opt_type);
     pHisNode.setChildValue("CLS_NM", clsName);
     pHisNode.setChildValue("SEQ_NO", "" + (++seq));
 
     return 0;
   }
 
   public int GenInfDetailBySQL(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     if ((argsMap == null) || (argsMap.size() == 0)) {
       throw new HiException("220026", "参数为空");
     }
 
     HiETF etfBody = (HiETF)msg.getBody();
     etfBody.setChildValue("CMP_AP_MMO", "CPM");
     etfBody.setChildValue("MSG_CD", "0000");
 
     String sql_cmd = argsMap.get("SQL_CMD");
     String newArea = argsMap.get("NEW_AREA");
     String clsName = argsMap.get("CLS_NM");
     String clsKey_1 = argsMap.get("CLS_KEY1");
     String clsKey_2 = argsMap.get("CLS_KEY2");
     String clsKey_3 = argsMap.get("CLS_KEY3");
 
     if ((sql_cmd == null) || (sql_cmd.equals(""))) {
       throw new HiException("213122", "GenInfDetailBySQL: 缺少输入参数[SQL_CMD]!");
     }
 
     boolean newAreaExist = (newArea != null) && (!(newArea.equals("")));
 
     HiETF newETF = null;
 
     if (newAreaExist) {
       newETF = (HiETF)ctx.getBaseSource(newArea);
       if (newETF == null) {
         throw new HiException("213122", "GenInfDetailBySQL: NEW AREA  ETF  data is  null.");
       }
 
     }
 
     String opt_type = "";
     if (newAreaExist)
       opt_type = "M";
     else {
       opt_type = "D";
     }
 
     String strSQL = HiDbtSqlHelper.getDynSentence(ctx, sql_cmd);
     List queryRs = ctx.getDataBaseUtil().execQuery(strSQL);
 
     if ((queryRs != null) && (queryRs.size() == 0)) {
       throw new HiException("220040", "GenInfDetailBySQL: no record .");
     }
 
     Map queryRec = (HashMap)queryRs.get(0);
     Map.Entry recEntry = null;
 
     StringBuffer oldValue_list = new StringBuffer();
     StringBuffer newValue_list = new StringBuffer();
     StringBuffer fldValue_list = new StringBuffer();
     Iterator recIt = queryRec.entrySet().iterator();
 
     while (recIt.hasNext()) {
       recEntry = (Map.Entry)recIt.next();
 
       fldValue_list.append(((String)recEntry.getKey()) + "|");
       oldValue_list.append(((String)recEntry.getValue()) + "|");
       if (newAreaExist);
       newValue_list.append(newETF.getChildValue((String)recEntry.getKey()) + "|");
     }
 
     HiETF pGWA = (HiETF)ctx.getBaseSource("GWA");
     if (pGWA == null) {
       throw new HiException("213122", "GenInfDetailBySQL: 取GWA区数据失败.");
     }
 
     HiETF pHisNode = pGWA.getChildNode("HIS");
     String SEQ = pHisNode.getChildValue("SEQ_NO");
     int seq = 0;
     try {
       seq = Integer.parseInt(SEQ);
     } catch (NumberFormatException nfe) {
     }
     if (log.isDebugEnabled()) {
       log.debug("GenInfDetailBySQL: SEQ_NO  :" + seq);
     }
     pHisNode.setChildValue("FLD_LST", fldValue_list.toString());
     pHisNode.setChildValue("OLD_VAL", oldValue_list.toString());
     pHisNode.setChildValue("NEW_VAL", newValue_list.toString());
     pHisNode.setChildValue("CLS_KEY1", clsKey_1);
     pHisNode.setChildValue("CLS_KEY2", clsKey_2);
     pHisNode.setChildValue("CLS_KEY3", clsKey_3);
     pHisNode.setChildValue("OPT_TYP", opt_type);
     pHisNode.setChildValue("CLS_NM", clsName);
     pHisNode.setChildValue("SEQ_NO", "" + (++seq));
 
     return 0;
   }
 
   public int RecordInfDetail(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
 
     HiETF etfBody = (HiETF)msg.getBody();
     etfBody.setChildValue("CMP_AP_MMO", "CPM");
     etfBody.setChildValue("MSG_CD", "0000");
 
     HiETF pGWA = (HiETF)ctx.getBaseSource("GWA");
 
     if (pGWA == null) {
       throw new HiException("213122", "RecordInfDetail: 取GWA区数据失败.");
     }
 
     HiETF pHisNode = pGWA.getChildNode("HIS");
 
     int rec = HiDbtUtils.dbtsqlinsrec("PUBTMHDL", pHisNode, ctx);
     if (rec != 0) {
       throw new HiException("220042", "RecordInfDetail: 插入记录失败!");
     }
 
     return 0;
   }
 
   public int RecordInfMain(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
 
     HiETF etfBody = (HiETF)msg.getBody();
     etfBody.setChildValue("CMP_AP_MMO", "CPM");
     etfBody.setChildValue("MSG_CD", "0000");
 
     HiETF pGWA = (HiETF)ctx.getBaseSource("GWA");
 
     if (pGWA == null) {
       throw new HiException("213122", "RecordInfMain: 取GWA区数据失败.");
     }
 
     HiETF pHisNode = pGWA.getChildNode("HIS");
     String sup_id1 = pGWA.getChildValue("SUP_ID1");
     pHisNode.setChildValue("ATH_TLR", sup_id1);
     String tia_type = pGWA.getChildValue("TIA_TYP");
 
     int rec = HiDbtUtils.dbtsqlinsrec("PUBTMHIS", pHisNode, ctx);
     if (rec != 0) {
       throw new HiException("220042", "RecordInfMain: 插入记录失败!");
     }
 
     return 0;
   }
 }