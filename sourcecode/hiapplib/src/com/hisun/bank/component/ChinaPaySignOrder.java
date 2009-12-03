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
/*    */ public class ChinaPaySignOrder
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 49 */     String merId = HiArgUtils.getStringNotNull(args, "MerId");
/* 50 */     String ordId = HiArgUtils.getStringNotNull(args, "OrdId");
/* 51 */     String transAmt = HiArgUtils.getStringNotNull(args, "TransAmt");
/* 52 */     String curyId = HiArgUtils.getStringNotNull(args, "CuryId");
/* 53 */     String transDate = HiArgUtils.getStringNotNull(args, "TransDate");
/* 54 */     String transType = HiArgUtils.getStringNotNull(args, "TransType");
/* 55 */     String keyFile = HiArgUtils.getStringNotNull(args, "KeyFile");
/* 56 */     String dstFld = args.get("dstFld");
/* 57 */     if (StringUtils.isBlank(dstFld)) {
/* 58 */       dstFld = "ChkValue";
/*    */     }
/* 60 */     HiMessage msg = ctx.getCurrentMsg();
/* 61 */     Logger log = HiLog.getLogger(msg);
/*    */ 
/* 63 */     PrivateKey key = new PrivateKey();
/* 64 */     if (!(key.buildKey(merId, 0, keyFile))) {
/* 65 */       log.error("[" + merId + "][" + keyFile + "] build key failure");
/* 66 */       return -1;
/*    */     }
/* 68 */     SecureLink secureLink = new SecureLink(key);
/* 69 */     String chkVal = secureLink.signOrder(merId, ordId, transAmt, curyId, transDate, transType);
/*    */ 
/* 71 */     msg.getETFBody().setChildValue(dstFld, chkVal);
/*    */ 
/* 73 */     return 0;
/*    */   }
/*    */ }