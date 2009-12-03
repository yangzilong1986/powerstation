/*    */ package com.hisun.mon.atc;
/*    */ 
/*    */ import com.hisun.atc.common.HiArgUtils;
/*    */ import com.hisun.atc.common.HiDbtSqlHelper;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import com.hisun.mon.HiDataBaseUtilExt;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.Set;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class QueryOneRecord
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 40 */     HiMessage msg = ctx.getCurrentMsg();
/* 41 */     Logger log = HiLog.getLogger(msg);
/* 42 */     if (log.isDebugEnabled()) {
/* 43 */       log.debug("Start: QueryOneRecord");
/*    */     }
/* 45 */     if ((args == null) || (args.size() == 0)) {
/* 46 */       throw new HiException("215110");
/*    */     }
/*    */ 
/* 49 */     String sqlCmd = HiArgUtils.getStringNotNull(args, "SqlCmd");
/* 50 */     HiETF etfBody = (HiETF)msg.getBody();
/* 51 */     boolean escape = args.getBoolean("escape");
/* 52 */     String sqlSentence = HiDbtSqlHelper.getDynSentence(ctx, sqlCmd, etfBody, escape);
/*    */ 
/* 57 */     String varList = args.get("VarList");
/*    */ 
/* 61 */     String varType = args.get("VarType");
/*    */ 
/* 63 */     String[] varArr = (String[])null;
/* 64 */     String[] varTypeArr = (String[])null;
/* 65 */     if (StringUtils.isNotBlank(varList)) {
/* 66 */       varArr = StringUtils.split(varList, '|');
/* 67 */       varTypeArr = StringUtils.split(varType, '|');
/*    */     }
/* 69 */     if (log.isDebugEnabled()) {
/* 70 */       log.debug(varList);
/* 71 */       log.debug(sqlSentence);
/*    */     }
/* 73 */     Map result = HiDataBaseUtilExt.readRecord(ctx, sqlSentence, varArr, varTypeArr);
/*    */ 
/* 75 */     if ((result == null) || (result.size() == 0)) {
/* 76 */       return 2;
/*    */     }
/* 78 */     Map.Entry recEntry = null;
/* 79 */     Iterator recIt = result.entrySet().iterator();
/*    */ 
/* 81 */     while (recIt.hasNext()) {
/* 82 */       recEntry = (Map.Entry)recIt.next();
/* 83 */       etfBody.setChildValue((String)recEntry.getKey(), 
/* 84 */         (String)recEntry.getValue());
/*    */     }
/*    */ 
/* 87 */     return 0;
/*    */   }
/*    */ }