/*    */ package com.hisun.bank.component;
/*    */ 
/*    */ import com.EasyLink.OpenVendorV34.NetTran;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ 
/*    */ public class NetGneteCheckAccount
/*    */ {
/*    */   private Logger log1;
/*    */ 
/*    */   public NetGneteCheckAccount()
/*    */   {
/* 14 */     this.log1 = HiLog.getLogger("callproc.trc");
/*    */   }
/*    */ 
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 30 */     String MerId = args.get("MerId");
/* 31 */     String UserId = args.get("UserId");
/* 32 */     String Pwd = args.get("Pwd");
/* 33 */     String PaySuc = args.get("PaySuc");
/* 34 */     String ShoppingTime = args.get("ShoppingTime");
/* 35 */     String BeginTime = args.get("BeginTime");
/* 36 */     String EndTime = args.get("EndTime");
/* 37 */     String OrderNo = args.get("OrderNo");
/* 38 */     String result = args.get("result");
/*    */ 
/* 40 */     NetTran netTran = new NetTran();
/* 41 */     boolean bret = netTran.GetResult(MerId, UserId, Pwd, PaySuc, ShoppingTime, BeginTime, EndTime, OrderNo);
/*    */ 
/* 44 */     HiMessage msg = ctx.getCurrentMsg();
/* 45 */     HiETF root = msg.getETFBody();
/*    */ 
/* 47 */     String resultVar = "";
/* 48 */     if (bret)
/* 49 */       result = "1";
/*    */     else
/* 51 */       result = "0";
/* 52 */     root.setChildValue(result, resultVar);
/*    */ 
/* 54 */     return 0;
/*    */   }
/*    */ }