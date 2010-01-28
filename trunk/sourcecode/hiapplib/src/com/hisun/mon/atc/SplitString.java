 package com.hisun.mon.atc;
 
 import com.hisun.atc.common.HiArgUtils;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import org.apache.commons.lang.StringUtils;
 
 public class SplitString
 {
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String list = HiArgUtils.getStringNotNull(args, "list");
     String sepr = args.get("sepr");
     if (StringUtils.isEmpty(sepr)) {
       sepr = "|";
     }
     String grpNm = args.get("group");
     if (StringUtils.isEmpty(grpNm)) {
       grpNm = "GROUP";
     }
     String[] strArr = StringUtils.split(list, sepr);
     HiETF body = ctx.getCurrentMsg().getETFBody();
     int i = 0;
     while (i < strArr.length)
     {
       body.setGrandChildNode(grpNm + "_" + (i + 1) + ".data", strArr[i]);
       ++i;
     }
     body.setGrandChildNode(grpNm + "_NUM", String.valueOf(strArr.length));
     return 0;
   }
 }