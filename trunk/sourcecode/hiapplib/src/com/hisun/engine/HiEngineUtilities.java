/*     */ package com.hisun.engine;
/*     */ 
/*     */ import com.hisun.exception.HiException;
/*     */ import com.hisun.hilog4j.HiLog;
/*     */ import com.hisun.hilog4j.Logger;
/*     */ import com.hisun.message.HiETF;
/*     */ import com.hisun.message.HiMessage;
/*     */ import com.hisun.message.HiMessageContext;
/*     */ import com.hisun.util.HiStringManager;
/*     */ import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentHashMap;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class HiEngineUtilities
/*     */ {
/*     */   private static final String CUR_FLOW_STEP = "CurFlowStep";
/*     */ 
/*     */   public static boolean isInnerMessage(HiMessage mess)
/*     */     throws HiException
/*     */   {
/*  77 */     Logger log = HiLog.getLogger(mess);
/*  78 */     String strType = mess.getHeadItem("ECT");
/*  79 */     if (strType == null) {
/*  80 */       throw new HiException("213332", "ECT");
/*     */     }
/*     */ 
/*  84 */     return "text/etf".equals(strType);
/*     */   }
/*     */ 
/*     */   private static void processBAS(String strName, Object value, boolean isSet, String strSign, HiMessageContext messContext)
/*     */   {
/*  97 */     String strKey = StringUtils.substringAfter(strName, strSign);
/*  98 */     if (isSet)
/*  99 */       messContext.setBaseSource(strKey, value);
/*     */     else
/* 101 */       messContext.removeBaseSource(strKey);
/*     */   }
/*     */ 
/*     */   private static void processETF(HiMessage mess, String strName, Object value, boolean isSet, String strSign)
/*     */   {
/* 116 */     String strKey = StringUtils.substringAfter(strName, strSign);
/* 117 */     HiETF etf = (HiETF)mess.getBody();
/* 118 */     if (isSet)
/* 119 */       etf.setGrandChildNode(strKey, value.toString());
/*     */     else
/* 121 */       etf.removeChildNode(strKey);
/*     */   }
/*     */ 
/*     */   public static void processFlow(String strName, Object value, boolean isSet, HiMessageContext messContext)
/*     */     throws HiException
/*     */   {
/* 135 */     HiMessage mess = messContext.getCurrentMsg();
/* 136 */     Logger log = HiLog.getLogger(mess);
/*     */ 
/* 138 */     if (log.isDebugEnabled()) {
/* 139 */       if (value == null) {
/* 140 */         log.debug(HiStringManager.getManager().getString("HiEngineUtilities.processFlow", strName));
/*     */       }
/*     */       else {
/* 143 */         log.debug(HiStringManager.getManager().getString("HiEngineUtilities.processFlow1", strName, value));
/*     */       }
/*     */     }
/*     */ 
/* 147 */     if (strName.startsWith("@ETF")) {
/* 148 */       processETF(mess, strName, value, isSet, "@ETF.");
/* 149 */     } else if (strName.startsWith("$")) {
/* 150 */       processETF(mess, strName, value, isSet, "$");
/* 151 */     } else if (strName.startsWith("@BAS")) {
/* 152 */       processBAS(strName, value, isSet, "@BAS.", messContext);
/*     */     }
/* 154 */     else if (strName.startsWith("~")) {
/* 155 */       processBAS(strName, value, isSet, "~", messContext);
/* 156 */     } else if (strName.startsWith("@MSG")) {
/* 157 */       processMess(mess, strName, value, isSet, "@MSG.");
/* 158 */     } else if (strName.startsWith("%")) {
/* 159 */       processMess(mess, strName, value, isSet, "%"); } else {
/* 160 */       if (strName.startsWith("@BCFG"))
/*     */         return;
/* 162 */       if (strName.startsWith("@PARA")) {
/* 163 */         messContext.setPara(StringUtils.substringAfter(strName, "@PARA."), value);
/* 164 */       } else if (strName.startsWith("#")) {
/* 165 */         messContext.setPara(StringUtils.substringAfter(strName, "#"), value);
/* 166 */       } else if (strName.startsWith("@")) {
/* 167 */         setValueToDS(messContext, strName, value);
/*     */       } else {
/* 169 */         HiETF etf = (HiETF)mess.getBody();
/* 170 */         if (isSet)
/* 171 */           if (value == null)
/* 172 */             etf.setGrandChildNode(strName, "");
/*     */           else
/* 174 */             etf.setGrandChildNode(strName, value.toString());
/*     */         else
/* 176 */           etf.removeGrandChild(strName); 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void setValueToDS(HiMessageContext ctx, String name, Object value) throws HiException {
/* 181 */     String key = null;
/*     */ 
/* 183 */     int idx = name.indexOf(".");
/* 184 */     Object o = ctx.getBaseSource(name.substring(1, idx));
/* 185 */     key = name.substring(idx + 1);
/* 186 */     if (o instanceof HiETF)
/* 187 */       ((HiETF)o).setGrandChildNode(key, value.toString());
/* 188 */     else if (o instanceof HiMessage)
/* 189 */       ((HiMessage)o).setHeadItem(key, value);
/* 190 */     else if (o instanceof ConcurrentHashMap)
/* 191 */       ((ConcurrentHashMap)o).put(key, value);
/*     */     else
/* 193 */       throw new HiException("220320", name.substring(1, idx));
/*     */   }
/*     */ 
/*     */   private static void processMess(HiMessage mess, String strName, Object value, boolean isSet, String strSign)
/*     */   {
/* 207 */     String strKey = StringUtils.substringAfter(strName, strSign);
/* 208 */     if (isSet)
/* 209 */       mess.setHeadItem(strKey, value);
/*     */     else
/* 211 */       mess.delHeadItem(strKey);
/*     */   }
/*     */ 
/*     */   public static String getCurFlowStep() {
/* 215 */     String flowStep = HiMessageContext.getCurrentMessageContext().getStrProp("CurFlowStep");
/*     */ 
/* 217 */     if (flowStep == null)
/* 218 */       flowStep = "1";
/* 219 */     return StringUtils.leftPad(flowStep, 2, '0');
/*     */   }
/*     */ 
/*     */   public static void setCurFlowStep(int i) {
/* 223 */     HiMessageContext ctx = HiMessageContext.getCurrentMessageContext();
/* 224 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 225 */     if (log.isInfoEnabled())
/* 226 */       HiMessageContext.getCurrentMessageContext().setProperty("CurFlowStep", String.valueOf(i + 1));
/*     */   }
/*     */ 
/*     */   public static void timeoutCheck(HiMessage message)
/*     */     throws HiException
/*     */   {
/* 247 */     Logger log = HiLog.getLogger(message);
/* 248 */     if (log.isDebugEnabled()) {
/* 249 */       log.debug("HiRouterOut.timeoutCheck() - start");
/*     */     }
/*     */ 
/* 252 */     Object o = message.getObjectHeadItem("ETM");
/* 253 */     if (!(o instanceof Long)) {
/* 254 */       if (log.isDebugEnabled()) {
/* 255 */         log.debug("HiRouterOut.timeoutCheck() - end");
/*     */       }
/* 257 */       return;
/*     */     }
/* 259 */     long etm = ((Long)o).longValue();
/* 260 */     if ((etm > 0L) && (System.currentTimeMillis() > etm)) {
/* 261 */       throw new HiException("212001");
/*     */     }
/*     */ 
/* 264 */     if (log.isDebugEnabled())
/* 265 */       log.debug("HiRouterOut.timeoutCheck() - end");
/*     */   }
/*     */ }