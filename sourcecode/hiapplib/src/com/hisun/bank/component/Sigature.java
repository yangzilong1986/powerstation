 package com.hisun.bank.component;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import java.security.MessageDigest;
 import org.apache.commons.lang.StringUtils;
 
 public class Sigature
 {
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
     Logger log = HiLog.getLogger(msg);
     HiETF root = msg.getETFBody();
 
     String alg = HiArgUtils.getStringNotNull(args, "alg");
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
     root.setChildValue(dstFld, MD5(buf.toString().getBytes()));
     return 0;
   }
 
   public static String MD5(byte[] source)
   {
     String s = null;
     char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
     try
     {
       MessageDigest md = MessageDigest.getInstance("MD5");
 
       md.update(source);
       byte[] tmp = md.digest();
 
       char[] str = new char[32];
 
       int k = 0;
       for (int i = 0; i < 16; ++i)
       {
         byte byte0 = tmp[i];
         str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
 
         str[(k++)] = hexDigits[(byte0 & 0xF)];
       }
       s = new String(str);
     }
     catch (Exception e) {
       e.printStackTrace();
     }
     return s;
   }
 }