 package com.hisun.bank.component;
 
 import chinapay.PrivateKey;
 import chinapay.SecureLink;
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import org.apache.commons.lang.StringUtils;
 
 public class ChinaPayVerifySignOrder
 {
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String merId = HiArgUtils.getStringNotNull(args, "MerId");
     String ordId = HiArgUtils.getStringNotNull(args, "OrdId");
     String transAmt = HiArgUtils.getStringNotNull(args, "TransAmt");
     String curyId = HiArgUtils.getStringNotNull(args, "CuryId");
     String transDate = HiArgUtils.getStringNotNull(args, "TransDate");
     String transType = HiArgUtils.getStringNotNull(args, "TransType");
     String orderStatus = HiArgUtils.getStringNotNull(args, "OrderStatus");
     String checkValue = HiArgUtils.getStringNotNull(args, "CheckValue");
     String keyFile = HiArgUtils.getStringNotNull(args, "KeyFile");
     String dstFld = args.get("dstFld");
     if (StringUtils.isBlank(dstFld)) {
       dstFld = "ChkValue";
     }
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
 
     PrivateKey key = new PrivateKey();
     if (!(key.buildKey(merId, 0, keyFile))) {
       log.error("[" + merId + "][" + keyFile + "] build key failure");
       return -1;
     }
     SecureLink secureLink = new SecureLink(key);
     if (secureLink.verifyTransResponse(merId, ordId, transAmt, curyId, transDate, transType, orderStatus, checkValue))
     {
       return 0;
     }
     return 1;
   }
 }