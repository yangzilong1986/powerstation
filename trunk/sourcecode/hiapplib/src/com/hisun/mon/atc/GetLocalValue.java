/*    */ package com.hisun.mon.atc;
/*    */ 
/*    */ import com.hisun.atc.common.HiArgUtils;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class GetLocalValue
/*    */ {
/*    */   private static final String LOCAL_DATA_INDEX = "_LOCAL_DATA";
/*    */ 
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 23 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 24 */     String index = HiArgUtils.getStringNotNull(args, "index");
/*    */ 
/* 26 */     String[] keyName = StringUtils.splitByWholeSeparator(HiArgUtils.getStringNotNull(args, "keyName"), "|");
/*    */ 
/* 28 */     Map dataMap = getCurrentDataMap(ctx, index);
/* 29 */     HiETF etf = ctx.getCurrentMsg().getETFBody();
/* 30 */     for (int i = 0; i < keyName.length; ++i) {
/* 31 */       log.info(keyName[i] + ":" + ((String)dataMap.get(keyName[i])));
/* 32 */       etf.setChildValue(keyName[i], (String)dataMap.get(keyName[i]));
/*    */     }
/* 34 */     return 0;
/*    */   }
/*    */ 
/*    */   private Map getCurrentDataMap(HiMessageContext ctx, String index) {
/* 38 */     Map localMap = (HashMap)ctx.getProperty("_LOCAL_DATA");
/* 39 */     if (localMap == null) {
/* 40 */       localMap = new HashMap();
/* 41 */       HiMessageContext.getRootContext().setProperty("_LOCAL_DATA", localMap);
/*    */     }
/* 43 */     Map curMap = (HashMap)localMap.get(index);
/* 44 */     if (curMap == null) {
/* 45 */       curMap = new HashMap();
/* 46 */       localMap.put(index, curMap);
/*    */     }
/*    */ 
/* 49 */     return curMap;
/*    */   }
/*    */ }