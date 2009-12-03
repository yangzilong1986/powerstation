/*     */ package com.hisun.ccb.atc;
/*     */ 
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiETFFactory;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.util.Calendar;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.apache.commons.lang.time.DateFormatUtils;
/*     */ 
/*     */ public class SwitchATC
/*     */ {
/*     */   public int countGroup(HiETF etfRoot, String groupName)
/*     */   {
/*  38 */     int count = 0;
/*     */ 
/*  40 */     for (; ; ++count)
/*     */     {
/*  42 */       String tmpName = groupName + "_" + Integer.toString(count + 1);
/*  43 */       if (etfRoot.getChildNode(tmpName) == null) {
/*     */         break;
/*     */       }
/*     */     }
/*     */ 
/*  48 */     return count;
/*     */   }
/*     */ 
/*     */   public int copyETFNode(HiETF sETF, HiETF dETF, String sName, String dName)
/*     */   {
/*  56 */     String data = sETF.getChildValue(sName);
/*  57 */     if (data != null)
/*     */     {
/*  59 */       dETF.setChildValue(dName, data);
/*     */     }
/*  61 */     return 0;
/*     */   }
/*     */ 
/*     */   public int copyETFNode(HiETF sETF, HiETF dETF, String name)
/*     */   {
/*  66 */     String data = sETF.getChildValue(name);
/*  67 */     if (data != null)
/*     */     {
/*  69 */       dETF.setChildValue(name, data);
/*     */     }
/*  71 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getRecGrpCount(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  85 */     HiMessage mess = ctx.getCurrentMsg();
/*  86 */     HiETF etfRoot = (HiETF)mess.getBody();
/*     */ 
/*  89 */     String groupName = argsMap.get("GrpNam");
/*  90 */     if (StringUtils.isEmpty(groupName)) {
/*  91 */       throw new HiException("212008");
/*     */     }
/*  93 */     int count = countGroup(etfRoot, groupName);
/*  94 */     etfRoot.setChildValue("RecCnt", Integer.toString(count));
/*     */ 
/*  96 */     return 0;
/*     */   }
/*     */ 
/*     */   public int createCWA(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 110 */     HiMessage mess = ctx.getCurrentMsg();
/* 111 */     HiETF etfRoot = (HiETF)mess.getBody();
/* 112 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/*     */ 
/* 114 */     String sql = "select * from IBSRUNSTS";
/*     */ 
/* 116 */     List list = dbUtil.execQuery(sql);
/* 117 */     if ((list == null) || (list.size() == 0))
/*     */     {
/* 119 */       dbUtil.close();
/* 120 */       return -1;
/*     */     }
/* 122 */     dbUtil.close();
/*     */ 
/* 124 */     Map mapCWA = (Map)list.iterator().next();
/*     */ 
/* 126 */     ctx.setBaseSource("CWA", mapCWA);
/*     */ 
/* 128 */     return 0;
/*     */   }
/*     */ 
/*     */   public int createGWA(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 142 */     HiMessage mess = ctx.getCurrentMsg();
/* 143 */     HiETF etfRoot = (HiETF)mess.getBody();
/* 144 */     Map mapCWA = (Map)ctx.getBaseSource("CWA");
/*     */ 
/* 146 */     HiETF etfGWA = HiETFFactory.createETF();
/*     */ 
/* 148 */     ctx.setBaseSource("GWA", etfGWA);
/*     */ 
/* 150 */     etfRoot.setChildValue("MSG_CD", "0000");
/* 151 */     etfGWA.setChildValue("MSG_CD", "0000");
/* 152 */     etfRoot.setChildValue("MSG_TYP", "N");
/* 153 */     etfGWA.setChildValue("MSG_TYP", "N");
/*     */ 
/* 155 */     copyETFNode(etfRoot, etfGWA, "TIA_TYP");
/* 156 */     copyETFNode(etfRoot, etfGWA, "CNL_NO");
/* 157 */     copyETFNode(etfRoot, etfGWA, "TRM");
/* 158 */     copyETFNode(etfRoot, etfGWA, "TLR");
/* 159 */     copyETFNode(etfRoot, etfGWA, "SUP_ID1");
/* 160 */     copyETFNode(etfRoot, etfGWA, "SUP_ID2");
/* 161 */     copyETFNode(etfRoot, etfGWA, "JRN_NO_OLD");
/* 162 */     copyETFNode(etfRoot, etfGWA, "ATH_LVL", "OATH_LVL");
/* 163 */     copyETFNode(etfRoot, etfGWA, "ATH_RSN_LST", "OATH_RSN_LST");
/* 164 */     copyETFNode(etfRoot, etfGWA, "TLR", "INP_TLR");
/* 165 */     copyETFNode(etfRoot, etfGWA, "AP_CD");
/* 166 */     copyETFNode(etfRoot, etfGWA, "TX_CD");
/* 167 */     copyETFNode(etfRoot, etfGWA, "FE_CD");
/* 168 */     String TXN_CD = etfRoot.getChildValue("AP_CD") + etfRoot.getChildValue("TX_CD");
/* 169 */     etfGWA.setChildValue("TXN_CD", TXN_CD);
/*     */ 
/* 174 */     String AC_DT = (String)mapCWA.get("AC_DATE_MD");
/* 175 */     etfRoot.setChildValue("ACC_DT", AC_DT);
/* 176 */     etfGWA.setChildValue("ACC_DT", AC_DT);
/*     */ 
/* 178 */     String JRN_IN_USE = (String)mapCWA.get("JRN_IN_USE");
/* 179 */     etfGWA.setChildValue("JRN_IN_USE", JRN_IN_USE);
/* 180 */     etfGWA.setChildValue("CUR_JRN_NO", JRN_IN_USE);
/*     */ 
/* 182 */     String MST_IN_USE = (String)mapCWA.get("MST_IN_USE");
/* 183 */     etfGWA.setChildValue("MST_IN_USE", MST_IN_USE);
/* 184 */     etfGWA.setChildValue("MST_JRN_NO", MST_IN_USE);
/*     */ 
/* 186 */     Calendar calendar = Calendar.getInstance();
/* 187 */     String date = DateFormatUtils.format(calendar.getTime(), "yyyyMMDD");
/* 188 */     String time = DateFormatUtils.format(calendar.getTime(), "HHmmss");
/* 189 */     etfGWA.setChildValue("TX_DT", date);
/* 190 */     etfGWA.setChildValue("TX_TM", time);
/* 191 */     etfRoot.setChildValue("TX_DT", date);
/* 192 */     etfRoot.setChildValue("TX_TM", time);
/* 193 */     etfGWA.setChildValue("ACT_ACC_DT", " ");
/* 194 */     etfGWA.setChildValue("VCH_NO", " ");
/* 195 */     etfGWA.setChildValue("CMP_NM", " ");
/* 196 */     etfGWA.setChildValue("FILLER", " ");
/* 197 */     etfGWA.setChildValue("ACT_ACC_DT", " ");
/* 198 */     etfGWA.setChildValue("WFF_FLG", "N");
/* 199 */     etfGWA.setChildValue("CAN_FLG", "N");
/* 200 */     etfGWA.setChildValue("REEN_FLG", "N");
/*     */ 
/* 205 */     etfGWA.addNode("TLR_AREA");
/*     */ 
/* 207 */     return 0;
/*     */   }
/*     */ 
/*     */   public int combineETF(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 221 */     HiMessage mess = ctx.getCurrentMsg();
/* 222 */     HiETF etfRoot = (HiETF)mess.getBody();
/* 223 */     HiETF pSrcRoot = etfRoot;
/* 224 */     Logger log = HiLog.getLogger(mess);
/*     */ 
/* 226 */     String sourName = argsMap.get("SourETF");
/* 227 */     String destName = argsMap.get("DestETF");
/* 228 */     String SourRootName = argsMap.get("SourRootName");
/* 229 */     String DestRootName = argsMap.get("DestRootName");
/*     */ 
/* 231 */     if (log.isDebugEnabled())
/*     */     {
/* 233 */       log.debug("[CombinETF] Input: SourETF=" + sourName + ",DestETF=" + destName + ",SourRootName=" + SourRootName + ",DestRootName=" + DestRootName);
/*     */     }
/*     */ 
/* 237 */     if (StringUtils.isEmpty(destName))
/*     */     {
/* 239 */       log.error("Get Argrument [DestETF] failure!");
/* 240 */       throw new HiException("220026", "CombineETF : 获取参数DestETF失败");
/*     */     }
/*     */ 
/* 244 */     HiETF pDstRoot = (HiETF)ctx.getBaseSource(destName);
/* 245 */     if (pDstRoot == null)
/*     */     {
/* 247 */       log.error("Get Datasource [" + destName + "] failure!");
/* 248 */       throw new HiException("220320", destName);
/*     */     }
/*     */ 
/* 254 */     if (StringUtils.isNotEmpty(DestRootName))
/*     */     {
/* 256 */       pDstRoot = pDstRoot.getChildNode(DestRootName);
/* 257 */       if (pDstRoot == null)
/*     */       {
/* 259 */         log.error("Get node [" + DestRootName + "] failure!");
/* 260 */         throw new HiException("220065", DestRootName);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 265 */     if (StringUtils.isNotEmpty(sourName))
/*     */     {
/* 267 */       pSrcRoot = (HiETF)ctx.getBaseSource(sourName);
/* 268 */       if (pSrcRoot == null)
/*     */       {
/* 270 */         log.error("Get Datasource [" + sourName + "] failure!");
/* 271 */         throw new HiException("220320", sourName);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 278 */     if (StringUtils.isNotEmpty(SourRootName))
/*     */     {
/* 280 */       pSrcRoot = pSrcRoot.getChildNode(SourRootName);
/* 281 */       if (pSrcRoot == null)
/*     */       {
/* 283 */         log.error("Get node [" + SourRootName + "] failure!");
/* 284 */         throw new HiException("220065", SourRootName);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 291 */     String replaceFlag = argsMap.get("ReplaceFlag");
/* 292 */     if (StringUtils.equals(replaceFlag, "Y"))
/*     */     {
/* 294 */       pDstRoot.combine(pSrcRoot, true);
/*     */     }
/*     */     else
/*     */     {
/* 298 */       pDstRoot.combine(pSrcRoot, false);
/*     */     }
/* 300 */     return 0;
/*     */   }
/*     */ 
/*     */   public int copyGroup(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 315 */     HiMessage mess = ctx.getCurrentMsg();
/* 316 */     HiETF etfRoot = (HiETF)mess.getBody();
/* 317 */     Logger log = HiLog.getLogger(mess);
/* 318 */     HiETF currSourGroupNode = null;
/* 319 */     HiETF currDestGroupNode = null;
/*     */ 
/* 321 */     String strSourGroupName = argsMap.get("SourGroupName");
/* 322 */     String strDestGroupName = argsMap.get("DestGroupName");
/* 323 */     String strSourBeginSeq = argsMap.get("SourBeginSeq");
/* 324 */     String strDestBeginSeq = argsMap.get("DestBeginSeq");
/* 325 */     String strCount = argsMap.get("Count");
/* 326 */     String strRecordNumName = argsMap.get("RecordNumName");
/*     */ 
/* 330 */     if ((StringUtils.isEmpty(strSourGroupName)) || (StringUtils.isEmpty(strDestGroupName)))
/*     */     {
/* 332 */       throw new HiException("220049");
/*     */     }
/*     */ 
/* 335 */     int sourBeginSeq = 1;
/* 336 */     if (StringUtils.isNotEmpty(strSourBeginSeq)) {
/* 337 */       sourBeginSeq = Integer.parseInt(strSourBeginSeq);
/*     */     }
/* 339 */     int destBeginSeq = 1;
/* 340 */     if (StringUtils.isNotEmpty(strDestBeginSeq)) {
/* 341 */       destBeginSeq = Integer.parseInt(strDestBeginSeq);
/*     */     }
/* 343 */     int sum = 0;
/* 344 */     int count = 0;
/* 345 */     if (StringUtils.isNotEmpty(strCount)) {
/* 346 */       count = Integer.parseInt(strCount);
/*     */     }
/*     */ 
/* 349 */     while ((count <= 0) || (sum < count))
/*     */     {
/* 351 */       String currSourGroupName = strSourGroupName + "_" + Integer.toString(sourBeginSeq + sum);
/* 352 */       String currDestGroupName = strDestGroupName + "_" + Integer.toString(destBeginSeq + sum);
/* 353 */       currSourGroupNode = etfRoot.getChildNode(currSourGroupName);
/* 354 */       if (currSourGroupNode == null) {
/*     */         break;
/*     */       }
/*     */ 
/* 358 */       currDestGroupNode = currSourGroupNode.cloneNode();
/* 359 */       currDestGroupNode.setName(currDestGroupName);
/*     */ 
/* 361 */       etfRoot.removeChildNode(currDestGroupName);
/* 362 */       etfRoot.appendNode(currDestGroupNode);
/*     */ 
/* 347 */       ++sum;
/*     */     }
/*     */ 
/* 365 */     if (StringUtils.isNotEmpty(strRecordNumName))
/*     */     {
/* 367 */       etfRoot.addNode(strRecordNumName, Integer.toString(sum));
/*     */     }
/* 369 */     return 0;
/*     */   }
/*     */ 
/*     */   public int DEBUG(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 383 */     HiMessage mess = ctx.getCurrentMsg();
/* 384 */     HiETF etfRoot = (HiETF)mess.getBody();
/* 385 */     Logger log = HiLog.getLogger(mess);
/*     */ 
/* 387 */     log.error("======= Current ETF:\n[" + etfRoot.toString() + "]\n");
/*     */ 
/* 389 */     return 0;
/*     */   }
/*     */ }