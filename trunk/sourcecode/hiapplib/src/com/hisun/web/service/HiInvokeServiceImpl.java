 package com.hisun.web.service;
 
 import com.hisun.dispatcher.HiRouterOut;
 import com.hisun.exception.HiException;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 
 public class HiInvokeServiceImpl
   implements IInvokeService
 {
   public HiETF invoke(HiETF reqETF)
     throws HiException
   {
     return reqETF;
   }
 
   public HiMessage invoke(HiMessage msg) throws HiException {
     HiMessageContext ctx = new HiMessageContext();
     ctx.setCurrentMsg(msg);
     HiMessageContext.setCurrentContext(ctx);
     msg = HiRouterOut.syncProcess(msg);
     return msg;
   }
 }