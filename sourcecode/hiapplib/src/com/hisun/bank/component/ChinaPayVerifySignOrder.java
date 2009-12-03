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
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class ChinaPayVerifySignOrder
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 51 */     String merId = HiArgUtils.getStringNotNull(args, "MerId");
/* 52 */     String ordId = HiArgUtils.getStringNotNull(args, "OrdId");
/* 53 */     String transAmt = HiArgUtils.getStringNotNull(args, "TransAmt");
/* 54 */     String curyId = HiArgUtils.getStringNotNull(args, "CuryId");
/* 55 */     String transDate = HiArgUtils.getStringNotNull(args, "TransDate");
/* 56 */     String transType = HiArgUtils.getStringNotNull(args, "TransType");
/* 57 */     String orderStatus = HiArgUtils.getStringNotNull(args, "OrderStatus");
/* 58 */     String checkValue = HiArgUtils.getStringNotNull(args, "CheckValue");
/* 59 */     String keyFile = HiArgUtils.getStringNotNull(args, "KeyFile");
/* 60 */     String dstFld = args.get("dstFld");
/* 61 */     if (StringUtils.isBlank(dstFld)) {
/* 62 */       dstFld = "ChkValue";
/*    */     }
/* 64 */     HiMessage msg = ctx.getCurrentMsg();
/* 65 */     Logger log = HiLog.getLogger(msg);
/*    */ 
/* 67 */     PrivateKey key = new PrivateKey();
/* 68 */     if (!(key.buildKey(merId, 0, keyFile))) {
/* 69 */       log.error("[" + merId + "][" + keyFile + "] build key failure");
/* 70 */       return -1;
/*    */     }
/* 72 */     SecureLink secureLink = new SecureLink(key);
/* 73 */     if (secureLink.verifyTransResponse(merId, ordId, transAmt, curyId, transDate, transType, orderStatus, checkValue))
/*    */     {
/* 75 */       return 0;
/*    */     }
/* 77 */     return 1;
/*    */   }
/*    */ }