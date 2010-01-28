 package com.hisun.ccb.atc;
 
 import com.hisun.database.HiDataBaseUtil;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiETFFactory;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import java.util.Calendar;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import org.apache.commons.lang.StringUtils;
 import org.apache.commons.lang.time.DateFormatUtils;
 
 public class SwitchATC
 {
   public int countGroup(HiETF etfRoot, String groupName)
   {
     int count = 0;
 
     for (; ; ++count)
     {
       String tmpName = groupName + "_" + Integer.toString(count + 1);
       if (etfRoot.getChildNode(tmpName) == null) {
         break;
       }
     }
 
     return count;
   }
 
   public int copyETFNode(HiETF sETF, HiETF dETF, String sName, String dName)
   {
     String data = sETF.getChildValue(sName);
     if (data != null)
     {
       dETF.setChildValue(dName, data);
     }
     return 0;
   }
 
   public int copyETFNode(HiETF sETF, HiETF dETF, String name)
   {
     String data = sETF.getChildValue(name);
     if (data != null)
     {
       dETF.setChildValue(name, data);
     }
     return 0;
   }
 
   public int getRecGrpCount(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     HiETF etfRoot = (HiETF)mess.getBody();
 
     String groupName = argsMap.get("GrpNam");
     if (StringUtils.isEmpty(groupName)) {
       throw new HiException("212008");
     }
     int count = countGroup(etfRoot, groupName);
     etfRoot.setChildValue("RecCnt", Integer.toString(count));
 
     return 0;
   }
 
   public int createCWA(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     HiETF etfRoot = (HiETF)mess.getBody();
     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
 
     String sql = "select * from IBSRUNSTS";
 
     List list = dbUtil.execQuery(sql);
     if ((list == null) || (list.size() == 0))
     {
       dbUtil.close();
       return -1;
     }
     dbUtil.close();
 
     Map mapCWA = (Map)list.iterator().next();
 
     ctx.setBaseSource("CWA", mapCWA);
 
     return 0;
   }
 
   public int createGWA(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     HiETF etfRoot = (HiETF)mess.getBody();
     Map mapCWA = (Map)ctx.getBaseSource("CWA");
 
     HiETF etfGWA = HiETFFactory.createETF();
 
     ctx.setBaseSource("GWA", etfGWA);
 
     etfRoot.setChildValue("MSG_CD", "0000");
     etfGWA.setChildValue("MSG_CD", "0000");
     etfRoot.setChildValue("MSG_TYP", "N");
     etfGWA.setChildValue("MSG_TYP", "N");
 
     copyETFNode(etfRoot, etfGWA, "TIA_TYP");
     copyETFNode(etfRoot, etfGWA, "CNL_NO");
     copyETFNode(etfRoot, etfGWA, "TRM");
     copyETFNode(etfRoot, etfGWA, "TLR");
     copyETFNode(etfRoot, etfGWA, "SUP_ID1");
     copyETFNode(etfRoot, etfGWA, "SUP_ID2");
     copyETFNode(etfRoot, etfGWA, "JRN_NO_OLD");
     copyETFNode(etfRoot, etfGWA, "ATH_LVL", "OATH_LVL");
     copyETFNode(etfRoot, etfGWA, "ATH_RSN_LST", "OATH_RSN_LST");
     copyETFNode(etfRoot, etfGWA, "TLR", "INP_TLR");
     copyETFNode(etfRoot, etfGWA, "AP_CD");
     copyETFNode(etfRoot, etfGWA, "TX_CD");
     copyETFNode(etfRoot, etfGWA, "FE_CD");
     String TXN_CD = etfRoot.getChildValue("AP_CD") + etfRoot.getChildValue("TX_CD");
     etfGWA.setChildValue("TXN_CD", TXN_CD);
 
     String AC_DT = (String)mapCWA.get("AC_DATE_MD");
     etfRoot.setChildValue("ACC_DT", AC_DT);
     etfGWA.setChildValue("ACC_DT", AC_DT);
 
     String JRN_IN_USE = (String)mapCWA.get("JRN_IN_USE");
     etfGWA.setChildValue("JRN_IN_USE", JRN_IN_USE);
     etfGWA.setChildValue("CUR_JRN_NO", JRN_IN_USE);
 
     String MST_IN_USE = (String)mapCWA.get("MST_IN_USE");
     etfGWA.setChildValue("MST_IN_USE", MST_IN_USE);
     etfGWA.setChildValue("MST_JRN_NO", MST_IN_USE);
 
     Calendar calendar = Calendar.getInstance();
     String date = DateFormatUtils.format(calendar.getTime(), "yyyyMMDD");
     String time = DateFormatUtils.format(calendar.getTime(), "HHmmss");
     etfGWA.setChildValue("TX_DT", date);
     etfGWA.setChildValue("TX_TM", time);
     etfRoot.setChildValue("TX_DT", date);
     etfRoot.setChildValue("TX_TM", time);
     etfGWA.setChildValue("ACT_ACC_DT", " ");
     etfGWA.setChildValue("VCH_NO", " ");
     etfGWA.setChildValue("CMP_NM", " ");
     etfGWA.setChildValue("FILLER", " ");
     etfGWA.setChildValue("ACT_ACC_DT", " ");
     etfGWA.setChildValue("WFF_FLG", "N");
     etfGWA.setChildValue("CAN_FLG", "N");
     etfGWA.setChildValue("REEN_FLG", "N");
 
     etfGWA.addNode("TLR_AREA");
 
     return 0;
   }
 
   public int combineETF(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     HiETF etfRoot = (HiETF)mess.getBody();
     HiETF pSrcRoot = etfRoot;
     Logger log = HiLog.getLogger(mess);
 
     String sourName = argsMap.get("SourETF");
     String destName = argsMap.get("DestETF");
     String SourRootName = argsMap.get("SourRootName");
     String DestRootName = argsMap.get("DestRootName");
 
     if (log.isDebugEnabled())
     {
       log.debug("[CombinETF] Input: SourETF=" + sourName + ",DestETF=" + destName + ",SourRootName=" + SourRootName + ",DestRootName=" + DestRootName);
     }
 
     if (StringUtils.isEmpty(destName))
     {
       log.error("Get Argrument [DestETF] failure!");
       throw new HiException("220026", "CombineETF : 获取参数DestETF失败");
     }
 
     HiETF pDstRoot = (HiETF)ctx.getBaseSource(destName);
     if (pDstRoot == null)
     {
       log.error("Get Datasource [" + destName + "] failure!");
       throw new HiException("220320", destName);
     }
 
     if (StringUtils.isNotEmpty(DestRootName))
     {
       pDstRoot = pDstRoot.getChildNode(DestRootName);
       if (pDstRoot == null)
       {
         log.error("Get node [" + DestRootName + "] failure!");
         throw new HiException("220065", DestRootName);
       }
 
     }
 
     if (StringUtils.isNotEmpty(sourName))
     {
       pSrcRoot = (HiETF)ctx.getBaseSource(sourName);
       if (pSrcRoot == null)
       {
         log.error("Get Datasource [" + sourName + "] failure!");
         throw new HiException("220320", sourName);
       }
 
     }
 
     if (StringUtils.isNotEmpty(SourRootName))
     {
       pSrcRoot = pSrcRoot.getChildNode(SourRootName);
       if (pSrcRoot == null)
       {
         log.error("Get node [" + SourRootName + "] failure!");
         throw new HiException("220065", SourRootName);
       }
 
     }
 
     String replaceFlag = argsMap.get("ReplaceFlag");
     if (StringUtils.equals(replaceFlag, "Y"))
     {
       pDstRoot.combine(pSrcRoot, true);
     }
     else
     {
       pDstRoot.combine(pSrcRoot, false);
     }
     return 0;
   }
 
   public int copyGroup(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     HiETF etfRoot = (HiETF)mess.getBody();
     Logger log = HiLog.getLogger(mess);
     HiETF currSourGroupNode = null;
     HiETF currDestGroupNode = null;
 
     String strSourGroupName = argsMap.get("SourGroupName");
     String strDestGroupName = argsMap.get("DestGroupName");
     String strSourBeginSeq = argsMap.get("SourBeginSeq");
     String strDestBeginSeq = argsMap.get("DestBeginSeq");
     String strCount = argsMap.get("Count");
     String strRecordNumName = argsMap.get("RecordNumName");
 
     if ((StringUtils.isEmpty(strSourGroupName)) || (StringUtils.isEmpty(strDestGroupName)))
     {
       throw new HiException("220049");
     }
 
     int sourBeginSeq = 1;
     if (StringUtils.isNotEmpty(strSourBeginSeq)) {
       sourBeginSeq = Integer.parseInt(strSourBeginSeq);
     }
     int destBeginSeq = 1;
     if (StringUtils.isNotEmpty(strDestBeginSeq)) {
       destBeginSeq = Integer.parseInt(strDestBeginSeq);
     }
     int sum = 0;
     int count = 0;
     if (StringUtils.isNotEmpty(strCount)) {
       count = Integer.parseInt(strCount);
     }
 
     while ((count <= 0) || (sum < count))
     {
       String currSourGroupName = strSourGroupName + "_" + Integer.toString(sourBeginSeq + sum);
       String currDestGroupName = strDestGroupName + "_" + Integer.toString(destBeginSeq + sum);
       currSourGroupNode = etfRoot.getChildNode(currSourGroupName);
       if (currSourGroupNode == null) {
         break;
       }
 
       currDestGroupNode = currSourGroupNode.cloneNode();
       currDestGroupNode.setName(currDestGroupName);
 
       etfRoot.removeChildNode(currDestGroupName);
       etfRoot.appendNode(currDestGroupNode);
 
       ++sum;
     }
 
     if (StringUtils.isNotEmpty(strRecordNumName))
     {
       etfRoot.addNode(strRecordNumName, Integer.toString(sum));
     }
     return 0;
   }
 
   public int DEBUG(HiATLParam argsMap, HiMessageContext ctx)
     throws HiException
   {
     HiMessage mess = ctx.getCurrentMsg();
     HiETF etfRoot = (HiETF)mess.getBody();
     Logger log = HiLog.getLogger(mess);
 
     log.error("======= Current ETF:\n[" + etfRoot.toString() + "]\n");
 
     return 0;
   }
 }