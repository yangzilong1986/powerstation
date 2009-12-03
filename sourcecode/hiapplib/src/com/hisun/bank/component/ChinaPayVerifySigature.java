/*    */ package com.hisun.bank.component;
/*    */ 
/*    */ import chinapay.PrivateKey;
/*    */ import chinapay.SecureLink;
/*    */ import com.hisun.atc.common.HiArgUtils;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ 
/*    */ public class ChinaPayVerifySigature
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 40 */     String merId = HiArgUtils.getStringNotNull(args, "MerId");
/* 41 */     String keyFile = HiArgUtils.getStringNotNull(args, "KeyFile");
/* 42 */     String signData = HiArgUtils.getStringNotNull(args, "SignData");
/* 43 */     String checkVal = HiArgUtils.getStringNotNull(args, "CheckVal");
/*    */ 
/* 45 */     HiMessage msg = ctx.getCurrentMsg();
/* 46 */     Logger log = HiLog.getLogger(msg);
/*    */ 
/* 48 */     PrivateKey key = new PrivateKey();
/* 49 */     if (!(key.buildKey(merId, 0, keyFile))) {
/* 50 */       log.error("[" + merId + "][" + keyFile + "] build key failure");
/* 51 */       return -1;
/*    */     }
/* 53 */     SecureLink secureLink = new SecureLink(key);
/* 54 */     if (secureLink.verifyAuthToken(signData, checkVal)) {
/* 55 */       return 0;
/*    */     }
/* 57 */     return 1;
/*    */   }
/*    */ }