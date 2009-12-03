/*    */ package com.hisun.bank.component;
/*    */ 
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.hilib.HiATLParam;
/*    */ import com.hisun.hilog4j.HiLog;
/*    */ import com.hisun.hilog4j.Logger;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ 
/*    */ public class NetGneteOrderId
/*    */ {
/*    */   private Logger log1;
/*    */ 
/*    */   public NetGneteOrderId()
/*    */   {
/* 15 */     this.log1 = HiLog.getLogger("callproc.trc");
/*    */   }
/*    */ 
/*    */   public int execute(HiATLParam args, HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 24 */     String sequence = args.get("sequence");
/* 25 */     this.log1.info("sequence=" + sequence);
/* 26 */     String orderId = args.get("orderId");
/* 27 */     this.log1.info("orderId=" + orderId);
/*    */ 
/* 29 */     HiMessage msg = ctx.getCurrentMsg();
/* 30 */     HiETF root = msg.getETFBody();
/*    */ 
/* 32 */     String stemp = "00000000000000000" + sequence;
/* 33 */     stemp = stemp.substring(stemp.length() - 17);
/*    */ 
/* 35 */     this.log1.info(orderId + "=" + stemp);
/* 36 */     root.setChildValue(orderId, stemp);
/*    */ 
/* 38 */     return 0;
/*    */   }
/*    */ }