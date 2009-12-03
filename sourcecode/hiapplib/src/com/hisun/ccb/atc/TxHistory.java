/*     */ package com.hisun.ccb.atc;
/*     */ 
/*     */ import com.hisun.atc.common.HiDbtSqlHelper;
/*     */ import com.hisun.atc.common.HiDbtUtils;
/*     */ import com.hisun.database.HiDataBaseUtil;
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilib.HiATLParam;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class TxHistory
/*     */ {
/*     */   public static final String OLD_AREA = "OLD_AREA";
/*     */   public static final String TBL_NM = "TBL_NM";
/*     */   public static final String NEW_AREA = "NEW_AREA";
/*     */   public static final String CLS_NM = "CLS_NM";
/*     */   public static final String CLS_KEY1 = "CLS_KEY1";
/*     */   public static final String CLS_KEY2 = "CLS_KEY2";
/*     */   public static final String CLS_KEY3 = "CLS_KEY3";
/*     */ 
/*     */   public int GenInfDetail(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/*  71 */     HiMessage mess = ctx.getCurrentMsg();
/*  72 */     Logger log = HiLog.getLogger(mess);
/*  73 */     if ((argsMap == null) || (argsMap.size() == 0)) {
/*  74 */       if (log.isErrorEnabled()) {
/*  75 */         log.error("GenInfDetail:参数为空");
/*     */       }
/*  77 */       throw new HiException("220026", "GenInfDetail:参数为空");
/*     */     }
/*     */ 
/*  80 */     String oldArea = argsMap.get("OLD_AREA");
/*  81 */     String tableName = argsMap.get("TBL_NM");
/*  82 */     String newArea = argsMap.get("NEW_AREA");
/*  83 */     String clsName = argsMap.get("CLS_NM");
/*  84 */     String clsKey_1 = argsMap.get("CLS_KEY1");
/*  85 */     String clsKey_2 = argsMap.get("CLS_KEY2");
/*  86 */     String clsKey_3 = argsMap.get("CLS_KEY3");
/*     */ 
/*  89 */     if ((tableName == null) || (tableName.equals(""))) {
/*  90 */       if (log.isErrorEnabled()) {
/*  91 */         log.error("GenInfDetail:table name is null.");
/*     */       }
/*  93 */       throw new HiException("220026", "GenInfDetail: table name is null.");
/*     */     }
/*     */ 
/*  96 */     boolean oldAreaExist = (oldArea != null) && (!(oldArea.equals("")));
/*     */ 
/*  98 */     boolean newAreaExist = (newArea != null) && (!(newArea.equals("")));
/*     */ 
/* 102 */     if ((((newAreaExist) || (oldAreaExist)) ? 1 : 0) == 0) {
/* 103 */       throw new HiException("220026", "GenInfDetail: OLD_AREA  and NEW_AREA both  null.");
/*     */     }
/*     */ 
/* 107 */     HiETF oldETF = null;
/* 108 */     HiETF newETF = null;
/* 109 */     if (oldAreaExist) {
/* 110 */       oldETF = (HiETF)ctx.getBaseSource(oldArea);
/* 111 */       if (oldETF == null) {
/* 112 */         throw new HiException("213122", "GenInfDetail: OLD_AREA  ETF  data is  null.");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 117 */     if (newAreaExist) {
/* 118 */       newETF = (HiETF)ctx.getBaseSource(newArea);
/* 119 */       if (newETF == null) {
/* 120 */         throw new HiException("213122", "GenInfDetail: NEW AREA  ETF  data is  null.");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 125 */     String opt_type = "";
/* 126 */     if ((newAreaExist) && (oldAreaExist))
/* 127 */       opt_type = "M";
/* 128 */     else if (newAreaExist)
/* 129 */       opt_type = "A";
/*     */     else {
/* 131 */       opt_type = "D";
/*     */     }
/*     */ 
/* 134 */     HiDataBaseUtil dbUtil = ctx.getDataBaseUtil();
/* 135 */     HashMap colsMap = dbUtil.getTableMetaData(tableName, dbUtil.getConnection());
/*     */ 
/* 137 */     if ((colsMap == null) || (colsMap.size() == 0)) {
/* 138 */       throw new HiException("220040", "GenInfDetail:  no table meta data info :" + tableName);
/*     */     }
/*     */ 
/* 142 */     if (log.isDebugEnabled()) {
/* 143 */       log.debug("map value object is :" + colsMap);
/*     */     }
/*     */ 
/* 146 */     String[] colNames = (String[])(String[])colsMap.keySet().toArray(new String[0]);
/*     */ 
/* 149 */     StringBuffer oldValue_list = new StringBuffer();
/* 150 */     StringBuffer newValue_list = new StringBuffer();
/* 151 */     StringBuffer fldValue_list = new StringBuffer();
/*     */ 
/* 153 */     for (int i = 0; i < colNames.length; ++i) {
/* 154 */       fldValue_list.append(colNames[i] + "|");
/* 155 */       if (oldAreaExist) {
/* 156 */         oldValue_list.append(oldETF.getChildValue(colNames[i]));
/* 157 */         oldValue_list.append('|');
/*     */       }
/* 159 */       if (newAreaExist) {
/* 160 */         newValue_list.append(newETF.getChildValue(colNames[i]));
/* 161 */         newValue_list.append('|');
/*     */       }
/*     */     }
/* 164 */     if (log.isDebugEnabled()) {
/* 165 */       log.debug("GenInfDetail:  fldValue_list  is:" + fldValue_list);
/* 166 */       log.debug("GenInfDetail:  oldValue_list  is:" + oldValue_list);
/* 167 */       log.debug("GenInfDetail:  newValue_list  is:" + newValue_list);
/*     */     }
/*     */ 
/* 170 */     HiETF pGWA = (HiETF)ctx.getBaseSource("GWA");
/* 171 */     if (pGWA == null) {
/* 172 */       throw new HiException("213122", "GenInfDetail: 取GWA区数据失败.");
/*     */     }
/*     */ 
/* 175 */     HiETF pHisNode = pGWA.getChildNode("HIS");
/* 176 */     String SEQ = pHisNode.getChildValue("SEQ_NO");
/* 177 */     int seq = 0;
/*     */     try {
/* 179 */       seq = Integer.parseInt(SEQ);
/*     */     } catch (NumberFormatException nfe) {
/*     */     }
/* 182 */     if (log.isDebugEnabled()) {
/* 183 */       log.debug("GenInfDetail: SEQ_NO  :" + seq);
/*     */     }
/* 185 */     pHisNode.setChildValue("FLD_LST", fldValue_list.toString());
/* 186 */     pHisNode.setChildValue("OLD_VAL", oldValue_list.toString());
/* 187 */     pHisNode.setChildValue("NEW_VAL", newValue_list.toString());
/* 188 */     pHisNode.setChildValue("CLS_KEY1", clsKey_1);
/* 189 */     pHisNode.setChildValue("CLS_KEY2", clsKey_2);
/* 190 */     pHisNode.setChildValue("CLS_KEY3", clsKey_3);
/* 191 */     pHisNode.setChildValue("OPT_TYP", opt_type);
/* 192 */     pHisNode.setChildValue("CLS_NM", clsName);
/* 193 */     pHisNode.setChildValue("SEQ_NO", "" + (++seq));
/*     */ 
/* 195 */     return 0;
/*     */   }
/*     */ 
/*     */   public int GenInfDetailBySQL(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 209 */     HiMessage msg = ctx.getCurrentMsg();
/* 210 */     Logger log = HiLog.getLogger(msg);
/* 211 */     if ((argsMap == null) || (argsMap.size() == 0)) {
/* 212 */       throw new HiException("220026", "参数为空");
/*     */     }
/*     */ 
/* 215 */     HiETF etfBody = (HiETF)msg.getBody();
/* 216 */     etfBody.setChildValue("CMP_AP_MMO", "CPM");
/* 217 */     etfBody.setChildValue("MSG_CD", "0000");
/*     */ 
/* 219 */     String sql_cmd = argsMap.get("SQL_CMD");
/* 220 */     String newArea = argsMap.get("NEW_AREA");
/* 221 */     String clsName = argsMap.get("CLS_NM");
/* 222 */     String clsKey_1 = argsMap.get("CLS_KEY1");
/* 223 */     String clsKey_2 = argsMap.get("CLS_KEY2");
/* 224 */     String clsKey_3 = argsMap.get("CLS_KEY3");
/*     */ 
/* 226 */     if ((sql_cmd == null) || (sql_cmd.equals(""))) {
/* 227 */       throw new HiException("213122", "GenInfDetailBySQL: 缺少输入参数[SQL_CMD]!");
/*     */     }
/*     */ 
/* 231 */     boolean newAreaExist = (newArea != null) && (!(newArea.equals("")));
/*     */ 
/* 234 */     HiETF newETF = null;
/*     */ 
/* 236 */     if (newAreaExist) {
/* 237 */       newETF = (HiETF)ctx.getBaseSource(newArea);
/* 238 */       if (newETF == null) {
/* 239 */         throw new HiException("213122", "GenInfDetailBySQL: NEW AREA  ETF  data is  null.");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 244 */     String opt_type = "";
/* 245 */     if (newAreaExist)
/* 246 */       opt_type = "M";
/*     */     else {
/* 248 */       opt_type = "D";
/*     */     }
/*     */ 
/* 251 */     String strSQL = HiDbtSqlHelper.getDynSentence(ctx, sql_cmd);
/* 252 */     List queryRs = ctx.getDataBaseUtil().execQuery(strSQL);
/*     */ 
/* 254 */     if ((queryRs != null) && (queryRs.size() == 0)) {
/* 255 */       throw new HiException("220040", "GenInfDetailBySQL: no record .");
/*     */     }
/*     */ 
/* 259 */     Map queryRec = (HashMap)queryRs.get(0);
/* 260 */     Map.Entry recEntry = null;
/*     */ 
/* 262 */     StringBuffer oldValue_list = new StringBuffer();
/* 263 */     StringBuffer newValue_list = new StringBuffer();
/* 264 */     StringBuffer fldValue_list = new StringBuffer();
/* 265 */     Iterator recIt = queryRec.entrySet().iterator();
/*     */ 
/* 267 */     while (recIt.hasNext()) {
/* 268 */       recEntry = (Map.Entry)recIt.next();
/*     */ 
/* 270 */       fldValue_list.append(((String)recEntry.getKey()) + "|");
/* 271 */       oldValue_list.append(((String)recEntry.getValue()) + "|");
/* 272 */       if (newAreaExist);
/* 273 */       newValue_list.append(newETF.getChildValue((String)recEntry.getKey()) + "|");
/*     */     }
/*     */ 
/* 279 */     HiETF pGWA = (HiETF)ctx.getBaseSource("GWA");
/* 280 */     if (pGWA == null) {
/* 281 */       throw new HiException("213122", "GenInfDetailBySQL: 取GWA区数据失败.");
/*     */     }
/*     */ 
/* 285 */     HiETF pHisNode = pGWA.getChildNode("HIS");
/* 286 */     String SEQ = pHisNode.getChildValue("SEQ_NO");
/* 287 */     int seq = 0;
/*     */     try {
/* 289 */       seq = Integer.parseInt(SEQ);
/*     */     } catch (NumberFormatException nfe) {
/*     */     }
/* 292 */     if (log.isDebugEnabled()) {
/* 293 */       log.debug("GenInfDetailBySQL: SEQ_NO  :" + seq);
/*     */     }
/* 295 */     pHisNode.setChildValue("FLD_LST", fldValue_list.toString());
/* 296 */     pHisNode.setChildValue("OLD_VAL", oldValue_list.toString());
/* 297 */     pHisNode.setChildValue("NEW_VAL", newValue_list.toString());
/* 298 */     pHisNode.setChildValue("CLS_KEY1", clsKey_1);
/* 299 */     pHisNode.setChildValue("CLS_KEY2", clsKey_2);
/* 300 */     pHisNode.setChildValue("CLS_KEY3", clsKey_3);
/* 301 */     pHisNode.setChildValue("OPT_TYP", opt_type);
/* 302 */     pHisNode.setChildValue("CLS_NM", clsName);
/* 303 */     pHisNode.setChildValue("SEQ_NO", "" + (++seq));
/*     */ 
/* 305 */     return 0;
/*     */   }
/*     */ 
/*     */   public int RecordInfDetail(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 319 */     HiMessage msg = ctx.getCurrentMsg();
/* 320 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/* 322 */     HiETF etfBody = (HiETF)msg.getBody();
/* 323 */     etfBody.setChildValue("CMP_AP_MMO", "CPM");
/* 324 */     etfBody.setChildValue("MSG_CD", "0000");
/*     */ 
/* 326 */     HiETF pGWA = (HiETF)ctx.getBaseSource("GWA");
/*     */ 
/* 328 */     if (pGWA == null) {
/* 329 */       throw new HiException("213122", "RecordInfDetail: 取GWA区数据失败.");
/*     */     }
/*     */ 
/* 332 */     HiETF pHisNode = pGWA.getChildNode("HIS");
/*     */ 
/* 334 */     int rec = HiDbtUtils.dbtsqlinsrec("PUBTMHDL", pHisNode, ctx);
/* 335 */     if (rec != 0) {
/* 336 */       throw new HiException("220042", "RecordInfDetail: 插入记录失败!");
/*     */     }
/*     */ 
/* 339 */     return 0;
/*     */   }
/*     */ 
/*     */   public int RecordInfMain(HiATLParam argsMap, HiMessageContext ctx)
/*     */     throws HiException
/*     */   {
/* 353 */     HiMessage msg = ctx.getCurrentMsg();
/* 354 */     Logger log = HiLog.getLogger(msg);
/*     */ 
/* 356 */     HiETF etfBody = (HiETF)msg.getBody();
/* 357 */     etfBody.setChildValue("CMP_AP_MMO", "CPM");
/* 358 */     etfBody.setChildValue("MSG_CD", "0000");
/*     */ 
/* 360 */     HiETF pGWA = (HiETF)ctx.getBaseSource("GWA");
/*     */ 
/* 362 */     if (pGWA == null) {
/* 363 */       throw new HiException("213122", "RecordInfMain: 取GWA区数据失败.");
/*     */     }
/*     */ 
/* 367 */     HiETF pHisNode = pGWA.getChildNode("HIS");
/* 368 */     String sup_id1 = pGWA.getChildValue("SUP_ID1");
/* 369 */     pHisNode.setChildValue("ATH_TLR", sup_id1);
/* 370 */     String tia_type = pGWA.getChildValue("TIA_TYP");
/*     */ 
/* 399 */     int rec = HiDbtUtils.dbtsqlinsrec("PUBTMHIS", pHisNode, ctx);
/* 400 */     if (rec != 0) {
/* 401 */       throw new HiException("220042", "RecordInfMain: 插入记录失败!");
/*     */     }
/*     */ 
/* 404 */     return 0;
/*     */   }
/*     */ }