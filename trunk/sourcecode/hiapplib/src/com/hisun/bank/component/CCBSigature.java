 package com.hisun.bank.component;
 
 import CCBSign.RSASig;
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import org.apache.commons.lang.StringUtils;
 
 public class CCBSigature
 {
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     HiETF root = msg.getETFBody();
 
     RSASig rsa = new RSASig();
     String key = HiArgUtils.getStringNotNull(args, "key");
     String flds = HiArgUtils.getStringNotNull(args, "flds");
     String dstFld = args.get("dstFld");
     if (StringUtils.isBlank(dstFld)) {
       dstFld = "SgnVal";
     }
     String[] tmps = flds.split("\\|");
     StringBuffer buf = new StringBuffer();
     for (int i = 0; i < tmps.length; ++i) {
       String tmpVal = root.getChildValue(tmps[i]);
       if (!(StringUtils.isEmpty(tmpVal))) {
         buf.append(tmps[i] + "=" + tmpVal);
         if (i < tmps.length - 1) {
           buf.append("&");
         }
       }
     }
     rsa.setPrivateKey(key);
     String sign = rsa.generateSigature(buf.toString());
     root.setChildValue(dstFld, sign);
     return 0;
   }
 }