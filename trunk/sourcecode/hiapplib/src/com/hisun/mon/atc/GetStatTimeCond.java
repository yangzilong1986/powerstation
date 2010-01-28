 package com.hisun.mon.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 
 public class GetStatTimeCond
 {
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String[] arr = { "000000", "010000", "020000", "030000", "040000", "050000", "060000", "070000", "080000", 
       "090000", "100000", "110000", "120000", "130000", "140000", "150000", "160000", "170000", "180000", 
       "190000", "200000", "210000", "220000", "230000", "240000" };
 
     int num = Integer.parseInt(HiArgUtils.getStringNotNull(args, "CND_NUM"));
     if ((num <= 0) || (num > 24)) {
       return 2;
     }
     HiETF etf = ctx.getCurrentMsg().getETFBody();
     etf.setChildValue("BEG_STS", arr[(num - 1)]);
     etf.setChildValue("END_STS", arr[num]);
     return 0;
   }
 }