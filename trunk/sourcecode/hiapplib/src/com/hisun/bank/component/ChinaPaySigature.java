/*    */ package com.hisun.bank.component;
/*    */ 
/*    */ import chinapay.PrivateKey;
/*    */ import chinapay.SecureLink;
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
/*    */ public class ChinaPaySigature
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 40 */     String merId = HiArgUtils.getStringNotNull(args, "MerId");
/* 41 */     String keyFile = HiArgUtils.getStringNotNull(args, "KeyFile");
/* 42 */     String signData = HiArgUtils.getStringNotNull(args, "SignData");
/* 43 */     String dstFld = args.get("dstFld");
/* 44 */     if (StringUtils.isBlank(dstFld)) {
/* 45 */       dstFld = "ChkValue";
/*    */     }
/*    */ 
/* 48 */     HiMessage msg = ctx.getCurrentMsg();
/* 49 */     Logger log = HiLog.getLogger(msg);
/*    */ 
/* 51 */     PrivateKey key = new PrivateKey();
/* 52 */     if (!(key.buildKey(merId, 0, keyFile))) {
/* 53 */       log.error("[" + merId + "][" + keyFile + "] build key failure");
/* 54 */       return -1;
/*    */     }
/* 56 */     SecureLink secureLink = new SecureLink(key);
/* 57 */     String chkVal = secureLink.Sign(signData);
/* 58 */     msg.getETFBody().setChildValue(dstFld, chkVal);
/* 59 */     return 0;
/*    */   }
/*    */ }