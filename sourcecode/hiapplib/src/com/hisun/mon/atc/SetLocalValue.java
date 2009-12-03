/*    */ package com.hisun.mon.atc;
/*    */ 
/*    */ import com.hisun.atc.common.HiArgUtils;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiContext;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class SetLocalValue
/*    */ {
/*    */   private static final String LOCAL_DATA_INDEX = "_LOCAL_DATA";
/*    */ 
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 21 */     Logger log = HiLog.getLogger(ctx.getCurrentMsg());
/* 22 */     String index = HiArgUtils.getStringNotNull(args, "index");
/*    */ 
/* 24 */     String[] keyName = StringUtils.split(HiArgUtils.getStringNotNull(args, "keyName"), "|");
/* 25 */     String[] keyValue = StringUtils.split(HiArgUtils.getStringNotNull(args, "keyValue"), "|");
/*    */ 
/* 27 */     Map dataMap = getCurrentDataMap(ctx, index);
/* 28 */     for (int i = 0; i < keyValue.length; ++i) {
/* 29 */       log.info(keyName[i] + ":" + keyValue[i]);
/* 30 */       dataMap.put(keyName[i], keyValue[i]);
/*    */     }
/* 32 */     return 0;
/*    */   }
/*    */ 
/*    */   private Map getCurrentDataMap(HiMessageContext ctx, String index) {
/* 36 */     Map localMap = (HashMap)ctx.getProperty("_LOCAL_DATA");
/* 37 */     if (localMap == null) {
/* 38 */       localMap = new HashMap();
/* 39 */       HiMessageContext.getRootContext().setProperty("_LOCAL_DATA", localMap);
/*    */     }
/* 41 */     Map curMap = (HashMap)localMap.get(index);
/* 42 */     if (curMap == null) {
/* 43 */       curMap = new HashMap();
/* 44 */       localMap.put(index, curMap);
/*    */     }
/*    */ 
/* 47 */     return curMap;
/*    */   }
/*    */ }