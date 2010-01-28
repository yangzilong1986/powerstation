 package com.hisun.bank.component;
 
 import com.EasyLink.OpenVendorV34.NetTran;
 import com.hisun.exception.HiException;
 import com.hisun.hilib.HiATLParam;
 import com.hisun.hilog4j.HiLog;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 
 public class NetGneteCheckAccount
 {
   private Logger log1;
 
   public NetGneteCheckAccount()
   {
     this.log1 = HiLog.getLogger("callproc.trc");
   }
 
   public int execute(HiATLParam args, HiMessageContext ctx)
     throws HiException
   {
     String MerId = args.get("MerId");
     String UserId = args.get("UserId");
     String Pwd = args.get("Pwd");
     String PaySuc = args.get("PaySuc");
     String ShoppingTime = args.get("ShoppingTime");
     String BeginTime = args.get("BeginTime");
     String EndTime = args.get("EndTime");
     String OrderNo = args.get("OrderNo");
     String result = args.get("result");
 
     NetTran netTran = new NetTran();
     boolean bret = netTran.GetResult(MerId, UserId, Pwd, PaySuc, ShoppingTime, BeginTime, EndTime, OrderNo);
 
     HiMessage msg = ctx.getCurrentMsg();
     HiETF root = msg.getETFBody();
 
     String resultVar = "";
     if (bret)
       result = "1";
     else
       result = "0";
     root.setChildValue(result, resultVar);
 
     return 0;
   }
 }