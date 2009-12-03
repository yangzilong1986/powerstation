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
/*    */ public class CCBSigature
/*    */ {
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 36 */     HiMessage msg = ctx.getCurrentMsg();
/* 37 */     Logger log = HiLog.getLogger(msg);
/* 38 */     HiETF root = msg.getETFBody();
/*    */ 
/* 40 */     RSASig rsa = new RSASig();
/* 41 */     String key = HiArgUtils.getStringNotNull(args, "key");
/* 42 */     String flds = HiArgUtils.getStringNotNull(args, "flds");
/* 43 */     String dstFld = args.get("dstFld");
/* 44 */     if (StringUtils.isBlank(dstFld)) {
/* 45 */       dstFld = "SgnVal";
/*    */     }
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
/* 58 */     rsa.setPrivateKey(key);
/* 59 */     String sign = rsa.generateSigature(buf.toString());
/* 60 */     root.setChildValue(dstFld, sign);
/* 61 */     return 0;
/*    */   }
/*    */ }