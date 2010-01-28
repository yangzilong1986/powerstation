 package com.hisun.mon.handler;
 
 import com.hisun.exception.HiException;
 import com.hisun.hilog4j.Logger;
 import com.hisun.message.HiContext;
 import com.hisun.message.HiETF;
 import com.hisun.message.HiMessage;
 import com.hisun.message.HiMessageContext;
 import com.hisun.pubinterface.IHandler;
 
 public class HiSetMsgHeaderItem
   implements IHandler
 {
   final Logger log = (Logger)HiContext.getCurrentContext()
     .getProperty("SVR.log");
 
   private String stc = "STC";
   private String node = "RTxnCd";
 
   public void setStc(String code) {
     this.stc = code;
   }
 
   public void setNode(String node) {
     this.node = node;
   }
 
   public void process(HiMessageContext ctx)
     throws HiException
   {
     HiMessage msg = ctx.getCurrentMsg();
 
     HiETF etf = msg.getETFBody();
     msg.setHeadItem(this.stc, etf.getGrandChildValue(this.node));
   }
 }