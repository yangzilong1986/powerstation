 package com.hisun.atc;
 
 import com.hisun.engine.invoke.impl.HiAttributesHelper;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import org.apache.commons.lang.StringUtils;
 
 public class HiTransJnlHelper
 {
   public static void rollBack(HiMessageContext ctx)
     throws HiException
   {
     invoke(ctx, "RollbackWork");
   }
 
   public static String queryTansaction(HiMessageContext ctx) throws HiException
   {
     HiMessage msg = invoke(ctx, "QueryTransaction");
     HiETF root = msg.getETFBody();
     if (root == null)
       return null;
     return root.getChildValue("Status");
   }
 
   public static void beginWork(HiMessageContext ctx)
     throws HiException
   {
     invoke(ctx, "BeginWork");
   }
 
   private static HiMessage invoke(HiMessageContext ctx, String service) throws HiException {
     HiMessage msg = ctx.getCurrentMsg();
     HiETF etf = msg.getETFBody();
     HiAttributesHelper attrs = HiAttributesHelper.getAttribute(ctx);
     HiTransJnl transJnl = new HiTransJnl();
 
     String logNo = ctx.getStrProp("LogNo");
     if (StringUtils.isEmpty(logNo))
       logNo = etf.getChildValue("LogNo");
     transJnl.setTranId(logNo);
     transJnl.setSerNam(ctx.getStrProp("trans_code"));
     transJnl.setActDat(ctx.getStrProp("ActDat"));
     transJnl.setExpSer(attrs.code());
     transJnl.setItv(attrs.interval());
     transJnl.setTmOut(attrs.timeout());
     transJnl.setTimOut(30);
     transJnl.setMaxTms(attrs.maxtimes());
     transJnl.setData(msg.getETFBody().toString());
     return transJnl.invoke(service);
   }
 
   public static boolean commitWork(HiMessageContext ctx) throws HiException
   {
     HiMessage msg = invoke(ctx, "CommitWork");
     String rspCod = msg.getETFBody().getChildValue("RspCod");
 
     return (!(StringUtils.equals(rspCod, "0000")));
   }
 }