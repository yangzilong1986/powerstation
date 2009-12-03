/*    */ package com.hisun.bank.component;
/*    */ 
/*    */ import CCBSign.RSASig;
/*    */ import com.hisun.atc.common.HiArgUtils;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class CCBVerifySigature
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 40 */     HiMessage msg = ctx.getCurrentMsg();
/* 41 */     Logger log = HiLog.getLogger(msg);
/* 42 */     HiETF root = msg.getETFBody();
/*    */ 
/* 44 */     String key = HiArgUtils.getStringNotNull(args, "key");
/* 45 */     String flds = HiArgUtils.getStringNotNull(args, "flds");
/* 46 */     String sgnVal = HiArgUtils.getStringNotNull(args, "sgnVal");
/* 47 */     String[] tmps = flds.split("\\|");
/* 48 */     StringBuffer buf = new StringBuffer();
/* 49 */     for (int i = 0; i < tmps.length; ++i) {
/* 50 */       String tmpVal = root.getChildValue(tmps[i]);
/* 51 */       if (!(StringUtils.isEmpty(tmpVal))) {
/* 52 */         buf.append(tmps[i] + "=" + tmpVal);
/* 53 */         if (i < tmps.length - 1) {
/* 54 */           buf.append("&");
/*    */         }
/*    */       }
/*    */     }
/* 58 */     RSASig rsa = new RSASig();
/* 59 */     rsa.setPublicKey(key);
/* 60 */     if (rsa.verifySigature(sgnVal, buf.toString())) {
/* 61 */       return 0;
/*    */     }
/* 63 */     return 1;
/*    */   }
/*    */ }