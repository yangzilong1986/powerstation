 package com.hisun.bank.component;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 
 public class NetCmbChinaOrderId
 {
   private Logger log1;
 
   public NetCmbChinaOrderId()
   {
     this.log1 = HiLog.getLogger("callproc.trc");
   }
 
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String sequence = args.get("sequence");
     this.log1.info("sequence=" + sequence);
     String orderId = args.get("orderId");
     this.log1.info("orderId=" + orderId);
 
     HiMessage msg = ctx.getCurrentMsg();
     HiETF root = msg.getETFBody();
 
     String stemp = "000000" + sequence;
     stemp = stemp.substring(stemp.length() - 6);
 
     this.log1.info(orderId + "=" + stemp);
     root.setChildValue(orderId, stemp);
 
     return 0;
   }
 }