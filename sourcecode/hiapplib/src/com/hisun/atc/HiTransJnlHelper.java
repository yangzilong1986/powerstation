/*    */ package com.hisun.atc;
/*    */ 
/*    */ import com.hisun.engine.invoke.impl.HiAttributesHelper;
/*    */ import com.hisun.exception.HiException;
/*    */ import com.hisun.message.HiETF;
/*    */ import com.hisun.message.HiMessage;
/*    */ import com.hisun.message.HiMessageContext;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class HiTransJnlHelper
/*    */ {
/*    */   public static void rollBack(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 20 */     invoke(ctx, "RollbackWork");
/*    */   }
/*    */ 
/*    */   public static String queryTansaction(HiMessageContext ctx) throws HiException
/*    */   {
/* 25 */     HiMessage msg = invoke(ctx, "QueryTransaction");
/* 26 */     HiETF root = msg.getETFBody();
/* 27 */     if (root == null)
/* 28 */       return null;
/* 29 */     return root.getChildValue("Status");
/*    */   }
/*    */ 
/*    */   public static void beginWork(HiMessageContext ctx)
/*    */     throws HiException
/*    */   {
/* 35 */     invoke(ctx, "BeginWork");
/*    */   }
/*    */ 
/*    */   private static HiMessage invoke(HiMessageContext ctx, String service) throws HiException {
/* 39 */     HiMessage msg = ctx.getCurrentMsg();
/* 40 */     HiETF etf = msg.getETFBody();
/* 41 */     HiAttributesHelper attrs = HiAttributesHelper.getAttribute(ctx);
/* 42 */     HiTransJnl transJnl = new HiTransJnl();
/*    */ 
/* 44 */     String logNo = ctx.getStrProp("LogNo");
/* 45 */     if (StringUtils.isEmpty(logNo))
/* 46 */       logNo = etf.getChildValue("LogNo");
/* 47 */     transJnl.setTranId(logNo);
/* 48 */     transJnl.setSerNam(ctx.getStrProp("trans_code"));
/* 49 */     transJnl.setActDat(ctx.getStrProp("ActDat"));
/* 50 */     transJnl.setExpSer(attrs.code());
/* 51 */     transJnl.setItv(attrs.interval());
/* 52 */     transJnl.setTmOut(attrs.timeout());
/* 53 */     transJnl.setTimOut(30);
/* 54 */     transJnl.setMaxTms(attrs.maxtimes());
/* 55 */     transJnl.setData(msg.getETFBody().toString());
/* 56 */     return transJnl.invoke(service);
/*    */   }
/*    */ 
/*    */   public static boolean commitWork(HiMessageContext ctx) throws HiException
/*    */   {
/* 61 */     HiMessage msg = invoke(ctx, "CommitWork");
/* 62 */     String rspCod = msg.getETFBody().getChildValue("RspCod");
/*    */ 
/* 64 */     return (!(StringUtils.equals(rspCod, "0000")));
/*    */   }
/*    */ }